/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Activity Diagram odesign.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class MappingTypes {

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String ACCEPT_CALL_ACTION_NODE_TYPE = "AD_AcceptCallAction"; //$NON-NLS-1$

	public static final String ACCEPT_EVENT_ACTION_NODE_TYPE = "AD_AcceptEventAction"; //$NON-NLS-1$

	public static final String ACTION_INPUT_PIN_NODE_TYPE = "AD_ActionInputPin"; //$NON-NLS-1$

	public static final String ACTIVITY_FINAL_NODE_NODE_TYPE = "AD_ActivityFinalNode"; //$NON-NLS-1$

	public static final String ACTIVITY_NODE_TYPE = "AD_Activity"; //$NON-NLS-1$

	public static final String ACTIVITY_PARAMETER_NODE_NODE_TYPE = "AD_ActivityParameterNode"; //$NON-NLS-1$

	public static final String ACTIVITY_PARTITION_NODE_TYPE = "AD_ActivityPartition"; //$NON-NLS-1$

	public static final String ADD_STRUCTURAL_FEATURE_VALUE_ACTION_NODE_TYPE = "AD_AddStructuralFeatureValueAction"; //$NON-NLS-1$

	public static final String BROADCAST_SIGNAL_ACTION_NODE_TYPE = "AD_BroadcastSignalAction"; //$NON-NLS-1$

	public static final String CALL_BEHAVIOR_ACTION_NODE_TYPE = "AD_CallBehaviorAction"; //$NON-NLS-1$

	public static final String CALL_OPERATION_ACTION_NODE_TYPE = "AD_CallOperationAction"; //$NON-NLS-1$

	public static final String CLEAR_ASSOCIATION_ACTION_NODE_TYPE = "AD_ClearAssociationAction"; //$NON-NLS-1$

	public static final String CLEAR_STRUCTURAL_FEATURE_ACTION_NODE_TYPE = "AD_ClearStructuralFeatureAction"; //$NON-NLS-1$

	public static final String COMMENT_NODE_TYPE = "AD_Comment"; //$NON-NLS-1$

	public static final String CONDITIONAL_NODE_NODE_TYPE = "AD_ConditionalNode"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "AD_Constraint"; //$NON-NLS-1$

	public static final String CREATE_OBJECT_ACTION_NODE_TYPE = "AD_CreateObjectAction"; //$NON-NLS-1$

	public static final String DECISION_NODE_COMMENT_NODE_TYPE = "AD_DecisionNodeComment"; //$NON-NLS-1$

	public static final String DECISION_NODE_NODE_TYPE = "AD_DecisionNode"; //$NON-NLS-1$

	public static final String DESTROY_OBJECT_ACTION_NODE_TYPE = "AD_DestroyObjectAction"; //$NON-NLS-1$

	public static final String EXPANSION_NODE_NODE_TYPE = "AD_ExpansionNode"; //$NON-NLS-1$

	public static final String EXPANSION_REGION_NODE_TYPE = "AD_ExpansionRegion"; //$NON-NLS-1$

	public static final String FLOW_FINAL_NODE_NODE_TYPE = "AD_FlowFinalNode"; //$NON-NLS-1$

	public static final String FORK_NODE_NODE_TYPE = "AD_ForkNode"; //$NON-NLS-1$

	public static final String INITIAL_NODE_NODE_TYPE = "AD_InitialNode"; //$NON-NLS-1$

	public static final String INPUT_PIN_NODE_TYPE = "AD_InputPin"; //$NON-NLS-1$

	public static final String INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE = "AD_InterruptibleActivityRegion"; //$NON-NLS-1$

	public static final String JOIN_NODE_NODE_TYPE = "AD_JoinNode"; //$NON-NLS-1$

	public static final String LOOP_NODE_NODE_TYPE = "AD_LoopNode"; //$NON-NLS-1$

	public static final String MERGE_NODE_NODE_TYPE = "AD_MergeNode"; //$NON-NLS-1$

	public static final String OPAQUE_ACTION_NODE_TYPE = "AD_OpaqueAction"; //$NON-NLS-1$

	public static final String OUTPUT_PIN_NODE_TYPE = "AD_OutputPin"; //$NON-NLS-1$

	public static final String READ_EXTENT_ACTION_NODE_TYPE = "AD_ReadExtentAction"; //$NON-NLS-1$

	public static final String READ_IS_CLASSIFIED_OBJECT_ACTION_NODE_TYPE = "AD_ReadIsClassifiedObjectAction"; //$NON-NLS-1$

	public static final String READ_SELF_ACTION_NODE_TYPE = "AD_ReadSelfAction"; //$NON-NLS-1$

	public static final String READ_STRUCTURAL_FEATURE_ACTION_NODE_TYPE = "AD_ReadStructuralFeatureAction"; //$NON-NLS-1$

	public static final String RECLASSIFY_OBJECT_ACTION_NODE_TYPE = "AD_ReclassifyObjectAction"; //$NON-NLS-1$

	public static final String REDUCE_ACTION_NODE_TYPE = "AD_ReduceAction"; //$NON-NLS-1$

	public static final String SEND_OBJECT_ACTION_NODE_TYPE = "AD_SendObjectAction"; //$NON-NLS-1$

	public static final String SEND_SIGNAL_ACTION_NODE_TYPE = "AD_SendSignalAction"; //$NON-NLS-1$

	public static final String SEQUENCE_NODE_NODE_TYPE = "AD_SequenceNode"; //$NON-NLS-1$

	public static final String START_CLASSIFIER_BEHAVIOR_ACTION_NODE_TYPE = "AD_StartClassifierBehaviorAction"; //$NON-NLS-1$

	public static final String START_OBJECT_BEHAVIOR_ACTION_NODE_TYPE = "AD_StartObjectBehaviorAction"; //$NON-NLS-1$

	public static final String STRUCTURED_ACTIVITY_NODE_NODE_TYPE = "AD_StructuredActivityNode"; //$NON-NLS-1$

	public static final String SUB_ACTIVITY_NODE_TYPE = "AD_SubActivity"; //$NON-NLS-1$

	public static final String TEST_IDENTITY_ACTION_NODE_TYPE = "AD_TestIdentityAction"; //$NON-NLS-1$

	public static final String VALUE_PIN_NODE_TYPE = "AD_ValuePin"; //$NON-NLS-1$

	public static final String VALUE_SPECIFICATION_ACTION_NODE_TYPE = "AD_ValueSpecificationAction"; //$NON-NLS-1$


	/*----------------------------------------------The Compartments mapping IDs----------------------------------------------*/

	public static final String ACTIVITY_AD_COMPARTMENTS_TYPE = "AD_ActivityContentCompartment"; //$NON-NLS-1$

	public static final String ACTIVITY_PARTITION_AD_COMPARTMENTS_TYPE = "AD_ActivityPartitionContentCompartment"; //$NON-NLS-1$

	public static final String CONDITIONAL_NODE_AD_COMPARTMENTS_TYPE = "AD_ConditionalNodeContentCompartment"; //$NON-NLS-1$

	public static final String EXPANSION_REGION_AD_COMPARTMENTS_TYPE = "AD_ExpansionRegionContentCompartment"; //$NON-NLS-1$

	public static final String INTERRUPTIBLE_ACTIVITY_REGION_AD_COMPARTMENTS_TYPE = "AD_InterruptibleActivityRegionContentCompartment"; //$NON-NLS-1$

	public static final String LOOP_NODE_AD_COMPARTMENTS_TYPE = "AD_LoopNodeContentCompartment"; //$NON-NLS-1$

	public static final String SEQUENCE_NODE_AD_COMPARTMENTS_TYPE = "AD_SequenceNodeContentCompartment"; //$NON-NLS-1$

	public static final String STRUCTURED_ACTIVITY_NODE_AD_COMPARTMENTS_TYPE = "AD_StructuredActivityNodeContentCompartment"; //$NON-NLS-1$

	public static final String SUB_ACTIVITY_AD_COMPARTMENTS_TYPE = "AD_SubActivityContentCompartment"; //$NON-NLS-1$


	/*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String CONTRL_FLOW_EDGE_TYPE = "AD_ControlFlow"; //$NON-NLS-1$

	public static final String CONTROL_FLOW_EDGE_TYPE = "AD_ControlFlow"; //$NON-NLS-1$

	public static final String DECISION_NODE_TO_DECISION_NODE_COMMENT_EDGE_TYPE = "AD_DecisionNodeToDecisionNodeComment"; //$NON-NLS-1$

	public static final String LINK_EDGE_TYPE = "AD_Link"; //$NON-NLS-1$

	public static final String OBJECT_FLOW_EDGE_TYPE = "AD_ObjectFlow"; //$NON-NLS-1$

	private MappingTypes() {
		// to prevent instantiation
	}

	/**
	 * Returns whether the given Mapping type is a border node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a border node, false otherwise.
	 */
	public static boolean isBorderNode(String mappingType) {
		return List.of(ACTIVITY_PARAMETER_NODE_NODE_TYPE,
				EXPANSION_NODE_NODE_TYPE,
				ACTION_INPUT_PIN_NODE_TYPE,
				INPUT_PIN_NODE_TYPE,
				INPUT_PIN_NODE_TYPE,
				OUTPUT_PIN_NODE_TYPE,
				VALUE_PIN_NODE_TYPE)
				.contains(mappingType);
	}

	/**
	 * Returns whether the given Mapping type is a node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a node, false otherwise.
	 */
	public static boolean isNode(String mappingType) {
		return List.of(ACCEPT_EVENT_ACTION_NODE_TYPE,
				ACTIVITY_FINAL_NODE_NODE_TYPE,
				COMMENT_NODE_TYPE,
				CONSTRAINT_NODE_TYPE,
				DECISION_NODE_NODE_TYPE,
				DECISION_NODE_COMMENT_NODE_TYPE,
				FLOW_FINAL_NODE_NODE_TYPE,
				FORK_NODE_NODE_TYPE,
				INITIAL_NODE_NODE_TYPE,
				JOIN_NODE_NODE_TYPE,
				MERGE_NODE_NODE_TYPE,
				SEND_SIGNAL_ACTION_NODE_TYPE)
				.contains(mappingType);
	}
}
