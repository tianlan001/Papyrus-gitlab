/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - Extension to validation test suite
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.validation.tests;


import org.eclipse.papyrus.uml.validation.tests.rules.TestOCLConstraintOnInstanceSpec;
import org.eclipse.papyrus.uml.validation.tests.rules.TestOCLValidationRuleFromFile;
import org.eclipse.papyrus.uml.validation.tests.rules.TestStereotypeValidation;
import org.eclipse.papyrus.uml.validation.tests.rules.TestUMLValidationRules;
import org.eclipse.papyrus.uml.validation.tests.rules.TestValidationRulesInPluginXML;
import org.eclipse.papyrus.uml.validation.tests.rules.TestValidationRulesInProfile;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for validation rules. See suite classes below
 */
@RunWith(Suite.class)
@SuiteClasses({
	
	/**
	 * Model with OCL constraints on class, verified in instance specification
	 * Model with OCL constraints on redefined attribute of a class.
	 */
	TestOCLConstraintOnInstanceSpec.class,

	/**
	 * Verify that custom OCL file contributes to validation, see bug 408215* Sample test for cut in model explorer
	 */
	TestOCLValidationRuleFromFile.class,

	/**
	 * Model with OCL and Java constraints in plugin.xml (EMFv)
	 */
	TestValidationRulesInPluginXML.class,

	/**
	 * Model with OCL constraints embedded in profile 
	 */
	TestValidationRulesInProfile.class,		

	/**
	 * Model with UML warning (identifiers of same name in package),
	 * Model/profile. Model applies a stereotype, but does not set the value of a required stereotype attribute
	 * Model with non-integer multiplicity on attribute (0..MAX)
	 */
	TestUMLValidationRules.class,

	/**
	 * Model/profile. Model applies a stereotype, but does not set the value of a required stereotype attribute. 
	 */
	TestStereotypeValidation.class
})
public class AllTests {
	// JUnit for test suite
}
