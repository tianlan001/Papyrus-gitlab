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
package org.eclipse.papyrus.uml.diagram.activity.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Class Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 *
 * @since 3.0
 */
public class ActivityReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String ActivityActivityParametersCompartmentEditPart_VISUAL_ID = "7001";
	private static final String ActivityActivityPreConditionsCompartmentEditPart_VISUAL_ID = "7002";
	private static final String ActivityActivityPostConditionsCompartmentEditPart_VISUAL_ID = "7003";
	private static final String ActivityActivityContentCompartmentEditPart_VISUAL_ID = "7004";
	private static final String ConditionalNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID = "7008";
	private static final String ExpansionRegionStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID = "7009";
	private static final String LoopNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID = "7010";
	private static final String SequenceNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID = "7012";
	private static final String StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID = "7005";
	private static final String ActivityPartitionActivityPartitionContentCompartmentEditPart_VISUAL_ID = "7006";
	private static final String InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart_VISUAL_ID = "7007";
	private static final String ActivityCNParametersCompartmentEditPart_VISUAL_ID = "7014";
	private static final String ActivityCNPreConditionsCompartmentEditPart_VISUAL_ID = "7015";
	private static final String ActivityCNPostConditionsCompartmentEditPart_VISUAL_ID = "7016";
	private static final String ActivityCNContentCompartmentEditPart_VISUAL_ID = "7013";

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			ActivityActivityParametersCompartmentEditPart_VISUAL_ID,
			ActivityActivityPreConditionsCompartmentEditPart_VISUAL_ID,
			ActivityActivityPostConditionsCompartmentEditPart_VISUAL_ID,
			ActivityActivityContentCompartmentEditPart_VISUAL_ID,
			ConditionalNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID,
			ExpansionRegionStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID,
			LoopNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID,
			SequenceNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID,
			StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart_VISUAL_ID,
			ActivityPartitionActivityPartitionContentCompartmentEditPart_VISUAL_ID,
			InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart_VISUAL_ID,
			ActivityCNParametersCompartmentEditPart_VISUAL_ID,
			ActivityCNPreConditionsCompartmentEditPart_VISUAL_ID,
			ActivityCNPostConditionsCompartmentEditPart_VISUAL_ID,
			ActivityCNContentCompartmentEditPart_VISUAL_ID);

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
