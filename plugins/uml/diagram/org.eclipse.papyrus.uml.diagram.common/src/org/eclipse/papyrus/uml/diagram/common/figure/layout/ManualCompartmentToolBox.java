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
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CResizableCompartmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ManualCompartmentLayoutManager;

/**
 * this is a tool kit to manage Manual layout.
 */

public class ManualCompartmentToolBox implements ILayoutToolBox {

	/** The MANUA l_ layout. */
	protected final String MANUAL_LAYOUT = "Manual_Layout";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditPolicy getEditPolicy() {
		return new CResizableCompartmentEditPolicy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLayout() {
		return MANUAL_LAYOUT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractLayout getFigureLayout() {
		return new ManualCompartmentLayoutManager();
	}

}
