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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Matrix Relation Ship Direction</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This Enumeration is used to define the direction of the relationship to display in the cell. 
 * In case of directed relationship and NONE selected, the cell won't be editable.
 * In case of non directed relationship and a direction selected, we will use get(0) and get(1) to define a fake orientation.
 * <!-- end-model-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getMatrixRelationShipDirection()
 * @model
 * @generated
 */
public enum MatrixRelationShipDirection implements Enumerator {
	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "NONE", "NONE"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>FROM ROW TO COLUMN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FROM_ROW_TO_COLUMN_VALUE
	 * @generated
	 * @ordered
	 */
	FROM_ROW_TO_COLUMN(1, "FROM_ROW_TO_COLUMN", "FROM_ROW_TO_COLUMN"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>FROM COLUMN TO ROW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FROM_COLUMN_TO_ROW_VALUE
	 * @generated
	 * @ordered
	 */
	FROM_COLUMN_TO_ROW(2, "FROM_COLUMN_TO_ROW", "FROM_COLUMN_TO_ROW"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>NONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>FROM ROW TO COLUMN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FROM ROW TO COLUMN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FROM_ROW_TO_COLUMN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FROM_ROW_TO_COLUMN_VALUE = 1;

	/**
	 * The '<em><b>FROM COLUMN TO ROW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FROM COLUMN TO ROW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FROM_COLUMN_TO_ROW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FROM_COLUMN_TO_ROW_VALUE = 2;

	/**
	 * An array of all the '<em><b>Matrix Relation Ship Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MatrixRelationShipDirection[] VALUES_ARRAY =
		new MatrixRelationShipDirection[] {
			NONE,
			FROM_ROW_TO_COLUMN,
			FROM_COLUMN_TO_ROW,
		};

	/**
	 * A public read-only list of all the '<em><b>Matrix Relation Ship Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MatrixRelationShipDirection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Matrix Relation Ship Direction</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipDirection get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatrixRelationShipDirection result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Matrix Relation Ship Direction</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipDirection getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatrixRelationShipDirection result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Matrix Relation Ship Direction</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipDirection get(int value) {
		switch (value) {
			case NONE_VALUE: return NONE;
			case FROM_ROW_TO_COLUMN_VALUE: return FROM_ROW_TO_COLUMN;
			case FROM_COLUMN_TO_ROW_VALUE: return FROM_COLUMN_TO_ROW;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private MatrixRelationShipDirection(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //MatrixRelationShipDirection
