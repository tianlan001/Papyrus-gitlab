/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.parser.custom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.Parameter;


public class StreamLabelParser implements IParser {

	@Override
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return null;
	}

	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return null;
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		EObject object = element.getAdapter(EObject.class);
		if (false == object instanceof ActivityParameterNode) {
			return "";
		}
		Parameter parameter = ((ActivityParameterNode) object).getParameter();
		if (parameter == null || !parameter.isStream()) {
			return "";
		}
		return "{stream}";
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}
}
