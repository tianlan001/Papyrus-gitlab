/*****************************************************************************
 * Copyright (c) 2010, 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 568782
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * All tests for this fragment
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ElementTypesRegistryTests.class,
		ElementEditHelperAdviceTests.class,
		TypesConfigurationsAllTests.class,
})
public class AllTests {

}
