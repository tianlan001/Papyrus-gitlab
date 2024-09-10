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
package org.eclipse.papyrus.uml.diagram.communication.part;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.AppliedStereotypeMessageEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ConstraintBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ConstraintNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.DurationObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.DurationObservationLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.DurationObservationStereotypeLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.InteractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.TimeObservationStereotypeLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.communication.expressions.UMLOCLFactory;
import org.eclipse.uml2.uml.Message;
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
	// private static final String DEBUG_KEY = "org.eclipse.papyrus.uml.diagram.communication/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (ModelEditPart.MODEL_ID.equals(view.getType())) {
				return ModelEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getVisualID(view.getType());
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
		return ModelEditPart.VISUAL_ID;
	}

	/**
	 * @generated
	 */
	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		String containerModelID = org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!ModelEditPart.MODEL_ID.equals(containerModelID)) {
			return ""; //$NON-NLS-1$
		}
		String containerVisualID;
		if (ModelEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ModelEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case ModelEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getInteraction().isSuperTypeOf(domainElement.eClass())) {
					return InteractionEditPart.VISUAL_ID;
				}
				break;
			case InteractionCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getLifeline().isSuperTypeOf(domainElement.eClass())) {
					return LifelineEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTimeObservation().isSuperTypeOf(domainElement.eClass())) {
					return TimeObservationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDurationObservation().isSuperTypeOf(domainElement.eClass())) {
					return DurationObservationEditPartCN.VISUAL_ID;
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
		String containerModelID = org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!ModelEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		String containerVisualID;
		if (ModelEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ModelEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case ModelEditPart.VISUAL_ID:
				if (InteractionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case InteractionEditPart.VISUAL_ID:
				if (InteractionNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (InteractionFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (InteractionCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case LifelineEditPartCN.VISUAL_ID:
				if (LifelineNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (LifelineFloatingLabelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ConstraintEditPartCN.VISUAL_ID:
				if (ConstraintNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintBodyEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPartCN.VISUAL_ID:
				if (CommentBodyEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TimeObservationEditPartCN.VISUAL_ID:
				if (TimeObservationNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeObservationStereotypeLabelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DurationObservationEditPartCN.VISUAL_ID:
				if (DurationObservationLabelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationObservationStereotypeLabelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case InteractionCompartmentEditPart.VISUAL_ID:
				if (LifelineEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeObservationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationObservationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageEditPart.VISUAL_ID:
				if (MessageNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AppliedStereotypeMessageEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isPath_Edge((Message) domainElement)) {
			return MessageEditPart.VISUAL_ID;
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
	private static boolean isPath_Edge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(5, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
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
			case InteractionCompartmentEditPart.VISUAL_ID:
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
			case ModelEditPart.VISUAL_ID:
				return false;
			case LifelineEditPartCN.VISUAL_ID:
			case ConstraintEditPartCN.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
			case TimeObservationEditPartCN.VISUAL_ID:
			case DurationObservationEditPartCN.VISUAL_ID:
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
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};
}
