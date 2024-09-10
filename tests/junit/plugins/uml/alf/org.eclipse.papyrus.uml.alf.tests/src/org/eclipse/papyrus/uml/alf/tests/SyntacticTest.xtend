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
 * IJI - Initial implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.ocl.ecore.delegate.OCLDelegateDomain
import org.eclipse.ocl.pivot.model.OCLstdlib
import org.eclipse.ocl.uml.OCL
import org.eclipse.papyrus.uml.alf.AlfInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.junit.Ignore
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.papyrus.uml.alf.validation.ModelNamespaceFacade

@InjectWith(AlfInjectorProvider)
@RunWith(XtextRunner)
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SyntacticTest extends ParserTest {

  static final public String TEST_DIRECTORY = "./tests"
  
  static protected ResourceSet resourceSet
  static protected String testDirectory;
  
  @BeforeClass
  static def void setUp() {
    resourceSet = new XtextResourceSet()
    
    OCL.initialize(resourceSet);
    OCLstdlib.install();
    OCLDelegateDomain.initialize(resourceSet)

    testDirectory = System.getProperty("test.directory", TEST_DIRECTORY)
  }
  
  @Test
  @Ignore("Doesn't run on Maven - Bug 464026")
  def void testSyntax() {
  	System.out.print("[SyntacticTest] ")
    val failures = parseDirectory(resourceSet, testDirectory, false);
    assertEquals(0, failures)
  }
  
  override parseResource(Resource resource, boolean validate) {
	ModelNamespaceFacade.instance.createEmptyValidationContext(resource);
	val failures = super.parseResource(resource, validate);
	ModelNamespaceFacade.instance.deleteValidationContext(resource);
	return failures;
  }
  
  @AfterClass
  static def void cleanUp() {
    resourceSet = null;
  }
  
}
