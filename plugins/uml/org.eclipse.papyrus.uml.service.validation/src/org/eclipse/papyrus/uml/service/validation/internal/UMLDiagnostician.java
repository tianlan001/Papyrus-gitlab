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
 * Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Contribution related to bug 410457, 410119 and 410059
 * Jeremie TATIBOUET (CEA LIST) jeremie.tatibouet@cea.fr
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.validation.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ocl.pivot.internal.delegate.OCLDelegateDomain;
import org.eclipse.papyrus.infra.services.validation.internal.EValidatorAdapter;
import org.eclipse.papyrus.infra.services.validation.internal.EcoreDiagnostician;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This is a specific diagnostician for UML. It is used to validate stereotype applications
 *
 */
public class UMLDiagnostician extends EcoreDiagnostician {

	public UMLDiagnostician() {
		super(new OCLEValidatorAdapter((EValidator) EValidator.Registry.INSTANCE.get(UMLPackage.eINSTANCE)));
		validateStereotype = false;
	}

	public UMLDiagnostician(EValidatorAdapter validatorAdapter) {
		super(validatorAdapter);
		validateStereotype = false;
	}

	@Override
	public Map<Object, Object> createDefaultContext() {
		Map<Object, Object> context = super.createDefaultContext();
		if (context != null) {
			OCLDelegateDomain.initializePivotOnlyDiagnosticianContext(context);
		}
		return context;
	}

	@Override
	public BasicDiagnostic createDefaultDiagnostic(EObject eObject) {
		if (eObject == null || eObject.eResource() == null) {
			return super.createDefaultDiagnostic(eObject);
		}
		ResourceSet resourceSet = eObject.eResource().getResourceSet();
		if (resourceSet != null) {
			OCLDelegateDomain.initializePivotOnlyDiagnosticianResourceSet(resourceSet);
		}
		return super.createDefaultDiagnostic(eObject);
	}

	/**
	 * Explicitly validate stereotype applications.
	 *
	 * @param eObject
	 *            the eObject to validate
	 * @param diagnostics
	 *            the diagnostic chain
	 * @param context
	 *            the context
	 * @return
	 */
	protected boolean doValidateStereotypeApplications(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (validateStereotype) {
			// this function is called recursively. Avoid trying to obtain stereotype applications, if we are
			// already examining a stereotype
			return true;
		}
		List<EObject> stereotypeApplications = eObject instanceof Element ? ((Element) eObject).getStereotypeApplications() : Collections.<EObject> emptyList();
		if (!stereotypeApplications.isEmpty()) {
			Iterator<EObject> i = stereotypeApplications.iterator();
			validateStereotype = true;
			boolean result = validate(i.next(), diagnostics, context);
			while (i.hasNext() && (result || diagnostics != null)) {
				result &= validate(i.next(), diagnostics, context);
			}
			validateStereotype = false;
			return result;
		} else {
			return true;
		}
	}

	@Override
	protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = doValidateStereotypeApplications(eObject, diagnostics, context);
		if (result || diagnostics != null) {
			result &= super.doValidateContents(eObject, diagnostics, context);
		}
		return result;
	}

	@Override
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!context.containsKey(this)) {
			// put instance of the UMLDiagnostician into context map to identify first invocation
			// (validate is called recursively)
			context.put(this, null);
			BasicDiagnostic newDiagChain = createDefaultDiagnostic(eObject);
			boolean ok = super.validate(eObject, newDiagChain, context);
			// replace markers here instead of using a validation adapter, see
			// bug 410457 - [Validation] Ghost markers when validating profile constraints
			// bug 410119 - [Validation] markers related to stereotype applications are not updated in diagrams
			// bug 410059 - [Validation] delete subtree does not remove markers associated with stereotypes
			for (Diagnostic d : newDiagChain.getChildren()) {
				Object data[] = d.getData().toArray();
				if (data.length > 0) {
					Object target = data[0];
					if (target instanceof EObject) {
						EObject base = UMLUtil.getBaseElement((EObject) target);
						if (base != null) {
							data[0] = base;
						}
					}
				}
				diagnostics.add(new BasicDiagnostic(
						d.getSeverity(), d.getSource(), d.getCode(), d.getMessage(), data));
			}
			return ok;
		} else {
			return super.validate(eObject, diagnostics, context);
		}
	}

	protected boolean validateStereotype;
}
