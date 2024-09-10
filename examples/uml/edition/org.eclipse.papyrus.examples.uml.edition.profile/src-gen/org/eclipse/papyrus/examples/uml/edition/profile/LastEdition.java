/**
 * Copyright (c) 2021 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Ibtihel Khemir (CEA LIST) ibtihel.khemir@cea.fr- Initial API and implementation
 */
package org.eclipse.papyrus.examples.uml.edition.profile;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Last
 * Edition</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getDate
 * <em>Date</em>}</li>
 * <li>{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getBase_NamedElement
 * <em>Base Named Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.examples.uml.edition.profile.EditionExamplePackage#getLastEdition()
 * @model
 * @generated
 */
public interface LastEdition extends EObject {
	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(String)
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.EditionExamplePackage#getLastEdition_Date()
	 * @model dataType="org.eclipse.uml2.types.String" required="true"
	 *        ordered="false"
	 * @generated
	 */
	String getDate();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getDate
	 * <em>Date</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(String value);

	/**
	 * Returns the value of the '<em><b>Base Named Element</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Named Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Base Named Element</em>' reference.
	 * @see #setBase_NamedElement(NamedElement)
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.EditionExamplePackage#getLastEdition_Base_NamedElement()
	 * @model ordered="false"
	 * @generated
	 */
	NamedElement getBase_NamedElement();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getBase_NamedElement
	 * <em>Base Named Element</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Base Named Element</em>' reference.
	 * @see #getBase_NamedElement()
	 * @generated
	 */
	void setBase_NamedElement(NamedElement value);

} // LastEdition
