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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.RepresentationQuerier;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.edges.IDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.reconnect.ElementDomainBasedEdgeReconnectSourceBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.reconnect.ElementDomainBasedEdgeReconnectTargetBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.reconnect.ElementDomainBasedEdgeReconnectionSourceChecker;
import org.eclipse.papyrus.uml.domain.services.reconnect.ElementDomainBasedEdgeReconnectionTargetChecker;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.tool.CreateEdgeView;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

/**
 * Services used to reconnect edges.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class ReconnectServices extends AbstractDiagramServices {

	/**
	 * Gets the semantic source of domain base edge from its semantic element.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @param oldSource
	 *            the previous source before the reconnect.
	 * @param newSource
	 *            the new source after the reconnect.
	 * @param newSourceView
	 *            the new source view after the reconnect.
	 * @return a source element or <code>null</code>
	 */
	public void reconnectSource(EObject semanticElementEdge, EObject oldSource, EObject newSource, DSemanticDecorator newSourceView) {
		RepresentationQuerier representationQuery = createRepresentationQuerier(newSourceView);
		CheckStatus reconnectSourceStatus = new ElementDomainBasedEdgeReconnectSourceBehaviorProvider(new EditableChecker(), representationQuery).reconnectSource(semanticElementEdge, oldSource, newSource, newSourceView);
		if (reconnectSourceStatus.isValid()) {
			if (isCompartment(newSourceView)) {
				// in case of compartment, edge view is not created by the tool
				final Session session = SessionManager.INSTANCE.getSession(semanticElementEdge);

				// Get all available mappings applicable for the semantic element in the
				// current container
				DDiagram containerView = ((DDiagramElement) newSourceView).getParentDiagram();
				final List<DiagramElementMapping> semanticElementMappings = getMappings(semanticElementEdge, (DSemanticDecorator) containerView, session);

				// Build a createEdgeView tool
				final CreateEdgeView createEdgeViewOp = ToolFactory.eINSTANCE.createCreateEdgeView();
				for (final DiagramElementMapping semanticElementMapping : semanticElementMappings) {
					if (semanticElementMapping instanceof EdgeMapping) {
						final DiagramElementMapping tmpSemanticElementMapping = semanticElementMapping;
						createEdgeViewOp.setMapping(tmpSemanticElementMapping);
						createEdgeViewOp.setContainerViewExpression("aql:sourceView.eContainer(diagram::DDiagram)"); //$NON-NLS-1$
						createEdgeViewOp.setSourceExpression("aql:target"); //$NON-NLS-1$
						createEdgeViewOp.setTargetExpression("aql:element.getTargets()->first()"); //$NON-NLS-1$

						executeCreateViewOperation(semanticElementEdge, session, (DSemanticDecorator) containerView, createEdgeViewOp);
					}
				}
			}
		}
	}

	/**
	 * Check if the domain based edge can reconnect with the new source.
	 * 
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * 
	 * @param edgeView
	 *            the graphical edge view.
	 *
	 * @param oldSource
	 *            the old source of the domain based edge
	 * @param newSource
	 *            the new source of the domain based edge
	 * @param newSourceView
	 *            the new source view of the domain based edge
	 * @return true if the source can be reconnected, false otherwise.
	 */
	public boolean canReconnectSource(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldSource, EObject newSource, DSemanticDecorator newSourceView) {
		return this.canReconnectSource(semanticElementEdge, edgeView, oldSource, newSource, newSourceView, new ElementDomainBasedEdgeContainerProvider(new EditableChecker()));
	}


	/**
	 * Check if the domain based edge can reconnect with the new source.
	 * 
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * 
	 * @param edgeView
	 *            the graphical edge view.
	 *
	 * @param oldSource
	 *            the old source of the domain based edge
	 * @param newSource
	 *            the new source of the domain based edge
	 * @param newSourceView
	 *            the new source view of the domain based edge
	 * @param domainBasedEdgeContainerProvider
	 *            the {@link IDomainBasedEdgeContainerProvider} implementation.
	 * @return true if the source can be reconnected, false otherwise.
	 */
	public boolean canReconnectSource(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldSource, EObject newSource, DSemanticDecorator newSourceView, IDomainBasedEdgeContainerProvider domainBasedEdgeContainerProvider) {
		RepresentationQuerier representationQuery = createRepresentationQuerier(newSourceView);
		Optional<EdgeTarget> optionalTargetView = Optional.ofNullable(edgeView)
				.filter(DEdge.class::isInstance)
				.map(DEdge.class::cast)
				.map(DEdge::getTargetNode);
		// @formatter:on
		CheckStatus canReconnectStatus = new ElementDomainBasedEdgeReconnectionSourceChecker(new EditableChecker(), representationQuery).canReconnect(semanticElementEdge, oldSource, newSource, newSourceView, optionalTargetView.orElse(null));
		boolean canReconnectSource = canReconnectStatus.isValid();
		if (canReconnectSource) {
			// check if a container exist
			Optional<EObject> optionalTarget = optionalTargetView
					.filter(DSemanticDecorator.class::isInstance)
					.map(DSemanticDecorator.class::cast)
					.map(DSemanticDecorator::getTarget);
			if (optionalTargetView.isPresent() && optionalTarget.isPresent()) {
				EObject container = domainBasedEdgeContainerProvider.getContainer(newSource, optionalTarget.get(), semanticElementEdge, representationQuery, newSourceView, optionalTargetView.get());
				canReconnectSource = container != null;
			} else {
				canReconnectSource = false;
			}
		}
		return canReconnectSource;
	}

	/**
	 * Gets the semantic source of domain base edge from its semantic element.
	 * 
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @param oldTarget
	 *            the old target of the semantic edge.
	 * @param newTarget
	 *            the new target of the semantic edge.
	 * @param newTargetView
	 *            the new target view.
	 * @return a source element or <code>null</code>
	 */
	public void reconnectTarget(EObject semanticElementEdge, EObject oldTarget, EObject newTarget, DSemanticDecorator newTargetView) {
		RepresentationQuerier representationQuery = createRepresentationQuerier(newTargetView);
		CheckStatus reconnectTargetStatus = new ElementDomainBasedEdgeReconnectTargetBehaviorProvider(representationQuery).reconnectTarget(semanticElementEdge, oldTarget, newTarget, newTargetView);
		if (reconnectTargetStatus.isValid()) {
			if (isCompartment(newTargetView)) {
				// in case of compartment, edge view is not created by the tool
				final Session session = SessionManager.INSTANCE.getSession(semanticElementEdge);

				// Get all available mappings applicable for the semantic element in the
				// current container
				DDiagram containerView = ((DDiagramElement) newTargetView).getParentDiagram();
				final List<DiagramElementMapping> semanticElementMappings = getMappings(semanticElementEdge, (DSemanticDecorator) containerView, session);

				// Build a createEdgeView tool
				final CreateEdgeView createEdgeViewOp = ToolFactory.eINSTANCE.createCreateEdgeView();
				for (final DiagramElementMapping semanticElementMapping : semanticElementMappings) {
					if (semanticElementMapping instanceof EdgeMapping) {
						final DiagramElementMapping tmpSemanticElementMapping = semanticElementMapping;
						createEdgeViewOp.setMapping(tmpSemanticElementMapping);
						createEdgeViewOp.setContainerViewExpression("aql:targetView.eContainer(diagram::DDiagram)"); //$NON-NLS-1$
						createEdgeViewOp.setSourceExpression("aql:element.getSource()"); //$NON-NLS-1$
						createEdgeViewOp.setTargetExpression("aql:target"); //$NON-NLS-1$

						executeCreateViewOperation(semanticElementEdge, session, (DSemanticDecorator) containerView, createEdgeViewOp);
					}
				}
			}
		}
	}

	/**
	 * Check if the domain based edge can reconnect with the new target.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @param oldTarget
	 *            the old target of the domain based edge
	 * @param newTarget
	 *            the new target of the domain based edge
	 * @param newTargetView
	 *            the new target view of the domain based edge
	 * @return true if the target can be reconnected, false otherwise.
	 */
	public boolean canReconnectTarget(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldTarget, EObject newTarget, DSemanticDecorator newTargetView) {
		return this.canReconnectTarget(semanticElementEdge, edgeView, oldTarget, newTarget, newTargetView, new ElementDomainBasedEdgeContainerProvider(new EditableChecker()));
	}

	/**
	 * Check if the domain based edge can reconnect with the new target.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @param oldTarget
	 *            the old target of the domain based edge
	 * @param newTarget
	 *            the new target of the domain based edge
	 * @param newTargetView
	 *            the new target view of the domain based edge
	 * @param domainBasedEdgeContainerProvider
	 *            the {@link IDomainBasedEdgeContainerProvider} implementation.
	 * @return true if the target can be reconnected, false otherwise.
	 */
	public boolean canReconnectTarget(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldTarget, EObject newTarget, DSemanticDecorator newTargetView, IDomainBasedEdgeContainerProvider domainBasedEdgeContainerProvider) {
		// @formatter:off
		RepresentationQuerier representationQuery = createRepresentationQuerier(newTargetView);
		Optional<EdgeTarget> optionalSourceView = Optional.ofNullable(edgeView)
				.filter(DEdge.class::isInstance)
				.map(DEdge.class::cast)
				.map(DEdge::getSourceNode);
		// @formatter:on
		CheckStatus canReconnectStatus = new ElementDomainBasedEdgeReconnectionTargetChecker(new EditableChecker(), representationQuery).canReconnect(semanticElementEdge, oldTarget, newTarget, newTargetView, optionalSourceView.orElse(null));
		boolean canReconnectTarget = canReconnectStatus.isValid();

		if (canReconnectTarget) {
			// check if a container exist
			Optional<EObject> optionalSource = optionalSourceView
					.filter(DSemanticDecorator.class::isInstance)
					.map(DSemanticDecorator.class::cast)
					.map(DSemanticDecorator::getTarget);
			if (optionalSourceView.isPresent() && optionalSource.isPresent()) {
				EObject container = domainBasedEdgeContainerProvider.getContainer(optionalSource.get(), newTarget, semanticElementEdge, representationQuery, optionalSourceView.get(), newTargetView);
				canReconnectTarget = container != null;
			} else {
				canReconnectTarget = false;
			}
		}
		return canReconnectTarget;
	}

	private RepresentationQuerier createRepresentationQuerier(DSemanticDecorator view) {
		Optional<DDiagram> optionalDDiagram = Optional.ofNullable(view)
				.filter(DDiagramElement.class::isInstance)
				.map(DDiagramElement.class::cast)
				.map(DDiagramElement::getParentDiagram);
		RepresentationQuerier representationQuery = new RepresentationQuerier(optionalDDiagram.orElse(null));
		return representationQuery;
	}

}
