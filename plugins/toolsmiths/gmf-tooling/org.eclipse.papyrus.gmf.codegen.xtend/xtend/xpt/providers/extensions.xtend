/*******************************************************************************
 * Copyright (c) 2007, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.providers

import com.google.inject.Inject
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.Utils_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.ElementType
import org.eclipse.papyrus.gmf.codegen.gmfgen.MetamodelType
import org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType
import org.eclipse.papyrus.gmf.codegen.gmfgen.NotationType
import parsers.ParserProvider
import xpt.diagram.edithelpers.EditHelper
import xpt.diagram.edithelpers.EditHelperAdvice
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode

@com.google.inject.Singleton class extensions {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;

	@Inject ViewProvider viewProvider;
	@Inject IconProvider iconProvider;
	@Inject EditPartProvider editPartProvider;
	@Inject EditPartModelingAssistantProvider xptEditPartModelingAssistant;
	@Inject ParserProvider labelParsers;
	@Inject ShortcutsDecoratorProvider shorcutProvider;
	@Inject VisualTypeProvider visualTypeProvider;
	@Inject EditHelper editHelper;
	@Inject EditHelperAdvice editHelperAdvice;

	def extensions(GenDiagram it) '''
		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.diagram.core.viewProviders" id="view-provider">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<viewProvider class="«viewProvider.qualifiedClassName(it)»">
		«tripleSpace(3)»<Priority name="«notationViewProviderPriority»"/>
				«IF ! shortcutsProvidedFor.empty/*allow provider activation when another diagram tries to create a node, perhaps ours*/»
		«tripleSpace(3)»<object id="referencing-diagrams" class="org.eclipse.gmf.runtime.notation.Diagram">
		«tripleSpace(4)»<method name="getType()" value="«FOR s : shortcutsProvidedFor SEPARATOR ','»«s»«ENDFOR»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<context viewClass="org.eclipse.gmf.runtime.notation.Node" containerViews="referencing-diagrams"/>
				«ENDIF»
		«tripleSpace(3)»<context viewClass="org.eclipse.gmf.runtime.notation.Diagram" semanticHints="«editorGen.modelID»"/>
		«tripleSpace(3)»<context viewClass="org.eclipse.gmf.runtime.notation.Node" semanticHints="«commaSeparatedVisualIDs(allNodes)»"/>
				«IF ! links.empty/*it is unlikely there would be a diagram without a node, but a diagram without links deemed possible */»
		«tripleSpace(3)»<context viewClass="org.eclipse.gmf.runtime.notation.Edge" semanticHints="«commaSeparatedVisualIDs(links)»"/>
				«ENDIF»
		«tripleSpace(2)»</viewProvider>
		«tripleSpace(1)»</extension>

		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.diagram.ui.editpartProviders" id="ep-provider">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<editpartProvider class="«editPartProvider.qualifiedClassName(it)»">
		«tripleSpace(3)»<Priority name="«editPartProviderPriority»"/>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Diagram" id="generated-diagram">
		«tripleSpace(4)»<method name="getType()" value="«editorGen.modelID»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Node" id="generated-nodes">
		«tripleSpace(4)»<method name="getType()" value="«commaSeparatedVisualIDs(allNodes)»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Edge" id="generated-links">
		«tripleSpace(4)»<method name="getType()" value="«commaSeparatedVisualIDs(links)»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Node" id="generated-labels">
		«tripleSpace(4)»<method name="getType()" value="«commaSeparatedVisualIDs(allNodes.map[n|n.labels].flatten)»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Node" id="generated-compartments">
		«tripleSpace(4)»<method name="getType()" value="«commaSeparatedVisualIDs(compartments)»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<context views="generated-diagram,generated-nodes,generated-links,generated-labels,generated-compartments"/>
		«tripleSpace(2)»</editpartProvider>
		«tripleSpace(1)»</extension>

«««		«modelingAssistantProvider(it)»

		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.common.ui.services.iconProviders" id="icon-provider">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<IconProvider class="«iconProvider.qualifiedClassName(it)»">
		«tripleSpace(3)»<Priority name="«iconProviderPriority»"/>
		«tripleSpace(2)»</IconProvider>
		«tripleSpace(1)»</extension>
		«IF editorGen.labelParsers !== null && editorGen.labelParsers.extensibleViaService»
		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.common.ui.services.parserProviders" id="parser-provider">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<ParserProvider class="«labelParsers.qualifiedClassName(editorGen.labelParsers)»">
		«tripleSpace(3)»<Priority name="«editorGen.labelParsers.providerPriority»"/>
		«tripleSpace(2)»</ParserProvider>
		«tripleSpace(1)»</extension>
		«ENDIF»
		«IF generateShortcutIcon()»
		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.diagram.ui.decoratorProviders" id="decorator-provider">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<decoratorProvider class="«shorcutProvider.qualifiedClassName(it)»">
		«tripleSpace(3)»<Priority name="«shortcutsDecoratorProviderPriority»"/>
		«tripleSpace(3)»<object class="org.eclipse.gmf.runtime.notation.Node(org.eclipse.gmf.runtime.notation)" id="generated-top-nodes">
		«tripleSpace(4)»<method name="getType()" value="«commaSeparatedVisualIDs(it.topLevelNodes)/*generated code supports shortcuts only to top-level nodes*/»"/>
		«tripleSpace(3)»</object>
		«tripleSpace(3)»<context decoratorTargets="generated-top-nodes"/>
		«tripleSpace(2)»</decoratorProvider>
		«tripleSpace(1)»</extension>
		«ENDIF»

«««		commented for bug 520882
«««		«IF !getLocalDefineTypedElements(it).empty»
«««		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.emf.type.core.elementTypes" id="element-types">
«««		«tripleSpace(2)»«xmlGeneratedTag»
«««		«FOR e : getLocalDefineTypedElements(it)»
«««		«elementTypeSafe(e.elementType)»
«««			«ENDFOR»
«««		«tripleSpace(1)»</extension>
«««		«ENDIF»
«««
«««		«tripleSpace(1)»<extension point="org.eclipse.gmf.runtime.emf.type.core.elementTypeBindings" id="element-types-bindings">
«««		«tripleSpace(2)»«xmlGeneratedTag»
«««		«IF it.eResource.allContents.filter(typeof (GenerateUsingElementTypeCreationCommand)).size<1»
«««			«tripleSpace(2)»<clientContext id="«editorGen.plugin.ID».TypeContext">
«««			«tripleSpace(3)»<enablement>
«««			«tripleSpace(4)»<test
«««			«tripleSpace(5)»property="org.eclipse.gmf.runtime.emf.core.editingDomain"
«««			«tripleSpace(5)»value="«editingDomainID»"/>
«««			«tripleSpace(3)»</enablement>
«««			«tripleSpace(2)»</clientContext>
«««			«tripleSpace(2)»<binding context="«editorGen.plugin.ID».TypeContext">
«««		«ENDIF»
«««		«IF it.eResource.allContents.filter(typeof (GenerateUsingElementTypeCreationCommand)).size>0»
«««
«««		<binding context="org.eclipse.papyrus.infra.services.edit.TypeContext">
«««		«ENDIF»
«««		«FOR e : getLocalDefineTypedElements(it)»
«««		«tripleSpace(3)»<elementType ref="«e.elementType.uniqueIdentifier»"/>
«««				«ENDFOR»
«««		«tripleSpace(3)»<advice ref="org.eclipse.gmf.runtime.diagram.core.advice.notationDepdendents"/>
«««		«tripleSpace(2)»</binding>
«««		«tripleSpace(1)»</extension>
		«tripleSpace(1)»<extension point="org.eclipse.papyrus.infra.gmfdiag.common.visualTypeProviders">
		«tripleSpace(2)»«xmlGeneratedTag»
		«tripleSpace(2)»<visualTypeProvider
		«tripleSpace(4)»class="«visualTypeProvider.qualifiedClassName(it)»"
		«tripleSpace(4)»diagramType="«editorGen.modelID»">
		«tripleSpace(3)»</visualTypeProvider>
		«tripleSpace(1)»</extension>
	'''

	def elementTypeSafe(ElementType it) '''
		«IF !it.definedExternally»
			«elementType(it)»
		«ENDIF»
	'''

	def dispatch elementType(ElementType it) '''«ERROR('Unknown element type: ' + it)»'''

	def dispatch elementType(MetamodelType it)'''
		«tripleSpace(2)»<metamodel nsURI="«getMetaClass().genPackage.getEcorePackage.nsURI»">
		«tripleSpace(3)»<metamodelType
		«tripleSpace(5)»id="«uniqueIdentifier»"
				«IF null !== displayName»
		«tripleSpace(5)»name="%metatype.name.«diagramElement.stringUniqueIdentifier»"
				«ENDIF»
		«tripleSpace(5)»kind="org.eclipse.gmf.runtime.emf.type.core.IHintedType"
		«tripleSpace(5)»eclass="«getMetaClass().ecoreClass.name»"
		«tripleSpace(5)»edithelper="«editHelper.qualifiedClassName(it)»">
		«tripleSpace(4)»<param name="semanticHint" value="«diagramElement.stringVisualID»"/>
		«tripleSpace(3)»</metamodelType>
		«tripleSpace(2)»</metamodel>
	'''

	def dispatch elementType(SpecializationType it) '''
		«IF null === getMetamodelClass()»
			«specializationType(it)»
		«ELSE»
			«tripleSpace(2)»<metamodel nsURI="«getMetamodelClass().genPackage.getEcorePackage.nsURI»">
			«specializationType(it)»
			«tripleSpace(2)»</metamodel>
		«ENDIF»
	'''

	def specializationType(SpecializationType it) '''
		«tripleSpace(3)»<specializationType
		«tripleSpace(5)»id="«uniqueIdentifier»"
			«IF null !== displayName»
		«tripleSpace(5)»name="%metatype.name.«diagramElement.stringUniqueIdentifier»"
			«ENDIF»
		«tripleSpace(5)»kind="org.eclipse.gmf.runtime.emf.type.core.IHintedType"«IF editHelperAdviceClassName !== null»
		«tripleSpace(5)»edithelperadvice="«editHelperAdvice.qualifiedClassName(it)»"«ENDIF»>
		«tripleSpace(4)»<specializes id="«IF (null === metamodelType)»org.eclipse.gmf.runtime.emf.type.core.null«ELSE»«metamodelType.
				uniqueIdentifier»«ENDIF»"/>
		«tripleSpace(4)»<param name="semanticHint" value="«diagramElement.stringVisualID»"/>
		«tripleSpace(3)»</specializationType>
	'''

	def dispatch elementType(NotationType it) '''
		«tripleSpace(2)»<specializationType
		«tripleSpace(4)»id="«uniqueIdentifier»"
			«IF null !== displayName»
		«tripleSpace(4)»name="%metatype.name.«diagramElement.stringUniqueIdentifier»"
			«ENDIF»
		«tripleSpace(4)»kind="org.eclipse.gmf.runtime.diagram.ui.util.INotationType">
		«tripleSpace(3)»<specializes id="org.eclipse.gmf.runtime.emf.type.core.null"/>
		«tripleSpace(3)»<param name="semanticHint" value="«diagramElement.stringVisualID»"/>
		«tripleSpace(2)»</specializationType>
	'''

	def modelingAssistantProvider(GenDiagram it) '''
		<extension point="org.eclipse.gmf.runtime.emf.ui.modelingAssistantProviders" id="modelassist-provider">
			«xmlGeneratedTag»
			<modelingAssistantProvider class="«modelingAssistantProviderQualifiedClassName(it)»">
				<Priority name="«it.modelingAssistantProviderPriority»"/>
				<object class="«it.editPartQualifiedClassName»" id="«it.uniqueIdentifier»"/>
				<context elements="«it.uniqueIdentifier»"/>
			</modelingAssistantProvider>
			«FOR n : it.getAllNodes()»
			<modelingAssistantProvider class="«modelingAssistantProviderQualifiedClassName(n)»">
				<Priority name="«n.diagram.modelingAssistantProviderPriority»"/>
				<object class="«n.editPartQualifiedClassName»" id="«n.uniqueIdentifier»"/>
				<context elements="«n.uniqueIdentifier»"/>
			</modelingAssistantProvider>
			«ENDFOR»
		</extension>
	'''

	@MetaDef def dispatch modelingAssistantProviderQualifiedClassName(GenContainerBase it)'''«/*NO-OP, all specific subclasses should be handled*/»'''

	@MetaDef def dispatch modelingAssistantProviderQualifiedClassName(GenDiagram it)'''«xptEditPartModelingAssistant.qualifiedClassName(it)»'''

	@MetaDef def dispatch modelingAssistantProviderQualifiedClassName(GenNode it)'''«xptEditPartModelingAssistant.qualifiedClassName(it)»'''

	def commaSeparatedVisualIDs(Iterable<? extends GenCommonBase> list) '''«FOR gcb : list SEPARATOR ','»«gcb.stringUniqueIdentifier»«ENDFOR»'''

	@Localization def i18n(GenDiagram it) '''
		# Providers
		«FOR next : getAllTypedElements().map[t|t.elementType]»
			«internal_i18n(next)»«ENDFOR»
	'''

	@Localization def internal_i18n(ElementType it) '''
		«IF null !== displayName && !definedExternally»metatype.name.«diagramElement.uniqueIdentifier»=«displayName»«ENDIF»
	'''

	def getLocalDefineTypedElements(GenDiagram it) {
		getAllTypedElements(it).filter[et| false == et.elementType.definedExternally]
	}

}
