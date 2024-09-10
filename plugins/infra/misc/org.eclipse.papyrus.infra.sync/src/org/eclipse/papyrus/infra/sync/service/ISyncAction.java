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

package org.eclipse.papyrus.infra.sync.service;

import org.eclipse.core.runtime.IStatus;

/**
 * An action performed on occurrence of a {@linkplain ISyncTrigger synchronization trigger} to configure/modify the synchronization
 * behaviour of some object.
 */
public interface ISyncAction {
	IStatus perform(ISyncService syncService, Object object);
}
