/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
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
 *  Svyatoslav Kovalsky - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies.LinksLFGraphicalNodeEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;

/**
 * For Papyrus diagrams the default connection view creation command {@link CreateCommand}
 * has to be replaced by {@link CreateViewCommand}, with different implementation of the
 * CreateViewCommand#canExecute() method.
 * <p/>
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=346739 as an root case
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=477440 for a LinkLF case
 */
public class DefaultLinksLFEditPolicy extends LinksLFGraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionAndRelationshipCompleteCommand(CreateConnectionViewAndElementRequest request) {

		// Add parameter (source and target view to the CreateRelationshipRequest
		CreateElementRequestAdapter requestAdapter = request.getConnectionViewAndElementDescriptor().getCreateElementRequestAdapter();
		CreateRelationshipRequest createElementRequest = (CreateRelationshipRequest) requestAdapter.getAdapter(CreateRelationshipRequest.class);

		View sourceView = (View) request.getSourceEditPart().getModel();
		createElementRequest.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW, sourceView);

		View targetView = (View) request.getTargetEditPart().getModel();
		createElementRequest.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW, targetView);
		createElementRequest.setParameter(RequestParameterConstants.EDGE_TARGET_POINT, request.getLocation());
		return super.getConnectionAndRelationshipCompleteCommand(request);
	}

	/**
	 * @return {@link CreateViewCommand} with the
	 *         {@link CreateViewCommand#canExecute()} method tweaked for
	 *         inherited diagrams
	 */
	protected CreateViewCommand createCreateConnectionViewCommand(CreateConnectionViewRequest req) {
		TransactionalEditingDomain editingDomain = getHost().getEditingDomain();
		Diagram diagramView = getHost().getNotationView().getDiagram();
		CreateViewCommand createCommand = new CreateViewCommand(editingDomain, req.getConnectionViewDescriptor(),
				diagramView.getDiagram());
		return createCommand;
	}

	@Override
	public IGraphicalEditPart getHost() {
		return (IGraphicalEditPart) super.getHost();
	}

}
