/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.junit.framework.classification;

import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.junit.runners.model.InitializationError;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * Factory for classification-sensitive parameterized test suites.
 * Specify this factory in the {@literal @}{@link UseParametersRunnerFactory}
 * annotation on your <tt>{@literal @}{@link RunWith}({@link Parameterized}.class)</tt>
 * test class to support the classfication and condition annotations of the Papyrus
 * test framework.
 * 
 * @see Parameterized
 * @see UseParametersRunnerFactory
 * @since 1.2
 */
public class ClassificationRunnerWithParametersFactory implements ParametersRunnerFactory {

	public ClassificationRunnerWithParametersFactory() {
		super();
	}

	@Override
	public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
		return new ClassificationRunnerWithParameters(test);
	}
}
