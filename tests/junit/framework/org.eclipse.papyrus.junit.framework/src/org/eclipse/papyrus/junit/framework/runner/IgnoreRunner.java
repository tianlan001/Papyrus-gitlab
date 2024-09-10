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

package org.eclipse.papyrus.junit.framework.runner;

import java.util.Iterator;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * A JUnit runner that just ignores all of the leaves of its {@link #getDescription() description} tree.
 */
public class IgnoreRunner extends Runner {
	private final Description description;

	/**
	 * Initializes me with the test suite that I ignore.
	 */
	public IgnoreRunner(Description testSuite) {
		super();

		this.description = testSuite;
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public void run(RunNotifier notifier) {
		for (Iterator<Description> iter = iterator(); iter.hasNext();) {
			Description next = iter.next();
			if (next.isTest()) {
				notifier.fireTestIgnored(next);
			}
		}
	}

	TreeIterator<Description> iterator() {
		return new AbstractTreeIterator<Description>(getDescription()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<? extends Description> getChildren(Object object) {
				return ((Description) object).getChildren().iterator();
			}
		};
	}
}
