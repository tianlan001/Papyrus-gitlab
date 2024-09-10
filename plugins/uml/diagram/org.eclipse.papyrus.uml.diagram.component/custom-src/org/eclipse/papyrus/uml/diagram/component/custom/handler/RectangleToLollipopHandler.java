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
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.handler;

import org.eclipse.core.commands.IHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.component.custom.command.RectangleToLollipopCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceEditPartCN;

/**
 * this handler is used to change the display of an interface as rectangle to lollipop
 *
 */
public class RectangleToLollipopHandler extends ChangeShapeHandler implements IHandler {
	/**
	 *
	 * Constructor.
	 *
	 */
	public RectangleToLollipopHandler() {
		newType = "" + InterfaceEditPart.VISUAL_ID;
	}

	@Override
	public boolean isEnabled() {
		GraphicalEditPart editPart = getSelectedGraphicalEditpart();
		return (editPart instanceof RectangleInterfaceEditPart) || (editPart instanceof RectangleInterfaceEditPartCN);
	}

	@Override
	protected AbstractTransactionalCommand getChangeShapeCommand(GraphicalEditPart editPart) {
		return new RectangleToLollipopCommand(editPart.getEditingDomain(), editPart, new SemanticAdapter(null, null));
	}
}