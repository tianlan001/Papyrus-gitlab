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
import org.eclipse.papyrus.uml.service.types.command.ElementImportReorientCommand;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This helper is used to set the source and the target for a {@link ElementImport}
 */
public class ElementImportEditHelper extends DirectedRelationshipEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getSourceReference() {
		return UMLPackage.eINSTANCE.getElementImport_ImportingNamespace();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EReference getTargetReference() {
		return UMLPackage.eINSTANCE.getElementImport_ImportedElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canCreate(EObject source, EObject target) {
		if ((source != null) && !(source instanceof Namespace)) {
			return false;
		}
		if ((target != null) && !(target instanceof PackageableElement)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new ElementImportReorientCommand(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req) {
		ICommand command = super.getDestroyReferenceCommand(req);
		PackageImport elementToEdit = (PackageImport) req.getContainer();
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementToEdit.eContainer());
		if (provider == null) {
			return command;
		}
		boolean shouldDestroyPackageImport = false;
		if (getSourceReference().equals(req.getContainingFeature()) && (elementToEdit.getImportingNamespace() != null)) {
			shouldDestroyPackageImport = true;
		}
		if (getTargetReference().equals(req.getContainingFeature()) && (elementToEdit.getImportedPackage() != null)) {
			shouldDestroyPackageImport = true;
		}
		if (shouldDestroyPackageImport) {
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
		// If sources or targets are null, the PackageImport should be destroyed.
		if (getSourceReference().equals(req.getFeature()) || getTargetReference().equals(req.getFeature())) {
			Object value = req.getValue();
			if (value == null) {
				// Get PackageImport destroy command from Element Edit Service
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(req.getElementToEdit());
				if (provider != null) {
					DestroyElementRequest destroyRequest = new DestroyElementRequest(req.getElementToEdit(), false);
					ICommand destroyCommand = provider.getEditCommand(destroyRequest);
					return destroyCommand;
				}
			}
		}
		return super.getSetCommand(req);
	}
}
