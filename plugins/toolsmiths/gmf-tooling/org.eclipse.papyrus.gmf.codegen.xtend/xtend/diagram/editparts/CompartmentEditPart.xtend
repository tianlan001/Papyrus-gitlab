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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 generate less dead or duplicate code + missing @override
 * Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Pull up refreshVisuals/setRatio for shape compartments (LinkLFShapeCompartmentEditPart)
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import xpt.Common
import xpt.diagram.editparts.Utils_qvto
import xpt.CodeStyle

@Singleton class CompartmentEditPart {
	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension Utils_qvto;

	@Inject impl.diagram.editparts.CompartmentEditPart xptCompartmentEditPartImpl;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def qualifiedClassName(GenCompartment it) '''«xptCompartmentEditPartImpl.packageName(it)».«xptCompartmentEditPartImpl.className(it)»'''

	def fullPath(GenCompartment it) '''«qualifiedClassName(it)»'''

	def Main(GenCompartment it) '''
		«copyright(getDiagram().editorGen)»
		package «xptCompartmentEditPartImpl.packageName(it)»;

		«generatedClassComment»
		public class «xptCompartmentEditPartImpl.className(it)» «extendsList(it)» {

			«attributes(it)»

			«xptCompartmentEditPartImpl.constructor(it)»
			«xptCompartmentEditPartImpl.hasModelChildrenChanged(it)»
			«xptCompartmentEditPartImpl.getCompartmentName(it)»
			«xptCompartmentEditPartImpl.createFigure(it)»
			«createDefaultEditPolicies(it)»
			«IF !commonResizableCompartment && superEditPart !== null»
				«xptCompartmentEditPartImpl.refreshVisuals(it)»
				«handleNotificationEvent(it)»
				«xptCompartmentEditPartImpl.refreshBounds(it)»
				«xptCompartmentEditPartImpl.setRatio(it)»
				«xptCompartmentEditPartImpl.getTargetEditPartMethod(it)»
				«additions(it)»
			«ENDIF»
		}
	'''

	def extendsList(GenCompartment it) '''
		«IF superEditPart!== null»
			extends «superEditPart»
		«ELSE»
			extends «IF listLayout»org.eclipse.papyrus.uml.diagram.common.editparts.AbstractListCompartmentEditPart«ELSE»org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.LinkLFShapeCompartmentEditPart«ENDIF»
		«ENDIF»
	'''


	def attributes(GenCompartment it) '''
		«xptEditpartsCommon.visualIDConstant(it)»
	'''

	def createDefaultEditPolicies(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			«xptCompartmentEditPartImpl.createDefaultEditPoliciesBody(it)»
		}
	'''

	def handleNotificationEvent(GenCompartment it) '''
		«IF isStoringChildPositions(node)»
			«generatedMemberComment»
			«overrideC»
			protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification notification) {
				«xptCompartmentEditPartImpl.handleNotificationEventBody(it)»
			}
		«ENDIF»
	'''

	def additions(GenCompartment it) '''
		«handleSize(it)»
		«refreshbound(it)»
		«refreshvisual(it)»
	'''

	def handleSize(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification notification) {
			Object feature = notification.getFeature();
			if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Width().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Height().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_X().equals(feature)
				|| org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
				refreshBounds();
			}
			super.handleNotificationEvent(notification);
		} 
	'''

	def refreshbound(GenCompartment it) '''
		«generatedMemberComment»
		protected void refreshBounds() {
			int width = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Width())).intValue();
			int height = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Height())).intValue();
			org.eclipse.draw2d.geometry.Dimension size = new org.eclipse.draw2d.geometry.Dimension(width, height);
			int x = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_X())).intValue();
			int y = ((Integer) getStructuralFeatureValue(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_Y())).intValue();
			org.eclipse.draw2d.geometry.Point loc = new org.eclipse.draw2d.geometry.Point(x, y);
			((org.eclipse.gef.GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new org.eclipse.draw2d.geometry.Rectangle(loc, size));
		}
	'''

	def refreshvisual(GenCompartment it) '''
		«generatedMemberComment»
		«overrideC»
		protected void refreshVisuals() {
			super.refreshVisuals();
			refreshBounds();
		}
	'''

	// Bug 569174 : L1.2 generate less dead or duplicate code : 
	//  - Common generated methods from ResizeableListCompartmentEditPart
	//  - moved to intermediate class AbstractResizableCompartmentEditPart
	def boolean isCommonResizableCompartment(GenCompartment node) {
		return null !== node.superEditPart
		// use regex 569174 to avoid static ref to diagram specific implementation
		&& node.superEditPart.matches('^org\\.eclipse\\.papyrus\\..*Compartment.*EditPart$')
	}
}
