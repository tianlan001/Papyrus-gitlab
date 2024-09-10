/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 458685, 468071, 465899, 478314, 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.ui.tests.suites;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.CreateElementTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.CreateProfileRelationshipTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.CreateRelationshipTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.MoveElementTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.SetFeatureTest;
import org.eclipse.papyrus.uml.service.types.ui.tests.deletion.DeleteAssociationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Main Test suite.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// TestElementTypeRegistryContent.class,
		CreateElementTest.class,
		CreateRelationshipTest.class,
		CreateProfileRelationshipTest.class,
		MoveElementTest.class,
		SetFeatureTest.class,
		DeleteAssociationTest.class,
})
public class AllTests {
	// JUnit 4 Test Suite
}
