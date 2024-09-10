/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.emf.resource.index;

import java.util.EventListener;

/**
 * Listener protocol for notifications of {@link WorkspaceModelIndex} events.
 */
public interface IWorkspaceModelIndexListener extends EventListener {
	/**
	 * Notifies me that the index is about to {@linkplain WorkspaceModelIndexEvent#ABOUT_TO_RECALCULATE re-calculate} changes in some project.
	 * 
	 * @param event
	 *            the event object
	 */
	void indexAboutToRecalculate(WorkspaceModelIndexEvent event);

	/**
	 * Notifies me that the index has finished {@linkplain WorkspaceModelIndexEvent#RECALCULATED re-calculation} of changes in some project.
	 * 
	 * @param event
	 *            the event object
	 */
	void indexRecalculated(WorkspaceModelIndexEvent event);

	/**
	 * Notifies me that the index is about to {@linkplain WorkspaceModelIndexEvent#ABOUT_TO_CALCULATE fully calculate} the index for some project.
	 * 
	 * @param event
	 *            the event object
	 */
	void indexAboutToCalculate(WorkspaceModelIndexEvent event);

	/**
	 * Notifies me that the index has finished {@linkplain WorkspaceModelIndexEvent#CALCULATED full calculation} for some project.
	 * 
	 * @param event
	 *            the event object
	 */
	void indexCalculated(WorkspaceModelIndexEvent event);

	/**
	 * Notifies me that the index {@linkplain WorkspaceModelIndexEvent#FAILED failed} to (re-)calculate some project.
	 * 
	 * @param event
	 *            the event object
	 */
	void indexFailed(WorkspaceModelIndexEvent event);
}
