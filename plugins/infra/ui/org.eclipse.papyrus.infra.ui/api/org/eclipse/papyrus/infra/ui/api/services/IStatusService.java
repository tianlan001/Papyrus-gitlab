/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.api.services;

/**
 * This OSGI service should be use to display a dialog with ProgressBar and trace the evolution of an operation
 *
 * @since 3.2
 */
public interface IStatusService {

	/**
	 * Trigger and event for example (begin or end event)
	 *
	 * @param event
	 *            an event reflecting the operation evolution
	 */
	public void trigger(StatusServiceEvent event);

}
