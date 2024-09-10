/*****************************************************************************
 * Copyright (c) 2013 CEA
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Soyatec - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Jin Liu (jin.liu@soyatec.com)
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		TestExecutionSpecificationPosition_395462.class,
		TestGuardVisibility_402966.class,
		TestInteractionUse.class,
		TestMessageCreateWithLifeline_403134.class,
		TestResizeStateInvariant_395774.class,
		TestMessageOccurrenceSpecification_402975.class,
		TestMakeSameHeightForLifelines_402978.class,
		TestMoveMessageLostFound_403138.class,
		TestMessageOccurrenceSpecification_477463.class,
		TestWeakReferences.class,
		Test_528787.class,
		Test_528473.class
})
public class BugTest_m7 {
}
