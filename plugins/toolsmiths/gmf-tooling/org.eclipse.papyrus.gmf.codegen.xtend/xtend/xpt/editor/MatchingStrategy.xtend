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
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common
import xpt.CodeStyle

@com.google.inject.Singleton class MatchingStrategy {
	@Inject extension Common;
	@Inject extension CodeStyle;

	def className(GenDiagram it) '''«it.matchingStrategyClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def implementsList(GenDiagram it) '''implements org.eclipse.ui.IEditorMatchingStrategy'''

	def MatchingStrategy(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «implementsList(it)» {

			«matches(it)»
		}
	'''

	/**
	 * FIXME: [MG] #175260 is fixed, remove the last check below
	 */
	def matches(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public boolean matches(org.eclipse.ui.IEditorReference editorRef, org.eclipse.ui.IEditorInput input) {
			org.eclipse.ui.IEditorInput editorInput;
			try {
				editorInput = editorRef.getEditorInput();
			} catch (org.eclipse.ui.PartInitException e) {
				return false;
			}

			if (editorInput.equals(input)) {
				return true;
			}
			«/*FIXME: [MG]
			 * Should be removed then https://bugs.eclipse.org/bugs/show_bug.cgi?id=175260 commited.
			 * Problem is: URIEditorInput has no .equals() overriden
			 */»if (editorInput instanceof org.eclipse.emf.common.ui.URIEditorInput && input instanceof org.eclipse.emf.common.ui.URIEditorInput) {
				return ((org.eclipse.emf.common.ui.URIEditorInput) editorInput).getURI().equals(((org.eclipse.emf.common.ui.URIEditorInput) input).getURI());
			}
			return false;
		}
	'''

}
