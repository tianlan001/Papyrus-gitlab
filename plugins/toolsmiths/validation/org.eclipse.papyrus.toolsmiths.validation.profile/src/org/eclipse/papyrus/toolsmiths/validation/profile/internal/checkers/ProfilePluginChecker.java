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
 *   Christian W. Damus - bugs 569357, 571125, 573245, 573886
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.ATTR_ICONPATH;
import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.ELEM_PROFILE;
import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ResourceKind;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * This allows to check an profile plug-in (extensions, builds, dependencies, ...).
 */
public class ProfilePluginChecker {

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of("org.eclipse.uml2.uml.resources"); //$NON-NLS-1$

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(ProfilePluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
				.withEMFGeneratorModels()
				.withReferencedResources(createPluginXMLOpaqueResourceProvider(project, modelFile));
	}

	private static OpaqueResourceProvider.XML createPluginXMLOpaqueResourceProvider(IProject project, IFile modelFile) {
		return OpaqueResourceProvider.XML.create(ResourceKind.ICON, UMLPackage.eNS_URI, UMLPROFILE_EXTENSION_POINT, ELEM_PROFILE, ATTR_ICONPATH);
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<Profile, PluginErrorReporter<Profile>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<Profile> sets = EcoreUtil.getObjectsByType(resource.getContents(), UMLPackage.Literals.PROFILE);
		Collection<Profile> allProfiles = new ArrayList<>();
		for (Profile profile : sets) {
			allProfiles.add(profile);
			allProfiles.addAll(PackageUtil.getSubProfiles(profile));
		}
		return new ExtensionsChecker<>(project, modelFile, allProfiles, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, ProfilePluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<Profile> createPluginErrorReporter(IFile pluginXML, IFile modelFile, Profile model) {
		ProfilePluginXMLValidator validator = new ProfilePluginXMLValidator(modelFile);

		PluginErrorReporter<Profile> reporter = new PluginErrorReporter<>(pluginXML, modelFile, model, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, profile -> profile.getName())
				.requireExtensionPoint(ProfilePluginValidationConstants.ECORE_GENERATED_PACKAGE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId)
				.requireExtensionPoint(ProfilePluginValidationConstants.UML_GENERATED_PACKAGE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId);

		if (model.getNestingPackage() == null) {
			return reporter.softRequireExtensionPoint(ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId);
		}
		return reporter;
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(UMLPackage.eNS_URI, ProfileCustomValidator::new)
				.withValidator(UMLResource.ECORE_PROFILE_NS_URI, ProfileCustomValidator::new);
	}

}
