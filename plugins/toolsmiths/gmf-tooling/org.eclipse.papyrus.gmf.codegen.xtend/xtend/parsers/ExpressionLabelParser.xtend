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
package parsers

import com.google.inject.Inject
import impl.parsers.expression_qvto
import xpt.CodeStyle
import xpt.Common

@com.google.inject.Singleton class ExpressionLabelParser {
	@Inject extension Common;
	@Inject extension expression_qvto;
	@Inject extension ParsersUtil;
	@Inject extension CodeStyle;
	

	@Inject impl.parsers.ExpressionLabelParser xptImplExpressionLabelParser;

	def className(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«classNameExpressionLabelParser(it)»'''

	def packageName(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«packageNameExpressionLabelParser(it)»'''

	def qualifiedClassName(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«packageName(it)».«className(it)»'''

	def fullPath(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''«qualifiedClassName(it)»'''

	def Main(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«copyright(it.holder.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«xptImplExpressionLabelParser.constructor(it, className(it).toString)»
			«body(it)»
		}
	'''

	def extendsList(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) // 
	'''«IF isParserViewExpressionDefinedAndOcl(it)»extends org.eclipse.gmf.tooling.runtime.parsers.ExpressionLabelParserBase«ENDIF»'''

	def implementsList(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) // 
	'''«IF !isParserViewExpressionDefinedAndOcl(it)»implements org.eclipse.gmf.runtime.common.ui.services.parser.IParser«ENDIF»'''

	def body(org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser it) '''
		«IF isParserViewExpressionDefinedAndOcl(it)»
			«generatedMemberComment»
			«overrideC»
			protected String getExpressionBody() {
				return «xptImplExpressionLabelParser.getExpression(viewExpression.provider, it, viewExpression)».body();
			}
		«ELSE»
			«generatedMemberComment»
			public String getPrintString(org.eclipse.core.runtime.IAdaptable element, int flags) {
				«xptImplExpressionLabelParser.accessViewExpression(it)»
			}

			«generatedMemberComment»
			public boolean isAffectingEvent(Object event, int flags) {
				// XXX Any event is recognized as important, unless there's a way to extract this information from expression itself.
				// TODO analyze expressions (e.g. using OCL parser) to find out structural features in use  
				return true;
			}
		«ENDIF»

		«generatedMemberComment»
		public String getEditString(org.eclipse.core.runtime.IAdaptable element, int flags) {
			«xptImplExpressionLabelParser.accessEditExpression(it)»
		}

		«generatedMemberComment»
		public org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus isValidEditString(org.eclipse.core.runtime.IAdaptable element, String editString) {
			«xptImplExpressionLabelParser.accessValidateExpression(it)»
		}

		«generatedMemberComment»
		public org.eclipse.gmf.runtime.common.core.command.ICommand getParseCommand(org.eclipse.core.runtime.IAdaptable element, final String newString, int flags) {
			final org.eclipse.emf.ecore.EObject target = element.getAdapter(org.eclipse.emf.ecore.EObject.class);
			if (!validateValues(target, newString)) {
				return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
			}
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = org.eclipse.emf.transaction.util.TransactionUtil.getEditingDomain(target);
			if (editingDomain == null) {
				return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
			}
			org.eclipse.core.resources.IFile affectedFile = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(target.eResource());
			return new org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand(editingDomain, "Set Values", affectedFile == null ? null : java.util.Collections.singletonList(affectedFile)) { «nonNLS(1)» 
				protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
					return new org.eclipse.gmf.runtime.common.core.command.CommandResult(updateValues(target, newString));
				}
			};
		}

		«generatedMemberComment»
		public org.eclipse.jface.text.contentassist.IContentAssistProcessor getCompletionProcessor(org.eclipse.core.runtime.IAdaptable element) {
			return null;
		}

		«generatedMemberComment»
		private boolean validateValues(org.eclipse.emf.ecore.EObject target, String newString) {
			// TODO implement as needed«/* with default 'true' I assume isValidEditString() has been called prior to getParseCommand and generally I don't need to do anything else here */»
			return true;
		}

		«generatedMemberComment»
		private org.eclipse.core.runtime.IStatus updateValues(org.eclipse.emf.ecore.EObject target, String newString) throws org.eclipse.core.commands.ExecutionException {
			// TODO implement this method
			// DO NOT FORGET to remove @generated tag or mark method @generated NOT
			throw new org.eclipse.core.commands.ExecutionException("Please implement parsing and value modification"); 
		}

		«xptImplExpressionLabelParser.extraMethods(it)»
	'''
}
