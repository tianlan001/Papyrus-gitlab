/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies.itemsemantic;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.policies.DependencyBranchItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;

/**
 * this class is used to forbid the creation of a dependency branch on a dependency branch.
 *
 * @since 3.0
 *
 */
public class CustomDependencyBranchSemanticEditPolicy extends DependencyBranchItemSemanticEditPolicy {

	@Override
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {

		if (UMLElementTypes.Dependency_BranchEdge == req.getElementType()) {
			return UnexecutableCommand.INSTANCE;
		}

		return super.getStartCreateRelationshipCommand(req);
	}

}
