/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *   Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ClassifierTemplateParameter;
import org.eclipse.uml2.uml.TemplateParameter;

public class ClassifierTemplateParameterParser implements IParser {

	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	@Override
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}

	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		EObject e = element.getAdapter(EObject.class);
		if (e != null) {
			final TemplateParameter templateParam = (TemplateParameter) e;
			if (templateParam.getOwnedParameteredElement() == null) {
				return "<UNDEFINED>";
			}
			String out = "";
			if (templateParam.getOwnedParameteredElement() instanceof Classifier) {
				Classifier namedElement = (Classifier) templateParam.getOwnedParameteredElement();
				out = out + UMLLabelInternationalization.getInstance().getLabel(namedElement) + ": " + namedElement.eClass().getName();
			}
			if (templateParam instanceof ClassifierTemplateParameter) {
				if (!((ClassifierTemplateParameter) templateParam).getConstrainingClassifiers().isEmpty()) {
					out = out + ">";
					for (int i = 0; i < ((ClassifierTemplateParameter) templateParam).getConstrainingClassifiers().size(); i++) {
						out = out + UMLLabelInternationalization.getInstance().getLabel(((ClassifierTemplateParameter) templateParam).getConstrainingClassifiers().get(i));
						if (i < ((ClassifierTemplateParameter) templateParam).getConstrainingClassifiers().size() - 1) {
							out = out + ", ";
						}
					}
				}
			}
			return out;
		}
		return "<UNDEFINED>";
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}
}
