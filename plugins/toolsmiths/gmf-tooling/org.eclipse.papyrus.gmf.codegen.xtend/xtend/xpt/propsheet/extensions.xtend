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
package xpt.propsheet

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomTabFilter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPropertyTab
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertySheet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertyTab
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertyTabFilter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPropertyTab
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeTabFilter
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Common_qvto

@com.google.inject.Singleton class extensions {
	@Inject extension Common;
	@Inject extension Common_qvto;

	@Inject LabelProvider labelProvider;
	def extensions(GenPropertySheet it) '''
		«tripleSpace(1)»<extension point="org.eclipse.ui.views.properties.tabbed.propertyContributor" id="prop-contrib">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<propertyContributor
		«tripleSpace(4)»contributorId="«editorGen.plugin.ID»"
		«tripleSpace(4)»«IF needsCaption»labelProvider="«labelProvider.qualifiedClassName(it)»"«ENDIF»>
		«tripleSpace(3)»<propertyCategory category="domain"/>
		«tripleSpace(3)»<propertyCategory category="visual"/>
		«tripleSpace(3)»<propertyCategory category="extra"/>
		«tripleSpace(2)»</propertyContributor>
		«tripleSpace(1)»</extension>

		«tripleSpace(1)»<extension point="org.eclipse.ui.views.properties.tabbed.propertyTabs" id="proptabs">
		«tripleSpace(2)»«xmlGeneratedTag»   
		«tripleSpace(2)»<propertyTabs contributorId="«editorGen.plugin.ID»">
				«FOR t : tabs»
		«tab(t)»
				«ENDFOR»
		«tripleSpace(2)»</propertyTabs>
		«tripleSpace(1)»</extension>

		«tripleSpace(1)»<extension point="org.eclipse.ui.views.properties.tabbed.propertySections" id="propsections">
		«tripleSpace(2)»«xmlGeneratedTag»   
		«tripleSpace(2)»<propertySections contributorId="«editorGen.plugin.ID»">
				«FOR t : tabs»
		«section(t)»
				«ENDFOR»
		«tripleSpace(2)»</propertySections>
		«tripleSpace(1)»</extension>
	'''

	def dispatch tab(GenPropertyTab it) '''«ERROR('Unknown property tab: ' + it)»'''

	def dispatch tab(GenStandardPropertyTab it) '''
		«IF ID == 'appearance'»
		«tripleSpace(3)»<propertyTab
		«tripleSpace(4)» category="visual"
		«tripleSpace(4)» id="property.tab.AppearancePropertySection"
		«tripleSpace(4)» label="%tab.appearance"/>
		«ELSEIF ID == 'diagram'»
		«tripleSpace(3)» <propertyTab
		«tripleSpace(4)» category="visual"
		«tripleSpace(4)» id="property.tab.DiagramPropertySection"
		«tripleSpace(4)» label="%tab.diagram"/>
		«ELSEIF ID == 'advanced'»
		<propertyTab
		«tripleSpace(4)» category="extra"
		«tripleSpace(4)» id="property.tab.AdvancedPropertySection"
		«tripleSpace(4)» label="%tab.advanced"/>           
		«ENDIF»
	'''

	def dispatch tab(GenCustomPropertyTab it) '''
		«tripleSpace(3)» <propertyTab
			«IF ID == 'domain'»
		«tripleSpace(4)» category="domain"
			«ELSE»
		«tripleSpace(4)» category="extra"
			«ENDIF»
		«tripleSpace(4)» id="property.tab.«ID»"
		«tripleSpace(4)» label="%tab.«ID»"/>
	'''

	def dispatch section(GenPropertyTab it) '''«ERROR('Unknown property tab: ' + it)»'''

	def dispatch section(GenStandardPropertyTab it) '''
		«IF ID == 'appearance'»
		«tripleSpace(3)»<propertySection id="property.section.ConnectorAppearancePropertySection" 
		«tripleSpace(4)»filter="org.eclipse.gmf.runtime.diagram.ui.properties.filters.ConnectionEditPartPropertySectionFilter" 
		«tripleSpace(4)»class="org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.ConnectionAppearancePropertySection" 
		«tripleSpace(4)»tab="property.tab.AppearancePropertySection">
		«tripleSpace(3)»</propertySection>
		«tripleSpace(3)»<propertySection id="property.section.ShapeColorAndFontPropertySection" 
		«tripleSpace(4)»filter="org.eclipse.gmf.runtime.diagram.ui.properties.filters.ShapeEditPartPropertySectionFilter" 
		«tripleSpace(4)»class="org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.ShapeColorsAndFontsPropertySection" 
		«tripleSpace(4)»tab="property.tab.AppearancePropertySection">
		«tripleSpace(3)»</propertySection> 
		«tripleSpace(3)»<propertySection id="property.section.DiagramColorsAndFontsPropertySection" 
		«tripleSpace(4)»filter="org.eclipse.gmf.runtime.diagram.ui.properties.filters.DiagramEditPartPropertySectionFilter" 
		«tripleSpace(4)»class="org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.DiagramColorsAndFontsPropertySection" 
		«tripleSpace(4)»tab="property.tab.AppearancePropertySection">
		«tripleSpace(3)»</propertySection>     
		«ELSEIF ID == 'diagram'»
		«tripleSpace(3)»<propertySection id="property.section.RulerGridPropertySection" 
		«tripleSpace(4)»filter="org.eclipse.gmf.runtime.diagram.ui.properties.filters.DiagramEditPartPropertySectionFilter" 
		«tripleSpace(4)»class="org.eclipse.gmf.runtime.diagram.ui.properties.sections.grid.RulerGridPropertySection" 
		«tripleSpace(4)»tab="property.tab.DiagramPropertySection">
		«tripleSpace(3)»</propertySection>     
		«ELSEIF ID == 'advanced'»
		«tripleSpace(3)»<propertySection id="property.section.AdvancedPropertySection"
		«tripleSpace(4)»class="org.eclipse.gmf.runtime.diagram.ui.properties.sections.AdvancedPropertySection"
		«tripleSpace(4)»filter="org.eclipse.gmf.runtime.diagram.ui.properties.filters.EditPartPropertySectionFilter"
		«tripleSpace(4)»tab="property.tab.AdvancedPropertySection">
		«tripleSpace(3)»</propertySection>            
		«ENDIF»
	'''

	def dispatch section(GenCustomPropertyTab it) '''
		«tripleSpace(3)»<propertySection
		«tripleSpace(4)»id="property.section.«ID»" 
		«tripleSpace(4)»tab="property.tab.«ID»"
		«tripleSpace(4)»«filter(it.filter)»
		«tripleSpace(4)»class="«getQualifiedClassName()»">
		«input(it.filter)»
		«tripleSpace(3)»</propertySection>
	'''

	def dispatch filter(GenPropertyTabFilter it) ''''''

	def dispatch filter(CustomTabFilter it) '''
		filter="«it.qualifiedClassName»"
	'''

	def dispatch input(GenPropertyTabFilter it) ''''''

	def dispatch input(TypeTabFilter it) '''
		«FOR type : getAllTypes()»
		«tripleSpace(4)»<input type="«type»"/>
		«ENDFOR»
	'''

	@Localization def i18n(GenPropertySheet it) '''

	# Property Sheet
	«FOR tab : tabs»
		tab.«tab.ID»=«tab.label»
	«ENDFOR»

	'''

}
