/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package diagram.editpolicies

import com.google.inject.Inject
import impl.diagram.update.CanonicalUpdate
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildContainer
import xpt.Common

@com.google.inject.Singleton class ChildContainerCanonicalEditPolicy {
	@Inject extension Common;

	@Inject CanonicalUpdate xptCanonicalUpdate;

	def className(GenChildContainer it) '''«canonicalEditPolicyClassName»'''

	def packageName(GenChildContainer it) '''«it.diagram.editPoliciesPackageName»'''

	def qualifiedClassName(GenChildContainer it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenChildContainer it) '''«qualifiedClassName(it)»'''

	def extendsList(GenChildContainer it) '''extends org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy'''

	def Main(GenChildContainer it) '''
		«copyright(it.diagram.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {
			«xptCanonicalUpdate.body(it)»
			
			«refreshSemanticMethod(it)»
		«additions(it)»
		}
	'''

	def refreshSemanticMethod(GenChildContainer it) '''
		«generatedMemberComment»
		protected void refreshSemantic() {
			if (resolveSemanticElement() == null) {
				return;
			}
			java.util.LinkedList<org.eclipse.core.runtime.IAdaptable> createdViews = new java.util.LinkedList<org.eclipse.core.runtime.IAdaptable>();
			«xptCanonicalUpdate.refreshSemanticChildren(it, 'createdViews', it.diagram.editorGen.diagramUpdater)»
			«xptCanonicalUpdate.executeLayoutCommand(it, 'createdViews')»
			
			makeViewsImmutable(createdViews);
		}
	'''

	def additions(GenChildContainer it) ''''''
}
