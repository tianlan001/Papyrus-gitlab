/**
 * Copyright (c) 2015 CEA LIST.
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
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tool Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration#getElementDescriptors <em>Element Descriptors</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration#getToolClassName <em>Tool Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage#getToolConfiguration()
 * @model
 * @generated
 */
public interface ToolConfiguration extends LeafConfiguration {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The default value is <code>"CreationTool"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolKind
	 * @see #setKind(ToolKind)
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage#getToolConfiguration_Kind()
	 * @model default="CreationTool" required="true"
	 * @generated
	 */
	ToolKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(ToolKind value);

	/**
	 * Returns the value of the '<em><b>Element Descriptors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Descriptors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Descriptors</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage#getToolConfiguration_ElementDescriptors()
	 * @model containment="true"
	 * @generated
	 */
	EList<ElementDescriptor> getElementDescriptors();

	/**
	 * Returns the value of the '<em><b>Tool Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tool Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tool Class Name</em>' attribute.
	 * @see #setToolClassName(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage#getToolConfiguration_ToolClassName()
	 * @model
	 * @generated
	 */
	String getToolClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration#getToolClassName <em>Tool Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tool Class Name</em>' attribute.
	 * @see #getToolClassName()
	 * @generated
	 */
	void setToolClassName(String value);

} // ToolConfiguration
