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
 *  Christian W. Damus (CEA) - add support for conditional tests
 *  Christian W. Damus (CEA) - bug 432813
 *  Christian W. Damus (CEA) - bug 434993
 *  Christian W. Damus (CEA) - bug 436047
 *  Christian W. Damus - bug 485156
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.classification;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.operations.DefaultOperationHistory;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.eclipse.papyrus.junit.framework.classification.rules.ConditionRule;
import org.eclipse.papyrus.junit.framework.classification.rules.Conditional;
import org.eclipse.papyrus.junit.framework.classification.rules.MemoryLeakRule;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Internal implementation of the common classification-sensitive behaviour
 * of the {@link ClassificationRunner} and {@link ClassificationRunnerWithParameters}
 * test runners.
 *
 * @author Camille Letavernier
 */
class ClassificationRunnerImpl {

	private final static long EVENT_LOOP_TIMEOUT = 2L * 60L * 1000L; // 2 minutes in millis

	private final static long ONE_MB = 1024L * 1024L; // a megabyte, in bytes

	private static final Supplier<TestRule> uiFlusherRuleSupplier = createUIFlusherRuleSupplier();

	private final ThreadLocal<Object> preparedTest = new ThreadLocal<Object>();

	private final Delegate delegate;

	ClassificationRunnerImpl(Delegate delegate) throws InitializationError {
		super();

		this.delegate = delegate;
	}

	final void runChild(FrameworkMethod method, RunNotifier notifier) {
		List<Annotation> allAnnotations = ListHelper.asList(method.getAnnotations());
		allAnnotations.addAll(Arrays.asList(method.getMethod().getDeclaringClass().getAnnotations()));
		if (ClassificationConfig.shouldRun(allAnnotations.toArray(new Annotation[allAnnotations.size()])) && conditionSatisfied(method)) {
			delegate.runChild(method, notifier);
		} else {
			Description description = delegate.describeChild(method);
			notifier.fireTestIgnored(description);
		}
	}

	final Object createTest() throws Exception {
		// Look for a prepared test instance
		Object result = preparedTest.get();
		if (result != null) {
			// We won't need this test instance again
			clearPreparedTest();
		} else {
			result = delegate.createTest();
		}

		return result;
	}

	final Object prepareTest() throws Exception {
		// Prepare the test instance and stash it to return on the next invocation
		Object result = delegate.createTest();
		preparedTest.set(result);
		return result;
	}

	final void clearPreparedTest() {
		preparedTest.remove();
	}

	private boolean conditionSatisfied(FrameworkMethod method) {
		boolean result = true;

		// Does this test declare some precondition?
		Conditional conditional = method.getAnnotation(Conditional.class);
		if (conditional != null) {
			try {
				// We need the test instance to invoke the condition on it, so prepare it now
				Object test = prepareTest();
				result = ConditionRule.testCondition(method.getMethod().getDeclaringClass(), conditional, test);
			} catch (Throwable t) {
				// If we couldn't create the test, then we should just ignore it
				result = false;
			} finally {
				if (!result) {
					// We won't be running the test, so forget the prepared instance (if any)
					clearPreparedTest();
				}
			}
		}

		return result;
	}

	List<TestRule> getTestRules(Object target) {
		// MemoryLeakRules must be the outer-most rules, because leak assertions must only happen after all possible tear-down actions have run
		return reorderForMemoryLeakRules(delegate.getTestRules(target));
	}

	private List<TestRule> reorderForMemoryLeakRules(List<TestRule> rules) {
		// Quick scan for memory rules
		if (!rules.isEmpty()) {
			int memoryRuleCount = Iterables.size(Iterables.filter(rules, Predicates.instanceOf(MemoryLeakRule.class)));
			if (memoryRuleCount > 0) {
				// Bubble the memory rules to the end
				int limit = rules.size() - memoryRuleCount;

				for (int i = 0; i < limit; i++) {
					if (rules.get(i) instanceof MemoryLeakRule) {
						// Move the rule to the end and take a step back to get the next element
						rules.add(rules.remove(i--));
					}
				}
			}
		}

		return rules;
	}

	Statement classBlock(RunNotifier notifier) {
		Statement result = delegate.classBlock(notifier);

		// Wrap the class suite in a rule that flushes the UI thread to release memory referenced by UI runnables
		TestRule uiFlusher = uiFlusherRuleSupplier.get();
		if (uiFlusher != null) {
			// This rule doesn't need any actual test description
			result = uiFlusher.apply(result, Description.EMPTY);
		}

		return result;
	}

	private static Supplier<TestRule> createUIFlusherRuleSupplier() {
		Supplier<TestRule> result = Suppliers.ofInstance(null);

		try {
			if (PlatformUI.isWorkbenchRunning()) {
				result = Suppliers.memoize(new Supplier<TestRule>() {

					@Override
					public TestRule get() {
						if (Display.getCurrent() != null) {
							return new TestWatcher() {

								@Override
								protected void finished(Description description) {
									final Display display = Display.getCurrent();
									if (display == null) {
										// Can't do UI manipulations and history listener hacking except on the UI thread
										return;
									}

									flushUIEventQueue(display);

									purgeZombieHistoryListeners();

									clearDecorationScheduler();
								}
							};
						}

						return null;
					}
				});
			}
		} catch (LinkageError e) {
			// Not running in Eclipse UI context. Fine
		}

		return result;
	}

	private static void flushUIEventQueue(Display display) {
		long base = System.currentTimeMillis();
		long timeout = EVENT_LOOP_TIMEOUT;

		// Flush the UI thread's pending events
		while (!display.isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					break;
				}
			} catch (Exception e) {
				// Ignore it
			}

			long now = System.currentTimeMillis();
			if ((now - base) > timeout) {
				// This seems to be taking a really long time. What's up?
				base = now;
				timeout = timeout * 3L / 2L; // Exponential back-off to avoid over-reporting
				int freeMB = (int) (Runtime.getRuntime().freeMemory() / ONE_MB);
				System.err.printf("========%nUI event queue clean-up seems to be running long.%nCurrent free memory: %d MB%n========%n%n", freeMB);
			}
		}
	}

	private static void purgeZombieHistoryListeners() {
		// If there are no editors open any longer, then all of the action handlers currently
		// listening to the operation history are leaked, so remove them. This ensures that we
		// do not end up wasting time in notifying thousands of dead/broken/useless listeners
		// every time a test case executes an operation on the history (which happens *a lot*)
		IWorkbench bench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = (bench == null) ? null : bench.getActiveWorkbenchWindow();
		if ((window == null) && (bench != null) && (bench.getWorkbenchWindowCount() > 0)) {
			window = bench.getWorkbenchWindows()[0];
		}
		if (window != null && window.getActivePage().getEditorReferences().length == 0) {
			final ListenerList historyListeners = OperationHistoryHelper.getOperationHistoryListeners();
			final Object[] listeners = historyListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				if (OperationHistoryHelper.shouldRemoveHistoryListener(listeners[i])) {
					historyListeners.remove(listeners[i]);
				}
			}
		}
	}

	private static void clearDecorationScheduler() {
		IWorkbench bench = PlatformUI.getWorkbench();
		if (bench != null) {
			IBaseLabelProvider bogusProvider = new BaseLabelProvider();

			try {
				// The DecoratorManager is a label-provider listener and
				// it clears the scheduler on label-provider change events
				((ILabelProviderListener) bench.getDecoratorManager()).labelProviderChanged(new LabelProviderChangedEvent(bogusProvider));
			} finally {
				bogusProvider.dispose();
			}
		}
	}

	//
	// Nested types
	//

	static class OperationHistoryHelper {

		static final Field listenersField;

		static final Set<Class<?>> historyListenerClasses;

		static {
			try {
				listenersField = DefaultOperationHistory.class.getDeclaredField("listeners");
				listenersField.setAccessible(true);

				historyListenerClasses = Sets.<Class<?>> newHashSet( //
						Platform.getBundle("org.eclipse.gmf.runtime.diagram.ui.actions").loadClass("org.eclipse.gmf.runtime.diagram.ui.actions.internal.PropertyChangeContributionItem"), //
						Platform.getBundle("org.eclipse.ui.workbench").loadClass("org.eclipse.ui.operations.OperationHistoryActionHandler$HistoryListener"));
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}

		static ListenerList getOperationHistoryListeners() {
			try {
				return (ListenerList) listenersField.get(PlatformUI.getWorkbench().getOperationSupport().getOperationHistory());
			} catch (Exception e) {
				org.junit.Assert.fail(e.getLocalizedMessage());
				return null; // Unreachable
			}
		}

		static boolean shouldRemoveHistoryListener(Object listener) {
			boolean result = historyListenerClasses.contains(listener.getClass().getName());

			if (!result) {
				// Maybe it's a subclass
				for (Class<?> next : historyListenerClasses) {
					if (next.isInstance(listener)) {
						// Remember this
						historyListenerClasses.add(listener.getClass());
						result = true;
						break;
					}
				}
			}

			return result;
		}
	}

	/**
	 * Protocol for a delegate that provides the default test framework behaviour
	 * for the classification runner. These methods are as specified by the
	 * corresponding APIs of the {@link BlockJUnit4ClassRunner} class.
	 */
	interface Delegate {
		void runChild(FrameworkMethod method, RunNotifier notifier);

		Description describeChild(FrameworkMethod method);

		Object createTest() throws Exception;

		List<TestRule> getTestRules(Object target);

		Statement classBlock(RunNotifier notifier);
	}
}
