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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.profile.Activator;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;

/**
 * Resolution for markers created for missing genModel attributes in an extension point.
 *
 */
public class MissingGenModelAttributeMarkerResolution
		extends AbstractMissingAttributeMarkerResolution {

	MissingGenModelAttributeMarkerResolution() {
		super(ProfilePluginValidationConstants.NO_GENMODEL_MARKER_ID, "genModel"); //$NON-NLS-1$
	}

	@Override
	public String getLabel() {
		return Messages.MissingGenModelAttributeMarkerResolution_label;
	}

	@Override
	public String getDescription() {
		return Messages.MissingGenModelAttributeMarkerResolution_description;
	}

	@Override
	protected String getAttributeValue(IMarker marker) {
		try {
			IFile genModelFile = ProfileMarkerResolutionUtils.getGenModelFile(marker);
			if (genModelFile != null) {
				return ResourceUtils.getStringURI(genModelFile.getProjectRelativePath());
			}
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return null;
	}
}
