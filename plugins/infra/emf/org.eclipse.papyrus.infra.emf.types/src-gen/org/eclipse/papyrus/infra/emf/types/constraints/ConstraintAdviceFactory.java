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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage
 * @generated
 */
public interface ConstraintAdviceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	ConstraintAdviceFactory eINSTANCE = org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	ConstraintAdviceConfiguration createConstraintAdviceConfiguration();

	/**
	 * Returns a new object of class '<em>Reference Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Reference Constraint</em>'.
	 * @generated
	 */
	ReferenceConstraint createReferenceConstraint();

	/**
	 * Returns a new object of class '<em>Any Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Any Reference</em>'.
	 * @generated
	 */
	AnyReference createAnyReference();

	/**
	 * Returns a new object of class '<em>Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Reference</em>'.
	 * @generated
	 */
	Reference createReference();

	/**
	 * Returns a new object of class '<em>Element Type Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Element Type Filter</em>'.
	 * @generated
	 */
	ElementTypeFilter createElementTypeFilter();

	/**
	 * Returns a new object of class '<em>Relationship Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Relationship Constraint</em>'.
	 * @generated
	 */
	RelationshipConstraint createRelationshipConstraint();

	/**
	 * Returns a new object of class '<em>End Permission</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>End Permission</em>'.
	 * @generated
	 */
	EndPermission createEndPermission();

	/**
	 * Returns a new object of class '<em>Composite Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Composite Constraint</em>'.
	 * @generated
	 */
	CompositeConstraint createCompositeConstraint();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConstraintAdvicePackage getConstraintAdvicePackage();

} // ConstraintAdviceFactory
