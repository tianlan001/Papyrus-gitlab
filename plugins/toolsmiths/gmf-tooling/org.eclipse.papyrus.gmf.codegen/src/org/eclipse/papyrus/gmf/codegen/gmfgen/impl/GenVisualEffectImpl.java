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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.helper.OCLHelper;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenVisualEffect;
import org.eclipse.papyrus.gmf.internal.common.codegen.Conversions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Visual Effect</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenVisualEffectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenVisualEffectImpl#getPinKind <em>Pin Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenVisualEffectImpl#getOperationName <em>Operation Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenVisualEffectImpl#getOperationType <em>Operation Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenVisualEffectImpl#getOclExpression <em>Ocl Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenVisualEffectImpl extends CustomBehaviourImpl implements GenVisualEffect {
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
	 * The default value of the '{@link #getPinKind() <em>Pin Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinKind()
	 * @generated
	 * @ordered
	 */
	protected static final String PIN_KIND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPinKind() <em>Pin Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPinKind()
	 * @generated
	 * @ordered
	 */
	protected String pinKind = PIN_KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationName()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationName()
	 * @generated
	 * @ordered
	 */
	protected String operationName = OPERATION_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected String operationType = OPERATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOclExpression() <em>Ocl Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String OCL_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOclExpression() <em>Ocl Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclExpression()
	 * @generated
	 * @ordered
	 */
	protected String oclExpression = OCL_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenVisualEffectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenVisualEffect();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_VISUAL_EFFECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPinKind() {
		return pinKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPinKind(String newPinKind) {
		String oldPinKind = pinKind;
		pinKind = newPinKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_VISUAL_EFFECT__PIN_KIND, oldPinKind, pinKind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOperationName() {
		return operationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOperationName(String newOperationName) {
		String oldOperationName = operationName;
		operationName = newOperationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_NAME, oldOperationName, operationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOperationType() {
		return operationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOperationType(String newOperationType) {
		String oldOperationType = operationType;
		operationType = newOperationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_TYPE, oldOperationType, operationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOclExpression() {
		return oclExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOclExpression(String newOclExpression) {
		String oldOclExpression = oclExpression;
		oclExpression = newOclExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_VISUAL_EFFECT__OCL_EXPRESSION, oldOclExpression, oclExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getOclExpressionString() {
		return Conversions.toStringLiteral(getOclExpression());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EClassifier getOperationRuntimeType() {
		OCL ocl = OCL.newInstance();
		OCLHelper helper = ocl.createOCLHelper();
		helper.setContext(EcorePackage.eINSTANCE.getEClass());
		org.eclipse.ocl.expressions.OCLExpression<EClassifier> expr = null;

//		String opType = getOperationType();
//		if (opType.trim().startsWith("Tuple")){
//			opType = "let t: Sequence(" + opType +") = Sequence{} in t->first()";
//			opType = "let a : " + opType + " in a";
//		}
			
		String expressionString = "let t: Sequence(" + getOperationType() +") = Sequence{} in t->first()";	
		
		try {
			expr = helper.createQuery(expressionString);
		} catch (org.eclipse.ocl.ParserException e) {
			throw new RuntimeException(e);
		}
		return expr.getType();
//		
//		EClassifier exprType = expr.getType();
////		if (exprType instanceof TypeType<?, ?>){
////			TypeType<?, ?> typeType = (TypeType<?, ?>)exprType;
////			exprType = (EClassifier)typeType.getReferredType();
////		}
//		EClassifier result = exprType instanceof EClassifier ? (EClassifier)exprType : null;
//		System.err.println("About to return: " + result);
//		return result;
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_VISUAL_EFFECT__NAME:
				return getName();
			case GMFGenPackage.GEN_VISUAL_EFFECT__PIN_KIND:
				return getPinKind();
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_NAME:
				return getOperationName();
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_TYPE:
				return getOperationType();
			case GMFGenPackage.GEN_VISUAL_EFFECT__OCL_EXPRESSION:
				return getOclExpression();
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
			case GMFGenPackage.GEN_VISUAL_EFFECT__NAME:
				setName((String)newValue);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__PIN_KIND:
				setPinKind((String)newValue);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_NAME:
				setOperationName((String)newValue);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_TYPE:
				setOperationType((String)newValue);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OCL_EXPRESSION:
				setOclExpression((String)newValue);
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
			case GMFGenPackage.GEN_VISUAL_EFFECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__PIN_KIND:
				setPinKind(PIN_KIND_EDEFAULT);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_NAME:
				setOperationName(OPERATION_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_TYPE:
				setOperationType(OPERATION_TYPE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_VISUAL_EFFECT__OCL_EXPRESSION:
				setOclExpression(OCL_EXPRESSION_EDEFAULT);
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
			case GMFGenPackage.GEN_VISUAL_EFFECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GMFGenPackage.GEN_VISUAL_EFFECT__PIN_KIND:
				return PIN_KIND_EDEFAULT == null ? pinKind != null : !PIN_KIND_EDEFAULT.equals(pinKind);
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_NAME:
				return OPERATION_NAME_EDEFAULT == null ? operationName != null : !OPERATION_NAME_EDEFAULT.equals(operationName);
			case GMFGenPackage.GEN_VISUAL_EFFECT__OPERATION_TYPE:
				return OPERATION_TYPE_EDEFAULT == null ? operationType != null : !OPERATION_TYPE_EDEFAULT.equals(operationType);
			case GMFGenPackage.GEN_VISUAL_EFFECT__OCL_EXPRESSION:
				return OCL_EXPRESSION_EDEFAULT == null ? oclExpression != null : !OCL_EXPRESSION_EDEFAULT.equals(oclExpression);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", pinKind: ");
		result.append(pinKind);
		result.append(", operationName: ");
		result.append(operationName);
		result.append(", operationType: ");
		result.append(operationType);
		result.append(", oclExpression: ");
		result.append(oclExpression);
		result.append(')');
		return result.toString();
	}

} //GenVisualEffectImpl
