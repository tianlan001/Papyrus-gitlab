/*****************************************************************************
 * Copyright (c) 2007, 2009, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Artem Tikhomirov (Borland) - [257119] Create views directly, not through ViewFactories
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.views

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.Behaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.ColorAttributes
import org.eclipse.papyrus.gmf.codegen.gmfgen.DesignLabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import xpt.Common
import xpt.diagram.Utils_qvto
import xpt.diagram.ViewmapAttributesUtils_qvto

/**
 * Renamed from xpt::diagram::views::Utils.xpt 
 * in order to have consistent naming between Xtend files migrated from _qvto helpers and xpt templates
 */
@com.google.inject.Singleton class ViewStyles {
	@Inject extension Common;
	@Inject extension ViewmapAttributesUtils_qvto;
	@Inject extension Utils_qvto;

	@Inject MetaModel xptMetaModel;

	/**
	 * @deprecated FIXME: ColorAttributes class is deprecated
	 */
	def dispatch CharSequence foregroundColour(Viewmap it, String viewVar) '''
		«FOR attr : it.attributes.filter(typeof(ColorAttributes))»
			«foregroundColour(attr, viewVar)»
		«ENDFOR»
	'''

	def dispatch CharSequence foregroundColour(ColorAttributes it, String viewVar) '''
		org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.setStructuralFeatureValue(«viewVar», org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getLineStyle_LineColor(), org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities.colorToInteger(org.eclipse.draw2d.ColorConstants.«foregroundColor»));
	'''

	/**
	 * @deprecated FIXME: ColorAttributes class is deprecated
	 */
	def dispatch CharSequence backgroundColour(Viewmap it, String viewVar) '''
		«FOR attr : it.attributes.filter(typeof(ColorAttributes))»
			«backgroundColour(attr, viewVar)»
		«ENDFOR»
	'''

	def dispatch backgroundColour(ColorAttributes it, String viewVar) '''
		org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.setStructuralFeatureValue(«viewVar», org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getFillStyle_FillColor(), org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities.colorToInteger(org.eclipse.draw2d.ColorConstants.«backgroundColor»));
	'''

	def addFontLineFillStylesConditionally(Viewmap it, String stylesVar) '''
		«IF !it.isFixedFont»
			«stylesVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createFontStyle());
		«ENDIF»
		«IF !it.isFixedForeground»
			«stylesVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createLineStyle());
		«ENDIF»
		«IF !it.isFixedBackground»
			«stylesVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createFillStyle());
		«ENDIF»
	'''

	def dispatch addTextStyle(LabelModelFacet it, String stylesVar) ''''''

	def dispatch addTextStyle(DesignLabelModelFacet it, String stylesVar) '''
		«stylesVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createDescriptionStyle());
	'''

	def dispatch CharSequence addLinkedDiagramStyle(GenCommonBase it, String stylesVar) '''«FOR b : behaviour»«addLinkedDiagramStyle(
		b, stylesVar)»«ENDFOR»'''

	def dispatch CharSequence addLinkedDiagramStyle(Behaviour it, String stylesVar) ''''''

	def dispatch CharSequence addLinkedDiagramStyle(OpenDiagramBehaviour it, String stylesVar) '''
		«IF diagramKind !== null »
			{
				org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle diagramFacet = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createHintedDiagramLinkStyle();
				diagramFacet.setHint("«diagramKind»"); «nonNLS(1)»
				«stylesVar».add(diagramFacet);
			}
		«ELSE»
			«stylesVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createHintedDiagramLinkStyle());
		«ENDIF»
	'''

	def addCustomStyles(GenCommonBase it, String stylesVar) '''
		«FOR style : styles»
			«stylesVar».add(«xptMetaModel.NewInstance(style)»);
		«ENDFOR»
	'''

	// external node and link labels' offset
	def dispatch offset(GenLabel it, String viewVar) ''''''

	def dispatch offset(GenLinkLabel it, String viewVar) '''
		«IF labelOffsetX(it.viewmap, 0) != 0 || labelOffsetY(it.viewmap, 0) != 0»
			«offset(it, viewVar, labelOffsetX(viewmap, 0), labelOffsetY(viewmap, 0))»
		«ENDIF»
	'''

	def dispatch offset(GenExternalNodeLabel it, String viewVar) '''
		«IF labelOffsetX(viewmap, 0) != 0 || labelOffsetY(viewmap, 0) != 0»
			«offset(it,viewVar, labelOffsetX(viewmap, 0), labelOffsetY(viewmap, 0))»
		«ELSE»
			«offset(it,viewVar, 0, 15)»
		«ENDIF»
	'''
	/**
	 * viewVar must be already inserted into a diagram (view.getDiagram() should be meaningful)
	 */
	def offset(GenLabel it, String viewVar, int x, int y) '''
		«val location = stringUniqueIdentifier.toFirstLower+'_Location'»
		org.eclipse.gmf.runtime.notation.Location «location» = (org.eclipse.gmf.runtime.notation.Location) «viewVar».getLayoutConstraint();
		«IF it.getDiagram().isPixelMapMode()»
			«location».setX(«x»);
			«location».setY(«y»);
		«ELSE»
			«location».setX(org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper.getMapMode(«viewVar».getDiagram().getMeasurementUnit()).DPtoLP(«x»));
			«location».setY(org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper.getMapMode(«viewVar».getDiagram().getMeasurementUnit()).DPtoLP(«y»));
		«ENDIF»
	'''

}
