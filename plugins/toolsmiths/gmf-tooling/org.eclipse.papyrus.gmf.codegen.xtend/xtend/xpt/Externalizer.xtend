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
 *	Anna Karjakina (Borland) - initial API and implementation
 *	Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *	Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import impl.actions.CreateShortcutAction
import xpt.editor.CreationWizard
import xpt.editor.CreationWizardPage
import xpt.editor.DiagramEditorUtil
import xpt.editor.DocumentProvider
import xpt.editor.InitDiagramFileAction
import xpt.editor.NewDiagramFileWizard
import xpt.editor.Editor
import xpt.editor.ElementChooser
import xpt.editor.ShortcutCreationWizard
import xpt.editor.ModelElementSelectionPage
import xpt.editor.ValidateAction
import xpt.editor.palette.PaletteFactory
import xpt.application.ActionBarAdvisor
import xpt.application.WorkbenchWindowAdvisor
import xpt.application.WizardNewFileCreationPage
import impl.diagram.editparts.CompartmentEditPart
import xpt.diagram.editpolicies.OpenDiagram
import xpt.navigator.NavigatorContentProvider
import xpt.navigator.NavigatorActionProvider
import parsers.PredefinedParser
import xpt.providers.ModelingAssistantProvider

@com.google.inject.Singleton class Externalizer {
	@Inject extension Common;
	@Inject extension ExternalizerUtils_qvto;

	@Inject CreateShortcutAction createShortcutAction;
	@Inject CreationWizard creationWizard;
	@Inject CreationWizardPage creationWizardPage;
	@Inject DiagramEditorUtil diagramEditorUtil;
	@Inject DocumentProvider documentProvider;
	@Inject InitDiagramFileAction initDiagramFileAction;
	@Inject NewDiagramFileWizard newDiagramFileWizard;
	@Inject Editor editor;
	@Inject ElementChooser elementChooser;
	@Inject ShortcutCreationWizard shortcutCreationWizard;
	@Inject ModelElementSelectionPage modelElementSelectionPage;
	@Inject ValidateAction validateAction;
	@Inject PaletteFactory paletteFactory;
	@Inject ActionBarAdvisor actionBarAdvisor;
	@Inject WorkbenchWindowAdvisor workbenchWindowAdvisor;
	@Inject WizardNewFileCreationPage wizardNewFileCreationPage;
	@Inject CompartmentEditPart compartmentEditPart;
	@Inject OpenDiagram openDiagram;
	@Inject NavigatorContentProvider navigatorContentProvider;
	@Inject NavigatorActionProvider navigatorActionProvider;
	@Inject PredefinedParser predefinedParser;
	@Inject ModelingAssistantProvider modelingAssistantProvider;

	@MetaDef def accessorCall(GenEditorGenerator it, String key) //
		'''«it.externalizerPackageName».«accessClassName(it)».«escapeIllegalKeySymbols(key)»'''

	@MetaDef def accessorField(String key) '''

		«generatedMemberComment»
		public static String «escapeIllegalKeySymbols(key)»;
	'''

	def messageEntry(String key, String message) '''
		«escapeIllegalKeySymbols(key)»=«escapeIllegalMessageSymbols(message)»
	'''

	def accessClassName(GenEditorGenerator it) '''«getExternalizerClassName()»'''
	def accessPackageName(GenEditorGenerator it) '''«getExternalizerPackageName(it)»'''

	def Access(GenEditorGenerator it) '''
		«copyright(it)»
		package «accessPackageName(it)»;

		«generatedClassComment»
		public class «accessClassName(it)» extends org.eclipse.osgi.util.NLS {

			«generatedMemberComment»
			static {
				org.eclipse.osgi.util.NLS.initializeMessages("«accessClassName(it).toString().toLowerCase»", «accessClassName(it)».class); «nonNLS»
			}

			«generatedMemberComment»
			private «accessClassName(it)»() {
			}
			«Fields»

			// TODO: put accessor fields manually
		}
	'''

	def Fields(GenEditorGenerator it)'''
		«createShortcutAction.i18nAccessors(diagram)»
		«creationWizard.i18nAccessors(diagram)»
		«creationWizardPage.i18nAccessors(diagram)»
		«diagramEditorUtil.i18nAccessors(diagram)»
		«documentProvider.i18nAccessors(diagram)»
		«initDiagramFileAction.i18nAccessors(diagram)»
		«newDiagramFileWizard.i18nAccessors(diagram)»
		«editor.i18nAccessors(it.editor)»
		«elementChooser.i18nAccessors(diagram)»
		«shortcutCreationWizard.i18nAccessors(diagram)»
		«modelElementSelectionPage.i18nAccessors(diagram)»
		«validateAction.i18nAccessors(diagram)»
		«paletteFactory.i18nAccessors(diagram.palette)»
		«IF application !== null »
			«actionBarAdvisor.i18nAccessors(application)»
			«workbenchWindowAdvisor.i18nAccessors(application)»
			«wizardNewFileCreationPage.i18nAccessors(application)»
		«ENDIF»
		«compartmentEditPart.i18nAccessors(diagram)»
		«openDiagram.i18nAccessors(diagram)»
		«IF navigator !== null »
			«navigatorContentProvider.i18nAccessors(navigator)»
			«navigatorActionProvider.i18nAccessors(navigator)»
		«ENDIF»
		«IF labelParsers !== null »«predefinedParser.i18nAccessors(labelParsers)»«ENDIF»
		«modelingAssistantProvider.i18nAccessors(diagram)»
	'''

	def Values(GenEditorGenerator it)'''

		# TODO: manually put keys and values
		«createShortcutAction.i18nValues(diagram)»
		«creationWizard.i18nValues(diagram)»
		«creationWizardPage.i18nValues(diagram)»
		«diagramEditorUtil.i18nValues(diagram)»
		«documentProvider.i18nValues(diagram)»
		«initDiagramFileAction.i18nValues(diagram)»
		«newDiagramFileWizard.i18nValues(diagram)»
		«editor.i18nValues(it.editor)»
		«elementChooser.i18nValues(diagram)»
		«shortcutCreationWizard.i18nValues(diagram)»
		«modelElementSelectionPage.i18nValues(diagram)»
		«validateAction.i18nValues(diagram)»
		«paletteFactory.i18nValues(diagram.palette)»
		«IF application !== null »
			«actionBarAdvisor.i18nValues(application)»
			«workbenchWindowAdvisor.i18nValues(application)»
			«wizardNewFileCreationPage.i18nValues(application)»
		«ENDIF»
		«compartmentEditPart.i18nValues(diagram)»
		«openDiagram.i18nValues(diagram)»
		«IF navigator !== null »
			«navigatorContentProvider.i18nValues(navigator)»
			«navigatorActionProvider.i18nValues(navigator)»
		«ENDIF»
		«IF labelParsers !== null »«predefinedParser.i18nValues(labelParsers)»«ENDIF»
		«modelingAssistantProvider.i18nValues(diagram)»
	'''
}
