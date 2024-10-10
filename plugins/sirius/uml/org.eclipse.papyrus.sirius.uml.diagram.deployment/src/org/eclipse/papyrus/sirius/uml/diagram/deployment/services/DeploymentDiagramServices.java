/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.deployment.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * All services related to the Deployment Diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DeploymentDiagramServices extends AbstractDiagramServices {

	private CommonDiagramServices commonDiagramServices;

	/**
	 * Constructor.
	 *
	 */
	public DeploymentDiagramServices() {
		this.commonDiagramServices = new CommonDiagramServices();
	}

	/**
	 * Checks if a {@link Manifestation} can be created between {@code source} and {@code target}.
	 *
	 * @param source
	 *            the source element of the {@link Manifestation}
	 * @param target
	 *            the target element of the {@link Manifestation}
	 * @param sourceView
	 *            the graphical element representing the source of the {@link Manifestation}
	 * @param targetView
	 *            the graphical element representing the target of the {@link Manifestation}
	 * @return {@code true} if the {@link Manifestation} can be created, {@code false} otherwise
	 */
	public boolean canCreateManifestationDD(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		boolean canCreate = false;
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		if (source instanceof Artifact) {
			canCreate = domainBasedEdgeServices.canCreateDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getManifestation().getName(), UMLPackage.eINSTANCE.getArtifact_Manifestation().getName(), sourceView, targetView);
		} else if (source instanceof PackageableElement) {
			canCreate = domainBasedEdgeServices.canCreateDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getManifestation().getName(), UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), sourceView, targetView);
		}
		return canCreate;
	}

	/**
	 * Creates a {@link Manifestation} between {@code source} and {@code target}.
	 *
	 * @param source
	 *            the source element of the {@link Manifestation}
	 * @param target
	 *            the target element of the {@link Manifestation}
	 * @param sourceView
	 *            the graphical element representing the source of the {@link Manifestation}
	 * @param targetView
	 *            the graphical element representing the target of the {@link Manifestation}
	 * @return the created element, or {@code null} if the element wasn't created
	 */
	public EObject createManifestationDD(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		EObject result = null;
		if (source instanceof Artifact) {
			domainBasedEdgeServices.createDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getManifestation().getName(), UMLPackage.eINSTANCE.getArtifact_Manifestation().getName(), sourceView, targetView);
		} else if (source instanceof PackageableElement) {
			domainBasedEdgeServices.createDomainBasedEdge(source, target, UMLPackage.eINSTANCE.getManifestation().getName(), UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), sourceView, targetView);
		}
		return result;
	}


	/**
	 * Provides {@link Artifact} candidates.
	 *
	 * @param container
	 *            the current container in which looking for the devices.
	 * @return the {@link Artifact} list.
	 */
	public List<? extends Artifact> getArtifactCandidates(Element container) {
		Optional<EReference> optionalContainmentFeature = this.getArtifactContainmentFeature(container);
		if (optionalContainmentFeature.isPresent()) {
			return this.retrieveCandidates(Artifact.class, container, optionalContainmentFeature.get());
		}
		return List.of();
	}

	/**
	 * Provides {@link Node} candidates.
	 *
	 * @param container
	 *            the current container in which looking for the nodes.
	 * @return the {@link Node} list.
	 */
	public List<? extends Node> getNodeCandidates(Element container) {
		Optional<EReference> optionalContainmentFeature = this.getNodeContainmentFeature(container);
		if (optionalContainmentFeature.isPresent()) {
			return this.retrieveCandidates(Node.class, container, optionalContainmentFeature.get());
		}
		return List.of();
	}

	/**
	 * Provides {@link Package} candidates.
	 *
	 * @param container
	 *            the current container in which looking for the packages.
	 * @return the {@link Package} list.
	 */
	public List<? extends Package> getPackageCandidates(Element container) {
		if (container instanceof Package) {
			return this.retrieveCandidates(Package.class, container, UMLPackage.eINSTANCE.getPackage_PackagedElement());
		} else {
			return List.of();
		}
	}

	/**
	 * Return whether the {@link Artifact} can be created on the given container.
	 *
	 * @param container
	 *            the container in which the {@link Artifact} should be created.
	 * @param type
	 *            the type of the {@link Artifact} to create: <code>Artifact</code> <code>DeploymentSpecification</code>
	 * @return true if it can be created. False otherwise.
	 */
	public boolean canCreateArtifactDD(Element container, EClass type) {
		Optional<EReference> containmentFeature = this.getArtifactContainmentFeature(container);
		if (containmentFeature.isPresent()) {
			return this.commonDiagramServices.canCreate(container, type, containmentFeature.get());
		}
		return false;
	}

	/**
	 * A service to create an {@link Artifact} in the given {@code container}.
	 *
	 * @param container
	 *            the future container.
	 * @param type
	 *            the type of the {@link Artifact} to create: <code>Artifact</code> <code>DeploymentSpecification</code>
	 * @param targetView
	 *            the selected Graphical container.
	 */
	public void createArtifactDD(Element container, EClass type, DSemanticDecorator targetView) {
		Optional<EReference> optional0CcontainmentFeature = this.getArtifactContainmentFeature(container);
		if (optional0CcontainmentFeature.isPresent()) {
			this.commonDiagramServices.createElement(container, type.getName(), optional0CcontainmentFeature.get().getName(), targetView);
		}
	}

	/**
	 * Return whether the {@link Node} can be created on the given container.
	 *
	 * @param container
	 *            the container in which the {@link Node} should be created.
	 * @param type
	 *            the type of the {@link Node} to create: <code>Node</code>, <code>Device</code>, <code>ExecutionEnvironment</code>
	 * @return true if it can be created. False otherwise.
	 */
	public boolean canCreateNodeDD(Element container, EClass type) {
		Optional<EReference> optionalContainmentFeature = this.getNodeContainmentFeature(container);
		if (optionalContainmentFeature.isPresent()) {
			return this.commonDiagramServices.canCreate(container, type, optionalContainmentFeature.get());
		}
		return false;
	}

	/**
	 * A service to create a {@link Node} in the given {@code container}.
	 *
	 * @param container
	 *            the future container.
	 * @param type
	 *            the type of the {@link Node} to create: <code>Node</code>, <code>Device</code>, <code>ExecutionEnvironment</code>
	 * @param targetView
	 *            the selected Graphical container.
	 */
	public void createNodeDD(Element container, EClass type, DSemanticDecorator targetView) {
		Optional<EReference> containmentFeature = this.getNodeContainmentFeature(container);
		if (containmentFeature.isPresent()) {
			this.commonDiagramServices.createElement(container, type.getName(), containmentFeature.get().getName(), targetView);
		}
	}

	private Optional<EReference> getArtifactContainmentFeature(Element container) {
		Optional<EReference> reference;
		if (container instanceof Package pkg) {
			reference = Optional.of(UMLPackage.eINSTANCE.getPackage_PackagedElement());
		} else if (container instanceof org.eclipse.uml2.uml.Class clazz) {
			reference = Optional.of(UMLPackage.eINSTANCE.getClass_NestedClassifier());
		} else if (container instanceof Artifact artifact) {
			reference = Optional.of(UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
		} else {
			reference = Optional.empty();
		}
		return reference;
	}

	private Optional<EReference> getNodeContainmentFeature(Element container) {
		Optional<EReference> reference;
		if (container instanceof Package pkg) {
			reference = Optional.of(UMLPackage.eINSTANCE.getPackage_PackagedElement());
		} else if (container instanceof Node node) {
			reference = Optional.of(UMLPackage.eINSTANCE.getNode_NestedNode());
		} else {
			reference = Optional.empty();
		}
		return reference;
	}

	private <T> List<? extends T> retrieveCandidates(Class<? extends T> type, Element container, EReference containmentFeature) {
		Stream<?> streamOfCandidates = Optional.ofNullable(container.eGet(containmentFeature))
				.filter(List.class::isInstance)//
				.map(List.class::cast)//
				.stream()//
				.flatMap(List::stream);
		return streamOfCandidates.filter(type::isInstance)//
				.map(type::cast)//
				.toList();
	}
}
