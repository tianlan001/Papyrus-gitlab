/*****************************************************************************
 * Copyright (c) 2010, 2023 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 582075
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.uml2.uml.Action;

/**
 * This edit policy deletes linked local conditions view when an action view is
 * deleted.
 */
public class DeleteActionViewEditPolicy extends ViewComponentEditPolicy {

	/**
	 * Returns true when the request is a graphical delete
	 *
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(Request)
	 * @return true for a graphical delete request
	 */
	@Override
	public boolean understandsRequest(Request req) {
		return org.eclipse.gef.RequestConstants.REQ_DELETE.equals(req.getType());
	}

	/**
	 * Returns a command for graphical delete (with local conditions also
	 * deleted).
	 *
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 * @return the command or null
	 */
	@Override
	public Command getCommand(Request request) {
		if (org.eclipse.gef.RequestConstants.REQ_DELETE.equals(request.getType())) {
			if (request instanceof GroupRequest) {
				// List of parts from the request is not up to date. Consider
				// the host only.
				List<?> parts = Collections.singletonList(getHost());
				((GroupRequest) request).setEditParts(getHost());
				// inspect the list of parts to add linked local conditions
				List<? extends EditPart> partsToAdd = getListOfLinkedLocalConditionsParts(parts);

				@SuppressWarnings("unchecked")
				List<EditPart> editParts = (List<EditPart>) ((GroupRequest) request).getEditParts();
				editParts.addAll(partsToAdd);
				((GroupRequest) request).setEditParts(editParts);
				return getDeleteCommand((GroupRequest) request);
			}
		}
		return null;
	}

	/**
	 * Get the list of local condition parts linked to a part of the list
	 *
	 * @param partsToExplore
	 *            list of parts to explore
	 * @return list of local condition parts
	 */
	private List<EditPart> getListOfLinkedLocalConditionsParts(List<?> partsToExplore) {
		List<EditPart> result = new LinkedList<>();
		for (Object part : partsToExplore) {
			if (part instanceof AbstractBorderedShapeEditPart) {
				EObject element = ((AbstractBorderedShapeEditPart) part).resolveSemanticElement();
				if (element instanceof Action) {
					Action action = (Action) element;
					List<?> connections = ((AbstractBorderedShapeEditPart) part).getSourceConnections();
					if (connections != null) {
						for (Object connection : connections) {
							if (connection instanceof ConnectionNodeEditPart) {
								EditPart target = ((ConnectionNodeEditPart) connection).getTarget();
								if (target instanceof ShapeNodeEditPart) {
									EObject linkedElement = ((ShapeNodeEditPart) target).resolveSemanticElement();
									if (action.getLocalPreconditions().contains(linkedElement) || action.getLocalPostconditions().contains(linkedElement)) {
										result.add(target);
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
}
