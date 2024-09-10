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

import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;
import org.eclipse.uml2.uml.Profile;

/**
 * Resolution for markers created for missing profile name attribute in the extension point.
 */
public class PapyrusProfileExtensionMissingNameMarkerResolution extends AbstractMissingAttributeMarkerResolution {

	public PapyrusProfileExtensionMissingNameMarkerResolution() {
		super(ProfilePluginValidationConstants.PAPYRUS_PROFILE_EXTENSION_NO_NAME_MARKER_ID, "name"); //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return Messages.PapyrusProfileExtensionMissingNameMarkerResolution_description;
	}

	@Override
	public String getLabel() {
		return Messages.PapyrusProfileExtensionMissingNameMarkerResolution_label;
	}

	@Override
	protected String getAttributeValue(IMarker marker) {
		Optional<Profile> profileOptional = ProfileMarkerResolutionUtils.getProfile(marker);
		if (profileOptional.isEmpty()) {
			return null;
		}
		Profile profile = profileOptional.get();
		return profile.getName();
	}

}
