/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.modelexplorer.actionprovider;

import org.eclipse.jface.action.IAction;
import org.eclipse.papyrus.infra.ui.api.actions.EclipseCommandToAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;

/**
 *
 * This provider provides the action used to be able to open an existing page on a DoubleClick
 * reusing the Papyrus Open Command declared in the plugin.xml
 *
 * @since 5.0
 *
 */
public class OpenActionProvider extends CommonActionProvider {

	/**
	 * The ID of the open command declared in the ModelExplorer
	 */
	private static final String OPEN_COMMAND_ID = "org.eclipse.papyrus.views.modelexplorer.popup.open.command"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public OpenActionProvider() {
		// nothing to do
	}

	/**
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 *
	 * @param actionBars
	 */
	@Override
	public void fillActionBars(final IActionBars actionBars) {
		final IAction openAction = new EclipseCommandToAction(OPEN_COMMAND_ID);
		if (openAction.isEnabled()) {
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
		}
	}

}
