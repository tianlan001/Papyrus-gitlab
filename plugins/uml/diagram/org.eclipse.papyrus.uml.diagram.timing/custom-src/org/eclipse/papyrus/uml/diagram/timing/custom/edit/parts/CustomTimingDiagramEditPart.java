/*******************************************************************************
 * Copyright (c) 2012, 2023 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 582075
 *
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.parts;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;

public class CustomTimingDiagramEditPart extends TimingDiagramEditPart {

	public CustomTimingDiagramEditPart(final View view) {
		super(view);
	}

	@Override
	public Command getCommand(final Request request) {
		if (request.getType() == RequestConstants.REQ_DROP && !canBeDropped(((GroupRequest) request).getEditParts())) {
			// this fixes the bug where an OccurrenceSpecification could be moved out of its timeline
			return UnexecutableCommand.INSTANCE;
		}
		return super.getCommand(request);
	}

	private static boolean canBeDropped(final List<? extends EditPart> editParts) {
		for (final EditPart editPart : editParts) {
			if (!(editPart instanceof InteractionEditPartTN)) {
				return false;
			}
		}
		return true;
	}

}
