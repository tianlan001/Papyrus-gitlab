/**
 * Copyright (c) 2021 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Ibtihel Khemir (CEA LIST) ibtihel.khemir@cea.fr- Initial API and implementation
 */
package org.eclipse.papyrus.examples.uml.edition.profile;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.examples.uml.edition.profile.EditionExamplePackage
 * @generated
 */
public interface EditionExampleFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	EditionExampleFactory eINSTANCE = org.eclipse.papyrus.examples.uml.edition.profile.impl.EditionExampleFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Last Edition</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Last Edition</em>'.
	 * @generated
	 */
	LastEdition createLastEdition();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	EditionExamplePackage getEditionExamplePackage();

} // EditionExampleFactory
