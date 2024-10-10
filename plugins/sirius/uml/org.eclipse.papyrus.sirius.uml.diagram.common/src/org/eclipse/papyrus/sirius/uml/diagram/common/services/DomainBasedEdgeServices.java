/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.RepresentationQuerier;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.uml.domain.services.create.CreationStatus;
import org.eclipse.papyrus.uml.domain.services.create.ElementBasedEdgeCreator;
import org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer;
import org.eclipse.papyrus.uml.domain.services.create.ElementDomainBasedEdgeCreationChecker;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeInitializer;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeSourceProvider;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeTargetsProvider;
import org.eclipse.papyrus.uml.domain.services.edges.IDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Services used to create domain based edge.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DomainBasedEdgeServices extends AbstractDiagramServices {


	/**
	 * Gets the semantic source of domain base edge from its semantic element.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @return a source element or <code>null</code>
	 */
	public EObject getSource(EObject semanticElementEdge) {
		return new ElementDomainBasedEdgeSourceProvider().getSource(semanticElementEdge);

	}

	/**
	 * Gets the semantic targets of domain base edge from its semantic element.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @return a list of target element
	 */
	public List<? extends EObject> getTargets(EObject semanticElementEdge) {
		return new ElementDomainBasedEdgeTargetsProvider().getTargets(semanticElementEdge);
	}

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	// CHECKSTYLE:OFF All the parameters are required. We could remove source and target and recompute them using the
	// IObjectService. But since it is done in Sirius component it would be a shame
	public boolean canCreateDomainBasedEdge(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		boolean canCreate = true;

		// check if edge can be semantically created
		// @formatter:off
		Optional<DDiagram> optionalDDiagram = Optional.ofNullable(sourceView)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getParentDiagram);
		// @formatter:on
		RepresentationQuerier representationQuery = new RepresentationQuerier(optionalDDiagram.orElse(null));
		CheckStatus canCreateStatus = new ElementDomainBasedEdgeCreationChecker().canCreate(source, target, type, containementReferenceName, representationQuery, sourceView, targetView);
		canCreate = canCreateStatus.isValid();

		if (canCreate) {
			// check if a container exist
			ElementDomainBasedEdgeContainerProvider containerProvider = new ElementDomainBasedEdgeContainerProvider(new EditableChecker());
			EClass eClass = UMLHelper.toEClass(type);
			if (eClass != null) {
				EObject newInstance = UMLFactory.eINSTANCE.create(eClass);
				EObject container = containerProvider.getContainer(source, target, newInstance, representationQuery, sourceView, targetView);
				canCreate = container != null;
			}
		}

		return canCreate;
	}

	/**
	 * Service to check if we can create the given edge type from the given source.
	 *
	 * @param source
	 *            the semantic source
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	public boolean canCreateDomainBasedEdgeFromSource(EObject source, String type, String containementReferenceName, DSemanticDecorator sourceView) {
		// @formatter:off
		Optional<DDiagram> optionalDDiagram = Optional.ofNullable(sourceView)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getParentDiagram);
		// @formatter:on
		RepresentationQuerier representationQuery = new RepresentationQuerier(optionalDDiagram.orElse(null));
		CheckStatus canCreateStatus = new ElementDomainBasedEdgeCreationChecker().canCreateFromSource(source, type, containementReferenceName, representationQuery, sourceView);
		return canCreateStatus.isValid();
	}
	// CHECKSTYLE:ON

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	// CHECKSTYLE:OFF All the parameters are required. We could remove source and target and recompute them using the
	// IObjectService. But since it is done in Sirius component it would be a shame
	public EObject createDomainBasedEdge(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		return createDomainBasedEdge(source, target, type, containementReferenceName, sourceView, targetView, null);
	}
	// CHECKSTYLE:ON

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @param containerProvider
	 *            the container provider used to retrieve container of the edge target,
	 * @return a new element or <code>null</code>
	 */
	// CHECKSTYLE:OFF All the parameters are required. We could remove source and target and recompute them using the
	// IObjectService. But since it is done in Sirius component it would be a shame
	public EObject createDomainBasedEdge(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView, IDomainBasedEdgeContainerProvider containerProvider) {
		// @formatter:off
		Optional<DDiagram> optionalDDiagram = Optional.ofNullable(sourceView)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getParentDiagram);
		// @formatter:on
		RepresentationQuerier representationQuery = new RepresentationQuerier(optionalDDiagram.orElse(null));
		final EObject result;
		if (containerProvider == null) {
			containerProvider = new ElementDomainBasedEdgeContainerProvider(new EditableChecker());
		}
		CreationStatus status = this.buildBasedEdgeCreator(source, containerProvider).createDomainBasedEdge(source, target, type, containementReferenceName, representationQuery, sourceView, targetView);

		result = status.getElement();

		if (status.getState() == State.FAILED) {
			Activator.log.warn("Creation failed : " + status.getMessage()); //$NON-NLS-1$
		} else {
			if (result != null) {
				createAdditionalViews(result, sourceView, targetView);
				final Session session = SessionManager.INSTANCE.getSession(source);
				createEdgeView(result, (DDiagramElement) sourceView, session);
			}
		}

		return result;
	}

	/**
	 * Override this method to create additional views before the edge view creation.
	 * 
	 * @param semanticEdge
	 *            the created semantic edge.
	 * @param sourceView
	 *            the selected sourceView.
	 * @param targetView
	 *            the selected targetView.
	 */
	protected void createAdditionalViews(EObject semanticEdge, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
	}

	// CHECKSTYLE:ON

	/**
	 * Get Domain based edge creator.
	 * 
	 * @param source
	 *            {@link Element} used to get {@link ECrossReferenceAdapter} for inverse reference,
	 * @param containerProvider
	 *            the container provider used to retrieve container of the edge target,
	 * @return Domain based edge creator.
	 */
	private ElementBasedEdgeCreator buildBasedEdgeCreator(EObject source, IDomainBasedEdgeContainerProvider containerProvider) {
		ECrossReferenceAdapter crossRef = this.getECrossReferenceAdapter(source);
		ElementBasedEdgeCreator baseEdgeCreator = new ElementBasedEdgeCreator(containerProvider, new ElementDomainBasedEdgeInitializer(),
				new ElementConfigurer(), new ElementFeatureModifier(crossRef, new EditableChecker()));
		return baseEdgeCreator;
	}
}
