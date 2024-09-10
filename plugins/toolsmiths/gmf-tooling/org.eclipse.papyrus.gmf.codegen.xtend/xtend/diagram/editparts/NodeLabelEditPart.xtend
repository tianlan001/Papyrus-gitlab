/*****************************************************************************
 * Copyright (c) 2006, 2009, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - L1.2 generate less dead or duplicate code
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import impl.diagram.editparts.TextAwareExtent
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel
import xpt.Common
import xpt.diagram.editparts.Utils_qvto
import xpt.CodeStyle
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap

@Singleton class NodeLabelEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;
	
	@Inject extension Utils_qvto;

	@Inject TextAwareExtent	 xptTextAware;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def Main(GenNodeLabel it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {
		
			«attributes(it)»
			«constructor(it)»
			«createDefaultEditPolicies(it)»
			«xptTextAware.getLabelIconNotUseElementIcon(it, elementIcon, diagram)»
			«xptTextAware.methodsExtent(it, isStoringChildPositions(node), readOnly,  modelFacet, node)»
			«handleNotificationEventExtent(it)»
			«IF !(viewmap instanceof ParentAssignedViewmap /* default overriden */)»
				«xptEditpartsCommon.labelFigure(it.viewmap)»
			«ENDIF»
		}
	'''

	def className(GenNodeLabel it) '''«editPartClassName»'''

	def packageName(GenNodeLabel it) '''«getDiagram().editPartsPackageName»'''

	def constructor(GenNodeLabel it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''

	def qualifiedClassName(GenNodeLabel it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNodeLabel it) '''«qualifiedClassName(it)»'''

	def extendsList(GenNodeLabel it) '''extends org.eclipse.papyrus.uml.diagram.common.editparts.AbstractNodeLabelEditPart'''


	def attributes(GenNodeLabel it) '''
		«xptEditpartsCommon.visualIDConstant(it)»
	'''

	def createDefaultEditPolicies(GenNodeLabel it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			super.createDefaultEditPolicies();
			installEditPolicy(org.eclipse.gef.EditPolicy.SELECTION_FEEDBACK_ROLE, new org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy());
			installEditPolicy(org.eclipse.gef.EditPolicy.DIRECT_EDIT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy());
			installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.edit.policies.DefaultNodeLabelDragPolicy());
			«xptEditpartsCommon.behaviour(it)»
		}
	'''

	def handleNotificationEventExtent(GenNodeLabel it) '''
		«IF isStoringChildPositions(node) || elementIcon»
			«generatedMemberComment»
			«overrideC»
			protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification event) {
				«IF isStoringChildPositions(node)»
					Object feature = event.getFeature();
					«xptEditpartsCommon.handleBounds(it)»
				«ENDIF»
				«««	START Papyrus Code
				«IF elementIcon»
					if(event.getNewValue() instanceof org.eclipse.emf.ecore.EAnnotation && org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON.equals(((org.eclipse.emf.ecore.EAnnotation)event.getNewValue()).getSource())){	
						refreshLabel();
					}
				«ENDIF»
				«««	End Papyrus Code
				super.handleNotificationEvent(event);
			}
		«ENDIF»
	'''
}
