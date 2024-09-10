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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Table Tester</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract class for the tester.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.NattabletesterPackage#getAbstractTableTester()
 * @model abstract="true"
 * @generated
 */
public interface AbstractTableTester extends StyledElement {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns an object representing the tester
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Object getTester();
} // AbstractTableTester
