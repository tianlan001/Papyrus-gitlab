/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.layout;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AutomaticCompartmentLayoutManager;

/**
 * The Class AutomaticCompartmentToolBox.
 */
public class AutomaticCompartmentToolBox implements ILayoutToolBox {

	/** The AUTOMATI c_ layout. */
	protected final String AUTOMATIC_LAYOUT = "Automatic_Layout";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditPolicy getEditPolicy() {
		return new ResizableCompartmentEditPolicy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLayout() {
		return AUTOMATIC_LAYOUT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractLayout getFigureLayout() {
		return new AutomaticCompartmentLayoutManager();
	}

}
