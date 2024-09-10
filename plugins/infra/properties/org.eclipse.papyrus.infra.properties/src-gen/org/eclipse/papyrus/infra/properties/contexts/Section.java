/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Section#getSectionFile <em>Section File</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Section#getWidget <em>Widget</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Section#getViews <em>Views</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Section#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getSection()
 * @model
 * @generated
 */
public interface Section extends AbstractSection {
	/**
	 * Returns the value of the '<em><b>Section File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Section File</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Section File</em>' attribute.
	 * @see #setSectionFile(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getSection_SectionFile()
	 * @model required="true"
	 * @generated
	 */
	String getSectionFile();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getSectionFile <em>Section File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Section File</em>' attribute.
	 * @see #getSectionFile()
	 * @generated
	 */
	void setSectionFile(String value);

	/**
	 * Returns the value of the '<em><b>Widget</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget</em>' reference.
	 * @see #setWidget(CompositeWidget)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getSection_Widget()
	 * @model required="true"
	 * @generated
	 */
	CompositeWidget getWidget();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getWidget <em>Widget</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget</em>' reference.
	 * @see #getWidget()
	 * @generated
	 */
	void setWidget(CompositeWidget value);

	/**
	 * Returns the value of the '<em><b>Views</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.View}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Views</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Views</em>' reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getSection_Views()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<View> getViews();

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getSections <em>Sections</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getTab() <em>Tab</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(Tab)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getSection_Owner()
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getSections
	 * @model opposite="sections" required="true" transient="true" volatile="true" derived="true"
	 *        annotation="subsets"
	 * @generated
	 */
	Tab getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Tab value);

} // Section
