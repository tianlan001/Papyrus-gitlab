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
 *    Michael Golubev (Montages) - #368521 - migrate to Xtend2
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.providers

import com.google.inject.Inject
import xpt.diagram.editparts.Utils_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import xpt.Common_qvto
import xpt.Common
import xpt.CodeStyle
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode

@com.google.inject.Singleton
class EditPartModelingAssistantProvider {
	@Inject extension Utils_qvto;
	@Inject extension Common_qvto;
	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject ModelingAssistantProvider xptModelingAssistantProvider;
	@Inject CodeStyle xptCodeStyle;
	@Inject ElementTypes xptElementTypes;

	def className(GenContainerBase it) '''«diagram.modelingAssistantProviderClassName»Of«editPartClassName»'''

	def packageName(GenContainerBase it) '''«getDiagram().providersPackageName».assistants'''

	def qualifiedClassName(GenContainerBase it) '''«packageName».«className»'''

	def EditPartModelingAssistantProvider(GenContainerBase it) '''
		«copyright(it.diagram.editorGen)»
		package «packageName»;

		«generatedClassComment»
		public class «className» «extendsList» {

			«getTypesForPopupBar»

			«linkAssistantMethods»
		}
	'''

	def extendsList(GenContainerBase it) '''extends «xptModelingAssistantProvider.qualifiedClassName(it.diagram)»'''

	def getTypesForPopupBar(GenContainerBase it) '''
		«IF it.getAssistantNodes().notEmpty»
			«generatedMemberComment»
			«xptCodeStyle.overrideC(it)»
			public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable host) {
				java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>(«it.getAssistantNodes().size»);
				«FOR node : it.getAssistantNodes()»
					types.add(«xptElementTypes.accessElementType(node)»);
				«ENDFOR»
				return types;
			}
		«ENDIF»
	'''

 	def dispatch linkAssistantMethods(GenContainerBase it) ''''''

	def dispatch linkAssistantMethods(GenNode it)'''
		«IF getAssistantOutgoingLinks(it).notEmpty»
			«getRelTypesOnSource»

			«doGetRelTypesOnSource»

			«getRelTypesOnSourceAndTarget»

			«doGetRelTypesOnSourceAndTarget»

			«getTypesForTarget»

			«doGetTypesForTarget»
		«ENDIF»

		«IF getAssistantIncomingLinks(it).notEmpty»
			«getRelTypesOnTarget»

			«doGetRelTypesOnTarget»

			«getTypesForSource»

			«doGetTypesForSource»
		«ENDIF»
	'''

	def getRelTypesOnSource(GenNode it) '''
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it)»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable source) {
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart sourceEditPart =
					source.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			return doGetRelTypesOnSource((«it.editPartQualifiedClassName») sourceEditPart);
		}
	'''

	def getRelTypesOnTarget(GenNode it) '''
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it)»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable target) {
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart targetEditPart =
					target.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			return doGetRelTypesOnTarget((«it.editPartQualifiedClassName») targetEditPart);
		}
	'''

	def getRelTypesOnSourceAndTarget(GenNode it) '''
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it)»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable source, org.eclipse.core.runtime.IAdaptable target) {
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart sourceEditPart =
					source.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart targetEditPart =
					target.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			return doGetRelTypesOnSourceAndTarget((«it.editPartQualifiedClassName») sourceEditPart, targetEditPart);
		}
	'''

	def getTypesForSource(GenNode it) '''
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it)»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getTypesForSource(org.eclipse.core.runtime.IAdaptable target, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart targetEditPart =
					target.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			return doGetTypesForSource((«it.editPartQualifiedClassName») targetEditPart, relationshipType);
		}
	'''

	def getTypesForTarget(GenNode it) '''
		«IF getAssistantOutgoingLinks(it).notEmpty»
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it)»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getTypesForTarget(org.eclipse.core.runtime.IAdaptable source, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart sourceEditPart =
					source.getAdapter(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			return doGetTypesForTarget((«it.editPartQualifiedClassName») sourceEditPart, relationshipType);
		}
		«ENDIF»
	'''

	// pre: getAssistantOutgoingLinks(this).size() > 0
	def doGetRelTypesOnSource(GenNode it) '''
		«generatedMemberComment»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> doGetRelTypesOnSource(«it.editPartQualifiedClassName» source) {
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>(«getAssistantOutgoingLinks(it).size»);
			«FOR link : getAssistantOutgoingLinks(it)»
			types.add(«xptElementTypes.accessElementType(link)»);
			«ENDFOR»
			return types;
		}
	'''

	// pre: getAssistantIncomingLinks(this).size() > 0
	def doGetRelTypesOnTarget(GenNode it) '''
		«generatedMemberComment»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> doGetRelTypesOnTarget(«it.editPartQualifiedClassName» target) {
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>(«getAssistantIncomingLinks(it).size»);
			«FOR link : getAssistantIncomingLinks(it) »
			types.add(«xptElementTypes.accessElementType(link)»);
			«ENDFOR»
			return types;
		}
	'''

	// pre: getAssistantOutgoingLinks(this).size() > 0
	def doGetRelTypesOnSourceAndTarget(GenNode it) '''
		«generatedMemberComment»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> doGetRelTypesOnSourceAndTarget(«it.editPartQualifiedClassName» source, org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart targetEditPart) {
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.LinkedList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>();
			«FOR link : getAssistantOutgoingLinks(it)»
				«FOR target : selectGenNodes(link.targets)»
					if (targetEditPart instanceof «target.getEditPartQualifiedClassName()») {
						types.add(«xptElementTypes.accessElementType(link)»);
					}
				«ENDFOR»
			«ENDFOR»
			return types;
		}
	'''

	// pre: getAssistantIncomingLinks(this).size() > 0
	def doGetTypesForSource(GenNode it) '''
		«generatedMemberComment»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> doGetTypesForSource(«it.editPartQualifiedClassName» target, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>();
			«FOR link : getAssistantIncomingLinks(it) SEPARATOR ' else '»
			if (relationshipType == «xptElementTypes.accessElementType(link)») {
				«FOR source : selectGenNodes(link.sources)»
					types.add(«xptElementTypes.accessElementType(source)»);
				«ENDFOR»
			}
			«ENDFOR»
			return types;
		}
	'''

	// pre: getAssistantOutgoingLinks(this).size() > 0
	def doGetTypesForTarget(GenNode it) '''
		«generatedMemberComment»
		public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> doGetTypesForTarget(«it.editPartQualifiedClassName» source, org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
			java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.emf.type.core.IElementType')»>();
			«FOR link : getAssistantOutgoingLinks(it) SEPARATOR ' else '»
			if (relationshipType == «xptElementTypes.accessElementType(link)») {
				«FOR target : selectGenNodes(link.targets)»
					types.add(«xptElementTypes.accessElementType(target)»);
				«ENDFOR»
			}
			«ENDFOR»
			return types;
		}
	'''
}