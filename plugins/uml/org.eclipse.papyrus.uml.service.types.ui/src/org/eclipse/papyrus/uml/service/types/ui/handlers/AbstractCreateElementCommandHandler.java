/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.util.WorkbenchPartHelper;
import org.eclipse.papyrus.uml.service.types.filter.ICommandFilter;
import org.eclipse.papyrus.uml.service.types.filter.UmlElementCommandFilter;
import org.eclipse.papyrus.uml.service.types.utils.ICommandContext;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract handler for commands regarding creation of elements
 *
 */
public abstract class AbstractCreateElementCommandHandler extends AbstractCommandHandler {
	
	protected CreateElementRequest createRequest;

	protected abstract IElementType getElementTypeToCreate();

	/**
	 * <pre>
	 * 
	 * Build the create command for an element creation in the selected container.
	 * The create command is given by the {@link IElementEditService} of selected 
	 * element.
	 * 
	 * @return the composite creation command for current selection
	 * 
	 * </pre>
	 */
	protected Command buildCommand() {
		ICommandContext commandContext = getCommandContext();
		if(commandContext == null) {
			return UnexecutableCommand.INSTANCE;
		}

		EObject container = commandContext.getContainer();

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(container);
		if(provider == null) {
			return UnexecutableCommand.INSTANCE;
		}

		ICommand createGMFCommand = provider.getEditCommand(createRequest);
		if(createGMFCommand != null) {
			Command emfCommand = org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper.wrap(createGMFCommand);
			return emfCommand;
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * 
	 * @return
	 *         the creation request to use in this handler
	 */
	protected CreateElementRequest buildRequest() {
		ICommandContext commandContext = getCommandContext();
		if(commandContext != null) {
			EObject container = commandContext.getContainer();
			EReference reference = commandContext.getReference();
			boolean nullReference = reference == null;
			return nullReference ? new CreateElementRequest(getEditingDomain(), container, getElementTypeToCreate()) : new CreateElementRequest(getEditingDomain(), container, getElementTypeToCreate(), reference);
		}
		return null;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.ui.handlers.modelexplorer.handler.AbstractCommandHandler#getCommand()
	 * 
	 * @return current command
	 */
	protected Command getCommand() {
		// In case we had one before, dispose it before replacing it
		disposeCommand();
		
		createRequest = buildRequest();
		return buildCommand();
	}

	protected IWorkbenchPart getActiveWorkbenchPart() {
		return WorkbenchPartHelper.getCurrentActiveWorkbenchPart();
	}


	/**
	 * This method is called by the commands service to validate if this particular handler is active.
	 * By default, the creation of UML handlers only verify that the element to be created is allowed by the applied filter (
	 * {@link UmlElementCommandFilter}, ...)
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.ui.handlers.AbstractCommandHandler#setEnabled(java.lang.Object)
	 * 
	 * 
	 * @param evaluationContext
	 */
	public void setEnabled(Object evaluationContext) {
		IElementType newElementType = getElementTypeToCreate();
		boolean isEnabled = getCommandFilter().getVisibleCommands().contains(newElementType);

		if(isEnabled) {
			Command command = getCommand();
			isEnabled = command.canExecute();
		}
		setBaseEnabled(isEnabled);
	}

	/** returns the command filter to use for this handler */
	public abstract ICommandFilter getCommandFilter();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object result;
		
		try {
			result = super.execute(event);
		} finally {
			// If execution succeeded, the command will be disposed later by the history.
			// If it failed, the history already disposed it.
			// Either way, we should not dispose it.
			createRequest = null;
		}
		
		return result;
	}
	
	private void disposeCommand() {
		createRequest = null;
	}
	
	@Override
	public void dispose() {
		disposeCommand();
		super.dispose();
	}
}

