/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types.util;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.papyrus.infra.types.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage
 * @generated
 */
public class ElementTypesConfigurationsValidator extends EObjectValidator {
	/**
	 * A validation context key set {@code true} to indicate that validation is happening in the loading
	 * of the Element Types Registry.
	 */
	public static final String CONTEXT_REGISTRY_LOADING = "org.eclipse.papyrus.infra.types.registry_loading"; //$NON-NLS-1$
	
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ElementTypesConfigurationsValidator INSTANCE = new ElementTypesConfigurationsValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.papyrus.infra.types";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

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
	public ElementTypesConfigurationsValidator() {
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
	  return ElementTypesConfigurationsPackage.eINSTANCE;
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION:
				return validateElementTypeSetConfiguration((ElementTypeSetConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.CONFIGURATION_ELEMENT:
				return validateConfigurationElement((ConfigurationElement)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION:
				return validateElementTypeConfiguration((ElementTypeConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ICON_ENTRY:
				return validateIconEntry((IconEntry)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION:
				return validateAbstractAdviceBindingConfiguration((AbstractAdviceBindingConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION:
				return validateAdviceConfiguration((AdviceConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.CONTAINER_CONFIGURATION:
				return validateContainerConfiguration((ContainerConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION:
				return validateAbstractMatcherConfiguration((AbstractMatcherConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION:
				return validateSpecializationTypeConfiguration((SpecializationTypeConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION:
				return validateAbstractEditHelperAdviceConfiguration((AbstractEditHelperAdviceConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.IDENTIFIED_CONFIGURATION:
				return validateIdentifiedConfiguration((IdentifiedConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.NAMED_CONFIGURATION:
				return validateNamedConfiguration((NamedConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ANNOTATION:
				return validateAnnotation((Annotation)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION:
				return validateMetamodelTypeConfiguration((MetamodelTypeConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.EDIT_HELPER_ADVICE_CONFIGURATION:
				return validateEditHelperAdviceConfiguration((EditHelperAdviceConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION:
				return validateAdviceBindingConfiguration((AdviceBindingConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION:
				return validateMatcherConfiguration((MatcherConfiguration)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.EXTERNALLY_REGISTERED_TYPE:
				return validateExternallyRegisteredType((ExternallyRegisteredType)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.EXTERNALLY_REGISTERED_ADVICE:
				return validateExternallyRegisteredAdvice((ExternallyRegisteredAdvice)value, diagnostics, context);
			case ElementTypesConfigurationsPackage.INHERITANCE_KIND:
				return validateInheritanceKind((InheritanceKind)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElementTypeSetConfiguration(ElementTypeSetConfiguration elementTypeSetConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(elementTypeSetConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConfigurationElement(ConfigurationElement configurationElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(configurationElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElementTypeConfiguration(ElementTypeConfiguration elementTypeConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(elementTypeConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIconEntry(IconEntry iconEntry, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(iconEntry, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractAdviceBindingConfiguration(AbstractAdviceBindingConfiguration abstractAdviceBindingConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(abstractAdviceBindingConfiguration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(abstractAdviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractAdviceBindingConfiguration_apply_to_all_types(abstractAdviceBindingConfiguration, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the apply_to_all_types constraint of '<em>Abstract Advice Binding Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES__EEXPRESSION = "applyToAllTypes = target.oclIsUndefined()";

	/**
	 * Validates the apply_to_all_types constraint of '<em>Abstract Advice Binding Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractAdviceBindingConfiguration_apply_to_all_types(AbstractAdviceBindingConfiguration abstractAdviceBindingConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ElementTypesConfigurationsPackage.Literals.ABSTRACT_ADVICE_BINDING_CONFIGURATION,
				 abstractAdviceBindingConfiguration,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL",
				 "apply_to_all_types",
				 ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdviceConfiguration(AdviceConfiguration adviceConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(adviceConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContainerConfiguration(ContainerConfiguration containerConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(containerConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractMatcherConfiguration(AbstractMatcherConfiguration abstractMatcherConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(abstractMatcherConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentifiedConfiguration(IdentifiedConfiguration identifiedConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(identifiedConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedConfiguration(NamedConfiguration namedConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(namedConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnnotation(Annotation annotation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(annotation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSpecializationTypeConfiguration(SpecializationTypeConfiguration specializationTypeConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(specializationTypeConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractEditHelperAdviceConfiguration(AbstractEditHelperAdviceConfiguration abstractEditHelperAdviceConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(abstractEditHelperAdviceConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetamodelTypeConfiguration(MetamodelTypeConfiguration metamodelTypeConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(metamodelTypeConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEditHelperAdviceConfiguration(EditHelperAdviceConfiguration editHelperAdviceConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(editHelperAdviceConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdviceBindingConfiguration(AdviceBindingConfiguration adviceBindingConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(adviceBindingConfiguration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(adviceBindingConfiguration, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractAdviceBindingConfiguration_apply_to_all_types(adviceBindingConfiguration, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMatcherConfiguration(MatcherConfiguration matcherConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(matcherConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExternallyRegisteredType(ExternallyRegisteredType externallyRegisteredType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(externallyRegisteredType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExternallyRegisteredAdvice(ExternallyRegisteredAdvice externallyRegisteredAdvice, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(externallyRegisteredAdvice, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(externallyRegisteredAdvice, diagnostics, context);
		if (result || diagnostics != null) result &= validateAbstractAdviceBindingConfiguration_apply_to_all_types(externallyRegisteredAdvice, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInheritanceKind(InheritanceKind inheritanceKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return Activator.INSTANCE;
	}
	
	@Override
	protected void reportConstraintDelegateViolation(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context, String constraint, int severity, String source, int code) {
		switch (eClass.getClassifierID()) {
		case ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION:
			switch (constraint) {
			case "apply_to_all_types": //$NON-NLS-1$
				// The constraint will fail for all existing models that haven't been edited since the constraint
				// was added. So, don't prevent the registry loading them, but still warn about the problem
				if (context != null && Boolean.TRUE.equals(context.get(CONTEXT_REGISTRY_LOADING))) {
					severity = Diagnostic.WARNING;
				}
				
				// How exactly did the constraint fail?
				AbstractAdviceBindingConfiguration advice = (AbstractAdviceBindingConfiguration) eObject;
				if (advice.isApplyToAllTypes()) {
					diagnostics.add(new BasicDiagnostic(severity,
							source,
							code,
							getString("_UI_apply_to_all_types_diagnostic", new Object[] { getObjectLabel(eObject, context) }), //$NON-NLS-1$
							new Object[] { eObject, ElementTypesConfigurationsPackage.Literals.ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES }));
				} else {
					diagnostics.add(new BasicDiagnostic(severity,
							source,
							code,
							getString("_UI_no_target_diagnostic", new Object[] { getObjectLabel(eObject, context) }), //$NON-NLS-1$
							new Object[] { eObject, ElementTypesConfigurationsPackage.Literals.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET }));
				}
				break;
			default:
				super.reportConstraintDelegateViolation(eClass, eObject, diagnostics, context, constraint, severity, source, code);
				break;
			}
			break;
		default:
			super.reportConstraintDelegateViolation(eClass, eObject, diagnostics, context, constraint, severity, source, code);
			break;
		}
	}

} //ElementTypesConfigurationsValidator
