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
package org.eclipse.papyrus.infra.ui.internal.emf.expressions;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * A provider of the same properties as the core tester, for selections.
 */
public class ReadOnlyTester extends PropertyTester {

	private final PropertyTester delegate = new org.eclipse.papyrus.infra.emf.readonly.ReadOnlyTester();

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		if (receiver instanceof IStructuredSelection) {
			Iterator<?> objects = ((IStructuredSelection) receiver).iterator();

			result = delegate.test(objects, property, args, expectedValue);
		}

		return result;
	}
}
