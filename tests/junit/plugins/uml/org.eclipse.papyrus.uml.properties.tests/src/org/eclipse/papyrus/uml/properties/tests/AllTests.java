/*****************************************************************************
 * Copyright (c) 2018-2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.tests;

import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.eclipse.papyrus.uml.properties.databinding.tests.AnnotationObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.AppliedCommentsObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.BasicObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.BasicObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ElementCustomizationElementIconObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ElementCustomizationQualifiedNameObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ElementCustomizationShadowObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ExtensionEndMultiplicityObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ExtensionRequiredObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ImageExpressionObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ImageKindObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ImageNameObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ImportedPackageLocationObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.KeywordObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.MaskValueObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.MultiplicityObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.NavigationObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.OwnedCommentsObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.OwnerObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ProfileApplicationObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.ProvidedInterfaceObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.RequiredInterfaceObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.StereotypeApplicationObservableListTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.UMLLabelObservableValueTest;
import org.eclipse.papyrus.uml.properties.databinding.tests.UnsettableStringObservableValueTest;
import org.eclipse.papyrus.uml.properties.modelelement.tests.UMLModelElementTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the UML Properties bundle.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		UMLModelElementTest.class,
		
		// Tests for observable values
		MultiplicityObservableValueTest.class,
		ExtensionEndMultiplicityObservableValueTest.class,
		NavigationObservableValueTest.class,
		OwnerObservableValueTest.class,
		KeywordObservableValueTest.class,
		UMLLabelObservableValueTest.class,
		ImageExpressionObservableValueTest.class,
		ImageKindObservableValueTest.class,
		ImageNameObservableValueTest.class,
		ElementCustomizationElementIconObservableValueTest.class,
		ElementCustomizationQualifiedNameObservableValueTest.class,
		ElementCustomizationShadowObservableValueTest.class,
		ExtensionRequiredObservableValueTest.class,
		ImportedPackageLocationObservableValueTest.class,
		UnsettableStringObservableValueTest.class,
		BasicObservableValueTest.class,
		AnnotationObservableValueTest.class,
		
		// Tests for observable lists
		MaskValueObservableListTest.class,
		AppliedCommentsObservableListTest.class,
		OwnedCommentsObservableListTest.class,
		ProvidedInterfaceObservableListTest.class,
		RequiredInterfaceObservableListTest.class,
		ProfileApplicationObservableListTest.class,
		StereotypeApplicationObservableListTest.class,
		BasicObservableListTest.class,
		
})
public class AllTests {
	// Everything is specified in class annotations
}
