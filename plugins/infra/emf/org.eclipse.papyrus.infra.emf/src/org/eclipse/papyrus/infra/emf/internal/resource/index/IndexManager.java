/*****************************************************************************
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.infra.emf.internal.resource.index;

import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.TRACE_INDEXER_EVENTS;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.TRACE_INDEXER_FILES;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.TRACE_INDEXER_FILES_MATCHING;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.detailf;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.isTracing;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.tracef;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.papyrus.infra.core.utils.JobBasedFuture;
import org.eclipse.papyrus.infra.core.utils.JobExecutorService;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.resource.index.IWorkspaceModelIndexListener;
import org.eclipse.papyrus.infra.emf.resource.index.IWorkspaceModelIndexProvider;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndexEvent;
import org.eclipse.papyrus.infra.tools.util.ReferenceCounted;

import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

/**
 * A controller of the indexing process for {@link WorkspaceModelIndex}s,
 * including initial loading of an index and invocation of incremental
 * indexing as resources in the workspace change.
 */
public class IndexManager {
	private static final int MAX_INDEX_RETRIES = 3;

	// Number of files to index at a time in the indexing job
	private static final int PARTITION_SIZE = 10;

	private static final IndexManager INSTANCE = new IndexManager();

	private final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
	private final IResourceChangeListener workspaceListener = new WorkspaceListener();

	private final Map<IProject, AbstractIndexJob> activeJobs = Maps.newHashMap();
	private final ContentTypeService contentTypeService;

	private Map<QualifiedName, InternalModelIndex> indices;
	private JobWrangler jobWrangler;
	private final CopyOnWriteArrayList<IndexListener> listeners = new CopyOnWriteArrayList<>();

	private final IndexSynchronizer synchronizer = new IndexSynchronizer();

	private final CopyOnWriteArrayList<IIndexManagerListener> managerListeners = new CopyOnWriteArrayList<>();

	private final Lock lifecycleLock = new ReentrantLock();
	private final Condition pausedCondition = lifecycleLock.newCondition();
	private boolean paused = false;

	public IndexManager() {
		super();

		contentTypeService = ContentTypeService.getInstance();
	}

	public static IndexManager getInstance() {
		return INSTANCE;
	}

	/**
	 * @return true, if the index manager has already been started
	 */
	public boolean isStarted() {
		return indices != null;
	}

	public void dispose() {
		if (indices != null) {
			wsRoot.getWorkspace().removeResourceChangeListener(workspaceListener);
			Job.getJobManager().cancel(this);

			indices.values().forEach(InternalModelIndex::dispose);
			// don't null the 'indices' to prevent starting again

			listeners.clear();
			managerListeners.clear();

			ContentTypeService.dispose(contentTypeService);
		}
	}

	public final boolean isPaused() {
		lifecycleLock.lock();

		try {
			return paused;
		} finally {
			lifecycleLock.unlock();
		}
	}

	public final void pause() {
		setPaused(true);
	}

	private void setPaused(boolean paused) {
		boolean notify = false;
		lifecycleLock.lock();

		try {
			if (this.paused != paused) {
				this.paused = paused;
				pausedCondition.signalAll();
				notify = true;
			}
		} finally {
			lifecycleLock.unlock();
		}

		if (notify) {
			tracef("Indexing %s", paused ? "paused" : "resumed"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

			if (!managerListeners.isEmpty()) {
				BiConsumer<IIndexManagerListener, IndexManager> action = paused
						? IIndexManagerListener::indexingPaused
						: IIndexManagerListener::indexingResumed;
				Consumer<IIndexManagerListener> callback = l -> action.accept(l, this);
				managerListeners.forEach(l -> SafeRunner.run(() -> callback.accept(l)));
			}
		}
	}

	public final void resume() {
		setPaused(false);
	}

	final void checkPaused() throws InterruptedException {
		lifecycleLock.lock();

		try {
			while (paused) {
				pausedCondition.await();
			}
		} finally {
			lifecycleLock.unlock();
		}
	}

	public void startManager() {
		if (indices != null) {
			throw new IllegalStateException("index manager already started"); //$NON-NLS-1$
		}

		// Load our indices and find out from them how many
		// jobs we need make available
		indices = loadIndices();
		int maxConcurrentJobs = indices.values().stream()
				.mapToInt(InternalModelIndex::getMaxIndexJobs)
				.max()
				.orElse(5);
		jobWrangler = new JobWrangler(maxConcurrentJobs);

		// Start the indices now
		indices.values().forEach(this::startIndex);

		// And load or index from scratch
		index(Arrays.asList(wsRoot.getProjects()));
		wsRoot.getWorkspace().addResourceChangeListener(workspaceListener, IResourceChangeEvent.POST_CHANGE);
	}

	public void addIndexManagerListener(IIndexManagerListener listener) {
		managerListeners.addIfAbsent(listener);
	}

	public void removeIndexManagerListener(IIndexManagerListener listener) {
		managerListeners.remove(listener);
	}

	private void startIndex(InternalModelIndex index) {
		index.start(this);
	}

	protected Map<QualifiedName, InternalModelIndex> loadIndices() {
		Map<QualifiedName, InternalModelIndex> result = Maps.newHashMap();

		for (IConfigurationElement config : Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, "index")) { //$NON-NLS-1$
			if ("indexProvider".equals(config.getName())) { //$NON-NLS-1$
				try {
					IWorkspaceModelIndexProvider provider = (IWorkspaceModelIndexProvider) config.createExecutableExtension("class"); //$NON-NLS-1$
					WorkspaceModelIndex<?> index = provider.get();

					if (index == null) {
						Activator.log.warn("No index provided by " + config.getContributor().getName()); //$NON-NLS-1$
					} else {
						QualifiedName key = index.getIndexKey();
						if (key == null) {
							Activator.log.warn("Index has no key and will be ignored: " + index); //$NON-NLS-1$
						} else {
							InternalModelIndex internal = index;
							// Ensure that the index can load classes from its
							// persistent store that are defined in its owner's
							// bundle
							internal.setOwnerClassLoader(provider.getClass().getClassLoader());
							result.put(key, internal);
						}
					}
				} catch (ClassCastException e) {
					Activator.log.error("Expected IWorkspaceModelIndexProvider in " + config.getContributor().getName(), e); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.log.log(e.getStatus());
				} catch (Exception e) {
					Activator.log.error("Failed to obtain index from provider in " + config.getContributor().getName(), e); //$NON-NLS-1$
				}
			}
		}

		return result;
	}

	IContentType[] getContentTypes(IFile file) {
		return contentTypeService.getContentTypes(file);
	}

	/**
	 * Obtains an asynchronous future result that is scheduled to run after
	 * any pending indexing work has completed.
	 *
	 * @param index
	 *            the index that is making the request
	 * @param callable
	 *            the operation to schedule
	 *
	 * @return the future result of the operation
	 */
	<V> ListenableFuture<V> afterIndex(InternalModelIndex index, Callable<V> callable) {
		ListenableFuture<V> result;

		if (isIdle() || wsRoot.getWorkspace().isTreeLocked()) {
			// Result is available now, or the workspace is currently notifying on this,
			// in which case none of the indexer jobs can run yet so we must return whatever
			// is the current state of the index (or else certainly deadlock)
			try {
				result = Futures.immediateFuture(callable.call());
			} catch (Exception e) {
				result = Futures.immediateFailedFuture(e);
			}
		} else {
			result = synchronizer.post(callable);
		}

		return result;
	}

	/**
	 * Query whether the index manager idle.
	 *
	 * @return {@code true} if either there is no job wrangler, yet (in which case there cannot be any
	 *         indexing jobs) or it is idle
	 */
	private boolean isIdle() {
		return jobWrangler == null || jobWrangler.isIdle();
	}

	private void waitForIndex() throws InterruptedException {
		if (isIdle()) {
			// Nothing to wait for
			return;
		}

		jobWrangler.waitForIdle();
	}

	/**
	 * Invokes an operation on some index if it is assured to be ready by
	 * virture of no indexing jobs currently working on computing the index.
	 *
	 * @param callable
	 *            an index operation
	 * @return the result of the {@code callable}, or {@code null} if indexing is
	 *         still pending (not ready to compute any result)
	 *
	 * @throws CoreException
	 *             on failure of the {@code callable}
	 */
	<V> V ifAvailable(Callable<V> callable) throws CoreException {
		V result = null;

		if (Job.getJobManager().find(this).length == 0) {
			// Result is available now
			try {
				result = callable.call();
			} catch (CoreException e) {
				throw e;
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			}
		}

		return result;
	}

	void index(Collection<? extends IProject> projects) {
		List<AbstractIndexJob> jobs = Lists.newArrayListWithCapacity(projects.size());
		for (IProject next : projects) {
			jobs.add(new DiscoverProjectJob(next));
		}
		schedule(jobs);
	}

	void index(IProject project) {
		schedule(new DiscoverProjectJob(project));
	}

	void process(IFile file) throws CoreException {
		IProject project = file.getProject();

		safeIterateIndices(index -> {
			boolean matched = index.match(file);

			if (isTracing()) {
				detailf(TRACE_INDEXER_FILES_MATCHING, "Index %s %s file %s", //$NON-NLS-1$
						index.getIndexKey().getLocalName(),
						matched ? "indexing" : "un-indexing", //$NON-NLS-1$ //$NON-NLS-2$
						file.getFullPath());
			}

			if (matched) {
				index.process(file);
			} else {
				index.remove(project, file);
			}
		});
	}

	private void safeIterateIndices(IndexAction action) throws CoreException {
		CoreException exception = null;

		for (InternalModelIndex index : indices.values()) {
			try {
				action.apply(index);
			} catch (CoreException e) {
				if (exception != null) {
					exception = e;
				}
			}
		}

		if (exception != null) {
			throw exception;
		}
	}

	void remove(IProject project, IFile file) throws CoreException {
		safeIterateIndices(index -> index.remove(project, file));
	}

	void remove(IProject project) throws CoreException {
		safeIterateIndices(index -> index.remove(project));
	}

	ReindexProjectJob reindex(IProject project, Collection<? extends IndexDelta> deltas) {
		ReindexProjectJob result = null;

		synchronized (activeJobs) {
			AbstractIndexJob active = activeJobs.get(project);

			if (active != null) {
				switch (active.kind()) {
				case REINDEX:
					ReindexProjectJob reindex = (ReindexProjectJob) active;
					reindex.addDeltas(deltas);
					break;
				case INDEX:
					IndexProjectJob index = (IndexProjectJob) active;
					ReindexProjectJob followup = index.getFollowup();
					if (followup != null) {
						followup.addDeltas(deltas);
					} else {
						followup = new ReindexProjectJob(project, deltas);
						index.setFollowup(followup);
					}
					break;
				case DISCOVERY:
					// This will be followed by a full index run, so re-indexing is moot
					break;
				case MASTER:
					throw new IllegalStateException("Master job is in the active table."); //$NON-NLS-1$
				}
			} else {
				// No active job. We'll need a new one
				result = new ReindexProjectJob(project, deltas);
			}
		}

		return result;
	}

	private void schedule(Collection<? extends AbstractIndexJob> jobs) {
		// Synchronize on the active jobs because this potentially alters the wrangler's follow-up job
		synchronized (activeJobs) {
			jobWrangler.add(jobs);
		}
	}

	private void schedule(AbstractIndexJob job) {
		// Synchronize on the active jobs because this potentially alters the wrangler's follow-up job
		synchronized (activeJobs) {
			jobWrangler.add(job);
		}
	}

	public void addListener(WorkspaceModelIndex<?> index, IWorkspaceModelIndexListener listener) {
		listeners.addIfAbsent(new IndexListener(index, listener));
	}

	public void removeListener(WorkspaceModelIndex<?> index, IWorkspaceModelIndexListener listener) {
		listeners.removeIf(l -> Objects.equal(l.index, index) && Objects.equal(l.listener, listener));
	}

	private void notifyStarting(AbstractIndexJob indexJob) {
		if (!listeners.isEmpty()) {
			Map<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> events = Maps.newHashMap();
			java.util.function.Function<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> eventFunction = index -> {
				switch (indexJob.kind()) {
				case INDEX:
					return new WorkspaceModelIndexEvent(index, WorkspaceModelIndexEvent.ABOUT_TO_CALCULATE, indexJob.getProject());
				case REINDEX:
					return new WorkspaceModelIndexEvent(index, WorkspaceModelIndexEvent.ABOUT_TO_RECALCULATE, indexJob.getProject());
				default:
					throw new IllegalArgumentException(indexJob.kind().name());
				}
			};

			switch (indexJob.kind()) {
			case INDEX:
				for (IndexListener next : listeners) {
					try {
						if (isTracing()) {
							detailf(TRACE_INDEXER_EVENTS, "Notifying calculating for %s to %s", //$NON-NLS-1$
									indexJob.getProject().getName(), next.getClass().getName());
						}
						next.listener.indexAboutToCalculate(events.computeIfAbsent(next.index, eventFunction));
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in index listsner.", e); //$NON-NLS-1$
					}
				}
				break;
			case REINDEX:
				for (IndexListener next : listeners) {
					try {
						if (isTracing()) {
							detailf(TRACE_INDEXER_EVENTS, "Notifying recalculating for %s to %s", //$NON-NLS-1$
									indexJob.getProject().getName(), next.getClass().getName());
						}
						next.listener.indexAboutToRecalculate(events.computeIfAbsent(next.index, eventFunction));
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in index listsner.", e); //$NON-NLS-1$
					}
				}
				break;
			case DISCOVERY:
			case MASTER:
				// Pass
				break;
			}
		}
	}

	private void notifyFinished(AbstractIndexJob indexJob, IStatus status) {
		if (!listeners.isEmpty()) {
			if ((status != null) && (status.getSeverity() >= IStatus.ERROR)) {
				Map<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> events = Maps.newHashMap();
				java.util.function.Function<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> eventFunction = index -> new WorkspaceModelIndexEvent(index, WorkspaceModelIndexEvent.FAILED, indexJob.getProject());

				for (IndexListener next : listeners) {
					try {
						if (isTracing()) {
							detailf(TRACE_INDEXER_EVENTS, "Notifying failure for %s to %s", //$NON-NLS-1$
									indexJob.getProject().getName(), next.getClass().getName());
						}
						next.listener.indexFailed(events.computeIfAbsent(next.index, eventFunction));
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in index listsner.", e); //$NON-NLS-1$
					}
				}
			} else {
				Map<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> events = Maps.newHashMap();
				java.util.function.Function<WorkspaceModelIndex<?>, WorkspaceModelIndexEvent> eventFunction = index -> {
					switch (indexJob.kind()) {
					case INDEX:
						return new WorkspaceModelIndexEvent(index, WorkspaceModelIndexEvent.CALCULATED, indexJob.getProject());
					case REINDEX:
						return new WorkspaceModelIndexEvent(index, WorkspaceModelIndexEvent.RECALCULATED, indexJob.getProject());
					default:
						throw new IllegalArgumentException(indexJob.kind().name());
					}
				};

				switch (indexJob.kind()) {
				case INDEX:
					for (IndexListener next : listeners) {
						try {
							if (isTracing()) {
								detailf(TRACE_INDEXER_EVENTS, "Notifying calculate done for %s to %s", //$NON-NLS-1$
										indexJob.getProject().getName(), next.getClass().getName());
							}
							next.listener.indexCalculated(events.computeIfAbsent(next.index, eventFunction));
						} catch (Exception e) {
							Activator.log.error("Uncaught exception in index listsner.", e); //$NON-NLS-1$
						}
					}
					break;
				case REINDEX:
					for (IndexListener next : listeners) {
						try {
							if (isTracing()) {
								detailf(TRACE_INDEXER_EVENTS, "Notifying recalculate done for %s to %s", //$NON-NLS-1$
										indexJob.getProject().getName(), next.getClass().getName());
							}
							next.listener.indexRecalculated(events.computeIfAbsent(next.index, eventFunction));
						} catch (Exception e) {
							Activator.log.error("Uncaught exception in index listsner.", e); //$NON-NLS-1$
						}
					}
					break;
				case DISCOVERY:
				case MASTER:
					// Pass
					break;
				}
			}
		}
	}

	private void notifyWranglerStarting() {
		if (!managerListeners.isEmpty()) {
			managerListeners.forEach(l -> SafeRunner.run(() -> l.indexingStarting(this)));
		}
	}

	private void notifyWranglerFinished() {
		if (!managerListeners.isEmpty()) {
			managerListeners.forEach(l -> SafeRunner.run(() -> l.indexingFinished(this)));
		}
	}

	//
	// Nested types
	//

	private enum JobKind {
		MASTER, DISCOVERY, INDEX, REINDEX;

		boolean isSystem() {
			return this != DISCOVERY;
		}
	}

	private abstract class AbstractIndexJob extends Job {
		private final IProject project;

		private volatile Semaphore permit;

		AbstractIndexJob(String name, IProject project) {
			this(name, project, project);
		}

		AbstractIndexJob(String name, IProject project, ISchedulingRule rule) {
			super(name);

			this.project = project;

			if (rule != null) {
				setRule(rule);

				if (project != null) {
					synchronized (activeJobs) {
						if (!activeJobs.containsKey(project)) {
							activeJobs.put(project, this);
						}
					}
				}
			}

			setSystem(kind().isSystem());
			setPriority(Job.BUILD);
		}

		@Override
		public boolean belongsTo(Object family) {
			return (family == IndexManager.this)
					|| ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
		}

		final IProject getProject() {
			return project;
		}

		abstract JobKind kind();

		@Override
		protected final IStatus run(IProgressMonitor monitor) {
			IStatus result;

			try {
				if (isTracing()) {
					switch (kind()) {
					case MASTER:
						tracef("Starting index manager"); //$NON-NLS-1$
						break;
					default:
						tracef("Starting %s of project %s", kind(), getProject().getName()); //$NON-NLS-1$
						break;
					}
				}

				result = doRun(monitor);

				if (isTracing()) {
					switch (kind()) {
					case MASTER:
						tracef("Finished index manager"); //$NON-NLS-1$
						break;
					default:
						tracef("Finished %s of project %s", kind(), getProject().getName()); //$NON-NLS-1$
						break;
					}
				}
			} finally {
				synchronized (activeJobs) {
					AbstractIndexJob followup = getFollowup();

					if (project != null) {
						if (followup == null) {
							activeJobs.remove(project);
						} else {
							activeJobs.put(project, followup);
						}
					}

					if (followup != null) {
						// Kick off the follow-up job
						IndexManager.this.schedule(followup);
						tracef("Follow-up %s of project %s with %s", //$NON-NLS-1$
								kind(), getProject().getName(), followup.kind());
					}
				}
			}

			return result;
		}

		final Semaphore getPermit() {
			return permit;
		}

		final void setPermit(Semaphore permit) {
			this.permit = permit;
		}

		protected abstract IStatus doRun(IProgressMonitor monitor);

		protected AbstractIndexJob getFollowup() {
			return null;
		}
	}

	private class JobWrangler extends AbstractIndexJob {
		private final Lock lock = new ReentrantLock();
		private final Condition idleCondition = lock.newCondition();

		private final Deque<AbstractIndexJob> queue = Queues.newArrayDeque();

		final AtomicInteger pending = new AtomicInteger(); // How many permits have we issued?
		final Condition pendingChanged = lock.newCondition();

		private final AtomicBoolean active = new AtomicBoolean();
		private final Semaphore indexJobSemaphore;

		private volatile boolean cancelled;

		JobWrangler(int maxConcurrentJobs) {
			super("Workspace model indexer", null);

			indexJobSemaphore = new Semaphore((maxConcurrentJobs <= 0) ? Integer.MAX_VALUE : maxConcurrentJobs);
		}

		@Override
		JobKind kind() {
			return JobKind.MASTER;
		}

		void add(AbstractIndexJob job) {
			lock.lock();

			try {
				scheduleIfNeeded();
				queue.add(job);
			} finally {
				lock.unlock();
			}
		}

		private void scheduleIfNeeded() {
			if (active.compareAndSet(false, true)) {
				// I am a new job
				schedule();
			}
		}

		void add(Iterable<? extends AbstractIndexJob> jobs) {
			lock.lock();

			try {
				for (AbstractIndexJob next : jobs) {
					add(next);
				}
			} finally {
				lock.unlock();
			}
		}

		@Override
		protected void canceling() {
			cancelled = true;
			Thread running = getThread();
			if (running != null) {
				running.interrupt();
			}
			tracef("Cancelling job %s", getName()); //$NON-NLS-1$
		}

		@Override
		protected IStatus doRun(IProgressMonitor progressMonitor) {
			final SubMonitor monitor = SubMonitor.convert(progressMonitor, IProgressMonitor.UNKNOWN);

			IStatus result = Status.OK_STATUS;

			IJobChangeListener listener = new JobChangeAdapter() {
				private final Map<IProject, Integer> retries = Maps.newHashMap();

				private Semaphore getIndexJobPermit(Job job) {
					return (job instanceof AbstractIndexJob)
							? ((AbstractIndexJob) job).getPermit()
							: null;
				}

				@Override
				public void aboutToRun(IJobChangeEvent event) {
					Job starting = event.getJob();

					if (getIndexJobPermit(starting) == indexJobSemaphore) {
						// one of mine is starting
						AbstractIndexJob indexJob = (AbstractIndexJob) starting;
						notifyStarting(indexJob);
					} else if (starting == jobWrangler) {
						// the orchestration job is starting

					}
				}

				@Override
				public void done(IJobChangeEvent event) {
					final Job finished = event.getJob();
					if (getIndexJobPermit(finished) == indexJobSemaphore) {
						try {
							// one of mine has finished
							AbstractIndexJob indexJob = (AbstractIndexJob) finished;
							IProject project = indexJob.getProject();

							notifyFinished(indexJob, event.getResult());

							if (project != null) {
								synchronized (retries) {
									if ((event.getResult() != null) && (event.getResult().getSeverity() >= IStatus.ERROR)) {
										// Indexing failed to complete. Need to re-build the index
										tracef("Retrying %s", finished); //$NON-NLS-1$

										int count = retries.containsKey(project) ? retries.get(project) : 0;
										if (count++ < MAX_INDEX_RETRIES) {
											// Only retry up to three times
											index(project);
										}
										retries.put(project, ++count);
									} else {
										// Successful re-indexing. Forget the retries
										retries.remove(project);
									}
								}
							}
						} finally {
							// Release this job's permit for the next one in the queue
							indexJobSemaphore.release();

							// And it's no longer pending
							pending.decrementAndGet();

							lock.lock();
							try {
								pendingChanged.signalAll();
							} finally {
								lock.unlock();
							}
						}
					}
				}
			};

			getJobManager().addJobChangeListener(listener);

			notifyWranglerStarting();

			lock.lock();

			// Initialize the pending count
			pending.set(0);

			try {
				out: for (;;) {
					monitor.setWorkRemaining(queue.size());

					for (AbstractIndexJob next = queue.poll(); next != null; next = queue.poll()) {
						lock.unlock();
						try {
							if (cancelled) {
								throw new InterruptedException();
							}

							checkPaused();

							// Enforce the concurrent jobs limit
							indexJobSemaphore.acquire();
							next.setPermit(indexJobSemaphore);
							pending.incrementAndGet();

							// Now go (after a brief delay)
							next.schedule(100L);

							monitor.worked(1);
						} catch (InterruptedException e) {
							// In case the interrupted happened some other way
							cancelled = true;

							// We were cancelled. Push this job back and re-schedule
							lock.lock();
							try {
								queue.addFirst(next);
							} finally {
								lock.unlock();
							}
							result = Status.CANCEL_STATUS;
							tracef("Job %s interrupted", getName()); //$NON-NLS-1$
							break out;
						} finally {
							lock.lock();
						}
					}

					if (isIdle()) {
						// Nothing left to wait for
						break out;
					} else if (pending.get() > 0) {
						try {
							if (cancelled) {
								throw new InterruptedException();
							}

							pendingChanged.await();
						} catch (InterruptedException e) {
							// In case the interrupted happened some other way
							cancelled = true;

							// We were cancelled. Re-schedule
							result = Status.CANCEL_STATUS;
							break out;
						}
					}
				}

				if (isIdle()) {
					// We've finished wrangling index jobs, for now
					tracef("Index manager idle"); //$NON-NLS-1$

					idleCondition.signalAll();
				}
			} finally {
				try {
					// If we were canceled then we re-schedule after a delay to recover
					if (cancelled) {
						// We cannot un-cancel a job, so we must replace ourselves with a new job
						schedule(1000L);
						cancelled = false;
						tracef("Re-scheduled cancelled job %s", getName()); //$NON-NLS-1$
					} else {
						// Don't think we're active any longer
						active.compareAndSet(true, false);

						// Double-check
						if (!queue.isEmpty()) {
							// We'll have to go around again
							scheduleIfNeeded();
							tracef("Re-scheduled job %s for new indexing jobs", getName()); //$NON-NLS-1$
						}
					}
				} finally {
					lock.unlock();
					getJobManager().removeJobChangeListener(listener);

					notifyWranglerFinished();
				}
			}

			return result;
		}

		boolean isIdle() {
			lock.lock();

			try {
				return queue.isEmpty() && pending.get() <= 0;
			} finally {
				lock.unlock();
			}
		}

		void waitForIdle() throws InterruptedException {
			lock.lock();

			try {
				while (!isIdle()) {
					idleCondition.await();
				}
			} finally {
				lock.unlock();
			}
		}

	}

	private class DiscoverProjectJob extends AbstractIndexJob {
		private AbstractIndexJob followup;

		DiscoverProjectJob(IProject project) {
			super("Initializing workspace model index", project);

			setUser(true);
		}

		@Override
		JobKind kind() {
			return JobKind.DISCOVERY;
		}

		@Override
		protected IStatus doRun(IProgressMonitor monitor) {
			IStatus result = Status.OK_STATUS;
			final IProject project = getProject();

			monitor.beginTask("Scanning project " + project.getName(), IProgressMonitor.UNKNOWN);

			try {
				if (project.isAccessible()) {
					List<InternalModelIndex> needIndex = Lists.newArrayListWithCapacity(indices.size());
					for (InternalModelIndex next : indices.values()) {
						if (!next.hasIndex(project)) {
							needIndex.add(next);
						}
					}

					if (!needIndex.isEmpty()) {
						ProjectScanner scanner = new ProjectScanner(needIndex, monitor);
						project.accept(scanner);
						if (!monitor.isCanceled() && !scanner.isEmpty()) {
							followup = new IndexProjectJob(project, scanner.getResourcesToIndex());
						}
					}
				} else {
					remove(project);
				}

				if (monitor.isCanceled()) {
					remove(project); // We've lost this project's index
					result = Status.CANCEL_STATUS;
				}
			} catch (CoreException e) {
				result = e.getStatus();

				try {
					remove(project); // We've lost this project's index
				} catch (CoreException e2) {
					// Already have an exception
					Activator.getDefault().getLog().log(e2.getStatus());
				}
			} finally {
				monitor.done();
			}

			return result;
		}

		@Override
		protected AbstractIndexJob getFollowup() {
			return followup;
		}
	}

	private class ProjectScanner implements IResourceVisitor {
		private final Predicate<IFile> fileMatcher;
		private final IProgressMonitor monitor;

		private final ImmutableList.Builder<IFile> resourcesToIndex = ImmutableList.builder();
		private boolean empty = true;

		ProjectScanner(List<InternalModelIndex> needIndex, IProgressMonitor monitor) {
			super();

			this.monitor = monitor;

			if (needIndex.size() == 1) {
				InternalModelIndex index = needIndex.get(0);
				fileMatcher = file -> {
					boolean result = index.match(file);

					if (result && isTracing()) {
						detailf(TRACE_INDEXER_FILES_MATCHING, "Index %s accepting file %s", //$NON-NLS-1$
								index.getIndexKey().getLocalName(),
								file.getFullPath());
					}

					return result;
				};
			} else {
				int indexCount = needIndex.size();
				fileMatcher = file -> {
					boolean result = false;

					for (int i = 0; (i < indexCount) && !result; i++) {
						result = needIndex.get(i).match(file);

						if (result && isTracing()) {
							detailf(TRACE_INDEXER_FILES_MATCHING, "Index %s accepting file %s", //$NON-NLS-1$
									needIndex.get(i).getIndexKey().getLocalName(),
									file.getFullPath());
						}
					}

					return result;
				};
			}
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			// bug 573042: do not index derived resources
			boolean result = !monitor.isCanceled() && !resource.isDerived();

			if (result && (resource.getType() == IResource.FILE)) {
				scan((IFile) resource);
			}

			return result;
		}

		public boolean isEmpty() {
			return empty;
		}

		public Collection<IFile> getResourcesToIndex() {
			return resourcesToIndex.build();
		}

		void scan(IFile file) throws CoreException {
			if (fileMatcher.test(file)) {
				resourcesToIndex.add(file);
				empty = false;
			}
		}
	}

	private class IndexProjectJob extends AbstractIndexJob {
		private final List<IFile> resourcesToIndex;

		private ReindexProjectJob followup;

		IndexProjectJob(IProject project, Collection<IFile> resourcesToIndex) {
			super("Indexing project " + project.getName(), project, null);

			this.resourcesToIndex = ImmutableList.copyOf(resourcesToIndex);
		}

		@Override
		JobKind kind() {
			return JobKind.INDEX;
		}

		@Override
		protected IStatus doRun(IProgressMonitor monitor) {
			IStatus result = Status.OK_STATUS;
			final IProject project = getProject();

			// Partition the files into chunks
			List<List<IFile>> partitions = Lists.partition(resourcesToIndex, PARTITION_SIZE);

			SubMonitor subMonitor = SubMonitor.convert(monitor,
					"Indexing models in project " + project.getName(),
					resourcesToIndex.size() + partitions.size());
			try {
				for (List<IFile> filesToIndex : partitions) {
					ISchedulingRule partitionRule = new MultiRule(filesToIndex.toArray(new ISchedulingRule[filesToIndex.size()]));

					// Reserve this partition of files for indexing
					getJobManager().beginRule(partitionRule, subMonitor.newChild(1));

					try {
						IProgressMonitor childMonitor = subMonitor.newChild(filesToIndex.size());

						if (project.isAccessible()) {
							for (IFile file : filesToIndex) {
								try {
									if (isTracing()) {
										detailf(TRACE_INDEXER_FILES, "Indexing file %s", file.getFullPath()); //$NON-NLS-1$
									}

									process(file);
									childMonitor.worked(1);
								} catch (CoreException e) {
									if (result.isOK()) {
										result = e.getStatus();
									} else if (!result.isMultiStatus()) {
										result = new MultiStatus(result.getPlugin(), result.getCode(),
												new IStatus[] { result, e.getStatus() },
												result.getMessage(), null);
									}
								}
							}
						} else {
							tracef("Removing project %s from index", getProject().getName()); //$NON-NLS-1$
							remove(project);
							break;
						}

						if (monitor.isCanceled()) {
							result = Status.CANCEL_STATUS;
							break;
						}
					} finally {
						// Release the partition
						getJobManager().endRule(partitionRule);
					}
				}
			} catch (CoreException e) {
				if (result.isOK()) {
					result = e.getStatus();
				} else if (!result.isMultiStatus()) {
					result = new MultiStatus(result.getPlugin(), result.getCode(),
							new IStatus[] { result, e.getStatus() },
							result.getMessage(), null);
				}
			} finally {
				monitor.done();
			}

			return result;
		}

		void setFollowup(ReindexProjectJob followup) {
			this.followup = followup;
		}

		@Override
		protected ReindexProjectJob getFollowup() {
			return followup;
		}
	}

	private class WorkspaceListener implements IResourceChangeListener {
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			final Multimap<IProject, IndexDelta> deltas = ArrayListMultimap.create();

			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						// bug 573042: do not index derived resources
						if (delta.getResource().getType() == IResource.FILE && !delta.getResource().isDerived(IResource.CHECK_ANCESTORS)) {
							IFile file = (IFile) delta.getResource();

							switch (delta.getKind()) {
							case IResourceDelta.CHANGED:
								if ((delta.getFlags() & (IResourceDelta.SYNC | IResourceDelta.CONTENT | IResourceDelta.REPLACED)) != 0) {
									// Re-index in place
									deltas.put(file.getProject(), new IndexDelta(file, IndexDelta.DeltaKind.REINDEX));
								}
								break;
							case IResourceDelta.REMOVED:
								deltas.put(file.getProject(), new IndexDelta(file, IndexDelta.DeltaKind.UNINDEX));
								break;
							case IResourceDelta.ADDED:
								deltas.put(file.getProject(), new IndexDelta(file, IndexDelta.DeltaKind.INDEX));
								break;
							}
						}
						return true;
					}
				});
			} catch (CoreException e) {
				Activator.log.error("Failed to analyze resource changes for re-indexing.", e); //$NON-NLS-1$
			}

			if (!deltas.isEmpty()) {
				List<ReindexProjectJob> jobs = Lists.newArrayListWithCapacity(deltas.keySet().size());
				for (IProject next : deltas.keySet()) {
					ReindexProjectJob reindex = reindex(next, deltas.get(next));
					if (reindex != null) {
						jobs.add(reindex);
					}
				}
				schedule(jobs);
			}
		}
	}

	private static final class IndexDelta {
		private final IFile file;

		private final DeltaKind kind;

		IndexDelta(IFile file, DeltaKind kind) {
			this.file = file;
			this.kind = kind;
		}

		DeltaKind kind() {
			return kind;
		}

		IFile file() {
			return file;
		}

		//
		// Nested types
		//

		enum DeltaKind {
			INDEX, REINDEX, UNINDEX;
		}
	}

	private class ReindexProjectJob extends AbstractIndexJob {
		private final IProject project;
		private final ConcurrentLinkedQueue<IndexDelta> moreDeltas;
		private List<IndexDelta> currentDeltas;

		ReindexProjectJob(IProject project, Collection<? extends IndexDelta> deltas) {
			super("Re-indexing project " + project.getName(), project,
					new MultiRule(deltas.stream().map(IndexDelta::file).distinct().toArray(ISchedulingRule[]::new)));

			this.project = project;
			this.currentDeltas = ImmutableList.copyOf(deltas);
			this.moreDeltas = Queues.newConcurrentLinkedQueue();
		}

		@Override
		JobKind kind() {
			return JobKind.REINDEX;
		}

		void addDeltas(Iterable<? extends IndexDelta> deltas) {
			Iterables.addAll(this.moreDeltas, deltas);
		}

		@Override
		protected IStatus doRun(IProgressMonitor monitor) {
			IStatus result = Status.OK_STATUS;

			monitor.beginTask("Re-indexing models in project " + project.getName(), IProgressMonitor.UNKNOWN);

			try {
				for (IndexDelta next : currentDeltas) {
					if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
						break;
					}

					try {
						switch (next.kind()) {
						case INDEX:
						case REINDEX:
							if (isTracing()) {
								detailf(TRACE_INDEXER_FILES, "Re-indexing file %s", next.file().getFullPath()); //$NON-NLS-1$
							}

							process(next.file());
							break;
						case UNINDEX:
							if (isTracing()) {
								detailf(TRACE_INDEXER_FILES, "Un-indexing file %s", next.file().getFullPath()); //$NON-NLS-1$
							}

							remove(project, next.file());
							break;
						}
					} catch (CoreException e) {
						result = e.getStatus();
						break;
					} finally {
						monitor.worked(1);
					}
				}
			} finally {
				monitor.done();
			}

			return result;
		}

		@Override
		protected AbstractIndexJob getFollowup() {
			// If I still have work to do, then I am my own follow-up
			if (moreDeltas.isEmpty()) {
				return null;
			}

			ImmutableList.Builder<IndexDelta> newDeltas = ImmutableList.builder();
			for (IndexDelta next = moreDeltas.poll(); next != null; next = moreDeltas.poll()) {
				newDeltas.add(next);
			}

			this.currentDeltas = newDeltas.build();
			return this;
		}
	}

	private static final class ContentTypeService extends ReferenceCounted<ContentTypeService> {
		private static ContentTypeService instance = null;

		private final ExecutorService serialExecution = new JobExecutorService();

		private final IContentTypeManager mgr = Platform.getContentTypeManager();

		private ContentTypeService() {
			super();
		}

		synchronized static ContentTypeService getInstance() {
			ContentTypeService result = instance;

			if (result == null) {
				result = new ContentTypeService();
				instance = result;
			}

			return result.retain();
		}

		synchronized static void dispose(ContentTypeService service) {
			service.release();
		}

		@Override
		protected void dispose() {
			serialExecution.shutdownNow();

			if (instance == this) {
				instance = null;
			}
		}

		IContentType[] getContentTypes(final IFile file) {
			Future<IContentType[]> futureResult = serialExecution.submit(new Callable<IContentType[]>() {

				@Override
				public IContentType[] call() {
					IContentType[] result = null;
					InputStream input = null;

					if (file.isAccessible()) {
						try {
							input = file.getContents(true);
							result = mgr.findContentTypesFor(input, file.getName());
						} catch (Exception e) {
							Activator.log.error("Failed to index file " + file.getFullPath(), e); //$NON-NLS-1$
						} finally {
							if (input != null) {
								try {
									input.close();
								} catch (IOException e) {
									Activator.log.error("Failed to close indexed file " + file.getFullPath(), e); //$NON-NLS-1$
								}
							}
						}
					}

					return result;
				}
			});

			return Futures.getUnchecked(futureResult);
		}
	}

	@FunctionalInterface
	private interface IndexAction {
		void apply(InternalModelIndex index) throws CoreException;
	}

	private static final class IndexListener {
		final WorkspaceModelIndex<?> index;
		final IWorkspaceModelIndexListener listener;

		IndexListener(WorkspaceModelIndex<?> index, IWorkspaceModelIndexListener listener) {
			super();

			this.index = index;
			this.listener = listener;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((index == null) ? 0 : index.hashCode());
			result = prime * result + ((listener == null) ? 0 : listener.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof IndexListener)) {
				return false;
			}
			IndexListener other = (IndexListener) obj;
			if (index == null) {
				if (other.index != null) {
					return false;
				}
			} else if (!index.equals(other.index)) {
				return false;
			}
			if (listener == null) {
				if (other.listener != null) {
					return false;
				}
			} else if (!listener.equals(other.listener)) {
				return false;
			}
			return true;
		}

	}

	/**
	 * A job-based future that accumulates a queue of computations waiting for indexing to finish.
	 * After the indexer is idle, the pending computations are run cleared out, publishing results
	 * to all clients that were waiting for them.
	 */
	private final class IndexSynchronizer extends JobBasedFuture<List<Future<?>>> {

		private final Lock lock = new ReentrantLock();

		private final Queue<Computation<?>> waitingComputations = new LinkedList<>();

		IndexSynchronizer() {
			super("Wait for workspace model index");

			setSystem(true);
		}

		@Override
		protected List<Future<?>> compute(IProgressMonitor monitor) throws Exception {
			List<Future<?>> result = new ArrayList<>();
			List<Computation<?>> toComplete;

			waitForIndex();

			lock.lock();
			try {
				do {
					toComplete = List.copyOf(waitingComputations);
					waitingComputations.clear();

					lock.unlock();

					try {
						for (Computation<?> next : toComplete) {
							next.complete();
							result.add(next.get());
						}
					} finally {
						lock.lock();
					}
				} while (!waitingComputations.isEmpty());
			} finally {
				lock.unlock();
			}

			return result;
		}

		/**
		 * Post a computation to run after the indexer is idle, returning its future result.
		 * If the index is currently idle, the result will be computed immediately and
		 * returned already completed without being queued.
		 *
		 * @param computation
		 *            a computation to post
		 * @return its future result
		 */
		<V> ListenableFuture<V> post(Callable<V> computation) {
			ListenableFuture<V> result;

			if (isIdle()) {
				// Nothing to wait for
				result = Computation.complete(computation);
			} else {
				lock.lock();
				try {
					boolean first = waitingComputations.isEmpty();

					Computation<V> wrapper = new Computation<>(computation);
					waitingComputations.offer(wrapper);
					result = wrapper.get();

					if (first) {
						// This is the first waiting computation. Schedule the queue
						schedule();
					}
				} finally {
					lock.unlock();
				}
			}

			return result;
		}
	};

	/**
	 * A computation queued in the {@link IndexSynchronizer}.
	 */
	private static final class Computation<V> implements Supplier<ListenableFuture<V>> {
		private final Callable<V> computation;
		private final SettableFuture<V> result = SettableFuture.create();

		Computation(Callable<V> computation) {
			super();

			this.computation = computation;
		}

		void complete() {
			try {
				result.set(computation.call());
			} catch (ThreadDeath d) {
				throw d;
			} catch (Throwable e) {
				result.setException(e);
			}
		}

		@Override
		public ListenableFuture<V> get() {
			return result;
		}

		static <V> ListenableFuture<V> complete(Callable<V> computation) {
			try {
				return Futures.immediateFuture(computation.call());
			} catch (ThreadDeath d) {
				throw d;
			} catch (Throwable e) {
				return Futures.immediateFailedFuture(e);
			}
		}

	}

}
