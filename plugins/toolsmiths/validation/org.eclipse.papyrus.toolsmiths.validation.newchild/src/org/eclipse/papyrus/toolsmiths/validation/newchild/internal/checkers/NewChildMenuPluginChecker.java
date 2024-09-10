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

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.checkers;

import static org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage.Literals.MENU__ICON;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ResourceKind;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;

/**
 * Plug-in checker factory for the new child menu models.
 */
public class NewChildMenuPluginChecker {

	public static final String NEW_CHILD_MENU_EXTENSION = "creationmenumodel";//$NON-NLS-1$

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.newchild" //$NON-NLS-1$
	);

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(NewChildMenuPluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(NewChildMenuPluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createOpaqueResourceProvider());
	}

	private static OpaqueResourceProvider.EMF createOpaqueResourceProvider() {
		// Icon resources
		return OpaqueResourceProvider.EMF.create(ResourceKind.ICON, ElementCreationMenuModelPackage.eNS_URI, MENU__ICON);
	}

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(NewChildMenuPluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(NewChildMenuPluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(ElementCreationMenuModelPackage.eNS_URI, NewChildCustomValidator::new);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(NewChildMenuPluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, resource, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createOpaqueResourceProvider());
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(NewChildMenuPluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<Menu, PluginErrorReporter<Menu>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<Menu> menus = EcoreUtil.getObjectsByType(resource.getContents(), ElementCreationMenuModelPackage.Literals.MENU);
		return new ExtensionsChecker<>(project, modelFile, menus, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE, NewChildMenuPluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<Menu> createPluginErrorReporter(IFile pluginXML, IFile modelFile, Menu model) {
		NewChildMenuPluginXMLValidator validator = new NewChildMenuPluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE, menu -> menu.getLabel())
				.requireExtensionPoint(NEWCHILD_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, null, validator::problemID);
	}

}
