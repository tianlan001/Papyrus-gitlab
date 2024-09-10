/*****************************************************************************
 * Copyright (c) 2017, 2019 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({
		AndExpressionTests.class,
		LiteralFalseExpressionTests.class,
		LiteralTrueExpressionTests.class,
		NotExpressionTests.class,
		OrExpressionTests.class,
		ReferenceExpressionTests.class,
		SingleEAttributeValueEqualityExpressionTests.class
})


public class AllTests {
	// JUnit 4 test suite

}
