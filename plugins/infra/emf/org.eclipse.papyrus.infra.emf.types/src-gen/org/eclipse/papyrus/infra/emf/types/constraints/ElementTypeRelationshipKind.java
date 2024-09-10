/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
 */
package org.eclipse.papyrus.infra.emf.types.constraints;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Element Type Relationship Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getElementTypeRelationshipKind()
 * @model
 * @generated
 */
public enum ElementTypeRelationshipKind implements Enumerator {
	/**
	 * The '<em><b>Exact Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EXACT_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	EXACT_TYPE(0, "exactType", "exactType"),

	/**
	 * The '<em><b>Specialization Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SPECIALIZATION_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	SPECIALIZATION_TYPE(1, "specializationType", "specializationType"),

	/**
	 * The '<em><b>Subtype</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SUBTYPE_VALUE
	 * @generated
	 * @ordered
	 */
	SUBTYPE(2, "subtype", "subtype"),

	/**
	 * The '<em><b>Supertype</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SUPERTYPE_VALUE
	 * @generated
	 * @ordered
	 */
	SUPERTYPE(3, "supertype", "supertype");

	/**
	 * The '<em><b>Exact Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EXACT_TYPE
	 * @model name="exactType"
	 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='exact_type'"
	 * @generated
	 * @ordered
	 */
	public static final int EXACT_TYPE_VALUE = 0;

	/**
	 * The '<em><b>Specialization Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SPECIALIZATION_TYPE
	 * @model name="specializationType"
	 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='specialization_type'"
	 * @generated
	 * @ordered
	 */
	public static final int SPECIALIZATION_TYPE_VALUE = 1;

	/**
	 * The '<em><b>Subtype</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SUBTYPE
	 * @model name="subtype"
	 * @generated
	 * @ordered
	 */
	public static final int SUBTYPE_VALUE = 2;

	/**
	 * The '<em><b>Supertype</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SUPERTYPE
	 * @model name="supertype"
	 * @generated
	 * @ordered
	 */
	public static final int SUPERTYPE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Element Type Relationship Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final ElementTypeRelationshipKind[] VALUES_ARRAY = new ElementTypeRelationshipKind[] {
			EXACT_TYPE,
			SPECIALIZATION_TYPE,
			SUBTYPE,
			SUPERTYPE,
	};

	/**
	 * A public read-only list of all the '<em><b>Element Type Relationship Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<ElementTypeRelationshipKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Element Type Relationship Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *                    the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ElementTypeRelationshipKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ElementTypeRelationshipKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Element Type Relationship Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                 the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ElementTypeRelationshipKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ElementTypeRelationshipKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Element Type Relationship Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ElementTypeRelationshipKind get(int value) {
		switch (value) {
		case EXACT_TYPE_VALUE:
			return EXACT_TYPE;
		case SPECIALIZATION_TYPE_VALUE:
			return SPECIALIZATION_TYPE;
		case SUBTYPE_VALUE:
			return SUBTYPE;
		case SUPERTYPE_VALUE:
			return SUPERTYPE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private ElementTypeRelationshipKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // ElementTypeRelationshipKind
