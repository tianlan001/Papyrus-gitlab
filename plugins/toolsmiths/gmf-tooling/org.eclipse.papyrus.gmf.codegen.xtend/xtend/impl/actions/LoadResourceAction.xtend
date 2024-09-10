/*****************************************************************************
 * Copyright (c) 2013, 2027, 2021 Montages AG, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package impl.actions

import com.google.inject.Inject
import xpt.Common
import xpt.Common_qvto

@com.google.inject.Singleton class LoadResourceAction {

	@Inject extension Common;
	@Inject extension Common_qvto;

	def className(org.eclipse.papyrus.gmf.codegen.gmfgen.LoadResourceAction it) '''«lastSegment(it.qualifiedClassName)»'''

	def packageName(org.eclipse.papyrus.gmf.codegen.gmfgen.LoadResourceAction it) '''«withoutLastSegment(qualifiedClassName)»'''

	def qualifiedClassName(org.eclipse.papyrus.gmf.codegen.gmfgen.LoadResourceAction it) '''«packageName(it)».«className(it)»'''

	def Main(org.eclipse.papyrus.gmf.codegen.gmfgen.LoadResourceAction it) '''
		«copyright(it.owner.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment()»
		public class «className(it)» «extendsList(it)» {

		}
	'''

	def extendsList(org.eclipse.papyrus.gmf.codegen.gmfgen.LoadResourceAction it) '''
		extends org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.actions.DefaultLoadResourceAction
	'''
}
