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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartCN;
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
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.DeploymentTarget;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	public static final org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramUpdater INSTANCE = new UMLDiagramUpdater();

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
			case DeploymentDiagramEditPart.VISUAL_ID:
				return getPackage_DeploymentDiagram_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPart.VISUAL_ID:
				return getModel_PackagedElementCompartment_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				return getPackage_PackagedElementCompartment_SemanticChildren(view);
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
				return getDevice_NestedNodeCompartment_SemanticChildren(view);
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
				return getExecutionEnvironment_NestedNodeCompartment_SemanticChildren(view);
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
				return getNode_NestedNodeCompartment_SemanticChildren(view);
			case ArtifactCompositeCompartmentEditPart.VISUAL_ID:
				return getArtifact_NestedArtifactCompartment_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getModel_PackagedElementCompartment_CN_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getPackage_PackagedElementCompartment_CN_SemanticChildren(view);
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
				return getDevice_NestedNodeCompartment_CN_SemanticChildren(view);
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_NestedNodeCompartment_CN_SemanticChildren(view);
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
				return getNode_NestedNodeCompartment_CN_SemanticChildren(view);
			case ArtifactCompositeCompartmentEditPartCN.VISUAL_ID:
				return getArtifact_NestedArtifactCompartment_CCN_SemanticChildren(view);
			case ArtifactCompositeCompartmentEditPartACN.VISUAL_ID:
				return getArtifact_NestedArtifactCompartment_ACN_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_DeploymentDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DependencyNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedMembers()
				.iterator(); it.hasNext();) {
			NamedElement childElement = (NamedElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DefaultNamedElementEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_NestedNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_NestedNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_NestedNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getArtifact_NestedArtifactCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Artifact modelElement = (Artifact) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedArtifacts()
				.iterator(); it.hasNext();) {
			Artifact childElement = (Artifact) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartACN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedArtifactNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedNodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsPackageableElEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_NestedNodeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_NestedNodeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_NestedNodeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedNodes()
				.iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DeviceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsClassifierEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getArtifact_NestedArtifactCompartment_CCN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Artifact modelElement = (Artifact) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedArtifacts()
				.iterator(); it.hasNext();) {
			Artifact childElement = (Artifact) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartACN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getArtifact_NestedArtifactCompartment_ACN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Artifact modelElement = (Artifact) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedArtifacts()
				.iterator(); it.hasNext();) {
			Artifact childElement = (Artifact) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ArtifactEditPartACN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case DeploymentDiagramEditPart.VISUAL_ID:
				return getPackage_DeploymentDiagram_ContainedLinks(view);
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_ContainedLinks(view);
			case ModelEditPart.VISUAL_ID:
				return getModel_Shape_ContainedLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_ContainedLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_ContainedLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_ContainedLinks(view);
			case DeviceEditPart.VISUAL_ID:
				return getDevice_Shape_ContainedLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_ContainedLinks(view);
			case NodeEditPart.VISUAL_ID:
				return getNode_Shape_ContainedLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_ContainedLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_ContainedLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_ContainedLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_ContainedLinks(view);
			case DeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CCN_ContainedLinks(view);
			case NestedDeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_ContainedLinks(view);
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CCN_ContainedLinks(view);
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_ContainedLinks(view);
			case NodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CCN_ContainedLinks(view);
			case NestedNodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_ContainedLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CCN_ContainedLinks(view);
			case ArtifactEditPartACN.VISUAL_ID:
				return getArtifact_Shape_ACN_ContainedLinks(view);
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_ContainedLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_ContainedLinks(view);
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CCN_ContainedLinks(view);
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_ContainedLinks(view);
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_ACN_ContainedLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_ContainedLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_ContainedLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_ContainedLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_ContainedLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_ContainedLinks(view);
			case CommunicationPathEditPart.VISUAL_ID:
				return getCommunicationPath_Edge_ContainedLinks(view);
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
			case ModelEditPart.VISUAL_ID:
				return getModel_Shape_IncomingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_IncomingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_IncomingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_IncomingLinks(view);
			case DeviceEditPart.VISUAL_ID:
				return getDevice_Shape_IncomingLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_IncomingLinks(view);
			case NodeEditPart.VISUAL_ID:
				return getNode_Shape_IncomingLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_IncomingLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_IncomingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_IncomingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_IncomingLinks(view);
			case DeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CCN_IncomingLinks(view);
			case NestedDeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_IncomingLinks(view);
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CCN_IncomingLinks(view);
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_IncomingLinks(view);
			case NodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CCN_IncomingLinks(view);
			case NestedNodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_IncomingLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CCN_IncomingLinks(view);
			case ArtifactEditPartACN.VISUAL_ID:
				return getArtifact_Shape_ACN_IncomingLinks(view);
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_IncomingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_IncomingLinks(view);
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CCN_IncomingLinks(view);
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_IncomingLinks(view);
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_ACN_IncomingLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_IncomingLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_IncomingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_IncomingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_IncomingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_IncomingLinks(view);
			case CommunicationPathEditPart.VISUAL_ID:
				return getCommunicationPath_Edge_IncomingLinks(view);
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
			case ModelEditPart.VISUAL_ID:
				return getModel_Shape_OutgoingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_OutgoingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_OutgoingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case ExecutionEnvironmentEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_OutgoingLinks(view);
			case DeviceEditPart.VISUAL_ID:
				return getDevice_Shape_OutgoingLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_OutgoingLinks(view);
			case NodeEditPart.VISUAL_ID:
				return getNode_Shape_OutgoingLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_OutgoingLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_OutgoingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_OutgoingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_OutgoingLinks(view);
			case DeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CCN_OutgoingLinks(view);
			case NestedDeviceEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_OutgoingLinks(view);
			case ExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CCN_OutgoingLinks(view);
			case NestedExecutionEnvironmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_OutgoingLinks(view);
			case NodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CCN_OutgoingLinks(view);
			case NestedNodeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_OutgoingLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CCN_OutgoingLinks(view);
			case ArtifactEditPartACN.VISUAL_ID:
				return getArtifact_Shape_ACN_OutgoingLinks(view);
			case NestedArtifactNodeEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_OutgoingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_OutgoingLinks(view);
			case DeploymentSpecAsClassifierEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CCN_OutgoingLinks(view);
			case DeploymentSpecAsPackageableElEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_OutgoingLinks(view);
			case DeploymentSpecAsNestedArtifactEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_ACN_OutgoingLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_OutgoingLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_OutgoingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_OutgoingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_OutgoingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_OutgoingLinks(view);
			case CommunicationPathEditPart.VISUAL_ID:
				return getCommunicationPath_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_DeploymentDiagram_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
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
	public List<UMLLinkDescriptor> getModel_Shape_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
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
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_ContainedLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_ContainedLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CCN_ContainedLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_ContainedLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CCN_ContainedLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_ContainedLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CCN_ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CCN_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_ACN_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
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
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CCN_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_ACN_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
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
	public List<UMLLinkDescriptor> getCommunicationPath_Edge_ContainedLinks(View view) {
		CommunicationPath modelElement = (CommunicationPath) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
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
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_IncomingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_IncomingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_IncomingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CCN_IncomingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_IncomingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CCN_IncomingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_IncomingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CCN_IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CCN_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_ACN_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
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
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CCN_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_ACN_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_IncomingLinks(View view) {
		Deployment modelElement = (Deployment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_IncomingLinks(View view) {
		Manifestation modelElement = (Manifestation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
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
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCommunicationPath_Edge_IncomingLinks(View view) {
		CommunicationPath modelElement = (CommunicationPath) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_CommunicationPath_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
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
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_OutgoingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_OutgoingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_OutgoingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CCN_OutgoingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_OutgoingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CCN_OutgoingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_OutgoingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CCN_OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CCN_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_ACN_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
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
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CCN_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_ACN_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_OutgoingLinks(View view) {
		Deployment modelElement = (Deployment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_OutgoingLinks(View view) {
		Manifestation modelElement = (Manifestation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
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
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
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
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCommunicationPath_Edge_OutgoingLinks(View view) {
		CommunicationPath modelElement = (CommunicationPath) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Deployment_Edge(DeploymentTarget container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getDeployments()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) linkObject;
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Manifestation_Edge(Artifact container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getManifestations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) linkObject;
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
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
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_CommunicationPath_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof CommunicationPath) {
				continue;
			}
			CommunicationPath link = (CommunicationPath) linkObject;
			if (!CommunicationPathEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.CommunicationPath_Edge, CommunicationPathEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Deployment_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) setting.getEObject();
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Manifestation_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) setting.getEObject();
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
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
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_CommunicationPath_Edge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof CommunicationPath) {
				continue;
			}
			CommunicationPath link = (CommunicationPath) setting.getEObject();
			if (!CommunicationPathEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.CommunicationPath_Edge, CommunicationPathEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Deployment_Edge(NamedElement source) {
		DeploymentTarget container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof DeploymentTarget) {
				container = (DeploymentTarget) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getDeployments()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) linkObject;
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Manifestation_Edge(NamedElement source) {
		Artifact container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Artifact) {
				container = (Artifact) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getManifestations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) linkObject;
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Generalization_Edge(Classifier source) {
		Classifier container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Classifier) {
				container = (Classifier) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
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
			Classifier src = link.getSpecific();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_CommunicationPath_Edge(Type source) {
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
			if (false == linkObject instanceof CommunicationPath) {
				continue;
			}
			CommunicationPath link = (CommunicationPath) linkObject;
			if (!CommunicationPathEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.CommunicationPath_Edge, CommunicationPathEditPart.VISUAL_ID));
		}
		return result;
	}
}
