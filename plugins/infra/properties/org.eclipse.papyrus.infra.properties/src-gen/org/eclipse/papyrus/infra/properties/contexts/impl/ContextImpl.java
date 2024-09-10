/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - add prototype reference to Context (CDO)
 *   Christian W. Damus - bugs 482927, 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getTabs <em>Tabs</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getViews <em>Views</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getDataContexts <em>Data Contexts</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getPrototype <em>Prototype</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl#getUserLabel <em>User Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextImpl extends EModelElementImpl implements Context {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Context> dependencies;

	/**
	 * The cached value of the '{@link #getTabs() <em>Tabs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTabs()
	 * @generated
	 * @ordered
	 */
	protected EList<Tab> tabs;

	/**
	 * The cached value of the '{@link #getViews() <em>Views</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViews()
	 * @generated
	 * @ordered
	 */
	protected EList<View> views;

	/**
	 * The cached value of the '{@link #getDataContexts() <em>Data Contexts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<DataContextRoot> dataContexts;

	/**
	 * The cached value of the '{@link #getPrototype() <em>Prototype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrototype()
	 * @generated
	 * @ordered
	 */
	protected Context prototype;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUserLabel() <em>User Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_LABEL_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextsPackage.Literals.CONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTEXT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Context> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectResolvingEList<Context>(Context.class, this, ContextsPackage.CONTEXT__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Tab> getTabs() {
		if (tabs == null) {
			tabs = new EObjectContainmentEList<Tab>(Tab.class, this, ContextsPackage.CONTEXT__TABS);
		}
		return tabs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<View> getViews() {
		if (views == null) {
			views = new EObjectContainmentWithInverseEList<View>(View.class, this, ContextsPackage.CONTEXT__VIEWS, ContextsPackage.VIEW__CONTEXT);
		}
		return views;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DataContextRoot> getDataContexts() {
		if (dataContexts == null) {
			dataContexts = new EObjectContainmentEList<DataContextRoot>(DataContextRoot.class, this, ContextsPackage.CONTEXT__DATA_CONTEXTS);
		}
		return dataContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Context getPrototype() {
		if (prototype != null && prototype.eIsProxy()) {
			InternalEObject oldPrototype = (InternalEObject)prototype;
			prototype = (Context)eResolveProxy(oldPrototype);
			if (prototype != oldPrototype) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.CONTEXT__PROTOTYPE, oldPrototype, prototype));
			}
		}
		return prototype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Context basicGetPrototype() {
		return prototype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrototype(Context newPrototype) {
		Context oldPrototype = prototype;
		prototype = newPrototype;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTEXT__PROTOTYPE, oldPrototype, prototype));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTEXT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getUserLabel() {
		String result = getLabel();
		if ((result == null) || result.isEmpty()) {
			result = getName();
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__VIEWS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getViews()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__TABS:
				return ((InternalEList<?>)getTabs()).basicRemove(otherEnd, msgs);
			case ContextsPackage.CONTEXT__VIEWS:
				return ((InternalEList<?>)getViews()).basicRemove(otherEnd, msgs);
			case ContextsPackage.CONTEXT__DATA_CONTEXTS:
				return ((InternalEList<?>)getDataContexts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__NAME:
				return getName();
			case ContextsPackage.CONTEXT__DEPENDENCIES:
				return getDependencies();
			case ContextsPackage.CONTEXT__TABS:
				return getTabs();
			case ContextsPackage.CONTEXT__VIEWS:
				return getViews();
			case ContextsPackage.CONTEXT__DATA_CONTEXTS:
				return getDataContexts();
			case ContextsPackage.CONTEXT__PROTOTYPE:
				if (resolve) return getPrototype();
				return basicGetPrototype();
			case ContextsPackage.CONTEXT__LABEL:
				return getLabel();
			case ContextsPackage.CONTEXT__USER_LABEL:
				return getUserLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__NAME:
				setName((String)newValue);
				return;
			case ContextsPackage.CONTEXT__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Context>)newValue);
				return;
			case ContextsPackage.CONTEXT__TABS:
				getTabs().clear();
				getTabs().addAll((Collection<? extends Tab>)newValue);
				return;
			case ContextsPackage.CONTEXT__VIEWS:
				getViews().clear();
				getViews().addAll((Collection<? extends View>)newValue);
				return;
			case ContextsPackage.CONTEXT__DATA_CONTEXTS:
				getDataContexts().clear();
				getDataContexts().addAll((Collection<? extends DataContextRoot>)newValue);
				return;
			case ContextsPackage.CONTEXT__PROTOTYPE:
				setPrototype((Context)newValue);
				return;
			case ContextsPackage.CONTEXT__LABEL:
				setLabel((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ContextsPackage.CONTEXT__DEPENDENCIES:
				getDependencies().clear();
				return;
			case ContextsPackage.CONTEXT__TABS:
				getTabs().clear();
				return;
			case ContextsPackage.CONTEXT__VIEWS:
				getViews().clear();
				return;
			case ContextsPackage.CONTEXT__DATA_CONTEXTS:
				getDataContexts().clear();
				return;
			case ContextsPackage.CONTEXT__PROTOTYPE:
				setPrototype((Context)null);
				return;
			case ContextsPackage.CONTEXT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ContextsPackage.CONTEXT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ContextsPackage.CONTEXT__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case ContextsPackage.CONTEXT__TABS:
				return tabs != null && !tabs.isEmpty();
			case ContextsPackage.CONTEXT__VIEWS:
				return views != null && !views.isEmpty();
			case ContextsPackage.CONTEXT__DATA_CONTEXTS:
				return dataContexts != null && !dataContexts.isEmpty();
			case ContextsPackage.CONTEXT__PROTOTYPE:
				return prototype != null;
			case ContextsPackage.CONTEXT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ContextsPackage.CONTEXT__USER_LABEL:
				return USER_LABEL_EDEFAULT == null ? getUserLabel() != null : !USER_LABEL_EDEFAULT.equals(getUserLabel());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", label: "); //$NON-NLS-1$
		result.append(label);
		result.append(')');
		return result.toString();
	}

	/**
	 * Retrieves the cache adapter for this '<em><b>Context</b></em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The cache adapter for this '<em><b>Context</b></em>'.
	 * @generated
	 */
	protected CacheAdapter getCacheAdapter() {
		return CacheAdapter.getInstance();
	}

} // ContextImpl
