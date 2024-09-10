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
package org.eclipse.papyrus.uml.modelexplorer.handler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.menu.AbstractCommonCommandHandler;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.NamedElement;

/**
 * This abstract command handler: - calculates the current selection -
 * calculates the visibility and enablement based on command executability -
 * executes the command in Papyrus command stack
 *
 */
public abstract class AbstractGenericCommandHandler extends AbstractCommonCommandHandler {

	protected abstract Command getCommand(ExecutionEvent event);

	/**
	 * Iterate over current selection and build a list of the {@link EObject} contained in the selection.
	 *
	 * @return the currently selected {@link EObject}
	 */
	protected List<EObject> getSelectedElements() {
		List<EObject> result = new LinkedList<EObject>();
		for (Object element : getSelection()) {
			EObject elementi = EMFHelper.getEObject(element) ;
			if(elementi instanceof NamedElement){
				result.add( elementi);
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
			getEditingDomain(event).getCommandStack().execute(getCommand(event));
		} finally {
			// clear the selection
			this.selection = Collections.EMPTY_LIST;
		}

		return null;
	}
}
