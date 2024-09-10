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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.test.load;

import org.junit.runner.RunWith;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests together.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
// 10 Component
Load10ComponentTest.class,
//100 Component
Load100ComponentTest.class,
//200 Component
Load200ComponentTest.class,
//300 Component
Load300ComponentTest.class,
//400 Component
Load400ComponentTest.class,
//500 Component
Load500ComponentTest.class,
//1000 Component
Load1000ComponentTest.class, })
public class LoadTests {

}
