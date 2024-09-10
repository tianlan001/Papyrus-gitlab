/**
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.editor.welcome.internal.operations;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Sash Column</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int) <em>Get Sash Row</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SashColumnOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SashColumnOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static SashRow getSashRow(SashColumn sashColumn, int index) {
		SashRow result = null;
		EList<SashRow> rows = sashColumn.getSashRows();

		if ((index >= 0) && (index < rows.size())) {
			result = rows.get(index);
		}

		return result;
	}

} // SashColumnOperations
