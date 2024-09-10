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
package org.eclipse.papyrus.uml.diagram.deployment.providers;

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
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.LinkDescriptorEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramEditorPlugin;
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
	public static final IElementType Package_DeploymentDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_DeploymentDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Shape"); //$NON-NLS-1$
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
	public static final IElementType Constraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExecutionEnvironment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Device_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Device_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType NamedElement_DefaultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DeploymentSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape"); //$NON-NLS-1$
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
	public static final IElementType Device_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Device_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Device_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Device_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExecutionEnvironment_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExecutionEnvironment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape_ACN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape_ACN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape_CN"); //$NON-NLS-1$
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
	public static final IElementType DeploymentSpecification_Shape_CCN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_CCN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DeploymentSpecification_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DeploymentSpecification_Shape_ACN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_ACN"); //$NON-NLS-1$
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
	public static final IElementType Deployment_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Deployment_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Manifestation_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Manifestation_Edge"); //$NON-NLS-1$
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
	public static final IElementType CommunicationPath_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CommunicationPath_Edge"); //$NON-NLS-1$

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
			elements.put(Package_DeploymentDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Dependency_Shape, UMLPackage.eINSTANCE.getDependency());
			elements.put(Model_Shape, UMLPackage.eINSTANCE.getModel());
			elements.put(Package_Shape, UMLPackage.eINSTANCE.getPackage());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(ExecutionEnvironment_Shape, UMLPackage.eINSTANCE.getExecutionEnvironment());
			elements.put(Device_Shape, UMLPackage.eINSTANCE.getDevice());
			elements.put(Artifact_Shape, UMLPackage.eINSTANCE.getArtifact());
			elements.put(Node_Shape, UMLPackage.eINSTANCE.getNode());
			elements.put(NamedElement_DefaultShape, UMLPackage.eINSTANCE.getNamedElement());
			elements.put(DeploymentSpecification_Shape, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(Model_Shape_CN, UMLPackage.eINSTANCE.getModel());
			elements.put(Package_Shape_CN, UMLPackage.eINSTANCE.getPackage());
			elements.put(Device_Shape_CCN, UMLPackage.eINSTANCE.getDevice());
			elements.put(Device_Shape_CN, UMLPackage.eINSTANCE.getDevice());
			elements.put(ExecutionEnvironment_Shape_CCN, UMLPackage.eINSTANCE.getExecutionEnvironment());
			elements.put(ExecutionEnvironment_Shape_CN, UMLPackage.eINSTANCE.getExecutionEnvironment());
			elements.put(Node_Shape_CCN, UMLPackage.eINSTANCE.getNode());
			elements.put(Node_Shape_CN, UMLPackage.eINSTANCE.getNode());
			elements.put(Artifact_Shape_CCN, UMLPackage.eINSTANCE.getArtifact());
			elements.put(Artifact_Shape_ACN, UMLPackage.eINSTANCE.getArtifact());
			elements.put(Artifact_Shape_CN, UMLPackage.eINSTANCE.getArtifact());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(DeploymentSpecification_Shape_CCN, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(DeploymentSpecification_Shape_CN, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(DeploymentSpecification_Shape_ACN, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(Deployment_Edge, UMLPackage.eINSTANCE.getDeployment());
			elements.put(Manifestation_Edge, UMLPackage.eINSTANCE.getManifestation());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Dependency_BranchEdge, UMLPackage.eINSTANCE.getDependency());
			elements.put(CommunicationPath_Edge, UMLPackage.eINSTANCE.getCommunicationPath());
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
			KNOWN_ELEMENT_TYPES.add(Package_DeploymentDiagram);
			KNOWN_ELEMENT_TYPES.add(Dependency_Shape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape);
			KNOWN_ELEMENT_TYPES.add(Package_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(ExecutionEnvironment_Shape);
			KNOWN_ELEMENT_TYPES.add(Device_Shape);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape);
			KNOWN_ELEMENT_TYPES.add(Node_Shape);
			KNOWN_ELEMENT_TYPES.add(NamedElement_DefaultShape);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Package_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Device_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Device_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(ExecutionEnvironment_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(ExecutionEnvironment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Node_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Node_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape_ACN);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape_CCN);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape_ACN);
			KNOWN_ELEMENT_TYPES.add(Link_DescriptorEdge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Deployment_Edge);
			KNOWN_ELEMENT_TYPES.add(Manifestation_Edge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(CommunicationPath_Edge);
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
			case DeploymentDiagramEditPart.VISUAL_ID:
				return Package_DeploymentDiagram;
			case DependencyNodeEditPart.VISUAL_ID:
				return Dependency_Shape;
			case ModelEditPart.VISUAL_ID:
				return Model_Shape;
			case PackageEditPart.VISUAL_ID:
				return Package_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_Shape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				return ExecutionEnvironment_Shape;
			case DeviceEditPart.VISUAL_ID:
				return Device_Shape;
			case ArtifactEditPart.VISUAL_ID:
				return Artifact_Shape;
			case NodeEditPart.VISUAL_ID:
				return Node_Shape;
			case DefaultNamedElementEditPart.VISUAL_ID:
				return NamedElement_DefaultShape;
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return DeploymentSpecification_Shape;
			case ModelEditPartCN.VISUAL_ID:
				return Model_Shape_CN;
			case PackageEditPartCN.VISUAL_ID:
				return Package_Shape_CN;
			case DeviceEditPartCN.VISUAL_ID:
				return Device_Shape_CCN;
			case NestedDeviceEditPartCN.VISUAL_ID:
				return Device_Shape_CN;
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				return ExecutionEnvironment_Shape_CCN;
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				return ExecutionEnvironment_Shape_CN;
			case NodeEditPartCN.VISUAL_ID:
				return Node_Shape_CCN;
			case NestedNodeEditPartCN.VISUAL_ID:
				return Node_Shape_CN;
			case ArtifactEditPartCN.VISUAL_ID:
				return Artifact_Shape_CCN;
			case ArtifactEditPartACN.VISUAL_ID:
				return Artifact_Shape_ACN;
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				return Artifact_Shape_CN;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape_CN;
			case ConstraintEditPartCN.VISUAL_ID:
				return Constraint_Shape_CN;
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				return DeploymentSpecification_Shape_CCN;
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				return DeploymentSpecification_Shape_CN;
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				return DeploymentSpecification_Shape_ACN;
			case LinkDescriptorEditPart.VISUAL_ID:
				return Link_DescriptorEdge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case DeploymentEditPart.VISUAL_ID:
				return Deployment_Edge;
			case ManifestationEditPart.VISUAL_ID:
				return Manifestation_Edge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case DependencyBranchEditPart.VISUAL_ID:
				return Dependency_BranchEdge;
			case CommunicationPathEditPart.VISUAL_ID:
				return CommunicationPath_Edge;
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
			return org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes.getElement(elementTypeAdapter);
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
