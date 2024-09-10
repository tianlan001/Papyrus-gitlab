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


import static org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage.Literals.MISC_CLASS__CLASS;
import static org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage.Literals.MODEL_ELEMENT_FACTORY_DESCRIPTOR__FACTORY_CLASS;
import static org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage.Literals.WIDGET_TYPE__WIDGET_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.ENVIRONMENTS_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.properties.environment.Environment;
import org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage;
import org.eclipse.papyrus.infra.properties.environment.Namespace;
import org.eclipse.papyrus.infra.properties.environment.WidgetType;
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
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;

/**
 * This allows to check <em>Properties Environment</em> models in a tooling plug-in (extensions, builds, dependencies, ...).
 */
public class PropertiesEnvironmentPluginChecker {

	public static final String XMI_EXTENSION = "xmi";//$NON-NLS-1$
	public static final String ENVIRONMENT_EXTENSION = "environment";//$NON-NLS-1$
	public static final String CONTENT_TYPE = EnvironmentPackage.eCONTENT_TYPE;

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.properties" //$NON-NLS-1$
	);

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesEnvironmentPluginChecker::createModelValidationChecker);
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
		return IPluginChecker2.Factory.forEMFResource(PropertiesEnvironmentPluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(PropertiesEnvironmentPluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(PropertiesEnvironmentPluginChecker::createModelDependenciesChecker));
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

	private static OpaqueResourceProvider.EMF createOpaqueResourceProvider(IProject project) {
		JavaClassDependencies dependencies = new JavaClassDependencies(project);

		// Java classes
		return OpaqueResourceProvider.EMF.<String> create(ResourceKind.CLASS, EnvironmentPackage.eNS_URI, WIDGET_TYPE__WIDGET_CLASS, (owner, attribute, className) -> getClassURI(owner, attribute, className, dependencies))
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.CLASS, EnvironmentPackage.eNS_URI, MODEL_ELEMENT_FACTORY_DESCRIPTOR__FACTORY_CLASS, dependencies::getClassURI))
				.and(OpaqueResourceProvider.EMF.create(ResourceKind.CLASS, EnvironmentPackage.eNS_URI, MISC_CLASS__CLASS, dependencies::getClassURI));
	}

	private static URI getClassURI(EObject owner, EAttribute attribute, String className, JavaClassDependencies dependencies) {
		String qualifiedName = className; // The common case

		if (owner instanceof WidgetType) {
			// These separate the class's package namespace from the simple name
			WidgetType type = (WidgetType) owner;
			Namespace namespace = type.getNamespace();
			if (namespace != null) {
				qualifiedName = namespace.getValue() + "." + className; //$NON-NLS-1$
			}
		}

		return dependencies.getClassURI(owner, attribute, qualifiedName);
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesEnvironmentPluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<Environment, PluginErrorReporter<Environment>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<Environment> environments = EcoreUtil.getObjectsByType(resource.getContents(), EnvironmentPackage.Literals.ENVIRONMENT);
		return new ExtensionsChecker<>(project, modelFile, environments, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE, PropertiesEnvironmentPluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<Environment> createPluginErrorReporter(IFile pluginXML, IFile modelFile, Environment model) {
		PropertiesEnvironmentPluginXMLValidator validator = new PropertiesEnvironmentPluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE, __ -> Messages.PropertiesEnvironmentPluginChecker_0)
				.requireExtensionPoint(ENVIRONMENTS_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, null, validator::problemID);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(PropertiesEnvironmentPluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(EnvironmentPackage.eNS_URI, PropertiesEnvironmentCustomValidator::new);
	}

}
