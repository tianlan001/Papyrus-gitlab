/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.emf.types.constraints.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.operations.AdviceConstraintOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advice Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl#getComposite <em>Composite</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl#getAdvice <em>Advice</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl#getOwningAdvice <em>Owning Advice</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AdviceConstraintImpl extends MinimalEObjectImpl.Container implements AdviceConstraint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AdviceConstraintImpl() {
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
		return ConstraintAdvicePackage.Literals.ADVICE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CompositeConstraint getComposite() {
		if (eContainerFeatureID() != ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE) {
			return null;
		}
		return (CompositeConstraint) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetComposite(CompositeConstraint newComposite, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newComposite, ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setComposite(CompositeConstraint newComposite) {
		if (newComposite != eInternalContainer() || (eContainerFeatureID() != ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE && newComposite != null)) {
			if (EcoreUtil.isAncestor(this, newComposite)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newComposite != null) {
				msgs = ((InternalEObject) newComposite).eInverseAdd(this, ConstraintAdvicePackage.COMPOSITE_CONSTRAINT__CONSTRAINT, CompositeConstraint.class, msgs);
			}
			msgs = basicSetComposite(newComposite, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE, newComposite, newComposite));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConstraintAdviceConfiguration getAdvice() {
		ConstraintAdviceConfiguration advice = basicGetAdvice();
		return advice != null && advice.eIsProxy() ? (ConstraintAdviceConfiguration) eResolveProxy((InternalEObject) advice) : advice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ConstraintAdviceConfiguration basicGetAdvice() {
		return AdviceConstraintOperations.getAdvice(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConstraintAdviceConfiguration getOwningAdvice() {
		if (eContainerFeatureID() != ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE) {
			return null;
		}
		return (ConstraintAdviceConfiguration) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwningAdvice(ConstraintAdviceConfiguration newOwningAdvice, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOwningAdvice, ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOwningAdvice(ConstraintAdviceConfiguration newOwningAdvice) {
		if (newOwningAdvice != eInternalContainer() || (eContainerFeatureID() != ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE && newOwningAdvice != null)) {
			if (EcoreUtil.isAncestor(this, newOwningAdvice)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newOwningAdvice != null) {
				msgs = ((InternalEObject) newOwningAdvice).eInverseAdd(this, ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT, ConstraintAdviceConfiguration.class, msgs);
			}
			msgs = basicSetOwningAdvice(newOwningAdvice, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE, newOwningAdvice, newOwningAdvice));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		return AdviceConstraintOperations.approveRequest(this, request);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetComposite((CompositeConstraint) otherEnd, msgs);
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetOwningAdvice((ConstraintAdviceConfiguration) otherEnd, msgs);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			return basicSetComposite(null, msgs);
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			return basicSetOwningAdvice(null, msgs);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			return eInternalContainer().eInverseRemove(this, ConstraintAdvicePackage.COMPOSITE_CONSTRAINT__CONSTRAINT, CompositeConstraint.class, msgs);
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			return eInternalContainer().eInverseRemove(this, ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT, ConstraintAdviceConfiguration.class, msgs);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			return getComposite();
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__ADVICE:
			if (resolve) {
				return getAdvice();
			}
			return basicGetAdvice();
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			return getOwningAdvice();
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			setComposite((CompositeConstraint) newValue);
			return;
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			setOwningAdvice((ConstraintAdviceConfiguration) newValue);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			setComposite((CompositeConstraint) null);
			return;
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			setOwningAdvice((ConstraintAdviceConfiguration) null);
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__COMPOSITE:
			return getComposite() != null;
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__ADVICE:
			return basicGetAdvice() != null;
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE:
			return getOwningAdvice() != null;
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
		case ConstraintAdvicePackage.ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST:
			return approveRequest((IEditCommandRequest) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} // AdviceConstraintImpl
