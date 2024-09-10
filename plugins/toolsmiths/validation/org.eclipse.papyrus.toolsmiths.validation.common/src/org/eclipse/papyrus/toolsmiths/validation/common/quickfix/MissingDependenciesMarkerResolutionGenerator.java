/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.emf.validation.DependencyValidationUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

/**
 * This class is in charge to provide the QuickFix for missing dependencies
 */
public class MissingDependenciesMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (isMissingDependencyProblem(marker)) {
			return new IMarkerResolution[] { new MissingDependenciesMarkerResolution() };
		}

		return new IMarkerResolution[] {};
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return isMissingDependencyProblem(marker);
	}

	static boolean isMissingDependencyProblem(IMarker marker) {
		boolean result = CommonMarkerResolutionGenerator.getProblemID(marker) == CommonProblemConstants.MISSING_DEPENDENCIES_MARKER_ID;
		if (!result) {
			try {
				final String value = (String) marker.getAttribute(DependencyValidationUtils.MISSING_DEPENDENCIES);
				result = value != null && !value.isEmpty();
			} catch (CoreException e) {
				// nothing to do
			}
		}

		return result;
	}

}
