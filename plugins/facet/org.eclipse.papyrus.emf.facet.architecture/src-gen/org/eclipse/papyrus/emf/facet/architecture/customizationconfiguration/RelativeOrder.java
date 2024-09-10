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
 * A representation of the model object '<em><b>Relative Order</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This ApplicationRule allows to define the placement of a customization relatively to another one.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getLocation <em>Location</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getRelativeCustomizationReference <em>Relative Customization Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getRelativeOrder()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='checkRelativeOrder'"
 * @generated
 */
public interface RelativeOrder extends IApplicationRule {
	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * The default value is <code>"BEFORE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This ApplicationRule allows to define the placement of a customization relatively to another one.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location
	 * @see #setLocation(Location)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getRelativeOrder_Location()
	 * @model default="BEFORE" required="true"
	 * @generated
	 */
	Location getLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Location</em>' attribute.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Location value);

	/**
	 * Returns the value of the '<em><b>Relative Customization Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The referenced Customization used to apply the expective placement.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Relative Customization Reference</em>' reference.
	 * @see #setRelativeCustomizationReference(CustomizationReference)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getRelativeOrder_RelativeCustomizationReference()
	 * @model required="true"
	 * @generated
	 */
	CustomizationReference getRelativeCustomizationReference();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getRelativeCustomizationReference <em>Relative Customization Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Relative Customization Reference</em>' reference.
	 * @see #getRelativeCustomizationReference()
	 * @generated
	 */
	void setRelativeCustomizationReference(CustomizationReference value);

} // RelativeOrder
