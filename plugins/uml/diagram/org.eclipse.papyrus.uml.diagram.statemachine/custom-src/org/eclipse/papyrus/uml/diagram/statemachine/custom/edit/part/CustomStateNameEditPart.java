/**
 * Copyright (c) 2010-2011-2014-2017 CEA LIST.
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
 * Arthur daussy (Atos) arthur.daussy@atos.net - Bug : 365405: [State Machine Diagram] Behaviours (Entry,exit,do) on states should have their own
 * mechanisms
 * Ansgar Radermacher: Bug 402068: Correct calculation of region height in refresh visuals
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Pauline DEVILLE (CEA LIST): Bug 509015 - [StateMachineDiagram] lacks support for UML 2.5 notation of redefinable elements with isLeaf=true
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomStateResizeCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.StateFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.State;

public class CustomStateNameEditPart extends StateNameEditPart {

	public CustomStateNameEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);

	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);

		refreshVisuals();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		StateFigure stateFigure = ((StateEditPart) getParent()).getPrimaryShape();

		View stateLabelView = (View) getModel();
		if (!(stateLabelView.getElement() instanceof State)) {
			return;
		}

		State state = (State) stateLabelView.getElement();

		View stateView = (View) stateLabelView.eContainer();
		if (stateView == null) {
			return;
		}
		View stateCompartmentView = CustomStateEditPart.getStateCompartmentView(stateView);
		if (stateCompartmentView.getChildren().isEmpty()) {
			stateFigure.getStateCompartmentFigure().setVisible(false);
		} else {
			stateFigure.getStateCompartmentFigure().setVisible(true);
		}


		if (state.isSubmachineState()) {
			stateFigure.setSubmachineStateName(UMLLabelInternationalization.getInstance().getLabel(state) + " : " + state.getSubmachine().getQualifiedName());
			stateFigure.setIsSubmachineState(true);
		} else {
			stateFigure.setIsSubmachineState(false);
		}

		// set <<final>> label
		stateFigure.setIsLeafState(state.isLeaf());
		stateFigure.restoreFinalLabel();

		int width = stateFigure.getBounds().width;
		// calculate height for labels via position of the rectangle figure after the labels. Layout managers such as the
		// AutomaticCompartmentLayoutManager add extra space on top of the first label which would not be accounted for
		// when adding the space for the labels.
		int height = 0;
		if (stateCompartmentView.isVisible() && (stateFigure.getStateCompartmentFigure() != null)) {
			stateFigure.validate(); // validate the figure, assure that layout manager is called.
			height = stateFigure.getStateCompartmentFigure().getBounds().y - stateFigure.getBounds().y + 1;
			// Sanity check
			if (height < 0) {
				height = 0;
			}
		} else {
			height = stateFigure.getNameLabel().getBounds().height;
		}

		int stateHeight = Zone.getHeight(stateView);
		int stateWidth = Zone.getWidth(stateView);

		int stateCompartHeight = Zone.getHeight(stateCompartmentView);

		int dx = width - stateWidth;
		int dy = stateCompartHeight + height - stateHeight;
		int x = Zone.getX(stateView);
		int y = Zone.getY(stateView);

		if ((stateHeight != -1) && (stateCompartHeight != -1) && (width != 0) && (dy != 0)) {
			dx = (dx > 0) ? dx : 0;
			// a resize request, which we route to the specific ResizeCommand
			IAdaptable adaptableForState = new SemanticAdapter(null, stateView);
			ChangeBoundsRequest internalResizeRequest = new ChangeBoundsRequest();
			internalResizeRequest.setResizeDirection(PositionConstants.EAST);
			internalResizeRequest.setSizeDelta(new Dimension(dx, dy));
			Rectangle rect = new Rectangle(x, y, stateWidth + dx, stateHeight + dy);

			CustomStateResizeCommand internalResizeCommand = new CustomStateResizeCommand(adaptableForState, getDiagramPreferencesHint(), getEditingDomain(), DiagramUIMessages.CreateCommand_Label,
					internalResizeRequest, rect, true);
			internalResizeCommand.setOptions(Collections.singletonMap(Transaction.OPTION_UNPROTECTED, Boolean.TRUE));

			try {
				internalResizeCommand.execute(null, null);
			} catch (ExecutionException e) {
			}
		}
	}
}
