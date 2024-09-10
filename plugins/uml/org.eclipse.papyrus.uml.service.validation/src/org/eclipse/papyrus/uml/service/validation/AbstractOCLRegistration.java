/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST.
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
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - Extension to validation test suite
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 539293
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.validation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ocl.pivot.internal.resource.OCLAdapter;
import org.eclipse.ocl.xtext.completeocl.utilities.CompleteOCLLoader;
import org.eclipse.papyrus.infra.services.validation.IValidationHook;

/**
 * An abstract class that can be used to register OCL files (= add to resource set) before the
 * Papyrus validation is executed. It can be used by extensions for a specific language variant
 * (such as SysML) that register additional OCL rules.
 */
public abstract class AbstractOCLRegistration implements IValidationHook {

	/**
	 * @return The URI of the OCL file that should be added to the resource set. Must be implemented by subclasses
	 *
	 */
	protected abstract URI getOCLFileURI();

	/**
	 * @param element
	 *            the element for which validation has been launched.
	 * @return true, if an additional OCL resource should be added to the model containing this element. Subclasses
	 *         need to implement this operation. They will typically navigate to the root element to check what kind
	 *         of model is currently validated.
	 */
	protected abstract boolean isApplicable(EObject element);

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IValidationHook#beforeValidation()
	 *
	 */
	public void beforeValidation(EObject element) {
		if (isApplicable(element)) {
			addOCLResource(element);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IValidationHook#afterValidation()
	 *
	 */
	public void afterValidation(EObject element) {
	}

	public void addOCLResource(EObject element) {
		if (null == element || null == element.eResource() || null == element.eResource().getResourceSet()) {
			return;
		}

		URI oclURI = getOCLFileURI();
		ResourceSet modelResources = element.eResource().getResourceSet();
		for (Resource resource : modelResources.getResources()) {
			if (resource.getURI().equals(oclURI)) {
				// already loaded, nothing to do
				return;
			}
		}
		OCLAdapter oclAdapter = OCLAdapter.getAdapter(modelResources);
		CompleteOCLLoader helper = new CompleteOCLLoader(oclAdapter.getEnvironmentFactory()) {
			@Override
			protected boolean error(String primaryMessage, String detailMessage) {
				Activator.debug("Can not get environment factory"); //$NON-NLS-1$
				return false;
			}
		};
		helper.loadMetamodels();

		try {
			if (!helper.loadDocument(oclURI)) {
				Activator.debug("Can not load OCL document with URI: " + oclURI.path()); //$NON-NLS-1$
			}
		} catch (Throwable e) {
			Activator.debug(String.format("Exception %s during loading of OCL document with URI: %s", e.getMessage(), oclURI.path())); //$NON-NLS-1$
		}
		helper.installPackages();
	}
}
