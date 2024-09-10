/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.AbstractToolEntry;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroup;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroupItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Tool Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.AbstractToolEntryImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.AbstractToolEntryImpl#isDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.AbstractToolEntryImpl#getQualifiedToolName <em>Qualified Tool Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.AbstractToolEntryImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractToolEntryImpl extends EntryBaseImpl implements AbstractToolEntry {
	/**
	 * The default value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean default_ = DEFAULT_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifiedToolName() <em>Qualified Tool Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedToolName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_TOOL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedToolName() <em>Qualified Tool Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedToolName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedToolName = QUALIFIED_TOOL_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> properties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractToolEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getAbstractToolEntry();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ToolGroup getGroup() {
		if (eContainerFeatureID() != GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP) return null;
		return (ToolGroup)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefault(boolean newDefault) {
		boolean oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ABSTRACT_TOOL_ENTRY__DEFAULT, oldDefault, default_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedToolName() {
		return qualifiedToolName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQualifiedToolName(String newQualifiedToolName) {
		String oldQualifiedToolName = qualifiedToolName;
		qualifiedToolName = newQualifiedToolName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ABSTRACT_TOOL_ENTRY__QUALIFIED_TOOL_NAME, oldQualifiedToolName, qualifiedToolName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP:
				return eBasicSetContainer(null, GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP, msgs);
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.TOOL_GROUP__ENTRIES, ToolGroup.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP:
				return getGroup();
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__DEFAULT:
				return isDefault();
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__QUALIFIED_TOOL_NAME:
				return getQualifiedToolName();
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES:
				if (coreType) return getProperties();
				else return getProperties().map();
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
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__DEFAULT:
				setDefault((Boolean)newValue);
				return;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__QUALIFIED_TOOL_NAME:
				setQualifiedToolName((String)newValue);
				return;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES:
				((EStructuralFeature.Setting)getProperties()).set(newValue);
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
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
				return;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__QUALIFIED_TOOL_NAME:
				setQualifiedToolName(QUALIFIED_TOOL_NAME_EDEFAULT);
				return;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES:
				getProperties().clear();
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
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP:
				return getGroup() != null;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__DEFAULT:
				return default_ != DEFAULT_EDEFAULT;
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__QUALIFIED_TOOL_NAME:
				return QUALIFIED_TOOL_NAME_EDEFAULT == null ? qualifiedToolName != null : !QUALIFIED_TOOL_NAME_EDEFAULT.equals(qualifiedToolName);
			case GMFGenPackage.ABSTRACT_TOOL_ENTRY__PROPERTIES:
				return properties != null && !properties.isEmpty();
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
		if (baseClass == ToolGroupItem.class) {
			switch (derivedFeatureID) {
				case GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP: return GMFGenPackage.TOOL_GROUP_ITEM__GROUP;
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
		if (baseClass == ToolGroupItem.class) {
			switch (baseFeatureID) {
				case GMFGenPackage.TOOL_GROUP_ITEM__GROUP: return GMFGenPackage.ABSTRACT_TOOL_ENTRY__GROUP;
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
		result.append(" (default: ");
		result.append(default_);
		result.append(", qualifiedToolName: ");
		result.append(qualifiedToolName);
		result.append(')');
		return result.toString();
	}

} //AbstractToolEntryImpl
