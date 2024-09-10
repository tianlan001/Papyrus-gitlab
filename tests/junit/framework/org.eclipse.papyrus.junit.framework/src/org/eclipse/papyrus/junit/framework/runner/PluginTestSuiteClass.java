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
 *  Christian W. Damus - bug 451230
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.runner;


/**
 * Entry for a test suite in a test plugin
 */
public class PluginTestSuiteClass implements ITestSuiteClass {

	/** main test suite class */
	private final Class<?> testClass;

	/**
	 * Constructor.
	 *
	 * @param testClass
	 *        the main test suite class
	 */
	public PluginTestSuiteClass(Class<?> testClass) {
		this.testClass = testClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getMainTestSuiteClass() {
		return testClass;
	}

	@Override
	public String toString() {
		return String.format("PluginTestSuite %s", testClass);
	}

}
