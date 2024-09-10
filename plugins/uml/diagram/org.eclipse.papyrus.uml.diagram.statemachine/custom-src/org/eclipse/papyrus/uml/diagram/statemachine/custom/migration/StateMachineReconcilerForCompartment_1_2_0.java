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
 *   CEA LIST - Initial API and implementation
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.statemachine.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 *
 * @since 3.1
 */
public class StateMachineReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String RegionCompartmentEditPart_VISUAL_ID = "3002";
	private static final String StateMachineCompartmentEditPart_VISUAL_ID = "2002";
	private static final String StateCompartmentEditPart_VISUAL_ID = "6002";

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			RegionCompartmentEditPart_VISUAL_ID,
			StateMachineCompartmentEditPart_VISUAL_ID,
			StateCompartmentEditPart_VISUAL_ID);

	/**
	 * Gets the compartments visual id.
	 *
	 * @return the compartments visual id
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment#getCompartmentsVisualID()
	 */
	@Override
	public List<String> getCompartmentsVisualID() {
		return compartmentsVisualID;
	}

}
