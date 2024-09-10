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
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.ui.IMarkerResolution;

/**
 * Resolution generator for markers created by the validation of static profiles.
 *
 */
public class StaticProfileMarkerResolutionGenerator extends CommonMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (hasCommonResolutions(marker)) {
			return super.getResolutions(marker);
		}

		int problemId = getProblemID(marker);
		switch (problemId) {
		case ProfilePluginValidationConstants.NO_GENMODEL_MARKER_ID:
			return new IMarkerResolution[] { new MissingGenModelAttributeMarkerResolution() };
		case ProfilePluginValidationConstants.NO_URI_MARKER_ID:
			return new IMarkerResolution[] { new MissingUriAttributeMarkerResolution() };
		case ProfilePluginValidationConstants.NO_ECORE_GEN_PACKAGE_MARKER_ID:
			return new IMarkerResolution[] { new NoEcoreGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_MARKER_ID:
			return new IMarkerResolution[] { new NoUMLGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_LOCATION_MARKER_ID:
			return new IMarkerResolution[] { new NoLocationUMLGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.PAPYRUS_PROFILE_EXTENSION_NO_NAME_MARKER_ID:
			return new IMarkerResolution[] { new PapyrusProfileExtensionMissingNameMarkerResolution() };
		case ProfilePluginValidationConstants.NO_PAPYRUS_PROFILE_MARKER_ID:
			return new IMarkerResolution[] { new NoPapyrusProfileExtensionMarkerResolution() };
		default:
			return noResolutions();
		}
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return super.hasResolutions(marker)
				|| matchProblemID(marker, ProfilePluginValidationConstants.PROBLEM_ID_BASE, ProfilePluginValidationConstants.MAX_PROBLEM_ID);
	}

}
