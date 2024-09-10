/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.deployment.test.canonical;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test in canonical package
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
// Top Node
TestDeploymentDiagramTopNode.class,
// Child Node
TestDeploymentDiagramForPackageChildNode.class,
TestDeploymentDiagramForNodeChildNode.class,
// Deployment Link
TestDeploymentDiagramDeploymentLink.class,
// Link by owned source 
TestDeploymentDiagramLinkOwnedBySource.class,
// Comment link
TestDeploymentDiagramCommentLink.class,
// Cosntraint link
TestDeploymentDiagramConstraintLink.class,
// Manifestation Link
TestDeploymentDiagramManifestationLink.class,
// Others Link
TestDeploymentDiagramLink.class,
//
TestSemantics.class
// End
})
public class AllCanonicalTests {

}
