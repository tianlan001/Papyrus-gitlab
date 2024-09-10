/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL 
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.tests;

import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.DiSashModelMngrTest;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.DiContentProvider2Test;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.DiContentProviderTest;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageManagerImplTest;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.TransactionalDiContentProvider2Test;
import org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query.SashModelQueryTest;
import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for this fragment
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
	// package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider
	DiSashModelMngrTest.class,
	// package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal
	DiContentProviderTest.class, 
	DiContentProvider2Test.class, 
	TransactionalDiContentProvider2Test.class,
	PageManagerImplTest.class,
	// package org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query
	SashModelQueryTest.class
})
public class AllTests {

}
