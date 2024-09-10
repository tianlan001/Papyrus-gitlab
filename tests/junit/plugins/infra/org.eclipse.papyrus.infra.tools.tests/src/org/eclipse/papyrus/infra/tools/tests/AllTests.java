/*
 * Copyright (c) 2014, 2021 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 485220, 539694
 *
 */
package org.eclipse.papyrus.infra.tools.tests;

import org.eclipse.papyrus.infra.tools.databinding.AllDataBindingTests;
import org.eclipse.papyrus.infra.tools.util.ClasspathHelperTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * The master test suite for the plug-in.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		AllDataBindingTests.class,
		ClasspathHelperTest.class,
})
@Headless
public class AllTests {

	public AllTests() {
		super();
	}

}
