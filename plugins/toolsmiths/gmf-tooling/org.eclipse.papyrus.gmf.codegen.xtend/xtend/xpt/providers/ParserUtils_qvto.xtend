/*****************************************************************************
 * Copyright (c) 2007, 2014, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Artem Tikhomirov (Borland) - refactored javaInitilizers not to use methods from GMFGen model
 *								[221347] Got rid of generated interfaces
 *								(IObjectInitializer, IFeatureInitializer) and implementation thereof
 * Christian W. Damus (CEA) - bug 440263
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.providers

import xpt.Common
import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef

@Singleton class ParserUtils_qvto {
	@Inject extension Common

	@MetaDef  def String parserFieldName(GenCommonBase element) {
		return element.stringUniqueIdentifier.toFirstLower + '_Parser'
	}

	@MetaDef  def String parserAccessorName(GenCommonBase element) {
		return 'get' + element.stringUniqueIdentifier.toFirstUpper + '_Parser'
	}

}
