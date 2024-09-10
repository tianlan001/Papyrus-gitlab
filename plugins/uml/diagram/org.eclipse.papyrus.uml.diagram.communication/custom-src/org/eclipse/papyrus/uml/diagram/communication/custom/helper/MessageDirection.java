/*****************************************************************************
 * Copyright (c) 2010 CEA
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
 *   Atos Origin - Initial API and implementation
 *  Saadia DHOUIB (CEA LIST) saadia.dhouib@cea.fr - adapted from sequence diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.helper;


/**
 * Enum for message direction (in or out)
 */
public enum MessageDirection {
	/**
	 * in direction, out direction
	 */
	IN("in"), //$NON-NLS-1$
	OUT("out");

	private String name;

	private MessageDirection(String name) {
		this.name = name;
	}

	/**
	 * Return the name of message direction
	 *
	 * @return The name
	 */
	public String getName() {
		return name;
	}
}
