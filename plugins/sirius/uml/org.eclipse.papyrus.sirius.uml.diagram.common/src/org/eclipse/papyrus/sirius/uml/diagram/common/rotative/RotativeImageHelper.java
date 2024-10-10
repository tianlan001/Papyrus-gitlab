/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.rotative;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator;
import org.eclipse.sirius.diagram.CustomStyle;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramBorderNodeEditPart;

/**
 * An utility class for RotativeImages.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class RotativeImageHelper {

	/**
	 * Prefix added to Custom Style Id to identify rotative images.
	 */
	public static final String ROTATIVE_PREFIX = "rotative:"; //$NON-NLS-1$

	/**
	 * The singleton instance.
	 */
	private static RotativeImageHelper instance;

	/**
	 * The constructor used to initialize the singleton.
	 */
	private RotativeImageHelper() {
	}

	/**
	 * Gets the singleton instance for this class.
	 *
	 * @return the singleton instance
	 */
	public static RotativeImageHelper getInstance() {
		if (instance == null) {
			instance = new RotativeImageHelper();
		}
		return instance;
	}

	/**
	 * Used to get the image path using the id of the CustomStyle, which describe the rotative image.
	 *
	 * @param customStyle
	 *            the style containing the id to use
	 * @return the image path
	 */
	public String getImagePath(CustomStyle customStyle) {
		return customStyle.getId().substring(RotativeImageHelper.ROTATIVE_PREFIX.length());
	}

	/**
	 * Used to get the {@link BorderedNodeFigure} associated to the specified editPart.
	 *
	 * @param editPart
	 *            the {@link IGraphicalEditPart} which may be a bordered node edit part
	 * @return the {@link BorderedNodeFigure} associated to the specified editPart
	 */
	public BorderedNodeFigure getBorderedNodeFigure(IGraphicalEditPart editPart) {
		BorderedNodeFigure borderedNodeFigure = null;
		IBorderItemLocator borderItemLocator = this.getBorderItemLocator(editPart);
		if (borderItemLocator != null) {
			if (this.getBorderedShapeEditpart(editPart) != null) {
				IBorderedShapeEditPart borderNodeEditPart = this.getBorderedShapeEditpart(editPart);
				borderedNodeFigure = borderNodeEditPart.getBorderedFigure();
			}
		}
		return borderedNodeFigure;
	}

	private IBorderedShapeEditPart getBorderedShapeEditpart(IGraphicalEditPart editPart) {
		IGraphicalEditPart current = editPart;
		IBorderedShapeEditPart borderedNodeEditPart = null;
		while (current != null && borderedNodeEditPart == null) {
			if (current instanceof AbstractBorderedShapeEditPart) {
				borderedNodeEditPart = (AbstractBorderedShapeEditPart) current;
			}
			EditPart parent = current.getParent();
			if (parent instanceof IGraphicalEditPart) {
				current = (IGraphicalEditPart) parent;
			}
		}
		return borderedNodeEditPart;
	}

	private IBorderItemLocator getBorderItemLocator(EditPart editPart) {
		IBorderItemLocator borderItemLocator = null;
		IDiagramBorderNodeEditPart borderNodeEditPart = this.getDiagramBorderedNodeEditPart(editPart);

		if (borderNodeEditPart instanceof IBorderItemEditPart) {
			borderItemLocator = ((IBorderItemEditPart) borderNodeEditPart).getBorderItemLocator();
		}

		return borderItemLocator;
	}

	private IDiagramBorderNodeEditPart getDiagramBorderedNodeEditPart(EditPart editPart) {
		EditPart current = editPart;
		IDiagramBorderNodeEditPart borderNodeEditPart = null;
		while (current != null && borderNodeEditPart == null) {
			if (current instanceof IDiagramBorderNodeEditPart) {
				borderNodeEditPart = (IDiagramBorderNodeEditPart) current;
			}
			current = current.getParent();
		}
		return borderNodeEditPart;
	}
}
