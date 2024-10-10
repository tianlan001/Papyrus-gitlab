/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl;

import java.util.Collection;

import org.eclipse.eef.EEFContainerDescription;
import org.eclipse.eef.EEFControlDescription;
import org.eclipse.eef.EEFLayoutDescription;
import org.eclipse.eef.EefPackage;

import org.eclipse.eef.impl.EEFControlDescriptionImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EEF Stereotype Application Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl#getControls <em>Controls</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl#getLayout <em>Layout</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl#getHelpExpression <em>Help Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EEFStereotypeApplicationDescriptionImpl extends EEFControlDescriptionImpl implements EEFStereotypeApplicationDescription {
	/**
	 * The cached value of the '{@link #getControls() <em>Controls</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControls()
	 * @generated
	 * @ordered
	 */
	protected EList<EEFControlDescription> controls;

	/**
	 * The cached value of the '{@link #getLayout() <em>Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected EEFLayoutDescription layout;

	/**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
	protected String helpExpression = HELP_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEFStereotypeApplicationDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EefAdvancedControlsPackage.Literals.EEF_STEREOTYPE_APPLICATION_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EEFControlDescription> getControls() {
		if (controls == null) {
			controls = new EObjectContainmentEList.Resolving<EEFControlDescription>(EEFControlDescription.class, this, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS);
		}
		return controls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFLayoutDescription getLayout() {
		if (layout != null && layout.eIsProxy()) {
			InternalEObject oldLayout = (InternalEObject)layout;
			layout = (EEFLayoutDescription)eResolveProxy(oldLayout);
			if (layout != oldLayout) {
				InternalEObject newLayout = (InternalEObject)layout;
				NotificationChain msgs = oldLayout.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, null);
				if (newLayout.eInternalContainer() == null) {
					msgs = newLayout.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, oldLayout, layout));
			}
		}
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFLayoutDescription basicGetLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLayout(EEFLayoutDescription newLayout, NotificationChain msgs) {
		EEFLayoutDescription oldLayout = layout;
		layout = newLayout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, oldLayout, newLayout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayout(EEFLayoutDescription newLayout) {
		if (newLayout != layout) {
			NotificationChain msgs = null;
			if (layout != null)
				msgs = ((InternalEObject)layout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, msgs);
			if (newLayout != null)
				msgs = ((InternalEObject)newLayout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, msgs);
			msgs = basicSetLayout(newLayout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, newLayout, newLayout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabelExpression() {
		return labelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelpExpression() {
		return helpExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelpExpression(String newHelpExpression) {
		String oldHelpExpression = helpExpression;
		helpExpression = newHelpExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION, oldHelpExpression, helpExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				return ((InternalEList<?>)getControls()).basicRemove(otherEnd, msgs);
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				return basicSetLayout(null, msgs);
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
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				return getControls();
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				if (resolve) return getLayout();
				return basicGetLayout();
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
				return getHelpExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				getControls().clear();
				getControls().addAll((Collection<? extends EEFControlDescription>)newValue);
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				setLayout((EEFLayoutDescription)newValue);
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
				setHelpExpression((String)newValue);
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
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				getControls().clear();
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				setLayout((EEFLayoutDescription)null);
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
				setHelpExpression(HELP_EXPRESSION_EDEFAULT);
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
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				return controls != null && !controls.isEmpty();
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				return layout != null;
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
				return HELP_EXPRESSION_EDEFAULT == null ? helpExpression != null : !HELP_EXPRESSION_EDEFAULT.equals(helpExpression);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == EEFContainerDescription.class) {
			switch (derivedFeatureID) {
				case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS: return EefPackage.EEF_CONTAINER_DESCRIPTION__CONTROLS;
				case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT: return EefPackage.EEF_CONTAINER_DESCRIPTION__LAYOUT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == EEFContainerDescription.class) {
			switch (baseFeatureID) {
				case EefPackage.EEF_CONTAINER_DESCRIPTION__CONTROLS: return EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS;
				case EefPackage.EEF_CONTAINER_DESCRIPTION__LAYOUT: return EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (labelExpression: "); //$NON-NLS-1$
		result.append(labelExpression);
		result.append(", helpExpression: "); //$NON-NLS-1$
		result.append(helpExpression);
		result.append(')');
		return result.toString();
	}

} //EEFStereotypeApplicationDescriptionImpl
