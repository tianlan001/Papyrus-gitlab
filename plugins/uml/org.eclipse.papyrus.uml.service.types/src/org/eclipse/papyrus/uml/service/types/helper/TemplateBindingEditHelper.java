/*****************************************************************************
 * Copyright (c) 2011, 2015 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 459701
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.uml.service.types.Activator;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.TemplateSignature;
import org.eclipse.uml2.uml.TemplateableElement;
import org.eclipse.uml2.uml.UMLPackage;

public class TemplateBindingEditHelper extends DirectedRelationshipEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getSourceReference() {
		return UMLPackage.eINSTANCE.getTemplateBinding_BoundElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getTargetReference() {
		return UMLPackage.eINSTANCE.getTemplateBinding_Signature();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canCreate(EObject source, EObject target) {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && !(source instanceof TemplateableElement)) {
			return false;
		}
		if (target != null && !(target instanceof TemplateableElement)) {
			return false;
		}
		if (target != null && (target instanceof TemplateableElement)) {
			// Can only bind to a template (a thing that has a signature) but not to self
			TemplateableElement template = ((TemplateableElement) target);
			if ((template.getOwnedTemplateSignature() == null) || (template == source)) {
				return false;
			}
		}
		if (source == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		return true;
	}

	@Override
	protected ICommand getConfigureSourcesAndTargetsCommand(final ConfigureRequest req) {
		return new ConfigureElementCommand(req) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				DirectedRelationship element = (DirectedRelationship) req.getElementToConfigure();
				if (req.getParameter(CreateRelationshipRequest.SOURCE) != null) {
					element.eSet(getSourceReference(), getSourceObject(req));
				}
				if (req.getParameter(CreateRelationshipRequest.TARGET) != null) {
					element.eSet(getTargetReference(), ((TemplateableElement) getTargetObject(req)).getOwnedTemplateSignature());
				}
				return CommandResult.newOKCommandResult(element);
			}
		};
	}

	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest request) {
		ICommand result = null;

		TemplateBinding binding = (TemplateBinding) request.getRelationship();

		switch (request.getDirection()) {
		case ReorientRelationshipRequest.REORIENT_SOURCE:
			EObject newOwner = request.getNewRelationshipEnd();
			if (!(newOwner instanceof TemplateableElement)) {
				// Cannot move a template binding to a non-templateable element
				result = UnexecutableCommand.INSTANCE;
			} else {
				TemplateSignature signature = binding.getSignature();
				if ((signature != null) && (signature.getTemplate() == newOwner)) {
					// Cannot bind to self (that would make the element its own template)
					result = UnexecutableCommand.INSTANCE;
				} else {
					// We move the template binding
					result = getEditServiceCommand(binding, new MoveRequest(newOwner, binding));
				}
			}
			break;
		case ReorientRelationshipRequest.REORIENT_TARGET:
			EObject newTarget = request.getNewRelationshipEnd();
			TemplateSignature newSignature = null;

			if (newTarget instanceof TemplateableElement) {
				newSignature = ((TemplateableElement) newTarget).getOwnedTemplateSignature();
			}

			if (newSignature == null) {
				result = UnexecutableCommand.INSTANCE;
			} else if (newSignature.getTemplate() == binding.getBoundElement()) {
				// Cannot bind to self (that would make the element its own template)
				result = UnexecutableCommand.INSTANCE;
			} else {
				result = getEditServiceCommand(binding, new SetRequest(binding, UMLPackage.Literals.TEMPLATE_BINDING__SIGNATURE, newSignature));
			}
			break;
		default:
			result = new UnexecutableCommand(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid relationship re-orientation direction requested: " + request.getDirection()));
			break;
		}

		return result;
	}
}
