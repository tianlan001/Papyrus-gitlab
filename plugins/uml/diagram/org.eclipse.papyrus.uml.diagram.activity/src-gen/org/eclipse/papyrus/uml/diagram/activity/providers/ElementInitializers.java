/*****************************************************************************
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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.providers;

import org.eclipse.papyrus.uml.diagram.activity.expressions.UMLOCLFactory;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.common.actions.LabelHelper;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.AddVariableValueAction;
import org.eclipse.uml2.uml.BroadcastSignalAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.CentralBufferNode;
import org.eclipse.uml2.uml.ClearAssociationAction;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.CreateLinkObjectAction;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DataStoreNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.DestroyObjectAction;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationInterval;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Interval;
import org.eclipse.uml2.uml.IntervalConstraint;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.ReadVariableAction;
import org.eclipse.uml2.uml.ReclassifyObjectAction;
import org.eclipse.uml2.uml.ReduceAction;
import org.eclipse.uml2.uml.SendObjectAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.SequenceNode;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeInterval;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UnmarshallAction;
import org.eclipse.uml2.uml.ValuePin;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.ValueSpecificationAction;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public void init_Activity_Shape(Activity instance) {
		try {
			Object value_0 = name_Activity_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Parameter_ParameterLabel(Parameter instance) {
		try {
			Object value_0 = name_Parameter_ParameterLabel(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_PreconditionLabel(Constraint instance) {
		try {
			Object value_0 = name_Constraint_PreconditionLabel(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_PostconditionLabel(Constraint instance) {
		try {
			Object value_0 = name_Constraint_PostconditionLabel(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InitialNode_Shape(InitialNode instance) {
		try {
			Object value_0 = name_InitialNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActivityFinalNode_Shape(ActivityFinalNode instance) {
		try {
			Object value_0 = name_ActivityFinalNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_FlowFinalNode_Shape(FlowFinalNode instance) {
		try {
			Object value_0 = name_FlowFinalNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OpaqueAction_Shape(OpaqueAction instance) {
		try {
			Object value_0 = name_OpaqueAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_OpaqueActionInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_OpaqueActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_OpaqueActionInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_OpaqueActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_OpaqueActionInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_OpaqueActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_OpaqueActionOutputShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_OpaqueActionOutputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CallBehaviorAction_Shape(CallBehaviorAction instance) {
		try {
			Object value_0 = name_CallBehaviorAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_CallBehaviorActionArgumentShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_CallBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_CallBehaviorActionArgumentShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_CallBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_CallBehaviorActionArgumentShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_CallBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_CallBehaviorActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_CallBehaviorActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CallOperationAction_Shape(CallOperationAction instance) {
		try {
			Object value_0 = name_CallOperationAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_CallOperationActionArgumentShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_CallOperationActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_CallOperationActionArgumentShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_CallOperationActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_CallOperationActionArgumentShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_CallOperationActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_CallOperationActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_CallOperationActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_CallOperationActionTargetShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_CallOperationActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_CallOperationActionTargetShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_CallOperationActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_CallOperationActionTargetShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_CallOperationActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DurationConstraint_LocalPreconditionShape(DurationConstraint instance) {
		try {
			Object value_0 = name_DurationConstraint_LocalPreconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			DurationInterval newInstance_1_0 = UMLFactory.eINSTANCE.createDurationInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_DurationConstraint_LocalPreconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DurationConstraint_LocalPostconditionShape(DurationConstraint instance) {
		try {
			Object value_0 = name_DurationConstraint_LocalPostconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			DurationInterval newInstance_1_0 = UMLFactory.eINSTANCE.createDurationInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_DurationConstraint_LocalPostconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_TimeConstraint_LocalPreconditionShape(TimeConstraint instance) {
		try {
			Object value_0 = name_TimeConstraint_LocalPreconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			TimeInterval newInstance_1_0 = UMLFactory.eINSTANCE.createTimeInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_TimeConstraint_LocalPreconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_TimeConstraint_LocalPostconditionShape(TimeConstraint instance) {
		try {
			Object value_0 = name_TimeConstraint_LocalPostconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			TimeInterval newInstance_1_0 = UMLFactory.eINSTANCE.createTimeInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_TimeConstraint_LocalPostconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_IntervalConstraint_LocalPreconditionShape(IntervalConstraint instance) {
		try {
			Object value_0 = name_IntervalConstraint_LocalPreconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Interval newInstance_1_0 = UMLFactory.eINSTANCE.createInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_IntervalConstraint_LocalPreconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_IntervalConstraint_LocalPostconditionShape(IntervalConstraint instance) {
		try {
			Object value_0 = name_IntervalConstraint_LocalPostconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Interval newInstance_1_0 = UMLFactory.eINSTANCE.createInterval();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_IntervalConstraint_LocalPostconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_LocalPreconditionShape(Constraint instance) {
		try {
			Object value_0 = name_Constraint_LocalPreconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			OpaqueExpression newInstance_1_0 = UMLFactory.eINSTANCE.createOpaqueExpression();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_Constraint_LocalPreconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_LocalPostconditionShape(Constraint instance) {
		try {
			Object value_0 = name_Constraint_LocalPostconditionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			OpaqueExpression newInstance_1_0 = UMLFactory.eINSTANCE.createOpaqueExpression();
			instance.setSpecification(newInstance_1_0);
			Object value_1_0_0 = name_specification_Constraint_LocalPostconditionShape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DecisionNode_Shape(DecisionNode instance) {
		try {
			Object value_0 = name_DecisionNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_MergeNode_Shape(MergeNode instance) {
		try {
			Object value_0 = name_MergeNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ForkNode_Shape(ForkNode instance) {
		try {
			Object value_0 = name_ForkNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_JoinNode_Shape(JoinNode instance) {
		try {
			Object value_0 = name_JoinNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DataStoreNode_Shape(DataStoreNode instance) {
		try {
			Object value_0 = name_DataStoreNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_SendObjectAction_Shape(SendObjectAction instance) {
		try {
			Object value_0 = name_SendObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_SendObjectActionRequestShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_SendObjectActionRequestShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_SendObjectActionRequestShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_SendObjectActionRequestShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_SendObjectActionRequestShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_SendObjectActionRequestShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_SendObjectActionTargetShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_SendObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_SendObjectActionTargetShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_SendObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_SendObjectActionTargetShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_SendObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_SendSignalAction_Shape(SendSignalAction instance) {
		try {
			Object value_0 = name_SendSignalAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_SendSignalActionArgumentShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_SendSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_SendSignalActionArgumentShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_SendSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_SendSignalActionArgumentShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_SendSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_SendSignalActionTargetShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_SendSignalActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_SendSignalActionTargetShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_SendSignalActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_SendSignalActionTargetShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_SendSignalActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActivityParameterNode_Shape(ActivityParameterNode instance) {
		try {
			Object value_0 = name_ActivityParameterNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = isControlType_ActivityParameterNode_Shape(instance);
			if (value_1 != null) {
				instance.setIsControlType(((Boolean) value_1).booleanValue());
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_AcceptEventAction_Shape(AcceptEventAction instance) {
		try {
			Object value_0 = name_AcceptEventAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_AcceptEventActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_AcceptEventActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValueSpecificationAction_Shape(ValueSpecificationAction instance) {
		try {
			Object value_0 = name_ValueSpecificationAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ValueSpecificationActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ValueSpecificationActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ConditionalNode_Shape(ConditionalNode instance) {
		try {
			Object value_0 = name_ConditionalNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ExpansionRegion_Shape(ExpansionRegion instance) {
		try {
			Object value_0 = name_ExpansionRegion_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ExpansionNode_InputShape(ExpansionNode instance) {
		try {
			Object value_0 = name_ExpansionNode_InputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ExpansionNode_OutputShape(ExpansionNode instance) {
		try {
			Object value_0 = name_ExpansionNode_OutputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_LoopNode_Shape(LoopNode instance) {
		try {
			Object value_0 = name_LoopNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_LoopNodeVariableInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_LoopNodeVariableInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_LoopNodeVariableInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_LoopNodeVariableInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_LoopNodeVariableInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_LoopNodeVariableInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_LoopNodeBodyOutputShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_LoopNodeBodyOutputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_LoopNodeVariableShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_LoopNodeVariableShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_LoopNodeResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_LoopNodeResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_SequenceNode_Shape(SequenceNode instance) {
		try {
			Object value_0 = name_SequenceNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_StructuredActivityNode_Shape(StructuredActivityNode instance) {
		try {
			Object value_0 = name_StructuredActivityNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_StructuredActivityNodeInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_StructuredActivityNodeInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_StructuredActivityNodeInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_StructuredActivityNodeInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_StructuredActivityNodeInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_StructuredActivityNodeInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_StructuredActivityNodeOutputShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_StructuredActivityNodeOutputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActivityPartition_Shape(ActivityPartition instance) {
		try {
			Object value_0 = name_ActivityPartition_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Comment_Shape(Comment instance) {
		try {
			Object value_0 = UMLOCLFactory.getExpression(28, UMLPackage.eINSTANCE.getComment(), null).evaluate(instance);
			if (value_0 != null) {
				instance.setBody((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadSelfAction_Shape(ReadSelfAction instance) {
		try {
			Object value_0 = name_ReadSelfAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadSelfActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadSelfActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Activity_Shape_CN(Activity instance) {
		try {
			Object value_0 = name_Activity_Shape_CN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CreateObjectAction_Shape(CreateObjectAction instance) {
		try {
			Object value_0 = name_CreateObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_CreateObjectActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_CreateObjectActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadStructuralFeatureAction_Shape(ReadStructuralFeatureAction instance) {
		try {
			Object value_0 = name_ReadStructuralFeatureAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ReadStructuralFeatureActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ReadStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ReadStructuralFeatureActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ReadStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ReadStructuralFeatureActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ReadStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadStructuralFeatureActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadStructuralFeatureActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_AddStructuralFeatureValueAction_Shape(AddStructuralFeatureValueAction instance) {
		try {
			Object value_0 = name_AddStructuralFeatureValueAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_AddStructuralFeatureValueActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_AddStructuralFeatureValueActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_AddStructuralFeatureValueActionValueShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_AddStructuralFeatureValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_AddStructuralFeatureValueActionInsertAtShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_AddStructuralFeatureValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_AddStructuralFeatureValueActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_AddStructuralFeatureValueActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_AddStructuralFeatureValueActionValueShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_AddStructuralFeatureValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_AddStructuralFeatureValueActionInsertAtShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_AddStructuralFeatureValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_AddStructuralFeatureValueActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_AddStructuralFeatureValueActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_AddStructuralFeatureValueActionValueShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_AddStructuralFeatureValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_AddStructuralFeatureValueActionInsertAtShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_AddStructuralFeatureValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_AddStructuralFeatureValueActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_AddStructuralFeatureValueActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DestroyObjectAction_Shape(DestroyObjectAction instance) {
		try {
			Object value_0 = name_DestroyObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_DestroyObjectActionTargetShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_DestroyObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_DestroyObjectActionTargetShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_DestroyObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_DestroyObjectActionTargetShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_DestroyObjectActionTargetShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadVariableAction_Shape(ReadVariableAction instance) {
		try {
			Object value_0 = name_ReadVariableAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadVariableActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadVariableActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_AddVariableValueAction_Shape(AddVariableValueAction instance) {
		try {
			Object value_0 = name_AddVariableValueAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_AddVariableValueActionInsertAtShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_AddVariableValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_AddVariableValueActionValueShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_AddVariableValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_AddVariableValueActionInsertAtShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_AddVariableValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_AddVariableValueActionValueShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_AddVariableValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_AddVariableValueActionInsertAtShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_AddVariableValueActionInsertAtShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_AddVariableValueActionValueShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_AddVariableValueActionValueShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_BroadcastSignalAction_Shape(BroadcastSignalAction instance) {
		try {
			Object value_0 = name_BroadcastSignalAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_BroadcastSignalActionArgumentShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_BroadcastSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_BroadcastSignalActionArgumentShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_BroadcastSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_BroadcastSignalActionArgumentShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_BroadcastSignalActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CentralBufferNode_Shape(CentralBufferNode instance) {
		try {
			Object value_0 = name_CentralBufferNode_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_Shape(Constraint instance) {
		try {
			Object value_0 = name_Constraint_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = specification_Constraint_Shape(instance);
			if (value_1 != null) {
				instance.setSpecification((ValueSpecification) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_StartObjectBehaviorAction_Shape(StartObjectBehaviorAction instance) {
		try {
			Object value_0 = name_StartObjectBehaviorAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_StartObjectBehaviorActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_StartObjectBehaviorActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_StartObjectBehaviorActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_StartObjectBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_StartObjectBehaviorActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_StartObjectBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_StartObjectBehaviorActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_StartObjectBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_StartObjectBehaviorActionArgumentShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_StartObjectBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_StartObjectBehaviorActionArgumentShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_StartObjectBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_StartObjectBehaviorActionArgumentShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_StartObjectBehaviorActionArgumentShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_TestIdentityAction_Shape(TestIdentityAction instance) {
		try {
			Object value_0 = name_TestIdentityAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_TestIdentityActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_TestIdentityActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_TestIdentityActionFirstShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_TestIdentityActionFirstShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_TestIdentityActionSecondShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_TestIdentityActionSecondShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_TestIdentityActionFirstShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_TestIdentityActionFirstShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_TestIdentityActionSecondShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_TestIdentityActionSecondShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_TestIdentityActionFirstShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_TestIdentityActionFirstShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_TestIdentityActionSecondShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_TestIdentityActionSecondShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ClearStructuralFeatureAction_Shape(ClearStructuralFeatureAction instance) {
		try {
			Object value_0 = name_ClearStructuralFeatureAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ClearStructuralFeatureActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ClearStructuralFeatureActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ClearStructuralFeatureActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ClearStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ClearStructuralFeatureActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ClearStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ClearStructuralFeatureActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ClearStructuralFeatureActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CreateLinkAction_Shape(CreateLinkAction instance) {
		try {
			Object value_0 = name_CreateLinkAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_CreateLinkActionInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_CreateLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_CreateLinkActionInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_CreateLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_CreateLinkActionInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_CreateLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadLinkAction_Shape(ReadLinkAction instance) {
		try {
			Object value_0 = name_ReadLinkAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadLinkActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadLinkActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ReadLinkActionInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ReadLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ReadLinkActionInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ReadLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ReadLinkActionInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ReadLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DestroyLinkAction_Shape(DestroyLinkAction instance) {
		try {
			Object value_0 = name_DestroyLinkAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_DestroyLinkActionInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_DestroyLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_DestroyLinkActionInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_DestroyLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_DestroyLinkActionInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_DestroyLinkActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ClearAssociationAction_Shape(ClearAssociationAction instance) {
		try {
			Object value_0 = name_ClearAssociationAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ClearAssociationActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ClearAssociationActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ClearAssociationActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ClearAssociationActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ClearAssociationActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ClearAssociationActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadExtentAction_Shape(ReadExtentAction instance) {
		try {
			Object value_0 = name_ReadExtentAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadExtentActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadExtentActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReclassifyObjectAction_Shape(ReclassifyObjectAction instance) {
		try {
			Object value_0 = name_ReclassifyObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ReclassifyObjectActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ReclassifyObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ReclassifyObjectActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ReclassifyObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ReclassifyObjectActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ReclassifyObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReadIsClassifiedObjectAction_Shape(ReadIsClassifiedObjectAction instance) {
		try {
			Object value_0 = name_ReadIsClassifiedObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReadIsClassifiedObjectActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReadIsClassifiedObjectActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ReadIsClassifiedObjectActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ReadIsClassifiedObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ReadIsClassifiedObjectActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ReadIsClassifiedObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ReadIsClassifiedObjectActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ReadIsClassifiedObjectActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ReduceAction_Shape(ReduceAction instance) {
		try {
			Object value_0 = name_ReduceAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_ReduceActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_ReduceActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_ReduceActionCollectionShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_ReduceActionCollectionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_ReduceActionCollectionShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_ReduceActionCollectionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_ReduceActionCollectionShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_ReduceActionCollectionShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_StartClassifierBehaviorAction_Shape(StartClassifierBehaviorAction instance) {
		try {
			Object value_0 = name_StartClassifierBehaviorAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_StartClassifierBehaviorActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_StartClassifierBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_StartClassifierBehaviorActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_StartClassifierBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_StartClassifierBehaviorActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_StartClassifierBehaviorActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_CreateLinkObjectAction_Shape(CreateLinkObjectAction instance) {
		try {
			Object value_0 = name_CreateLinkObjectAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_CreateLinkObjectActionInputShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_CreateLinkObjectActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_CreateLinkObjectActionInputShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_CreateLinkObjectActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_CreateLinkObjectActionInputShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_CreateLinkObjectActionInputShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_CreateLinkObjectActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_CreateLinkObjectActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_UnmarshallAction_Shape(UnmarshallAction instance) {
		try {
			Object value_0 = name_UnmarshallAction_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_InputPin_UnmarshallActionObjectShape(InputPin instance) {
		try {
			Object value_0 = name_InputPin_UnmarshallActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ValuePin_UnmarshallActionObjectShape(ValuePin instance) {
		try {
			Object value_0 = name_ValuePin_UnmarshallActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ActionInputPin_UnmarshallActionObjectShape(ActionInputPin instance) {
		try {
			Object value_0 = name_ActionInputPin_UnmarshallActionObjectShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_OutputPin_UnmarshallActionResultShape(OutputPin instance) {
		try {
			Object value_0 = name_OutputPin_UnmarshallActionResultShape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ObjectFlow_Edge(ObjectFlow instance) {
		try {
			Object value_0 = name_ObjectFlow_Edge(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String name_Activity_Shape(Activity it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Parameter_ParameterLabel(Parameter it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Constraint_PreconditionLabel(Constraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Constraint_PostconditionLabel(Constraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InitialNode_Shape(InitialNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActivityFinalNode_Shape(ActivityFinalNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_FlowFinalNode_Shape(FlowFinalNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OpaqueAction_Shape(OpaqueAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_OpaqueActionInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_OpaqueActionInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_OpaqueActionInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_OpaqueActionOutputShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CallBehaviorAction_Shape(CallBehaviorAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_CallBehaviorActionArgumentShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_CallBehaviorActionArgumentShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_CallBehaviorActionArgumentShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_CallBehaviorActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CallOperationAction_Shape(CallOperationAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_CallOperationActionArgumentShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_CallOperationActionArgumentShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_CallOperationActionArgumentShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_CallOperationActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_CallOperationActionTargetShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_CallOperationActionTargetShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_CallOperationActionTargetShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DurationConstraint_LocalPreconditionShape(DurationConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_DurationConstraint_LocalPreconditionShape(DurationInterval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DurationConstraint_LocalPostconditionShape(DurationConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_DurationConstraint_LocalPostconditionShape(DurationInterval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_TimeConstraint_LocalPreconditionShape(TimeConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_TimeConstraint_LocalPreconditionShape(TimeInterval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_TimeConstraint_LocalPostconditionShape(TimeConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_TimeConstraint_LocalPostconditionShape(TimeInterval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_IntervalConstraint_LocalPreconditionShape(IntervalConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_IntervalConstraint_LocalPreconditionShape(Interval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_IntervalConstraint_LocalPostconditionShape(IntervalConstraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_IntervalConstraint_LocalPostconditionShape(Interval it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Constraint_LocalPreconditionShape(Constraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_Constraint_LocalPreconditionShape(OpaqueExpression it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Constraint_LocalPostconditionShape(Constraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_specification_Constraint_LocalPostconditionShape(OpaqueExpression it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DecisionNode_Shape(DecisionNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_MergeNode_Shape(MergeNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ForkNode_Shape(ForkNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_JoinNode_Shape(JoinNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DataStoreNode_Shape(DataStoreNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_SendObjectAction_Shape(SendObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_SendObjectActionRequestShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_SendObjectActionRequestShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_SendObjectActionRequestShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_SendObjectActionTargetShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_SendObjectActionTargetShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_SendObjectActionTargetShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_SendSignalAction_Shape(SendSignalAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_SendSignalActionArgumentShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_SendSignalActionArgumentShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_SendSignalActionArgumentShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_SendSignalActionTargetShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_SendSignalActionTargetShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_SendSignalActionTargetShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActivityParameterNode_Shape(ActivityParameterNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private Boolean isControlType_ActivityParameterNode_Shape(ActivityParameterNode it) {
		return true;
	}

	/**
	 * @generated
	 */
	private String name_AcceptEventAction_Shape(AcceptEventAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_AcceptEventActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValueSpecificationAction_Shape(ValueSpecificationAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ValueSpecificationActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ConditionalNode_Shape(ConditionalNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ExpansionRegion_Shape(ExpansionRegion it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ExpansionNode_InputShape(ExpansionNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ExpansionNode_OutputShape(ExpansionNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_LoopNode_Shape(LoopNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_LoopNodeVariableInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_LoopNodeVariableInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_LoopNodeVariableInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_LoopNodeBodyOutputShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_LoopNodeVariableShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_LoopNodeResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_SequenceNode_Shape(SequenceNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_StructuredActivityNode_Shape(StructuredActivityNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_StructuredActivityNodeInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_StructuredActivityNodeInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_StructuredActivityNodeInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_StructuredActivityNodeOutputShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActivityPartition_Shape(ActivityPartition it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadSelfAction_Shape(ReadSelfAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadSelfActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Activity_Shape_CN(Activity it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CreateObjectAction_Shape(CreateObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_CreateObjectActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadStructuralFeatureAction_Shape(ReadStructuralFeatureAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ReadStructuralFeatureActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ReadStructuralFeatureActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ReadStructuralFeatureActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadStructuralFeatureActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_AddStructuralFeatureValueAction_Shape(AddStructuralFeatureValueAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_AddStructuralFeatureValueActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_AddStructuralFeatureValueActionValueShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_AddStructuralFeatureValueActionInsertAtShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_AddStructuralFeatureValueActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_AddStructuralFeatureValueActionValueShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_AddStructuralFeatureValueActionInsertAtShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_AddStructuralFeatureValueActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_AddStructuralFeatureValueActionValueShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_AddStructuralFeatureValueActionInsertAtShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_AddStructuralFeatureValueActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DestroyObjectAction_Shape(DestroyObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_DestroyObjectActionTargetShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_DestroyObjectActionTargetShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_DestroyObjectActionTargetShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadVariableAction_Shape(ReadVariableAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadVariableActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_AddVariableValueAction_Shape(AddVariableValueAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_AddVariableValueActionInsertAtShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_AddVariableValueActionValueShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_AddVariableValueActionInsertAtShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_AddVariableValueActionValueShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_AddVariableValueActionInsertAtShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_AddVariableValueActionValueShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_BroadcastSignalAction_Shape(BroadcastSignalAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_BroadcastSignalActionArgumentShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_BroadcastSignalActionArgumentShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_BroadcastSignalActionArgumentShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CentralBufferNode_Shape(CentralBufferNode it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_Constraint_Shape(Constraint it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private ValueSpecification specification_Constraint_Shape(Constraint it) {
		LiteralString literalString = UMLFactory.eINSTANCE.createLiteralString();
		literalString.setValue(""); //$NON-NLS-1$
		return literalString;
	}

	/**
	 * @generated
	 */
	private String name_StartObjectBehaviorAction_Shape(StartObjectBehaviorAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_StartObjectBehaviorActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_StartObjectBehaviorActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_StartObjectBehaviorActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_StartObjectBehaviorActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_StartObjectBehaviorActionArgumentShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_StartObjectBehaviorActionArgumentShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_StartObjectBehaviorActionArgumentShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_TestIdentityAction_Shape(TestIdentityAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_TestIdentityActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_TestIdentityActionFirstShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_TestIdentityActionSecondShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_TestIdentityActionFirstShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_TestIdentityActionSecondShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_TestIdentityActionFirstShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_TestIdentityActionSecondShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ClearStructuralFeatureAction_Shape(ClearStructuralFeatureAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ClearStructuralFeatureActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ClearStructuralFeatureActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ClearStructuralFeatureActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ClearStructuralFeatureActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CreateLinkAction_Shape(CreateLinkAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_CreateLinkActionInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_CreateLinkActionInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_CreateLinkActionInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadLinkAction_Shape(ReadLinkAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadLinkActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ReadLinkActionInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ReadLinkActionInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ReadLinkActionInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_DestroyLinkAction_Shape(DestroyLinkAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_DestroyLinkActionInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_DestroyLinkActionInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_DestroyLinkActionInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ClearAssociationAction_Shape(ClearAssociationAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ClearAssociationActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ClearAssociationActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ClearAssociationActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadExtentAction_Shape(ReadExtentAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadExtentActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReclassifyObjectAction_Shape(ReclassifyObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ReclassifyObjectActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ReclassifyObjectActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ReclassifyObjectActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReadIsClassifiedObjectAction_Shape(ReadIsClassifiedObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReadIsClassifiedObjectActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ReadIsClassifiedObjectActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ReadIsClassifiedObjectActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ReadIsClassifiedObjectActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ReduceAction_Shape(ReduceAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_ReduceActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_ReduceActionCollectionShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_ReduceActionCollectionShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_ReduceActionCollectionShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_StartClassifierBehaviorAction_Shape(StartClassifierBehaviorAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_StartClassifierBehaviorActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_StartClassifierBehaviorActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_StartClassifierBehaviorActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_CreateLinkObjectAction_Shape(CreateLinkObjectAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_CreateLinkObjectActionInputShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_CreateLinkObjectActionInputShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_CreateLinkObjectActionInputShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_CreateLinkObjectActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_UnmarshallAction_Shape(UnmarshallAction it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_InputPin_UnmarshallActionObjectShape(InputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ValuePin_UnmarshallActionObjectShape(ValuePin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ActionInputPin_UnmarshallActionObjectShape(ActionInputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_OutputPin_UnmarshallActionResultShape(OutputPin it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ObjectFlow_Edge(ObjectFlow it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated
	 */
	private String name_ControlFlow_Edge(ControlFlow it) {
		return LabelHelper.INSTANCE.findName(it.eContainer(), it);
	}

	/**
	 * @generated NOT
	 */
	public void init_ControlFlow_Edge(ControlFlow instance) {
		try {
			Object value_0 = name_ControlFlow_Edge(instance);
			instance.setName((String) value_0);
			// Initialize the guard to true
			LiteralBoolean lBoolean = UMLFactory.eINSTANCE.createLiteralBoolean();
			lBoolean.setValue(true);
			instance.setGuard(lBoolean);
			// Initialize the weight to 0
			LiteralInteger lInteger = UMLFactory.eINSTANCE.createLiteralInteger();
			lInteger.setValue(0);
			instance.setWeight(lInteger);
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = UMLDiagramEditorPlugin.getInstance().getElementInitializers();
		if (cached == null) {
			UMLDiagramEditorPlugin.getInstance().setElementInitializers(cached = new ElementInitializers());
		}
		return cached;
	}
}
