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

package org.eclipse.papyrus.views.validation.internal.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.services.markerlistener.IPapyrusMarker;
import org.eclipse.ui.ide.IDE;

/**
 * Property tester for instances of the {@link IPapyrusMarker} interface.
 */
public class PapyrusMarkerPropertyTester extends PropertyTester {
	public static final String PROPERTY_HAS_QUICK_FIX = "hasQuickFix"; //$NON-NLS-1$

	public PapyrusMarkerPropertyTester() {
		super();
	}

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		if (PROPERTY_HAS_QUICK_FIX.equals(property)) {
			result = hasQuickFix(receiver) == asBoolean(expectedValue);
		}

		return result;
	}

	Boolean asBoolean(Object expectedValue) {
		// Default expected is true
		return (expectedValue instanceof Boolean) ? (Boolean) expectedValue : Boolean.TRUE;
	}

	Boolean hasQuickFix(Object object) {
		Boolean result = null;

		if (object != null) {
			IMarker marker = AdapterUtils.adapt(object, IMarker.class, null);
			if (marker != null) {
				result = Boolean.valueOf(IDE.getMarkerHelpRegistry().hasResolutions(marker));
			}
		}

		return result;
	}
}
