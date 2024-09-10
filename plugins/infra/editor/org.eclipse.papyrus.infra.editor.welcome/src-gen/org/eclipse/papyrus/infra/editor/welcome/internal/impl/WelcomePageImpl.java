/**
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *
 */
package org.eclipse.papyrus.infra.editor.welcome.internal.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;

import org.eclipse.papyrus.infra.editor.welcome.internal.operations.WelcomePageOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl#getSections <em>Section</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl#getVisibleSections <em>Visible Section</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl#getSashColumns <em>Sash Column</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WelcomePageImpl extends MinimalEObjectImpl.Container implements WelcomePage {
	/**
	 * The cached value of the '{@link #getSections() <em>Section</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSections()
	 * @generated
	 * @ordered
	 */
	protected EList<WelcomeSection> sections;

	/**
	 * The cached value of the '{@link #getSashColumns() <em>Sash Column</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSashColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<SashColumn> sashColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomePageImpl() {
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
		return WelcomePackage.Literals.WELCOME_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<WelcomeSection> getSections() {
		if (sections == null) {
			sections = new EObjectContainmentWithInverseEList<>(WelcomeSection.class, this, WelcomePackage.WELCOME_PAGE__SECTION, WelcomePackage.WELCOME_SECTION__PAGE);
		}
		return sections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomeSection createSection() {
		WelcomeSection newSection = (WelcomeSection) create(WelcomePackage.Literals.WELCOME_SECTION);
		getSections().add(newSection);
		return newSection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<WelcomeSection> getVisibleSections() {
		return WelcomePageOperations.getVisibleSections(this);
	}

	/**
	 * The array of superset feature identifiers for the '{@link #getVisibleSections() <em>Visible Section</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getVisibleSections()
	 * @generated
	 * @ordered
	 */
	protected static final int[] VISIBLE_SECTION_ESUPERSETS = new int[] { WelcomePackage.WELCOME_PAGE__SECTION };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomeSection createVisibleSection() {
		WelcomeSection newVisibleSection = (WelcomeSection) create(WelcomePackage.Literals.WELCOME_SECTION);
		getVisibleSections().add(newVisibleSection);
		return newVisibleSection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<SashColumn> getSashColumns() {
		if (sashColumns == null) {
			sashColumns = new EObjectContainmentWithInverseEList<>(SashColumn.class, this, WelcomePackage.WELCOME_PAGE__SASH_COLUMN, WelcomePackage.SASH_COLUMN__PAGE);
		}
		return sashColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashColumn createSashColumn() {
		SashColumn newSashColumn = (SashColumn) create(WelcomePackage.Literals.SASH_COLUMN);
		getSashColumns().add(newSashColumn);
		return newSashColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomeSection getSection(String identifier) {
		return WelcomePageOperations.getSection(this, identifier);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashColumn getSashColumn(int index) {
		return WelcomePageOperations.getSashColumn(this, index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashRow getSashRow(int column, int row) {
		return WelcomePageOperations.getSashRow(this, column, row);
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getSections()).basicAdd(otherEnd, msgs);
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getSashColumns()).basicAdd(otherEnd, msgs);
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			return ((InternalEList<?>) getSections()).basicRemove(otherEnd, msgs);
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			return ((InternalEList<?>) getSashColumns()).basicRemove(otherEnd, msgs);
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			return getSections();
		case WelcomePackage.WELCOME_PAGE__VISIBLE_SECTION:
			return getVisibleSections();
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			return getSashColumns();
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			getSections().clear();
			getSections().addAll((Collection<? extends WelcomeSection>) newValue);
			return;
		case WelcomePackage.WELCOME_PAGE__VISIBLE_SECTION:
			getVisibleSections().clear();
			getVisibleSections().addAll((Collection<? extends WelcomeSection>) newValue);
			return;
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			getSashColumns().clear();
			getSashColumns().addAll((Collection<? extends SashColumn>) newValue);
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			getSections().clear();
			return;
		case WelcomePackage.WELCOME_PAGE__VISIBLE_SECTION:
			getVisibleSections().clear();
			return;
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			getSashColumns().clear();
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
		case WelcomePackage.WELCOME_PAGE__SECTION:
			return sections != null && !sections.isEmpty();
		case WelcomePackage.WELCOME_PAGE__VISIBLE_SECTION:
			return !getVisibleSections().isEmpty();
		case WelcomePackage.WELCOME_PAGE__SASH_COLUMN:
			return sashColumns != null && !sashColumns.isEmpty();
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
		case WelcomePackage.WELCOME_PAGE___GET_SECTION__STRING:
			return getSection((String) arguments.get(0));
		case WelcomePackage.WELCOME_PAGE___GET_SASH_COLUMN__INT:
			return getSashColumn((Integer) arguments.get(0));
		case WelcomePackage.WELCOME_PAGE___GET_SASH_ROW__INT_INT:
			return getSashRow((Integer) arguments.get(0), (Integer) arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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

} // WelcomePageImpl
