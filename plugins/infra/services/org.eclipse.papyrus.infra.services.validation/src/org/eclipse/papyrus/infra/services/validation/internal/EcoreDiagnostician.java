/*****************************************************************************
 * Copyright (c) 2010, 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 * Ed Willink, Klaas Gadeyne, A. Radermacher - Remove hard-coded UML dependency - Bug 408215 
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.validation.internal;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;

/**
 * This is a generic diagnostician to validate Ecore model in Papyrus
 *
 */
public class EcoreDiagnostician extends Diagnostician implements IPapyrusDiagnostician {

	protected AdapterFactory adapterFactory;
	protected IProgressMonitor progressMonitor;
	protected EValidatorAdapter validatorAdapter;
	
	/**
	 * Create diagnostician with custom validator (that must subclass ECore validator)
	 *
	 * @param validatorAdapter
	 *            custom validator adapter
	 */
	public EcoreDiagnostician(EValidatorAdapter validatorAdapter) {
		this.validatorAdapter = validatorAdapter;
	}

	public EcoreDiagnostician() {
		validatorAdapter = new EValidatorAdapter((EValidator) EValidator.Registry.INSTANCE.get(EcorePackage.eINSTANCE));
	}

	public void initialize(final AdapterFactory adapterFactory, final IProgressMonitor progressMonitor) {
		this.adapterFactory = adapterFactory;
		this.progressMonitor = progressMonitor;
	}

	@Override
	public String getObjectLabel(EObject eObject) {
		if (adapterFactory != null && !eObject.eIsProxy())
		{
			IItemLabelProvider itemLabelProvider = (IItemLabelProvider) adapterFactory.adapt(eObject, IItemLabelProvider.class);
			if (itemLabelProvider != null) {
				return itemLabelProvider.getText(eObject);
			}
		}
		return super.getObjectLabel(eObject);
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		assert (progressMonitor != null);
		progressMonitor.worked(1);

		// copied from superclass, difference: use EValidatorAdapter instead of first value from eValidatorRegistry
		// fix of bug 397518

		boolean circular = context.get(EObjectValidator.ROOT_OBJECT) == eObject;
		boolean result = validatorAdapter.validate(eClass, eObject, diagnostics, context);
		if ((result || diagnostics != null) && !circular)
		{
			result &= doValidateContents(eObject, diagnostics, context);
		}
		return result;
	}
}