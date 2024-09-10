/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.deployment.custom.edit.policies.itemsemantic;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.deployment.custom.edit.command.BranchDependenctReorientCommand;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyBranchEditPart;

/**
 * this class has been specialized in order to manage reconnection of multidependency
 *
 */
public class CustomDefaultNamedElementItemSemanticEditPolicy extends DefaultSemanticEditPolicy {

	public static final String VISUAL_ID_KEY = "visual_id";

	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		if (request instanceof ReorientRelationshipRequest) {
			if (DependencyBranchEditPart.VISUAL_ID == getVisualID(request)) {
				return new GMFtoGEFCommandWrapper(new BranchDependenctReorientCommand((ReorientRelationshipRequest) request));
			}
		}
		return super.getSemanticCommand(request);
	}

	protected String getVisualID(IEditCommandRequest request) {
		return (String) request.getParameter(VISUAL_ID_KEY);
	}
}
