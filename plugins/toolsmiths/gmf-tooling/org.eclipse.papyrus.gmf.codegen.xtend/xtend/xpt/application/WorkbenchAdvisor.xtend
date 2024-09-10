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

@com.google.inject.Singleton class WorkbenchAdvisor {
	
	@Inject extension Common;
	@Inject WorkbenchWindowAdvisor xptWorkbenchWindowAdvisor

	def className(GenApplication it) '''«it.workbenchAdvisorClassName»'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def WorkbenchAdvisor(GenApplication it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» extends org.eclipse.ui.application.WorkbenchAdvisor {
			«perspectiveId(it)»
			
			«getInitialWindowPerspectiveId(it)»
			
			«initialize(it)»
			
			«createWorkbenchWindowAdvisor(it)»
			
			«additions(it)»
		}
	'''

	def perspectiveId(GenApplication it) '''
		«generatedMemberComment»
		public static final String PERSPECTIVE_ID = "«perspectiveId»"; «nonNLS(1)»
	'''

	def getInitialWindowPerspectiveId(GenApplication it) '''
		«generatedMemberComment»
		public String getInitialWindowPerspectiveId() {
			return PERSPECTIVE_ID;
		}
	'''

	def initialize(GenApplication it) '''
		«generatedMemberComment»
		public void initialize(org.eclipse.ui.application.IWorkbenchConfigurer configurer) {
			super.initialize(configurer);
			configurer.setSaveAndRestore(true);
		}
	'''

	def createWorkbenchWindowAdvisor(GenApplication it) '''
		«generatedMemberComment»
		public org.eclipse.ui.application.WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
				org.eclipse.ui.application.IWorkbenchWindowConfigurer configurer) {
			return new «xptWorkbenchWindowAdvisor.qualifiedClassName(it)»(configurer);
		}
	'''

	def additions(GenApplication it) ''''''

}
