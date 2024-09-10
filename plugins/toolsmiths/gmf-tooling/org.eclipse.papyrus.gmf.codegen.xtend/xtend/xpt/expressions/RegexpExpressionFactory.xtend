/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - API extracted to GMF-T runtime, migrated to Xtend2 
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.expressions

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLanguage
import xpt.Common

@com.google.inject.Singleton class RegexpExpressionFactory {
	@Inject extension Common;

	@Inject ExpressionAbstractExpression xptAbstractExpression
	@Inject OCLExpressionFactory xptOCLExpressionFactory;

	def className(GenExpressionInterpreter it) '''«it.className»'''

	def packageName(GenExpressionInterpreter it) '''«it.container.expressionsPackageName»'''

	def qualifiedClassName(GenExpressionInterpreter it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenExpressionInterpreter it) '''«qualifiedClassName(it)»'''

	def RegexpExpressionFactory(GenExpressionInterpreter it) '''
		«copyright(it.container.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» {

			«xptOCLExpressionFactory.initInterpreterFactory(it)»

			«generatedMemberComment»
			public static «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» getExpression(String body, org.eclipse.emf.ecore.EClassifier context, java.util.Map<String, org.eclipse.emf.ecore.EClassifier> environment) {
				return new Expression(body, context, environment);
			}

			«generatedMemberComment('This method will become private in the next release')»/* FIXME [MG] private or completely remove in the next release  */
			public static «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» getExpression(String body, org.eclipse.emf.ecore.EClassifier context) {
				return getExpression(body, context, java.util.Collections.<String, org.eclipse.emf.ecore.EClassifier>emptyMap());
			}

			«generatedMemberComment»
			private static class Expression extends «xptAbstractExpression.qualifiedClassName(it.container.editorGen.diagram)» {

				«generatedMemberComment»
				private final java.util.regex.Pattern pattern;

				«generatedMemberComment»
				@SuppressWarnings("rawtypes")
				public Expression(String body, org.eclipse.emf.ecore.EClassifier context, java.util.Map environment) {
					super(body, context);
					java.util.regex.Pattern p;
					try {
						p = java.util.regex.Pattern.compile(body);
					} catch (java.util.regex.PatternSyntaxException e) {
						setStatus(org.eclipse.core.runtime.IStatus.ERROR, e.getMessage(), e);
						p = null;
					}
					this.pattern = p;
				}

				«generatedMemberComment»
				@SuppressWarnings("rawtypes")
				protected Object doEvaluate(Object contextInstance, java.util.Map env) {
					if (pattern == null) {
						return null;
					}
					if (context() instanceof org.eclipse.emf.ecore.EDataType) {
						contextInstance = org.eclipse.emf.ecore.util.EcoreUtil.convertToString((org.eclipse.emf.ecore.EDataType) context(), contextInstance);
					}
					java.util.regex.Matcher matcher = this.pattern.matcher(String.valueOf(contextInstance));
					return Boolean.valueOf(«IF language == GenLanguage::NREGEXP_LITERAL»!«ENDIF»matcher.matches());
				}
			}
		}
	'''

}
