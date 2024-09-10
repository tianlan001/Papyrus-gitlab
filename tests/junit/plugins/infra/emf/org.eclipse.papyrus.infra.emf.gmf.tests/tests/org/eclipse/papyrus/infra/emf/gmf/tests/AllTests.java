/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.gmf.tests;

import org.eclipse.papyrus.infra.emf.gmf.util.tests.CommandTreeIteratorTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * The test suite for the {@code org.eclipse.papyrus.infra.emf.gmf} plug-in.
 */
@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		CommandTreeIteratorTest.class,
})
public class AllTests {

	public AllTests() {
		super();
	}
}
