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

import java.util.Optional;

import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;

/**
 * Services related to the Link relation which represented the Annotated feature relation and constrained feature relation.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class LinkRelationServices extends AbstractDiagramServices {

	/**
	 * Sets the link target according to the source type.
	 * 
	 * @param source
	 *            the source UML element (a {@link Comment} or a {@link Constraint}).
	 * @param target
	 *            the UML target {@link Element}.
	 * @param sourceView
	 *            the source view.
	 * @param mappingName
	 *            the name of the edge mapping representing the Link.
	 */
	public void setLinkTarget(Element source, Element target, DSemanticDecorator sourceView, String mappingName) {
		if (source instanceof Comment) {
			((Comment) source).getAnnotatedElements().add(target);
		} else if (source instanceof Constraint) {
			((Constraint) source).getConstrainedElements().add(target);
		}
		final Session session = SessionManager.INSTANCE.getSession(source);
		createRelationBasedEdgeViewFromCreationTool(source, sourceView, session, mappingName);
	}

	/**
	 * Checks whether the Link can be reconnected on the new source.
	 * 
	 * @param newSemanticSource
	 *            the new semantic source.
	 * @param oldSemanticSource
	 *            the previous semantic source.
	 * @return true if the new source type is the same type than the previous source and is a Constraint or a Comment.
	 */
	public boolean canReconnectLinkSource(Element newSemanticSource, Element oldSemanticSource) {
		// We can't reconnect a link from a Comment to a Constraint and vice versa.
		return (newSemanticSource instanceof Constraint || newSemanticSource instanceof Comment) && oldSemanticSource.eClass().equals(newSemanticSource.eClass());
	}

	/**
	 * Reconnects the Link relation based edge source.
	 * 
	 * @param oldSource
	 *            the old source.
	 * @param newSource
	 *            the new connected source.
	 * @param edgeView
	 *            the edge view (used to retrieve the target).
	 */
	public void reconnectLinkSource(Element oldSource, Element newSource, DEdge edgeView) {
		// @formatter:off
		Optional<Element> optionalTarget = Optional.of(edgeView.getTargetNode())
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.map(DSemanticDecorator::getTarget)
				.filter(Element.class::isInstance)
				.map(Element.class::cast);
		// @formatter:on
		if (optionalTarget.isPresent()) {
			Element target = optionalTarget.get();
			if (oldSource instanceof Comment) {
				((Comment) oldSource).getAnnotatedElements().remove(target);
			} else if (oldSource instanceof Constraint) {
				((Constraint) oldSource).getConstrainedElements().remove(target);
			}
			if (newSource instanceof Comment) {
				((Comment) newSource).getAnnotatedElements().add(target);
			} else if (newSource instanceof Constraint) {
				((Constraint) newSource).getConstrainedElements().add(target);
			}
		}
	}

	/**
	 * Reconnects the Link relation based edge target.
	 * 
	 * @param source
	 *            the current source.
	 * @param oldTarget
	 *            the old target.
	 * @param newTarget
	 *            the new target.
	 * @param mappingName
	 *            the name of the corresponding Link mapping.
	 */
	public void reconnectLinkTarget(Element source, DSemanticDecorator sourceView, Element oldTarget, Element newTarget, String mappingName) {
		boolean isReconnected = false;
		if (source instanceof Comment) {
			((Comment) source).getAnnotatedElements().remove(oldTarget);
			((Comment) source).getAnnotatedElements().add(newTarget);
			isReconnected = true;
		} else if (source instanceof Constraint) {
			((Constraint) source).getConstrainedElements().remove(oldTarget);
			((Constraint) source).getConstrainedElements().add(newTarget);
			isReconnected = true;
		}
		if (isReconnected) {
			EObjectQuery query = new EObjectQuery(source);
			Session session = query.getSession();
			if (session != null) {
				// The source does not need to create the view, the sirius reconnection tool handles it well.
				// For the target reconnection, we need to handle the compartment case.
				// Indeed, the reconnect is authorized on a internal container structure (to make it easier for the end user to create or reonnect and edge on a container)
				// but to avoid displaying the edge twice, the edge mapping precondition excludes the internal compartments.
				// Without calling the method, the edge is removed by the refresh after the reconnect if we had selected the internal container structure.
				createReconnectTargetRelationBasedEdgeView(source, sourceView, session, mappingName);
			}
		}
	}

}
