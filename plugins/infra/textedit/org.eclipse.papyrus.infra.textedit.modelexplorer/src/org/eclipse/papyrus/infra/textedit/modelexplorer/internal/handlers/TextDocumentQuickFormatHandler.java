/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.papyrus.infra.textedit.modelexplorer.internal.actions.TextDocumentQuickFormatAction;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;
import org.eclipse.papyrus.infra.ui.menu.NamePropertyTester;

/**
 * The handler for the {@link TextDocumentQuickFormatAction}
 */
public class TextDocumentQuickFormatHandler extends AbstractTextDocumentCommandHandler {

	/**
	 *
	 * Constructor.
	 */
	public TextDocumentQuickFormatHandler() {
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

		TextDocumentQuickFormatAction action = new TextDocumentQuickFormatAction(parameter, getSelectedElements());
		setBaseEnabled(action.isEnabled());
		if (action.isEnabled()) {
			return action.getCommand();
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.command.AbstractCommandHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		TextDocumentQuickFormatAction action = new TextDocumentQuickFormatAction(NameNormalizationCommand.DEFAULT_ACTION, getSelectedElements());
		setBaseEnabled(action.isEnabled());
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.command.AbstractCommandHandler#getCommand(org.eclipse.core.expressions.IEvaluationContext)
	 *
	 * @param context
	 * @return
	 */
	@Override
	protected Command getCommand(IEvaluationContext context) {
		// not used
		return null;
	}
}
