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
package org.eclipse.papyrus.uml.diagram.timing.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 *
 * @since 1.3
 */
public class TimingReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String InteractionCompartmentEditPartTN_VISUAL_ID = "5";
	private static final String FullLifelineStateDefinitionCompartmentEditPartCN_VISUAL_ID = "7";
	private static final String FullLifelineTimelineCompartmentEditPartCN_VISUAL_ID = "8";
	private static final String CompactLifelineCompartmentEditPartCN_VISUAL_ID = "23";
	private static final String TimeRulerCompartmentEditPartCN_VISUAL_ID = "29";
	private static final String FullLifelineTimeRulerCompartmentEditPartCN_VISUAL_ID = "82";
	private static final String CompactLifelineTimeRulerCompartmentEditPartCN_VISUAL_ID = "83";
	private static final String FreeTimeRulerCompartmentEditPart_VISUAL_ID = "80";
	private static final String LinearTimeRulerCompartmentEditPart_VISUAL_ID = "81";

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			InteractionCompartmentEditPartTN_VISUAL_ID,
			FullLifelineStateDefinitionCompartmentEditPartCN_VISUAL_ID,
			FullLifelineTimelineCompartmentEditPartCN_VISUAL_ID,
			CompactLifelineCompartmentEditPartCN_VISUAL_ID,
			TimeRulerCompartmentEditPartCN_VISUAL_ID,
			FullLifelineTimeRulerCompartmentEditPartCN_VISUAL_ID,
			CompactLifelineTimeRulerCompartmentEditPartCN_VISUAL_ID,
			FreeTimeRulerCompartmentEditPart_VISUAL_ID,
			LinearTimeRulerCompartmentEditPart_VISUAL_ID);

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
