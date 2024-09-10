/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
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
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.application

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication
import xpt.Common

@com.google.inject.Singleton class Perspective {
	@Inject extension Common;
	@Inject WorkbenchAdvisor xptWorkbenchAdvisor;
	
	def className(GenApplication it) '''«it.perspectiveClassName»'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def Perspective(GenApplication it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» implements org.eclipse.ui.IPerspectiveFactory {
			«createInitialLayout(it)»
			«additions(it)»
		}
	'''

	def createInitialLayout(GenApplication it) '''
		«generatedMemberComment»
		public void createInitialLayout(org.eclipse.ui.IPageLayout layout) {
			layout.setEditorAreaVisible(true);
			layout.addPerspectiveShortcut(«xptWorkbenchAdvisor.qualifiedClassName(it)».PERSPECTIVE_ID);
			org.eclipse.ui.IFolderLayout right = layout.createFolder(
				"right", org.eclipse.ui.IPageLayout.RIGHT, 0.6f, layout.getEditorArea()); «nonNLS(1)»
			right.addView(org.eclipse.ui.IPageLayout.ID_OUTLINE);
			org.eclipse.ui.IFolderLayout bottomRight = layout.createFolder(
				"bottomRight", org.eclipse.ui.IPageLayout.BOTTOM, 0.6f, "right"); «nonNLS(1)»	 «nonNLS(2)»
			bottomRight.addView(org.eclipse.ui.IPageLayout.ID_PROP_SHEET);
			«layoutAdditions(it)»
		}
	'''

	def layoutAdditions(GenApplication it) ''''''

	def additions(GenApplication it) ''''''

}
