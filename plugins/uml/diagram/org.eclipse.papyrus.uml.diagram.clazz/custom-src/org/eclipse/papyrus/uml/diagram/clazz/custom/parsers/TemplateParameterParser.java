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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.parsers;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.ClassifierTemplateParameter;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.OperationTemplateParameter;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.TemplateParameter;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * this parser is used to display parameter of a template
 *
 */
public class TemplateParameterParser implements IParser {

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param element
	 * @return
	 */
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
		ICommand command = UnexecutableCommand.INSTANCE;
		EObject e = element.getAdapter(EObject.class);
		if (e != null) {
			final TemplateParameter templateParam = (TemplateParameter) e;
			if (newString.contains("<UNDEFINED>")) {
				return UnexecutableCommand.INSTANCE;
			}
			if (templateParam.getParameteredElement() instanceof NamedElement) {
				NamedElement namedElement = (NamedElement) templateParam.getParameteredElement();
				String name = newString.substring(0, newString.indexOf(":"));
				if (InternationalizationPreferencesUtils.getInternationalizationPreference(namedElement) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(namedElement)) {
					final ModelSet modelSet = (ModelSet) namedElement.eResource().getResourceSet();
					command = new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(modelSet.getTransactionalEditingDomain(), namedElement, name.trim(), null));
				} else {
					SetRequest request = new SetRequest(namedElement, UMLPackage.eINSTANCE.getNamedElement_Name(), name.trim());
					command = new SetValueCommand(request);
				}
			}
		}
		return command;
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		EObject e = element.getAdapter(EObject.class);
		if (e != null) {
			final TemplateParameter templateParam = (TemplateParameter) e;
			if (templateParam.getParameteredElement() == null) {
				return "<UNDEFINED>";
			}
			String out = "";
			if (templateParam.getParameteredElement() instanceof NamedElement) {
				NamedElement namedElement = (NamedElement) templateParam.getParameteredElement();
				out = UMLLabelInternationalization.getInstance().getLabel(namedElement) + ": " + namedElement.eClass().getName();
			}
			if (templateParam instanceof OperationTemplateParameter) {
				if (templateParam.getParameteredElement() != null) {
					Operation op = (Operation) (templateParam.getParameteredElement());
					out = displayOperation(op);
				}
			} else if (templateParam instanceof ClassifierTemplateParameter) {
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
			if (templateParam.getDefault() instanceof Operation) {
				out = out + "=" + displayOperation((Operation) templateParam.getDefault());
			} else if (templateParam.getDefault() instanceof NamedElement) {
				out = out + "=" + UMLLabelInternationalization.getInstance().getLabel(((NamedElement) templateParam.getDefault()));
			}
			return out;
		}
		return "<UNDEFINED>";
	}

	protected String displayOperation(Operation op) {
		String out = UMLLabelInternationalization.getInstance().getLabel(op) + "(";
		Iterator<Parameter> iter = op.getOwnedParameters().iterator();
		while (iter.hasNext()) {
			Parameter param = iter.next();
			out = out + UMLLabelInternationalization.getInstance().getLabel(param);
			if (!param.equals(op.getOwnedParameters().get(op.getOwnedParameters().size() - 1))) {
				out = out + ", ";
			}
		}
		out = out + ")";
		return out;
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return true;
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}
}
