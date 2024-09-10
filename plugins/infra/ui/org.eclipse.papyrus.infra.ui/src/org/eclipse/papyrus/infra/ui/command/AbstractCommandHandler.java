/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *      Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr
 *      Christian W. Damus (CEA) - Refactoring package/profile import/apply UI for CDO
 *      Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * <pre>
 *
 * This abstract command handler manages:
 * - current selection in order to build a list of the selected {@link EObject}
 * - execute the command (returned by children) in Papyrus {@link TransactionalEditingDomain}
 * - calculate the command enablement and visibility regarding the command executability
 * (the command is now shown in menu if not executable).
 *
 * </pre>
 * 
 * @since 1.2
 */
public abstract class AbstractCommandHandler extends AbstractPapyrusHandler {

	private List<?> selection = Collections.EMPTY_LIST;

	/**
	 * Returns the command to execute (to be implemented
	 * in children implementing this class)
	 *
	 * @param context
	 *            the command evaluation context
	 * 
	 * @return the command to execute
	 */
	protected abstract Command getCommand(IEvaluationContext context);

	protected Command getCommand(ExecutionEvent event) {
		Command result = null;

		Object context = event.getApplicationContext();
		if (context instanceof IEvaluationContext) {
			result = getCommand((IEvaluationContext) context);
		} else {
			throw new IllegalArgumentException("No evaluation context in execution event: " + event); //$NON-NLS-1$
		}

		return result;
	}

	protected List<?> getSelection() {
		return selection;
	}

	/**
	 * <pre>
	 * Get the selected element, the first selected element if several are selected or null
	 * if no selection or the selection is not an {@link EObject}.
	 *
	 * @return selected {@link EObject} or null
	 * </pre>
	 *
	 */
	protected EObject getSelectedElement() {
		EObject eObject = null;

		// Get current selection
		List<?> selection = getSelection();

		// Treat non-null selected object (try to adapt and return EObject)
		if (!selection.isEmpty()) {

			// Get first element if the selection is an IStructuredSelection
			Object first = selection.get(0);

			EObject businessObject = EMFHelper.getEObject(first);
			if (businessObject != null) {
				eObject = businessObject;
			}
		}

		return eObject;
	}

	/**
	 * <pre>
	 * Parse current selection and extract the list of {@link EObject} from
	 * this selection.
	 *
	 * This also tries to adapt selected element into {@link EObject}
	 * (for example to get the {@link EObject} from a selection in the ModelExplorer).
	 *
	 * @return a list of currently selected {@link EObject}
	 * </pre>
	 *
	 */
	protected List<EObject> getSelectedElements() {

		List<EObject> selectedEObjects = new ArrayList<EObject>();

		// Get current selection
		Collection<?> selection = getSelection();

		// Treat non-null selected object (try to adapt and return EObject)
		if (!selection.isEmpty()) {

			// Parse current selection
			for (Object current : selection) {
				// Adapt current selection to EObject
				EObject selectedEObject = EMFHelper.getEObject(current);
				if (selectedEObject != null) {
					// we avoid to add null element in the list!
					selectedEObjects.add(selectedEObject);
				}
			}
		}

		return selectedEObjects;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			this.selection = (selection instanceof IStructuredSelection) ? ((IStructuredSelection) selection).toList() : Collections.EMPTY_LIST;

			ServiceUtilsForHandlers.getInstance().getTransactionalEditingDomain(event).getCommandStack().execute(getCommand(event));
		} catch (ServiceException e) {
			Activator.log.error("Unexpected error while executing command.", e); //$NON-NLS-1$
		} finally {
			// clear the selection
			this.selection = Collections.EMPTY_LIST;
		}

		return null;
	}

	protected boolean computeEnabled(IEvaluationContext context) {
		boolean result = false;

		Command command = getCommand(context);
		if (command != null) {
			result = command.canExecute();
			command.dispose();
		}

		return result;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext) evaluationContext;

			Object selection = ((IEvaluationContext) evaluationContext).getDefaultVariable();
			if (selection instanceof Collection<?>) {
				this.selection = (selection instanceof List<?>) ? (List<?>) selection : new java.util.ArrayList<Object>((Collection<?>) selection);
				setBaseEnabled(computeEnabled(context));
			}
		}
		super.setEnabled(evaluationContext);
	}
}
