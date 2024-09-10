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
 * A representation of the literals of the enumeration '<em><b>Matrix Relation Ship Owner Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This Enumeration is used to define the owner of relationships created editing the matrix.
 * <!-- end-model-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getMatrixRelationShipOwnerStrategy()
 * @model
 * @generated
 */
public enum MatrixRelationShipOwnerStrategy implements Enumerator {
	/**
	 * The '<em><b>DEFAULT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT(0, "DEFAULT", "DEFAULT"), //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * The '<em><b>TABLE CONTEXT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TABLE_CONTEXT_VALUE
	 * @generated
	 * @ordered
	 */
	TABLE_CONTEXT(1, "TABLE_CONTEXT", "TABLE_CONTEXT"), //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * The '<em><b>ROW OWNER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROW_OWNER_VALUE
	 * @generated
	 * @ordered
	 */
	ROW_OWNER(2, "ROW_OWNER", "ROW_OWNER"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>ROW AS OWNER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROW_AS_OWNER_VALUE
	 * @generated
	 * @ordered
	 */
	ROW_AS_OWNER(3, "ROW_AS_OWNER", "ROW_AS_OWNER"), //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * The '<em><b>COLUMN OWNER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COLUMN_OWNER_VALUE
	 * @generated
	 * @ordered
	 */
	COLUMN_OWNER(4, "COLUMN_OWNER", "COLUMN_OWNER"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>COLUMN AS OWNER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COLUMN_AS_OWNER_VALUE
	 * @generated
	 * @ordered
	 */
	COLUMN_AS_OWNER(5, "COLUMN_AS_OWNER", "COLUMN_AS_OWNER"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>OTHER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(6, "OTHER", "OTHER"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>DEFAULT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DEFAULT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>TABLE CONTEXT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TABLE CONTEXT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TABLE_CONTEXT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_CONTEXT_VALUE = 1;

	/**
	 * The '<em><b>ROW OWNER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ROW OWNER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROW_OWNER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ROW_OWNER_VALUE = 2;

	/**
	 * The '<em><b>ROW AS OWNER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ROW AS OWNER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROW_AS_OWNER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ROW_AS_OWNER_VALUE = 3;

	/**
	 * The '<em><b>COLUMN OWNER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>COLUMN OWNER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COLUMN_OWNER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_OWNER_VALUE = 4;

	/**
	 * The '<em><b>COLUMN AS OWNER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>COLUMN AS OWNER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COLUMN_AS_OWNER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COLUMN_AS_OWNER_VALUE = 5;

	/**
	 * The '<em><b>OTHER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OTHER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OTHER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 6;

	/**
	 * An array of all the '<em><b>Matrix Relation Ship Owner Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MatrixRelationShipOwnerStrategy[] VALUES_ARRAY = new MatrixRelationShipOwnerStrategy[] {
			DEFAULT,
			TABLE_CONTEXT,
			ROW_OWNER,
			ROW_AS_OWNER,
			COLUMN_OWNER,
			COLUMN_AS_OWNER,
			OTHER,
		};

	/**
	 * A public read-only list of all the '<em><b>Matrix Relation Ship Owner Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MatrixRelationShipOwnerStrategy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Matrix Relation Ship Owner Strategy</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipOwnerStrategy get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatrixRelationShipOwnerStrategy result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Matrix Relation Ship Owner Strategy</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipOwnerStrategy getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatrixRelationShipOwnerStrategy result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Matrix Relation Ship Owner Strategy</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MatrixRelationShipOwnerStrategy get(int value) {
		switch (value) {
			case DEFAULT_VALUE: return DEFAULT;
			case TABLE_CONTEXT_VALUE: return TABLE_CONTEXT;
			case ROW_OWNER_VALUE: return ROW_OWNER;
			case ROW_AS_OWNER_VALUE: return ROW_AS_OWNER;
			case COLUMN_OWNER_VALUE: return COLUMN_OWNER;
			case COLUMN_AS_OWNER_VALUE: return COLUMN_AS_OWNER;
			case OTHER_VALUE: return OTHER;
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
	private MatrixRelationShipOwnerStrategy(int value, String name, String literal) {
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

} // MatrixRelationShipOwnerStrategy
