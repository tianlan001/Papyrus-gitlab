/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 568782, 568853
 */
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Type Set Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAdviceBindingsConfigurations <em>Advice Bindings Configurations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings <em>All Advice Bindings</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getElementTypeConfigurations <em>Element Type Configurations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getMetamodelNsURI <em>Metamodel Ns URI</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getElementTypeSetConfiguration()
 * @model
 * @generated
 */
public interface ElementTypeSetConfiguration extends ConfigurationElement, IdentifiedConfiguration, NamedConfiguration {
	/**
	 * Returns the value of the '<em><b>Element Type Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwningSet <em>Owning Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type Configurations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type Configurations</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getElementTypeSetConfiguration_ElementTypeConfigurations()
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwningSet
	 * @model opposite="owningSet" containment="true"
	 * @generated
	 */
	EList<ElementTypeConfiguration> getElementTypeConfigurations();

	/**
	 * Returns the value of the '<em><b>Advice Bindings Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningSet <em>Owning Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Advice Bindings Configurations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Advice Bindings Configurations</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getElementTypeSetConfiguration_AdviceBindingsConfigurations()
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningSet
	 * @model opposite="owningSet" containment="true"
	 * @generated
	 */
	EList<AbstractAdviceBindingConfiguration> getAdviceBindingsConfigurations();

	/**
	 * Returns the value of the '<em><b>All Advice Bindings</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet <em>Element Type Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Advice Bindings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Advice Bindings</em>' reference list.
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getElementTypeSetConfiguration_AllAdviceBindings()
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet
	 * @model opposite="elementTypeSet" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<AbstractAdviceBindingConfiguration> getAllAdviceBindings();

	/**
	 * Returns the value of the '<em><b>Metamodel Ns URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel Ns URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel Ns URI</em>' attribute.
	 * @see #setMetamodelNsURI(String)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getElementTypeSetConfiguration_MetamodelNsURI()
	 * @model required="true"
	 * @generated
	 */
	String getMetamodelNsURI();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getMetamodelNsURI <em>Metamodel Ns URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel Ns URI</em>' attribute.
	 * @see #getMetamodelNsURI()
	 * @generated
	 */
	void setMetamodelNsURI(String value);

} // ElementTypeSetConfiguration
