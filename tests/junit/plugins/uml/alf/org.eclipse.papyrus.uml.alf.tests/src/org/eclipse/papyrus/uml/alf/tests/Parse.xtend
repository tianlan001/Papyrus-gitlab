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
 * MDS - Initial implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.papyrus.uml.alf.UnitDefinition
import org.eclipse.xtext.resource.XtextResourceSet
import java.io.IOException
import org.eclipse.papyrus.uml.alf.AlfStandaloneSetup

class Parse {

  static String testDirectory = "tests"
  static String testFile = "TestClass"

  static protected ResourceSet resourceSet
  
  def static UnitDefinition parseFile(ResourceSet resourceSet, String path) {
    var unit = null as UnitDefinition;
    System.out.println(path + "...")
    val resource = resourceSet.getResource(URI.createFileURI(path), true)
    unit = resource.getContents().get(0) as UnitDefinition
    resource.unload()
    return unit
  }
  
  def static void main(String[] args) {
    AlfStandaloneSetup.doSetup()
    
    resourceSet = new XtextResourceSet()
    
    testDirectory = System.getProperty("test.directory", testDirectory)
    testFile = System.getProperty("test.file", testFile)
    
    if (testFile.endsWith(".alf")) {
      testFile = testFile.substring(0, testFile.length-4)
    }
    
    val path = testDirectory + "/" + testFile
    
    val unit = parseFile(resourceSet, path + ".alf")
    
    if (unit == null) {
      System.out.println("PARSE FAILED")
    } else {
      val resource = resourceSet.createResource(URI.createFileURI(path + ".ecore"));
      resource.getContents().add(unit);
      try {
        resource.save(null)
      } catch (IOException e) {
        System.out.println("SAVE FAILED")
      }
    }
  }
  
}