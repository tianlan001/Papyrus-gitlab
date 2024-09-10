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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 missing @override
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode
import xpt.CodeStyle
import xpt.Common
import xpt.diagram.editparts.Utils_qvto

//DOCUMENTATION: PapyrusGenCode
//add call template in order to generate handlenotification in order to refresh figure by taking account event

@Singleton class NodeEditPart {
	@Inject extension Common;
	@Inject extension Utils_qvto;
	@Inject extension CodeStyle;

	@Inject impl.diagram.editparts.NodeEditPart xptNodeEditPartImpl;
	@Inject xpt.diagram.editparts.Common xptCommon;

	def qualifiedClassName(GenNode it) '''«xptNodeEditPartImpl.packageName(it)».«xptNodeEditPartImpl.className(it)»'''

	def fullPath(GenNode it) '''«qualifiedClassName(it)»'''

	def Main(GenNode it) '''
	«copyright(diagram.editorGen)»
	package «xptNodeEditPartImpl.packageName(it)»;

	«generatedClassComment»
	public class «xptNodeEditPartImpl.className(it)» «extendsList(it)» {
		
		«attributes(it)»

		«xptNodeEditPartImpl.constructor(it)»
		«createDefaultEditPolicies(it)»
		«xptNodeEditPartImpl.createLayoutEditPolicy(it)»
«««	BEGIN: PapyrusGenCode
«««	call template to paste code for refresh figure by taking account event
	«xptNodeEditPartImpl.specificHandleNotificationEvent(it)»
«««	END: PapyrusGenCode
	«xptNodeEditPartImpl.createNodeShape(it.viewmap, it)»
		«IF hasFixedChildren(it)»
			«xptNodeEditPartImpl.addFixedChild(it)»
			«xptNodeEditPartImpl.removeFixedChild(it)»
			«xptNodeEditPartImpl.addChildVisual(it)»
			«xptNodeEditPartImpl.removeChildVisual(it)»
			«xptNodeEditPartImpl.getContentPaneFor(it)»
		«ENDIF»
		«xptNodeEditPartImpl.addBorderItem(it)»
		«xptNodeEditPartImpl.createNodePlate(it)»
		«xptNodeEditPartImpl.getPrimaryDragEditPolicy(it)»
		«xptNodeEditPartImpl.createFigure(it)»
		«xptNodeEditPartImpl.setupContentPane(it)»
		«xptNodeEditPartImpl.getContentPane(it)»
		«xptNodeEditPartImpl.setForegroundColor(it)»
«««		«xptNodeEditPartImpl.setBackgroundColor(it)»
		«xptNodeEditPartImpl.setLineWidth(it)»
		«xptNodeEditPartImpl.setLineStyle(it)»
		«xptNodeEditPartImpl.getPrimaryChildEditPart(it)»
		«IF hasChildrenInListCompartments(it)»
			«xptNodeEditPartImpl.getTargetEditPartMethod(it)»
		«ENDIF»
		«handleNotificationEvent(it)»
	«xptNodeEditPartImpl.innerClassDeclaration(viewmap)»
	}
	'''

	def extendsList(GenNode it) '''extends «xptNodeEditPartImpl.extendsListContents(it)»'''

	def attributes(GenNode it) '''
		«xptCommon.visualIDConstant(it)»

		«generatedMemberComment»
		protected org.eclipse.draw2d.IFigure contentPane;

		«generatedMemberComment»
		protected org.eclipse.draw2d.IFigure primaryShape;
	'''

	def createDefaultEditPolicies(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			«xptNodeEditPartImpl.createDefaultEditPoliciesBody(it)»
		}
	'''

	def dispatch handleNotificationEvent(GenNode it) ''''''

	def dispatch handleNotificationEvent(GenTopLevelNode it) '''
		«/** This code is important for refreshing shortcut decoration
		on adding corresponding annotation to the View instance.*/»
		«IF diagram.generateShortcutIcon()»
			«generatedMemberComment»
			«overrideC»
			protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification event) {
				«xptNodeEditPartImpl.handleNotificationEventBody(it)»
			}
		«ENDIF»
	'''
}