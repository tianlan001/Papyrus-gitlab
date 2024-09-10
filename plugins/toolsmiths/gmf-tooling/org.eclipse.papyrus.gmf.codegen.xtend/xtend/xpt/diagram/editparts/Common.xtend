/*****************************************************************************
 * Copyright (c) 2006, 2010, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Patrick Tessier (CEA LIST)
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to xpand/qvto
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 missing @override
 *****************************************************************************/
package xpt.diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import impl.diagram.editparts.TextAware
import org.eclipse.papyrus.gmf.codegen.gmfgen.Behaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomBehaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.FigureViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.InnerClassViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.SnippetViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import xpt.Common_qvto
import xpt.QualifiedClassNameProvider

@Singleton class Common {
	@Inject extension xpt.CodeStyle;
	@Inject extension xpt.Common;
	@Inject extension Common_qvto;
	@Inject QualifiedClassNameProvider qualifiedClassNameProvider;
	@Inject TextAware xptTextAware;

	def visualIDConstant(GenCommonBase it) '''
		«generatedMemberComment»
		public static final String VISUAL_ID = "«stringVisualID»"; «nonNLS(1)»
	'''

	def behaviour(GenCommonBase it) '''
		«FOR b : it.behaviour»
			«dispatchBehaviour(b)»
		«ENDFOR»
	'''

	def dispatch dispatchBehaviour(Behaviour it) ''''''

	def dispatch dispatchBehaviour(CustomBehaviour it) '''
		«IF editPolicyQualifiedClassName.nullOrSpaces»
			removeEditPolicy(«key»); «IF key.startsWith('\"') && key.endsWith('\"')»«nonNLS(1)»«ENDIF»
		«ELSE»
			installEditPolicy(«key», new «getEditPolicyQualifiedClassName()»()); «IF key.startsWith('\"') && key.endsWith('\"')»«nonNLS(1)»«ENDIF»
		«ENDIF»
	'''
	
	def dispatch dispatchBehaviour(OpenDiagramBehaviour it) '''
		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.OPEN_ROLE,
			new «it.getEditPolicyQualifiedClassName»());
	'''

	def dispatch CharSequence labelFigure(ParentAssignedViewmap it) '''
		«generatedMemberComment»
		protected org.eclipse.draw2d.IFigure createFigure() {
			// Parent should assign one using «xptTextAware.labelSetterName(it)»() method
			return null;
		}
	'''

	def dispatch CharSequence labelFigure(Viewmap it) '''
		«labelFigureDelegateToPrim(it)»
	'''

	def labelFigureDelegateToPrim(Viewmap it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.draw2d.IFigure createFigure() {
			org.eclipse.draw2d.IFigure label = createFigurePrim();
			defaultText = getLabelTextHelper(label);
			return label;
		}
	
		«generatedMemberComment»
		protected org.eclipse.draw2d.IFigure createFigurePrim() {
			«labelFigurePrim(it)»
	'''

	def dispatch labelFigurePrim(FigureViewmap it) '''
		«IF figureQualifiedClassName === null »
			return new org.eclipse.draw2d.Label();
		«ELSE»
			return new «figureQualifiedClassName»();
		«ENDIF»
		}
	'''

	def dispatch labelFigurePrim(SnippetViewmap it) '''
		return «body»;
		}
	'''

	def dispatch labelFigurePrim(InnerClassViewmap it) '''
		return new «className»();
		}

		«classBody»
	'''

	def notationalListeners(GenCommonBase it) '''
		«generatedMemberComment»
		«overrideC»
		protected void addNotationalListeners() {
			super.addNotationalListeners();
			addListenerFilter("PrimaryView", this, getPrimaryView()); «nonNLS(1)»
		}
	
		«generatedMemberComment»
		«overrideC»
		protected void removeNotationalListeners() {
			super.removeNotationalListeners();
			removeListenerFilter("PrimaryView"); «nonNLS(1)»
		}
	'''

	def handleBounds(GenCommonBase it) '''
		if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Width().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getSize_Height().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_X().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
			refreshBounds();
		}
	'''

	def handleText(GenCommonBase it) '''
		if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_FontColor().equals(feature)) {
			Integer c = (Integer) event.getNewValue();
			setFontColor(org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry.getInstance().getColor(c));
		} else if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_Underline().equals(feature)) {
			refreshUnderline();
		} else if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_StrikeThrough().equals(feature)) {
			refreshStrikeThrough();
		} else if (org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_FontHeight().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_FontName().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_Bold().equals(feature) ||
				org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFontStyle_Italic().equals(feature)) {
			refreshFont();
		} else {
			if (getParser() != null && getParser().isAffectingEvent(event, getParserOptions().intValue())) {
				refreshLabel();
			}
			if (getParser() instanceof org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser) {
				org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser modelParser =
					(org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser) getParser();
				if (modelParser.areSemanticElementsAffected(null, event)) {
					removeSemanticListeners();
					if (resolveSemanticElement() != null) {
						addSemanticListeners();
					}
					refreshLabel();
				}
			}
		}
	'''

	def installSemanticEditPolicy(GenCommonBase it) '''
		«IF sansDomain»
			removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.SEMANTIC_ROLE);
		«ELSE»
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.SEMANTIC_ROLE, new «qualifiedClassNameProvider.getItemSemanticEditPolicyQualifiedClassName(it)»());
		«ENDIF»
	'''

	def installCanonicalEditPolicy(GenContainerBase it) '''
		«IF it.needsCanonicalEditPolicy»
			«««	BEGIN: PapyrusGenCode
			«««	Used to remove at each time canonical editpolicies
			// in Papyrus diagrams are not strongly synchronised
			// installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new «getCanonicalEditPolicyQualifiedClassName()»());
			«««	END: PapyrusGenCode
		«ENDIF»
	'''
	def installCreationEditPolicy(GenCommonBase it) '''
		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CREATION_ROLE, «creationEditPolicyNewInstance»);
	'''

	def creationEditPolicyNewInstance(GenCommonBase it) '''
		new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy()
	'''

}