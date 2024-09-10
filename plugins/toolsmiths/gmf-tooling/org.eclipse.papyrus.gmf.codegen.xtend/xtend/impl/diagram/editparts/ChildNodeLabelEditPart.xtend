/*****************************************************************************
 * Copyright (c) 2006, 2009, 2013 , 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 *****************************************************************************/
package impl.diagram.editparts

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import xpt.Common
import xpt.QualifiedClassNameProvider
import xpt.CodeStyle

/**
 * Revisit: [MG]: @Inject extension same-named-api-class -> template extends api-class?
 */
@com.google.inject.Singleton class ChildNodeLabelEditPart {
	@Inject extension Common;
	@Inject extension QualifiedClassNameProvider
	@Inject extension CodeStyle
	@Inject extension xpt.diagram.editparts.Common;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def className(GenChildLabelNode it) '''«editPartClassName»'''

	def packageName(GenChildLabelNode it) '''«getDiagram().editPartsPackageName»'''

	def constructor(GenChildLabelNode it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''

	def getDragTrackerBody(GenChildLabelNode it) '''
		if (request instanceof org.eclipse.gef.requests.SelectionRequest && ((org.eclipse.gef.requests.SelectionRequest) request).getLastButtonPressed() == 3) {
			return null;
		}
		return new org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx(this);
	'''

	def createDefaultEditPoliciesBody(GenChildLabelNode it) '''
		super.createDefaultEditPolicies();
		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.SEMANTIC_ROLE, new «getItemSemanticEditPolicyQualifiedClassName(it)»());
		installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextNonResizableEditPolicy());
		installEditPolicy(org.eclipse.gef.EditPolicy.COMPONENT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.ListItemComponentEditPolicy());
		installEditPolicy(org.eclipse.gef.EditPolicy.DIRECT_EDIT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy());
		«xptEditpartsCommon.behaviour(it)»
	'''


	def handleNotificationEventBody(GenChildLabelNode it) '''
			Object feature = event.getFeature();
	«handleText(it)»
	«IF labelElementIcon»
		if(event.getNewValue() instanceof org.eclipse.emf.ecore.EAnnotation && org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON.equals(((org.eclipse.emf.ecore.EAnnotation)event.getNewValue()).getSource())){	
			refreshLabel();
		}
	«ENDIF»
	if (org.eclipse.uml2.uml.UMLPackage.eINSTANCE.getFeature_IsStatic().equals(feature)) {
			refreshUnderline();
	}
	super.handleNotificationEvent(event);
	'''

	def isSelectable(GenChildLabelNode it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isSelectable() {
			return getFigure().isShowing();
		}
	'''
}
