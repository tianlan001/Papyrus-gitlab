/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.ecore.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.util.SelectionHelper;

/**
 * This handler allows to create an EAnnotation inside the selected EObject
 */
public class CreateEAnnotationHandler extends AbstractHandler {

	/**
	 * the editing domain to use
	 */
	private TransactionalEditingDomain domain;

	/**
	 * the selected eobject
	 */
	private EObject selectedEObject;


	/**
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final EAnnotation createdEAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		final Command cmd = AddCommand.create(this.domain, this.selectedEObject, EcorePackage.eINSTANCE.getEModelElement_EAnnotations(), createdEAnnotation);

		if (cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
		return null;
	}


	/**
	 *
	 * @return
	 *         the selected EObject or <code>null</code>
	 */
	private EObject getSelectedEObject() {
		this.selectedEObject = null;
		final Object firstSelectedElement = SelectionHelper.getCurrentStructuredSelection().getFirstElement();
		if (null != firstSelectedElement) {
			this.selectedEObject = EMFHelper.getEObject(firstSelectedElement);
		}
		return this.selectedEObject;
	}

	/**
	 *
	 * @return
	 *         the editing domain to use or <code>null</code>
	 */
	private TransactionalEditingDomain getEditingDomain() {
		this.domain = null;
		if (null != this.selectedEObject) {
			return this.domain = TransactionUtil.getEditingDomain(this.selectedEObject);
		}
		return this.domain;
	}

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(null != getSelectedEObject() && null != getEditingDomain());
	}
}
