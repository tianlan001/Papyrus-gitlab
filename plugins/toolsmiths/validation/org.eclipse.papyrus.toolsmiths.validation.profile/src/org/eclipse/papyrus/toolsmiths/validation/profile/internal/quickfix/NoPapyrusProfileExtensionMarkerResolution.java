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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.uml2.uml.Profile;

/**
 * Resolution that adds an entry for the 'org.eclipse.papyrus.uml.extensionpoints.UMLProfile' extension and configures it.
 */
public class NoPapyrusProfileExtensionMarkerResolution extends AbstractMissingExtensionMarkerResolution {

	public NoPapyrusProfileExtensionMarkerResolution() {
		super(ProfilePluginValidationConstants.NO_PAPYRUS_PROFILE_MARKER_ID);
	}

	@Override
	public String getDescription() {
		return Messages.NoPapyrusProfileExtensionMarkerResolution_description;
	}

	@Override
	public String getLabel() {
		return Messages.NoPapyrusProfileExtensionMarkerResolution_label;
	}

	@Override
	protected String getExtensionPoint(IMarker marker) {
		return ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT;
	}

	@Override
	protected void configureExtension(IPluginExtension extension, IMarker marker) throws CoreException {
		IPluginElement packageElement = createElement(extension, "profile"); //$NON-NLS-1$

		Optional<Profile> profileOptional = ProfileMarkerResolutionUtils.getProfile(marker);
		if (profileOptional.isEmpty()) {
			return;
		}
		Profile profile = profileOptional.get();
		packageElement.setAttribute("name", profile.getName()); //$NON-NLS-1$
		IFile modelFile = ProfileMarkerResolutionUtils.getUMLModelFile(marker);
		packageElement.setAttribute("path", ResourceUtils.mapAndEncodePath(modelFile)); //$NON-NLS-1$
	}

}
