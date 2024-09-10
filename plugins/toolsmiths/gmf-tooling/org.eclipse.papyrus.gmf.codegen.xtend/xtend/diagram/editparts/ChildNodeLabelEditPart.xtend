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
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - L1.2 generate less dead or duplicate code + missing @override
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import impl.diagram.editparts.TextAware
import impl.diagram.editparts.TextAwareExtent
import org.eclipse.papyrus.gmf.codegen.gmfgen.FigureViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import xpt.CodeStyle
import xpt.Common

@Singleton class ChildNodeLabelEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject impl.diagram.editparts.ChildNodeLabelEditPart xptChildNodeLabelEditPart;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	@Inject TextAware xptTextAware;
	@Inject TextAwareExtent xptTextAwareExtent;

	def className(GenChildLabelNode it) '''«editPartClassName»'''

	def packageName(GenChildLabelNode it) '''«getDiagram().editPartsPackageName»'''

	def qualifiedClassName(GenChildLabelNode it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenChildLabelNode it) '''«qualifiedClassName(it)»'''

	def Main(GenChildLabelNode it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {
			«IF commonUmlCompartment»
				«xptEditpartsCommon.visualIDConstant(it)»

				«xptChildNodeLabelEditPart.constructor(it)»
				«createDefaultEditPolicies(it)»
				«xptTextAwareExtent.methodsExtent(it, false, labelReadOnly, labelModelFacet, it)»
				«createFigurePrim(viewmap)»
				«handleNotificationEventExtent(it)»
			«ELSE»
				«attributes(it)»

				«xptChildNodeLabelEditPart.constructor(it)»

				«getDragTracker(it)»
				«createDefaultEditPolicies(it)»
				«xptTextAware.methods(it, false, labelReadOnly, labelElementIcon, viewmap, labelModelFacet, it, getDiagram())»
				«xptEditpartsCommon.notationalListeners(it)»
				«handleNotificationEvent(it)»
				«xptEditpartsCommon.labelFigure(it.viewmap)»
				«xptChildNodeLabelEditPart.isSelectable(it)»
			«ENDIF»
		}
	'''

	def extendsList(GenChildLabelNode it) {
		if(commonUmlCompartment)
			'''extends org.eclipse.papyrus.uml.diagram.common.editparts.AbstractCompartmentEditPart'''
		else
			'''extends «superEditPart» '''
	}

	def implementsList(GenChildLabelNode it) '''implements org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart, org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart, org.eclipse.papyrus.infra.gmfdiag.common.editpart.IControlParserForDirectEdit'''

	def attributes(GenChildLabelNode it) '''
		«xptEditpartsCommon.visualIDConstant(it)»

		«xptTextAware.fields(it)»
	'''

	def getDragTracker(GenChildLabelNode it) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.gef.DragTracker getDragTracker(org.eclipse.gef.Request request) {
			«xptChildNodeLabelEditPart.getDragTrackerBody(it)»
		}
	'''

	def createDefaultEditPolicies(GenChildLabelNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			«xptChildNodeLabelEditPart.createDefaultEditPoliciesBody(it)»
		}
	'''

	def handleNotificationEvent(GenChildLabelNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification event) {
			«xptChildNodeLabelEditPart.handleNotificationEventBody(it)»
		}
	'''

	def handleNotificationEventExtent(GenChildLabelNode it) '''
		«IF labelElementIcon»
			«generatedMemberComment»
			«overrideC»
			protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification event) {
				if(event.getNewValue() instanceof org.eclipse.emf.ecore.EAnnotation && org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON.equals(((org.eclipse.emf.ecore.EAnnotation)event.getNewValue()).getSource())){
					refreshLabel();
				}
				super.handleNotificationEvent(event);
			}
		«ENDIF»
	'''

	def dispatch createFigurePrim(Viewmap it) ''''''

	def dispatch createFigurePrim(FigureViewmap it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.draw2d.IFigure createFigurePrim() {
			«IF figureQualifiedClassName === null»
				return new org.eclipse.draw2d.Label();
			«ELSE»
				return new «figureQualifiedClassName»();
			«ENDIF»
		}
	'''

	// Bug 569174 : L1.2 generate less dead or duplicate code : 
	//  - Common generated methods from UMLCompartmentEditPart 
	//  - moved to intermediate class AbstractCompartmentEditPart
	def boolean isCommonUmlCompartment(GenChildLabelNode it) {
		return superEditPart === null 
		|| 'org.eclipse.papyrus.uml.diagram.common.editparts.AbstractCompartmentEditPart' == superEditPart
		|| 'org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart' == superEditPart
	}
}
