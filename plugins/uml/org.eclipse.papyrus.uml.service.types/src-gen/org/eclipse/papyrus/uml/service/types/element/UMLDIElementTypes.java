package org.eclipse.papyrus.uml.service.types.element;

/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
import org.eclipse.gmf.runtime.emf.type.core.AbstractElementTypeEnumerator;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

public class UMLDIElementTypes extends AbstractElementTypeEnumerator {

	public static final IHintedType ABSTRACTION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Abstraction_Edge"); //$NON-NLS-1$

	public static final IHintedType ABSTRACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Abstraction_Shape"); //$NON-NLS-1$

	public static final IHintedType ABSTRACTION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Abstraction_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ACCEPT_CALL_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AcceptCallAction_Shape"); //$NON-NLS-1$

	public static final IHintedType ACCEPT_EVENT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AcceptEventAction_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTION_EXECUTION_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionExecutionSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTION_EXECUTION_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionExecutionSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ACCEPT_CALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AcceptCallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ACCEPT_EVENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AcceptEventActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_AddVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_BROADCAST_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_BROADCAST_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_BroadcastSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CALL_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CALL_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CallBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CALL_OPERATION_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CallOperationActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CALL_OPERATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CallOperationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CALL_OPERATION_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CallOperationActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CLEAR_ASSOCIATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ClearAssociationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CLEAR_ASSOCIATION_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ClearAssociationActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ClearStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CLEAR_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ClearVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CONDITIONAL_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ConditionalNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CREATE_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CreateLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CREATE_LINK_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_CREATE_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_CreateObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_DESTROY_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_DestroyLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_DESTROY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_DestroyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_DESTROY_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_DestroyObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_EXPANSION_REGION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ExpansionRegionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_LOOP_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_LoopNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_LOOP_NODE_VARIABLE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_LoopNodeVariableInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_OPAQUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_OpaqueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_RAISE_EXCEPTION_ACTION_EXCEPTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RaiseExceptionActionExceptionShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_RAISE_EXCEPTION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RaiseExceptionActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_EXTENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadExtentActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadIsClassifiedObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_LINK_OBJECT_END_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkObjectEndActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_LINK_OBJECT_END_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkObjectEndActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkObjectEndQualifierActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkObjectEndQualifierActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_SELF_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadSelfActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_READ_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReadVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_RECLASSIFY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReclassifyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_RECLASSIFY_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REDUCE_ACTION_COLLECTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReduceActionCollectionShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REDUCE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReduceActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveStructuralFeatureValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveVariableValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_RemoveVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REPLY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReplyActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REPLY_ACTION_REPLY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReplyActionReplyShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_REPLY_ACTION_RETURN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ReplyActionReturnShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_OBJECT_ACTION_REQUEST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendObjectActionRequestShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEND_SIGNAL_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SendSignalActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SEQUENCE_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_SequenceNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StartClassifierBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StartObjectBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_STRUCTURED_ACTIVITY_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_StructuredActivityNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_TEST_IDENTITY_ACTION_FIRST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_TestIdentityActionFirstShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_TEST_IDENTITY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_TestIdentityActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_TEST_IDENTITY_ACTION_SECOND_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_TestIdentityActionSecondShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_UNMARSHALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_UnmarshallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_UNMARSHALL_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_UnmarshallActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_INPUT_PIN_VALUE_SPECIFICATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActionInputPin_ValueSpecificationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType ACTION_LOCAL_POSTCONDITION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Action_LocalPostconditionEdge"); //$NON-NLS-1$

	public static final IHintedType ACTION_LOCAL_PRECONDITION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Action_LocalPreconditionEdge"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_FINAL_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActivityFinalNode_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_PARAMETER_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActivityParameterNode_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_PARTITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActivityPartition_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_PARTITION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActivityPartition_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ACTOR_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTOR_CLASSIFIER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_ClassifierShape"); //$NON-NLS-1$

	public static final IHintedType ACTOR_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTOR_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTOR_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ACTOR_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_Shape"); //$NON-NLS-1$

	public static final IHintedType ACTOR_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType ACTOR_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Actor_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ADD_STRUCTURAL_FEATURE_VALUE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AddStructuralFeatureValueAction_Shape"); //$NON-NLS-1$

	public static final IHintedType ADD_VARIABLE_VALUE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AddVariableValueAction_Shape"); //$NON-NLS-1$

	public static final IHintedType ANY_RECEIVE_EVENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AnyReceiveEvent_Shape"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_DEPLOYED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_DeployedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_NESTED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_NestedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_Shape"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_SHAPE_ACN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_Shape_ACN"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType ARTIFACT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Artifact_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_CLASS_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationClass_Edge"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_CLASS_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationClass_Shape"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_CLASS_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationClass_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_CLASS_TETHER_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationClass_TetherEdge"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_BRANCH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Association_BranchEdge"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Association_Edge"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_NON_DIRECTED_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationNonDirected_Edge"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_DIRECTED_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationDirected_Edge"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Association_Shape"); //$NON-NLS-1$

	public static final IHintedType ASSOCIATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Association_Shape_CN"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_COMPOSITE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationComposite_Edge"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_COMPOSITE_DIRECTED_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationCompositeDirected_Edge"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_SHARED_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationShared_Edge"); //$NON-NLS-1$
	
	public static final IHintedType ASSOCIATION_SHARED_DIRECTED_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.AssociationSharedDirected_Edge"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_EXECUTION_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.BehaviorExecutionSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_EXECUTION_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.BehaviorExecutionSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_DO_ACTIVITY_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Behavior_DoActivityBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_ENTRY_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Behavior_EntryBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_EXIT_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Behavior_ExitBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType BEHAVIOR_INTERNAL_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Behavior_InternalBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType BROADCAST_SIGNAL_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.BroadcastSignalAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CALL_BEHAVIOR_ACTION_BASE_TYPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallBehaviorAction_BaseType"); //$NON-NLS-1$

	public static final IHintedType CALL_BEHAVIOR_ACTION_INTERACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallBehaviorAction_InteractionShape"); //$NON-NLS-1$

	public static final IHintedType CALL_BEHAVIOR_ACTION_INTERACTION_USE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallBehaviorAction_InteractionUseShape"); //$NON-NLS-1$

	public static final IHintedType CALL_BEHAVIOR_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallBehaviorAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CALL_EVENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallEvent_Shape"); //$NON-NLS-1$

	public static final IHintedType CALL_OPERATION_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallOperationAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CENTRAL_BUFFER_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CentralBufferNode_Shape"); //$NON-NLS-1$

	public static final IHintedType CHANGE_EVENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ChangeEvent_Shape"); //$NON-NLS-1$

	public static final IHintedType CLASS_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType CLASS_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType CLASS_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType CLASS_METACLASS_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_MetaclassShape"); //$NON-NLS-1$

	public static final IHintedType CLASS_METACLASS_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_MetaclassShape_CN"); //$NON-NLS-1$

	public static final IHintedType CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType CLASS_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_Shape"); //$NON-NLS-1$

	public static final IHintedType CLASS_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Class_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CLASSIFIER_TEMPLATE_PARAMETER_TEMPLATE_PARAMETER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ClassifierTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$

	public static final IHintedType CLASSIFIER_SUBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Classifier_SubjectShape"); //$NON-NLS-1$

	public static final IHintedType CLEAR_ASSOCIATION_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ClearAssociationAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CLEAR_STRUCTURAL_FEATURE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ClearStructuralFeatureAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CLEAR_VARIABLE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ClearVariableAction_Shape"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_USE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CollaborationUse_Shape"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_USE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CollaborationUse_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_Shape"); //$NON-NLS-1$

	public static final IHintedType COLLABORATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Collaboration_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COMBINED_FRAGMENT_CO_REGION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CombinedFragment_CoRegionShape"); //$NON-NLS-1$

	public static final IHintedType COMBINED_FRAGMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CombinedFragment_Shape"); //$NON-NLS-1$

	public static final IHintedType COMBINED_FRAGMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CombinedFragment_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COMMENT_ANNOTATED_ELEMENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$

	public static final IHintedType COMMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$

	public static final IHintedType COMMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COMMUNICATION_PATH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CommunicationPath_Edge"); //$NON-NLS-1$

	public static final IHintedType COMMUNICATION_PATH_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CommunicationPath_Shape"); //$NON-NLS-1$

	public static final IHintedType COMMUNICATION_PATH_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CommunicationPath_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_REALIZATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ComponentRealization_Edge"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_REALIZATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ComponentRealization_Shape"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_REALIZATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ComponentRealization_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_PACKAGED_ELEMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_PackagedElementShape"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_PACKAGED_ELEMENT_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_PackagedElementShape_CCN"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_PACKAGED_ELEMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_PackagedElementShape_CN"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_Shape"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType COMPONENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Component_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CONDITIONAL_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConditionalNode_Shape"); //$NON-NLS-1$

	public static final IHintedType CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER_TEMPLATE_PARAMETER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConnectableElementTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$

	public static final IHintedType CONNECTABLE_ELEMENT_COLLABORATION_ROLE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConnectableElement_CollaborationRoleShape"); //$NON-NLS-1$

	public static final IHintedType CONNECTION_POINT_REFERENCE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConnectionPointReference_Shape"); //$NON-NLS-1$

	public static final IHintedType CONNECTION_POINT_REFERENCE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConnectionPointReference_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CONNECTOR_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Connector_Edge"); //$NON-NLS-1$

	public static final IHintedType CONSIDER_IGNORE_FRAGMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConsiderIgnoreFragment_Shape"); //$NON-NLS-1$

	public static final IHintedType CONSIDER_IGNORE_FRAGMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConsiderIgnoreFragment_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_CONSTRAINED_ELEMENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_ConstrainedElementEdge"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_CONTEXT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_ContextEdge"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_LOCAL_POSTCONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_LocalPostconditionShape"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_LOCAL_PRECONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_LocalPreconditionShape"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_PACKAGED_ELEMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_PACKAGED_ELEMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape_CN"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_POSTCONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_PostconditionLabel"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_PRECONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_PreconditionLabel"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType CONSTRAINT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CONTINUATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Continuation_Shape"); //$NON-NLS-1$

	public static final IHintedType CONTINUATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Continuation_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType CONTROL_FLOW_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ControlFlow_Edge"); //$NON-NLS-1$

	public static final IHintedType CREATE_LINK_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CreateLinkAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CREATE_LINK_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CreateLinkObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType CREATE_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CreateObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType DATA_STORE_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataStoreNode_Shape"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_Shape"); //$NON-NLS-1$

	public static final IHintedType DATA_TYPE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DataType_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DECISION_NODE_DECISION_INPUT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DecisionNode_DecisionInputLabel"); //$NON-NLS-1$

	public static final IHintedType DECISION_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DecisionNode_Shape"); //$NON-NLS-1$

	public static final IHintedType DEPENDENCY_BRANCH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Dependency_BranchEdge"); //$NON-NLS-1$

	public static final IHintedType DEPENDENCY_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$

	public static final IHintedType DEPENDENCY_ROLE_BINDING_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Dependency_RoleBindingEdge"); //$NON-NLS-1$

	public static final IHintedType DEPENDENCY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Dependency_Shape"); //$NON-NLS-1$

	public static final IHintedType DEPENDENCY_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Dependency_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_DEPLOYED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_DeployedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_NESTED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_NestedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_SHAPE_ACN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_ACN"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Deployment_Edge"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Deployment_Shape"); //$NON-NLS-1$

	public static final IHintedType DEPLOYMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Deployment_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DESTROY_LINK_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DestroyLinkAction_Shape"); //$NON-NLS-1$

	public static final IHintedType DESTROY_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DestroyObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType DESTRUCTION_OCCURRENCE_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DestructionOccurrenceSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType DESTRUCTION_OCCURRENCE_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DestructionOccurrenceSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DEVICE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEVICE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEVICE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEVICE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType DEVICE_NESTED_NODE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_NestedNodeLabel"); //$NON-NLS-1$

	public static final IHintedType DEVICE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_Shape"); //$NON-NLS-1$

	public static final IHintedType DEVICE_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType DEVICE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Device_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DIAGRAM_SHORTCUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Diagram_ShortcutShape"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_LOCAL_POSTCONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_LocalPostconditionShape"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_LOCAL_PRECONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_LocalPreconditionShape"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_POSTCONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_PostconditionLabel"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_PRECONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_PreconditionLabel"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_Shape"); //$NON-NLS-1$

	public static final IHintedType DURATION_CONSTRAINT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationConstraint_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DURATION_INTERVAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationInterval_Shape"); //$NON-NLS-1$

	public static final IHintedType DURATION_OBSERVATION_EVENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationObservation_EventEdge"); //$NON-NLS-1$

	public static final IHintedType DURATION_OBSERVATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationObservation_Shape"); //$NON-NLS-1$

	public static final IHintedType DURATION_OBSERVATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DurationObservation_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType DURATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Duration_Shape"); //$NON-NLS-1$

	public static final IHintedType DURATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Duration_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ELEMENT_IMPORT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ElementImport_Edge"); //$NON-NLS-1$

	public static final IHintedType ELEMENT_CONTAINMENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Element_ContainmentEdge"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_LITERAL_DEPLOYED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.EnumerationLiteral_DeployedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_LITERAL_LITERAL_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.EnumerationLiteral_LiteralLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_LITERAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.EnumerationLiteral_Shape"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_LITERAL_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.EnumerationLiteral_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_Shape"); //$NON-NLS-1$

	public static final IHintedType ENUMERATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Enumeration_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType EXCEPTION_HANDLER_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExceptionHandler_Edge"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_NESTED_NODE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_NestedNodeLabel"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_ENVIRONMENT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_OCCURRENCE_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionOccurrenceSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType EXECUTION_OCCURRENCE_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExecutionOccurrenceSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType EXPANSION_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExpansionNode_InputShape"); //$NON-NLS-1$

	public static final IHintedType EXPANSION_NODE_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExpansionNode_OutputShape"); //$NON-NLS-1$

	public static final IHintedType EXPANSION_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExpansionNode_Shape"); //$NON-NLS-1$

	public static final IHintedType EXPANSION_REGION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExpansionRegion_Shape"); //$NON-NLS-1$

	public static final IHintedType EXPRESSION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Expression_Shape"); //$NON-NLS-1$

	public static final IHintedType EXTEND_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Extend_Edge"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_AttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_CLASS_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_ClassAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_COMPONENT_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_ComponentAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_DATA_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_DataTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_Edge"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_INTERFACE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_InterfaceAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_PRIMITIVE_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_PrimitiveTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_Shape"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_END_SIGNAL_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionEnd_SignalAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_POINT_CLASSIFIER_EXTENSION_POINT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionPoint_ClassifierExtensionPointLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_POINT_EXTENSION_POINT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ExtensionPoint_ExtensionPointLabel"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Extension_Edge"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Extension_Shape"); //$NON-NLS-1$

	public static final IHintedType EXTENSION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Extension_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType FINAL_STATE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FinalState_Shape"); //$NON-NLS-1$

	public static final IHintedType FINAL_STATE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FinalState_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType FLOW_FINAL_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FlowFinalNode_Shape"); //$NON-NLS-1$

	public static final IHintedType FORK_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ForkNode_Shape"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_Shape"); //$NON-NLS-1$

	public static final IHintedType FUNCTION_BEHAVIOR_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FunctionBehavior_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType GATE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Gate_Shape"); //$NON-NLS-1$

	public static final IHintedType GATE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Gate_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType GENERAL_ORDERING_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.GeneralOrdering_Edge"); //$NON-NLS-1$

	public static final IHintedType GENERAL_ORDERING_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.GeneralOrdering_Shape"); //$NON-NLS-1$

	public static final IHintedType GENERAL_ORDERING_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.GeneralOrdering_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType GENERALIZATION_SET_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.GeneralizationSet_Edge"); //$NON-NLS-1$

	public static final IHintedType GENERALIZATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$

	public static final IHintedType INCLUDE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Include_Edge"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_FLOW_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationFlow_Edge"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_Shape"); //$NON-NLS-1$

	public static final IHintedType INFORMATION_ITEM_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InformationItem_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INITIAL_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InitialNode_Shape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ACCEPT_CALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AcceptCallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ACCEPT_EVENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AcceptEventActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_ADD_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_AddVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_BROADCAST_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_BROADCAST_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_BroadcastSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CALL_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CALL_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CallBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CALL_OPERATION_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CallOperationActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CALL_OPERATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CallOperationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CALL_OPERATION_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CallOperationActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CLEAR_ASSOCIATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ClearAssociationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CLEAR_ASSOCIATION_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ClearAssociationActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ClearStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CLEAR_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ClearVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CONDITIONAL_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ConditionalNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CREATE_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CreateLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CREATE_LINK_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_CREATE_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_CreateObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_DESTROY_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_DestroyLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_DESTROY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_DestroyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_DESTROY_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_DestroyObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_EXPANSION_REGION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ExpansionRegionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_LOOP_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_LoopNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_LOOP_NODE_VARIABLE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_LoopNodeVariableInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_OPAQUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_OpaqueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_RAISE_EXCEPTION_ACTION_EXCEPTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RaiseExceptionActionExceptionShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_RAISE_EXCEPTION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RaiseExceptionActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_EXTENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadExtentActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadIsClassifiedObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_LINK_OBJECT_END_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadLinkObjectEndActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_LINK_OBJECT_END_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadLinkObjectEndActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadLinkObjectEndQualifierActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadLinkObjectEndQualifierActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_SELF_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadSelfActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_READ_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReadVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_RECLASSIFY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReclassifyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_RECLASSIFY_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REDUCE_ACTION_COLLECTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReduceActionCollectionShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REDUCE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReduceActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveStructuralFeatureValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveVariableValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_RemoveVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REPLY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReplyActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REPLY_ACTION_REPLY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReplyActionReplyShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_REPLY_ACTION_RETURN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ReplyActionReturnShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_OBJECT_ACTION_REQUEST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendObjectActionRequestShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEND_SIGNAL_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SendSignalActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SEQUENCE_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_SequenceNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_Shape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StartClassifierBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StartObjectBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_STRUCTURED_ACTIVITY_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_StructuredActivityNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_TEST_IDENTITY_ACTION_FIRST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_TestIdentityActionFirstShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_TEST_IDENTITY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_TestIdentityActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_TEST_IDENTITY_ACTION_SECOND_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_TestIdentityActionSecondShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_UNMARSHALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_UnmarshallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_UNMARSHALL_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_UnmarshallActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType INPUT_PIN_VALUE_SPECIFICATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InputPin_ValueSpecificationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType INSTANCE_SPECIFICATION_DEPLOYED_ARTIFACT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InstanceSpecification_DeployedArtifactLabel"); //$NON-NLS-1$

	public static final IHintedType INSTANCE_SPECIFICATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InstanceSpecification_Edge"); //$NON-NLS-1$

	public static final IHintedType INSTANCE_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InstanceSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType INSTANCE_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InstanceSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INSTANCE_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InstanceValue_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_CONSTRAINT_POSTCONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionConstraint_PostconditionLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_CONSTRAINT_PRECONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionConstraint_PreconditionLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_CONSTRAINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionConstraint_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_CONSTRAINT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionConstraint_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_OPERAND_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionOperand_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_OPERAND_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionOperand_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_USE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionUse_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_USE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InteractionUse_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERACTION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interaction_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_REALIZATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InterfaceRealization_Edge"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_REALIZATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InterfaceRealization_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_REALIZATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InterfaceRealization_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_CLASSIFIER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_ClassifierShape"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_CLASSIFIER_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_ClassifierShape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERFACE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interface_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERRUPTIBLE_ACTIVITY_REGION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InterruptibleActivityRegion_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERRUPTIBLE_ACTIVITY_REGION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InterruptibleActivityRegion_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_LOCAL_POSTCONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_LocalPostconditionShape"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_LOCAL_PRECONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_LocalPreconditionShape"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_POSTCONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_PostconditionLabel"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_PRECONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_PreconditionLabel"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_Shape"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_CONSTRAINT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.IntervalConstraint_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType INTERVAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Interval_Shape"); //$NON-NLS-1$

	public static final IHintedType JOIN_NODE_JOIN_SPEC_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.JoinNode_JoinSpecLabel"); //$NON-NLS-1$

	public static final IHintedType JOIN_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.JoinNode_Shape"); //$NON-NLS-1$

	public static final IHintedType LIFELINE_COMPACT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Lifeline_CompactShape"); //$NON-NLS-1$

	public static final IHintedType LIFELINE_FULL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Lifeline_FullShape"); //$NON-NLS-1$

	public static final IHintedType LIFELINE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Lifeline_Shape"); //$NON-NLS-1$

	public static final IHintedType LIFELINE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Lifeline_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType LINK_DESCRIPTOR_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Link_DescriptorEdge"); //$NON-NLS-1$

	public static final IHintedType LINK_INTERFACE_PORT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Link_InterfacePortEdge"); //$NON-NLS-1$

	public static final IHintedType LITERAL_BOOLEAN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralBoolean_Shape"); //$NON-NLS-1$

	public static final IHintedType LITERAL_INTEGER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralInteger_Shape"); //$NON-NLS-1$

	public static final IHintedType LITERAL_NULL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralNull_Shape"); //$NON-NLS-1$

	public static final IHintedType LITERAL_REAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralReal_Shape"); //$NON-NLS-1$

	public static final IHintedType LITERAL_STRING_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralString_Shape"); //$NON-NLS-1$

	public static final IHintedType LITERAL_UNLIMITED_NATURAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LiteralUnlimitedNatural_Shape"); //$NON-NLS-1$

	public static final IHintedType LOOP_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LoopNode_Shape"); //$NON-NLS-1$

	public static final IHintedType MANIFESTATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Manifestation_Edge"); //$NON-NLS-1$

	public static final IHintedType MANIFESTATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Manifestation_Shape"); //$NON-NLS-1$

	public static final IHintedType MANIFESTATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Manifestation_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType MERGE_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.MergeNode_Shape"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_OCCURRENCE_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.MessageOccurrenceSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_OCCURRENCE_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.MessageOccurrenceSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_ASYNCH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_AsynchEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_CREATE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_CreateEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_DELETE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_DeleteEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_Edge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_FOUND_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_FoundEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_LOST_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_LostEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_REPLY_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_ReplyEdge"); //$NON-NLS-1$

	public static final IHintedType MESSAGE_SYNCH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Message_SynchEdge"); //$NON-NLS-1$

	public static final IHintedType MODEL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Model_Shape"); //$NON-NLS-1$

	public static final IHintedType MODEL_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Model_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType NAMED_ELEMENT_DEFAULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$

	public static final IHintedType NODE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType NODE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType NODE_FREE_TIME_RULER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_FreeTimeRulerShape"); //$NON-NLS-1$

	public static final IHintedType NODE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType NODE_LINEAR_TIME_RULER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_LinearTimeRulerShape"); //$NON-NLS-1$

	public static final IHintedType NODE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType NODE_NESTED_NODE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_NestedNodeLabel"); //$NON-NLS-1$

	public static final IHintedType NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_Shape"); //$NON-NLS-1$

	public static final IHintedType NODE_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType NODE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType NODE_STATE_DEFINITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_StateDefinitionShape"); //$NON-NLS-1$

	public static final IHintedType NODE_STATE_INVARIANT_TRANSITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_StateInvariantTransitionShape"); //$NON-NLS-1$

	public static final IHintedType NODE_TICK_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Node_TickShape"); //$NON-NLS-1$

	public static final IHintedType OBJECT_FLOW_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ObjectFlow_Edge"); //$NON-NLS-1$

	public static final IHintedType OCCURRENCE_SPECIFICATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OccurrenceSpecification_Shape"); //$NON-NLS-1$

	public static final IHintedType OCCURRENCE_SPECIFICATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OccurrenceSpecification_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueAction_Shape"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_Shape"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_BEHAVIOR_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueBehavior_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType OPAQUE_EXPRESSION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OpaqueExpression_Shape"); //$NON-NLS-1$

	public static final IHintedType OPERATION_TEMPLATE_PARAMETER_TEMPLATE_PARAMETER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OperationTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_CLASS_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_ClassOperationLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_COMPONENT_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_ComponentOperationLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_DATA_TYPE_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_DataTypeOperationLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_INTERFACE_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_InterfaceOperationLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_OperationLabel"); //$NON-NLS-1$

	public static final IHintedType OPERATION_PRIMITIVE_TYPE_OPERATION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Operation_PrimitiveTypeOperationLabel"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ACCEPT_CALL_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AcceptCallActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ACCEPT_CALL_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AcceptCallActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ACCEPT_CALL_ACTION_RETURN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AcceptCallActionReturnShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ACCEPT_EVENT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AcceptEventActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ACCEPT_EVENT_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AcceptEventActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AddStructuralFeatureValueActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AddStructuralFeatureValueActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_ADD_VARIABLE_VALUE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_AddVariableValueActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_BROADCAST_SIGNAL_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_BroadcastSignalActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CALL_BEHAVIOR_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CallBehaviorActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CALL_BEHAVIOR_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CallBehaviorActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CALL_OPERATION_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CallOperationActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CALL_OPERATION_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CallOperationActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CLEAR_ASSOCIATION_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ClearAssociationActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ClearStructuralFeatureActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ClearStructuralFeatureActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CLEAR_VARIABLE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ClearVariableActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CONDITIONAL_NODE_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ConditionalNodeOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CONDITIONAL_NODE_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ConditionalNodeResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CREATE_LINK_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CreateLinkActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CREATE_LINK_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CreateLinkObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CREATE_LINK_OBJECT_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CreateLinkObjectActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CREATE_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CreateObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_CREATE_OBJECT_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_CreateObjectActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_DESTROY_LINK_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_DestroyLinkActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_DESTROY_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_DestroyObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_EXPANSION_REGION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ExpansionRegionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_LOOP_NODE_BODY_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_LoopNodeBodyOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_LOOP_NODE_DECIDER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_LoopNodeDeciderShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_LOOP_NODE_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_LoopNodeOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_LOOP_NODE_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_LoopNodeResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_LOOP_NODE_VARIABLE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_LoopNodeVariableShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_OPAQUE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_OpaqueActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_RAISE_EXCEPTION_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_RaiseExceptionActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_EXTENT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadExtentActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_EXTENT_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadExtentActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadIsClassifiedObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadIsClassifiedObjectActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_OBJECT_END_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkObjectEndActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_OBJECT_END_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkObjectEndActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkObjectEndQualifierActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadLinkObjectEndQualifierActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_SELF_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadSelfActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_SELF_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadSelfActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadStructuralFeatureActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_STRUCTURAL_FEATURE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadStructuralFeatureActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_VARIABLE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadVariableActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_READ_VARIABLE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReadVariableActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_RECLASSIFY_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReclassifyObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REDUCE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReduceActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REDUCE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReduceActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_RemoveStructuralFeatureValueActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_RemoveStructuralFeatureValueActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REMOVE_VARIABLE_VALUE_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_RemoveVariableValueActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_REPLY_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ReplyActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_SEND_OBJECT_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_SendObjectActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_SEND_SIGNAL_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_SendSignalActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_SEQUENCE_NODE_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_SequenceNodeOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_Shape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_StartClassifierBehaviorActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_StartObjectBehaviorActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_START_OBJECT_BEHAVIOR_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_StartObjectBehaviorActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_STRUCTURED_ACTIVITY_NODE_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_StructuredActivityNodeOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_TEST_IDENTITY_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_TestIdentityActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_TEST_IDENTITY_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_TestIdentityActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_UNMARSHALL_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_UnmarshallActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_UNMARSHALL_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_UnmarshallActionResultShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_VALUE_SPECIFICATION_ACTION_OUTPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ValueSpecificationActionOutputShape"); //$NON-NLS-1$

	public static final IHintedType OUTPUT_PIN_VALUE_SPECIFICATION_ACTION_RESULT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.OutputPin_ValueSpecificationActionResultShape"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_IMPORT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PackageImport_Edge"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_MERGE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PackageMerge_Edge"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_ACTIVITY_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_ActivityDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_CLASS_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_ClassDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_COMMUNICATION_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_CommunicationDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_COMPONENT_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_ComponentDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_COMPOSITE_STRUCTURE_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_CompositeStructureDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_DEPLOYMENT_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_DeploymentDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_INTERACTION_OVERVIEW_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_InteractionOverviewDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_PROFILE_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_ProfileDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_SEQUENCE_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_SequenceDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_Shape"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_STATE_MACHINE_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_StateMachineDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_TIMING_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_TimingDiagram"); //$NON-NLS-1$

	public static final IHintedType PACKAGE_USE_CASE_DIAGRAM = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Package_UseCaseDiagram"); //$NON-NLS-1$

	public static final IHintedType PARAMETER_PARAMETER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Parameter_ParameterLabel"); //$NON-NLS-1$

	public static final IHintedType PARAMETER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Parameter_Shape"); //$NON-NLS-1$

	public static final IHintedType PART_DECOMPOSITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PartDecomposition_Shape"); //$NON-NLS-1$

	public static final IHintedType PART_DECOMPOSITION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PartDecomposition_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PATH_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Path_Edge"); //$NON-NLS-1$

	public static final IHintedType PORT_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_AttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_BEHAVIOR_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_BehaviorEdge"); //$NON-NLS-1$

	public static final IHintedType PORT_BEHAVIOR_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_BehaviorShape"); //$NON-NLS-1$

	public static final IHintedType PORT_CLASS_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_ClassAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_COMPONENT_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_ComponentAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_DATA_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_DataTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_Edge"); //$NON-NLS-1$

	public static final IHintedType PORT_INTERFACE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_InterfaceAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_PRIMITIVE_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_PrimitiveTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PORT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_Shape"); //$NON-NLS-1$

	public static final IHintedType PORT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PORT_SIGNAL_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Port_SignalAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_Shape"); //$NON-NLS-1$

	public static final IHintedType PRIMITIVE_TYPE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.PrimitiveType_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PROFILE_APPLICATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProfileApplication_Edge"); //$NON-NLS-1$

	public static final IHintedType PROFILE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Profile_Shape"); //$NON-NLS-1$

	public static final IHintedType PROFILE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Profile_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_AttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_CLASS_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_ClassAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_COMPONENT_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_ComponentAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_DATA_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_DataTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_Edge"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_INTERFACE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_InterfaceAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_PRIMITIVE_TYPE_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_PrimitiveTypeAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_Shape"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PROPERTY_SIGNAL_ATTRIBUTE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Property_SignalAttributeLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_Shape"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_STATE_MACHINE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolStateMachine_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_TRANSITION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolTransition_Edge"); //$NON-NLS-1$

	public static final IHintedType PROTOCOL_TRANSITION_INTERNAL_TRANSITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ProtocolTransition_InternalTransitionLabel"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_CHOICE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_ChoiceShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_DEEP_HISTORY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_DeepHistoryShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_ENTRY_POINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_EntryPointShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_EXIT_POINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_ExitPointShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_FORK_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_ForkShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_INITIAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_InitialShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_JOIN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_JoinShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_JUNCTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_JunctionShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_SHALLOW_HISTORY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_ShallowHistoryShape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_Shape"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType PSEUDOSTATE_TERMINATE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Pseudostate_TerminateShape"); //$NON-NLS-1$

	public static final IHintedType RAISE_EXCEPTION_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.RaiseExceptionAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_EXTENT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadExtentAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_IS_CLASSIFIED_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadIsClassifiedObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_LINK_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadLinkAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_LINK_OBJECT_END_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadLinkObjectEndAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_LINK_OBJECT_END_QUALIFIER_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadLinkObjectEndQualifierAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_SELF_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadSelfAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_STRUCTURAL_FEATURE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadStructuralFeatureAction_Shape"); //$NON-NLS-1$

	public static final IHintedType READ_VARIABLE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReadVariableAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REALIZATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Realization_Edge"); //$NON-NLS-1$

	public static final IHintedType REALIZATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Realization_Shape"); //$NON-NLS-1$

	public static final IHintedType REALIZATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Realization_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType RECEPTION_INTERFACE_RECEPTION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Reception_InterfaceReceptionLabel"); //$NON-NLS-1$

	public static final IHintedType RECEPTION_RECEPTION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Reception_ReceptionLabel"); //$NON-NLS-1$

	public static final IHintedType RECLASSIFY_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReclassifyObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REDEFINABLE_TEMPLATE_SIGNATURE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.RedefinableTemplateSignature_Shape"); //$NON-NLS-1$

	public static final IHintedType REDEFINABLE_TEMPLATE_SIGNATURE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.RedefinableTemplateSignature_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType REDUCE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReduceAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REGION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Region_Shape"); //$NON-NLS-1$

	public static final IHintedType REGION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Region_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.RemoveStructuralFeatureValueAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REMOVE_VARIABLE_VALUE_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.RemoveVariableValueAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REPLY_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ReplyAction_Shape"); //$NON-NLS-1$

	public static final IHintedType REPRESENTATION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Representation_Edge"); //$NON-NLS-1$

	public static final IHintedType SEND_OBJECT_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.SendObjectAction_Shape"); //$NON-NLS-1$

	public static final IHintedType SEND_SIGNAL_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.SendSignalAction_Shape"); //$NON-NLS-1$

	public static final IHintedType SEQUENCE_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.SequenceNode_Shape"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_EVENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.SignalEvent_Shape"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_Shape"); //$NON-NLS-1$

	public static final IHintedType SIGNAL_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Signal_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType SLOT_SLOT_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Slot_SlotLabel"); //$NON-NLS-1$

	public static final IHintedType START_CLASSIFIER_BEHAVIOR_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StartClassifierBehaviorAction_Shape"); //$NON-NLS-1$

	public static final IHintedType START_OBJECT_BEHAVIOR_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StartObjectBehaviorAction_Shape"); //$NON-NLS-1$

	public static final IHintedType STATE_INVARIANT_COMPACT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateInvariant_CompactShape"); //$NON-NLS-1$

	public static final IHintedType STATE_INVARIANT_FULL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateInvariant_FullShape"); //$NON-NLS-1$

	public static final IHintedType STATE_INVARIANT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateInvariant_Shape"); //$NON-NLS-1$

	public static final IHintedType STATE_INVARIANT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateInvariant_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_OWNED_BEHAVIOR_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_OwnedBehaviorLabel"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_Shape"); //$NON-NLS-1$

	public static final IHintedType STATE_MACHINE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StateMachine_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType STATE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.State_Shape"); //$NON-NLS-1$

	public static final IHintedType STATE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.State_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_Shape"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType STEREOTYPE_STEREOTYPE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Stereotype_StereotypeLabel"); //$NON-NLS-1$

	public static final IHintedType STRING_EXPRESSION_PACKAGED_ELEMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StringExpression_PackagedElementShape"); //$NON-NLS-1$

	public static final IHintedType STRING_EXPRESSION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StringExpression_Shape"); //$NON-NLS-1$

	public static final IHintedType STRUCTURED_ACTIVITY_NODE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.StructuredActivityNode_Shape"); //$NON-NLS-1$

	public static final IHintedType SUBSTITUTION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Substitution_Edge"); //$NON-NLS-1$

	public static final IHintedType SUBSTITUTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Substitution_Shape"); //$NON-NLS-1$

	public static final IHintedType SUBSTITUTION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Substitution_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType TEMPLATE_BINDING_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TemplateBinding_Edge"); //$NON-NLS-1$

	public static final IHintedType TEMPLATE_PARAMETER_TEMPLATE_PARAMETER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$

	public static final IHintedType TEMPLATE_SIGNATURE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TemplateSignature_Shape"); //$NON-NLS-1$

	public static final IHintedType TEMPLATE_SIGNATURE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TemplateSignature_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType TEST_IDENTITY_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TestIdentityAction_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_LOCAL_POSTCONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_LocalPostconditionShape"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_LOCAL_PRECONDITION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_LocalPreconditionShape"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_POSTCONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_PostconditionLabel"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_PRECONDITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_PreconditionLabel"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_CONSTRAINT_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeConstraint_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType TIME_EVENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeEvent_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_EXPRESSION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeExpression_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_EXPRESSION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeExpression_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType TIME_INTERVAL_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeInterval_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_OBSERVATION_EVENT_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeObservation_EventEdge"); //$NON-NLS-1$

	public static final IHintedType TIME_OBSERVATION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeObservation_Shape"); //$NON-NLS-1$

	public static final IHintedType TIME_OBSERVATION_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.TimeObservation_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType TRANSITION_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Transition_Edge"); //$NON-NLS-1$

	public static final IHintedType TRANSITION_INTERNAL_TRANSITION_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Transition_InternalTransitionLabel"); //$NON-NLS-1$

	public static final IHintedType TRIGGER_DEFERRABLE_TRIGGER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Trigger_DeferrableTriggerLabel"); //$NON-NLS-1$

	public static final IHintedType UNMARSHALL_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UnmarshallAction_Shape"); //$NON-NLS-1$

	public static final IHintedType USAGE_EDGE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Usage_Edge"); //$NON-NLS-1$

	public static final IHintedType USAGE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Usage_Shape"); //$NON-NLS-1$

	public static final IHintedType USAGE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Usage_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_CLASS_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_ClassNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_CLASSIFIER_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_ClassifierShape"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_COMPONENT_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_ComponentNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_INTERFACE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_InterfaceNestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_NESTED_CLASSIFIER_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_NestedClassifierLabel"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_OWNED_USE_CASE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_OwnedUseCaseLabel"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_Shape"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_SHAPE_CCN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_Shape_CCN"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_SHAPE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_Shape_CN"); //$NON-NLS-1$

	public static final IHintedType USE_CASE_USE_CASE_LABEL = (IHintedType) getElementType("org.eclipse.papyrus.umldi.UseCase_UseCaseLabel"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ACCEPT_CALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AcceptCallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ACCEPT_EVENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AcceptEventActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_VARIABLE_VALUE_ACTION_INSERT_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_ADD_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_AddVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_BROADCAST_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_BROADCAST_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_BroadcastSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CALL_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CALL_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CallBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CALL_OPERATION_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CallOperationActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CALL_OPERATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CallOperationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CALL_OPERATION_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CallOperationActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CLEAR_ASSOCIATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ClearAssociationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CLEAR_ASSOCIATION_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ClearAssociationActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ClearStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CLEAR_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CLEAR_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ClearVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CONDITIONAL_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ConditionalNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CREATE_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CreateLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CREATE_LINK_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_CREATE_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_CreateObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_DESTROY_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_DestroyLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_DESTROY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_DestroyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_DESTROY_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_DestroyObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_EXPANSION_REGION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ExpansionRegionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_LOOP_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_LoopNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_LOOP_NODE_VARIABLE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_LoopNodeVariableInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_OPAQUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_OpaqueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_RAISE_EXCEPTION_ACTION_EXCEPTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RaiseExceptionActionExceptionShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_RAISE_EXCEPTION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RaiseExceptionActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_EXTENT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadExtentActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadIsClassifiedObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_IS_CLASSIFIED_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_LINK_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadLinkActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_LINK_OBJECT_END_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadLinkObjectEndActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_LINK_OBJECT_END_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadLinkObjectEndActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadLinkObjectEndQualifierActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_LINK_OBJECT_END_QUALIFIER_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadLinkObjectEndQualifierActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_SELF_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadSelfActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_STRUCTURAL_FEATURE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadStructuralFeatureActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_STRUCTURAL_FEATURE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_READ_VARIABLE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReadVariableActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_RECLASSIFY_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReclassifyObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_RECLASSIFY_OBJECT_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REDUCE_ACTION_COLLECTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReduceActionCollectionShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REDUCE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReduceActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveStructuralFeatureValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveStructuralFeatureValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveStructuralFeatureValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_VARIABLE_VALUE_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveVariableValueActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_VARIABLE_VALUE_ACTION_REMOVE_AT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveVariableValueActionRemoveAtShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REMOVE_VARIABLE_VALUE_ACTION_VALUE_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_RemoveVariableValueActionValueShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REPLY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReplyActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REPLY_ACTION_REPLY_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReplyActionReplyShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_REPLY_ACTION_RETURN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ReplyActionReturnShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_OBJECT_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendObjectActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_OBJECT_ACTION_REQUEST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendObjectActionRequestShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_OBJECT_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendObjectActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_SIGNAL_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendSignalActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_SIGNAL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendSignalActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEND_SIGNAL_ACTION_TARGET_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SendSignalActionTargetShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SEQUENCE_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_SequenceNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_Shape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StartClassifierBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_START_CLASSIFIER_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_START_OBJECT_BEHAVIOR_ACTION_ARGUMENT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_START_OBJECT_BEHAVIOR_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StartObjectBehaviorActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_START_OBJECT_BEHAVIOR_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_STRUCTURED_ACTIVITY_NODE_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_StructuredActivityNodeInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_TEST_IDENTITY_ACTION_FIRST_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_TestIdentityActionFirstShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_TEST_IDENTITY_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_TestIdentityActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_TEST_IDENTITY_ACTION_SECOND_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_TestIdentityActionSecondShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_UNMARSHALL_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_UnmarshallActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_UNMARSHALL_ACTION_OBJECT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_UnmarshallActionObjectShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_PIN_VALUE_SPECIFICATION_ACTION_INPUT_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValuePin_ValueSpecificationActionInputShape"); //$NON-NLS-1$

	public static final IHintedType VALUE_SPECIFICATION_ACTION_SHAPE = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ValueSpecificationAction_Shape"); //$NON-NLS-1$


}
