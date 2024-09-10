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

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Owning Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule that controls what element can own representations of this kind.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getStereotypes <em>Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getNewModelPath <em>New Model Path</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getSelectDiagramRoot <em>Select Diagram Root</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule()
 * @model
 * @generated
 */
public interface OwningRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type (from the language's metamodel) that a model element must have to own a representation of this kind.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Element</em>' reference.
	 * @see #setElement(EClass)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule_Element()
	 * @model
	 * @generated
	 */
	EClass getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getElement <em>Element</em>}' reference.
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
	 * The set of stereotypes (from the language's profiles) that a model element must have to own a representation of this kind.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stereotypes</em>' reference list.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule_Stereotypes()
	 * @model
	 * @generated
	 */
	EList<EClass> getStereotypes();

	/**
	 * Returns the value of the '<em><b>Multiplicity</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiplicity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The maximum number of representations of this kind that a model element can own. -1 represents an unbounded number.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Multiplicity</em>' attribute.
	 * @see #setMultiplicity(int)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule_Multiplicity()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMultiplicity();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getMultiplicity <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiplicity</em>' attribute.
	 * @see #getMultiplicity()
	 * @generated
	 */
	void setMultiplicity(int value);

	/**
	 * Returns the value of the '<em><b>New Model Path</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Model Path</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The settings of auto creating model elements with this representation kind
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>New Model Path</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule_NewModelPath()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModelAutoCreate> getNewModelPath();

	/**
	 * Returns the value of the '<em><b>Select Diagram Root</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Select Diagram Root</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The settings of selecting a model element that is the root of this representation kind
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Select Diagram Root</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getOwningRule_SelectDiagramRoot()
	 * @model containment="true"
	 * @generated
	 */
	EList<RootAutoSelect> getSelectDiagramRoot();

} // OwningRule
