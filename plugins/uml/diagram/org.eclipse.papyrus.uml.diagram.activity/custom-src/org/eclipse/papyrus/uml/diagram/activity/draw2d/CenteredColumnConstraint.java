/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
package org.eclipse.papyrus.uml.diagram.activity.draw2d;

/**
 * Constraint to use with {@link CenteredColumnLayout}. Specifies whether figure
 * fills extra space
 */
public class CenteredColumnConstraint {

	private boolean fill;

	/**
	 * Constructor.
	 *
	 * @param fillAvailableSpace
	 *            whether available space is filled by this figure
	 */
	public CenteredColumnConstraint(boolean fillAvailableSpace) {
		fill = fillAvailableSpace;
	}

	public boolean isAvailableSpaceFilled() {
		return fill;
	}
}
