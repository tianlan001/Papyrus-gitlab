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
 *		Dmitry Stadnik (Borland) - initial API and implementation
 *		Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import xpt.Common
import xpt.editor.UriEditorInputTester
import xpt.editor.ShortcutPropertyTester

/**
 * FIXME: [MG]: xptXXX forfields
 */
@com.google.inject.Singleton class extensions {
	@Inject extension Common;

	@Inject UriEditorInputTester uriTester;
	@Inject ShortcutPropertyTester shortcutTester;
	@Inject NavigatorAbstractNavigatorItem abstractNavigatorItem;
	@Inject NavigatorLabelProvider labelProvider;
	@Inject NavigatorContentProvider contentProvider;
	@Inject DomainNavigatorContentProvider domainContentProvider;
	@Inject DomainNavigatorLabelProvider domainLabelNavigator;
	@Inject DomainNavigatorItem xptDomainNavigatorItem;

	def extensions(GenNavigator it) '''
	«IF it !== null »
		«editorInputPropertyTester(it, 'URIEditorInput', 'org.eclipse.emf.common.ui.URIEditorInput', '' + uriTester.qualifiedClassName(it))»

		«IF editorGen.diagram.generateShortcutIcon()»
			«editorInputPropertyTester(it, 'Shortcut', 'org.eclipse.gmf.runtime.notation.View', shortcutTester.qualifiedClassName(editorGen.diagram).toString)»
		«ENDIF»

		«registerBindings(it)»

		«tripleSpace(1)»<extension point="org.eclipse.ui.navigator.navigatorContent" id="navigator-content">
		«tripleSpace(2)»«xmlGeneratedTag()»
		«tripleSpace(2)»<navigatorContent
		«tripleSpace(4)»id="«contentExtensionID»" 
		«tripleSpace(4)»name="«contentExtensionName»" 
		«tripleSpace(4)»priority="«contentExtensionPriority»" 
		«tripleSpace(4)»contentProvider="«contentProvider.qualifiedClassName(it)»" 
		«tripleSpace(4)»labelProvider="«labelProvider.qualifiedClassName(it)»"
		«tripleSpace(4)»icon="«editorGen.editor.iconPathX»"
		«tripleSpace(4)»activeByDefault="true">
		«tripleSpace(3)»<triggerPoints>
		«tripleSpace(4)»<or>
		«tripleSpace(5)»<and>
		«tripleSpace(6)»<instanceof value="org.eclipse.core.resources.IFile"/>
		«tripleSpace(6)»<test property="org.eclipse.core.resources.extension" value="«editorGen.diagramFileExtension»"/>
		«tripleSpace(5)»</and>
		«tripleSpace(5)»<instanceof value="«abstractNavigatorItem.qualifiedClassName(it)»"/>
				«IF editorGen.diagram.generateShortcutIcon()»
		«tripleSpace(5)»<adapt type="org.eclipse.gmf.runtime.notation.View">
		«tripleSpace(6)»<test property="«editorGen.plugin.ID».isShortcut"/>
		«tripleSpace(5)»</adapt>
				«ENDIF»
		«tripleSpace(4)»</or>
		«tripleSpace(3)»</triggerPoints>
		«tripleSpace(3)»<possibleChildren>
		«tripleSpace(4)»<or>
		«tripleSpace(5)»<instanceof value="«abstractNavigatorItem.qualifiedClassName(it)»"/>
				«IF editorGen.diagram.generateShortcutIcon()»
		«tripleSpace(5)»<adapt type="org.eclipse.gmf.runtime.notation.View">
		«tripleSpace(6)»<test property="«editorGen.plugin.ID».isShortcut"/>
		«tripleSpace(5)»</adapt>
				«ENDIF»
		«tripleSpace(4)»</or>
		«tripleSpace(3)»</possibleChildren>
		«tripleSpace(3)»<commonSorter 
		«tripleSpace(5)»id="«sorterExtensionID»" 
		«tripleSpace(5)»class="«getSorterQualifiedClassName()»">
		«tripleSpace(4)»<parentExpression>
		«tripleSpace(5)»<or>
		«tripleSpace(6)»<and>
		«tripleSpace(7)»<instanceof value="org.eclipse.core.resources.IFile"/>
		«tripleSpace(7)»<test property="org.eclipse.core.resources.extension" value="«editorGen.diagramFileExtension»"/>
		«tripleSpace(6)»</and>
		«tripleSpace(6)»<instanceof value="«abstractNavigatorItem.qualifiedClassName(it)»"/>
		«tripleSpace(5)»</or>
		«tripleSpace(4)»</parentExpression>
		«tripleSpace(3)»</commonSorter>
		«tripleSpace(2)»</navigatorContent>
		«IF generateDomainModelNavigator && null !== editorGen.domainGenModel »
			«tripleSpace(2)»<navigatorContent
			«tripleSpace(4)»id="«domainContentExtensionID»" 
			«tripleSpace(4)»name="«domainContentExtensionName»" 
			«tripleSpace(4)»priority="«domainContentExtensionPriority»" 
			«tripleSpace(4)»contentProvider="«domainContentProvider.qualifiedClassName(it)»" 
			«tripleSpace(4)»labelProvider="«domainLabelNavigator.qualifiedClassName(it)»"
			«tripleSpace(4)»icon="«editorGen.editor.iconPathX»"
			«tripleSpace(4)»activeByDefault="true">
			«tripleSpace(3)»<triggerPoints>
			«tripleSpace(4)»<or>
			«tripleSpace(5)»<and>
			«tripleSpace(6)»<instanceof value="org.eclipse.core.resources.IFile"/>
			«tripleSpace(6)»<test property="org.eclipse.core.resources.extension" value="«editorGen.domainFileExtension»"/>
			«tripleSpace(5)»</and>
			«tripleSpace(5)»<instanceof value="«xptDomainNavigatorItem.qualifiedClassName(it)»"/>
			«tripleSpace(4)»</or>
			«tripleSpace(3)»</triggerPoints>
			«tripleSpace(3)»<possibleChildren>
			«tripleSpace(4)»<instanceof value="«xptDomainNavigatorItem.qualifiedClassName(it)»"/>
			«tripleSpace(3)»</possibleChildren>
			«tripleSpace(2)»</navigatorContent>
		«ENDIF»
		«tripleSpace(2)»<actionProvider
		«tripleSpace(4)»id="«actionProviderID»"
		«tripleSpace(4)»class="«getActionProviderQualifiedClassName()»">
		«tripleSpace(3)»<enablement>
		«tripleSpace(4)»<or>
		«tripleSpace(5)»<instanceof value="«abstractNavigatorItem.qualifiedClassName(it)»"/>
				«IF editorGen.diagram.generateShortcutIcon()»
		«tripleSpace(5)»<adapt type="org.eclipse.gmf.runtime.notation.View">
		«tripleSpace(6)»<test property="«editorGen.plugin.ID».isShortcut"/>
		«tripleSpace(5)»</adapt>
				«ENDIF»
		«tripleSpace(4)»</or>
		«tripleSpace(3)»</enablement>
		«tripleSpace(2)»</actionProvider>
		«tripleSpace(1)»</extension>

		«registerLinkHelper(it)»
	«ENDIF»
	'''

	def editorInputPropertyTester(GenNavigator it, String property, String type, String testerClass) '''
			«tripleSpace(1)»<extension point="org.eclipse.core.expressions.propertyTesters" id="navigator-proptest.is«property»">
		«tripleSpace(2)»«xmlGeneratedTag()»
		«tripleSpace(2)»<propertyTester
		«tripleSpace(4)»id="«editorGen.plugin.ID».«property»PropertyTester"
		«tripleSpace(4)»type="«type»"
		«tripleSpace(4)»namespace="«editorGen.plugin.ID»"
		«tripleSpace(4)»properties="is«property»"
		«tripleSpace(4)»class="«testerClass»">
		«tripleSpace(2)»</propertyTester>
		«tripleSpace(1)»</extension>
	'''

	def registerBindings(GenNavigator it) '''
		«tripleSpace(1)»<extension point="org.eclipse.ui.navigator.viewer" id="navigator-viewbinding">
		«tripleSpace(2)»«xmlGeneratedTag()»
		«tripleSpace(2)»<viewerContentBinding viewerId="org.eclipse.ui.navigator.ProjectExplorer">
		«tripleSpace(3)»<includes>
		«tripleSpace(4)»<contentExtension pattern="«contentExtensionID»"/>
		«IF generateDomainModelNavigator && null !== editorGen.domainGenModel »
		«tripleSpace(4)»<contentExtension pattern="«domainContentExtensionID»"/>
		«ENDIF»
		«tripleSpace(4)»<contentExtension pattern="«linkHelperExtensionID»"/>
		«tripleSpace(3)»</includes>
		«tripleSpace(2)»</viewerContentBinding>
		«tripleSpace(2)»<viewerActionBinding viewerId="org.eclipse.ui.navigator.ProjectExplorer">
		«tripleSpace(3)»<includes>
		«tripleSpace(4)»<actionExtension pattern="«actionProviderID»"/>
		«tripleSpace(3)»</includes>
		«tripleSpace(2)»</viewerActionBinding>
		«tripleSpace(1)»</extension>
	'''

	def registerLinkHelper(GenNavigator it) '''
			«tripleSpace(1)»<extension point="org.eclipse.ui.navigator.linkHelper" id="navigator-linkhelper">
		«tripleSpace(2)»«xmlGeneratedTag()»
		«tripleSpace(2)»<linkHelper
		«tripleSpace(4)»id="«linkHelperExtensionID»"
		«tripleSpace(4)»class="«getLinkHelperQualifiedClassName()»">
		«tripleSpace(3)»<editorInputEnablement>
		«tripleSpace(4)»<and>
		«tripleSpace(5)»<instanceof value="org.eclipse.emf.common.ui.URIEditorInput"/>
		«tripleSpace(5)»<test property="«editorGen.plugin.ID».isURIEditorInput"/>
		«tripleSpace(4)»</and>
		«tripleSpace(3)»</editorInputEnablement>
		«tripleSpace(3)»<selectionEnablement>
		«tripleSpace(4)»<instanceof value="«abstractNavigatorItem.qualifiedClassName(it)»"/>
		«tripleSpace(3)»</selectionEnablement>
		«tripleSpace(2)»</linkHelper>
		«tripleSpace(1)»</extension>
	'''

}
