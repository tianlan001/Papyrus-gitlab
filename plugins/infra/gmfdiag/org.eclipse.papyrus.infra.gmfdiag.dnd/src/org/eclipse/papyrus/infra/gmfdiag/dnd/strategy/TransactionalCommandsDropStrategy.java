/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.dnd.strategy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.dnd.Activator;

/**
 * Based upon TransactionalDropStrategy, an abstract implementation for TransactionalCommandsDropStrategy. Extenders do not
 * need to implement their commands' #undo and #redo methods.
 * The differences with TransactionalDropStrategy is the fact that this class give the priority to the CommandS rather to the Command 
 *
 * @author Camille Letavernier
 */
public abstract class TransactionalCommandsDropStrategy extends TransactionalDropStrategy implements MultipleDropStrategy {

	protected boolean isTransactional(EditPart targetEditPart) {
		return getTransactionalEditingDomain(targetEditPart) != null;
	}

	protected TransactionalEditingDomain getTransactionalEditingDomain(EditPart targetEditPart) {
		EditingDomain domain = getEditingDomain(targetEditPart);
		if (domain instanceof TransactionalEditingDomain) {
			return (TransactionalEditingDomain) domain;
		}
		return null;
	}

	protected EditingDomain getEditingDomain(EditPart targetEditPart) {
		return EMFHelper.resolveEditingDomain(targetEditPart);
	}

	protected abstract List<Command> doGetCommands(Request request, EditPart targetEditPart);


	/**
	 * The command to be executed when the strategy is applied.
	 * Should return null if the strategy cannot handle the request.
	 *
	 * @param request
	 *            The drop request
	 * @param targetEditPart
	 *            The target edit part
	 * @return
	 *         A command, or null if the strategy cannot handle the request
	 */
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		List<Command> commands = doGetCommands(request, targetEditPart);
		if(commands!=null && commands.size()>0){
			return commands.get(0);
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getCommands(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	public List<Command> getCommands(Request request, EditPart targetEditPart) {
		List<Command> commands = new ArrayList<>();
		if(doGetCommands(request,targetEditPart)!=null && doGetCommands(request,targetEditPart).size()!=0){

			for(final Command c : doGetCommands(request,targetEditPart)){
				String label = c.getLabel();
				if (label == null || "".equals(label)) {
					label = getLabel();
				}

				AbstractTransactionalCommand transactionalCommand = new AbstractTransactionalCommand(getTransactionalEditingDomain(targetEditPart), label, null) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						try {
							c.execute();
							return CommandResult.newOKCommandResult();
						} catch (Exception ex) {
							Activator.log.error(ex);
							return CommandResult.newErrorCommandResult(ex);
						}
					}
				};
				commands.add(new ICommandProxy(transactionalCommand));
			}
		}
		return commands;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getCommand(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	public Command getCommand(Request request, EditPart targetEditPart) {

		final Command command;
		List<Command> commands = doGetCommands(request,targetEditPart);
		if(commands!=null && commands.size()>0){
			command = commands.get(0);
		}
		else{
			return null;
		}

		if (command == null) {
			return null;
		}

		String label = command.getLabel();
		if (label == null || "".equals(label)) {
			label = getLabel();
		}

		AbstractTransactionalCommand transactionalCommand = new AbstractTransactionalCommand(getTransactionalEditingDomain(targetEditPart), label, null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				try {
					command.execute();
					return CommandResult.newOKCommandResult();
				} catch (Exception ex) {
					Activator.log.error(ex);
					return CommandResult.newErrorCommandResult(ex);
				}
			}
		};

		return new ICommandProxy(transactionalCommand);
	}
}
