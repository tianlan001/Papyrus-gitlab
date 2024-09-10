/*****************************************************************************
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
 *  Christian W. Damus (CEA) - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.readonly.tests;

import org.eclipse.papyrus.infra.emf.readonly.PapyrusROTransactionalEditingDomainTest;
import org.eclipse.papyrus.infra.emf.readonly.ReadOnlyManagerTest;
import org.eclipse.papyrus.infra.emf.readonly.ReadOnlyTesterTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Master test suite.
 */
@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		PapyrusROTransactionalEditingDomainTest.class, ReadOnlyManagerTest.class, ReadOnlyTesterTest.class
})
public class AllTests {

}
