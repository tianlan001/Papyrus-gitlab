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
 *   Christian W. Damus - bugs 570097, 571125, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;


import static org.eclipse.papyrus.infra.properties.contexts.ContextsPackage.Literals.TAB__IMAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.CONTEXTS_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ResourceKind;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesContextDecoratorAdapterFactory;

/**
 * This allows to check <em>Properties Context</em> models in a tooling plug-in (extensions, builds, dependencies, ...).
 */
public class PropertiesContextPluginChecker {

	public static final String CONTEXT_EXTENSION = "ctx";//$NON-NLS-1$
	public static final String CONTENT_TYPE = ContextsPackage.eCONTENT_TYPE;

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.properties" //$NON-NLS-1$
	);

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesContextPluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesContextPluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createIconProvider());
	}

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(PropertiesContextPluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(PropertiesContextPluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withReferencedResources(createOpaqueResourceProvider(project));
	}

	// Icon resources
	private static OpaqueResourceProvider.EMF createIconProvider() {
		return OpaqueResourceProvider.EMF.create(ResourceKind.ICON, ContextsPackage.eNS_URI, TAB__IMAGE);
	}

	private static OpaqueResourceProvider.EMF createOpaqueResourceProvider(IProject project) {
		// Icon resources
		return createIconProvider();
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesContextPluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<Context, PluginErrorReporter<Context>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<Context> contexts = EcoreUtil.getObjectsByType(resource.getContents(), ContextsPackage.Literals.CONTEXT);
		return new ExtensionsChecker<>(project, modelFile, contexts, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE, PropertiesContextPluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<Context> createPluginErrorReporter(IFile pluginXML, IFile modelFile, Context model) {
		PropertiesContextPluginXMLValidator validator = new PropertiesContextPluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE, context -> context.getName())
				.requireExtensionPoint(CONTEXTS_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, null, validator::problemID);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesContextPluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withAdapterFactoryDecorator(PropertiesContextDecoratorAdapterFactory::new)
				.withValidator(ContextsPackage.eNS_URI, PropertiesContextCustomValidator::new)
				.withValidator(UiPackage.eNS_URI, PropertiesUICustomValidator::new)
				.withValidator(ConstraintsPackage.eNS_URI, ConstraintsCustomValidator::new);
	}

}
