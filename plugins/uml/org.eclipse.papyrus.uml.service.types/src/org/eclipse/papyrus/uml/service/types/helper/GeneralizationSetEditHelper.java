/*****************************************************************************
 * Copyright (c) 2011-2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.command.GeneralizationSetCreationCommand;
import org.eclipse.papyrus.uml.service.types.command.GeneralizationSetReorientCommand;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Generalization;

/**
 * Edit helper class for binary {@link Association}
 */
public class GeneralizationSetEditHelper extends ElementEditHelper {

	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new GeneralizationSetReorientCommand(req);
	}



	/**
	 * Test if the relationship creation is allowed.
	 * 
	 * @param source
	 *            the relationship source can be null
	 * @param target
	 *            the relationship target can be null
	 * @return true if the creation is allowed
	 */
	protected boolean canCreate(EObject source, EObject target) {
		if ((source != null) && !(source instanceof Generalization)) {
			return false;
		}

		if ((target != null) && !(target instanceof Generalization)) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {

		EObject source = req.getSource();
		EObject target = req.getTarget();

		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);

		if (!noSourceAndTarget && !canCreate(source, target)) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}

		if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			return IdentityCommand.INSTANCE;
		}

		EObject proposedContainer = ((Generalization) source).getNearestPackage();
		req.setContainer(proposedContainer);

		return new GeneralizationSetCreationCommand(req);
	}



}
