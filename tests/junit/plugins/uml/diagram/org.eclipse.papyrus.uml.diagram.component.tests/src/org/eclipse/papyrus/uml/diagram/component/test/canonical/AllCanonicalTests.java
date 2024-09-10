/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 450921
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.test.canonical;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test in canonical package
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		TestComponentDiagramTopNode.class,
		TestComponentDiagramPackageChildNode.class,
		TestComponentDiagramComponentChildNode.class,
		TestComponentDiagramChildLabel.class,
		TestComponentDiagramLink.class,
		TestComponentDiagramLinkOwnedBySource.class,
		TestComponentDiagramCommentLink.class,
		TestComponentDiagramConstraintLink.class,
		TestListCompartmentNodeChildDuplicates.class,
		TestListCompartmentIllegalElements.class,
		TestListCompartmentPropertiesOperationsDrop.class,
		TestComponentDiagramConnector.class,
		TestComponentDiagramPortLink.class,
		TestPortLocation.class, 
		TestComponentDiagramConnector.class
})
public class AllCanonicalTests {

}
