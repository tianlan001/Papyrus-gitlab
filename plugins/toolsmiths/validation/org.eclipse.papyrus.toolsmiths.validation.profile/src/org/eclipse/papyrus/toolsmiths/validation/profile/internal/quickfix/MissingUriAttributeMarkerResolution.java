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
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;

/**
 * Resolution for markers created for missing genModel attributes in the extension point.
 *
 */
public class MissingUriAttributeMarkerResolution
		extends AbstractMissingAttributeMarkerResolution {

	MissingUriAttributeMarkerResolution() {
		super(ProfilePluginValidationConstants.NO_URI_MARKER_ID, "uri"); //$NON-NLS-1$
	}

	@Override
	public String getLabel() {
		return Messages.MissingUriAttributeMarkerResolution_label;
	}

	@Override
	public String getDescription() {
		return Messages.MissingUriAttributeMarkerResolution_description;
	}

	@Override
	protected String getAttributeValue(IMarker marker) {
		return marker.getAttribute(ProfilePluginValidationConstants.STATIC_PROFILE_STEREOTYPE_URI, ""); //$NON-NLS-1$
	}
}
