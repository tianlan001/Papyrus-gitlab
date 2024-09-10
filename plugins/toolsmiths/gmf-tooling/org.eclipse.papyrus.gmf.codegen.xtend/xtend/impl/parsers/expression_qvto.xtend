/*******************************************************************************
 * Copyright (c) 2011 - 2020 Montages AG, CEA LIST, Artal
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up parsers
 *****************************************************************************/
package impl.parsers

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParserImplementation
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import xpt.Common_qvto

@com.google.inject.Singleton class expression_qvto {
	@Inject extension Common_qvto

	def boolean isViewExpressionDefinedAndOcl(GenCommonBase xptSelf) {
		var LabelModelFacet labelModelFacet = getLabelModelfacet(xptSelf)
		return labelModelFacet !== null && labelModelFacet.parser.isParserViewExpressionDefinedAndOcl();
	}

	def dispatch boolean isParserViewExpressionDefinedAndOcl(ExpressionLabelParser xptSelf) {
		return xptSelf.viewExpression !== null && xptSelf.viewExpression.provider.oclIsKindOf(typeof(GenExpressionInterpreter));
	}

	def dispatch boolean isParserViewExpressionDefinedAndOcl(GenParserImplementation xptSelf) {
		return false;
	}

	private def LabelModelFacet getLabelModelfacet(GenCommonBase xptSelf) {
		return switch (xptSelf) {
			GenChildLabelNode: xptSelf.labelModelFacet
			GenLabel: xptSelf.modelFacet
			default: null
		}
	}

}
