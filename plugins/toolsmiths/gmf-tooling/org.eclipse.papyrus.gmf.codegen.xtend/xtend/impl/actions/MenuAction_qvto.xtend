/*******************************************************************************
 * Copyright (c) 2009-2013 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package impl.actions

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator

@com.google.inject.Singleton class MenuAction_qvto {

	def boolean hasCommandsToContribute(GenEditorGenerator editorGen) {
		return !editorGen.contextMenus.empty
	}

	def boolean hasHandlersToContribute(GenEditorGenerator editorGen) {
		return hasCommandsToContribute(editorGen)
	}

}
