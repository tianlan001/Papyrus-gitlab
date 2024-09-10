/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.dnd.strategy.transition;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrus.uml.diagram.dnd.Activator;
import org.eclipse.papyrus.uml.tools.helper.EventCreationHelper;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Class provide ability of drop a {@link Signal} or {@link Reception} on a {@link Transition} as a {@link Trigger}
 */
public class SignalOrReceptionAsTriggerToTransitionDropStrategy extends TransactionalDropStrategy {
	protected boolean understandRequest(Request request) {
		return (request instanceof DropObjectsRequest);
	}

	protected boolean isTransitionEditPart(EditPart ep) {
		if (false == ep instanceof GraphicalEditPart) {
			return false;
		}
		EObject targetSemantic = getTargetSemanticElement(ep);
		if (false == targetSemantic instanceof Transition) {
			return false;
		}
		return true;
	}

	@Override
	public Command doGetCommand(Request request, EditPart targetEditPart) {
		if (understandRequest(request) && isTransitionEditPart(targetEditPart)) {
			// Verify that source eObjects are signals or receptions
			List<EObject> sourceEObjects = getSourceEObjects(request);
			if (sourceEObjects.size() != 1) {
				return null;
			}
			if (!(sourceEObjects.get(0) instanceof Signal)
					&& !(sourceEObjects.get(0) instanceof Reception)) {
				return null;
			}

			// Get the signal
			Signal draggedSignal = null;
			if (sourceEObjects.get(0) instanceof Signal) {
				draggedSignal = (Signal) sourceEObjects.get(0);
			} else if (sourceEObjects.get(0) instanceof Reception) {
				draggedSignal = ((Reception) sourceEObjects.get(0)).getSignal();
			}
			
			// Variables for command
			final Signal signal = draggedSignal;
			final Transition transition = (Transition) getTargetSemanticElement(targetEditPart);
			
			return new Command() {
				@Override
				public void execute() {
					if (transition != null && signal != null) {
						// Check that the transition does not already have a trigger attached to a signal event attached to the signal
						for (Trigger trigger : transition.getTriggers()) {
							if (trigger.getEvent() instanceof SignalEvent) {
								if (((SignalEvent) trigger.getEvent()).getSignal() == signal) {
									return;
								}
							}
						}
						
						// No triggers found, create one
						Trigger trigger = transition.createTrigger("");
						EventCreationHelper helper = new EventCreationHelper(transition);
						if (trigger != null) {
							Package pkg = transition.getNearestPackage();
							if (pkg != null) {
								SignalEvent signalEvent = helper.getOrCreateSignalEvent(signal, false);
								if (signalEvent != null) {
									signalEvent.setSignal(signal);
									trigger.setEvent(signalEvent);
								}
							}
						}
					}
				}
			};
		}
		
		return null;
	}

	@Override
	public String getLabel() {
		return "Drop signal as trigger with signal event"; //$NON-NLS-1$
	}

	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".SignalAsTriggerToTransitionDrop"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Drops a signal into a transition as a trigger with a signal event"; //$NON-NLS-1$
	}

	public String getCategoryID() {
		return "org.eclipse.papyrus.dnd.SignalAsTriggerToTransitionDrop"; //$NON-NLS-1$
	}

	public String getCategoryLabel() {
		return "Drop a signal into a transition as a trigger with a signal event"; //$NON-NLS-1$
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	public void setOptions(Map<String, Object> options) {
		// Nothing
	}
}
