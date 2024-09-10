/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 525245, 525367, 520602 
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({
		OpenDependencyMatrixTest.class,
		CreateMatrixTableTests.class, 
		UpdateTableAfterEditingRowSourceTest.class,
		UpdateTableAfterEditingColumnSourceTest.class,
		UpdateTableContentsAfterExpressionChangeWithLocalHeaderPreconfiguredTests.class,
		DeleteObjectWrapperForSourceElementsTest.class,
		// Bug 520602 - Columns are not deleted when the semantic element is destroyed from the ModelExplorer
		UpdateMatrixAxisColumnTest.class
})
public class AllTests {
	// JUnit 4 test suite

}
