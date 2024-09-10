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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.ui.ISources;

/**
 * This abstract command handler: - calculates the current selection -
 * calculates the visibility and enablement based on command executability -
 * executes the command in Papyrus command stack
 *
 */
public abstract class AbstractCommonCommandHandler extends AbstractHandler {

	/**
	 * Iterate over current selection and build a list of the {@link EObject} contained in the selection.
	 *
	 * @return the currently selected {@link EObject}
	 */
	protected abstract List<EObject> getSelectedElements();

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return null
	 * @throws ExecutionException
	 */
	@Override
	public abstract Object execute(ExecutionEvent event) throws ExecutionException;

	protected TransactionalEditingDomain getEditingDomain(ExecutionEvent event) {
		try {
			return ServiceUtilsForHandlers.getInstance().getTransactionalEditingDomain(event);
		} catch (ServiceException ex) {
			return null;
		}
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
		List<EObject> elts = getSelectedElements();
		return !(elts.size() == 0);
	}

	protected List<?> getSelection() {
		return selection;
	}

	protected List<?> selection = Collections.EMPTY_LIST;

	/**
	 *
	 * @return true if the command can be executed
	 */
	public boolean isVisible() {
		return isEnabled();
	}
}
