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

package org.eclipse.papyrus.infra.emf.internal.resource.index;

/**
 * Empty implementation of the {@link IIndexManagerListener} protocol.
 */
public class IndexManagerAdapter implements IIndexManagerListener {

	@Override
	public void indexingStarting(IndexManager manager) {
		// Pass
	}

	@Override
	public void indexingFinished(IndexManager manager) {
		// Pass
	}

	@Override
	public void indexingPaused(IndexManager manager) {
		// Pass
	}

	@Override
	public void indexingResumed(IndexManager manager) {
		// Pass
	}

}
