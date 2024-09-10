/**
 *  Copyright (c) 2011-2021 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Remi Schnekenburger - Initial API and implementation
 *  Christian W. Damus - bug 573986
 */
package org.eclipse.papyrus.infra.properties.contexts;

import org.eclipse.papyrus.infra.constraints.DisplayUnit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Section</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getTab <em>Tab</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getAbstractSection()
 * @model abstract="true"
 * @generated
 */
public interface AbstractSection extends DisplayUnit, Annotatable {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getAbstractSection_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Tab</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getAllSections <em>All Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tab</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tab</em>' container reference.
	 * @see #setTab(Tab)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getAbstractSection_Tab()
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getAllSections
	 * @model opposite="allSections" required="true" transient="false"
	 * @generated
	 */
	Tab getTab();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getTab <em>Tab</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tab</em>' container reference.
	 * @see #getTab()
	 * @generated
	 */
	void setTab(Tab value);

} // AbstractSection
