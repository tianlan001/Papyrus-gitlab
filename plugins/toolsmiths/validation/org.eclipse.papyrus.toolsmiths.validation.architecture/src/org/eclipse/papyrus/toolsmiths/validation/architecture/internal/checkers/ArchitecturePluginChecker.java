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
 *   Christian W. Damus - bugs 570097, 571125, 573245, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.checkers;


import static org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage.Literals.AD_ELEMENT__ICON;
import static org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS;
import static org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS;
import static org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage.Literals.REPRESENTATION_KIND__GRAYED_ICON;
import static org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage.Literals.PAPYRUS_DIAGRAM__CREATION_COMMAND_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureCommandUtils;
import org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.JavaClassDependencies;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ResourceKind;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;

/**
 * This allows to check an architecture plug-in (extensions, builds, dependencies, ...).
 */
public class ArchitecturePluginChecker {

	public static final String ARCHITECTURE_EXTENSION = "architecture";//$NON-NLS-1$

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.architecture" //$NON-NLS-1$
	);

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ArchitecturePluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ArchitecturePluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, resource, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createIconProvider());
	}

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(ArchitecturePluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(ArchitecturePluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ArchitecturePluginChecker::getRequiredCommandBundleDependencies)
				.withReferencedResources(createOpaqueResourceProvider(project));
	}

	// Icon resources
	private static OpaqueResourceProvider.EMF createIconProvider() {
		return OpaqueResourceProvider.EMF.create(ResourceKind.ICON, ArchitecturePackage.eNS_URI, AD_ELEMENT__ICON)
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.ICON, RepresentationPackage.eNS_URI, REPRESENTATION_KIND__GRAYED_ICON));
	}

	private static OpaqueResourceProvider.EMF createOpaqueResourceProvider(IProject project) {
		JavaClassDependencies dependencies = new JavaClassDependencies(project, ArchitectureCommandUtils::getCommandClass);

		// Icon resources
		return createIconProvider()
				// Creation/conversion command classes
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.CLASS, ArchitecturePackage.eNS_URI, ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS, dependencies::getClassURI))
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.CLASS, ArchitecturePackage.eNS_URI, ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS, dependencies::getClassURI))
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.CLASS, RepresentationPackage.eNS_URI, PAPYRUS_DIAGRAM__CREATION_COMMAND_CLASS, dependencies::getClassURI));
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ArchitecturePluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<ArchitectureDomain, PluginErrorReporter<ArchitectureDomain>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<ArchitectureDomain> domains = EcoreUtil.getObjectsByType(resource.getContents(), ArchitecturePackage.Literals.ARCHITECTURE_DOMAIN);
		return new ExtensionsChecker<>(project, modelFile, domains, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE, ArchitecturePluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<ArchitectureDomain> createPluginErrorReporter(IFile pluginXML, IFile modelFile, ArchitectureDomain model) {
		ArchitecturePluginXMLValidator validator = new ArchitecturePluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE, domain -> domain.getId())
				.requireExtensionPoint(ARCHITECTURE_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, null, validator::problemID);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ArchitecturePluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(ArchitecturePackage.eNS_URI, ArchitectureCustomValidator::new)
				.withValidator(RepresentationPackage.eNS_URI, ArchitectureCustomValidator::new); // No Papyrus-specific rules, yet
	}

	private static Set<String> getRequiredCommandBundleDependencies(Resource modelResource) {
		return modelResource.getContents().stream()
				.map(ArchitectureCommandUtils::getRequiredCommandBundleDependencies)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

}
