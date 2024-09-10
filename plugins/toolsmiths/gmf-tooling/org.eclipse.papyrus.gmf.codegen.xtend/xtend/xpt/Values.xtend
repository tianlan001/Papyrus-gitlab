/******************************************************************************
 * Copyright (c) 2014, 2020 Borland Software Corporation, CEA LIST, Artal and others
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

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator

@com.google.inject.Singleton class Values {

	@Inject Externalizer ext;

	def qualifiedClassName(GenEditorGenerator it) '''messages.properties'''
	def fullPath(GenEditorGenerator it) '''«qualifiedClassName(it)»'''

	def Values(GenEditorGenerator it) '''«ext.Values(it)»'''

}