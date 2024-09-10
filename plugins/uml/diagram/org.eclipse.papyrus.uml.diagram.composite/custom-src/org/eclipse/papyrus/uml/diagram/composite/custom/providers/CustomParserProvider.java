/*****************************************************************************
 * Copyright (c) 2009-2011, 2017 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.diagram.common.keyword.KeywordLabel;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedKeywordParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.composite.custom.parsers.ConnectorLabelParser;
import org.eclipse.papyrus.uml.diagram.composite.custom.parsers.MultiplicityLabelParser;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.AbstractionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorMultiplicitySourceEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorMultiplicityTargetEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorNameEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeploymentAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InformationFlowAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ManifestationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SubstitutionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.UsageAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLParserProvider;

public class CustomParserProvider extends UMLParserProvider {

	public CustomParserProvider() {
		super();
	}

	protected IParser getAppliedStereotypeParser(String defaultEditString) {
		return new AppliedStereotypeParser(defaultEditString);
	}
	
	/**
	 * @since 3.0
	 */
	protected IParser getAppliedKeywordParser(String defaultPrintString) {
		return new AppliedKeywordParser(defaultPrintString);
	}

	@Override
	protected IParser getParser(String visualID) {
		switch (visualID) {
		case ConnectorMultiplicitySourceEditPart.VISUAL_ID:
		case ConnectorMultiplicityTargetEditPart.VISUAL_ID:
			return getMultiplicityFormatParser();
		case ConnectorNameEditPart.VISUAL_ID:
			return getConnectorLabelParser();
		case AbstractionAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.ABSTRACTION);
		case DeploymentAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.DEPLOY);
		case InformationFlowAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.FLOW);
		case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.MANIFEST);
		case SubstitutionAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.SUBSTITUTE);
		case UsageAppliedStereotypeEditPart.VISUAL_ID:
			return getAppliedKeywordParser(KeywordLabel.USE);
		}
		return super.getParser(visualID);
	}

	private IParser getConnectorLabelParser() {
		return new ConnectorLabelParser();
	}

	private IParser getMultiplicityFormatParser() {
		return new MultiplicityLabelParser();
	}
}
