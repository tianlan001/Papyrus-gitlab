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
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Assistant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getProvider <em>Provider</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getSourceFilter <em>Source Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getTargetFilter <em>Target Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getOwnedSourceFilter <em>Owned Source Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getOwnedTargetFilter <em>Owned Target Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl#getOwningProvider <em>Owning Provider</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectionAssistantImpl extends AssistantImpl implements ConnectionAssistant {
	/**
	 * The cached value of the '{@link #getSourceFilter() <em>Source Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSourceFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter sourceFilter;

	/**
	 * The cached value of the '{@link #getTargetFilter() <em>Target Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTargetFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter targetFilter;

	/**
	 * The cached value of the '{@link #getOwnedSourceFilter() <em>Owned Source Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedSourceFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter ownedSourceFilter;

	/**
	 * The cached value of the '{@link #getOwnedTargetFilter() <em>Owned Target Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedTargetFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter ownedTargetFilter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ConnectionAssistantImpl() {
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
		return AssistantPackage.Literals.CONNECTION_ASSISTANT;
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
	public Filter getSourceFilter() {
		if (sourceFilter != null && sourceFilter.eIsProxy()) {
			InternalEObject oldSourceFilter = (InternalEObject) sourceFilter;
			sourceFilter = (Filter) eResolveProxy(oldSourceFilter);
			if (sourceFilter != oldSourceFilter) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER, oldSourceFilter, sourceFilter));
				}
			}
		}
		return sourceFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Filter basicGetSourceFilter() {
		return sourceFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSourceFilter(Filter newSourceFilter) {
		Filter oldSourceFilter = sourceFilter;
		sourceFilter = newSourceFilter;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER, oldSourceFilter, sourceFilter));
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (ownedSourceFilter != null && ownedSourceFilter != newSourceFilter) {
				setOwnedSourceFilter(null);
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
	public Filter getTargetFilter() {
		if (targetFilter != null && targetFilter.eIsProxy()) {
			InternalEObject oldTargetFilter = (InternalEObject) targetFilter;
			targetFilter = (Filter) eResolveProxy(oldTargetFilter);
			if (targetFilter != oldTargetFilter) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER, oldTargetFilter, targetFilter));
				}
			}
		}
		return targetFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Filter basicGetTargetFilter() {
		return targetFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setTargetFilter(Filter newTargetFilter) {
		Filter oldTargetFilter = targetFilter;
		targetFilter = newTargetFilter;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER, oldTargetFilter, targetFilter));
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (ownedTargetFilter != null && ownedTargetFilter != newTargetFilter) {
				setOwnedTargetFilter(null);
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
	public ModelingAssistantProvider getOwningProvider() {
		if (eContainerFeatureID() != AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER) {
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
		msgs = eBasicSetContainer((InternalEObject) newOwningProvider, AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER, msgs);
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
		if (newOwningProvider != eInternalContainer() || (eContainerFeatureID() != AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER && newOwningProvider != null)) {
			if (EcoreUtil.isAncestor(this, newOwningProvider)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newOwningProvider != null) {
				msgs = ((InternalEObject) newOwningProvider).eInverseAdd(this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT, ModelingAssistantProvider.class, msgs);
			}
			msgs = basicSetOwningProvider(newOwningProvider, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER, newOwningProvider, newOwningProvider));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedSourceFilter() {
		return ownedSourceFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwnedSourceFilter(Filter newOwnedSourceFilter, NotificationChain msgs) {
		Filter oldOwnedSourceFilter = ownedSourceFilter;
		ownedSourceFilter = newOwnedSourceFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER, oldOwnedSourceFilter, newOwnedSourceFilter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newOwnedSourceFilter != null) {
				if (newOwnedSourceFilter != sourceFilter) {
					setSourceFilter(newOwnedSourceFilter);
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
	public void setOwnedSourceFilter(Filter newOwnedSourceFilter) {
		if (newOwnedSourceFilter != ownedSourceFilter) {
			NotificationChain msgs = null;
			if (ownedSourceFilter != null) {
				msgs = ((InternalEObject) ownedSourceFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER, null, msgs);
			}
			if (newOwnedSourceFilter != null) {
				msgs = ((InternalEObject) newOwnedSourceFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER, null, msgs);
			}
			msgs = basicSetOwnedSourceFilter(newOwnedSourceFilter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER, newOwnedSourceFilter, newOwnedSourceFilter));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter createOwnedSourceFilter(String name, EClass eClass) {
		Filter newOwnedSourceFilter = (Filter) create(eClass);
		setOwnedSourceFilter(newOwnedSourceFilter);
		if (name != null) {
			newOwnedSourceFilter.setName(name);
		}
		return newOwnedSourceFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedTargetFilter() {
		return ownedTargetFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwnedTargetFilter(Filter newOwnedTargetFilter, NotificationChain msgs) {
		Filter oldOwnedTargetFilter = ownedTargetFilter;
		ownedTargetFilter = newOwnedTargetFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER, oldOwnedTargetFilter, newOwnedTargetFilter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newOwnedTargetFilter != null) {
				if (newOwnedTargetFilter != targetFilter) {
					setTargetFilter(newOwnedTargetFilter);
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
	public void setOwnedTargetFilter(Filter newOwnedTargetFilter) {
		if (newOwnedTargetFilter != ownedTargetFilter) {
			NotificationChain msgs = null;
			if (ownedTargetFilter != null) {
				msgs = ((InternalEObject) ownedTargetFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER, null, msgs);
			}
			if (newOwnedTargetFilter != null) {
				msgs = ((InternalEObject) newOwnedTargetFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER, null, msgs);
			}
			msgs = basicSetOwnedTargetFilter(newOwnedTargetFilter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER, newOwnedTargetFilter, newOwnedTargetFilter));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter createOwnedTargetFilter(String name, EClass eClass) {
		Filter newOwnedTargetFilter = (Filter) create(eClass);
		setOwnedTargetFilter(newOwnedTargetFilter);
		if (name != null) {
			newOwnedTargetFilter.setName(name);
		}
		return newOwnedTargetFilter;
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
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
			return basicSetOwnedSourceFilter(null, msgs);
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
			return basicSetOwnedTargetFilter(null, msgs);
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
			return eInternalContainer().eInverseRemove(this, AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT, ModelingAssistantProvider.class, msgs);
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
		case AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER:
			if (resolve) {
				return getSourceFilter();
			}
			return basicGetSourceFilter();
		case AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER:
			if (resolve) {
				return getTargetFilter();
			}
			return basicGetTargetFilter();
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
			return getOwnedSourceFilter();
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
			return getOwnedTargetFilter();
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
		case AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER:
			setSourceFilter((Filter) newValue);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER:
			setTargetFilter((Filter) newValue);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
			setOwnedSourceFilter((Filter) newValue);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
			setOwnedTargetFilter((Filter) newValue);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
		case AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER:
			setSourceFilter((Filter) null);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER:
			setTargetFilter((Filter) null);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
			setOwnedSourceFilter((Filter) null);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
			setOwnedTargetFilter((Filter) null);
			return;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
		case AssistantPackage.CONNECTION_ASSISTANT__PROVIDER:
			return isSetProvider();
		case AssistantPackage.CONNECTION_ASSISTANT__SOURCE_FILTER:
			return sourceFilter != null;
		case AssistantPackage.CONNECTION_ASSISTANT__TARGET_FILTER:
			return targetFilter != null;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
			return ownedSourceFilter != null;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
			return ownedTargetFilter != null;
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
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
				|| eIsSet(AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER);
	}

} // ConnectionAssistantImpl
