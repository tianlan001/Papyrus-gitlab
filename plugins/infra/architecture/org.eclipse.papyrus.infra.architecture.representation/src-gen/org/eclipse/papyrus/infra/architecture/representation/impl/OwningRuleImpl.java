/**
 * Copyright (c) 2016 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.architecture.representation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;
import org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Owning Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl#getStereotypes <em>Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl#getNewModelPath <em>New Model Path</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl#getSelectDiagramRoot <em>Select Diagram Root</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OwningRuleImpl extends RuleImpl implements OwningRule {
	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected EClass element;

	/**
	 * The cached value of the '{@link #getStereotypes() <em>Stereotypes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypes()
	 * @generated
	 * @ordered
	 */
	protected EList<EClass> stereotypes;

	/**
	 * The default value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final int MULTIPLICITY_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected int multiplicity = MULTIPLICITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNewModelPath() <em>New Model Path</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewModelPath()
	 * @generated
	 * @ordered
	 */
	protected EList<ModelAutoCreate> newModelPath;

	/**
	 * The cached value of the '{@link #getSelectDiagramRoot() <em>Select Diagram Root</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectDiagramRoot()
	 * @generated
	 * @ordered
	 */
	protected EList<RootAutoSelect> selectDiagramRoot;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OwningRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepresentationPackage.Literals.OWNING_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElement() {
		if (element != null && element.eIsProxy()) {
			InternalEObject oldElement = (InternalEObject)element;
			element = (EClass)eResolveProxy(oldElement);
			if (element != oldElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepresentationPackage.OWNING_RULE__ELEMENT, oldElement, element));
			}
		}
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetElement() {
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElement(EClass newElement) {
		EClass oldElement = element;
		element = newElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.OWNING_RULE__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EClass> getStereotypes() {
		if (stereotypes == null) {
			stereotypes = new EObjectResolvingEList<EClass>(EClass.class, this, RepresentationPackage.OWNING_RULE__STEREOTYPES);
		}
		return stereotypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMultiplicity() {
		return multiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMultiplicity(int newMultiplicity) {
		int oldMultiplicity = multiplicity;
		multiplicity = newMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.OWNING_RULE__MULTIPLICITY, oldMultiplicity, multiplicity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ModelAutoCreate> getNewModelPath() {
		if (newModelPath == null) {
			newModelPath = new EObjectContainmentEList<ModelAutoCreate>(ModelAutoCreate.class, this, RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH);
		}
		return newModelPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RootAutoSelect> getSelectDiagramRoot() {
		if (selectDiagramRoot == null) {
			selectDiagramRoot = new EObjectContainmentEList<RootAutoSelect>(RootAutoSelect.class, this, RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT);
		}
		return selectDiagramRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH:
				return ((InternalEList<?>)getNewModelPath()).basicRemove(otherEnd, msgs);
			case RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT:
				return ((InternalEList<?>)getSelectDiagramRoot()).basicRemove(otherEnd, msgs);
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
			case RepresentationPackage.OWNING_RULE__ELEMENT:
				if (resolve) return getElement();
				return basicGetElement();
			case RepresentationPackage.OWNING_RULE__STEREOTYPES:
				return getStereotypes();
			case RepresentationPackage.OWNING_RULE__MULTIPLICITY:
				return getMultiplicity();
			case RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH:
				return getNewModelPath();
			case RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT:
				return getSelectDiagramRoot();
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
			case RepresentationPackage.OWNING_RULE__ELEMENT:
				setElement((EClass)newValue);
				return;
			case RepresentationPackage.OWNING_RULE__STEREOTYPES:
				getStereotypes().clear();
				getStereotypes().addAll((Collection<? extends EClass>)newValue);
				return;
			case RepresentationPackage.OWNING_RULE__MULTIPLICITY:
				setMultiplicity((Integer)newValue);
				return;
			case RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH:
				getNewModelPath().clear();
				getNewModelPath().addAll((Collection<? extends ModelAutoCreate>)newValue);
				return;
			case RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT:
				getSelectDiagramRoot().clear();
				getSelectDiagramRoot().addAll((Collection<? extends RootAutoSelect>)newValue);
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
			case RepresentationPackage.OWNING_RULE__ELEMENT:
				setElement((EClass)null);
				return;
			case RepresentationPackage.OWNING_RULE__STEREOTYPES:
				getStereotypes().clear();
				return;
			case RepresentationPackage.OWNING_RULE__MULTIPLICITY:
				setMultiplicity(MULTIPLICITY_EDEFAULT);
				return;
			case RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH:
				getNewModelPath().clear();
				return;
			case RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT:
				getSelectDiagramRoot().clear();
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
			case RepresentationPackage.OWNING_RULE__ELEMENT:
				return element != null;
			case RepresentationPackage.OWNING_RULE__STEREOTYPES:
				return stereotypes != null && !stereotypes.isEmpty();
			case RepresentationPackage.OWNING_RULE__MULTIPLICITY:
				return multiplicity != MULTIPLICITY_EDEFAULT;
			case RepresentationPackage.OWNING_RULE__NEW_MODEL_PATH:
				return newModelPath != null && !newModelPath.isEmpty();
			case RepresentationPackage.OWNING_RULE__SELECT_DIAGRAM_ROOT:
				return selectDiagramRoot != null && !selectDiagramRoot.isEmpty();
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
		result.append(" (multiplicity: "); //$NON-NLS-1$
		result.append(multiplicity);
		result.append(')');
		return result.toString();
	}

} //OwningRuleImpl
