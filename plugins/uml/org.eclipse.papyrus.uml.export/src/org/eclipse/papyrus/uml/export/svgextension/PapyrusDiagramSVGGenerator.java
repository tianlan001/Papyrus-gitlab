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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.common.core.util.Log;
import org.eclipse.gmf.runtime.common.core.util.Trace;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.image.PartPositionInfo;
import org.eclipse.gmf.runtime.diagram.ui.l10n.SharedImages;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.DiagramUIRenderDebugOptions;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.DiagramUIRenderPlugin;
import org.eclipse.gmf.runtime.draw2d.ui.render.RenderedImage;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.image.ImageConverter;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;
import org.eclipse.gmf.runtime.draw2d.ui.render.factory.RenderedImageFactory;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.RenderedImageDescriptor;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;
import org.eclipse.papyrus.uml.export.svgextension.internal.OpenAPIDiagramSVGGenerator;
import org.eclipse.uml2.uml.NamedElement;
import org.w3c.dom.Element;

/**
 * The Class PapyrusDiagramSVGGenerator.
 */
public class PapyrusDiagramSVGGenerator extends OpenAPIDiagramSVGGenerator {

	/** The annotations. */
	private List<AnnotateSVG> annotations = new ArrayList<>();

	/**
	 * Instantiates a new papyrus diagram SVG generator.
	 *
	 * @param diagramEditPart
	 *            the diagram edit part
	 * @param annotations
	 *            the annotations
	 */
	public PapyrusDiagramSVGGenerator(DiagramEditPart diagramEditPart, List<AnnotateSVG> annotations) {
		super(diagramEditPart);
		this.annotations = annotations;
	}

	/** The rendered image. */
	private RenderedImage renderedImage = null;

	/** The svg root. */
	private Element svgRoot = null;

	/** The view box. */
	private Rectangle viewBox = null;

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator#setUpGraphics(int, int)
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramGenerator#
	 * setUpGraphics(int, int)
	 */
	@Override
	protected Graphics setUpGraphics(int width, int height) {
		viewBox = new Rectangle(0, 0, width, height);
		return GraphicsSVG.getInstance(viewBox);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator#getImageDescriptor(org.eclipse.draw2d.Graphics)
	 *
	 * @param g
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramGenerator#
	 * getImageDescriptor(org.eclipse.draw2d.Graphics)
	 */
	@Override
	protected ImageDescriptor getImageDescriptor(Graphics g) {
		try {
			Activator.log(IStatus.INFO, "Start transformation from Graphics to image descriptor");

			GraphicsSVG svgG = (GraphicsSVG) g;
			svgRoot = svgG.getRoot();

			//////// Papyrus Specific code //////////
			List<PartPositionInfo> allPartPositionInfo = this.getDiagramPartInfo();

			Collections.reverse(allPartPositionInfo);// Required to have property after class
			for (PartPositionInfo partPositionInfo : allPartPositionInfo) {
				Element rectangle = svgG.getDocument().createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
				if (partPositionInfo.getSemanticElement() instanceof NamedElement) {
					NamedElement nameElement = (NamedElement) partPositionInfo.getSemanticElement();
					rectangle.setAttributeNS(null, "id", nameElement.getName());// useful for svg debug
				}

				rectangle.setAttributeNS(null, "x", String.valueOf(partPositionInfo.getPartX()));
				rectangle.setAttributeNS(null, "y", String.valueOf(partPositionInfo.getPartY()));
				rectangle.setAttributeNS(null, "width", String.valueOf(partPositionInfo.getPartWidth()));
				rectangle.setAttributeNS(null, "height", String.valueOf(partPositionInfo.getPartHeight()));
				rectangle.setAttributeNS(null, "fill-opacity", "0"); // transparent
				rectangle.setAttributeNS(null, "stroke-opacity", "0"); // no border
				View view = partPositionInfo.getView();
				if (view instanceof Shape) { // filter on shape only to avoid duplication

					boolean hasAnnotation = applyAll(view, svgG, rectangle);
					if (hasAnnotation) {
						svgRoot.appendChild(rectangle);
					}
				}

			}

			/////////////////////////


			ByteArrayOutputStream os = new ByteArrayOutputStream(5000); // 5K
																		// buffer
			stream(os);
			os.close();

			setRenderedImage(RenderedImageFactory.getInstance(os.toByteArray()));

			return RenderedImageDescriptor
					.createFromRenderedImage(getRenderedImage());
		} catch (IOException ex) {
			Log.error(DiagramUIRenderPlugin.getInstance(), IStatus.ERROR, ex
					.getMessage(), ex);
		}

		return null;
	}


	/**
	 * Apply all.
	 *
	 * @param view
	 *            the view
	 * @param svgG
	 *            the svg G
	 * @param rectangle
	 *            the rectangle
	 * @return true, if successful
	 */
	public boolean applyAll(View view, GraphicsSVG svgG, Element rectangle) {
		boolean res = false;
		for (AnnotateSVG annotateSVG : annotations) {
			res = res || annotateSVG.addAnnotation(view, svgG, rectangle);
		}
		return true;
	}

	/**
	 * Writes the SVG Model out to a file.
	 *
	 * @param outputStream
	 *            output stream to store the SVG Model
	 */
	@Override
	public void stream(OutputStream outputStream) {
		try {
			// Define the view box
			svgRoot.setAttributeNS(null, "viewBox", String.valueOf(viewBox.x) + " " + //$NON-NLS-1$ //$NON-NLS-2$
					String.valueOf(viewBox.y) + " " + //$NON-NLS-1$
					String.valueOf(viewBox.width) + " " + //$NON-NLS-1$
					String.valueOf(viewBox.height));

			// Write the document to the stream
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$

			DOMSource source = new DOMSource(svgRoot);
			StreamResult result = new StreamResult(outputStream);
			transformer.transform(source, result);
		} catch (Exception ex) {
			Log.error(DiagramUIRenderPlugin.getInstance(), IStatus.ERROR, ex.getMessage(), ex);
		}
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator#createAWTImageForParts(java.util.List, org.eclipse.swt.graphics.Rectangle)
	 *
	 * @param editparts
	 * @param sourceRect
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.internal.clipboard.DiagramGenerator#
	 * createAWTImageForParts(java.util.List)
	 */
	@Override
	public Image createAWTImageForParts(List editparts, org.eclipse.swt.graphics.Rectangle sourceRect) {
		createSWTImageDescriptorForParts(editparts, sourceRect);
		if (getRenderedImage() != null) {
			try {
				BufferedImage bufImg = getRenderedImage().getAdapter(BufferedImage.class);
				if (bufImg == null) {
					bufImg = ImageConverter.convert(getRenderedImage().getSWTImage());
				}
				return bufImg;
			} catch (Error e) {
				// log the Error but allow execution to continue
				Trace.catching(DiagramUIRenderPlugin.getInstance(), DiagramUIRenderDebugOptions.EXCEPTIONS_THROWING,
						getClass(), "createAWTImageForParts() failed to generate image", //$NON-NLS-1$
						e);
				return ImageConverter.convert(SharedImages.get(SharedImages.IMG_ERROR));

			} catch (Exception ex) {
				// log the Exception but allow execution to continue
				Trace.catching(DiagramUIRenderPlugin.getInstance(), DiagramUIRenderDebugOptions.EXCEPTIONS_THROWING,
						getClass(), "createAWTImageForParts() failed to generate image", //$NON-NLS-1$
						ex);
				return ImageConverter.convert(SharedImages.get(SharedImages.IMG_ERROR));
			}
		}

		return ImageConverter.convert(SharedImages.get(SharedImages.IMG_ERROR));
	}

	/**
	 * Gets the rendered image.
	 *
	 * @return Returns the rendered image created by previous call to
	 *         createSWTImageDescriptorForParts
	 */
	@Override
	public RenderedImage getRenderedImage() {
		return renderedImage;
	}

	/**
	 * Sets the rendered image.
	 *
	 * @param renderedImage
	 *            the new rendered image
	 */
	private void setRenderedImage(RenderedImage renderedImage) {
		this.renderedImage = renderedImage;
	}

}
