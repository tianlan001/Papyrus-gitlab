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

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Representation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation is an element that is displayed on a diagram.
 * It is attached to a controler (see editpart) and to a notation structure (build by the viewFactory)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getInducedRepresentations <em>Induced Representations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getSubRepresentations <em>Sub Representations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getGraphicalElementTypeRef <em>Graphical Element Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getRepresentation()
 * @model
 * @generated
 */
public interface Representation extends AbstractRepresentation {
	/**
	 * Returns the value of the '<em><b>Induced Representations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This reperesentation that musr be automatically created with this representation, for example compartment, labels
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Induced Representations</em>' reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getRepresentation_InducedRepresentations()
	 * @model ordered="false"
	 * @generated
	 */
	EList<InducedRepresentation> getInducedRepresentations();

	/**
	 * Returns the value of the '<em><b>Sub Representations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * this is representation that can be directly create inside this representation for example  borderItems like ports
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sub Representations</em>' reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getRepresentation_SubRepresentations()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Representation> getSubRepresentations();

	/**
	 * Returns the value of the '<em><b>Graphical Element Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphical Element Type Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphical Element Type Ref</em>' reference.
	 * @see #setGraphicalElementTypeRef(ElementTypeConfiguration)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getRepresentation_GraphicalElementTypeRef()
	 * @model ordered="false"
	 * @generated
	 */
	ElementTypeConfiguration getGraphicalElementTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getGraphicalElementTypeRef <em>Graphical Element Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphical Element Type Ref</em>' reference.
	 * @see #getGraphicalElementTypeRef()
	 * @generated
	 */
	void setGraphicalElementTypeRef(ElementTypeConfiguration value);

} // Representation
