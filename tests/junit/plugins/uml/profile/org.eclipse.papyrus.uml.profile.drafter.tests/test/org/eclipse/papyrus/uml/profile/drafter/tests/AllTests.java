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
package org.eclipse.papyrus.uml.profile.drafter.tests;


import org.eclipse.papyrus.uml.profile.drafter.ui.model.CompoundTypeProviderTest;
import org.eclipse.papyrus.uml.profile.drafter.ui.model.SimpleModelVisitorTest;
import org.eclipse.papyrus.uml.profile.drafter.ui.model.StereotypeURLTest;
import org.eclipse.papyrus.uml.profile.drafter.ui.model.TypeProviderTest2;
import org.eclipse.papyrus.uml.profile.drafter.utils.UMLPrimitiveTypesModelTest;
import org.eclipse.papyrus.uml.profile.drafter.ui.model.TypeProviderTest;
import org.eclipse.papyrus.uml.profile.drafter.tests.utils.UMLModelCreatorTest;
import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for this fragment
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
	ProfileCatalogTest.class,
	EclipseProjectTest.class, 
	ModelSetManagerTest.class, 
	UMLModelCreatorTest.class,
	CompoundTypeProviderTest.class,
	SimpleModelVisitorTest.class,
	StereotypeURLTest.class,
	TypeProviderTest.class,
	TypeProviderTest2.class,
	UMLPrimitiveTypesModelTest.class
})
public class AllTests {

}
