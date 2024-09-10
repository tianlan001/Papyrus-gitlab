/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bugs 569357, 570097, 571125, 573245
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import static org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI;
import static org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage.Literals.ICON_ENTRY__BUNDLE_ID;
import static org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage.Literals.ICON_ENTRY__ICON_PATH;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ResourceKind;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;

/**
 * This allows to check an element types plug-in (extensions, builds, dependencies, ...).
 */
public class ElementTypesPluginChecker {

	public static final String ELEMENT_TYPES_CONFIGURATION_EXTENSION = "elementtypesconfigurations";//$NON-NLS-1$

	// TODO: Why were some of these in the original definition of base requirements?
	// private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
	// "org.eclipse.papyrus.infra.types.core", //$NON-NLS-1$
	// "org.eclipse.gmf.runtime.emf.type.core", //$NON-NLS-1$
	// "org.eclipse.papyrus.uml.service.types", //$NON-NLS-1$
	// "org.eclipse.papyrus.infra.services.edit", //$NON-NLS-1$
	// "org.eclipse.papyrus.infra.types", //$NON-NLS-1$
	// "org.eclipse.papyrus.uml.tools.utils" //$NON-NLS-1$
	// );
	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.types.core", //$NON-NLS-1$
			"org.eclipse.papyrus.infra.types" //$NON-NLS-1$
	);

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(ElementTypesPluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createOpaqueResourceProvider());
	}

	private static OpaqueResourceProvider.EMF createOpaqueResourceProvider() {
		// Icon resources
		return OpaqueResourceProvider.EMF.create(ResourceKind.ICON, ElementTypesConfigurationsPackage.eNS_URI, ICON_ENTRY__ICON_PATH, ICON_ENTRY__BUNDLE_ID)
				// Metamodel resources by NS URI
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.METAMODEL, ElementTypesConfigurationsPackage.eNS_URI, ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI));
	}

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withDependencies(file -> new ElementTypesBuildPropertiesDependencies(resource).getDependencies())
				.withReferencedResources(createOpaqueResourceProvider());
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<ElementTypeSetConfiguration, PluginErrorReporter<ElementTypeSetConfiguration>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<ElementTypeSetConfiguration> sets = EcoreUtil.getObjectsByType(resource.getContents(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
		return new ExtensionsChecker<>(project, modelFile, sets, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, ElementTypesPluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<ElementTypeSetConfiguration> createPluginErrorReporter(IFile pluginXML, IFile modelFile, ElementTypeSetConfiguration model) {
		ElementTypesPluginXMLValidator validator = new ElementTypesPluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, set -> set.getIdentifier())
				.softRequireExtensionPoint(ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, validator::checkExtension, validator::problemID);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(ElementTypesConfigurationsPackage.eNS_URI, ElementTypesCustomValidator::new)
				.withValidator(ApplyStereotypeAdvicePackage.eNS_URI, ApplyStereotypeAdviceCustomValidator::new)
				.withValidator(StereotypeApplicationMatcherPackage.eNS_URI, StereotypeApplicationMatcherCustomValidator::new)
				.withValidator(StereotypePropertyReferenceEdgeAdvicePackage.eNS_URI, StereotypePropertyReferenceEdgeAdviceCustomValidator::new);
	}

}
