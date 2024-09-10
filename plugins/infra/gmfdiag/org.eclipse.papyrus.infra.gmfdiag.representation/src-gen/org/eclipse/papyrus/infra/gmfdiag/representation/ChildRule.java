/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.gmfdiag.representation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.papyrus.infra.architecture.representation.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Child Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule that controls the types of elements that can be dropped on this diagram kind.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getStereotypes <em>Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getOrigin <em>Origin</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getInsertionPath <em>Insertion Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getChildRule()
 * @model
 * @generated
 */
public interface ChildRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type (from the language's metamodel) that an element must have to be dropped on this diagram kind.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Element</em>' reference.
	 * @see #setElement(EClass)
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getChildRule_Element()
	 * @model
	 * @generated
	 */
	EClass getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getElement <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' reference.
	 * @see #getElement()
	 * @generated
	 */
	void setElement(EClass value);

	/**
	 * Returns the value of the '<em><b>Stereotypes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stereotypes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The stereotype (from the language's profiles) that an element must have to be dropped on this diagram kind.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stereotypes</em>' reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getChildRule_Stereotypes()
	 * @model
	 * @generated
	 */
	EList<EClass> getStereotypes();

	/**
	 * Returns the value of the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type (from the language's metamodel) that an element must have to be the target of the drop of the child model element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Origin</em>' reference.
	 * @see #setOrigin(EClass)
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getChildRule_Origin()
	 * @model
	 * @generated
	 */
	EClass getOrigin();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getOrigin <em>Origin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' reference.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(EClass value);

	/**
	 * Returns the value of the '<em><b>Insertion Path</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.representation.PathElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertion Path</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A path of properties that must be used from the origin to insert the new child model element in the model.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Insertion Path</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getChildRule_InsertionPath()
	 * @model containment="true"
	 * @generated
	 */
	EList<PathElement> getInsertionPath();

} // ChildRule
