/*******************************************************************************
 * Copyright (c) 2010 Artem Tikhomirov and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (independent) - Initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package plugin

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin
import com.google.inject.Inject
import xpt.plugin.ActivatorImpl

@com.google.inject.Singleton class Activator { 
	
	@Inject ActivatorImpl activatorImpl;
	
	def className(GenPlugin it) '''«it.activatorClassName»'''

	def packageName(GenPlugin it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenPlugin it) '''«packageName(it)».«className(it)»'''
	
	def fullPath(GenPlugin it) '''«qualifiedClassName(it)»'''
	//	/**
	//	 * FIXME[artem]: For 2.3, delegates to legacy xpt::plugin::Activator template.
	//	 * Refactoring (moving templates out from xpt) pending.
	//	 */
	//	def Main(GenPlugin it) '''«xpt::plugin::Activator::Activator(it)»'''
	// access to the sole Activator instance
	@MetaDef def instanceAccess(GenEditorGenerator it) '''«qualifiedClassName(it.plugin)».getInstance()'''

	@MetaDef def preferenceHintAccess(GenEditorGenerator it) '''«qualifiedClassName(it.plugin)».DIAGRAM_PREFERENCES_HINT'''
	
	def Main(GenPlugin it) '''«activatorImpl.ActivatorImpl(it)»'''
}
