/*****************************************************************************
 * Copyright (c) 2006, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Michael Golubev (Montages) - #368169 - extract common code to GMFT-specific runtime
 * 							  - #386838 - migrate to Xtend2
 * Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.edithelpers;

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common

 class BaseEditHelper {
	@Inject extension Common;

	def extendsClause(GenDiagram it) '''extends «superClass(it)»'''

	def superClass(GenDiagram it) '''org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.edit.helpers.GeneratedEditHelperBase'''

	def className(GenDiagram it) '''«it.baseEditHelperClassName»'''

	def packageName(GenDiagram it) '''«it.editHelpersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def BaseEditHelper(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» «extendsClause(it)» {

		}
	'''

}
