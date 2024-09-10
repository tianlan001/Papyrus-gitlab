/*****************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation, CEA, and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Christian W. Damus (CEA) - Target EObject must be the diagnostic's first data element
 *   Ed Willink, Klaas Gadeyne, A. Radermacher - Bug 408215 
 *
 *****************************************************************************/


package org.eclipse.papyrus.uml.service.validation.internal;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.ocl.pivot.uml.internal.validation.UMLOCLEValidator;
import org.eclipse.papyrus.infra.services.validation.internal.EValidatorAdapter;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;


/**
 * An adapter that plugs the EMF Model Validation Service API into the {@link org.eclipse.emf.ecore.EValidator} API.
 * <p>
 * <strong>NOTE</strong> that this class was copied from the EMF Validation "Validation Adapter" example project as created by the EMF Validation SDK, and modified only to ensure that the {@link Diagnostic} created from an {@link IConstraintStatus} has the
 * original validation target element as the first element of the {@linkplain Diagnostic#getData() data list} because the {@linkplain IConstraintStatus#getResultLocus() result locus} of a constraint status is an unordered set.
 * </p>
 *
 * bug 405160 - avoid "false" errors by using the UMLValidator instead of EObjectValidator as base class
 */
public class OCLEValidatorAdapter
		extends EValidatorAdapter {

	/**
	 * Constructor.
	 *
	 */
	public OCLEValidatorAdapter(EValidator registeredValidator) {
		super(registeredValidator);
	}
	
	// Overridden to invoke OCLDelegateValidator
	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (eObject.eIsProxy()) {
			if (context != null && context.get(EObjectValidator.ROOT_OBJECT) != null) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, EObjectValidator.DIAGNOSTIC_SOURCE,
							EObjectValidator.EOBJECT__EVERY_PROXY_RESOLVES,
							// create message
							EcorePlugin.INSTANCE.getString("_UI_UnresolvedProxy_diagnostic", //$NON-NLS-1$
								new Object[] {
										EObjectValidator.getFeatureLabel(eObject.eContainmentFeature(), context),
										EObjectValidator.getObjectLabel(eObject.eContainer(), context),
										EObjectValidator.getObjectLabel(eObject, context) }
							),
							new Object[] {
									eObject.eContainer(),
									eObject.eContainmentFeature(),
									eObject }
							));
				}
			}
		}
		if (eObject instanceof InstanceSpecification) {
			UMLOCLEValidator.INSTANCE.validateInstanceSpecification((InstanceSpecification) eObject, diagnostics, context);
		}
		else if (eObject instanceof OpaqueAction) {
			UMLOCLEValidator.INSTANCE.validateOpaqueAction((OpaqueAction) eObject, diagnostics, context);
		}
		else if (eObject instanceof OpaqueBehavior) {
			return UMLOCLEValidator.INSTANCE.validateOpaqueBehavior((OpaqueBehavior) eObject, diagnostics, context);
		}
		else if (eObject instanceof OpaqueExpression) {
			return UMLOCLEValidator.INSTANCE.validateOpaqueExpression((OpaqueExpression) eObject, diagnostics, context);
		}
		registeredValidator.validate(eClass, eObject, diagnostics, context);

		return batchValidate(eObject, diagnostics, context);
	}
}