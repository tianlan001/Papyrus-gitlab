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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Popup Assistant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl#getProvider <em>Provider</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl#getFilter <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl#getOwnedFilter <em>Owned Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl#getOwningProvider <em>Owning Provider</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PopupAssistantImpl extends AssistantImpl implements PopupAssistant {
	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter filter;

	/**
	 * The cached value of the '{@link #getOwnedFilter() <em>Owned Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter ownedFilter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected PopupAssistantImpl() {
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
		return AssistantPackage.Literals.POPUP_ASSISTANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelingAssistantProvider getProvider() {
		ModelingAssistantProvider provider = basicGetProvider();
		return provider != null && provider.eIsProxy() ? (ModelingAssistantProvider) eResolveProxy((InternalEObject) provider) : provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelingAssistantProvider basicGetProvider() {
		ModelingAssistantProvider owningProvider = getOwningProvider();
		if (owningProvider != null) {
			return owningProvider;
		}
		return super.basicGetProvider();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getFilter() {
		if (filter != null && filter.eIsProxy()) {
			InternalEObject oldFilter = (InternalEObject) filter;
			filter = (Filter) eResolveProxy(oldFilter);
			if (filter != oldFilter) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AssistantPackage.POPUP_ASSISTANT__FILTER, oldFilter, filter));
				}
			}
		}
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Filter basicGetFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFilter(Filter newFilter) {
		Filter oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.POPUP_ASSISTANT__FILTER, oldFilter, filter));
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (ownedFilter != null && ownedFilter != newFilter) {
				setOwnedFilter(null);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter() {
		return ownedFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwnedFilter(Filter newOwnedFilter, NotificationChain msgs) {
		Filter oldOwnedFilter = ownedFilter;
		ownedFilter = newOwnedFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER, oldOwnedFilter, newOwnedFilter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newOwnedFilter != null) {
				if (newOwnedFilter != filter) {
					setFilter(newOwnedFilter);
				}
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOwnedFilter(Filter newOwnedFilter) {
		if (newOwnedFilter != ownedFilter) {
			NotificationChain msgs = null;
			if (ownedFilter != null) {
				msgs = ((InternalEObject) ownedFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER, null, msgs);
			}
			if (newOwnedFilter != null) {
				msgs = ((InternalEObject) newOwnedFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER, null, msgs);
			}
			msgs = basicSetOwnedFilter(newOwnedFilter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER, newOwnedFilter, newOwnedFilter));
		}
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
		setOwnedFilter(newOwnedFilter);
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
	public ModelingAssistantProvider getOwningProvider() {
		if (eContainerFeatureID() != AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER) {
			return null;
		}
		return (ModelingAssistantProvider) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwningProvider(ModelingAssistantProvider newOwningProvider, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOwningProvider, AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOwningProvider(ModelingAssistantProvider newOwningProvider) {
		if (newOwningProvider != eInternalContainer() || (eContainerFeatureID() != AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER && newOwningProvider != null)) {
			if (EcoreUtil.isAncestor(this, newOwningProvider)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newOwningProvider != null) {
				msgs = ((InternalEObject) newOwningProvider).eInverseAdd(this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT, ModelingAssistantProvider.class, msgs);
			}
			msgs = basicSetOwningProvider(newOwningProvider, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER, newOwningProvider, newOwningProvider));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetOwningProvider((ModelingAssistantProvider) otherEnd, msgs);
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
		case AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER:
			return basicSetOwnedFilter(null, msgs);
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			return basicSetOwningProvider(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			return eInternalContainer().eInverseRemove(this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT, ModelingAssistantProvider.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
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
		case AssistantPackage.POPUP_ASSISTANT__FILTER:
			if (resolve) {
				return getFilter();
			}
			return basicGetFilter();
		case AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER:
			return getOwnedFilter();
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			return getOwningProvider();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AssistantPackage.POPUP_ASSISTANT__FILTER:
			setFilter((Filter) newValue);
			return;
		case AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER:
			setOwnedFilter((Filter) newValue);
			return;
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			setOwningProvider((ModelingAssistantProvider) newValue);
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
		case AssistantPackage.POPUP_ASSISTANT__FILTER:
			setFilter((Filter) null);
			return;
		case AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER:
			setOwnedFilter((Filter) null);
			return;
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			setOwningProvider((ModelingAssistantProvider) null);
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
		case AssistantPackage.POPUP_ASSISTANT__PROVIDER:
			return isSetProvider();
		case AssistantPackage.POPUP_ASSISTANT__FILTER:
			return filter != null;
		case AssistantPackage.POPUP_ASSISTANT__OWNED_FILTER:
			return ownedFilter != null;
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			return getOwningProvider() != null;
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
	public boolean isSetProvider() {
		return super.isSetProvider()
				|| eIsSet(AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER);
	}

} // PopupAssistantImpl
