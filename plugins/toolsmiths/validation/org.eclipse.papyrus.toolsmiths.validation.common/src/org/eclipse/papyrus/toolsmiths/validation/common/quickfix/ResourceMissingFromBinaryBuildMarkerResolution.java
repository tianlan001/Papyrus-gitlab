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
 *   alex - Initial API and implementation
 *   Christian W. Damus - bugs 570097, 574690
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants.MISSING_FROM_BINARY_BUILD_MARKER_ID;

import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.eclipse.project.editors.file.BuildEditor;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.ui.IMarkerResolution;

/**
 * Resolution that adds the missing file to the binary build, either itself or by adding the
 * parent folder.
 */
public abstract class ResourceMissingFromBinaryBuildMarkerResolution extends AbstractPapyrusWorkbenchMarkerResolution {

	private ResourceMissingFromBinaryBuildMarkerResolution() {
		super(MISSING_FROM_BINARY_BUILD_MARKER_ID);
	}

	@Override
	public String getDescription() {
		return Messages.ResourceMissingFromBinaryBuildMarkerResolution_description;
	}

	IPath getPathToAdd(IMarker marker) {
		return new Path(marker.getAttribute(CommonProblemConstants.BINARY_BUILD_PATH, "")); //$NON-NLS-1$
	}

	@Override
	public void run(IMarker marker) {
		String path = getPathToAdd(marker).toPortableString();

		BuildEditor buildEditor = new BuildEditor(marker.getResource().getProject());
		buildEditor.init();
		buildEditor.addToBuild(path);
		buildEditor.save();
	}

	/**
	 * Create one or more resolutions to add the missing resource to the binary build. An additional resolution
	 * is produced for the folder containing the resource if it is in a folder and not in the project root.
	 *
	 * @param marker
	 *            the marker for which to create resolutions
	 * @return the resolutions
	 */
	public static IMarkerResolution[] forMarker(IMarker marker) {
		IPath binaryBuildPath = new Path(marker.getAttribute(CommonProblemConstants.BINARY_BUILD_PATH, "")); //$NON-NLS-1$

		switch (binaryBuildPath.segmentCount()) {
		case 0:
			// This should not happen
			return new IMarkerResolution[0];
		case 1:
			return new IMarkerResolution[] { new ResourceMissingFromBinaryBuildMarkerResolution.File() };
		default:
			return new IMarkerResolution[] {
					new ResourceMissingFromBinaryBuildMarkerResolution.File(),
					new ResourceMissingFromBinaryBuildMarkerResolution.Folder()
			};
		}
	}

	//
	// Nested types
	//

	/** Resolution adding the file to <tt>build.properties</tt>. */
	private static final class File extends ResourceMissingFromBinaryBuildMarkerResolution {

		@Override
		public String getLabel() {
			return NLS.bind(Messages.ResourceMissingFromBinaryBuildMarkerResolution_label,
					Messages.ResourceMissingFromBinaryBuildMarkerResolution_file);
		}

	}

	/** Resolution adding the file's parent folder to <tt>build.properties</tt>. */
	private static final class Folder extends ResourceMissingFromBinaryBuildMarkerResolution {

		@Override
		public String getLabel() {
			return NLS.bind(Messages.ResourceMissingFromBinaryBuildMarkerResolution_label,
					Messages.ResourceMissingFromBinaryBuildMarkerResolution_folder);
		}

		@Override
		IPath getPathToAdd(IMarker marker) {
			// The path in the marker is is project relative, so take only the first segment for
			// the top folder within the project
			return super.getPathToAdd(marker).uptoSegment(1).addTrailingSeparator();
		}

		// Only those that are in a folder (not the project root)
		@Override
		protected Stream<IMarker> findSimilarMarkers(IMarker[] markers) {
			return super.findSimilarMarkers(markers).filter(m -> getPathToAdd(m).segmentCount() > 0);
		}

	}

}
