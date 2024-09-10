/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.internal.ui.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.internal.ui.providers.ClientContextContentProvider;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;


public class UndeployElementTypeSetConfigurationHandler extends AbstractHandler {

	protected IClientContext clientContext;

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

		activeShell.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				ListSelectionDialog dialog = new ListSelectionDialog(activeShell, ClientContextManager.getInstance().getClientContexts(), new ClientContextContentProvider(), new LabelProvider(),
						"Select the clientContext you want to register the elementtypesconfiguration to");
				dialog.open();
				Object[] clientContextSelection = dialog.getResult();

				if (clientContextSelection != null) {
					if (clientContextSelection.length > 0) {
						Object selectedClientContext = clientContextSelection[0];
						if (selectedClientContext instanceof IClientContext) {
							clientContext = (IClientContext) selectedClientContext;
						}
					}
				}
			}
		});

		if (clientContext == null) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The elementTypes configuration has not been undeployed"), StatusManager.SHOW);
		}


		Job job = new Job("Undeploy elementTypes set configuration") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Undeploying selected configurations", selection.size());
				return doExecute(selection, monitor, activeShell);
			}
		};
		job.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(final IJobChangeEvent event) {
				activeShell.getDisplay().asyncExec(new Runnable() {

					public void run() {
						if (event.getResult().isOK()) {
							MessageDialog.openInformation(activeShell, "Success", event.getResult().getMessage());
						} else if (event.getResult().getSeverity() < IStatus.ERROR) { // Errors are already logged
							StatusManager.getManager().handle(event.getResult(), StatusManager.SHOW);
						}
					}
				});
			}
		});
		job.setUser(true);
		job.schedule();
		return null;
	}

	protected IStatus doExecute(IStructuredSelection selection, IProgressMonitor monitor, Shell shell) {
		Iterator<?> selectionIterator = selection.iterator();
		MultiStatus result = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, "The elementTypes configuration has been successfully undeployed", null);
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
				monitor.subTask("Deploy " + fileName);
				URI emfURI = null;
				if (selectedFile.getFullPath() != null) {
					emfURI = URI.createPlatformResourceURI(selectedFile.getFullPath().toString(), true);
				} else if (selectedFile.getRawLocation() != null) {
					emfURI = URI.createFileURI(selectedFile.getRawLocation().toString());
				}
				if (emfURI == null) {
					monitor.worked(1);
					result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The selected element is not a valid configuration file"));
					continue;
				}
				monitor.subTask("Reset elementTypes Registry");

				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.getResource(emfURI, true);

				EObject root = resource.getContents().get(0);
				if (!(root instanceof ElementTypeSetConfiguration)) {
					result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The selected element is not a valid configuration file"));
					continue;
				}

				ElementTypeSetConfigurationRegistry.getInstance().unload(clientContext.getId(), ((ElementTypeSetConfiguration) root).getIdentifier());

				monitor.worked(1);

				result.add(new Status(IStatus.OK, Activator.PLUGIN_ID, "The elementTypes configuration has been successfully undeployed"));
			}
		}
		if (result.getChildren().length == 1) {
			return result.getChildren()[0];
		} else { // Merge the result and specify an appropriate message based on the result
			if (result.isOK()) {
				return result;
			} else {
				MultiStatus actualResult = new MultiStatus(Activator.PLUGIN_ID, result.getCode(), "Some errors occurred during the undeployment", result.getException());
				actualResult.merge(result);
				return actualResult;
			}
		}
	}
}
