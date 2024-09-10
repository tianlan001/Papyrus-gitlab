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
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomTemplateInput;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFixedInputsTemplateInvocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Fixed Inputs Template Invocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenFixedInputsTemplateInvocationImpl#getOclType <em>Ocl Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenFixedInputsTemplateInvocationImpl#getFixedInputs <em>Fixed Inputs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenFixedInputsTemplateInvocationImpl extends GenTemplateInvocationBaseImpl implements GenFixedInputsTemplateInvocation {
	/**
	 * The default value of the '{@link #getOclType() <em>Ocl Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclType()
	 * @generated
	 * @ordered
	 */
	protected static final String OCL_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOclType() <em>Ocl Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclType()
	 * @generated
	 * @ordered
	 */
	protected String oclType = OCL_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFixedInputs() <em>Fixed Inputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> fixedInputs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenFixedInputsTemplateInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenFixedInputsTemplateInvocation();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOclType() {
		return oclType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOclType(String newOclType) {
		String oldOclType = oclType;
		oclType = newOclType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE, oldOclType, oclType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getFixedInputs() {
		if (fixedInputs == null) {
			fixedInputs = new EObjectResolvingEList<EObject>(EObject.class, this, GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__FIXED_INPUTS);
		}
		return fixedInputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE:
				return getOclType();
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__FIXED_INPUTS:
				return getFixedInputs();
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
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE:
				setOclType((String)newValue);
				return;
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__FIXED_INPUTS:
				getFixedInputs().clear();
				getFixedInputs().addAll((Collection<? extends EObject>)newValue);
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
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE:
				setOclType(OCL_TYPE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__FIXED_INPUTS:
				getFixedInputs().clear();
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
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE:
				return OCL_TYPE_EDEFAULT == null ? oclType != null : !OCL_TYPE_EDEFAULT.equals(oclType);
			case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__FIXED_INPUTS:
				return fixedInputs != null && !fixedInputs.isEmpty();
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
		if (baseClass == GenCustomTemplateInput.class) {
			switch (derivedFeatureID) {
				case GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE: return GMFGenPackage.GEN_CUSTOM_TEMPLATE_INPUT__OCL_TYPE;
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
		if (baseClass == GenCustomTemplateInput.class) {
			switch (baseFeatureID) {
				case GMFGenPackage.GEN_CUSTOM_TEMPLATE_INPUT__OCL_TYPE: return GMFGenPackage.GEN_FIXED_INPUTS_TEMPLATE_INVOCATION__OCL_TYPE;
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
		result.append(" (oclType: ");
		result.append(oclType);
		result.append(')');
		return result.toString();
	}

} //GenFixedInputsTemplateInvocationImpl
