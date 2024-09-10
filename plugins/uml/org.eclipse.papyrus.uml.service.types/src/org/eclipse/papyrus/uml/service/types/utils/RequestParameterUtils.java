/*****************************************************************************
 * Copyright (c) 2012, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 530201, 570542
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Lifeline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * Utility class for request parameters.
 */
public class RequestParameterUtils {

	// Locally re-declare the deprecated request parameter to avoid deprecation warnings
	@Deprecated
	private static final String COVERED_REQUEST_PARAMETER = SequenceRequestConstant.COVERED;

	/**
	 * Get the source graphical view provided as {@link IEditCommandRequest} parameter.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the source view
	 */
	public static View getSourceView(IEditCommandRequest req) {
		return (View) req.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW);
	}

	/**
	 * Get the target graphical view provided as {@link IEditCommandRequest} parameter.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the target view
	 */
	public static View getTargetView(IEditCommandRequest req) {
		return (View) req.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW);
	}

	/**
	 * Get the reconnected graphical edge provided as {@link IEditCommandRequest} parameter.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the reconnected edge
	 */
	public static Edge getReconnectedEdge(IEditCommandRequest req) {
		return (Edge) req.getParameter(RequestParameterConstants.GRAPHICAL_RECONNECTED_EDGE);
	}

	/**
	 * Get the reconnected graphical end view provided as {@link IEditCommandRequest} parameter.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the reconnected end view
	 */
	public static View getReconnectedEndView(IEditCommandRequest req) {
		return (View) req.getParameter(RequestParameterConstants.EDGE_REORIENT_REQUEST_END_VIEW);
	}

	/**
	 * Get the list of dependents that should not be destroyed during delete.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the list of dependents to keep
	 */
	public static List<EObject> getDependentsToKeep(IEditCommandRequest req) {
		return getDependentsToKeep(req, false);
	}

	/**
	 * Get the list of dependents that should not be destroyed during delete, optionally creating
	 * the parameter if it does not already exist. This is useful for clients that will add
	 * objects to the list.
	 *
	 * @param req
	 *            the edit command request
	 * @param create
	 *            whether to create the request parameter if it does not already exist
	 *
	 * @return the list of dependents to keep
	 */
	public static List<EObject> getDependentsToKeep(IEditCommandRequest req, boolean create) {
		@SuppressWarnings("unchecked")
		List<EObject> result = (List<EObject>) req.getParameter(RequestParameterConstants.DEPENDENTS_TO_KEEP);

		if (result == null && create) {
			result = new ArrayList<>();
			req.setParameter(RequestParameterConstants.DEPENDENTS_TO_KEEP, result);
		}

		return result;
	}

	/**
	 * Get the list of association related elements currently re-factored.
	 *
	 * @param req
	 *            the edit command request
	 *
	 * @return the list of association related elements currently re-factored
	 */
	@SuppressWarnings("unchecked")
	public static List<EObject> getAssociationRefactoredElements(IEditCommandRequest req) {
		return (List<EObject>) req.getParameter(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS);
	}

	/**
	 * Add an EObject to the list of association related elements currently re-factored.
	 *
	 * @param eObject
	 *            the eObject to add
	 */
	public static void addAssociationRefactoredElement(IEditCommandRequest req, EObject eObject) {
		List<EObject> refactoredElements = (getAssociationRefactoredElements(req) != null) ? getAssociationRefactoredElements(req) : new ArrayList<>();
		refactoredElements.add(eObject);
		req.setParameter(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS, refactoredElements);
	}

	/**
	 * Obtains the covered lifelines attached to a {@code request}.
	 *
	 * @param request
	 *            an edit request
	 * @return the covered lifelines parameter, or an empty iterable if none
	 * @since 4.0
	 * @see SequenceRequestConstant#COVERED_LIFELINES
	 * @see #setCoveredLifelines(IEditCommandRequest, Iterable)
	 */
	public static Iterable<Lifeline> getCoveredLifelines(IEditCommandRequest request) {
		Iterable<Lifeline> result = Collections.emptySet();
		Object parameterValue = request.getParameter(SequenceRequestConstant.COVERED_LIFELINES);
		if (parameterValue instanceof Iterable<?>) {
			result = Iterables.filter((Iterable<?>) parameterValue, Lifeline.class);
		} else if (request.getParameter(COVERED_REQUEST_PARAMETER) instanceof Lifeline) {
			// Backward compatibility for clients using the deprecated parameter
			result = Collections.singleton((Lifeline) request.getParameter(COVERED_REQUEST_PARAMETER));
		}
		return result;
	}

	/**
	 * Sets the covered lifelines attached to a {@code request}.
	 *
	 * @param request
	 *            an edit request
	 * @param coveredLifelines
	 *            the covered lifelines parameter value
	 * @since 4.0
	 * @see SequenceRequestConstant#COVERED_LIFELINES
	 * @see #getCoveredLifelines(IEditCommandRequest)
	 */
	public static void setCoveredLifelines(IEditCommandRequest request, Iterable<Lifeline> coveredLifelines) {
		request.setParameter(SequenceRequestConstant.COVERED_LIFELINES, ImmutableSet.copyOf(coveredLifelines));
	}
}
