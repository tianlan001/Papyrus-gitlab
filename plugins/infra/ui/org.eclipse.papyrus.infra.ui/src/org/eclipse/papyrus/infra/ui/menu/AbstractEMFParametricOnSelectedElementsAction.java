/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.menu;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.menu.AbstractParametricOnSelectedElementsAction;


public abstract class AbstractEMFParametricOnSelectedElementsAction extends AbstractParametricOnSelectedElementsAction {

	/**
	 *
	 * Constructor.
	 *
	 * @param parameter
	 *            parameter for the action
	 * @param selectedEditPart
	 *            the selectedEditPart for the action
	 */
	public AbstractEMFParametricOnSelectedElementsAction(String parameter, List<EObject> selectedEditPart) {
		super(parameter, selectedEditPart);
	}

	/**
	 * Returns the command for this action
	 *
	 * @return
	 *         the command for this action
	 */
	public Command getCommand() {
		if (isEnabled()) {
			Command cmd = getBuildedCommand();
			if (cmd != null && cmd.canExecute()) {
				return cmd;
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @return
	 *         the command for this action
	 */
	protected abstract Command getBuildedCommand();
}
