/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite.DynamicClasses;
import org.eclipse.papyrus.uml.diagram.clazz.test.canonical.AllCanonicalTests;
import org.eclipse.papyrus.uml.diagram.clazz.test.canonical.TestClassDiagram;
import org.eclipse.papyrus.uml.diagram.clazz.test.copyPaste.ConstraintPasteStrategyTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.dnd.DragAndDropAssociationsTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.legacy.PackageDiagramLegacyTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.resources.ClassPaletteTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.resources.ModelValidationTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.tests.Bug382954_InstanceSpecificationLink;
import org.eclipse.papyrus.uml.diagram.clazz.test.tests.Bug476872_MoveCommandTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.tests.Bug481317_MoveGeneralizationTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.tests.MoveContentsTest;
import org.eclipse.papyrus.uml.diagram.clazz.test.tests.RoundedCompartmentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests together.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// canonical
		AllCanonicalTests.class,
		TestClassDiagram.class,
		PackageDiagramLegacyTest.class,
		Bug382954_InstanceSpecificationLink.class,
		ConstraintPasteStrategyTest.class,
		RoundedCompartmentTest.class,
		Bug476872_MoveCommandTest.class,
		Bug481317_MoveGeneralizationTest.class,
		MoveContentsTest.class,
		DragAndDropAssociationsTest.class,
		// load
		// LoadTests.class
		ClassPaletteTest.class,
		ModelValidationTest.class

})
@DynamicClasses("org.eclipse.papyrus.uml.diagram.clazz.test.AllGenTests")
public class AllTests {
}
