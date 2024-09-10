/**
 * Copyright (c) 2014 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage
 * @generated
 */
public interface AssistantFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	AssistantFactory eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Assisted Element Type Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Assisted Element Type Filter</em>'.
	 * @generated
	 */
	AssistedElementTypeFilter createAssistedElementTypeFilter();

	/**
	 * Returns a new object of class '<em>Modeling Assistant Provider</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Modeling Assistant Provider</em>'.
	 * @generated
	 */
	ModelingAssistantProvider createModelingAssistantProvider();

	/**
	 * Returns a new object of class '<em>Connection Assistant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Connection Assistant</em>'.
	 * @generated
	 */
	ConnectionAssistant createConnectionAssistant();

	/**
	 * Returns a new object of class '<em>Popup Assistant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Popup Assistant</em>'.
	 * @generated
	 */
	PopupAssistant createPopupAssistant();

	/**
	 * Returns a new object of class '<em>Element Type Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Element Type Filter</em>'.
	 * @generated
	 */
	ElementTypeFilter createElementTypeFilter();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	AssistantPackage getAssistantPackage();

} // AssistantFactory
