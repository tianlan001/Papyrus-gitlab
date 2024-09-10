/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.properties.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CanonicalObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.ConnectionDecorationStyleObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CustomBooleanStyleWithStoreObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CustomDoubleStyleWithStoreObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CustomIntStyleCompartmentObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CustomIntStyleWithStoreObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.CustomStringStyleCompartmentObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.DiagramLabelObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.RulersUnitStyleObservableValueTest;
import org.eclipse.papyrus.uml.diagram.properties.databinding.tests.SwitchOrientationObservableValueTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the UML Diagram Properties bundle.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// Tests for diagram observable values
		CanonicalObservableValueTest.class,
		ConnectionDecorationStyleObservableValueTest.class,
		CustomStringStyleCompartmentObservableValueTest.class,
		CustomIntStyleCompartmentObservableValueTest.class,
		DiagramLabelObservableValueTest.class,
		CustomBooleanStyleWithStoreObservableValueTest.class,
		CustomDoubleStyleWithStoreObservableValueTest.class,
		CustomIntStyleWithStoreObservableValueTest.class,
		RulersUnitStyleObservableValueTest.class,

		// Test for activity diagram specific observable value
		SwitchOrientationObservableValueTest.class,
})
public class AllTests {
	// Everything is specified in class annotations
}
