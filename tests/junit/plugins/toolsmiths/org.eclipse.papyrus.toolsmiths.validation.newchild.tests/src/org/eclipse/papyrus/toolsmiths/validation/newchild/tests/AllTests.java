/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.toolsmiths.validation.newchild.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests for Architecture Domain Model plug-in validation.
 */
@RunWith(Suite.class)
@SuiteClasses({
		NewChildPluginBuilderTest.class,
		NewChildPluginXMLBuilderTest.class,
		NewChildModelBuilderTest.class,
		NewChildDependenciesBuilderTest.class,
		NewChildBuildPropertiesBuilderTest.class,
})
public class AllTests {
	// Everything is specified in annotations
}
