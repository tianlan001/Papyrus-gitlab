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
package org.eclipse.papyrus.uml.diagram.timing.providers;

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
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FreeTimingRulerEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantVerticalLineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GeneralOrderingEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LinearTimingRulerEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateDefinitionEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TickEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Package_TimingDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_TimingDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interaction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interaction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Lifeline_FullShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Lifeline_FullShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Lifeline_CompactShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Lifeline_CompactShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StateInvariant_FullShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StateInvariant_FullShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StateInvariant_CompactShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StateInvariant_CompactShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OccurrenceSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OccurrenceSpecification_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType MessageOccurrenceSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.MessageOccurrenceSpecification_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_StateDefinitionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_StateDefinitionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_StateInvariantTransitionShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_StateInvariantTransitionShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType GeneralOrdering_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.GeneralOrdering_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_FreeTimeRulerShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_FreeTimeRulerShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_LinearTimeRulerShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_LinearTimeRulerShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_TickShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_TickShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DestructionOccurrenceSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DestructionOccurrenceSpecification_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Lifeline_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Lifeline_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Gate_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Gate_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_SynchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_SynchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_AsynchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_AsynchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_ReplyEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_ReplyEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_CreateEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_CreateEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_DeleteEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_DeleteEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_LostEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_LostEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Message_FoundEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Message_FoundEdge"); //$NON-NLS-1$

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
			elements.put(Package_TimingDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Interaction_Shape, UMLPackage.eINSTANCE.getInteraction());
			elements.put(Lifeline_FullShape, UMLPackage.eINSTANCE.getLifeline());
			elements.put(Lifeline_CompactShape, UMLPackage.eINSTANCE.getLifeline());
			elements.put(StateInvariant_FullShape, UMLPackage.eINSTANCE.getStateInvariant());
			elements.put(StateInvariant_CompactShape, UMLPackage.eINSTANCE.getStateInvariant());
			elements.put(OccurrenceSpecification_Shape, UMLPackage.eINSTANCE.getOccurrenceSpecification());
			elements.put(MessageOccurrenceSpecification_Shape, UMLPackage.eINSTANCE.getMessageOccurrenceSpecification());
			elements.put(TimeConstraint_Shape, UMLPackage.eINSTANCE.getTimeConstraint());
			elements.put(TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation());
			elements.put(DurationConstraint_Shape, UMLPackage.eINSTANCE.getDurationConstraint());
			elements.put(DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation());
			elements.put(GeneralOrdering_Shape, UMLPackage.eINSTANCE.getGeneralOrdering());
			elements.put(DestructionOccurrenceSpecification_Shape, UMLPackage.eINSTANCE.getDestructionOccurrenceSpecification());
			elements.put(Lifeline_Shape, UMLPackage.eINSTANCE.getLifeline());
			elements.put(Gate_Shape, UMLPackage.eINSTANCE.getGate());
			elements.put(Message_SynchEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_AsynchEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_ReplyEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_CreateEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_DeleteEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_LostEdge, UMLPackage.eINSTANCE.getMessage());
			elements.put(Message_FoundEdge, UMLPackage.eINSTANCE.getMessage());
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
			KNOWN_ELEMENT_TYPES.add(Package_TimingDiagram);
			KNOWN_ELEMENT_TYPES.add(Interaction_Shape);
			KNOWN_ELEMENT_TYPES.add(Lifeline_FullShape);
			KNOWN_ELEMENT_TYPES.add(Lifeline_CompactShape);
			KNOWN_ELEMENT_TYPES.add(StateInvariant_FullShape);
			KNOWN_ELEMENT_TYPES.add(StateInvariant_CompactShape);
			KNOWN_ELEMENT_TYPES.add(OccurrenceSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(MessageOccurrenceSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(Node_StateDefinitionShape);
			KNOWN_ELEMENT_TYPES.add(Node_StateInvariantTransitionShape);
			KNOWN_ELEMENT_TYPES.add(TimeConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(DurationConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(DurationObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(GeneralOrdering_Shape);
			KNOWN_ELEMENT_TYPES.add(Node_FreeTimeRulerShape);
			KNOWN_ELEMENT_TYPES.add(Node_LinearTimeRulerShape);
			KNOWN_ELEMENT_TYPES.add(Node_TickShape);
			KNOWN_ELEMENT_TYPES.add(DestructionOccurrenceSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(Lifeline_Shape);
			KNOWN_ELEMENT_TYPES.add(Gate_Shape);
			KNOWN_ELEMENT_TYPES.add(Message_SynchEdge);
			KNOWN_ELEMENT_TYPES.add(Message_AsynchEdge);
			KNOWN_ELEMENT_TYPES.add(Message_ReplyEdge);
			KNOWN_ELEMENT_TYPES.add(Message_CreateEdge);
			KNOWN_ELEMENT_TYPES.add(Message_DeleteEdge);
			KNOWN_ELEMENT_TYPES.add(Message_LostEdge);
			KNOWN_ELEMENT_TYPES.add(Message_FoundEdge);
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
			case TimingDiagramEditPart.VISUAL_ID:
				return Package_TimingDiagram;
			case InteractionEditPartTN.VISUAL_ID:
				return Interaction_Shape;
			case FullLifelineEditPartCN.VISUAL_ID:
				return Lifeline_FullShape;
			case CompactLifelineEditPartCN.VISUAL_ID:
				return Lifeline_CompactShape;
			case FullStateInvariantEditPartCN.VISUAL_ID:
				return StateInvariant_FullShape;
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				return StateInvariant_CompactShape;
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				return OccurrenceSpecification_Shape;
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return MessageOccurrenceSpecification_Shape;
			case StateDefinitionEditPart.VISUAL_ID:
				return Node_StateDefinitionShape;
			case FullStateInvariantVerticalLineEditPart.VISUAL_ID:
				return Node_StateInvariantTransitionShape;
			case TimeConstraintEditPart.VISUAL_ID:
				return TimeConstraint_Shape;
			case TimeObservationEditPart.VISUAL_ID:
				return TimeObservation_Shape;
			case DurationConstraintEditPartCN.VISUAL_ID:
				return DurationConstraint_Shape;
			case DurationObservationEditPartCN.VISUAL_ID:
				return DurationObservation_Shape;
			case GeneralOrderingEditPart.VISUAL_ID:
				return GeneralOrdering_Shape;
			case FreeTimingRulerEditPartCN.VISUAL_ID:
				return Node_FreeTimeRulerShape;
			case LinearTimingRulerEditPartCN.VISUAL_ID:
				return Node_LinearTimeRulerShape;
			case TickEditPart.VISUAL_ID:
				return Node_TickShape;
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return DestructionOccurrenceSpecification_Shape;
			case LifelineEditPart.VISUAL_ID:
				return Lifeline_Shape;
			case GateEditPart.VISUAL_ID:
				return Gate_Shape;
			case MessageSyncEditPart.VISUAL_ID:
				return Message_SynchEdge;
			case MessageAsyncEditPart.VISUAL_ID:
				return Message_AsynchEdge;
			case MessageReplyEditPart.VISUAL_ID:
				return Message_ReplyEdge;
			case MessageCreateEditPart.VISUAL_ID:
				return Message_CreateEdge;
			case MessageDeleteEditPart.VISUAL_ID:
				return Message_DeleteEdge;
			case MessageLostEditPart.VISUAL_ID:
				return Message_LostEdge;
			case MessageFoundEditPart.VISUAL_ID:
				return Message_FoundEdge;
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
			return org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
