/*******************************************************************************
 * Copyright (c) 2013-2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Svyatoslav Kovalsky (Montages) - initial API and implementation
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import com.google.inject.Inject

@com.google.inject.Singleton class Access {

	@Inject Externalizer ext;

	def className(GenEditorGenerator it) '''«ext.accessClassName(it)»'''

	def packageName(GenEditorGenerator it) '''«ext.accessPackageName(it)»'''

	def qualifiedClassName(GenEditorGenerator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenEditorGenerator it) '''«qualifiedClassName(it)»'''

	def Access(GenEditorGenerator it) '''«ext.Access(it)»'''
}