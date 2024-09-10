/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.handler;

import org.eclipse.core.commands.IHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.uml.diagram.usecase.command.ChangeUseCasetoClassifierShapeCommand;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageEditPart;

/**
 * the goal of this class is to transform a change a Subject as a default named Element
 *
 */
public class UseCaseToClassiferHandler extends ChangeShapeHandler implements IHandler {

	@Override
	public boolean isEnabled() {
		GraphicalEditPart editPart = getSelectedGraphicalEditpart();
		if ((editPart instanceof UseCaseEditPartTN) || (editPart instanceof UseCaseInComponentEditPart) || (editPart instanceof UseCaseInPackageEditPart)) {
			return true;
		}
		return false;
	}

	@Override
	protected AbstractTransactionalCommand getChangeShapeCommand(GraphicalEditPart editPart) {
		ChangeUseCasetoClassifierShapeCommand command = new ChangeUseCasetoClassifierShapeCommand(editPart.getEditingDomain(), editPart);
		return command;
	}
}
