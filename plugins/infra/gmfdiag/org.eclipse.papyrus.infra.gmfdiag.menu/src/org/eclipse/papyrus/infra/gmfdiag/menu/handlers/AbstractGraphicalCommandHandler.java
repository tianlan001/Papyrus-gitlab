/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 430701
 *  Christian W. Damus (CEA) - bug 433320
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 454891
 *  Christian W. Damus - bug 433206
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.menu.handlers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This abstract command handler: - calculates the current selection -
 * calculates the visibility and enablement based on command executability -
 * executes the command in Papyrus command stack
 *
 */
public abstract class AbstractGraphicalCommandHandler extends AbstractHandler {

	protected abstract Command getCommand();

	protected Command getCommand(ExecutionEvent event) {
		return getCommand(); // Implement an overridable, default behavior. Extend it only when you actually need the ExecutionEvent (i.e. for your own contribution)
	}

	/**
	 * Iterate over current selection and build a list of the {@link IGraphicalEditPart} contained in the selection.
	 *
	 * @return the currently selected {@link IGraphicalEditPart}
	 */
	protected List<IGraphicalEditPart> getSelectedElements() {
		List<IGraphicalEditPart> result = new LinkedList<IGraphicalEditPart>();
		for (Object element : getSelection()) {
			if (element instanceof IGraphicalEditPart) {
				result.add((IGraphicalEditPart) element);
			}
		}

		return result;
	}

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return null
	 * @throws ExecutionException
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			this.selection = (selection instanceof IStructuredSelection) ? ((IStructuredSelection) selection).toList() : Collections.EMPTY_LIST;

			getEditingDomain(event).getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(getCommand(event)));
		} finally {
			// clear the selection
			this.selection = Collections.EMPTY_LIST;
		}

		return null;
	}

	protected TransactionalEditingDomain getEditingDomain(ExecutionEvent event) {
		try {
			return ServiceUtilsForHandlers.getInstance().getTransactionalEditingDomain(event);
		} catch (ServiceException ex) {
			return null;
		}
	}

	protected TransactionalEditingDomain getEditingDomain() {
		TransactionalEditingDomain editingDomain = null;
		for (IGraphicalEditPart editPart : getSelectedElements()) {
			try {
				editingDomain = ServiceUtilsForEditPart.getInstance().getTransactionalEditingDomain(editPart);
				if (editingDomain != null) {
					break;
				}
			} catch (ServiceException ex) {
				// Keep searching
			}
		}

		// TODO: From active editor?

		return editingDomain;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			Object selection = ((IEvaluationContext) evaluationContext).getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			if (selection instanceof Collection<?>) {
				this.selection = (selection instanceof List<?>) ? (List<?>) selection : new java.util.ArrayList<Object>((Collection<?>) selection);
			} else if (selection instanceof IStructuredSelection) {
				this.selection = ((IStructuredSelection) selection).toList();
			}
			setBaseEnabled(computeEnabled());
			this.selection = Collections.EMPTY_LIST;
		}
		super.setEnabled(evaluationContext);
	}

	protected boolean computeEnabled() {
		boolean result = false;

		TransactionalEditingDomain domain = getEditingDomain();
		if ((domain != null) && !TransactionHelper.isDisposed(domain)) {
			Command command = getCommand(null);
			if (command != null) {
				result = command.canExecute();
				command.dispose();
			}
		}

		return result;
	}

	protected List<?> getSelection() {
		return selection;
	}

	private List<?> selection = Collections.EMPTY_LIST;

	/**
	 *
	 * @return true if the command can be executed
	 */
	public boolean isVisible() {
		return isEnabled();
	}
}
