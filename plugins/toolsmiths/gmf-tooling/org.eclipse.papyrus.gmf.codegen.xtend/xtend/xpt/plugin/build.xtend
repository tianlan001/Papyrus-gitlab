/*******************************************************************************
 * Copyright (c) 2013, 2020, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *   Michael Golubev (Montages) - initial API and implementation
 *   Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 574258: [Toolsmiths] Improve GMF generation for manifest.MF and build.properties
 *****************************************************************************/
package xpt.plugin

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin
import org.eclipse.papyrus.gmf.codegen.util.AbstractBuild

@com.google.inject.Singleton class build extends AbstractBuild{

	def qualifiedClassName(GenPlugin it) '''build.properties'''
	def fullPath(GenPlugin it) '''«qualifiedClassName(it)»'''

	def build(GenPlugin it)'''
		«init(ID)»
		«license»
		«includes»
		«compileOrders»
		«declareSourceFolder»
		«declareOutputFolders»
	'''
	
	def declareOutputFolders(GenPlugin it) '''
		output.. = bin/
	'''
	def includes(GenPlugin it)
		'''
		«buildBinaryIncludes»
		«buildSourceIncludes»
	'''

	def compileOrders(GenPlugin it)'''
		jars.compile.order = .
	'''
	
	def declareSourceFolder(GenPlugin it)'''
		«buildSourceFolder»
	'''
	
	def license(GenPlugin it)'''
		«buildLicense(it.editorGen.copyrightText)»
	'''
	
	
}