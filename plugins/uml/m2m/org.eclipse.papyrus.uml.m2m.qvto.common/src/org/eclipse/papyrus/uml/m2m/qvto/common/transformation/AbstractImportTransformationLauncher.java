/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 496439
 *  Vincent Lorenzo - bug 496176
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.internal.Schedulable;
import org.eclipse.papyrus.uml.m2m.qvto.common.internal.Scheduler;
import org.eclipse.papyrus.uml.m2m.qvto.common.internal.TransformationWrapper;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Executes a batch of {@link ImportTransformation}s, then restores the dependencies (References)
 * between each other
 * 
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractImportTransformationLauncher implements IImportTransformationLauncher {

	// Nano to Second
	protected final static long SECOND = 1000 * 1000 * 1000;

	// Nano to Milliseconds
	protected final static long MILLIS = 1000 * 1000;

	protected final ThreadConfig config;

	protected final Control baseControl;

	/**
	 * Own execution time, in nano-seconds. Doesn't take individual transformation's exec
	 * time into account. Also ignores the time when Blocking user Dialogs are opened
	 */
	protected long ownExecutionTime;

	/**
	 * Own loading time (For initial model loading)
	 */
	protected long ownLoadingTime;

	/**
	 * Own cumulated execution time for repairing stereotypes
	 */
	protected long ownRepairStereotypesTime;

	/**
	 * Own cumulated execution time for repairing libraries
	 */
	protected long ownRepairLibrariesTime;

	/**
	 * Own execution time for resolving all matches for broken profiles/libraries
	 */
	protected long resolveAllDependencies;

	/**
	 * The top-level job for this transformation
	 */
	protected Job importDependenciesJob;

	/**
	 * Total time for all invidivual transformations to complete. Since they are executed in parallel,
	 * this may be different from their cumulated execution time (Unless a single thread is used)
	 */
	protected long transformationsExecutionTime = 0L;

	protected IDependencyAnalysisHelper analysisHelper;

	public AbstractImportTransformationLauncher(ThreadConfig config) {
		this(config, null);
	}

	public AbstractImportTransformationLauncher(ThreadConfig config, Control baseControl) {
		this.config = config;
		this.baseControl = baseControl;
	}

	/**
	 * Executes the transformation (Asynchronous)
	 *
	 * @param urisToImport
	 */
	public void run(List<URI> urisToImport) {
		List<IImportTransformation> transformations = new LinkedList<IImportTransformation>();

		analysisHelper = createDependencyAnalysisHelper(config);

		for (URI uri : urisToImport) {
			IImportTransformation transformation = createTransformation(uri, config, analysisHelper);
			transformations.add(transformation);
		}

		// Always use the batch launcher, even if there is only 1 transformation (Bug 455012)
		importModels(transformations);
	}

	protected abstract IDependencyAnalysisHelper createDependencyAnalysisHelper(final ThreadConfig config);

	protected abstract IImportTransformation createTransformation(URI transformationURI, final ThreadConfig config, final IDependencyAnalysisHelper analysisHelper);

	public static final String IMPORT_MODELS_JOB_NAME = "Import Models"; // $NON-NLS-0$

	/**
	 * Start a Job and delegate to {@link #importModels(IProgressMonitor, List)}
	 *
	 * @param transformations
	 */
	protected void importModels(final List<IImportTransformation> transformations) {
		importDependenciesJob = new Job(IMPORT_MODELS_JOB_NAME) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IStatus result = AbstractImportTransformationLauncher.this.importModels(monitor, transformations);

				if (monitor.isCanceled()) {
					return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Operation Canceled");
				}

				long cumulatedLoadingTime = 0L;
				long cumulatedTransformationTime = 0L;
				long cumulatedHandleDanglingTime = 0L;
				long cumulatedImportExtensionsTime = 0L;
				for (IImportTransformation transformation : transformations) {
					cumulatedLoadingTime += transformation.getLoadingTime();
					cumulatedImportExtensionsTime += transformation.getImportExtensionsTime();
					cumulatedHandleDanglingTime += transformation.getHandleDanglingRefTime();

					cumulatedTransformationTime += transformation.getExecutionTime();

					log("Import " + transformation.getModelName());
					log("First phase (0-50%):");
					log("\tTotal loading time: " + timeFormat(transformation.getLoadingTime()));
					log("\tTotal Import Extensions time: " + timeFormat(transformation.getImportExtensionsTime()));
					log("\tTotal Handle Dangling References time: " + timeFormat(transformation.getHandleDanglingRefTime()));
					log("\tTotal execution time: " + timeFormat(transformation.getExecutionTime()));

					Long loadingTime = loadingTimeV2.get(transformation);
					Long repairProxiesTime = proxiesTime.get(transformation);
					Long repairStereoTime = stereoTime.get(transformation);
					Long totalPhase2 = totalTimeV2.get(transformation);

					log("Second phase (50-100%):");
					log("\tTotal loading time: " + timeFormat(loadingTime));
					log("\tTotal fix proxies time: " + timeFormat(repairProxiesTime));
					log("\tTotal fix stereotypes time: " + timeFormat(repairStereoTime));
					log("\tTotal execution time: " + timeFormat(totalPhase2));

					log("Total");
					log("\tTotal execution time: " + timeFormat(transformation.getExecutionTime() + (totalPhase2 == null ? 0 : totalPhase2)));
					log("\n");
				}

				int nbThreads = Math.max(1, config.getMaxThreads());
				log("First phase (0-50%) / " + nbThreads + " Threads");
				log("\tCumulated Transformation Time: " + timeFormat(cumulatedTransformationTime));
				log("\tCumulated Loading Time: " + timeFormat(cumulatedLoadingTime));
				log("\tCumulated Handle Dangling Refs Time: " + timeFormat(cumulatedHandleDanglingTime));
				log("\tCumulated Import Extensions Time: " + timeFormat(cumulatedImportExtensionsTime));
				log("\tTotal Transformation Time: " + timeFormat(transformationsExecutionTime));

				log("Second phase (50-100%) / " + nbThreads + " Threads");
				log("\tTotal Handle all Dangling References: " + timeFormat(resolveAllDependencies));
				log("\tCumulated Loading Time: " + timeFormat(ownLoadingTime));
				log("\tCumulated Fix Libraries Time: " + timeFormat(ownRepairLibrariesTime));
				log("\tCumulated Fix Stereotypes Time: " + timeFormat(ownRepairStereotypesTime));
				log("\tTotal Fix Dependencies Time: " + timeFormat(ownExecutionTime));

				log("Total");
				log("\tCumulated Total time: " + timeFormat(ownExecutionTime + cumulatedTransformationTime));
				log("\tTotal time: " + timeFormat(ownExecutionTime + transformationsExecutionTime));

				log("Import Complete");
				log("");

				return result;
			}

		};

		importDependenciesJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {

				MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, "", null);

				multiStatus.merge(event.getResult());

				for (IImportTransformation transformation : transformations) {
					multiStatus.merge(transformation.getStatus());
				}

				int severity = multiStatus.getSeverity();
				String message;

				switch (severity) {
				case IStatus.OK:
					message = "The selected models have been successfully imported";
					break;
				case IStatus.CANCEL:
					message = "Operation canceled";
					break;
				case IStatus.WARNING:
					message = "The selected models have been imported; some warnings have been reported";
					break;
				default:
					message = "Some errors occurred during model import";
					break;
				}


				handle(new MultiStatus(Activator.PLUGIN_ID, severity, multiStatus.getChildren(), message, null));
			}

			protected void handle(final IStatus status) {
				if (baseControl == null) {
					int severity = status.getSeverity();
					if (severity == IStatus.OK || severity == IStatus.CANCEL) {
						return;
					}

					StatusManager.getManager().handle(status, StatusManager.LOG);
					return;
				}

				Display display = baseControl.getDisplay();

				if (status.getSeverity() == IStatus.OK) {
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							MessageDialog.openInformation(baseControl.getShell(), "Import models", status.getMessage());
						}
					});

				} else if (status.getSeverity() == IStatus.CANCEL) {
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							MessageDialog.openInformation(baseControl.getShell(), "Import models", status.getMessage());
						}
					});
				} else {
					StatusManager.getManager().handle(status, StatusManager.BLOCK);
				}
			}
		});

		importDependenciesJob.setUser(true);
		importDependenciesJob.schedule();
	}

	protected void log(String message) {
		System.out.println(message);

		MessageConsole console = getConsole();
		MessageConsoleStream out = console.newMessageStream();
		out.println(message);
	}

	protected static final String CONSOLE_NAME = "Model Import Results"; // The name is both the ID and the Label

	protected MessageConsole getConsole() {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = plugin.getConsoleManager();
		IConsole[] existing = consoleManager.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (CONSOLE_NAME.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		// no console found, so create a new one
		MessageConsole rsaConsole = new MessageConsole(CONSOLE_NAME, null);
		consoleManager.addConsoles(new IConsole[] { rsaConsole });
		return rsaConsole;
	}

	protected String timeFormat(Long nano) {
		if (nano == null) {
			return "?"; // FIXME: crash?
		}
		long seconds = nano / SECOND;
		if (seconds < 1) {
			long millis = nano / MILLIS;
			return String.format("%s ms", millis);
		}
		return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
	}

	/**
	 * Schedules all the individual transformations, wait for completion, then
	 * call {@link #handleModelDependencies(List, IProgressMonitor)}
	 *
	 * @param monitor
	 * @param transformations
	 * @return
	 */
	protected IStatus importModels(IProgressMonitor monitor, List<IImportTransformation> transformations) {

		long begin = System.nanoTime();

		monitor.setTaskName("Waiting for import tasks to complete...");
		int numTasks = transformations.size() * 2; // For each transformation: wait for completion, then handle dependencies
		monitor.beginTask("Importing Models...", numTasks);

		List<Schedulable> tasks = new LinkedList<Schedulable>();

		for (IImportTransformation transformation : transformations) {
			tasks.add(new TransformationWrapper(transformation));
		}

		Scheduler scheduler = new Scheduler(config.getMaxThreads());
		scheduler.schedule(monitor, tasks);

		long end = System.nanoTime();
		transformationsExecutionTime = end - begin;

		if (monitor.isCanceled()) {
			return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Operation canceled");
		}

		handleModelDependencies(transformations, monitor);

		return Status.OK_STATUS;
	}

	protected void handleModelDependencies(List<IImportTransformation> transformations, IProgressMonitor monitor) {
		// TODO, seem not required for Rpy import
	}

	final protected Map<IImportTransformation, Long> loadingTimeV2 = new HashMap<IImportTransformation, Long>();

	final protected Map<IImportTransformation, Long> proxiesTime = new HashMap<IImportTransformation, Long>();

	final protected Map<IImportTransformation, Long> stereoTime = new HashMap<IImportTransformation, Long>();

	final protected Map<IImportTransformation, Long> totalTimeV2 = new HashMap<IImportTransformation, Long>();

	/** Mainly for test purpose */
	public void waitForCompletion() throws Exception {
		importDependenciesJob.join();
	}

	/** Mainly for test purpose */
	public IStatus getResult() {
		return importDependenciesJob.getResult();
	}
}
