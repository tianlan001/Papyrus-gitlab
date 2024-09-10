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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;


public class CustomExitStateBehaviorEditPart extends ExitStateBehaviorEditPart {

	public CustomExitStateBehaviorEditPart(View view) {
		super(view);
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
