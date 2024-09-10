/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013, 2021 Borland Software Corporation and others, CEA LIST, Artal and others
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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.CodeStyle
import xpt.Common

class DiagramEditPart {

	@Inject extension Common;
	@Inject extension CodeStyle;

	@Inject impl.diagram.editparts.DiagramEditPart xptDiagramEditPart;

	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;

	def qualifiedClassName(GenDiagram it) '''«xptDiagramEditPart.packageName(it)».«xptDiagramEditPart.className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def Main(GenDiagram it) '''
		«copyright(editorGen)»
		package «xptDiagramEditPart.packageName(it)»;

		«generatedClassComment»
		public class «xptDiagramEditPart.className(it)» «extendsList(it)» {

			«attributes(it)»

			«xptDiagramEditPart.constructor(it)»

			«createDefaultEditPolicies(it)»

			«xptDiagramEditPart.createFigure(it)»
		}
	'''

	def extendsList(GenDiagram it) '''extends «xptDiagramEditPart.extendsListContents(it)»'''

	def attributes(GenDiagram it) '''
		«generatedMemberComment»
		public final static String MODEL_ID = "«editorGen.modelID»"; «nonNLS(1)»

		«xptEditpartsCommon.visualIDConstant(it)»
	'''

	def createDefaultEditPolicies(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void createDefaultEditPolicies() {
			«xptDiagramEditPart.createDefaultEditPoliciesBody(it)»
		}
	'''

}
