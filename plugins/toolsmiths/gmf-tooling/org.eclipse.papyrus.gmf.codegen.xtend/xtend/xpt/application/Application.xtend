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

@com.google.inject.Singleton class Application {
	@Inject extension Common;
	@Inject WorkbenchAdvisor xptWorkbenchAdvisor

	def className(GenApplication it) '''«it.className»'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def Application(GenApplication it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» implements org.eclipse.equinox.app.IApplication {
			
			«run(it)»
			
			«generatedMemberComment»
			public void stop() {
			}
			
			«additions(it)»
		}
	'''

	def run(GenApplication it) '''
		
		«generatedMemberComment»
		public Object start(org.eclipse.equinox.app.IApplicationContext context) throws Exception {
			org.eclipse.swt.widgets.Display display = org.eclipse.ui.PlatformUI.createDisplay();
			try {
				int returnCode = org.eclipse.ui.PlatformUI.createAndRunWorkbench(display,
					new «xptWorkbenchAdvisor.qualifiedClassName(it)»());
				if (returnCode == org.eclipse.ui.PlatformUI.RETURN_RESTART) {
					return org.eclipse.equinox.app.IApplication.EXIT_RESTART;
				}
				return org.eclipse.equinox.app.IApplication.EXIT_OK;
			} finally {
				display.dispose();
			}
		}
	'''

	def additions(GenApplication it) ''''''

}
