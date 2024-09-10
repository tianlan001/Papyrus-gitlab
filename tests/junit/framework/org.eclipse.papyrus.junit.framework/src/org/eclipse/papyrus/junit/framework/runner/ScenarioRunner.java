/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.framework.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.eclipse.papyrus.junit.framework.classification.ClassificationConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * <p>
 * A scenario-based test runner. A method annotated with {@link Scenario @Scenario} lays out a scenario and at various places where something is to be verified, calls this class's static {@link #verificationPoint()} method as an {@code if} condition to guard a
 * block of assertion statements. The {@link Scenario @Scenario} annotation provides the labels of the verification points, in the order in which they appear. Each verification point is surfaced as a separate test, which may pass or fail independently of the
 * others.
 * </p>
 * <p>
 * Classic {@link Test @Test} methods are supported by this runner, also. They are run in the usual way, not as scenarios with multiple verification points.
 * </p>
 * 
 * @see Scenario
 * @see #verificationPoint()
 */
public class ScenarioRunner extends ParentRunner<Runner> {

	private static final Deque<VerificationPointsRunner> runnerStack = new ArrayDeque<VerificationPointsRunner>();

	public ScenarioRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected List<Runner> getChildren() {
		Iterable<FrameworkMethod> methods = Iterables.concat(
				getTestClass().getAnnotatedMethods(Test.class),
				getTestClass().getAnnotatedMethods(Scenario.class));
		return ImmutableList.copyOf(Iterables.transform(methods, new Function<FrameworkMethod, Runner>() {
			@Override
			public Runner apply(FrameworkMethod input) {
				return new VerificationPointsRunnerBuilder(input).build();
			}
		}));
	}

	@Override
	protected Description describeChild(Runner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(Runner child, RunNotifier notifier) {
		if (!(child instanceof VerificationPointsRunner)) {
			// Probably the error-reporting runner
			child.run(notifier);
		} else {
			VerificationPointsRunner points = (VerificationPointsRunner) child;

			pushRunner(points);
			points.start();

			try {
				points.run(notifier);
			} finally {
				points.finish();
				popRunner();
			}
		}
	}

	/**
	 * Declares the next verification point in the scenario. Use as the condition of an {@code if} block
	 * enclosing the verification point's assertion statements. There must be one verification-point
	 * block per verification-point label declared in the {@link Scenario @Scenario} annotation. e.g.,
	 * 
	 * <pre>
	 *     import static org.eclipse.papyrus.junit.framework.runners.ScenarioRunner.verificationPoint;
	 *     
	 *     // ...
	 *     
	 *     &#64;Scenario({ "first", "second" })
	 *     public void myLongAndIntricateScenario() {
	 *       // Setup stuff ...
	 *       
	 *       if (verificationPoint()) {
	 *         // Assertions here
	 *       }
	 *       
	 *       // More stuff ...
	 *       
	 *       if (verificationPoint()) {
	 *         // More assertions here
	 *       }
	 *     }
	 * </pre>
	 */
	public static boolean verificationPoint() {
		return currentRunner().verificationPoint();
	}

	private static VerificationPointsRunner currentRunner() {
		return runnerStack.getLast();
	}

	private static VerificationPointsRunner popRunner() {
		return runnerStack.removeLast();
	}

	private static void pushRunner(VerificationPointsRunner runner) {
		runnerStack.addLast(runner);
	}

	/**
	 * Queries whether a test's annotations indicate that it is to be ignored in the
	 * current run. That may is if any of the annotations is the {@code @Ignore} annotation
	 * or if none of the {@link ClassificationConfig} annotations match the current run.
	 * 
	 * @param testAnnotations
	 *            a test's annotations (including those inherited from its class)
	 * 
	 * @return whether the test should be skipped
	 */
	static boolean isIgnored(Annotation[] testAnnotations) {
		boolean result = !ClassificationConfig.shouldRun(testAnnotations);

		if (!result) {
			// Look for the @Ignore annotation
			result = Iterators.filter(Arrays.asList(testAnnotations).iterator(), Ignore.class).hasNext();
		}

		return result;
	}

	//
	// Nested types
	//

	private class VerificationPointsRunnerBuilder extends RunnerBuilder {
		private final FrameworkMethod scenarioMethod;

		VerificationPointsRunnerBuilder(FrameworkMethod scenarioMethod) {
			super();

			this.scenarioMethod = scenarioMethod;

		}

		@Override
		public Runner runnerForClass(Class<?> testClass) throws Throwable {
			Runner result;

			if (scenarioMethod.getAnnotation(Scenario.class) != null) {
				result = new VerificationPointsRunner(scenarioMethod);
			} else {
				// It's just an @Test method
				result = new JUnitAccess(testClass).classicTest(scenarioMethod);
			}

			List<Annotation> allAnnotations = Lists.newArrayList(scenarioMethod.getAnnotations());
			allAnnotations.addAll(Arrays.asList(scenarioMethod.getMethod().getDeclaringClass().getAnnotations()));
			if (isIgnored(Iterables.toArray(allAnnotations, Annotation.class))) {
				result = new IgnoreRunner(result.getDescription());
			}

			return result;
		}

		public Runner build() {
			return safeRunnerForClass(scenarioMethod.getMethod().getDeclaringClass());
		}
	}

	private class VerificationPointsRunner extends ParentRunner<String> {
		private final FrameworkMethod scenarioMethod;
		private final Scenario scenario;
		private final JUnitAccess junit;

		private List<String> verpts = Lists.newArrayList();
		private int nextVerificationPoint;
		private Failure lastFailure;

		VerificationPointsRunner(FrameworkMethod scenarioMethod) throws InitializationError {
			super(scenarioMethod.getMethod().getDeclaringClass());

			this.scenarioMethod = scenarioMethod;
			this.scenario = scenarioMethod.getAnnotation(Scenario.class);
			this.junit = new JUnitAccess(scenarioMethod.getMethod().getDeclaringClass());
		}

		@Override
		public Description getDescription() {
			Description result = Description.createSuiteDescription(scenarioMethod.getName(), scenarioMethod.getAnnotations());
			for (Description child : super.getDescription().getChildren()) {
				result.addChild(child);
			}
			return result;
		}

		@Override
		protected List<String> getChildren() {
			return Arrays.asList(scenario.value());
		}

		@Override
		protected Description describeChild(String child) {
			return Description.createTestDescription(getTestClass().getJavaClass(), scenarioMethod.getName() + ":" + child);
		}

		@Override
		protected void runChild(String child, RunNotifier notifier) {
			Description description = describeChild(child);

			if (verpts.contains(child)) {
				if (verpts.get(0).equals(child)) {
					// This is the first verification point. It needs to run the scenario method.
					// If all verification points pass, this will be the only execution of that
					// method. Otherwise, if some verification point fails, the next one (if
					// any) will run the scenario again, skipping all verifications before it.
					if (!runScenario(child, description, notifier)) {
						// This run failed, so run again from the beginning of the scenario
						nextVerificationPoint = 0;
					}
				} else {
					// This test failed in a previous execution
					Throwable last = (lastFailure == null)
							? new AssertionError("Previous execution failed")
							: lastFailure.getException();
					notifier.fireTestStarted(description);
					notifier.fireTestFailure(new Failure(description, last));
					lastFailure = null;
					notifier.fireTestFinished(description);
				}
			} else if (failedLastTime(child)) {
				// This test failed in a previous execution
				notifier.fireTestStarted(description);
				notifier.fireTestFailure(new Failure(description, lastFailure.getException()));
				lastFailure = null;
				notifier.fireTestFinished(description);
			} else {
				// This verification point passed in a previous execution of the scenario
				notifier.fireTestStarted(description);
				notifier.fireTestFinished(description);
			}
		}

		void start() {
			verpts.addAll(getChildren());
			nextVerificationPoint = 0;
		}

		boolean verificationPoint() {
			final String[] points = scenario.value();

			boolean result = ((nextVerificationPoint < points.length)
					&& verpts.contains(points[nextVerificationPoint]));

			nextVerificationPoint++;

			int limit = Math.min(nextVerificationPoint, points.length);

			// We have passed all verifications up to this point
			for (int i = 0; i < limit; i++) {
				verpts.remove(points[i]);
			}

			return result;
		}

		String currentVerificationPoint() {
			final String[] points = scenario.value();
			int index = Math.max(0, Math.min(nextVerificationPoint - 1, points.length - 1));

			return points[index];
		}

		boolean failedLastTime(String child) {
			boolean result = lastFailure != null;

			if (result) {
				final String[] points = scenario.value();
				// If there are no verification points remaining, then is this the last one
				// and it failed in the previous run?
				if (verpts.isEmpty()) {
					result = points[points.length - 1].equals(child);
				} else {
					int successor = Math.max(Arrays.asList(points).indexOf(child) + 1, points.length - 1);
					result = verpts.contains(points[successor]);
				}
			}

			return result;
		}

		void finish() {
			verpts.clear();
			nextVerificationPoint = scenario.value().length;
			lastFailure = null;
		}

		private boolean runScenario(final String child, Description description, final RunNotifier notifier) {
			final boolean[] result = { true };

			RunNotifier notifierWrapper = new RunNotifier() {
				@Override
				public void fireTestFailure(Failure failure) {
					result[0] = false;

					if (child.equals(currentVerificationPoint())) {
						// This verification point failed
						notifier.fireTestFailure(failure);
					} else {
						// A subsequent verification point failed. This one passed
						lastFailure = failure;
					}
				}

				@Override
				public void fireTestAssumptionFailed(Failure failure) {
					result[0] = false;

					if (child.equals(currentVerificationPoint())) {
						// This verification point failed
						notifier.fireTestAssumptionFailed(failure);
					} else {
						// A subsequent verification point failed. This one passed
						lastFailure = failure;
					}
				}

				@Override
				public void fireTestIgnored(Description description) {
					notifier.fireTestIgnored(description);
				}

				@Override
				public void fireTestStarted(Description description) throws StoppedByUserException {
					notifier.fireTestStarted(description);
				}

				@Override
				public void fireTestFinished(Description description) {
					notifier.fireTestFinished(description);
				}
			};

			runLeaf(junit.methodBlock(scenarioMethod), description, notifierWrapper);

			return result[0];
		}
	}

	static class JUnitAccess extends BlockJUnit4ClassRunner {
		public JUnitAccess(Class<?> testClass) throws InitializationError {
			super(testClass);
		}

		@Override
		protected Statement methodBlock(FrameworkMethod method) {
			return super.methodBlock(method);
		}

		// Our test methods are annotated with @Scenario or @Test
		@Override
		protected List<FrameworkMethod> computeTestMethods() {
			List<FrameworkMethod> result = Lists.newArrayList(getTestClass().getAnnotatedMethods(Scenario.class));
			result.addAll(getTestClass().getAnnotatedMethods(Test.class));
			return result;
		}

		Runner classicTest(final FrameworkMethod testMethod) {
			return new Runner() {

				@Override
				public void run(RunNotifier notifier) {
					runLeaf(methodBlock(testMethod), getDescription(), notifier);
				}

				@Override
				public Description getDescription() {
					return Description.createTestDescription(
							getTestClass().getJavaClass(),
							testMethod.getName(),
							testMethod.getAnnotations());
				}
			};
		}
	}
}
