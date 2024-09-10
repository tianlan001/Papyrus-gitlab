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
 *  Christian W. Damus - bugs 485220, 474467
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.tests;

import org.eclipse.papyrus.infra.ui.emf.providers.tests.DependentEMFLabelProviderTest;
import org.eclipse.papyrus.infra.ui.emf.providers.tests.EMFLabelProviderTest;
import org.eclipse.papyrus.infra.ui.internal.emf.readonly.handlers.ReferencedModelReadOnlyHandlerTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Master test suite.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ReferencedModelReadOnlyHandlerTest.class,
		EMFLabelProviderTest.class,
		DependentEMFLabelProviderTest.class,
})
public class AllTests {

}
