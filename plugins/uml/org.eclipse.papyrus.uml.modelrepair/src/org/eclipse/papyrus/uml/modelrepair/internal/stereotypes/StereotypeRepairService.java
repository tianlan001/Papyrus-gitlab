/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import static org.eclipse.papyrus.uml.modelrepair.Activator.TRACE_EXECUTOR;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.ui.util.UIUtil;
import org.eclipse.papyrus.uml.modelrepair.Activator;
import org.eclipse.papyrus.uml.modelrepair.service.IStereotypeRepairService;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


/**
 * The implementation of the stereotype repair service.
 */
public class StereotypeRepairService implements IStereotypeRepairService, IService {

	private static final Lock lock = new ReentrantLock();

	private static final Condition executorsReady = lock.newCondition();

	private static final Queue<PostRepairExecutor> ready = Lists.newLinkedList();

	private static final Map<ModelSet, Set<Resource>> modelSetsInRepair = Maps.newIdentityHashMap();

	private static final Map<ModelSet, PostRepairExecutor> pending = Maps.newIdentityHashMap();

	private static ExecutorService pendingExecutor;

	private ServicesRegistry registry;

	private ModelSet modelSet;

	private ExecutorService executor;

	public StereotypeRepairService() {
		super();
	}

	//
	// Service API
	//

	public boolean isRepairing() {
		return isInRepair(modelSet);
	}

	public ExecutorService getPostRepairExecutor() {
		return executor;
	}

	//
	// Service lifecycle
	//

	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.registry = servicesRegistry;
	}

	public void startService() throws ServiceException {
		modelSet = registry.getService(ModelSet.class);
		executor = new PostRepairExecutor(UIUtil.createUIExecutor(Display.getDefault()));
	}

	public void disposeService() throws ServiceException {
		modelSet = null;
		registry = null;

		if (executor != null) {
			// Don't need to run any tasks still pending because the model-set, editor, everything are all gone
			executor.shutdownNow();
			executor = null;
		}
	}

	//
	// Private API
	//

	static boolean isInRepair(ModelSet modelSet) {
		lock.lock();
		try {
			return modelSetsInRepair.containsKey(modelSet);
		} finally {
			lock.unlock();
		}
	}

	static void startedRepairing(ModelSet modelSet, Resource resource) {
		lock.lock();
		try {
			Set<Resource> resources = modelSetsInRepair.get(modelSet);
			if (resources == null) {
				resources = Sets.newIdentityHashSet();
				modelSetsInRepair.put(modelSet, resources);
			}
			resources.add(resource);
		} finally {
			lock.unlock();
		}
	}

	static void finishedRepairing(ModelSet modelSet, Resource resource) {
		lock.lock();
		try {
			Set<Resource> resources = modelSetsInRepair.get(modelSet);
			if (resources != null) {
				resources.remove(resource);
				if (resources.isEmpty()) {
					modelSetsInRepair.remove(modelSet);
					Activator.log.trace(TRACE_EXECUTOR, "Model Repair completed"); //$NON-NLS-1$

					PostRepairExecutor executor = pending.remove(modelSet);
					if (executor != null) {
						ready.offer(executor);
						executorsReady.signalAll();
						Activator.log.trace(TRACE_EXECUTOR, "Pending post-repair executor ready"); //$NON-NLS-1$
					}
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private static void pending(PostRepairExecutor executor) {
		lock.lock();
		try {
			pending.put(executor.getModelSet(), executor);
			Activator.log.trace(TRACE_EXECUTOR, "Post-repair executor pending"); //$NON-NLS-1$

			if ((pendingExecutor == null) || pendingExecutor.isShutdown()) {
				pendingExecutor = Executors.newSingleThreadExecutor();
				start(pendingExecutor);
				Activator.log.trace(TRACE_EXECUTOR, "Post-repair scheduling executor started"); //$NON-NLS-1$
			}
		} finally {
			lock.unlock();
		}
	}

	private static void start(final ExecutorService executor) {
		lock.lock();
		try {
			executor.execute(new Runnable() {

				public void run() {
					lock.lock();
					try {
						while (ready.isEmpty()) {
							try {
								executorsReady.await();
							} catch (InterruptedException e) {
								Activator.log.error("Model repair post-repair executor thread interrupt ignored.", e); //$NON-NLS-1$
							}
						}

						for (PostRepairExecutor next = ready.poll(); next != null; next = ready.poll()) {
							Activator.log.trace(TRACE_EXECUTOR, "Post-repair executor dequeued"); //$NON-NLS-1$
							if (!next.processPending()) {
								// Add it back to the pending set to await repair completion again
								Activator.log.trace(TRACE_EXECUTOR, "Post-repair executor canceled; repair still in progress"); //$NON-NLS-1$
								pending(next);
							}
						}

						// Any still pending?
						if (!pending.isEmpty()) {
							executor.submit(this);
							Activator.log.trace(TRACE_EXECUTOR, "Re-scheduling for pending post-repair executors"); //$NON-NLS-1$
						} else {
							executor.shutdown();
							Activator.log.trace(TRACE_EXECUTOR, "No more pending post-repair executors; shutting down"); //$NON-NLS-1$
						}
					} finally {
						lock.unlock();
					}
				}
			});
		} finally {
			lock.unlock();
		}
	}

	//
	// Nested types
	//

	protected class PostRepairExecutor extends AbstractExecutorService {

		private final ExecutorService delegate;

		private volatile boolean shutdown;

		private final Lock lock = new ReentrantLock();

		private List<Runnable> pending = Lists.newLinkedList();

		protected PostRepairExecutor(ExecutorService delegate) {
			this.delegate = delegate;
		}

		public void execute(Runnable command) {
			if (shutdown) {
				throw new RejectedExecutionException("Executor is shut down"); //$NON-NLS-1$
			}

			if (!isInRepair(modelSet)) {
				// Fire away
				try {
					delegate.execute(command);
				} catch (RejectedExecutionException e) {
					// Must be shut down. Fine
				}
			} else {
				lock.lock();
				try {
					pending.add(command);

					// Add me to the list for processing when the repair operation finishes
					pending(this);
				} finally {
					lock.unlock();
				}
			}
		}

		public void shutdown() {
			shutdown = true;
			delegate.shutdown();
		}

		public List<Runnable> shutdownNow() {
			shutdown = true;

			List<Runnable> result = Lists.newArrayList();

			lock.lock();
			try {
				result.addAll(pending);
				pending.clear();
			} finally {
				lock.unlock();
			}

			result.addAll(delegate.shutdownNow());

			return result;
		}

		public boolean isShutdown() {
			return shutdown;
		}

		public boolean isTerminated() {
			return shutdown && delegate.isTerminated();
		}

		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			return delegate.awaitTermination(timeout, unit);
		}

		ModelSet getModelSet() {
			return modelSet;
		}

		boolean processPending() {
			boolean result = !isInRepair(modelSet);

			if (result) {
				lock.lock();
				try {
					try {
						for (Runnable next : pending) {
							delegate.execute(next);
						}
					} catch (RejectedExecutionException e) {
						// Must be shut down. Fine
					}
					pending.clear();
				} finally {
					lock.unlock();
				}
			}

			return result;
		}
	}
}
