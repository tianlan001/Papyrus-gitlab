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
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Class provide ability of drop an {@link Operation} on a {@link Transition} as a {@link Trigger}
 */
public class OperationAsTriggerToTransitionDropStrategy extends TransactionalDropStrategy {
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
			if (!(sourceEObjects.get(0) instanceof Operation)) {
				return null;
			}
			
			// Final variables for command
			final Operation operation = (Operation) sourceEObjects.get(0);
			final Transition transition = (Transition) getTargetSemanticElement(targetEditPart);
			
			// Return a command that will create triggers and signal events if necessary
			return new Command() {
				@Override
				public void execute() {
					if (transition != null && operation != null) {
						// Check that the transition does not already have a trigger attached to a call event attached to the operation
						for (Trigger trigger : transition.getTriggers()) {
							if (trigger.getEvent() instanceof CallEvent) {
								if (((CallEvent) trigger.getEvent()).getOperation() == operation) {
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
								CallEvent callEvent = helper.getOrCreateCallEvent(operation, false);
								if (callEvent != null) {
									callEvent.setOperation(operation);
									trigger.setEvent(callEvent);
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
		return "Drop operation as trigger with call event"; //$NON-NLS-1$
	}

	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".OperationAsTriggerToTransitionDrop"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Drops an operation into a transition as a trigger with a call event"; //$NON-NLS-1$
	}

	public String getCategoryID() {
		return "org.eclipse.papyrus.dnd.OperationAsTriggerToTransitionDrop"; //$NON-NLS-1$
	}

	public String getCategoryLabel() {
		return "Drop an operation into a transition as a trigger with a call event"; //$NON-NLS-1$
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
