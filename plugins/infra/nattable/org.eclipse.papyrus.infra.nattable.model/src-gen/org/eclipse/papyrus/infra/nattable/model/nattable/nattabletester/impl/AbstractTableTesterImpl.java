/**
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl.StyledElementImpl;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.AbstractTableTester;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.NattabletesterPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Table Tester</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AbstractTableTesterImpl extends StyledElementImpl implements AbstractTableTester {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractTableTesterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattabletesterPackage.Literals.ABSTRACT_TABLE_TESTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getTester() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case NattabletesterPackage.ABSTRACT_TABLE_TESTER___GET_TESTER:
				return getTester();
		}
		return super.eInvoke(operationID, arguments);
	}
} // AbstractTableTesterImpl
