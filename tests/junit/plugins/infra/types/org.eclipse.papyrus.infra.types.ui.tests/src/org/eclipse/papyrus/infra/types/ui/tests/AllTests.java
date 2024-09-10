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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 485220, 571561
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.ui.tests;

import org.eclipse.papyrus.infra.types.core.internal.ui.handlers.tests.RuleRefactoringHandlerTest;
import org.eclipse.papyrus.infra.types.tests.ElementTypesRegistryTests;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * All tests for this fragment
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ElementTypesRegistryTests.class,
		RuleRefactoringHandlerTest.class,
})
public class AllTests {

}
