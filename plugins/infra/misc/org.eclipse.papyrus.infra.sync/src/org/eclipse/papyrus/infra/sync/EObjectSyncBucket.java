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

package org.eclipse.papyrus.infra.sync;

import org.eclipse.emf.ecore.EObject;

/**
 * A specialization of the {@link SyncBucket} that synchronizes EMF objects.
 */
public abstract class EObjectSyncBucket<M extends EObject, T, X> extends SyncBucket<M, T, X> {

	public EObjectSyncBucket(M model) throws IllegalArgumentException {
		super(model);
	}

}
