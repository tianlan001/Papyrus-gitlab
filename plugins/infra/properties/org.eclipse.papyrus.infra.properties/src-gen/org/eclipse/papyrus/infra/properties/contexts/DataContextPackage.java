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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Context Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.DataContextPackage#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextPackage()
 * @model
 * @generated
 */
public interface DataContextPackage extends DataContextElement {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextPackage_Elements()
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage
	 * @model opposite="package" containment="true"
	 * @generated
	 */
	EList<DataContextElement> getElements();

} // DataContextPackage
