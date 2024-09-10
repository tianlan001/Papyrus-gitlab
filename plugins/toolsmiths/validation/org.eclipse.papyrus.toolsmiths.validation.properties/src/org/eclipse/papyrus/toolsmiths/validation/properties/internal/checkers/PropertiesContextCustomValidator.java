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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2.problem;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_MULTIPLICITY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MARKER_ATTR_LAYOUT_GENERATOR;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ROOT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.objectToReference;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.valueToSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.customization.properties.generation.generators.GeneratorHelper;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations;
import org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage;
import org.eclipse.papyrus.infra.properties.environment.ModelElementFactoryDescriptor;
import org.eclipse.papyrus.infra.properties.environment.Type;
import org.eclipse.papyrus.infra.properties.ui.Widget;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertyTypeHelper;

import com.google.common.collect.Lists;

/**
 * Custom validation rules for <em>Properties Context</em> models.
 */
public class PropertiesContextCustomValidator extends CustomModelChecker.SwitchValidator {

	/**
	 * Validation context key tracking the data context elements found to be obsolete. Its value is
	 * a set of {@link DataContextElement}s.
	 */
	private static final String OBSOLETE_DATA_CONTEXT_ELEMENTS = "obsoleteDataContextElement"; //$NON-NLS-1$

	/**
	 * Validation context key tracking the data context roots found to use custom factories, that should
	 * not be validated. Its value is a map of {@link DataContextRoot}s to {@link Boolean} indicating whether
	 * the data context root is custom.
	 */
	private static final String CUSTOM_DATA_CONTEXT_ROOTS = "customDataContextRoots"; //$NON-NLS-1$

	/**
	 * The set of model-element factories for which we understand and can validate
	 * {@link DataContextRoot}s. Only these factories are used by the context model
	 * generation.
	 */
	private final Set<String> applicableModelElementFactories = Set.of(
			// For Ecore models
			"org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory", //$NON-NLS-1$
			// For UML profiles
			"org.eclipse.papyrus.uml.properties.modelelement.UMLModelElementFactory", //$NON-NLS-1$
			"org.eclipse.papyrus.uml.properties.modelelement.StereotypeModelElementFactory"); //$NON-NLS-1$

	public PropertiesContextCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(DataContextPackage package_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (isInCustomDataContextRoot(package_, context)) {
			// We cannot validate custom data contexts that we do not understand
			return;
		}

		if (package_.getPackage() != null && isObsolete(package_.getPackage(), context)) {
			// Don't need to validate a package nested within an obsolete package
			markObsolete(package_, context);
			return;
		}

		PropertiesCache cache = PropertiesCache.getInstance(package_);
		EObject sourcePackage = cache.getSourceElement(package_);
		if (sourcePackage == null) {
			// No traceability? Warn on the assumption that the DataContextPackage is obsolete
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, package_,
					format(Messages.PropertiesContextCustomValidator_0, context, package_),
					problem(OBSOLETE_DATA_CONTEXT_PACKAGE)));
			markObsolete(package_, context);
		} else if (sourcePackage.eIsProxy()) {
			// Source element was deleted/moved? This DataContextPackage is now obsolete

			if (package_ instanceof DataContextRoot) {
				// But this is a special case: we hope that the entire source was moved, which is fixable
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, package_,
						format(Messages.PropertiesContextCustomValidator_12, context, package_),
						problem(OBSOLETE_DATA_CONTEXT_ROOT)));
			} else {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, package_,
						format(Messages.PropertiesContextCustomValidator_1, context, package_),
						problem(OBSOLETE_DATA_CONTEXT_PACKAGE)));
			}
			markObsolete(package_, context);
		} else {
			String sourceName = cache.getName(sourcePackage);
			if (!Objects.equals(sourceName, package_.getName())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, package_, ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__NAME,
						format(Messages.PropertiesContextCustomValidator_9, context, package_, sourceName, sourcePackage),
						problem(RENAMED_PACKAGE), valueToSet(sourceName, EcorePackage.Literals.ESTRING)));
			}
			checkForMissingPackages(package_, sourcePackage, diagnostics, context);
			checkForMissingClasses(package_, sourcePackage, diagnostics, context);
		}
	}

	protected boolean isInCustomDataContextRoot(EObject object, Map<Object, Object> context) {
		boolean result = false;

		if (object instanceof DataContextRoot) {
			DataContextRoot root = (DataContextRoot) object;

			@SuppressWarnings("unchecked")
			Map<DataContextRoot, Boolean> customRoots = (Map<DataContextRoot, Boolean>) context.computeIfAbsent(CUSTOM_DATA_CONTEXT_ROOTS, __ -> new HashMap<>());
			result = customRoots.computeIfAbsent(root, dcr -> {
				ModelElementFactoryDescriptor factory = dcr.getModelElementFactory();
				// If this is a custom model element factory, then we can infer nothing about its mappings or
				// consistency to any model because its purpose is to describe concepts not in the model
				return factory == null || !applicableModelElementFactories.contains(factory.getFactoryClass());
			});
		} else if (object.eContainer() != null) {
			result = isInCustomDataContextRoot(object.eContainer(), context);
		}

		return result;
	}

	private void checkForMissingPackages(DataContextPackage package_, EObject sourcePackage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		checkForMissingElements(package_, new HashSet<>(PropertiesCache.getInstance(package_).getNestedPackages(sourcePackage)),
				MISSING_DATA_CONTEXT_PACKAGE, Messages.PropertiesContextCustomValidator_13, diagnostics, context);
	}

	private void checkForMissingClasses(DataContextPackage package_, EObject sourcePackage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		checkForMissingElements(package_, new HashSet<>(PropertiesCache.getInstance(package_).getClasses(sourcePackage)),
				MISSING_DATA_CONTEXT_ELEMENT, Messages.PropertiesContextCustomValidator_2, diagnostics, context);
	}

	private void checkForMissingElements(DataContextPackage package_, Set<? extends EObject> sourceElements, int problemID, String messagePattern,
			DiagnosticChain diagnostics, Map<Object, Object> context) {

		PropertiesCache cache = PropertiesCache.getInstance(package_);
		package_.getElements().stream().map(cache::getSourceElement).forEach(sourceElements::remove);
		if (!sourceElements.isEmpty()) {
			// We have unmapped source elements (classes or packages)
			sourceElements.stream().map(sourceElement -> createDiagnostic(Diagnostic.WARNING, package_,
					format(messagePattern, context, package_, sourceElement),
					missingElementAttributes(package_, problemID, sourceElement)))
					.forEach(diagnostics::add);
		}
	}

	private Collection<? extends IPluginChecker2.MarkerAttribute> missingElementAttributes(DataContextPackage package_, int problemID, EObject sourceClass) {
		Collection<IPluginChecker2.MarkerAttribute> result = Lists.newArrayList(problem(problemID),
				objectToReference(sourceClass));

		addLayoutGenerator(result, package_);
		return result;
	}

	private void addLayoutGenerator(Collection<? super IPluginChecker2.MarkerAttribute> marker, DataContextElement element) {
		// Does the data context root have a layout-generator annotation?
		DataContextRoot root = GeneratorHelper.getRoot(element);
		String layoutGenerator = (root != null) ? ContextAnnotations.getLayoutGeneratorClassName(root) : null;
		if (layoutGenerator != null) {
			// Tell the quick fix which layout generator to use
			marker.add(new IPluginChecker2.MarkerAttribute(MARKER_ATTR_LAYOUT_GENERATOR, layoutGenerator));
		}
	}

	public void validate(DataContextElement element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// DataContextPackages are DataContextElements but do not map to classes, so skip them
		if (element instanceof DataContextPackage) {
			return;
		}
		if (isInCustomDataContextRoot(element, context)) {
			// We cannot validate custom data contexts that we do not understand
			return;
		}

		if (isObsolete(element.getPackage(), context)) {
			// Don't need to validate an element within an obsolete package
			markObsolete(element, context);
			return;
		}

		PropertiesCache cache = PropertiesCache.getInstance(element);
		EObject sourceClass = cache.getSourceElement(element);
		if (sourceClass == null) {
			// No traceability? Warn on the assumption that the DataContextElement is obsolete
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, element,
					format(Messages.PropertiesContextCustomValidator_3, context, element),
					problem(OBSOLETE_DATA_CONTEXT_ELEMENT)));
			markObsolete(element, context);
		} else if (sourceClass.eIsProxy()) {
			// Source element was deleted/moved? This DataContextElement is now obsolete
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, element,
					format(Messages.PropertiesContextCustomValidator_4, context, element),
					problem(OBSOLETE_DATA_CONTEXT_ELEMENT)));
			markObsolete(element, context);
		} else {
			String sourceName = cache.getName(sourceClass);
			if (!Objects.equals(sourceName, element.getName())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, element, ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__NAME,
						format(Messages.PropertiesContextCustomValidator_10, context, element, sourceName, sourceClass),
						problem(RENAMED_CLASS), valueToSet(sourceName, EcorePackage.Literals.ESTRING)));
			}
			checkForMissingProperties(element, sourceClass, diagnostics, context);
		}
	}

	private void checkForMissingProperties(DataContextElement element, EObject sourceClass, DiagnosticChain diagnostics, Map<Object, Object> context) {
		PropertiesCache cache = PropertiesCache.getInstance(element);
		Set<? extends EObject> properties = new HashSet<>(cache.getProperties(sourceClass));

		// There is no requirement to mirror redefining properties, as the general context should already
		// have the property (recursively)
		properties.removeIf(cache::isPropertyRedefinition);

		element.getProperties().stream().map(cache::getSourceElement).forEach(properties::remove);

		if (!properties.isEmpty()) {
			// We have unmapped properties
			properties.stream().map(sourceProp -> createDiagnostic(Diagnostic.WARNING, element,
					format(Messages.PropertiesContextCustomValidator_5, context, element, sourceProp),
					missingPropertyAttributes(element, sourceProp)))
					.forEach(diagnostics::add);
		}
	}

	private Collection<? extends IPluginChecker2.MarkerAttribute> missingPropertyAttributes(DataContextElement element, EObject sourceProperty) {
		Collection<IPluginChecker2.MarkerAttribute> result = Lists.newArrayList(problem(MISSING_DATA_CONTEXT_PROPERTY),
				objectToReference(sourceProperty));

		addLayoutGenerator(result, element);
		return result;
	}

	private void markObsolete(DataContextElement element, Map<Object, Object> context) {
		if (context != null) {
			@SuppressWarnings("unchecked")
			Set<DataContextElement> obsolete = (Set<DataContextElement>) context.computeIfAbsent(OBSOLETE_DATA_CONTEXT_ELEMENTS, __ -> new HashSet<>());
			obsolete.add(element);
		}
	}

	private boolean isObsolete(DataContextElement element, Map<Object, Object> context) {
		boolean result = false;

		if (context != null) {
			Set<?> obsolete = (Set<?>) context.get(OBSOLETE_DATA_CONTEXT_ELEMENTS);
			result = obsolete != null && obsolete.contains(element);
		}

		return result;
	}

	public void validate(Property property, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// If the data context element containing the property is obsolete, then so are all of its
		// properties, so we needn't worry about this
		if (isObsolete(property.getContextElement(), context)) {
			return;
		}
		if (isInCustomDataContextRoot(property, context)) {
			// We cannot validate custom data contexts that we do not understand
			return;
		}

		PropertiesCache cache = PropertiesCache.getInstance(property);
		EObject sourceProperty = cache.getSourceElement(property);
		if (sourceProperty == null) {
			// No traceability? Warn on the assumption that the property is obsolete
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, property,
					format(Messages.PropertiesContextCustomValidator_6, context, property),
					problem(OBSOLETE_DATA_CONTEXT_PROPERTY)));
		} else if (sourceProperty.eIsProxy()) {
			// Source element was deleted/moved? This property is now obsolete
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, property,
					format(Messages.PropertiesContextCustomValidator_7, context, property),
					problem(OBSOLETE_DATA_CONTEXT_PROPERTY)));
		} else {
			String sourceName = cache.getName(sourceProperty);
			if (!Objects.equals(sourceName, property.getName())) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, property, ContextsPackage.Literals.PROPERTY__NAME,
						format(Messages.PropertiesContextCustomValidator_11, context, property, sourceName, sourceProperty),
						problem(RENAMED_PROPERTY), valueToSet(sourceName, EcorePackage.Literals.ESTRING)));
			}
			if (property.getType() != null) { // If it's missing, that's a different problem
				validatePropertyType(property, sourceProperty, diagnostics, context);
			}
			validatePropertyMultiplicity(property, sourceProperty, diagnostics, context);
		}
	}

	/**
	 * Check the {@code property} {@link Property#getType() type} for consistency with the nature of the
	 * source property to which it traces.
	 *
	 * @param property
	 *            a data-context property
	 * @param sourceProperty
	 *            the property to which it traces in the source model
	 * @param diagnostics
	 *            record of problems
	 * @param context
	 *            validation context
	 */
	private void validatePropertyType(Property property, EObject sourceProperty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Type expectedType = PropertyTypeHelper.getInstance(property).getPropertyType(sourceProperty);
		if (expectedType != property.getType()) {
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, property, ContextsPackage.Literals.PROPERTY__TYPE,
					format(Messages.PropertiesContextCustomValidator_8, context, property,
							value(ContextsPackage.Literals.PROPERTY__TYPE, expectedType), sourceProperty),
					problem(INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE),
					valueToSet(expectedType, EnvironmentPackage.Literals.TYPE)));
		}
	}

	public void validate(Section section, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Widget widget = section.getWidget();

		if (widget != null && !widget.eIsProxy()) {
			// This is not a cross-resource containment reference, so add the containing resource to the
			// current validation scope
			validateResource(widget.eResource(), context);
		}
	}

	private void validatePropertyMultiplicity(Property property, EObject sourceProperty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		int expectedMultiplicity = PropertyTypeHelper.getInstance(property).getMultiplicity(sourceProperty);
		if (expectedMultiplicity != property.getMultiplicity()) {
			diagnostics.add(createDiagnostic(Diagnostic.WARNING, property, ContextsPackage.Literals.PROPERTY__MULTIPLICITY,
					format(Messages.PropertiesContextCustomValidator_14, context, property,
							value(ContextsPackage.Literals.PROPERTY__MULTIPLICITY, expectedMultiplicity), sourceProperty),
					problem(INCONSISTENT_DATA_CONTEXT_PROPERTY_MULTIPLICITY),
					valueToSet(expectedMultiplicity, EcorePackage.Literals.EINT)));
		}
	}

}
