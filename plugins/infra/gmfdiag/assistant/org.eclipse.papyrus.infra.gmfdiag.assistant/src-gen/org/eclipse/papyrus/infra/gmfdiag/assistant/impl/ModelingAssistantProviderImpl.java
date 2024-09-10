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
package org.eclipse.papyrus.infra.gmfdiag.assistant.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.operations.ModelingAssistantProviderOperations;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.common.util.SubsetSupersetEDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Modeling Assistant Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getListeners <em>Listener</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getAssistants <em>Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getElementTypeIDs <em>Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getOwnedFilters <em>Owned Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getPopupAssistants <em>Popup Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getConnectionAssistants <em>Connection Assistant</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getElementTypes <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getClientContext <em>Client Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getClientContextID <em>Client Context ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getExcludedElementTypes <em>Excluded Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getExcludedElementTypeIDs <em>Excluded Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getRelationshipTypes <em>Relationship Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl#getRelationshipTypeIDs <em>Relationship Type ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelingAssistantProviderImpl extends MinimalEObjectImpl.Container implements ModelingAssistantProvider {
	/**
	 * The cached value of the '{@link #getListeners() <em>Listener</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getListeners()
	 * @generated
	 * @ordered
	 */
	protected EList<IProviderChangeListener> listeners;

	/**
	 * The cached value of the '{@link #getElementTypeIDs() <em>Element Type ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> elementTypeIDs;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOwnedFilters() <em>Owned Filter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedFilters()
	 * @generated
	 * @ordered
	 */
	protected EList<Filter> ownedFilters;

	/**
	 * The cached value of the '{@link #getPopupAssistants() <em>Popup Assistant</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getPopupAssistants()
	 * @generated
	 * @ordered
	 */
	protected EList<PopupAssistant> popupAssistants;

	/**
	 * The cached value of the '{@link #getConnectionAssistants() <em>Connection Assistant</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getConnectionAssistants()
	 * @generated
	 * @ordered
	 */
	protected EList<ConnectionAssistant> connectionAssistants;

	/**
	 * The default value of the '{@link #getClientContext() <em>Client Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClientContext()
	 * @generated
	 * @ordered
	 */
	protected static final IClientContext CLIENT_CONTEXT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getClientContextID() <em>Client Context ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClientContextID()
	 * @generated
	 * @ordered
	 */
	protected static final String CLIENT_CONTEXT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClientContextID() <em>Client Context ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClientContextID()
	 * @generated
	 * @ordered
	 */
	protected String clientContextID = CLIENT_CONTEXT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExcludedElementTypeIDs() <em>Excluded Element Type ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getExcludedElementTypeIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> excludedElementTypeIDs;

	/**
	 * The cached value of the '{@link #getRelationshipTypeIDs() <em>Relationship Type ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRelationshipTypeIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> relationshipTypeIDs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ModelingAssistantProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EList<IProviderChangeListener> getListeners() {
		if (listeners == null) {
			listeners = new EDataTypeUniqueEList<>(IProviderChangeListener.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER);
		}
		return listeners;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Assistant> getAssistants() {
		return new DerivedUnionEObjectEList<>(Assistant.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__ASSISTANT, ASSISTANT_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getAssistants() <em>Assistant</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAssistants()
	 * @generated
	 * @ordered
	 */
	protected static final int[] ASSISTANT_ESUBSETS = new int[] { AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT, AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		newName = newName == null ? NAME_EDEFAULT : newName;
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Filter> getOwnedFilters() {
		if (ownedFilters == null) {
			ownedFilters = new EObjectContainmentEList<>(Filter.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER);
		}
		return ownedFilters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter createOwnedFilter(String name, EClass eClass) {
		Filter newOwnedFilter = (Filter) create(eClass);
		getOwnedFilters().add(newOwnedFilter);
		if (name != null) {
			newOwnedFilter.setName(name);
		}
		return newOwnedFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter(String name) {
		return getOwnedFilter(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		ownedFilterLoop: for (Filter ownedFilter : getOwnedFilters()) {
			if (eClass != null && !eClass.isInstance(ownedFilter)) {
				continue ownedFilterLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(ownedFilter.getName()) : name.equals(ownedFilter.getName()))) {
				continue ownedFilterLoop;
			}
			return ownedFilter;
		}
		return createOnDemand && eClass != null ? createOwnedFilter(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<PopupAssistant> getPopupAssistants() {
		if (popupAssistants == null) {
			popupAssistants = new EObjectContainmentWithInverseEList<>(PopupAssistant.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT, AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER);
		}
		return popupAssistants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PopupAssistant createPopupAssistant() {
		PopupAssistant newPopupAssistant = (PopupAssistant) create(AssistantPackage.Literals.POPUP_ASSISTANT);
		getPopupAssistants().add(newPopupAssistant);
		return newPopupAssistant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ConnectionAssistant> getConnectionAssistants() {
		if (connectionAssistants == null) {
			connectionAssistants = new EObjectContainmentWithInverseEList<>(ConnectionAssistant.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT, AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER);
		}
		return connectionAssistants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConnectionAssistant createConnectionAssistant() {
		ConnectionAssistant newConnectionAssistant = (ConnectionAssistant) create(AssistantPackage.Literals.CONNECTION_ASSISTANT);
		getConnectionAssistants().add(newConnectionAssistant);
		return newConnectionAssistant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getElementTypes() {
		return ModelingAssistantProviderOperations.getElementTypes(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getElementTypeIDs() {
		if (elementTypeIDs == null) {
			elementTypeIDs = new SubsetSupersetEDataTypeUniqueEList<>(String.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID, null, ELEMENT_TYPE_ID_ESUBSETS);
		}
		return elementTypeIDs;
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getElementTypeIDs() <em>Element Type ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeIDs()
	 * @generated
	 * @ordered
	 */
	protected static final int[] ELEMENT_TYPE_ID_ESUBSETS = new int[] { AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IClientContext getClientContext() {
		return ModelingAssistantProviderOperations.getClientContext(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getClientContextID() {
		return clientContextID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setClientContextID(String newClientContextID) {
		newClientContextID = newClientContextID == null ? CLIENT_CONTEXT_ID_EDEFAULT : newClientContextID;
		String oldClientContextID = clientContextID;
		clientContextID = newClientContextID;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID, oldClientContextID, clientContextID));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getExcludedElementTypes() {
		return ModelingAssistantProviderOperations.getExcludedElementTypes(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getExcludedElementTypeIDs() {
		if (excludedElementTypeIDs == null) {
			excludedElementTypeIDs = new EDataTypeUniqueEList<>(String.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID);
		}
		return excludedElementTypeIDs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelationshipTypes() {
		return ModelingAssistantProviderOperations.getRelationshipTypes(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getRelationshipTypeIDs() {
		if (relationshipTypeIDs == null) {
			relationshipTypeIDs = new SubsetSupersetEDataTypeUniqueEList<>(String.class, this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID, RELATIONSHIP_TYPE_ID_ESUPERSETS, null);
		}
		return relationshipTypeIDs;
	}

	/**
	 * The array of superset feature identifiers for the '{@link #getRelationshipTypeIDs() <em>Relationship Type ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRelationshipTypeIDs()
	 * @generated
	 * @ordered
	 */
	protected static final int[] RELATIONSHIP_TYPE_ID_ESUPERSETS = new int[] { AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		return ModelingAssistantProviderOperations.provides(this, operation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void addProviderChangeListener(IProviderChangeListener listener) {
		ModelingAssistantProviderOperations.addProviderChangeListener(this, listener);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void removeProviderChangeListener(IProviderChangeListener listener) {
		ModelingAssistantProviderOperations.removeProviderChangeListener(this, listener);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getTypes(String hint, IAdaptable data) {
		return ModelingAssistantProviderOperations.getTypes(this, hint, data);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnSource(IAdaptable source) {
		return ModelingAssistantProviderOperations.getRelTypesOnSource(this, source);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnTarget(IAdaptable target) {
		return ModelingAssistantProviderOperations.getRelTypesOnTarget(this, target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		return ModelingAssistantProviderOperations.getRelTypesOnSourceAndTarget(this, source, target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesForSREOnTarget(IAdaptable target) {
		return ModelingAssistantProviderOperations.getRelTypesForSREOnTarget(this, target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getRelTypesForSREOnSource(IAdaptable source) {
		return ModelingAssistantProviderOperations.getRelTypesForSREOnSource(this, source);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getTypesForSource(IAdaptable target, IElementType relationshipType) {
		return ModelingAssistantProviderOperations.getTypesForSource(this, target, relationshipType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		return ModelingAssistantProviderOperations.getTypesForTarget(this, source, relationshipType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject selectExistingElementForSource(IAdaptable target, IElementType relationshipType) {
		return ModelingAssistantProviderOperations.selectExistingElementForSource(this, target, relationshipType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject selectExistingElementForTarget(IAdaptable source, IElementType relationshipType) {
		return ModelingAssistantProviderOperations.selectExistingElementForTarget(this, source, relationshipType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<IElementType> getTypesForPopupBar(IAdaptable host) {
		return ModelingAssistantProviderOperations.getTypesForPopupBar(this, host);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IElementType getElementType(String id) {
		return ModelingAssistantProviderOperations.getElementType(this, id);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isRelationshipType(IElementType elementType) {
		return ModelingAssistantProviderOperations.isRelationshipType(this, elementType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getPopupAssistants()).basicAdd(otherEnd, msgs);
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getConnectionAssistants()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
			return ((InternalEList<?>) getOwnedFilters()).basicRemove(otherEnd, msgs);
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			return ((InternalEList<?>) getPopupAssistants()).basicRemove(otherEnd, msgs);
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			return ((InternalEList<?>) getConnectionAssistants()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER:
			return getListeners();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ASSISTANT:
			return getAssistants();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID:
			return getElementTypeIDs();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME:
			return getName();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
			return getOwnedFilters();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			return getPopupAssistants();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			return getConnectionAssistants();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE:
			return getElementTypes();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT:
			return getClientContext();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID:
			return getClientContextID();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE:
			return getExcludedElementTypes();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID:
			return getExcludedElementTypeIDs();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE:
			return getRelationshipTypes();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID:
			return getRelationshipTypeIDs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER:
			getListeners().clear();
			getListeners().addAll((Collection<? extends IProviderChangeListener>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID:
			getElementTypeIDs().clear();
			getElementTypeIDs().addAll((Collection<? extends String>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME:
			setName((String) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
			getOwnedFilters().clear();
			getOwnedFilters().addAll((Collection<? extends Filter>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			getPopupAssistants().clear();
			getPopupAssistants().addAll((Collection<? extends PopupAssistant>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			getConnectionAssistants().clear();
			getConnectionAssistants().addAll((Collection<? extends ConnectionAssistant>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID:
			setClientContextID((String) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID:
			getExcludedElementTypeIDs().clear();
			getExcludedElementTypeIDs().addAll((Collection<? extends String>) newValue);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID:
			getRelationshipTypeIDs().clear();
			getRelationshipTypeIDs().addAll((Collection<? extends String>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER:
			getListeners().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID:
			getElementTypeIDs().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
			getOwnedFilters().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			getPopupAssistants().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			getConnectionAssistants().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID:
			setClientContextID(CLIENT_CONTEXT_ID_EDEFAULT);
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID:
			getExcludedElementTypeIDs().clear();
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID:
			getRelationshipTypeIDs().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER:
			return listeners != null && !listeners.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ASSISTANT:
			return isSetAssistants();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID:
			return elementTypeIDs != null && !elementTypeIDs.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
			return ownedFilters != null && !ownedFilters.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			return popupAssistants != null && !popupAssistants.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			return connectionAssistants != null && !connectionAssistants.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE:
			return !getElementTypes().isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT:
			return CLIENT_CONTEXT_EDEFAULT == null ? getClientContext() != null : !CLIENT_CONTEXT_EDEFAULT.equals(getClientContext());
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID:
			return CLIENT_CONTEXT_ID_EDEFAULT == null ? clientContextID != null : !CLIENT_CONTEXT_ID_EDEFAULT.equals(clientContextID);
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE:
			return !getExcludedElementTypes().isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID:
			return excludedElementTypeIDs != null && !excludedElementTypeIDs.isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE:
			return !getRelationshipTypes().isEmpty();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID:
			return relationshipTypeIDs != null && !relationshipTypeIDs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___PROVIDES__IOPERATION:
			return provides((IOperation) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER:
			addProviderChangeListener((IProviderChangeListener) arguments.get(0));
			return null;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER:
			removeProviderChangeListener((IProviderChangeListener) arguments.get(0));
			return null;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE:
			return getTypes((String) arguments.get(0), (IAdaptable) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE:
			return getRelTypesOnSource((IAdaptable) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE:
			return getRelTypesOnTarget((IAdaptable) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE:
			return getRelTypesOnSourceAndTarget((IAdaptable) arguments.get(0), (IAdaptable) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE:
			return getRelTypesForSREOnTarget((IAdaptable) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE:
			return getRelTypesForSREOnSource((IAdaptable) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE:
			return getTypesForSource((IAdaptable) arguments.get(0), (IElementType) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE:
			return getTypesForTarget((IAdaptable) arguments.get(0), (IElementType) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE:
			return selectExistingElementForSource((IAdaptable) arguments.get(0), (IElementType) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE:
			return selectExistingElementForTarget((IAdaptable) arguments.get(0), (IElementType) arguments.get(1));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE:
			return getTypesForPopupBar((IAdaptable) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING:
			return getElementType((String) arguments.get(0));
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE:
			return isRelationshipType((IElementType) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (listener: "); //$NON-NLS-1$
		result.append(listeners);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", elementTypeID: "); //$NON-NLS-1$
		result.append(elementTypeIDs);
		result.append(", clientContextID: "); //$NON-NLS-1$
		result.append(clientContextID);
		result.append(", excludedElementTypeID: "); //$NON-NLS-1$
		result.append(excludedElementTypeIDs);
		result.append(", relationshipTypeID: "); //$NON-NLS-1$
		result.append(relationshipTypeIDs);
		result.append(')');
		return result.toString();
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param eClass
	 *            The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetAssistants() {
		return eIsSet(AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT)
				|| eIsSet(AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT);
	}

} // ModelingAssistantProviderImpl
