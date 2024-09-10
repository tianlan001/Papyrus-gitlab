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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.tests;

import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel1.Strategy0TestModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel1.Strategy1TestModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel1.Strategy2TestModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy0TestModel2WithModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy0TestModel2WithPackage0;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy1TestModel2WithModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy1TestModel2WithPackage0;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy2TestModel2WithModel1;
import org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2.Strategy2TestModel2WithPackage0;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * All tests together.
 */
@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// testModel1
		Strategy1TestModel1.class,
		Strategy2TestModel1.class,
		Strategy0TestModel1.class,
		// testModel2
		Strategy0TestModel2WithModel1.class,
		Strategy0TestModel2WithPackage0.class,
		Strategy1TestModel2WithModel1.class,
		Strategy1TestModel2WithPackage0.class,
		Strategy2TestModel2WithModel1.class,
		Strategy2TestModel2WithPackage0.class
})
public class AllTests {
	// Test suite
}