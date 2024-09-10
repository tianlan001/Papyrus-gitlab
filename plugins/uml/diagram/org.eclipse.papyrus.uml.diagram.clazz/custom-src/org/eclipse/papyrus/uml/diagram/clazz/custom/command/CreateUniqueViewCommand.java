/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *   Arthur Daussy (Atos) arthur.daussy@atos.net - Bug 249786: [General] drag'n'drop does not work for elements stored in nested classifier compartment
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Basic Create Command but check that there is no other view which is related to the same element before. If so do nothing
 */
public class CreateUniqueViewCommand extends CreateViewCommand {

	public CreateUniqueViewCommand(TransactionalEditingDomain editingDomain, ViewDescriptor viewDescriptor, View containerView) {
		super(editingDomain, viewDescriptor, containerView);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Object object = viewDescriptor.getElementAdapter().getAdapter(EObject.class);
		if (object instanceof EObject) {
			final EObject childEObject = (EObject) object;
			// trnasforme view to the semantic element (EObject)
			@SuppressWarnings("unchecked")
			Iterable<EObject> ite = Iterables.transform(containerView.getChildren(), new Function<View, EObject>() {

				@Override
				public EObject apply(View from) {
					return from.getElement();
				}
			});
			// try to find in the view a view which correspond to the EObbject
			Iterable<EObject> find = Iterables.filter(ite, new Predicate<EObject>() {

				@Override
				public boolean apply(EObject input) {
					return childEObject.equals(input);
				}
			});
			/*
			 * Create the element only if there is no element relative to same EOBject already created
			 */
			if (!find.iterator().hasNext()) {
				return super.doExecuteWithResult(monitor, info);
			}
		}
		return CommandResult.newOKCommandResult();
	}

	@Override
	public boolean canExecute() {
		return super.canExecute();
	}
}
