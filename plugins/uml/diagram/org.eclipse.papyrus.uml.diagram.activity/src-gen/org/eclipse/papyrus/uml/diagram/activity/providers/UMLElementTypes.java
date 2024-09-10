/**
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.activity.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DiagramElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLElementTypes {

	/**
	 * @generated
	 */
	private UMLElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(UMLDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Package_ActivityDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_ActivityDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Activity_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Activity_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Parameter_ParameterLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Parameter_ParameterLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PreconditionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PreconditionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PostconditionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PostconditionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InitialNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InitialNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActivityFinalNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActivityFinalNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType FlowFinalNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.FlowFinalNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OpaqueAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OpaqueAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_OpaqueActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_OpaqueActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_OpaqueActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_OpaqueActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_OpaqueActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_OpaqueActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_OpaqueActionOutputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_OpaqueActionOutputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CallBehaviorAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CallBehaviorAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_CallBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_CallBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_CallBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_CallBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_CallBehaviorActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_CallBehaviorActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CallOperationAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CallOperationAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_CallOperationActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_CallOperationActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_CallOperationActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_CallOperationActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_CallOperationActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_CallOperationActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_CallOperationActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_CallOperationActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_CallOperationActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_CallOperationActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_CallOperationActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_CallOperationActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_CallOperationActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_CallOperationActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationConstraint_LocalPreconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationConstraint_LocalPreconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationConstraint_LocalPostconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationConstraint_LocalPostconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeConstraint_LocalPreconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeConstraint_LocalPreconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeConstraint_LocalPostconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeConstraint_LocalPostconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType IntervalConstraint_LocalPreconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.IntervalConstraint_LocalPreconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType IntervalConstraint_LocalPostconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.IntervalConstraint_LocalPostconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_LocalPreconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_LocalPreconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_LocalPostconditionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_LocalPostconditionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DecisionNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DecisionNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType MergeNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.MergeNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ForkNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ForkNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType JoinNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.JoinNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataStoreNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataStoreNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SendObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.SendObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_SendObjectActionRequestShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_SendObjectActionRequestShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_SendObjectActionRequestShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_SendObjectActionRequestShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_SendObjectActionRequestShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_SendObjectActionRequestShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_SendObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_SendObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_SendObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_SendObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_SendObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_SendObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SendSignalAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.SendSignalAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_SendSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_SendSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_SendSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_SendSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_SendSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_SendSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_SendSignalActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_SendSignalActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_SendSignalActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_SendSignalActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_SendSignalActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_SendSignalActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActivityParameterNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActivityParameterNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AcceptEventAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AcceptEventAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_AcceptEventActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_AcceptEventActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValueSpecificationAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValueSpecificationAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ValueSpecificationActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ValueSpecificationActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ConditionalNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ConditionalNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExpansionRegion_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExpansionRegion_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExpansionNode_InputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExpansionNode_InputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExpansionNode_OutputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExpansionNode_OutputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LoopNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LoopNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_LoopNodeVariableInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_LoopNodeVariableInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_LoopNodeVariableInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_LoopNodeVariableInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_LoopNodeVariableInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_LoopNodeVariableInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_LoopNodeBodyOutputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_LoopNodeBodyOutputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_LoopNodeVariableShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_LoopNodeVariableShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_LoopNodeResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_LoopNodeResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SequenceNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.SequenceNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StructuredActivityNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StructuredActivityNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_StructuredActivityNodeInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_StructuredActivityNodeInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_StructuredActivityNodeInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_StructuredActivityNodeInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_StructuredActivityNodeInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_StructuredActivityNodeInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_StructuredActivityNodeOutputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_StructuredActivityNodeOutputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActivityPartition_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActivityPartition_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InterruptibleActivityRegion_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InterruptibleActivityRegion_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadSelfAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadSelfAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadSelfActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadSelfActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Activity_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Activity_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CreateObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CreateObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_CreateObjectActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_CreateObjectActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType NamedElement_DefaultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadStructuralFeatureAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadStructuralFeatureAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ReadStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ReadStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ReadStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ReadStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadStructuralFeatureActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadStructuralFeatureActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AddStructuralFeatureValueAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AddStructuralFeatureValueAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_AddStructuralFeatureValueActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_AddStructuralFeatureValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_AddStructuralFeatureValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_AddStructuralFeatureValueActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_AddStructuralFeatureValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_AddStructuralFeatureValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_AddStructuralFeatureValueActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_AddStructuralFeatureValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_AddStructuralFeatureValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_AddStructuralFeatureValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_AddStructuralFeatureValueActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_AddStructuralFeatureValueActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DestroyObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DestroyObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_DestroyObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_DestroyObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_DestroyObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_DestroyObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_DestroyObjectActionTargetShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_DestroyObjectActionTargetShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadVariableAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadVariableAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadVariableActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadVariableActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AddVariableValueAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AddVariableValueAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_AddVariableValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_AddVariableValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_AddVariableValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_AddVariableValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_AddVariableValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_AddVariableValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_AddVariableValueActionInsertAtShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_AddVariableValueActionInsertAtShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_AddVariableValueActionValueShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_AddVariableValueActionValueShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType BroadcastSignalAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.BroadcastSignalAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_BroadcastSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_BroadcastSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_BroadcastSignalActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_BroadcastSignalActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CentralBufferNode_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CentralBufferNode_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StartObjectBehaviorAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StartObjectBehaviorAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_StartObjectBehaviorActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_StartObjectBehaviorActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_StartObjectBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_StartObjectBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_StartObjectBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_StartObjectBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_StartObjectBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_StartObjectBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_StartObjectBehaviorActionArgumentShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_StartObjectBehaviorActionArgumentShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TestIdentityAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TestIdentityAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_TestIdentityActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_TestIdentityActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_TestIdentityActionFirstShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_TestIdentityActionFirstShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_TestIdentityActionSecondShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_TestIdentityActionSecondShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_TestIdentityActionFirstShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_TestIdentityActionFirstShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_TestIdentityActionSecondShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_TestIdentityActionSecondShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_TestIdentityActionFirstShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_TestIdentityActionFirstShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_TestIdentityActionSecondShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_TestIdentityActionSecondShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ClearStructuralFeatureAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ClearStructuralFeatureAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ClearStructuralFeatureActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ClearStructuralFeatureActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ClearStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ClearStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ClearStructuralFeatureActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ClearStructuralFeatureActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CreateLinkAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CreateLinkAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_CreateLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_CreateLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_CreateLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_CreateLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_CreateLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_CreateLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadLinkAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadLinkAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadLinkActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadLinkActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ReadLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ReadLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ReadLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ReadLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ReadLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ReadLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DestroyLinkAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DestroyLinkAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_DestroyLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_DestroyLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_DestroyLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_DestroyLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_DestroyLinkActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_DestroyLinkActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ClearAssociationAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ClearAssociationAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ClearAssociationActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ClearAssociationActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ClearAssociationActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ClearAssociationActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ClearAssociationActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ClearAssociationActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadExtentAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadExtentAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadExtentActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadExtentActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReclassifyObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReclassifyObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ReclassifyObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ReclassifyObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ReclassifyObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ReclassifyObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReadIsClassifiedObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReadIsClassifiedObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReadIsClassifiedObjectActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReadIsClassifiedObjectActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ReadIsClassifiedObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ReadIsClassifiedObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ReadIsClassifiedObjectActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ReadIsClassifiedObjectActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ReduceAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ReduceAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_ReduceActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_ReduceActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_ReduceActionCollectionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_ReduceActionCollectionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_ReduceActionCollectionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_ReduceActionCollectionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_ReduceActionCollectionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_ReduceActionCollectionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StartClassifierBehaviorAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StartClassifierBehaviorAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_StartClassifierBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_StartClassifierBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_StartClassifierBehaviorActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_StartClassifierBehaviorActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CreateLinkObjectAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CreateLinkObjectAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_CreateLinkObjectActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_CreateLinkObjectActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_CreateLinkObjectActionInputShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_CreateLinkObjectActionInputShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_CreateLinkObjectActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_CreateLinkObjectActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UnmarshallAction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UnmarshallAction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InputPin_UnmarshallActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InputPin_UnmarshallActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ValuePin_UnmarshallActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ValuePin_UnmarshallActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ActionInputPin_UnmarshallActionObjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ActionInputPin_UnmarshallActionObjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OutputPin_UnmarshallActionResultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OutputPin_UnmarshallActionResultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Action_LocalPreconditionEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Action_LocalPreconditionEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Action_LocalPostconditionEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Action_LocalPostconditionEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ObjectFlow_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ObjectFlow_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ControlFlow_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ControlFlow_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExceptionHandler_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExceptionHandler_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_AnnotatedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_ConstrainedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ConstrainedElementEdge"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 *
	 * @generated
	 */
	public static synchronized ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<>();
			elements.put(Package_ActivityDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Activity_Shape, UMLPackage.eINSTANCE.getActivity());
			elements.put(Parameter_ParameterLabel, UMLPackage.eINSTANCE.getParameter());
			elements.put(Constraint_PreconditionLabel, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Constraint_PostconditionLabel, UMLPackage.eINSTANCE.getConstraint());
			elements.put(InitialNode_Shape, UMLPackage.eINSTANCE.getInitialNode());
			elements.put(ActivityFinalNode_Shape, UMLPackage.eINSTANCE.getActivityFinalNode());
			elements.put(FlowFinalNode_Shape, UMLPackage.eINSTANCE.getFlowFinalNode());
			elements.put(OpaqueAction_Shape, UMLPackage.eINSTANCE.getOpaqueAction());
			elements.put(ValuePin_OpaqueActionInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_OpaqueActionInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_OpaqueActionInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(OutputPin_OpaqueActionOutputShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(CallBehaviorAction_Shape, UMLPackage.eINSTANCE.getCallBehaviorAction());
			elements.put(ValuePin_CallBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_CallBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_CallBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(OutputPin_CallBehaviorActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(CallOperationAction_Shape, UMLPackage.eINSTANCE.getCallOperationAction());
			elements.put(ActionInputPin_CallOperationActionArgumentShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ValuePin_CallOperationActionArgumentShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(InputPin_CallOperationActionArgumentShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(OutputPin_CallOperationActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(ValuePin_CallOperationActionTargetShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_CallOperationActionTargetShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_CallOperationActionTargetShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(DurationConstraint_LocalPreconditionShape, UMLPackage.eINSTANCE.getDurationConstraint());
			elements.put(DurationConstraint_LocalPostconditionShape, UMLPackage.eINSTANCE.getDurationConstraint());
			elements.put(TimeConstraint_LocalPreconditionShape, UMLPackage.eINSTANCE.getTimeConstraint());
			elements.put(TimeConstraint_LocalPostconditionShape, UMLPackage.eINSTANCE.getTimeConstraint());
			elements.put(IntervalConstraint_LocalPreconditionShape, UMLPackage.eINSTANCE.getIntervalConstraint());
			elements.put(IntervalConstraint_LocalPostconditionShape, UMLPackage.eINSTANCE.getIntervalConstraint());
			elements.put(Constraint_LocalPreconditionShape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Constraint_LocalPostconditionShape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(DecisionNode_Shape, UMLPackage.eINSTANCE.getDecisionNode());
			elements.put(MergeNode_Shape, UMLPackage.eINSTANCE.getMergeNode());
			elements.put(ForkNode_Shape, UMLPackage.eINSTANCE.getForkNode());
			elements.put(JoinNode_Shape, UMLPackage.eINSTANCE.getJoinNode());
			elements.put(DataStoreNode_Shape, UMLPackage.eINSTANCE.getDataStoreNode());
			elements.put(SendObjectAction_Shape, UMLPackage.eINSTANCE.getSendObjectAction());
			elements.put(ValuePin_SendObjectActionRequestShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_SendObjectActionRequestShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_SendObjectActionRequestShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_SendObjectActionTargetShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_SendObjectActionTargetShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_SendObjectActionTargetShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(SendSignalAction_Shape, UMLPackage.eINSTANCE.getSendSignalAction());
			elements.put(ActionInputPin_SendSignalActionArgumentShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ValuePin_SendSignalActionArgumentShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(InputPin_SendSignalActionArgumentShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_SendSignalActionTargetShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_SendSignalActionTargetShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_SendSignalActionTargetShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ActivityParameterNode_Shape, UMLPackage.eINSTANCE.getActivityParameterNode());
			elements.put(AcceptEventAction_Shape, UMLPackage.eINSTANCE.getAcceptEventAction());
			elements.put(OutputPin_AcceptEventActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(ValueSpecificationAction_Shape, UMLPackage.eINSTANCE.getValueSpecificationAction());
			elements.put(OutputPin_ValueSpecificationActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(ConditionalNode_Shape, UMLPackage.eINSTANCE.getConditionalNode());
			elements.put(ExpansionRegion_Shape, UMLPackage.eINSTANCE.getExpansionRegion());
			elements.put(ExpansionNode_InputShape, UMLPackage.eINSTANCE.getExpansionNode());
			elements.put(ExpansionNode_OutputShape, UMLPackage.eINSTANCE.getExpansionNode());
			elements.put(LoopNode_Shape, UMLPackage.eINSTANCE.getLoopNode());
			elements.put(InputPin_LoopNodeVariableInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_LoopNodeVariableInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_LoopNodeVariableInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_LoopNodeBodyOutputShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(OutputPin_LoopNodeVariableShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(OutputPin_LoopNodeResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(SequenceNode_Shape, UMLPackage.eINSTANCE.getSequenceNode());
			elements.put(StructuredActivityNode_Shape, UMLPackage.eINSTANCE.getStructuredActivityNode());
			elements.put(InputPin_StructuredActivityNodeInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_StructuredActivityNodeInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_StructuredActivityNodeInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_StructuredActivityNodeOutputShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(ActivityPartition_Shape, UMLPackage.eINSTANCE.getActivityPartition());
			elements.put(InterruptibleActivityRegion_Shape, UMLPackage.eINSTANCE.getInterruptibleActivityRegion());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(ReadSelfAction_Shape, UMLPackage.eINSTANCE.getReadSelfAction());
			elements.put(OutputPin_ReadSelfActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(Activity_Shape_CN, UMLPackage.eINSTANCE.getActivity());
			elements.put(CreateObjectAction_Shape, UMLPackage.eINSTANCE.getCreateObjectAction());
			elements.put(OutputPin_CreateObjectActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(NamedElement_DefaultShape, UMLPackage.eINSTANCE.getNamedElement());
			elements.put(ReadStructuralFeatureAction_Shape, UMLPackage.eINSTANCE.getReadStructuralFeatureAction());
			elements.put(InputPin_ReadStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ReadStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ReadStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_ReadStructuralFeatureActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(AddStructuralFeatureValueAction_Shape, UMLPackage.eINSTANCE.getAddStructuralFeatureValueAction());
			elements.put(InputPin_AddStructuralFeatureValueActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(InputPin_AddStructuralFeatureValueActionValueShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(InputPin_AddStructuralFeatureValueActionInsertAtShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_AddStructuralFeatureValueActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ValuePin_AddStructuralFeatureValueActionValueShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ValuePin_AddStructuralFeatureValueActionInsertAtShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_AddStructuralFeatureValueActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ActionInputPin_AddStructuralFeatureValueActionValueShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ActionInputPin_AddStructuralFeatureValueActionInsertAtShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_AddStructuralFeatureValueActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(DestroyObjectAction_Shape, UMLPackage.eINSTANCE.getDestroyObjectAction());
			elements.put(InputPin_DestroyObjectActionTargetShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_DestroyObjectActionTargetShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_DestroyObjectActionTargetShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ReadVariableAction_Shape, UMLPackage.eINSTANCE.getReadVariableAction());
			elements.put(OutputPin_ReadVariableActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(AddVariableValueAction_Shape, UMLPackage.eINSTANCE.getAddVariableValueAction());
			elements.put(InputPin_AddVariableValueActionInsertAtShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(InputPin_AddVariableValueActionValueShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_AddVariableValueActionInsertAtShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ValuePin_AddVariableValueActionValueShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_AddVariableValueActionInsertAtShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ActionInputPin_AddVariableValueActionValueShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(BroadcastSignalAction_Shape, UMLPackage.eINSTANCE.getBroadcastSignalAction());
			elements.put(InputPin_BroadcastSignalActionArgumentShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_BroadcastSignalActionArgumentShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_BroadcastSignalActionArgumentShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(CentralBufferNode_Shape, UMLPackage.eINSTANCE.getCentralBufferNode());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(StartObjectBehaviorAction_Shape, UMLPackage.eINSTANCE.getStartObjectBehaviorAction());
			elements.put(OutputPin_StartObjectBehaviorActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_StartObjectBehaviorActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_StartObjectBehaviorActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_StartObjectBehaviorActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(InputPin_StartObjectBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_StartObjectBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_StartObjectBehaviorActionArgumentShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(TestIdentityAction_Shape, UMLPackage.eINSTANCE.getTestIdentityAction());
			elements.put(OutputPin_TestIdentityActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_TestIdentityActionFirstShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(InputPin_TestIdentityActionSecondShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_TestIdentityActionFirstShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ValuePin_TestIdentityActionSecondShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_TestIdentityActionFirstShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ActionInputPin_TestIdentityActionSecondShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ClearStructuralFeatureAction_Shape, UMLPackage.eINSTANCE.getClearStructuralFeatureAction());
			elements.put(OutputPin_ClearStructuralFeatureActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_ClearStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ClearStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ClearStructuralFeatureActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(CreateLinkAction_Shape, UMLPackage.eINSTANCE.getCreateLinkAction());
			elements.put(InputPin_CreateLinkActionInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_CreateLinkActionInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_CreateLinkActionInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ReadLinkAction_Shape, UMLPackage.eINSTANCE.getReadLinkAction());
			elements.put(OutputPin_ReadLinkActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_ReadLinkActionInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ReadLinkActionInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ReadLinkActionInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(DestroyLinkAction_Shape, UMLPackage.eINSTANCE.getDestroyLinkAction());
			elements.put(InputPin_DestroyLinkActionInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_DestroyLinkActionInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_DestroyLinkActionInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ClearAssociationAction_Shape, UMLPackage.eINSTANCE.getClearAssociationAction());
			elements.put(InputPin_ClearAssociationActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ClearAssociationActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ClearAssociationActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ReadExtentAction_Shape, UMLPackage.eINSTANCE.getReadExtentAction());
			elements.put(OutputPin_ReadExtentActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(ReclassifyObjectAction_Shape, UMLPackage.eINSTANCE.getReclassifyObjectAction());
			elements.put(InputPin_ReclassifyObjectActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ReclassifyObjectActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ReclassifyObjectActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ReadIsClassifiedObjectAction_Shape, UMLPackage.eINSTANCE.getReadIsClassifiedObjectAction());
			elements.put(OutputPin_ReadIsClassifiedObjectActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_ReadIsClassifiedObjectActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ReadIsClassifiedObjectActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ReadIsClassifiedObjectActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(ReduceAction_Shape, UMLPackage.eINSTANCE.getReduceAction());
			elements.put(OutputPin_ReduceActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(InputPin_ReduceActionCollectionShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_ReduceActionCollectionShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_ReduceActionCollectionShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(StartClassifierBehaviorAction_Shape, UMLPackage.eINSTANCE.getStartClassifierBehaviorAction());
			elements.put(InputPin_StartClassifierBehaviorActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_StartClassifierBehaviorActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_StartClassifierBehaviorActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(CreateLinkObjectAction_Shape, UMLPackage.eINSTANCE.getCreateLinkObjectAction());
			elements.put(InputPin_CreateLinkObjectActionInputShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_CreateLinkObjectActionInputShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_CreateLinkObjectActionInputShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_CreateLinkObjectActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(UnmarshallAction_Shape, UMLPackage.eINSTANCE.getUnmarshallAction());
			elements.put(InputPin_UnmarshallActionObjectShape, UMLPackage.eINSTANCE.getInputPin());
			elements.put(ValuePin_UnmarshallActionObjectShape, UMLPackage.eINSTANCE.getValuePin());
			elements.put(ActionInputPin_UnmarshallActionObjectShape, UMLPackage.eINSTANCE.getActionInputPin());
			elements.put(OutputPin_UnmarshallActionResultShape, UMLPackage.eINSTANCE.getOutputPin());
			elements.put(Action_LocalPreconditionEdge, UMLPackage.eINSTANCE.getAction_LocalPrecondition());
			elements.put(Action_LocalPostconditionEdge, UMLPackage.eINSTANCE.getAction_LocalPostcondition());
			elements.put(ObjectFlow_Edge, UMLPackage.eINSTANCE.getObjectFlow());
			elements.put(ControlFlow_Edge, UMLPackage.eINSTANCE.getControlFlow());
			elements.put(ExceptionHandler_Edge, UMLPackage.eINSTANCE.getExceptionHandler());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
		}
		return elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementTypeByUniqueId(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static synchronized boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<>();
			KNOWN_ELEMENT_TYPES.add(Package_ActivityDiagram);
			KNOWN_ELEMENT_TYPES.add(Activity_Shape);
			KNOWN_ELEMENT_TYPES.add(Parameter_ParameterLabel);
			KNOWN_ELEMENT_TYPES.add(Constraint_PreconditionLabel);
			KNOWN_ELEMENT_TYPES.add(Constraint_PostconditionLabel);
			KNOWN_ELEMENT_TYPES.add(InitialNode_Shape);
			KNOWN_ELEMENT_TYPES.add(ActivityFinalNode_Shape);
			KNOWN_ELEMENT_TYPES.add(FlowFinalNode_Shape);
			KNOWN_ELEMENT_TYPES.add(OpaqueAction_Shape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_OpaqueActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_OpaqueActionInputShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_OpaqueActionInputShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_OpaqueActionOutputShape);
			KNOWN_ELEMENT_TYPES.add(CallBehaviorAction_Shape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_CallBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_CallBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_CallBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_CallBehaviorActionResultShape);
			KNOWN_ELEMENT_TYPES.add(CallOperationAction_Shape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_CallOperationActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_CallOperationActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_CallOperationActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_CallOperationActionResultShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_CallOperationActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_CallOperationActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_CallOperationActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(DurationConstraint_LocalPreconditionShape);
			KNOWN_ELEMENT_TYPES.add(DurationConstraint_LocalPostconditionShape);
			KNOWN_ELEMENT_TYPES.add(TimeConstraint_LocalPreconditionShape);
			KNOWN_ELEMENT_TYPES.add(TimeConstraint_LocalPostconditionShape);
			KNOWN_ELEMENT_TYPES.add(IntervalConstraint_LocalPreconditionShape);
			KNOWN_ELEMENT_TYPES.add(IntervalConstraint_LocalPostconditionShape);
			KNOWN_ELEMENT_TYPES.add(Constraint_LocalPreconditionShape);
			KNOWN_ELEMENT_TYPES.add(Constraint_LocalPostconditionShape);
			KNOWN_ELEMENT_TYPES.add(DecisionNode_Shape);
			KNOWN_ELEMENT_TYPES.add(MergeNode_Shape);
			KNOWN_ELEMENT_TYPES.add(ForkNode_Shape);
			KNOWN_ELEMENT_TYPES.add(JoinNode_Shape);
			KNOWN_ELEMENT_TYPES.add(DataStoreNode_Shape);
			KNOWN_ELEMENT_TYPES.add(SendObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_SendObjectActionRequestShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_SendObjectActionRequestShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_SendObjectActionRequestShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_SendObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_SendObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_SendObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(SendSignalAction_Shape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_SendSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_SendSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_SendSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_SendSignalActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_SendSignalActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_SendSignalActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ActivityParameterNode_Shape);
			KNOWN_ELEMENT_TYPES.add(AcceptEventAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_AcceptEventActionResultShape);
			KNOWN_ELEMENT_TYPES.add(ValueSpecificationAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ValueSpecificationActionResultShape);
			KNOWN_ELEMENT_TYPES.add(ConditionalNode_Shape);
			KNOWN_ELEMENT_TYPES.add(ExpansionRegion_Shape);
			KNOWN_ELEMENT_TYPES.add(ExpansionNode_InputShape);
			KNOWN_ELEMENT_TYPES.add(ExpansionNode_OutputShape);
			KNOWN_ELEMENT_TYPES.add(LoopNode_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_LoopNodeVariableInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_LoopNodeVariableInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_LoopNodeVariableInputShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_LoopNodeBodyOutputShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_LoopNodeVariableShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_LoopNodeResultShape);
			KNOWN_ELEMENT_TYPES.add(SequenceNode_Shape);
			KNOWN_ELEMENT_TYPES.add(StructuredActivityNode_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_StructuredActivityNodeInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_StructuredActivityNodeInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_StructuredActivityNodeInputShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_StructuredActivityNodeOutputShape);
			KNOWN_ELEMENT_TYPES.add(ActivityPartition_Shape);
			KNOWN_ELEMENT_TYPES.add(InterruptibleActivityRegion_Shape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(ReadSelfAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadSelfActionResultShape);
			KNOWN_ELEMENT_TYPES.add(Activity_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(CreateObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_CreateObjectActionResultShape);
			KNOWN_ELEMENT_TYPES.add(NamedElement_DefaultShape);
			KNOWN_ELEMENT_TYPES.add(ReadStructuralFeatureAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ReadStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ReadStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ReadStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadStructuralFeatureActionResultShape);
			KNOWN_ELEMENT_TYPES.add(AddStructuralFeatureValueAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_AddStructuralFeatureValueActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_AddStructuralFeatureValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_AddStructuralFeatureValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_AddStructuralFeatureValueActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_AddStructuralFeatureValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_AddStructuralFeatureValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_AddStructuralFeatureValueActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_AddStructuralFeatureValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_AddStructuralFeatureValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_AddStructuralFeatureValueActionResultShape);
			KNOWN_ELEMENT_TYPES.add(DestroyObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_DestroyObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_DestroyObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_DestroyObjectActionTargetShape);
			KNOWN_ELEMENT_TYPES.add(ReadVariableAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadVariableActionResultShape);
			KNOWN_ELEMENT_TYPES.add(AddVariableValueAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_AddVariableValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_AddVariableValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_AddVariableValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_AddVariableValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_AddVariableValueActionInsertAtShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_AddVariableValueActionValueShape);
			KNOWN_ELEMENT_TYPES.add(BroadcastSignalAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_BroadcastSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_BroadcastSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_BroadcastSignalActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(CentralBufferNode_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(StartObjectBehaviorAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_StartObjectBehaviorActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_StartObjectBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_StartObjectBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_StartObjectBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_StartObjectBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_StartObjectBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_StartObjectBehaviorActionArgumentShape);
			KNOWN_ELEMENT_TYPES.add(TestIdentityAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_TestIdentityActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_TestIdentityActionFirstShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_TestIdentityActionSecondShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_TestIdentityActionFirstShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_TestIdentityActionSecondShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_TestIdentityActionFirstShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_TestIdentityActionSecondShape);
			KNOWN_ELEMENT_TYPES.add(ClearStructuralFeatureAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ClearStructuralFeatureActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ClearStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ClearStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ClearStructuralFeatureActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(CreateLinkAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_CreateLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_CreateLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_CreateLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ReadLinkAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadLinkActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ReadLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ReadLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ReadLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(DestroyLinkAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_DestroyLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_DestroyLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_DestroyLinkActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ClearAssociationAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ClearAssociationActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ClearAssociationActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ClearAssociationActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ReadExtentAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadExtentActionResultShape);
			KNOWN_ELEMENT_TYPES.add(ReclassifyObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ReclassifyObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ReclassifyObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ReclassifyObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ReadIsClassifiedObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReadIsClassifiedObjectActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ReadIsClassifiedObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ReadIsClassifiedObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ReadIsClassifiedObjectActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ReduceAction_Shape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_ReduceActionResultShape);
			KNOWN_ELEMENT_TYPES.add(InputPin_ReduceActionCollectionShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_ReduceActionCollectionShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_ReduceActionCollectionShape);
			KNOWN_ELEMENT_TYPES.add(StartClassifierBehaviorAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_StartClassifierBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_StartClassifierBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_StartClassifierBehaviorActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(CreateLinkObjectAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_CreateLinkObjectActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_CreateLinkObjectActionInputShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_CreateLinkObjectActionInputShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_CreateLinkObjectActionResultShape);
			KNOWN_ELEMENT_TYPES.add(UnmarshallAction_Shape);
			KNOWN_ELEMENT_TYPES.add(InputPin_UnmarshallActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ValuePin_UnmarshallActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(ActionInputPin_UnmarshallActionObjectShape);
			KNOWN_ELEMENT_TYPES.add(OutputPin_UnmarshallActionResultShape);
			KNOWN_ELEMENT_TYPES.add(Action_LocalPreconditionEdge);
			KNOWN_ELEMENT_TYPES.add(Action_LocalPostconditionEdge);
			KNOWN_ELEMENT_TYPES.add(ObjectFlow_Edge);
			KNOWN_ELEMENT_TYPES.add(ControlFlow_Edge);
			KNOWN_ELEMENT_TYPES.add(ExceptionHandler_Edge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
		}

		boolean result = KNOWN_ELEMENT_TYPES.contains(elementType);

		if (!result) {
			IElementType[] supertypes = elementType.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = KNOWN_ELEMENT_TYPES.contains(supertypes[i]);
			}
		}

		return result;
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case ActivityDiagramEditPart.VISUAL_ID:
				return Package_ActivityDiagram;
			case ActivityEditPart.VISUAL_ID:
				return Activity_Shape;
			case ParameterEditPart.VISUAL_ID:
				return Parameter_ParameterLabel;
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return Constraint_PreconditionLabel;
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return Constraint_PostconditionLabel;
			case InitialNodeEditPart.VISUAL_ID:
				return InitialNode_Shape;
			case ActivityFinalNodeEditPart.VISUAL_ID:
				return ActivityFinalNode_Shape;
			case FlowFinalNodeEditPart.VISUAL_ID:
				return FlowFinalNode_Shape;
			case OpaqueActionEditPart.VISUAL_ID:
				return OpaqueAction_Shape;
			case ValuePinInOpaqueActEditPart.VISUAL_ID:
				return ValuePin_OpaqueActionInputShape;
			case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
				return ActionInputPin_OpaqueActionInputShape;
			case InputPinInOpaqueActEditPart.VISUAL_ID:
				return InputPin_OpaqueActionInputShape;
			case OutputPinInOpaqueActEditPart.VISUAL_ID:
				return OutputPin_OpaqueActionOutputShape;
			case CallBehaviorActionEditPart.VISUAL_ID:
				return CallBehaviorAction_Shape;
			case ValuePinInCallBeActEditPart.VISUAL_ID:
				return ValuePin_CallBehaviorActionArgumentShape;
			case ActionInputPinInCallBeActEditPart.VISUAL_ID:
				return ActionInputPin_CallBehaviorActionArgumentShape;
			case InputPinInCallBeActEditPart.VISUAL_ID:
				return InputPin_CallBehaviorActionArgumentShape;
			case OutputPinInCallBeActEditPart.VISUAL_ID:
				return OutputPin_CallBehaviorActionResultShape;
			case CallOperationActionEditPart.VISUAL_ID:
				return CallOperationAction_Shape;
			case ActionInputPinInCallOpActEditPart.VISUAL_ID:
				return ActionInputPin_CallOperationActionArgumentShape;
			case ValuePinInCallOpActEditPart.VISUAL_ID:
				return ValuePin_CallOperationActionArgumentShape;
			case InputPinInCallOpActEditPart.VISUAL_ID:
				return InputPin_CallOperationActionArgumentShape;
			case OutputPinInCallOpActEditPart.VISUAL_ID:
				return OutputPin_CallOperationActionResultShape;
			case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
				return ValuePin_CallOperationActionTargetShape;
			case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return ActionInputPin_CallOperationActionTargetShape;
			case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return InputPin_CallOperationActionTargetShape;
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return DurationConstraint_LocalPreconditionShape;
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return DurationConstraint_LocalPostconditionShape;
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return TimeConstraint_LocalPreconditionShape;
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return TimeConstraint_LocalPostconditionShape;
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return IntervalConstraint_LocalPreconditionShape;
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return IntervalConstraint_LocalPostconditionShape;
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return Constraint_LocalPreconditionShape;
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return Constraint_LocalPostconditionShape;
			case DecisionNodeEditPart.VISUAL_ID:
				return DecisionNode_Shape;
			case MergeNodeEditPart.VISUAL_ID:
				return MergeNode_Shape;
			case ForkNodeEditPart.VISUAL_ID:
				return ForkNode_Shape;
			case JoinNodeEditPart.VISUAL_ID:
				return JoinNode_Shape;
			case DataStoreNodeEditPart.VISUAL_ID:
				return DataStoreNode_Shape;
			case SendObjectActionEditPart.VISUAL_ID:
				return SendObjectAction_Shape;
			case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
				return ValuePin_SendObjectActionRequestShape;
			case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return ActionInputPin_SendObjectActionRequestShape;
			case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return InputPin_SendObjectActionRequestShape;
			case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
				return ValuePin_SendObjectActionTargetShape;
			case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return ActionInputPin_SendObjectActionTargetShape;
			case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return InputPin_SendObjectActionTargetShape;
			case SendSignalActionEditPart.VISUAL_ID:
				return SendSignalAction_Shape;
			case ActionInputPinInSendSigActEditPart.VISUAL_ID:
				return ActionInputPin_SendSignalActionArgumentShape;
			case ValuePinInSendSigActEditPart.VISUAL_ID:
				return ValuePin_SendSignalActionArgumentShape;
			case InputPinInSendSigActEditPart.VISUAL_ID:
				return InputPin_SendSignalActionArgumentShape;
			case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
				return ValuePin_SendSignalActionTargetShape;
			case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return ActionInputPin_SendSignalActionTargetShape;
			case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return InputPin_SendSignalActionTargetShape;
			case ActivityParameterNodeEditPart.VISUAL_ID:
				return ActivityParameterNode_Shape;
			case AcceptEventActionEditPart.VISUAL_ID:
				return AcceptEventAction_Shape;
			case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
				return OutputPin_AcceptEventActionResultShape;
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return ValueSpecificationAction_Shape;
			case OutputPinInValSpecActEditPart.VISUAL_ID:
				return OutputPin_ValueSpecificationActionResultShape;
			case ConditionalNodeEditPart.VISUAL_ID:
				return ConditionalNode_Shape;
			case ExpansionRegionEditPart.VISUAL_ID:
				return ExpansionRegion_Shape;
			case ExpansionNodeAsInEditPart.VISUAL_ID:
				return ExpansionNode_InputShape;
			case ExpansionNodeAsOutEditPart.VISUAL_ID:
				return ExpansionNode_OutputShape;
			case LoopNodeEditPart.VISUAL_ID:
				return LoopNode_Shape;
			case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return InputPin_LoopNodeVariableInputShape;
			case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return ValuePin_LoopNodeVariableInputShape;
			case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return ActionInputPin_LoopNodeVariableInputShape;
			case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
				return OutputPin_LoopNodeBodyOutputShape;
			case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
				return OutputPin_LoopNodeVariableShape;
			case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
				return OutputPin_LoopNodeResultShape;
			case SequenceNodeEditPart.VISUAL_ID:
				return SequenceNode_Shape;
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return StructuredActivityNode_Shape;
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return InputPin_StructuredActivityNodeInputShape;
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return ValuePin_StructuredActivityNodeInputShape;
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return ActionInputPin_StructuredActivityNodeInputShape;
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return OutputPin_StructuredActivityNodeOutputShape;
			case ActivityPartitionEditPart.VISUAL_ID:
				return ActivityPartition_Shape;
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
				return InterruptibleActivityRegion_Shape;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape;
			case ReadSelfActionEditPart.VISUAL_ID:
				return ReadSelfAction_Shape;
			case ReadSelfActionOutputPinEditPart.VISUAL_ID:
				return OutputPin_ReadSelfActionResultShape;
			case ActivityEditPartCN.VISUAL_ID:
				return Activity_Shape_CN;
			case CreateObjectActionEditPart.VISUAL_ID:
				return CreateObjectAction_Shape;
			case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
				return OutputPin_CreateObjectActionResultShape;
			case ShapeNamedElementEditPart.VISUAL_ID:
				return NamedElement_DefaultShape;
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return ReadStructuralFeatureAction_Shape;
			case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return InputPin_ReadStructuralFeatureActionObjectShape;
			case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return ValuePin_ReadStructuralFeatureActionObjectShape;
			case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_ReadStructuralFeatureActionObjectShape;
			case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
				return OutputPin_ReadStructuralFeatureActionResultShape;
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return AddStructuralFeatureValueAction_Shape;
			case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return InputPin_AddStructuralFeatureValueActionObjectShape;
			case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return InputPin_AddStructuralFeatureValueActionValueShape;
			case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return InputPin_AddStructuralFeatureValueActionInsertAtShape;
			case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_AddStructuralFeatureValueActionObjectShape;
			case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return ValuePin_AddStructuralFeatureValueActionValueShape;
			case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return ValuePin_AddStructuralFeatureValueActionInsertAtShape;
			case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_AddStructuralFeatureValueActionObjectShape;
			case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return ActionInputPin_AddStructuralFeatureValueActionValueShape;
			case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return ActionInputPin_AddStructuralFeatureValueActionInsertAtShape;
			case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
				return OutputPin_AddStructuralFeatureValueActionResultShape;
			case DestroyObjectActionEditPart.VISUAL_ID:
				return DestroyObjectAction_Shape;
			case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
				return InputPin_DestroyObjectActionTargetShape;
			case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
				return ValuePin_DestroyObjectActionTargetShape;
			case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
				return ActionInputPin_DestroyObjectActionTargetShape;
			case ReadVariableActionEditPart.VISUAL_ID:
				return ReadVariableAction_Shape;
			case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
				return OutputPin_ReadVariableActionResultShape;
			case AddVariableValueActionEditPart.VISUAL_ID:
				return AddVariableValueAction_Shape;
			case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return InputPin_AddVariableValueActionInsertAtShape;
			case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return InputPin_AddVariableValueActionValueShape;
			case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return ValuePin_AddVariableValueActionInsertAtShape;
			case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return ValuePin_AddVariableValueActionValueShape;
			case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return ActionInputPin_AddVariableValueActionInsertAtShape;
			case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return ActionInputPin_AddVariableValueActionValueShape;
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return BroadcastSignalAction_Shape;
			case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return InputPin_BroadcastSignalActionArgumentShape;
			case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
				return ValuePin_BroadcastSignalActionArgumentShape;
			case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return ActionInputPin_BroadcastSignalActionArgumentShape;
			case CentralBufferNodeEditPart.VISUAL_ID:
				return CentralBufferNode_Shape;
			case ConstraintEditPartCN.VISUAL_ID:
				return Constraint_Shape;
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return StartObjectBehaviorAction_Shape;
			case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
				return OutputPin_StartObjectBehaviorActionResultShape;
			case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return InputPin_StartObjectBehaviorActionObjectShape;
			case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_StartObjectBehaviorActionObjectShape;
			case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_StartObjectBehaviorActionObjectShape;
			case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return InputPin_StartObjectBehaviorActionArgumentShape;
			case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return ValuePin_StartObjectBehaviorActionArgumentShape;
			case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return ActionInputPin_StartObjectBehaviorActionArgumentShape;
			case TestIdentityActionEditPart.VISUAL_ID:
				return TestIdentityAction_Shape;
			case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
				return OutputPin_TestIdentityActionResultShape;
			case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return InputPin_TestIdentityActionFirstShape;
			case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return InputPin_TestIdentityActionSecondShape;
			case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return ValuePin_TestIdentityActionFirstShape;
			case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return ValuePin_TestIdentityActionSecondShape;
			case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return ActionInputPin_TestIdentityActionFirstShape;
			case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return ActionInputPin_TestIdentityActionSecondShape;
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return ClearStructuralFeatureAction_Shape;
			case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
				return OutputPin_ClearStructuralFeatureActionResultShape;
			case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return InputPin_ClearStructuralFeatureActionObjectShape;
			case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_ClearStructuralFeatureActionObjectShape;
			case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_ClearStructuralFeatureActionObjectShape;
			case CreateLinkActionEditPart.VISUAL_ID:
				return CreateLinkAction_Shape;
			case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return InputPin_CreateLinkActionInputShape;
			case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return ValuePin_CreateLinkActionInputShape;
			case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return ActionInputPin_CreateLinkActionInputShape;
			case ReadLinkActionEditPart.VISUAL_ID:
				return ReadLinkAction_Shape;
			case OutputPinInReadLinkActionEditPart.VISUAL_ID:
				return OutputPin_ReadLinkActionResultShape;
			case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return InputPin_ReadLinkActionInputShape;
			case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return ValuePin_ReadLinkActionInputShape;
			case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return ActionInputPin_ReadLinkActionInputShape;
			case DestroyLinkActionEditPart.VISUAL_ID:
				return DestroyLinkAction_Shape;
			case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return InputPin_DestroyLinkActionInputShape;
			case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return ValuePin_DestroyLinkActionInputShape;
			case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return ActionInputPin_DestroyLinkActionInputShape;
			case ClearAssociationActionEditPart.VISUAL_ID:
				return ClearAssociationAction_Shape;
			case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return InputPin_ClearAssociationActionObjectShape;
			case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_ClearAssociationActionObjectShape;
			case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_ClearAssociationActionObjectShape;
			case ReadExtentActionEditPart.VISUAL_ID:
				return ReadExtentAction_Shape;
			case OutputPinInReadExtentActionEditPart.VISUAL_ID:
				return OutputPin_ReadExtentActionResultShape;
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return ReclassifyObjectAction_Shape;
			case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return InputPin_ReclassifyObjectActionObjectShape;
			case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_ReclassifyObjectActionObjectShape;
			case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_ReclassifyObjectActionObjectShape;
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return ReadIsClassifiedObjectAction_Shape;
			case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return OutputPin_ReadIsClassifiedObjectActionResultShape;
			case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return InputPin_ReadIsClassifiedObjectActionObjectShape;
			case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_ReadIsClassifiedObjectActionObjectShape;
			case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_ReadIsClassifiedObjectActionObjectShape;
			case ReduceActionEditPart.VISUAL_ID:
				return ReduceAction_Shape;
			case OutputPinInReduceActionEditPart.VISUAL_ID:
				return OutputPin_ReduceActionResultShape;
			case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return InputPin_ReduceActionCollectionShape;
			case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return ValuePin_ReduceActionCollectionShape;
			case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return ActionInputPin_ReduceActionCollectionShape;
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return StartClassifierBehaviorAction_Shape;
			case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return InputPin_StartClassifierBehaviorActionObjectShape;
			case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_StartClassifierBehaviorActionObjectShape;
			case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_StartClassifierBehaviorActionObjectShape;
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return CreateLinkObjectAction_Shape;
			case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return InputPin_CreateLinkObjectActionInputShape;
			case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return ValuePin_CreateLinkObjectActionInputShape;
			case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return ActionInputPin_CreateLinkObjectActionInputShape;
			case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
				return OutputPin_CreateLinkObjectActionResultShape;
			case UnmarshallActionEditPart.VISUAL_ID:
				return UnmarshallAction_Shape;
			case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return InputPin_UnmarshallActionObjectShape;
			case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return ValuePin_UnmarshallActionObjectShape;
			case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return ActionInputPin_UnmarshallActionObjectShape;
			case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
				return OutputPin_UnmarshallActionResultShape;
			case ActionLocalPreconditionEditPart.VISUAL_ID:
				return Action_LocalPreconditionEdge;
			case ActionLocalPostconditionEditPart.VISUAL_ID:
				return Action_LocalPostconditionEdge;
			case ObjectFlowEditPart.VISUAL_ID:
				return ObjectFlow_Edge;
			case ControlFlowEditPart.VISUAL_ID:
				return ControlFlow_Edge;
			case ExceptionHandlerEditPart.VISUAL_ID:
				return ExceptionHandler_Edge;
			case CommentLinkEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		/**
		 * @generated
		 */
		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes.getElement(elementTypeAdapter);
		}
	};

	/**
	 * @generated
	 */
	public static boolean isKindOf(IElementType subtype, IElementType supertype) {
		boolean result = subtype == supertype;

		if (!result) {
			IElementType[] supertypes = subtype.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = supertype == supertypes[i];
			}
		}

		return result;
	}
}
