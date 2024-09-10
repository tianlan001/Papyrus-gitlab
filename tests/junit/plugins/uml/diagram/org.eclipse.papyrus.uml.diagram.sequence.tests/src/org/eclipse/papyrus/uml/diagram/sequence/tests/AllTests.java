/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440284
 *  Christian W. Damus - bug 464647
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite.DynamicClasses;
import org.eclipse.papyrus.junit.utils.rules.HideViewRule;
import org.eclipse.papyrus.uml.diagram.sequence.tests.bug.BugTests;
import org.eclipse.papyrus.uml.diagram.sequence.tests.bug.BugTests2;
import org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7.BugTest_m7;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.AllCanonicalTests;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests together.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		AllCanonicalTests.class,
		BugTests.class,
		BugTests2.class,
		BugTest_m7.class,
		LifelineXYLayoutEditPolicyTest.class,
})
@DynamicClasses("org.eclipse.papyrus.uml.diagram.sequence.test.AllGenTests")
public class AllTests {

	@ClassRule
	public static final HideViewRule hideOutline = new HideViewRule("org.eclipse.ui.views.ContentOutline");

}
