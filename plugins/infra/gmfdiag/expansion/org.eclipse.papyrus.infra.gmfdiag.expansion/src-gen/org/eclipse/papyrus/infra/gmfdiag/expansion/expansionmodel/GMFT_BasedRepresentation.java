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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GMFT Based Representation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Usecase is for a specific diagram. In case of class diagram, the goal is to be able to extend existing diagrams based on gmf.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation#getReusedID <em>Reused ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getGMFT_BasedRepresentation()
 * @model
 * @generated
 */
public interface GMFT_BasedRepresentation extends Representation {
	/**
	 * Returns the value of the '<em><b>Reused ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * In this case this is the visualID defined in gmf diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reused ID</em>' attribute.
	 * @see #setReusedID(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getGMFT_BasedRepresentation_ReusedID()
	 * @model ordered="false"
	 * @generated
	 */
	String getReusedID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation#getReusedID <em>Reused ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reused ID</em>' attribute.
	 * @see #getReusedID()
	 * @generated
	 */
	void setReusedID(String value);

} // GMFT_BasedRepresentation
