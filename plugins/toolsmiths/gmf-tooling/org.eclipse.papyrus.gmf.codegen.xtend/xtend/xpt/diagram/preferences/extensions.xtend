/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Thibault Landre (Atos Origin) - initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.diagram.preferences

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPreferencePage
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.diagram.Utils_qvto
import impl.preferences.CustomPage
import impl.preferences.StandardPage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPreferencePage
import utils.PrefsConstant_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.StandardPreferencePages

@com.google.inject.Singleton class extensions {
	@Inject extension Common;
	@Inject extension Utils_qvto;
	@Inject extension PrefsConstant_qvto;

	@Inject PreferenceInitializer xptPreferenceInitializer;
	@Inject CustomPage xptCustomPage;
	@Inject StandardPage xptStandardPage;

	def extensions(GenDiagram it) '''

		«tripleSpace(1)»<extension point="org.eclipse.core.runtime.preferences" id="prefs">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<initializer class="«xptPreferenceInitializer.qualifiedClassName(it)»"/>
		«tripleSpace(1)»</extension>

		«IF ! it.preferencePages.empty»
			«tripleSpace(1)»<extension point="org.eclipse.ui.preferencePages" id="prefpages">
			«tripleSpace(2)»«xmlGeneratedTag»
					«FOR pref : allPreferencePages(it)»
						«IF pref instanceof GenStandardPreferencePage»
					«papyrusPreferencePage(pref as GenStandardPreferencePage)»
				«ENDIF»
			«ENDFOR»
			«tripleSpace(1)»</extension>
		«ENDIF»
	'''

	def preferencePage(GenPreferencePage it) '''
		«tripleSpace(2)»<page
		«tripleSpace(4)»id="«ID»"
		«tripleSpace(4)»name="%prefpage.«ID»"
			«IF null !== parent »
		«tripleSpace(4)»category="«parent.ID»"
			«ELSEIF !parentCategory.nullOrEmpty»
		«tripleSpace(4)»category="«parentCategory»"
			«ENDIF»
		«tripleSpace(4)»class="«getQualifiedPageName(it)»">
		«tripleSpace(2)»</page>
	'''

	@Localization def i18n(GenDiagram it) '''
		# Preferences
		«FOR pref : allPreferencePages(it)»
			prefpage.«pref.ID»=«pref.name»
		«ENDFOR»
	'''
	def dispatch getQualifiedPageName(GenPreferencePage it) ''''''
	def dispatch getQualifiedPageName(GenCustomPreferencePage it) '''«xptCustomPage.qualifiedClassName(it)»'''
	def dispatch getQualifiedPageName(GenStandardPreferencePage it) '''«xptStandardPage.qualifiedClassName(it)»'''

	def papyrusPreferencePage(GenStandardPreferencePage it) '''
		«IF StandardPreferencePages.GENERAL_LITERAL == kind»
				<page
						id="«getDiagramPreferencePageCategory()».«getDiagram().editorGen.modelID»"
						name="«getDiagram().editorGen.modelID» Diagram"
						category="«getDiagramPreferencePageCategory()»"
						class="«getQualifiedClassName()»">
				</page>
				«ELSEIF StandardPreferencePages.PRINTING_LITERAL == kind || StandardPreferencePages.RULERS_AND_GRID_LITERAL == kind»
				<page
						id="«getQualifiedClassName()»"
						name="%prefpage.«ID»"
						category="«getDiagramPreferencePageCategory()».«getDiagram().editorGen.modelID»"
						class="«getQualifiedClassName()»">
				</page>
		«ENDIF»
	'''

}
