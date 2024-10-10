/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription;
import org.eclipse.sirius.properties.AbstractContainerDescription;
import org.eclipse.sirius.properties.ContainerDescription;
import org.eclipse.sirius.properties.ControlDescription;
import org.eclipse.sirius.properties.LayoutDescription;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stereotype Application Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getControls <em>Controls</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getLayout <em>Layout</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getExtends <em>Extends</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getFilterControlsFromExtendedContainerExpression <em>Filter Controls From Extended Container Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl#getHelpExpression <em>Help Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StereotypeApplicationDescriptionImpl extends WidgetDescriptionImpl implements StereotypeApplicationDescription {
	/**
	 * The cached value of the '{@link #getControls() <em>Controls</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControls()
	 * @generated
	 * @ordered
	 */
	protected EList<ControlDescription> controls;

	/**
	 * The cached value of the '{@link #getLayout() <em>Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected LayoutDescription layout;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected ContainerDescription extends_;

	/**
	 * The default value of the '{@link #getFilterControlsFromExtendedContainerExpression() <em>Filter Controls From Extended Container Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilterControlsFromExtendedContainerExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilterControlsFromExtendedContainerExpression() <em>Filter Controls From Extended Container Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilterControlsFromExtendedContainerExpression()
	 * @generated
	 * @ordered
	 */
	protected String filterControlsFromExtendedContainerExpression = FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION_EDEFAULT;

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
	protected StereotypeApplicationDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesAdvancedControlsPackage.Literals.STEREOTYPE_APPLICATION_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ControlDescription> getControls() {
		if (controls == null) {
			controls = new EObjectContainmentEList<ControlDescription>(ControlDescription.class, this, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS);
		}
		return controls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LayoutDescription getLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLayout(LayoutDescription newLayout, NotificationChain msgs) {
		LayoutDescription oldLayout = layout;
		layout = newLayout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, oldLayout, newLayout);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLayout(LayoutDescription newLayout) {
		if (newLayout != layout) {
			NotificationChain msgs = null;
			if (layout != null)
				msgs = ((InternalEObject) layout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, msgs);
			if (newLayout != null)
				msgs = ((InternalEObject) newLayout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, null, msgs);
			msgs = basicSetLayout(newLayout, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT, newLayout, newLayout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContainerDescription getExtends() {
		if (extends_ != null && extends_.eIsProxy()) {
			InternalEObject oldExtends = (InternalEObject) extends_;
			extends_ = (ContainerDescription) eResolveProxy(oldExtends);
			if (extends_ != oldExtends) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS, oldExtends, extends_));
			}
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainerDescription basicGetExtends() {
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExtends(ContainerDescription newExtends) {
		ContainerDescription oldExtends = extends_;
		extends_ = newExtends;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS, oldExtends, extends_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFilterControlsFromExtendedContainerExpression() {
		return filterControlsFromExtendedContainerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilterControlsFromExtendedContainerExpression(String newFilterControlsFromExtendedContainerExpression) {
		String oldFilterControlsFromExtendedContainerExpression = filterControlsFromExtendedContainerExpression;
		filterControlsFromExtendedContainerExpression = newFilterControlsFromExtendedContainerExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION, oldFilterControlsFromExtendedContainerExpression,
					filterControlsFromExtendedContainerExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabelExpression() {
		return labelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHelpExpression() {
		return helpExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHelpExpression(String newHelpExpression) {
		String oldHelpExpression = helpExpression;
		helpExpression = newHelpExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION, oldHelpExpression, helpExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
			return ((InternalEList<?>) getControls()).basicRemove(otherEnd, msgs);
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
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
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
			return getControls();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
			return getLayout();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
			if (resolve)
				return getExtends();
			return basicGetExtends();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
			return getFilterControlsFromExtendedContainerExpression();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			return getLabelExpression();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
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
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
			getControls().clear();
			getControls().addAll((Collection<? extends ControlDescription>) newValue);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
			setLayout((LayoutDescription) newValue);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
			setExtends((ContainerDescription) newValue);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
			setFilterControlsFromExtendedContainerExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			setHelpExpression((String) newValue);
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
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
			getControls().clear();
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
			setLayout((LayoutDescription) null);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
			setExtends((ContainerDescription) null);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
			setFilterControlsFromExtendedContainerExpression(FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
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
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
			return controls != null && !controls.isEmpty();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
			return layout != null;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
			return extends_ != null;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
			return FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION_EDEFAULT == null ? filterControlsFromExtendedContainerExpression != null : !FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION_EDEFAULT.equals(filterControlsFromExtendedContainerExpression);
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
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
		if (baseClass == AbstractContainerDescription.class) {
			switch (derivedFeatureID) {
			case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
				return PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS;
			case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
				return PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__LAYOUT;
			case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
				return PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__EXTENDS;
			case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
				return PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION;
			default:
				return -1;
			}
		}
		if (baseClass == ContainerDescription.class) {
			switch (derivedFeatureID) {
			default:
				return -1;
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
		if (baseClass == AbstractContainerDescription.class) {
			switch (baseFeatureID) {
			case PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS:
				return PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS;
			case PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__LAYOUT:
				return PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT;
			case PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__EXTENDS:
				return PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS;
			case PropertiesPackage.ABSTRACT_CONTAINER_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
				return PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION;
			default:
				return -1;
			}
		}
		if (baseClass == ContainerDescription.class) {
			switch (baseFeatureID) {
			default:
				return -1;
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (filterControlsFromExtendedContainerExpression: "); //$NON-NLS-1$
		result.append(filterControlsFromExtendedContainerExpression);
		result.append(", labelExpression: "); //$NON-NLS-1$
		result.append(labelExpression);
		result.append(", helpExpression: "); //$NON-NLS-1$
		result.append(helpExpression);
		result.append(')');
		return result.toString();
	}

} //StereotypeApplicationDescriptionImpl
