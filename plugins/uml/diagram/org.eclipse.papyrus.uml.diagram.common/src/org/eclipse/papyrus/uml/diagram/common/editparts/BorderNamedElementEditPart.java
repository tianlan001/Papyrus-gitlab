/*****************************************************************************
 * Copyright (c) 2008-2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Céline Janssens (All4TEC) celine.janssens@all4tec.net - Bug 440230 : Margin Label
 *  Mickael ADAM (ALL@TEC) mickael.adam@all4tec.net - bug 462448
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.NameLabelIconHelper;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.uml2.uml.NamedElement;

/**
 * <pre>
 * Abstract EditPart supporting font and icon management of name and qualified name labels.
 * Similar implementation to {@link NamedElementEditPart} but indirectly inheriting from
 * {@link BorderedBorderItemEditPart} in order to provide support for Affixed Child nodes.
 * </pre>
 */
public abstract class BorderNamedElementEditPart extends BorderUMLNodeEditPart implements IUMLNamedElementEditPart {


	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 */
	public BorderNamedElementEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NamedElement getNamedElement() {
		return (NamedElement) getUMLElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);

		if (resolveSemanticElement() != null) {
			refreshIconNamedLabel();
			refreshFontColor();
		}
	}

	/**
	 * Refresh the icon in name label
	 */
	private void refreshIconNamedLabel() {
		getNodeNamedElementFigure().setNameLabelIcon(NameLabelIconHelper.showLabelIcon((View) getModel()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		if ((getNodeNamedElementFigure() != null) && (resolveSemanticElement() != null)) {
			refreshIconNamedLabel();
			refreshFontColor();
		}
		refreshBounds();
	}



	/**
	 * A method to specify the labels to be update when the font is refreshed.
	 * Subclasses should call super.refreshLabelsFont(font)
	 *
	 * @param font
	 *            the font to use
	 */
	@Override
	protected void refreshLabelsFont(Font font) {
		super.refreshLabelsFont(font);

		// Apply the font to the Name Label
		getNodeNamedElementFigure().getNameLabel().setFont(font);

		// Apply the font to the Qualified Name
		WrappingLabel qualifiedNameLabel = getNodeNamedElementFigure().getQualifiedNameLabel();
		if (qualifiedNameLabel != null) {
			qualifiedNameLabel.setFont(font);
		}

		// Apply the font to the tagged Label
		WrappingLabel tagLabel = getNodeNamedElementFigure().getTaggedLabel();
		if (tagLabel != null) {
			tagLabel.setFont(font);
		}
	}

	/**
	 * Get EditPart figure.
	 *
	 * @return the EditPart figure
	 */
	private IPapyrusNodeNamedElementFigure getNodeNamedElementFigure() {
		return (IPapyrusNodeNamedElementFigure) getPrimaryShape();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setFontColor(Color color) {
		super.setFontColor(color);

		// Qualified Name
		WrappingLabel qualifiedNameLabel = getNodeNamedElementFigure().getQualifiedNameLabel();
		if (qualifiedNameLabel != null) {
			qualifiedNameLabel.setForegroundColor(color);
		}
		// Tagged Label
		WrappingLabel tagLabel = getNodeNamedElementFigure().getTaggedLabel();
		if (tagLabel != null) {
			tagLabel.setForegroundColor(color);
		}
	}

}
