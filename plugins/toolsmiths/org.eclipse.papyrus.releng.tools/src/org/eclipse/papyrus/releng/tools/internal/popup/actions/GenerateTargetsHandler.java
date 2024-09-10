/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cbi.targetplatform.pde.Converter;
import org.eclipse.cbi.targetplatform.ui.internal.TargetplatformActivator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.releng.tools.internal.Activator;
import org.eclipse.papyrus.releng.tools.internal.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.inject.Injector;


/**
 * A global handler, enabled on a set of IResources (Project/Folder/File), which recursively:
 *
 * - Finds all *.tpd files
 * - Updates them from the selected Simrel/B3 model
 * - Generates all *.target files
 * - Generates an Eclipse-Server version for each *.target file (In an eclipse/*.target folder)
 *
 * The Eclipse-Server version of the target is similar to the default one, except it uses
 * the file:/ protocol instead of http:// for all access to download.eclipse.org,
 * for improved performances when building on Eclipse Servers
 *
 * @author Camille Letavernier
 *
 */
public class GenerateTargetsHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Iterator<?> iterator = sSelection.iterator();

			List<IFile> tpdFiles = new ArrayList<>();

			try {

				while (iterator.hasNext()) {
					Object next = iterator.next();

					if (next instanceof IResource) {
						collectTPDFiles((IResource) next, tpdFiles);
					}
				}

				final Shell activeShell = HandlerUtil.getActiveShell(event);

				if (!tpdFiles.isEmpty()) {
					new UpdateDependencies().updateDependencies(tpdFiles, activeShell); // Update all TPD Files from Simrel

					String jobTitle = String.format(Messages.GenerateTargetsHandler_GenerateTargetFile, tpdFiles.size());
					Job topLevelJob = new Job(jobTitle) {
						/**
						 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
						 *
						 * @param monitor
						 * @return
						 */
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							int maxThreads = 2; // Multi-threading is not really relevant, most time is spent in downloading artifacts

							JobGroup tpdConverters = new JobGroup(Messages.GenerateTargetsHandler_GenerateTarget, maxThreads, tpdFiles.size());
							for (IFile tpdFile : tpdFiles) {
								generate(tpdFile, tpdConverters); // Generate *.target files
							}

							try {
								tpdConverters.join(0, monitor);
							} catch (InterruptedException e) {
								return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.GenerateTargetsHandler_UnexpectedException, e);
							}

							return tpdConverters.getResult();
						}
					};

					topLevelJob.setUser(true);
					topLevelJob.addJobChangeListener(new JobChangeAdapter() {
						/**
						 * @see org.eclipse.core.runtime.jobs.JobChangeAdapter#done(org.eclipse.core.runtime.jobs.IJobChangeEvent)
						 *
						 * @param event
						 */
						@Override
						public void done(IJobChangeEvent event) {
							if (Display.getCurrent() != null) {
								done(event.getResult());
							} else {
								Display.getDefault().asyncExec(() -> {
									done(event.getResult());
								});
							}
						}

						void done(IStatus status) {
							String title = Messages.GenerateTargetsHandler_GenerateTarget;
							switch (status.getCode()) {
							case IStatus.OK:
							case IStatus.INFO:
								MessageDialog.openInformation(activeShell, title, Messages.GenerateTargetsHandler_OperationComplete);
								break;
							case IStatus.CANCEL:
								MessageDialog.openInformation(activeShell, title, Messages.GenerateTargetsHandler_OperationCanceled);
								break;
							case IStatus.ERROR:
								MessageDialog.openError(activeShell, title, Messages.GenerateTargetsHandler_OperationCompleteWithError);
								break;
							case IStatus.WARNING:
								MessageDialog.openWarning(activeShell, title, Messages.GenerateTargetsHandler_OperationCompleteWithWarning);
								break;
							}
						}
					});
					topLevelJob.schedule();

				}
			} catch (CoreException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.GenerateTargetsHandler_UnexpectedException, e));
			}

		}

		return null;

	}

	/**
	 * Finds all *.tpd files, recursively, in the given IResource (IFile, IFolder, IProject...)
	 * The collected *.tpd IFiles are stored in the result List
	 *
	 * @param resource
	 * @param result
	 * @throws CoreException
	 */
	protected void collectTPDFiles(IResource resource, List<IFile> result) throws CoreException {
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			if ("tpd".equals(file.getFileExtension())) { //$NON-NLS-1$
				result.add(file);
			}
		} else if (resource instanceof IContainer) {
			collectTPDFiles((IContainer) resource, result);
		}
	}

	/**
	 * Finds all *.tpd files, recursively, in the given IContainer (IFolder, IProject...)
	 * The collected *.tpd IFiles are stored in the result List
	 *
	 * @param resource
	 * @param result
	 * @throws CoreException
	 */
	protected void collectTPDFiles(IContainer parent, List<IFile> result) throws CoreException {
		for (IResource child : parent.members()) {
			collectTPDFiles(child, result);
		}
	}

	/**
	 * Inits a job for converting a single *.tpd file to a *.target file
	 * Also creates an Eclipse Server version of each *.target file (Using file:/ protocol instead of http://)
	 *
	 * @param file
	 *            A *.tpd IFile
	 * @param jobGroup
	 *            The job group used to managed all *.tpd to *.target conversion jobs
	 * @throws CoreException
	 */
	protected void generate(IFile file, JobGroup jobGroup) {
		// The Converter currently only supports File URIs (No platform resource)
		// URI tpdURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		String filePath = file.getLocation().toFile().getAbsolutePath();
		URI tpdURI = URI.createFileURI(filePath);

		Converter converter = new Converter();

		Injector injector = TargetplatformActivator.getInstance().getInjector(TargetplatformActivator.ORG_ECLIPSE_CBI_TARGETPLATFORM_TARGETPLATFORM);
		injector.injectMembers(converter);

		Job job = new Job(Messages.GenerateTargetsHandler_GenerateTargetPlatformFor + file.getLocation().lastSegment()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				Diagnostic result = converter.generateTargetDefinitionFile(tpdURI, new NullProgressMonitor());
				if (result.getSeverity() >= Diagnostic.WARNING) {
					Activator.getDefault().getLog().log(BasicDiagnostic.toIStatus(result));
				}

				try {
					file.getParent().refreshLocal(IResource.DEPTH_ONE, null);
				} catch (CoreException ex) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.GenerateTargetsHandler_UnexpectedException, ex);
				}

				return BasicDiagnostic.toIStatus(result);
			}
		};

		job.setJobGroup(jobGroup);
		job.schedule();
	}

	/**
	 * Returns an InputStream similar to the source stream, replacing all occurrences of the source pattern
	 * with the target pattern
	 *
	 * @param source
	 * @param sourcePattern
	 * @param targetPattern
	 * @return
	 * @throws CoreException
	 */
	protected InputStream convert(InputStream source, String sourcePattern, String targetPattern) throws CoreException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(source));

		StringBuilder builder = new StringBuilder();
		String line;

		String patternSt = sourcePattern.replaceAll("\\.", "\\."); //$NON-NLS-1$ //$NON-NLS-2$
		Pattern pattern = Pattern.compile(patternSt);

		try {
			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				String newLine = matcher.replaceAll(targetPattern);
				builder.append(newLine).append("\n"); //$NON-NLS-1$
			}
		} catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.GenerateTargetsHandler_UnexpectedError, ex));
		}

		ByteArrayInputStream result = new ByteArrayInputStream(builder.toString().getBytes());

		return result;
	}

}
