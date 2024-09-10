/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Activator;
import org.eclipse.papyrus.toolsmiths.plugin.builder.ManifestBuilder;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

/**
 * Quick fixes for papyrus manifest errors
 */
public class ManifestResolutionGenerator implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		int kind = marker.getAttribute(ManifestBuilder.KIND_MARKER_ATTRIBUTE, 0);
		if (ManifestBuilder.REEXPORT_KIND == kind) {
			return new IMarkerResolution[] {
					new DependencyReexportMarkerResolution()
			};
		}
		if (kind > ManifestBuilder.REEXPORT_KIND) {
			return new IMarkerResolution[] {
					new DependencyRangeMarkerResolution()
			};
		}
		return new IMarkerResolution[] {};
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 * @return
	 */
	@Override
	public boolean hasResolutions(IMarker marker) {
		try {
			if (marker.getAttribute(ManifestBuilder.DEPENDENCY_MARKER_ATTRIBUTE) != null
					&& marker.getAttribute(ManifestBuilder.KIND_MARKER_ATTRIBUTE) != null) {
				return true;
			}
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return false;
	}

}
