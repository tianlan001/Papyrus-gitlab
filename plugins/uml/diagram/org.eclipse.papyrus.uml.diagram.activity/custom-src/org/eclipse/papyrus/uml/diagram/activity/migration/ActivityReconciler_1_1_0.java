/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.InsertFloatingLabelFromMapCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * Activity Diagram Reconciler from 1.0.0 to 1.1.0
 *
 * @since 3.0
 */
public class ActivityReconciler_1_1_0 extends DiagramReconciler {

	private final static String AcceptEventActionEditPart_VISUAL_ID = "3063";
	private final static String AcceptEventActionFloatingNameEditPart_VISUAL_ID = "6040";
	private final static String ActivityFinalNodeEditPart_VISUAL_ID = "3005";
	private final static String ActivityFinalNodeFloatingNameEditPart_VISUAL_ID = "5081";
	private final static String ActivityPartitionEditPart_VISUAL_ID = "3067";
	private final static String ActivityPartitionFloatingNameEditPart_VISUAL_ID = "6016";
	private final static String AddStructuralFeatureValueActionEditPart_VISUAL_ID = "3091";
	private final static String AddStructuralFeatureValueActionFloatingNameEditPart_VISUAL_ID = "6019";
	private final static String AddVariableValueActionEditPart_VISUAL_ID = "3099";
	private final static String AddVariableValueActionFloatingNameEditPart_VISUAL_ID = "6018";
	private final static String BroadcastSignalActionEditPart_VISUAL_ID = "3102";
	private final static String BroadcastSignalActionFloatingNameEditPart_VISUAL_ID = "6017";
	private final static String CallBehaviorActionEditPart_VISUAL_ID = "3008";
	private final static String CallBehaviorActionFloatingNameEditPart_VISUAL_ID = "6029";
	private final static String CallOperationActionEditPart_VISUAL_ID = "3010";
	private final static String CallOperationActionFloatingNameEditPart_VISUAL_ID = "6020";
	private final static String CentralBufferNodeEditPart_VISUAL_ID = "3104";
	private final static String CentralBufferNodeFloatingNameEditPart_VISUAL_ID = "6030";
	private final static String ClearAssociationActionEditPart_VISUAL_ID = "3119";
	private final static String ClearAssociationActionFloatingNameEditPart_VISUAL_ID = "5400";
	private final static String ClearStructuralFeatureActionEditPart_VISUAL_ID = "3115";
	private final static String ClearStructuralFeatureActionFloatingNameEditPart_VISUAL_ID = "5396";
	private final static String CreateLinkActionEditPart_VISUAL_ID = "3117";
	private final static String CreateLinkActionFloatingNameEditPart_VISUAL_ID = "5397";
	private final static String CreateLinkObjectActionEditPart_VISUAL_ID = "3198";
	private final static String CreateLinkObjectActionFloatingNameEditPart_VISUAL_ID = "5407";
	private final static String CreateObjectActionEditPart_VISUAL_ID = "3086";
	private final static String CreateObjectActionFloatingNameEditPart_VISUAL_ID = "6024";
	private final static String DataStoreNodeEditPart_VISUAL_ID = "3078";
	private final static String DataStoreNodeFloatingNameEditPart_VISUAL_ID = "6031";
	private final static String DecisionNodeEditPart_VISUAL_ID = "3038";
	private final static String DecisionNodeFloatingNameEditPart_VISUAL_ID = "6036";
	private final static String DestroyLinkActionEditPart_VISUAL_ID = "3118";
	private final static String DestroyLinkActionFloatingNameEditPart_VISUAL_ID = "5399";
	private final static String DestroyObjectActionEditPart_VISUAL_ID = "3095";
	private final static String DestroyObjectActionFloatingNameEditPart_VISUAL_ID = "6022";
	private final static String FlowFinalNodeEditPart_VISUAL_ID = "3006";
	private final static String FlowFinalNodeFloatingNameEditPart_VISUAL_ID = "6035";
	private final static String ForkNodeEditPart_VISUAL_ID = "3040";
	private final static String ForkNodeFloatingNameEditPart_VISUAL_ID = "6038";
	private final static String InitialNodeEditPart_VISUAL_ID = "3004";
	private final static String InitialNodeFloatingNameEditPart_VISUAL_ID = "6034";
	private final static String JoinNodeEditPart_VISUAL_ID = "3041";
	private final static String JoinNodeFloatingNameEditPart_VISUAL_ID = "6039";
	private final static String MergeNodeEditPart_VISUAL_ID = "3039";
	private final static String MergeNodeFloatingNameEditPart_VISUAL_ID = "6037";
	private final static String OpaqueActionEditPart_VISUAL_ID = "3007";
	private final static String OpaqueActionFloatingNameEditPart_VISUAL_ID = "6028";
	private final static String ReadExtentActionEditPart_VISUAL_ID = "3120";
	private final static String ReadExtentActionFloatingNameEditPart_VISUAL_ID = "5402";
	private final static String ReadIsClassifiedObjectActionEditPart_VISUAL_ID = "3122";
	private final static String ReadIsClassifiedObjectActionFloatingNameEditPart_VISUAL_ID = "5403";
	private final static String ReadLinkActionEditPart_VISUAL_ID = "3116";
	private final static String ReadLinkActionFloatingNameEditPart_VISUAL_ID = "5398";
	private final static String ReadSelfActionEditPart_VISUAL_ID = "3081";
	private final static String ReadSelfActionFloatingNameEditPart_VISUAL_ID = "6025";
	private final static String ReadStructuralFeatureActionEditPart_VISUAL_ID = "3088";
	private final static String ReadStructuralFeatureActionFloatingNameEditPart_VISUAL_ID = "6023";
	private final static String ReadVariableActionEditPart_VISUAL_ID = "3097";
	private final static String ReadVariableActionFloatingNameEditPart_VISUAL_ID = "6021";
	private final static String ReclassifyObjectActionEditPart_VISUAL_ID = "3221";
	private final static String ReclassifyObjectActionFloatingNameEditPart_VISUAL_ID = "5401";
	private final static String ReduceActionEditPart_VISUAL_ID = "3123";
	private final static String ReduceActionFloatingNameEditPart_VISUAL_ID = "5404";
	private final static String SendObjectActionEditPart_VISUAL_ID = "3042";
	private final static String SendObjectActionFloatingNameEditPart_VISUAL_ID = "6027";
	private final static String SendSignalActionEditPart_VISUAL_ID = "3052";
	private final static String SendSignalActionFloatingNameEditPart_VISUAL_ID = "6032";
	private final static String StartClassifierBehaviorActionEditPart_VISUAL_ID = "3124";
	private final static String StartClassifierBehaviorActionFloatingNameEditPart_VISUAL_ID = "5405";
	private final static String StartObjectBehavoiurActionEditPart_VISUAL_ID = "3113";
	private final static String StartObjectBehaviorActionFloatingNameEditPart_VISUAL_ID = "5394";
	private final static String TestIdentityActionEditPart_VISUAL_ID = "3114";
	private final static String TestIdentityActionFloatingNameEditPart_VISUAL_ID = "5395";
	private final static String ValueSpecificationActionEditPart_VISUAL_ID = "3076";
	private final static String ValueSpecificationActionFloatingNameEditPart_VISUAL_ID = "6026";

	/**
	 * Gets the reconcile command.
	 *
	 * @param diagram
	 *            the diagram
	 * @return the reconcile command
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler#getReconcileCommand(org.eclipse.gmf.runtime.notation.Diagram)
	 */
	@Override
	public ICommand getReconcileCommand(Diagram diagram) {
		return new InsertFloatingLabelFromMapCommand(diagram, getFloatingLabelMap());
	}

	/**
	 * Gets the floating label map to add.
	 *
	 * @return the floating label map
	 */
	private Map<String, String> getFloatingLabelMap() {
		Map<String, String> map = new HashMap<>();
		map.put(AcceptEventActionEditPart_VISUAL_ID, AcceptEventActionFloatingNameEditPart_VISUAL_ID);
		map.put(ActivityFinalNodeEditPart_VISUAL_ID, ActivityFinalNodeFloatingNameEditPart_VISUAL_ID);
		map.put(ActivityPartitionEditPart_VISUAL_ID, ActivityPartitionFloatingNameEditPart_VISUAL_ID);
		map.put(AddStructuralFeatureValueActionEditPart_VISUAL_ID, AddStructuralFeatureValueActionFloatingNameEditPart_VISUAL_ID);
		map.put(AddVariableValueActionEditPart_VISUAL_ID, AddVariableValueActionFloatingNameEditPart_VISUAL_ID);
		map.put(BroadcastSignalActionEditPart_VISUAL_ID, BroadcastSignalActionFloatingNameEditPart_VISUAL_ID);
		map.put(CallBehaviorActionEditPart_VISUAL_ID, CallBehaviorActionFloatingNameEditPart_VISUAL_ID);
		map.put(CallOperationActionEditPart_VISUAL_ID, CallOperationActionFloatingNameEditPart_VISUAL_ID);
		map.put(CentralBufferNodeEditPart_VISUAL_ID, CentralBufferNodeFloatingNameEditPart_VISUAL_ID);
		map.put(ClearAssociationActionEditPart_VISUAL_ID, ClearAssociationActionFloatingNameEditPart_VISUAL_ID);
		map.put(ClearStructuralFeatureActionEditPart_VISUAL_ID, ClearStructuralFeatureActionFloatingNameEditPart_VISUAL_ID);
		map.put(CreateLinkActionEditPart_VISUAL_ID, CreateLinkActionFloatingNameEditPart_VISUAL_ID);
		map.put(CreateLinkObjectActionEditPart_VISUAL_ID, CreateLinkObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(CreateObjectActionEditPart_VISUAL_ID, CreateObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(DataStoreNodeEditPart_VISUAL_ID, DataStoreNodeFloatingNameEditPart_VISUAL_ID);
		map.put(DecisionNodeEditPart_VISUAL_ID, DecisionNodeFloatingNameEditPart_VISUAL_ID);
		map.put(DestroyLinkActionEditPart_VISUAL_ID, DestroyLinkActionFloatingNameEditPart_VISUAL_ID);
		map.put(DestroyObjectActionEditPart_VISUAL_ID, DestroyObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(FlowFinalNodeEditPart_VISUAL_ID, FlowFinalNodeFloatingNameEditPart_VISUAL_ID);
		map.put(ForkNodeEditPart_VISUAL_ID, ForkNodeFloatingNameEditPart_VISUAL_ID);
		map.put(InitialNodeEditPart_VISUAL_ID, InitialNodeFloatingNameEditPart_VISUAL_ID);
		map.put(JoinNodeEditPart_VISUAL_ID, JoinNodeFloatingNameEditPart_VISUAL_ID);
		map.put(MergeNodeEditPart_VISUAL_ID, MergeNodeFloatingNameEditPart_VISUAL_ID);
		map.put(OpaqueActionEditPart_VISUAL_ID, OpaqueActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadExtentActionEditPart_VISUAL_ID, ReadExtentActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadIsClassifiedObjectActionEditPart_VISUAL_ID, ReadIsClassifiedObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadLinkActionEditPart_VISUAL_ID, ReadLinkActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadSelfActionEditPart_VISUAL_ID, ReadSelfActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadStructuralFeatureActionEditPart_VISUAL_ID, ReadStructuralFeatureActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReadVariableActionEditPart_VISUAL_ID, ReadVariableActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReclassifyObjectActionEditPart_VISUAL_ID, ReclassifyObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(ReduceActionEditPart_VISUAL_ID, ReduceActionFloatingNameEditPart_VISUAL_ID);
		map.put(SendObjectActionEditPart_VISUAL_ID, SendObjectActionFloatingNameEditPart_VISUAL_ID);
		map.put(SendSignalActionEditPart_VISUAL_ID, SendSignalActionFloatingNameEditPart_VISUAL_ID);
		map.put(StartClassifierBehaviorActionEditPart_VISUAL_ID, StartClassifierBehaviorActionFloatingNameEditPart_VISUAL_ID);
		map.put(StartObjectBehavoiurActionEditPart_VISUAL_ID, StartObjectBehaviorActionFloatingNameEditPart_VISUAL_ID);
		map.put(TestIdentityActionEditPart_VISUAL_ID, TestIdentityActionFloatingNameEditPart_VISUAL_ID);
		map.put(ValueSpecificationActionEditPart_VISUAL_ID, ValueSpecificationActionFloatingNameEditPart_VISUAL_ID);

		return map;
	}
}
