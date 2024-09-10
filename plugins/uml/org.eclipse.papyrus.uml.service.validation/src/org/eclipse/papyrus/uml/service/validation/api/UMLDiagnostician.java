/*****************************************************************************
 * Copyright (c) 2010, 2013, 2020 CEA LIST.
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
 * Jeremie TATIBOUET (CEA LIST) jeremie.tatibouet@cea.fr
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.validation.api;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.papyrus.uml.service.validation.internal.OCLEValidatorAdapter;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML diagnostician is in charge on validating UML models.
 */
public class UMLDiagnostician implements IPapyrusDiagnostician {

	/**
	 * Delegate diagnostician (not API)
	 */
	private org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician delegate;

	/**
	 * When instantiated using this constructor, this diagnostician relies on the classic UML Validator provided by the UML implementation
	 */
	public UMLDiagnostician() {
		delegate = new org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician(new OCLEValidatorAdapter((EValidator) EValidator.Registry.INSTANCE.get(UMLPackage.eINSTANCE)));
	}

	/**
	 *
	 * When instantiated using this constructor, the diagnostician relies on a user defined validator.
	 *
	 * @param validator
	 *            - the validator to be used
	 */
	public UMLDiagnostician(EValidator validator) {
		delegate = new org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician(new OCLEValidatorAdapter(validator));
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician#initialize(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void initialize(AdapterFactory adapterFactory, IProgressMonitor progressMonitor) {
		delegate.initialize(adapterFactory, progressMonitor);
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician#createDefaultContext()
	 */
	public Map<Object, Object> createDefaultContext() {
		return delegate.createDefaultContext();
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician#createDefaultDiagnostic(org.eclipse.emf.ecore.EObject)
	 */
	public BasicDiagnostic createDefaultDiagnostic(EObject eObject) {
		return delegate.createDefaultDiagnostic(eObject);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return delegate.validate(eObject, diagnostics, context);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getObjectLabel(org.eclipse.emf.ecore.EObject)
	 */
	public String getObjectLabel(EObject eObject) {
		return delegate.getObjectLabel(eObject);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getFeatureLabel(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
		return delegate.getFeatureLabel(eStructuralFeature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getValueLabel(org.eclipse.emf.ecore.EDataType, java.lang.Object)
	 */
	public String getValueLabel(EDataType eDataType, Object value) {
		return delegate.getValueLabel(eDataType, value);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return delegate.validate(eClass, eObject, diagnostics, context);
	}

	/**
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EDataType, java.lang.Object, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return delegate.validate(eDataType, value, diagnostics, context);
	}

}
