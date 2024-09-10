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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.StyleAttributesImpl#isFixedFont <em>Fixed Font</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.StyleAttributesImpl#isFixedForeground <em>Fixed Foreground</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.StyleAttributesImpl#isFixedBackground <em>Fixed Background</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StyleAttributesImpl extends EObjectImpl implements StyleAttributes {
	/**
	 * The default value of the '{@link #isFixedFont() <em>Fixed Font</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedFont()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FIXED_FONT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFixedFont() <em>Fixed Font</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedFont()
	 * @generated
	 * @ordered
	 */
	protected boolean fixedFont = FIXED_FONT_EDEFAULT;

	/**
	 * The default value of the '{@link #isFixedForeground() <em>Fixed Foreground</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedForeground()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FIXED_FOREGROUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFixedForeground() <em>Fixed Foreground</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedForeground()
	 * @generated
	 * @ordered
	 */
	protected boolean fixedForeground = FIXED_FOREGROUND_EDEFAULT;

	/**
	 * The default value of the '{@link #isFixedBackground() <em>Fixed Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedBackground()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FIXED_BACKGROUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFixedBackground() <em>Fixed Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedBackground()
	 * @generated
	 * @ordered
	 */
	protected boolean fixedBackground = FIXED_BACKGROUND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StyleAttributesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getStyleAttributes();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFixedFont() {
		return fixedFont;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFixedFont(boolean newFixedFont) {
		boolean oldFixedFont = fixedFont;
		fixedFont = newFixedFont;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FONT, oldFixedFont, fixedFont));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFixedForeground() {
		return fixedForeground;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFixedForeground(boolean newFixedForeground) {
		boolean oldFixedForeground = fixedForeground;
		fixedForeground = newFixedForeground;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FOREGROUND, oldFixedForeground, fixedForeground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFixedBackground() {
		return fixedBackground;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFixedBackground(boolean newFixedBackground) {
		boolean oldFixedBackground = fixedBackground;
		fixedBackground = newFixedBackground;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.STYLE_ATTRIBUTES__FIXED_BACKGROUND, oldFixedBackground, fixedBackground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FONT:
				return isFixedFont();
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FOREGROUND:
				return isFixedForeground();
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_BACKGROUND:
				return isFixedBackground();
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
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FONT:
				setFixedFont((Boolean)newValue);
				return;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FOREGROUND:
				setFixedForeground((Boolean)newValue);
				return;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_BACKGROUND:
				setFixedBackground((Boolean)newValue);
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
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FONT:
				setFixedFont(FIXED_FONT_EDEFAULT);
				return;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FOREGROUND:
				setFixedForeground(FIXED_FOREGROUND_EDEFAULT);
				return;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_BACKGROUND:
				setFixedBackground(FIXED_BACKGROUND_EDEFAULT);
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
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FONT:
				return fixedFont != FIXED_FONT_EDEFAULT;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_FOREGROUND:
				return fixedForeground != FIXED_FOREGROUND_EDEFAULT;
			case GMFGenPackage.STYLE_ATTRIBUTES__FIXED_BACKGROUND:
				return fixedBackground != FIXED_BACKGROUND_EDEFAULT;
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
		result.append(" (fixedFont: ");
		result.append(fixedFont);
		result.append(", fixedForeground: ");
		result.append(fixedForeground);
		result.append(", fixedBackground: ");
		result.append(fixedBackground);
		result.append(')');
		return result.toString();
	}

} //StyleAttributesImpl
