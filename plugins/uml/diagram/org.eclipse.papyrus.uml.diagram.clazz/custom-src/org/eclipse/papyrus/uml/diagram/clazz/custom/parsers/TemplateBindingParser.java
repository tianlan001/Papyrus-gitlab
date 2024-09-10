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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.TemplateParameterSubstitution;

/**
 * this is a parser for binding substitution
 *
 */
public class TemplateBindingParser implements IParser {

	private static final String EMPTY_STR = ""; //$NON-NLS-1$

	final ILabelProvider labelProvider = new AdapterFactoryLabelProvider(org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 *
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return new ParserEditStatus(UMLDiagramEditorPlugin.ID, IStatus.OK, EMPTY_STR);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 *
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		String out = EMPTY_STR;
		EObject e = element.getAdapter(EObject.class);
		if (e != null) {
			final TemplateBinding binding = (TemplateBinding) e;
			for (TemplateParameterSubstitution substitution : binding.getParameterSubstitutions()) {
				if (!EMPTY_STR.equals(out)) {
					out += ", "; // add separator, if not empty //$NON-NLS-1$
				}
				if (substitution.getFormal() != null && substitution.getFormal().getParameteredElement() instanceof NamedElement) {
					out += UMLLabelInternationalization.getInstance().getLabel(((NamedElement) substitution.getFormal().getParameteredElement()));
				}
				if (substitution.getActual() instanceof NamedElement) {
					out += " -> " + //$NON-NLS-1$
							UMLLabelInternationalization.getInstance().getLabel(((NamedElement) substitution.getActual()));
				}
			}
		}
		if (EMPTY_STR.equals(out)) {
			out = String.format("«bind»\n<No Binding Substitution>", out); //$NON-NLS-1$
		} else {
			out = String.format("«bind»\n<%s>", out); //$NON-NLS-1$
		}
		return out;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent(java.lang.Object, int)
	 *
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return true;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 *
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}
}
