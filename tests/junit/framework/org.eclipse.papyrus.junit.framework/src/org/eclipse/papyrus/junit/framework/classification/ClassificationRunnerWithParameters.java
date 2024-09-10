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
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.classification;

import java.util.List;

import org.eclipse.papyrus.junit.framework.classification.rules.Conditional;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * A Test Runner which is aware of Classification-related annotations and {@link Conditional @Conditional} tests,
 * for use with test {@link Parameters}.
 *
 * It ignores the test methods according to their annotations, and the policy defined
 * in {@link ClassificationConfig}.
 *
 * @see Parameterized
 * @see UseParametersRunnerFactory
 * @see ClassificationRunnerWithParametersFactory
 * @see ClassificationConfig
 * @see TestCategory
 * @see Conditional
 *
 */
public class ClassificationRunnerWithParameters extends BlockJUnit4ClassRunnerWithParameters {

	private final ClassificationRunnerImpl impl;

	public ClassificationRunnerWithParameters(TestWithParameters test) throws InitializationError {
		super(test);

		this.impl = new ClassificationRunnerImpl(new ClassificationRunnerImpl.Delegate() {

			@Override
			public void runChild(FrameworkMethod method, RunNotifier notifier) {
				ClassificationRunnerWithParameters.super.runChild(method, notifier);
			}

			@Override
			public Description describeChild(FrameworkMethod method) {
				return ClassificationRunnerWithParameters.super.describeChild(method);
			}

			@Override
			public Object createTest() throws Exception {
				return ClassificationRunnerWithParameters.super.createTest();
			}

			@Override
			public List<TestRule> getTestRules(Object target) {
				return ClassificationRunnerWithParameters.super.getTestRules(target);
			}

			@Override
			public Statement classBlock(RunNotifier notifier) {
				return ClassificationRunnerWithParameters.super.classBlock(notifier);
			}

		});
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		impl.runChild(method, notifier);
	}

	@Override
	public Object createTest() throws Exception {
		return impl.createTest();
	}

	@Override
	protected List<TestRule> getTestRules(Object target) {
		return impl.getTestRules(target);
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		return impl.classBlock(notifier);
	}

}
