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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForActionHandlers;


public abstract class AbstractParametricOnSelectedElementsAction {

	/**
	 * parameter for the action
	 */
	protected String parameter;

	/**
	 * selected EditPart
	 */
	private List<EObject> selection;

	/**
	 *
	 * Constructor.
	 *
	 * @param parameter
	 *            parameter for the action
	 * @param selectedEditPart
	 *            the selectedEditPart for the action
	 */
	public AbstractParametricOnSelectedElementsAction(String parameter, List<EObject> selectedEditPart) {
		this.parameter = parameter;
		this.selection = selectedEditPart;
	}

	/**
	 * Returns the selected Editparts for this action
	 *
	 * @return
	 *         {@link #selection}
	 */
	protected List<EObject> getSelection() {
		return selection;
	}

	/**
	 * Test if the command can be build
	 *
	 * @return
	 *         <code>true</code> if the command can be build
	 */
	public boolean isEnabled() {
		return true;
		//return !selection.isEmpty();
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}


	/**
	 * Sets the parameter.
	 *
	 * @param parameter
	 *            the new parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * executes the action
	 */
	public void doRun(IProgressMonitor progressMonitor) {
		// may be implemented by inherited class
	};

	
	/**
	 * Returns the {@link TransactionalEditingDomain}
	 *
	 * @return the {@link TransactionalEditingDomain} or <code>null</code> if it can not be found
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
}
