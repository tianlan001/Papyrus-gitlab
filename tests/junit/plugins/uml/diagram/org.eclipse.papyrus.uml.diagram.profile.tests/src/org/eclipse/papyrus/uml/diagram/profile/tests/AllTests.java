/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *   Christian W. Damus - bug 451613
 *   Christian W. Damus - bug 464647
 *  
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite.DynamicClasses;
import org.eclipse.papyrus.uml.diagram.profile.custom.commands.tests.AllCustomCommandTests;
import org.eclipse.papyrus.uml.diagram.profile.tests.canonical.AllCanonicalTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(ClassificationSuite.class)
@SuiteClasses({
		AllCustomCommandTests.class,
		AllCanonicalTests.class,
})
@DynamicClasses("org.eclipse.papyrus.uml.diagram.profile.tests.AllGenTests")
public class AllTests {
	// Test suite
}
