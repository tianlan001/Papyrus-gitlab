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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ResizeConstraints;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resize Constraints</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ResizeConstraintsImpl#getResizeHandles <em>Resize Handles</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ResizeConstraintsImpl#getNonResizeHandles <em>Non Resize Handles</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ResizeConstraintsImpl#getResizeHandleNames <em>Resize Handle Names</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ResizeConstraintsImpl#getNonResizeHandleNames <em>Non Resize Handle Names</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResizeConstraintsImpl extends EObjectImpl implements ResizeConstraints {
	/**
	 * The default value of the '{@link #getResizeHandles() <em>Resize Handles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResizeHandles()
	 * @generated
	 * @ordered
	 */
	protected static final int RESIZE_HANDLES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getResizeHandles() <em>Resize Handles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResizeHandles()
	 * @generated
	 * @ordered
	 */
	protected int resizeHandles = RESIZE_HANDLES_EDEFAULT;

	/**
	 * The default value of the '{@link #getNonResizeHandles() <em>Non Resize Handles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonResizeHandles()
	 * @generated
	 * @ordered
	 */
	protected static final int NON_RESIZE_HANDLES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNonResizeHandles() <em>Non Resize Handles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonResizeHandles()
	 * @generated
	 * @ordered
	 */
	protected int nonResizeHandles = NON_RESIZE_HANDLES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResizeConstraintsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getResizeConstraints();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getResizeHandles() {
		return resizeHandles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResizeHandles(int newResizeHandles) {
		int oldResizeHandles = resizeHandles;
		resizeHandles = newResizeHandles;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLES, oldResizeHandles, resizeHandles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNonResizeHandles() {
		return nonResizeHandles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNonResizeHandles(int newNonResizeHandles) {
		int oldNonResizeHandles = nonResizeHandles;
		nonResizeHandles = newNonResizeHandles;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLES, oldNonResizeHandles, nonResizeHandles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<String> getResizeHandleNames() {
		return getPositionConstantNames(getResizeHandles());
	}

	/**
	 * <!-- begin-user-doc -->
	 * XXX Actually, we make no use of non-resize handles, perhaps, should remove them
	 * from model (to use them, need to override method createSelectionHandles() in editPolicy
	 * class returned from getPrimaryDragEditPolicy, and use NonResizableHandleKit.addHandle.
	 * It's too much code (which is different from simple setResizeDirections(int), thus, not supported, at least now
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<String> getNonResizeHandleNames() {
		return getPositionConstantNames(getNonResizeHandles());
	}

	private EList<String> getPositionConstantNames(int value) {
		BasicEList<String> rv = new BasicEList<String>();
		addConstantIfFlagSet(value, PositionConstants.NORTH, "NORTH", rv);
		addConstantIfFlagSet(value, PositionConstants.SOUTH, "SOUTH", rv);
		addConstantIfFlagSet(value, PositionConstants.WEST, "WEST", rv);
		addConstantIfFlagSet(value, PositionConstants.EAST, "EAST", rv);
		return rv;
	}

	private static void addConstantIfFlagSet(int value, int flag, String constantName, EList<String> list) {
		if ((value & flag) == flag) {
			list.add(constantName);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLES:
				return getResizeHandles();
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLES:
				return getNonResizeHandles();
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLE_NAMES:
				return getResizeHandleNames();
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLE_NAMES:
				return getNonResizeHandleNames();
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
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLES:
				setResizeHandles((Integer)newValue);
				return;
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLES:
				setNonResizeHandles((Integer)newValue);
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
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLES:
				setResizeHandles(RESIZE_HANDLES_EDEFAULT);
				return;
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLES:
				setNonResizeHandles(NON_RESIZE_HANDLES_EDEFAULT);
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
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLES:
				return resizeHandles != RESIZE_HANDLES_EDEFAULT;
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLES:
				return nonResizeHandles != NON_RESIZE_HANDLES_EDEFAULT;
			case GMFGenPackage.RESIZE_CONSTRAINTS__RESIZE_HANDLE_NAMES:
				return !getResizeHandleNames().isEmpty();
			case GMFGenPackage.RESIZE_CONSTRAINTS__NON_RESIZE_HANDLE_NAMES:
				return !getNonResizeHandleNames().isEmpty();
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
		result.append(" (resizeHandles: ");
		result.append(resizeHandles);
		result.append(", nonResizeHandles: ");
		result.append(nonResizeHandles);
		result.append(')');
		return result.toString();
	}

} //ResizeConstraintsImpl
