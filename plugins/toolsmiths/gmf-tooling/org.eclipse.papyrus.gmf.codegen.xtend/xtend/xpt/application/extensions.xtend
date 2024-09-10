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
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.application

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication
import xpt.Common

@com.google.inject.Singleton class extensions {
	@Inject extension Common;
	@Inject Application xptApplication
	@Inject Perspective xptPerspective
	@Inject ActionBarAdvisor xptActionBarAdvisor


	def extensions(GenApplication it) '''
	«IF it !== null »
		«tripleSpace(1)»<extension id="«ID»" point="org.eclipse.core.runtime.applications">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<application>
		«tripleSpace(3)»<run class="«xptApplication.qualifiedClassName(it)»"/>
		«tripleSpace(2)»</application>
		«tripleSpace(1)»</extension>
		«tripleSpace(1)»<extension point="org.eclipse.ui.perspectives" id="rcp-perspective">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<perspective
		«tripleSpace(3)»id="«perspectiveId»"
		«tripleSpace(3)»name="%perspectiveName"
		«tripleSpace(3)»class="«xptPerspective.qualifiedClassName(it)»">
		«tripleSpace(2)»</perspective>
		«tripleSpace(1)»</extension>
		«tripleSpace(2)»<extension point="org.eclipse.ui.commands" id="rcp-menu-commands">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<command
		«tripleSpace(3)»name="%openURIActionLabel"
		«tripleSpace(3)»description="%openURIActionDescription"
		«tripleSpace(3)»categoryId="org.eclipse.ui.category.file"
		«tripleSpace(3)»id="«editorGen.plugin.ID».OpenURICommand"/>  
		«tripleSpace(2)»<command
		«tripleSpace(3)»name="%openActionLabel"
		«tripleSpace(3)»description="%openActionDescription"
		«tripleSpace(3)»categoryId="org.eclipse.ui.category.file"
		«tripleSpace(3)»id="«editorGen.plugin.ID».OpenCommand"/>  
		«tripleSpace(1)»</extension>
		«tripleSpace(1)»
		«tripleSpace(1)»<extension point="org.eclipse.ui.bindings" id="rcp-command-bindings">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<key
		«tripleSpace(3)»commandId="«editorGen.plugin.ID».OpenURICommand"
		«tripleSpace(3)»sequence="M1+U"
		«tripleSpace(3)»schemeId="org.eclipse.ui.defaultAcceleratorConfiguration"/>
		«tripleSpace(2)»<key
		«tripleSpace(3)»commandId="«editorGen.plugin.ID».OpenCommand"
		«tripleSpace(3)»sequence="M1+O"
		«tripleSpace(3)»schemeId="org.eclipse.ui.defaultAcceleratorConfiguration"/>
		«tripleSpace(1)»</extension>
		
		«tripleSpace(1)»<extension point="org.eclipse.ui.actionSets" id="rcp-actions">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<actionSet
		«tripleSpace(4)»label="%applicationActionSetLabel"
		«tripleSpace(4)»visible="true"
		«tripleSpace(4)»id="«editorGen.plugin.ID».ActionSet">
		«tripleSpace(3)»<action
		«tripleSpace(5)»label="%newDiagramActionLabel"
		«tripleSpace(5)»class="«xptActionBarAdvisor.qualifiedClassName(it)»$NewDiagramAction"
		«tripleSpace(5)»menubarPath="file/new/additions"
		«tripleSpace(5)»id="«editorGen.plugin.ID».NewDiagramAction">
		«tripleSpace(3)»</action>
		«tripleSpace(3)»<action
		«tripleSpace(5)»label="%aboutActionLabel"
		«tripleSpace(5)»class="«xptActionBarAdvisor.qualifiedClassName(it)»$AboutAction"
		«tripleSpace(5)»menubarPath="help/additions"
		«tripleSpace(5)»id="«editorGen.plugin.ID».AboutAction">
		«tripleSpace(3)»</action>
		«tripleSpace(3)»<action
		«tripleSpace(5)»label="%openURIActionLabel"
		«tripleSpace(5)»definitionId="«editorGen.plugin.ID».OpenURICommand"
		«tripleSpace(5)»class="«xptActionBarAdvisor.qualifiedClassName(it)»$OpenURIAction"
		«tripleSpace(5)»menubarPath="file/additions"
		«tripleSpace(5)»id="«editorGen.plugin.ID».OpenURIAction">
		«tripleSpace(3)»</action>
		«tripleSpace(3)»<action
		«tripleSpace(5)»label="%openActionLabel"
		«tripleSpace(5)»definitionId="«editorGen.plugin.ID».OpenCommand"
		«tripleSpace(5)»class="«xptActionBarAdvisor.qualifiedClassName(it)»$OpenAction"
		«tripleSpace(5)»menubarPath="file/additions"
		«tripleSpace(5)»id="«editorGen.plugin.ID».OpenAction">
		«tripleSpace(3)»</action>
		«tripleSpace(2)»</actionSet>
		«tripleSpace(1)»</extension>
	«ENDIF»
	'''

}
