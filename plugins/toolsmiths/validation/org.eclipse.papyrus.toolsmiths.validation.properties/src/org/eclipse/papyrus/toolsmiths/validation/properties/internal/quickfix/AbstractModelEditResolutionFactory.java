/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.quickfix;

import static java.util.function.Predicate.not;
import static org.eclipse.papyrus.infra.tools.util.Iterators2.autoPrune;
import static org.eclipse.papyrus.infra.tools.util.Iterators2.filter;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MARKER_ATTR_LAYOUT_GENERATOR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.customization.properties.generation.generators.GeneratorHelper;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsFactory;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.UnknownProperty;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.Widget;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.tools.util.TriFunction;
import org.eclipse.papyrus.infra.tools.util.Try;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleModelEditMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.Activator;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace.ComposedSourceTraceHelper;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace.SourceTraceHelper;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertyTypeHelper;
import org.eclipse.ui.IMarkerResolution;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * Abstraction of a factory for quick fixes that add or remove properties context model elements.
 * It is not called a "generator" because it does not implement the Eclipse marker-resolution generator API.
 */
abstract class AbstractModelEditResolutionFactory {

	private final int problemID;
	private final SourceTraceHelper traceHelper;

	private final ThreadLocal<IMarker> marker = new ThreadLocal<>();

	AbstractModelEditResolutionFactory(int problemID) {
		super();

		this.problemID = problemID;
		this.traceHelper = new ComposedSourceTraceHelper();
	}

	protected final IMarker getMarker() {
		return marker.get();
	}

	protected final int getProblemID() {
		return problemID;
	}

	protected final SourceTraceHelper getTraceHelper() {
		return traceHelper;
	}

	public Iterable<IMarkerResolution> createResolutions(IMarker marker) {
		return withMarker(marker, this::createResolutions);
	}

	private <T> T withMarker(IMarker marker, Supplier<T> computation) {
		this.marker.set(marker);

		try {
			return computation.get();
		} finally {
			this.marker.remove();
		}
	}

	protected abstract Iterable<IMarkerResolution> createResolutions();

	protected <T extends EObject> IMarkerResolution createResolution(String label, String description, Class<T> type, BiFunction<? super EditingDomain, ? super T, ? extends Command> command) {
		return SimpleModelEditMarkerResolution.create(getProblemID(), label, description, type, withMarker(command));
	}

	private <T extends EObject> TriFunction<EditingDomain, T, IMarker, Command> withMarker(BiFunction<? super EditingDomain, ? super T, ? extends Command> command) {
		return (domain, object, marker) -> withMarker(marker, () -> command.apply(domain, object));
	}

	@SafeVarargs
	protected final <T extends EObject> TriFunction<EditingDomain, T, IMarker, Command> compose(TriFunction<? super EditingDomain, ? super T, ? super IMarker, ? extends Command>... commands) {
		return (domain, object, marker) -> {
			CompoundCommand result = new CompoundCommand();

			for (TriFunction<? super EditingDomain, ? super T, ? super IMarker, ? extends Command> commandFunction : commands) {
				Command command = commandFunction.apply(domain, object, marker);
				if (command != null) {
					result.append(command);
				}
			}

			return result.unwrap();
		};
	}

	protected Iterator<CompositeWidget> widgeterator(Section section) {
		return widgeterator(section, CompositeWidget.class);
	}

	protected <T extends Widget> Iterator<T> widgeterator(Section section, Class<? extends T> type) {
		CompositeWidget composite = section.getWidget();

		if (composite == null || composite.eIsProxy()) {
			return Collections.emptyIterator();
		}

		return autoPrune(filter(EcoreUtil.getAllContents(Set.of(composite)), type), not(CompositeWidget.class::isInstance));
	}

	protected Context getContext(DataContextElement element) {
		EObject result = EcoreUtil.getRootContainer(element);
		return (result instanceof Context) ? (Context) result : null;
	}

	protected int getElementMultiplicity(Section section) {
		int result = SourceTraceHelper.DEFAULT_MULTIPLICITY;

		for (View view : section.getViews()) {
			result = view.getElementMultiplicity();

			if (result != SourceTraceHelper.DEFAULT_MULTIPLICITY) {
				break;
			}
		}

		return result;
	}

	/**
	 * Find an existing property editor for a {@code property} in a {@code section}.
	 *
	 * @param section
	 *            a section
	 * @param property
	 *            a data-context property
	 * @return the existing property editor in the {@code section}, or {@code null} if none
	 */
	protected PropertyEditor getPropertyEditor(Section section, Property property) {
		return Iterators.find(widgeterator(section, PropertyEditor.class), pe -> isPropertyEditorFor(pe, property), null);
	}

	/**
	 * Find an existing property editor for a {@code property} of a data-context {@code element} in a {@code section}.
	 * This is useful in cases where the {@code property} is not yet added to its owning {@code element}.
	 *
	 * @param section
	 *            a section
	 * @param element
	 *            a data-context element
	 * @param property
	 *            a property of the {@code element}
	 * @return the existing property editor in the {@code section}, or {@code null} if none
	 */
	protected PropertyEditor getPropertyEditor(Section section, DataContextElement element, Property property) {
		return Iterators.find(widgeterator(section, PropertyEditor.class), pe -> isPropertyEditorFor(pe, element, property), null);
	}

	/**
	 * Query whether a property editor edits a data-context {@code property}.
	 *
	 * @param propertyEditor
	 *            a property editor
	 * @param property
	 *            a property
	 * @return whether the editor edits the {@code property}
	 */
	protected boolean isPropertyEditorFor(PropertyEditor propertyEditor, Property property) {
		boolean result = propertyEditor.getProperty() == property;

		if (!result) {
			// Perhaps it's an unresolved property reference
			String qname = getQualifiedName(property);
			UnknownProperty unknown = propertyEditor.getUnresolvedProperty();
			result = unknown != null && qname.equals(unknown.getName());
		}

		return result;
	}

	/**
	 * Query whether a property editor edits a {@code property} of a data-context {@code element}.
	 * This is useful in cases where the {@code property} is not yet added to its owning {@code element}.
	 *
	 * @param propertyEditor
	 *            a property editor
	 * @param element
	 *            a data-context element
	 * @param property
	 *            a property of the {@code element}
	 * @return whether the editor edits the {@code property}
	 */
	protected boolean isPropertyEditorFor(PropertyEditor propertyEditor, DataContextElement element, Property property) {
		boolean result = propertyEditor.getProperty() == property;

		if (!result) {
			// Perhaps it's an unresolved property reference
			String qname = getQualifiedName(element, property);
			UnknownProperty unknown = propertyEditor.getUnresolvedProperty();
			result = unknown != null && qname.equals(unknown.getName());
		}

		return result;
	}

	protected final String getQualifiedName(DataContextElement element) {
		StringBuilder result = new StringBuilder();
		collectQualifiedName(element, result);
		return result.toString();
	}

	private void collectQualifiedName(DataContextElement element, StringBuilder name) {
		if (element.getPackage() != null) {
			collectQualifiedName(element.getPackage(), name);
			name.append(':');
		}
		name.append(element.getName());
	}

	protected final String getQualifiedName(Property property) {
		StringBuilder result = new StringBuilder();
		collectQualifiedName(property, result);
		return result.toString();
	}

	private void collectQualifiedName(Property property, StringBuilder name) {
		if (property.getContextElement() != null) {
			collectQualifiedName(property.getContextElement(), name);
			name.append(':');
		}
		name.append(property.getName());
	}

	protected final String getQualifiedName(DataContextElement element, Property property) {
		StringBuilder result = new StringBuilder();
		collectQualifiedName(element, result);
		result.append(':');
		result.append(property.getName());
		return result.toString();
	}

	protected Property createDataContextProperty(EditingDomain domain, EObject sourceProperty) {
		Property result = ContextsFactory.eINSTANCE.createProperty();
		ContextAnnotations.setSourceModel(result, sourceProperty);

		String name = getTraceHelper().getName(sourceProperty);
		result.setName(name);
		result.setLabel(PropertiesUtil.getLabel(name));
		result.setMultiplicity(getTraceHelper().getMultiplicity(sourceProperty));
		result.setType(PropertyTypeHelper.getInstance(result).getPropertyType(sourceProperty));

		return result;
	}

	protected DataContextPackage createDataContextPackage(EditingDomain domain, EObject sourcePackage) {
		DataContextPackage result = ContextsFactory.eINSTANCE.createDataContextPackage();
		ContextAnnotations.setSourceModel(result, sourcePackage);

		String name = getTraceHelper().getName(sourcePackage);
		result.setName(name);

		// Create its nested packages
		getTraceHelper().getNestedPackages(sourcePackage).stream()
				.map(nested -> createDataContextPackage(domain, nested))
				.forEach(result.getElements()::add);

		// And its classes
		getTraceHelper().getClasses(sourcePackage).stream()
				.map(sourceClass -> createDataContextElement(domain, sourceClass))
				.forEach(result.getElements()::add);

		// Packages do not use the inherited 'supertypes' and 'properties' properties

		return result;
	}

	protected DataContextElement createDataContextElement(EditingDomain domain, EObject sourceClass) {
		DataContextElement result = ContextsFactory.eINSTANCE.createDataContextElement();
		ContextAnnotations.setSourceModel(result, sourceClass);

		String name = getTraceHelper().getName(sourceClass);
		result.setName(name);

		// Create the properties
		getTraceHelper().getProperties(sourceClass).stream()
				.map(sourceProperty -> createDataContextProperty(domain, sourceProperty))
				.forEach(result.getProperties()::add);

		// Set the data-context supertypes
		CommonMarkerResolutionUtils.getModelObject(getMarker(), DataContextPackage.class, domain).ifPresent(package_ -> {
			PropertiesCache cache = PropertiesCache.getInstance(package_);

			cache.getSuperclasses(sourceClass).stream()
					.map(superclass -> cache.getDataContextElement(package_, superclass))
					.flatMap(Optional::stream)
					.forEach(result.getSupertypes()::add);
		});

		return result;
	}

	protected Optional<Class<? extends ILayoutGenerator>> getLayoutGeneratorClass() {
		return Optional.ofNullable(getMarker().getAttribute(MARKER_ATTR_LAYOUT_GENERATOR, null))
				.map(URI::createURI)
				.filter(ClassLoaderHelper::isClassURI)
				.map(ClassLoaderHelper::loadClass)
				.map(loaded -> loaded.orElse(null))
				.filter(ILayoutGenerator.class::isAssignableFrom)
				.map(c -> c.asSubclass(ILayoutGenerator.class));
	}

	protected ILayoutGenerator instantiateLayout(Class<? extends ILayoutGenerator> layoutClass) {
		return Try.call(() -> layoutClass.getConstructor().newInstance())
				.orElseAccept((reason, exception) -> Activator.log.error("Failed to instantiate layout generator.", exception)); //$NON-NLS-1$
	}

	protected View createView(Context context, DataContextElement element, int multiplicity) {
		// We know this exists explicitly because we just now set it
		EObject sourceClass = ContextAnnotations.getSourceModel(element, context);

		View result = ContextsFactory.eINSTANCE.createView();
		ContextAnnotations.setSourceModel(result, sourceClass);

		String namePattern = (multiplicity == 1) ? Messages.AbstractModelEditResolutionFactory_1 : Messages.AbstractModelEditResolutionFactory_2;
		result.setName(NLS.bind(namePattern, element.getName()));
		result.setElementMultiplicity(multiplicity);
		result.setAutomaticContext(true);
		result.getDatacontexts().add(element);

		ConstraintDescriptor constraint = getTraceHelper().createInstanceOfConstraint(sourceClass);
		constraint.setName(NLS.bind(Messages.AbstractModelEditResolutionFactory_3, result.getName().replaceAll("\\s+", ""))); //$NON-NLS-1$//$NON-NLS-2$
		result.getConstraints().add(constraint);

		return result;
	}

	/**
	 * Create a command that generates the section layout for a {@code view}.
	 *
	 * @param domain
	 *            the contextual editing domain
	 * @param layout
	 *            the layout generator to use
	 * @param context
	 *            the properties context model
	 * @param element
	 *            the data context element for which to create a section
	 * @param view
	 *            the view of the data context {@code element} for which to create sections
	 *
	 * @return a command to generate the sections
	 */
	protected Command createLayoutCommand(EditingDomain domain, ILayoutGenerator layout, Context context, DataContextElement element, View view) {
		return createLayoutCommand(domain, layout, context, element, view, null);
	}

	/**
	 * Create a command that generates the section layout for a {@code view}.
	 *
	 * @param domain
	 *            the contextual editing domain
	 * @param layout
	 *            the layout generator to use
	 * @param context
	 *            the properties context model
	 * @param element
	 *            the data context element for which to create a section
	 * @param view
	 *            the view of the data context {@code element} for which to create sections
	 * @param properties
	 *            which properties of the data contect {@code element} to create editors for in the generated sections, or {@code null}
	 *            to generate all eligible properties
	 *
	 * @return a command to generate the sections
	 */
	protected Command createLayoutCommand(EditingDomain domain, ILayoutGenerator layout, Context context, DataContextElement element, View view,
			Collection<? extends Property> properties) {

		CompoundCommand result = new CompoundCommand();

		// The layout generation needs the view to be added to the context, first
		result.append(AddCommand.create(domain, context, ContextsPackage.Literals.CONTEXT__VIEWS, view));

		// We know this exists explicitly because we just now set it
		EObject sourceClass = ContextAnnotations.getSourceModel(element, context);

		// We need this for profile layout generation
		IGenerator generator = getTraceHelper().createGenerator(sourceClass);
		GeneratorHelper helper = new GeneratorHelper(generator, layout);

		Tab tab = Iterables.getFirst(context.getTabs(), null);
		if (tab == null) {
			return UnexecutableCommand.INSTANCE;
		}

		result.append(new ChangeCommand(context) {

			private List<Section> generatedSections;

			@Override
			protected void doExecute() {
				generatedSections = helper.generateLayout(context, tab, view,
						(property, multiplicity) -> multiplicity.intValue() == 1 && (properties == null || properties.contains(property)));
			}

			// We need to save the generated section files
			@Override
			public Collection<?> getAffectedObjects() {
				List<EObject> result = new ArrayList<>();

				result.add(context);

				if (generatedSections != null) {
					generatedSections.stream().map(Section::getWidget)
							.filter(Objects::nonNull)
							.forEach(result::add);
				}

				return result;
			}
		});

		return result.unwrap();
	}

}
