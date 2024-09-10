/*****************************************************************************
 * Copyright (c) 2017 Ericsson Communications and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionNameEditPart;



/**
 * @since 3.0
 */
public class CustomActivityPartitionNameEditPart extends ActivityPartitionNameEditPart {
	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomActivityPartitionNameEditPart(View view) {
		super(view);
	}

	@Override
	protected void setLabelTextHelper(IFigure figure, String text) {
		if (figure instanceof PapyrusWrappingLabel) {
			return;
		} else {
			super.setLabelTextHelper(figure, text);
		}
	}
}
