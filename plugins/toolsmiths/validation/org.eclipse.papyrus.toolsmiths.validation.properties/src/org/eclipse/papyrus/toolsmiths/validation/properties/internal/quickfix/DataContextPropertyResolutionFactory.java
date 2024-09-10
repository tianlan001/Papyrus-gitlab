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

import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.getObjectToReference;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.customization.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.environment.PropertyEditorType;
import org.eclipse.papyrus.infra.properties.environment.WidgetType;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.UiFactory;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.WidgetTypeHelper;
import org.eclipse.ui.IMarkerResolution;

import com.google.common.collect.Iterators;

/**
 * A factory for quick fixes that add or remove data-context properties.
 * It is not called a "generator" because it does not implement the Eclipse
 * marker-resolution generator API.
 */
class DataContextPropertyResolutionFactory extends AbstractModelEditResolutionFactory {

	DataContextPropertyResolutionFactory(int problemID) {
		super(problemID);
	}

	@Override
	protected Iterable<IMarkerResolution> createResolutions() {
		switch (getProblemID()) {
		case MISSING_DATA_CONTEXT_PROPERTY:
			return List.of(addDataContextProperty(), addDataContextPropertyAndEditor());
		case OBSOLETE_DATA_CONTEXT_PROPERTY:
			return List.of(removeDataContextProperty());
		default:
			return List.of();
		}
	}

	IMarkerResolution addDataContextProperty() {
		return createResolution(Messages.DataContextPropertyResolutionFactory_0, Messages.DataContextPropertyResolutionFactory_1,
				DataContextElement.class, this::createAddDataContextPropertyCommand);
	}

	IMarkerResolution addDataContextPropertyAndEditor() {
		return createResolution(Messages.DataContextPropertyResolutionFactory_2, Messages.DataContextPropertyResolutionFactory_3,
				DataContextElement.class, this::createAddDataContextPropertyAndEditorCommand);
	}

	private Command createAddDataContextPropertyCommand(EditingDomain domain, DataContextElement element) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourceProperty -> createDataContextProperty(domain, sourceProperty))
				.map(property -> AddCommand.create(domain, element, ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__PROPERTIES, property))
				.orElse(null);
	}

	private Command createAddDataContextPropertyAndEditorCommand(EditingDomain domain, DataContextElement element) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourceProperty -> createDataContextProperty(domain, sourceProperty))
				.map(property -> {
					CompoundCommand result = new CompoundCommand();
					result.append(AddCommand.create(domain, element, ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__PROPERTIES, property));

					Collection<Section> sections = PropertiesCache.getInstance(element).getSections(element);
					if (sections.isEmpty()) {
						// No sections? then we need to generate the initial views
						Command createViews = createCreateViewsForPropertyEditorCommand(domain, element, property);
						if (createViews != null) {
							result.append(createViews);
						}
					} else {
						for (Section section : PropertiesCache.getInstance(element).getSections(element)) {
							// Only add a property editor for single selections. Note that sections do not use
							// their own multiplicity attributes; we rely on the associated views
							if (getElementMultiplicity(section) == 1) {
								Command createPropertyEditor = createCreatePropertyEditorCommand(domain, section, element, property);
								if (createPropertyEditor != null) {
									result.append(createPropertyEditor);
								}
							}
						}
					}

					return result.unwrap();
				})
				.orElse(null);
	}

	private Command createCreatePropertyEditorCommand(EditingDomain domain, Section section, DataContextElement element, Property property) {
		Command result = null;

		// TODO: Is there a good way to find a more appropriate composite widget to add the property to?

		// Search for the last most deeply nested composite to add to
		CompositeWidget composite = Iterators.getLast(widgeterator(section), null);

		// And don't add another editor if this section already has one for this property
		if (composite != null && getPropertyEditor(section, element, property) == null) {
			WidgetType type = WidgetTypeHelper.getInstance(composite).getDefaultWidgetType(property);
			if (type instanceof PropertyEditorType) {
				PropertyEditor editor = UiFactory.eINSTANCE.createPropertyEditor();
				editor.setProperty(property);
				editor.setWidgetType((PropertyEditorType) type);
				result = AddCommand.create(domain, composite, UiPackage.Literals.COMPOSITE_WIDGET__WIDGETS, editor);
			}
		}

		return result;
	}

	private Command createCreateViewsForPropertyEditorCommand(EditingDomain domain, DataContextElement element, Property property) {
		CompoundCommand result = new CompoundCommand();
		Context context = getContext(element);

		// To generate just one property, it doesn't really matter the layout we use
		ILayoutGenerator layout = getLayoutGeneratorClass().map(this::instantiateLayout).orElseGet(StandardLayoutGenerator::new);
		Stream.of(createView(context, element, 1), createView(context, element, -1))
				.map(view -> createLayoutCommand(domain, layout, context, element, view, Set.of(property)))
				.forEach(result::append);

		return result.unwrap();
	}

	IMarkerResolution removeDataContextProperty() {
		return createResolution(Messages.DataContextPropertyResolutionFactory_4, Messages.DataContextPropertyResolutionFactory_5,
				Property.class, this::createDeleteDataContextPropertyCommand);
	}

	private Command createDeleteDataContextPropertyCommand(EditingDomain domain, Property property) {
		DataContextElement element = property.getContextElement();
		return Optional.ofNullable(element).map(container -> {
			CompoundCommand result = new CompoundCommand();

			// The editor uses RemoveCommand instead of DeleteCommand, so we do, also
			result.append(RemoveCommand.create(domain, property));

			// Ensure that the sections that can legitimately reference this property via property editors are loaded
			PropertiesCache.getInstance(element).getSections(element).forEach(Section::getWidget);

			// Find referencing property editors and remove them (don't need a delete command because they
			// are not referenced by anything, themselves)
			PropertiesCache.getInstance(element).getReferencers(property, UiPackage.Literals.PROPERTY_EDITOR__PROPERTY).stream()
					.map(propertyEditor -> RemoveCommand.create(domain, propertyEditor))
					.forEach(result::append);

			return result.unwrap();
		}).orElse(null);
	}

}
