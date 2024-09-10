/**
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Nicolas Guyomar (Mia-Software) - Bug 333652 Extension point offering the possibility to declare an EPackage browser
 *  Nicolas Bros (Mia-Software) - Bug 335218 - Extension point for registering EObject, EPackage, model editor
 */
package org.eclipse.papyrus.emf.facet.util.emf.core;

import org.eclipse.emf.ecore.EPackage;

/**
 * This class is used to register a Browser (typically an Eclipse editor or view), that can be
 * opened from a given EPackage. The registration is done through the
 * "org.eclipse.papyrus.emf.facet.util.emf.core.modelViewer" extension point.
 */
public interface IEPackageBrowserOpener {

	/**
	 * This method opens the given {@link EPackage} with the browser corresponding to this {@link IEPackageBrowserOpener}.
	 *
	 * @param ePackage
	 *            the {@link EPackage} to be opened
	 */
	public void openEPackage(final EPackage ePackage);

	/**
	 * Return a human readable name for the {@link EPackage} browser.
	 *
	 * @return a human readable name for the {@link EPackage} browser
	 */
	public String getBrowserName();

}
