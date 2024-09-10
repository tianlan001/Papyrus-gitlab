/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * This class update the current focused nattable model manager in the application.
 *
 * @since 6.1
 */
public class NatTableFocusUtils {

	/** The current focused Nattable Model Manager actually focused. */
	private INattableModelManager nattableManager;

	/** The singleton instance. */
	private static NatTableFocusUtils instance;

	/**
	 * Private constructor.
	 */
	private NatTableFocusUtils() {
		// Do nothing
	}

	/**
	 * @return The singleton instance
	 */
	public static NatTableFocusUtils getInstance() {
		if (null == instance) {
			instance = new NatTableFocusUtils();
		}
		return instance;
	}

	/**
	 * This allows to set the current Nattable Model Manager.
	 *
	 * @param manager
	 *            The current focused Nattable Model Manager
	 */
	public void setCurrentNattableModelManager(final INattableModelManager manager) {
		this.nattableManager = manager;
	}

	/**
	 * @return The current focused Nattable Model Manager
	 */
	public INattableModelManager getCurrentNattableModelManager() {
		return this.nattableManager;
	}
}
