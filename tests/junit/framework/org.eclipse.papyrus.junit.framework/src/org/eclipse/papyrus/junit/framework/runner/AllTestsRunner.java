/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 402525, 323802, 431953, 433310, 434993
 *  Christian W. Damus - bug 399859
 *  Christian W. Damus - bug 451230
 *
 *****************************************************************************/

package org.eclipse.papyrus.junit.framework.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * Finds and runs tests.
 */
public class AllTestsRunner extends Suite {

	/**
	 * Constructor.
	 *
	 * @param clazz
	 *            the suite class � AllTests2
	 * @throws InitializationError
	 *             if there's a problem
	 * @throws org.junit.runners.model.InitializationError
	 */
	public AllTestsRunner(final Class<?> clazz) throws InitializationError {
		super(clazz, getSuites(clazz));
	}

	/**
	 * Returns the list of test classes
	 *
	 * @return the list of test classes
	 */
	static Class<?>[] getSuites(final Class<?> clazz) {
		// retrieve all test suites.
		final Collection<Class<?>> suites = new ArrayList<Class<?>>();

		TestClass testClass = new TestClass(clazz);
		List<ITestSuiteClass> suiteClassSpecs = new ArrayList<ITestSuiteClass>();
		List<FrameworkField> suiteSpots = testClass.getAnnotatedFields(SuiteSpot.class);
		for (FrameworkField spot : suiteSpots) {
			if (spot.isStatic()) {
				try {
					Object value = spot.get(null);
					Iterable<?> listValue;
					if (value instanceof Object[]) {
						listValue = Arrays.asList((Object[]) value);
					} else if (value instanceof Iterable<?>) {
						listValue = (Iterable<?>) value;
					} else {
						listValue = Collections.emptyList();
					}
					for (Object next : listValue) {
						if (next instanceof ITestSuiteClass) {
							suiteClassSpecs.add((ITestSuiteClass) next);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (suiteClassSpecs.isEmpty()) {
			System.err.println("No suite classes specified in class " + clazz.getName());
		} else {
			for (final ITestSuiteClass testSuiteClass : suiteClassSpecs) {
				final Class<?> class_ = testSuiteClass.getMainTestSuiteClass();
				if (class_ != null) {
					suites.add(class_);
				} else {
					System.err.println(testSuiteClass + " does not give a correct test suite class");
				}
			}
		}
		return suites.toArray(new Class<?>[] {});
	}
}
