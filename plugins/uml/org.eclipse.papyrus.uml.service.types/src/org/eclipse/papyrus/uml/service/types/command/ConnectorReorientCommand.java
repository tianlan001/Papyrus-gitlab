/*****************************************************************************
 * Copyright (c) 2011, 2017 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 517462
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterUtils;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * <pre>
 * Re-orient command for binary {@link Connector}. This command is able to manage graphical reorient action, but also semantic reorient action
 * </pre>
 */
public class ConnectorReorientCommand extends ConnectorReorientSemanticCommand {

	/**
	 * the new end view
	 */
	private View newEndView;

	/**
	 * the opposite end view
	 */
	private View oppositeEndView;

	/**
	 * the reoriented edge
	 */
	private Edge reorientedEdgeView;

	// private ConnectorUtils utils = new ConnectorUtils();

	/**
	 * Constructor.
	 */
	public ConnectorReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	/**
	 * 
	 * Constructor.
	 * 
	 * @param request
	 */
	public ConnectorReorientCommand(ReorientReferenceRelationshipRequest request) {
		super(request);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.command.ConnectorReorientSemanticCommand#initFields()
	 * 
	 */
	@Override
	protected void initFields() {
		super.initFields();
		reorientedEdgeView = RequestParameterUtils.getReconnectedEdge(getRequest());
		newEndView = RequestParameterUtils.getReconnectedEndView(getRequest());
		if (this.reorientedEdgeView != null) {
			oppositeEndView = (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) ? reorientedEdgeView.getTarget() : reorientedEdgeView.getSource();
		} else {
			oppositeEndView = null;
		}
		if (getNewPartWithPort() == null) {
			setNewPartWithPort(findNewPartWithPort());
		}
		if (getOppositePartWithPort() == null) {
			setOppositePartWithPort(findNewOppositePartWithPort());
		}
	}

	/**
	 * Get the new {@link Connector} end graphical parent.
	 * 
	 * @return the new {@link Connector} end graphical parent.
	 */
	protected Element getEndParent(View endView) {
		if (endView != null) {
			View parentView = ViewUtil.getContainerView(endView);
			EObject parent = (parentView == null) ? null : parentView.getElement();
			return (parent instanceof Element) ? (Element) parent : null;
		}
		return null;
	}


	/**
	 * Get the new {@link Connector} end part with port.
	 * 
	 * @return the new {@link Connector} end part with port.
	 */
	protected Property findNewPartWithPort() {
		Property partWithPort = null;
		Element newEndParent = getEndParent(this.newEndView);
		if (getNewEnd() instanceof Port) {
			// Only look for PartWithPort if the role is a Port.

			if ((newEndParent != null) && (newEndParent instanceof Property) && !(newEndParent instanceof Port)) {
				// Only add PartWithPort for assembly (not for delegation)
				if (!EcoreUtil.isAncestor(ViewUtil.getContainerView(this.newEndView), this.oppositeEndView)) {
					partWithPort = (Property) newEndParent;
				}
			}
		}
		return partWithPort;
	}

	/**
	 * Get the new {@link Connector} opposite end part with port.
	 * 
	 * @return the new {@link Connector} opposite end part with port.
	 */
	protected Property findNewOppositePartWithPort() {
		Property partWithPort = null;
		Element oppositeEndParent = getEndParent(this.oppositeEndView);
		if (this.oppositeEndView != null && this.oppositeEndView.getElement() instanceof Port) {
			// Only look for PartWithPort if the role is a Port.

			if ((oppositeEndParent != null) && (oppositeEndParent instanceof Property) && !(oppositeEndParent instanceof Port)) {
				// Only add PartWithPort for assembly (not for delegation)
				if (!EcoreUtil.isAncestor(ViewUtil.getContainerView(this.oppositeEndView), this.newEndView)) {
					partWithPort = (Property) oppositeEndParent;
				}
			}
		}
		return partWithPort;
	}

}
