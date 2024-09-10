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
package org.eclipse.papyrus.uml.export.svgextension.internal;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IExpandableFigure;
import org.eclipse.gmf.runtime.diagram.ui.l10n.SharedImages;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.util.DiagramImageUtils;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.graphics.RenderedMapModeGraphics;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.swt.graphics.ImageData;

//This is an "open" version of DiagramSVGGenerator with public method/attribute
public class OpenAPIDiagramSVGGenerator extends DiagramSVGGenerator {

	private Point ltranslateOffset;

	public OpenAPIDiagramSVGGenerator(DiagramEditPart diagramEditPart) {
		super(diagramEditPart);
	}

	public ImageDescriptor openCreateSWTImageDescriptorForParts(List editparts,
			org.eclipse.swt.graphics.Rectangle sourceRect) {

		// initialize imageDesc to the error icon
		ImageDescriptor imageDesc = new ImageDescriptor() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.resource.ImageDescriptor#getImageData()
			 */
			public ImageData getImageData() {
				return SharedImages.get(SharedImages.IMG_ERROR).getImageData();
			}
		};

		Graphics graphics = null;
		try {
			IMapMode mm = getMapMode();

			PrecisionRectangle rect = new PrecisionRectangle();
			rect.setX(sourceRect.x);
			rect.setY(sourceRect.y);
			rect.setWidth(sourceRect.width);
			rect.setHeight(sourceRect.height);

			mm.LPtoDP(rect);

			// Create the graphics and wrap it with the HiMetric graphics object
			graphics = setUpGraphics((int) Math.round(rect.preciseWidth), (int) Math.round(rect.preciseHeight));

			RenderedMapModeGraphics mapModeGraphics = new OpenRenderedMapModeGraphics(graphics, getMapMode());

			openRenderToGraphics(mapModeGraphics, new Point(sourceRect.x, sourceRect.y), editparts);
			imageDesc = getImageDescriptor(graphics);
		} finally {
			if (graphics != null)
				disposeGraphics(graphics);
		}

		return imageDesc;
	}
	
	
	public void openRenderToGraphics(Graphics graphics, Point translateOffset, List editparts) {

		this.ltranslateOffset = translateOffset;
		graphics.translate((-translateOffset.x), (-translateOffset.y));
		graphics.pushState();

		List<GraphicalEditPart> connectionsToPaint = new LinkedList<>();

		Map decorations = findDecorations(editparts);		
		
		for (Iterator editPartsItr = editparts.listIterator(); editPartsItr.hasNext();) {
			IGraphicalEditPart editPart = (IGraphicalEditPart) editPartsItr.next();

			// do not paint selected connection part
			if (editPart instanceof ConnectionEditPart) {
				connectionsToPaint.add(editPart);
			} else {
				connectionsToPaint.addAll(findConnectionsToPaint(editPart));
				// paint shape figure
				IFigure figure = editPart.getFigure();

				paintFigure(graphics, figure, editPart);
				paintDecorations(graphics, figure, decorations);
			}
		}

		// paint the connection parts after shape parts paint
		decorations = findDecorations(connectionsToPaint);

		for (Iterator<GraphicalEditPart> connItr = connectionsToPaint.iterator(); connItr.hasNext();) {
			IFigure figure = connItr.next().getFigure();
			paintFigure(graphics, figure);
			paintDecorations(graphics, figure, decorations);
		}
	}



	public IFigure getPrintableLayer() {
		Field f;
		try {
			f = this.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("printableLayer");
			f.setAccessible(true);
			return (IFigure) f.get(this); // IllegalAccessException
		} catch (NoSuchFieldException | SecurityException |IllegalArgumentException | IllegalAccessException e) {
			Activator.log(e);
		}
		return null;
	}

	/**
	 * Collects all connections contained within the given edit part
	 * 
	 * @param editPart
	 *            the container editpart
	 * @return connections within it
	 */
	public Collection<ConnectionEditPart> findConnectionsToPaint(IGraphicalEditPart editPart) {
		/*
		 * Set of node editparts contained within the given editpart
		 */
		HashSet<GraphicalEditPart> editParts = new HashSet<GraphicalEditPart>();

		/*
		 * All connection editparts that have a source contained within the given
		 * editpart
		 */
		HashSet<ConnectionEditPart> connectionEPs = new HashSet<ConnectionEditPart>();

		/*
		 * Connections contained within the given editpart (or just the connections to
		 * paint
		 */
		HashSet<ConnectionEditPart> connectionsToPaint = new HashSet<ConnectionEditPart>();

		/*
		 * Populate the set of node editparts
		 */
		getNestedEditParts(editPart, editParts);

		/*
		 * Populate the set of connections whose source is within the given editpart
		 */
		for (Iterator<GraphicalEditPart> editPartsItr = editParts.iterator(); editPartsItr.hasNext();) {
			connectionEPs.addAll(getAllConnectionsFrom(editPartsItr.next()));
		}

		/*
		 * Create a set of connections constained within the given editpart
		 */
		while (!connectionEPs.isEmpty()) {
			/*
			 * Take the first connection and check whethe there is a path through that
			 * connection that leads to the target contained within the given editpart
			 */
			Stack<ConnectionEditPart> connectionsPath = new Stack<ConnectionEditPart>();
			ConnectionEditPart conn = connectionEPs.iterator().next();
			connectionEPs.remove(conn);
			connectionsPath.add(conn);

			/*
			 * Initialize the target for the current path
			 */
			EditPart target = conn.getTarget();
			while (connectionEPs.contains(target)) {
				/*
				 * If the target end is a connection, check if it's one of the connection's
				 * whose target is a connection and within the given editpart. Append it to the
				 * path if it is. Otherwise check if the target is within the actual connections
				 * or nodes contained within the given editpart
				 */
				ConnectionEditPart targetConn = (ConnectionEditPart) target;
				connectionEPs.remove(targetConn);
				connectionsPath.add(targetConn);

				/*
				 * Update the target for the new path
				 */
				target = targetConn.getTarget();
			}

			/*
			 * The path is built, check if it's target is a node or a connection contained
			 * within the given editpart
			 */
			if (editParts.contains(target) || connectionsToPaint.contains(target)) {
				connectionsToPaint.addAll(connectionsPath);
			}
		}
		return connectionsToPaint;
	}

	/**
	 * This method is used when a figure needs to be painted to the graphics. The
	 * figure will be translated based on its absolute positioning.
	 * 
	 * @param graphics
	 *            Graphics object to render figure
	 * @param figure
	 *            the figure to be rendered
	 */
	public void paintFigure(Graphics graphics, IFigure figure) {
		
		if (!figure.isVisible() || figure.getBounds().isEmpty())
			return;

		// Calculate the Relative bounds and absolute bounds
		Rectangle relBounds = null;
		if (figure instanceof IExpandableFigure)
			relBounds = ((IExpandableFigure) figure).getExtendedBounds().getCopy();
		else
			relBounds = figure.getBounds().getCopy();

		Rectangle abBounds = relBounds.getCopy();
		DiagramImageUtils.translateTo(abBounds, figure, getPrintableLayer());

		// Calculate the difference
		int transX = abBounds.x - relBounds.x;
		int transY = abBounds.y - relBounds.y;

		// Paint the figure		
		graphics.pushState();
		graphics.translate(transX, transY);
		figure.paint(graphics);
				
		graphics.popState();
		graphics.restoreState();
	}

	public void paintFigure(Graphics graphics, IFigure figure, IGraphicalEditPart editPart) {
		
		if (!figure.isVisible() || figure.getBounds().isEmpty())
			return;

		// Calculate the Relative bounds and absolute bounds
		Rectangle relBounds = null;
		if (figure instanceof IExpandableFigure)
			relBounds = ((IExpandableFigure) figure).getExtendedBounds().getCopy();
		else
			relBounds = figure.getBounds().getCopy();

		Rectangle abBounds = relBounds.getCopy();
		DiagramImageUtils.translateTo(abBounds, figure, getPrintableLayer());

		// Calculate the difference
		int transX = abBounds.x - relBounds.x;
		int transY = abBounds.y - relBounds.y;

		// Paint the figure		
		graphics.pushState();
		graphics.translate(transX, transY);
		figure.paint(graphics);
				
		graphics.popState();
		graphics.restoreState();
	}	
	
	
	/**
	 * Find the decorations that adorn the specified <code>editParts</code>.
	 * 
	 * @param editparts
	 *            the list of <code>IGraphicalEditParts</code> for which to find
	 *            decorations
	 * @return a mapping of {@link IFigure}to ({@link Decoration}or
	 *         {@link Collection}of decorations})
	 */
	public Map findDecorations(Collection editparts) {
		// create inverse mapping of figures to edit parts (need this to map
		// decorations to edit parts)
		Map figureMap = mapFiguresToEditParts(editparts);

		Map result = new java.util.HashMap();

		if (!editparts.isEmpty()) {
			IGraphicalEditPart first = (IGraphicalEditPart) editparts.iterator().next();

			IFigure decorationLayer = LayerManager.Helper.find(first)
					.getLayer(DiagramRootEditPart.DECORATION_PRINTABLE_LAYER);

			if (decorationLayer != null) {
				// compute the figures of the shapes
				List figures = new java.util.ArrayList(editparts);
				for (ListIterator iter = figures.listIterator(); iter.hasNext();) {
					iter.set(((IGraphicalEditPart) iter.next()).getFigure());
				}

				// find the decorations on figures that were selected
				for (Iterator iter = decorationLayer.getChildren().iterator(); iter.hasNext();) {
					Object next = iter.next();

					if (next instanceof Decoration) {
						Decoration decoration = (Decoration) next;
						IFigure owner = decoration.getOwnerFigure();

						while (owner != null) {
							if (figureMap.containsKey(owner)) {
								Object existing = result.get(owner);

								if (existing == null) {
									result.put(owner, decoration);
								} else if (existing instanceof Collection) {
									((Collection) existing).add(decoration);
								} else {
									Collection c = new java.util.ArrayList(2);
									c.add(existing);
									c.add(decoration);
									result.put(owner, c);
								}
								break;
							} else {
								owner = owner.getParent();
							}
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Constructs a mapping of figures to their corresponding edit parts.
	 * 
	 * @param editParts
	 *            a collection of <code>IGraphicalEditParts</code>
	 * @return a mapping of {@link IFigure}to {@link IGraphicalEditPart}
	 */
	public Map mapFiguresToEditParts(Collection editParts) {
		Map result = new java.util.HashMap();

		for (Iterator iter = editParts.iterator(); iter.hasNext();) {
			IGraphicalEditPart next = (IGraphicalEditPart) iter.next();

			result.put(next.getFigure(), next);
		}

		return result;
	}

	/**
	 * This method is used to obtain the list of child edit parts for shape
	 * compartments.
	 * 
	 * @param childEditPart
	 *            base edit part to get the list of children editparts
	 * @param editParts
	 *            list of nested shape edit parts
	 */
	public void getNestedEditParts(IGraphicalEditPart childEditPart, Collection editParts) {

		for (Iterator iter = childEditPart.getChildren().iterator(); iter.hasNext();) {

			IGraphicalEditPart child = (IGraphicalEditPart) iter.next();
			editParts.add(child);
			getNestedEditParts(child, editParts);
		}
	}

	/**
	 * Paints the decorations adorning the specified <code>figure</code>, if any.
	 * 
	 * @param graphics
	 *            the graphics to paint on
	 * @param figure
	 *            the figure
	 * @param decorations
	 *            mapping of figures to decorations, in which we will find the
	 *            <code>figure</code>'s decorations
	 */
	public void paintDecorations(Graphics graphics, IFigure figure, Map decorations) {
		Object decoration = decorations.get(figure);

		if (decoration != null) {
			if (decoration instanceof Collection) {
				for (Iterator iter = ((Collection) decoration).iterator(); iter.hasNext();) {
					paintFigure(graphics, (IFigure) iter.next());
				}
			} else {
				paintFigure(graphics, (IFigure) decoration);
			}
		}
	}

	/**
	 * Returns all connections orginating from a given editpart. All means that
	 * connections originating from connections that have a source given editpart
	 * will be included
	 * 
	 * @param ep
	 *            the editpart
	 * @return all source connections
	 */
	public List<ConnectionEditPart> getAllConnectionsFrom(GraphicalEditPart ep) {
		LinkedList<ConnectionEditPart> connections = new LinkedList<>();
		for (Iterator itr = ep.getSourceConnections().iterator(); itr.hasNext();) {
			ConnectionEditPart sourceConn = (ConnectionEditPart) itr.next();
			connections.add(sourceConn);
			connections.addAll(getAllConnectionsFrom(sourceConn));
		}
		return connections;
	}
}
