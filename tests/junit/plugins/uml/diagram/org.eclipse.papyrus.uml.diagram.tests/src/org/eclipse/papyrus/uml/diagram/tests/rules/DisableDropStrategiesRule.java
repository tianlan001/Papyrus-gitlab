/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.diagram.tests.rules;

import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.DropStrategyManager;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A JUnit rule that suppresses all pluggable drop strategies for the duration
 * of the test(s) that it decorates.
 */
public class DisableDropStrategiesRule extends TestWatcher {
	private boolean restore = true;

	public DisableDropStrategiesRule() {
		super();
	}

	@Override
	protected void starting(Description description) {
		restore = DropStrategyManager.instance.isEnabled();
		DropStrategyManager.instance.setEnabled(false);
	}

	@Override
	protected void finished(Description description) {
		DropStrategyManager.instance.setEnabled(restore);
	}
}
