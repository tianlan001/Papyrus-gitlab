/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *   Saadia DHOUIB (CEA LIST) saadia.dhouib@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.parser;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.uml.diagram.communication.custom.messages.Messages;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Message;

/**
 * The Class MessageParser.
 */
public class MessageParser implements IParser {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		if (element instanceof EObjectAdapter) {
			String result = ""; //$NON-NLS-1$
			final Message message = ((Message) ((EObjectAdapter) element).getRealObject());
			if (message == null) {
				return Messages.MessageParser_undefined;
			}
			if (message.getEAnnotations() != null) {
				// EList<EAnnotation> an = message.getEAnnotations();
				/*
				 * if (an.isEmpty())
				 * System.out.print("List is empty");
				 */
				// if ( message.getEAnnotations().get(0) != null)
				// result =
				// message.getEAnnotations().get(0).getDetails().get(0).getValue()
				// + ":" + message.getName();
				// else
				result = UMLLabelInternationalization.getInstance().getLabel(message);
			} else {
				result = UMLLabelInternationalization.getInstance().getLabel(message);
			}
			// result = "1:" + result;
			return result;
		}
		return Messages.MessageParser_undefined;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent(java.lang.Object, int)
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}
}
