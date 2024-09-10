/*****************************************************************************
 * Copyright (c) 2010-2011 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.command.PackageMergeReorientCommand;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This helper is used to set the source and the target for a {@link PackageMerge}
 */
public class PackageMergeEditHelper extends DirectedRelationshipEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getSourceReference() {
		return UMLPackage.eINSTANCE.getPackageMerge_ReceivingPackage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getTargetReference() {
		return UMLPackage.eINSTANCE.getPackageMerge_MergedPackage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canCreate(EObject source, EObject target) {
		if((source != null) && !(source instanceof Package)) {
			return false;
		}
		if((target != null) && !(target instanceof Package)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new PackageMergeReorientCommand(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req) {
		ICommand command = super.getDestroyReferenceCommand(req);
		PackageMerge elementToEdit = (PackageMerge)req.getContainer();
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementToEdit.eContainer());
		if(provider == null) {
			return command;
		}
		boolean shouldDestroyPackageMerge = false;
		if(getSourceReference().equals(req.getContainingFeature()) && (elementToEdit.getReceivingPackage() != null)) {
			shouldDestroyPackageMerge = true;
		}
		if(getTargetReference().equals(req.getContainingFeature()) && (elementToEdit.getMergedPackage() != null)) {
			shouldDestroyPackageMerge = true;
		}
		if(shouldDestroyPackageMerge) {
			DestroyElementRequest destroyRequest = new DestroyElementRequest(elementToEdit, false);
			command = provider.getEditCommand(destroyRequest);
		}
		return command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getSetCommand(SetRequest req) {
		// If sources or targets are null, the PackageMerge should be destroyed.
		if(getSourceReference().equals(req.getFeature()) || getTargetReference().equals(req.getFeature())) {
			Object value = req.getValue();
			if(value == null) {
				// Get PackageMerge destroy command from Element Edit Service
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(req.getElementToEdit());
				if(provider != null) {
					DestroyElementRequest destroyRequest = new DestroyElementRequest(req.getElementToEdit(), false);
					ICommand destroyCommand = provider.getEditCommand(destroyRequest);
					return destroyCommand;
				}
			}
		}
		return super.getSetCommand(req);
	}
}
