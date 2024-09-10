/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 451230, 488791
 *  
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.runner;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;


/**
 * Test Suite class entry for a test fragment.
 * 
 * @deprecated As of 2.0, All test suites should be plug-in bundles, not fragment bundles.
 */
@Deprecated
public class FragmentTestSuiteClass implements ITestSuiteClass {

	/** unique identifier of the bundle host */
	private final String hostBundleId;

	/** qualified name of the test suite class */
	private final String classQualifiedName;

	/**
	 * Constructor.
	 *
	 * @param hostBundleId
	 *            unique identifier of the bundle host
	 * @param classQualifiedName
	 *            qualified name of the test suite class
	 */
	public FragmentTestSuiteClass(String hostBundleId, String classQualifiedName) {
		this.hostBundleId = hostBundleId;
		this.classQualifiedName = classQualifiedName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getMainTestSuiteClass() {
		Bundle bundle = Platform.getBundle(hostBundleId);
		if (bundle == null) {
			System.err.println("Impossible to find bundle: " + hostBundleId);
		} else {
			try {
				Class<?> class_ = bundle.loadClass(classQualifiedName);
				return class_;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("FragmentTestSuite %s/%s", hostBundleId, classQualifiedName);
	}

}
