/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ReferenceEdgeEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.StereotypePropertyReferenceEdgeCleaningEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice;

/**
 * {@link ReferenceEdgeEditPart} for Stereotype property reference
 *
 * @author Mickaël ADAM
 *
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeEditPart extends ReferenceEdgeEditPart {

	/**
	 * The visual Id.
	 */
	public static final String VISUAL_ID = IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;

	/**
	 * Constructor.
	 */
	public StereotypePropertyReferenceEdgeEditPart(final View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ReferenceEdgeEditPart#createDefaultEditPolicies()
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(StereotypePropertyReferenceEdgeCleaningEditPolicy.EDIT_POLICY_KEY, new StereotypePropertyReferenceEdgeCleaningEditPolicy());
	}

}
