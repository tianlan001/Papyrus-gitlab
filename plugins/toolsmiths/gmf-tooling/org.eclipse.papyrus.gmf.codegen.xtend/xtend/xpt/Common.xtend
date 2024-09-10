/*****************************************************************************
 * Copyright (c) 2006-2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up + nonNLS(String) helper
 *****************************************************************************/
package xpt

import com.google.inject.Singleton
import java.util.regex.Pattern
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.internal.common.codegen.Conversions
import xpt.editor.VisualIDRegistry

/**
 * XXX: [MG] I don't like dependency from Common -> VisualIdRegistry 
 */
@Singleton class Common {

	def copyright(GenEditorGenerator it) {
		if(copyrightText !== null) {
			val split =  copyrightText.split('\n');
			// Bug 569174 : L1.2 : fix copyright header extra space to remove at line 2 ... n
			'''
			/**«FOR element : split »«'\n *'»«IF !element.isBlank» «element»«ENDIF»«ENDFOR»
			 */
			'''
		}
	}

	def xcopyright(GenEditorGenerator it) 
	'''
	«IF copyrightText !== null»
	<!--
	«escapeXML(it.copyrightText)»
	-->
	«ENDIF»
	'''

	def escapeXML(CharSequence forXML) {
		Conversions::escapeXML(forXML.toString) 
	}

	def generatedClassComment(){
		generatedClassComment('')
	}

	def generatedClassComment(String comment) {
		doGeneratedComment(comment, '')
	} 

	def generatedMemberComment() {
		doGeneratedComment('', '')	
	}

	def generatedMemberComment(String comment) {
		doGeneratedComment(comment, '')
	} 

	def generatedMemberComment(String comment, String comment2) {
		doGeneratedComment(comment, comment2)
	}

	/**
	 * XXX: FIXME: merge all generatedXXXcomment to go here
	 */ 
	def doGeneratedComment(String comment, String comment2) 
	'''
	/**«IF comment.length > 0»«'\n'» * «comment.replaceAll('\n', '\n * ').replaceAll('\\* \n', '*\n')»
	 *«ENDIF»
	 * @generated«IF comment2.length > 0»«'\n' /*the first space inherited*/»* «comment2.replaceAll('\n',
	 		/*the first space inherited*/'\n* ').replaceAll('\\* \n', '*\n ')»«ENDIF»
	 */
	'''

	def xmlGeneratedTag() '''<?gmfgen generated="true"?>'''

	def nonNLS_All(Iterable<?> list) '''«IF !list.empty»«FOR i : 1..list.size SEPARATOR ' '»«nonNLS(i)»«ENDFOR»«ENDIF»'''

	def nonNLS() '''«nonNLS(1)»'''

	def nonNLS(Object xptSelf, int i) '''«nonNLS(i)»'''

	def nonNLS(int xptSelf) '''//$NON-NLS-«xptSelf»$'''

	/**
	 * Generates nonNLS(n) a line of java code by counting the quoted strings.
	 */
	def nonNLS(String toExternalize) '''«var count = countQuotedStrings(toExternalize)»«IF count > 0»«FOR i : 1..count SEPARATOR ' '»«nonNLS(i)»«ENDFOR»«ENDIF»'''

	/**
	 * Counts quoted strings.
	 */
	def countQuotedStrings(String toCount) {
		Pattern.compile('"([^"]*)"').matcher(toCount).results.count.intValue;
	}

	/**
	 * XXX:[MG] move this to VIDRegistry(?)
	 */

	/**
	 * Provides handy single point to override generation of assert statements
	 * TODO refactor this Common.xpt into different flavours - like CommonCodeStyle (nls, assert), CommonSnippets(pkgStmt, setCharset, getSaveOptions) and so on
	 * TODO condition.xpandToCharList()->count('"') / 2 gives better guess about number of nonNLS to generate
	 */ 
	def _assert(String condition) //
	'''assert «condition»;«IF condition.indexOf('\"') > 0» «nonNLS»«ENDIF»'''

	def CharSequence addShortcutAnnotation(GenDiagram it, String viewVar) '''
		org.eclipse.emf.ecore.EAnnotation shortcutAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEAnnotation();
		shortcutAnnotation.setSource("Shortcut"); «nonNLS()»
		shortcutAnnotation.getDetails().put("modelID", «VisualIDRegistry::modelID(it)»); «nonNLS()»
		«viewVar».getEAnnotations().add(shortcutAnnotation);
	'''


	def tripleSpace(int amount) {
		var b = new StringBuilder;
		var counter = 0;
		while(counter < amount) {
			b.append('   ');
			counter = counter + 1;
		}
		return b.toString;
	}

	def String stringVisualID(GenCommonBase it) {
		if (visualIDOverride !== null)
			visualIDOverride
		else
			visualID.toString
	}

	def String stringUniqueIdentifier(GenCommonBase it) {
		if (visualIDOverride !== null)
			visualIDOverride
		else
			it.uniqueIdentifier
	}
}

