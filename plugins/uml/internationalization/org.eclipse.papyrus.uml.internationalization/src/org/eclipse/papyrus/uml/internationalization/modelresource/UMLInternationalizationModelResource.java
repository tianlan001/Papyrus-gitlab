/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.modelresource;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.internationalization.commands.ResetNameCommand;
import org.eclipse.papyrus.infra.internationalization.commands.ResetNameTransactionalCommand;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.internationalization.modelresource.InternationalizationModelResource;
import org.eclipse.papyrus.infra.internationalization.utils.InternationalizationKeyResolver;
import org.eclipse.papyrus.uml.internationalization.utils.UMLInternationalizationKeyResolver;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This allows to manage the UML internationalization resource.
 */
public class UMLInternationalizationModelResource extends InternationalizationModelResource {

	/**
	 * Constructor.
	 */
	public UMLInternationalizationModelResource() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.modelresource.InternationalizationModelResource#createKeyResolver()
	 */
	@Override
	protected InternationalizationKeyResolver createKeyResolver() {
		return new UMLInternationalizationKeyResolver();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.modelresource.InternationalizationModelResource#getSetNameValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected Command getSetNameValueCommand(final EditingDomain domain, final EObject eObject) {
		Command result = null;
		
		if(InternationalizationPreferencesUtils.isInternationalizationNeedToBeLoaded()) {
	
			// Change name for named element
			if (eObject instanceof NamedElement) {
				if (domain instanceof TransactionalEditingDomain) {
					result = new GMFtoEMFCommandWrapper(new ResetNameTransactionalCommand(
							(TransactionalEditingDomain) domain, eObject, UMLPackage.eINSTANCE.getNamedElement_Name()));
				} else {
					result = new ResetNameCommand(domain, eObject, UMLPackage.eINSTANCE.getNamedElement_Name());
				}
			} else {
				result = super.getSetNameValueCommand(domain, eObject);
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.modelresource.InternationalizationModelResource#setNameValue(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void setNameValue(final EObject eObject) {

		// Change name for named element
		if (eObject instanceof NamedElement) {
			String oldName = ((NamedElement) eObject).getName();
			((NamedElement) eObject).setName(null);
			((NamedElement) eObject).setName(oldName);
		} else {
			super.setNameValue(eObject);
		}
	}
}