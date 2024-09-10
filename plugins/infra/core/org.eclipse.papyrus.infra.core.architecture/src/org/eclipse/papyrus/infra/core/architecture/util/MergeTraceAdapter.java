/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.architecture.util;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ADElement;

/**
 * Adapter for {@link ADElement}s that provides traceability to the
 * source model elements that they are merged from.
 */
public interface MergeTraceAdapter extends Adapter {

	/** Query the source elements that a given merged element traces to. */
	Collection<? extends ADElement> trace(ADElement mergedElement);

	/** Query whether a given merged element traces to a particular source element. */
	boolean tracesTo(ADElement mergedElement, ADElement sourceElement);

	/**
	 * Trace a merged element to the source element that contributed its value of the given
	 * {@code feature}. In case of a multi-valued feature, this will be any source element
	 * that contributed any values in that feature. <strong>Note</strong> that an empty
	 * result is possible because it could be that no source elements contributed any
	 * values to this {@code feature} (in which case that feature probably is unset).
	 *
	 * @param <T>
	 *            the element type to trace
	 * @param mergedElement
	 *            the element to trace to a contributing source
	 * @param feature
	 *            the feature to trace
	 * @return the source element for that feature
	 */
	<T extends ADElement> T trace(T mergedElement, EStructuralFeature feature);

	/**
	 * Get the merge traces for an {@link object}.
	 *
	 * @param element
	 *            an architecture model object
	 * @return its merge trace adapter, or {@code null} if none
	 */
	static MergeTraceAdapter getMergeTraces(ADElement element) {
		return (MergeTraceAdapter) EcoreUtil.getExistingAdapter(element, MergeTraceAdapter.class);
	}

	/**
	 * Query the source of a merged element that matches it in the given {@code feature}. This will be
	 * the source element that contributed the {@code feature} value to the merge.
	 *
	 * @param <T>
	 *            the element type
	 * @param mergedElement
	 *            an element in the merged domain model
	 * @param feature
	 *            the feature to trace
	 * @return the source element, or the original merged element if no source can be determined (in which
	 *         case this element is assumed to be a free-standing, effectively unmerged element)
	 */
	static <T extends ADElement> T getSource(T mergedElement, EStructuralFeature feature) {
		return Optional.ofNullable(getMergeTraces(mergedElement))
				.map(traces -> traces.trace(mergedElement, feature))
				.orElse(mergedElement);
	}

}
