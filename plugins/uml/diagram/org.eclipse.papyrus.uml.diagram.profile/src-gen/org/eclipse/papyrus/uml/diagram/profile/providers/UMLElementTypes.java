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
package org.eclipse.papyrus.uml.diagram.profile.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DiagramElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassPropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationLiteralEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ExtensionEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileApplicationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Profile_ProfileDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Profile_ProfileDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Stereotype_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Stereotype_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_MetaclassShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_MetaclassShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PackagedElementShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Profile_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Profile_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Diagram_ShortcutShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Diagram_ShortcutShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_DataTypeOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_DataTypeOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType EnumerationLiteral_LiteralLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.EnumerationLiteral_LiteralLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_DataTypeAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_DataTypeAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_ClassAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_ClassAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_ClassOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_ClassOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Stereotype_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Stereotype_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_MetaclassShape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_MetaclassShape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Profile_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Profile_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PackagedElementShape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Extension_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Extension_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ProfileApplication_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ProfileApplication_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_BranchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_BranchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_BranchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_BranchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ElementImport_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ElementImport_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PackageImport_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PackageImport_Edge"); //$NON-NLS-1$
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
			elements.put(Profile_ProfileDiagram, UMLPackage.eINSTANCE.getProfile());
			elements.put(Dependency_Shape, UMLPackage.eINSTANCE.getDependency());
			elements.put(Association_Shape, UMLPackage.eINSTANCE.getAssociation());
			elements.put(Stereotype_Shape, UMLPackage.eINSTANCE.getStereotype());
			elements.put(Class_Shape, UMLPackage.eINSTANCE.getClass_());
			elements.put(Class_MetaclassShape, UMLPackage.eINSTANCE.getClass_());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_PackagedElementShape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Model_Shape, UMLPackage.eINSTANCE.getModel());
			elements.put(Profile_Shape, UMLPackage.eINSTANCE.getProfile());
			elements.put(Package_Shape, UMLPackage.eINSTANCE.getPackage());
			elements.put(Enumeration_Shape, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(PrimitiveType_Shape, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(DataType_Shape, UMLPackage.eINSTANCE.getDataType());
			elements.put(Diagram_ShortcutShape, NotationPackage.eINSTANCE.getDiagram());
			elements.put(PrimitiveType_Shape_CN, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(Operation_DataTypeOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(EnumerationLiteral_LiteralLabel, UMLPackage.eINSTANCE.getEnumerationLiteral());
			elements.put(Property_DataTypeAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_ClassAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Operation_ClassOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Stereotype_Shape_CN, UMLPackage.eINSTANCE.getStereotype());
			elements.put(Class_Shape_CN, UMLPackage.eINSTANCE.getClass_());
			elements.put(Class_MetaclassShape_CN, UMLPackage.eINSTANCE.getClass_());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(Model_Shape_CN, UMLPackage.eINSTANCE.getModel());
			elements.put(Profile_Shape_CN, UMLPackage.eINSTANCE.getProfile());
			elements.put(Package_Shape_CN, UMLPackage.eINSTANCE.getPackage());
			elements.put(Constraint_PackagedElementShape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Enumeration_Shape_CN, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType());
			elements.put(Extension_Edge, UMLPackage.eINSTANCE.getExtension());
			elements.put(Association_Edge, UMLPackage.eINSTANCE.getAssociation());
			elements.put(ProfileApplication_Edge, UMLPackage.eINSTANCE.getProfileApplication());
			elements.put(Association_BranchEdge, UMLPackage.eINSTANCE.getAssociation());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Dependency_BranchEdge, UMLPackage.eINSTANCE.getDependency());
			elements.put(ElementImport_Edge, UMLPackage.eINSTANCE.getElementImport());
			elements.put(PackageImport_Edge, UMLPackage.eINSTANCE.getPackageImport());
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
			KNOWN_ELEMENT_TYPES.add(Profile_ProfileDiagram);
			KNOWN_ELEMENT_TYPES.add(Dependency_Shape);
			KNOWN_ELEMENT_TYPES.add(Association_Shape);
			KNOWN_ELEMENT_TYPES.add(Stereotype_Shape);
			KNOWN_ELEMENT_TYPES.add(Class_Shape);
			KNOWN_ELEMENT_TYPES.add(Class_MetaclassShape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_PackagedElementShape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape);
			KNOWN_ELEMENT_TYPES.add(Profile_Shape);
			KNOWN_ELEMENT_TYPES.add(Package_Shape);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape);
			KNOWN_ELEMENT_TYPES.add(Diagram_ShortcutShape);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Operation_DataTypeOperationLabel);
			KNOWN_ELEMENT_TYPES.add(EnumerationLiteral_LiteralLabel);
			KNOWN_ELEMENT_TYPES.add(Property_DataTypeAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_ClassAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_ClassOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Stereotype_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Class_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Class_MetaclassShape_CN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Model_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Profile_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Package_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_PackagedElementShape_CN);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Extension_Edge);
			KNOWN_ELEMENT_TYPES.add(Association_Edge);
			KNOWN_ELEMENT_TYPES.add(ProfileApplication_Edge);
			KNOWN_ELEMENT_TYPES.add(Association_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(ElementImport_Edge);
			KNOWN_ELEMENT_TYPES.add(PackageImport_Edge);
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
			case ProfileDiagramEditPart.VISUAL_ID:
				return Profile_ProfileDiagram;
			case DependencyNodeEditPart.VISUAL_ID:
				return Dependency_Shape;
			case AssociationNodeEditPart.VISUAL_ID:
				return Association_Shape;
			case StereotypeEditPart.VISUAL_ID:
				return Stereotype_Shape;
			case ClassEditPart.VISUAL_ID:
				return Class_Shape;
			case MetaclassEditPart.VISUAL_ID:
				return Class_MetaclassShape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_PackagedElementShape;
			case ModelEditPartTN.VISUAL_ID:
				return Model_Shape;
			case ProfileEditPartTN.VISUAL_ID:
				return Profile_Shape;
			case PackageEditPart.VISUAL_ID:
				return Package_Shape;
			case EnumerationEditPart.VISUAL_ID:
				return Enumeration_Shape;
			case PrimitiveTypeEditPart.VISUAL_ID:
				return PrimitiveType_Shape;
			case DataTypeEditPart.VISUAL_ID:
				return DataType_Shape;
			case ShortCutDiagramEditPart.VISUAL_ID:
				return Diagram_ShortcutShape;
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return PrimitiveType_Shape_CN;
			case DataTypeOperationEditPart.VISUAL_ID:
				return Operation_DataTypeOperationLabel;
			case EnumerationLiteralEditPart.VISUAL_ID:
				return EnumerationLiteral_LiteralLabel;
			case DataTypePropertyEditPart.VISUAL_ID:
				return Property_DataTypeAttributeLabel;
			case ClassPropertyEditPart.VISUAL_ID:
				return Property_ClassAttributeLabel;
			case ClassOperationEditPart.VISUAL_ID:
				return Operation_ClassOperationLabel;
			case StereotypeEditPartCN.VISUAL_ID:
				return Stereotype_Shape_CN;
			case ClassEditPartCN.VISUAL_ID:
				return Class_Shape_CN;
			case MetaclassEditPartCN.VISUAL_ID:
				return Class_MetaclassShape_CN;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape_CN;
			case ModelEditPartCN.VISUAL_ID:
				return Model_Shape_CN;
			case ProfileEditPartCN.VISUAL_ID:
				return Profile_Shape_CN;
			case PackageEditPartCN.VISUAL_ID:
				return Package_Shape_CN;
			case ConstraintEditPartCN.VISUAL_ID:
				return Constraint_PackagedElementShape_CN;
			case EnumerationEditPartCN.VISUAL_ID:
				return Enumeration_Shape_CN;
			case DataTypeEditPartCN.VISUAL_ID:
				return DataType_Shape_CN;
			case ExtensionEditPart.VISUAL_ID:
				return Extension_Edge;
			case AssociationEditPart.VISUAL_ID:
				return Association_Edge;
			case ProfileApplicationEditPart.VISUAL_ID:
				return ProfileApplication_Edge;
			case AssociationBranchEditPart.VISUAL_ID:
				return Association_BranchEdge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case DependencyBranchEditPart.VISUAL_ID:
				return Dependency_BranchEdge;
			case ElementImportEditPart.VISUAL_ID:
				return ElementImport_Edge;
			case PackageImportEditPart.VISUAL_ID:
				return PackageImport_Edge;
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
			return org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
