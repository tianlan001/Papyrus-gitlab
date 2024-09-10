/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.provider;

import org.eclipse.gmf.runtime.emf.type.core.AbstractElementTypeEnumerator;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

public class ElementTypes extends AbstractElementTypeEnumerator {

	/** ********************************************************* */
	/** Interaction Overview Diagram specific elements **************** */
	/** ********************************************************* */

	/** Interaction Overview Diagram :: Diagram */
	public static final String DIAGRAM_ID = "PapyrusUMLInteractionOverviewDiagram"; //$NON-NLS-1$

	/** ********************************************************* */
	/** Interaction Overview Diagram - ActivityDiagram related elements */
	/** ********************************************************* */

	/** ActivityDiagram :: ACTIVITY_FINAL_NODE_CN */
	public static final IHintedType ACTIVITY_FINAL_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ActivityFinalNode_Shape"); //$NON-NLS-1$

	public static final String ACTIVITY_FINAL_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "ActivityFinalNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: CALL_BEHAVIOR_ACTION_CN */
	public static final IHintedType CALL_BEHAVIOR_ACTION_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.CallBehaviorAction_Shape"); //$NON-NLS-1$

	public static final String CALL_BEHAVIOR_ACTION_CN_LABEL_NAME_HINT = "CallBehaviorAction_NameLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: COMMENT_CN */
	public static final IHintedType COMMENT_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$

	public static final String COMMENT_CN_LABEL_BODY_LABEL_HINT = "Comment_BodyLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: CONDITIONAL_NODE_CN */
	public static final IHintedType CONDITIONAL_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ConditionalNode_Shape"); //$NON-NLS-1$

	public static final String CONDITIONAL_NODE_CN_COMPARTMENT_STRUCTURED_ACTIVITY_NODE_CONTENT_HINT = "ConditionalNode_ActivityNodeCompartment"; //$NON-NLS-1$

	public static final String CONDITIONAL_NODE_CN_LABEL_KEYWORD_HINT = "ConditionalNode_KeywordLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: DECISION_NODE_CN */
	public static final IHintedType DECISION_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.DecisionNode_Shape"); //$NON-NLS-1$

	public static final String DECISION_NODE_CN_LABEL_DECISION_INPUT_HINT = "DecisionNode_DecisionInputLabel"; //$NON-NLS-1$

	public static final String DECISION_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "DecisionNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: FLOW_FINAL_NODE_CN */
	public static final IHintedType FLOW_FINAL_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.FlowFinalNode_Shape"); //$NON-NLS-1$

	public static final String FLOW_FINAL_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "FlowFinalNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: FORK_NODE_CN */
	public static final IHintedType FORK_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ForkNode_Shape"); //$NON-NLS-1$

	public static final String FORK_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "ForkNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: INITIAL_NODE_CN */
	public static final IHintedType INITIAL_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.InitialNode_Shape"); //$NON-NLS-1$

	public static final String INITIAL_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "InitialNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: JOIN_NODE_CN */
	public static final IHintedType JOIN_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.JoinNode_Shape"); //$NON-NLS-1$

	public static final String JOIN_NODE_CN_LABEL_JOIN_SPEC_HINT = "JoinNode_JoinSpecLabel"; //$NON-NLS-1$

	public static final String JOIN_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "JoinNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: LOOP_NODE_CN */
	public static final IHintedType LOOP_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.LoopNode_Shape"); //$NON-NLS-1$

	public static final String LOOP_NODE_CN_COMPARTMENT_STRUCTURED_ACTIVITY_NODE_CONTENT_HINT = "LoopNode_ActivityNodeCompartment"; //$NON-NLS-1$

	public static final String LOOP_NODE_CN_LABEL_KEYWORD_HINT = "LoopNode_KeywordLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: MERGE_NODE_CN */
	public static final IHintedType MERGE_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.MergeNode_Shape"); //$NON-NLS-1$

	public static final String MERGE_NODE_CN_LABEL_APPLIED_STEREOTYPE_HINT = "MergeNode_StereotypeLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: SHAPE_NAMED_ELEMENT_CN */
	public static final IHintedType SHAPE_NAMED_ELEMENT_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$

	public static final String SHAPE_NAMED_ELEMENT_CN_LABEL_NAME_HINT = "NamedElement_NameLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: ACTIVITY */
	public static final IHintedType ACTIVITY = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Activity_Shape"); //$NON-NLS-1$

	public static final String ACTIVITY_COMPARTMENT_ACTIVITY_FIGURE_PARAMETER_HINT = "Activity_ParameterCompartment"; //$NON-NLS-1$

	public static final String ACTIVITY_COMPARTMENT_ACTIVITY_FIGURE_PRECONDITION_HINT = "Activity_PreconditionCompartment"; //$NON-NLS-1$

	public static final String ACTIVITY_COMPARTMENT_ACTIVITY_FIGURE_POST_CONDTION_HINT = "Activity_PostconditionCompartment"; //$NON-NLS-1$

	public static final String ACTIVITY_COMPARTMENT_ACTIVITY_FIGURE_CONTENT_HINT = "Activity_ActivityNodeCompartment"; //$NON-NLS-1$

	public static final String ACTIVITY_LABEL_NAME_HINT = "Activity_NameLabel"; //$NON-NLS-1$

	public static final String ACTIVITY_LABEL_IS_SINGLE_EXECUTION_HINT = "Activity_KeywordLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: SEQUENCE_NODE_CN */
	public static final IHintedType SEQUENCE_NODE_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.SequenceNode_Shape"); //$NON-NLS-1$

	public static final String SEQUENCE_NODE_CN_COMPARTMENT_STRUCTURED_ACTIVITY_NODE_CONTENT_HINT = "SequenceNode_ActivityNodeCompartment"; //$NON-NLS-1$

	public static final String SEQUENCE_NODE_CN_LABEL_KEYWORD_HINT = "SequenceNode_KeywordLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: CONSTRAINT_CN */
	public static final IHintedType CONSTRAINT_CN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$

	public static final String CONSTRAINT_CN_LABEL_NAME_HINT = "Constraint_NameLabel"; //$NON-NLS-1$

	public static final String CONSTRAINT_CN_LABEL_BODY_HINT = "Constraint_BodyLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: COMMENT_ANNOTATED_ELEMENT */
	public static final IHintedType COMMENT_ANNOTATED_ELEMENT = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$

	/** ActivityDiagram :: CONTROL_FLOW */
	public static final IHintedType CONTROL_FLOW = (IHintedType) getElementType("org.eclipse.papyrus.umldi.ControlFlow_Edge"); //$NON-NLS-1$

	public static final String CONTROL_FLOW_LABEL_NAME_HINT = "ControlFlow_NameLabel"; //$NON-NLS-1$

	public static final String CONTROL_FLOW_LABEL_WEIGHT_HINT = "ControlFlow_WeightLabel"; //$NON-NLS-1$

	public static final String CONTROL_FLOW_LABEL_GUARD_HINT = "ControlFlow_GuardLabel"; //$NON-NLS-1$

	public static final String CONTROL_FLOW_LABEL_APPLIED_STEREOTYPE_HINT = "ControlFlow_StereotypeLabel"; //$NON-NLS-1$

	public static final String CONTROL_FLOW_LABEL_INTERRUPTIBLE_ICON_HINT = "ControlFlow_IconLabel"; //$NON-NLS-1$

	/** ActivityDiagram :: ChildLabelNodes */
	public static final IHintedType ACTIVITY_PARAMETER_CLN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Parameter_ParameterLabel"); //$NON-NLS-1$

	public static final IHintedType ACTIVITY_CONSTRAINT_CLN = (IHintedType) getElementType("org.eclipse.papyrus.umldi.Constraint_PreconditionLabel"); //$NON-NLS-1$

}
