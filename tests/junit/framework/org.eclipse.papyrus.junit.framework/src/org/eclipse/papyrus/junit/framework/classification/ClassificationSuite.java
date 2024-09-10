/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.framework.classification;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;

/**
 * A specialized Test {@linkplain Suite Suite} runner that recognizes the Papyrus-specific
 * {@link ClassificationConfig classification} annotations on the suite as a whole. It also
 * supports a dynamic specification of test suites to include via the {@literal @}{@link DynamicClasses}
 * annotation, especially useful for generated tests that may or may not have been generated at
 * the time of test execution (or at least of compilation of the test suite class).
 */
public class ClassificationSuite extends Suite {

	/**
	 * Mapping of whether any tests at all in a suite tree are statically enabled, according to
	 * their annotations.
	 */
	private static final LoadingCache<Description, Boolean> enabledSuites = CacheBuilder.newBuilder().build(
			CacheLoader.from(ClassificationSuite::isEnabled));

	private Description description;

	public ClassificationSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		this(builder, klass, getAnnotatedClasses(klass));
	}

	public ClassificationSuite(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
		super(klass, withDynamicSuites(klass, suiteClasses));
	}

	public ClassificationSuite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
		super(builder, klass, withDynamicSuites(klass, suiteClasses));
	}

	private static Class<?>[] getAnnotatedClasses(Class<?> class_) throws InitializationError {
		SuiteClasses annotation = class_.getAnnotation(SuiteClasses.class);
		if (annotation == null) {
			throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", class_.getName()));
		}
		return annotation.value();
	}

	private static Class<?>[] withDynamicSuites(Class<?> suiteClass, Class<?>[] staticSuites) {
		Class<?>[] result = staticSuites;

		Class<?>[] dynamicSuites = getDynamicSuites(suiteClass);
		if (dynamicSuites.length > 0) {
			result = ObjectArrays.concat(staticSuites, dynamicSuites, Class.class);
		}

		return result;
	}

	private static Class<?>[] getDynamicSuites(Class<?> suiteClass) {
		List<Class<?>> result;

		DynamicClasses dynclasses = suiteClass.getAnnotation(DynamicClasses.class);
		if (dynclasses == null) {
			result = Collections.emptyList();
		} else {
			result = Lists.newArrayListWithCapacity(dynclasses.value().length);
			for (String classname : dynclasses.value()) {
				try {
					result.add(suiteClass.getClassLoader().loadClass(classname));
				} catch (Exception e) {
					// OK. It's not there, so we just ignore it
				}
			}
		}

		return Iterables.toArray(result, Class.class);
	}

	@Override
	protected List<Runner> getChildren() {
		// If I don't match the current configuration, none of my tests will run, so don't even provide them
		// because they just inflate the size of the progress meter
		if (ClassificationConfig.shouldRun(getRunnerAnnotations())) {
			return super.getChildren();
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void run(RunNotifier notifier) {
		// If I don't match the current configuration, none of my tests should be run
		if (ClassificationConfig.shouldRun(getRunnerAnnotations())) {
			super.run(notifier);
		} else {
			Description description = getDescription();
			notifier.fireTestIgnored(description);
		}
	}

	@Override
	protected Statement classBlock(final RunNotifier notifier) {
		// We never throw in these lazy calculations
		return enabledSuites.getUnchecked(getDescription())
				// Include the @BeforeClass, @AfterClass, and @ClassRule steps
				? super.classBlock(notifier)
				// Just the bare bones to record everything that is skipped
				: skipAll(notifier);
	}

	@Override
	public Description getDescription() {
		if (description == null) {
			description = super.getDescription();
		}
		return description;
	}

	/**
	 * Creates a statement that simply skips all of the tests in my suite, recursively.
	 * 
	 * @param notifier
	 *            tracks the execution (or, rather, skipping) of the tests
	 * 
	 * @return the all-skipping statement
	 */
	protected Statement skipAll(RunNotifier notifier) {
		return new Statement() {
			@Override
			public void evaluate() {
				skipAll(getDescription(), notifier);
			}
		};
	}

	/**
	 * Skips all of the tests in the given {@code suite}, recursively.
	 * 
	 * @param suite
	 *            a test suite to skip
	 * @param notifier
	 *            tracks the execution (or, rather, skipping) of the tests
	 */
	protected void skipAll(Description suite, RunNotifier notifier) {
		for (Description next : suite.getChildren()) {
			if (next.isSuite()) {
				skipAll(next, notifier);
			} else {
				notifier.fireTestIgnored(next);
			}
		}

		notifier.fireTestIgnored(suite);
	}

	/**
	 * Queries whether a given {@code suite} will run any tests at all.
	 * 
	 * @param suite
	 *            a test suite
	 * @return whether it has any leaf tests that will not be ignored for some reason
	 */
	static boolean isEnabled(Description suite) {
		boolean result = false;

		for (Description leaf : getLeafTests(suite)) {
			Iterable<Annotation> annotations = Iterables.concat(
					leaf.getAnnotations(),
					Arrays.asList(leaf.getTestClass().getAnnotations()));

			if (ClassificationConfig.shouldRun(Iterables.toArray(annotations, Annotation.class))) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains an iteration of all of the leaf-level tests in a {@code suite}, in depth-first order.
	 * 
	 * @param suite
	 *            a test suite to iterate
	 * 
	 * @return all of its leaf-level tests
	 */
	static Iterable<Description> getLeafTests(Description suite) {
		return new Iterable<Description>() {

			@Override
			public Iterator<Description> iterator() {
				return new AbstractIterator<Description>() {
					Deque<Iterator<Description>> stack = new ArrayDeque<>();
					Iterator<Description> current = suite.getChildren().iterator();

					// Advance the tractor
					Iterator<Description> feed() {
						while (!current.hasNext()) {
							current = stack.pollLast();

							if (current == null) {
								current = Collections.emptyIterator();
								break;
							}
						}

						return current;
					}

					void push(Description suite) {
						stack.addLast(current);
						current = suite.getChildren().iterator();
					}

					@Override
					protected Description computeNext() {
						Description result = null;

						while ((result == null) && feed().hasNext()) {
							Description next = feed().next();

							if (next.isTest() && (next.getTestClass() != null)) {
								result = next;
							} else if (next.isSuite()) {
								// Push this suite onto the stack
								push(next);
							} else {
								// Otherwise it's a weird test without a class?!?
								System.err.println("Leaf test without a class: " + next);
							}
						}

						if (result == null) {
							result = endOfData();
						}

						return result;
					}
				};
			}
		};
	}

	//
	// Nested types
	//

	/**
	 * An annotation like the {@literal @}{@link SuiteClasses} that specifies, by name, test suite classes
	 * to find and include dynamically. Any that are not available are simply ignored.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DynamicClasses {
		/**
		 * Names of test suite classes to optionally include in the test suite.
		 */
		String[] value();
	}
}
