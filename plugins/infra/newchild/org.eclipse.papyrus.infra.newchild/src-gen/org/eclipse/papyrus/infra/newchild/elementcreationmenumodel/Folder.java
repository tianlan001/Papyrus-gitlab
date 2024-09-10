/**
 * Copyright (c) 2017 CEA LIST.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *  
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.infra.newchild.elementcreationmenumodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder#getMenu <em>Menu</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends Menu {
	/**
	 * Returns the value of the '<em><b>Menu</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menu</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menu</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage#getFolder_Menu()
	 * @model containment="true"
	 * @generated
	 */
	EList<Menu> getMenu();

} // Folder
