/*****************************************************************************
 * Copyright (c) 2009-2014 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Manage BorderItemContainerFigure into the getMoveCommand
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableLabelEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.BorderItemContainerFigure;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;

/**
 * This policy provides the selection handles, feedback and move command for
 * external node label.
 * The expected behavior is to provide an external label that can freely move and with a link feedback
 * towards its parent figure during the move.
 */
public class ExternalLabelPrimaryDragRoleEditPolicy extends NonResizableLabelEditPolicy {

	@Override
	protected List<?> createSelectionHandles() {
		MoveHandle mh = new MoveHandle((GraphicalEditPart) getHost());
		mh.setBorder(null);
		return Collections.singletonList(mh);
	}

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request) {
		LabelEditPart editPart = (LabelEditPart) getHost();

		// FeedBack - Parent + Delta
		Rectangle updatedRect = new Rectangle();
		PrecisionRectangle initialRect = new PrecisionRectangle(getInitialFeedbackBounds().getCopy());
		// in case of bordered item figure bounds is 1x1, real parent figure is then the grandParent
		if (getHostFigure().getParent() instanceof BorderItemContainerFigure) {
			updatedRect = initialRect.getTranslated(getHostFigure().getParent().getParent().getBounds().getLocation().getNegated());
		} else {
			updatedRect = initialRect.getTranslated(getHostFigure().getParent().getBounds().getLocation().getNegated());
		}
		updatedRect = updatedRect.getTranslated(request.getMoveDelta());

		// translate according to the text alignments
		if (editPart instanceof PapyrusLabelEditPart) {
			switch (((PapyrusLabelEditPart) editPart).getTextAlignment()) {
			case PositionConstants.LEFT:
				break;
			case PositionConstants.CENTER:
				updatedRect.translate(getHostFigure().getBounds().width / 2, 0);
				break;
			case PositionConstants.RIGHT:
				updatedRect.translate(getHostFigure().getBounds().width, 0);
				break;
			default:
				break;
			}
		}

		ICommand moveCommand = new SetBoundsCommand(editPart.getEditingDomain(), DiagramUIMessages.MoveLabelCommand_Label_Location, new EObjectAdapter((View) editPart.getModel()), updatedRect);

		return new ICommandProxy(moveCommand);

	}



}
