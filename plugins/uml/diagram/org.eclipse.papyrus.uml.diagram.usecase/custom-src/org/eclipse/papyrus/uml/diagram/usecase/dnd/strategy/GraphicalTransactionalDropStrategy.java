/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST)  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.dnd.strategy;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RefreshConnectionsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideElementsRequest;

//FIXME should be moved to the plugin org.eclipse.papyrus.infra.gmfdiag.dnd
/**
 * An abstract strategy with a basic preimplemented graphical command management.
 *
 * @author Francois Le Fevre
 */
public abstract class GraphicalTransactionalDropStrategy extends TransactionalDropStrategy {

	/**
	 *
	 * @param request
	 * @param targetEditPart
	 * @return A command to edit the graphical view
	 */
	protected Command getGraphicalCommand(Request request, EditPart targetEditPart) {
		DropObjectsRequest dropRequest = getDropObjectsRequest(request);
		if (dropRequest == null) {
			return null;
		}

		// Create a view request from the drop request and then forward getting
		// the command for that.
		List<CreateViewRequest.ViewDescriptor> viewDescriptors = getViewDescriptors(dropRequest, targetEditPart);
		if (viewDescriptors.isEmpty()) {
			return null;
		}

		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());

		Command createCommand = targetEditPart.getCommand(createViewRequest);

		if (createCommand != null) {
			List<?> result = (List<?>) createViewRequest.getNewObject();
			dropRequest.setResult(result);

			RefreshConnectionsRequest refreshRequest = new RefreshConnectionsRequest(result);
			Command refreshCommand = targetEditPart.getCommand(refreshRequest);

			ArrangeRequest arrangeRequest = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
			arrangeRequest.setViewAdaptersToArrange(result);
			Command arrangeCommand = targetEditPart.getCommand(arrangeRequest);

			CompoundCommand cc = new CompoundCommand(createCommand.getLabel());

			cc.add(createCommand.chain(refreshCommand));
			cc.add(arrangeCommand);

			for (Object object : dropRequest.getObjects()) {
				if (object instanceof EditPart) {

					EditPart editPart = (EditPart) object;
					ShowHideElementsRequest destroyViewRequest = new ShowHideElementsRequest(editPart);
					Command destroyViewCommand = editPart.getCommand(destroyViewRequest);
					if (destroyViewCommand != null) {
						cc.add(destroyViewCommand);
					}
				}
			}

			return cc;
		}

		return null;
	}

	protected abstract List<CreateViewRequest.ViewDescriptor> getViewDescriptors(DropObjectsRequest dropRequest, EditPart targetEditPart);
}
