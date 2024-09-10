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
 * Ed Seidewitz (IJI) - Initial implementation
 * Ed Seidewitz (MDS) - Updated for Luna
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests

import java.io.File
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.papyrus.uml.alf.UnitDefinition
import org.eclipse.papyrus.uml.alf.CachingDiagnostician
import org.eclipse.emf.ecore.EValidator

abstract class ParserTest {

  def int parseDirectory(ResourceSet resourceSet, String directoryPath, boolean validate) {
      var failures = 0;
      System.out.println("Directory " + directoryPath + ":")
      val directory = new File(directoryPath)
      for (file : directory.listFiles()) {
        failures = failures + this.parseFile(resourceSet, file, validate)
      }
      System.out.println()
      return failures
  }

  def int parseFile(ResourceSet resourceSet, File file, boolean validate) {
    var failures = 0;
    val name = file.name;
    val l = name.length
    if (name.substring(l - 4, l).equals(".alf")) {
      System.out.print(name + ": ")
      val resource = resourceSet.getResource(URI.createFileURI(file.path), true)
      failures = parseResource(resource, validate)
      resource.unload()
    }
    return failures
  }
  
  def int parseResource(Resource resource, boolean validate) {
    var failures = 0
    val unit = resource.getContents().get(0) as UnitDefinition
    if (unit == null) {
      System.out.println("PARSE FAILED")
      failures = failures + 1
    } else {
      val definition = unit.definition
      if (definition == null) {
        System.out.println("NO DEFINITION")
        failures = failures + 1
      } else {
        System.out.println(definition.class.simpleName + " " + definition.actualName);
        val errors = resource.getErrors()
        if (errors.size() > 0) {
          failures = failures + 1
          System.out.println("SYNTACTIC ERRORS:");
          for (error: errors) {
            System.out.println(error)
          }
          System.out.println();
        } else if (validate) {
          val diagnostic = new CachingDiagnostician(EValidator.Registry.INSTANCE).validate(unit)
          if (diagnostic.severity == Diagnostic.ERROR) {
            failures = failures + 1;
            System.out.println("SEMANTIC ERRORS:")
            for (child: diagnostic.children) {
              System.out.println(child.class.simpleName + ": " + child.message);
              var exception = child.exception;
              if (exception != null) {
                var cause = exception.cause
                while (cause != null && cause != exception) {
                  System.out.println("Caused by: " + cause.class.name)
                  System.out.println(cause.message)
                  exception = cause;
                  cause = exception.cause;
                }
                System.out.println()            
              }
            }
            System.out.println();
          }
        }
      }
    }
    return failures
  }
  
}
