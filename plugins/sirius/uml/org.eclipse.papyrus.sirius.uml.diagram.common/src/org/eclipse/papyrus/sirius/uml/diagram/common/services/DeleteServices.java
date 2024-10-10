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
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.uml.domain.services.destroy.DestroyerStatus;
import org.eclipse.papyrus.uml.domain.services.destroy.ElementDestroyer;
import org.eclipse.papyrus.uml.domain.services.destroy.IDestroyer;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;

/**
 * Services used to delete elements.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DeleteServices {


	/**
	 * Used to delete an UML element by invoking papyrus uml deletion services.
	 * 
	 * @param selectedObject
	 *            the object to delete
	 * @return <code>true</code> if the element is removed, <code>false</code> otherwise.
	 */
	public boolean delete(EObject objectToRemove) {
		ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
				.getCrossReferenceAdapter(objectToRemove);
		IDestroyer destroyer = ElementDestroyer.buildDefault(crossReferenceAdapter, new EditableChecker());
		DestroyerStatus status = destroyer.destroy(objectToRemove);
		Set<EObject> elements = status.getElements();
		return status.getState() == State.DONE && elements != null && !elements.isEmpty();
	}

	/**
	 * Delete the Link relation from the linkSource (a {@link Comment} or a {@link Constraint}) to an {@link Element}.
	 * 
	 * @param linkSource
	 *            the source {@link Comment} or {@link Constraint}.
	 * @param edgeView
	 *            the link edge view to delete.
	 */
	public void deleteLink(Element linkSource, DEdge edgeView) {
		Optional<Element> optionalSemanticTarget = getSemanticTarget(edgeView);
		if (optionalSemanticTarget.isPresent()) {
			Element semanticTarget = optionalSemanticTarget.get();
			if (linkSource instanceof Constraint) {
				Constraint constraint = (Constraint) linkSource;
				constraint.getConstrainedElements().remove(semanticTarget);
			} else if (linkSource instanceof Comment) {
				Comment comment = (Comment) linkSource;
				comment.getAnnotatedElements().remove(semanticTarget);
			}
		}
	}

	/**
	 * retrieves the target node semantic element from the given edge view (a Sirius {@link DEdge}).
	 * 
	 * @param edgeView
	 *            the edge view from which we want to retrieve the semantic target.
	 * @return an {@link Optional} {@link Element}.
	 */
	private Optional<Element> getSemanticTarget(DEdge edgeView) {
		return Optional.ofNullable(edgeView)
				.map(DEdge::getTargetNode)
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.map(DSemanticDecorator::getTarget)
				.filter(Element.class::isInstance)
				.map(Element.class::cast);
	}
}
