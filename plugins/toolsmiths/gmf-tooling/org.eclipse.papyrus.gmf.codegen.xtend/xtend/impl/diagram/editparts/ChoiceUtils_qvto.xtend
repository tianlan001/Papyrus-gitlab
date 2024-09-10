/*****************************************************************************
 * Copyright (c) 2011 - 2013, 2021 Montages AG, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Svyatoslav Kovalsky (Montages) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package impl.diagram.editparts

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedEnumParser
import xpt.Common_qvto

@com.google.inject.Singleton class ChoiceUtils_qvto {
	@Inject extension Common_qvto

	def boolean isOclChoiceLabel(GenCommonBase xptSelf) {
		var LabelModelFacet labelModelFacet = getLabelModelfacet(xptSelf)
		return labelModelFacet !== null && labelModelFacet.parser.oclIsKindOf(typeof(OclChoiceParser))
	}

	def boolean isOclChoiceLabelWithShowExpr(GenCommonBase xptSelf) {
		var LabelModelFacet labelModelFacet = getLabelModelfacet(xptSelf)
		if (labelModelFacet === null) {
			return false
		}
		var parser = labelModelFacet.parser;
		return switch (parser) {
			OclChoiceParser: parser.showExpression !== null
			default: false
		}
	}

	def boolean isChoiceLabel(LabelModelFacet modelFacet) {
		var parser = modelFacet.parser;
		if (parser !== null) {
			return parser.oclIsKindOf(typeof(PredefinedEnumParser)) || parser.oclIsKindOf(typeof(OclChoiceParser));
		} else {
			return false;
		}
	}

	def String getDirectManagerFQN(LabelModelFacet modelFacet) {
		return if (isChoiceLabel(modelFacet)) {
			"org.eclipse.gmf.tooling.runtime.directedit.ComboDirectEditManager"
		} else {
			"org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager"
		}
	}

	private def LabelModelFacet getLabelModelfacet(GenCommonBase xptSelf) {
		return switch (xptSelf) {
			GenChildLabelNode: xptSelf.labelModelFacet
			GenLabel: xptSelf.modelFacet
			default: null
		}
	}
}
