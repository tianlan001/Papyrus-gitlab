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

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * The test suite for data-bindings API.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		DelegatingObservableValueTest.class,
		DelegatingObservableSetTest.class,
		DelegatingObservableListTest.class,
		WritableListWithIteratorTest.class,
		WritableListWithIteratorContainmentTest.class,
})
public class AllDataBindingTests {

	private AllDataBindingTests() {
		super();
	}

}
