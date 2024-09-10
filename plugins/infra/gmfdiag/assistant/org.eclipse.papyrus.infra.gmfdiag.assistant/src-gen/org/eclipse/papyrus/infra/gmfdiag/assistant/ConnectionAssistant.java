/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Assistant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getSourceFilter <em>Source Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedSourceFilter <em>Owned Source Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getTargetFilter <em>Target Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedTargetFilter <em>Owned Target Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider <em>Owning Provider</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant()
 * @model
 * @generated
 */
public interface ConnectionAssistant extends Assistant {
	/**
	 * Returns the value of the '<em><b>Owning Provider</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getConnectionAssistants <em>Connection Assistant</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getProvider() <em>Provider</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owning Provider</em>' container reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owning Provider</em>' container reference.
	 * @see #setOwningProvider(ModelingAssistantProvider)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant_OwningProvider()
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getConnectionAssistants
	 * @model opposite="connectionAssistant" required="true" transient="false" ordered="false"
	 * @generated
	 */
	ModelingAssistantProvider getOwningProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider <em>Owning Provider</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Owning Provider</em>' container reference.
	 * @see #getOwningProvider()
	 * @generated
	 */
	void setOwningProvider(ModelingAssistantProvider value);

	/**
	 * Returns the value of the '<em><b>Source Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Filter</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Source Filter</em>' reference.
	 * @see #setSourceFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant_SourceFilter()
	 * @model ordered="false"
	 * @generated
	 */
	Filter getSourceFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getSourceFilter <em>Source Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source Filter</em>' reference.
	 * @see #getSourceFilter()
	 * @generated
	 */
	void setSourceFilter(Filter value);

	/**
	 * Returns the value of the '<em><b>Owned Source Filter</b></em>' containment reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getSourceFilter() <em>Source Filter</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Source Filter</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owned Source Filter</em>' containment reference.
	 * @see #setOwnedSourceFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant_OwnedSourceFilter()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Filter getOwnedSourceFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedSourceFilter <em>Owned Source Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Owned Source Filter</em>' containment reference.
	 * @see #getOwnedSourceFilter()
	 * @generated
	 */
	void setOwnedSourceFilter(Filter value);

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.filters.Filter}, with the specified '<em><b>Name</b></em>', and sets the '<em><b>Owned Source Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' for the new {@link org.eclipse.papyrus.infra.filters.Filter}, or <code>null</code>.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to create.
	 * @return The new {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * @see #getOwnedSourceFilter()
	 * @generated
	 */
	Filter createOwnedSourceFilter(String name, EClass eClass);

	/**
	 * Returns the value of the '<em><b>Target Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Filter</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Target Filter</em>' reference.
	 * @see #setTargetFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant_TargetFilter()
	 * @model ordered="false"
	 * @generated
	 */
	Filter getTargetFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getTargetFilter <em>Target Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target Filter</em>' reference.
	 * @see #getTargetFilter()
	 * @generated
	 */
	void setTargetFilter(Filter value);

	/**
	 * Returns the value of the '<em><b>Owned Target Filter</b></em>' containment reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getTargetFilter() <em>Target Filter</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Target Filter</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owned Target Filter</em>' containment reference.
	 * @see #setOwnedTargetFilter(Filter)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getConnectionAssistant_OwnedTargetFilter()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Filter getOwnedTargetFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedTargetFilter <em>Owned Target Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Owned Target Filter</em>' containment reference.
	 * @see #getOwnedTargetFilter()
	 * @generated
	 */
	void setOwnedTargetFilter(Filter value);

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.filters.Filter}, with the specified '<em><b>Name</b></em>', and sets the '<em><b>Owned Target Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' for the new {@link org.eclipse.papyrus.infra.filters.Filter}, or <code>null</code>.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to create.
	 * @return The new {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * @see #getOwnedTargetFilter()
	 * @generated
	 */
	Filter createOwnedTargetFilter(String name, EClass eClass);

} // ConnectionAssistant
