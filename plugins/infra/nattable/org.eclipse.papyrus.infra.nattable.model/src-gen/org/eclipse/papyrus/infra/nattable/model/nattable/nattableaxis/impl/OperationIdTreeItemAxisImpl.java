/**
 * Copyright (c) 2013 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdTreeItemAxis;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl.StyledElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Id Tree Item Axis</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getManager <em>Manager</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#isExpanded <em>Expanded</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.OperationIdTreeItemAxisImpl#getLocalLabelConfiguration <em>Local Label Configuration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationIdTreeItemAxisImpl extends StyledElementImpl implements OperationIdTreeItemAxis {
	/**
	 * The cached value of the '{@link #getManager() <em>Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManager()
	 * @generated
	 * @ordered
	 */
	protected AxisManagerRepresentation manager;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected ITreeItemAxis parent;

	/**
	 * The default value of the '{@link #isExpanded() <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExpanded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXPANDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExpanded() <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExpanded()
	 * @generated
	 * @ordered
	 */
	protected boolean expanded = EXPANDED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<ITreeItemAxis> children;

	/**
	 * The default value of the '{@link #getElement() <em>Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected String element = ELEMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLocalLabelConfiguration() <em>Local Label Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalLabelConfiguration()
	 * @generated
	 * @ordered
	 */
	protected OperationLabelProviderConfiguration localLabelConfiguration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationIdTreeItemAxisImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattableaxisPackage.Literals.OPERATION_ID_TREE_ITEM_AXIS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AxisManagerRepresentation getManager() {
		if (manager != null && manager.eIsProxy()) {
			InternalEObject oldManager = (InternalEObject)manager;
			manager = (AxisManagerRepresentation)eResolveProxy(oldManager);
			if (manager != oldManager) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER, oldManager, manager));
			}
		}
		return manager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AxisManagerRepresentation basicGetManager() {
		return manager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManager(AxisManagerRepresentation newManager) {
		AxisManagerRepresentation oldManager = manager;
		manager = newManager;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER, oldManager, manager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITreeItemAxis getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (ITreeItemAxis)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITreeItemAxis basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(ITreeItemAxis newParent, NotificationChain msgs) {
		ITreeItemAxis oldParent = parent;
		parent = newParent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT, oldParent, newParent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(ITreeItemAxis newParent) {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, NattableaxisPackage.ITREE_ITEM_AXIS__CHILDREN, ITreeItemAxis.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, NattableaxisPackage.ITREE_ITEM_AXIS__CHILDREN, ITreeItemAxis.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExpanded() {
		return expanded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpanded(boolean newExpanded) {
		boolean oldExpanded = expanded;
		expanded = newExpanded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__EXPANDED, oldExpanded, expanded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ITreeItemAxis> getChildren() {
		if (children == null) {
			children = new EObjectWithInverseResolvingEList<ITreeItemAxis>(ITreeItemAxis.class, this, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN, NattableaxisPackage.ITREE_ITEM_AXIS__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElement() {
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElement(String newElement) {
		String oldElement = element;
		element = newElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationLabelProviderConfiguration getLocalLabelConfiguration() {
		return localLabelConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocalLabelConfiguration(OperationLabelProviderConfiguration newLocalLabelConfiguration, NotificationChain msgs) {
		OperationLabelProviderConfiguration oldLocalLabelConfiguration = localLabelConfiguration;
		localLabelConfiguration = newLocalLabelConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION, oldLocalLabelConfiguration, newLocalLabelConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalLabelConfiguration(OperationLabelProviderConfiguration newLocalLabelConfiguration) {
		if (newLocalLabelConfiguration != localLabelConfiguration) {
			NotificationChain msgs = null;
			if (localLabelConfiguration != null)
				msgs = ((InternalEObject)localLabelConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION, null, msgs);
			if (newLocalLabelConfiguration != null)
				msgs = ((InternalEObject)newLocalLabelConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION, null, msgs);
			msgs = basicSetLocalLabelConfiguration(newLocalLabelConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION, newLocalLabelConfiguration, newLocalLabelConfiguration));
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, NattableaxisPackage.ITREE_ITEM_AXIS__CHILDREN, ITreeItemAxis.class, msgs);
				return basicSetParent((ITreeItemAxis)otherEnd, msgs);
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				return basicSetParent(null, msgs);
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION:
				return basicSetLocalLabelConfiguration(null, msgs);
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER:
				if (resolve) return getManager();
				return basicGetManager();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ALIAS:
				return getAlias();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__EXPANDED:
				return isExpanded();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				return getChildren();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT:
				return getElement();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION:
				return getLocalLabelConfiguration();
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER:
				setManager((AxisManagerRepresentation)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ALIAS:
				setAlias((String)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				setParent((ITreeItemAxis)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__EXPANDED:
				setExpanded((Boolean)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends ITreeItemAxis>)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT:
				setElement((String)newValue);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION:
				setLocalLabelConfiguration((OperationLabelProviderConfiguration)newValue);
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER:
				setManager((AxisManagerRepresentation)null);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				setParent((ITreeItemAxis)null);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__EXPANDED:
				setExpanded(EXPANDED_EDEFAULT);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				getChildren().clear();
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION:
				setLocalLabelConfiguration((OperationLabelProviderConfiguration)null);
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
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__MANAGER:
				return manager != null;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__PARENT:
				return parent != null;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__EXPANDED:
				return expanded != EXPANDED_EDEFAULT;
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__CHILDREN:
				return children != null && !children.isEmpty();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION:
				return localLabelConfiguration != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IdAxis.class) {
			switch (derivedFeatureID) {
				case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT: return NattableaxisPackage.ID_AXIS__ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == OperationAxis.class) {
			switch (derivedFeatureID) {
				case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION: return NattableaxisPackage.OPERATION_AXIS__LOCAL_LABEL_CONFIGURATION;
				default: return -1;
			}
		}
		if (baseClass == OperationIdAxis.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IdAxis.class) {
			switch (baseFeatureID) {
				case NattableaxisPackage.ID_AXIS__ELEMENT: return NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == OperationAxis.class) {
			switch (baseFeatureID) {
				case NattableaxisPackage.OPERATION_AXIS__LOCAL_LABEL_CONFIGURATION: return NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS__LOCAL_LABEL_CONFIGURATION;
				default: return -1;
			}
		}
		if (baseClass == OperationIdAxis.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (alias: "); //$NON-NLS-1$
		result.append(alias);
		result.append(", expanded: "); //$NON-NLS-1$
		result.append(expanded);
		result.append(", element: "); //$NON-NLS-1$
		result.append(element);
		result.append(')');
		return result.toString();
	}

} //OperationIdTreeItemAxisImpl
