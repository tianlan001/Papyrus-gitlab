/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.handler;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Activator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * @author Remi Schnekenburger
 */
public abstract class AbstractUndeployPaletteConfigurationHandler extends AbstractHandler {

	/**
	 *
	 */
	public AbstractUndeployPaletteConfigurationHandler() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection) || currentSelection.isEmpty()) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) currentSelection;

		final Shell activeShell = HandlerUtil.getActiveShell(event);


		doExecute(selection, activeShell, new NullProgressMonitor());
		return null;
	}

	protected void doExecute(IStructuredSelection selection, Shell activeShell, IProgressMonitor monitor) {
		Iterator<?> selectionIterator = selection.iterator();

		MultiStatus result = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, "The palette configuration has been successfully deactivated and undeployed", null);

		while (selectionIterator.hasNext()) {
			Object selectedElement = selectionIterator.next();
			if (selectedElement instanceof IAdaptable) {
				IFile selectedFile = (IFile) ((IAdaptable) selectedElement).getAdapter(IFile.class);
				if (selectedFile == null) {
					monitor.worked(1);
					result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The selected element is not a file"));
					continue;
				}


				String fileName = selectedFile.getFullPath().removeFileExtension().lastSegment();
				monitor.subTask("Undeploy " + fileName);

				// do not delete.
				// PapyrusPalettePreferences.deleteWorkspacePalette(fileName);

				// if not deploy, do nothing and onlyh warn
				String editorId = getEditorID(fileName);
				if (editorId == null) {
					// not deployed: cancel the undeploy action
					MessageDialog.openInformation(activeShell, "Not deployed", "This palette is currently not deployed");
					result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The palette configuration " + fileName + " was not undeployed already"));
				} else {
					result.add(deactivatePalette(fileName, editorId));
				}

			}
		}

		if (result.getChildren().length == 1) {
			if (result.isOK()) {
				MessageDialog.openInformation(activeShell, "Success", result.getMessage());
			} else if (result.getSeverity() < IStatus.ERROR) { // Errors are already logged
				StatusManager.getManager().handle(result, StatusManager.SHOW);
			}
		} else { // Merge the result and specify an appropriate message based on the result
			if (result.isOK()) {
				MessageDialog.openInformation(activeShell, "Success", result.getMessage());
			} else {
				MultiStatus actualResult = new MultiStatus(Activator.PLUGIN_ID, result.getCode(), "Some errors occurred during the deployment", result.getException());
				actualResult.merge(result);
			}
		}
	}

	/**
	 * @return
	 */
	protected abstract XMLMemento getMemento();

	/**
	 * @param fileName
	 * @return
	 */
	protected abstract IStatus deactivatePalette(String identifier, String editorId);

	/**
	 * @param identifier
	 * @return
	 */
	protected String getEditorID(String identifier) {
		return PapyrusPalettePreferences.getEditorID(identifier, getMemento());
	}
}
