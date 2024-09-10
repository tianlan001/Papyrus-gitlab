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
 * IJI - Initial implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests.mapper

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.papyrus.uml.alf.ActiveClassDefinition
import org.eclipse.papyrus.uml.alf.ActivityDefinition
import org.eclipse.papyrus.uml.alf.AssociationDefinition
import org.eclipse.papyrus.uml.alf.ClassDefinition
import org.eclipse.papyrus.uml.alf.DataTypeDefinition
import org.eclipse.papyrus.uml.alf.EnumerationDefinition
import org.eclipse.papyrus.uml.alf.MappingError
import org.eclipse.papyrus.uml.alf.PackageDefinition
import org.eclipse.papyrus.uml.alf.SignalDefinition
import org.eclipse.papyrus.uml.alf.UnitDefinition
import org.eclipse.papyrus.uml.alf.tests.ParserTest
import org.eclipse.papyrus.uml.alf.validation.ModelNamespaceFacade
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.junit.Ignore

@RunWith(XtextRunner)
class MergeTest extends ParserTest {

  static final public String TEST_DIRECTORY = "./tests"
  static final public String UML_DIRECTORY = System.getProperty("user.dir") + "/UML"

  static protected AlfMapper mapper;
  static protected String testDirectory;

  @BeforeClass
  static def void setUp() {  
    mapper = new AlfMapper()
    testDirectory = System.getProperty("test.directory", TEST_DIRECTORY)
  }

  @Test
  @Ignore("Doesn't run on Maven - Bug 464026")
  def void testMerging() {
    var failures = 0;
    System.out.println("[MergeTest] Directory " + testDirectory + ":")
    val directory = new File(testDirectory)
    for (file : directory.listFiles()) {
      val name = file.name;
      val l = name.length
      if (name.substring(l - 4, l).equals(".alf")) {
        System.out.print(name + ": ")
        val resource = mapper.getResource(file.path)
        val modelNamespace = ModelNamespaceFacade.instance.createEmptyValidationContext(resource);
        val parseFailures = parseResource(resource, false)
        if (parseFailures > 0) {
          failures = failures + parseFailures
        } else {
          try {
            val contents = resource.getContents();
            val unit = contents.get(0) as UnitDefinition;
            val contextElement = this.mapContext(unit);
            contextElement.name = unit.definition.actualName();
            (modelNamespace.contextNamespace as Package).packagedElements.clear();
            (modelNamespace.contextNamespace as Package).packagedElements.add(contextElement);
            mapper.map(contextElement, contents)
            modelNamespace.contextNamespace.eResource.save(
              new FileOutputStream(UML_DIRECTORY + "/" + name.substring(0, l - 4) + ".uml"), 
              null
            );
          } catch (MappingError e) {
            val status = BasicDiagnostic.toIStatus(e.diagnostic)
            System.out.println("  " + status)
            failures = failures + 1
            
          } catch (IOException e) {
            e.printStackTrace()
          }
        }
        ModelNamespaceFacade.instance.deleteValidationContext(resource);
        resource.unload()
      }
    }
    System.out.println()
    assertEquals(0, failures)
  }
  
  def PackageableElement mapContext(UnitDefinition unit) {
    val definition = unit.definition
    if (definition instanceof PackageDefinition) {
      return UMLFactory.eINSTANCE.createPackage()
    } else if (definition instanceof ClassDefinition ||
           definition instanceof ActiveClassDefinition) {
      return UMLFactory.eINSTANCE.createClass()
    } else if (definition instanceof DataTypeDefinition) {
      return UMLFactory.eINSTANCE.createDataType()
    } else if (definition instanceof EnumerationDefinition) {
      return UMLFactory.eINSTANCE.createEnumeration()
    } else if (definition instanceof AssociationDefinition) {
      return UMLFactory.eINSTANCE.createAssociation()
    } else if (definition instanceof SignalDefinition) {
      return UMLFactory.eINSTANCE.createSignal()
    } else if (definition instanceof ActivityDefinition) {
      return UMLFactory.eINSTANCE.createActivity()
    } else {
      return null
    }
  }  

}
