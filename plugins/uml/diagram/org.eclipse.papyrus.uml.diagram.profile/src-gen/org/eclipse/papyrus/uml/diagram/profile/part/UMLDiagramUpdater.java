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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassPropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEnumerationLiteralCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationLiteralEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ExtensionEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelPackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelPackageableElementCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackagePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileApplicationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfilePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfilePackageableElementCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	public static final org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramUpdater INSTANCE = new UMLDiagramUpdater();

	/**
	 * @generated
	 */
	protected UMLDiagramUpdater() {
		// to prevent instantiation allowing the override
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLNodeDescriptor> getSemanticChildren(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case ProfileDiagramEditPart.VISUAL_ID:
				return getProfile_ProfileDiagram_SemanticChildren(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				return getPackage_PackagedElementCompartment_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getPackage_PackagedElementCompartment_CN_SemanticChildren(view);
			case ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID:
				return getProfile_PackagedElementCompartment_SemanticChildren(view);
			case ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getProfile_PackagedElementCompartment_CN_SemanticChildren(view);
			case StereotypeAttributeCompartmentEditPart.VISUAL_ID:
				return getStereotype_AttributeCompartment_SemanticChildren(view);
			case StereotypeAttributeCompartmentEditPartCN.VISUAL_ID:
				return getStereotype_AttributeCompartment_CN_SemanticChildren(view);
			case StereotypeOperationCompartmentEditPart.VISUAL_ID:
				return getStereotype_OperationCompartment_SemanticChildren(view);
			case StereotypeOperationCompartmentEditPartCN.VISUAL_ID:
				return getStereotype_OperationCompartment_CN_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPartTN.VISUAL_ID:
				return getModel_PackagedElementCompartment_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getModel_PackagedElementCompartment_CN_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
				return getEnumeration_LiteralCompartment_CN_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
				return getEnumeration_LiteralCompartment_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
				return getDataType_AttributeCompartment_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
				return getDataType_AttributeCompartment_CN_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPart.VISUAL_ID:
				return getDataType_OperationCompartment_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
				return getDataType_OperationCompartment_CN_SemanticChildren(view);
			case ClassAttributeCompartmentEditPart.VISUAL_ID:
				return getClass_AttributeCompartment_SemanticChildren(view);
			case ClassAttributeCompartmentEditPartCN.VISUAL_ID:
				return getClass_AttributeCompartment_CN_SemanticChildren(view);
			case ClassOperationCompartmentEditPart.VISUAL_ID:
				return getClass_OperationCompartment_SemanticChildren(view);
			case ClassOperationCompartmentEditPartCN.VISUAL_ID:
				return getClass_OperationCompartment_CN_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProfile_ProfileDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DependencyNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AssociationNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MetaclassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedStereotypes()
				.iterator(); it.hasNext();) {
			Stereotype childElement = (Stereotype) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (StereotypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		Resource resource = modelElement.eResource();
		for (Iterator<EObject> it = getPhantomNodesIterator(resource); it.hasNext();) {
			EObject childElement = it.next();
			if (childElement == modelElement) {
				continue;
			}
			if (UMLVisualIDRegistry.getNodeVisualID(view, childElement) == ShortCutDiagramEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, ShortCutDiagramEditPart.VISUAL_ID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_PackagedElementCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedStereotypes()
				.iterator(); it.hasNext();) {
			Stereotype childElement = (Stereotype) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (StereotypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedStereotypes()
				.iterator(); it.hasNext();) {
			Stereotype childElement = (Stereotype) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (StereotypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProfile_PackagedElementCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Profile modelElement = (Profile) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedStereotypes()
				.iterator(); it.hasNext();) {
			Stereotype childElement = (Stereotype) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (StereotypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MetaclassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProfile_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Profile modelElement = (Profile) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedStereotypes()
				.iterator(); it.hasNext();) {
			Stereotype childElement = (Stereotype) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (StereotypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MetaclassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStereotype_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Stereotype modelElement = (Stereotype) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassPropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStereotype_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Stereotype modelElement = (Stereotype) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassPropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStereotype_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Stereotype modelElement = (Stereotype) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStereotype_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Stereotype modelElement = (Stereotype) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_PackagedElementCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProfileEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DataTypePropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DataTypePropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DataTypeOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DataTypeOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassPropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassPropertyEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassOperationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Iterator<EObject> getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case ProfileDiagramEditPart.VISUAL_ID:
				return getProfile_ProfileDiagram_ContainedLinks(view);
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_ContainedLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_ContainedLinks(view);
			case StereotypeEditPart.VISUAL_ID:
				return getStereotype_Shape_ContainedLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_ContainedLinks(view);
			case MetaclassEditPart.VISUAL_ID:
				return getClass_MetaclassShape_ContainedLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_ContainedLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_ContainedLinks(view);
			case ProfileEditPartTN.VISUAL_ID:
				return getProfile_Shape_ContainedLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_ContainedLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_ContainedLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_ContainedLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_ContainedLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_ContainedLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_ContainedLinks(view);
			case DataTypeOperationEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_ContainedLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_ContainedLinks(view);
			case DataTypePropertyEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_ContainedLinks(view);
			case ClassPropertyEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_ContainedLinks(view);
			case ClassOperationEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_ContainedLinks(view);
			case StereotypeEditPartCN.VISUAL_ID:
				return getStereotype_Shape_CN_ContainedLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_ContainedLinks(view);
			case MetaclassEditPartCN.VISUAL_ID:
				return getClass_MetaclassShape_CN_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_ContainedLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_ContainedLinks(view);
			case ProfileEditPartCN.VISUAL_ID:
				return getProfile_Shape_CN_ContainedLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_ContainedLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_ContainedLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_ContainedLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_ContainedLinks(view);
			case ExtensionEditPart.VISUAL_ID:
				return getExtension_Edge_ContainedLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_ContainedLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_ContainedLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_ContainedLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_ContainedLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_ContainedLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_ContainedLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_ContainedLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_ContainedLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getIncomingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_IncomingLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_IncomingLinks(view);
			case StereotypeEditPart.VISUAL_ID:
				return getStereotype_Shape_IncomingLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_IncomingLinks(view);
			case MetaclassEditPart.VISUAL_ID:
				return getClass_MetaclassShape_IncomingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_IncomingLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_IncomingLinks(view);
			case ProfileEditPartTN.VISUAL_ID:
				return getProfile_Shape_IncomingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_IncomingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_IncomingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_IncomingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_IncomingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_IncomingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_IncomingLinks(view);
			case DataTypeOperationEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_IncomingLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_IncomingLinks(view);
			case DataTypePropertyEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_IncomingLinks(view);
			case ClassPropertyEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_IncomingLinks(view);
			case ClassOperationEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_IncomingLinks(view);
			case StereotypeEditPartCN.VISUAL_ID:
				return getStereotype_Shape_CN_IncomingLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_IncomingLinks(view);
			case MetaclassEditPartCN.VISUAL_ID:
				return getClass_MetaclassShape_CN_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_IncomingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_IncomingLinks(view);
			case ProfileEditPartCN.VISUAL_ID:
				return getProfile_Shape_CN_IncomingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_IncomingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_IncomingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_IncomingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_IncomingLinks(view);
			case ExtensionEditPart.VISUAL_ID:
				return getExtension_Edge_IncomingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_IncomingLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_IncomingLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_IncomingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_IncomingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_IncomingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_IncomingLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_IncomingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_IncomingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getOutgoingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_OutgoingLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_OutgoingLinks(view);
			case StereotypeEditPart.VISUAL_ID:
				return getStereotype_Shape_OutgoingLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_OutgoingLinks(view);
			case MetaclassEditPart.VISUAL_ID:
				return getClass_MetaclassShape_OutgoingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_OutgoingLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_OutgoingLinks(view);
			case ProfileEditPartTN.VISUAL_ID:
				return getProfile_Shape_OutgoingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_OutgoingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_OutgoingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_OutgoingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_OutgoingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_OutgoingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_OutgoingLinks(view);
			case DataTypeOperationEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_OutgoingLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_OutgoingLinks(view);
			case DataTypePropertyEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_OutgoingLinks(view);
			case ClassPropertyEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_OutgoingLinks(view);
			case ClassOperationEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_OutgoingLinks(view);
			case StereotypeEditPartCN.VISUAL_ID:
				return getStereotype_Shape_CN_OutgoingLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_OutgoingLinks(view);
			case MetaclassEditPartCN.VISUAL_ID:
				return getClass_MetaclassShape_CN_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_OutgoingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_OutgoingLinks(view);
			case ProfileEditPartCN.VISUAL_ID:
				return getProfile_Shape_CN_OutgoingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_OutgoingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_OutgoingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_OutgoingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_OutgoingLinks(view);
			case ExtensionEditPart.VISUAL_ID:
				return getExtension_Edge_OutgoingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_OutgoingLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_OutgoingLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_OutgoingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_OutgoingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_OutgoingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_OutgoingLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_OutgoingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_ProfileDiagram_ContainedLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_ContainedLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_ContainedLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_CN_ContainedLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_CN_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_CN_ContainedLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Extension_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtension_Edge_ContainedLinks(View view) {
		Extension modelElement = (Extension) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_IncomingLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_IncomingLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ProfileApplication_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_CN_IncomingLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_CN_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Extension_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_CN_IncomingLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ProfileApplication_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtension_Edge_IncomingLinks(View view) {
		Extension modelElement = (Extension) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_IncomingLinks(View view) {
		ProfileApplication modelElement = (ProfileApplication) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_IncomingLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_IncomingLinks(View view) {
		ElementImport modelElement = (ElementImport) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_IncomingLinks(View view) {
		PackageImport modelElement = (PackageImport) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_OutgoingLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_OutgoingLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStereotype_Shape_CN_OutgoingLinks(View view) {
		Stereotype modelElement = (Stereotype) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_MetaclassShape_CN_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfile_Shape_CN_OutgoingLinks(View view) {
		Profile modelElement = (Profile) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtension_Edge_OutgoingLinks(View view) {
		Extension modelElement = (Extension) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Extension_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Extension) {
				continue;
			}
			Extension link = (Extension) linkObject;
			if (!ExtensionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Class dst = link.getMetaclass();
			List<?> sources = link.getOwnedEnds();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Property) {
				continue;
			}
			Property src = (Property) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Extension_Edge, ExtensionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Association_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ProfileApplication_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getProfileApplications()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ProfileApplication) {
				continue;
			}
			ProfileApplication link = (ProfileApplication) linkObject;
			if (!ProfileApplicationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Profile dst = link.getAppliedProfile();
			Package src = link.getApplyingPackage();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ProfileApplication_Edge, ProfileApplicationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Association_BranchEdge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Generalization_Edge(Classifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getGeneralizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) linkObject;
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getGeneral();
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_BranchEdge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ElementImport_Edge(Namespace container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getElementImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ElementImport) {
				continue;
			}
			ElementImport link = (ElementImport) linkObject;
			if (!ElementImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			PackageableElement dst = link.getImportedElement();
			Namespace src = link.getImportingNamespace();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ElementImport_Edge, ElementImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_PackageImport_Edge(Namespace container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackageImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof PackageImport) {
				continue;
			}
			PackageImport link = (PackageImport) linkObject;
			if (!PackageImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package dst = link.getImportedPackage();
			Namespace src = link.getImportingNamespace();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.PackageImport_Edge, PackageImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Extension_Edge(Class target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getExtension_Metaclass() || false == setting.getEObject() instanceof Extension) {
				continue;
			}
			Extension link = (Extension) setting.getEObject();
			if (!ExtensionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getOwnedEnds();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Property) {
				continue;
			}
			Property src = (Property) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Extension_Edge, ExtensionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Association_Edge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof Association) {
				continue;
			}
			Association link = (Association) setting.getEObject();
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ProfileApplication_Edge(Profile target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getProfileApplication_AppliedProfile() || false == setting.getEObject() instanceof ProfileApplication) {
				continue;
			}
			ProfileApplication link = (ProfileApplication) setting.getEObject();
			if (!ProfileApplicationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package src = link.getApplyingPackage();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ProfileApplication_Edge, ProfileApplicationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Association_BranchEdge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof Association) {
				continue;
			}
			Association link = (Association) setting.getEObject();
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Generalization_Edge(Classifier target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getGeneralization_General() || false == setting.getEObject() instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) setting.getEObject();
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			if (false == link.eContainer() instanceof Classifier) {
				continue;
			}
			Classifier container = (Classifier) link.eContainer();
			result.add(new UMLLinkDescriptor(container, target, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_BranchEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ElementImport_Edge(PackageableElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getElementImport_ImportedElement() || false == setting.getEObject() instanceof ElementImport) {
				continue;
			}
			ElementImport link = (ElementImport) setting.getEObject();
			if (!ElementImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Namespace src = link.getImportingNamespace();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ElementImport_Edge, ElementImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_PackageImport_Edge(Package target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getPackageImport_ImportedPackage() || false == setting.getEObject() instanceof PackageImport) {
				continue;
			}
			PackageImport link = (PackageImport) setting.getEObject();
			if (!PackageImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Namespace src = link.getImportingNamespace();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.PackageImport_Edge, PackageImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getComment_AnnotatedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getConstraint_ConstrainedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(Namespace target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getConstraint_Context()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Constraint_ContextEdge, ContextLinkEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Association_Edge(Type source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ProfileApplication_Edge(Package source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getProfileApplications()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ProfileApplication) {
				continue;
			}
			ProfileApplication link = (ProfileApplication) linkObject;
			if (!ProfileApplicationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Profile dst = link.getAppliedProfile();
			Package src = link.getApplyingPackage();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ProfileApplication_Edge, ProfileApplicationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Association_BranchEdge(Type source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ElementImport_Edge(Namespace source) {
		Namespace container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Namespace) {
				container = (Namespace) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getElementImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ElementImport) {
				continue;
			}
			ElementImport link = (ElementImport) linkObject;
			if (!ElementImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			PackageableElement dst = link.getImportedElement();
			Namespace src = link.getImportingNamespace();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ElementImport_Edge, ElementImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_PackageImport_Edge(Namespace source) {
		Namespace container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Namespace) {
				container = (Namespace) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackageImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof PackageImport) {
				continue;
			}
			PackageImport link = (PackageImport) linkObject;
			if (!PackageImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package dst = link.getImportedPackage();
			Namespace src = link.getImportingNamespace();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.PackageImport_Edge, PackageImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Comment source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getAnnotatedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getConstrainedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Namespace destination = source.getContext();
		if (destination == null) {
			return result;
		}
		result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ContextEdge, ContextLinkEditPart.VISUAL_ID));
		return result;
	}
}
