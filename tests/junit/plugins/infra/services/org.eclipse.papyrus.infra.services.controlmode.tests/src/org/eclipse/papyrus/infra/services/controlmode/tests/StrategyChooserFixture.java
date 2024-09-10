/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.infra.services.controlmode.tests;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.services.resourceloading.IStrategyChooser;


/**
 * An {@link IStrategyChooser} fixture for the control-mode tests that ensures a normal control-mode strategy
 * is restored after completion for predictable/correct resource loading behaviour in the execution of
 * subsequent (and unrelated) tests.
 */
public class StrategyChooserFixture {

	private final int strategyToRestore;
	private IStrategyChooser chooser;

	public StrategyChooserFixture(ServicesRegistry serviceRegistry, int choose) {
		super();

		try {
			chooser = serviceRegistry.getService(IStrategyChooser.class);
			strategyToRestore = chooser.getCurrentStrategy();
			chooser.setStrategy(choose);
		} catch (ServiceException e) {
			throw new IllegalStateException("IStrategyChooser service is not available", e);
		}
	}

	public void dispose() {
		if (chooser != null) {
			chooser.setStrategy(strategyToRestore);
			chooser = null;
		}
	}

}
