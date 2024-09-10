/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.provider;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionNameEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLParserProvider;
import org.eclipse.papyrus.uml.diagram.interactionoverview.parser.CustomCallBehaviorActionParser;

public class CustomUMLParserProvider extends UMLParserProvider {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.providers.UMLParserProvider#getParser(int)
	 *
	 * @param visualID
	 * @return
	 */

	@Override
	protected IParser getParser(final String visualID) {
		switch (visualID) {
		case CallBehaviorActionNameEditPart.VISUAL_ID:
			return getCallBehaviorAction_NameLabel_Parser();
		default:
			return super.getParser(visualID);
		}


	}

	private IParser getCallBehaviorAction_NameLabel_Parser() {
		if (custom_callBehaviorAction_NameLabel_Parser == null) {
			final CustomCallBehaviorActionParser parser = new CustomCallBehaviorActionParser();
			custom_callBehaviorAction_NameLabel_Parser = parser;
		}
		return custom_callBehaviorAction_NameLabel_Parser;
	}

	private IParser custom_callBehaviorAction_NameLabel_Parser;
}
