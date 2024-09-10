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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
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
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPartCN;
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
import org.eclipse.uml2.uml.Profile;

/**
 * @generated
 */
public class UMLDiagramContentInitializer {

	/**
	 * @generated
	 */
	private Map myDomain2NotationMap = new HashMap();

	/**
	 * @generated
	 */
	private Collection myLinkDescriptors = new LinkedList();

	/**
	 * @generated
	 */
	public void initDiagramContent(Diagram diagram) {
		if (!ProfileDiagramEditPart.MODEL_ID.equals(diagram.getType())) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram passed as a parameter: " + diagram.getType());
			return;
		}
		if (false == diagram.getElement() instanceof Profile) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram element specified: " + diagram.getElement() + " instead of Profile");
			return;
		}
		createProfile_ProfileDiagram_Children(diagram);
		createLinks(diagram);
	}

	/**
	 * @generated
	 */
	private void createProfile_ProfileDiagram_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProfile_ProfileDiagram_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDependency_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDependency_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createAssociation_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getAssociation_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createStereotype_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStereotype_Shape_OutgoingLinks(view));
		createStereotype_AttributeCompartment_Children(getCompartment(view, StereotypeAttributeCompartmentEditPart.VISUAL_ID));
		createStereotype_OperationCompartment_Children(getCompartment(view, StereotypeOperationCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_Shape_OutgoingLinks(view));
		createClass_OperationCompartment_Children(getCompartment(view, ClassOperationCompartmentEditPart.VISUAL_ID));
		createClass_AttributeCompartment_Children(getCompartment(view, ClassAttributeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_MetaclassShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_MetaclassShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createComment_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComment_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createConstraint_PackagedElementShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getConstraint_PackagedElementShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createModel_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getModel_Shape_OutgoingLinks(view));
		createModel_PackagedElementCompartment_Children(getCompartment(view, ModelPackageableElementCompartmentEditPartTN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createProfile_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProfile_Shape_OutgoingLinks(view));
		createProfile_PackagedElementCompartment_Children(getCompartment(view, ProfilePackageableElementCompartmentEditPartTN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createPackage_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPackage_Shape_OutgoingLinks(view));
		createPackage_PackagedElementCompartment_Children(getCompartment(view, PackagePackageableElementCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createEnumeration_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_OutgoingLinks(view));
		createEnumeration_LiteralCompartment_Children(getCompartment(view, EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createPrimitiveType_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPrimitiveType_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDataType_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDataType_Shape_OutgoingLinks(view));
		createDataType_AttributeCompartment_Children(getCompartment(view, DataTypeAttributeCompartmentEditPart.VISUAL_ID));
		createDataType_OperationCompartment_Children(getCompartment(view, DataTypeOperationCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDiagram_ShortcutShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDiagram_ShortcutShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createPrimitiveType_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPrimitiveType_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createOperation_DataTypeOperationLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOperation_DataTypeOperationLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createEnumerationLiteral_LiteralLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumerationLiteral_LiteralLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createProperty_DataTypeAttributeLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProperty_DataTypeAttributeLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createProperty_ClassAttributeLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProperty_ClassAttributeLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createOperation_ClassOperationLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOperation_ClassOperationLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createStereotype_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStereotype_Shape_CN_OutgoingLinks(view));
		createStereotype_AttributeCompartment_CN_Children(getCompartment(view, StereotypeAttributeCompartmentEditPartCN.VISUAL_ID));
		createStereotype_OperationCompartment_CN_Children(getCompartment(view, StereotypeOperationCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_Shape_CN_OutgoingLinks(view));
		createClass_AttributeCompartment_CN_Children(getCompartment(view, ClassAttributeCompartmentEditPartCN.VISUAL_ID));
		createClass_OperationCompartment_CN_Children(getCompartment(view, ClassOperationCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_MetaclassShape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_MetaclassShape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createComment_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComment_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createModel_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getModel_Shape_CN_OutgoingLinks(view));
		createModel_PackagedElementCompartment_CN_Children(getCompartment(view, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createProfile_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProfile_Shape_CN_OutgoingLinks(view));
		createProfile_PackagedElementCompartment_CN_Children(getCompartment(view, ProfilePackageableElementCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createPackage_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPackage_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getPackage_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createPackage_PackagedElementCompartment_CN_Children(getCompartment(view, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createConstraint_PackagedElementShape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getConstraint_PackagedElementShape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createEnumeration_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_CN_OutgoingLinks(view));
		createEnumeration_LiteralCompartment_CN_Children(getCompartment(view, EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDataType_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDataType_Shape_CN_OutgoingLinks(view));
		createDataType_AttributeCompartment_CN_Children(getCompartment(view, DataTypeAttributeCompartmentEditPartCN.VISUAL_ID));
		createDataType_OperationCompartment_CN_Children(getCompartment(view, DataTypeOperationCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createPackage_PackagedElementCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getPackage_PackagedElementCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createPackage_PackagedElementCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getPackage_PackagedElementCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createProfile_PackagedElementCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProfile_PackagedElementCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createProfile_PackagedElementCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProfile_PackagedElementCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStereotype_AttributeCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStereotype_AttributeCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStereotype_AttributeCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStereotype_AttributeCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStereotype_OperationCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStereotype_OperationCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStereotype_OperationCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStereotype_OperationCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createModel_PackagedElementCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getModel_PackagedElementCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createModel_PackagedElementCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getModel_PackagedElementCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnumeration_LiteralCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_LiteralCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnumeration_LiteralCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_LiteralCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_AttributeCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_AttributeCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_AttributeCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_AttributeCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_OperationCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_OperationCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_OperationCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_OperationCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_AttributeCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_AttributeCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_AttributeCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_AttributeCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_OperationCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_OperationCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_OperationCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_OperationCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createNode(View parentView, UMLNodeDescriptor nodeDescriptor) {
		final String nodeType = UMLVisualIDRegistry.getType(nodeDescriptor.getVisualID());
		Node node = ViewService.createNode(parentView, nodeDescriptor.getModelElement(), nodeType, UMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		switch (nodeDescriptor.getVisualID()) {
		case DependencyNodeEditPart.VISUAL_ID:
			createDependency_Shape_Children(node);
			return;
		case AssociationNodeEditPart.VISUAL_ID:
			createAssociation_Shape_Children(node);
			return;
		case StereotypeEditPart.VISUAL_ID:
			createStereotype_Shape_Children(node);
			return;
		case ClassEditPart.VISUAL_ID:
			createClass_Shape_Children(node);
			return;
		case MetaclassEditPart.VISUAL_ID:
			createClass_MetaclassShape_Children(node);
			return;
		case CommentEditPart.VISUAL_ID:
			createComment_Shape_Children(node);
			return;
		case ConstraintEditPart.VISUAL_ID:
			createConstraint_PackagedElementShape_Children(node);
			return;
		case ModelEditPartTN.VISUAL_ID:
			createModel_Shape_Children(node);
			return;
		case ProfileEditPartTN.VISUAL_ID:
			createProfile_Shape_Children(node);
			return;
		case PackageEditPart.VISUAL_ID:
			createPackage_Shape_Children(node);
			return;
		case EnumerationEditPart.VISUAL_ID:
			createEnumeration_Shape_Children(node);
			return;
		case PrimitiveTypeEditPart.VISUAL_ID:
			createPrimitiveType_Shape_Children(node);
			return;
		case DataTypeEditPart.VISUAL_ID:
			createDataType_Shape_Children(node);
			return;
		case ShortCutDiagramEditPart.VISUAL_ID:
			createDiagram_ShortcutShape_Children(node);
			return;
		case PrimitiveTypeEditPartCN.VISUAL_ID:
			createPrimitiveType_Shape_CN_Children(node);
			return;
		case DataTypeOperationEditPart.VISUAL_ID:
			createOperation_DataTypeOperationLabel_Children(node);
			return;
		case EnumerationLiteralEditPart.VISUAL_ID:
			createEnumerationLiteral_LiteralLabel_Children(node);
			return;
		case DataTypePropertyEditPart.VISUAL_ID:
			createProperty_DataTypeAttributeLabel_Children(node);
			return;
		case ClassPropertyEditPart.VISUAL_ID:
			createProperty_ClassAttributeLabel_Children(node);
			return;
		case ClassOperationEditPart.VISUAL_ID:
			createOperation_ClassOperationLabel_Children(node);
			return;
		case StereotypeEditPartCN.VISUAL_ID:
			createStereotype_Shape_CN_Children(node);
			return;
		case ClassEditPartCN.VISUAL_ID:
			createClass_Shape_CN_Children(node);
			return;
		case MetaclassEditPartCN.VISUAL_ID:
			createClass_MetaclassShape_CN_Children(node);
			return;
		case CommentEditPartCN.VISUAL_ID:
			createComment_Shape_CN_Children(node);
			return;
		case ModelEditPartCN.VISUAL_ID:
			createModel_Shape_CN_Children(node);
			return;
		case ProfileEditPartCN.VISUAL_ID:
			createProfile_Shape_CN_Children(node);
			return;
		case PackageEditPartCN.VISUAL_ID:
			createPackage_Shape_CN_Children(node);
			return;
		case ConstraintEditPartCN.VISUAL_ID:
			createConstraint_PackagedElementShape_CN_Children(node);
			return;
		case EnumerationEditPartCN.VISUAL_ID:
			createEnumeration_Shape_CN_Children(node);
			return;
		case DataTypeEditPartCN.VISUAL_ID:
			createDataType_Shape_CN_Children(node);
			return;
		}
	}

	/**
	 * @generated
	 */
	private void createLinks(Diagram diagram) {
		for (boolean continueLinkCreation = true; continueLinkCreation;) {
			continueLinkCreation = false;
			Collection additionalDescriptors = new LinkedList();
			for (Iterator it = myLinkDescriptors.iterator(); it.hasNext();) {
				UMLLinkDescriptor nextLinkDescriptor = (UMLLinkDescriptor) it.next();
				if (!myDomain2NotationMap.containsKey(nextLinkDescriptor.getSource()) || !myDomain2NotationMap.containsKey(nextLinkDescriptor.getDestination())) {
					continue;
				}
				final String linkType = UMLVisualIDRegistry.getType(nextLinkDescriptor.getVisualID());
				Edge edge = ViewService.getInstance().createEdge(nextLinkDescriptor.getSemanticAdapter(), diagram, linkType, ViewUtil.APPEND, true, UMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (edge != null) {
					edge.setSource((View) myDomain2NotationMap.get(nextLinkDescriptor.getSource()));
					edge.setTarget((View) myDomain2NotationMap.get(nextLinkDescriptor.getDestination()));
					it.remove();
					if (nextLinkDescriptor.getModelElement() != null) {
						myDomain2NotationMap.put(nextLinkDescriptor.getModelElement(), edge);
					}
					continueLinkCreation = true;
					switch (nextLinkDescriptor.getVisualID()) {
					case ExtensionEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getExtension_Edge_OutgoingLinks(edge));
						break;
					case AssociationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getAssociation_Edge_OutgoingLinks(edge));
						break;
					case ProfileApplicationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getProfileApplication_Edge_OutgoingLinks(edge));
						break;
					case AssociationBranchEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getAssociation_BranchEdge_OutgoingLinks(edge));
						break;
					case GeneralizationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getGeneralization_Edge_OutgoingLinks(edge));
						break;
					case DependencyEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getDependency_Edge_OutgoingLinks(edge));
						break;
					case DependencyBranchEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getDependency_BranchEdge_OutgoingLinks(edge));
						break;
					case ElementImportEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getElementImport_Edge_OutgoingLinks(edge));
						break;
					case PackageImportEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getPackageImport_Edge_OutgoingLinks(edge));
						break;
					}
				}
			}
			myLinkDescriptors.addAll(additionalDescriptors);
		}
	}

	/**
	 * @generated
	 */
	private Node getCompartment(View node, String visualID) {
		String type = UMLVisualIDRegistry.getType(visualID);
		for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (nextView instanceof Node && type.equals(nextView.getType())) {
				return (Node) nextView;
			}
		}
		return null;
	}
}
