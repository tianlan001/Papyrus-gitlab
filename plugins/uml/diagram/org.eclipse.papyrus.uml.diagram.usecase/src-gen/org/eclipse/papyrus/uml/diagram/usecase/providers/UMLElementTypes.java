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
package org.eclipse.papyrus.uml.diagram.usecase.providers;

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
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DefaultNamedElementEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Package_UseCaseDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_UseCaseDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_ClassifierShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_ClassifierShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_ClassifierShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_ClassifierShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Classifier_SubjectShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Classifier_SubjectShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType NamedElement_DefaultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Diagram_ShortcutShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Diagram_ShortcutShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExtensionPoint_ExtensionPointLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExtensionPoint_ExtensionPointLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExtensionPoint_ClassifierExtensionPointLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExtensionPoint_ClassifierExtensionPointLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Include_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Include_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Extend_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Extend_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_ConstrainedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ConstrainedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_AnnotatedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Abstraction_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Abstraction_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Usage_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Usage_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Realization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Realization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PackageMerge_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PackageMerge_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PackageImport_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PackageImport_Edge"); //$NON-NLS-1$

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
			elements.put(Package_UseCaseDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Actor_Shape, UMLPackage.eINSTANCE.getActor());
			elements.put(Actor_ClassifierShape, UMLPackage.eINSTANCE.getActor());
			elements.put(UseCase_Shape, UMLPackage.eINSTANCE.getUseCase());
			elements.put(UseCase_ClassifierShape, UMLPackage.eINSTANCE.getUseCase());
			elements.put(Classifier_SubjectShape, UMLPackage.eINSTANCE.getClassifier());
			elements.put(Package_Shape, UMLPackage.eINSTANCE.getPackage());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(NamedElement_DefaultShape, UMLPackage.eINSTANCE.getNamedElement());
			elements.put(Diagram_ShortcutShape, NotationPackage.eINSTANCE.getDiagram());
			elements.put(ExtensionPoint_ExtensionPointLabel, UMLPackage.eINSTANCE.getExtensionPoint());
			elements.put(ExtensionPoint_ClassifierExtensionPointLabel, UMLPackage.eINSTANCE.getExtensionPoint());
			elements.put(UseCase_Shape_CCN, UMLPackage.eINSTANCE.getUseCase());
			elements.put(Component_Shape_CCN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_Shape_CCN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Actor_Shape_CCN, UMLPackage.eINSTANCE.getActor());
			elements.put(Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Actor_Shape_CN, UMLPackage.eINSTANCE.getActor());
			elements.put(UseCase_Shape_CN, UMLPackage.eINSTANCE.getUseCase());
			elements.put(Component_Shape_CN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Package_Shape_CN, UMLPackage.eINSTANCE.getPackage());
			elements.put(Include_Edge, UMLPackage.eINSTANCE.getInclude());
			elements.put(Extend_Edge, UMLPackage.eINSTANCE.getExtend());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(Association_Edge, UMLPackage.eINSTANCE.getAssociation());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Abstraction_Edge, UMLPackage.eINSTANCE.getAbstraction());
			elements.put(Usage_Edge, UMLPackage.eINSTANCE.getUsage());
			elements.put(Realization_Edge, UMLPackage.eINSTANCE.getRealization());
			elements.put(PackageMerge_Edge, UMLPackage.eINSTANCE.getPackageMerge());
			elements.put(PackageImport_Edge, UMLPackage.eINSTANCE.getPackageImport());
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
			KNOWN_ELEMENT_TYPES.add(Package_UseCaseDiagram);
			KNOWN_ELEMENT_TYPES.add(Actor_Shape);
			KNOWN_ELEMENT_TYPES.add(Actor_ClassifierShape);
			KNOWN_ELEMENT_TYPES.add(UseCase_Shape);
			KNOWN_ELEMENT_TYPES.add(UseCase_ClassifierShape);
			KNOWN_ELEMENT_TYPES.add(Classifier_SubjectShape);
			KNOWN_ELEMENT_TYPES.add(Package_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(NamedElement_DefaultShape);
			KNOWN_ELEMENT_TYPES.add(Diagram_ShortcutShape);
			KNOWN_ELEMENT_TYPES.add(ExtensionPoint_ExtensionPointLabel);
			KNOWN_ELEMENT_TYPES.add(ExtensionPoint_ClassifierExtensionPointLabel);
			KNOWN_ELEMENT_TYPES.add(UseCase_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Component_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Actor_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Actor_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(UseCase_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Component_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Package_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Include_Edge);
			KNOWN_ELEMENT_TYPES.add(Extend_Edge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(Association_Edge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Abstraction_Edge);
			KNOWN_ELEMENT_TYPES.add(Usage_Edge);
			KNOWN_ELEMENT_TYPES.add(Realization_Edge);
			KNOWN_ELEMENT_TYPES.add(PackageMerge_Edge);
			KNOWN_ELEMENT_TYPES.add(PackageImport_Edge);
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
			case UseCaseDiagramEditPart.VISUAL_ID:
				return Package_UseCaseDiagram;
			case ActorEditPartTN.VISUAL_ID:
				return Actor_Shape;
			case ActorAsRectangleEditPartTN.VISUAL_ID:
				return Actor_ClassifierShape;
			case UseCaseEditPartTN.VISUAL_ID:
				return UseCase_Shape;
			case UseCaseAsRectangleEditPartTN.VISUAL_ID:
				return UseCase_ClassifierShape;
			case SubjectClassifierEditPartTN.VISUAL_ID:
				return Classifier_SubjectShape;
			case PackageEditPartTN.VISUAL_ID:
				return Package_Shape;
			case ConstraintEditPartTN.VISUAL_ID:
				return Constraint_Shape;
			case CommentEditPartTN.VISUAL_ID:
				return Comment_Shape;
			case DefaultNamedElementEditPartTN.VISUAL_ID:
				return NamedElement_DefaultShape;
			case ShortCutDiagramEditPart.VISUAL_ID:
				return Diagram_ShortcutShape;
			case ExtensionPointEditPart.VISUAL_ID:
				return ExtensionPoint_ExtensionPointLabel;
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return ExtensionPoint_ClassifierExtensionPointLabel;
			case UseCaseInComponentEditPart.VISUAL_ID:
				return UseCase_Shape_CCN;
			case ComponentInComponentEditPart.VISUAL_ID:
				return Component_Shape_CCN;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape_CN;
			case ConstraintInComponentEditPart.VISUAL_ID:
				return Constraint_Shape_CCN;
			case ActorInComponentEditPart.VISUAL_ID:
				return Actor_Shape_CCN;
			case ConstraintInPackageEditPart.VISUAL_ID:
				return Constraint_Shape_CN;
			case ActorInPackageEditPart.VISUAL_ID:
				return Actor_Shape_CN;
			case UseCaseInPackageEditPart.VISUAL_ID:
				return UseCase_Shape_CN;
			case ComponentInPackageEditPart.VISUAL_ID:
				return Component_Shape_CN;
			case PackageEditPartCN.VISUAL_ID:
				return Package_Shape_CN;
			case IncludeEditPart.VISUAL_ID:
				return Include_Edge;
			case ExtendEditPart.VISUAL_ID:
				return Extend_Edge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case AssociationEditPart.VISUAL_ID:
				return Association_Edge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case AbstractionEditPart.VISUAL_ID:
				return Abstraction_Edge;
			case UsageEditPart.VISUAL_ID:
				return Usage_Edge;
			case RealizationEditPart.VISUAL_ID:
				return Realization_Edge;
			case PackageMergeEditPart.VISUAL_ID:
				return PackageMerge_Edge;
			case PackageImportEditPart.VISUAL_ID:
				return PackageImport_Edge;
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
			return org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
