/**
* Copyright (c) 2017 CEA LIST.
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
package org.eclipse.papyrus.infra.core.architecture.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Viewpoint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureViewpointImpl#getConcerns <em>Concerns</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureViewpointImpl#getRepresentationKinds <em>Representation Kinds</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureViewpointImpl#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArchitectureViewpointImpl extends ADElementImpl implements ArchitectureViewpoint {
	/**
	 * The cached value of the '{@link #getConcerns() <em>Concerns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcerns()
	 * @generated
	 * @ordered
	 */
	protected EList<Concern> concerns;

	/**
	 * The cached value of the '{@link #getRepresentationKinds() <em>Representation Kinds</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepresentationKinds()
	 * @generated
	 * @ordered
	 */
	protected EList<RepresentationKind> representationKinds;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureViewpointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Concern> getConcerns() {
		if (concerns == null) {
			concerns = new EObjectResolvingEList<Concern>(Concern.class, this, ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS);
		}
		return concerns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RepresentationKind> getRepresentationKinds() {
		if (representationKinds == null) {
			representationKinds = new EObjectResolvingEList<RepresentationKind>(RepresentationKind.class, this, ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS);
		}
		return representationKinds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArchitectureContext getContext() {
		if (eContainerFeatureID() != ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT) return null;
		return (ArchitectureContext)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContext(ArchitectureContext newContext, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContext, ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContext(ArchitectureContext newContext) {
		if (newContext != eInternalContainer() || (eContainerFeatureID() != ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT && newContext != null)) {
			if (EcoreUtil.isAncestor(this, newContext))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContext != null)
				msgs = ((InternalEObject)newContext).eInverseAdd(this, ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS, ArchitectureContext.class, msgs);
			msgs = basicSetContext(newContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT, newContext, newContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContext((ArchitectureContext)otherEnd, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				return basicSetContext(null, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				return eInternalContainer().eInverseRemove(this, ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS, ArchitectureContext.class, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS:
				return getConcerns();
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS:
				return getRepresentationKinds();
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				return getContext();
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS:
				getConcerns().clear();
				getConcerns().addAll((Collection<? extends Concern>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS:
				getRepresentationKinds().clear();
				getRepresentationKinds().addAll((Collection<? extends RepresentationKind>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				setContext((ArchitectureContext)newValue);
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS:
				getConcerns().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS:
				getRepresentationKinds().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				setContext((ArchitectureContext)null);
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
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS:
				return concerns != null && !concerns.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS:
				return representationKinds != null && !representationKinds.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT:
				return getContext() != null;
		}
		return super.eIsSet(featureID);
	}

} //ArchitectureViewpointImpl
