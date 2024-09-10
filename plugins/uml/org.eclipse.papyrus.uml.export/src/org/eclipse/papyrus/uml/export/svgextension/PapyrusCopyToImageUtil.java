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
package org.eclipse.papyrus.uml.export.svgextension;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramImageGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.image.ImageExporter;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;

/**
 * The Class PapyrusCopyToImageUtil.
 */
public class PapyrusCopyToImageUtil  extends CopyToImageUtil{

	/** The annotations. */
	private  List<AnnotateSVG> annotations = new ArrayList<>();	

	/**
	 * Instantiates a new papyrus copy to image util.
	 *
	 * @param annotations the annotations
	 */
	public PapyrusCopyToImageUtil(List<AnnotateSVG> annotations) {
		this.annotations = annotations;
	}
	
	

    /**
     * Creates the appropriate <code>DiagramGenerator</code> from <code>DiagramEditPart</code>
     * based on the supplied <code>ImageFileFormat</code>.
     *
     * @param diagramEP diagram editpart
     * @param format image file format
     * @return appropriate diagram generator
     */
	@Override
    protected DiagramGenerator getDiagramGenerator(DiagramEditPart diagramEP, ImageFileFormat format) {
        if (format.equals(ImageFileFormat.SVG) || format.equals(ImageFileFormat.PDF)) {
            return new PapyrusDiagramSVGGenerator(diagramEP, annotations);
        } else {
        	return new DiagramImageGenerator(diagramEP);
        }
    }
    
    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil#copyToImage(org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramGenerator, java.util.List, org.eclipse.swt.graphics.Rectangle, org.eclipse.core.runtime.IPath, org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat, org.eclipse.core.runtime.IProgressMonitor)
     *
     * @param gen
     * @param editParts
     * @param imageRect
     * @param destination
     * @param format
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void copyToImage(DiagramGenerator gen, List editParts,
			org.eclipse.swt.graphics.Rectangle imageRect, IPath destination,
			ImageFileFormat format, IProgressMonitor monitor)
			throws CoreException {
		boolean found = false;
		if (format.equals(ImageFileFormat.SVG)
				|| format.equals(ImageFileFormat.PDF)) {
			((PapyrusDiagramSVGGenerator)gen).openCreateSWTImageDescriptorForParts(editParts, imageRect);
			monitor.worked(1);
			saveToFile(destination, (DiagramSVGGenerator) gen, format, monitor);
			found = true;
		} else if (format.equals(ImageFileFormat.JPEG)
				|| format.equals(ImageFileFormat.PNG)) {

			String exportFormat = ImageExporter.JPEG_FILE;
			if (format.equals(ImageFileFormat.PNG))
				exportFormat = ImageExporter.PNG_FILE;

			java.awt.Image image = gen.createAWTImageForParts(editParts,
					imageRect);
			monitor.worked(1);
			if (image instanceof BufferedImage) {
				ImageExporter.exportToFile(destination, (BufferedImage) image,
						exportFormat, monitor, format.getQuality());
				found = true;
			}
		}

		if (!found) {
			org.eclipse.swt.graphics.Image image = gen.createSWTImageDescriptorForParts(editParts,
					imageRect).createImage();
			monitor.worked(1);
			saveToFile(destination, image, format, monitor);
			image.dispose();
		}
	}

}
