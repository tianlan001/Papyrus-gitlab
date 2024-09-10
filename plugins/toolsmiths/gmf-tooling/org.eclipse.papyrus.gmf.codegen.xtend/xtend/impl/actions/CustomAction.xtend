/*******************************************************************************
 * Copyright (c) 2008, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package impl.actions

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomAction
import xpt.Common
import xpt.Common_qvto

@com.google.inject.Singleton class CustomAction {

	@Inject extension Common;
	@Inject extension Common_qvto;

	def className(GenCustomAction it) '''«lastSegment(it.qualifiedClassName)»'''

	def packageName(GenCustomAction it) '''«withoutLastSegment(qualifiedClassName)»'''

	def qualifiedClassName(GenCustomAction it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenCustomAction it) '''«qualifiedClassName(it)»'''

	def Main(GenCustomAction it) '''
		«copyright(it.owner.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment()»
		public class «className(it)» extends org.eclipse.core.commands.AbstractHandler {
		
			«generatedMemberComment()»
			public Object execute(org.eclipse.core.commands.ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
				org.eclipse.ui.IEditorPart diagramEditor = org.eclipse.ui.handlers.HandlerUtil.getActiveEditorChecked(event);
				org.eclipse.jface.viewers.ISelection selection = org.eclipse.ui.handlers.HandlerUtil.getCurrentSelectionChecked(event);
				// FIXME implement required behavior
				throw new UnsupportedOperationException();
			}
		}
	'''

}
