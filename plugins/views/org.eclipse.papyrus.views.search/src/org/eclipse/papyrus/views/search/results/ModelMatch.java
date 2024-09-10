/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.search.results;

import org.eclipse.papyrus.views.search.scope.IScopeEntry;

/**
 *
 * A real result entry in a model
 *
 */
public abstract class ModelMatch extends AbstractResultEntry {

	public ModelMatch(int offset, int lenght, Object source, IScopeEntry scopeEntry) {
		super(offset, lenght, source, scopeEntry);
	}

}
