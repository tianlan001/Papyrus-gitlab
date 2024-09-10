/**
 * Copyright (c) 2015 CEA LIST.
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
 */
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tool Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.impl.ToolConfigurationImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.impl.ToolConfigurationImpl#getElementDescriptors <em>Element Descriptors</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.impl.ToolConfigurationImpl#getToolClassName <em>Tool Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ToolConfigurationImpl extends LeafConfigurationImpl implements ToolConfiguration {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final ToolKind KIND_EDEFAULT = ToolKind.CREATION_TOOL;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected ToolKind kind = KIND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElementDescriptors() <em>Element Descriptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementDescriptors()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementDescriptor> elementDescriptors;

	/**
	 * The default value of the '{@link #getToolClassName() <em>Tool Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOL_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getToolClassName() <em>Tool Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolClassName()
	 * @generated
	 * @ordered
	 */
	protected String toolClassName = TOOL_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ToolConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PaletteconfigurationPackage.Literals.TOOL_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToolKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(ToolKind newKind) {
		ToolKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PaletteconfigurationPackage.TOOL_CONFIGURATION__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ElementDescriptor> getElementDescriptors() {
		if (elementDescriptors == null) {
			elementDescriptors = new EObjectContainmentEList<ElementDescriptor>(ElementDescriptor.class, this, PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS);
		}
		return elementDescriptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getToolClassName() {
		return toolClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolClassName(String newToolClassName) {
		String oldToolClassName = toolClassName;
		toolClassName = newToolClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PaletteconfigurationPackage.TOOL_CONFIGURATION__TOOL_CLASS_NAME, oldToolClassName, toolClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS:
				return ((InternalEList<?>)getElementDescriptors()).basicRemove(otherEnd, msgs);
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
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__KIND:
				return getKind();
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS:
				return getElementDescriptors();
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__TOOL_CLASS_NAME:
				return getToolClassName();
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
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__KIND:
				setKind((ToolKind)newValue);
				return;
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS:
				getElementDescriptors().clear();
				getElementDescriptors().addAll((Collection<? extends ElementDescriptor>)newValue);
				return;
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__TOOL_CLASS_NAME:
				setToolClassName((String)newValue);
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
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS:
				getElementDescriptors().clear();
				return;
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__TOOL_CLASS_NAME:
				setToolClassName(TOOL_CLASS_NAME_EDEFAULT);
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
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__KIND:
				return kind != KIND_EDEFAULT;
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS:
				return elementDescriptors != null && !elementDescriptors.isEmpty();
			case PaletteconfigurationPackage.TOOL_CONFIGURATION__TOOL_CLASS_NAME:
				return TOOL_CLASS_NAME_EDEFAULT == null ? toolClassName != null : !TOOL_CLASS_NAME_EDEFAULT.equals(toolClassName);
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
		result.append(" (kind: ");
		result.append(kind);
		result.append(", toolClassName: ");
		result.append(toolClassName);
		result.append(')');
		return result.toString();
	}

} //ToolConfigurationImpl
