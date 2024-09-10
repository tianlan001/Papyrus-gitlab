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
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Externalizer

@com.google.inject.Singleton class WorkbenchWindowAdvisor {
	@Inject extension Common;
	
	@Inject Externalizer xptExternalizer;
	@Inject ActionBarAdvisor xptActionBarAdvisor;

	def className(GenApplication it) '''«it.workbenchWindowAdvisorClassName»'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def WorkbenchWindowAdvisor(GenApplication it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» extends org.eclipse.ui.application.WorkbenchWindowAdvisor {
		
			«generatedMemberComment»
			public «className(it)»(org.eclipse.ui.application.IWorkbenchWindowConfigurer configurer) {
				super(configurer);
			}
		
			«createActionBarAdvisor(it)»
		
			«preWindowOpen(it)»
		
			«additions(it)»
		}
	'''

	def createActionBarAdvisor(GenApplication it) '''
		«generatedMemberComment»
		public org.eclipse.ui.application.ActionBarAdvisor createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer configurer) {
			return new «xptActionBarAdvisor.qualifiedClassName(it)»(configurer);
		}
	'''

	def preWindowOpen(GenApplication it) '''
		«generatedMemberComment»
		public void preWindowOpen() {
			org.eclipse.ui.application.IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
			configurer.setInitialSize(new org.eclipse.swt.graphics.Point(1000, 700));
			configurer.setTitle(«xptExternalizer.accessorCall(editorGen, i18nKeyForWindowTitle(it))»);
		}
	'''

	def additions(GenApplication it) ''''''

	@Localization
	def i18nAccessors(GenApplication it) '''
		«xptExternalizer.accessorField(i18nKeyForWindowTitle(it))»
	'''

	@Localization def i18nValues(GenApplication it) '''
		«xptExternalizer.messageEntry(i18nKeyForWindowTitle(it), title)»
	'''

	@Localization protected def String i18nKeyForWindowTitle(GenApplication app) {
		return className(app) + '.Title'
	}

}
