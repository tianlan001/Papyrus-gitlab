/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

/**
 * A custom version of the Text actionbar contributor for org.eclipse.papyrus.infra.textedit.xtext.nested.editor.PapyrusXTextEditor.
 * This action bar contributor allows to preserve the Undo/Redo actions inside Papyrus
 */
public class CustomXtextActionBarContributor extends org.eclipse.ui.editors.text.TextEditorActionContributor {


	/**
	 * Constructor.
	 *
	 */
	public CustomXtextActionBarContributor() {
		// nothing to do
	}


	/**
	 * @see org.eclipse.ui.editors.text.TextEditorActionContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 *
	 * @param part
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {
		final IActionBars actionBars = getActionBars();

		final IAction undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO.getId());
		final IAction redo = actionBars.getGlobalActionHandler(ActionFactory.REDO.getId());

		super.setActiveEditor(part);// this super seems erase undo/redo actions

		// see bug Bug 579033: [Diagram][KeyBinding] Undo/Redo actions are broken in diagram and it seems comes form the keybinding
		if (undo != null) {
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		}
		if (redo != null) {
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
		}
	}
}
