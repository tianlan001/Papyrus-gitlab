/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus - bug 399859
 *  Christian W. Damus - bug 468030
 *  Christian W. Damus - bug 481302
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.decoratormodel.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.decoratormodel.helper.tests.DecoratorModelUtilsTest;
import org.eclipse.papyrus.uml.decoratormodel.helper.tests.PapyrusStereotypeApplicationHelperTest;
import org.eclipse.papyrus.uml.decoratormodel.internal.expressions.tests.FilePropertyTesterTest;
import org.eclipse.papyrus.uml.decoratormodel.internal.resource.index.tests.ProfileIndexTest;
import org.eclipse.papyrus.uml.decoratormodel.internal.resource.tests.DecoratorModelCopierTest;
import org.eclipse.papyrus.uml.decoratormodel.internal.resource.tests.DecoratorModelIndexTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Main tests suite for this plugin
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ExternalizeProfileApplicationTest.class, InternalizeProfileApplicationTest.class, //
		ProfileMigrationTest.class, StereotypeApplicationRepairTest.class, //
		DecoratorModelIndexTest.class, DecoratorModelCopierTest.class, //
		DecoratorModelUtilsTest.class, PapyrusStereotypeApplicationHelperTest.class, //
		FilePropertyTesterTest.class,
		ProfileIndexTest.class,
		RegressionTest.class,
})
public class AllTests {
	// Test suite
}
