/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.dev.project.management.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.papyrus.dev.project.management.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.ProjectEditors;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Ensures that all projects that have the Oomph Version Management nature configured
 * also have the PDE API Analysis nature configured.
 * 
 * @since 1.2
 */
public class EnsureAPIAnalysisHandler extends AbstractHandler {

	public EnsureAPIAnalysisHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IProject> versionProjects = getAllVersionProjects();

		Job updateJob = new Job("Ensure API Analysis") {

			{
				setRule(ResourcesPlugin.getWorkspace().getRoot());
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				Set<String> naturesToAdd = Collections.singleton("org.eclipse.pde.api.tools.apiAnalysisNature");
				Set<String> buildersToAdd = Collections.singleton("org.eclipse.pde.api.tools.apiAnalysisBuilder");
				SubMonitor sub = SubMonitor.convert(monitor, "Updating projects...", versionProjects.size());

				versionProjects.forEach(project -> {
					try {
						if (sub.isCanceled()) {
							throw new OperationCanceledException();
						}

						IProjectEditor projectEditor = ProjectEditors.getProjectEditor(project);
						if (projectEditor.hasNature("org.eclipse.oomph.version.VersionNature")) {
							// Ensure the API Analysis nature and builder
							projectEditor.addNatures(naturesToAdd);
							projectEditor.addBuildCommands(buildersToAdd);
							if (projectEditor.isDirty()) {
								projectEditor.save();
							}
						}

						sub.worked(1);
					} catch (CoreException e) {
						StatusManager.getManager().handle(e.getStatus(), StatusManager.SHOW);
					}
				});

				sub.done();

				return Status.OK_STATUS;
			}
		};
		updateJob.schedule();

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IProgressService progress = window.getWorkbench().getProgressService();
		progress.showInDialog(window.getShell(), updateJob);

		return null;
	}

	List<IProject> getAllVersionProjects() {
		return Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
				.filter(this::hasVersionNature)
				.collect(Collectors.toList());
	}

	boolean hasVersionNature(IProject project) {
		boolean result = false;

		if (project.isAccessible()) {
			try {
				IProjectDescription desc = project.getDescription();
				List<String> natures = Arrays.asList(desc.getNatureIds());
				result = natures.contains("org.eclipse.oomph.version.VersionNature"); //$NON-NLS-1$
			} catch (CoreException e) {
				// Guess it's not an interesting project
				Activator.log.log(e.getStatus());
			}
		}

		return result;
	}
}
