/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net -  Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * An interface for implementing a LabelProvider which requires
 * graphical elements.
 * 
 * @since 3.0
 */
public interface IGraphicalLabelProvider extends ILabelProvider {

	/**
	 * Create graphical elements, which will appear before the widget
	 * used to display the provided elements
	 * 
	 * @param parent
	 *            The parent composite.
	 * @since 3.0
	 */
	default public void createBefore(final Composite parent) {
	};

	/**
	 * Create graphical elements, which will appear after the widget
	 * used to display the provided elements
	 *
	 * @param parent
	 *            The parent composite.
	 * @since 3.0
	 */
	default public void createAfter(final Composite parent) {
	};

	/**
	 * Create a graphical element which will be add as a toolbar of the viewer.Typically create in parent a Toolbar including ToolItem.
	 * 
	 * @param parent
	 *            The parent composite.
	 * 
	 * @since 3.0
	 */
	default public void createViewerToolbar(final Composite parent) {
	};
}
