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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation - bug 487199
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild.ui.handler;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.newchild.Activator;
import org.eclipse.papyrus.infra.newchild.CreationMenuRegistry;
import org.eclipse.papyrus.infra.newchild.messages.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Handler to undeploy a creation menu model
 */
public class UndeployCreationMenuModelHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection && !currentSelection.isEmpty()) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;
			final Shell activeShell = HandlerUtil.getActiveShell(event);

			Job job = new Job(Messages.UndeployCreationMenuModelHandler_UndeployMenuJobTitle) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask(Messages.UndeployCreationMenuModelHandler_UndeploySectionTasksTitle, selection.size());
					return doExecute(selection, monitor, activeShell);
				}
			};
			job.addJobChangeListener(new JobChangeAdapter() {

				@Override
				public void done(final IJobChangeEvent event) {
					activeShell.getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (event.getResult().isOK()) {
								MessageDialog.openInformation(activeShell, Messages.UndeployCreationMenuModelHandler_SuccessLabel, event.getResult().getMessage());
							} else if (IStatus.ERROR != event.getResult().getSeverity()) { // Errors are already logged
								StatusManager.getManager().handle(event.getResult(), StatusManager.SHOW);
							}
						}
					});
				}
			});
			job.setUser(true);
			job.schedule();
		}
		return null;
	}

	/**
	 * do the execution of undeployment on the selection
	 * 
	 * @param selection
	 *            selected files
	 * @param monitor
	 *            the monitor
	 * @return the return status of the execution
	 */
	protected IStatus doExecute(final IStructuredSelection selection, final IProgressMonitor monitor, Shell shell) {
		Iterator<?> selectionIterator = selection.iterator();
		MultiStatus result = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, Messages.UndeployCreationMenuModelHandler_Success_UndeployMenu, null);
		while (selectionIterator.hasNext()) {
			Object selectedElement = selectionIterator.next();
			if (selectedElement instanceof IAdaptable) {
				IFile selectedFile = ((IAdaptable) selectedElement).getAdapter(IFile.class);
				if (null == selectedFile) {
					monitor.worked(1);
					result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.UndeployCreationMenuModelHandler_Error_NotAFile + selectedElement.toString()));
				} else {
					String fileName = selectedFile.getFullPath().removeFileExtension().lastSegment();
					monitor.subTask(Messages.UndeployCreationMenuModelHandler_Undeploy + fileName);
					URI emfURI = null;
					if (null != selectedFile.getFullPath()) {
						emfURI = URI.createPlatformResourceURI(selectedFile.getFullPath().toString(), true);
					} else if (selectedFile.getRawLocation() != null) {
						emfURI = URI.createFileURI(selectedFile.getRawLocation().toString());
					}
					if (null == emfURI) {
						monitor.worked(1);
						result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.UndeployCreationMenuModelHandler_Error_NotAValidFile));
						continue;
					}
					monitor.subTask(Messages.UndeployCreationMenuModelHandler_SubTaskTitle_ResetRegistry);

					try {
						CreationMenuRegistry.getInstance().unloadCreationMenuModel(URI.createPlatformResourceURI(selectedFile.getFullPath().toString(), true));
						monitor.worked(1);
						result.add(new Status(IStatus.OK, Activator.PLUGIN_ID, Messages.UndeployCreationMenuModelHandler_SuccessfullyUndeployed + fileName));
					} catch (Exception e) {
						result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.UndeployCreationMenuModelHandler_Error_UndeployFile + e));
					}
				}
			}
		}

		// The returned status
		IStatus returnedStatus = null;

		if (1 == result.getChildren().length) {
			returnedStatus = result.getChildren()[0];
		} else { // Merge the result and specify an appropriate message based on the result
			if (!result.isOK()) {
				returnedStatus = new MultiStatus(Activator.PLUGIN_ID, result.getCode(), Messages.UndeployCreationMenuModelHandler_Erro_SomeErrorsOccurred, result.getException());
				((MultiStatus) returnedStatus).merge(result);
			}
		}
		return returnedStatus;
	}
}
