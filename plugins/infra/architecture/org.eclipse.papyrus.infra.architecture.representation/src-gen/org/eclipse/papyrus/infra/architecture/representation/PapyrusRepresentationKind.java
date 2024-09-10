/**
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.infra.architecture.representation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus Representation Kind</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A subclass of representation kind that adds rules
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getModelRules <em>Model Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getOwningRules <em>Owning Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getImplementationID <em>Implementation ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getPapyrusRepresentationKind()
 * @model abstract="true"
 * @generated
 */
public interface PapyrusRepresentationKind extends RepresentationKind {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The parent representation kind from which this representation kind inherits
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(PapyrusRepresentationKind)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getPapyrusRepresentationKind_Parent()
	 * @model
	 * @generated
	 */
	PapyrusRepresentationKind getParent();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(PapyrusRepresentationKind value);

	/**
	 * Returns the value of the '<em><b>Model Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.architecture.representation.ModelRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of rules that determine which model elements can be the semantic element behind this diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Model Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getPapyrusRepresentationKind_ModelRules()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ModelRule> getModelRules();

	/**
	 * Returns the value of the '<em><b>Owning Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.architecture.representation.OwningRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of rules that determine which elements can be the syntactic owner of this diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owning Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getPapyrusRepresentationKind_OwningRules()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<OwningRule> getOwningRules();

	/**
	 * Returns the value of the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The id of the underlying implementation of this representation kind
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Implementation ID</em>' attribute.
	 * @see #setImplementationID(String)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getPapyrusRepresentationKind_ImplementationID()
	 * @model required="true"
	 * @generated
	 */
	String getImplementationID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getImplementationID <em>Implementation ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation ID</em>' attribute.
	 * @see #getImplementationID()
	 * @generated
	 */
	void setImplementationID(String value);

} // PapyrusRepresentationKind
