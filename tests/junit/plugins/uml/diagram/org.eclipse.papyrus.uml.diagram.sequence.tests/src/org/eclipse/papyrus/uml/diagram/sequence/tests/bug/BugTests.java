/*****************************************************************************
 * Copyright (c) 2012, 2018 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 533673, 530201, 536486
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests for bug.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		TestCombinedFragmentKind_364710.class,
		TestCombinedFragmentOperand_364701.class,
		TestMessagesDeletion_364828.class,
		TestNestedCombinedFragment_364795.class,
		TestSynchronousMessageCreation_364827.class,
		/**
		 * TestCombinedFragmentGates_364816.class,
		 * TestCombinedFragmentDeletion_364804.class,
		 */
		TestAdvancedDragDrop_364696.class,
		TestGuardEdition_364808.class,
		CombinedFragmentRegressionTest.class,
		TestCombinedFragmentOperandsLayout.class,
		LifelineCoverageRegressionTest.class,
		TestCFOperandsCoveredNodes.class,
		TestCFOperandsSemanticCoverage.class,
		TestCFOperandsReorder.class,
		TestDurationConstraintDisplay.class,
		TestDurationObservationDisplay.class,
		TestGeneralOrderingDisplay.class,
		DurationConstraintCreationTest.class,
		DurationObservationCreationTest.class,
		GeneralOrderingCreationTest.class,
		TimeElementCreationTest.class,
		TimeElementMoveTest.class,
})
public class BugTests {

}
