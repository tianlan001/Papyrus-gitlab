/*****************************************************************************
 * Copyright (c) 2006-2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.edithelpers;

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.MetamodelType
import xpt.Common
import utils.EditHelperUtils_qvto

class EditHelper {
	@Inject extension Common;
	@Inject extension EditHelperUtils_qvto

	def className(MetamodelType it) '''«it.editHelperClassName»'''

	def packageName(MetamodelType it) '''«it.diagramElement.getDiagram().editHelpersPackageName»'''

	def qualifiedClassName(MetamodelType it) '''«packageName(it)».«className(it)»'''

	def fullPath(MetamodelType it) '''«qualifiedClassName(it)»'''

	def EditHelper(MetamodelType it) '''
		«copyright(diagramElement.diagram.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «editHelperClassName» extends «getBaseEditHelperFullName(diagramElement.getDiagram())» {

		}
	'''

}
