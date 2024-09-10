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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Customization Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This object allows to reference an EMF Facet Customization.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getReferencedCustomization <em>Referenced Customization</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getApplicationRule <em>Application Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getCustomizationReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='checkCustomizationReference'"
 * @generated
 */
public interface CustomizationReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A description of the referenced Customization (not mandatory).
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getCustomizationReference_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Referenced Customization</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The referenced EMF Facet Customization.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Referenced Customization</em>' reference.
	 * @see #setReferencedCustomization(Customization)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getCustomizationReference_ReferencedCustomization()
	 * @model required="true"
	 * @generated
	 */
	Customization getReferencedCustomization();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getReferencedCustomization <em>Referenced Customization</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Referenced Customization</em>' reference.
	 * @see #getReferencedCustomization()
	 * @generated
	 */
	void setReferencedCustomization(Customization value);

	/**
	 * Returns the value of the '<em><b>Application Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The rule giving the way to apply the Customization.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Application Rule</em>' containment reference.
	 * @see #setApplicationRule(IApplicationRule)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getCustomizationReference_ApplicationRule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IApplicationRule getApplicationRule();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getApplicationRule <em>Application Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Application Rule</em>' containment reference.
	 * @see #getApplicationRule()
	 * @generated
	 */
	void setApplicationRule(IApplicationRule value);

} // CustomizationReference
