/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.draw2d.InteractionFigure;
import org.eclipse.papyrus.uml.diagram.common.draw2d.LeftToolbarLayout;

public class CustomInteractionRectangleFigure extends InteractionTimingRectangleFigure {

	private RectangleFigure timeRulerCompartment;

	public CustomInteractionRectangleFigure() {
		setLayoutManager(new ToolbarLayout());
		createContents();
	}

	@Override
	protected void createContents() {
		add(createInteractionFigureHeader());
		add(createContentPane());
		final int childrenSize = getChildren().size();
		add(createTimeRulerCompartment(), childrenSize);
	}

	protected IFigure createTimeRulerCompartment() {
		return this.timeRulerCompartment = new TimeRulerFigure();
	}

	protected InteractionFigure createInteractionFigureHeader() {
		this.interactionLabel = new PapyrusWrappingLabel() {

			@Override
			public Dimension getPreferredSize(final int wHint, final int hHint) {
				final Dimension preferredSize = super.getPreferredSize(wHint, hHint);
				if (preferredSize.width == 0) {
					return preferredSize;
				}
				return new Dimension(preferredSize.width + 2, preferredSize.height + 2);
			}
		};

		this.interactionLabelContainer = new InteractionFigure();
		this.interactionLabelContainer.setBorder(new MarginBorder(3, 3, 0, 0));
		this.interactionLabelContainer.setLayoutManager(new LeftToolbarLayout());

		this.interactionLabelContainer.add(this.interactionLabel);
		return this.interactionLabelContainer;
	}

	public InteractionFigure getHeaderLabelContainer() {
		return this.interactionLabelContainer;
	}

	public RectangleFigure getTimeRulerContainerFigure() {
		return this.timeRulerCompartment;
	}

}
