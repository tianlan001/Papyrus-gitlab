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
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.quickfix;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Activator;
import org.eclipse.papyrus.toolsmiths.plugin.builder.ManifestBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractPapyrusMarkerResolution;

/**
 * Quick fix to remove 'visibility:=reexport' in required dependencies
 */
public class DependencyReexportMarkerResolution extends AbstractPapyrusMarkerResolution {

	/**
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return "Remove reexport"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 */
	@Override
	public void run(IMarker marker) {
		String dependency = marker.getAttribute(ManifestBuilder.DEPENDENCY_MARKER_ATTRIBUTE, ""); //$NON-NLS-1$
		IResource resource = marker.getResource();
		IProject project = resource != null ? resource.getProject() : null;
		if (project != null) {
			try {
				ManifestEditor me = new ManifestEditor(project);
				me.init();
				me.setRequiredBundleExported(dependency, false);
				me.save();
			} catch (IOException | CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return "Remove 'visibility:=reexport'"; //$NON-NLS-1$
	}

}
