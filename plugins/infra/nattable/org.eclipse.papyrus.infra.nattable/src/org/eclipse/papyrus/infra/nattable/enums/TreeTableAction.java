/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.enums;

/**
 * Enumeration that is used to configure the action for a tree table.
 */
public enum TreeTableAction {
	COLLAPSE, EXPAND, NONE;

	/**
	 * The list of values for this enum type.
	 */
	public static final TreeTableAction[] VALUES = { COLLAPSE, EXPAND, NONE };

	/**
	 * @return The default table action
	 */
	public static TreeTableAction getDefaultTableAction() {
		return NONE;
	}

	/**
	 * Get the corresponding action from a given action text.
	 *
	 * @param actionText
	 *            The action text
	 * @return The relevant action or <code>null</code<> if not found
	 */
	public static TreeTableAction getAction(final String actionText) {
		TreeTableAction action = null;

		if (null != actionText && !actionText.isEmpty()) {
			if (actionText.equals(COLLAPSE.toString())) {
				action = COLLAPSE;
			} else if (actionText.equals(EXPAND.toString())) {
				action = EXPAND;
			} else if (actionText.equals(NONE.toString())) {
				action = NONE;
			}
		}

		return action;
	}
}
