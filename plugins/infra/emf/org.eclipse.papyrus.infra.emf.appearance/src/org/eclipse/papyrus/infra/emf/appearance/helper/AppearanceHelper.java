/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.appearance.helper;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.papyrus.infra.emf.appearance.Activator;
import org.eclipse.papyrus.infra.emf.appearance.style.AnnotationStyleProvider;
import org.eclipse.papyrus.infra.emf.appearance.style.AppearanceStyleProvider;


public class AppearanceHelper {

	public static final String EXTENSION_ID = Activator.PLUGIN_ID + ".styleProvider"; //$NON-NLS-1$

	private static AppearanceStyleProvider styleProvider = findStyleProvider();

	public static boolean showElementIcon(EModelElement modelElement) {
		return styleProvider.showElementIcon(modelElement);
	}

	public static int getQualifiedNameDepth(EModelElement modelElement) {
		return styleProvider.getQualifiedNameDepth(modelElement);
	}

	public static boolean showShadow(EModelElement modelElement) {
		return styleProvider.showShadow(modelElement);
	}

	private static AppearanceStyleProvider findStyleProvider() {
		// Default style provider
		AppearanceStyleProvider provider = new AnnotationStyleProvider();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		int currentPriority = Integer.MAX_VALUE;
		for (IConfigurationElement e : config) {
			try {
				int priority = Integer.parseInt(e.getAttribute("priority")); //$NON-NLS-1$
				if (priority < currentPriority) {
					provider = (AppearanceStyleProvider) e.createExecutableExtension("styleProvider"); //$NON-NLS-1$
					currentPriority = priority;
				}
			} catch (Exception ex) {
				Activator.log.error("The plugin " + e.getContributor() + " contributed an invalid extension for " + EXTENSION_ID, ex); //$NON-NLS-1$//$NON-NLS-2$
			}
		}

		return provider;
	}



}
