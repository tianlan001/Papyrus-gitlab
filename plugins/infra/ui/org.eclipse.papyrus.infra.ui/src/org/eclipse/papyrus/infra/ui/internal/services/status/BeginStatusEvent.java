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
 * This is an event send at the end of the execution we want to trace
 *
 */
public class BeginStatusEvent implements StatusServiceEvent {

	/**
	 * The expected maximum number of step
	 */
	private int maxStepNum;

	/**
	 * The message of the task
	 */
	private String message;

	/**
	 * The title of the dialog
	 */
	private String executionTitle;

	/**
	 * Constructor.
	 *
	 * @param executionTitle
	 *            the title of the dialog
	 * @param message
	 *            the message of the task
	 * @param maxStepNum
	 *            the maximum number of step expected in the process
	 */
	public BeginStatusEvent(String executionTitle, String message, int maxStepNum) {
		super();
		this.executionTitle = executionTitle;
		this.maxStepNum = maxStepNum;
		this.message = message;
	}

	/**
	 * @return the executionTitle
	 */
	public String getExecutionTitle() {
		return executionTitle;
	}

	/**
	 * @return the maxStepNum
	 */
	public int getMaxStepNum() {
		return maxStepNum;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
