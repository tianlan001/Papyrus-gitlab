/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.helper;

import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.Trigger;

/**
 * this class contains a util method use for AccepEventAction
 */
public class CustomAcceptEventActionEditHelper {

	/**
	 * Test whether the action may be considered as a time event action
	 *
	 * @param action
	 *            the action to test
	 * @return true if action is a time event action
	 */
	public static boolean isAcceptTimeEventAction(AcceptEventAction action) {
		if (action.getTriggers() != null) {
			boolean hasTimeEvent = false;
			for (Trigger trigger : action.getTriggers()) {
				if (trigger != null) {
					Event event = trigger.getEvent();
					if (event instanceof TimeEvent) {
						hasTimeEvent = true;
					} else {
						return false;
					}
				}
			}
			// only time events have been encountered, but maybe no event at all.
			return hasTimeEvent;
		}
		return false;
	}
}