/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.stereotype.edition.internal.edithelper.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Element;

/**
 * Dependents advice for deletion of the applied-stereotype views associated with
 * UML elements being deleted.
 */
public class AppliedStereotypeNotationDependentsAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public AppliedStereotypeNotationDependentsAdvice() {
		super();
	}

	@Override
	protected ICommand getAfterDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = super.getAfterDestroyDependentsCommand(request);

		EObject element = request.getElementToDestroy();
		if (element instanceof Element) {
			Collection<View> appliedStereotypeViews = getAppliedStereotypeViews(element);
			if (!appliedStereotypeViews.isEmpty()) {
				ICommand destroy = request.getDestroyDependentsCommand(appliedStereotypeViews);

				if (result == null) {
					result = destroy;
				} else {
					result = result.compose(destroy);
				}
			}
		}

		return result;
	}

	protected Collection<View> getAppliedStereotypeViews(EObject object) {
		Collection<View> result = null;
		List<View> views = (List<View>) DiagramEditPartsUtil.getEObjectViews(object);
		if (!views.isEmpty()) {
			// Assume three applied-stereotype views for each and on average
			// one and a half child views also to consider
			result = new ArrayList<View>(views.size() * 3 * 3 / 2);

			collectAppliedStereotypeViews(new LinkedList<View>(views), result, StereotypeDisplayUtil.getInstance());
		}


		return (result == null) ? Collections.<View> emptyList() : result;
	}

	@SuppressWarnings("unchecked")
	protected void collectAppliedStereotypeViews(Queue<View> views, Collection<? super View> result, StereotypeDisplayUtil helper) {
		for (View next = views.poll(); next != null; next = views.poll()) {
			Node comment = helper.getStereotypeComment(next);
			if (comment != null) {
				result.add(comment);
				result.addAll(comment.getSourceEdges());
				result.addAll(comment.getTargetEdges());
			}

			// Add its children to the work queue
			views.addAll(next.getChildren());
		}
	}
}
