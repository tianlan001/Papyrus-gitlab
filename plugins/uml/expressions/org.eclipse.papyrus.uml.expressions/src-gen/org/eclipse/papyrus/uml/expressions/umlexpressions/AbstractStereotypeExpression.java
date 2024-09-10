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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.expressions.umlexpressions;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Stereotype Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract Expression working on stereotype. This expression allows to represent a stereotype using its qualified name.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getProfileURI <em>Profile URI</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getAbstractStereotypeExpression()
 * @model abstract="true"
 * @generated
 */
public interface AbstractStereotypeExpression extends IBooleanEObjectExpression {
	/**
	 * Returns the value of the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The qualified name of the required stereotype.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stereotype Qualified Name</em>' attribute.
	 * @see #setStereotypeQualifiedName(String)
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getAbstractStereotypeExpression_StereotypeQualifiedName()
	 * @model ordered="false"
	 * @generated
	 */
	String getStereotypeQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stereotype Qualified Name</em>' attribute.
	 * @see #getStereotypeQualifiedName()
	 * @generated
	 */
	void setStereotypeQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The URI of the profile owning the stereotype.
	 * The URI can be null or empty. In this case, we don't check the URI of the profile providing the Stereotype.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Profile URI</em>' attribute.
	 * @see #setProfileURI(String)
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getAbstractStereotypeExpression_ProfileURI()
	 * @model ordered="false"
	 * @generated
	 */
	String getProfileURI();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getProfileURI <em>Profile URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Profile URI</em>' attribute.
	 * @see #getProfileURI()
	 * @generated
	 */
	void setProfileURI(String value);

} // AbstractStereotypeExpression
