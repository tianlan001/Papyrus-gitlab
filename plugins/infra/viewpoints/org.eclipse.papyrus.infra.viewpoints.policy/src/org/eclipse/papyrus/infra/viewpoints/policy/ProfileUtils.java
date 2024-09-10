/*****************************************************************************
 * Copyright (c) 2013 CEA LIST, EclipseSource and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Camille Letavernier (EclipseSource) - Cache the parsed ProfileHelper
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.viewpoints.policy;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;


/**
 * Helper class for the ProfileHelper extension point
 *
 * @author Laurent Wouters
 * @since 2.0
 */
public class ProfileUtils {
	/**
	 * ID of the extension point
	 */
	private static final String EXTENSION_ID = "org.eclipse.papyrus.infra.viewpoints.policy.profilehelper";

	private static final IProfileHelper profileHelper = readProfileHelper();

	/**
	 * Gets a instance of the <code>IProfileHelper</code> interface
	 *
	 * @return an instance of <code>IProfileHelper</code>
	 */
	public static IProfileHelper getProfileHelper() {
		return profileHelper;
	}

	private static IProfileHelper readProfileHelper() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);

		try {
			for (IConfigurationElement e : point.getConfigurationElements()) {
				try {
					IProfileHelper instance = (IProfileHelper) e.createExecutableExtension("class");
					if (instance != null) {
						return instance;
					}
				} catch (CoreException | ClassCastException ex) {
					Activator.log.error(ex);
				}
			}
		} catch (Throwable t) {
			Activator.log.error(t);
			// Swallow and return a default value: do not fail during class initialization
		}

		return new DefaultProfileHelper();
	}

	/**
	 * Represents a default implementation of the profile helper
	 *
	 * @author Laurent Wouters
	 */
	private static class DefaultProfileHelper implements IProfileHelper {
		/**
		 * @see org.eclipse.papyrus.infra.viewpoints.policy.IProfileHelper#getAppliedProfiles(org.eclipse.emf.ecore.EObject)
		 */
		public Collection<EPackage> getAppliedProfiles(EObject model) {
			return new ArrayList<>(0);
		}

		/**
		 * @see org.eclipse.papyrus.infra.viewpoints.policy.IProfileHelper#getAppliedStereotypes(org.eclipse.emf.ecore.EObject)
		 */
		public Collection<EClass> getAppliedStereotypes(EObject object) {
			return new ArrayList<>(0);
		}
	}
}
