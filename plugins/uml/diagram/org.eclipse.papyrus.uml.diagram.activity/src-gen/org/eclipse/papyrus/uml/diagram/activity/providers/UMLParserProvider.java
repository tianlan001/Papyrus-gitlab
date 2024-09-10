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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.AcceptEventActionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.AcceptTimeEventActionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ActivityEdgeWeightParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ActivityParameterNodeParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ActivitySingleExecutionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.CallBehaviorActionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.CallOperationActionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.DecisionInputFlowParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.EdgeGuardParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ExceptionHandlerTypeParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.InputDecisionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.JoinSpecParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ObjectFlowSelectionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ObjectFlowTransformationParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ObjectNodeParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ObjectNodeSelectionParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.ParameterParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.PinParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.PinValueParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.PostConditionConstraintLabelParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.PreConditionConstraintLabelParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.StreamLabelParser;
import org.eclipse.papyrus.uml.diagram.activity.parser.custom.StructuredActivityNodeKeywordParser;
import org.eclipse.papyrus.uml.diagram.activity.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser activity_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_NameLabel_Parser() {
		if (activity_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activity_NameLabel_Parser = parser;
		}
		return activity_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ActivitySingleExecutionParser activity_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_KeywordLabel_Parser() {
		if (activity_KeywordLabel_Parser == null) {
			activity_KeywordLabel_Parser = new ActivitySingleExecutionParser();
		}
		return activity_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ParameterParser parameter_ParameterLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getParameter_ParameterLabel_Parser() {
		if (parameter_ParameterLabel_Parser == null) {
			parameter_ParameterLabel_Parser = new ParameterParser();
		}
		return parameter_ParameterLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PreConditionConstraintLabelParser constraint_PreconditionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_PreconditionLabel_Parser() {
		if (constraint_PreconditionLabel_Parser == null) {
			constraint_PreconditionLabel_Parser = new PreConditionConstraintLabelParser();
		}
		return constraint_PreconditionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PostConditionConstraintLabelParser constraint_PostconditionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_PostconditionLabel_Parser() {
		if (constraint_PostconditionLabel_Parser == null) {
			constraint_PostconditionLabel_Parser = new PostConditionConstraintLabelParser();
		}
		return constraint_PostconditionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser initialNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInitialNode_FloatingNameLabel_Parser() {
		if (initialNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			initialNode_FloatingNameLabel_Parser = parser;
		}
		return initialNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser initialNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInitialNode_StereotypeLabel_Parser() {
		if (initialNode_StereotypeLabel_Parser == null) {
			initialNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return initialNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activityFinalNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityFinalNode_FloatingNameLabel_Parser() {
		if (activityFinalNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activityFinalNode_FloatingNameLabel_Parser = parser;
		}
		return activityFinalNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser activityFinalNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityFinalNode_StereotypeLabel_Parser() {
		if (activityFinalNode_StereotypeLabel_Parser == null) {
			activityFinalNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return activityFinalNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser flowFinalNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFlowFinalNode_FloatingNameLabel_Parser() {
		if (flowFinalNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			flowFinalNode_FloatingNameLabel_Parser = parser;
		}
		return flowFinalNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser flowFinalNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFlowFinalNode_StereotypeLabel_Parser() {
		if (flowFinalNode_StereotypeLabel_Parser == null) {
			flowFinalNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return flowFinalNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueAction_NameLabel_Parser() {
		if (opaqueAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			opaqueAction_NameLabel_Parser = parser;
		}
		return opaqueAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueAction_FloatingNameLabel_Parser() {
		if (opaqueAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			opaqueAction_FloatingNameLabel_Parser = parser;
		}
		return opaqueAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_OpaqueActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_OpaqueActionInputNameLabel_Parser() {
		if (valuePin_OpaqueActionInputNameLabel_Parser == null) {
			valuePin_OpaqueActionInputNameLabel_Parser = new PinParser();
		}
		return valuePin_OpaqueActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_OpaqueActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_OpaqueActionInputValueLabel_Parser() {
		if (valuePin_OpaqueActionInputValueLabel_Parser == null) {
			valuePin_OpaqueActionInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_OpaqueActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_OpaqueActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_OpaqueActionInputStereotypeLabel_Parser() {
		if (valuePin_OpaqueActionInputStereotypeLabel_Parser == null) {
			valuePin_OpaqueActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_OpaqueActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_OpaqueActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_OpaqueActionInputNameLabel_Parser() {
		if (actionInputPin_OpaqueActionInputNameLabel_Parser == null) {
			actionInputPin_OpaqueActionInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_OpaqueActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_OpaqueActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_OpaqueActionInputValueLabel_Parser() {
		if (actionInputPin_OpaqueActionInputValueLabel_Parser == null) {
			actionInputPin_OpaqueActionInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_OpaqueActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_OpaqueActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_OpaqueActionInputStereotypeLabel_Parser() {
		if (actionInputPin_OpaqueActionInputStereotypeLabel_Parser == null) {
			actionInputPin_OpaqueActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_OpaqueActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_OpaqueActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_OpaqueActionInputNameLabel_Parser() {
		if (inputPin_OpaqueActionInputNameLabel_Parser == null) {
			inputPin_OpaqueActionInputNameLabel_Parser = new PinParser();
		}
		return inputPin_OpaqueActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_OpaqueActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_OpaqueActionInputStereotypeLabel_Parser() {
		if (inputPin_OpaqueActionInputStereotypeLabel_Parser == null) {
			inputPin_OpaqueActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_OpaqueActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_OpaqueActionOutputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_OpaqueActionOutputNameLabel_Parser() {
		if (outputPin_OpaqueActionOutputNameLabel_Parser == null) {
			outputPin_OpaqueActionOutputNameLabel_Parser = new PinParser();
		}
		return outputPin_OpaqueActionOutputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_OpaqueActionOutputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_OpaqueActionOutputStereotypeLabel_Parser() {
		if (outputPin_OpaqueActionOutputStereotypeLabel_Parser == null) {
			outputPin_OpaqueActionOutputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_OpaqueActionOutputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private CallBehaviorActionParser callBehaviorAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallBehaviorAction_NameLabel_Parser() {
		if (callBehaviorAction_NameLabel_Parser == null) {
			callBehaviorAction_NameLabel_Parser = new CallBehaviorActionParser();
		}
		return callBehaviorAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser callBehaviorAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallBehaviorAction_FloatingNameLabel_Parser() {
		if (callBehaviorAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			callBehaviorAction_FloatingNameLabel_Parser = parser;
		}
		return callBehaviorAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_CallBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallBehaviorActionArgumentNameLabel_Parser() {
		if (valuePin_CallBehaviorActionArgumentNameLabel_Parser == null) {
			valuePin_CallBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return valuePin_CallBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_CallBehaviorActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallBehaviorActionArgumentValueLabel_Parser() {
		if (valuePin_CallBehaviorActionArgumentValueLabel_Parser == null) {
			valuePin_CallBehaviorActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return valuePin_CallBehaviorActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_CallBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallBehaviorActionArgumentStereotypeLabel_Parser() {
		if (valuePin_CallBehaviorActionArgumentStereotypeLabel_Parser == null) {
			valuePin_CallBehaviorActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_CallBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_CallBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallBehaviorActionArgumentNameLabel_Parser() {
		if (actionInputPin_CallBehaviorActionArgumentNameLabel_Parser == null) {
			actionInputPin_CallBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return actionInputPin_CallBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_CallBehaviorActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallBehaviorActionArgumentValueLabel_Parser() {
		if (actionInputPin_CallBehaviorActionArgumentValueLabel_Parser == null) {
			actionInputPin_CallBehaviorActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_CallBehaviorActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser() {
		if (actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser == null) {
			actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_CallBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallBehaviorActionArgumentNameLabel_Parser() {
		if (inputPin_CallBehaviorActionArgumentNameLabel_Parser == null) {
			inputPin_CallBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return inputPin_CallBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_CallBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser() {
		if (inputPin_CallBehaviorActionArgumentStereotypeLabel_Parser == null) {
			inputPin_CallBehaviorActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_CallBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_CallBehaviorActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CallBehaviorActionResultNameLabel_Parser() {
		if (outputPin_CallBehaviorActionResultNameLabel_Parser == null) {
			outputPin_CallBehaviorActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_CallBehaviorActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_CallBehaviorActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CallBehaviorActionResultStereotypeLabel_Parser() {
		if (outputPin_CallBehaviorActionResultStereotypeLabel_Parser == null) {
			outputPin_CallBehaviorActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_CallBehaviorActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private CallOperationActionParser callOperationAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallOperationAction_NameLabel_Parser() {
		if (callOperationAction_NameLabel_Parser == null) {
			callOperationAction_NameLabel_Parser = new CallOperationActionParser();
		}
		return callOperationAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser callOperationAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallOperationAction_FloatingNameLabel_Parser() {
		if (callOperationAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			callOperationAction_FloatingNameLabel_Parser = parser;
		}
		return callOperationAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_CallOperationActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionArgumentNameLabel_Parser() {
		if (actionInputPin_CallOperationActionArgumentNameLabel_Parser == null) {
			actionInputPin_CallOperationActionArgumentNameLabel_Parser = new PinParser();
		}
		return actionInputPin_CallOperationActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_CallOperationActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionArgumentValueLabel_Parser() {
		if (actionInputPin_CallOperationActionArgumentValueLabel_Parser == null) {
			actionInputPin_CallOperationActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_CallOperationActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_CallOperationActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionArgumentStereotypeLabel_Parser() {
		if (actionInputPin_CallOperationActionArgumentStereotypeLabel_Parser == null) {
			actionInputPin_CallOperationActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_CallOperationActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_CallOperationActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionArgumentNameLabel_Parser() {
		if (valuePin_CallOperationActionArgumentNameLabel_Parser == null) {
			valuePin_CallOperationActionArgumentNameLabel_Parser = new PinParser();
		}
		return valuePin_CallOperationActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_CallOperationActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionArgumentValueLabel_Parser() {
		if (valuePin_CallOperationActionArgumentValueLabel_Parser == null) {
			valuePin_CallOperationActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return valuePin_CallOperationActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_CallOperationActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionArgumentStereotypeLabel_Parser() {
		if (valuePin_CallOperationActionArgumentStereotypeLabel_Parser == null) {
			valuePin_CallOperationActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_CallOperationActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_CallOperationActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallOperationActionArgumentNameLabel_Parser() {
		if (inputPin_CallOperationActionArgumentNameLabel_Parser == null) {
			inputPin_CallOperationActionArgumentNameLabel_Parser = new PinParser();
		}
		return inputPin_CallOperationActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_CallOperationActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallOperationActionArgumentStereotypeLabel_Parser() {
		if (inputPin_CallOperationActionArgumentStereotypeLabel_Parser == null) {
			inputPin_CallOperationActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_CallOperationActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_CallOperationActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CallOperationActionResultNameLabel_Parser() {
		if (outputPin_CallOperationActionResultNameLabel_Parser == null) {
			outputPin_CallOperationActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_CallOperationActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_CallOperationActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CallOperationActionResultStereotypeLabel_Parser() {
		if (outputPin_CallOperationActionResultStereotypeLabel_Parser == null) {
			outputPin_CallOperationActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_CallOperationActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_CallOperationActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionTargetNameLabel_Parser() {
		if (valuePin_CallOperationActionTargetNameLabel_Parser == null) {
			valuePin_CallOperationActionTargetNameLabel_Parser = new PinParser();
		}
		return valuePin_CallOperationActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_CallOperationActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionTargetValueLabel_Parser() {
		if (valuePin_CallOperationActionTargetValueLabel_Parser == null) {
			valuePin_CallOperationActionTargetValueLabel_Parser = new PinValueParser();
		}
		return valuePin_CallOperationActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_CallOperationActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CallOperationActionTargetStereotypeLabel_Parser() {
		if (valuePin_CallOperationActionTargetStereotypeLabel_Parser == null) {
			valuePin_CallOperationActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_CallOperationActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_CallOperationActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionTargetNameLabel_Parser() {
		if (actionInputPin_CallOperationActionTargetNameLabel_Parser == null) {
			actionInputPin_CallOperationActionTargetNameLabel_Parser = new PinParser();
		}
		return actionInputPin_CallOperationActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_CallOperationActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionTargetValueLabel_Parser() {
		if (actionInputPin_CallOperationActionTargetValueLabel_Parser == null) {
			actionInputPin_CallOperationActionTargetValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_CallOperationActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_CallOperationActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CallOperationActionTargetStereotypeLabel_Parser() {
		if (actionInputPin_CallOperationActionTargetStereotypeLabel_Parser == null) {
			actionInputPin_CallOperationActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_CallOperationActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_CallOperationActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallOperationActionTargetNameLabel_Parser() {
		if (inputPin_CallOperationActionTargetNameLabel_Parser == null) {
			inputPin_CallOperationActionTargetNameLabel_Parser = new PinParser();
		}
		return inputPin_CallOperationActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_CallOperationActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CallOperationActionTargetStereotypeLabel_Parser() {
		if (inputPin_CallOperationActionTargetStereotypeLabel_Parser == null) {
			inputPin_CallOperationActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_CallOperationActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationConstraint_LocalPreconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_LocalPreconditionNameLabel_Parser() {
		if (durationConstraint_LocalPreconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationConstraint_LocalPreconditionNameLabel_Parser = parser;
		}
		return durationConstraint_LocalPreconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser durationConstraint_LocalPreconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_LocalPreconditionBodyLabel_Parser() {
		if (durationConstraint_LocalPreconditionBodyLabel_Parser == null) {
			durationConstraint_LocalPreconditionBodyLabel_Parser = new ConstraintParser();
		}
		return durationConstraint_LocalPreconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationConstraint_LocalPostconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_LocalPostconditionNameLabel_Parser() {
		if (durationConstraint_LocalPostconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationConstraint_LocalPostconditionNameLabel_Parser = parser;
		}
		return durationConstraint_LocalPostconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser durationConstraint_LocalPostconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_LocalPostconditionBodyLabel_Parser() {
		if (durationConstraint_LocalPostconditionBodyLabel_Parser == null) {
			durationConstraint_LocalPostconditionBodyLabel_Parser = new ConstraintParser();
		}
		return durationConstraint_LocalPostconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeConstraint_LocalPreconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_LocalPreconditionNameLabel_Parser() {
		if (timeConstraint_LocalPreconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeConstraint_LocalPreconditionNameLabel_Parser = parser;
		}
		return timeConstraint_LocalPreconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser timeConstraint_LocalPreconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_LocalPreconditionBodyLabel_Parser() {
		if (timeConstraint_LocalPreconditionBodyLabel_Parser == null) {
			timeConstraint_LocalPreconditionBodyLabel_Parser = new ConstraintParser();
		}
		return timeConstraint_LocalPreconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeConstraint_LocalPostconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_LocalPostconditionNameLabel_Parser() {
		if (timeConstraint_LocalPostconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeConstraint_LocalPostconditionNameLabel_Parser = parser;
		}
		return timeConstraint_LocalPostconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser timeConstraint_LocalPostconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_LocalPostconditionBodyLabel_Parser() {
		if (timeConstraint_LocalPostconditionBodyLabel_Parser == null) {
			timeConstraint_LocalPostconditionBodyLabel_Parser = new ConstraintParser();
		}
		return timeConstraint_LocalPostconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser intervalConstraint_LocalPreconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_LocalPreconditionNameLabel_Parser() {
		if (intervalConstraint_LocalPreconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			intervalConstraint_LocalPreconditionNameLabel_Parser = parser;
		}
		return intervalConstraint_LocalPreconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser intervalConstraint_LocalPreconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_LocalPreconditionBodyLabel_Parser() {
		if (intervalConstraint_LocalPreconditionBodyLabel_Parser == null) {
			intervalConstraint_LocalPreconditionBodyLabel_Parser = new ConstraintParser();
		}
		return intervalConstraint_LocalPreconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser intervalConstraint_LocalPostconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_LocalPostconditionNameLabel_Parser() {
		if (intervalConstraint_LocalPostconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			intervalConstraint_LocalPostconditionNameLabel_Parser = parser;
		}
		return intervalConstraint_LocalPostconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser intervalConstraint_LocalPostconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_LocalPostconditionBodyLabel_Parser() {
		if (intervalConstraint_LocalPostconditionBodyLabel_Parser == null) {
			intervalConstraint_LocalPostconditionBodyLabel_Parser = new ConstraintParser();
		}
		return intervalConstraint_LocalPostconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_LocalPreconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_LocalPreconditionNameLabel_Parser() {
		if (constraint_LocalPreconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_LocalPreconditionNameLabel_Parser = parser;
		}
		return constraint_LocalPreconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_LocalPreconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_LocalPreconditionBodyLabel_Parser() {
		if (constraint_LocalPreconditionBodyLabel_Parser == null) {
			constraint_LocalPreconditionBodyLabel_Parser = new ConstraintParser();
		}
		return constraint_LocalPreconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_LocalPostconditionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_LocalPostconditionNameLabel_Parser() {
		if (constraint_LocalPostconditionNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_LocalPostconditionNameLabel_Parser = parser;
		}
		return constraint_LocalPostconditionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_LocalPostconditionBodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_LocalPostconditionBodyLabel_Parser() {
		if (constraint_LocalPostconditionBodyLabel_Parser == null) {
			constraint_LocalPostconditionBodyLabel_Parser = new ConstraintParser();
		}
		return constraint_LocalPostconditionBodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser decisionNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDecisionNode_FloatingNameLabel_Parser() {
		if (decisionNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			decisionNode_FloatingNameLabel_Parser = parser;
		}
		return decisionNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private InputDecisionParser decisionNode_DecisionInputLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDecisionNode_DecisionInputLabel_Parser() {
		if (decisionNode_DecisionInputLabel_Parser == null) {
			decisionNode_DecisionInputLabel_Parser = new InputDecisionParser();
		}
		return decisionNode_DecisionInputLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser decisionNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDecisionNode_StereotypeLabel_Parser() {
		if (decisionNode_StereotypeLabel_Parser == null) {
			decisionNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return decisionNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser mergeNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMergeNode_FloatingNameLabel_Parser() {
		if (mergeNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			mergeNode_FloatingNameLabel_Parser = parser;
		}
		return mergeNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser mergeNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getMergeNode_StereotypeLabel_Parser() {
		if (mergeNode_StereotypeLabel_Parser == null) {
			mergeNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return mergeNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser forkNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getForkNode_FloatingNameLabel_Parser() {
		if (forkNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			forkNode_FloatingNameLabel_Parser = parser;
		}
		return forkNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser forkNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getForkNode_StereotypeLabel_Parser() {
		if (forkNode_StereotypeLabel_Parser == null) {
			forkNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return forkNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser joinNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getJoinNode_FloatingNameLabel_Parser() {
		if (joinNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			joinNode_FloatingNameLabel_Parser = parser;
		}
		return joinNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private JoinSpecParser joinNode_JoinSpecLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getJoinNode_JoinSpecLabel_Parser() {
		if (joinNode_JoinSpecLabel_Parser == null) {
			joinNode_JoinSpecLabel_Parser = new JoinSpecParser();
		}
		return joinNode_JoinSpecLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser joinNode_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getJoinNode_StereotypeLabel_Parser() {
		if (joinNode_StereotypeLabel_Parser == null) {
			joinNode_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return joinNode_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectNodeParser dataStoreNode_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataStoreNode_NameLabel_Parser() {
		if (dataStoreNode_NameLabel_Parser == null) {
			dataStoreNode_NameLabel_Parser = new ObjectNodeParser();
		}
		return dataStoreNode_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectNodeSelectionParser dataStoreNode_SelectionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataStoreNode_SelectionLabel_Parser() {
		if (dataStoreNode_SelectionLabel_Parser == null) {
			dataStoreNode_SelectionLabel_Parser = new ObjectNodeSelectionParser();
		}
		return dataStoreNode_SelectionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataStoreNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataStoreNode_FloatingNameLabel_Parser() {
		if (dataStoreNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataStoreNode_FloatingNameLabel_Parser = parser;
		}
		return dataStoreNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser sendObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSendObjectAction_NameLabel_Parser() {
		if (sendObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			sendObjectAction_NameLabel_Parser = parser;
		}
		return sendObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser sendObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSendObjectAction_FloatingNameLabel_Parser() {
		if (sendObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			sendObjectAction_FloatingNameLabel_Parser = parser;
		}
		return sendObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_SendObjectActionRequestNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionRequestNameLabel_Parser() {
		if (valuePin_SendObjectActionRequestNameLabel_Parser == null) {
			valuePin_SendObjectActionRequestNameLabel_Parser = new PinParser();
		}
		return valuePin_SendObjectActionRequestNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_SendObjectActionRequestValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionRequestValueLabel_Parser() {
		if (valuePin_SendObjectActionRequestValueLabel_Parser == null) {
			valuePin_SendObjectActionRequestValueLabel_Parser = new PinValueParser();
		}
		return valuePin_SendObjectActionRequestValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_SendObjectActionRequestStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionRequestStereotypeLabel_Parser() {
		if (valuePin_SendObjectActionRequestStereotypeLabel_Parser == null) {
			valuePin_SendObjectActionRequestStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_SendObjectActionRequestStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_SendObjectActionRequestNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionRequestNameLabel_Parser() {
		if (actionInputPin_SendObjectActionRequestNameLabel_Parser == null) {
			actionInputPin_SendObjectActionRequestNameLabel_Parser = new PinParser();
		}
		return actionInputPin_SendObjectActionRequestNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_SendObjectActionRequestValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionRequestValueLabel_Parser() {
		if (actionInputPin_SendObjectActionRequestValueLabel_Parser == null) {
			actionInputPin_SendObjectActionRequestValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_SendObjectActionRequestValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_SendObjectActionRequestStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionRequestStereotypeLabel_Parser() {
		if (actionInputPin_SendObjectActionRequestStereotypeLabel_Parser == null) {
			actionInputPin_SendObjectActionRequestStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_SendObjectActionRequestStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_SendObjectActionRequestNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendObjectActionRequestNameLabel_Parser() {
		if (inputPin_SendObjectActionRequestNameLabel_Parser == null) {
			inputPin_SendObjectActionRequestNameLabel_Parser = new PinParser();
		}
		return inputPin_SendObjectActionRequestNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_SendObjectActionRequestStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendObjectActionRequestStereotypeLabel_Parser() {
		if (inputPin_SendObjectActionRequestStereotypeLabel_Parser == null) {
			inputPin_SendObjectActionRequestStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_SendObjectActionRequestStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_SendObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionTargetNameLabel_Parser() {
		if (valuePin_SendObjectActionTargetNameLabel_Parser == null) {
			valuePin_SendObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return valuePin_SendObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_SendObjectActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionTargetValueLabel_Parser() {
		if (valuePin_SendObjectActionTargetValueLabel_Parser == null) {
			valuePin_SendObjectActionTargetValueLabel_Parser = new PinValueParser();
		}
		return valuePin_SendObjectActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_SendObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendObjectActionTargetStereotypeLabel_Parser() {
		if (valuePin_SendObjectActionTargetStereotypeLabel_Parser == null) {
			valuePin_SendObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_SendObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_SendObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionTargetNameLabel_Parser() {
		if (actionInputPin_SendObjectActionTargetNameLabel_Parser == null) {
			actionInputPin_SendObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return actionInputPin_SendObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_SendObjectActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionTargetValueLabel_Parser() {
		if (actionInputPin_SendObjectActionTargetValueLabel_Parser == null) {
			actionInputPin_SendObjectActionTargetValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_SendObjectActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_SendObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendObjectActionTargetStereotypeLabel_Parser() {
		if (actionInputPin_SendObjectActionTargetStereotypeLabel_Parser == null) {
			actionInputPin_SendObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_SendObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_SendObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendObjectActionTargetNameLabel_Parser() {
		if (inputPin_SendObjectActionTargetNameLabel_Parser == null) {
			inputPin_SendObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return inputPin_SendObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_SendObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendObjectActionTargetStereotypeLabel_Parser() {
		if (inputPin_SendObjectActionTargetStereotypeLabel_Parser == null) {
			inputPin_SendObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_SendObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser sendSignalAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSendSignalAction_NameLabel_Parser() {
		if (sendSignalAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			sendSignalAction_NameLabel_Parser = parser;
		}
		return sendSignalAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser sendSignalAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSendSignalAction_FloatingNameLabel_Parser() {
		if (sendSignalAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			sendSignalAction_FloatingNameLabel_Parser = parser;
		}
		return sendSignalAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_SendSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionArgumentNameLabel_Parser() {
		if (actionInputPin_SendSignalActionArgumentNameLabel_Parser == null) {
			actionInputPin_SendSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return actionInputPin_SendSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_SendSignalActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionArgumentValueLabel_Parser() {
		if (actionInputPin_SendSignalActionArgumentValueLabel_Parser == null) {
			actionInputPin_SendSignalActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_SendSignalActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_SendSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionArgumentStereotypeLabel_Parser() {
		if (actionInputPin_SendSignalActionArgumentStereotypeLabel_Parser == null) {
			actionInputPin_SendSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_SendSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_SendSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionArgumentNameLabel_Parser() {
		if (valuePin_SendSignalActionArgumentNameLabel_Parser == null) {
			valuePin_SendSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return valuePin_SendSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_SendSignalActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionArgumentValueLabel_Parser() {
		if (valuePin_SendSignalActionArgumentValueLabel_Parser == null) {
			valuePin_SendSignalActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return valuePin_SendSignalActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_SendSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionArgumentStereotypeLabel_Parser() {
		if (valuePin_SendSignalActionArgumentStereotypeLabel_Parser == null) {
			valuePin_SendSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_SendSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_SendSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendSignalActionArgumentNameLabel_Parser() {
		if (inputPin_SendSignalActionArgumentNameLabel_Parser == null) {
			inputPin_SendSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return inputPin_SendSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_SendSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendSignalActionArgumentStereotypeLabel_Parser() {
		if (inputPin_SendSignalActionArgumentStereotypeLabel_Parser == null) {
			inputPin_SendSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_SendSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_SendSignalActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionTargetNameLabel_Parser() {
		if (valuePin_SendSignalActionTargetNameLabel_Parser == null) {
			valuePin_SendSignalActionTargetNameLabel_Parser = new PinParser();
		}
		return valuePin_SendSignalActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_SendSignalActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionTargetValueLabel_Parser() {
		if (valuePin_SendSignalActionTargetValueLabel_Parser == null) {
			valuePin_SendSignalActionTargetValueLabel_Parser = new PinValueParser();
		}
		return valuePin_SendSignalActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_SendSignalActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_SendSignalActionTargetStereotypeLabel_Parser() {
		if (valuePin_SendSignalActionTargetStereotypeLabel_Parser == null) {
			valuePin_SendSignalActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_SendSignalActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_SendSignalActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionTargetNameLabel_Parser() {
		if (actionInputPin_SendSignalActionTargetNameLabel_Parser == null) {
			actionInputPin_SendSignalActionTargetNameLabel_Parser = new PinParser();
		}
		return actionInputPin_SendSignalActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_SendSignalActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionTargetValueLabel_Parser() {
		if (actionInputPin_SendSignalActionTargetValueLabel_Parser == null) {
			actionInputPin_SendSignalActionTargetValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_SendSignalActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_SendSignalActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_SendSignalActionTargetStereotypeLabel_Parser() {
		if (actionInputPin_SendSignalActionTargetStereotypeLabel_Parser == null) {
			actionInputPin_SendSignalActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_SendSignalActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_SendSignalActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendSignalActionTargetNameLabel_Parser() {
		if (inputPin_SendSignalActionTargetNameLabel_Parser == null) {
			inputPin_SendSignalActionTargetNameLabel_Parser = new PinParser();
		}
		return inputPin_SendSignalActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_SendSignalActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_SendSignalActionTargetStereotypeLabel_Parser() {
		if (inputPin_SendSignalActionTargetStereotypeLabel_Parser == null) {
			inputPin_SendSignalActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_SendSignalActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ActivityParameterNodeParser activityParameterNode_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityParameterNode_NameLabel_Parser() {
		if (activityParameterNode_NameLabel_Parser == null) {
			activityParameterNode_NameLabel_Parser = new ActivityParameterNodeParser();
		}
		return activityParameterNode_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StreamLabelParser activityParameterNode_StreamLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityParameterNode_StreamLabel_Parser() {
		if (activityParameterNode_StreamLabel_Parser == null) {
			activityParameterNode_StreamLabel_Parser = new StreamLabelParser();
		}
		return activityParameterNode_StreamLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AcceptEventActionParser acceptEventAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAcceptEventAction_NameLabel_Parser() {
		if (acceptEventAction_NameLabel_Parser == null) {
			acceptEventAction_NameLabel_Parser = new AcceptEventActionParser();
		}
		return acceptEventAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AcceptTimeEventActionParser acceptEventAction_TriggerLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAcceptEventAction_TriggerLabel_Parser() {
		if (acceptEventAction_TriggerLabel_Parser == null) {
			acceptEventAction_TriggerLabel_Parser = new AcceptTimeEventActionParser();
		}
		return acceptEventAction_TriggerLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser acceptEventAction_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAcceptEventAction_StereotypeLabel_Parser() {
		if (acceptEventAction_StereotypeLabel_Parser == null) {
			acceptEventAction_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return acceptEventAction_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser acceptEventAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAcceptEventAction_FloatingNameLabel_Parser() {
		if (acceptEventAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			acceptEventAction_FloatingNameLabel_Parser = parser;
		}
		return acceptEventAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_AcceptEventActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_AcceptEventActionResultNameLabel_Parser() {
		if (outputPin_AcceptEventActionResultNameLabel_Parser == null) {
			outputPin_AcceptEventActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_AcceptEventActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_AcceptEventActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_AcceptEventActionResultStereotypeLabel_Parser() {
		if (outputPin_AcceptEventActionResultStereotypeLabel_Parser == null) {
			outputPin_AcceptEventActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_AcceptEventActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser valueSpecificationAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValueSpecificationAction_NameLabel_Parser() {
		if (valueSpecificationAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			valueSpecificationAction_NameLabel_Parser = parser;
		}
		return valueSpecificationAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser valueSpecificationAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValueSpecificationAction_FloatingNameLabel_Parser() {
		if (valueSpecificationAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			valueSpecificationAction_FloatingNameLabel_Parser = parser;
		}
		return valueSpecificationAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ValueSpecificationActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ValueSpecificationActionResultNameLabel_Parser() {
		if (outputPin_ValueSpecificationActionResultNameLabel_Parser == null) {
			outputPin_ValueSpecificationActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ValueSpecificationActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ValueSpecificationActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ValueSpecificationActionResultStereotypeLabel_Parser() {
		if (outputPin_ValueSpecificationActionResultStereotypeLabel_Parser == null) {
			outputPin_ValueSpecificationActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ValueSpecificationActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StructuredActivityNodeKeywordParser conditionalNode_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConditionalNode_KeywordLabel_Parser() {
		if (conditionalNode_KeywordLabel_Parser == null) {
			conditionalNode_KeywordLabel_Parser = new StructuredActivityNodeKeywordParser();
		}
		return conditionalNode_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StructuredActivityNodeKeywordParser expansionRegion_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExpansionRegion_KeywordLabel_Parser() {
		if (expansionRegion_KeywordLabel_Parser == null) {
			expansionRegion_KeywordLabel_Parser = new StructuredActivityNodeKeywordParser();
		}
		return expansionRegion_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StructuredActivityNodeKeywordParser loopNode_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLoopNode_KeywordLabel_Parser() {
		if (loopNode_KeywordLabel_Parser == null) {
			loopNode_KeywordLabel_Parser = new StructuredActivityNodeKeywordParser();
		}
		return loopNode_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_LoopNodeVariableInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_LoopNodeVariableInputNameLabel_Parser() {
		if (inputPin_LoopNodeVariableInputNameLabel_Parser == null) {
			inputPin_LoopNodeVariableInputNameLabel_Parser = new PinParser();
		}
		return inputPin_LoopNodeVariableInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_LoopNodeVariableInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_LoopNodeVariableInputStereotypeLabel_Parser() {
		if (inputPin_LoopNodeVariableInputStereotypeLabel_Parser == null) {
			inputPin_LoopNodeVariableInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_LoopNodeVariableInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_LoopNodeVariableInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_LoopNodeVariableInputNameLabel_Parser() {
		if (valuePin_LoopNodeVariableInputNameLabel_Parser == null) {
			valuePin_LoopNodeVariableInputNameLabel_Parser = new PinParser();
		}
		return valuePin_LoopNodeVariableInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_LoopNodeVariableInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_LoopNodeVariableInputValueLabel_Parser() {
		if (valuePin_LoopNodeVariableInputValueLabel_Parser == null) {
			valuePin_LoopNodeVariableInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_LoopNodeVariableInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_LoopNodeVariableInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_LoopNodeVariableInputStereotypeLabel_Parser() {
		if (valuePin_LoopNodeVariableInputStereotypeLabel_Parser == null) {
			valuePin_LoopNodeVariableInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_LoopNodeVariableInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_LoopNodeVariableInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_LoopNodeVariableInputNameLabel_Parser() {
		if (actionInputPin_LoopNodeVariableInputNameLabel_Parser == null) {
			actionInputPin_LoopNodeVariableInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_LoopNodeVariableInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_LoopNodeVariableInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_LoopNodeVariableInputValueLabel_Parser() {
		if (actionInputPin_LoopNodeVariableInputValueLabel_Parser == null) {
			actionInputPin_LoopNodeVariableInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_LoopNodeVariableInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_LoopNodeVariableInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_LoopNodeVariableInputStereotypeLabel_Parser() {
		if (actionInputPin_LoopNodeVariableInputStereotypeLabel_Parser == null) {
			actionInputPin_LoopNodeVariableInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_LoopNodeVariableInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_LoopNodeBodyOutputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeBodyOutputNameLabel_Parser() {
		if (outputPin_LoopNodeBodyOutputNameLabel_Parser == null) {
			outputPin_LoopNodeBodyOutputNameLabel_Parser = new PinParser();
		}
		return outputPin_LoopNodeBodyOutputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_LoopNodeBodyOutputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeBodyOutputStereotypeLabel_Parser() {
		if (outputPin_LoopNodeBodyOutputStereotypeLabel_Parser == null) {
			outputPin_LoopNodeBodyOutputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_LoopNodeBodyOutputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_LoopNodeVariableNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeVariableNameLabel_Parser() {
		if (outputPin_LoopNodeVariableNameLabel_Parser == null) {
			outputPin_LoopNodeVariableNameLabel_Parser = new PinParser();
		}
		return outputPin_LoopNodeVariableNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_LoopNodeVariableStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeVariableStereotypeLabel_Parser() {
		if (outputPin_LoopNodeVariableStereotypeLabel_Parser == null) {
			outputPin_LoopNodeVariableStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_LoopNodeVariableStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_LoopNodeResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeResultNameLabel_Parser() {
		if (outputPin_LoopNodeResultNameLabel_Parser == null) {
			outputPin_LoopNodeResultNameLabel_Parser = new PinParser();
		}
		return outputPin_LoopNodeResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_LoopNodeResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_LoopNodeResultStereotypeLabel_Parser() {
		if (outputPin_LoopNodeResultStereotypeLabel_Parser == null) {
			outputPin_LoopNodeResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_LoopNodeResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StructuredActivityNodeKeywordParser sequenceNode_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSequenceNode_KeywordLabel_Parser() {
		if (sequenceNode_KeywordLabel_Parser == null) {
			sequenceNode_KeywordLabel_Parser = new StructuredActivityNodeKeywordParser();
		}
		return sequenceNode_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private StructuredActivityNodeKeywordParser structuredActivityNode_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStructuredActivityNode_KeywordLabel_Parser() {
		if (structuredActivityNode_KeywordLabel_Parser == null) {
			structuredActivityNode_KeywordLabel_Parser = new StructuredActivityNodeKeywordParser();
		}
		return structuredActivityNode_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_StructuredActivityNodeInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StructuredActivityNodeInputNameLabel_Parser() {
		if (inputPin_StructuredActivityNodeInputNameLabel_Parser == null) {
			inputPin_StructuredActivityNodeInputNameLabel_Parser = new PinParser();
		}
		return inputPin_StructuredActivityNodeInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_StructuredActivityNodeInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StructuredActivityNodeInputStereotypeLabel_Parser() {
		if (inputPin_StructuredActivityNodeInputStereotypeLabel_Parser == null) {
			inputPin_StructuredActivityNodeInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_StructuredActivityNodeInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_StructuredActivityNodeInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StructuredActivityNodeInputNameLabel_Parser() {
		if (valuePin_StructuredActivityNodeInputNameLabel_Parser == null) {
			valuePin_StructuredActivityNodeInputNameLabel_Parser = new PinParser();
		}
		return valuePin_StructuredActivityNodeInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_StructuredActivityNodeInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StructuredActivityNodeInputValueLabel_Parser() {
		if (valuePin_StructuredActivityNodeInputValueLabel_Parser == null) {
			valuePin_StructuredActivityNodeInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_StructuredActivityNodeInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_StructuredActivityNodeInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StructuredActivityNodeInputStereotypeLabel_Parser() {
		if (valuePin_StructuredActivityNodeInputStereotypeLabel_Parser == null) {
			valuePin_StructuredActivityNodeInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_StructuredActivityNodeInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_StructuredActivityNodeInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StructuredActivityNodeInputNameLabel_Parser() {
		if (actionInputPin_StructuredActivityNodeInputNameLabel_Parser == null) {
			actionInputPin_StructuredActivityNodeInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_StructuredActivityNodeInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_StructuredActivityNodeInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StructuredActivityNodeInputValueLabel_Parser() {
		if (actionInputPin_StructuredActivityNodeInputValueLabel_Parser == null) {
			actionInputPin_StructuredActivityNodeInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_StructuredActivityNodeInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser() {
		if (actionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser == null) {
			actionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_StructuredActivityNodeOutputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_StructuredActivityNodeOutputNameLabel_Parser() {
		if (outputPin_StructuredActivityNodeOutputNameLabel_Parser == null) {
			outputPin_StructuredActivityNodeOutputNameLabel_Parser = new PinParser();
		}
		return outputPin_StructuredActivityNodeOutputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_StructuredActivityNodeOutputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_StructuredActivityNodeOutputStereotypeLabel_Parser() {
		if (outputPin_StructuredActivityNodeOutputStereotypeLabel_Parser == null) {
			outputPin_StructuredActivityNodeOutputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_StructuredActivityNodeOutputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activityPartition_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityPartition_NameLabel_Parser() {
		if (activityPartition_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activityPartition_NameLabel_Parser = parser;
		}
		return activityPartition_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activityPartition_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivityPartition_FloatingNameLabel_Parser() {
		if (activityPartition_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activityPartition_FloatingNameLabel_Parser = parser;
		}
		return activityPartition_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private CommentParser comment_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComment_BodyLabel_Parser() {
		if (comment_BodyLabel_Parser == null) {
			comment_BodyLabel_Parser = new CommentParser();
		}
		return comment_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readSelfAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadSelfAction_NameLabel_Parser() {
		if (readSelfAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readSelfAction_NameLabel_Parser = parser;
		}
		return readSelfAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readSelfAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadSelfAction_FloatingNameLabel_Parser() {
		if (readSelfAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readSelfAction_FloatingNameLabel_Parser = parser;
		}
		return readSelfAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadSelfActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadSelfActionResultNameLabel_Parser() {
		if (outputPin_ReadSelfActionResultNameLabel_Parser == null) {
			outputPin_ReadSelfActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadSelfActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadSelfActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadSelfActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadSelfActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadSelfActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadSelfActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activity_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_NameLabel_CN_Parser() {
		if (activity_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activity_NameLabel_CN_Parser = parser;
		}
		return activity_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ActivitySingleExecutionParser activity_KeywordLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_KeywordLabel_CN_Parser() {
		if (activity_KeywordLabel_CN_Parser == null) {
			activity_KeywordLabel_CN_Parser = new ActivitySingleExecutionParser();
		}
		return activity_KeywordLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateObjectAction_NameLabel_Parser() {
		if (createObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createObjectAction_NameLabel_Parser = parser;
		}
		return createObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateObjectAction_FloatingNameLabel_Parser() {
		if (createObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createObjectAction_FloatingNameLabel_Parser = parser;
		}
		return createObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_CreateObjectActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CreateObjectActionResultNameLabel_Parser() {
		if (outputPin_CreateObjectActionResultNameLabel_Parser == null) {
			outputPin_CreateObjectActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_CreateObjectActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_CreateObjectActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CreateObjectActionResultStereotypeLabel_Parser() {
		if (outputPin_CreateObjectActionResultStereotypeLabel_Parser == null) {
			outputPin_CreateObjectActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_CreateObjectActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ActivitySingleExecutionParser namedElement_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNamedElement_NameLabel_Parser() {
		if (namedElement_NameLabel_Parser == null) {
			namedElement_NameLabel_Parser = new ActivitySingleExecutionParser();
		}
		return namedElement_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readStructuralFeatureAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadStructuralFeatureAction_NameLabel_Parser() {
		if (readStructuralFeatureAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readStructuralFeatureAction_NameLabel_Parser = parser;
		}
		return readStructuralFeatureAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readStructuralFeatureAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadStructuralFeatureAction_FloatingNameLabel_Parser() {
		if (readStructuralFeatureAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readStructuralFeatureAction_FloatingNameLabel_Parser = parser;
		}
		return readStructuralFeatureAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ReadStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser() {
		if (inputPin_ReadStructuralFeatureActionObjectNameLabel_Parser == null) {
			inputPin_ReadStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_ReadStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ReadStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadStructuralFeatureActionObjectNameLabel_Parser() {
		if (valuePin_ReadStructuralFeatureActionObjectNameLabel_Parser == null) {
			valuePin_ReadStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_ReadStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ReadStructuralFeatureActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadStructuralFeatureActionObjectValueLabel_Parser() {
		if (valuePin_ReadStructuralFeatureActionObjectValueLabel_Parser == null) {
			valuePin_ReadStructuralFeatureActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ReadStructuralFeatureActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser() {
		if (actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser == null) {
			actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser() {
		if (actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser == null) {
			actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadStructuralFeatureActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadStructuralFeatureActionResultNameLabel_Parser() {
		if (outputPin_ReadStructuralFeatureActionResultNameLabel_Parser == null) {
			outputPin_ReadStructuralFeatureActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadStructuralFeatureActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser addStructuralFeatureValueAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAddStructuralFeatureValueAction_NameLabel_Parser() {
		if (addStructuralFeatureValueAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			addStructuralFeatureValueAction_NameLabel_Parser = parser;
		}
		return addStructuralFeatureValueAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser addStructuralFeatureValueAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAddStructuralFeatureValueAction_FloatingNameLabel_Parser() {
		if (addStructuralFeatureValueAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			addStructuralFeatureValueAction_FloatingNameLabel_Parser = parser;
		}
		return addStructuralFeatureValueAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_AddStructuralFeatureValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionValueNameLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionValueNameLabel_Parser = new PinParser();
		}
		return inputPin_AddStructuralFeatureValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser() {
		if (inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser == null) {
			inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_AddStructuralFeatureValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionValueNameLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionValueNameLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionValueNameLabel_Parser = new PinParser();
		}
		return valuePin_AddStructuralFeatureValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_AddStructuralFeatureValueActionValueValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionValueValueLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionValueValueLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionValueValueLabel_Parser = new PinValueParser();
		}
		return valuePin_AddStructuralFeatureValueActionValueValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser = new PinValueParser();
		}
		return valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser() {
		if (valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser == null) {
			valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser = new PinParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser() {
		if (actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser == null) {
			actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_AddStructuralFeatureValueActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_AddStructuralFeatureValueActionResultNameLabel_Parser() {
		if (outputPin_AddStructuralFeatureValueActionResultNameLabel_Parser == null) {
			outputPin_AddStructuralFeatureValueActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_AddStructuralFeatureValueActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser() {
		if (outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser == null) {
			outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser destroyObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestroyObjectAction_NameLabel_Parser() {
		if (destroyObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			destroyObjectAction_NameLabel_Parser = parser;
		}
		return destroyObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser destroyObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestroyObjectAction_FloatingNameLabel_Parser() {
		if (destroyObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			destroyObjectAction_FloatingNameLabel_Parser = parser;
		}
		return destroyObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_DestroyObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_DestroyObjectActionTargetNameLabel_Parser() {
		if (inputPin_DestroyObjectActionTargetNameLabel_Parser == null) {
			inputPin_DestroyObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return inputPin_DestroyObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_DestroyObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_DestroyObjectActionTargetStereotypeLabel_Parser() {
		if (inputPin_DestroyObjectActionTargetStereotypeLabel_Parser == null) {
			inputPin_DestroyObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_DestroyObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_DestroyObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyObjectActionTargetNameLabel_Parser() {
		if (valuePin_DestroyObjectActionTargetNameLabel_Parser == null) {
			valuePin_DestroyObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return valuePin_DestroyObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_DestroyObjectActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyObjectActionTargetValueLabel_Parser() {
		if (valuePin_DestroyObjectActionTargetValueLabel_Parser == null) {
			valuePin_DestroyObjectActionTargetValueLabel_Parser = new PinValueParser();
		}
		return valuePin_DestroyObjectActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_DestroyObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyObjectActionTargetStereotypeLabel_Parser() {
		if (valuePin_DestroyObjectActionTargetStereotypeLabel_Parser == null) {
			valuePin_DestroyObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_DestroyObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_DestroyObjectActionTargetNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyObjectActionTargetNameLabel_Parser() {
		if (actionInputPin_DestroyObjectActionTargetNameLabel_Parser == null) {
			actionInputPin_DestroyObjectActionTargetNameLabel_Parser = new PinParser();
		}
		return actionInputPin_DestroyObjectActionTargetNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_DestroyObjectActionTargetValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyObjectActionTargetValueLabel_Parser() {
		if (actionInputPin_DestroyObjectActionTargetValueLabel_Parser == null) {
			actionInputPin_DestroyObjectActionTargetValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_DestroyObjectActionTargetValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser() {
		if (actionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser == null) {
			actionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readVariableAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadVariableAction_NameLabel_Parser() {
		if (readVariableAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readVariableAction_NameLabel_Parser = parser;
		}
		return readVariableAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readVariableAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadVariableAction_FloatingNameLabel_Parser() {
		if (readVariableAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readVariableAction_FloatingNameLabel_Parser = parser;
		}
		return readVariableAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadVariableActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadVariableActionResultNameLabel_Parser() {
		if (outputPin_ReadVariableActionResultNameLabel_Parser == null) {
			outputPin_ReadVariableActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadVariableActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadVariableActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadVariableActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadVariableActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadVariableActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadVariableActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser addVariableValueAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAddVariableValueAction_NameLabel_Parser() {
		if (addVariableValueAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			addVariableValueAction_NameLabel_Parser = parser;
		}
		return addVariableValueAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser addVariableValueAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAddVariableValueAction_FloatingNameLabel_Parser() {
		if (addVariableValueAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			addVariableValueAction_FloatingNameLabel_Parser = parser;
		}
		return addVariableValueAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_AddVariableValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddVariableValueActionInsertAtNameLabel_Parser() {
		if (inputPin_AddVariableValueActionInsertAtNameLabel_Parser == null) {
			inputPin_AddVariableValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return inputPin_AddVariableValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser() {
		if (inputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser == null) {
			inputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_AddVariableValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddVariableValueActionValueNameLabel_Parser() {
		if (inputPin_AddVariableValueActionValueNameLabel_Parser == null) {
			inputPin_AddVariableValueActionValueNameLabel_Parser = new PinParser();
		}
		return inputPin_AddVariableValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_AddVariableValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_AddVariableValueActionValueStereotypeLabel_Parser() {
		if (inputPin_AddVariableValueActionValueStereotypeLabel_Parser == null) {
			inputPin_AddVariableValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_AddVariableValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_AddVariableValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionInsertAtNameLabel_Parser() {
		if (valuePin_AddVariableValueActionInsertAtNameLabel_Parser == null) {
			valuePin_AddVariableValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return valuePin_AddVariableValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_AddVariableValueActionInsertAtValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionInsertAtValueLabel_Parser() {
		if (valuePin_AddVariableValueActionInsertAtValueLabel_Parser == null) {
			valuePin_AddVariableValueActionInsertAtValueLabel_Parser = new PinValueParser();
		}
		return valuePin_AddVariableValueActionInsertAtValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser() {
		if (valuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser == null) {
			valuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_AddVariableValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionValueNameLabel_Parser() {
		if (valuePin_AddVariableValueActionValueNameLabel_Parser == null) {
			valuePin_AddVariableValueActionValueNameLabel_Parser = new PinParser();
		}
		return valuePin_AddVariableValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_AddVariableValueActionValueValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionValueValueLabel_Parser() {
		if (valuePin_AddVariableValueActionValueValueLabel_Parser == null) {
			valuePin_AddVariableValueActionValueValueLabel_Parser = new PinValueParser();
		}
		return valuePin_AddVariableValueActionValueValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_AddVariableValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_AddVariableValueActionValueStereotypeLabel_Parser() {
		if (valuePin_AddVariableValueActionValueStereotypeLabel_Parser == null) {
			valuePin_AddVariableValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_AddVariableValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_AddVariableValueActionInsertAtNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionInsertAtNameLabel_Parser() {
		if (actionInputPin_AddVariableValueActionInsertAtNameLabel_Parser == null) {
			actionInputPin_AddVariableValueActionInsertAtNameLabel_Parser = new PinParser();
		}
		return actionInputPin_AddVariableValueActionInsertAtNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_AddVariableValueActionInsertAtValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionInsertAtValueLabel_Parser() {
		if (actionInputPin_AddVariableValueActionInsertAtValueLabel_Parser == null) {
			actionInputPin_AddVariableValueActionInsertAtValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_AddVariableValueActionInsertAtValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser() {
		if (actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser == null) {
			actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_AddVariableValueActionValueNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionValueNameLabel_Parser() {
		if (actionInputPin_AddVariableValueActionValueNameLabel_Parser == null) {
			actionInputPin_AddVariableValueActionValueNameLabel_Parser = new PinParser();
		}
		return actionInputPin_AddVariableValueActionValueNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_AddVariableValueActionValueValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionValueValueLabel_Parser() {
		if (actionInputPin_AddVariableValueActionValueValueLabel_Parser == null) {
			actionInputPin_AddVariableValueActionValueValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_AddVariableValueActionValueValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_AddVariableValueActionValueStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_AddVariableValueActionValueStereotypeLabel_Parser() {
		if (actionInputPin_AddVariableValueActionValueStereotypeLabel_Parser == null) {
			actionInputPin_AddVariableValueActionValueStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_AddVariableValueActionValueStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser broadcastSignalAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getBroadcastSignalAction_NameLabel_Parser() {
		if (broadcastSignalAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			broadcastSignalAction_NameLabel_Parser = parser;
		}
		return broadcastSignalAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser broadcastSignalAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getBroadcastSignalAction_FloatingNameLabel_Parser() {
		if (broadcastSignalAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			broadcastSignalAction_FloatingNameLabel_Parser = parser;
		}
		return broadcastSignalAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_BroadcastSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_BroadcastSignalActionArgumentNameLabel_Parser() {
		if (inputPin_BroadcastSignalActionArgumentNameLabel_Parser == null) {
			inputPin_BroadcastSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return inputPin_BroadcastSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser inputPin_BroadcastSignalActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_BroadcastSignalActionArgumentValueLabel_Parser() {
		if (inputPin_BroadcastSignalActionArgumentValueLabel_Parser == null) {
			inputPin_BroadcastSignalActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return inputPin_BroadcastSignalActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser() {
		if (inputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser == null) {
			inputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_BroadcastSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_BroadcastSignalActionArgumentNameLabel_Parser() {
		if (valuePin_BroadcastSignalActionArgumentNameLabel_Parser == null) {
			valuePin_BroadcastSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return valuePin_BroadcastSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_BroadcastSignalActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_BroadcastSignalActionArgumentValueLabel_Parser() {
		if (valuePin_BroadcastSignalActionArgumentValueLabel_Parser == null) {
			valuePin_BroadcastSignalActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return valuePin_BroadcastSignalActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser() {
		if (valuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser == null) {
			valuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_BroadcastSignalActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_BroadcastSignalActionArgumentNameLabel_Parser() {
		if (actionInputPin_BroadcastSignalActionArgumentNameLabel_Parser == null) {
			actionInputPin_BroadcastSignalActionArgumentNameLabel_Parser = new PinParser();
		}
		return actionInputPin_BroadcastSignalActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_BroadcastSignalActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_BroadcastSignalActionArgumentValueLabel_Parser() {
		if (actionInputPin_BroadcastSignalActionArgumentValueLabel_Parser == null) {
			actionInputPin_BroadcastSignalActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_BroadcastSignalActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser() {
		if (actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser == null) {
			actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectNodeParser centralBufferNode_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCentralBufferNode_NameLabel_Parser() {
		if (centralBufferNode_NameLabel_Parser == null) {
			centralBufferNode_NameLabel_Parser = new ObjectNodeParser();
		}
		return centralBufferNode_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectNodeSelectionParser centralBufferNode_SelectionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCentralBufferNode_SelectionLabel_Parser() {
		if (centralBufferNode_SelectionLabel_Parser == null) {
			centralBufferNode_SelectionLabel_Parser = new ObjectNodeSelectionParser();
		}
		return centralBufferNode_SelectionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser centralBufferNode_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCentralBufferNode_FloatingNameLabel_Parser() {
		if (centralBufferNode_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			centralBufferNode_FloatingNameLabel_Parser = parser;
		}
		return centralBufferNode_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_NameLabel_Parser() {
		if (constraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_NameLabel_Parser = parser;
		}
		return constraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_BodyLabel_Parser() {
		if (constraint_BodyLabel_Parser == null) {
			constraint_BodyLabel_Parser = new ConstraintParser();
		}
		return constraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser startObjectBehaviorAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStartObjectBehaviorAction_NameLabel_Parser() {
		if (startObjectBehaviorAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			startObjectBehaviorAction_NameLabel_Parser = parser;
		}
		return startObjectBehaviorAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser startObjectBehaviorAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStartObjectBehaviorAction_FloatingNameLabel_Parser() {
		if (startObjectBehaviorAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			startObjectBehaviorAction_FloatingNameLabel_Parser = parser;
		}
		return startObjectBehaviorAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_StartObjectBehaviorActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_StartObjectBehaviorActionResultNameLabel_Parser() {
		if (outputPin_StartObjectBehaviorActionResultNameLabel_Parser == null) {
			outputPin_StartObjectBehaviorActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_StartObjectBehaviorActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser() {
		if (outputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser == null) {
			outputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_StartObjectBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartObjectBehaviorActionObjectNameLabel_Parser() {
		if (inputPin_StartObjectBehaviorActionObjectNameLabel_Parser == null) {
			inputPin_StartObjectBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_StartObjectBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser() {
		if (inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser == null) {
			inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_StartObjectBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionObjectNameLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionObjectNameLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_StartObjectBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_StartObjectBehaviorActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionObjectValueLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionObjectValueLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_StartObjectBehaviorActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_StartObjectBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser() {
		if (inputPin_StartObjectBehaviorActionArgumentNameLabel_Parser == null) {
			inputPin_StartObjectBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return inputPin_StartObjectBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser() {
		if (inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser == null) {
			inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser = new PinValueParser();
		}
		return inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_StartObjectBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionArgumentNameLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionArgumentNameLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return valuePin_StartObjectBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_StartObjectBehaviorActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionArgumentValueLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionArgumentValueLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return valuePin_StartObjectBehaviorActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser() {
		if (valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser == null) {
			valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser = new PinParser();
		}
		return actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser() {
		if (actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser == null) {
			actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser testIdentityAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTestIdentityAction_NameLabel_Parser() {
		if (testIdentityAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			testIdentityAction_NameLabel_Parser = parser;
		}
		return testIdentityAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser testIdentityAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTestIdentityAction_FloatingNameLabel_Parser() {
		if (testIdentityAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			testIdentityAction_FloatingNameLabel_Parser = parser;
		}
		return testIdentityAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_TestIdentityActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_TestIdentityActionResultNameLabel_Parser() {
		if (outputPin_TestIdentityActionResultNameLabel_Parser == null) {
			outputPin_TestIdentityActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_TestIdentityActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_TestIdentityActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_TestIdentityActionResultStereotypeLabel_Parser() {
		if (outputPin_TestIdentityActionResultStereotypeLabel_Parser == null) {
			outputPin_TestIdentityActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_TestIdentityActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_TestIdentityActionFirstNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_TestIdentityActionFirstNameLabel_Parser() {
		if (inputPin_TestIdentityActionFirstNameLabel_Parser == null) {
			inputPin_TestIdentityActionFirstNameLabel_Parser = new PinParser();
		}
		return inputPin_TestIdentityActionFirstNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_TestIdentityActionFirstStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_TestIdentityActionFirstStereotypeLabel_Parser() {
		if (inputPin_TestIdentityActionFirstStereotypeLabel_Parser == null) {
			inputPin_TestIdentityActionFirstStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_TestIdentityActionFirstStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_TestIdentityActionSecondNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_TestIdentityActionSecondNameLabel_Parser() {
		if (inputPin_TestIdentityActionSecondNameLabel_Parser == null) {
			inputPin_TestIdentityActionSecondNameLabel_Parser = new PinParser();
		}
		return inputPin_TestIdentityActionSecondNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_TestIdentityActionSecondStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_TestIdentityActionSecondStereotypeLabel_Parser() {
		if (inputPin_TestIdentityActionSecondStereotypeLabel_Parser == null) {
			inputPin_TestIdentityActionSecondStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_TestIdentityActionSecondStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_TestIdentityActionFirstNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionFirstNameLabel_Parser() {
		if (valuePin_TestIdentityActionFirstNameLabel_Parser == null) {
			valuePin_TestIdentityActionFirstNameLabel_Parser = new PinParser();
		}
		return valuePin_TestIdentityActionFirstNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_TestIdentityActionFirstValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionFirstValueLabel_Parser() {
		if (valuePin_TestIdentityActionFirstValueLabel_Parser == null) {
			valuePin_TestIdentityActionFirstValueLabel_Parser = new PinValueParser();
		}
		return valuePin_TestIdentityActionFirstValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_TestIdentityActionFirstStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionFirstStereotypeLabel_Parser() {
		if (valuePin_TestIdentityActionFirstStereotypeLabel_Parser == null) {
			valuePin_TestIdentityActionFirstStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_TestIdentityActionFirstStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_TestIdentityActionSecondNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionSecondNameLabel_Parser() {
		if (valuePin_TestIdentityActionSecondNameLabel_Parser == null) {
			valuePin_TestIdentityActionSecondNameLabel_Parser = new PinParser();
		}
		return valuePin_TestIdentityActionSecondNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_TestIdentityActionSecondValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionSecondValueLabel_Parser() {
		if (valuePin_TestIdentityActionSecondValueLabel_Parser == null) {
			valuePin_TestIdentityActionSecondValueLabel_Parser = new PinValueParser();
		}
		return valuePin_TestIdentityActionSecondValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_TestIdentityActionSecondStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_TestIdentityActionSecondStereotypeLabel_Parser() {
		if (valuePin_TestIdentityActionSecondStereotypeLabel_Parser == null) {
			valuePin_TestIdentityActionSecondStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_TestIdentityActionSecondStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_TestIdentityActionFirstNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionFirstNameLabel_Parser() {
		if (actionInputPin_TestIdentityActionFirstNameLabel_Parser == null) {
			actionInputPin_TestIdentityActionFirstNameLabel_Parser = new PinParser();
		}
		return actionInputPin_TestIdentityActionFirstNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_TestIdentityActionFirstValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionFirstValueLabel_Parser() {
		if (actionInputPin_TestIdentityActionFirstValueLabel_Parser == null) {
			actionInputPin_TestIdentityActionFirstValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_TestIdentityActionFirstValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_TestIdentityActionFirstStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionFirstStereotypeLabel_Parser() {
		if (actionInputPin_TestIdentityActionFirstStereotypeLabel_Parser == null) {
			actionInputPin_TestIdentityActionFirstStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_TestIdentityActionFirstStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_TestIdentityActionSecondNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionSecondNameLabel_Parser() {
		if (actionInputPin_TestIdentityActionSecondNameLabel_Parser == null) {
			actionInputPin_TestIdentityActionSecondNameLabel_Parser = new PinParser();
		}
		return actionInputPin_TestIdentityActionSecondNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_TestIdentityActionSecondValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionSecondValueLabel_Parser() {
		if (actionInputPin_TestIdentityActionSecondValueLabel_Parser == null) {
			actionInputPin_TestIdentityActionSecondValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_TestIdentityActionSecondValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_TestIdentityActionSecondStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_TestIdentityActionSecondStereotypeLabel_Parser() {
		if (actionInputPin_TestIdentityActionSecondStereotypeLabel_Parser == null) {
			actionInputPin_TestIdentityActionSecondStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_TestIdentityActionSecondStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser clearStructuralFeatureAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClearStructuralFeatureAction_NameLabel_Parser() {
		if (clearStructuralFeatureAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			clearStructuralFeatureAction_NameLabel_Parser = parser;
		}
		return clearStructuralFeatureAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser clearStructuralFeatureAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClearStructuralFeatureAction_FloatingNameLabel_Parser() {
		if (clearStructuralFeatureAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			clearStructuralFeatureAction_FloatingNameLabel_Parser = parser;
		}
		return clearStructuralFeatureAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ClearStructuralFeatureActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ClearStructuralFeatureActionResultNameLabel_Parser() {
		if (outputPin_ClearStructuralFeatureActionResultNameLabel_Parser == null) {
			outputPin_ClearStructuralFeatureActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ClearStructuralFeatureActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser() {
		if (outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser == null) {
			outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ClearStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser() {
		if (inputPin_ClearStructuralFeatureActionObjectNameLabel_Parser == null) {
			inputPin_ClearStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_ClearStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ClearStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearStructuralFeatureActionObjectNameLabel_Parser() {
		if (valuePin_ClearStructuralFeatureActionObjectNameLabel_Parser == null) {
			valuePin_ClearStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_ClearStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ClearStructuralFeatureActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearStructuralFeatureActionObjectValueLabel_Parser() {
		if (valuePin_ClearStructuralFeatureActionObjectValueLabel_Parser == null) {
			valuePin_ClearStructuralFeatureActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ClearStructuralFeatureActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser() {
		if (actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser == null) {
			actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser() {
		if (actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser == null) {
			actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createLinkAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateLinkAction_NameLabel_Parser() {
		if (createLinkAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createLinkAction_NameLabel_Parser = parser;
		}
		return createLinkAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createLinkAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateLinkAction_FloatingNameLabel_Parser() {
		if (createLinkAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createLinkAction_FloatingNameLabel_Parser = parser;
		}
		return createLinkAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_CreateLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CreateLinkActionInputNameLabel_Parser() {
		if (inputPin_CreateLinkActionInputNameLabel_Parser == null) {
			inputPin_CreateLinkActionInputNameLabel_Parser = new PinParser();
		}
		return inputPin_CreateLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_CreateLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CreateLinkActionInputStereotypeLabel_Parser() {
		if (inputPin_CreateLinkActionInputStereotypeLabel_Parser == null) {
			inputPin_CreateLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_CreateLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_CreateLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkActionInputNameLabel_Parser() {
		if (valuePin_CreateLinkActionInputNameLabel_Parser == null) {
			valuePin_CreateLinkActionInputNameLabel_Parser = new PinParser();
		}
		return valuePin_CreateLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_CreateLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkActionInputValueLabel_Parser() {
		if (valuePin_CreateLinkActionInputValueLabel_Parser == null) {
			valuePin_CreateLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_CreateLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_CreateLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkActionInputStereotypeLabel_Parser() {
		if (valuePin_CreateLinkActionInputStereotypeLabel_Parser == null) {
			valuePin_CreateLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_CreateLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_CreateLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkActionInputNameLabel_Parser() {
		if (actionInputPin_CreateLinkActionInputNameLabel_Parser == null) {
			actionInputPin_CreateLinkActionInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_CreateLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_CreateLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkActionInputValueLabel_Parser() {
		if (actionInputPin_CreateLinkActionInputValueLabel_Parser == null) {
			actionInputPin_CreateLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_CreateLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_CreateLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkActionInputStereotypeLabel_Parser() {
		if (actionInputPin_CreateLinkActionInputStereotypeLabel_Parser == null) {
			actionInputPin_CreateLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_CreateLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readLinkAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadLinkAction_NameLabel_Parser() {
		if (readLinkAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readLinkAction_NameLabel_Parser = parser;
		}
		return readLinkAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readLinkAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadLinkAction_FloatingNameLabel_Parser() {
		if (readLinkAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readLinkAction_FloatingNameLabel_Parser = parser;
		}
		return readLinkAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadLinkActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadLinkActionResultNameLabel_Parser() {
		if (outputPin_ReadLinkActionResultNameLabel_Parser == null) {
			outputPin_ReadLinkActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadLinkActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadLinkActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadLinkActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadLinkActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadLinkActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadLinkActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ReadLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadLinkActionInputNameLabel_Parser() {
		if (inputPin_ReadLinkActionInputNameLabel_Parser == null) {
			inputPin_ReadLinkActionInputNameLabel_Parser = new PinParser();
		}
		return inputPin_ReadLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ReadLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadLinkActionInputStereotypeLabel_Parser() {
		if (inputPin_ReadLinkActionInputStereotypeLabel_Parser == null) {
			inputPin_ReadLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ReadLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ReadLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadLinkActionInputNameLabel_Parser() {
		if (valuePin_ReadLinkActionInputNameLabel_Parser == null) {
			valuePin_ReadLinkActionInputNameLabel_Parser = new PinParser();
		}
		return valuePin_ReadLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ReadLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadLinkActionInputValueLabel_Parser() {
		if (valuePin_ReadLinkActionInputValueLabel_Parser == null) {
			valuePin_ReadLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ReadLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ReadLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadLinkActionInputStereotypeLabel_Parser() {
		if (valuePin_ReadLinkActionInputStereotypeLabel_Parser == null) {
			valuePin_ReadLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ReadLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ReadLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadLinkActionInputNameLabel_Parser() {
		if (actionInputPin_ReadLinkActionInputNameLabel_Parser == null) {
			actionInputPin_ReadLinkActionInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ReadLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ReadLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadLinkActionInputValueLabel_Parser() {
		if (actionInputPin_ReadLinkActionInputValueLabel_Parser == null) {
			actionInputPin_ReadLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ReadLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ReadLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadLinkActionInputStereotypeLabel_Parser() {
		if (actionInputPin_ReadLinkActionInputStereotypeLabel_Parser == null) {
			actionInputPin_ReadLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ReadLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser destroyLinkAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestroyLinkAction_NameLabel_Parser() {
		if (destroyLinkAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			destroyLinkAction_NameLabel_Parser = parser;
		}
		return destroyLinkAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser destroyLinkAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDestroyLinkAction_FloatingNameLabel_Parser() {
		if (destroyLinkAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			destroyLinkAction_FloatingNameLabel_Parser = parser;
		}
		return destroyLinkAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_DestroyLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_DestroyLinkActionInputNameLabel_Parser() {
		if (inputPin_DestroyLinkActionInputNameLabel_Parser == null) {
			inputPin_DestroyLinkActionInputNameLabel_Parser = new PinParser();
		}
		return inputPin_DestroyLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_DestroyLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_DestroyLinkActionInputStereotypeLabel_Parser() {
		if (inputPin_DestroyLinkActionInputStereotypeLabel_Parser == null) {
			inputPin_DestroyLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_DestroyLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_DestroyLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyLinkActionInputNameLabel_Parser() {
		if (valuePin_DestroyLinkActionInputNameLabel_Parser == null) {
			valuePin_DestroyLinkActionInputNameLabel_Parser = new PinParser();
		}
		return valuePin_DestroyLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_DestroyLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyLinkActionInputValueLabel_Parser() {
		if (valuePin_DestroyLinkActionInputValueLabel_Parser == null) {
			valuePin_DestroyLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_DestroyLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_DestroyLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_DestroyLinkActionInputStereotypeLabel_Parser() {
		if (valuePin_DestroyLinkActionInputStereotypeLabel_Parser == null) {
			valuePin_DestroyLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_DestroyLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_DestroyLinkActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyLinkActionInputNameLabel_Parser() {
		if (actionInputPin_DestroyLinkActionInputNameLabel_Parser == null) {
			actionInputPin_DestroyLinkActionInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_DestroyLinkActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_DestroyLinkActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyLinkActionInputValueLabel_Parser() {
		if (actionInputPin_DestroyLinkActionInputValueLabel_Parser == null) {
			actionInputPin_DestroyLinkActionInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_DestroyLinkActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_DestroyLinkActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_DestroyLinkActionInputStereotypeLabel_Parser() {
		if (actionInputPin_DestroyLinkActionInputStereotypeLabel_Parser == null) {
			actionInputPin_DestroyLinkActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_DestroyLinkActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser clearAssociationAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClearAssociationAction_NameLabel_Parser() {
		if (clearAssociationAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			clearAssociationAction_NameLabel_Parser = parser;
		}
		return clearAssociationAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser clearAssociationAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClearAssociationAction_FloatingNameLabel_Parser() {
		if (clearAssociationAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			clearAssociationAction_FloatingNameLabel_Parser = parser;
		}
		return clearAssociationAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ClearAssociationActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ClearAssociationActionObjectNameLabel_Parser() {
		if (inputPin_ClearAssociationActionObjectNameLabel_Parser == null) {
			inputPin_ClearAssociationActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_ClearAssociationActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ClearAssociationActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ClearAssociationActionObjectStereotypeLabel_Parser() {
		if (inputPin_ClearAssociationActionObjectStereotypeLabel_Parser == null) {
			inputPin_ClearAssociationActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ClearAssociationActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ClearAssociationActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearAssociationActionObjectNameLabel_Parser() {
		if (valuePin_ClearAssociationActionObjectNameLabel_Parser == null) {
			valuePin_ClearAssociationActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_ClearAssociationActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ClearAssociationActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearAssociationActionObjectValueLabel_Parser() {
		if (valuePin_ClearAssociationActionObjectValueLabel_Parser == null) {
			valuePin_ClearAssociationActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ClearAssociationActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ClearAssociationActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ClearAssociationActionObjectStereotypeLabel_Parser() {
		if (valuePin_ClearAssociationActionObjectStereotypeLabel_Parser == null) {
			valuePin_ClearAssociationActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ClearAssociationActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ClearAssociationActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearAssociationActionObjectNameLabel_Parser() {
		if (actionInputPin_ClearAssociationActionObjectNameLabel_Parser == null) {
			actionInputPin_ClearAssociationActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ClearAssociationActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ClearAssociationActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearAssociationActionObjectValueLabel_Parser() {
		if (actionInputPin_ClearAssociationActionObjectValueLabel_Parser == null) {
			actionInputPin_ClearAssociationActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ClearAssociationActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readExtentAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadExtentAction_NameLabel_Parser() {
		if (readExtentAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readExtentAction_NameLabel_Parser = parser;
		}
		return readExtentAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readExtentAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadExtentAction_FloatingNameLabel_Parser() {
		if (readExtentAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readExtentAction_FloatingNameLabel_Parser = parser;
		}
		return readExtentAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadExtentActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadExtentActionResultNameLabel_Parser() {
		if (outputPin_ReadExtentActionResultNameLabel_Parser == null) {
			outputPin_ReadExtentActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadExtentActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadExtentActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadExtentActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadExtentActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadExtentActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadExtentActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser reclassifyObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReclassifyObjectAction_NameLabel_Parser() {
		if (reclassifyObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			reclassifyObjectAction_NameLabel_Parser = parser;
		}
		return reclassifyObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser reclassifyObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReclassifyObjectAction_FloatingNameLabel_Parser() {
		if (reclassifyObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			reclassifyObjectAction_FloatingNameLabel_Parser = parser;
		}
		return reclassifyObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ReclassifyObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReclassifyObjectActionObjectNameLabel_Parser() {
		if (inputPin_ReclassifyObjectActionObjectNameLabel_Parser == null) {
			inputPin_ReclassifyObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_ReclassifyObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser() {
		if (inputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser == null) {
			inputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ReclassifyObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReclassifyObjectActionObjectNameLabel_Parser() {
		if (valuePin_ReclassifyObjectActionObjectNameLabel_Parser == null) {
			valuePin_ReclassifyObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_ReclassifyObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ReclassifyObjectActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReclassifyObjectActionObjectValueLabel_Parser() {
		if (valuePin_ReclassifyObjectActionObjectValueLabel_Parser == null) {
			valuePin_ReclassifyObjectActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ReclassifyObjectActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser() {
		if (valuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser == null) {
			valuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ReclassifyObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReclassifyObjectActionObjectNameLabel_Parser() {
		if (actionInputPin_ReclassifyObjectActionObjectNameLabel_Parser == null) {
			actionInputPin_ReclassifyObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ReclassifyObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ReclassifyObjectActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReclassifyObjectActionObjectValueLabel_Parser() {
		if (actionInputPin_ReclassifyObjectActionObjectValueLabel_Parser == null) {
			actionInputPin_ReclassifyObjectActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ReclassifyObjectActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readIsClassifiedObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadIsClassifiedObjectAction_NameLabel_Parser() {
		if (readIsClassifiedObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readIsClassifiedObjectAction_NameLabel_Parser = parser;
		}
		return readIsClassifiedObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser readIsClassifiedObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReadIsClassifiedObjectAction_FloatingNameLabel_Parser() {
		if (readIsClassifiedObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			readIsClassifiedObjectAction_FloatingNameLabel_Parser = parser;
		}
		return readIsClassifiedObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser() {
		if (outputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser == null) {
			outputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser() {
		if (outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser == null) {
			outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser() {
		if (inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser == null) {
			inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser() {
		if (inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser == null) {
			inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser() {
		if (valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser == null) {
			valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser() {
		if (valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser == null) {
			valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser() {
		if (valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser == null) {
			valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser() {
		if (actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser == null) {
			actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser() {
		if (actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser == null) {
			actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser reduceAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReduceAction_NameLabel_Parser() {
		if (reduceAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			reduceAction_NameLabel_Parser = parser;
		}
		return reduceAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser reduceAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReduceAction_FloatingNameLabel_Parser() {
		if (reduceAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			reduceAction_FloatingNameLabel_Parser = parser;
		}
		return reduceAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_ReduceActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReduceActionResultNameLabel_Parser() {
		if (outputPin_ReduceActionResultNameLabel_Parser == null) {
			outputPin_ReduceActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_ReduceActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_ReduceActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_ReduceActionResultStereotypeLabel_Parser() {
		if (outputPin_ReduceActionResultStereotypeLabel_Parser == null) {
			outputPin_ReduceActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_ReduceActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_ReduceActionCollectionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReduceActionCollectionNameLabel_Parser() {
		if (inputPin_ReduceActionCollectionNameLabel_Parser == null) {
			inputPin_ReduceActionCollectionNameLabel_Parser = new PinParser();
		}
		return inputPin_ReduceActionCollectionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_ReduceActionCollectionStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_ReduceActionCollectionStereotypeLabel_Parser() {
		if (inputPin_ReduceActionCollectionStereotypeLabel_Parser == null) {
			inputPin_ReduceActionCollectionStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_ReduceActionCollectionStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_ReduceActionCollectionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReduceActionCollectionNameLabel_Parser() {
		if (valuePin_ReduceActionCollectionNameLabel_Parser == null) {
			valuePin_ReduceActionCollectionNameLabel_Parser = new PinParser();
		}
		return valuePin_ReduceActionCollectionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_ReduceActionCollectionValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReduceActionCollectionValueLabel_Parser() {
		if (valuePin_ReduceActionCollectionValueLabel_Parser == null) {
			valuePin_ReduceActionCollectionValueLabel_Parser = new PinValueParser();
		}
		return valuePin_ReduceActionCollectionValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_ReduceActionCollectionStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_ReduceActionCollectionStereotypeLabel_Parser() {
		if (valuePin_ReduceActionCollectionStereotypeLabel_Parser == null) {
			valuePin_ReduceActionCollectionStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_ReduceActionCollectionStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_ReduceActionCollectionNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReduceActionCollectionNameLabel_Parser() {
		if (actionInputPin_ReduceActionCollectionNameLabel_Parser == null) {
			actionInputPin_ReduceActionCollectionNameLabel_Parser = new PinParser();
		}
		return actionInputPin_ReduceActionCollectionNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_ReduceActionCollectionValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReduceActionCollectionValueLabel_Parser() {
		if (actionInputPin_ReduceActionCollectionValueLabel_Parser == null) {
			actionInputPin_ReduceActionCollectionValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_ReduceActionCollectionValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_ReduceActionCollectionStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_ReduceActionCollectionStereotypeLabel_Parser() {
		if (actionInputPin_ReduceActionCollectionStereotypeLabel_Parser == null) {
			actionInputPin_ReduceActionCollectionStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_ReduceActionCollectionStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser startClassifierBehaviorAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStartClassifierBehaviorAction_NameLabel_Parser() {
		if (startClassifierBehaviorAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			startClassifierBehaviorAction_NameLabel_Parser = parser;
		}
		return startClassifierBehaviorAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser startClassifierBehaviorAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStartClassifierBehaviorAction_FloatingNameLabel_Parser() {
		if (startClassifierBehaviorAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			startClassifierBehaviorAction_FloatingNameLabel_Parser = parser;
		}
		return startClassifierBehaviorAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_StartClassifierBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser() {
		if (inputPin_StartClassifierBehaviorActionObjectNameLabel_Parser == null) {
			inputPin_StartClassifierBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_StartClassifierBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser() {
		if (inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser == null) {
			inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_StartClassifierBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartClassifierBehaviorActionObjectNameLabel_Parser() {
		if (valuePin_StartClassifierBehaviorActionObjectNameLabel_Parser == null) {
			valuePin_StartClassifierBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_StartClassifierBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_StartClassifierBehaviorActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartClassifierBehaviorActionObjectValueLabel_Parser() {
		if (valuePin_StartClassifierBehaviorActionObjectValueLabel_Parser == null) {
			valuePin_StartClassifierBehaviorActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_StartClassifierBehaviorActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser() {
		if (valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser == null) {
			valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser() {
		if (actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser == null) {
			actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser() {
		if (actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser == null) {
			actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createLinkObjectAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateLinkObjectAction_NameLabel_Parser() {
		if (createLinkObjectAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createLinkObjectAction_NameLabel_Parser = parser;
		}
		return createLinkObjectAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser createLinkObjectAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCreateLinkObjectAction_FloatingNameLabel_Parser() {
		if (createLinkObjectAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			createLinkObjectAction_FloatingNameLabel_Parser = parser;
		}
		return createLinkObjectAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_CreateLinkObjectActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CreateLinkObjectActionInputNameLabel_Parser() {
		if (inputPin_CreateLinkObjectActionInputNameLabel_Parser == null) {
			inputPin_CreateLinkObjectActionInputNameLabel_Parser = new PinParser();
		}
		return inputPin_CreateLinkObjectActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_CreateLinkObjectActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser() {
		if (inputPin_CreateLinkObjectActionInputStereotypeLabel_Parser == null) {
			inputPin_CreateLinkObjectActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_CreateLinkObjectActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_CreateLinkObjectActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkObjectActionInputNameLabel_Parser() {
		if (valuePin_CreateLinkObjectActionInputNameLabel_Parser == null) {
			valuePin_CreateLinkObjectActionInputNameLabel_Parser = new PinParser();
		}
		return valuePin_CreateLinkObjectActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_CreateLinkObjectActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkObjectActionInputValueLabel_Parser() {
		if (valuePin_CreateLinkObjectActionInputValueLabel_Parser == null) {
			valuePin_CreateLinkObjectActionInputValueLabel_Parser = new PinValueParser();
		}
		return valuePin_CreateLinkObjectActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_CreateLinkObjectActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_CreateLinkObjectActionInputStereotypeLabel_Parser() {
		if (valuePin_CreateLinkObjectActionInputStereotypeLabel_Parser == null) {
			valuePin_CreateLinkObjectActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_CreateLinkObjectActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_CreateLinkObjectActionInputNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkObjectActionInputNameLabel_Parser() {
		if (actionInputPin_CreateLinkObjectActionInputNameLabel_Parser == null) {
			actionInputPin_CreateLinkObjectActionInputNameLabel_Parser = new PinParser();
		}
		return actionInputPin_CreateLinkObjectActionInputNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_CreateLinkObjectActionInputValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkObjectActionInputValueLabel_Parser() {
		if (actionInputPin_CreateLinkObjectActionInputValueLabel_Parser == null) {
			actionInputPin_CreateLinkObjectActionInputValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_CreateLinkObjectActionInputValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser() {
		if (actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser == null) {
			actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_CreateLinkObjectActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CreateLinkObjectActionResultNameLabel_Parser() {
		if (outputPin_CreateLinkObjectActionResultNameLabel_Parser == null) {
			outputPin_CreateLinkObjectActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_CreateLinkObjectActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_CreateLinkObjectActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_CreateLinkObjectActionResultStereotypeLabel_Parser() {
		if (outputPin_CreateLinkObjectActionResultStereotypeLabel_Parser == null) {
			outputPin_CreateLinkObjectActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_CreateLinkObjectActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser unmarshallAction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUnmarshallAction_NameLabel_Parser() {
		if (unmarshallAction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			unmarshallAction_NameLabel_Parser = parser;
		}
		return unmarshallAction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser unmarshallAction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUnmarshallAction_FloatingNameLabel_Parser() {
		if (unmarshallAction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			unmarshallAction_FloatingNameLabel_Parser = parser;
		}
		return unmarshallAction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser inputPin_UnmarshallActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_UnmarshallActionObjectNameLabel_Parser() {
		if (inputPin_UnmarshallActionObjectNameLabel_Parser == null) {
			inputPin_UnmarshallActionObjectNameLabel_Parser = new PinParser();
		}
		return inputPin_UnmarshallActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser inputPin_UnmarshallActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInputPin_UnmarshallActionObjectStereotypeLabel_Parser() {
		if (inputPin_UnmarshallActionObjectStereotypeLabel_Parser == null) {
			inputPin_UnmarshallActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return inputPin_UnmarshallActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser valuePin_UnmarshallActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_UnmarshallActionObjectNameLabel_Parser() {
		if (valuePin_UnmarshallActionObjectNameLabel_Parser == null) {
			valuePin_UnmarshallActionObjectNameLabel_Parser = new PinParser();
		}
		return valuePin_UnmarshallActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser valuePin_UnmarshallActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_UnmarshallActionObjectValueLabel_Parser() {
		if (valuePin_UnmarshallActionObjectValueLabel_Parser == null) {
			valuePin_UnmarshallActionObjectValueLabel_Parser = new PinValueParser();
		}
		return valuePin_UnmarshallActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser valuePin_UnmarshallActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getValuePin_UnmarshallActionObjectStereotypeLabel_Parser() {
		if (valuePin_UnmarshallActionObjectStereotypeLabel_Parser == null) {
			valuePin_UnmarshallActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return valuePin_UnmarshallActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser actionInputPin_UnmarshallActionObjectNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_UnmarshallActionObjectNameLabel_Parser() {
		if (actionInputPin_UnmarshallActionObjectNameLabel_Parser == null) {
			actionInputPin_UnmarshallActionObjectNameLabel_Parser = new PinParser();
		}
		return actionInputPin_UnmarshallActionObjectNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinValueParser actionInputPin_UnmarshallActionObjectValueLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_UnmarshallActionObjectValueLabel_Parser() {
		if (actionInputPin_UnmarshallActionObjectValueLabel_Parser == null) {
			actionInputPin_UnmarshallActionObjectValueLabel_Parser = new PinValueParser();
		}
		return actionInputPin_UnmarshallActionObjectValueLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actionInputPin_UnmarshallActionObjectStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActionInputPin_UnmarshallActionObjectStereotypeLabel_Parser() {
		if (actionInputPin_UnmarshallActionObjectStereotypeLabel_Parser == null) {
			actionInputPin_UnmarshallActionObjectStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actionInputPin_UnmarshallActionObjectStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PinParser outputPin_UnmarshallActionResultNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_UnmarshallActionResultNameLabel_Parser() {
		if (outputPin_UnmarshallActionResultNameLabel_Parser == null) {
			outputPin_UnmarshallActionResultNameLabel_Parser = new PinParser();
		}
		return outputPin_UnmarshallActionResultNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser outputPin_UnmarshallActionResultStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOutputPin_UnmarshallActionResultStereotypeLabel_Parser() {
		if (outputPin_UnmarshallActionResultStereotypeLabel_Parser == null) {
			outputPin_UnmarshallActionResultStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return outputPin_UnmarshallActionResultStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser objectFlow_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_NameLabel_Parser() {
		if (objectFlow_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			objectFlow_NameLabel_Parser = parser;
		}
		return objectFlow_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ActivityEdgeWeightParser objectFlow_WeightLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_WeightLabel_Parser() {
		if (objectFlow_WeightLabel_Parser == null) {
			objectFlow_WeightLabel_Parser = new ActivityEdgeWeightParser();
		}
		return objectFlow_WeightLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectFlowSelectionParser objectFlow_SelectionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_SelectionLabel_Parser() {
		if (objectFlow_SelectionLabel_Parser == null) {
			objectFlow_SelectionLabel_Parser = new ObjectFlowSelectionParser();
		}
		return objectFlow_SelectionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ObjectFlowTransformationParser objectFlow_TransformationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_TransformationLabel_Parser() {
		if (objectFlow_TransformationLabel_Parser == null) {
			objectFlow_TransformationLabel_Parser = new ObjectFlowTransformationParser();
		}
		return objectFlow_TransformationLabel_Parser;
	}

	/**
	 * @generated
	 */
	private DecisionInputFlowParser objectFlow_KeywordLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_KeywordLabel_Parser() {
		if (objectFlow_KeywordLabel_Parser == null) {
			objectFlow_KeywordLabel_Parser = new DecisionInputFlowParser();
		}
		return objectFlow_KeywordLabel_Parser;
	}

	/**
	 * @generated
	 */
	private EdgeGuardParser objectFlow_GuardLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_GuardLabel_Parser() {
		if (objectFlow_GuardLabel_Parser == null) {
			objectFlow_GuardLabel_Parser = new EdgeGuardParser();
		}
		return objectFlow_GuardLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser objectFlow_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getObjectFlow_StereotypeLabel_Parser() {
		if (objectFlow_StereotypeLabel_Parser == null) {
			objectFlow_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return objectFlow_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser controlFlow_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getControlFlow_NameLabel_Parser() {
		if (controlFlow_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			controlFlow_NameLabel_Parser = parser;
		}
		return controlFlow_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ActivityEdgeWeightParser controlFlow_WeightLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getControlFlow_WeightLabel_Parser() {
		if (controlFlow_WeightLabel_Parser == null) {
			controlFlow_WeightLabel_Parser = new ActivityEdgeWeightParser();
		}
		return controlFlow_WeightLabel_Parser;
	}

	/**
	 * @generated
	 */
	private EdgeGuardParser controlFlow_GuardLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getControlFlow_GuardLabel_Parser() {
		if (controlFlow_GuardLabel_Parser == null) {
			controlFlow_GuardLabel_Parser = new EdgeGuardParser();
		}
		return controlFlow_GuardLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser controlFlow_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getControlFlow_StereotypeLabel_Parser() {
		if (controlFlow_StereotypeLabel_Parser == null) {
			controlFlow_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return controlFlow_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ExceptionHandlerTypeParser exceptionHandler_TypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExceptionHandler_TypeLabel_Parser() {
		if (exceptionHandler_TypeLabel_Parser == null) {
			exceptionHandler_TypeLabel_Parser = new ExceptionHandlerTypeParser();
		}
		return exceptionHandler_TypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case ActivityNameEditPart.VISUAL_ID:
				return getActivity_NameLabel_Parser();
			case ActivityIsSingleExecutionEditPart.VISUAL_ID:
				return getActivity_KeywordLabel_Parser();
			case ParameterEditPart.VISUAL_ID:
				return getParameter_ParameterLabel_Parser();
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return getConstraint_PreconditionLabel_Parser();
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return getConstraint_PostconditionLabel_Parser();
			case InitialNodeFloatingNameEditPart.VISUAL_ID:
				return getInitialNode_FloatingNameLabel_Parser();
			case InitialNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getInitialNode_StereotypeLabel_Parser();
			case ActivityFinalNodeFloatingNameEditPart.VISUAL_ID:
				return getActivityFinalNode_FloatingNameLabel_Parser();
			case ActivityFinalNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getActivityFinalNode_StereotypeLabel_Parser();
			case FlowFinalNodeFloatingNameEditPart.VISUAL_ID:
				return getFlowFinalNode_FloatingNameLabel_Parser();
			case FlowFinalNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getFlowFinalNode_StereotypeLabel_Parser();
			case OpaqueActionNameEditPart.VISUAL_ID:
				return getOpaqueAction_NameLabel_Parser();
			case OpaqueActionFloatingNameEditPart.VISUAL_ID:
				return getOpaqueAction_FloatingNameLabel_Parser();
			case ValuePinInOActLabelEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputNameLabel_Parser();
			case ValuePinInOActValueEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputValueLabel_Parser();
			case ValuePinInOActAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputStereotypeLabel_Parser();
			case ActionInputPinInOActLabelEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputNameLabel_Parser();
			case ActionInputPinInOActValueEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputValueLabel_Parser();
			case ActionInputPinInOActAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputStereotypeLabel_Parser();
			case InputPinInOActLabelEditPart.VISUAL_ID:
				return getInputPin_OpaqueActionInputNameLabel_Parser();
			case InputPinInOActAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_OpaqueActionInputStereotypeLabel_Parser();
			case OutputPinInOActLabelEditPart.VISUAL_ID:
				return getOutputPin_OpaqueActionOutputNameLabel_Parser();
			case OutputPinInOActAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_OpaqueActionOutputStereotypeLabel_Parser();
			case CallBehaviorActionNameEditPart.VISUAL_ID:
				return getCallBehaviorAction_NameLabel_Parser();
			case CallBehaviorActionFloatingNameEditPart.VISUAL_ID:
				return getCallBehaviorAction_FloatingNameLabel_Parser();
			case ValuePinInCBActLabelEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentNameLabel_Parser();
			case ValuePinInCBActValueEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentValueLabel_Parser();
			case ValuePinInCBActAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentStereotypeLabel_Parser();
			case ActionInputPinInCBActLabelEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentNameLabel_Parser();
			case ActionInputPinInCBActValueEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentValueLabel_Parser();
			case ActionInputPinInCBActAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser();
			case InputPinInCBActLabelEditPart.VISUAL_ID:
				return getInputPin_CallBehaviorActionArgumentNameLabel_Parser();
			case InputPinInCBActAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_CallBehaviorActionArgumentStereotypeLabel_Parser();
			case OutputPinInCBActLabelEditPart.VISUAL_ID:
				return getOutputPin_CallBehaviorActionResultNameLabel_Parser();
			case OutputPinInCBActAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_CallBehaviorActionResultStereotypeLabel_Parser();
			case CallOperationActionNameEditPart.VISUAL_ID:
				return getCallOperationAction_NameLabel_Parser();
			case CallOperationActionFloatingNameEditPart.VISUAL_ID:
				return getCallOperationAction_FloatingNameLabel_Parser();
			case ActionInputPinInCOActLabelEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentNameLabel_Parser();
			case ActionInputPinInCOActValueEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentValueLabel_Parser();
			case ActionInputPinInCOActAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentStereotypeLabel_Parser();
			case ValuePinInCOActLabelEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentNameLabel_Parser();
			case ValuePinInCOActValueEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentValueLabel_Parser();
			case ValuePinInCOActAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentStereotypeLabel_Parser();
			case InputPinInCOActLabelEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionArgumentNameLabel_Parser();
			case InputPinInCOActAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionArgumentStereotypeLabel_Parser();
			case OutputPinInCOActLabelEditPart.VISUAL_ID:
				return getOutputPin_CallOperationActionResultNameLabel_Parser();
			case OutputPinInCOActAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_CallOperationActionResultStereotypeLabel_Parser();
			case ValuePinInCOActAsTargetLabelEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetNameLabel_Parser();
			case ValuePinInCOActAsTargetValueEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetValueLabel_Parser();
			case ValuePinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetStereotypeLabel_Parser();
			case ActionInputPinInCOActAsTargetLabelEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetNameLabel_Parser();
			case ActionInputPinInCOActAsTargetValueEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetValueLabel_Parser();
			case ActionInputPinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetStereotypeLabel_Parser();
			case InputPinInCOActAsTargetLabelEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionTargetNameLabel_Parser();
			case InputPinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionTargetStereotypeLabel_Parser();
			case DurationConstraintAsLocalPrecondNameEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPreconditionNameLabel_Parser();
			case DurationConstraintAsLocalPrecondBodyEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPreconditionBodyLabel_Parser();
			case DurationConstraintAsLocalPostcondNameEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPostconditionNameLabel_Parser();
			case DurationConstraintAsLocalPostcondBodyEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPostconditionBodyLabel_Parser();
			case TimeConstraintAsLocalPrecondNameEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPreconditionNameLabel_Parser();
			case TimeConstraintAsLocalPrecondBodyEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPreconditionBodyLabel_Parser();
			case TimeConstraintAsLocalPostcondNameEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPostconditionNameLabel_Parser();
			case TimeConstraintAsLocalPostcondBodyEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPostconditionBodyLabel_Parser();
			case IntervalConstraintAsLocalPrecondNameEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPreconditionNameLabel_Parser();
			case IntervalConstraintAsLocalPrecondBodyEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPreconditionBodyLabel_Parser();
			case IntervalConstraintAsLocalPostcondNameEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPostconditionNameLabel_Parser();
			case IntervalConstraintAsLocalPostcondBodyEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPostconditionBodyLabel_Parser();
			case ConstraintAsLocalPrecondNameEditPart.VISUAL_ID:
				return getConstraint_LocalPreconditionNameLabel_Parser();
			case ConstraintAsLocalPrecondBodyEditPart.VISUAL_ID:
				return getConstraint_LocalPreconditionBodyLabel_Parser();
			case ConstraintAsLocalPostcondNameEditPart.VISUAL_ID:
				return getConstraint_LocalPostconditionNameLabel_Parser();
			case ConstraintAsLocalPostcondBodyEditPart.VISUAL_ID:
				return getConstraint_LocalPostconditionBodyLabel_Parser();
			case DecisionNodeFloatingNameEditPart.VISUAL_ID:
				return getDecisionNode_FloatingNameLabel_Parser();
			case DecisionInputEditPart.VISUAL_ID:
				return getDecisionNode_DecisionInputLabel_Parser();
			case DecisionNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getDecisionNode_StereotypeLabel_Parser();
			case MergeNodeFloatingNameEditPart.VISUAL_ID:
				return getMergeNode_FloatingNameLabel_Parser();
			case MergeNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getMergeNode_StereotypeLabel_Parser();
			case ForkNodeFloatingNameEditPart.VISUAL_ID:
				return getForkNode_FloatingNameLabel_Parser();
			case ForkNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getForkNode_StereotypeLabel_Parser();
			case JoinNodeFloatingNameEditPart.VISUAL_ID:
				return getJoinNode_FloatingNameLabel_Parser();
			case JoinSpecEditPart.VISUAL_ID:
				return getJoinNode_JoinSpecLabel_Parser();
			case JoinNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getJoinNode_StereotypeLabel_Parser();
			case DataStoreNodeLabelEditPart.VISUAL_ID:
				return getDataStoreNode_NameLabel_Parser();
			case DataStoreSelectionEditPart.VISUAL_ID:
				return getDataStoreNode_SelectionLabel_Parser();
			case DataStoreNodeFloatingNameEditPart.VISUAL_ID:
				return getDataStoreNode_FloatingNameLabel_Parser();
			case SendObjectActionNameEditPart.VISUAL_ID:
				return getSendObjectAction_NameLabel_Parser();
			case SendObjectActionFloatingNameEditPart.VISUAL_ID:
				return getSendObjectAction_FloatingNameLabel_Parser();
			case ValuePinInSendObjActAsReqLabelEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestNameLabel_Parser();
			case ValuePinInSendObjActAsReqValueEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestValueLabel_Parser();
			case ValuePinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestStereotypeLabel_Parser();
			case ActionInputPinInSendObjActAsReqLabelEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestNameLabel_Parser();
			case ActionInputPinInSendObjActAsReqValueEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestValueLabel_Parser();
			case ActionInputPinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestStereotypeLabel_Parser();
			case InputPinInSendObjActAsReqLabelEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionRequestNameLabel_Parser();
			case InputPinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionRequestStereotypeLabel_Parser();
			case ValuePinInSendObjActAsTargetLabelEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetNameLabel_Parser();
			case ValuePinInSendObjActAsTargetValueEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetValueLabel_Parser();
			case ValuePinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetStereotypeLabel_Parser();
			case ActionInputPinInSendObjActAsTargetLabelEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetNameLabel_Parser();
			case ActionInputPinInSendObjActAsTargetValueEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetValueLabel_Parser();
			case ActionInputPinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetStereotypeLabel_Parser();
			case InputPinInSendObjActAsTargetLabelEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionTargetNameLabel_Parser();
			case InputPinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionTargetStereotypeLabel_Parser();
			case SendSignalActionNameEditPart.VISUAL_ID:
				return getSendSignalAction_NameLabel_Parser();
			case SendSignalActionFloatingNameEditPart.VISUAL_ID:
				return getSendSignalAction_FloatingNameLabel_Parser();
			case ActionInputPinInSendSigActLabelEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentNameLabel_Parser();
			case ActionInputPinInSendSigActValueEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentValueLabel_Parser();
			case ActionInputPinInSendSigActAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentStereotypeLabel_Parser();
			case ValuePinInSendSigActLabelEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentNameLabel_Parser();
			case ValuePinInSendSigActValueEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentValueLabel_Parser();
			case ValuePinInSendSigActAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentStereotypeLabel_Parser();
			case InputPinInSendSigActLabelEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionArgumentNameLabel_Parser();
			case InputPinInSendSigActAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionArgumentStereotypeLabel_Parser();
			case ValuePinInSendSigActAsTargetLabelEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetNameLabel_Parser();
			case ValuePinInSendSigActAsTargetValueEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetValueLabel_Parser();
			case ValuePinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetStereotypeLabel_Parser();
			case ActionInputPinInSendSigActAsTargetLabelEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetNameLabel_Parser();
			case ActionInputPinInSendSigActAsTargetValueEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetValueLabel_Parser();
			case ActionInputPinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetStereotypeLabel_Parser();
			case InputPinInSendSigActAsTargetLabelEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionTargetNameLabel_Parser();
			case InputPinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionTargetStereotypeLabel_Parser();
			case ParameterNodeNameEditPart.VISUAL_ID:
				return getActivityParameterNode_NameLabel_Parser();
			case ActivityParameterNodeStreamLabelEditPart.VISUAL_ID:
				return getActivityParameterNode_StreamLabel_Parser();
			case AcceptEventActionLabelEditPart.VISUAL_ID:
				return getAcceptEventAction_NameLabel_Parser();
			case AcceptTimeEventActionLabelEditPart.VISUAL_ID:
				return getAcceptEventAction_TriggerLabel_Parser();
			case AcceptTimeEventActionAppliedStereotypeEditPart.VISUAL_ID:
				return getAcceptEventAction_StereotypeLabel_Parser();
			case AcceptEventActionFloatingNameEditPart.VISUAL_ID:
				return getAcceptEventAction_FloatingNameLabel_Parser();
			case OutputPinInAcceptEventActionLabelEditPart.VISUAL_ID:
				return getOutputPin_AcceptEventActionResultNameLabel_Parser();
			case OutputPinInAcceptEventActionAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_AcceptEventActionResultStereotypeLabel_Parser();
			case ValueSpecificationActionNameEditPart.VISUAL_ID:
				return getValueSpecificationAction_NameLabel_Parser();
			case ValueSpecificationActionFloatingNameEditPart.VISUAL_ID:
				return getValueSpecificationAction_FloatingNameLabel_Parser();
			case OutputPinInValSpecActLabelEditPart.VISUAL_ID:
				return getOutputPin_ValueSpecificationActionResultNameLabel_Parser();
			case OutputPinInValSpecActAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_ValueSpecificationActionResultStereotypeLabel_Parser();
			case ConditionalNodeKeywordEditPart.VISUAL_ID:
				return getConditionalNode_KeywordLabel_Parser();
			case ExpansionRegionKeywordEditPart.VISUAL_ID:
				return getExpansionRegion_KeywordLabel_Parser();
			case LoopNodeKeywordEditPart.VISUAL_ID:
				return getLoopNode_KeywordLabel_Parser();
			case InputPinInLoopNodeAsVariableLabelEditPart.VISUAL_ID:
				return getInputPin_LoopNodeVariableInputNameLabel_Parser();
			case InputPinInStructuredActivityNodeAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_LoopNodeVariableInputStereotypeLabel_Parser();
			case ValuePinInLoopNodeAsVariableLabelEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputNameLabel_Parser();
			case ValuePinInLoopNodeAsVariableValueEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputValueLabel_Parser();
			case ValuePinInLoopNodeAsVariableAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputStereotypeLabel_Parser();
			case ActionPinInLoopNodeAsVariableLabelEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputNameLabel_Parser();
			case ActionPinInLoopNodeAsVariableValueEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputValueLabel_Parser();
			case ActionPinInLoopNodeAsVariableAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputStereotypeLabel_Parser();
			case OutputPinInLoopNodeAsBodyOutputLabelEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeBodyOutputNameLabel_Parser();
			case OutputPinInLoopNodeAsBodyOutputAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeBodyOutputStereotypeLabel_Parser();
			case OutputPinInLoopNodeAsLoopVariableLabelEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeVariableNameLabel_Parser();
			case OutputPinInLoopNodeAsLoopVariableAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeVariableStereotypeLabel_Parser();
			case OutputPinInLoopNodeAsResultLabelEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeResultNameLabel_Parser();
			case OutputPinInLoopNodeAsResultAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeResultStereotypeLabel_Parser();
			case SequenceNodeKeywordEditPart.VISUAL_ID:
				return getSequenceNode_KeywordLabel_Parser();
			case StructuredActivityNodeKeywordEditPart.VISUAL_ID:
				return getStructuredActivityNode_KeywordLabel_Parser();
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID:
				return getInputPin_StructuredActivityNodeInputNameLabel_Parser();
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID:
				return getInputPin_StructuredActivityNodeInputStereotypeLabel_Parser();
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputNameLabel_Parser();
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputValueLabel_Parser();
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputStereotypeLabel_Parser();
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputNameLabel_Parser();
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputValueLabel_Parser();
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputStereotypeLabel_Parser();
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID:
				return getOutputPin_StructuredActivityNodeOutputNameLabel_Parser();
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_StructuredActivityNodeOutputStereotypeLabel_Parser();
			case ActivityPartitionNameEditPart.VISUAL_ID:
				return getActivityPartition_NameLabel_Parser();
			case ActivityPartitionFloatingNameEditPart.VISUAL_ID:
				return getActivityPartition_FloatingNameLabel_Parser();
			case CommentBodyLabelEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case ReadSelfActionNameEditPart.VISUAL_ID:
				return getReadSelfAction_NameLabel_Parser();
			case ReadSelfActionFloatingNameEditPart.VISUAL_ID:
				return getReadSelfAction_FloatingNameLabel_Parser();
			case OutputPinInReadSelfActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadSelfActionResultNameLabel_Parser();
			case OutputPinInReadSelfActionAppliedStereotypeEditPart.VISUAL_ID:
				return getOutputPin_ReadSelfActionResultStereotypeLabel_Parser();
			case ActivityNameEditPartCN.VISUAL_ID:
				return getActivity_NameLabel_CN_Parser();
			case ActivityIsSingleExecutionCNEditPart.VISUAL_ID:
				return getActivity_KeywordLabel_CN_Parser();
			case CreateObjectActionNameEditPart.VISUAL_ID:
				return getCreateObjectAction_NameLabel_Parser();
			case CreateObjectActionFloatingNameEditPart.VISUAL_ID:
				return getCreateObjectAction_FloatingNameLabel_Parser();
			case OutputPinInCreateObjectActionAsResultLabelEditPart.VISUAL_ID:
				return getOutputPin_CreateObjectActionResultNameLabel_Parser();
			case OutputPinInCreateObjectActionAsResultAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getOutputPin_CreateObjectActionResultStereotypeLabel_Parser();
			case ShapeNamedElementNameEditPart.VISUAL_ID:
				return getNamedElement_NameLabel_Parser();
			case ReadStructuralFeatureActionNameEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_NameLabel_Parser();
			case ReadStructuralFeatureActionFloatingNameEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_FloatingNameLabel_Parser();
			case InputPinInReadStructuralFeatureAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser();
			case InputPinInReadStructuralFeatureAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser();
			case ValuePinInReadStructuralFeatureAsObjectNameLabelEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectNameLabel_Parser();
			case ValuePinInReadStructuralFeatureAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectValueLabel_Parser();
			case ValuePinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser();
			case ActionPinInReadStructuralFeatureAsObjectNameLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectNameLabel_Parser();
			case ActionPinInReadStructuralFeatureAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectValueLabel_Parser();
			case ActionPinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Parser();
			case OutputPinInReadStructuralFeatureAsResultLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadStructuralFeatureActionResultNameLabel_Parser();
			case InputPinInReadStructuralFeatureAsResultWrappingLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadStructuralFeatureActionResultStereotypeLabel_Parser();
			case AddStructuralFeatureValueActionNameEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_NameLabel_Parser();
			case AddStructuralFeatureValueActionFloatingNameEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_FloatingNameLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsValueLabel2EditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabel2EditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser();
			case InputPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectNameLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectValueLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsValueLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueNameLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsValueValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueValueLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsInserAtValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser();
			case ValuePinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueNameLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsValueValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueValueLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsInserAtValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Parser();
			case ActionPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Parser();
			case OutputPinInAddStructuralFeatureValueActionAsResultLabel3EditPart.VISUAL_ID:
				return getOutputPin_AddStructuralFeatureValueActionResultNameLabel_Parser();
			case OutputPinInAddStructuralFeatureValueActionAsResultAppliedStereotypeWrappingLabel3EditPart.VISUAL_ID:
				return getOutputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Parser();
			case DestroyObjectActionNameEditPart.VISUAL_ID:
				return getDestroyObjectAction_NameLabel_Parser();
			case DestroyObjectActionFloatingNameEditPart.VISUAL_ID:
				return getDestroyObjectAction_FloatingNameLabel_Parser();
			case InputPinInDestroyObjectActionLabelEditPart.VISUAL_ID:
				return getInputPin_DestroyObjectActionTargetNameLabel_Parser();
			case InputPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_DestroyObjectActionTargetStereotypeLabel_Parser();
			case ValuePinInDestroyObjectActionLabelEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetNameLabel_Parser();
			case ValuePinInDestroyObjectActionValueEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetValueLabel_Parser();
			case ValuePinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetStereotypeLabel_Parser();
			case ActionPinInDestroyObjectActionLabelEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetNameLabel_Parser();
			case ActionPinInDestroyObjectActionValueEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetValueLabel_Parser();
			case ActionPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetStereotypeLabel_Parser();
			case ReadVariableActionNameEditPart.VISUAL_ID:
				return getReadVariableAction_NameLabel_Parser();
			case ReadVariableActionFloatingNameEditPart.VISUAL_ID:
				return getReadVariableAction_FloatingNameLabel_Parser();
			case OutputPinInReadVariableActionAsResultLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadVariableActionResultNameLabel_Parser();
			case OutputPinInReadVariableActionAsResultAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadVariableActionResultStereotypeLabel_Parser();
			case AddVariableValueActionNameEditPart.VISUAL_ID:
				return getAddVariableValueAction_NameLabel_Parser();
			case AddVariableValueActionFloatingNameEditPart.VISUAL_ID:
				return getAddVariableValueAction_FloatingNameLabel_Parser();
			case InputPinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionInsertAtNameLabel_Parser();
			case InputPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser();
			case InputPinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionValueNameLabel_Parser();
			case InputPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionValueStereotypeLabel_Parser();
			case ValuePinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtNameLabel_Parser();
			case ValuePinInAddVariableValueActionAsInsertAtValueEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtValueLabel_Parser();
			case ValuePinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtStereotypeLabel_Parser();
			case ValuePinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueNameLabel_Parser();
			case ValuePinInAddVariableValueActionAsValueValueEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueValueLabel_Parser();
			case ValuePinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueStereotypeLabel_Parser();
			case ActionPinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtNameLabel_Parser();
			case ActionPinInAddVariableValueActionAsInsertAtValueEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtValueLabel_Parser();
			case ActionPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Parser();
			case ActionPinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueNameLabel_Parser();
			case ActionPinInAddVariableValueActionAsValueValueEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueValueLabel_Parser();
			case ActionPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueStereotypeLabel_Parser();
			case BroadcastSignalActionNameEditPart.VISUAL_ID:
				return getBroadcastSignalAction_NameLabel_Parser();
			case BroadcastSignalActionFloatingNameEditPart.VISUAL_ID:
				return getBroadcastSignalAction_FloatingNameLabel_Parser();
			case InputPinInBroadcastSignalActionLabelEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentNameLabel_Parser();
			case InputPinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentValueLabel_Parser();
			case InputPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser();
			case ValuePinInBroadcastSignalActionLabelEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentNameLabel_Parser();
			case ValuePinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentValueLabel_Parser();
			case ValuePinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentStereotypeLabel_Parser();
			case ActionPinInBroadcastSignalActionLabelEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentNameLabel_Parser();
			case ActionPinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentValueLabel_Parser();
			case ActionPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Parser();
			case CentralBufferNodeLabelEditPart.VISUAL_ID:
				return getCentralBufferNode_NameLabel_Parser();
			case CentralBufferNodeSelectionEditPart.VISUAL_ID:
				return getCentralBufferNode_SelectionLabel_Parser();
			case CentralBufferNodeFloatingNameEditPart.VISUAL_ID:
				return getCentralBufferNode_FloatingNameLabel_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPartCN.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case StartObjectBehaviorActionNameEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_NameLabel_Parser();
			case StartObjectBehaviorActionFloatingNameEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_FloatingNameLabel_Parser();
			case OutputPinInStartObjectBehaviorActionLabelEditPart.VISUAL_ID:
				return getOutputPin_StartObjectBehaviorActionResultNameLabel_Parser();
			case OutputPinInStartObjectBehaviorActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_StartObjectBehaviorActionResultStereotypeLabel_Parser();
			case InputPinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionObjectNameLabel_Parser();
			case InputPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectNameLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectValueLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectStereotypeLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectNameLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectValueLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Parser();
			case InputPinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser();
			case InputPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentNameLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsArgumentValueEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentValueLabel_Parser();
			case ValuePinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentNameLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsArgumentValueEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentValueLabel_Parser();
			case ActionPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Parser();
			case TestIdentityActionNameEditPart.VISUAL_ID:
				return getTestIdentityAction_NameLabel_Parser();
			case TestIdentityActionFloatingNameEditPart.VISUAL_ID:
				return getTestIdentityAction_FloatingNameLabel_Parser();
			case OutputPinInTestIdentityActionItemLabelEditPart.VISUAL_ID:
				return getOutputPin_TestIdentityActionResultNameLabel_Parser();
			case OutputPinInTestIdentityActionItemAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_TestIdentityActionResultStereotypeLabel_Parser();
			case InputPinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionFirstNameLabel_Parser();
			case InputPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionFirstStereotypeLabel_Parser();
			case InputPinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionSecondNameLabel_Parser();
			case InputPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionSecondStereotypeLabel_Parser();
			case ValuePinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstNameLabel_Parser();
			case ValuePinInTestIdentityActionAsFirstValueEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstValueLabel_Parser();
			case ValuePinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstStereotypeLabel_Parser();
			case ValuePinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondNameLabel_Parser();
			case ValuePinInTestIdentityActionAsSecondValueEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondValueLabel_Parser();
			case ValuePinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondStereotypeLabel_Parser();
			case ActionPinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstNameLabel_Parser();
			case ActionPinInTestIdentityActionAsFirstValueEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstValueLabel_Parser();
			case ActionPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstStereotypeLabel_Parser();
			case ActionPinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondNameLabel_Parser();
			case ActionPinInTestIdentityActionAsSecondValueEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondValueLabel_Parser();
			case ActionPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondStereotypeLabel_Parser();
			case ClearStructuralFeatureActionNameEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_NameLabel_Parser();
			case ClearStructuralFeatureActionFloatingNameEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_FloatingNameLabel_Parser();
			case OutputPinInClearStructuralFeatureActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ClearStructuralFeatureActionResultNameLabel_Parser();
			case OutputPinInClearStructuralFeatureActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_ClearStructuralFeatureActionResultStereotypeLabel_Parser();
			case InputPinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser();
			case InputPinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser();
			case ValuePinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectNameLabel_Parser();
			case ValuePinInClearStructuralFeatureActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectValueLabel_Parser();
			case ValuePinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser();
			case ActionInputPinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectNameLabel_Parser();
			case ActionInputPinInClearStructuralFeatureActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectValueLabel_Parser();
			case ActionInputPinInClearStructFeatActAsObjectAppliedStereotypeEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Parser();
			case CreateLinkActionNameEditPart.VISUAL_ID:
				return getCreateLinkAction_NameLabel_Parser();
			case CreateLinkActionFloatingNameEditPart.VISUAL_ID:
				return getCreateLinkAction_FloatingNameLabel_Parser();
			case InputPinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getInputPin_CreateLinkActionInputNameLabel_Parser();
			case InputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_CreateLinkActionInputStereotypeLabel_Parser();
			case ValuePinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputNameLabel_Parser();
			case ValuePinInCreateLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputValueLabel_Parser();
			case ValuePinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputStereotypeLabel_Parser();
			case ActionInputPinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputNameLabel_Parser();
			case ActionInputPinInCreateLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputValueLabel_Parser();
			case ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputStereotypeLabel_Parser();
			case ReadLinkActionNameEditPart.VISUAL_ID:
				return getReadLinkAction_NameLabel_Parser();
			case ReadLinkActionFloatingNameEditPart.VISUAL_ID:
				return getReadLinkAction_FloatingNameLabel_Parser();
			case OutputPinInReadLinkActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadLinkActionResultNameLabel_Parser();
			case OutputPinInReadLinkActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadLinkActionResultStereotypeLabel_Parser();
			case InputPinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getInputPin_ReadLinkActionInputNameLabel_Parser();
			case InputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ReadLinkActionInputStereotypeLabel_Parser();
			case ValuePinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputNameLabel_Parser();
			case ValuePinInReadLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputValueLabel_Parser();
			case ValuePinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputStereotypeLabel_Parser();
			case ActionInputPinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputNameLabel_Parser();
			case ActionInputPinInReadLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputValueLabel_Parser();
			case ActionInputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputStereotypeLabel_Parser();
			case DestroyLinkActionNameEditPart.VISUAL_ID:
				return getDestroyLinkAction_NameLabel_Parser();
			case DestroyLinkActionFloatingNameEditPart.VISUAL_ID:
				return getDestroyLinkAction_FloatingNameLabel_Parser();
			case InputPinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getInputPin_DestroyLinkActionInputNameLabel_Parser();
			case InputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_DestroyLinkActionInputStereotypeLabel_Parser();
			case ValuePinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputNameLabel_Parser();
			case ValuePinInDestroyLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputValueLabel_Parser();
			case ValuePinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputStereotypeLabel_Parser();
			case ActionInputPinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputNameLabel_Parser();
			case ActionInputPinInDestroyLinkActionAsInputValueValueEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputValueLabel_Parser();
			case ActionInputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputStereotypeLabel_Parser();
			case ClearAssociationActionNameEditPart.VISUAL_ID:
				return getClearAssociationAction_NameLabel_Parser();
			case ClearAssociationActionFloatingNameEditPart.VISUAL_ID:
				return getClearAssociationAction_FloatingNameLabel_Parser();
			case InputPinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_ClearAssociationActionObjectNameLabel_Parser();
			case InputPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ClearAssociationActionObjectStereotypeLabel_Parser();
			case ValuePinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectNameLabel_Parser();
			case ValuePinInClearAssociationActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectValueLabel_Parser();
			case ValuePinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectStereotypeLabel_Parser();
			case ActionPinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectNameLabel_Parser();
			case ActionPinInClearAssociationActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectValueLabel_Parser();
			case ActionPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectStereotypeLabel_Parser();
			case ReadExtentActionNameEditPart.VISUAL_ID:
				return getReadExtentAction_NameLabel_Parser();
			case ReadExtentActionFloatingNameEditPart.VISUAL_ID:
				return getReadExtentAction_FloatingNameLabel_Parser();
			case OutputPinInReadExtentActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadExtentActionResultNameLabel_Parser();
			case OutputPinInReadExtentActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadExtentActionResultStereotypeLabel_Parser();
			case ReclassifyObjectActionNameEditPart.VISUAL_ID:
				return getReclassifyObjectAction_NameLabel_Parser();
			case ReclassifyObjectActionFloatingNameEditPart.VISUAL_ID:
				return getReclassifyObjectAction_FloatingNameLabel_Parser();
			case InputPinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_ReclassifyObjectActionObjectNameLabel_Parser();
			case InputPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser();
			case ValuePinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectNameLabel_Parser();
			case ValuePinInReclassifyObjectActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectValueLabel_Parser();
			case ValuePinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectStereotypeLabel_Parser();
			case ActionPinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectNameLabel_Parser();
			case ActionPinInReclassifyObjectActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectValueLabel_Parser();
			case ActionPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Parser();
			case ReadIsClassifiedObjectActionNameEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_NameLabel_Parser();
			case ReadIsClassifiedObjectActionFloatingNameEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_FloatingNameLabel_Parser();
			case OutputPinInReadIsClassifiedObjectActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadIsClassifiedObjectActionResultNameLabel_Parser();
			case OutputPinInReadIsClassifiedObjectActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Parser();
			case InputPinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser();
			case InputPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser();
			case ValuePinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectNameLabel_Parser();
			case ValuePinInReadIsClassifiedObjectActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectValueLabel_Parser();
			case ValuePinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser();
			case ActionPinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Parser();
			case ActionPinInReadIsClassifiedObjectActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Parser();
			case ActionPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Parser();
			case ReduceActionNameEditPart.VISUAL_ID:
				return getReduceAction_NameLabel_Parser();
			case ReduceActionFloatingNameEditPart.VISUAL_ID:
				return getReduceAction_FloatingNameLabel_Parser();
			case OutputPinInReduceActionLabelEditPart.VISUAL_ID:
				return getOutputPin_ReduceActionResultNameLabel_Parser();
			case OutputPinInReduceActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_ReduceActionResultStereotypeLabel_Parser();
			case InputPinInReduceActionAsCollectionLabelEditPart.VISUAL_ID:
				return getInputPin_ReduceActionCollectionNameLabel_Parser();
			case InputPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_ReduceActionCollectionStereotypeLabel_Parser();
			case ValuePinInReduceActionAsCollectionLabelEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionNameLabel_Parser();
			case ValuePinInReduceActionAsCollectionValueEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionValueLabel_Parser();
			case ValuePinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionStereotypeLabel_Parser();
			case ActionPinInReduceActionAsCollectionLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionNameLabel_Parser();
			case ActionPinInReduceActionAsCollectionValueEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionValueLabel_Parser();
			case ActionPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionStereotypeLabel_Parser();
			case StartClassifierBehaviorActionNameEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_NameLabel_Parser();
			case StartClassifierBehaviorActionFloatingNameEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_FloatingNameLabel_Parser();
			case InputPinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser();
			case InputPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser();
			case ValuePinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectNameLabel_Parser();
			case ValuePinInStartClassifierBehaviorActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectValueLabel_Parser();
			case ValuePinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser();
			case ActionPinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectNameLabel_Parser();
			case ActionPinInStartClassifierBehaviorActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectValueLabel_Parser();
			case ActionPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Parser();
			case CreateLinkObjectActionNameEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_NameLabel_Parser();
			case CreateLinkObjectActionFloatingNameEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_FloatingNameLabel_Parser();
			case InputPinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID:
				return getInputPin_CreateLinkObjectActionInputNameLabel_Parser();
			case InputPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser();
			case ValuePinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputNameLabel_Parser();
			case ValuePinInCreateLinkObjectActionAsInputValueValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputValueLabel_Parser();
			case ValuePinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputStereotypeLabel_Parser();
			case ActionPinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputNameLabel_Parser();
			case ActionPinInCreateLinkObjectActionAsInputValueValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputValueLabel_Parser();
			case ActionPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputStereotypeLabel_Parser();
			case OutputPinInCreateLinkObjectActionLabelEditPart.VISUAL_ID:
				return getOutputPin_CreateLinkObjectActionResultNameLabel_Parser();
			case OutputPinInCreateLinkObjectActionAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_CreateLinkObjectActionResultStereotypeLabel_Parser();
			case UnmarshallActionNameEditPart.VISUAL_ID:
				return getUnmarshallAction_NameLabel_Parser();
			case UnmarshallActionFloatingNameEditPart.VISUAL_ID:
				return getUnmarshallAction_FloatingNameLabel_Parser();
			case InputPinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID:
				return getInputPin_UnmarshallActionObjectNameLabel_Parser();
			case InputPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getInputPin_UnmarshallActionObjectStereotypeLabel_Parser();
			case ValuePinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectNameLabel_Parser();
			case ValuePinInUnmarshallActionAsObjectValueEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectValueLabel_Parser();
			case ValuePinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectStereotypeLabel_Parser();
			case ActionPinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectNameLabel_Parser();
			case ActionPinInUnmarshallActionAsObjectValueEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectValueLabel_Parser();
			case ActionPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectStereotypeLabel_Parser();
			case OutputPinInUnmarshallActionAsResultLabelEditPart.VISUAL_ID:
				return getOutputPin_UnmarshallActionResultNameLabel_Parser();
			case OutputPinInUnmarshallActionAsResultAppliedStereotypeLabelEditPart.VISUAL_ID:
				return getOutputPin_UnmarshallActionResultStereotypeLabel_Parser();
			case ObjectFlowNameEditPart.VISUAL_ID:
				return getObjectFlow_NameLabel_Parser();
			case ObjectFlowWeightEditPart.VISUAL_ID:
				return getObjectFlow_WeightLabel_Parser();
			case ObjectFlowSelectionEditPart.VISUAL_ID:
				return getObjectFlow_SelectionLabel_Parser();
			case ObjectFlowTransformationEditPart.VISUAL_ID:
				return getObjectFlow_TransformationLabel_Parser();
			case DecisionInputFlowEditPart.VISUAL_ID:
				return getObjectFlow_KeywordLabel_Parser();
			case ObjectFlowGuardEditPart.VISUAL_ID:
				return getObjectFlow_GuardLabel_Parser();
			case ObjectFlowAppliedStereotypeEditPart.VISUAL_ID:
				return getObjectFlow_StereotypeLabel_Parser();
			case ControlFlowNameEditPart.VISUAL_ID:
				return getControlFlow_NameLabel_Parser();
			case ControlFlowWeightEditPart.VISUAL_ID:
				return getControlFlow_WeightLabel_Parser();
			case ControlFlowGuardEditPart.VISUAL_ID:
				return getControlFlow_GuardLabel_Parser();
			case ControlFlowAppliedStereotypeEditPart.VISUAL_ID:
				return getControlFlow_StereotypeLabel_Parser();
			case ExceptionHandlerTypeEditPart.VISUAL_ID:
				return getExceptionHandler_TypeLabel_Parser();
			}
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 *
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		String vid = hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(vid));
		}
		View view = hint.getAdapter(View.class);
		if (view != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (UMLElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
}
