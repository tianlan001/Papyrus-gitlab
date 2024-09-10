/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.example.core.sashwindows.fulleditor.texteditor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Command to create a new TextEditor.
 * The command create a new IEditorModel and add it to the Sashwindow repository model.
 * 
 * @author dumoulin
 */
public class CreateTextEditorCommandHandler extends AbstractHandler implements IHandler {

	/**
	 * Method called when the command is invoked.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// Create the Editor Model
		IEditorModel model = new TextEditorPartModel();
		// Get the Sashwindow model
		ISashWindowsContentProvider contentProvider = getSashWindowsContentProvider(event);
		if(contentProvider == null) {
			showErrorDialog("Can't create Editor. Reason: Can't get current editor ContentProvider.");
		}
		// Add it to the current folder
		contentProvider.addPage(model);
		return null;
	}


	/**
	 * Show an ErrorDialog.
	 * 
	 * @param string
	 */
	private void showErrorDialog(String text) {
		MessageBox dialog = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_WARNING | SWT.OK | SWT.APPLICATION_MODAL);
		dialog.setText(text);
		dialog.open();
		return;
	}


	/**
	 * Get the current MultiDiagramEditor.
	 * 
	 * @return
	 */
	protected IEditorPart getMultiDiagramEditor() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = page.getActiveEditor();
		return editorPart;
	}

	/**
	 * Get the shared object.
	 * @param event 
	 * 
	 * @return
	 * @throws ExecutionException 
	 */
	protected ISashWindowsContentProvider getSashWindowsContentProvider(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditorChecked(event);
//		IEditorPart editor = getMultiDiagramEditor();

		ISashWindowsContentProvider contentProvider = (ISashWindowsContentProvider)editor.getAdapter(ISashWindowsContentProvider.class);
		return contentProvider;
	}

}
