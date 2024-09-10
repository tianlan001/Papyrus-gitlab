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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Advice Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite <em>Composite</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice <em>Advice</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getOwningAdvice <em>Owning Advice</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAdviceConstraint()
 * @model abstract="true"
 * @generated
 */
public interface AdviceConstraint extends EObject {

	/**
	 * Returns the value of the '<em><b>Composite</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getConstraints <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Composite</em>' container reference.
	 * @see #setComposite(CompositeConstraint)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAdviceConstraint_Composite()
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getConstraints
	 * @model opposite="constraint" transient="false" ordered="false"
	 * @generated
	 */
	CompositeConstraint getComposite();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite <em>Composite</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Composite</em>' container reference.
	 * @see #getComposite()
	 * @generated
	 */
	void setComposite(CompositeConstraint value);

	/**
	 * Returns the value of the '<em><b>Advice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Advice</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Advice</em>' reference.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAdviceConstraint_Advice()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	ConstraintAdviceConfiguration getAdvice();

	/**
	 * Returns the value of the '<em><b>Owning Advice</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration#getConstraints <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owning Advice</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owning Advice</em>' container reference.
	 * @see #setOwningAdvice(ConstraintAdviceConfiguration)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAdviceConstraint_OwningAdvice()
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration#getConstraints
	 * @model opposite="constraint" transient="false" ordered="false"
	 * @generated
	 */
	ConstraintAdviceConfiguration getOwningAdvice();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getOwningAdvice <em>Owning Advice</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Owning Advice</em>' container reference.
	 * @see #getOwningAdvice()
	 * @generated
	 */
	void setOwningAdvice(ConstraintAdviceConfiguration value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" requestDataType="org.eclipse.papyrus.infra.emf.types.constraints.EditCommandRequest" requestRequired="true" requestOrdered="false"
	 * @generated
	 */
	boolean approveRequest(IEditCommandRequest request);
} // AdviceConstraint
