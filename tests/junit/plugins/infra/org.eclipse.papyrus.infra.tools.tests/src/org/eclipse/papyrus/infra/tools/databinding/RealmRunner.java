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
 *   Christian W. Damus - bug 487027
 *
 */
package org.eclipse.papyrus.infra.tools.databinding;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;


/**
 * A custom test runner that runs tests in the thread (which is not the SWT UI thread!) of a data-bindings realm specially
 * designed for the purpose of JUnit testing.
 */
public class RealmRunner extends ClassificationRunner {

	private final Field realmField;

	public RealmRunner(@SuppressWarnings("rawtypes") Class klass) throws InitializationError {
		super(klass);

		realmField = Stream.iterate(klass, Class::getSuperclass)
				.flatMap(c -> Stream.<Field> of(c.getDeclaredFields()))
				.filter(f -> Modifier.isStatic(f.getModifiers()))
				.filter(f -> f.getType() == Realm.class)
				.findAny().get();
		realmField.setAccessible(true);
	}

	private TestRealm getRealm() throws Exception {
		return ((TestRealm) realmField.get(null));
	}

	private void setRealm(TestRealm realm) throws Exception {
		realmField.set(null, realm);
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		final Statement base = super.classBlock(notifier);
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				setRealm(new TestRealm());
				try {
					base.evaluate();
				} finally {
					getRealm().dispose();
					setRealm(null);
				}
			}
		};
	}

	void basicRunChild(FrameworkMethod method, RunNotifier notifier) {
		super.runChild(method, notifier);
	}

	/**
	 * Schedules a test to run asynchronously on the realm's thread, waiting for it to finish there.
	 */
	@Override
	protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
		AsyncRunnable run = new AsyncRunnable() {

			@Override
			protected void doRun() {
				basicRunChild(method, notifier);
			}
		};

		try {
			getRealm().exec(run);
		} catch (Exception e) {
			notifier.fireTestFailure(new Failure(describeChild(method), e));
		}
		run.await();
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod method, final Object test) {
		final Statement base = super.methodInvoker(method, test);
		Statement result = base;

		if (method.getAnnotation(TrackedGetterTest.class) != null) {
			result = new Statement() {

				@Override
				public void evaluate() throws Throwable {
					AsyncRunnable run = new AsyncRunnable() {

						@Override
						protected void doRun() throws Throwable {
							base.evaluate();
						}
					};

					// Don't actually need to listen to the tracked observables, just to collect them
					IObservable[] tracked = ObservableTracker.runAndMonitor(run, null, null);

					// This was not actually run asynchronously
					run.throwCaughtThrowable();

					IObservable observable = getObservableFixture(test);

					boolean found = false;
					for (int i = 0; !found && (i < tracked.length); i++) {
						found = tracked[i] == observable;
					}
					assertThat("Observable fixture did not invoke ObservableTracker::getterCalled()", found, is(true));
				}
			};
		}

		return result;
	}

	IObservable getObservableFixture(Object test) {
		List<IObservable> list = getTestClass().getAnnotatedFieldValues(test, ObservableFixture.class, IObservable.class);

		assertThat("No @ObservableFixture field found in test class.", list.isEmpty(), is(false));

		return list.get(0);
	}

	//
	// Nested types
	//

	private static class TestRealm extends Realm {

		private ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				realmThread = new Thread(r, "Test Realm");
				return realmThread;
			}
		});

		private volatile Thread realmThread;

		@Override
		public boolean isCurrent() {
			return Thread.currentThread() == realmThread;
		}

		@Override
		public void asyncExec(Runnable runnable) {
			executor.execute(runnable);
		}

		void dispose() {
			if (executor != null) {
				executor.shutdown();
				executor = null;
				realmThread = null;
			}
		}
	}

	static abstract class AsyncRunnable implements Runnable {

		private final CountDownLatch latch = new CountDownLatch(1);

		private Throwable thrown;

		@Override
		public void run() {
			try {
				doRun();
			} catch (Throwable e) {
				thrown = e;
			} finally {
				latch.countDown();
			}
		}

		protected abstract void doRun() throws Throwable;

		public void await() {
			try {
				latch.await();
			} catch (Exception e) {
				fail(e.getLocalizedMessage());
			}

			if (thrown instanceof Error) {
				throw (Error) thrown;
			} else if (thrown instanceof RuntimeException) {
				throw (RuntimeException) thrown;
			} else if (thrown instanceof Exception) {
				throw new RuntimeException(thrown);
			}
		}

		void throwCaughtThrowable() throws Throwable {
			if (thrown != null) {
				throw thrown;
			}
		}
	};
}
