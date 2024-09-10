/**
 * Copyright (c) 2015 CEA LIST.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *  
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage
 * @generated
 */
public class ExpansionModelValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ExpansionModelValidator INSTANCE = new ExpansionModelValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel";

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate' of 'Abstract Representation'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ABSTRACT_REPRESENTATION__VALIDATE = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpansionModelValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return ExpansionModelPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case ExpansionModelPackage.REPRESENTATION:
				return validateRepresentation((Representation)value, diagnostics, context);
			case ExpansionModelPackage.ABSTRACT_REPRESENTATION:
				return validateAbstractRepresentation((AbstractRepresentation)value, diagnostics, context);
			case ExpansionModelPackage.REPRESENTATION_KIND:
				return validateRepresentationKind((RepresentationKind)value, diagnostics, context);
			case ExpansionModelPackage.INDUCED_REPRESENTATION:
				return validateInducedRepresentation((InducedRepresentation)value, diagnostics, context);
			case ExpansionModelPackage.GRAPHICAL_ELEMENT_LIBRARY:
				return validateGraphicalElementLibrary((GraphicalElementLibrary)value, diagnostics, context);
			case ExpansionModelPackage.USE_CONTEXT:
				return validateUseContext((UseContext)value, diagnostics, context);
			case ExpansionModelPackage.GMFT_BASED_REPRESENTATION:
				return validateGMFT_BasedRepresentation((GMFT_BasedRepresentation)value, diagnostics, context);
			case ExpansionModelPackage.DIAGRAM_EXPANSION:
				return validateDiagramExpansion((DiagramExpansion)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRepresentation(Representation representation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(representation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(representation, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractRepresentation_validate(representation, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractRepresentation(AbstractRepresentation abstractRepresentation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(abstractRepresentation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(abstractRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractRepresentation_validate(abstractRepresentation, diagnostics, context);
		return result;
	}

	/**
	 * Validates the validate constraint of '<em>Abstract Representation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractRepresentation_validate(AbstractRepresentation abstractRepresentation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return abstractRepresentation.validate(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRepresentationKind(RepresentationKind representationKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(representationKind, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInducedRepresentation(InducedRepresentation inducedRepresentation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(inducedRepresentation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(inducedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractRepresentation_validate(inducedRepresentation, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraphicalElementLibrary(GraphicalElementLibrary graphicalElementLibrary, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(graphicalElementLibrary, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUseContext(UseContext useContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(useContext, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGMFT_BasedRepresentation(GMFT_BasedRepresentation gmfT_BasedRepresentation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(gmfT_BasedRepresentation, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(gmfT_BasedRepresentation, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractRepresentation_validate(gmfT_BasedRepresentation, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDiagramExpansion(DiagramExpansion diagramExpansion, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(diagramExpansion, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ExpansionModelValidator
