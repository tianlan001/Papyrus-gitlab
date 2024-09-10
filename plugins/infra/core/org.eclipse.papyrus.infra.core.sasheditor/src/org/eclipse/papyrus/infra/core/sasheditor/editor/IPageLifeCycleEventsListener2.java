/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.editor;

/**
 * Propagate more events from the {@link IPage} lifecycle.
 * 
 * @author cedric dumoulin
 *
 */
public interface IPageLifeCycleEventsListener2 extends IPageLifeCycleEventsListener {

	/**
	 * The specified {@link IPage} has received a firePropertyChange event. Relay it.
	 * 
	 * @param page
	 *        The page firing the event.
	 * @param propertyId
	 * 		  The ID of the page property that has changed
	 */
	public void pageFirePropertyChange(IPage page, int propertyId);

}
