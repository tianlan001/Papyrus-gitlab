/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add missing edit Policy
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideClassifierContentsEditPolicy;

/**
 * Abstract EditPart for UseCase nodes.
 */
public abstract class UseCaseNodeEditPart extends RoundedCompartmentEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public UseCaseNodeEditPart(final View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Add missing edit policy to show/hide contents (Bug 489118)
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#createDefaultEditPolicies()
	 *
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(ShowHideClassifierContentsEditPolicy.SHOW_HIDE_CLASSIFIER_CONTENTS_POLICY, new ShowHideClassifierContentsEditPolicy());
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultIsOvalValue()
	 *
	 * @return The default value for isOval property.
	 */
	@Override
	protected boolean getDefaultIsOvalValue() {
		return true;
	}

}
