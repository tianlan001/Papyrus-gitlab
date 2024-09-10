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
package org.eclipse.papyrus.uml.diagram.statemachine.part;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.expressions.UMLOCLFactory;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DeferrableTriggerEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionStereotypeEditPart;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 *
 * @generated
 */
public class UMLVisualIDRegistry {

	// Uncomment for debug purpose ?
	// /**
	// * @generated
	// */
	// private static final String DEBUG_KEY = "org.eclipse.papyrus.uml.diagram.statemachine/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (PackageEditPart.MODEL_ID.equals(view.getType())) {
				return PackageEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static String getVisualID(String type) {
		return type;
	}

	/**
	 * @generated
	 */
	public static String getType(String visualID) {
		return visualID;
	}

	/**
	 * @generated
	 */
	public static String getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		return PackageEditPart.VISUAL_ID;
	}

	/**
	 * @generated
	 */
	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		String containerModelID = org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!PackageEditPart.MODEL_ID.equals(containerModelID)) {
			return ""; //$NON-NLS-1$
		}
		String containerVisualID;
		if (PackageEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PackageEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case PackageEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getStateMachine().isSuperTypeOf(domainElement.eClass())) {
					return StateMachineEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getState().isSuperTypeOf(domainElement.eClass())) {
					return StateEditPartTN.VISUAL_ID;
				}
				break;
			case StateMachineEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				break;
			case StateEditPartTN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				break;
			case StateEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getBehavior().isSuperTypeOf(domainElement.eClass()) && isBehavior_EntryBehaviorLabel((Behavior) domainElement)) {
					return EntryStateBehaviorEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getBehavior().isSuperTypeOf(domainElement.eClass()) && isBehavior_DoActivityBehaviorLabel((Behavior) domainElement)) {
					return DoActivityStateBehaviorStateEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getBehavior().isSuperTypeOf(domainElement.eClass()) && isBehavior_ExitBehaviorLabel((Behavior) domainElement)) {
					return ExitStateBehaviorEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTrigger().isSuperTypeOf(domainElement.eClass())) {
					return DeferrableTriggerEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTransition().isSuperTypeOf(domainElement.eClass())) {
					return InternalTransitionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getRegion().isSuperTypeOf(domainElement.eClass())) {
					return RegionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConnectionPointReference().isSuperTypeOf(domainElement.eClass())) {
					return ConnectionPointReferenceEditPart.VISUAL_ID;
				}
				break;
			case RegionCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_InitialShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateInitialEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_JoinShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateJoinEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ForkShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateForkEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ChoiceShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateChoiceEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_JunctionShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateJunctionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ShallowHistoryShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateShallowHistoryEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_DeepHistoryShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateDeepHistoryEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_TerminateShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateTerminateEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getFinalState().isSuperTypeOf(domainElement.eClass())) {
					return FinalStateEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getState().isSuperTypeOf(domainElement.eClass())) {
					return StateEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPart.VISUAL_ID;
				}
				break;
			case StateMachineCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getRegion().isSuperTypeOf(domainElement.eClass())) {
					return RegionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				break;
			case StateCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getRegion().isSuperTypeOf(domainElement.eClass())) {
					return RegionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConnectionPointReference().isSuperTypeOf(domainElement.eClass())) {
					return ConnectionPointReferenceEditPart.VISUAL_ID;
				}
				break;
			case StateCompartmentEditPartTN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getRegion().isSuperTypeOf(domainElement.eClass())) {
					return RegionEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_EntryPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateEntryPointEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPseudostate().isSuperTypeOf(domainElement.eClass()) && isPseudostate_ExitPointShape(containerView, (Pseudostate) domainElement)) {
					return PseudostateExitPointEditPart.VISUAL_ID;
				}
				break;
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, String nodeVisualID) {
		String containerModelID = org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!PackageEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		String containerVisualID;
		if (PackageEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PackageEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case PackageEditPart.VISUAL_ID:
				if (StateMachineEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateMachineEditPart.VISUAL_ID:
				if (StateMachineNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateMachineCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateEditPartTN.VISUAL_ID:
				if (StateNameEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateCompartmentEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case RegionEditPart.VISUAL_ID:
				if (RegionCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FinalStateEditPart.VISUAL_ID:
				if (FinalStateFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FinalStateStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateEditPart.VISUAL_ID:
				if (StateNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EntryStateBehaviorEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DoActivityStateBehaviorStateEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExitStateBehaviorEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeferrableTriggerEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (InternalTransitionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (RegionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConnectionPointReferenceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateInitialEditPart.VISUAL_ID:
				if (PseudostateInitialFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateInitialStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateJoinEditPart.VISUAL_ID:
				if (PseudostateJoinFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateJoinStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateForkEditPart.VISUAL_ID:
				if (PseudostateForkNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateForkStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateChoiceEditPart.VISUAL_ID:
				if (PseudostateChoiceFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateChoiceStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateJunctionEditPart.VISUAL_ID:
				if (PseudostateJunctionFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateJunctionStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
				if (PseudostateShallowHistoryFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateShallowHistoryStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
				if (PseudostateDeepHistoryFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateDeepHistoryStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateTerminateEditPart.VISUAL_ID:
				if (PseudostateTerminateFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateTerminateStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateEntryPointEditPart.VISUAL_ID:
				if (PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PseudostateExitPointEditPart.VISUAL_ID:
				if (PseudostateExitPointFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				if (ConnectionPointReferenceNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConnectionPointReferenceStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPart.VISUAL_ID:
				if (CommentBodyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ConstraintEditPart.VISUAL_ID:
				if (ConstraintNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintBodyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case RegionCompartmentEditPart.VISUAL_ID:
				if (PseudostateInitialEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateJoinEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateForkEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateChoiceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateJunctionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateShallowHistoryEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateDeepHistoryEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateTerminateEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FinalStateEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateMachineCompartmentEditPart.VISUAL_ID:
				if (RegionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateCompartmentEditPart.VISUAL_ID:
				if (RegionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConnectionPointReferenceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateCompartmentEditPartTN.VISUAL_ID:
				if (RegionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateEntryPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PseudostateExitPointEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TransitionEditPart.VISUAL_ID:
				if (TransitionNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TransitionGuardEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TransitionStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case GeneralizationEditPart.VISUAL_ID:
				if (GeneralizationStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ContextLinkEditPart.VISUAL_ID:
				if (ContextLinkAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static String getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		if (UMLPackage.eINSTANCE.getTransition().isSuperTypeOf(domainElement.eClass())) {
			return TransitionEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getGeneralization().isSuperTypeOf(domainElement.eClass())) {
			return GeneralizationEditPart.VISUAL_ID;
		}
		return ""; //$NON-NLS-1$
	}

	// Uncomment for debug purpose ?
	// /**
	// * User can change implementation of this method to handle some specific
	// * situations not covered by default logic.
	// *
	// * @generated
	// */
	// private static boolean isDiagram(Package element) {
	// return true;
	// }

	/**
	 * @generated
	 */
	private static boolean isPseudostate_InitialShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is an initial
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.INITIAL_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_JoinShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a join
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.JOIN_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_ForkShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a fork
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.FORK_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_ChoiceShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a choice
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.CHOICE_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_JunctionShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a junction
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.JUNCTION_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_ShallowHistoryShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a shallowHistory
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.SHALLOW_HISTORY_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_DeepHistoryShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a deepHistory
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.DEEP_HISTORY_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_TerminateShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is a terminate
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.TERMINATE_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_EntryPointShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is an entryPoint
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.ENTRY_POINT_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isPseudostate_ExitPointShape(View containerView, Pseudostate domainElement) {
		// Test if the pseudostate is an exitPoint
		if (domainElement instanceof Pseudostate) {
			PseudostateKind kind = domainElement.getKind();
			if (kind.equals(PseudostateKind.EXIT_POINT_LITERAL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static boolean isBehavior_EntryBehaviorLabel(Behavior domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getBehavior(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isBehavior_DoActivityBehaviorLabel(Behavior domainElement) {
		Object result = UMLOCLFactory.getExpression(3, UMLPackage.eINSTANCE.getBehavior(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isBehavior_ExitBehaviorLabel(Behavior domainElement) {
		Object result = UMLOCLFactory.getExpression(1, UMLPackage.eINSTANCE.getBehavior(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	public static boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
		if (candidate == null) {
			// unrecognized id is always bad
			return false;
		}
		String basic = getNodeVisualID(containerView, domainElement);
		return candidate.equals(basic);
	}

	/**
	 * @generated
	 */
	public static boolean isCompartmentVisualID(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case RegionCompartmentEditPart.VISUAL_ID:
			case StateMachineCompartmentEditPart.VISUAL_ID:
			case StateCompartmentEditPart.VISUAL_ID:
			case StateCompartmentEditPartTN.VISUAL_ID:
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case PackageEditPart.VISUAL_ID:
				return false;
			case CommentEditPart.VISUAL_ID:
			case ConstraintEditPart.VISUAL_ID:
			case InternalTransitionEditPart.VISUAL_ID:
			case EntryStateBehaviorEditPart.VISUAL_ID:
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
			case ExitStateBehaviorEditPart.VISUAL_ID:
			case DeferrableTriggerEditPart.VISUAL_ID:
			case FinalStateEditPart.VISUAL_ID:
			case PseudostateInitialEditPart.VISUAL_ID:
			case PseudostateJoinEditPart.VISUAL_ID:
			case PseudostateForkEditPart.VISUAL_ID:
			case PseudostateChoiceEditPart.VISUAL_ID:
			case PseudostateJunctionEditPart.VISUAL_ID:
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
			case PseudostateTerminateEditPart.VISUAL_ID:
			case PseudostateEntryPointEditPart.VISUAL_ID:
			case PseudostateExitPointEditPart.VISUAL_ID:
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		 * @generated
		 */
		@Override
		public String getVisualID(View view) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};
}
