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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.Palette;
import org.eclipse.papyrus.gmf.codegen.gmfgen.StandardEntry;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroup;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PaletteImpl#getDiagram <em>Diagram</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PaletteImpl#isFlyout <em>Flyout</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PaletteImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PaletteImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PaletteImpl#getFactoryClassName <em>Factory Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaletteImpl extends EObjectImpl implements Palette {
	/**
	 * The default value of the '{@link #isFlyout() <em>Flyout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlyout()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FLYOUT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isFlyout() <em>Flyout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlyout()
	 * @generated
	 * @ordered
	 */
	protected boolean flyout = FLYOUT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<ToolGroup> groups;

	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFactoryClassName() <em>Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String FACTORY_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFactoryClassName() <em>Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClassName()
	 * @generated
	 * @ordered
	 */
	protected String factoryClassName = FACTORY_CLASS_NAME_EDEFAULT;

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
		return GMFGenPackage.eINSTANCE.getPalette();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagram getDiagram() {
		if (eContainerFeatureID() != GMFGenPackage.PALETTE__DIAGRAM) return null;
		return (GenDiagram)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFlyout() {
		return flyout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFlyout(boolean newFlyout) {
		boolean oldFlyout = flyout;
		flyout = newFlyout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PALETTE__FLYOUT, oldFlyout, flyout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ToolGroup> getGroups() {
		if (groups == null) {
			groups = new EObjectContainmentWithInverseEList<ToolGroup>(ToolGroup.class, this, GMFGenPackage.PALETTE__GROUPS, GMFGenPackage.TOOL_GROUP__PALETTE);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageNameGen() {
		return packageName;
	}

	public String getPackageName() {
		String value = getPackageNameGen();
		if (GenCommonBaseImpl.isEmpty(value) && getDiagram() != null && getDiagram().getEditorGen() != null) {
			value = getDiagram().getEditorGen().getEditor().getPackageName();
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PALETTE__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFactoryClassNameGen() {
		return factoryClassName;
	}

	public String getFactoryClassName() {
		String value = getFactoryClassNameGen();
		if (GenCommonBaseImpl.isEmpty(value)) {
			value = ((GenDiagramImpl) getDiagram()).getDomainPackageCapName() + "PaletteFactory"; //$NON-NLS-1$
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFactoryClassName(String newFactoryClassName) {
		String oldFactoryClassName = factoryClassName;
		factoryClassName = newFactoryClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PALETTE__FACTORY_CLASS_NAME, oldFactoryClassName, factoryClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFactoryQualifiedClassName() {
		return getPackageName() + '.' + getFactoryClassName();
	}

	public boolean definesStandardTools() {
		for (Iterator<?> it = eAllContents(); it.hasNext();) {
			if (it.next() instanceof StandardEntry) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.PALETTE__DIAGRAM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.PALETTE__DIAGRAM, msgs);
			case GMFGenPackage.PALETTE__GROUPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGroups()).basicAdd(otherEnd, msgs);
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
			case GMFGenPackage.PALETTE__DIAGRAM:
				return eBasicSetContainer(null, GMFGenPackage.PALETTE__DIAGRAM, msgs);
			case GMFGenPackage.PALETTE__GROUPS:
				return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
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
			case GMFGenPackage.PALETTE__DIAGRAM:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_DIAGRAM__PALETTE, GenDiagram.class, msgs);
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
			case GMFGenPackage.PALETTE__DIAGRAM:
				return getDiagram();
			case GMFGenPackage.PALETTE__FLYOUT:
				return isFlyout();
			case GMFGenPackage.PALETTE__GROUPS:
				return getGroups();
			case GMFGenPackage.PALETTE__PACKAGE_NAME:
				return getPackageName();
			case GMFGenPackage.PALETTE__FACTORY_CLASS_NAME:
				return getFactoryClassName();
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
			case GMFGenPackage.PALETTE__FLYOUT:
				setFlyout((Boolean)newValue);
				return;
			case GMFGenPackage.PALETTE__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends ToolGroup>)newValue);
				return;
			case GMFGenPackage.PALETTE__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case GMFGenPackage.PALETTE__FACTORY_CLASS_NAME:
				setFactoryClassName((String)newValue);
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
			case GMFGenPackage.PALETTE__FLYOUT:
				setFlyout(FLYOUT_EDEFAULT);
				return;
			case GMFGenPackage.PALETTE__GROUPS:
				getGroups().clear();
				return;
			case GMFGenPackage.PALETTE__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case GMFGenPackage.PALETTE__FACTORY_CLASS_NAME:
				setFactoryClassName(FACTORY_CLASS_NAME_EDEFAULT);
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
			case GMFGenPackage.PALETTE__DIAGRAM:
				return getDiagram() != null;
			case GMFGenPackage.PALETTE__FLYOUT:
				return flyout != FLYOUT_EDEFAULT;
			case GMFGenPackage.PALETTE__GROUPS:
				return groups != null && !groups.isEmpty();
			case GMFGenPackage.PALETTE__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case GMFGenPackage.PALETTE__FACTORY_CLASS_NAME:
				return FACTORY_CLASS_NAME_EDEFAULT == null ? factoryClassName != null : !FACTORY_CLASS_NAME_EDEFAULT.equals(factoryClassName);
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
		result.append(" (flyout: ");
		result.append(flyout);
		result.append(", packageName: ");
		result.append(packageName);
		result.append(", factoryClassName: ");
		result.append(factoryClassName);
		result.append(')');
		return result.toString();
	}

} //PaletteImpl
