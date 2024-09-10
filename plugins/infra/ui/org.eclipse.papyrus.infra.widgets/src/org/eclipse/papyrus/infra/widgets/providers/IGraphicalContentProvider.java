/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * An interface for implementing a ContentProvider which requires
 * graphical elements for features such as filters.
 *
 * @author Camille Letavernier
 *
 */
public interface IGraphicalContentProvider extends IContentProvider {

	/**
	 * Create graphical elements, which will appear before the widget
	 * used to display the provided elements
	 *
	 * @param parent
	 */
	public abstract void createBefore(Composite parent);

	/**
	 * Create graphical elements, which will appear after the widget
	 * used to display the provided elements
	 *
	 * @param parent
	 */
	public abstract void createAfter(Composite parent);

	/**
	 * Create a graphical element, such as button to be add as toolbar of the Viewer.
	 * 
	 * @param parent
	 * @since 3.0
	 */
	default public void createViewerToolbar(Composite parent) {
	};
}
