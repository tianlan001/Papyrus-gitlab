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
 * Patrick Tessier (CEA LIST) - Initial API and implementation
 * Christian W. Damus - bug 464647
 * Christian W. Damus - bug 468207
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.statemachine.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.tests.ZoneTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite.DynamicClasses;
import org.eclipse.papyrus.uml.diagram.statemachine.tests.canonical.AllCanonicalTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests together.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// canonical
		AllCanonicalTests.class,
		RoundedCompartmentTest.class,
		// load
		// LoadTests.class,
ZoneTest.class,
})
@DynamicClasses("org.eclipse.papyrus.uml.diagram.statemachine.test.AllGenTests")
public class AllTests {
}
