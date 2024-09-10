/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Artem Tikhomirov (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor.palette

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Map
import org.eclipse.papyrus.gmf.codegen.gmfgen.AbstractToolEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.EntryBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.Palette
import org.eclipse.papyrus.gmf.codegen.gmfgen.Separator
import org.eclipse.papyrus.gmf.codegen.gmfgen.StandardEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.StandardEntryKind
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroup
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroupItem
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.Externalizer
import xpt.providers.ElementTypes

@Singleton class PaletteFactory {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;
	@Inject extension CodeStyle;

	@Inject ElementTypes xptElementTypes;
	@Inject Externalizer xptExternalizer;

	def className(Palette it) '''«it.factoryClassName»'''

	def packageName(Palette it) '''«it.packageName»'''

	def qualifiedClassName(Palette it) '''«packageName(it)».«className(it)»'''

	def fullPath(Palette it) '''«qualifiedClassName(it)»'''

	@Deprecated
	def Factory(Palette it) '''«PaletteFactory(it)»'''

	def PaletteFactory(Palette it) '''
		«copyright(diagram.editorGen)»
		package «packageName»;

		«generatedClassComment»
		public class «factoryClassName» extends org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory.Adapter {
			//RS: New Palette generation

		//Generates the ID for the tool elements
		//Generate the tool factory (if(ID) createtool...)
		«FOR tool : collectTools(it)»
			«generateIDAttribute(tool)»
		«ENDFOR»

		«««Generates the default constructor
		«generatedMemberComment»
			public «factoryClassName»() {

			}

		«««Generates the main method to create tool
		«generateCreateTool(it)»

		«««Generates the main method to create template
		«generateGetTemplate(it)»

		«««Generates each method for tool creation

		«FOR tool : collectTools(it)»
			«createTool(tool)»
		«ENDFOR»

		}
	'''

	def createGroup(ToolGroup it) '''
		«generatedMemberComment('Creates \"' + title + '\" palette tool group')»
		private org.eclipse.gef.palette.PaletteContainer «createMethodName»() {
			«newContainer(it, 'paletteContainer')»
			«setIdentity(it, 'paletteContainer')»
			«setDescription(it, 'paletteContainer')»
			«setSmallImage(it, 'paletteContainer', palette)»
			«setLargeImage(it, 'paletteContainer', palette)»
			«FOR entry : it.entries»
				«addEntry(entry, 'paletteContainer')»
			«ENDFOR»
			return paletteContainer;
		}
	'''

	def newContainer(ToolGroup it, String varName) '''
	«IF collapse && toolsOnly»
		org.eclipse.gef.palette.PaletteDrawer «varName» = new org.eclipse.gef.palette.PaletteDrawer(«i18nTitle(it)»);
	«ELSEIF stack»
		org.eclipse.gef.palette.PaletteStack «varName» = new org.eclipse.gef.palette.PaletteStack(«i18nTitle(it)», null, null);
	«ELSE»
		org.eclipse.gef.palette.PaletteGroup «varName» = new org.eclipse.gef.palette.PaletteGroup(«i18nTitle(it)»);
	«ENDIF»
	'''

	def setDescription(ToolGroup gr, String varName) '''
		«IF gr.description !== null »
		«varName».setDescription(«i18nDesc(gr)»);
		«ENDIF»
	'''

	def dispatch addEntry(ToolGroupItem it, String varName) '''
		«ERROR('abstract addEntry(ToolGroupItem,String) template for' + it)»
	'''

	def dispatch addEntry(AbstractToolEntry it, String varName) '''
		«varName».add(«createMethodName»());
		«IF it.isDefault && it.group.stack»
			«varName».setActiveEntry((org.eclipse.gef.palette.ToolEntry) paletteContainer.getChildren().get(paletteContainer.getChildren().size() - 1));
		«ENDIF»
	'''

	def dispatch addEntry(Separator it, String varName) '''
		«varName».add(new org.eclipse.gef.palette.PaletteSeparator());
	'''

	def dispatch addEntry(ToolGroup it, String varName) '''
		«varName».add(«createMethodName»());
	'''

	def createEntry(AbstractToolEntry it) '''
		«generatedMemberComment»
		private org.eclipse.gef.palette.ToolEntry «createMethodName»() {
			«newEntry(it, 'entry')»
			«setIdentity(it, 'entry')»
			«setSmallImage(it, 'entry', group.palette)»
			«setLargeImage(it, 'entry', group.palette)»
			«setToolClass(it, 'entry')»
			«FOR prop : it.properties»
				«setToolProperty(prop, 'entry')»
			«ENDFOR»
			return entry;
		}
	'''

	protected def String toolEntryClass(ToolEntry entry) {
		return if(entry.genNodes.empty) linkToolEntryClassName() else nodeToolEntryClassName();
	}

	protected def getLinkToolEntryGeneratedClassName() {
		return 'LinkToolEntry';
	}

	protected def getNodeToolEntryGeneratedClassName() {
		return 'NodeToolEntry';
	}

	protected def getDefaultLinkToolEntryClassName() {
		return 'org.eclipse.gmf.tooling.runtime.part.DefaultLinkToolEntry';
	}

	protected def getDefaultNodeToolEntryClassName() {
		return 'org.eclipse.gmf.tooling.runtime.part.DefaultNodeToolEntry';
	}

	private def linkToolEntryClassName() {
		return if (shouldGenerateToolEntryClasses())
			getLinkToolEntryGeneratedClassName()
		else
			getDefaultLinkToolEntryClassName()
	}

	private def nodeToolEntryClassName() {
		return if (shouldGenerateToolEntryClasses())
			getNodeToolEntryGeneratedClassName()
		else
			getDefaultNodeToolEntryClassName()
	}

	def dispatch newEntry(AbstractToolEntry it, String toolVarName) '''
		«ERROR('abstract newEntry(AbstractToolEntry,String) template for: ' + it)»
	'''

	def dispatch newEntry(ToolEntry it, String toolVarName) '''
		«IF it.elements.empty»
			org.eclipse.gef.palette.ToolEntry «toolVarName» = new org.eclipse.gef.palette.ToolEntry(«i18nTitle(it)», «i18nDesc(it)», null, null) {};
		«ELSEIF it.elements.size() > 1»
			java.util.ArrayList<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«elements.get(0).diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>(«elements.
			size»);
			«FOR e : elements»
				types.add(«xptElementTypes.accessElementType(e)»);
			«ENDFOR»
			«toolEntryClass(it)» «toolVarName» = new «toolEntryClass(it)»(«i18nTitle(it)», «i18nDesc(it)», types);
		«ELSE»
			«toolEntryClass(it)» «toolVarName» = new «toolEntryClass(it)»(«i18nTitle(it)», «i18nDesc(it)», java.util.Collections.singletonList(«xptElementTypes.
				accessElementType(elements.head)»));
		«ENDIF»
	'''

	def dispatch newEntry(StandardEntry it, String toolVarName) '''
	«IF kind == StandardEntryKind::SELECT_LITERAL»
		«newStdSelectEntry(it, toolVarName)»
	«ELSEIF kind == StandardEntryKind::MARQUEE_LITERAL»
		«newStdMarqueeEntry(it, toolVarName)»
	«ELSEIF kind == StandardEntryKind::ZOOM_LITERAL»
		«newStdZoomEntry(it, toolVarName)»
	«ELSE»
		«newStdOtherEntry(it, toolVarName)»
	«ENDIF»
	'''

	def newStdSelectEntry(StandardEntry it, String toolVarName) '''
		org.eclipse.gef.palette.PanningSelectionToolEntry «toolVarName» = new org.eclipse.gef.palette.PanningSelectionToolEntry();
	'''

	def newStdMarqueeEntry(StandardEntry it, String toolVarName) '''
		org.eclipse.gef.palette.MarqueeToolEntry «toolVarName» = new org.eclipse.gef.palette.MarqueeToolEntry();
	'''

	def newStdZoomEntry(StandardEntry it, String toolVarName) '''
		FIXME
	'''

	def newStdOtherEntry(StandardEntry it, String toolVarName) '''
		«ERROR('override newStdOtherEntry(StandardEntry, String) for kind ' + it.kind)»
	'''

	def setSmallImage(EntryBase it, String toolVarName, Palette palette) '''
	«IF null !== smallIconPath »
		«toolVarName».setSmallIcon(«palette.activatorFQN».findImageDescriptor("«smallIconPath»")); «nonNLS(1)»
	«ELSEIF it.oclIsKindOf(typeof(ToolEntry))»
		«IF (it as ToolEntry).elements.head !== null »
			«toolVarName».setSmallIcon(«xptElementTypes.qualifiedClassName(palette.diagram)».getImageDescriptor(«xptElementTypes.accessElementType((it as ToolEntry).elements.head)»));
		«ENDIF»
	«ENDIF»
	'''

	def setLargeImage(EntryBase it, String toolVarName, Palette palette) '''
	«IF null !== largeIconPath »
		«toolVarName».setLargeIcon(«palette.activatorFQN».findImageDescriptor("«largeIconPath»")); «nonNLS(1)»
	«ELSEIF it.oclIsKindOf(typeof(ToolEntry))»
		«IF (it as ToolEntry).elements.head !== null »
			«toolVarName».setLargeIcon(«toolVarName».getSmallIcon());
		«ENDIF»
	«ENDIF»
	'''

	def setToolClass(AbstractToolEntry it, String toolVarName) '''
	«IF null !== qualifiedToolName »
		«toolVarName».setToolClass(«qualifiedToolName».class);
	«ENDIF»
	'''

	def setToolProperty(Map.Entry<String, String> it, String toolVarName) '''
		«toolVarName».setToolProperty(«key», «value»);
	'''

	@Localization def dispatch i18nTitle(ToolEntry it) // 
	'''«IF title === null »null«ELSE»«xptExternalizer.accessorCall(group.palette.diagram.editorGen, i18nTitleKey(it))»«ENDIF»'''

	@Localization def dispatch i18nTitle(ToolGroup it) //
	'''«IF title === null »null«ELSE»«xptExternalizer.accessorCall(palette.diagram.editorGen, i18nTitleKey(it))»«ENDIF»'''

	@Localization def dispatch i18nDesc(ToolEntry it) //
	'''«IF description === null »null«ELSE»«xptExternalizer.accessorCall(group.palette.diagram.editorGen, i18nDescKey(it))»«ENDIF»'''

	@Localization def dispatch i18nDesc(ToolGroup it) //
	'''«IF description === null »null«ELSE»«xptExternalizer.accessorCall(palette.diagram.editorGen, i18nDescKey(it))»«ENDIF»'''

	@Localization def i18nAccessors(Palette it) '''
	«FOR group : collectGroups(it)»
		«internal_i18n_accessors(group)»
	«ENDFOR»
	«FOR tool : collectTools(it)»
		«internal_i18n_accessors(tool)»
	«ENDFOR»
	'''

	@Localization def i18nValues(Palette it) '''
	«FOR group : collectGroups(it)»
		«internal_i18n_values(group)»
	«ENDFOR»
	«FOR tool : collectTools(it)»
		«internal_i18n_values(tool)»
	«ENDFOR»
	'''

	@Localization protected def internal_i18n_accessors(EntryBase it) '''
	«IF null !== title »«xptExternalizer.accessorField(i18nTitleKey(it))»«ENDIF»
	«IF null !== description »«xptExternalizer.accessorField(i18nDescKey(it))»«ENDIF»
	'''

	@Localization protected def internal_i18n_values(EntryBase it) '''
	«IF null !== title »«xptExternalizer.messageEntry(i18nTitleKey(it), title)»«ENDIF»
	«IF null !== description »«xptExternalizer.messageEntry(i18nDescKey(it), description)»«ENDIF»
	'''

	def cleanStandardToolsHack(Palette it) '''
		«generatedMemberComment('Workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=159289')»
		private void cleanStandardTools(org.eclipse.gef.palette.PaletteRoot paletteRoot) {
			for (java.util.Iterator it = paletteRoot.getChildren().iterator(); it.hasNext();) {
				org.eclipse.gef.palette.PaletteEntry entry = (org.eclipse.gef.palette.PaletteEntry) it.next();
				if (!"standardGroup".equals(entry.getId())) { «nonNLS(1)»
					continue;
				}
				for (java.util.Iterator it2 = ((org.eclipse.gef.palette.PaletteContainer) entry).getChildren().iterator(); it2.hasNext();) {
					org.eclipse.gef.palette.PaletteEntry entry2 = (org.eclipse.gef.palette.PaletteEntry) it2.next();
					if ("zoomTool".equals(entry2.getId())) { «nonNLS(1)»
						it2.remove();
					} else if ("noteStack".equals(entry2.getId())) { «nonNLS(1)»
						it2.remove();
					} else if ("selectionTool".equals(entry2.getId())) { «nonNLS(1)»
						it2.remove();
					}
					if (paletteRoot.getDefaultEntry() == entry2) {
						paletteRoot.setDefaultEntry(null);
					}
				}
			}
		}
	'''

	def nodeToolEntry(Palette it) '''
		«generatedClassComment»
		private static class «getNodeToolEntryGeneratedClassName()» extends «getDefaultNodeToolEntryClassName()» {

			«generatedClassComment»
			private NodeToolEntry(String title, String description, java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> elementTypes) {
				super(title, description, elementTypes);
			}

		}
	'''

	def linkToolEntry(Palette it) '''
		«generatedClassComment»
		private static class «getLinkToolEntryGeneratedClassName()» extends «getDefaultLinkToolEntryClassName()» {

			«generatedClassComment»
			private LinkToolEntry(String title, String description, java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> elementTypes) {
				super(title, description, elementTypes);
			}

		}
	'''

	def shouldGenerateToolEntryClasses() {
		return false;
	}

	def setIdentity(EntryBase it, String toolVarName) '''
	«IF !it.id.nullOrEmpty»
		 «toolVarName».setId(«id»);«IF id.startsWith('\"') && id.endsWith('\"')» «nonNLS(1)»«ENDIF»
	«ENDIF»
	'''

	def generateCreateTool(Palette it) '''
		«generatedMemberComment»
			public org.eclipse.gef.Tool createTool(String toolId) {
				«FOR tool : collectTools(it)»
					«checkToolID(tool)»
			«ENDFOR»
			// default return: null
			return null;
			}
	'''

	def checkToolID(AbstractToolEntry it) '''
		if (toolId.equals(«getConstantIDName(id)»)) {
			return «createMethodName»();
		}
	'''

	def generateGetTemplate(Palette it) '''
		«generatedMemberComment»
			public Object getTemplate(String templateId) {

				// default return: null
				return null;
			}
	'''

	def generateIDAttribute(AbstractToolEntry it) '''
		«generatedMemberComment»
		private final static String «getConstantIDName(id)» = «id»;«IF isQuoted(id,'"')»«nonNLS»«ENDIF»
	'''

	def createTool(AbstractToolEntry it) '''
		«generatedMemberComment»
		private org.eclipse.gef.Tool «createMethodName»() {
			«newTool(it as ToolEntry, 'entry')»
		}
	'''

	def newTool(ToolEntry it, String toolVarName) '''
		«IF elements.isEmpty()»
			«ERROR('no elements for tool generation (Palette)')»
		«ELSE»
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«elements.get(0).diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>(«elements.size»);
				«FOR e : elements»
					types.add(«xptElementTypes.accessElementType(e)»);
				«ENDFOR»
				«««	RS: modified tool creation to have stereotypes-aware tools
		org.eclipse.gef.Tool tool = new org.eclipse.papyrus.uml.diagram.common.service.«IF it.genNodes.isEmpty()»AspectUnspecifiedTypeConnectionTool«ELSE»AspectUnspecifiedTypeCreationTool«ENDIF»(types);
			return tool;
		«ENDIF»
	'''

}
