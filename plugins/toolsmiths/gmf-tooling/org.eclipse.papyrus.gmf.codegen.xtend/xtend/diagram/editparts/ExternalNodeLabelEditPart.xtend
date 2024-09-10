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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.2 cleanup + less code with AbstractExternalLabelEditPart
 *****************************************************************************/
package diagram.editparts

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import xpt.Common
import xpt.CodeStyle
import xpt.editor.VisualIDRegistry
import impl.diagram.editparts.TextAwareExtent

@com.google.inject.Singleton class ExternalNodeLabelEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;
	@Inject VisualIDRegistry xptVisualIDRegistry;

	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;
	@Inject TextAwareExtent xptTextAware;

	def qualifiedClassName(GenExternalNodeLabel it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenExternalNodeLabel it) '''«qualifiedClassName(it)»'''

	def Main(GenExternalNodeLabel it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«attributes(it)»

			«initializer(it)»

			«constructor(it)»

			«createDefaultEditPolicies(it)»

			«xptTextAware.getLabelIconNotUseElementIcon(it, elementIcon, diagram)»

			«xptTextAware.methodsExtent(it, false, readOnly, modelFacet, node)»

			«createFigure(it)»

			«additions(it)»
		}
	'''

	def className(GenExternalNodeLabel it) '''«editPartClassName»'''

	def packageName(GenExternalNodeLabel it) '''«getDiagram().editPartsPackageName»'''

	def extendsList(GenExternalNodeLabel it) {
		// Bug 569174 : 1.2 cleanup extra newline
		if (superEditPart !== null) {
			'extends ' + superEditPart
		} else {
			'extends org.eclipse.papyrus.uml.diagram.common.editparts.AbstractExternalLabelEditPart'
		}
	}

	def implementsList(GenExternalNodeLabel it) '''
		implements org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart, org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart
		«IF labelVisibilityPreference !== null»
			, org.eclipse.papyrus.uml.diagram.common.editparts.ILabelRoleProvider
		«ENDIF»
	'''

	def attributes(GenExternalNodeLabel it) '''
		«xptEditpartsCommon.visualIDConstant(it)»
	'''

	def constructor(GenExternalNodeLabel it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''
		def initializer(GenExternalNodeLabel it) '''
		«generatedMemberComment»
		static {
			registerSnapBackPosition(«xptVisualIDRegistry.typeMethodCall(it)», new org.eclipse.draw2d.geometry.Point(0, 0));
		}
	'''

	def createDefaultEditPolicies(GenExternalNodeLabel it) '''
		«IF !behaviour.empty»
			«generatedMemberComment»
			«overrideC»
			protected void createDefaultEditPolicies() {
				super.createDefaultEditPolicies();
				«xptEditpartsCommon.behaviour(it)»
			}
		«ENDIF»
	'''

	def additions(GenExternalNodeLabel it) '''
	«««	BEGIN: PapyrusGenCode
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
	«««	END: PapyrusGenCode
	'''

	def createFigure(GenExternalNodeLabel it) '''
		«xptEditpartsCommon.labelFigure(viewmap)»
	'''	

}
