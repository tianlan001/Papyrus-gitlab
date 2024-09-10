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
package xpt.diagram.preferences

import com.google.inject.Inject
import impl.preferences.CustomPage
import impl.preferences.StandardPage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenColor
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenConstantColor
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomFont
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramPreferences
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFont
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenRGBColor
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardFont
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPreferencePage

import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.Utils_qvto
import com.google.inject.Singleton

@Singleton class PreferenceInitializer {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;
	@Inject extension CodeStyle;

	@Inject Activator xptActivator;
	@Inject CustomPage xptCustomPage;
	@Inject StandardPage xptStandardPage;

	def className(GenDiagram it) '''DiagramPreferenceInitializer'''

	def packageName(GenDiagram it) '''«preferencesPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def PreferenceInitializer(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» extends org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer {

			«generatedMemberComment»
			«overrideC»
			public void initializeDefaultPreferences() {
				org.eclipse.jface.preference.IPreferenceStore store = getPreferenceStore();
				«IF it.preferences === null »
					«FOR pref : allPreferencePages(it)»
						«initDefaults(pref, 'store')»
					«ENDFOR»
				«ELSE/*default values for predefined pages will be set manually */»
					«FOR pref : allPreferencePages(it).filter(typeof(GenCustomPreferencePage))»
						«initDefaults(pref, 'store')»
					«ENDFOR»
					«initDefaults(it.preferences, 'store')»
				«ENDIF»
			}

			«generatedMemberComment»
			protected org.eclipse.jface.preference.IPreferenceStore getPreferenceStore() {
				return «xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getPreferenceStore();
			}
		} 
	'''

	def dispatch initDefaults(GenPreferencePage it, String storeVar) '''«ERROR('Abstract define for ' + it)»'''

	def dispatch initDefaults(GenStandardPreferencePage it, String storeVar) '''
		«xptStandardPage.call_initDefaults(it, storeVar)»
	'''

	def dispatch initDefaults(GenCustomPreferencePage it, String storeVar) '''
		«xptCustomPage.call_initDefaults(it, storeVar)»
	'''

	def dispatch initDefaults(GenDiagramPreferences it, String storeVar) '''
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SHOW_CONNECTION_HANDLES, «showConnectionHandles»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SHOW_POPUP_BARS, «showPopupBars»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_ENABLE_ANIMATED_LAYOUT, «enableAnimatedLayout»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_ENABLE_ANIMATED_ZOOM, «enableAnimatedZoom»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_ENABLE_ANTIALIAS, «enableAntiAlias»);
		«initDefaultFont(storeVar, 'PREF_DEFAULT_FONT', defaultFont)»
		«initDefaultColor(storeVar, 'PREF_FONT_COLOR', fontColor)»
		«initDefaultColor(storeVar, 'PREF_FILL_COLOR', fillColor)»
		«initDefaultColor(storeVar, 'PREF_LINE_COLOR', lineColor)»
		«initDefaultColor(storeVar, 'PREF_NOTE_FILL_COLOR', noteFillColor)»
		«initDefaultColor(storeVar, 'PREF_NOTE_LINE_COLOR', noteLineColor)»
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_LINE_STYLE, org.eclipse.gmf.runtime.notation.Routing.«lineStyle»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SHOW_RULERS, «showRulers»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_RULER_UNITS, org.eclipse.gef.rulers.RulerProvider.UNIT_«rulerUnits»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SHOW_GRID, «showGrid»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SNAP_TO_GRID, «snapToGrid»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_SNAP_TO_GEOMETRY, «snapToGeometry»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_GRID_SPACING, «gridSpacing»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_PROMPT_ON_DEL_FROM_MODEL, «promptOnDelFromModel»);
		«storeVar».setDefault(org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.PREF_PROMPT_ON_DEL_FROM_DIAGRAM, «promptOnDelFromDiagram»);
	'''

	def initDefaultColor(String storeVar, String prefName, GenColor color) '''
		«IF color !== null »
			org.eclipse.jface.preference.PreferenceConverter.setDefault(«storeVar», org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.«prefName», «rgb(color)»);
		«ENDIF»
	'''

	def initDefaultFont(String storeVar, String prefName, GenFont font) '''
		«IF font !== null »
			org.eclipse.jface.preference.PreferenceConverter.setDefault(«storeVar», org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants.«prefName», «fontData(font)»);
		«ENDIF»
	'''

	def dispatch rgb(GenColor it) '''«ERROR('Unknown color: ' + it)»'''

	def dispatch rgb(GenConstantColor it) '''
		org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants.«name».getRGB()
	'''

	def dispatch rgb(GenRGBColor it) '''
		new org.eclipse.swt.graphics.RGB(«red», «green», «blue»)
	'''

	def dispatch fontData(GenFont it) '''«ERROR('Unknown font: ' + it)»'''

	def dispatch fontData(GenCustomFont it) '''
		new org.eclipse.swt.graphics.FontData("«name»", «height», org.eclipse.swt.SWT.«style»)
	'''

	def dispatch fontData(GenStandardFont it) '''
		org.eclipse.jface.resource.JFaceResources.get«name»Font().getFontData()
	'''

}
