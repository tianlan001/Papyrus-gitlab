/*****************************************************************************
 * Copyright (c) 2012, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440108
 *  Christian W. Damus - bugs 458197, 468030, 485220, 502461, 508629, 571629
 *  Ansgar Radermacher (CEA LIST) - bug 541686 (duplicated replationships)
 *  Pauline DEVILLE (CEA LIST) - bug 552410
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.tests.tests;


import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.runner.Headless;
import org.eclipse.papyrus.uml.tools.commands.tests.ReorderStereotypeApplicationsCommandTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@Headless
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		ContentProviderTest.class,
		UMLStereotypePropertyContentProviderTest.class,
		DependencyManagementTest.class,
		NamedElementValidatorTest.class,
		StereotypeElementListenerTest.class,
		ProfileApplicationListenerTest.class,
		UMLReferenceConverterTest.class,
		UMLReferenceConverterCompletionTest.class,
		UMLLanguageProvidersTest.class,
		IconsStereotypesTest.class,
		CustomizableDelegatingItemLabelProviderTest.class,
		DelegatingItemLabelProviderTest.class,
		UMLCopyTest.class,
		UMLCopyTestME.class,
		DuplicateStereotypeCommandTest.class,
		ReorderStereotypeApplicationsCommandTest.class,
})
public class AllTests {
	// Test suite
}
