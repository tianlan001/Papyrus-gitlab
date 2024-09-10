/*****************************************************************************
 * Copyright (c) 2006, 2009, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 explicit call to the parent page on clean up
 *****************************************************************************/
package impl.preferences

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.StandardPreferencePages
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import xpt.Common

@com.google.inject.Singleton class StandardPage {
	@Inject extension Common;

	def className(GenStandardPreferencePage it) '''«getClassName()»'''

	def packageName(GenStandardPreferencePage it) '''«getDiagram().preferencesPackageName»'''

	def qualifiedClassName(GenStandardPreferencePage it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenStandardPreferencePage it) '''«qualifiedClassName(it)»'''

	def Main(GenStandardPreferencePage it) '''
		«copyright(it.diagram.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«generatedMemberComment»
			public «className(it)»() {
				setPreferenceStore(«getDiagram().editorGen.plugin.activatorQualifiedClassName».getInstance().getPreferenceStore());
				«IF StandardPreferencePages.GENERAL_LITERAL == kind»
					setPreferenceKey(«getDiagram().editPartsPackageName».«getDiagram().editPartClassName».MODEL_ID);
				«ENDIF»
			}
		}
	'''

	def extendsList(GenStandardPreferencePage it) '''
		«IF kind == StandardPreferencePages::GENERAL_LITERAL»
			extends org.eclipse.papyrus.infra.gmfdiag.preferences.pages.DiagramPreferencePage«»
		«ELSEIF kind == StandardPreferencePages::APPEARANCE_LITERAL»
			extends org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage«»
		«ELSEIF kind == StandardPreferencePages::CONNECTIONS_LITERAL»
			extends org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage«»
		«ELSEIF kind == StandardPreferencePages::PRINTING_LITERAL»
			extends org.eclipse.gmf.runtime.diagram.ui.preferences.PrintingPreferencePage«»
		«ELSEIF kind == StandardPreferencePages::RULERS_AND_GRID_LITERAL»
			extends org.eclipse.gmf.runtime.diagram.ui.preferences.RulerGridPreferencePage«»
		«ENDIF»
	'''
	
	/**
	 *  Bug 569174 : L1.2 clean up will generate an explicit call to the parent page on call_initDefaults
	 */
	def getParentPage(GenStandardPreferencePage it) {
		if (kind == StandardPreferencePages::GENERAL_LITERAL) {
			'org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage'
		} else if (kind == StandardPreferencePages::APPEARANCE_LITERAL) {
			'org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage'
		} else if (kind == StandardPreferencePages::CONNECTIONS_LITERAL) {
			'org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage'
		} else if (kind == StandardPreferencePages::PRINTING_LITERAL) {
			'org.eclipse.gmf.runtime.diagram.ui.preferences.PrintingPreferencePage'
		} else if (kind == StandardPreferencePages::RULERS_AND_GRID_LITERAL) {
			'org.eclipse.gmf.runtime.diagram.ui.preferences.RulerGridPreferencePage'
		}
	}

	@MetaDef def call_initDefaults(GenStandardPreferencePage it, String storeVarName) //
	'''«IF kind != StandardPreferencePages::PATHMAPS_LITERAL»«parentPage».initDefaults(«storeVarName»);«ENDIF»''' // explicit call to the parent page on clean up
}
