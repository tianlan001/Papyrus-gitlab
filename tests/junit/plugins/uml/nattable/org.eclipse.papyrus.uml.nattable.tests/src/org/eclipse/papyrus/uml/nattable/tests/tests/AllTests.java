/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.tests.tests;


import org.eclipse.papyrus.uml.nattable.tests.bugs.DeleteRowElementTest;
import org.eclipse.papyrus.uml.nattable.tests.bugs.MoveElementInSynchronizedTableTest;
import org.eclipse.papyrus.uml.nattable.tests.tests.configs.GenericTreeTableConfigurationTest;
import org.eclipse.papyrus.uml.nattable.tests.tests.configs.GenericTreeTableNattableConfigurationTest;
import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({ UMLRestrictedContentProviderTest.class, UMLRestrictedContentProviderTestCustomProfile.class,
	GenericTreeTableConfigurationTest.class,
	GenericTreeTableNattableConfigurationTest.class,
	
	// Delete row elements
	DeleteRowElementTest.class,
	
	// Bug 495312: Move Elements,
	MoveElementInSynchronizedTableTest.class,
	
	// Tests of the UMLTableUtils methods
	UMLTableUtilsTest.class
})
public class AllTests {

}
