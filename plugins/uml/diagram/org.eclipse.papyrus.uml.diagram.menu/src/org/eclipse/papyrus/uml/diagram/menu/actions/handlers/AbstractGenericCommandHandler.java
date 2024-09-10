/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions.handlers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.menu.AbstractCommonCommandHandler;
import org.eclipse.ui.handlers.HandlerUtil;

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
			if(elementi instanceof Diagram){
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
			getEditingDomain(event).getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(getCommand(event)));
		} finally {
			// clear the selection
			this.selection = Collections.EMPTY_LIST;
		}

		return null;
	}
}
