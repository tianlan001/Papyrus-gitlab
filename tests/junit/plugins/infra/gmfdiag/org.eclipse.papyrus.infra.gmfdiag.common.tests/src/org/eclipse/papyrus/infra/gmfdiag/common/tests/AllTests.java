/*
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus (CEA) - initial API and implementation
 *  Christian W. Damus - bugs 465416, 474467, 507618, 508404
 */
package org.eclipse.papyrus.infra.gmfdiag.common.tests;

import org.eclipse.papyrus.infra.gmfdiag.common.commands.tests.CreateEditBasedElementCommandTest;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.tests.DefaultCopyCommandTest;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.tests.DefaultDiagramCopyCommandTest;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.tests.DefaultDiagramPasteCommandTest;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.tests.DefaultPasteCommandTest;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.tests.NotationLabelProviderTest;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.tests.SyncTests;
import org.eclipse.papyrus.infra.gmfdiag.common.tests.resources.ModelValidationTest;
import org.eclipse.papyrus.infra.gmfdiag.common.tests.resources.TestModelValidationTest;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.GMFUnsafeTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Master test suite for this test fragment.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		LoadExpansionModel.class,
		ExpansionAddCompartment.class,
		ExpansionAddChildLabel.class,
		ExpansionAddBorderItem.class,
		ExpansionAddCompartmentWithKind.class,
		ExpansionAddLink.class,
		ExpansionDropElements.class,
		AssistantUsage.class,
		GMFUnsafeTest.class,
		SyncTests.class,
		NotationLabelProviderTest.class,
		CreateEditBasedElementCommandTest.class,
		DefaultCopyCommandTest.class,
		DefaultPasteCommandTest.class,
		DefaultDiagramCopyCommandTest.class,
		DefaultDiagramPasteCommandTest.class,
		ModelValidationTest.class,
		TestModelValidationTest.class,
})
public class AllTests {

}
