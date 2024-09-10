/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 440224 : Label Alignment
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Text alignment implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.edit.parts;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationNameEditPartCN;

/**
 * The Class CustomTimeObservationNameEditPartCN.
 */
public class CustomTimeObservationNameEditPartCN extends TimeObservationNameEditPartCN {


	/**
	 * Instantiates a new custom time observation name edit part child node.
	 *
	 * @param view
	 *            the view
	 */
	public CustomTimeObservationNameEditPartCN(View view) {
		super(view);
	}

	/**
	 * Gets the default text alignment.
	 *
	 * @return the default text alignment
	 */
	@Override
	protected int getDefaultTextAlignment() {
		return PositionConstants.LEFT;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.FloatingLabelEditPart#getDefaultLabelVisibility()
	 *
	 * @return
	 */
	@Override
	protected boolean getDefaultLabelVisibility() {
		return true;
	}

}
