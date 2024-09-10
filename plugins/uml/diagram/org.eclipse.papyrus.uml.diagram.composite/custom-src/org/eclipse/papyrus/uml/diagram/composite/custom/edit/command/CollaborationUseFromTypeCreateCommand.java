/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.composite.providers.ElementInitializers;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * This command creates a new CollaborationUse with a specified type. This command is used when a Collaboration is dropped on a Collaboration or a
 * StructuredClassifier (a new CollabiorationUse is created typed by the dropped Collaboration).
 */
public class CollaborationUseFromTypeCreateCommand extends EditElementCommand {

	protected Collaboration collaboration = null;

	protected StructuredClassifier owner = null;

	protected CollaborationUse newElement = null;

	protected SemanticAdapter semanticAdapter;

	/**
	 * @since 3.0
	 */
	public CollaborationUseFromTypeCreateCommand(CreateElementRequest req, StructuredClassifier owner, Collaboration collaboration, SemanticAdapter semanticAdapter) {
		super(req.getLabel(), null, req);
		this.owner = owner;
		this.collaboration = collaboration;
		this.semanticAdapter = semanticAdapter;
		setResult(CommandResult.newOKCommandResult(semanticAdapter));
	}

	@Override
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		if (container != null) {
			return container;
		}
		return owner;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		newElement = UMLFactory.eINSTANCE.createCollaborationUse();
		StructuredClassifier owner = (StructuredClassifier) getElementToEdit();
		owner.getCollaborationUses().add(newElement);
		newElement.setType(collaboration);

		ElementInitializers.getInstance().init_CollaborationUse_Shape(newElement);

		((CreateElementRequest) getRequest()).setNewElement(newElement);

		semanticAdapter.setElement(newElement);
		return CommandResult.newOKCommandResult(semanticAdapter);
	}
}
