/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.component.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.diagram.common.keyword.KeywordLabel;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedKeywordParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLParserProvider;

/**
 * @since 3.0
 */
public class CustomUMLParserProvider extends UMLParserProvider {

	public CustomUMLParserProvider() {
		super();
	}

	protected IParser getAppliedStereotypeParser(String defaultEditString) {
		return new AppliedStereotypeParser(defaultEditString);
	}

	protected IParser getAppliedKeywordParser(String defaultPrintString) {
		return new AppliedKeywordParser(defaultPrintString);
	}

	@Override
	protected IParser getParser(String visualID) {
		switch (visualID) {
		case AbstractionAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.ABSTRACTION);
		case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.MANIFEST);
		case SubstitutionAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedStereotypeParser(KeywordLabel.SUBSTITUTE);
		case UsageAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.USE);
		}
		return super.getParser(visualID);
	}
}
