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
package org.eclipse.papyrus.uml.diagram.profile.part;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.uml.diagram.common.util.Util;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.*;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 *
 * @generated
 */
public class UMLVisualIDRegistry {

	/**
	 * @generated
	 */
	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (ProfileDiagramEditPart.MODEL_ID.equals(view.getType())) {
				return ProfileDiagramEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getVisualID(view.getType());
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
		return ProfileDiagramEditPart.VISUAL_ID;
	}

	/**
	 * @generated NOT
	 *            we manually test if it's a class or a metaclass
	 */
	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return null;
		}
		String containerModelID = org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry
				.getModelID(containerView);
		if (!ProfileDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return null;
		}
		String containerVisualID;
		if (ProfileDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProfileDiagramEditPart.VISUAL_ID;
			} else {
				return null;
			}
		}
		switch (containerVisualID) {
		case ProfileDiagramEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getDependency().isSuperTypeOf(domainElement.eClass())) {
				return DependencyNodeEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getAssociation().isSuperTypeOf(domainElement.eClass())) {
				return AssociationNodeEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getStereotype().isSuperTypeOf(domainElement.eClass())) {
				return StereotypeEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				if (domainElement instanceof Type) {
					if (Util.isMetaclass((Type) domainElement)) {
						return MetaclassEditPart.VISUAL_ID;
					}
				}
				return ClassEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartTN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartTN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPart.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPart.VISUAL_ID;
			}
			if (NotationPackage.eINSTANCE.getDiagram().isSuperTypeOf(domainElement.eClass())) {
				return ShortCutDiagramEditPart.VISUAL_ID;
			}
			break;
		case PackageEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			break;
		case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getStereotype().isSuperTypeOf(domainElement.eClass())) {
				return StereotypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getStereotype().isSuperTypeOf(domainElement.eClass())) {
				return StereotypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getStereotype().isSuperTypeOf(domainElement.eClass())) {
				return StereotypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				if (domainElement instanceof Type) {
					if (Util.isMetaclass((Type) domainElement)) {
						return MetaclassEditPartCN.VISUAL_ID;
					}
				}
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getStereotype().isSuperTypeOf(domainElement.eClass())) {
				return StereotypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				if (domainElement instanceof Type) {
					if (Util.isMetaclass((Type) domainElement)) {
						return MetaclassEditPartCN.VISUAL_ID;
					}
				}
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case StereotypeAttributeCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return ClassPropertyEditPart.VISUAL_ID;
			}
			break;
		case StereotypeAttributeCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return ClassPropertyEditPart.VISUAL_ID;
			}
			break;
		case StereotypeOperationCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return ClassOperationEditPart.VISUAL_ID;
			}
			break;
		case StereotypeOperationCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return ClassOperationEditPart.VISUAL_ID;
			}
			break;
		case ModelPackageableElementCompartmentEditPartTN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
				return CommentEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
				return ModelEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getProfile().isSuperTypeOf(domainElement.eClass())) {
				return ProfileEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
				return PackageEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
				return ConstraintEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getEnumeration().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getPrimitiveType().isSuperTypeOf(domainElement.eClass())) {
				return PrimitiveTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getDataType().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeEditPartCN.VISUAL_ID;
			}
			if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(domainElement.eClass())) {
				return ClassEditPartCN.VISUAL_ID;
			}
			break;
		case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getEnumerationLiteral().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationLiteralEditPart.VISUAL_ID;
			}
			break;
		case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getEnumerationLiteral().isSuperTypeOf(domainElement.eClass())) {
				return EnumerationLiteralEditPart.VISUAL_ID;
			}
			break;
		case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return DataTypePropertyEditPart.VISUAL_ID;
			}
			break;
		case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return DataTypePropertyEditPart.VISUAL_ID;
			}
			break;
		case DataTypeOperationCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeOperationEditPart.VISUAL_ID;
			}
			break;
		case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return DataTypeOperationEditPart.VISUAL_ID;
			}
			break;
		case ClassAttributeCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return ClassPropertyEditPart.VISUAL_ID;
			}
			break;
		case ClassAttributeCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getProperty().isSuperTypeOf(domainElement.eClass())) {
				return ClassPropertyEditPart.VISUAL_ID;
			}
			break;
		case ClassOperationCompartmentEditPart.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return ClassOperationEditPart.VISUAL_ID;
			}
			break;
		case ClassOperationCompartmentEditPartCN.VISUAL_ID:
			if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(domainElement.eClass())) {
				return ClassOperationEditPart.VISUAL_ID;
			}
			break;
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, String nodeVisualID) {
		String containerModelID = org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!ProfileDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		String containerVisualID;
		if (ProfileDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProfileDiagramEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case ProfileDiagramEditPart.VISUAL_ID:
				if (DependencyNodeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationNodeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MetaclassEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ShortCutDiagramEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DependencyNodeEditPart.VISUAL_ID:
				if (MultiDependencyLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeEditPart.VISUAL_ID:
				if (StereotypeNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeAttributeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeOperationCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassEditPart.VISUAL_ID:
				if (ClassNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassOperationCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassAttributeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MetaclassEditPart.VISUAL_ID:
				if (MetaclassNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPart.VISUAL_ID:
				if (CommentBodyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ConstraintEditPart.VISUAL_ID:
				if (ConstraintNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintBodyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelEditPartTN.VISUAL_ID:
				if (ModelNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelPackageableElementCompartmentEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ProfileEditPartTN.VISUAL_ID:
				if (ProfileNameEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackageEditPart.VISUAL_ID:
				if (PackageNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackagePackageableElementCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case EnumerationEditPart.VISUAL_ID:
				if (EnumerationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PrimitiveTypeEditPart.VISUAL_ID:
				if (PrimitiveTypeNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeEditPart.VISUAL_ID:
				if (DataTypeNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeAttributeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeOperationCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ShortCutDiagramEditPart.VISUAL_ID:
				if (DiagramNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				if (PrimitiveTypeNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeEditPartCN.VISUAL_ID:
				if (StereotypeNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeAttributeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeOperationCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassEditPartCN.VISUAL_ID:
				if (ClassNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassAttributeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassOperationCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MetaclassEditPartCN.VISUAL_ID:
				if (MetaclassNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPartCN.VISUAL_ID:
				if (CommentBodyEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelEditPartCN.VISUAL_ID:
				if (ModelNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelPackageableElementCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ProfileEditPartCN.VISUAL_ID:
				if (ProfileNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackageEditPartCN.VISUAL_ID:
				if (PackageNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackagePackageableElementCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
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
			case EnumerationEditPartCN.VISUAL_ID:
				if (EnumerationNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeEditPartCN.VISUAL_ID:
				if (DataTypeNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeAttributeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeOperationCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StereotypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID:
				if (StereotypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MetaclassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (StereotypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MetaclassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeAttributeCompartmentEditPart.VISUAL_ID:
				if (ClassPropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeAttributeCompartmentEditPartCN.VISUAL_ID:
				if (ClassPropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeOperationCompartmentEditPart.VISUAL_ID:
				if (ClassOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StereotypeOperationCompartmentEditPartCN.VISUAL_ID:
				if (ClassOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelPackageableElementCompartmentEditPartTN.VISUAL_ID:
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ProfileEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (EnumerationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DataTypeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ClassEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
				if (EnumerationLiteralEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
				if (EnumerationLiteralEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
				if (DataTypePropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
				if (DataTypePropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeOperationCompartmentEditPart.VISUAL_ID:
				if (DataTypeOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
				if (DataTypeOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassAttributeCompartmentEditPart.VISUAL_ID:
				if (ClassPropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassAttributeCompartmentEditPartCN.VISUAL_ID:
				if (ClassPropertyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassOperationCompartmentEditPart.VISUAL_ID:
				if (ClassOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ClassOperationCompartmentEditPartCN.VISUAL_ID:
				if (ClassOperationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case AssociationEditPart.VISUAL_ID:
				if (AssociationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationRoleTargetEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationRoleSourceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationMultiplicitySourceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationMultiplicityTargetEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case AssociationBranchEditPart.VISUAL_ID:
				if (AssociationBranchRoleEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AssociationBranchMultiplicityEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case GeneralizationEditPart.VISUAL_ID:
				if (GeneralizationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DependencyEditPart.VISUAL_ID:
				if (DependencyNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DependencyAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ElementImportEditPart.VISUAL_ID:
				if (ElementImportAliasEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (AppliedStereotypeElementImportEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackageImportEditPart.VISUAL_ID:
				if (PackageImportAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
		if (UMLPackage.eINSTANCE.getExtension().isSuperTypeOf(domainElement.eClass())) {
			return ExtensionEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getAssociation().isSuperTypeOf(domainElement.eClass())) {
			return AssociationEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getProfileApplication().isSuperTypeOf(domainElement.eClass())) {
			return ProfileApplicationEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getAssociation().isSuperTypeOf(domainElement.eClass())) {
			return AssociationBranchEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getGeneralization().isSuperTypeOf(domainElement.eClass())) {
			return GeneralizationEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getDependency().isSuperTypeOf(domainElement.eClass())) {
			return DependencyEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getDependency().isSuperTypeOf(domainElement.eClass())) {
			return DependencyBranchEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getElementImport().isSuperTypeOf(domainElement.eClass())) {
			return ElementImportEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getPackageImport().isSuperTypeOf(domainElement.eClass())) {
			return PackageImportEditPart.VISUAL_ID;
		}
		return ""; //$NON-NLS-1$
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
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
			case ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID:
			case ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID:
			case StereotypeAttributeCompartmentEditPart.VISUAL_ID:
			case StereotypeAttributeCompartmentEditPartCN.VISUAL_ID:
			case StereotypeOperationCompartmentEditPart.VISUAL_ID:
			case StereotypeOperationCompartmentEditPartCN.VISUAL_ID:
			case ModelPackageableElementCompartmentEditPartTN.VISUAL_ID:
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
			case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
			case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
			case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
			case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
			case DataTypeOperationCompartmentEditPart.VISUAL_ID:
			case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
			case ClassAttributeCompartmentEditPart.VISUAL_ID:
			case ClassAttributeCompartmentEditPartCN.VISUAL_ID:
			case ClassOperationCompartmentEditPart.VISUAL_ID:
			case ClassOperationCompartmentEditPartCN.VISUAL_ID:
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
			case ProfileDiagramEditPart.VISUAL_ID:
				return false;
			case CommentEditPart.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
			case ConstraintEditPart.VISUAL_ID:
			case ConstraintEditPartCN.VISUAL_ID:
			case MetaclassEditPart.VISUAL_ID:
			case EnumerationLiteralEditPart.VISUAL_ID:
			case PrimitiveTypeEditPart.VISUAL_ID:
			case DependencyNodeEditPart.VISUAL_ID:
			case AssociationNodeEditPart.VISUAL_ID:
			case ShortCutDiagramEditPart.VISUAL_ID:
			case ClassPropertyEditPart.VISUAL_ID:
			case DataTypePropertyEditPart.VISUAL_ID:
			case DataTypeOperationEditPart.VISUAL_ID:
			case ClassOperationEditPart.VISUAL_ID:
			case PrimitiveTypeEditPartCN.VISUAL_ID:
			case MetaclassEditPartCN.VISUAL_ID:
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
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};
}
