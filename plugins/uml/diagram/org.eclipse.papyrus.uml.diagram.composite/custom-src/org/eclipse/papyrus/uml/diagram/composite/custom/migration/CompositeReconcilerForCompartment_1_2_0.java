/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 * @since 3.0
 */
public class CompositeReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String DataTypeAttributeCompartmentEditPart_VISUAL_ID = "7033"; //$NON-NLS-1$
	private static final String DataTypeOperationCompartmentEditPart_VISUAL_ID = "7034"; //$NON-NLS-1$
	private static final String DataTypeAttributeCompartmentEditPartCN_VISUAL_ID = "7036"; //$NON-NLS-1$
	private static final String DataTypeOperationCompartmentEditPartCN_VISUAL_ID = "7037"; //$NON-NLS-1$
	private static final String EnumerationEnumerationLiteralCompartmentEditPart_VISUAL_ID = "7048"; //$NON-NLS-1$
	private static final String EnumerationEnumerationLiteralCompartmentEditPartCN_VISUAL_ID = "7049"; //$NON-NLS-1$
	private static final String ActivityCompositeCompartmentEditPartCN_VISUAL_ID = "7050"; //$NON-NLS-1$
	private static final String InteractionCompositeCompartmentEditPartCN_VISUAL_ID = "7051"; //$NON-NLS-1$
	private static final String ProtocolStateMachineCompositeCompartmentEditPartCN_VISUAL_ID = "7052"; //$NON-NLS-1$
	private static final String StateMachineCompositeCompartmentEditPartCN_VISUAL_ID = "7053"; //$NON-NLS-1$
	private static final String FunctionBehaviorCompositeCompartmentEditPartCN_VISUAL_ID = "7054"; //$NON-NLS-1$
	private static final String OpaqueBehaviorCompositeCompartmentEditPartCN_VISUAL_ID = "7055"; //$NON-NLS-1$
	private static final String ComponentCompositeCompartmentEditPartCN_VISUAL_ID = "7056"; //$NON-NLS-1$
	private static final String DeviceCompositeCompartmentEditPartCN_VISUAL_ID = "7057"; //$NON-NLS-1$
	private static final String ExecutionEnvironmentCompositeCompartmentEditPartCN_VISUAL_ID = "7058"; //$NON-NLS-1$
	private static final String NodeCompositeCompartmentEditPartCN_VISUAL_ID = "7059"; //$NON-NLS-1$
	private static final String ClassCompositeCompartmentEditPartCN_VISUAL_ID = "7060"; //$NON-NLS-1$
	private static final String CollaborationCompositeCompartmentEditPartCN_VISUAL_ID = "7061"; //$NON-NLS-1$
	private static final String ActivityCompositeCompartmentEditPart_VISUAL_ID = "7063"; //$NON-NLS-1$
	private static final String InteractionCompositeCompartmentEditPart_VISUAL_ID = "7064"; //$NON-NLS-1$
	private static final String ProtocolStateMachineCompositeCompartmentEditPart_VISUAL_ID = "7065"; //$NON-NLS-1$
	private static final String StateMachineCompositeCompartmentEditPart_VISUAL_ID = "7066"; //$NON-NLS-1$
	private static final String FunctionBehaviorCompositeCompartmentEditPart_VISUAL_ID = "7067"; //$NON-NLS-1$
	private static final String OpaqueBehaviorCompositeCompartmentEditPart_VISUAL_ID = "7068"; //$NON-NLS-1$
	private static final String ComponentCompositeCompartmentEditPart_VISUAL_ID = "7069"; //$NON-NLS-1$
	private static final String DeviceCompositeCompartmentEditPart_VISUAL_ID = "7070"; //$NON-NLS-1$
	private static final String ExecutionEnvironmentCompositeCompartmentEditPart_VISUAL_ID = "7071"; //$NON-NLS-1$
	private static final String NodeCompositeCompartmentEditPart_VISUAL_ID = "7072"; //$NON-NLS-1$
	private static final String ClassCompositeCompartmentEditPart_VISUAL_ID = "7073"; //$NON-NLS-1$
	private static final String CollaborationCompositeCompartmentEditPart_VISUAL_ID = "7075"; //$NON-NLS-1$
	private static final String PropertyPartCompartmentEditPartCN_VISUAL_ID = "7077"; //$NON-NLS-1$

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			DataTypeAttributeCompartmentEditPart_VISUAL_ID,
			DataTypeOperationCompartmentEditPart_VISUAL_ID,
			DataTypeAttributeCompartmentEditPartCN_VISUAL_ID,
			DataTypeOperationCompartmentEditPartCN_VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPart_VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPartCN_VISUAL_ID,
			ActivityCompositeCompartmentEditPartCN_VISUAL_ID,
			InteractionCompositeCompartmentEditPartCN_VISUAL_ID,
			ProtocolStateMachineCompositeCompartmentEditPartCN_VISUAL_ID,
			StateMachineCompositeCompartmentEditPartCN_VISUAL_ID,
			FunctionBehaviorCompositeCompartmentEditPartCN_VISUAL_ID,
			OpaqueBehaviorCompositeCompartmentEditPartCN_VISUAL_ID,
			ComponentCompositeCompartmentEditPartCN_VISUAL_ID,
			DeviceCompositeCompartmentEditPartCN_VISUAL_ID,
			ExecutionEnvironmentCompositeCompartmentEditPartCN_VISUAL_ID,
			NodeCompositeCompartmentEditPartCN_VISUAL_ID,
			ClassCompositeCompartmentEditPartCN_VISUAL_ID,
			CollaborationCompositeCompartmentEditPartCN_VISUAL_ID,
			ActivityCompositeCompartmentEditPart_VISUAL_ID,
			InteractionCompositeCompartmentEditPart_VISUAL_ID,
			ProtocolStateMachineCompositeCompartmentEditPart_VISUAL_ID,
			StateMachineCompositeCompartmentEditPart_VISUAL_ID,
			FunctionBehaviorCompositeCompartmentEditPart_VISUAL_ID,
			OpaqueBehaviorCompositeCompartmentEditPart_VISUAL_ID,
			ComponentCompositeCompartmentEditPart_VISUAL_ID,
			DeviceCompositeCompartmentEditPart_VISUAL_ID,
			ExecutionEnvironmentCompositeCompartmentEditPart_VISUAL_ID,
			NodeCompositeCompartmentEditPart_VISUAL_ID,
			ClassCompositeCompartmentEditPart_VISUAL_ID,
			CollaborationCompositeCompartmentEditPart_VISUAL_ID,
			PropertyPartCompartmentEditPartCN_VISUAL_ID);

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
