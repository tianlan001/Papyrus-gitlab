/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

public class DiagramNodeFigure extends PapyrusNodeFigure {

	/** figure for external call */
	private static final String ICONS_OBJ16_CALL_GIF = "/icons/obj16/call.gif";

	/**
	 * @deprecated use org.eclipse.papyrus.uml.diagram.common.figure.layout.
	 *             PropertiesCompartmentLayoutManager instead
	 */
	@Deprecated
	private class PropertiesCompatmentLayoutManager extends AbstractLayout {

		/**
		 *
		 * {@inheritDoc}
		 */
		@Override
		protected Dimension calculatePreferredSize(IFigure container, int hint, int hint2) {

			int minimumWith = 0;
			int minimumHeight = 0;
			// display name
			for (int i = 0; i < container.getChildren().size(); i++) {
				minimumHeight = minimumHeight + ((IFigure) container.getChildren().get(i)).getPreferredSize().height;
			}

			return new Dimension(minimumWith, minimumHeight);
		}

		/**
		 *
		 * {@inheritDoc}
		 */
		@Override
		public void layout(IFigure container) {
			List childrenList = container.getChildren();
			for (int i = 0; i < container.getChildren().size(); i++) {
				Rectangle bound = new Rectangle(((IFigure) childrenList.get(i)).getBounds());
				bound.setSize(((IFigure) childrenList.get(i)).getPreferredSize());
				if (i > 0) {
					bound.y = ((IFigure) childrenList.get(i - 1)).getBounds().getBottomLeft().y - 1;
					bound.x = getBounds().x;
					bound.width = container.getBounds().width;

				} else {
					bound.x = container.getBounds().x + 2;
					bound.y = container.getBounds().y + 2;
					bound.width = container.getBounds().width;

				}
				((IFigure) childrenList.get(i)).setBounds(bound);
			}

		}

	}

	protected WrappingLabel iconLabel;

	public DiagramNodeFigure() {
		super();
		FontData[] fontdata2 = { new FontData("Arial", 10, SWT.BOLD) };

		Font font2 = Activator.getFontManager().get(fontdata2);

		this.iconLabel = new WrappingLabel("");
		this.iconLabel.setForegroundColor(ColorConstants.red);
		this.iconLabel.setBackgroundColor(ColorConstants.red);
		this.iconLabel.setFont(font2);
		this.iconLabel.setOpaque(false);
		this.add(this.iconLabel);
		ToolbarLayout toolbarLayout = new ToolbarLayout();
		toolbarLayout.setSpacing(5);
		toolbarLayout.setVertical(false);
		this.setLayoutManager(new PropertiesCompatmentLayoutManager());
	}

	public WrappingLabel getIconContainer() {
		return iconLabel;
	}

	public void setIcon(Image image) {
		Image imageVis = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.ID, ICONS_OBJ16_CALL_GIF);

		// Overlay custom image over base image
		// OverlayVisibilityIcon overlayIcon = new
		// OverlayVisibilityIcon(initialImage, visDesc);
		// image = overlayIcon.getImage();

		getIconContainer().setIcon(image, 0);
		getIconContainer().setIcon(imageVis, 1);
	}

}
