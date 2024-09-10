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

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenConstraint;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression Label Parser</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ExpressionLabelParserImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ExpressionLabelParserImpl#getExpressionContext <em>Expression Context</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ExpressionLabelParserImpl#getViewExpression <em>View Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ExpressionLabelParserImpl#getEditExpression <em>Edit Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ExpressionLabelParserImpl#getValidateExpression <em>Validate Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExpressionLabelParserImpl extends GenParserImplementationImpl implements ExpressionLabelParser {
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
	 * The cached value of the '{@link #getExpressionContext() <em>Expression Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionContext()
	 * @generated
	 * @ordered
	 */
	protected GenClass expressionContext;

	/**
	 * The cached value of the '{@link #getViewExpression() <em>View Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewExpression()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression viewExpression;

	/**
	 * The cached value of the '{@link #getEditExpression() <em>Edit Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditExpression()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression editExpression;

	/**
	 * The cached value of the '{@link #getValidateExpression() <em>Validate Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidateExpression()
	 * @generated
	 * @ordered
	 */
	protected GenConstraint validateExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpressionLabelParserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getExpressionLabelParser();
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
		String n = getClassNameGen();
		if (!GenCommonBaseImpl.isEmpty(n)) {
			return n;
		}
		String baseName = "ExpressionLabelParser";
		if (getUses().size() == 1) {
			if (getUses().get(0).eContainer() instanceof GenCommonBase) {
				return ((GenCommonBase) getUses().get(0).eContainer()).getClassNamePrefix() + baseName;
			}
		}
		if (getHolder() != null && getHolder().getImplementations().size() > 1) {
			return baseName + (1+getHolder().getImplementations().indexOf(this));
		}
		return baseName;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.EXPRESSION_LABEL_PARSER__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenClass getExpressionContext() {
		if (expressionContext != null && expressionContext.eIsProxy()) {
			InternalEObject oldExpressionContext = (InternalEObject)expressionContext;
			expressionContext = (GenClass)eResolveProxy(oldExpressionContext);
			if (expressionContext != oldExpressionContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT, oldExpressionContext, expressionContext));
			}
		}
		return expressionContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenClass basicGetExpressionContext() {
		return expressionContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpressionContext(GenClass newExpressionContext) {
		GenClass oldExpressionContext = expressionContext;
		expressionContext = newExpressionContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT, oldExpressionContext, expressionContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueExpression getViewExpression() {
		if (viewExpression != null && viewExpression.eIsProxy()) {
			InternalEObject oldViewExpression = (InternalEObject)viewExpression;
			viewExpression = (ValueExpression)eResolveProxy(oldViewExpression);
			if (viewExpression != oldViewExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION, oldViewExpression, viewExpression));
			}
		}
		return viewExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression basicGetViewExpression() {
		return viewExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setViewExpression(ValueExpression newViewExpression) {
		ValueExpression oldViewExpression = viewExpression;
		viewExpression = newViewExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION, oldViewExpression, viewExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueExpression getEditExpression() {
		if (editExpression != null && editExpression.eIsProxy()) {
			InternalEObject oldEditExpression = (InternalEObject)editExpression;
			editExpression = (ValueExpression)eResolveProxy(oldEditExpression);
			if (editExpression != oldEditExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION, oldEditExpression, editExpression));
			}
		}
		return editExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression basicGetEditExpression() {
		return editExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditExpression(ValueExpression newEditExpression) {
		ValueExpression oldEditExpression = editExpression;
		editExpression = newEditExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION, oldEditExpression, editExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenConstraint getValidateExpression() {
		if (validateExpression != null && validateExpression.eIsProxy()) {
			InternalEObject oldValidateExpression = (InternalEObject)validateExpression;
			validateExpression = (GenConstraint)eResolveProxy(oldValidateExpression);
			if (validateExpression != oldValidateExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION, oldValidateExpression, validateExpression));
			}
		}
		return validateExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenConstraint basicGetValidateExpression() {
		return validateExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValidateExpression(GenConstraint newValidateExpression) {
		GenConstraint oldValidateExpression = validateExpression;
		validateExpression = newValidateExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION, oldValidateExpression, validateExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedClassName() {
		String n = getClassName();
		if (getHolder() == null || GenCommonBaseImpl.isEmpty(getHolder().getImplPackageName())) {
			return n;
		}
		return getHolder().getImplPackageName() + '.' + n;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__CLASS_NAME:
				return getClassName();
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT:
				if (resolve) return getExpressionContext();
				return basicGetExpressionContext();
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION:
				if (resolve) return getViewExpression();
				return basicGetViewExpression();
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION:
				if (resolve) return getEditExpression();
				return basicGetEditExpression();
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION:
				if (resolve) return getValidateExpression();
				return basicGetValidateExpression();
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
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT:
				setExpressionContext((GenClass)newValue);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION:
				setViewExpression((ValueExpression)newValue);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION:
				setEditExpression((ValueExpression)newValue);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION:
				setValidateExpression((GenConstraint)newValue);
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
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT:
				setExpressionContext((GenClass)null);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION:
				setViewExpression((ValueExpression)null);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION:
				setEditExpression((ValueExpression)null);
				return;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION:
				setValidateExpression((GenConstraint)null);
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
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EXPRESSION_CONTEXT:
				return expressionContext != null;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VIEW_EXPRESSION:
				return viewExpression != null;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__EDIT_EXPRESSION:
				return editExpression != null;
			case GMFGenPackage.EXPRESSION_LABEL_PARSER__VALIDATE_EXPRESSION:
				return validateExpression != null;
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
		result.append(')');
		return result.toString();
	}

} //ExpressionLabelParserImpl
