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
 * A specialization of the {@link MasterSlaveSyncBucket} that synchronizes EMF objects.
 */
public abstract class EObjectMasterSlaveSyncBucket<M extends EObject, T, X> extends MasterSlaveSyncBucket<M, T, X> {

	public EObjectMasterSlaveSyncBucket(M model, T master) {
		super(model, master);
	}

	@Override
	public boolean isActive() {
		return getMaster().isActive();
	}

}
