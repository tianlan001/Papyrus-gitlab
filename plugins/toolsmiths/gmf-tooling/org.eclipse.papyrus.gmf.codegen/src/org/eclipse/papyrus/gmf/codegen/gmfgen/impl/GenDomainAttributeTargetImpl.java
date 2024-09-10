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
 * $Id: GenDomainAttributeTargetImpl.java,v 1.4 2009/02/16 14:04:49 atikhomirov Exp $
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Domain Attribute Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenDomainAttributeTargetImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenDomainAttributeTargetImpl#isNullAsError <em>Null As Error</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenDomainAttributeTargetImpl extends GenAuditableImpl implements GenDomainAttributeTarget {
	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected GenFeature attribute;

	/**
	 * The default value of the '{@link #isNullAsError() <em>Null As Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNullAsError()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NULL_AS_ERROR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNullAsError() <em>Null As Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNullAsError()
	 * @generated
	 * @ordered
	 */
	protected boolean nullAsError = NULL_AS_ERROR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenDomainAttributeTargetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenDomainAttributeTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFeature getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject)attribute;
			attribute = (GenFeature)eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE, oldAttribute, attribute));
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenFeature basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(GenFeature newAttribute) {
		GenFeature oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public GenClassifier getContext() {
		if(getAttribute() == null) {
			return null;
		}
		return getAttribute().getTypeGenClassifier();  
	}
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */	
	public GenClass getTargetClass() {
		if(getAttribute() == null) {
			return null;
		}		
		return getAttribute().getGenClass();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNullAsError() {
		return nullAsError;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNullAsError(boolean newNullAsError) {
		boolean oldNullAsError = nullAsError;
		nullAsError = newNullAsError;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__NULL_AS_ERROR, oldNullAsError, nullAsError));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE:
				if (resolve) return getAttribute();
				return basicGetAttribute();
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__NULL_AS_ERROR:
				return isNullAsError();
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
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE:
				setAttribute((GenFeature)newValue);
				return;
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__NULL_AS_ERROR:
				setNullAsError((Boolean)newValue);
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
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE:
				setAttribute((GenFeature)null);
				return;
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__NULL_AS_ERROR:
				setNullAsError(NULL_AS_ERROR_EDEFAULT);
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
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__ATTRIBUTE:
				return attribute != null;
			case GMFGenPackage.GEN_DOMAIN_ATTRIBUTE_TARGET__NULL_AS_ERROR:
				return nullAsError != NULL_AS_ERROR_EDEFAULT;
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
		result.append(" (nullAsError: ");
		result.append(nullAsError);
		result.append(')');
		return result.toString();
	}

} //GenDomainAttributeTargetImpl
