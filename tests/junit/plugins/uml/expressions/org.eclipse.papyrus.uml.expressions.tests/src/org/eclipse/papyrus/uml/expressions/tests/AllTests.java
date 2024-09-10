/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({
		IsStereotypedWithExpressionTests.class,
		HasAppliedStereotypeExpressionTests.class,
		IsKindOfExpressionTest.class,
		IsTypeOfExpressionTests.class,
		IsKindOfStereotypeExpressionTests.class,
		IsTypeOfStereotypeExpressionTests.class,
		SingleStereotypeAttributeEqualityExpressionTests.class
})
/**
 * @author
 *
 */
public class AllTests {
	// JUnit 4 test suite

}
