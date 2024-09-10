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
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils.getModelAttribute;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils.getModelPath;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution.optionalAttribute;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.A_CONTEXT_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.A_ENVIRONMENT_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.CONTEXTS_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.ENVIRONMENTS_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.E_CONTEXT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.E_ENVIRONMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_MULTIPLICITY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.INCONSISTENT_WIDGET_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MAX_PROBLEM_ID;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_CONTEXT_MODEL_EXTENSION_ID;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_ENVIRONMENT_MODEL_EXTENSION_ID;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ROOT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROBLEM_ID_BASE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.RENAMED_PROPERTY;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS_MULTIPLE_CHOICE;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.toolsmiths.validation.common.command.TouchResourceCommand;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleModelEditMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;
import org.eclipse.ui.IMarkerResolution;

/**
 * Resolution generator for markers created by the validation of <em>Properties</em> models.
 */
public class PropertiesMarkerResolutionGenerator extends CommonMarkerResolutionGenerator {

	public PropertiesMarkerResolutionGenerator() {
		super();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (hasCommonResolutions(marker)) {
			return super.getResolutions(marker);
		}

		int problemID = getProblemID(marker);
		switch (problemID) {
		case MISSING_CONTEXT_MODEL_EXTENSION_ID:
			return only(new SimpleMissingExtensionMarkerResolution(problemID,
					Messages.MissingPropertiesExtension_0, Messages.MissingPropertiesExtension_1,
					CONTEXTS_EXTENSION_POINT_IDENTIFIER, E_CONTEXT,
					optionalAttribute(A_CONTEXT_MODEL, m -> getModelPath(m).map(IPath::toPortableString))));
		case MISSING_ENVIRONMENT_MODEL_EXTENSION_ID:
			return only(new SimpleMissingExtensionMarkerResolution(problemID,
					Messages.MissingPropertiesExtension_2, Messages.MissingPropertiesExtension_3,
					ENVIRONMENTS_EXTENSION_POINT_IDENTIFIER, E_ENVIRONMENT,
					optionalAttribute(A_ENVIRONMENT_MODEL, m -> getModelPath(m).map(IPath::toPortableString))));
		case INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE:
			return only(createSetValueResolution(problemID, ContextsPackage.Literals.PROPERTY__TYPE, Messages.PropertiesMarkerResolutionGenerator_0, Messages.PropertiesMarkerResolutionGenerator_1));
		case INCONSISTENT_WIDGET_TYPE:
			return only(createSetReferenceResolution(problemID, UiPackage.Literals.PROPERTY_EDITOR__WIDGET_TYPE, Messages.PropertiesMarkerResolutionGenerator_2, Messages.PropertiesMarkerResolutionGenerator_3));
		case MISSING_DATA_CONTEXT_PACKAGE:
		case MISSING_DATA_CONTEXT_ELEMENT:
		case OBSOLETE_DATA_CONTEXT_ELEMENT:
			return allOf(new DataContextElementResolutionFactory(problemID).createResolutions(marker));
		case MISSING_DATA_CONTEXT_PROPERTY:
		case OBSOLETE_DATA_CONTEXT_PROPERTY:
			return allOf(new DataContextPropertyResolutionFactory(problemID).createResolutions(marker));
		case RENAMED_PROPERTY:
		case RENAMED_CLASS:
		case RENAMED_PACKAGE:
			return maybe(getModelAttribute(marker).map(feature -> createSetValueResolution(problemID, feature, Messages.PropertiesMarkerResolutionGenerator_4, Messages.PropertiesMarkerResolutionGenerator_5)));
		case OBSOLETE_DATA_CONTEXT_ROOT:
			return only(SimpleModelEditMarkerResolution.create(problemID, Messages.PropertiesMarkerResolutionGenerator_6, Messages.PropertiesMarkerResolutionGenerator_7,
					DataContextRoot.class, new FindNewPackageLocation()::fix));
		case UNRESOLVED_CONSTRAINT_CLASS:
			return maybe(getModelAttribute(marker).map(feature -> createSetValueResolution(problemID, feature,
					Messages.PropertiesMarkerResolutionGenerator_8, Messages.PropertiesMarkerResolutionGenerator_9)));
		case UNRESOLVED_CONSTRAINT_CLASS_MULTIPLE_CHOICE:
			String validClassNames = PropertiesPluginValidationConstants.getValueToSet(marker, String.class, EcorePackage.Literals.ESTRING).orElse(""); //$NON-NLS-1$
			return allOf(Stream.of(validClassNames.split(",")) //$NON-NLS-1$
					.map(className -> getModelAttribute(marker).map(feature -> createSetValueResolution(problemID, feature, className,
							NLS.bind(Messages.PropertiesMarkerResolutionGenerator_10, className), NLS.bind(Messages.PropertiesMarkerResolutionGenerator_11, className))))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.toArray(IMarkerResolution[]::new));
		case INCONSISTENT_DATA_CONTEXT_PROPERTY_MULTIPLICITY:
			return only(createSetValueResolution(problemID, ContextsPackage.Literals.PROPERTY__MULTIPLICITY, Messages.PropertiesMarkerResolutionGenerator_12, Messages.PropertiesMarkerResolutionGenerator_13));
		default:
			return noResolutions();
		}
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return super.hasResolutions(marker)
				|| matchProblemID(marker, PROBLEM_ID_BASE, MAX_PROBLEM_ID);
	}

	protected IMarkerResolution createSetValueResolution(int problemID, EAttribute attribute, String label, String description) {
		Class<? extends EObject> ownerType = attribute.getEContainingClass().getInstanceClass().asSubclass(EObject.class);
		return SimpleModelEditMarkerResolution.create(problemID, label, description, ownerType, (domain, owner, marker) -> {
			CompoundCommand result = new CompoundCommand();

			EDataType dataType = attribute.getEAttributeType();
			Optional<?> value = PropertiesPluginValidationConstants.getValueToSet(marker, dataType.getInstanceClass(), dataType);

			result.append(value.map(v -> SetCommand.create(domain, owner, attribute, v)).orElse(UnexecutableCommand.INSTANCE));

			if (attribute == ContextsPackage.Literals.PROPERTY__NAME) {
				result.append(touchSectionFiles(domain, (Property) owner));
			} else if (attribute == ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__NAME) {
				result.append(touchSectionFiles(domain, (DataContextElement) owner));
			}

			return result.unwrap();
		});
	}

	protected IMarkerResolution createSetValueResolution(int problemID, EAttribute attribute, Object value, String label, String description) {
		Class<? extends EObject> ownerType = attribute.getEContainingClass().getInstanceClass().asSubclass(EObject.class);
		return SimpleModelEditMarkerResolution.create(problemID, label, description, ownerType, (domain, owner, marker) -> {
			CompoundCommand result = new CompoundCommand();

			result.append(SetCommand.create(domain, owner, attribute, value));

			if (attribute == ContextsPackage.Literals.PROPERTY__NAME) {
				result.append(touchSectionFiles(domain, (Property) owner));
			} else if (attribute == ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__NAME) {
				result.append(touchSectionFiles(domain, (DataContextElement) owner));
			}

			return result.unwrap();
		}).disableMultiFix(); // We encode a static fix plan and so cannot apply it to other markers
	}

	protected IMarkerResolution createSetReferenceResolution(int problemID, EReference reference, String label, String description) {
		Class<? extends EObject> ownerType = reference.getEContainingClass().getInstanceClass().asSubclass(EObject.class);
		return SimpleModelEditMarkerResolution.create(problemID, label, description, ownerType, (domain, owner, marker) -> {
			Class<? extends EObject> referenceType = reference.getEReferenceType().getInstanceClass().asSubclass(EObject.class);
			Optional<? extends EObject> object = PropertiesPluginValidationConstants.getObjectToReference(marker, referenceType, domain);
			return object.map(ref -> SetCommand.create(domain, owner, reference, ref)).orElse(UnexecutableCommand.INSTANCE);
		});
	}

	/**
	 * Create a command that touches all of the section files referencing the given {@code property} so that
	 * they will be rewritten with the new property name.
	 *
	 * @param domain
	 *            the contextual editing domain
	 * @param property
	 *            a property that is renamed
	 * @return the touch command
	 */
	protected Command touchSectionFiles(EditingDomain domain, Property property) {
		// Ensure that the sections that can legitimately reference this property via property editors are loaded
		PropertiesCache.getInstance(property).getSections(property.getContextElement()).forEach(Section::getWidget);

		return PropertiesCache.getInstance(property).getReferencers(property, UiPackage.Literals.PROPERTY_EDITOR__PROPERTY).stream()
				.map(EObject::eResource).distinct()
				.collect(TouchResourceCommand.toTouchCommand(domain));
	}

	/**
	 * Create a command that touches all of the section files referencing properties of the given
	 * data-context {@code element} so that they will be rewritten with the new property name.
	 *
	 * @param domain
	 *            the contextual editing domain
	 * @param element
	 *            a data-context element that is renamed
	 * @return the touch command
	 */
	protected Command touchSectionFiles(EditingDomain domain, DataContextElement element) {
		PropertiesCache cache = PropertiesCache.getInstance(element);

		Stream<DataContextElement> classes = !(element instanceof DataContextPackage)
				? Stream.of(element)
				: Iterators2.stream(Iterators2.filter(element.eAllContents(), DataContextElement.class))
						.filter(not(DataContextPackage.class::isInstance));

		return classes.map(cache::getSections).flatMap(Collection::stream)
				.map(Section::getWidget).filter(Objects::nonNull).filter(not(EObject::eIsProxy))
				.map(EObject::eResource).distinct()
				.collect(TouchResourceCommand.toTouchCommand(domain));
	}

}
