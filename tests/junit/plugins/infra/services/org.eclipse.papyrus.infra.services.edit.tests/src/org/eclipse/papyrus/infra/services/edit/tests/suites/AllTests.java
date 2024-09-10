/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 
 *   Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *   Christian W. Damus (CEA) - bug 431618
 *   Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.tests.suites;

import org.eclipse.papyrus.infra.services.edit.tests.commands.TestConfigureFeatureCommandFactory;
import org.eclipse.papyrus.infra.services.edit.tests.commands.TestConfigureFeatureListCommandFactory;
import org.eclipse.papyrus.infra.services.edit.tests.context.TestClientContext;
import org.eclipse.papyrus.infra.services.edit.tests.edit.advice.MarkerDeletionAdviceTest;
import org.eclipse.papyrus.infra.services.edit.tests.service.TestElementEditService;
import org.eclipse.papyrus.infra.services.edit.tests.service.TestElementEditServiceProvider;
import org.eclipse.papyrus.infra.services.edit.tests.service.TestElementEditServiceProviderFactory;
import org.eclipse.papyrus.infra.services.edit.tests.service.TestElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.tests.utils.TestGMFCommandUtils;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Main Test suite.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		TestClientContext.class,
		TestElementEditService.class,
		TestElementEditServiceProvider.class,
		TestElementEditServiceProviderFactory.class,
		TestElementEditServiceUtils.class,
		TestGMFCommandUtils.class,
		TestConfigureFeatureCommandFactory.class,
		TestConfigureFeatureListCommandFactory.class,
		MarkerDeletionAdviceTest.class
})
public class AllTests {

}
