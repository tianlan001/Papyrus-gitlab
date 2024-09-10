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
package org.eclipse.papyrus.uml.diagram.component.providers;

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
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConnectorEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfacePortLinkEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.LinkDescriptorEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.OperationForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PropertyForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ReceptionInInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Package_ComponentDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_ComponentDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_PackagedElementShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_PackagedElementShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_ClassifierShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_ClassifierShape"); //$NON-NLS-1$
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
	public static final IElementType NamedElement_DefaultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Port_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_ClassifierShape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_ClassifierShape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_PackagedElementShape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_PackagedElementShape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_PackagedElementShape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_PackagedElementShape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_InterfaceAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_InterfaceAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_InterfaceOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_InterfaceOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Reception_InterfaceReceptionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Reception_InterfaceReceptionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Usage_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Usage_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InterfaceRealization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InterfaceRealization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Substitution_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Substitution_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Manifestation_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Manifestation_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ComponentRealization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ComponentRealization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Abstraction_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Abstraction_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Link_DescriptorEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Link_DescriptorEdge"); //$NON-NLS-1$
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
	public static final IElementType Dependency_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_BranchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_BranchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Link_InterfacePortEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Link_InterfacePortEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Connector_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Connector_Edge"); //$NON-NLS-1$

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
			elements.put(Package_ComponentDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Dependency_Shape, UMLPackage.eINSTANCE.getDependency());
			elements.put(Component_PackagedElementShape, UMLPackage.eINSTANCE.getComponent());
			elements.put(Model_Shape, UMLPackage.eINSTANCE.getModel());
			elements.put(Package_Shape, UMLPackage.eINSTANCE.getPackage());
			elements.put(Interface_ClassifierShape, UMLPackage.eINSTANCE.getInterface());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(NamedElement_DefaultShape, UMLPackage.eINSTANCE.getNamedElement());
			elements.put(Interface_Shape, UMLPackage.eINSTANCE.getInterface());
			elements.put(Port_Shape, UMLPackage.eINSTANCE.getPort());
			elements.put(Model_Shape_CN, UMLPackage.eINSTANCE.getModel());
			elements.put(Package_Shape_CN, UMLPackage.eINSTANCE.getPackage());
			elements.put(Interface_ClassifierShape_CN, UMLPackage.eINSTANCE.getInterface());
			elements.put(Component_PackagedElementShape_CCN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Component_PackagedElementShape_CN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Property_InterfaceAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Operation_InterfaceOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Reception_InterfaceReceptionLabel, UMLPackage.eINSTANCE.getReception());
			elements.put(Interface_Shape_CN, UMLPackage.eINSTANCE.getInterface());
			elements.put(Property_Shape, UMLPackage.eINSTANCE.getProperty());
			elements.put(Usage_Edge, UMLPackage.eINSTANCE.getUsage());
			elements.put(InterfaceRealization_Edge, UMLPackage.eINSTANCE.getInterfaceRealization());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(Substitution_Edge, UMLPackage.eINSTANCE.getSubstitution());
			elements.put(Manifestation_Edge, UMLPackage.eINSTANCE.getManifestation());
			elements.put(ComponentRealization_Edge, UMLPackage.eINSTANCE.getComponentRealization());
			elements.put(Abstraction_Edge, UMLPackage.eINSTANCE.getAbstraction());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Dependency_BranchEdge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Connector_Edge, UMLPackage.eINSTANCE.getConnector());
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
			KNOWN_ELEMENT_TYPES.add(Package_ComponentDiagram);
			KNOWN_ELEMENT_TYPES.add(Dependency_Shape);
			KNOWN_ELEMENT_TYPES.add(Component_PackagedElementShape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape);
			KNOWN_ELEMENT_TYPES.add(Package_Shape);
			KNOWN_ELEMENT_TYPES.add(Interface_ClassifierShape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(NamedElement_DefaultShape);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape);
			KNOWN_ELEMENT_TYPES.add(Port_Shape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Package_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Interface_ClassifierShape_CN);
			KNOWN_ELEMENT_TYPES.add(Component_PackagedElementShape_CCN);
			KNOWN_ELEMENT_TYPES.add(Component_PackagedElementShape_CN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Property_InterfaceAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_InterfaceOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Reception_InterfaceReceptionLabel);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Property_Shape);
			KNOWN_ELEMENT_TYPES.add(Usage_Edge);
			KNOWN_ELEMENT_TYPES.add(InterfaceRealization_Edge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(Substitution_Edge);
			KNOWN_ELEMENT_TYPES.add(Manifestation_Edge);
			KNOWN_ELEMENT_TYPES.add(ComponentRealization_Edge);
			KNOWN_ELEMENT_TYPES.add(Abstraction_Edge);
			KNOWN_ELEMENT_TYPES.add(Link_DescriptorEdge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(Link_InterfacePortEdge);
			KNOWN_ELEMENT_TYPES.add(Connector_Edge);
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
			case ComponentDiagramEditPart.VISUAL_ID:
				return Package_ComponentDiagram;
			case DependencyNodeEditPart.VISUAL_ID:
				return Dependency_Shape;
			case ComponentEditPart.VISUAL_ID:
				return Component_PackagedElementShape;
			case ModelEditPart.VISUAL_ID:
				return Model_Shape;
			case PackageEditPart.VISUAL_ID:
				return Package_Shape;
			case RectangleInterfaceEditPart.VISUAL_ID:
				return Interface_ClassifierShape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_Shape;
			case DefaultNamedElementEditPart.VISUAL_ID:
				return NamedElement_DefaultShape;
			case InterfaceEditPart.VISUAL_ID:
				return Interface_Shape;
			case PortEditPart.VISUAL_ID:
				return Port_Shape;
			case ModelEditPartCN.VISUAL_ID:
				return Model_Shape_CN;
			case PackageEditPartCN.VISUAL_ID:
				return Package_Shape_CN;
			case RectangleInterfaceEditPartCN.VISUAL_ID:
				return Interface_ClassifierShape_CN;
			case ComponentEditPartCN.VISUAL_ID:
				return Component_PackagedElementShape_CCN;
			case ComponentEditPartPCN.VISUAL_ID:
				return Component_PackagedElementShape_CN;
			case CommentEditPartPCN.VISUAL_ID:
				return Comment_Shape_CN;
			case ConstraintEditPartPCN.VISUAL_ID:
				return Constraint_Shape_CN;
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return Property_InterfaceAttributeLabel;
			case OperationForInterfaceEditPart.VISUAL_ID:
				return Operation_InterfaceOperationLabel;
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return Reception_InterfaceReceptionLabel;
			case InterfaceEditPartPCN.VISUAL_ID:
				return Interface_Shape_CN;
			case PropertyPartEditPartCN.VISUAL_ID:
				return Property_Shape;
			case UsageEditPart.VISUAL_ID:
				return Usage_Edge;
			case InterfaceRealizationEditPart.VISUAL_ID:
				return InterfaceRealization_Edge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case SubstitutionEditPart.VISUAL_ID:
				return Substitution_Edge;
			case ManifestationEditPart.VISUAL_ID:
				return Manifestation_Edge;
			case ComponentRealizationEditPart.VISUAL_ID:
				return ComponentRealization_Edge;
			case AbstractionEditPart.VISUAL_ID:
				return Abstraction_Edge;
			case LinkDescriptorEditPart.VISUAL_ID:
				return Link_DescriptorEdge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case DependencyBranchEditPart.VISUAL_ID:
				return Dependency_BranchEdge;
			case InterfacePortLinkEditPart.VISUAL_ID:
				return Link_InterfacePortEdge;
			case ConnectorEditPart.VISUAL_ID:
				return Connector_Edge;
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
			return org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
