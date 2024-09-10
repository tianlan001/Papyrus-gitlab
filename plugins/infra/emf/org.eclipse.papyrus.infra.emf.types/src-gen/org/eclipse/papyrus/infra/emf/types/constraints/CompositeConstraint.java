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
import org.eclipse.papyrus.infra.filters.OperatorKind;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * if operator = filters::OperatorKind::_not then
 * constraint->size() = 1
 * else
 * constraint->size() >= 2
 * endif
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getOperator <em>Operator</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getConstraints <em>Constraint</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getCompositeConstraint()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='operandCount'"
 *        annotation="duplicates"
 * @generated
 */
public interface CompositeConstraint extends AdviceConstraint {
	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Constraint</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getCompositeConstraint_Constraint()
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite
	 * @model opposite="composite" containment="true" ordered="false"
	 * @generated
	 */
	EList<AdviceConstraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.filters.OperatorKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @see #setOperator(OperatorKind)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getCompositeConstraint_Operator()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	OperatorKind getOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OperatorKind value);

} // CompositeConstraint
