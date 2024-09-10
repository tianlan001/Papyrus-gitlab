/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForActionHandlers;
import org.eclipse.papyrus.uml.diagram.common.Activator;


/**
 * This handler is used when the action returns a commands
 * This handler can use a parameter.
 * The command is executed by the handler
 *
 * @deprecated Since February 2014 use org.eclipse.papyrus.infra.gmfdiag.menu.handlers.AbstractGraphicalCommandHandler
 * FIXME: it's impossible to use the new class due to missing parameter field from AbstractParametricHandler 
 */
@Deprecated
public abstract class AbstractGraphicalCommandHandler extends AbstractParametricHandler {

	/**
	 *
	 * Constructor.
	 *
	 * @param parameterID
	 *            the parameter id for this handler
	 */
	public AbstractGraphicalCommandHandler(String parameterID) {
		super(parameterID);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parameterID
	 *            the parameter id for this handler
	 * @param parameter
	 *            the value of the parameter for this handler
	 */
	public AbstractGraphicalCommandHandler(String parameterID, String parameter) {
		super(parameterID, parameter);
	}

	/**
	 *
	 * @return the command provided by the handler
	 * @throws ExecutionException
	 */
	protected abstract Command getCommand() throws ExecutionException;


	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return null
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TransactionalEditingDomain editingDomain = getEditingDomain();
		if (editingDomain != null) {
			editingDomain.getCommandStack().execute(new GEFtoEMFCommandWrapper(getCommand()));
		}

		return null;
	}

	/**
	 * Set enabled to true is the command can be executed
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		try {
			if (getCommand().canExecute()) {
				setBaseEnabled(true);
				return;
			}
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}

		setBaseEnabled(false);
	}

	/**
	 *
	 * @return true if the command can be executed
	 */
	public boolean isVisible() {
		try {
			if (getCommand().canExecute()) {
				return true;
			}
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}

		return false;
	}

	/**
	 * Retrieves the TransactionalEditingDomain
	 *
	 * @return the editing domain (can be null)
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		TransactionalEditingDomain editingDomain = null;
		try {
			editingDomain = ServiceUtilsForActionHandlers.getInstance().getTransactionalEditingDomain();
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return editingDomain;
	}

	/**
	 * gives access to the diagram command stack
	 *
	 * @return the diagram command stack
	 */
	protected DiagramCommandStack getDiagramCommandStack() {
		Object stack = getWorkbenchPart().getAdapter(CommandStack.class);
		return (stack instanceof DiagramCommandStack) ? (DiagramCommandStack) stack : null;
	}

}
