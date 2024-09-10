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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.constraints.DisplayUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl#getElementMultiplicity <em>Element Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl#getStereotypes <em>Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl#getMultiplicity <em>Multiplicity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelRuleImpl extends RuleImpl implements ModelRule {
	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintDescriptor> constraints;

	/**
	 * The default value of the '{@link #getElementMultiplicity() <em>Element Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final int ELEMENT_MULTIPLICITY_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getElementMultiplicity() <em>Element Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected int elementMultiplicity = ELEMENT_MULTIPLICITY_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepresentationPackage.Literals.MODEL_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConstraintDescriptor> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentWithInverseEList<ConstraintDescriptor>(ConstraintDescriptor.class, this, RepresentationPackage.MODEL_RULE__CONSTRAINTS, ConstraintsPackage.CONSTRAINT_DESCRIPTOR__DISPLAY);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getElementMultiplicity() {
		return elementMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElementMultiplicity(int newElementMultiplicity) {
		int oldElementMultiplicity = elementMultiplicity;
		elementMultiplicity = newElementMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY, oldElementMultiplicity, elementMultiplicity));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepresentationPackage.MODEL_RULE__ELEMENT, oldElement, element));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.MODEL_RULE__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EClass> getStereotypes() {
		if (stereotypes == null) {
			stereotypes = new EObjectResolvingEList<EClass>(EClass.class, this, RepresentationPackage.MODEL_RULE__STEREOTYPES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.MODEL_RULE__MULTIPLICITY, oldMultiplicity, multiplicity));
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstraints()).basicAdd(otherEnd, msgs);
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				return getConstraints();
			case RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY:
				return getElementMultiplicity();
			case RepresentationPackage.MODEL_RULE__ELEMENT:
				if (resolve) return getElement();
				return basicGetElement();
			case RepresentationPackage.MODEL_RULE__STEREOTYPES:
				return getStereotypes();
			case RepresentationPackage.MODEL_RULE__MULTIPLICITY:
				return getMultiplicity();
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends ConstraintDescriptor>)newValue);
				return;
			case RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY:
				setElementMultiplicity((Integer)newValue);
				return;
			case RepresentationPackage.MODEL_RULE__ELEMENT:
				setElement((EClass)newValue);
				return;
			case RepresentationPackage.MODEL_RULE__STEREOTYPES:
				getStereotypes().clear();
				getStereotypes().addAll((Collection<? extends EClass>)newValue);
				return;
			case RepresentationPackage.MODEL_RULE__MULTIPLICITY:
				setMultiplicity((Integer)newValue);
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				getConstraints().clear();
				return;
			case RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY:
				setElementMultiplicity(ELEMENT_MULTIPLICITY_EDEFAULT);
				return;
			case RepresentationPackage.MODEL_RULE__ELEMENT:
				setElement((EClass)null);
				return;
			case RepresentationPackage.MODEL_RULE__STEREOTYPES:
				getStereotypes().clear();
				return;
			case RepresentationPackage.MODEL_RULE__MULTIPLICITY:
				setMultiplicity(MULTIPLICITY_EDEFAULT);
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
			case RepresentationPackage.MODEL_RULE__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY:
				return elementMultiplicity != ELEMENT_MULTIPLICITY_EDEFAULT;
			case RepresentationPackage.MODEL_RULE__ELEMENT:
				return element != null;
			case RepresentationPackage.MODEL_RULE__STEREOTYPES:
				return stereotypes != null && !stereotypes.isEmpty();
			case RepresentationPackage.MODEL_RULE__MULTIPLICITY:
				return multiplicity != MULTIPLICITY_EDEFAULT;
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
		if (baseClass == DisplayUnit.class) {
			switch (derivedFeatureID) {
				case RepresentationPackage.MODEL_RULE__CONSTRAINTS: return ConstraintsPackage.DISPLAY_UNIT__CONSTRAINTS;
				case RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY: return ConstraintsPackage.DISPLAY_UNIT__ELEMENT_MULTIPLICITY;
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
		if (baseClass == DisplayUnit.class) {
			switch (baseFeatureID) {
				case ConstraintsPackage.DISPLAY_UNIT__CONSTRAINTS: return RepresentationPackage.MODEL_RULE__CONSTRAINTS;
				case ConstraintsPackage.DISPLAY_UNIT__ELEMENT_MULTIPLICITY: return RepresentationPackage.MODEL_RULE__ELEMENT_MULTIPLICITY;
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
		result.append(" (elementMultiplicity: "); //$NON-NLS-1$
		result.append(elementMultiplicity);
		result.append(", multiplicity: "); //$NON-NLS-1$
		result.append(multiplicity);
		result.append(')');
		return result.toString();
	}

} //ModelRuleImpl
