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
 *   Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.contexts.operations.SectionOperations;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl#getTab <em>Tab</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl#getSectionFile <em>Section File</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl#getWidget <em>Widget</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl#getViews <em>Views</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SectionImpl extends AbstractSectionImpl implements Section {
	/**
	 * The default value of the '{@link #getSectionFile() <em>Section File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSectionFile()
	 * @generated
	 * @ordered
	 */
	protected static final String SECTION_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSectionFile() <em>Section File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSectionFile()
	 * @generated
	 * @ordered
	 */
	protected String sectionFile = SECTION_FILE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWidget() <em>Widget</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidget()
	 * @generated
	 * @ordered
	 */
	protected CompositeWidget widget;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextsPackage.Literals.SECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Tab getTab() {
		if (eContainerFeatureID() != ContextsPackage.SECTION__TAB) return null;
		return (Tab)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain basicSetTab(Tab newTab, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTab, ContextsPackage.SECTION__TAB, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTab(Tab newTab) {
		if (newTab != eInternalContainer() || (eContainerFeatureID() != ContextsPackage.SECTION__TAB && newTab != null)) {
			if (EcoreUtil.isAncestor(this, newTab))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTab != null)
				msgs = ((InternalEObject)newTab).eInverseAdd(this, ContextsPackage.TAB__ALL_SECTIONS, Tab.class, msgs);
			msgs = basicSetTab(newTab, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.SECTION__TAB, newTab, newTab));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSectionFile() {
		return sectionFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSectionFile(String newSectionFile) {
		String oldSectionFile = sectionFile;
		sectionFile = newSectionFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.SECTION__SECTION_FILE, oldSectionFile, sectionFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeWidget getWidget() {
		if (widget != null && widget.eIsProxy()) {
			InternalEObject oldWidget = (InternalEObject)widget;
			widget = (CompositeWidget)eResolveProxy(oldWidget);
			if (widget != oldWidget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.SECTION__WIDGET, oldWidget, widget));
			}
		}
		return widget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeWidget basicGetWidget() {
		return widget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidget(CompositeWidget newWidget) {
		CompositeWidget oldWidget = widget;
		widget = newWidget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.SECTION__WIDGET, oldWidget, widget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<View> getViews() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			EList<View> result = (EList<View>) cache.get(eResource(), this, ContextsPackage.Literals.SECTION__VIEWS);
			if (result == null) {
				cache.put(eResource(), this, ContextsPackage.Literals.SECTION__VIEWS, result = SectionOperations.getViews(this));
			}
			return result;
		}
		return SectionOperations.getViews(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Tab getOwner() {
		Tab owner = basicGetOwner();
		return owner != null && owner.eIsProxy() ? (Tab)eResolveProxy((InternalEObject)owner) : owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public Tab basicGetOwner() {
		return getTab();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setOwner(Tab newOwner) {
		this.setTab(newOwner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextsPackage.SECTION__TAB:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTab((Tab)otherEnd, msgs);
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
			case ContextsPackage.SECTION__TAB:
				return basicSetTab(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ContextsPackage.SECTION__TAB:
				return eInternalContainer().eInverseRemove(this, ContextsPackage.TAB__ALL_SECTIONS, Tab.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextsPackage.SECTION__SECTION_FILE:
				return getSectionFile();
			case ContextsPackage.SECTION__WIDGET:
				if (resolve) return getWidget();
				return basicGetWidget();
			case ContextsPackage.SECTION__VIEWS:
				return getViews();
			case ContextsPackage.SECTION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ContextsPackage.SECTION__SECTION_FILE:
				setSectionFile((String)newValue);
				return;
			case ContextsPackage.SECTION__WIDGET:
				setWidget((CompositeWidget)newValue);
				return;
			case ContextsPackage.SECTION__OWNER:
				setOwner((Tab)newValue);
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
			case ContextsPackage.SECTION__SECTION_FILE:
				setSectionFile(SECTION_FILE_EDEFAULT);
				return;
			case ContextsPackage.SECTION__WIDGET:
				setWidget((CompositeWidget)null);
				return;
			case ContextsPackage.SECTION__OWNER:
				setOwner((Tab)null);
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
			case ContextsPackage.SECTION__TAB:
				return getTab() != null;
			case ContextsPackage.SECTION__SECTION_FILE:
				return SECTION_FILE_EDEFAULT == null ? sectionFile != null : !SECTION_FILE_EDEFAULT.equals(sectionFile);
			case ContextsPackage.SECTION__WIDGET:
				return widget != null;
			case ContextsPackage.SECTION__VIEWS:
				return !getViews().isEmpty();
			case ContextsPackage.SECTION__OWNER:
				return basicGetOwner() != null;
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
		result.append(" (sectionFile: "); //$NON-NLS-1$
		result.append(sectionFile);
		result.append(')');
		return result.toString();
	}

} // SectionImpl
