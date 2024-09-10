/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
package org.eclipse.papyrus.uml.diagram.common.parser.stereotype;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * UML keywords are reserved words that are an integral part of the UML notation and normally appear as text
 * annotations attached to a UML graphic element or as part of a text line in a UML diagram.
 * See Table C.1 Keywords in OMG Unified Modeling Language TM (OMG UML) Version 2.5
 *
 * @author Francois Le Fevre - francois.le-fevre@cea.fr
 * @since 3.1
 */
public class AppliedKeywordParser extends AppliedStereotypeParser {


	public AppliedKeywordParser() {
		this(null);
	}

	public AppliedKeywordParser(String defaultPrintString) {
		super(defaultPrintString);
	}

	@Override
	public String getEditString(IAdaptable element, int flags) {
		Element subject = doAdapt(element);
		List<Stereotype> stereos = subject.getAppliedStereotypes();
		if (stereos.isEmpty()) {
			return getMyDefaultPrintString();
		}
		StringBuffer result = new StringBuffer();
		result.append(getMyDefaultPrintString());
		for (Stereotype next : stereos) {
			if (result.length() > 0) {
				result.append(", "); //$NON-NLS-1$
			}
			result.append(UMLLabelInternationalization.getInstance().getKeyword(next));
		}
		return result.toString();
	}

}
