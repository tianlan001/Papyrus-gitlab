/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.nature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This handler allows to add the Papyrus plugin nature to Java Plugin project
 */
public class AddPapyrusPluginNatureHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection ss = (IStructuredSelection) currentSelection;
			final Iterator<?> iter = ss.iterator();
			final IAdapterManager adapterManager = Platform.getAdapterManager();
			int nbUpdatedProject = 0;
			final List<String> ignoredProject = new ArrayList<>();
			final List<String> projectInError = new ArrayList<>();
			while (iter.hasNext()) {
				Object element = iter.next();

				// Get an IResource as an adapter from the current selection
				IResource resourceAdapter = adapterManager.getAdapter(element, IResource.class);
				if (resourceAdapter != null) {
					final IResource resource = resourceAdapter;
					final IProject project = resource.getProject();

					if (canSetPapyrusPluginNature(project)) {

						if (checkPluginDocumentation(project)) {

							try {
								IProjectDescription description = project.getDescription();
								String[] natures = description.getNatureIds();
								String[] newNatures = new String[natures.length + 1];
								System.arraycopy(natures, 0, newNatures, 0, natures.length);

								// add our new "com.example.project.examplenature" id
								newNatures[natures.length] = PapyrusPluginNature.PAPYRUS_PLUGIN_NATURE_ID;

								// validate the natures
								IWorkspace workspace = ResourcesPlugin.getWorkspace();
								IStatus status = workspace.validateNatureSet(newNatures);

								// only apply new nature, if the status is ok or warni
								if (status.getSeverity() == IStatus.OK || status.getSeverity() == IStatus.WARNING) { // warning : typically warning due to oomph which is not installed
									description.setNatureIds(newNatures);
									project.setDescription(description, null);
									nbUpdatedProject++;
								} else {
									projectInError.add(project.getName());
								}
							} catch (CoreException e) {
								NLS.bind("Project {0} has not been updated, due to an exception:\n {1}", project.getName(), e); //$NON-NLS-1$
								projectInError.add(project.getName());
							}
						} else {
							ignoredProject.add(project.getName());
						}
					} else {
						ignoredProject.add(project.getName());
					}
				}
			}
			final StringBuilder message = new StringBuilder();
			message.append(NLS.bind("{0} projects have been successfully updated.\n", nbUpdatedProject)); //$NON-NLS-1$

			final StringBuilder ignoredProjectMessage = new StringBuilder();
			ignoredProjectMessage.append(NLS.bind("{0} projects have been ignored:\n", ignoredProject.size())); //$NON-NLS-1$
			Iterator<String> ignoredProjectIter = ignoredProject.iterator();
			while (ignoredProjectIter.hasNext()) {
				ignoredProjectMessage.append("  "); //$NON-NLS-1$
				ignoredProjectMessage.append(ignoredProjectIter.next());
				if (ignoredProjectIter.hasNext()) {
					ignoredProjectMessage.append("\n"); //$NON-NLS-1$
				}
			}
			final StringBuilder errorProjectMessage = new StringBuilder();
			errorProjectMessage.append(NLS.bind("{0} projects generate error :\n", projectInError.size())); //$NON-NLS-1$
			Iterator<String> errorProjectIter = projectInError.iterator();
			while (errorProjectIter.hasNext()) {
				errorProjectMessage.append("  "); //$NON-NLS-1$
				errorProjectMessage.append(errorProjectIter.next());
				if (errorProjectIter.hasNext()) {
					errorProjectMessage.append("\n"); //$NON-NLS-1$
				}
			}
			message.append(ignoredProjectMessage.toString());
			message.append("\n"); //$NON-NLS-1$
			message.append(errorProjectMessage.toString());
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Apply Papyrus nature", message.toString()); //$NON-NLS-1$
		}

		return Status.OK_STATUS;
	}

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		boolean isEnable = false;
		final Object tmpSelection = HandlerUtil.getVariable(evaluationContext, "selection"); //$NON-NLS-1$
		if (tmpSelection instanceof IStructuredSelection) {
			final IStructuredSelection ss = (IStructuredSelection) tmpSelection;
			final Iterator<?> iter = ss.iterator();
			final IAdapterManager adapterManager = Platform.getAdapterManager();
			while (iter.hasNext() && !isEnable) {
				final Object current = iter.next();
				final IResource resource = adapterManager.getAdapter(current, IResource.class);
				final IProject project = resource.getProject();
				isEnable = canSetPapyrusPluginNature(project);
			}
		}
		setBaseEnabled(isEnable);

	}

	/**
	 *
	 * @param project
	 *            a project
	 * @return
	 *         <code>true</code> if we can set the Papyrus Plugin Nature to this project
	 */
	private boolean canSetPapyrusPluginNature(final IProject project) {
		boolean canSet = false;

		if (project.exists() && project.isOpen()) {
			try {
				canSet = project.hasNature(PapyrusPluginNature.PLUGIN_NATURE)
						&& !project.hasNature(PapyrusPluginNature.PAPYRUS_PLUGIN_NATURE_ID);
			} catch (CoreException e) {
				// we ignore the exception
			}
		}

		return canSet;
	}

	/**
	 * If the project name ends with ".doc", we ask to the user if he really want to convert this project
	 *
	 * @param project
	 *            project
	 * @return
	 *         <code>true</code> if the project must be managed and <code>false</code> to ignore it
	 */
	protected boolean checkPluginDocumentation(final IProject project) {
		if (project.getName().endsWith(".doc")) { //$NON-NLS-1$
			boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Apply Papyrus Plugin Project Nature", //$NON-NLS-1$
					NLS.bind("Do you really want to apply the Papyrus Plugin Nature to ''{0}''? It seems to be a documentation plugin...", project.getName())); //$NON-NLS-1$
			return result;
		}
		return true;
	}
}
