/**
 * Copyright (c) 2012 CEA LIST.
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
 * 
 */
package org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getPriorityName <em>Priority Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.PaletteImpl#getEditorId <em>Editor Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaletteImpl extends FileBasedCustomizableElementImpl implements Palette {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getClazz() <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazz()
	 * @generated
	 * @ordered
	 */
	protected static final String CLAZZ_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getClazz() <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazz()
	 * @generated
	 * @ordered
	 */
	protected String clazz = CLAZZ_EDEFAULT;
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected String provider = PROVIDER_EDEFAULT;
	/**
	 * The default value of the '{@link #getPriorityName() <em>Priority Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriorityName()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIORITY_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPriorityName() <em>Priority Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriorityName()
	 * @generated
	 * @ordered
	 */
	protected String priorityName = PRIORITY_NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getEditorId() <em>Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorId()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITOR_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getEditorId() <em>Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorId()
	 * @generated
	 * @ordered
	 */
	protected String editorId = EDITOR_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationPluginPackage.Literals.PALETTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		String oldID = id;
		id = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__ID, oldID, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClazz(String newClazz) {
		String oldClazz = clazz;
		clazz = newClazz;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__CLAZZ, oldClazz, clazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__PROVIDER, oldProvider, provider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPriorityName() {
		return priorityName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriorityName(String newPriorityName) {
		String oldPriorityName = priorityName;
		priorityName = newPriorityName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__PRIORITY_NAME, oldPriorityName, priorityName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditorId() {
		return editorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorId(String newEditorId) {
		String oldEditorId = editorId;
		editorId = newEditorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.PALETTE__EDITOR_ID, oldEditorId, editorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CustomizationPluginPackage.PALETTE__ID:
				return getID();
			case CustomizationPluginPackage.PALETTE__CLAZZ:
				return getClazz();
			case CustomizationPluginPackage.PALETTE__NAME:
				return getName();
			case CustomizationPluginPackage.PALETTE__PROVIDER:
				return getProvider();
			case CustomizationPluginPackage.PALETTE__PRIORITY_NAME:
				return getPriorityName();
			case CustomizationPluginPackage.PALETTE__EDITOR_ID:
				return getEditorId();
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
			case CustomizationPluginPackage.PALETTE__ID:
				setID((String)newValue);
				return;
			case CustomizationPluginPackage.PALETTE__CLAZZ:
				setClazz((String)newValue);
				return;
			case CustomizationPluginPackage.PALETTE__NAME:
				setName((String)newValue);
				return;
			case CustomizationPluginPackage.PALETTE__PROVIDER:
				setProvider((String)newValue);
				return;
			case CustomizationPluginPackage.PALETTE__PRIORITY_NAME:
				setPriorityName((String)newValue);
				return;
			case CustomizationPluginPackage.PALETTE__EDITOR_ID:
				setEditorId((String)newValue);
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
			case CustomizationPluginPackage.PALETTE__ID:
				setID(ID_EDEFAULT);
				return;
			case CustomizationPluginPackage.PALETTE__CLAZZ:
				setClazz(CLAZZ_EDEFAULT);
				return;
			case CustomizationPluginPackage.PALETTE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CustomizationPluginPackage.PALETTE__PROVIDER:
				setProvider(PROVIDER_EDEFAULT);
				return;
			case CustomizationPluginPackage.PALETTE__PRIORITY_NAME:
				setPriorityName(PRIORITY_NAME_EDEFAULT);
				return;
			case CustomizationPluginPackage.PALETTE__EDITOR_ID:
				setEditorId(EDITOR_ID_EDEFAULT);
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
			case CustomizationPluginPackage.PALETTE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CustomizationPluginPackage.PALETTE__CLAZZ:
				return CLAZZ_EDEFAULT == null ? clazz != null : !CLAZZ_EDEFAULT.equals(clazz);
			case CustomizationPluginPackage.PALETTE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CustomizationPluginPackage.PALETTE__PROVIDER:
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
			case CustomizationPluginPackage.PALETTE__PRIORITY_NAME:
				return PRIORITY_NAME_EDEFAULT == null ? priorityName != null : !PRIORITY_NAME_EDEFAULT.equals(priorityName);
			case CustomizationPluginPackage.PALETTE__EDITOR_ID:
				return EDITOR_ID_EDEFAULT == null ? editorId != null : !EDITOR_ID_EDEFAULT.equals(editorId);
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
		result.append(" (ID: "); //$NON-NLS-1$
		result.append(id);
		result.append(", clazz: "); //$NON-NLS-1$
		result.append(clazz);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", provider: "); //$NON-NLS-1$
		result.append(provider);
		result.append(", priorityName: "); //$NON-NLS-1$
		result.append(priorityName);
		result.append(", editorId: "); //$NON-NLS-1$
		result.append(editorId);
		result.append(')');
		return result.toString();
	}

} //PaletteImpl
