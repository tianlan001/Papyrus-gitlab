/*****************************************************************************
 * Copyright (c) 2018, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.tests;

import org.eclipse.papyrus.infra.architecture.tests.merged.MergedArchitectureDomainTest;
import org.eclipse.papyrus.infra.architecture.tests.merged.MergedArchitectureFacadeTest;
import org.eclipse.papyrus.infra.core.architecture.tests.ArchitectureAllTests;
import org.eclipse.papyrus.infra.core.internal.architecture.merger.tests.ArchitectureDomainInheritanceTest;
import org.eclipse.papyrus.infra.core.internal.architecture.merger.tests.ArchitectureDomainMergerAddContextTest;
import org.eclipse.papyrus.infra.core.internal.architecture.merger.tests.ArchitectureDomainMergerLegacyTest;
import org.eclipse.papyrus.infra.core.internal.architecture.merger.tests.ArchitectureDomainMergerTest;
import org.eclipse.papyrus.infra.core.internal.architecture.merger.tests.ArchitectureDomainMixedMergesTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The master test suite for the plug-in.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ArchitectureAllTests.class,
		MergedArchitectureDomainTest.class,
		MergedArchitectureFacadeTest.class,
		ArchitectureDomainMergerTest.class,
		ArchitectureDomainInheritanceTest.class,
		ArchitectureDomainMergerLegacyTest.class,
		ArchitectureDomainMixedMergesTest.class,
		ArchitectureDomainMergerAddContextTest.class,
})
@Headless
public class AllTests {
	// Nothing required
}
