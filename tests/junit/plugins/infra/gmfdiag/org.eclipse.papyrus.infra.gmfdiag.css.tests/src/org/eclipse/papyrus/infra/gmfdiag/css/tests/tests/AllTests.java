/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 461595
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(ClassificationSuite.class)
@SuiteClasses({
		CSSClassProviderTest.class,
		CSSStylesheetTest.class,
		CSSModelStylesheetTest.class,
		CSSNamedStyleTest.class,
		CSSCompartmentsTests.class,
		CSSSupportTest.class,
		CSSProjectStylesheetsTest.class,
		CSSWorkspaceThemeTest.class,
		InferredNotationStylesTest.class,
		CSSNamedElementsTest.class,
		CSSKindSelectorTest.class
})
public class AllTests {
	// Test suite
}
