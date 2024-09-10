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

package org.eclipse.papyrus.infra.ui.internal.services.status;

import org.eclipse.papyrus.infra.ui.api.services.StatusServiceEvent;

/**
 * This is an event send during an execution to trace a step of it
 */
public class StepStatusEvent implements StatusServiceEvent {

	private String message;

	/**
	 *
	 * Constructor.
	 *
	 * @param message
	 *            the massage of the step
	 */
	public StepStatusEvent(String message) {
		super();
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
