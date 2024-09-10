/**
 * Copyright (c) 2014 CEA LIST.
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
 */

package org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;


public class CustomTransitionGuardEditPart extends TransitionGuardEditPart {

	public CustomTransitionGuardEditPart(View view) {
		super(view);
	}

	/**
	 * Label should be capable of wrapping, i.e. of displaying text in multiple lines
	 * which is in particular required when the code of an operation is shown instead of the operations name
	 * [see bug 369305]
	 *
	 * @see org.eclipse.papyrus.diagram.statemachine.edit.parts.TransitionGuardEditPart#setLabelTextHelper(org.eclipse.draw2d.IFigure, java.lang.String)
	 *
	 * @param figure
	 * @param text
	 */
	@Override
	protected void setLabelTextHelper(IFigure figure, String text) {
		if (figure instanceof WrappingLabel) {
			((WrappingLabel) figure).setText(text);
			// [addition for bug 369305]
			((WrappingLabel) figure).setTextWrap(true);
		} else if (figure instanceof ILabelFigure) {
			((ILabelFigure) figure).setText(text);
		} else {
			((Label) figure).setText(text);
		}
	}


	@Override
	protected String getLabelText() {
		String text = null;
		EObject parserElement = getParserElement();
		if (parserElement != null && getParser() != null) {
			// pass an EObject based on the view (getModel) to the parser. This enables the parser to
			// access the view dependent configuration how much text should be displayed.
			text = getParser().getPrintString(
					new EObjectAdapter((View) getModel()),
					getParserOptions().intValue());
		}
		if (text == null || text.length() == 0) {
			text = super.getLabelText();
		}
		return text;
	}
}
