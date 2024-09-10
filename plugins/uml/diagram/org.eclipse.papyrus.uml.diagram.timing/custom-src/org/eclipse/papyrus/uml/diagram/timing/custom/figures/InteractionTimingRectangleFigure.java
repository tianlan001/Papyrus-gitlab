/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available unfer the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Atos Origin - Initial API and implementation
 * Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.draw2d.InteractionFigure;
import org.eclipse.papyrus.uml.diagram.common.draw2d.LeftToolbarLayout;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

public class InteractionTimingRectangleFigure extends RoundedCompartmentFigure {

	protected InteractionFigure interactionLabelContainer;

	protected RectangleFigure interactionContentPane;

	protected PapyrusWrappingLabel interactionLabel;

	private boolean myUseLocalCoordinates = false;

	/**
	 * @param marginBorder
	 *
	 */
	public InteractionTimingRectangleFigure() {
		setLayoutManager(new BorderLayout());
		createContents();
	}


	protected void createContents() {
		add(createHeader(), BorderLayout.TOP);
		add(createContentPane(), BorderLayout.CENTER);
	}

	protected RectangleFigure createContentPane() {
		interactionContentPane = new RectangleFigure();
		// Margin allow to add message easily
		interactionContentPane.setBorder(new MarginBorder(5, 5, 5, 5));
		interactionContentPane.setFill(false);
		interactionContentPane.setOutline(true);

		return interactionContentPane;
	}

	protected RectangleFigure createHeader() {
		interactionLabel = new PapyrusWrappingLabel();

		interactionLabelContainer = new InteractionFigure();
		interactionLabelContainer.setBorder(new MarginBorder(3, 3, 3, 3));
		interactionLabelContainer.setLayoutManager(new LeftToolbarLayout());

		interactionLabelContainer.add(interactionLabel);

		RectangleFigure container = new RectangleFigure();
		container.setOutline(false);
		container.setFill(false);
		container.setLayoutManager(new ToolbarLayout(false));
		container.add(interactionLabelContainer, OrderedLayout.ALIGN_CENTER);
		return container;
	}

	/**
	 * @generated
	 */
	@Override
	protected boolean useLocalCoordinates() {
		return myUseLocalCoordinates;
	}

	/**
	 * @generated
	 */
	protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
		myUseLocalCoordinates = useLocalCoordinates;
	}

	/**
	 * @generated
	 */
	public WrappingLabel getHeaderLabel() {
		return interactionLabel;
	}

	public RectangleFigure getCompartmentFigure() {
		return interactionContentPane;
	}

}
