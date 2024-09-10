/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   J�r�mie TATIBOUET (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({ TestAcceptCallActionPinDerivation.class, TestAcceptEventActionPinDerivation.class, TestAddStructuralFeatureValueActionPinDerivation.class, TestCallBehaviorActionPinDerivation.class, TestCallOperationActionPinDerivation.class,
		TestCreateLinkActionPinDerivation.class, TestCreateObjectActionPinDerivation.class, TestDestroyLinkActionPinDerivation.class, TestReadLinkActionPinDerivation.class, TestReadSelfActionPinDerivation.class,
		TestReadStructuralFeatureActionPinDerivation.class, TestSendSignalActionPinDerivation.class, TestStartClassifierBehaviorActionPinDerivation.class, TestStartObjectBehaviorActionPinDerivation.class, TestTestIdentityActionPinDerivation.class,
		TestValueSpecificationActionPinDerivation.class })





public class AllPinDerivationTests {

}
