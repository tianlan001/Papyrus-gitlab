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

package org.eclipse.papyrus.infra.gmfdiag.canonical.tests;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the {@code org.eclipse.papyrus.infra.gmfdiag.canonical} plug-in.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		BasicCanonicalClassDiagramTest.class,
		CanonicalStateInClassDiagramTest.class,
		EditingInModelInClassDiagramTest.class,
		CanonicalViewDeletionInClassDiagramTest.class,
		EditingInClassDiagramRegressionTest.class,
		CSSCanonicalClassDiagramTest.class,
		CSSCanonicalStateInClassDiagramTest.class,
		CSSExternalStylesheetInClassDiagramTest.class,
		RegressionTest.class,
})
public class AllTests {

	public AllTests() {
		super();
	}

}
