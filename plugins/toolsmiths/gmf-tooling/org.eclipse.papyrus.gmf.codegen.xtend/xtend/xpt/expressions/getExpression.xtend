/*******************************************************************************
 * Copyright (c) 2007-2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.expressions

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLanguage
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import xpt.Common_qvto

/**
 * FIXME: [MG] - fieldNames should be xptXXX
 */
@com.google.inject.Singleton class getExpression {
	@Inject extension Common_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject OCLExpressionFactory oclFactory;
	@Inject RegexpExpressionFactory regexpFactory;

	@MetaDef def CharSequence getExpressionBody(ValueExpression it) '''«getExpressionBody(provider, it)»'''

	@MetaDef def dispatch getExpressionBody(GenExpressionProviderBase it, ValueExpression valueExpr) //
		'''«ERROR('Abstract template call: getExpression')»'''

	@MetaDef def dispatch getExpressionBody(GenExpressionInterpreter it, ValueExpression valueExpr) //
		'''«getExpressionFactory(it)».getExpressionBody(«it.expressionIndex(valueExpr)»)'''

	@MetaDef def dispatch getExpression(GenExpressionProviderBase it, ValueExpression valueExpr, GenClassifier context) //
		'''«ERROR('Abstract template call: getExpression')»'''

	@MetaDef def dispatch getExpression(GenExpressionInterpreter it, ValueExpression valueExpr, GenClassifier context) //
	'''«getExpression(it, valueExpr, context, 'null')»'''

	// occasionally we need to use some well-known context type, i.e. String
	@MetaDef def dispatch getExpression(GenExpressionInterpreter it, ValueExpression valueExpr, String contextMetaClassifier) //
		'''«getExpressionFactory(it)».«getExpressionAccessor(valueExpr)»(«it.expressionIndex(valueExpr)», «contextMetaClassifier», 'null')'''

	// pass specific environment to obtain expression 
	@MetaDef def getExpression(GenExpressionInterpreter it, ValueExpression valueExpr, GenClassifier context, String environmentArg) //
		'''«getExpressionFactory(it)».«getExpressionAccessor(valueExpr)»(«it.expressionIndex(valueExpr)», «xptMetaModel.MetaClass(context)», «environmentArg»)'''

	def CharSequence getExpressionFactory(GenExpressionInterpreter it) {
		if (it.language == GenLanguage::OCL_LITERAL) return oclFactory.qualifiedClassName(it);
		if (it.language == GenLanguage::OCL_LITERAL) return regexpFactory.qualifiedClassName(it);
		return getQualifiedClassName();
	}

	/**
	 * XXX: [MG] in Xpand, there was additional "-1" here: 'it.expressions.indexOf(expr) - 1', hope in Xtend we don't need it
	 */   
	private static def int expressionIndex(GenExpressionInterpreter it, ValueExpression expr) {
		it.expressions.indexOf(expr)
	}

	def getExpressionInterpriterQualifiedClassName(GenExpressionInterpreter it) '''
		«IF it.language == GenLanguage::OCL_LITERAL»«oclFactory.qualifiedClassName(it)»
		«ELSEIF it.language == GenLanguage::REGEXP_LITERAL»«regexpFactory.qualifiedClassName(it)»
		«ELSEIF it.language == GenLanguage::JAVA_LITERAL»«it.getQualifiedClassName()»
		«ELSEIF it.language == GenLanguage::NREGEXP_LITERAL»«it.getQualifiedClassName()»
		«ELSEIF it.language == GenLanguage::LITERAL_LITERAL»«it.getQualifiedClassName()»
		«ELSE»«ERROR('Language not supported: ' + it)»«ENDIF»'''

	def getExpressionInterpriterClassName(GenExpressionInterpreter it)'''
		«IF it.language == GenLanguage::OCL_LITERAL»«oclFactory.className(it)»
		«ELSEIF it.language == GenLanguage::REGEXP_LITERAL»«regexpFactory.className(it)»
		«ELSEIF it.language == GenLanguage::JAVA_LITERAL»«getQualifiedClassName().split('\\.').last()»
		«ELSEIF it.language == GenLanguage::NREGEXP_LITERAL»«getQualifiedClassName().split('\\.').last()»
		«ELSEIF it.language == GenLanguage::LITERAL_LITERAL»«it.getQualifiedClassName().split('\\.').last()»
		«ELSE»«ERROR('Language not supported: ' + it)»«ENDIF»'''
}