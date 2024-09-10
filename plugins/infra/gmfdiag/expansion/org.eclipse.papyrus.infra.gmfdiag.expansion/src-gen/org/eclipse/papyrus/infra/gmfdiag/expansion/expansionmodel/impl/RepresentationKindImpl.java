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
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Representation Kind</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl#getEditPartQualifiedName <em>Edit Part Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl#getViewFactory <em>View Factory</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepresentationKindImpl extends MinimalEObjectImpl.Container implements RepresentationKind {
	/**
	 * The default value of the '{@link #getEditPartQualifiedName() <em>Edit Part Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPartQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_PART_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditPartQualifiedName() <em>Edit Part Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPartQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String editPartQualifiedName = EDIT_PART_QUALIFIED_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getViewFactory() <em>View Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewFactory()
	 * @generated
	 * @ordered
	 */
	protected static final String VIEW_FACTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getViewFactory() <em>View Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewFactory()
	 * @generated
	 * @ordered
	 */
	protected String viewFactory = VIEW_FACTORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepresentationKindImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpansionModelPackage.Literals.REPRESENTATION_KIND;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditPartQualifiedName() {
		return editPartQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditPartQualifiedName(String newEditPartQualifiedName) {
		String oldEditPartQualifiedName = editPartQualifiedName;
		editPartQualifiedName = newEditPartQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpansionModelPackage.REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME, oldEditPartQualifiedName, editPartQualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpansionModelPackage.REPRESENTATION_KIND__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getViewFactory() {
		return viewFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViewFactory(String newViewFactory) {
		String oldViewFactory = viewFactory;
		viewFactory = newViewFactory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpansionModelPackage.REPRESENTATION_KIND__VIEW_FACTORY, oldViewFactory, viewFactory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpansionModelPackage.REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME:
				return getEditPartQualifiedName();
			case ExpansionModelPackage.REPRESENTATION_KIND__NAME:
				return getName();
			case ExpansionModelPackage.REPRESENTATION_KIND__VIEW_FACTORY:
				return getViewFactory();
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
			case ExpansionModelPackage.REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME:
				setEditPartQualifiedName((String)newValue);
				return;
			case ExpansionModelPackage.REPRESENTATION_KIND__NAME:
				setName((String)newValue);
				return;
			case ExpansionModelPackage.REPRESENTATION_KIND__VIEW_FACTORY:
				setViewFactory((String)newValue);
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
			case ExpansionModelPackage.REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME:
				setEditPartQualifiedName(EDIT_PART_QUALIFIED_NAME_EDEFAULT);
				return;
			case ExpansionModelPackage.REPRESENTATION_KIND__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ExpansionModelPackage.REPRESENTATION_KIND__VIEW_FACTORY:
				setViewFactory(VIEW_FACTORY_EDEFAULT);
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
			case ExpansionModelPackage.REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME:
				return EDIT_PART_QUALIFIED_NAME_EDEFAULT == null ? editPartQualifiedName != null : !EDIT_PART_QUALIFIED_NAME_EDEFAULT.equals(editPartQualifiedName);
			case ExpansionModelPackage.REPRESENTATION_KIND__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExpansionModelPackage.REPRESENTATION_KIND__VIEW_FACTORY:
				return VIEW_FACTORY_EDEFAULT == null ? viewFactory != null : !VIEW_FACTORY_EDEFAULT.equals(viewFactory);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (editPartQualifiedName: ");
		result.append(editPartQualifiedName);
		result.append(", name: ");
		result.append(name);
		result.append(", viewFactory: ");
		result.append(viewFactory);
		result.append(')');
		return result.toString();
	}

} //RepresentationKindImpl
