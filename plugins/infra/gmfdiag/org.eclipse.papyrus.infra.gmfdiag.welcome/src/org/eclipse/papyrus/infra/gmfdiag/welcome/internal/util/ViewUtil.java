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

package org.eclipse.papyrus.infra.gmfdiag.welcome.internal.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * Static utilities for working with {@link EObject}s that are notation views
 * described by {@code ViewPrototype}s.
 */
public class ViewUtil {

	private ViewUtil() {
		super();
	}

	/**
	 * Obtains the context of a notation {@code view} (diagram, table, whatever).
	 * 
	 * @param view
	 *            a notation view
	 * @return its context, or {@code null} if none can be determined
	 */
	public static EObject getContext(EObject view) {
		return ViewPrototype.get(view).getRootOf(view);
	}

}
