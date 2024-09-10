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
package org.eclipse.papyrus.infra.nattable.modelexplorer.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.papyrus.infra.nattable.modelexplorer.actions.NattableQuickFormatAction;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;
import org.eclipse.papyrus.infra.ui.menu.NamePropertyTester;

/**
 * The handler for the {@link NattableQuickFormatAction}
 *
 *
 *
 */
public class NattableQuickFormatHandler extends AbstractGenericCommandHandler {

	/**
	 *
	 * Constructor.
	 */
	public NattableQuickFormatHandler() {
		super();
	}
	
	/**
	 * @see org.eclipse.papyrus.infra.nattable.modelexplorer.handlers.AbstractGenericCommandHandler#getCommand(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 */
	@Override
	protected Command getCommand(ExecutionEvent event) {
		String parameter = event.getParameter(NamePropertyTester.PARAMETER_ID);
		
		NattableQuickFormatAction action = new NattableQuickFormatAction(parameter,getSelectedElements());
		setBaseEnabled(action.isEnabled());
		if (action.isEnabled()) {
			return action.getCommand();
		}
		return UnexecutableCommand.INSTANCE;
	}


	@Override
	public void setEnabled(Object evaluationContext) {
		NattableQuickFormatAction action = new NattableQuickFormatAction(NameNormalizationCommand.DEFAULT_ACTION, getSelectedElements());
		setBaseEnabled(action.isEnabled());
	}




}
