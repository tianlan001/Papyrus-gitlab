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
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DeferrableTriggerEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Package_StateMachineDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_StateMachineDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StateMachine_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StateMachine_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType State_Shape_TN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.State_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Region_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Region_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType FinalState_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.FinalState_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType State_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.State_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_InitialShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_InitialShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_JoinShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_JoinShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_ForkShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_ForkShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_ChoiceShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_ChoiceShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_JunctionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_JunctionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_ShallowHistoryShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_ShallowHistoryShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_DeepHistoryShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_DeepHistoryShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_TerminateShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_TerminateShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_EntryPointShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_EntryPointShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pseudostate_ExitPointShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Pseudostate_ExitPointShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ConnectionPointReference_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ConnectionPointReference_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Transition_InternalTransitionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Transition_InternalTransitionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Behavior_EntryBehaviorLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Behavior_EntryBehaviorLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Behavior_DoActivityBehaviorLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Behavior_DoActivityBehaviorLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Behavior_ExitBehaviorLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Behavior_ExitBehaviorLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Trigger_DeferrableTriggerLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Trigger_DeferrableTriggerLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Transition_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Transition_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
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
	public static final IElementType Constraint_ContextEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ContextEdge"); //$NON-NLS-1$

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
			elements.put(Package_StateMachineDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(StateMachine_Shape, UMLPackage.eINSTANCE.getStateMachine());
			elements.put(State_Shape_TN, UMLPackage.eINSTANCE.getState());
			elements.put(Region_Shape, UMLPackage.eINSTANCE.getRegion());
			elements.put(FinalState_Shape, UMLPackage.eINSTANCE.getFinalState());
			elements.put(State_Shape, UMLPackage.eINSTANCE.getState());
			elements.put(Pseudostate_InitialShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_JoinShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_ForkShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_ChoiceShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_JunctionShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_ShallowHistoryShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_DeepHistoryShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_TerminateShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_EntryPointShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(Pseudostate_ExitPointShape, UMLPackage.eINSTANCE.getPseudostate());
			elements.put(ConnectionPointReference_Shape, UMLPackage.eINSTANCE.getConnectionPointReference());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Transition_InternalTransitionLabel, UMLPackage.eINSTANCE.getTransition());
			elements.put(Behavior_EntryBehaviorLabel, UMLPackage.eINSTANCE.getBehavior());
			elements.put(Behavior_DoActivityBehaviorLabel, UMLPackage.eINSTANCE.getBehavior());
			elements.put(Behavior_ExitBehaviorLabel, UMLPackage.eINSTANCE.getBehavior());
			elements.put(Trigger_DeferrableTriggerLabel, UMLPackage.eINSTANCE.getTrigger());
			elements.put(Transition_Edge, UMLPackage.eINSTANCE.getTransition());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(Constraint_ContextEdge, UMLPackage.eINSTANCE.getConstraint_Context());
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
			KNOWN_ELEMENT_TYPES.add(Package_StateMachineDiagram);
			KNOWN_ELEMENT_TYPES.add(StateMachine_Shape);
			KNOWN_ELEMENT_TYPES.add(State_Shape_TN);
			KNOWN_ELEMENT_TYPES.add(Region_Shape);
			KNOWN_ELEMENT_TYPES.add(FinalState_Shape);
			KNOWN_ELEMENT_TYPES.add(State_Shape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_InitialShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_JoinShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_ForkShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_ChoiceShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_JunctionShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_ShallowHistoryShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_DeepHistoryShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_TerminateShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_EntryPointShape);
			KNOWN_ELEMENT_TYPES.add(Pseudostate_ExitPointShape);
			KNOWN_ELEMENT_TYPES.add(ConnectionPointReference_Shape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(Transition_InternalTransitionLabel);
			KNOWN_ELEMENT_TYPES.add(Behavior_EntryBehaviorLabel);
			KNOWN_ELEMENT_TYPES.add(Behavior_DoActivityBehaviorLabel);
			KNOWN_ELEMENT_TYPES.add(Behavior_ExitBehaviorLabel);
			KNOWN_ELEMENT_TYPES.add(Trigger_DeferrableTriggerLabel);
			KNOWN_ELEMENT_TYPES.add(Transition_Edge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ContextEdge);
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
			case PackageEditPart.VISUAL_ID:
				return Package_StateMachineDiagram;
			case StateMachineEditPart.VISUAL_ID:
				return StateMachine_Shape;
			case StateEditPartTN.VISUAL_ID:
				return State_Shape_TN;
			case RegionEditPart.VISUAL_ID:
				return Region_Shape;
			case FinalStateEditPart.VISUAL_ID:
				return FinalState_Shape;
			case StateEditPart.VISUAL_ID:
				return State_Shape;
			case PseudostateInitialEditPart.VISUAL_ID:
				return Pseudostate_InitialShape;
			case PseudostateJoinEditPart.VISUAL_ID:
				return Pseudostate_JoinShape;
			case PseudostateForkEditPart.VISUAL_ID:
				return Pseudostate_ForkShape;
			case PseudostateChoiceEditPart.VISUAL_ID:
				return Pseudostate_ChoiceShape;
			case PseudostateJunctionEditPart.VISUAL_ID:
				return Pseudostate_JunctionShape;
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
				return Pseudostate_ShallowHistoryShape;
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
				return Pseudostate_DeepHistoryShape;
			case PseudostateTerminateEditPart.VISUAL_ID:
				return Pseudostate_TerminateShape;
			case PseudostateEntryPointEditPart.VISUAL_ID:
				return Pseudostate_EntryPointShape;
			case PseudostateExitPointEditPart.VISUAL_ID:
				return Pseudostate_ExitPointShape;
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return ConnectionPointReference_Shape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_Shape;
			case InternalTransitionEditPart.VISUAL_ID:
				return Transition_InternalTransitionLabel;
			case EntryStateBehaviorEditPart.VISUAL_ID:
				return Behavior_EntryBehaviorLabel;
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
				return Behavior_DoActivityBehaviorLabel;
			case ExitStateBehaviorEditPart.VISUAL_ID:
				return Behavior_ExitBehaviorLabel;
			case DeferrableTriggerEditPart.VISUAL_ID:
				return Trigger_DeferrableTriggerLabel;
			case TransitionEditPart.VISUAL_ID:
				return Transition_Edge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case ContextLinkEditPart.VISUAL_ID:
				return Constraint_ContextEdge;
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
			return org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
