/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.advices.values;

import org.eclipse.uml2.uml.ValueSpecification;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.advices.values.ConstantValue#getValueInstance <em>Value Instance</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage#getConstantValue()
 * @model
 * @generated
 */
public interface ConstantValue extends FeatureValue {
	/**
	 * Returns the value of the '<em><b>Value Instance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Instance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Instance</em>' containment reference.
	 * @see #setValueInstance(ValueSpecification)
	 * @see org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage#getConstantValue_ValueInstance()
	 * @model containment="true"
	 * @generated
	 */
	ValueSpecification getValueInstance();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.advices.values.ConstantValue#getValueInstance <em>Value Instance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Instance</em>' containment reference.
	 * @see #getValueInstance()
	 * @generated
	 */
	void setValueInstance(ValueSpecification value);

} // ConstantValue
