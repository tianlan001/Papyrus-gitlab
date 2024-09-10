/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.services.SaveLayoutBeforeClose;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Command handler for the private page-layout storage toggle menu.
 */
public class TogglePageLayoutStorageHandler extends AbstractHandler {

	public TogglePageLayoutStorageHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart active = HandlerUtil.getActiveEditor(event);
		if (active instanceof IMultiDiagramEditor) {
			IMultiDiagramEditor editor = (IMultiDiagramEditor) active;

			// Toggle the storage of the layout
			togglePrivatePageLayout(editor);

			// And then save the layout immediately if the editor is not dirty
			// (if it is dirty, then the layout will be saved when the editor
			// is saved; saving it now would possibly result in inconsistencies)
			try {
				SaveLayoutBeforeClose save = editor.getServicesRegistry().getService(SaveLayoutBeforeClose.class);
				save.saveBeforeClose(editor);
			} catch (ServiceException e) {
				// Doesn't matter; we'll just have to rely on the normal editor save
			}
		}

		return null;
	}

	public void togglePrivatePageLayout(IMultiDiagramEditor editor) {
		Command command = new SashLayoutCommandFactory(editor).createTogglePrivateLayoutCommand();
		EditingDomain domain = editor.getAdapter(EditingDomain.class);

		// Don't execute on the undo history because the changes in the sash model
		// are never tracked for undo/redo
		try {
			TransactionHelper.run(domain, () -> command.execute());
		} catch (RollbackException e) {
			StatusManager.getManager().handle(e.getStatus());
		} catch (InterruptedException e) {
			Activator.log.error("Failed to execute page layout toggle command", e); //$NON-NLS-1$
		}
	}

}
