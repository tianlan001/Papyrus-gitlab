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
 *   Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 419357
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.helper.CreateOrShowExistingElementHelper;

/**
 * <pre>
 * Custom semantic edit policy that replace DefaultSemanticEditPolicy
 * in order to manage the creation of each links.
 * </pre>
 */
public class CustomDefaultSemanticEditPolicy extends DefaultSemanticEditPolicy {

	private CreateOrShowExistingElementHelper existingElementHelper = new CreateOrShowExistingElementHelper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getCreateRelationshipCommand(final CreateRelationshipRequest req) {
		Command defaultCommand = super.getCreateRelationshipCommand(req);
		if ((defaultCommand.canExecute()) && (null != req.getSource()) && (null != req.getTarget())) {
			final IElementType elementType = req.getElementType();
			defaultCommand = this.existingElementHelper.getCreateOrRestoreElementCommand(req, defaultCommand, elementType);
		}
		return defaultCommand;
	}

}
