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
 *	 Artem Tikhomirov (Borland) - initial API and implementation
 *	 Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	 Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *	 Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers 
 *****************************************************************************/
package impl.actions

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAction
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommandAction
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContextMenu
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionItem
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionManager
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomAction
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenGroupMarker
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMenuManager
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenSeparator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenToolBarManager
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.editparts.EditPartFactory

@com.google.inject.Singleton class extensions {
	@Inject extension Common_qvto;
	@Inject extension Common;
	@Inject extension MenuAction_qvto;
	
	@Inject EditPartFactory xptEditPartFactory;

	//[MG] why different pattern here, @Inject extension is normally usd for QCNP
	@Inject PredefinedAction predefinedAction;
	
	def Main(GenEditorGenerator it) '''
		«tripleSpace(1)»<extension point="org.eclipse.ui.menus" id="context-menus">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<!-- menuContribution locationURI="menu:org.eclipse.ui.main.menu?after=">
		«tripleSpace(2)»</menuContribution>
		«tripleSpace(2)»<menuContribution locationURI="toolbar:org.eclipse.ui.main.toolbar?after=">
		«tripleSpace(2)»</menuContribution -->
			«FOR cm : contextMenus»
		«menuContribution(cm)»
			«ENDFOR»
		«tripleSpace(1)»</extension>
		
		«IF hasCommandsToContribute(it)»
			«tripleSpace(1)»<extension point="org.eclipse.ui.commands" id="menu-commands">
			«tripleSpace(2)»«xmlGeneratedTag»
			«tripleSpace(2)»<category id="«editor.ID»" name="%cmdcategory.name" description="%cmdcategory.desc"/>
				«FOR cm : contextMenus»
			«tripleSpace(2)»«commandContribution(cm)»
				«ENDFOR»
			«tripleSpace(1)»</extension>
		«ENDIF»
		«IF hasHandlersToContribute(it)»
			«tripleSpace(1)»<extension point="org.eclipse.ui.handlers" id="menu-handlers">
			«tripleSpace(2)»«xmlGeneratedTag»
				«FOR cm : contextMenus»
			«handlerContribution(cm)»
				«ENDFOR»
			«tripleSpace(1)»</extension>
		«ENDIF»
		
		«tripleSpace(1)»<!-- optionally, specify keybindings -->
	'''

	def menuContribution(GenContextMenu it) '''
		«menuContribution3(it, it, 'popup:org.eclipse.gmf.runtime.diagram.ui.DiagramEditorContextMenu')»
	'''

	def dispatch CharSequence menuContribution(GenContributionManager it, GenContextMenu contextMenu) '''«ERROR(
		'Abstract menuContribution for ' + it)»'''

	def dispatch CharSequence menuContribution(GenMenuManager it, GenContextMenu contextMenu) '''«menuContribution3(it,
		contextMenu, 'popup:' + it.ID)»'''

	def dispatch CharSequence menuContribution(GenToolBarManager it, GenContextMenu contextMenu) '''«menuContribution3(
		it, contextMenu, 'toolbar:' + it.ID)»'''

	def CharSequence menuContribution3(GenContributionManager it, GenContextMenu contextMenu, String locationURI) '''
		«tripleSpace(2)»<menuContribution locationURI="«locationURI»">
		«FOR i : items»
			«menuEntry(i, contextMenu)»
		«ENDFOR»
		«tripleSpace(2)»</menuContribution>
		«FOR m : items.filter(typeof(GenContributionManager))»
			«menuContribution(m, contextMenu)»
		«ENDFOR»
	'''

	def dispatch CharSequence commandContribution(GenContributionManager it) '''
		«FOR ca : items.filter(typeof(GenCustomAction))»
			«commandContribution(ca)»
		«ENDFOR»
		«FOR a : items.filter(typeof(GenAction))»
			«commandContribution(a)»
		«ENDFOR»
		«FOR cm : items.filter(typeof(GenContributionManager))»
			«commandContribution(cm)»
		«ENDFOR»
	'''

	def dispatch CharSequence handlerContribution(GenContributionManager it) '''
		«FOR ca : items.filter(typeof(GenCustomAction))»
			«handlerContribution(ca)»
		«ENDFOR»
		«FOR cm : items.filter(typeof(GenContributionManager))»
			«handlerContribution(cm)»
		«ENDFOR»
	'''

	def dispatch CharSequence commandContribution(GenCustomAction it) '''
		<command id="«commandIdentifier(it)»"
		«tripleSpace(3)»name="«name»"
		«tripleSpace(3)»categoryId="«owner.editorGen.editor.ID»"/>
	'''

	def dispatch CharSequence commandContribution(GenAction it) '''
		<command id="«commandIdentifier(it)»"
		«tripleSpace(3)»name="«name/*FIXME: into i18n keys*/»"
		«tripleSpace(3)»categoryId="«owner.editorGen.editor.ID»"
		«tripleSpace(3)»defaultHandler="«predefinedAction.qualifiedClassName(it)»"/>
	'''

	def dispatch CharSequence handlerContribution(GenCustomAction it) '''
		«tripleSpace(2)»<handler
		«tripleSpace(4)»commandId="«commandIdentifier(it)»"
		«tripleSpace(4)»class="«qualifiedClassName»">
		«tripleSpace(3)»<enabledWhen>
		«tripleSpace(4)»<with variable="activePartId"><equals value="«owner.editorGen.editor.ID»"/></with>
		«tripleSpace(3)»</enabledWhen>
		«tripleSpace(2)»</handler>
	'''

	def dispatch CharSequence commandIdentifier(GenCustomAction it) '''«owner.editorGen.plugin.ID».«lastSegment(
		qualifiedClassName)»'''

	def dispatch CharSequence commandIdentifier(GenAction it) '''«owner.editorGen.plugin.ID».«predefinedAction.className(it)»'''

	def dispatch CharSequence menuEntry(GenContributionItem it, GenContextMenu contextMenu) '''«/* NO-OP XXX or ERROR? */»'''

	def dispatch CharSequence menuEntry(GenSeparator it, GenContextMenu contextMenu) '''
		<separator name=«IF groupName !== null»"«groupName»"«ELSE»"withoutname"«ENDIF» visible="true"/>
	'''

	def dispatch CharSequence menuEntry(GenGroupMarker it, GenContextMenu contextMenu) '''
		<separator name="«groupName»"/>
	'''

	def dispatch CharSequence menuEntry(GenCommandAction it, GenContextMenu contextMenu) '''
		«tripleSpace(3)»<command commandId="«commandIdentifier»">
		«tripleSpace(4)»<visibleWhen>
		«menuCondition(contextMenu)»
		«tripleSpace(4)»</visibleWhen>
		«tripleSpace(3)»</command>
	'''

	def dispatch CharSequence menuEntry(GenCustomAction it, GenContextMenu contextMenu) '''
		«tripleSpace(3)»<command commandId="«commandIdentifier(it)»">
		«tripleSpace(4)»<visibleWhen>
		«menuCondition(contextMenu)»
		«tripleSpace(4)»</visibleWhen>
		«tripleSpace(3)»</command>
	'''

	def dispatch CharSequence menuEntry(GenAction it, GenContextMenu contextMenu) '''
		«tripleSpace(3)»<command commandId="«commandIdentifier(it)»">
		«tripleSpace(4)»<visibleWhen>
		«menuCondition(contextMenu)»
		«tripleSpace(4)»</visibleWhen>
		«tripleSpace(3)»</command>
	'''

	def dispatch CharSequence menuEntry(GenMenuManager it, GenContextMenu contextMenu) '''
		«tripleSpace(3)»<menu id="«it.ID»" label="«name»">
		«tripleSpace(4)»<visibleWhen>
		«menuCondition(contextMenu)»
		«tripleSpace(4)»</visibleWhen>
		«tripleSpace(3)»</menu>
	'''

	def menuCondition(GenContextMenu it) '''
		«tripleSpace(5)»<and>
		«tripleSpace(6)»<with variable="activePartId"><equals value="«editorGen.editor.ID»"/></with>
		«tripleSpace(6)»<with variable="selection"><iterate ifEmpty="false">«IF context.size > 1»<or>«ENDIF» 
			«/* XXX, perhaps, <adapt type="EditPart">? */FOR de : context»
		«tripleSpace(7)»<instanceof value="«xptEditPartFactory.getEditPartQualifiedClassName(de)»"/>
			«ENDFOR»
		«tripleSpace(6)»«IF context.size > 1»</or>«ENDIF»</iterate></with>
		«tripleSpace(5)»</and>
	'''

	@Localization def i18n(GenEditorGenerator it) '''
		«IF hasCommandsToContribute(it)»
			# Commands and menu actions
			cmdcategory.name=«modelID» Editor Commands
			cmdcategory.desc=«modelID» Editor Commands
		«ENDIF»
	'''

}
