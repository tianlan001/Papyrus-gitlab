/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.groups.core.ui;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.RGB;


public class CheckboxIGraphicalFocusListenner implements MouseTrackListener {


	private int oldColor, newColor;

	private View parentView;

	private TransactionalEditingDomain editingDomain;


	public CheckboxIGraphicalFocusListenner(IGraphicalEditPart parent) {
		super();
		this.parentView = (View) parent.getModel();
		newColor = FigureUtilities.RGBToInteger(new RGB(255, 0, 0));

	}


	public void mouseEnter(MouseEvent arg0) {

		// SetPropertyCommand changeColor = new SetPropertyCommand( editingDomain,"blabla",parentView,NotationPackage.Literals.LINE_STYLE,newColor);
	}

	public void mouseExit(MouseEvent arg0) {
		// LineStyle lineStyle = (LineStyle)parentView.getStyle(NotationPackage.Literals.LINE_STYLE);
		// if(lineStyle != null) {
		// // line color
		// //RGB lineRGB = PreferenceConverter.getColor(store, preferenceLineColorName);
		// newColor = lineStyle.getLineColor();
		// lineStyle.setLineColor(oldColor);
		// }


	}

	public void mouseHover(MouseEvent arg0) {
		

	}

}
