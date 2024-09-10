/*****************************************************************************
 * Copyright (c) 2011, 2015 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus - bug 469188
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sashwindows.di;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tab Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder#getCurrentSelection <em>Current Selection</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage#getTabFolder()
 * @model
 * @generated
 */
public interface TabFolder extends AbstractPanel {

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.sashwindows.di.PageRef}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.core.sashwindows.di.PageRef#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage#getTabFolder_Children()
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.PageRef#getParent
	 * @model opposite="parent" containment="true" ordered="false"
	 * @generated
	 */
	EList<PageRef> getChildren();

	/**
	 * Returns the value of the '<em><b>Current Selection</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder#getChildren() <em>Children</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Selection</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Current Selection</em>' reference.
	 * @see #setCurrentSelection(PageRef)
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage#getTabFolder_CurrentSelection()
	 * @model ordered="false"
	 * @generated
	 * @since 1.2
	 */
	PageRef getCurrentSelection();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder#getCurrentSelection <em>Current Selection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Current Selection</em>' reference.
	 * @see #getCurrentSelection()
	 * @generated
	 * @since 1.2
	 */
	void setCurrentSelection(PageRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model oldIndexDataType="org.eclipse.papyrus.infra.core.sashwindows.di.int" oldIndexRequired="true" oldIndexOrdered="false" newIndexDataType="org.eclipse.papyrus.infra.core.sashwindows.di.int" newIndexRequired="true" newIndexOrdered="false"
	 * @generated
	 */
	void movePage(int oldIndex, int newIndex);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model pageIdentifierDataType="org.eclipse.papyrus.infra.core.sashwindows.di.JavaObject" pageIdentifierRequired="true" pageIdentifierOrdered="false"
	 * @generated
	 */
	void addPage(Object pageIdentifier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model pageIndexDataType="org.eclipse.papyrus.infra.core.sashwindows.di.int" pageIndexRequired="true" pageIndexOrdered="false"
	 * @generated
	 */
	void removePage(int pageIndex);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model indexDataType="org.eclipse.uml2.types.Integer" indexRequired="true" indexOrdered="false" pageIdentifierDataType="org.eclipse.papyrus.infra.core.sashwindows.di.JavaObject" pageIdentifierRequired="true" pageIdentifierOrdered="false"
	 * @generated
	 */
	void addPage(int index, Object pageIdentifier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model pageRefRequired="true" pageRefOrdered="false"
	 * @generated
	 * @since 1.2
	 */
	void addPage(PageRef pageRef);

} // TabFolder
