/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 402525, 323802, 431953, 433310, 434993
 *  Christian W. Damus - bugs 399859, 451230, 433206, 463156, 474610, 469188, 485220, 488791, 496598, 508629, 573885
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.suite.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.junit.framework.runner.AllTestsRunner;
import org.eclipse.papyrus.junit.framework.runner.ITestSuiteClass;
import org.eclipse.papyrus.junit.framework.runner.PluginTestSuiteClass;
import org.eclipse.papyrus.junit.framework.runner.SuiteSpot;
import org.junit.runner.RunWith;


/**
 * Test class for all tests for Papyrus
 */
@RunWith(AllTestsRunner.class)
public class AllTests {

	@SuiteSpot
	public static final List<ITestSuiteClass> suiteClasses;

	/** list of classes to launch */
	static {
		suiteClasses = new ArrayList<>();

		/* **************** plugins *********************** */

		/* uml */
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.service.types.tests.suites.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.service.types.ui.tests.suites.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.modelexplorer.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.diagram.dnd.tests.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.tools.tests.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.tools.utils.tests.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.diagram.stereotypeproperty.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.diagram.wizards.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.modelrepair.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.properties.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.diagram.properties.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.profile.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.decoratormodel.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.decoratormodel.controlmode.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.validation.tests.AllTests.class));

		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.filters.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.profile.assistants.generator.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.profile.types.generator.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.profile.drafter.tests.AllTests.class));

		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.internationalization.tests.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.alf.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.expressions.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.architecture.tests.AllTests.class));
		suiteClasses.add(new PluginTestSuiteClass(org.eclipse.papyrus.uml.types.core.tests.AllTests.class));

		// end
	}

}
