/*******************************************************************************
 * Copyright (c) 2010, 2020 Artem Tikhomirov, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (independent) - Initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up parsers
 *****************************************************************************/
package impl.parsers

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenConstraint
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenJavaExpressionProvider
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLanguage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLiteralExpressionProvider
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression
import xpt.Common
import xpt.Common_qvto
import xpt.expressions.getExpression

@com.google.inject.Singleton class ExpressionLabelParser {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension parsers.ExpressionLabelParser

	@Inject getExpression xptGetExpression;

	def constructor(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it, String name) '''
		«generatedMemberComment»
		public «className(it)»() {
		}
	'''

	def accessEditExpression(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«IF editExpression === null || editExpression.provider === null »
			return getPrintString(element, flags);
		«ELSE»
			«evaluateAndReturnExpressionResult(editExpression.provider, it, 'evaluateEditExpression', editExpression)»
		«ENDIF»
	'''

	def accessViewExpression(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«IF viewExpression === null || viewExpression.provider === null »
			// TODO - viewExpression is not defined in the model
			throw new UnsupportedOperationException(""); 
		«ELSE»
			«evaluateAndReturnExpressionResult(viewExpression.provider, it, 'evaluatePrintExpression', viewExpression)»
		«ENDIF»
	'''

	def dispatch getExpression(GenExpressionProviderBase it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, ValueExpression expression) '''«ERROR('Abstract template call: getExpression')»'''

	def dispatch getExpression(GenExpressionInterpreter it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, ValueExpression expression) '''
		«xptGetExpression.getExpression(it, expression, parser.expressionContext)»
	'''

	def dispatch evaluateAndReturnExpressionResult(GenExpressionProviderBase it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, String javaMethodName, ValueExpression expression) '''
		«ERROR('Abstract template call for: ' + it)»
	'''

	def dispatch evaluateAndReturnExpressionResult(GenExpressionInterpreter it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, String javaMethodName, ValueExpression expression) '''
		org.eclipse.emf.ecore.EObject target = element.getAdapter(org.eclipse.emf.ecore.EObject.class);
		Object result =  «getExpression(it, parser, expression)».evaluate(target);
		return String.valueOf(result);
	'''

	def dispatch evaluateAndReturnExpressionResult(GenJavaExpressionProvider it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, String javaMethodName, ValueExpression expression) '''
		return «javaMethodName»(element.getAdapter(org.eclipse.emf.ecore.EObject.class));
	'''

	def dispatch evaluateAndReturnExpressionResult(GenLiteralExpressionProvider it, org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser parser, String javaMethodName, ValueExpression expression) '''
		return «expression.body»;
	'''

	def accessValidateExpression(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«IF validateExpression !== null »
			«IF validateExpression.provider.language == GenLanguage::LITERAL_LITERAL»
				if (!«validateExpression.body») {
			«ELSE»
				if (Boolean.FALSE.equals(«dispatchCheckValidateExpression(it.validateExpression.provider, it.validateExpression)»)) {
			«ENDIF»
			return org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus.UNEDITABLE_STATUS;
			} // else fall-through
		«ENDIF»
		return org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus.EDITABLE_STATUS;
	'''

	/**
	 * The xpt behavior, that is compilation error in generated code, is preserved, but additional comment had been added in Xtend version
	 */
	def dispatch dispatchCheckValidateExpression(GenExpressionProviderBase it, GenConstraint expression) '''/*FIXME: unkwnown expression provider */'''

	def dispatch dispatchCheckValidateExpression(GenExpressionInterpreter it, GenConstraint expression) // 
	'''«xptGetExpression.getExpression(it, expression, 'org.eclipse.emf.ecore.getEcorePackage.eINSTANCE.getEString()')».evaluate(editString)»'''

	def dispatch dispatchCheckValidateExpression(GenJavaExpressionProvider it, GenConstraint expression) // 
	'''evaluateValidateExpression(editString)'''

	def extraMethods(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«IF viewExpression !== null && viewExpression.provider.oclIsKindOf(typeof(GenJavaExpressionProvider))»
			«javaMethod(viewExpression.provider as GenJavaExpressionProvider, 'evaluatePrintExpression', 'String', 'org.eclipse.emf.ecore.EObject', viewExpression)»
		«ENDIF»
		«IF editExpression !== null && editExpression.provider.oclIsKindOf(typeof(GenJavaExpressionProvider))»
			«javaMethod(editExpression.provider as GenJavaExpressionProvider, 'evaluateEditExpression', 'String', 'org.eclipse.emf.ecore.EObject', editExpression)»
		«ENDIF»
		«IF validateExpression !== null && validateExpression.provider.oclIsKindOf(typeof(GenJavaExpressionProvider))»
			«javaMethod(validateExpression.provider as GenJavaExpressionProvider, 'evaluateValidateExpression', 'Boolean', 'String', validateExpression)»
		«ENDIF»
	'''

	def javaMethod(GenJavaExpressionProvider it, String methodName, String returnType, String paramType, ValueExpression expression) '''
		«generatedMemberComment»
		private «returnType» «methodName»(«paramType» self) {
		«IF injectExpressionBody && expression !== null && !expression.body.nullOrEmpty»
			«expression.body»
		«ELSEIF throwException || (injectExpressionBody && ( expression === null || expression.body.nullOrEmpty))»
			// TODO: implement this method to return «returnType» value  
			// Ensure that you remove @generated or mark it @generated NOT
			throw new java.lang.UnsupportedOperationException("No user java implementation provided in '«methodName»' operation"); «nonNLS»
		«ELSE»
			return null;
		«ENDIF»	
		}
	'''

}
