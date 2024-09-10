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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.tests;

import org.eclipse.papyrus.toolsmiths.profilemigration.tests.automatic.AutomaticMigrationTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.AddPropertyToStereotypeTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.ChangeIsAbstractFromStereotypeTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.ChangeIsStaticFromPropertyTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.ChangeMultiplicityFromPropertyMigratorTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.DeleteEnumLiteralFromEnumerationTest;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators.MoveMigratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests for Profile migration tool
 */
@RunWith(Suite.class)
@SuiteClasses({ AutomaticMigrationTest.class,
		ChangeIsAbstractFromStereotypeTest.class,
		MoveMigratorTest.class,
		ChangeMultiplicityFromPropertyMigratorTest.class,
		DeleteEnumLiteralFromEnumerationTest.class,
		ChangeIsStaticFromPropertyTest.class,
		AddPropertyToStereotypeTest.class
})
public class AllTests {

}
