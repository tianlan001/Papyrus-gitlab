/*****************************************************************************
 * Copyright (c) 2006, 2009, 2021 Borland Software Corporation, CEA LIST, ARTAL and others
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
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Externalizer
import xpt.diagram.editparts.Utils_qvto
import xpt.CodeStyle

@Singleton class CompartmentEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject extension Utils_qvto;

	@Inject Externalizer xptExternalizer;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def className(GenCompartment it) '''«editPartClassName»'''

	def packageName(GenCompartment it) '''«getDiagram().editPartsPackageName»'''

	def constructor(GenCompartment it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''

	def hasModelChildrenChanged(GenCompartment it) '''
		«IF listLayout»
			«generatedMemberComment»
			«overrideC»
			protected boolean hasModelChildrenChanged(org.eclipse.emf.common.notify.Notification evt) {
				return false;
			}
		«ENDIF»
	'''

	def getCompartmentName(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		public String getCompartmentName() {
			return «xptExternalizer.accessorCall(diagram.editorGen, i18nKeyForCompartmentTitle(it))»;
		}
	'''

	def createFigure(GenCompartment it) {
		if (hasExternalSuperClass(it,
			'org.eclipse.papyrus.uml.diagram.activity.edit.part.ShapeCompartmentWithoutScrollbarsEditPart')) {
			'''
				«overrideC»
				public org.eclipse.draw2d.IFigure createFigure() {
					return super.createFigure();
				}
			'''
		} else {

			'''
				«IF !needsTitle»
					«/*By default titles are shown even if there are no TitleStyle, we need to switch it off*/generatedMemberComment»
					«overrideC»
					public org.eclipse.draw2d.IFigure createFigure() {
						org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure result = (org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure) super.createFigure();
						result.setTitleVisibility(false);
						return result;
					}
				«ENDIF»
			'''
		}
	}

	def CharSequence createDefaultEditPoliciesBody(GenCompartment it) '''
		super.createDefaultEditPolicies();
		«IF canCollapse»
			installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy());
		«ENDIF»
		«xptEditpartsCommon.installSemanticEditPolicy(it)»
		«IF ! childNodes.empty»
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CREATION_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy());
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.DRAG_DROP_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy());
			installEditPolicy(org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy.PASTE_ROLE, new org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy());
		«ENDIF»
		«xptEditpartsCommon.installCanonicalEditPolicy(it)»
		«xptEditpartsCommon.behaviour(it)»
	'''

	def additionalEditPolicies(GenCompartment it) ''''''

	def refreshVisuals(GenCompartment it) '''
		«IF isStoringChildPositions(node)»
			«generatedMemberComment»
			«overrideC»
			protected void refreshVisuals() {
				super.refreshVisuals();
				refreshBounds();
			}
		«ENDIF»
	'''

	def handleNotificationEventBody(GenCompartment it) '''
		super.handleNotificationEvent(notification);
		Object feature = notification.getFeature();
		if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Width().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Height().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_X().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
			refreshBounds();
		} 
	'''

	def refreshBounds(GenCompartment it) '''
		«IF isStoringChildPositions(node)»
			«generatedMemberComment»
			«overrideC»
			protected void refreshBounds() {
				int x = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_X())).intValue();
				int y = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_Y())).intValue();
				int width = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Width())).intValue();
				int height = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Height())).intValue();
				((org.eclipse.gef.GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new org.eclipse.draw2d.geometry.Rectangle(x, y, width, height));
			}
		«ENDIF»
	'''

	def setRatio(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		protected void setRatio(Double ratio) {
			«IF ViewmapLayoutType::UNKNOWN_LITERAL == node.layoutType»
				if (getFigure().getParent().getLayoutManager() instanceof org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout) {
					super.setRatio(ratio);
				}
			«ELSE»
				// nothing to do -- parent layout does not accept Double constraints as ratio
				// super.setRatio(ratio); 
			«ENDIF»
		}
	'''

	def getTargetEditPartMethod(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.gef.EditPart getTargetEditPart(org.eclipse.gef.Request request) {
			return super.getTargetEditPart(request);
		}
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
	«FOR compartment : it.compartments»
		«internal_i18nAccessors(compartment)»
	«ENDFOR»
	'''

	@Localization def internal_i18nAccessors(GenCompartment it) //
	'''«IF null !== title»«xptExternalizer.accessorField(i18nKeyForCompartmentTitle(it))»«ENDIF»'''

	@Localization def i18nValues(GenDiagram it) '''
		«FOR compartment : it.compartments»
			«internal_i18nValues(compartment)»
		«ENDFOR»
	'''

	@Localization def internal_i18nValues(GenCompartment it) '''
		«IF null !== title»«xptExternalizer.messageEntry(i18nKeyForCompartmentTitle(it), title)»«ENDIF»
	'''

	@Localization def String i18nKeyForCompartmentTitle(GenCompartment compartment) {
		return className(compartment) + '.title'
	}

	def boolean hasExternalSuperClass(GenCompartment it, String className) {
		superEditPart == className
	}
}
