/*******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
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
 *   Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.plugin

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin

@com.google.inject.Singleton class options {

	def qualifiedClassName(GenPlugin it) '''.options'''
	def fullPath(GenPlugin it) '''«qualifiedClassName(it)»'''

	def options(GenPlugin it) '''
		# Tracing options for the «ID» plug-in

		# Common issues
		«ID»/debug=false

		# Visual IDs
		«ID»/debug/visualID=false
	'''
}