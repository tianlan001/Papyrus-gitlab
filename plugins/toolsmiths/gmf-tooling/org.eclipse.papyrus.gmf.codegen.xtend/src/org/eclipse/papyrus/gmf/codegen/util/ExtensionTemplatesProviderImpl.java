/******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.wiring.FrameworkWiring;

public class ExtensionTemplatesProviderImpl implements IExtensionTemplatesProvider {

	private static final String PLATFORM_PLUGIN_PREFIX = "platform:/plugin/";

	public static final String DEFAULT_DYNAMIC_TEMPLATES_FOLDER = "aspects";

	public static final String POINT_SEPARATOR = ".";

	public static final String EMPLTY_STRING = "";

	public static final String TEMPLATE_FILE_EXTENSIION = "xtend";

	private final String myCustomTemplatePath;

	private List<Class<?>> myDynamicClasses;

	private List<Class<?>> myCustomClasses;

	private static final String SLASH = "/";

	private final Bundle myBundle;

	private final boolean myNeedAspects;

	private final boolean myUsePluginNotProject;

	public ExtensionTemplatesProviderImpl(String customPath, boolean needAspects) {
		boolean usePluginNotProject = customPath.startsWith(PLATFORM_PLUGIN_PREFIX);
		customPath = cutPrefix(customPath, PLATFORM_PLUGIN_PREFIX);
		customPath = cutPrefix(customPath, SLASH);

		String bundleName = customPath.split(SLASH)[0];

		myCustomTemplatePath = cutPrefix(cutPrefix(customPath, bundleName), SLASH);

		Bundle platformBundle = Platform.getBundle(bundleName);

		if (usePluginNotProject) {
			myBundle = platformBundle;
		} else {
			if (platformBundle != null) {
				// TODO: could be test-specific
				usePluginNotProject = true;
				System.err.println("Bundle presents in platform: " + bundleName);
				myBundle = platformBundle;
			} else {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);

				ManifestUtil.createOrFillManifest(project);
				try {
					myBundle = loadBundle(project);
				} catch (MalformedURLException e) {
					throw new RuntimeException("Cannot create correct URL for Bundle.", e);
				} catch (BundleException e) {
					throw new RuntimeException("Error. Bundle was not load.", e);
				}
			}
		}

		myUsePluginNotProject = usePluginNotProject;
		myNeedAspects = needAspects;
	}

	private static String cutPrefix(String text, String prefix) {
		if (text.startsWith(prefix)) {
			return text.substring(prefix.length());
		} else {
			return text;
		}
	}

	private static Bundle loadBundle(IProject project) throws MalformedURLException, BundleException {
		String url = project.getLocation().toFile().toURI().toURL().toExternalForm();
		BundleContext bundleContext = CodegenXtendPlugin.getInstance().getContext();
		return bundleContext.installBundle(url);
	}

	@Override
	public List<Class<?>> getCustomTemplateClasses() {
		loadClassesFromBundle();
		return myCustomClasses;
	}

	@Override
	public List<Class<?>> getDynamicTemplateClasses() {
		loadClassesFromBundle();
		return myDynamicClasses;
	}

	@Override
	public Class<?> getSuperClassForDynamic(Class<?> _class) {
		return _class.getSuperclass();
	}

	private Class<?> loadClass(String className) throws ClassNotFoundException, IOException {
		return myBundle.loadClass(className);
	}

	@Override
	public void dispose() {
		if (!myUsePluginNotProject) {
			try {
				Bundle systemBundle = CodegenXtendPlugin.getInstance().getContext().getBundle(0);
				myBundle.uninstall();
				FrameworkWiring frameworkWiring = systemBundle.adapt(FrameworkWiring.class);
				frameworkWiring.refreshBundles(frameworkWiring.getRemovalPendingBundles());
			} catch (BundleException e) {
				throw new RuntimeException("Error while unloading bundle.", e);
			}
		}
	}

	private void loadClassesFromBundle() {
		if (myDynamicClasses != null && myCustomClasses != null) {
			return;
		}
		myDynamicClasses = new ArrayList<Class<?>>();
		myCustomClasses = new ArrayList<Class<?>>();

		Enumeration<java.net.URL> classURLs = myBundle.findEntries(myCustomTemplatePath, "*." + TEMPLATE_FILE_EXTENSIION, true);

		while (classURLs != null && classURLs.hasMoreElements()) {
			String classPath = classURLs.nextElement().toString().trim();
			classPath = classPath.substring(classPath.indexOf(myCustomTemplatePath), classPath.length()).replace(myCustomTemplatePath, EMPLTY_STRING)
					.replace(POINT_SEPARATOR + TEMPLATE_FILE_EXTENSIION, EMPLTY_STRING);

			try {
				Class<?> templateClass = loadClass(getFQCN(classPath));
				if (classPath.startsWith(DEFAULT_DYNAMIC_TEMPLATES_FOLDER) && isAspectClass(templateClass)) {
					if (myNeedAspects) {
						myDynamicClasses.add(templateClass);
					}
				} else {
					myCustomClasses.add(templateClass);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Error. Did not load " + classPath + ". Class not found.", e);
			} catch (IOException e) {
				throw new RuntimeException("Error has occurred when try to load " + classPath, e);
			}
		}
	}

	private boolean isAspectClass(Class<?> customClass) {
		Class<?> superClass = customClass.getSuperclass();
		if (superClass == null) {
			return false;
		}

		String superTemplateResourceName = superClass.getName().replace(POINT_SEPARATOR, SLASH) + "." + TEMPLATE_FILE_EXTENSIION;

		URL codegenEntry = CodegenXtendPlugin.getInstance().getBundle().getResource(superTemplateResourceName);

		return codegenEntry != null;
	}

	private String getFQCN(String entryPath) {
		return entryPath.replace(SLASH, POINT_SEPARATOR);
	}
}
