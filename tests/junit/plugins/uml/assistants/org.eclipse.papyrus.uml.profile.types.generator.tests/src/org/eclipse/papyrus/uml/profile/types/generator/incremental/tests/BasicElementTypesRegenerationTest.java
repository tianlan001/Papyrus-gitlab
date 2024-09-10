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

package org.eclipse.papyrus.uml.profile.types.generator.incremental.tests;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ProjectName;
import org.eclipse.papyrus.uml.profile.types.generator.tests.BasicElementTypesGenerationTest;
import org.eclipse.papyrus.uml.profile.types.generator.tests.GenOption;
import org.eclipse.papyrus.uml.profile.types.generator.tests.GenOptions;

/**
 * Test cases for the basics of element types generation for UML profiles.
 */
@PluginResource({ "/resources/j2ee.profile.uml", "/resources/incremental/j2ee.elementtypesconfigurations" })
@GenOptions(GenOption.INCREMENTAL)
@ProjectName("j2ee")
public class BasicElementTypesRegenerationTest extends BasicElementTypesGenerationTest {
	// All tests are inherited; only the generation options are different. Expected results
	// are the same with standard generation and incremental re-generation
}
