/*****************************************************************************
* Copyright (c) 2021 CEA LIST, ARTAL
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Etienne ALLOGO (ARTAL) - Initial API and implementation
*   Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : generate less dead or duplicate code
*****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.notation.View;

/**
 *
 * @author allogo
 * @since 5.0
 */
public abstract class AbstractLinkLabelEditPart extends UMLLabelEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public AbstractLinkLabelEditPart(View view) {
		super(view);
	}

	@Override
	protected IFigure createFigure() {
		// Parent should assign one using setLabel() method
		return null;
	}
}