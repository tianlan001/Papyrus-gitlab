/*****************************************************************************
 * Copyright (c) 2006, 2017 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation
 *    Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 Stereotype Display
 *    Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser.stereotype;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.uml.diagram.common.parser.assist.FixedSetCompletionProcessor;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Stereotype;

public class AppliedStereotypeParser implements ISemanticParser {

	private final String myDefaultPrintString;

	public AppliedStereotypeParser() {
		this(null);
	}

	public AppliedStereotypeParser(String defaultPrintString) {
		myDefaultPrintString = defaultPrintString;
	}

	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		if (notification instanceof Notification) {
			Object feature = ((Notification) notification).getFeature();
			if (feature instanceof EStructuralFeature) {
				EStructuralFeature featureImpl = (EStructuralFeature) feature;
				return featureImpl.getName().startsWith(Extension.METACLASS_ROLE_PREFIX);
			}
		}
		return false;
	}

	@Override
	public List<?> getSemanticElementsBeingParsed(EObject eObject) {
		Element element = (Element) eObject;
		List<EObject> result = new LinkedList<>();
		// result.add(element);
		result.addAll(element.getStereotypeApplications());
		return result;
	}

	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable subject) {
		Element element = doAdapt(subject);
		List<Stereotype> remaining = new LinkedList<>();
		remaining.addAll(element.getApplicableStereotypes());
		remaining.removeAll(element.getAppliedStereotypes());

		List<String> names = new LinkedList<>();
		for (Stereotype next : remaining) {
			names.add(next.getName());
		}
		return new FixedSetCompletionProcessor(names);
	}

	@Override
	public String getEditString(IAdaptable element, int flags) {
		Element subject = doAdapt(element);
		List<Stereotype> stereos = subject.getAppliedStereotypes();
		if (stereos.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		StringBuffer result = new StringBuffer();
		for (Stereotype next : stereos) {
			if (result.length() > 0) {
				result.append(", "); //$NON-NLS-1$
			}
			result.append(UMLLabelInternationalization.getInstance().getKeyword(next));
		}
		return result.toString();
	}

	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		String editString = getEditString(element, flags);
		if (editString != null && !editString.isEmpty()) {
			return StereotypeDisplayConstant.QUOTE_LEFT + editString + StereotypeDisplayConstant.QUOTE_RIGHT;
		}
		return myDefaultPrintString;
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.UNEDITABLE_STATUS;
	}

	/**
	 * @since 3.1
	 */
	protected Element doAdapt(IAdaptable adaptable) {
		Element element = (Element) adaptable.getAdapter(EObject.class);
		return element;
	}

	/**
	 * @return the myDefaultPrintString
	 * @since 3.1
	 */
	protected String getMyDefaultPrintString() {
		return myDefaultPrintString;
	}

}
