/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @author Mickael ADAM
 *         Deprecated: use of css instead of hard code to set header and position.
 */
@Deprecated
public abstract class AbstractInteractionEditPart extends RoundedCompartmentEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public AbstractInteractionEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultHasHeader()
	 */
	@Override
	protected boolean getDefaultHasHeader() {
		return true;
	}

	/**
	 *
	 * deprecated, use css instead
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultNamePosition()
	 */
	@Override
	protected int getDefaultNamePosition() {
		return PositionConstants.LEFT;
	}

}