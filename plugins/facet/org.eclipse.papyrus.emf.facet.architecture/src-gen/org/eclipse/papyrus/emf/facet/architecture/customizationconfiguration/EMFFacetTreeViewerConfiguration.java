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

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EMF Facet Tree Viewer Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This object allows to define a set of EMF Facet Customization to apply on the TreeViewer.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getCustomizationReferences <em>Customization References</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getExtends <em>Extends</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getEMFFacetTreeViewerConfiguration()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='checkConfiguration'"
 * @generated
 */
public interface EMFFacetTreeViewerConfiguration extends TreeViewerConfiguration {
	/**
	 * Returns the value of the '<em><b>Customization References</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This list references the Customization to apply, with there application rule.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Customization References</em>' containment reference list.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getEMFFacetTreeViewerConfiguration_CustomizationReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<CustomizationReference> getCustomizationReferences();

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This property references the extended EMFFatceTreeViewerConfiguration
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Extends</em>' reference.
	 * @see #setExtends(EMFFacetTreeViewerConfiguration)
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#getEMFFacetTreeViewerConfiguration_Extends()
	 * @model
	 * @generated
	 */
	EMFFacetTreeViewerConfiguration getExtends();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getExtends <em>Extends</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Extends</em>' reference.
	 * @see #getExtends()
	 * @generated
	 */
	void setExtends(EMFFacetTreeViewerConfiguration value);

} // EMFFacetTreeViewerConfiguration
