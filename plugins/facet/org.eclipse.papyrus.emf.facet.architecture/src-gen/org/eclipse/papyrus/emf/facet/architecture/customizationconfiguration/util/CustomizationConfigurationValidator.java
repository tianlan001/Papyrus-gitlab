/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util;

import java.util.Map;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage
 * @generated
 */
public class CustomizationConfigurationValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final CustomizationConfigurationValidator INSTANCE = new CustomizationConfigurationValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration"; //$NON-NLS-1$

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
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CustomizationConfigurationValidator() {
		super();
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
		return CustomizationConfigurationPackage.eINSTANCE;
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
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION:
			return validateEMFFacetTreeViewerConfiguration((EMFFacetTreeViewerConfiguration) value, diagnostics, context);
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE:
			return validateCustomizationReference((CustomizationReference) value, diagnostics, context);
		case CustomizationConfigurationPackage.IAPPLICATION_RULE:
			return validateIApplicationRule((IApplicationRule) value, diagnostics, context);
		case CustomizationConfigurationPackage.ABSOLUTE_ORDER:
			return validateAbsoluteOrder((AbsoluteOrder) value, diagnostics, context);
		case CustomizationConfigurationPackage.RELATIVE_ORDER:
			return validateRelativeOrder((RelativeOrder) value, diagnostics, context);
		case CustomizationConfigurationPackage.REDEFINITION:
			return validateRedefinition((Redefinition) value, diagnostics, context);
		case CustomizationConfigurationPackage.LOCATION:
			return validateLocation((Location) value, diagnostics, context);
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
	public boolean validateEMFFacetTreeViewerConfiguration(EMFFacetTreeViewerConfiguration emfFacetTreeViewerConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(emfFacetTreeViewerConfiguration, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(emfFacetTreeViewerConfiguration, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateEMFFacetTreeViewerConfiguration_checkConfiguration(emfFacetTreeViewerConfiguration, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the checkConfiguration constraint of '<em>EMF Facet Tree Viewer Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEMFFacetTreeViewerConfiguration_checkConfiguration(EMFFacetTreeViewerConfiguration emfFacetTreeViewerConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic", //$NON-NLS-1$
						new Object[] { "checkConfiguration", getObjectLabel(emfFacetTreeViewerConfiguration, context) }, //$NON-NLS-1$
						new Object[] { emfFacetTreeViewerConfiguration },
						context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateCustomizationReference(CustomizationReference customizationReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(customizationReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(customizationReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(customizationReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateCustomizationReference_checkCustomizationReference(customizationReference, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the checkCustomizationReference constraint of '<em>Customization Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateCustomizationReference_checkCustomizationReference(CustomizationReference customizationReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic", //$NON-NLS-1$
						new Object[] { "checkCustomizationReference", getObjectLabel(customizationReference, context) }, //$NON-NLS-1$
						new Object[] { customizationReference },
						context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateIApplicationRule(IApplicationRule iApplicationRule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(iApplicationRule, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAbsoluteOrder(AbsoluteOrder absoluteOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(absoluteOrder, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(absoluteOrder, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(absoluteOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateAbsoluteOrder_checkAbsoluteOrder(absoluteOrder, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the checkAbsoluteOrder constraint of '<em>Absolute Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAbsoluteOrder_checkAbsoluteOrder(AbsoluteOrder absoluteOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic", //$NON-NLS-1$
						new Object[] { "checkAbsoluteOrder", getObjectLabel(absoluteOrder, context) }, //$NON-NLS-1$
						new Object[] { absoluteOrder },
						context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRelativeOrder(RelativeOrder relativeOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(relativeOrder, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(relativeOrder, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(relativeOrder, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateRelativeOrder_checkRelativeOrder(relativeOrder, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the checkRelativeOrder constraint of '<em>Relative Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRelativeOrder_checkRelativeOrder(RelativeOrder relativeOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic", //$NON-NLS-1$
						new Object[] { "checkRelativeOrder", getObjectLabel(relativeOrder, context) }, //$NON-NLS-1$
						new Object[] { relativeOrder },
						context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRedefinition(Redefinition redefinition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(redefinition, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(redefinition, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(redefinition, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateRedefinition_checkRedefinition(redefinition, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the checkRedefinition constraint of '<em>Redefinition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRedefinition_checkRedefinition(Redefinition redefinition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR,
						DIAGNOSTIC_SOURCE,
						0,
						"_UI_GenericConstraint_diagnostic", //$NON-NLS-1$
						new Object[] { "checkRedefinition", getObjectLabel(redefinition, context) }, //$NON-NLS-1$
						new Object[] { redefinition },
						context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLocation(Location location, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} // CustomizationConfigurationValidator
