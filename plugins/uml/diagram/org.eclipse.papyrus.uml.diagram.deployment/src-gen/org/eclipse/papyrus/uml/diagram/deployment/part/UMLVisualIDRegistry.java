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
package org.eclipse.papyrus.uml.diagram.deployment.part;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DefaultNamedElementNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.MultiDependencyLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.expressions.UMLOCLFactory;
import org.eclipse.uml2.uml.Artifact;
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
	// private static final String DEBUG_KEY = "org.eclipse.papyrus.uml.diagram.deployment/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (DeploymentDiagramEditPart.MODEL_ID.equals(view.getType())) {
				return DeploymentDiagramEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getVisualID(view.getType());
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
		return DeploymentDiagramEditPart.VISUAL_ID;
	}

	/**
	 * @generated
	 */
	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		String containerModelID = org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!DeploymentDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return ""; //$NON-NLS-1$
		}
		String containerVisualID;
		if (DeploymentDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DeploymentDiagramEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case DeploymentDiagramEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getDependency().isSuperTypeOf(domainElement.eClass())) {
					return DependencyNodeEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
					return ModelEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
					return PackageEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return DeviceEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape((Artifact) domainElement)) {
					return ArtifactEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NodeEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNamedElement().isSuperTypeOf(domainElement.eClass())) {
					return DefaultNamedElementEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecificationEditPart.VISUAL_ID;
				}
				break;
			case ModelPackageableElementCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
					return ModelEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
					return PackageEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return NestedDeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return NestedExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NestedNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CN((Artifact) domainElement)) {
					return NestedArtifactNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsPackageableElEditPart.VISUAL_ID;
				}
				break;
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
					return ModelEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
					return PackageEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CN((Artifact) domainElement)) {
					return NestedArtifactNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return NestedDeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return NestedExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NestedNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsPackageableElEditPart.VISUAL_ID;
				}
				break;
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return DeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CCN((Artifact) domainElement)) {
					return ArtifactEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return DeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CCN((Artifact) domainElement)) {
					return ArtifactEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case ArtifactCompositeCompartmentEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_ACN((Artifact) domainElement)) {
					return ArtifactEditPartACN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID;
				}
				break;
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getModel().isSuperTypeOf(domainElement.eClass())) {
					return ModelEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
					return PackageEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return NestedDeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return NestedExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NestedNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CN((Artifact) domainElement)) {
					return NestedArtifactNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsPackageableElEditPart.VISUAL_ID;
				}
				break;
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(domainElement.eClass())) {
					return PackageEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CN((Artifact) domainElement)) {
					return NestedArtifactNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return NestedDeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return NestedExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NestedNodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(domainElement.eClass())) {
					return CommentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(domainElement.eClass())) {
					return ConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsPackageableElEditPart.VISUAL_ID;
				}
				break;
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return DeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CCN((Artifact) domainElement)) {
					return ArtifactEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getDevice().isSuperTypeOf(domainElement.eClass())) {
					return DeviceEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getExecutionEnvironment().isSuperTypeOf(domainElement.eClass())) {
					return ExecutionEnvironmentEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
					return NodeEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_CCN((Artifact) domainElement)) {
					return ArtifactEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsClassifierEditPart.VISUAL_ID;
				}
				break;
			case ArtifactCompositeCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_ACN((Artifact) domainElement)) {
					return ArtifactEditPartACN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID;
				}
				break;
			case ArtifactCompositeCompartmentEditPartACN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getArtifact().isSuperTypeOf(domainElement.eClass()) && isArtifact_Shape_ACN((Artifact) domainElement)) {
					return ArtifactEditPartACN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDeploymentSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID;
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
		String containerModelID = org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!DeploymentDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		String containerVisualID;
		if (DeploymentDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DeploymentDiagramEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case DeploymentDiagramEditPart.VISUAL_ID:
				if (DependencyNodeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeviceEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DefaultNamedElementEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecificationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DependencyNodeEditPart.VISUAL_ID:
				if (MultiDependencyLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelEditPart.VISUAL_ID:
				if (ModelNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ModelPackageableElementCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
			case ConstraintEditPart.VISUAL_ID:
				if (ConstraintNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintSpecificationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPart.VISUAL_ID:
				if (CommentBodyEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				if (ExecutionEnvironmentNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeviceEditPart.VISUAL_ID:
				if (DeviceNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeviceCompositeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactEditPart.VISUAL_ID:
				if (ArtifactNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactCompositeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NodeEditPart.VISUAL_ID:
				if (NodeNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeCompositeCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DefaultNamedElementEditPart.VISUAL_ID:
				if (DefaultNamedElementNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeploymentSpecificationEditPart.VISUAL_ID:
				if (DeploymentSpecificationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecificationFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
			case PackageEditPartCN.VISUAL_ID:
				if (PackageNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackagePackageableElementCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeviceEditPartCN.VISUAL_ID:
				if (DeviceNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NestedDeviceEditPartCN.VISUAL_ID:
				if (NestedDeviceNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeviceCompositeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				if (ExecutionEnvironmentNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				if (NestedExecutionEnvironmentNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NodeEditPartCN.VISUAL_ID:
				if (NodeNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NestedNodeEditPartCN.VISUAL_ID:
				if (NestedNodeNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeCompositeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactEditPartCN.VISUAL_ID:
				if (ArtifactNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactFloatingLabelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactCompositeCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactEditPartACN.VISUAL_ID:
				if (ArtifactNameEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactFloatingLabelEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactCompositeCompartmentEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				if (NestedArtifactNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CommentEditPartCN.VISUAL_ID:
				if (CommentBodyEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ConstraintEditPartCN.VISUAL_ID:
				if (ConstraintNameEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				if (DeploymentSpecAsClassifierNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				if (DeploymentSpecAsPackageableElNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsPackageableElFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				if (DeploymentSpecAsNestedArtifactNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsNestedArtifactFloatingLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelPackageableElementCompartmentEditPart.VISUAL_ID:
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedDeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedDeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
				if (DeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactCompositeCompartmentEditPart.VISUAL_ID:
				if (ArtifactEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (ModelEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedDeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				if (PackageEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedDeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NestedNodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
				if (ArtifactEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
				if (DeviceEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (NodeEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ArtifactEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactCompositeCompartmentEditPartCN.VISUAL_ID:
				if (ArtifactEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ArtifactCompositeCompartmentEditPartACN.VISUAL_ID:
				if (ArtifactEditPartACN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DeploymentEditPart.VISUAL_ID:
				if (DeploymentNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DeploymentAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case ManifestationEditPart.VISUAL_ID:
				if (ManifestationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (ManifestationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
			case CommunicationPathEditPart.VISUAL_ID:
				if (CommunicationPathNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CommunicationPathAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
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
		if (UMLPackage.eINSTANCE.getDeployment().isSuperTypeOf(domainElement.eClass())) {
			return DeploymentEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getManifestation().isSuperTypeOf(domainElement.eClass())) {
			return ManifestationEditPart.VISUAL_ID;
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
		if (UMLPackage.eINSTANCE.getCommunicationPath().isSuperTypeOf(domainElement.eClass())) {
			return CommunicationPathEditPart.VISUAL_ID;
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
	private static boolean isArtifact_Shape(Artifact domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isArtifact_Shape_CCN(Artifact domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isArtifact_Shape_ACN(Artifact domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isArtifact_Shape_CN(Artifact domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(domainElement);
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
			case ModelPackageableElementCompartmentEditPart.VISUAL_ID:
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
			case ArtifactCompositeCompartmentEditPart.VISUAL_ID:
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
			case ArtifactCompositeCompartmentEditPartCN.VISUAL_ID:
			case ArtifactCompositeCompartmentEditPartACN.VISUAL_ID:
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
			case DeploymentDiagramEditPart.VISUAL_ID:
				return false;
			case DeviceEditPartCN.VISUAL_ID:
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
			case NodeEditPartCN.VISUAL_ID:
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
			case ConstraintEditPartCN.VISUAL_ID:
			case CommentEditPart.VISUAL_ID:
			case ConstraintEditPart.VISUAL_ID:
			case DependencyNodeEditPart.VISUAL_ID:
			case DefaultNamedElementEditPart.VISUAL_ID:
			case DeploymentSpecificationEditPart.VISUAL_ID:
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
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
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};
}
