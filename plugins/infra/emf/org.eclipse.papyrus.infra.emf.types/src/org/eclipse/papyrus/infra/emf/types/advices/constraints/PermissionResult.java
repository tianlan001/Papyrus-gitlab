/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.advices.constraints;

/**
 * @author damus
 *
 */
public enum PermissionResult {
	/** No permission is determined. */
	NONE,
	/** Permission is granted. */
	GRANTED,
	/** Permission is denied. */
	DENIED;

	public boolean isDetermined() {
		return this != NONE;
	}

	public boolean isGranted() {
		return this == GRANTED;
	}

	public boolean isDenied() {
		return this == DENIED;
	}

	public static PermissionResult of(boolean value) {
		return value ? GRANTED : DENIED;
	}

}
