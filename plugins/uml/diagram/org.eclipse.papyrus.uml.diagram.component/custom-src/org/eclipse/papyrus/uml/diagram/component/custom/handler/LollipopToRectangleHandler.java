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
import org.eclipse.papyrus.uml.diagram.component.custom.command.LollipopToRectangleCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceEditPart;

/**
 * this class is the Handler to change a interface displayed as lollipop to rectangle
 *
 */
public class LollipopToRectangleHandler extends ChangeShapeHandler implements IHandler {
	/**
	 *
	 * Constructor.
	 *
	 */
	public LollipopToRectangleHandler() {
		super();
		newType = RectangleInterfaceEditPart.VISUAL_ID;
	}

	@Override
	public boolean isEnabled() {
		GraphicalEditPart editPart = getSelectedGraphicalEditpart();
		return (editPart instanceof InterfaceEditPart) || (editPart instanceof InterfaceEditPartPCN);
	}

	@Override
	protected AbstractTransactionalCommand getChangeShapeCommand(GraphicalEditPart editPart) {
		return new LollipopToRectangleCommand(editPart.getEditingDomain(), editPart, new SemanticAdapter(null, null));
	}
}