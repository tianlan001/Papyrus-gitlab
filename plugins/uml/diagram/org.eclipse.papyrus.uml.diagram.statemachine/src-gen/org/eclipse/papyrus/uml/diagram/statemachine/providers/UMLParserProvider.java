/**
 * Copyright (c) 2014 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.statemachine.providers;

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
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.DeferrableTriggerParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.DoActivityStateBehaviorParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.EntryStateBehaviorParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.ExitStateBehaviorParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.InternalTransitionParser;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.TransitionPropertiesParser;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DeferrableTriggerEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser stateMachine_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateMachine_NameLabel_Parser() {
		if (stateMachine_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stateMachine_NameLabel_Parser = parser;
		}
		return stateMachine_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser state_NameLabel_TN_Parser;

	/**
	 * @generated
	 */
	private IParser getState_NameLabel_TN_Parser() {
		if (state_NameLabel_TN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			state_NameLabel_TN_Parser = parser;
		}
		return state_NameLabel_TN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser finalState_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFinalState_FloatingNameLabel_Parser() {
		if (finalState_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			finalState_FloatingNameLabel_Parser = parser;
		}
		return finalState_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser finalState_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFinalState_StereotypeLabel_Parser() {
		if (finalState_StereotypeLabel_Parser == null) {
			finalState_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return finalState_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser state_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getState_NameLabel_Parser() {
		if (state_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			state_NameLabel_Parser = parser;
		}
		return state_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser state_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getState_FloatingNameLabel_Parser() {
		if (state_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			state_FloatingNameLabel_Parser = parser;
		}
		return state_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_InitialFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_InitialFloatingNameLabel_Parser() {
		if (pseudostate_InitialFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_InitialFloatingNameLabel_Parser = parser;
		}
		return pseudostate_InitialFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_InitialStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_InitialStereotypeLabel_Parser() {
		if (pseudostate_InitialStereotypeLabel_Parser == null) {
			pseudostate_InitialStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_InitialStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_JoinFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_JoinFloatingNameLabel_Parser() {
		if (pseudostate_JoinFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_JoinFloatingNameLabel_Parser = parser;
		}
		return pseudostate_JoinFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_JoinStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_JoinStereotypeLabel_Parser() {
		if (pseudostate_JoinStereotypeLabel_Parser == null) {
			pseudostate_JoinStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_JoinStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_ForkFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ForkFloatingNameLabel_Parser() {
		if (pseudostate_ForkFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_ForkFloatingNameLabel_Parser = parser;
		}
		return pseudostate_ForkFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_ForkStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ForkStereotypeLabel_Parser() {
		if (pseudostate_ForkStereotypeLabel_Parser == null) {
			pseudostate_ForkStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_ForkStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_ChoiceFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ChoiceFloatingNameLabel_Parser() {
		if (pseudostate_ChoiceFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_ChoiceFloatingNameLabel_Parser = parser;
		}
		return pseudostate_ChoiceFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_ChoiceStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ChoiceStereotypeLabel_Parser() {
		if (pseudostate_ChoiceStereotypeLabel_Parser == null) {
			pseudostate_ChoiceStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_ChoiceStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_JunctionFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_JunctionFloatingNameLabel_Parser() {
		if (pseudostate_JunctionFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_JunctionFloatingNameLabel_Parser = parser;
		}
		return pseudostate_JunctionFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_JunctionStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_JunctionStereotypeLabel_Parser() {
		if (pseudostate_JunctionStereotypeLabel_Parser == null) {
			pseudostate_JunctionStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_JunctionStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_ShallowHistoryFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ShallowHistoryFloatingNameLabel_Parser() {
		if (pseudostate_ShallowHistoryFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_ShallowHistoryFloatingNameLabel_Parser = parser;
		}
		return pseudostate_ShallowHistoryFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_ShallowHistoryStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ShallowHistoryStereotypeLabel_Parser() {
		if (pseudostate_ShallowHistoryStereotypeLabel_Parser == null) {
			pseudostate_ShallowHistoryStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_ShallowHistoryStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_DeepHistoryFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_DeepHistoryFloatingNameLabel_Parser() {
		if (pseudostate_DeepHistoryFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_DeepHistoryFloatingNameLabel_Parser = parser;
		}
		return pseudostate_DeepHistoryFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_DeepHistoryStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_DeepHistoryStereotypeLabel_Parser() {
		if (pseudostate_DeepHistoryStereotypeLabel_Parser == null) {
			pseudostate_DeepHistoryStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_DeepHistoryStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_TerminateFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_TerminateFloatingNameLabel_Parser() {
		if (pseudostate_TerminateFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_TerminateFloatingNameLabel_Parser = parser;
		}
		return pseudostate_TerminateFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_TerminateStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_TerminateStereotypeLabel_Parser() {
		if (pseudostate_TerminateStereotypeLabel_Parser == null) {
			pseudostate_TerminateStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_TerminateStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_EntryPointFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_EntryPointFloatingNameLabel_Parser() {
		if (pseudostate_EntryPointFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_EntryPointFloatingNameLabel_Parser = parser;
		}
		return pseudostate_EntryPointFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_EntryPointStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_EntryPointStereotypeLabel_Parser() {
		if (pseudostate_EntryPointStereotypeLabel_Parser == null) {
			pseudostate_EntryPointStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_EntryPointStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser pseudostate_ExitPointFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ExitPointFloatingNameLabel_Parser() {
		if (pseudostate_ExitPointFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			pseudostate_ExitPointFloatingNameLabel_Parser = parser;
		}
		return pseudostate_ExitPointFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser pseudostate_ExitPointStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPseudostate_ExitPointStereotypeLabel_Parser() {
		if (pseudostate_ExitPointStereotypeLabel_Parser == null) {
			pseudostate_ExitPointStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return pseudostate_ExitPointStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connectionPointReference_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnectionPointReference_NameLabel_Parser() {
		if (connectionPointReference_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			connectionPointReference_NameLabel_Parser = parser;
		}
		return connectionPointReference_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser connectionPointReference_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnectionPointReference_StereotypeLabel_Parser() {
		if (connectionPointReference_StereotypeLabel_Parser == null) {
			connectionPointReference_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return connectionPointReference_StereotypeLabel_Parser;
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
	private InternalTransitionParser transition_InternalTransitionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTransition_InternalTransitionLabel_Parser() {
		if (transition_InternalTransitionLabel_Parser == null) {
			transition_InternalTransitionLabel_Parser = new InternalTransitionParser();
		}
		return transition_InternalTransitionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private EntryStateBehaviorParser behavior_EntryBehaviorLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getBehavior_EntryBehaviorLabel_Parser() {
		if (behavior_EntryBehaviorLabel_Parser == null) {
			behavior_EntryBehaviorLabel_Parser = new EntryStateBehaviorParser();
		}
		return behavior_EntryBehaviorLabel_Parser;
	}

	/**
	 * @generated
	 */
	private DoActivityStateBehaviorParser behavior_DoActivityBehaviorLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getBehavior_DoActivityBehaviorLabel_Parser() {
		if (behavior_DoActivityBehaviorLabel_Parser == null) {
			behavior_DoActivityBehaviorLabel_Parser = new DoActivityStateBehaviorParser();
		}
		return behavior_DoActivityBehaviorLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ExitStateBehaviorParser behavior_ExitBehaviorLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getBehavior_ExitBehaviorLabel_Parser() {
		if (behavior_ExitBehaviorLabel_Parser == null) {
			behavior_ExitBehaviorLabel_Parser = new ExitStateBehaviorParser();
		}
		return behavior_ExitBehaviorLabel_Parser;
	}

	/**
	 * @generated
	 */
	private DeferrableTriggerParser trigger_DeferrableTriggerLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTrigger_DeferrableTriggerLabel_Parser() {
		if (trigger_DeferrableTriggerLabel_Parser == null) {
			trigger_DeferrableTriggerLabel_Parser = new DeferrableTriggerParser();
		}
		return trigger_DeferrableTriggerLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser transition_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTransition_NameLabel_Parser() {
		if (transition_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			transition_NameLabel_Parser = parser;
		}
		return transition_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private TransitionPropertiesParser transition_GuardLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTransition_GuardLabel_Parser() {
		if (transition_GuardLabel_Parser == null) {
			transition_GuardLabel_Parser = new TransitionPropertiesParser();
		}
		return transition_GuardLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser transition_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTransition_StereotypeLabel_Parser() {
		if (transition_StereotypeLabel_Parser == null) {
			transition_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return transition_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser generalization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralization_StereotypeLabel_Parser() {
		if (generalization_StereotypeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getGeneralization_IsSubstitutable()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("<<{0}>>"); //$NON-NLS-1$
			parser.setEditorPattern("<<{0}>>"); //$NON-NLS-1$
			parser.setEditPattern("<<{0}>>"); //$NON-NLS-1$
			generalization_StereotypeLabel_Parser = parser;
		}
		return generalization_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case StateMachineNameEditPart.VISUAL_ID:
				return getStateMachine_NameLabel_Parser();
			case StateNameEditPartTN.VISUAL_ID:
				return getState_NameLabel_TN_Parser();
			case FinalStateFloatingLabelEditPart.VISUAL_ID:
				return getFinalState_FloatingNameLabel_Parser();
			case FinalStateStereotypeEditPart.VISUAL_ID:
				return getFinalState_StereotypeLabel_Parser();
			case StateNameEditPart.VISUAL_ID:
				return getState_NameLabel_Parser();
			case StateFloatingLabelEditPart.VISUAL_ID:
				return getState_FloatingNameLabel_Parser();
			case PseudostateInitialFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_InitialFloatingNameLabel_Parser();
			case PseudostateInitialStereotypeEditPart.VISUAL_ID:
				return getPseudostate_InitialStereotypeLabel_Parser();
			case PseudostateJoinFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_JoinFloatingNameLabel_Parser();
			case PseudostateJoinStereotypeEditPart.VISUAL_ID:
				return getPseudostate_JoinStereotypeLabel_Parser();
			case PseudostateForkNameEditPart.VISUAL_ID:
				return getPseudostate_ForkFloatingNameLabel_Parser();
			case PseudostateForkStereotypeEditPart.VISUAL_ID:
				return getPseudostate_ForkStereotypeLabel_Parser();
			case PseudostateChoiceFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_ChoiceFloatingNameLabel_Parser();
			case PseudostateChoiceStereotypeEditPart.VISUAL_ID:
				return getPseudostate_ChoiceStereotypeLabel_Parser();
			case PseudostateJunctionFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_JunctionFloatingNameLabel_Parser();
			case PseudostateJunctionStereotypeEditPart.VISUAL_ID:
				return getPseudostate_JunctionStereotypeLabel_Parser();
			case PseudostateShallowHistoryFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_ShallowHistoryFloatingNameLabel_Parser();
			case PseudostateShallowHistoryStereotypeEditPart.VISUAL_ID:
				return getPseudostate_ShallowHistoryStereotypeLabel_Parser();
			case PseudostateDeepHistoryFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_DeepHistoryFloatingNameLabel_Parser();
			case PseudostateDeepHistoryStereotypeEditPart.VISUAL_ID:
				return getPseudostate_DeepHistoryStereotypeLabel_Parser();
			case PseudostateTerminateFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_TerminateFloatingNameLabel_Parser();
			case PseudostateTerminateStereotypeEditPart.VISUAL_ID:
				return getPseudostate_TerminateStereotypeLabel_Parser();
			case PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_EntryPointFloatingNameLabel_Parser();
			case PseudostateEntryPointStereotypeEditPart.VISUAL_ID:
				return getPseudostate_EntryPointStereotypeLabel_Parser();
			case PseudostateExitPointFloatingLabelEditPart.VISUAL_ID:
				return getPseudostate_ExitPointFloatingNameLabel_Parser();
			case PseudostateExitPointStereotypeEditPart.VISUAL_ID:
				return getPseudostate_ExitPointStereotypeLabel_Parser();
			case ConnectionPointReferenceNameEditPart.VISUAL_ID:
				return getConnectionPointReference_NameLabel_Parser();
			case ConnectionPointReferenceStereotypeEditPart.VISUAL_ID:
				return getConnectionPointReference_StereotypeLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case ConstraintNameLabelEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case InternalTransitionEditPart.VISUAL_ID:
				return getTransition_InternalTransitionLabel_Parser();
			case EntryStateBehaviorEditPart.VISUAL_ID:
				return getBehavior_EntryBehaviorLabel_Parser();
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
				return getBehavior_DoActivityBehaviorLabel_Parser();
			case ExitStateBehaviorEditPart.VISUAL_ID:
				return getBehavior_ExitBehaviorLabel_Parser();
			case DeferrableTriggerEditPart.VISUAL_ID:
				return getTrigger_DeferrableTriggerLabel_Parser();
			case TransitionNameEditPart.VISUAL_ID:
				return getTransition_NameLabel_Parser();
			case TransitionGuardEditPart.VISUAL_ID:
				return getTransition_GuardLabel_Parser();
			case TransitionStereotypeEditPart.VISUAL_ID:
				return getTransition_StereotypeLabel_Parser();
			case GeneralizationStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
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
