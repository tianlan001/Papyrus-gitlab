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
 *   Arthur Daussy Bug 366159 - [ActivityDiagram] Activity Diagram should be able to handle correctly Interruptible Edge
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.part.interfaces;

import org.eclipse.gmf.runtime.notation.Node;

/**
 * Interfaces that need to be implemented by all activity edges
 *
 * @author arthur daussy
 *
 */
public interface InterruptibleEdge {

	/**
	 * @return return the visual ID of the edit which should hold the InterruptibleEdge icon
	 */
	public String getInterruptibleEdgeIconVisualID();

	/**
	 * Get the Interruptible edge icon or null if not set
	 *
	 * @return
	 */
	public Node getInterrutibleEgdeIcon();
}
