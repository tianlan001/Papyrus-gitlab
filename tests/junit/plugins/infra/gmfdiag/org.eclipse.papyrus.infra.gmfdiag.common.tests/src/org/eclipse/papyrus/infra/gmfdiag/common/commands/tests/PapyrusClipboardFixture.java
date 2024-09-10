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

package org.eclipse.papyrus.infra.gmfdiag.common.commands.tests;

import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A specialization of the {@link PapyrusClipboard} that installs itself as the
 * shared clipboard for the duration of a test.
 */
public class PapyrusClipboardFixture extends PapyrusClipboard<Object> implements TestRule {

	private static final long serialVersionUID = 1L;

	public PapyrusClipboardFixture() {
		super();
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				PapyrusClipboard<Object> oldInstance = PapyrusClipboard.getInstance();
				PapyrusClipboard.setInstance(PapyrusClipboardFixture.this);

				try {
					base.evaluate();
				} finally {
					PapyrusClipboard.setInstance(oldInstance);
				}
			}
		};
	}

}
