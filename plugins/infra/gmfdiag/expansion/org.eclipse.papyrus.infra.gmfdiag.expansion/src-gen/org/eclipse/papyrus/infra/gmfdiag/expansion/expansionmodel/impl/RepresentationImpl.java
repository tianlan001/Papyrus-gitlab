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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Representation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl#getInducedRepresentations <em>Induced Representations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl#getSubRepresentations <em>Sub Representations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl#getGraphicalElementTypeRef <em>Graphical Element Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepresentationImpl extends AbstractRepresentationImpl implements Representation {
	/**
	 * The cached value of the '{@link #getInducedRepresentations() <em>Induced Representations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInducedRepresentations()
	 * @generated
	 * @ordered
	 */
	protected EList<InducedRepresentation> inducedRepresentations;

	/**
	 * The cached value of the '{@link #getSubRepresentations() <em>Sub Representations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubRepresentations()
	 * @generated
	 * @ordered
	 */
	protected EList<Representation> subRepresentations;

	/**
	 * The cached value of the '{@link #getGraphicalElementTypeRef() <em>Graphical Element Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalElementTypeRef()
	 * @generated
	 * @ordered
	 */
	protected ElementTypeConfiguration graphicalElementTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepresentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpansionModelPackage.Literals.REPRESENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InducedRepresentation> getInducedRepresentations() {
		if (inducedRepresentations == null) {
			inducedRepresentations = new EObjectResolvingEList<InducedRepresentation>(InducedRepresentation.class, this, ExpansionModelPackage.REPRESENTATION__INDUCED_REPRESENTATIONS);
		}
		return inducedRepresentations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Representation> getSubRepresentations() {
		if (subRepresentations == null) {
			subRepresentations = new EObjectResolvingEList<Representation>(Representation.class, this, ExpansionModelPackage.REPRESENTATION__SUB_REPRESENTATIONS);
		}
		return subRepresentations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfiguration getGraphicalElementTypeRef() {
		if (graphicalElementTypeRef != null && graphicalElementTypeRef.eIsProxy()) {
			InternalEObject oldGraphicalElementTypeRef = (InternalEObject)graphicalElementTypeRef;
			graphicalElementTypeRef = (ElementTypeConfiguration)eResolveProxy(oldGraphicalElementTypeRef);
			if (graphicalElementTypeRef != oldGraphicalElementTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF, oldGraphicalElementTypeRef, graphicalElementTypeRef));
			}
		}
		return graphicalElementTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfiguration basicGetGraphicalElementTypeRef() {
		return graphicalElementTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphicalElementTypeRef(ElementTypeConfiguration newGraphicalElementTypeRef) {
		ElementTypeConfiguration oldGraphicalElementTypeRef = graphicalElementTypeRef;
		graphicalElementTypeRef = newGraphicalElementTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF, oldGraphicalElementTypeRef, graphicalElementTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpansionModelPackage.REPRESENTATION__INDUCED_REPRESENTATIONS:
				return getInducedRepresentations();
			case ExpansionModelPackage.REPRESENTATION__SUB_REPRESENTATIONS:
				return getSubRepresentations();
			case ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF:
				if (resolve) return getGraphicalElementTypeRef();
				return basicGetGraphicalElementTypeRef();
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
			case ExpansionModelPackage.REPRESENTATION__INDUCED_REPRESENTATIONS:
				getInducedRepresentations().clear();
				getInducedRepresentations().addAll((Collection<? extends InducedRepresentation>)newValue);
				return;
			case ExpansionModelPackage.REPRESENTATION__SUB_REPRESENTATIONS:
				getSubRepresentations().clear();
				getSubRepresentations().addAll((Collection<? extends Representation>)newValue);
				return;
			case ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF:
				setGraphicalElementTypeRef((ElementTypeConfiguration)newValue);
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
			case ExpansionModelPackage.REPRESENTATION__INDUCED_REPRESENTATIONS:
				getInducedRepresentations().clear();
				return;
			case ExpansionModelPackage.REPRESENTATION__SUB_REPRESENTATIONS:
				getSubRepresentations().clear();
				return;
			case ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF:
				setGraphicalElementTypeRef((ElementTypeConfiguration)null);
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
			case ExpansionModelPackage.REPRESENTATION__INDUCED_REPRESENTATIONS:
				return inducedRepresentations != null && !inducedRepresentations.isEmpty();
			case ExpansionModelPackage.REPRESENTATION__SUB_REPRESENTATIONS:
				return subRepresentations != null && !subRepresentations.isEmpty();
			case ExpansionModelPackage.REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF:
				return graphicalElementTypeRef != null;
		}
		return super.eIsSet(featureID);
	}

} //RepresentationImpl
