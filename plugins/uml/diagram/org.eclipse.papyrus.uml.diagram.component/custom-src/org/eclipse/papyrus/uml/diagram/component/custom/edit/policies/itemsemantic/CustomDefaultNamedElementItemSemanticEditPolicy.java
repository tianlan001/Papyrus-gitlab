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
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies.itemsemantic;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.command.BranchDependenctReorientCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.policies.DefaultNamedElementItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyBranchEditPart;

/**
 * this class has been specialized in order to manage reconnection of multidependency
 *
 * @since 3.0
 *
 */
public class CustomDefaultNamedElementItemSemanticEditPolicy extends DefaultNamedElementItemSemanticEditPolicy {

	@Override
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case DependencyBranchEditPart.VISUAL_ID:
			return getGEFWrapper(new BranchDependenctReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}
}
