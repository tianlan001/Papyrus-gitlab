/*****************************************************************************
 * Copyright (c) 2012, 2020 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 458685, 468071, 465899, 478314, 485220, 568853
 *   Vincent Lorenzo - bug 492522
 *   Martin Fleck - bug 510268
 *   Francois Le Fevre - bug 477724
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.suites;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.service.types.tests.creation.ConnectorReadOnlyTestBug465899;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationCompositeDirectedTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationCompositeTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationDirectedTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationSharedDirectedTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationSharedTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreateAssociationTest;
import org.eclipse.papyrus.uml.service.types.tests.creation.CreatePureUMLElementTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeleteAssociationPropertyTypeTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeleteCommentLinkTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeleteContainmentSubsetTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeleteDependentWithStereotypeApplications458685;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeletePureUMLElementTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.DeleteTransitionsWithVertexTest;
import org.eclipse.papyrus.uml.service.types.tests.deletion.InteractionDeletionTest;
import org.eclipse.papyrus.uml.service.types.tests.registry.StereotypeMatcherAdviceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Main Test suite.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// TestElementTypeRegistryContent.class,
		CreatePureUMLElementTest.class, // pure uml tests, only element edit service
		DeletePureUMLElementTest.class,
		DeleteDependentWithStereotypeApplications458685.class,
		DeleteTransitionsWithVertexTest.class,
		DeleteContainmentSubsetTest.class,
		ConnectorReadOnlyTestBug465899.class,
		DeleteAssociationPropertyTypeTest.class,
		DeleteCommentLinkTest.class,
		CreateAssociationTest.class,
		CreateAssociationDirectedTest.class,
		CreateAssociationCompositeTest.class,
		CreateAssociationCompositeDirectedTest.class,
		CreateAssociationSharedTest.class,
		CreateAssociationSharedDirectedTest.class,
		InteractionDeletionTest.class,
		StereotypeMatcherAdviceTest.class,
})
public class AllTests {
	// JUnit 4 Test Suite
}
