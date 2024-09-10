/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bugs 485220, 498140
 *
 */
package org.eclipse.papyrus.infra.ui.tests;

import org.eclipse.papyrus.infra.ui.contentoutline.NestedEditorDelegatedOutlinePageTest;
import org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProviderTest;
import org.eclipse.papyrus.infra.ui.lifecycleevents.SaveAndDirtyServiceTest;
import org.eclipse.papyrus.infra.ui.providers.DelegatingPapyrusContentProviderTest;
import org.eclipse.papyrus.infra.ui.providers.SemanticContentProviderFactoryTest;
import org.eclipse.papyrus.infra.ui.util.TransactionUIHelperTest;
import org.eclipse.papyrus.infra.ui.util.UIUtilTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * The master test suite for the plug-in.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({ UIUtilTest.class,
		TransactionUIHelperTest.class,
		SaveAndDirtyServiceTest.class,
		LifeCycleEventsProviderTest.class,
		NestedEditorDelegatedOutlinePageTest.class,
		DelegatingPapyrusContentProviderTest.class,
		SemanticContentProviderFactoryTest.class,
})
public class AllTests {

	public AllTests() {
		super();
	}

}
