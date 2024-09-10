/*******************************************************************************
 * Copyright (c) 2006-2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram.edithelpers;

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType
import xpt.Common

class EditHelperAdvice {
	@Inject extension Common;

	def className(SpecializationType it) '''«it.editHelperAdviceClassName»'''

	def packageName(SpecializationType it) '''«it.diagramElement.getDiagram().editHelpersPackageName»'''

	def qualifiedClassName(SpecializationType it) '''«packageName(it)».«className(it)»'''

	def fullPath(SpecializationType it) '''«qualifiedClassName(it)»'''

	def EditHelperAdvice(SpecializationType it) '''
		«copyright(diagramElement.diagram.editorGen)»
		package «packageName(it)»;

		public class «className(it)» «extendsClause(it)» {

		}
	'''

	def extendsClause(SpecializationType it) '''extends «superClass(it)»'''

	def superClass(SpecializationType it) '''org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice'''

}
