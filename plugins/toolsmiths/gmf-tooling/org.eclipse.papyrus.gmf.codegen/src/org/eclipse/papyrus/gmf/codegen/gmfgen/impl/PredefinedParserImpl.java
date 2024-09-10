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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod;
import org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Predefined Parser</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PredefinedParserImpl#getViewMethod <em>View Method</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PredefinedParserImpl#getEditMethod <em>Edit Method</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.PredefinedParserImpl#getClassName <em>Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PredefinedParserImpl extends GenParserImplementationImpl implements PredefinedParser {
	/**
	 * The default value of the '{@link #getViewMethod() <em>View Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewMethod()
	 * @generated
	 * @ordered
	 */
	protected static final LabelTextAccessMethod VIEW_METHOD_EDEFAULT = LabelTextAccessMethod.MESSAGE_FORMAT;

	/**
	 * The cached value of the '{@link #getViewMethod() <em>View Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewMethod()
	 * @generated
	 * @ordered
	 */
	protected LabelTextAccessMethod viewMethod = VIEW_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditMethod() <em>Edit Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditMethod()
	 * @generated
	 * @ordered
	 */
	protected static final LabelTextAccessMethod EDIT_METHOD_EDEFAULT = LabelTextAccessMethod.MESSAGE_FORMAT;

	/**
	 * The cached value of the '{@link #getEditMethod() <em>Edit Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditMethod()
	 * @generated
	 * @ordered
	 */
	protected LabelTextAccessMethod editMethod = EDIT_METHOD_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PredefinedParserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getPredefinedParser();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelTextAccessMethod getViewMethod() {
		return viewMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setViewMethod(LabelTextAccessMethod newViewMethod) {
		LabelTextAccessMethod oldViewMethod = viewMethod;
		viewMethod = newViewMethod == null ? VIEW_METHOD_EDEFAULT : newViewMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PREDEFINED_PARSER__VIEW_METHOD, oldViewMethod, viewMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelTextAccessMethod getEditMethod() {
		return editMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditMethod(LabelTextAccessMethod newEditMethod) {
		LabelTextAccessMethod oldEditMethod = editMethod;
		editMethod = newEditMethod == null ? EDIT_METHOD_EDEFAULT : newEditMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PREDEFINED_PARSER__EDIT_METHOD, oldEditMethod, editMethod));
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
		final String suffix = "Parser"; //$NON-NLS-1$
		if (viewMethod == editMethod) {
			return getFriendlyName(viewMethod) + suffix;
		} else {
			return getFriendlyName(viewMethod) + getFriendlyName(editMethod) + suffix;
		}
	}
	private String getFriendlyName(LabelTextAccessMethod m) {
		switch (m) {
		case MESSAGE_FORMAT : return "MessageFormat"; //$NON-NLS-1$
		case NATIVE : return "Native"; //$NON-NLS-1$
		case REGEXP : return "Regexp"; //$NON-NLS-1$
		case PRINTF : return "Printf"; //$NON-NLS-1$
		}
		return m.getName();
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
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.PREDEFINED_PARSER__CLASS_NAME, oldClassName, className));
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
			case GMFGenPackage.PREDEFINED_PARSER__VIEW_METHOD:
				return getViewMethod();
			case GMFGenPackage.PREDEFINED_PARSER__EDIT_METHOD:
				return getEditMethod();
			case GMFGenPackage.PREDEFINED_PARSER__CLASS_NAME:
				return getClassName();
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
			case GMFGenPackage.PREDEFINED_PARSER__VIEW_METHOD:
				setViewMethod((LabelTextAccessMethod)newValue);
				return;
			case GMFGenPackage.PREDEFINED_PARSER__EDIT_METHOD:
				setEditMethod((LabelTextAccessMethod)newValue);
				return;
			case GMFGenPackage.PREDEFINED_PARSER__CLASS_NAME:
				setClassName((String)newValue);
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
			case GMFGenPackage.PREDEFINED_PARSER__VIEW_METHOD:
				setViewMethod(VIEW_METHOD_EDEFAULT);
				return;
			case GMFGenPackage.PREDEFINED_PARSER__EDIT_METHOD:
				setEditMethod(EDIT_METHOD_EDEFAULT);
				return;
			case GMFGenPackage.PREDEFINED_PARSER__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
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
			case GMFGenPackage.PREDEFINED_PARSER__VIEW_METHOD:
				return viewMethod != VIEW_METHOD_EDEFAULT;
			case GMFGenPackage.PREDEFINED_PARSER__EDIT_METHOD:
				return editMethod != EDIT_METHOD_EDEFAULT;
			case GMFGenPackage.PREDEFINED_PARSER__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
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
		result.append(" (viewMethod: ");
		result.append(viewMethod);
		result.append(", editMethod: ");
		result.append(editMethod);
		result.append(", className: ");
		result.append(className);
		result.append(')');
		return result.toString();
	}

} //PredefinedParserImpl
