/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker;

/**
 * This allows to validate a papyrus plug-in.
 */
public class PluginValidation {

	/**
	 * The plug-in checkers to validate a papyrus plug-in.
	 */
	private List<IPluginChecker> pluginCheckers;

	/**
	 * Default constructor.
	 */
	public PluginValidation() {
		this.pluginCheckers = new ArrayList<>();
	}

	/**
	 * Constructor.
	 *
	 * @param pluginCheckers
	 *            The plug-in checkers.
	 */
	public PluginValidation(final List<IPluginChecker> pluginCheckers) {
		this.pluginCheckers = new ArrayList<>(pluginCheckers);
	}

	/**
	 * This allows to add a plug-in checker.
	 *
	 * @param pluginChecker
	 *            The plug-in checker to add.
	 */
	public void addPluginChecker(final IPluginChecker pluginChecker) {
		this.pluginCheckers.add(pluginChecker);
	}

	/**
	 * This allows to remove a plug-in checker
	 *
	 * @param pluginChecker
	 *            The plug-in checker to remove.
	 * @return Result of the remove : <code>true</code> if done successfully, <code>false</code> otherwise.
	 */
	public boolean removePluginChecker(final IPluginChecker pluginChecker) {
		return this.pluginCheckers.remove(pluginChecker);
	}

	/**
	 * This allows to validate a papyrus plug-in.
	 *
	 * @param monitor
	 *            The progress monitor for the action (can be <code>null</code>).
	 */
	public void validate(final IProgressMonitor monitor) {
		this.pluginCheckers.stream().forEach(checker -> {
			if (!monitor.isCanceled()) {
				checker.check(monitor);
			}
		});
	}
}
