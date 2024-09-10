/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - bug 496176
 * 
 */
package org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advanced Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#getMappingParameters <em>Mapping Parameters</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#isRemoveUnmappedDiagrams <em>Remove Unmapped Diagrams</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#isConvertOpaqueExpressionToLiteralString <em>Convert Opaque Expression To Literal String</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#isRemoveUnmappedProfilesAndStereotypes <em>Remove Unmapped Profiles And Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#isRemoveUnmappedAnnotations <em>Remove Unmapped Annotations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl#isAlwaysAcceptSuggestedMappings <em>Always Accept Suggested Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdvancedConfigImpl extends ThreadConfigImpl implements AdvancedConfig {
	/**
	 * The cached value of the '{@link #getMappingParameters() <em>Mapping Parameters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingParameters()
	 * @generated
	 * @ordered
	 */
	protected MappingParameters mappingParameters;

	/**
	 * The default value of the '{@link #isRemoveUnmappedDiagrams() <em>Remove Unmapped Diagrams</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedDiagrams()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMOVE_UNMAPPED_DIAGRAMS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRemoveUnmappedDiagrams() <em>Remove Unmapped Diagrams</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedDiagrams()
	 * @generated
	 * @ordered
	 */
	protected boolean removeUnmappedDiagrams = REMOVE_UNMAPPED_DIAGRAMS_EDEFAULT;

	/**
	 * The default value of the '{@link #isConvertOpaqueExpressionToLiteralString() <em>Convert Opaque Expression To Literal String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConvertOpaqueExpressionToLiteralString()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isConvertOpaqueExpressionToLiteralString() <em>Convert Opaque Expression To Literal String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConvertOpaqueExpressionToLiteralString()
	 * @generated
	 * @ordered
	 */
	protected boolean convertOpaqueExpressionToLiteralString = CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #isRemoveUnmappedProfilesAndStereotypes() <em>Remove Unmapped Profiles And Stereotypes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedProfilesAndStereotypes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRemoveUnmappedProfilesAndStereotypes() <em>Remove Unmapped Profiles And Stereotypes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedProfilesAndStereotypes()
	 * @generated
	 * @ordered
	 */
	protected boolean removeUnmappedProfilesAndStereotypes = REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES_EDEFAULT;

	/**
	 * The default value of the '{@link #isRemoveUnmappedAnnotations() <em>Remove Unmapped Annotations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedAnnotations()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMOVE_UNMAPPED_ANNOTATIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRemoveUnmappedAnnotations() <em>Remove Unmapped Annotations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoveUnmappedAnnotations()
	 * @generated
	 * @ordered
	 */
	protected boolean removeUnmappedAnnotations = REMOVE_UNMAPPED_ANNOTATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #isAlwaysAcceptSuggestedMappings() <em>Always Accept Suggested Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlwaysAcceptSuggestedMappings()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALWAYS_ACCEPT_SUGGESTED_MAPPINGS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAlwaysAcceptSuggestedMappings() <em>Always Accept Suggested Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlwaysAcceptSuggestedMappings()
	 * @generated
	 * @ordered
	 */
	protected boolean alwaysAcceptSuggestedMappings = ALWAYS_ACCEPT_SUGGESTED_MAPPINGS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdvancedConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MigrationParametersPackage.Literals.ADVANCED_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappingParameters getMappingParameters() {
		return mappingParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMappingParameters(MappingParameters newMappingParameters, NotificationChain msgs) {
		MappingParameters oldMappingParameters = mappingParameters;
		mappingParameters = newMappingParameters;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS, oldMappingParameters, newMappingParameters);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappingParameters(MappingParameters newMappingParameters) {
		if (newMappingParameters != mappingParameters) {
			NotificationChain msgs = null;
			if (mappingParameters != null)
				msgs = ((InternalEObject)mappingParameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS, null, msgs);
			if (newMappingParameters != null)
				msgs = ((InternalEObject)newMappingParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS, null, msgs);
			msgs = basicSetMappingParameters(newMappingParameters, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS, newMappingParameters, newMappingParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRemoveUnmappedDiagrams() {
		return removeUnmappedDiagrams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoveUnmappedDiagrams(boolean newRemoveUnmappedDiagrams) {
		boolean oldRemoveUnmappedDiagrams = removeUnmappedDiagrams;
		removeUnmappedDiagrams = newRemoveUnmappedDiagrams;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS, oldRemoveUnmappedDiagrams, removeUnmappedDiagrams));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConvertOpaqueExpressionToLiteralString() {
		return convertOpaqueExpressionToLiteralString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConvertOpaqueExpressionToLiteralString(boolean newConvertOpaqueExpressionToLiteralString) {
		boolean oldConvertOpaqueExpressionToLiteralString = convertOpaqueExpressionToLiteralString;
		convertOpaqueExpressionToLiteralString = newConvertOpaqueExpressionToLiteralString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING, oldConvertOpaqueExpressionToLiteralString, convertOpaqueExpressionToLiteralString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRemoveUnmappedProfilesAndStereotypes() {
		return removeUnmappedProfilesAndStereotypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoveUnmappedProfilesAndStereotypes(boolean newRemoveUnmappedProfilesAndStereotypes) {
		boolean oldRemoveUnmappedProfilesAndStereotypes = removeUnmappedProfilesAndStereotypes;
		removeUnmappedProfilesAndStereotypes = newRemoveUnmappedProfilesAndStereotypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES, oldRemoveUnmappedProfilesAndStereotypes, removeUnmappedProfilesAndStereotypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRemoveUnmappedAnnotations() {
		return removeUnmappedAnnotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoveUnmappedAnnotations(boolean newRemoveUnmappedAnnotations) {
		boolean oldRemoveUnmappedAnnotations = removeUnmappedAnnotations;
		removeUnmappedAnnotations = newRemoveUnmappedAnnotations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS, oldRemoveUnmappedAnnotations, removeUnmappedAnnotations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAlwaysAcceptSuggestedMappings() {
		return alwaysAcceptSuggestedMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlwaysAcceptSuggestedMappings(boolean newAlwaysAcceptSuggestedMappings) {
		boolean oldAlwaysAcceptSuggestedMappings = alwaysAcceptSuggestedMappings;
		alwaysAcceptSuggestedMappings = newAlwaysAcceptSuggestedMappings;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MigrationParametersPackage.ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS, oldAlwaysAcceptSuggestedMappings, alwaysAcceptSuggestedMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS:
				return basicSetMappingParameters(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS:
				return getMappingParameters();
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS:
				return isRemoveUnmappedDiagrams();
			case MigrationParametersPackage.ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING:
				return isConvertOpaqueExpressionToLiteralString();
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES:
				return isRemoveUnmappedProfilesAndStereotypes();
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS:
				return isRemoveUnmappedAnnotations();
			case MigrationParametersPackage.ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS:
				return isAlwaysAcceptSuggestedMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS:
				setMappingParameters((MappingParameters)newValue);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS:
				setRemoveUnmappedDiagrams((Boolean)newValue);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING:
				setConvertOpaqueExpressionToLiteralString((Boolean)newValue);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES:
				setRemoveUnmappedProfilesAndStereotypes((Boolean)newValue);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS:
				setRemoveUnmappedAnnotations((Boolean)newValue);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS:
				setAlwaysAcceptSuggestedMappings((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS:
				setMappingParameters((MappingParameters)null);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS:
				setRemoveUnmappedDiagrams(REMOVE_UNMAPPED_DIAGRAMS_EDEFAULT);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING:
				setConvertOpaqueExpressionToLiteralString(CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING_EDEFAULT);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES:
				setRemoveUnmappedProfilesAndStereotypes(REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES_EDEFAULT);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS:
				setRemoveUnmappedAnnotations(REMOVE_UNMAPPED_ANNOTATIONS_EDEFAULT);
				return;
			case MigrationParametersPackage.ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS:
				setAlwaysAcceptSuggestedMappings(ALWAYS_ACCEPT_SUGGESTED_MAPPINGS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MigrationParametersPackage.ADVANCED_CONFIG__MAPPING_PARAMETERS:
				return mappingParameters != null;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS:
				return removeUnmappedDiagrams != REMOVE_UNMAPPED_DIAGRAMS_EDEFAULT;
			case MigrationParametersPackage.ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING:
				return convertOpaqueExpressionToLiteralString != CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING_EDEFAULT;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES:
				return removeUnmappedProfilesAndStereotypes != REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES_EDEFAULT;
			case MigrationParametersPackage.ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS:
				return removeUnmappedAnnotations != REMOVE_UNMAPPED_ANNOTATIONS_EDEFAULT;
			case MigrationParametersPackage.ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS:
				return alwaysAcceptSuggestedMappings != ALWAYS_ACCEPT_SUGGESTED_MAPPINGS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (removeUnmappedDiagrams: "); //$NON-NLS-1$
		result.append(removeUnmappedDiagrams);
		result.append(", convertOpaqueExpressionToLiteralString: "); //$NON-NLS-1$
		result.append(convertOpaqueExpressionToLiteralString);
		result.append(", removeUnmappedProfilesAndStereotypes: "); //$NON-NLS-1$
		result.append(removeUnmappedProfilesAndStereotypes);
		result.append(", removeUnmappedAnnotations: "); //$NON-NLS-1$
		result.append(removeUnmappedAnnotations);
		result.append(", alwaysAcceptSuggestedMappings: "); //$NON-NLS-1$
		result.append(alwaysAcceptSuggestedMappings);
		result.append(')');
		return result.toString();
	}

} //AdvancedConfigImpl
