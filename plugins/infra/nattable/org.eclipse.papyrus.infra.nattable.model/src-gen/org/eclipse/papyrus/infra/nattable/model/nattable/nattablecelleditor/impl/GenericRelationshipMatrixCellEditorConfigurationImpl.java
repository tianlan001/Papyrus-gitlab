/**
 * Copyright (c) 2013 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl.StyledElementImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Relationship Matrix Cell Editor Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getCellEditorId <em>Cell Editor Id</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getCellContentsFilter <em>Cell Contents Filter</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getEditedElement <em>Edited Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getRelationshipOwnerStrategy <em>Relationship Owner Strategy</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getRelationshipOwner <em>Relationship Owner</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl#getRelationshipOwnerFeature <em>Relationship Owner Feature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericRelationshipMatrixCellEditorConfigurationImpl extends StyledElementImpl implements GenericRelationshipMatrixCellEditorConfiguration {
	/**
	 * The default value of the '{@link #getCellEditorId() <em>Cell Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellEditorId()
	 * @generated
	 * @ordered
	 */
	protected static final String CELL_EDITOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCellEditorId() <em>Cell Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellEditorId()
	 * @generated
	 * @ordered
	 */
	protected String cellEditorId = CELL_EDITOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final MatrixRelationShipDirection DIRECTION_EDEFAULT = MatrixRelationShipDirection.FROM_ROW_TO_COLUMN;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected MatrixRelationShipDirection direction = DIRECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCellContentsFilter() <em>Cell Contents Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellContentsFilter()
	 * @generated
	 * @ordered
	 */
	protected IBooleanEObjectExpression cellContentsFilter;

	/**
	 * The cached value of the '{@link #getEditedElement() <em>Edited Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditedElement()
	 * @generated
	 * @ordered
	 */
	protected ElementTypeConfiguration editedElement;

	/**
	 * The default value of the '{@link #getRelationshipOwnerStrategy() <em>Relationship Owner Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationshipOwnerStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final MatrixRelationShipOwnerStrategy RELATIONSHIP_OWNER_STRATEGY_EDEFAULT = MatrixRelationShipOwnerStrategy.DEFAULT;

	/**
	 * The cached value of the '{@link #getRelationshipOwnerStrategy() <em>Relationship Owner Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationshipOwnerStrategy()
	 * @generated
	 * @ordered
	 */
	protected MatrixRelationShipOwnerStrategy relationshipOwnerStrategy = RELATIONSHIP_OWNER_STRATEGY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRelationshipOwner() <em>Relationship Owner</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationshipOwner()
	 * @generated
	 * @ordered
	 */
	protected IWrapper relationshipOwner;

	/**
	 * The cached value of the '{@link #getRelationshipOwnerFeature() <em>Relationship Owner Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationshipOwnerFeature()
	 * @generated
	 * @ordered
	 */
	protected IAxis relationshipOwnerFeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericRelationshipMatrixCellEditorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattablecelleditorPackage.Literals.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCellEditorId() {
		return cellEditorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCellEditorId(String newCellEditorId) {
		String oldCellEditorId = cellEditorId;
		cellEditorId = newCellEditorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID, oldCellEditorId, cellEditorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MatrixRelationShipDirection getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(MatrixRelationShipDirection newDirection) {
		MatrixRelationShipDirection oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBooleanEObjectExpression getCellContentsFilter() {
		return cellContentsFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCellContentsFilter(IBooleanEObjectExpression newCellContentsFilter, NotificationChain msgs) {
		IBooleanEObjectExpression oldCellContentsFilter = cellContentsFilter;
		cellContentsFilter = newCellContentsFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER, oldCellContentsFilter, newCellContentsFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCellContentsFilter(IBooleanEObjectExpression newCellContentsFilter) {
		if (newCellContentsFilter != cellContentsFilter) {
			NotificationChain msgs = null;
			if (cellContentsFilter != null)
				msgs = ((InternalEObject)cellContentsFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER, null, msgs);
			if (newCellContentsFilter != null)
				msgs = ((InternalEObject)newCellContentsFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER, null, msgs);
			msgs = basicSetCellContentsFilter(newCellContentsFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER, newCellContentsFilter, newCellContentsFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfiguration getEditedElement() {
		if (editedElement != null && editedElement.eIsProxy()) {
			InternalEObject oldEditedElement = (InternalEObject)editedElement;
			editedElement = (ElementTypeConfiguration)eResolveProxy(oldEditedElement);
			if (editedElement != oldEditedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT, oldEditedElement, editedElement));
			}
		}
		return editedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfiguration basicGetEditedElement() {
		return editedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditedElement(ElementTypeConfiguration newEditedElement) {
		ElementTypeConfiguration oldEditedElement = editedElement;
		editedElement = newEditedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT, oldEditedElement, editedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MatrixRelationShipOwnerStrategy getRelationshipOwnerStrategy() {
		return relationshipOwnerStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationshipOwnerStrategy(MatrixRelationShipOwnerStrategy newRelationshipOwnerStrategy) {
		MatrixRelationShipOwnerStrategy oldRelationshipOwnerStrategy = relationshipOwnerStrategy;
		relationshipOwnerStrategy = newRelationshipOwnerStrategy == null ? RELATIONSHIP_OWNER_STRATEGY_EDEFAULT : newRelationshipOwnerStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY, oldRelationshipOwnerStrategy, relationshipOwnerStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWrapper getRelationshipOwner() {
		return relationshipOwner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelationshipOwner(IWrapper newRelationshipOwner, NotificationChain msgs) {
		IWrapper oldRelationshipOwner = relationshipOwner;
		relationshipOwner = newRelationshipOwner;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER, oldRelationshipOwner, newRelationshipOwner);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationshipOwner(IWrapper newRelationshipOwner) {
		if (newRelationshipOwner != relationshipOwner) {
			NotificationChain msgs = null;
			if (relationshipOwner != null)
				msgs = ((InternalEObject)relationshipOwner).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER, null, msgs);
			if (newRelationshipOwner != null)
				msgs = ((InternalEObject)newRelationshipOwner).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER, null, msgs);
			msgs = basicSetRelationshipOwner(newRelationshipOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER, newRelationshipOwner, newRelationshipOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAxis getRelationshipOwnerFeature() {
		return relationshipOwnerFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelationshipOwnerFeature(IAxis newRelationshipOwnerFeature, NotificationChain msgs) {
		IAxis oldRelationshipOwnerFeature = relationshipOwnerFeature;
		relationshipOwnerFeature = newRelationshipOwnerFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE, oldRelationshipOwnerFeature, newRelationshipOwnerFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationshipOwnerFeature(IAxis newRelationshipOwnerFeature) {
		if (newRelationshipOwnerFeature != relationshipOwnerFeature) {
			NotificationChain msgs = null;
			if (relationshipOwnerFeature != null)
				msgs = ((InternalEObject)relationshipOwnerFeature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE, null, msgs);
			if (newRelationshipOwnerFeature != null)
				msgs = ((InternalEObject)newRelationshipOwnerFeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE, null, msgs);
			msgs = basicSetRelationshipOwnerFeature(newRelationshipOwnerFeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE, newRelationshipOwnerFeature, newRelationshipOwnerFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER:
				return basicSetCellContentsFilter(null, msgs);
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER:
				return basicSetRelationshipOwner(null, msgs);
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE:
				return basicSetRelationshipOwnerFeature(null, msgs);
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
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID:
				return getCellEditorId();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION:
				return getDirection();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER:
				return getCellContentsFilter();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT:
				if (resolve) return getEditedElement();
				return basicGetEditedElement();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY:
				return getRelationshipOwnerStrategy();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER:
				return getRelationshipOwner();
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE:
				return getRelationshipOwnerFeature();
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
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID:
				setCellEditorId((String)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION:
				setDirection((MatrixRelationShipDirection)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER:
				setCellContentsFilter((IBooleanEObjectExpression)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT:
				setEditedElement((ElementTypeConfiguration)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY:
				setRelationshipOwnerStrategy((MatrixRelationShipOwnerStrategy)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER:
				setRelationshipOwner((IWrapper)newValue);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE:
				setRelationshipOwnerFeature((IAxis)newValue);
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
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID:
				setCellEditorId(CELL_EDITOR_ID_EDEFAULT);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER:
				setCellContentsFilter((IBooleanEObjectExpression)null);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT:
				setEditedElement((ElementTypeConfiguration)null);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY:
				setRelationshipOwnerStrategy(RELATIONSHIP_OWNER_STRATEGY_EDEFAULT);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER:
				setRelationshipOwner((IWrapper)null);
				return;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE:
				setRelationshipOwnerFeature((IAxis)null);
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
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID:
				return CELL_EDITOR_ID_EDEFAULT == null ? cellEditorId != null : !CELL_EDITOR_ID_EDEFAULT.equals(cellEditorId);
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER:
				return cellContentsFilter != null;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT:
				return editedElement != null;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY:
				return relationshipOwnerStrategy != RELATIONSHIP_OWNER_STRATEGY_EDEFAULT;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER:
				return relationshipOwner != null;
			case NattablecelleditorPackage.GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE:
				return relationshipOwnerFeature != null;
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
		result.append(" (cellEditorId: "); //$NON-NLS-1$
		result.append(cellEditorId);
		result.append(", direction: "); //$NON-NLS-1$
		result.append(direction);
		result.append(", relationshipOwnerStrategy: "); //$NON-NLS-1$
		result.append(relationshipOwnerStrategy);
		result.append(')');
		return result.toString();
	}

} //GenericRelationshipMatrixCellEditorConfigurationImpl
