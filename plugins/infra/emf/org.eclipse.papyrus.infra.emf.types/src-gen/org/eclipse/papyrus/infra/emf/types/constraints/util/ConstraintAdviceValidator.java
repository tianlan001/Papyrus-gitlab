/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.constraints.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.*;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.AnyReference;
import org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter;
import org.eclipse.papyrus.infra.emf.types.constraints.Reference;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;
import org.eclipse.papyrus.infra.emf.types.rules.container.provider.InvariantContainerRuleEditPlugin;
import org.eclipse.papyrus.infra.filters.OperatorKind;
import org.eclipse.papyrus.infra.types.util.ElementTypesConfigurationsValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage
 * @generated
 */
public class ConstraintAdviceValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final ConstraintAdviceValidator INSTANCE = new ConstraintAdviceValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.infra.emf.types.constraints";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypesConfigurationsValidator elementTypesConfigurationsValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ConstraintAdviceValidator() {
		super();
		elementTypesConfigurationsValidator = ElementTypesConfigurationsValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return ConstraintAdvicePackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION:
			return validateConstraintAdviceConfiguration((ConstraintAdviceConfiguration) value, diagnostics, context);
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT:
			return validateAdviceConstraint((AdviceConstraint) value, diagnostics, context);
		case ConstraintAdvicePackage.COMPOSITE_CONSTRAINT:
			return validateCompositeConstraint((CompositeConstraint) value, diagnostics, context);
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT:
			return validateReferenceConstraint((ReferenceConstraint) value, diagnostics, context);
		case ConstraintAdvicePackage.REFERENCE_PERMISSION:
			return validateReferencePermission((ReferencePermission) value, diagnostics, context);
		case ConstraintAdvicePackage.ANY_REFERENCE:
			return validateAnyReference((AnyReference) value, diagnostics, context);
		case ConstraintAdvicePackage.REFERENCE:
			return validateReference((Reference) value, diagnostics, context);
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER:
			return validateElementTypeFilter((ElementTypeFilter) value, diagnostics, context);
		case ConstraintAdvicePackage.RELATIONSHIP_CONSTRAINT:
			return validateRelationshipConstraint((RelationshipConstraint) value, diagnostics, context);
		case ConstraintAdvicePackage.END_PERMISSION:
			return validateEndPermission((EndPermission) value, diagnostics, context);
		case ConstraintAdvicePackage.REFERENCE_KIND:
			return validateReferenceKind((ReferenceKind) value, diagnostics, context);
		case ConstraintAdvicePackage.ELEMENT_TYPE_RELATIONSHIP_KIND:
			return validateElementTypeRelationshipKind((ElementTypeRelationshipKind) value, diagnostics, context);
		case ConstraintAdvicePackage.END_KIND:
			return validateEndKind((EndKind) value, diagnostics, context);
		case ConstraintAdvicePackage.EDIT_COMMAND_REQUEST:
			return validateEditCommandRequest((IEditCommandRequest) value, diagnostics, context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateConstraintAdviceConfiguration(ConstraintAdviceConfiguration constraintAdviceConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(constraintAdviceConfiguration, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(constraintAdviceConfiguration, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(constraintAdviceConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= elementTypesConfigurationsValidator.validateAbstractAdviceBindingConfiguration_apply_to_all_types(constraintAdviceConfiguration, diagnostics, context);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAdviceConstraint(AdviceConstraint adviceConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(adviceConstraint, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateReferenceConstraint(ReferenceConstraint referenceConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(referenceConstraint, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateReferencePermission(ReferencePermission referencePermission, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(referencePermission, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyReference(AnyReference anyReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(anyReference, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateReference(Reference reference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(reference, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateElementTypeFilter(ElementTypeFilter elementTypeFilter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(elementTypeFilter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRelationshipConstraint(RelationshipConstraint relationshipConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(relationshipConstraint, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEndPermission(EndPermission endPermission, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(endPermission, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateCompositeConstraint(CompositeConstraint compositeConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(compositeConstraint, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(compositeConstraint, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(compositeConstraint, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateCompositeConstraint_operandCount(compositeConstraint, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the operandCount constraint of '<em>Composite Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public boolean validateCompositeConstraint_operandCount(CompositeConstraint compositeConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = compositeConstraint.getOperator() == OperatorKind.NOT
				? compositeConstraint.getConstraints().size() == 1
				: compositeConstraint.getConstraints().size() >= 2;

		if (!result && diagnostics != null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR,
					DIAGNOSTIC_SOURCE,
					0,
					compositeConstraint.getOperator() == OperatorKind.NOT ? "_UI_operandCount_diagnostic_one" : "_UI_operandCount_diagnostic_atLeastTwo", //$NON-NLS-1$//$NON-NLS-2$
					new Object[] { getObjectLabel(compositeConstraint, context) },
					new Object[] { compositeConstraint, ConstraintAdvicePackage.Literals.COMPOSITE_CONSTRAINT__CONSTRAINT },
					context));
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateReferenceKind(ReferenceKind referenceKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateElementTypeRelationshipKind(ElementTypeRelationshipKind elementTypeRelationshipKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEndKind(EndKind endKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEditCommandRequest(IEditCommandRequest editCommandRequest, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return InvariantContainerRuleEditPlugin.INSTANCE;
	}

} // ConstraintAdviceValidator
