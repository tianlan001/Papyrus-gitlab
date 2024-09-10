/*****************************************************************************
 * Copyright (c) 2023 CEA LIST
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
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 582075
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.edit.policies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CInteractionOperandEditPart;

/**
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 * @since 7.1
 *        Forbidden InteractionOperand all drag and drop operations - Bug 582412
 */

public class InteractionFragmentContainerDragDropEditPolicy extends DragDropEditPolicy {
	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy#getDropCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {
		List<? extends EditPart> editParts = request.getEditParts();

		if (editParts.size() == 1 && editParts.get(0) instanceof CInteractionOperandEditPart) {
			return UnexecutableCommand.INSTANCE;
		}
		return super.getDropCommand(request);
	}
}
