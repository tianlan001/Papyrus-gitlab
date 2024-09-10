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
 * This handler allows to create Details Entry for a selected EAnnotation
 */
public class CreateEAnnotationDetailsEntryHandler extends AbstractHandler {

	/**
	 * the editing domain to use
	 */
	private TransactionalEditingDomain domain;

	/**
	 * the selected EAnnotation
	 */
	private EAnnotation selectedEAnnotation;


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
		final EObject detailsMap = EcoreFactory.eINSTANCE.create(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY);
		final Command cmd = AddCommand.create(this.domain, this.selectedEAnnotation, EcorePackage.eINSTANCE.getEAnnotation_Details(), detailsMap);

		if (cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
		return null;
	}


	/**
	 *
	 * @return
	 *         the selected EAnnotation, or <code>null</code>
	 */
	private EAnnotation getSelectedEAnnotation() {
		this.selectedEAnnotation = null;
		final Object firstSelectedElement = SelectionHelper.getCurrentStructuredSelection().getFirstElement();
		if (null != firstSelectedElement) {
			final EObject current = EMFHelper.getEObject(firstSelectedElement);
			if (current instanceof EAnnotation) {
				this.selectedEAnnotation = (EAnnotation) current;
			}
		}
		return this.selectedEAnnotation;
	}

	/**
	 *
	 * @return
	 *         the editing domain, or <code>null</code>
	 */
	private TransactionalEditingDomain getEditingDomain() {
		this.domain = null;
		if (null != this.selectedEAnnotation) {
			return this.domain = TransactionUtil.getEditingDomain(this.selectedEAnnotation);
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
		setBaseEnabled(null != getSelectedEAnnotation() && null != getEditingDomain());
	}

}
