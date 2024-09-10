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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Representation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getEditPartQualifiedName <em>Edit Part Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getViewFactory <em>View Factory</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation()
 * @model abstract="true"
 * @generated
 */
public interface AbstractRepresentation extends EObject {
	/**
	 * Returns the value of the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * if the kind is filled, the editPartQualifiedName has to be filled. It references the controler see GEF framework
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Edit Part Qualified Name</em>' attribute.
	 * @see #setEditPartQualifiedName(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation_EditPartQualifiedName()
	 * @model ordered="false"
	 * @generated
	 */
	String getEditPartQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getEditPartQualifiedName <em>Edit Part Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Part Qualified Name</em>' attribute.
	 * @see #getEditPartQualifiedName()
	 * @generated
	 */
	void setEditPartQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' reference.
	 * @see #setKind(RepresentationKind)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation_Kind()
	 * @model ordered="false"
	 * @generated
	 */
	RepresentationKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getKind <em>Kind</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' reference.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(RepresentationKind value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * It is more like a comment, it is no used by interpretors.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation_Name()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * if the kind is filled, the view factory has to be filled. It references the factory of notation element see (model inside GEF framework)
	 * Have alook to http://www-01.ibm.com/support/knowledgecenter/SS8PJ7_7.0.0/org.eclipse.gmf.doc/reference/api/runtime/org/eclipse/gmf/runtime/diagram/core/view/factories/ViewFactory.html
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>View Factory</em>' attribute.
	 * @see #setViewFactory(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation_ViewFactory()
	 * @model ordered="false"
	 * @generated
	 */
	String getViewFactory();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getViewFactory <em>View Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Factory</em>' attribute.
	 * @see #getViewFactory()
	 * @generated
	 */
	void setViewFactory(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage#getAbstractRepresentation_Description()
	 * @model ordered="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" ordered="false" diagnosticRequired="true" diagnosticOrdered="false" contextRequired="true" contextOrdered="false"
	 * @generated
	 */
	boolean validate(DiagnosticChain diagnostic, Map context);

} // AbstractRepresentation
