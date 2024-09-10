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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule that controls the definition of a representation kind.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.Rule#isPermit <em>Permit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getRule()
 * @model abstract="true"
 * @generated
 */
public interface Rule extends EObject {
	/**
	 * Returns the value of the '<em><b>Permit</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the rule permits or forbids the action it represents
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Permit</em>' attribute.
	 * @see #setPermit(boolean)
	 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage#getRule_Permit()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isPermit();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.architecture.representation.Rule#isPermit <em>Permit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Permit</em>' attribute.
	 * @see #isPermit()
	 * @generated
	 */
	void setPermit(boolean value);

} // Rule
