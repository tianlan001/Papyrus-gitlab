/*****************************************************************************
 * Copyright (c) 2009 CEA
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.util;

/**
 * Enum for message direction (in or out)
 */
public enum MessageDirection {

	IN("in"), OUT("out");

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
