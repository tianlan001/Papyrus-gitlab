/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * All test in canonical package
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
// child nodes
TestActivityDiagramChildNode.class, 
// TestActivityDiagramChildWithOtherCreationNode.class, 
TestActivityChildNodeInStructuredActivity.class,
TestActivityGroup.class,
TestDropObjects.class,
TestCommentLink.class,
TestSemanticContainerFeature.class,
TestLinks.class,
TestControlNodesInStructuredActivityNodes.class,
TestItemProviderCommand.class
})
public class AllCanonicalTests {

}
