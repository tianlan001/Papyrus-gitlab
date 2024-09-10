/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Jeremie Tatibouet (CEA LIST)
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests;

import org.eclipse.papyrus.uml.alf.tests.generator.GenerationTest;
import org.eclipse.papyrus.uml.alf.tests.mapper.MappingTest;
import org.eclipse.papyrus.uml.alf.tests.mapper.MergeTest;
import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({MappingTest.class, MergeTest.class, SemanticTest.class, 
	SingleTest.class, SyntacticTest.class, GenerationTest.class})
public class AllTests {

	public AllTests() {}

}
