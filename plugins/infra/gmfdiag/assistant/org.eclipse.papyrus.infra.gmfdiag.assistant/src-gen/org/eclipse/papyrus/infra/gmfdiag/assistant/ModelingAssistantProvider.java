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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;
import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modeling Assistant Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * From package assistantapi.
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getAssistants <em>Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getOwnedFilters <em>Owned Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getPopupAssistants <em>Popup Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getConnectionAssistants <em>Connection Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypeIDs <em>Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext <em>Client Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContextID <em>Client Context ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes <em>Excluded Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypeIDs <em>Excluded Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes <em>Relationship Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypeIDs <em>Relationship Type ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider()
 * @model superTypes="org.eclipse.papyrus.infra.gmfdiag.assistant.IModelingAssistantProvider"
 * @generated
 */
public interface ModelingAssistantProvider extends EObject, IModelingAssistantProvider {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_Name()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Assistant</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getProvider <em>Provider</em>}'.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assistant</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Assistant</em>' reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_Assistant()
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getProvider
	 * @model opposite="provider" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<Assistant> getAssistants();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" required="true" ordered="false" idDataType="org.eclipse.uml2.types.String" idRequired="true" idOrdered="false"
	 * @generated
	 */
	IElementType getElementType(String id);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" elementTypeDataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" elementTypeRequired="true" elementTypeOrdered="false"
	 * @generated
	 */
	boolean isRelationshipType(IElementType elementType);

	/**
	 * Returns the value of the '<em><b>Owned Filter</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Filter</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owned Filter</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_OwnedFilter()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Filter> getOwnedFilters();

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.filters.Filter}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' for the new {@link org.eclipse.papyrus.infra.filters.Filter}, or <code>null</code>.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to create.
	 * @return The new {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter createOwnedFilter(String name, EClass eClass);

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter getOwnedFilter(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @param createOnDemand
	 *            Whether to create a {@link org.eclipse.papyrus.infra.filters.Filter} on demand if not found.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter getOwnedFilter(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand);

	/**
	 * Returns the value of the '<em><b>Popup Assistant</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwningProvider <em>Owning Provider</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getAssistants() <em>Assistant</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Popup Assistant</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Popup Assistant</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_PopupAssistant()
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwningProvider
	 * @model opposite="owningProvider" containment="true" ordered="false"
	 * @generated
	 */
	EList<PopupAssistant> getPopupAssistants();

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant} and appends it to the '<em><b>Popup Assistant</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return The new {@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant}.
	 * @see #getPopupAssistants()
	 * @generated
	 */
	PopupAssistant createPopupAssistant();

	/**
	 * Returns the value of the '<em><b>Connection Assistant</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider <em>Owning Provider</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getAssistants() <em>Assistant</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Assistant</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Connection Assistant</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ConnectionAssistant()
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider
	 * @model opposite="owningProvider" containment="true" ordered="false"
	 * @generated
	 */
	EList<ConnectionAssistant> getConnectionAssistants();

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant} and appends it to the '<em><b>Connection Assistant</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return The new {@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant}.
	 * @see #getConnectionAssistants()
	 * @generated
	 */
	ConnectionAssistant createConnectionAssistant();

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.gmf.runtime.emf.type.core.IElementType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Element Type</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ElementType()
	 * @model unique="false" dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<IElementType> getElementTypes();

	/**
	 * Returns the value of the '<em><b>Element Type ID</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The identifiers of element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Element Type ID</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ElementTypeID()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	EList<String> getElementTypeIDs();

	/**
	 * Returns the value of the '<em><b>Client Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Client Context</em>' attribute.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ClientContext()
	 * @model unique="false" dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ClientContext" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	IClientContext getClientContext();

	/**
	 * Returns the value of the '<em><b>Client Context ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The identifiers of element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Client Context ID</em>' attribute.
	 * @see #setClientContextID(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ClientContextID()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getClientContextID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContextID <em>Client Context ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Client Context ID</em>' attribute.
	 * @see #getClientContextID()
	 * @generated
	 */
	void setClientContextID(String value);

	/**
	 * Returns the value of the '<em><b>Excluded Element Type</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.gmf.runtime.emf.type.core.IElementType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Element types that the provider will never return as popup or connection assistants.
	 * This implies that inferred element types specializing any of these excluded types also will not be provided as assistants.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Excluded Element Type</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ExcludedElementType()
	 * @model unique="false" dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<IElementType> getExcludedElementTypes();

	/**
	 * Returns the value of the '<em><b>Excluded Element Type ID</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Identifiers of element types that the provider will never return as popup or connection assistants.
	 * This implies that inferred element types specializing any of these excluded types also will not be provided as assistants.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Excluded Element Type ID</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_ExcludedElementTypeID()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	EList<String> getExcludedElementTypeIDs();

	/**
	 * Returns the value of the '<em><b>Relationship Type</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.gmf.runtime.emf.type.core.IElementType}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes() <em>Element Type</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Relationship Type</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_RelationshipType()
	 * @model unique="false" dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<IElementType> getRelationshipTypes();

	/**
	 * Returns the value of the '<em><b>Relationship Type ID</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypeIDs() <em>Element Type ID</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The identifiers of element types supported by the provider.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Relationship Type ID</em>' attribute list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getModelingAssistantProvider_RelationshipTypeID()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	EList<String> getRelationshipTypeIDs();

} // ModelingAssistantProvider
