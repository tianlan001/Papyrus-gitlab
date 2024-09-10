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
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import impl.diagram.editparts.TextAwareExtent
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomBehaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment
import xpt.Common
import xpt.diagram.ViewmapAttributesUtils_qvto
import xpt.editor.VisualIDRegistry
import xpt.CodeStyle
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap

@Singleton class LinkLabelEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;
	
	@Inject TextAwareExtent xptTextAware;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject extension ViewmapAttributesUtils_qvto;

	def className(GenLinkLabel it) '''«editPartClassName»'''

	def Main(GenLinkLabel it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {
		
			«attributes(it)»

			«initializer(it)»
			«constructor(it)»
			«createDefaultEditPolicies(it)»
			«getKeyPointExtent(it)»
			«xptTextAware.getLabelIconNotUseElementIcon(it, elementIcon, diagram)»
			«xptTextAware.methodsExtent(it, false, readOnly, modelFacet, link)»
			«handleNotificationEvent(it)»
			«IF !(viewmap instanceof ParentAssignedViewmap /* default overriden */)»
				«xptEditpartsCommon.labelFigure(it.viewmap)»
			«ENDIF»
			
			«additions(it)»
		}
	'''

	def packageName(GenLinkLabel it) '''«getDiagram().editPartsPackageName»'''

	def initializer(GenLinkLabel it) '''
		«generatedMemberComment»
		static {
			registerSnapBackPosition(«xptVisualIDRegistry.typeMethodCall(it)», new org.eclipse.draw2d.geometry.Point(«labelOffsetX(viewmap, 0)», «labelOffsetY(viewmap, 0)»));
		}
	'''
	
	def qualifiedClassName(GenLinkLabel it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenLinkLabel it) '''«qualifiedClassName(it)»'''

	
	def extendsList(GenLinkLabel it) '''extends org.eclipse.papyrus.uml.diagram.common.editparts.AbstractLinkLabelEditPart'''

	def implementsList(GenLinkLabel it) '''
		«/* BEGIN:	PapyrusGenCode */IF labelVisibilityPreference !== null»
			implements org.eclipse.papyrus.uml.diagram.common.editparts.ILabelRoleProvider
		«/* END:	PapyrusGenCode */ENDIF»
	'''

	def attributes(GenLinkLabel it) '''
		«xptEditpartsCommon.visualIDConstant(it)»
	'''


	def constructor(GenLinkLabel it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''


	def getKeyPointExtent(GenLinkLabel it) '''
		«IF alignment != LinkLabelAlignment.MIDDLE_LITERAL»
			«generatedMemberComment»
			«overrideC»
			public int getKeyPoint() {
				return org.eclipse.draw2d.ConnectionLocator.«alignment»;
			}
		«ENDIF»
	'''

	def createDefaultEditPolicies(GenLinkLabel it) '''
		/**
		 * @generated Papyrus Generation
		 */
		«overrideC»
		protected void createDefaultEditPolicies() {	
			super.createDefaultEditPolicies();
			installEditPolicy(org.eclipse.gef.EditPolicy.DIRECT_EDIT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy());
			installEditPolicy(org.eclipse.gef.EditPolicy.SELECTION_FEEDBACK_ROLE, new org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy());
			installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusLinkLabelDragPolicy());
			«FOR CustomBehaviour:it.behaviour.filter(typeof (CustomBehaviour))
			// Get the added custom behavoir
			»
			installEditPolicy(«CustomBehaviour.key», new «CustomBehaviour.editPolicyQualifiedClassName»());
			«ENDFOR»
		}
	'''

	def handleNotificationEvent(GenLinkLabel it) '''
		«IF elementIcon»
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

	def additions(GenLinkLabel it) '''
		«IF labelVisibilityPreference !== null»
			«generatedClassComment»
			«overrideI»
			public String getLabelRole(){
				return "«labelVisibilityPreference.role»";//$NON-NLS-1$
			}

			«generatedClassComment»
			«overrideI»
			public String getIconPathRole(){
				return "«labelVisibilityPreference.iconPathRole»";//$NON-NLS-1$
			}
		«ENDIF»
 	'''
}
