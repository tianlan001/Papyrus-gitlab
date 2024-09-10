/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.types.generator.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.profile.types.generator.incremental.tests.BasicElementTypesRegenerationTest;
import org.eclipse.papyrus.uml.profile.types.generator.incremental.tests.SimpleDeltaStrategyTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The master test suite for the plug-in.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		BasicElementTypesGenerationTest.class, DiagramSpecificElementTypesGenerationTest.class,
		ProfilesWithPackageNestingTest.class,
		DiagramSpecificElementTypesGenerationBug461717Test.class,
		BasicElementTypesRegenerationTest.class, SimpleDeltaStrategyTest.class })
public class AllTests {
	// Nothing required
}
