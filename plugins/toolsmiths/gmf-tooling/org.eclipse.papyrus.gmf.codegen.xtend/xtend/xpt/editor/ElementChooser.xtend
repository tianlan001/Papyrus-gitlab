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
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import plugin.Activator
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvtoimport xpt.CodeStyle

@com.google.inject.Singleton class ElementChooser {
	@Inject extension Common;
	@Inject extension CodeStyle;
	@Inject extension ExternalizerUtils_qvto;
	@Inject Externalizer xptExternalizer;
	@Inject Activator xptActivator;

	def className(GenDiagram it) '''«it.elementChooserClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.gmf.tooling.runtime.part.DefaultElementChooserDialog'''

	def ElementChooser(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«constructors(it)»

			«context(it)»
		}
	'''

	def constructors(GenDiagram it)'''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.swt.widgets.Shell parentShell, org.eclipse.gmf.runtime.notation.View view) {
			this(parentShell, view, false);
		}

		«generatedMemberComment»
		public «className(it)»(org.eclipse.swt.widgets.Shell parentShell, org.eclipse.gmf.runtime.notation.View view, boolean allowMultiSelection) {
			super(parentShell, view, new «contextClassName(it)»(allowMultiSelection));
		}
	'''

	def contextClassName(GenDiagram it)'''«className(it)»ContextImpl'''

	def context(GenDiagram it)'''
		«generatedClassComment»
		private static class «contextClassName(it)» implements org.eclipse.gmf.tooling.runtime.part.DefaultElementChooserDialog.Context {

			«context_attributes(it)»

			«context_constructors(it)»

			«getItemProvidersAdapterFactory(it)»

			«getDiagramPreferencesHint(it)»

			«getFileExtensions(it)»

			«getDialogTitle(it)»

			«getTreeContentProvider(it)»

			«allowMultiSelection(it)»
		}
	'''

	def context_attributes(GenDiagram it)'''
		«generatedMemberComment»
		private static final String[] FILE_EXTENSIONS = new String[]{
			«FOR ext : containsShortcutsTo SEPARATOR ', '»"«ext»"«ENDFOR»
		};

		«generatedMemberComment»
		private final boolean myAllowMultiSelection;
	'''

	def context_constructors(GenDiagram it)'''
		«generatedMemberComment»
		private «contextClassName»(boolean allowMultiSelection) {
			myAllowMultiSelection = allowMultiSelection;
		}
	'''

	def getItemProvidersAdapterFactory(GenDiagram it)'''
		«generatedMemberComment»
		«overrideI(it)»
		public org.eclipse.emf.common.notify.AdapterFactory getAdapterFactory() {
			return «xptActivator.qualifiedClassName(it.editorGen.plugin)».getInstance().getItemProvidersAdapterFactory();
		}
	'''

	def getDiagramPreferencesHint(GenDiagram it)'''
		«generatedMemberComment»
		«overrideI(it)»
		public org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint getPreferenceHint() {
			return «xptActivator.qualifiedClassName(it.editorGen.plugin)».DIAGRAM_PREFERENCES_HINT;
		}
	'''

	def getFileExtensions(GenDiagram it)'''
		«generatedMemberComment»
		«overrideI(it)»
		public String[] getFileExtesions() {
			return FILE_EXTENSIONS.clone();
		}
	'''

	def getDialogTitle(GenDiagram it)'''
		«generatedMemberComment»
		«overrideI(it)»
		public String getDialogTitle() {
			return «xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForSelectModelElement(it)))»;
		}
	'''

	def getTreeContentProvider(GenDiagram it)'''
		«generatedMemberComment»
		«overrideI(it)»
		public org.eclipse.jface.viewers.ITreeContentProvider getTreeContentProvider() {
			return new «IF it.editorGen.application !== null »new org.eclipse.ui.model.WorkbenchContentProvider«ELSE»
			 org.eclipse.ui.model.BaseWorkbenchContentProvider«ENDIF»();
		}
	'''

	def allowMultiSelection(GenDiagram it)'''
		«generatedMemberComment»
		public boolean allowMultiSelection() {
			return myAllowMultiSelection;
		}
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForSelectModelElement(it)), 'Select model element')»
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForSelectModelElement(it)))»
	'''

	@Localization def String i18nKeyForSelectModelElement(GenDiagram diagram) {
		return i18nKeyForElementChooser(diagram) + '.SelectModelElement'
	}

	@Localization def String i18nKeyForElementChooser(GenDiagram diagram) {
		return '' + className(diagram)
	}

}
