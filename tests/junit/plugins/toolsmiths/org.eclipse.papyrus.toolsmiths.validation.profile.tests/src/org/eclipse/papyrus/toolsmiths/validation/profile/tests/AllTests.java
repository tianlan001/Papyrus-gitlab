/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bugs 573886, 572676
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.profile.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests for Profile plug-in validation
 */
@RunWith(Suite.class)
@SuiteClasses({ ProfilePluginValidationTest.class,
		ProfilePluginXMLBuilderTest.class,
		ProfileModelBuilderTest.class,
})
public class AllTests {

}
