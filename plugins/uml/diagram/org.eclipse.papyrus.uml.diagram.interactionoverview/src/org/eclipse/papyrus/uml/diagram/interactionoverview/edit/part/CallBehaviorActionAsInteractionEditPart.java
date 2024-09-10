/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST and others.
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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus (CEA) - bug 323802
 *   Christian W. Damus (CEA) - bug 429422
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.RefreshCommandForUndo;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.Activator;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.commands.CreateSnapshotForInteractionFromRefreshCommand;
import org.eclipse.papyrus.uml.diagram.interactionoverview.figures.InteractionWithSnapshotFigure;
import org.eclipse.papyrus.uml.diagram.interactionoverview.utils.CallBehaviorUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.CallBehaviorAction;


public class CallBehaviorActionAsInteractionEditPart extends CallBehaviorActionEditPart {

	public static final String VISUAL_ID = "CallBehaviorAction_InteractionShape";

	public CallBehaviorActionAsInteractionEditPart(final View view) {
		super(view);
	}

	private boolean isRefreshingSnapshotFigure;

	@Override
	protected IFigure createNodeShape() {
		return primaryShape = new InteractionWithSnapshotFigure();
	}

	@Override
	protected boolean addFixedChild(final EditPart childEditPart) {
		return false;
	}

	@Override
	protected boolean removeFixedChild(final EditPart childEditPart) {
		return false;
	}

	@Override
	protected void refreshChildren() {
		super.refreshChildren();
		// add the rake if behavior is an activity
		if (resolveSemanticElement() instanceof CallBehaviorAction) {
			final CallBehaviorAction action = (CallBehaviorAction) resolveSemanticElement();
			if (action.getBehavior() instanceof Activity) {
				getPrimaryShape().displayRake(true);
			} else {
				getPrimaryShape().displayRake(false);
			}
		}
	}

	@Override
	public Command getCommand(final Request _request) {

		if (_request instanceof ChangeBoundsRequest) {
			final ChangeBoundsRequest request = (ChangeBoundsRequest) _request;
			final CompoundCommand compoundCommand = new CompoundCommand();
			compoundCommand.add(new RefreshCommandForUndo(this));
			compoundCommand.add(new Command() {

				@Override
				public void execute() {
					final Rectangle newBound = request.getTransformedRectangle(primaryShape.getBounds());
					((InteractionWithSnapshotFigure) primaryShape).updateSnapshot(new Rectangle(newBound.x, newBound.y, newBound.width, newBound.height - 15));
				}
			});
			compoundCommand.add(super.getCommand(request));

			return compoundCommand;
		}
		return super.getCommand(_request);
	}

	@Override
	public void refresh() {
		super.refresh();
		if (((InteractionWithSnapshotFigure) primaryShape).getImageFigure() != null && isValidFromRefresh()) {
			if (!isRefreshingSnapshotFigure) {
				isRefreshingSnapshotFigure = true;

				try {
					if (((InteractionWithSnapshotFigure) primaryShape).getImageFigure().getImage() == null) {

						try {

							final CreateSnapshotForInteractionFromRefreshCommand command = CreateSnapshotForInteractionFromRefreshCommand.create((View) getModel(), (GraphicalEditPart) getParent());
							// use to avoid to put it in the command stack
							try {
								GMFUnsafe.write(getEditingDomain(), command);
							} catch (final Exception e) {
								Activator.log.error(e);
							}

						} catch (final Exception e) {
							Activator.log.error(e);
						}
					}
					if (!((InteractionWithSnapshotFigure) primaryShape).isImageSizeFitsImageFigure()) {

						final Rectangle bounds = new Rectangle(0, 0, primaryShape.getBounds().width, primaryShape.getBounds().height - 15);
						((InteractionWithSnapshotFigure) primaryShape).updateSnapshot(bounds);
					}
				} finally {
					isRefreshingSnapshotFigure = false;
				}
			}
		}
	}

	private boolean isValidFromRefresh() {
		final View callBehaviorActionView = (View) getModel();
		return CallBehaviorUtil.getDiagramLinked(callBehaviorActionView) != null && !CallBehaviorUtil.getDiagramLinked(callBehaviorActionView).equals("");
	}
}
