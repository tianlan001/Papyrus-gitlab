/*****************************************************************************
 *  Copyright (c) 2018 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier  (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.internal;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.EFacetPackage;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.Facet;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetOperation;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetSet;

/**
 * This class is is used to overload the validator of a file EMF facet
 *
 */
public class EFacetValidator extends EObjectValidator {


	/**
	 * the constant that reference the eOperation image of emf Facet
	 */
	private static final String IMAGE_OVERRIDE = "image"; //$NON-NLS-1$
	/**
	 * the constant that reference the eOperation label of emf Facet
	 */
	private static final String LABEL_OVERRIDE = "label"; //$NON-NLS-1$
	public static EFacetValidator eInstance= new EFacetValidator();

	@Override
	protected EPackage getEPackage() {
		return EFacetPackage.eINSTANCE;
	}
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
		case EFacetPackage.FACET_OPERATION:
			return validateOperation((FacetOperation)value, diagnostics, context);
		default:
			return super.validate(classifierID, value, diagnostics, context);
		}
	}

	private boolean validateOperation(FacetOperation facetOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(facetOperation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(facetOperation, diagnostics, context);
		if (result || diagnostics != null) result &= validateOperation_validate(facetOperation, diagnostics, context);
		return result;
	}
	/**
	 * This method validate an operation in the facet on order to be successful understood by the EMF FACET engine
	 * @param facetOperation
	 * @param diagnostics
	 * @param context
	 * @return a boolean by taking in account the kind of the operation
	 */
	private boolean validateOperation_validate(FacetOperation facetOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean valid = true;
		if (diagnostics != null) {
			//here we test parameter by taking account the kind of override
			if( facetOperation.getOverride()!=null) {
				if (facetOperation.getOverride().getName().equals(LABEL_OVERRIDE)||
						facetOperation.getOverride().getName().equals(IMAGE_OVERRIDE)) {
					if (facetOperation.getEParameters().size()!=1) {
						valid=false;
					}
					if (!((EClass)facetOperation.getEParameters().get(0).getEType()).equals(EcorePackage.eINSTANCE.getEReference())) {
						diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
								EFacetValidator.DIAGNOSTIC_SOURCE,
								1, "The operation "+facetOperation.getName()+" that overrides 'label'must have a parameter typed by EReference '", //$NON-NLS-1$ //$NON-NLS-2$
								new Object[] { facetOperation }));
						valid=false;
					}
				}
			}
		}
		return valid;
	}

}
