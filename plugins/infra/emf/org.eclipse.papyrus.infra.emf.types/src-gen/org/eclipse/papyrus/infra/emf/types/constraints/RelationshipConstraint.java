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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relationship Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint#getPermissions <em>Permission</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getRelationshipConstraint()
 * @model annotation="duplicates"
 * @generated
 */
public interface RelationshipConstraint extends AdviceConstraint {
	/**
	 * Returns the value of the '<em><b>Permission</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permission</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Permission</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getRelationshipConstraint_Permission()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<EndPermission> getPermissions();

} // RelationshipConstraint
