/*****************************************************************************
 * Copyright (c) 2015, 2021, 2023 Christian W. Damus, CEA LIST, Artal and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 * Ansgar Radermacher - bug 582492, move to com.google.inject
 *****************************************************************************/
package xpt.providers

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.CodeStyle
import xpt.Common
import xpt.editor.VisualIDRegistry
import xpt.providers.ElementTypes
import com.google.inject.Singleton
import com.google.inject.Inject

/**
 * Template for the class that plugs in knowledge of the Visual IDs of this diagram
 * into the {@code VisualTypeService}.
 */
@Singleton class VisualTypeProvider {

	@Inject extension Common
	@Inject extension CodeStyle
	@Inject VisualIDRegistry visualIDs
	@Inject ElementTypes elementTypes

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«visualTypeProvider»'''

	protected def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public «visualTypeProvider»() {
			super();
		}
	'''

	protected def getElementType_(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public org.eclipse.gmf.runtime.emf.type.core.IElementType getElementType(org.eclipse.gmf.runtime.notation.Diagram diagram, String viewType) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType result = null;
			try {
				result = «elementTypes.qualifiedClassName(it)».getElementType(viewType);
			} catch (NumberFormatException e) {
				// Not supported by this diagram
			}
			return result;
		}
	'''

	protected def getNodeType(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public String getNodeType(org.eclipse.gmf.runtime.notation.View parentView, org.eclipse.emf.ecore.EObject element) {
			return «visualIDs.getNodeVisualIDMethodCall(it)»(parentView, element);
		}
	'''

	protected def getLinkType(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public String getLinkType(org.eclipse.gmf.runtime.notation.Diagram diagram, org.eclipse.emf.ecore.EObject element) {
			return «visualIDs.getLinkWithClassVisualIDMethodCall(it)»(element);
		}
	'''

	def VisualTypeProvider(GenDiagram it) '''
		«editorGen.copyright»
		package «packageName»;

		«generatedClassComment»
		public class «visualTypeProvider» extends org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.AbstractVisualTypeProvider {

			«constructor»
			«getElementType_»
			«getNodeType»
			«getLinkType»
		}
	'''
}