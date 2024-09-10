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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPropertyTab;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertyTabFilter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Custom Property Tab</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomPropertyTabImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomPropertyTabImpl#getFilter <em>Filter</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomPropertyTabImpl#isGenerateBoilerplate <em>Generate Boilerplate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenCustomPropertyTabImpl extends GenPropertyTabImpl implements GenCustomPropertyTab {
	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected GenPropertyTabFilter filter;

	/**
	 * The default value of the '{@link #isGenerateBoilerplate() <em>Generate Boilerplate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateBoilerplate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_BOILERPLATE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateBoilerplate() <em>Generate Boilerplate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateBoilerplate()
	 * @generated
	 * @ordered
	 */
	protected boolean generateBoilerplate = GENERATE_BOILERPLATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenCustomPropertyTabImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenCustomPropertyTab();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassNameGen() {
		return className;
	}

	public String getClassName() {
		String rv = getClassNameGen();
		
		//Fix for 480502
		if(getSheet() == null || getSheet().getEditorGen() == null)
			return rv;
		
		if (rv == null || rv.trim().length() == 0) {
			return ((GenEditorGeneratorImpl) getSheet().getEditorGen()).getDomainModelCapName() + "PropertySection";
		}
		return rv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenPropertyTabFilter getFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilter(GenPropertyTabFilter newFilter, NotificationChain msgs) {
		GenPropertyTabFilter oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER, oldFilter, newFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilter(GenPropertyTabFilter newFilter) {
		if (newFilter != filter) {
			NotificationChain msgs = null;
			if (filter != null)
				msgs = ((InternalEObject)filter).eInverseRemove(this, GMFGenPackage.GEN_PROPERTY_TAB_FILTER__TAB, GenPropertyTabFilter.class, msgs);
			if (newFilter != null)
				msgs = ((InternalEObject)newFilter).eInverseAdd(this, GMFGenPackage.GEN_PROPERTY_TAB_FILTER__TAB, GenPropertyTabFilter.class, msgs);
			msgs = basicSetFilter(newFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER, newFilter, newFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGenerateBoilerplate() {
		return generateBoilerplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGenerateBoilerplate(boolean newGenerateBoilerplate) {
		boolean oldGenerateBoilerplate = generateBoilerplate;
		generateBoilerplate = newGenerateBoilerplate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__GENERATE_BOILERPLATE, oldGenerateBoilerplate, generateBoilerplate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedClassName() {
		String simpleName = getClassName();
		if (simpleName.indexOf('.') != -1) {
			return simpleName; // not so simple name
		}
		return getSheet().getPackageName() + '.' + simpleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				if (filter != null)
					msgs = ((InternalEObject)filter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER, null, msgs);
				return basicSetFilter((GenPropertyTabFilter)otherEnd, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				return basicSetFilter(null, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__CLASS_NAME:
				return getClassName();
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				return getFilter();
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__GENERATE_BOILERPLATE:
				return isGenerateBoilerplate();
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
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				setFilter((GenPropertyTabFilter)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__GENERATE_BOILERPLATE:
				setGenerateBoilerplate((Boolean)newValue);
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
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				setFilter((GenPropertyTabFilter)null);
				return;
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__GENERATE_BOILERPLATE:
				setGenerateBoilerplate(GENERATE_BOILERPLATE_EDEFAULT);
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
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__FILTER:
				return filter != null;
			case GMFGenPackage.GEN_CUSTOM_PROPERTY_TAB__GENERATE_BOILERPLATE:
				return generateBoilerplate != GENERATE_BOILERPLATE_EDEFAULT;
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
		result.append(" (className: ");
		result.append(className);
		result.append(", generateBoilerplate: ");
		result.append(generateBoilerplate);
		result.append(')');
		return result.toString();
	}

} //GenCustomPropertyTabImpl
