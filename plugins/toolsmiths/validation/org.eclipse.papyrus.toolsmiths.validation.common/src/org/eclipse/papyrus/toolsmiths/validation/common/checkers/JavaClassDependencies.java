/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.papyrus.infra.tools.util.ClasspathHelper;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Inference of bundle dependencies from the Java classes referenced by a tooling model.
 */
public class JavaClassDependencies {

	private final String hostBundle;
	private final BiFunction<? super EObject, ? super EAttribute, ?> classExtractor;

	public JavaClassDependencies(IProject project) {
		this(project, null);
	}

	public JavaClassDependencies(IProject project, BiFunction<? super EObject, ? super EAttribute, ?> classExtractor) {
		super();

		this.hostBundle = Optional.ofNullable(PluginRegistry.findModel(project))
				.map(IPluginModelBase::getBundleDescription)
				.map(BundleDescription::getSymbolicName)
				.orElse(project.getName());
		this.classExtractor = (classExtractor != null) ? classExtractor : this::getClass;
	}

	public URI getClassURI(EObject owner, EAttribute attribute, String className) {
		Optional<String> bundleName;

		Object commandClass = classExtractor.apply(owner, attribute);
		if (commandClass instanceof Class) {
			Bundle bundle = FrameworkUtil.getBundle((Class<?>) commandClass);
			bundleName = Optional.ofNullable(bundle).map(Bundle::getSymbolicName);
		} else if (commandClass instanceof IType) {
			Optional<IPluginModelBase> plugin = Optional.empty();

			IType commandType = (IType) commandClass;
			if (commandType.isBinary()) {
				IPackageFragmentRoot jar = (IPackageFragmentRoot) commandType.getAncestor(IJavaModel.PACKAGE_FRAGMENT_ROOT);
				if (jar != null) {
					IPath jarPath = jar.getPath();

					// It could be a bundle JAR or a library in a project
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(jarPath);
					if (file != null && file.isAccessible()) {
						plugin = Optional.ofNullable(PluginRegistry.findModel(file.getProject()));
					} else {
						for (IPluginModelBase next : PluginRegistry.getExternalModels()) {
							IPath path = new Path(next.getInstallLocation());
							if (jarPath.equals(path) || path.isPrefixOf(jarPath)) {
								plugin = Optional.of(next);
								break;
							}
						}
					}
				}
			} else {
				plugin = Optional.of(((IType) commandClass).getJavaProject())
						.map(IJavaProject::getProject)
						.map(PluginRegistry::findModel);
			}

			bundleName = plugin.map(IPluginModelBase::getBundleDescription)
					.map(BundleDescription::getSymbolicName);
		} else {
			bundleName = Optional.empty();
		}

		return URI.createURI(String.format("bundleclass://%s/%s", bundleName.orElse(hostBundle), className)); //$NON-NLS-1$
	}

	private Object getClass(EObject owner, EAttribute attribute) {
		Object result = null;

		EDataType dataType = attribute.getEAttributeType();

		if (dataType.getInstanceClass() == Class.class || dataType.getInstanceClass() == URI.class) {
			// Easy case
			result = owner.eGet(attribute);
		} else if (dataType.getInstanceClass() == String.class) {
			String className = (String) owner.eGet(attribute);
			URI context = EcoreUtil.getURI(owner);
			if (context != null) {
				context = context.trimFragment();
			}
			result = ClasspathHelper.INSTANCE.findClass(className, context, null);
		}

		return result;
	}

}
