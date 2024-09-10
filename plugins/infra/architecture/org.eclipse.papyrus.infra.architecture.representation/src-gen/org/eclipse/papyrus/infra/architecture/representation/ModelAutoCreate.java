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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Auto Create</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The settings of auto creating model elements with a representation kind
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getOrigin <em>Origin</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getCreationType <em>Creation Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getModelAutoCreate()
 * @model
 * @generated
 */
public interface ModelAutoCreate extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A reference in the origin type that will contain the newly created model element with this representation kind.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(EReference)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getModelAutoCreate_Feature()
	 * @model required="true"
	 * @generated
	 */
	EReference getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(EReference value);

	/**
	 * Returns the value of the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type (from the language's metamodel) that will own the newly created model element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Origin</em>' reference.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getModelAutoCreate_Origin()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EClass getOrigin();

	/**
	 * Returns the value of the '<em><b>Creation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element type (from the element type registry) that will used to created the new model element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Creation Type</em>' attribute.
	 * @see #setCreationType(String)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getModelAutoCreate_CreationType()
	 * @model required="true"
	 * @generated
	 */
	String getCreationType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getCreationType <em>Creation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Type</em>' attribute.
	 * @see #getCreationType()
	 * @generated
	 */
	void setCreationType(String value);

} // ModelAutoCreate
