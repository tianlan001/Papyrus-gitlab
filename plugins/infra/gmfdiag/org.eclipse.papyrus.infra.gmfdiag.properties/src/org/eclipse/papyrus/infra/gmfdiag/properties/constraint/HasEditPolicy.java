/*****************************************************************************
 * Copyright (c) 2011, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.constraint;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;

import com.google.common.base.Objects;

/**
 *
 * A constraint to test whether an object has the given EditPolicy or not
 *
 * @author Camille Letavernier
 *
 */
public class HasEditPolicy extends AbstractConstraint {

	/**
	 * The ID of the edit policy to look for
	 */
	protected String editPolicyID;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean match(Object selection) {
		if (editPolicyID != null && selection instanceof GraphicalEditPart) {
			return ((GraphicalEditPart) selection).getEditPolicy(editPolicyID) != null;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setDescriptor(SimpleConstraint descriptor) {
		editPolicyID = getValue("editPolicy"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean equivalent(Constraint constraint) {
		if (constraint == null) {
			return false;
		}

		return constraint instanceof HasEditPolicy && Objects.equal(((HasEditPolicy) constraint).editPolicyID, editPolicyID);
	}

}
