/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *   Bug 366159 - [ActivityDiagram] Activity Diagram should be able to handle correctly Interruptible Edge
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.request;

import org.eclipse.gef.Request;

/**
 * Create a request related to Interruptible Edge.
 * For now this request is created but and throw to editPart but there is no edit part which used.
 * The aim is to have for the future a mechanism in place in order to add specific EditPart mechanism
 *
 * @author arthur daussy
 */
public class InterruptibleEdgeRequest extends Request {

	/**
	 * Request type to set an activity edge as an interruptible edge
	 */
	public static String SET_INTERRUPTIBLE_EDGE = "set_interruptible_edge";

	/**
	 * Request type to unset an activity edge as an interruptible edge
	 */
	public static String UNSET_INTERRUPTIBLE_EDGE = "unset_interruptible_edge";

	@Override
	public void setType(Object type) {
		if (!SET_INTERRUPTIBLE_EDGE.equals(type) && !UNSET_INTERRUPTIBLE_EDGE.equals(type)) {
			setType(UNSET_INTERRUPTIBLE_EDGE);
		}
		super.setType(type);
	}
}
