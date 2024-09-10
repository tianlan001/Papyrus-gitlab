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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Single Stereotype Attribute Equality Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This expression allows to check equality between the current property value of the context object and the expected value. 
 * The evaluate method returns true, in case of equality and false otherwise.
 * If the propertyName or the stereotypeQualifiedName are not defined, the evalute methods retuns false.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getExpectedValue <em>Expected Value</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getPropertyName <em>Property Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getSingleStereotypeAttributeEqualityExpression()
 * @model
 * @generated
 */
public interface SingleStereotypeAttributeEqualityExpression extends AbstractStereotypeExpression {
	/**
	 * Returns the value of the '<em><b>Expected Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expected Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expected Value</em>' attribute.
	 * @see #setExpectedValue(String)
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getSingleStereotypeAttributeEqualityExpression_ExpectedValue()
	 * @model ordered="false"
	 * @generated
	 */
	String getExpectedValue();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getExpectedValue <em>Expected Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expected Value</em>' attribute.
	 * @see #getExpectedValue()
	 * @generated
	 */
	void setExpectedValue(String value);

	/**
	 * Returns the value of the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of the stereotype property. 
	 * The property must be single valued and typde with a PrimitiveTypes
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property Name</em>' attribute.
	 * @see #setPropertyName(String)
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getSingleStereotypeAttributeEqualityExpression_PropertyName()
	 * @model ordered="false"
	 * @generated
	 */
	String getPropertyName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getPropertyName <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Name</em>' attribute.
	 * @see #getPropertyName()
	 * @generated
	 */
	void setPropertyName(String value);

} // SingleStereotypeAttributeEqualityExpression
