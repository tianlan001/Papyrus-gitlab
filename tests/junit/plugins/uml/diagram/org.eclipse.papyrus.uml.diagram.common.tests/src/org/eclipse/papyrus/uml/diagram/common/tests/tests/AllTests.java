/*****************************************************************************
 * Copyright (c) 2014, 2017, 2018 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 471954
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 507488
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 518645
    Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.tests.tests;


import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.diagram.common.canonical.tests.CanonicalRegressionTest;
import org.eclipse.papyrus.uml.diagram.common.tests.css.Bug431694_UndoDeleteTest;
import org.eclipse.papyrus.uml.diagram.common.tests.parser.HTMLCleanerTestCase;
import org.eclipse.papyrus.uml.diagram.common.tests.parser.HTMLCornerBentFigureTestCase;
import org.eclipse.papyrus.uml.diagram.common.tests.parser.MultiplicityStringFormatterTestCase;
import org.eclipse.papyrus.uml.diagram.common.tests.parser.ValueSpecificationUtilTestCase;
import org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AppliedStereotypeDisplayCompartmentClassDiagramTest;
import org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AppliedStereotypeDisplayLinkClassDiagramTest;
import org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AppliedStereotypeDisplayNodeClassDiagramTest;
import org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AppliedStereotypeDisplayStructureTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({
		AppliedStereotypeDisplayStructureTest.class,
		AppliedStereotypeDisplayNodeClassDiagramTest.class,
		AppliedStereotypeDisplayCompartmentClassDiagramTest.class,
		AppliedStereotypeDisplayLinkClassDiagramTest.class,
		CommonBendpointsTest.class,
		HTMLCleanerTestCase.class,
		HTMLCornerBentFigureTestCase.class,
		Bug431694_UndoDeleteTest.class,
		Bug418509_ReorientationAssociation.class,
		MultiplicityStringFormatterTestCase.class,
		ValueSpecificationUtilTestCase.class,
		CanonicalRegressionTest.class,
		ShapeCustomisationTest.class,
		Bug476873_MoveCommandTest.class,
		Bug495430_DuplicatedTransitionsTest.class,
		Bug488744_PortPositionTest.class,
		Bug507488_BinaryRelationshipLabelPositionTest.class,
		StereotypePropertyReferenceEdgeTest.class,
		EdgeCustomizationTest.class
})

public class AllTests {
	// JUnit 4 test suite
}
