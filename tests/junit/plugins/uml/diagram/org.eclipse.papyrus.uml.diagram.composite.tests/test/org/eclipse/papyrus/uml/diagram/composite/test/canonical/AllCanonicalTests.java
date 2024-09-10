/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 470823
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.test.canonical;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test in canonical package
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// test links
		TestCompositeDiagramLinkOwnedBySource.class,
		TestCompositeDiagramElementWithSameParentLink.class,
		TestCompositeDiagramElementWithDifferentParentLink.class,
		TestCompositeDiagramTopNode.class,
		TestCompositeDiagramCompositeChildNode.class,
		TestCompositeDiagramActivityChildNode.class,
		TestCompositeDiagramChildLabel.class,
		TestCompositeDiagramSimpleLink.class,
		TestPortLocation.class,

		// Specific regression tests
		TestCompositeDiagramDropOntoChildNode.class
})
public class AllCanonicalTests {
}
