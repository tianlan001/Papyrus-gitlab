/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Anna Karjakina (Borland) - initial API and implementation
 *	Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef

@com.google.inject.Singleton class ExternalizerUtils_qvto {

	@MetaDef def String getExternalizerPackageName(GenEditorGenerator generator) {
		return generator.editor.packageName
	}

	@MetaDef def String getExternalizerClassName() {
		return 'Messages'
	}

	def String escapeIllegalKeySymbols(String key) {
		return key.replaceAll('[=&\"]', '').replaceAll('[ .]', '_');
	}

	def String escapeIllegalMessageSymbols(String message) {
		return message.replaceAll('!', '\\\\!')
	}

	def String titleKey(String dialogKey) {
		return dialogKey + 'Title'
	}

	def String messageKey(String dialogKey) {
		return dialogKey + 'Message'
	}

	def String nameKey(String dialogKey) {
		return dialogKey + 'Name'
	}

	def String descriptionKey(String dialogKey) {
		return dialogKey + 'Description'
	}

}
