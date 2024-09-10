/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA LIST) - add test for AdapterUtils
 *  Christian W. Damus (CEA) - bugs 402525, 422257, 399859
 *  Christian W. Damus - bugs 456934, 468030, 482949, 485220, 488558, 498140, 528343
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.tests;

import org.eclipse.papyrus.infra.core.language.LanguageServiceTest;
import org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResourceTest;
import org.eclipse.papyrus.infra.core.resource.ModelSetTest;
import org.eclipse.papyrus.infra.core.resource.NestingTransactionalCommandStackTest;
import org.eclipse.papyrus.infra.core.resource.PapyrusProjectScopeTest;
import org.eclipse.papyrus.infra.core.resource.ResourceAdapterTest;
import org.eclipse.papyrus.infra.core.resource.ResourceAdapterTransactionalTest;
import org.eclipse.papyrus.infra.core.services.ComposedServiceTest;
import org.eclipse.papyrus.infra.core.services.ServicesRegistryTest;
import org.eclipse.papyrus.infra.core.services.SharedServiceFactoryTest;
import org.eclipse.papyrus.infra.core.utils.AdapterUtilsTest;
import org.eclipse.papyrus.infra.core.utils.JobBasedFutureTest;
import org.eclipse.papyrus.infra.core.utils.JobExecutorServiceTest;
import org.eclipse.papyrus.infra.core.utils.TransactionHelperTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;



@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// {oep.resource}
		ModelSetTest.class, AbstractModelWithSharedResourceTest.class, NestingTransactionalCommandStackTest.class,
		ResourceAdapterTest.class, ResourceAdapterTransactionalTest.class,
		PapyrusProjectScopeTest.class,
		// {oep}.core.language
		LanguageServiceTest.class,
		// {oep}.core.services
		ComposedServiceTest.class, ServicesRegistryTest.class, SharedServiceFactoryTest.class,
		// {oep}.core.utils
		AdapterUtilsTest.class, JobBasedFutureTest.class, JobExecutorServiceTest.class,
		TransactionHelperTest.class,
})
/**
 * Suite Class for all tests in the plugin
 */
public class AllTests {
	// Junit 4 Test suite
}
