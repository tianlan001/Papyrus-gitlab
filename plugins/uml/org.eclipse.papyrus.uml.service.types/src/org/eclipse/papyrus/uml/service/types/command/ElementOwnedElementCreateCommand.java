/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.service.types.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.utils.NamespaceOwnedMemberUtils;
import org.eclipse.uml2.uml.Namespace;

public class ElementOwnedElementCreateCommand extends EditElementCommand {

	protected final EObject source;

	protected final EObject target;

	public ElementOwnedElementCreateCommand(CreateRelationshipRequest request) {
		super(request.getLabel(), null, request);
		this.source = request.getSource();
		this.target = request.getTarget();
	}



	@Override
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && !(source instanceof org.eclipse.uml2.uml.Namespace)) {
			return false;
		}

		if (target != null && !(target instanceof org.eclipse.uml2.uml.Namespace)) {
			return false;
		}
		if (!NamespaceOwnedMemberUtils.canContainTarget((Namespace) source, (Namespace) target)) {
			return false;
		}
		if (getSource() == null) {
			return true;
		}
		return true;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in create link command"); //$NON-NLS-1$
		}
		if (getSource() != null && getTarget() != null) {
			Object value = getSource().eGet(NamespaceOwnedMemberUtils.getContainmentFeature(getSource(), getTarget()));
			if (value instanceof List<?>) {
				((List) value).add(getTarget());
			} else if (value != null) {
				getSource().eSet(NamespaceOwnedMemberUtils.getContainmentFeature(getSource(), getTarget()), getTarget());
			}

		}
		return CommandResult.newOKCommandResult();
	}

	@Override
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	protected org.eclipse.uml2.uml.Namespace getSource() {
		return (org.eclipse.uml2.uml.Namespace) source;
	}

	protected org.eclipse.uml2.uml.Namespace getTarget() {
		return (org.eclipse.uml2.uml.Namespace) target;
	}
}
