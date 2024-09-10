/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bugs 539694, 570486
 *  
 * 
 */
package org.eclipse.papyrus.infra.core.architecture.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.papyrus.infra.core.architecture.*;
import org.eclipse.papyrus.infra.core.architecture.impl.ArchitecturePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage
 * @generated
 */
public class ArchitectureValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ArchitectureValidator INSTANCE = new ArchitectureValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.infra.core.architecture"; //$NON-NLS-1$

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Ceation Command Class Exists' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__CEATION_COMMAND_CLASS_EXISTS = 1;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Conversion Command Class Exists' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS_EXISTS = 2;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Context Extensions Are Consistent' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__CONTEXT_EXTENSIONS_ARE_CONSISTENT = 3;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Context Generalization Is Consistent' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__CONTEXT_GENERALIZATION_IS_CONSISTENT = 4;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Creation Command Class Required' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS_REQUIRED = 5;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Extension Cycle' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__EXTENSION_CYCLE = 6;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Generalization Cycle' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__GENERALIZATION_CYCLE = 7;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'General Not Extended' of 'Context'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int ARCHITECTURE_CONTEXT__GENERAL_NOT_EXTENDED = 8;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 8;

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
	public ArchitectureValidator() {
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
	  return ArchitecturePackage.eINSTANCE;
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
			case ArchitecturePackage.AD_ELEMENT:
				return validateADElement((ADElement)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_DOMAIN:
				return validateArchitectureDomain((ArchitectureDomain)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE:
				return validateArchitectureDescriptionLanguage((ArchitectureDescriptionLanguage)value, diagnostics, context);
			case ArchitecturePackage.STAKEHOLDER:
				return validateStakeholder((Stakeholder)value, diagnostics, context);
			case ArchitecturePackage.CONCERN:
				return validateConcern((Concern)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT:
				return validateArchitectureViewpoint((ArchitectureViewpoint)value, diagnostics, context);
			case ArchitecturePackage.REPRESENTATION_KIND:
				return validateRepresentationKind((RepresentationKind)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT:
				return validateArchitectureContext((ArchitectureContext)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_FRAMEWORK:
				return validateArchitectureFramework((ArchitectureFramework)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION:
				return validateArchitectureDescription((ArchitectureDescription)value, diagnostics, context);
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_PREFERENCES:
				return validateArchitectureDescriptionPreferences((ArchitectureDescriptionPreferences)value, diagnostics, context);
			case ArchitecturePackage.TREE_VIEWER_CONFIGURATION:
				return validateTreeViewerConfiguration((TreeViewerConfiguration)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateADElement(ADElement adElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(adElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureDomain(ArchitectureDomain architectureDomain, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(architectureDomain, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureDescriptionLanguage(ArchitectureDescriptionLanguage architectureDescriptionLanguage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(architectureDescriptionLanguage, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_ceationCommandClassExists(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_conversionCommandClassExists(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextExtensionsAreConsistent(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextGeneralizationIsConsistent(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_creationCommandClassRequired(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_extensionCycle(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalizationCycle(architectureDescriptionLanguage, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalNotExtended(architectureDescriptionLanguage, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStakeholder(Stakeholder stakeholder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stakeholder, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConcern(Concern concern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(concern, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureViewpoint(ArchitectureViewpoint architectureViewpoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(architectureViewpoint, diagnostics, context);
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
	public boolean validateArchitectureContext(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(architectureContext, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_ceationCommandClassExists(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_conversionCommandClassExists(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextExtensionsAreConsistent(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextGeneralizationIsConsistent(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_creationCommandClassRequired(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_extensionCycle(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalizationCycle(architectureContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalNotExtended(architectureContext, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ceationCommandClassExists constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_ceationCommandClassExists(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.ceationCommandClassExists(diagnostics, context);
	}

	/**
	 * Validates the conversionCommandClassExists constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_conversionCommandClassExists(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.conversionCommandClassExists(diagnostics, context);
	}

	/**
	 * Validates the contextExtensionsAreConsistent constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_contextExtensionsAreConsistent(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.contextExtensionsAreConsistent(diagnostics, context);
	}

	/**
	 * Validates the contextGeneralizationIsConsistent constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_contextGeneralizationIsConsistent(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.contextGeneralizationIsConsistent(diagnostics, context);
	}

	/**
	 * Validates the creationCommandClassRequired constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_creationCommandClassRequired(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.creationCommandClassRequired(diagnostics, context);
	}

	/**
	 * Validates the extensionCycle constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_extensionCycle(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.extensionCycle(diagnostics, context);
	}

	/**
	 * Validates the generalizationCycle constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_generalizationCycle(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.generalizationCycle(diagnostics, context);
	}

	/**
	 * Validates the generalNotExtended constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureContext_generalNotExtended(ArchitectureContext architectureContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return architectureContext.generalNotExtended(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureFramework(ArchitectureFramework architectureFramework, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(architectureFramework, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_ceationCommandClassExists(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_conversionCommandClassExists(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextExtensionsAreConsistent(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_contextGeneralizationIsConsistent(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_creationCommandClassRequired(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_extensionCycle(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalizationCycle(architectureFramework, diagnostics, context);
		if (result || diagnostics != null) result &= validateArchitectureContext_generalNotExtended(architectureFramework, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureDescription(ArchitectureDescription architectureDescription, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(architectureDescription, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArchitectureDescriptionPreferences(ArchitectureDescriptionPreferences architectureDescriptionPreferences, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(architectureDescriptionPreferences, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTreeViewerConfiguration(TreeViewerConfiguration treeViewerConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(treeViewerConfiguration, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ArchitecturePlugin.INSTANCE;
	}

} //ArchitectureValidator
