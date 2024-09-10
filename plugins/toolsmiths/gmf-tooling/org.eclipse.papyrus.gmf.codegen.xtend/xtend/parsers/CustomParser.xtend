/*******************************************************************************
 * Copyright (c) 2008, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - [244419] custom parsers
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up parsers
 *****************************************************************************/
package parsers

import com.google.inject.Inject
import xpt.Common

@com.google.inject.Singleton class CustomParser {
	@Inject extension Common;
	@Inject extension ParsersUtil;

	def className(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«classNameCustomParser(it)»'''

	def packageName(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«packageNameCustomParser(it)»'''

	def qualifiedClassName(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«packageName(it)».«className(it)»'''

	def fullPath(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''«qualifiedClassName(it)»'''

	def Main(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''
		«copyright(holder.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «implementsList(it)» {

			«body(it)»
		}
	'''

	def implementsList(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) ''' implements org.eclipse.gmf.runtime.common.ui.services.parser.IParser'''

	/**
	 * As this is a borderblate class generator, there are no reasons to split it to per-method pieces.
	 */
	def body(org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser it) '''
		«generatedMemberComment»
		public String getEditString(org.eclipse.core.runtime.IAdaptable element, int flags) {
			return "";
		}

		«generatedMemberComment»
		public org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus isValidEditString(org.eclipse.core.runtime.IAdaptable element, String editString) {
			// TODO change to EDITABLE_STATUS as appropriate
			return org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus.UNEDITABLE_STATUS;
		}

		«generatedMemberComment»
		public org.eclipse.gmf.runtime.common.core.command.ICommand getParseCommand(org.eclipse.core.runtime.IAdaptable element, String newString, int flags) {
			// TODO
			throw new UnsupportedOperationException(); 
		}

		«generatedMemberComment»
		public String getPrintString(org.eclipse.core.runtime.IAdaptable element, int flags) {
			return "";
		}

		«generatedMemberComment»
		public boolean isAffectingEvent(Object event, int flags) {
			return false;
		}

		«generatedMemberComment»
		public org.eclipse.jface.text.contentassist.IContentAssistProcessor getCompletionProcessor(org.eclipse.core.runtime.IAdaptable element) {
			return null;
		}
	'''

}
