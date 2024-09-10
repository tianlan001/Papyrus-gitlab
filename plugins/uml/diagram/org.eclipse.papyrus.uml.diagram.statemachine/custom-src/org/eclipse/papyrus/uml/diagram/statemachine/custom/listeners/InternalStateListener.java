/**
 * Copyright (c) 2014, 2017 CEA LIST.
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
 *  Pauline DEVILLE - Bug 521260 - [StateMachineDiagram] Transition is deleted from the model when its kind is changed to internal
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.listeners;

import java.util.Collections;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.RefreshEditPartCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy.PapyrusCanonicalEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Listener of internal state transition
 *
 * @deprecated this is a bad way to create/synchronize views. Behavior similar to {@link PapyrusCanonicalEditPolicy} should be used instead.
 */
@Deprecated
public class InternalStateListener extends AbstractModifcationTriggerListener {

	private static final String DROP_INTERNAL_TRANSITION_COMMAND_LABEL = "Drop internal transition";

	@Override
	protected boolean isCorrectStructuralfeature(EStructuralFeature eStructuralFeature) {
		if (UMLPackage.Literals.TRANSITION__KIND.equals(eStructuralFeature)) {
			return true;
		}
		return false;
	}

	@Override
	protected CompositeCommand getModificationCommand(Notification notif) {
		Object newValue = notif.getNewValue();
		Object notifier = notif.getNotifier();
		if (newValue instanceof TransitionKind && notifier instanceof EObject) {
			if (Display.getCurrent() == null) { // UI Thread safe (getDiagramEditPart will be null in this case...)
				return null;
			}
			CompositeCommand cc = new CompositeCommand("Modification command triggered by modification of the kind of the current selected transition");
			EObject eNotifier = (EObject) notifier;
			// Handle deletion of the old EditPart
			boolean becomingInternal = isBecomingInternal(notif);
			IGraphicalEditPart availableEditPart = getChildByEObject(eNotifier, getDiagramEditPart(), becomingInternal);
			// If there no current represent nothing has to be done
			if (availableEditPart == null) {
				return null;
			}
			Command deleteCommand = getDeleteCommand(becomingInternal, availableEditPart);
			Command creationCommand = getCreationCommand(becomingInternal, eNotifier);
			if (deleteCommand != null && deleteCommand.canExecute() &&
					creationCommand != null && creationCommand.canExecute()) {
				// only delete, if addition is possible, see bug 407736
				cc.compose(new CommandProxy(deleteCommand));
				// handle addition of the new EditPart
				cc.compose(new CommandProxy(creationCommand));
			}

			return cc;
		}

		return null;
	}

	/**
	 * Refresh all needed EditParts {@link AbstractModifcationTriggerListener#needRefresh()} and {@link AbstractModifcationTriggerListener#getEditPartsToRefresh()}
	 *
	 * @param cc
	 * @param set
	 */
	protected void refreshEditParts(CompositeCommand cc, Set<IGraphicalEditPart> set) {
		for (IGraphicalEditPart part : set) {
			RefreshEditPartCommand refreshEditPart = new RefreshEditPartCommand(part, true);
			if (refreshEditPart != null && refreshEditPart.canExecute()) {
				cc.compose(refreshEditPart);
			}
		}
	}

	/**
	 * Return true if the the current feature indicate that the new value of the feature is {@link TransitionKind#INTERNAL}
	 *
	 * @param notif
	 * @return
	 */
	protected boolean isBecomingInternal(Notification notif) {
		Object newValue = notif.getNewValue();
		if (newValue instanceof TransitionKind) {
			TransitionKind newKind = (TransitionKind) newValue;
			return TransitionKind.INTERNAL_LITERAL.equals(newKind);
		}
		return false;

	}

	/**
	 * Get the command to delete the old EditPart
	 *
	 * @param isBecomingInternal
	 *            Boolean true if transition is going to kind X -> Internal
	 * @param availableEditPart
	 *            Existing editpart of the transition
	 * @return
	 */
	private Command getDeleteCommand(boolean isBecomingInternal, IGraphicalEditPart availableEditPart) {
		if (isBecomingInternal) {
			// Get the old transition editpart
			if (!(availableEditPart instanceof TransitionEditPart)) {
				return null;
			}
		} else {
			if (!(availableEditPart instanceof InternalTransitionEditPart)) {
				return null;
			}
		}
		// Bug 521260
		// Here we delete directly the EditPart (without GroupRequest(RequestConstants.REQ_DELETE))
		// because there is a conflict between delete and canonical mode
		// This listener should be independent of the canonical mode
		DeleteCommand deleteCommand = new DeleteCommand(availableEditPart.getNotationView());
		return GMFtoGEFCommandWrapper.wrap(deleteCommand);
	}

	private Command getCreationCommand(boolean isBecomingInternal, EObject eNotifier) {
		if (Display.getCurrent() == null) { // non UI-thread safe (getDiagramEditPart will be null..)
			return null;
		}
		// IGraphicalEditPart
		if (eNotifier instanceof Transition) {
			Transition transition = (Transition) eNotifier;
			IGraphicalEditPart dropTarget = null;
			dropTarget = getChildByEObject(transition.getSource(), getDiagramEditPart(), isBecomingInternal);
			if (isBecomingInternal) {
				dropTarget = getChildByEObject(transition.getSource(), getDiagramEditPart(), false);
			} else {
				// get the region
				dropTarget = getChildByEObject(transition.getContainer(), getDiagramEditPart(), false);
				// get the compartment
				if (dropTarget != null) {
					dropTarget = dropTarget.getChildBySemanticHint(String.valueOf(RegionCompartmentEditPart.VISUAL_ID));
				}
			}
			if (dropTarget != null) {
				Request request = new DropObjectsRequest();
				((DropObjectsRequest) request).setLocation(new Point(1, 1));
				((DropObjectsRequest) request).setObjects(Collections.singletonList(transition));
				Command cmd = dropTarget.getCommand(request);
				if (cmd != null && cmd.canExecute()) {
					CompositeCommand cc = new CompositeCommand(DROP_INTERNAL_TRANSITION_COMMAND_LABEL);
					cc.compose(new CommandProxy(cmd));
					refreshEditParts(cc, Collections.singleton(dropTarget));
					return new ICommandProxy(cc);
				}

				return cmd;
			}
		}
		return null;
	}
}
