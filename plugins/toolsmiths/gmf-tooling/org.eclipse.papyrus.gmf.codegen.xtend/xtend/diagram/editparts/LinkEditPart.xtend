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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import xpt.Common
import xpt.CodeStyle

@com.google.inject.Singleton class LinkEditPart {
	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject impl.diagram.editparts.LinkEditPart xptLinkEditPart;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def qualifiedClassName(GenLink it) '''«xptLinkEditPart.packageName(it)».«xptLinkEditPart.className(it)»'''

	def fullPath(GenLink it) '''«qualifiedClassName(it)»'''

	def Main(GenLink it) '''
	«copyright(getDiagram().editorGen)»
	package «xptLinkEditPart.packageName(it)»;

	«generatedClassComment»
	public class «xptLinkEditPart.className(it)» «extendsList(it)» «implementsList(it)» {

		«attributes(it)»
		
		«xptLinkEditPart.constructor(it)»
		«createDefaultEditPolicies(it)»
		«xptLinkEditPart.addFixedChild(it)»
		«xptLinkEditPart.addChildVisual(it)»
		«xptLinkEditPart.removeFixedChild(it)»
		«xptLinkEditPart.removeChildVisual(it)»
		«xptLinkEditPart.createLinkFigure(it)»
	}
	'''

	def extendsList(GenLink it) '''extends «xptLinkEditPart.extendsListContents(it)»'''

	def implementsList(GenLink it) '''
		«IF treeBranch»implements org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart«ENDIF»
	'''

	def attributes(GenLink it) '''
		«xptEditpartsCommon.visualIDConstant(it)»
	'''

	def createDefaultEditPolicies(GenLink it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			«xptLinkEditPart.createDefaultEditPoliciesBody(it)»
		}
	'''
}
