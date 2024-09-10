/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - add support for conditional tests
 *  Christian W. Damus (CEA) - bug 432813
 *  Christian W. Damus (CEA) - bug 434993
 *  Christian W. Damus (CEA) - bug 436047
 *  Christian W. Damus - bug 485156
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.framework.classification;

import java.util.List;

import org.eclipse.papyrus.junit.framework.classification.rules.Conditional;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * A Test Runner which is aware of Classification-related annotations and {@link Conditional @Conditional} tests.
 *
 * It ignores the test methods according to their annotations, and the policy defined
 * in {@link ClassificationConfig}
 *
 *
 * @see {@link ClassificationConfig}
 * @see {@link TestCategory}
 * @see Conditional
 *
 * @author Camille Letavernier
 *
 */
public class ClassificationRunner extends BlockJUnit4ClassRunner {

	private final ClassificationRunnerImpl impl;

	public ClassificationRunner(Class<?> klass) throws InitializationError {
		super(klass);

		this.impl = new ClassificationRunnerImpl(new ClassificationRunnerImpl.Delegate() {

			@Override
			public void runChild(FrameworkMethod method, RunNotifier notifier) {
				ClassificationRunner.super.runChild(method, notifier);
			}

			@Override
			public Description describeChild(FrameworkMethod method) {
				return ClassificationRunner.super.describeChild(method);
			}

			@Override
			public Object createTest() throws Exception {
				return ClassificationRunner.super.createTest();
			}

			@Override
			public List<TestRule> getTestRules(Object target) {
				return ClassificationRunner.super.getTestRules(target);
			}

			@Override
			public Statement classBlock(RunNotifier notifier) {
				return ClassificationRunner.super.classBlock(notifier);
			}

		});
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		impl.runChild(method, notifier);
	}

	@Override
	protected Object createTest() throws Exception {
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
