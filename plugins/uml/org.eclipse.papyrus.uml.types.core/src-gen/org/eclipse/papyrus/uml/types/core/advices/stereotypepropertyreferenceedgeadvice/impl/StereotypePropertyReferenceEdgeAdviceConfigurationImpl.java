/**
 * Copyright (c) 2017, 2020 CEA LIST, Christian W. Damus, and others.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 568782
 */
  
package org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl#getFeatureToSet <em>Feature To Set</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl#getEdgeLabel <em>Edge Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StereotypePropertyReferenceEdgeAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements StereotypePropertyReferenceEdgeAdviceConfiguration {
	/**
	 * The default value of the '{@link #getFeatureToSet() <em>Feature To Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureToSet()
	 * @generated
	 * @ordered
	 */
	protected static final String FEATURE_TO_SET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFeatureToSet() <em>Feature To Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureToSet()
	 * @generated
	 * @ordered
	 */
	protected String featureToSet = FEATURE_TO_SET_EDEFAULT;

	/**
	 * The default value of the '{@link #getStereotypeQualifiedName() <em>Stereotype Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypeQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String STEREOTYPE_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStereotypeQualifiedName() <em>Stereotype Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypeQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String stereotypeQualifiedName = STEREOTYPE_QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEdgeLabel() <em>Edge Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String EDGE_LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEdgeLabel() <em>Edge Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeLabel()
	 * @generated
	 * @ordered
	 */
	protected String edgeLabel = EDGE_LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StereotypePropertyReferenceEdgeAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StereotypePropertyReferenceEdgeAdvicePackage.Literals.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFeatureToSet() {
		return featureToSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatureToSet(String newFeatureToSet) {
		String oldFeatureToSet = featureToSet;
		featureToSet = newFeatureToSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET, oldFeatureToSet, featureToSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStereotypeQualifiedName() {
		return stereotypeQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStereotypeQualifiedName(String newStereotypeQualifiedName) {
		String oldStereotypeQualifiedName = stereotypeQualifiedName;
		stereotypeQualifiedName = newStereotypeQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME, oldStereotypeQualifiedName, stereotypeQualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEdgeLabel() {
		return edgeLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEdgeLabel(String newEdgeLabel) {
		String oldEdgeLabel = edgeLabel;
		edgeLabel = newEdgeLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL, oldEdgeLabel, edgeLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET:
				return getFeatureToSet();
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
				return getStereotypeQualifiedName();
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL:
				return getEdgeLabel();
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
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET:
				setFeatureToSet((String)newValue);
				return;
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
				setStereotypeQualifiedName((String)newValue);
				return;
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL:
				setEdgeLabel((String)newValue);
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
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET:
				setFeatureToSet(FEATURE_TO_SET_EDEFAULT);
				return;
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
				setStereotypeQualifiedName(STEREOTYPE_QUALIFIED_NAME_EDEFAULT);
				return;
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL:
				setEdgeLabel(EDGE_LABEL_EDEFAULT);
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
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET:
				return FEATURE_TO_SET_EDEFAULT == null ? featureToSet != null : !FEATURE_TO_SET_EDEFAULT.equals(featureToSet);
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
				return STEREOTYPE_QUALIFIED_NAME_EDEFAULT == null ? stereotypeQualifiedName != null : !STEREOTYPE_QUALIFIED_NAME_EDEFAULT.equals(stereotypeQualifiedName);
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL:
				return EDGE_LABEL_EDEFAULT == null ? edgeLabel != null : !EDGE_LABEL_EDEFAULT.equals(edgeLabel);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (featureToSet: ");
		result.append(featureToSet);
		result.append(", stereotypeQualifiedName: ");
		result.append(stereotypeQualifiedName);
		result.append(", edgeLabel: ");
		result.append(edgeLabel);
		result.append(')');
		return result.toString();
	}

} //StereotypePropertyReferenceEdgeAdviceConfigurationImpl
