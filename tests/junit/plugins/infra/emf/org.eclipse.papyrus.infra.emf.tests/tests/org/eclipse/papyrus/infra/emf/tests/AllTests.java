/*
 * Copyright (c) 2014, 2021 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 399859, 465416, 485220, 496299, 551740, 572865
 *
 */
package org.eclipse.papyrus.infra.emf.tests;

import org.eclipse.papyrus.infra.emf.advice.ReadOnlyObjectEditAdviceTest;
import org.eclipse.papyrus.infra.emf.edit.domain.PapyrusTransactionalEditingDomainTest;
import org.eclipse.papyrus.infra.emf.resource.CrossReferenceIndexTest;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelperTest;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceLocatorTest;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndexTest;
import org.eclipse.papyrus.infra.emf.utils.InternalCrossReferencerTest;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtilsTest;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceTest;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * The test suite for the {@code org.eclipse.papyrus.infra.emf} plug-in.
 */
@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// oep.infra.emf.advice
		ReadOnlyObjectEditAdviceTest.class,
		// oep.infra.emf.edit.domain
		PapyrusTransactionalEditingDomainTest.class,
		// oep.infra.emf.utils
		InternalCrossReferencerTest.class,
		ServiceUtilsForResourceTest.class,
		ResourceUtilsTest.class,
		// oep.infra.emf.resource
		ShardResourceHelperTest.class, ShardResourceLocatorTest.class, CrossReferenceIndexTest.class,
		// oep.infra.emf.resource.index
		WorkspaceModelIndexTest.class,
})
public class AllTests {
	@ClassRule
	public static Timeout timeout = Timeout.seconds(180);

	public AllTests() {
		super();
	}

	/**
	 * Ensure that the modeled element types are loaded, especially when running only
	 * this suite.
	 */
	@BeforeClass
	public static void initElementTypes() {
		ElementTypeSetConfigurationRegistry.getInstance().getClass();
	}
}
