/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.util;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.image.PartPositionInfo;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;
import org.eclipse.papyrus.uml.export.svgextension.PapyrusCopyToImageUtil;

/**
 * The Class ImageUtil.
 */
public class ImageUtil {

	/**
	 * Re factored generate img.
	 *
	 * @param path the path
	 * @param diagram the diagram
	 * @param annotations the annotations
	 * @return the list
	 */
	public static List<PartPositionInfo> reFactoredgenerateImg(Path path, Diagram diagram, 	List<AnnotateSVG> annotations ) {
		if (Activator.getDefault().isDebugging()) {
			Activator.debug("Start generating svg for diagram " + diagram.getName() + " in "  + path); //$NON-NLS-1$
		}
		PapyrusCopyToImageUtil copyToImageUtil = new PapyrusCopyToImageUtil(annotations);
		String diagramNaming = HTMLUtil.diagramRelativPath(diagram);
		IPath append = path.append(diagramNaming+"."+ImageFileFormat.SVG.toString());
		return generateImg(append, copyToImageUtil, diagram, ImageFileFormat.SVG);
	}


	/**
	 * Generate img.
	 *
	 * @param imagePath the image path
	 * @param copyImageUtil the copy image util
	 * @param currentDiagram the current diagram
	 * @param fileFormat the file format
	 * @return the list
	 */
	public static List<PartPositionInfo> generateImg(IPath imagePath, PapyrusCopyToImageUtil copyImageUtil, Diagram currentDiagram, ImageFileFormat fileFormat) {

		Activator.log(IStatus.INFO, "Generate image to:"+imagePath.toString());
		
		File file = imagePath.toFile();
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			root.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Activator.log(e);
		}	
		List<PartPositionInfo> copyToImage = Collections.emptyList();
		try {
			copyToImage = copyImageUtil.copyToImage(currentDiagram, imagePath, fileFormat, new NullProgressMonitor(),PreferencesHint.USE_DEFAULTS);
		} catch (CoreException e) {
			Activator.log(e);// Exception present when running in Project EXLPORE
		}
		
		return copyToImage;

	}

}
