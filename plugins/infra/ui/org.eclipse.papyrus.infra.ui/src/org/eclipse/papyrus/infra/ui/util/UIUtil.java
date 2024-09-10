/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 399859
 *   Christian W. Damus - bug 451557
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.infra.ui.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.IServiceRegistryProvider;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.tools.util.IExecutorService;
import org.eclipse.papyrus.infra.tools.util.IProgressCallable;
import org.eclipse.papyrus.infra.tools.util.IProgressRunnable;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

import com.google.common.collect.Iterators;


/**
 * Miscellaneous general-purpose UI utilities.
 * 
 * @since 1.2
 */
public class UIUtil {

	/**
	 * Not instantiable by clients.
	 */
	private UIUtil() {
		super();
	}

	/**
	 * Create an executor that runs tasks asynchronously on the UI thread. If you need synchronous execution, schedule {@link Future}s and {@linkplain Future#get() wait} for them.
	 *
	 * @param display
	 *            the display on which thread to execute tasks
	 *
	 * @return the executor
	 */
	public static IExecutorService createUIExecutor(Display display) {
		return new DisplayExecutorService(display);
	}

	/**
	 * Create an executor that runs tasks asynchronously on an observable {@link Realm}. If you need synchronous execution, schedule {@link Future}s and {@linkplain Future#get() wait} for them.
	 *
	 * @param realm
	 *            the observable realm on which thread to execute tasks
	 *
	 * @return the executor
	 */
	public static ExecutorService createObservableExecutor(Realm realm) {
		return new RealmExecutorService(realm);
	}

	/**
	 * Creates a local memento that is not persistable and is not based on an XML document. This is useful for capturing the
	 * state of UI elements locally in cases where persistence of the memento is not required.
	 *
	 * @return the memento
	 */
	public static IMemento createLocalMemento() {
		return LocalMemento.createMemento("__anonymous__", null); //$NON-NLS-1$
	}

	/**
	 * Synchronously invokes a {@code callable} on the given {@code display}'s thread.
	 *
	 * @param display
	 *            a display
	 * @param callable
	 *            a callable to invoke
	 * @return the callable's result (which, because this method is synchronous, will be ready)
	 *
	 * @see #asyncCall(Display, Callable)
	 * @see #createUIExecutor(Display)
	 */
	public static <V> Future<V> syncCall(Display display, Callable<V> callable) {
		final FutureTask<V> result = new FutureTask<>(callable);
		display.syncExec(result);
		return result;
	}

	/**
	 * Synchronously invokes a {@code callable} on the default display thread.
	 *
	 * @param callable
	 *            a callable to invoke
	 * @return the callable's result (which, because this method is synchronous, will be ready)
	 *
	 * @see #syncCall(Display, Callable)
	 * @see #asyncCall(Callable)
	 * @see #createUIExecutor(Display)
	 */
	public static <V> Future<V> syncCall(Callable<V> callable) {
		return syncCall(Display.getDefault(), callable);
	}

	/**
	 * Asynchronously invokes a {@code callable} on the given {@code display}'s thread.
	 *
	 * @param display
	 *            a display
	 * @param callable
	 *            a callable to invoke
	 * @return the callable's result
	 *
	 * @see #syncCall(Display, Callable)
	 * @see #createUIExecutor(Display)
	 */
	public static <V> Future<V> asyncCall(Display display, Callable<V> callable) {
		final FutureTask<V> result = new FutureTask<>(callable);
		display.asyncExec(result);
		return result;
	}

	/**
	 * Asynchronously invokes a {@code callable} on the default display thread.
	 *
	 * @param callable
	 *            a callable to invoke
	 * @return the callable's result
	 *
	 * @see #asyncCall(Display, Callable)
	 * @see #syncCall(Callable)
	 * @see #createUIExecutor(Display)
	 */
	public static <V> Future<V> asyncCall(Callable<V> callable) {
		return asyncCall(Display.getDefault(), callable);
	}

	/**
	 * Calls a {@code callable} in the given {@code context}.
	 *
	 * @param fork
	 *            {@code true} if the runnable should be run in a separate thread,
	 *            and {@code false} to run in the same thread
	 * @param cancelable
	 *            {@code true} to enable the cancellation, and {@code false} to make the operation uncancellable
	 * @param runnable
	 *            the runnable to run
	 *
	 * @exception InvocationTargetException
	 *                wraps any exception or error which occurs
	 *                while running the runnable
	 * @exception InterruptedException
	 *                propagated by the context if the runnable
	 *                acknowledges cancellation by throwing this exception. This should not be thrown
	 *                if {@code cancelable} is {@code false}.
	 */
	public static <V> V call(IRunnableContext context, boolean fork, boolean cancelable, IProgressCallable<V> callable) throws InvocationTargetException, InterruptedException {
		class RunnableWrapper implements IRunnableWithProgress {
			final IProgressCallable<V> delegate;

			V result;

			RunnableWrapper(IProgressCallable<V> delegate) {
				this.delegate = delegate;
			}

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					result = delegate.call(monitor);
				} catch (OperationCanceledException e) {
					throw new InterruptedException(e.getMessage());
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				}
			}
		}

		RunnableWrapper wrapper = new RunnableWrapper(callable);
		context.run(fork, cancelable, wrapper);
		return wrapper.result;
	}

	/**
	 * Obtains a simple executor that asynchronously executes at most one task on the default
	 * display thread. While any task is still pending execution on this executor,
	 * all others are silently discarded. This is useful for cases where, for example, UI
	 * refreshes are posted repeatedly from independent events that aren't aware of each other
	 * but where each refresh task would repeat the same work.
	 * 
	 * @param display
	 *            a display on which thread to execute tasks
	 * 
	 * @return the executor
	 * 
	 * @see #createAsyncOnceExecutor(Display)
	 */
	public static Executor createAsyncOnceExecutor() {
		return createAsyncOnceExecutor(Display.getDefault());
	}

	/**
	 * Obtains a simple executor that asynchronously executes at most one task on the given {@code display}'s thread. While any task is still pending execution on this executor,
	 * all others are silently discarded. This is useful for cases where, for example, UI
	 * refreshes are posted repeatedly from independent events that aren't aware of each other
	 * but where each refresh task would repeat the same work.
	 * 
	 * @param display
	 *            a display on which thread to execute tasks
	 * 
	 * @return the executor
	 */
	public static Executor createAsyncOnceExecutor(final Display display) {
		return new Executor() {
			private final AtomicBoolean pending = new AtomicBoolean();

			@Override
			public void execute(final Runnable task) {
				if (pending.compareAndSet(false, true)) {
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							pending.set(false);
							task.run();
						}
					});
				}
			}
		};
	}

	/**
	 * Obtains a tree iterator over all of the controls contained within a given {@code root} control, not including that {@code root}.
	 * 
	 * @param root
	 *            a control to iterate
	 * @return an unmodifiable iterator over all of its nested controls, which naturally will be empty if the {@code root} is not a {@link Composite}
	 */
	public static TreeIterator<Control> allChildren(Control root) {
		return new AbstractTreeIterator<Control>(root, false) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<? extends Control> getChildren(Object object) {
				return (object instanceof Composite) ? Iterators.forArray(((Composite) object).getChildren()) : Collections.emptyIterator();
			}
		};
	}

	/**
	 * Obtains a tree iterator over all of the controls of a particular type contained within a given {@code root} control, not including that {@code root}.
	 * 
	 * @param root
	 *            a control to iterate
	 * @param type
	 *            the type of children to include in the iteration
	 * 
	 * @return an unmodifiable iterator over all of its nested controls, which naturally will be empty if the {@code root} is not a {@link Composite}
	 */
	public static <C extends Control> TreeIterator<C> allChildren(Control root, final Class<C> type) {
		return Iterators2.filter(allChildren(root), type);
	}

	//
	// Nested types
	//

	private static abstract class UIExecutorService extends AbstractExecutorService implements IExecutorService {

		private final Lock lock = new ReentrantLock();

		private final Condition emptyCond = lock.newCondition();

		private final Queue<RunnableWrapper> pending = new LinkedList<>();

		private volatile boolean shutdown;

		UIExecutorService() {
			super();
		}

		@Override
		public void execute(Runnable command) {
			if (isShutdown()) {
				throw new RejectedExecutionException("Executor service is shut down"); //$NON-NLS-1$
			}

			asyncExec(enqueue(command));
		}

		@Override
		public <V> V syncCall(Callable<V> callable) throws InterruptedException, ExecutionException {
			class SyncResult implements Runnable {
				V result;
				ExecutionException fail;

				@Override
				public void run() {
					try {
						result = callable.call();
					} catch (Exception e) {
						fail = new ExecutionException(e);
						fail.fillInStackTrace();
					}
				}
			}

			SyncResult result = new SyncResult();

			syncExec(result);

			if (result.fail != null) {
				throw result.fail;
			}

			return result.result;
		}

		abstract void asyncExec(Runnable runnable);

		@Override
		public List<Runnable> shutdownNow() {
			List<Runnable> result = new ArrayList<>();

			shutdown();

			for (Runnable dequeued = dequeue(); dequeued != null; dequeued = dequeue()) {
				result.add(dequeued);
			}

			return result;
		}

		private RunnableWrapper enqueue(Runnable task) {
			RunnableWrapper result = new RunnableWrapper(task);

			lock.lock();
			try {
				boolean wasEmpty = pending.isEmpty();
				pending.offer(result);
				if (wasEmpty) {
					// Now not empty
					emptyCond.signalAll();
				}
			} finally {
				lock.unlock();
			}

			return result;
		}

		private RunnableWrapper dequeue() {
			RunnableWrapper result = null;

			lock.lock();
			try {
				result = pending.poll();
				if (result == null) {
					// Now empty
					emptyCond.signalAll();
				}
			} finally {
				lock.unlock();
			}

			return result;
		}

		boolean dequeue(RunnableWrapper task) {
			boolean result = false;

			lock.lock();
			try {
				result = pending.remove(task);
				if (result && pending.isEmpty()) {
					// Now empty
					emptyCond.signalAll();
				}
			} finally {
				lock.unlock();
			}

			return result;
		}

		@Override
		public void shutdown() {
			shutdown = true;
		}

		@Override
		public boolean isTerminated() {
			lock.lock();
			try {
				return isShutdown() && pending.isEmpty();
			} finally {
				lock.unlock();
			}
		}

		@Override
		public boolean isShutdown() {
			return shutdown;
		}

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			if (timeout < 0L) {
				throw new IllegalArgumentException("negative timeout"); //$NON-NLS-1$
			}

			final Date deadline = (timeout == 0L) ? null : new Date(System.currentTimeMillis() + unit.toMillis(timeout));
			boolean result = false;

			lock.lock();
			try {
				boolean stillWaiting = true;
				for (result = isTerminated(); !result && stillWaiting; result = isTerminated()) {
					if (deadline == null) {
						emptyCond.await();
					} else {
						stillWaiting = emptyCond.awaitUntil(deadline);
					}
				}
			} finally {
				lock.unlock();
			}

			return result;
		}

		@Override
		public Future<?> submit(IProgressRunnable task) {
			return submit(new IProgressCallable<Void>() {
				@Override
				public Void call(IProgressMonitor monitor) {
					task.run(monitor);
					return null;
				}
			});
		}

		@Override
		public void syncExec(IProgressRunnable task) throws InterruptedException, ExecutionException {
			syncCall(new IProgressCallable<Void>() {
				@Override
				public Void call(IProgressMonitor monitor) {
					task.run(monitor);
					return null;
				}
			});
		}

		IProgressService getProgressService(IProgressCallable<?> callable) {
			IProgressService result;

			try {
				ServicesRegistry registry = (callable instanceof IServiceRegistryProvider)
						? ((IServiceRegistryProvider) callable).getServiceRegistry()
						: null;
				IMultiDiagramEditor editor = ServiceUtils.getInstance().getService(
						IMultiDiagramEditor.class, registry);
				result = editor.getEditorSite().getService(IWorkbenchSiteProgressService.class);
			} catch (ServiceException e) {
				// Fine, there's no editor
				result = PlatformUI.getWorkbench().getProgressService();
			}

			return result;
		}

		//
		// Nested types
		//

		private class RunnableWrapper implements Runnable {

			private final Runnable delegate;

			RunnableWrapper(Runnable delegate) {
				this.delegate = delegate;
			}

			@Override
			public void run() {
				// Don't run if I was cancelled by shutdown
				if (dequeue(this)) {
					delegate.run();
				}
			}
		}
	};

	private static class DisplayExecutorService extends UIExecutorService {
		private final Display display;

		DisplayExecutorService(Display display) {
			super();

			this.display = display;
		}

		@Override
		void asyncExec(Runnable runnable) {
			display.asyncExec(runnable);
		}

		@Override
		public void syncExec(Runnable task) {
			display.syncExec(task);
		}

		@Override
		public <V> Future<V> submit(IProgressCallable<V> callable) {
			IProgressService service = getProgressService(callable);
			IWorkbenchSiteProgressService wbService = (service instanceof IWorkbenchSiteProgressService) ? (IWorkbenchSiteProgressService) service : null;

			FutureProgress<V> result = new FutureProgress<>(callable, wbService);

			try {
				service.run(true, true, result);
			} catch (Exception e) {
				// This shouldn't happen when running asynchronously
				result.completeExceptionally(e);
			}

			return result;
		}

		@Override
		public <V> V syncCall(IProgressCallable<V> callable) throws InterruptedException, ExecutionException {
			IProgressService service = getProgressService(callable);
			IWorkbenchSiteProgressService wbService = (service instanceof IWorkbenchSiteProgressService) ? (IWorkbenchSiteProgressService) service : null;

			FutureProgress<V> result = new FutureProgress<>(callable, wbService);

			try {
				service.busyCursorWhile(result);
			} catch (Exception e) {
				result.completeExceptionally(e);
			}

			return result.get(); // It really should be completed, by now
		}
	}

	private static class RealmExecutorService extends UIExecutorService {
		private final Realm realm;

		RealmExecutorService(Realm realm) {
			super();

			this.realm = realm;
		}

		@Override
		void asyncExec(Runnable runnable) {
			realm.asyncExec(runnable);
		}

		@Override
		public void syncExec(Runnable task) {
			realm.exec(task);
		}

		@Override
		public <V> Future<V> submit(IProgressCallable<V> callable) {
			// No place to report progress in this case
			FutureTask<V> result = new FutureTask<>(() -> callable.call(new NullProgressMonitor()));
			asyncExec(result);
			return result;
		}

		@Override
		public <V> V syncCall(IProgressCallable<V> callable) throws InterruptedException, ExecutionException {
			// No place to report progress in this case
			FutureTask<V> result = new FutureTask<>(() -> callable.call(new NullProgressMonitor()));
			syncExec(result);
			return result.get(); // It really should be completed, by now
		}
	}

	private static class FutureProgress<V> extends CompletableFuture<V> implements IRunnableWithProgress {
		private final IProgressCallable<V> delegate;
		private final IWorkbenchSiteProgressService service;

		private volatile IProgressMonitor monitor;

		FutureProgress(IProgressCallable<V> delegate, IWorkbenchSiteProgressService service) {
			super();

			this.delegate = delegate;
			this.service = service;
		}

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				this.monitor = monitor;

				if (service != null) {
					service.incrementBusy();
				}

				try {
					complete(delegate.call(monitor));
				} finally {
					this.monitor = null;

					if (service != null) {
						service.decrementBusy();
					}
				}
			} catch (OperationCanceledException e) {
				throw new InterruptedException(e.getMessage());
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			}
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			IProgressMonitor monitor = this.monitor;
			if (monitor != null) {
				monitor.setCanceled(true);
			}

			return super.cancel(mayInterruptIfRunning);
		}

	}
}
