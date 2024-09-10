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
 *  Christian W. Damus - bug 451230
 *  Christian W. Damus - bug 464647
 *  Christian W. Damus - bug 480812
 *  Christian W. Damus - bug 485156
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.classification;

import static org.eclipse.papyrus.junit.framework.classification.TestCategory.ExpensiveTest;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.FailingTest;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.GeneratedTest;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.InteractiveTest;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.InvalidTest;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.NotImplemented;
import static org.eclipse.papyrus.junit.framework.classification.TestCategory.Standard;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


/**
 * <p>
 * This class is used to configure the Classification Annotations which should be excluded from a test job. A classification configuration is an immutable set of {@linkplain TestCategory test categories} that are included in the test configuration.
 * </p>
 * <p>
 * Usage:
 * </p>
 * 
 * <pre>
 * ClassificationConfig.setExcludedTestCategories(TestCategory.InvalidTest, TestCategory.NotImplemented);
 * 
 * // or
 * 
 * ClassificationConfig.setIncludedTestCategories(TestCategory.Standard, TestCategory.NotImplemented);
 * 
 * // or
 * 
 * ClassificationConfig.setExcludedTestCategories(ClassificationConfig.FAILING_TESTS_CONFIG);
 * 
 * // or
 * 
 * ClassificationConfig.setIncludedTestCategories(ClassificationConfig.FULL_CI_TESTS_CONFIG);
 * 
 * // or
 * 
 * ClassificationConfig.setTestsConfiguration(ClassificationConfig.CI_TESTS_CONFIG);
 * </pre>
 * 
 * @author Camille Letavernier
 *
 * @see {@link TestCategory}
 * @see {@link ClassificationRunner}
 *
 */
public enum ClassificationConfig implements Set<TestCategory> {

	/**
	 * Default tests configuration for Continuous Integration (Hudson): Excludes the tests which are
	 * identified as failing, as well as the interactive tests
	 *
	 * This configuration may require a couple of hours to run
	 */
	CI_TESTS_CONFIG(NotImplemented, InvalidTest, FailingTest, InteractiveTest, GeneratedTest),

	/**
	 * This tests configuration runs all tests which are already identified as failing.
	 * If one test from this configuration passes, this might mean that the annotation should be removed
	 *
	 * InteractiveTests are excluded as well, as this configuration is supposed to be executed on Hudson
	 */
	FAILING_TESTS_CONFIG(Standard, InteractiveTest, GeneratedTest, ExpensiveTest),

	/**
	 * This tests configuration is expected to run in ~15 minutes. This is useful for quick testing,
	 * and validation through Gerrit
	 */
	LIGTHWEIGHT_TESTS_CONFIG(InteractiveTest, NotImplemented, FailingTest, InvalidTest, ExpensiveTest, GeneratedTest),

	/**
	 * This test configuration runs all generated diagram tests only.
	 * 
	 * @since 1.2
	 */
	GENERATED_TESTS_CONFIG(InteractiveTest, NotImplemented, FailingTest, InvalidTest, ExpensiveTest, Standard),

	/**
	 * This tests configuration is meant to execute all tests in an automated environment
	 * It includes all tests which are known to be failing, but excludes tests which
	 * require a user interaction
	 */
	FULL_CI_TESTS_CONFIG(InteractiveTest),

	/**
	 * Executes all tests
	 */
	FULL_TESTS_CONFIG();

	public static final Set<TestCategory> excludedTestCategories = new HashSet<TestCategory>();

	private final Set<TestCategory> categories;

	static {
		// Default on Hudson: exclude everything which is already identified as an issue (i.e. is not a (new) regression)
		setTestsConfiguration(CI_TESTS_CONFIG);

		// Check whether identified regressions are still failing
		// setIncludedTestCategories(FailingTest);

		for (String arg : Platform.getApplicationArgs()) {
			if (arg.contains("-testConfig=")) {
				String configName = arg.substring("-testConfig=".length());
				Set<TestCategory> testsConfig = ClassificationConfig.valueOf(configName);
				if (testsConfig != null) {
					setTestsConfiguration(testsConfig);
				}
				break;
			}
		}
	}

	private ClassificationConfig(TestCategory... exclusions) {
		Set<TestCategory> categories = Sets.newHashSet(TestCategory.values());
		categories.removeAll(Arrays.asList(exclusions));
		this.categories = ImmutableSet.copyOf(categories);
	}

	// Same as setExcludedTestsCategories, but renamed for clarity (To be used with predefined configurations)
	public static void setTestsConfiguration(Set<TestCategory> predefinedConfiguration) {
		setIncludedTestCategories(predefinedConfiguration);
	}

	/**
	 * Define the test categories which should be excluded from the TestSuite
	 *
	 * @param categoriesToInclude
	 */
	public static void setExcludedTestCategories(TestCategory... categories) {
		setExcludedTestCategories(Arrays.asList(categories));
	}

	/**
	 * Define the test categories which should be included in the TestSuite
	 *
	 * @param categoriesToInclude
	 */
	public static void setIncludedTestCategories(TestCategory... categories) {
		setIncludedTestCategories(Arrays.asList(categories));
	}

	/**
	 * Define the test categories which should be excluded from the TestSuite
	 *
	 * @param categoriesToInclude
	 */
	public static void setExcludedTestCategories(Collection<TestCategory> categoriesToExclude) {
		excludedTestCategories.clear();
		excludedTestCategories.addAll(categoriesToExclude);
	}

	/**
	 * Define the test categories which should be included in the TestSuite
	 *
	 * @param categoriesToInclude
	 */
	public static void setIncludedTestCategories(Collection<TestCategory> categoriesToInclude) {
		excludedTestCategories.clear();
		List<TestCategory> categoriesToExclude = ListHelper.asList(TestCategory.values());
		categoriesToExclude.removeAll(categoriesToInclude);
		excludedTestCategories.addAll(categoriesToExclude);
	}

	/**
	 * Tests whether a method containing the given set of Annotations should be executed
	 *
	 * @param annotations
	 *            The annotations applied to the Method
	 * @return
	 * 		True if the test method should be executed, false if it should be ignored
	 */
	public static boolean shouldRun(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			Class<? extends Annotation> annotationClass = annotation.annotationType();

			// Support the usual @Ignore annotation, too, of course
			if ((annotationClass == Ignore.class) || isExcluded(annotationClass)) {
				return false;
			}
		}

		if (excludedTestCategories.contains(TestCategory.Standard)) {
			// If it's just a test suite, then let the execution of its children be filtered
			for (Annotation annotation : annotations) {
				if (RunWith.class.isInstance(annotation)) {
					RunWith runWith = (RunWith) annotation;
					Class<?> runner = runWith.value();
					if (Suite.class.isAssignableFrom(runner) && !Parameterized.class.isAssignableFrom(runner)) {
						return true;
					}
				}
			}

			for (TestCategory testCategory : TestCategory.values()) {
				if (testCategory == TestCategory.Standard) {
					continue;
				}

				for (Annotation annotation : annotations) {
					if (testCategory.match(annotation.annotationType())) {
						return true;
					}
				}
			}

			return false;
		}

		return true;
	}

	/**
	 * Tests whether the given Annotation should be excluded
	 *
	 * @param annotationClass
	 * @return
	 */
	public static boolean isExcluded(Class<? extends Annotation> annotationClass) {
		for (TestCategory testCategory : excludedTestCategories) {
			if (testCategory.match(annotationClass)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int size() {
		return categories.size();
	}

	@Override
	public boolean isEmpty() {
		return categories.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return categories.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return categories.containsAll(c);
	}

	@Override
	public Iterator<TestCategory> iterator() {
		return categories.iterator();
	}

	@Override
	public Object[] toArray() {
		return categories.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return categories.toArray(a);
	}

	@Override
	public boolean add(TestCategory e) {
		throw new UnsupportedOperationException("add"); //$NON-NLS-1$
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
	}

	@Override
	public boolean addAll(Collection<? extends TestCategory> c) {
		throw new UnsupportedOperationException("addAll"); //$NON-NLS-1$
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll"); //$NON-NLS-1$
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("removeAll"); //$NON-NLS-1$
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear"); //$NON-NLS-1$
	}

}
