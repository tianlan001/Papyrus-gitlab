/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Redefinition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This ApplicationRule allows to redefines an existing Customization.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition#getRedefinedCustomizationReference <em>Redefined Customization Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getRedefinition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='checkRedefinition'"
 * @generated
 */
public interface Redefinition extends IApplicationRule {
	/**
	 * Returns the value of the '<em><b>Redefined Customization Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The redefined Customization.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Customization Reference</em>' reference.
	 * @see #setRedefinedCustomizationReference(CustomizationReference)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getRedefinition_RedefinedCustomizationReference()
	 * @model required="true"
	 * @generated
	 */
	CustomizationReference getRedefinedCustomizationReference();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition#getRedefinedCustomizationReference <em>Redefined Customization Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Redefined Customization Reference</em>' reference.
	 * @see #getRedefinedCustomizationReference()
	 * @generated
	 */
	void setRedefinedCustomizationReference(CustomizationReference value);

} // Redefinition
