/**
 * Copyright (c) 2015 CEA LIST.
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
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage
 * @generated
 */
public interface ExpansionModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpansionModelFactory eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Representation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Representation</em>'.
	 * @generated
	 */
	Representation createRepresentation();

	/**
	 * Returns a new object of class '<em>Representation Kind</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Representation Kind</em>'.
	 * @generated
	 */
	RepresentationKind createRepresentationKind();

	/**
	 * Returns a new object of class '<em>Induced Representation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Induced Representation</em>'.
	 * @generated
	 */
	InducedRepresentation createInducedRepresentation();

	/**
	 * Returns a new object of class '<em>Graphical Element Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graphical Element Library</em>'.
	 * @generated
	 */
	GraphicalElementLibrary createGraphicalElementLibrary();

	/**
	 * Returns a new object of class '<em>Use Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Use Context</em>'.
	 * @generated
	 */
	UseContext createUseContext();

	/**
	 * Returns a new object of class '<em>GMFT Based Representation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GMFT Based Representation</em>'.
	 * @generated
	 */
	GMFT_BasedRepresentation createGMFT_BasedRepresentation();

	/**
	 * Returns a new object of class '<em>Diagram Expansion</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diagram Expansion</em>'.
	 * @generated
	 */
	DiagramExpansion createDiagramExpansion();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExpansionModelPackage getExpansionModelPackage();

} //ExpansionModelFactory
