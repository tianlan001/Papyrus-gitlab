/*******************************************************************************
 * Copyright (c) 2021 CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: 
 *  Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - initial API and implementation
*   Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : generate less dead or duplicate code
 *****************************************************************************/
package impl.diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import xpt.CodeStyle
import xpt.Common
import xpt.diagram.ViewmapAttributesUtils_qvto
import xpt.providers.ElementTypes

@Singleton class TextAwareExtent {

	@Inject extension Common
	@Inject extension CodeStyle
	@Inject extension ViewmapAttributesUtils_qvto
	@Inject ElementTypes xptElementTypes;

	def methodsExtent(GenCommonBase it, boolean needsRefreshBounds, boolean readOnly, LabelModelFacet modelFacet, GenCommonBase host) '''
		«getParserElementNullModelFacet(it,modelFacet)»
		«isEditableReadOnly(it, readOnly)»
		«getParser(it, host)»
		«refreshVisualsNeedRefreshBounds(it,needsRefreshBounds)»
		«getFontStyleOwnerViewFixedFont(it)»
		«
		// BEGIN: PapyrusGenCode
		// Add extended editors management for direct edit
		getDirectEditionTypeReadOnly(it,readOnly)
		// END: PapyrusGenCode
		»
'''

	def getParserElementNullModelFacet(GenCommonBase it, LabelModelFacet modelFacet) '''
		«IF modelFacet === null»
			«generatedMemberComment»
			«overrideC»
			protected org.eclipse.emf.ecore.EObject getParserElement() {
				org.eclipse.emf.ecore.EObject element = resolveSemanticElement();
				return element != null ? element : (org.eclipse.gmf.runtime.notation.View) getModel();
			}
		«ENDIF»
	'''

	def getLabelIconNotUseElementIcon(GenCommonBase it, boolean useElementIcon, GenDiagram diagram) '''
		«IF !useElementIcon»
			«generatedMemberComment»
			«overrideI»
			protected org.eclipse.swt.graphics.Image getLabelIcon() {
				// not use element icon
				return null;
			}
		«ENDIF»
	'''

	def refreshVisualsNeedRefreshBounds(GenCommonBase it, boolean needsRefreshBounds) '''
		«IF needsRefreshBounds»
			«generatedMemberComment»
			«overrideC»
			protected void refreshVisuals() {
				super.refreshVisuals();
				refreshBounds();
			}
		«ENDIF»
	'''

	def getFontStyleOwnerViewFixedFont(GenCommonBase it) '''
		«IF isFixedFont(viewmap)»
			«generatedMemberComment»
			«overrideC»
			protected View getFontStyleOwnerView() {
				return (org.eclipse.gmf.runtime.notation.View) getModel();
			}
		«ENDIF»
	'''

	def getDirectEditionTypeReadOnly(GenCommonBase it, boolean readOnly) '''
		«IF readOnly»
			«generatedMemberComment»
			«overrideC»
			public int getDirectEditionType() {
				// The label is read-only (defined in GMFGen model)
				return org.eclipse.papyrus.uml.diagram.common.editpolicies.IDirectEdition.NO_DIRECT_EDITION;
			}
		«ENDIF»
	'''

	def isEditableReadOnly(GenCommonBase it, boolean readOnly) '''
		«IF readOnly»
			«generatedMemberComment»
			«overrideC»
			protected boolean isEditable() {
				return false;
			}
		«ENDIF»
	'''

	def getParser(GenCommonBase it, GenCommonBase host) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.gmf.runtime.common.ui.services.parser.IParser getParser() {
			if (parser == null) {
				parser = org.eclipse.papyrus.infra.gmfdiag.common.parsers.ParserUtil.getParser(«xptElementTypes.accessElementType(host)», getParserElement(), this, VISUAL_ID);
			}
			return parser;
		}
	'''
}
