/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.internal;

/**
 * Minimal API for schedulable tasks
 *
 * @author Camille Letavernier
 *
 */
public interface Schedulable {
	/**
	 *
	 * @return true if the task is complete
	 */
	public boolean isComplete();

	/**
	 *
	 * @return the label of the tasks
	 */
	public String getName();

	/**
	 * Starts the task. The implementation should start in a separate thread (e.g. via a Job)
	 */
	public void start();

	/**
	 * Requests the task to cancel. The task may not be canceled immediately; invoker should wait
	 * for isComplete() to return true after invoking this method
	 */
	public void cancel();

}
