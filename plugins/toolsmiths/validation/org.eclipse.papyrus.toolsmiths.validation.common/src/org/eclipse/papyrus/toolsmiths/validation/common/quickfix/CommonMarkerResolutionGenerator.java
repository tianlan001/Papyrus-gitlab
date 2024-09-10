/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alexandra Buzila (EclipseSource) - Initial API and implementation
 *   Christian W. Damus - bugs 570097, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

import com.google.common.collect.Iterables;

/**
 * Resolution generator for markers created by the common plug-in builders validation
 * framework: manifest problems, build spec problems, and common <tt>plugin.xml</tt> problems.
 */
@SuppressWarnings("restriction")
public class CommonMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {
	private static IMarkerResolution[] NO_RESOLUTIONS = new IMarkerResolution[0];

	public CommonMarkerResolutionGenerator() {
		super();
	}

	protected final IMarkerResolution[] noResolutions() {
		return NO_RESOLUTIONS;
	}

	protected final IMarkerResolution[] only(IMarkerResolution resolution) {
		return new IMarkerResolution[] { resolution };
	}

	protected final IMarkerResolution[] maybe(Optional<? extends IMarkerResolution> resolution) {
		return resolution.map(res -> new IMarkerResolution[] { res }).orElse(NO_RESOLUTIONS);
	}

	protected final IMarkerResolution[] allOf(IMarkerResolution... resolutions) {
		return resolutions;
	}

	protected final IMarkerResolution[] allOf(Iterable<? extends IMarkerResolution> resolutions) {
		return Iterables.toArray(Iterables.filter(resolutions, Objects::nonNull), IMarkerResolution.class);
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		int problemID = marker.getAttribute(PDEMarkerFactory.PROBLEM_ID, PDEMarkerFactory.NO_RESOLUTION);
		switch (problemID) {
		case CommonProblemConstants.MISSING_FROM_BINARY_BUILD_MARKER_ID:
			return ResourceMissingFromBinaryBuildMarkerResolution.forMarker(marker);
		case CommonProblemConstants.MISSING_DEPENDENCIES_MARKER_ID:
			return new IMarkerResolution[] { new MissingDependenciesMarkerResolution() };
		default:
			return noResolutions();
		}
	}

	/** Get the problem ID (for marker resolution) from a {@code marker}. */
	public static int getProblemID(IMarker marker) {
		return marker.getAttribute(PDEMarkerFactory.PROBLEM_ID, PDEMarkerFactory.NO_RESOLUTION);
	}

	protected final boolean matchProblemID(IMarker marker, int minInclusive, int maxInclusive) {
		int problemID = getProblemID(marker);
		return problemID >= minInclusive && problemID <= maxInclusive;
	}

	protected final boolean hasCommonResolutions(IMarker marker) {
		return matchProblemID(marker, CommonProblemConstants.PROBLEM_ID_BASE, CommonProblemConstants.MAX_PROBLEM_ID);
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return hasCommonResolutions(marker);
	}

}
