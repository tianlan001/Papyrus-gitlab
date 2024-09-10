/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.axis;


import org.eclipse.emf.common.notify.Notification;

import ca.odell.glazedlists.EventList;

/**
 * @author Vincent Lorenzo
 *
 */
public interface IAxisManagerForEventList extends IAxisManager {

	/**
	 *
	 * @param notification
	 */
	public void manageEvent(final Notification notification);

	/**
	 *
	 * @param list
	 *            the event list owning axis element
	 */
	public void setEventList(final EventList<Object> list);

	/**
	 * This method allows to notify the axis manager than the filling configuration has changed
	 */
	public void fillingConfigurationsHaveChanged();
}
