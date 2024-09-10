/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
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
package org.eclipse.papyrus.uml.alf.tests.mapper

import java.io.File
import java.io.IOException
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.papyrus.uml.alf.MappingError
import org.eclipse.papyrus.uml.alf.tests.ParserTest
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.junit.Ignore
import org.eclipse.papyrus.uml.alf.validation.ModelNamespaceFacade

@RunWith(XtextRunner)
class MappingTest extends ParserTest {

  static final public String TEST_DIRECTORY = "./tests"
  static final public String UML_DIRECTORY = System.getProperty("user.dir") + "/UML"

  static protected AlfMapper mapper;
  static protected String testDirectory;
  static protected String testFile;

  @BeforeClass
  static def void setUp() {
    mapper = new AlfMapper()
    testDirectory = System.getProperty("test.directory", TEST_DIRECTORY)
    testFile = System.getProperty("test.file");
  }

  @Test
  @Ignore("Doesn't run on Maven - Bug 464026")
  def void testMapping() {
    var failures = 0
    var File[] files
    if (testFile == null) {
	    System.out.println("[MappingTest] Directory " + testDirectory + ":")
	    files = new File(testDirectory).listFiles()
    } else {
    	System.out.println("[MappingTest] File " + testDirectory + "/" + testFile + ":")
    	files = #{new File(testDirectory + "/" + testFile)}
    }
    for (file : files) {
      val name = file.name;
      val l = name.length
      if (name.substring(l - 4, l).equals(".alf")) {
        System.out.print(name + ": ")
        val resource = mapper.getResource(file.path)
        ModelNamespaceFacade.instance.createEmptyValidationContext(resource);
        val parseFailures = parseResource(resource, true)
        if (parseFailures > 0) {
          failures = failures + parseFailures
        } else {
          try {
            val uml = mapper.map(resource.getContents())
            mapper.save(UML_DIRECTORY + "/" + name.substring(0, l - 4) + ".uml", uml)
            
          } catch (MappingError e) {
            val status = BasicDiagnostic.toIStatus(e.diagnostic)
            System.out.println("  " + status)
            failures = failures + 1
            
          } catch (IOException e) {
            e.printStackTrace()
          }
          
          catch (Exception e) {
            e.printStackTrace()
            failures = failures + 1
          }
        }
        ModelNamespaceFacade.instance.deleteValidationContext(resource);
        resource.unload()
      }
    }
    System.out.println()
    assertEquals(0, failures)
  }

}
