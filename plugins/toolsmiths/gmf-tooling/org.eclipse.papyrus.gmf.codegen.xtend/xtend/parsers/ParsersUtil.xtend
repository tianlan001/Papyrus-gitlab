/*******************************************************************************
 * Copyright (c) 2013, 2020 Montages A.G., CEA LIST, Artal
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
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up parsers
 *****************************************************************************/
package parsers

import com.google.inject.Inject
import xpt.Common_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers

/**
 * [MG] FIXME: revisit, he same problem as with the QualifiedClassNameProvider
*/ 	
@com.google.inject.Singleton class ParsersUtil {

	@Inject extension Common_qvto;

	def classNameCustomParser(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«lastSegment(it.qualifiedName)»'''

	def packageNameCustomParser(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«withoutLastSegment(it.qualifiedName)»'''

	def classNameExpressionLabelParser(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«lastSegment(it.qualifiedClassName)»'''

	def packageNameExpressionLabelParser(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«withoutLastSegment(it.qualifiedClassName)»'''

	def classNameGenParsers(GenParsers it) '''«className»'''

	def packageNameGenParsers(GenParsers it) '''«packageName»'''

}