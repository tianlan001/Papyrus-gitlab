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
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test in canonical package
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
// top nodes
TestClassDiagramTopNode.class,
// child nodes
TestClassDiagramChildNode.class,
//labelNodes
TestClassDiagramChildLabel.class,
//test links
TestClassDiagramLink.class,
//test links owned by source
TestClassDiagramLinkOwnedBySource.class,
// multilinks
TestMultiLink.class,
//containmentLink
TestClassDiagramContainmentLink.class,
//contextLink
TestClassDiagramContextLink.class,
//test the order for the drop
TestDropfunction.class,
//test is a static operation is underlined
TestStaticFeatureRepresentation.class,
//test nested classifier
TestClassDiagramNestedClassifier.class,
//List Compartment child duplicates
TestListCompartmentNodesChildDuplicates.class,
//List Compartment illegal elements
TestListCompartmentIllegalElements.class,
TestListCompartmentPropertiesOperationsDrop.class,
TestEditableClassDiagramTopNode.class,
TestClassDiagramAssociationClass.class,
TestListCompartmentNestedChild.class,
TestClassDiagramAssociationLinkSemantic.class,
TestClassDiagramInstanceSpecification.class,
TestRedefinableTemplateSignature.class
})
public class AllCanonicalTests {
}
