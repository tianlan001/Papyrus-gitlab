/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.architecture.internal.operations;

import org.eclipse.papyrus.infra.core.architecture.ADElement;

/**
 * Externalization of custom (non-generatable) {@link ADElement} method implementations
 * for reuse in the CDO implementation of the model.
 */
public class ADElementOperations {

	public static String getQualifiedName(ADElement self) {
		String qualifiedName = self.getName();
		ADElement parent = (ADElement) self.eContainer();
		while (parent != null) {
			qualifiedName = parent.getName() + "::" + qualifiedName; //$NON-NLS-1$
			parent = (ADElement) parent.eContainer();
		}
		return qualifiedName;
	}

}
