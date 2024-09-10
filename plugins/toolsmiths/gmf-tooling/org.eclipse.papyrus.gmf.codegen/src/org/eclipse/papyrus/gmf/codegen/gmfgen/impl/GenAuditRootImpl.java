/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Audit Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRootImpl#getEditorGen <em>Editor Gen</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRootImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRootImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRootImpl#getClientContexts <em>Client Contexts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenAuditRootImpl extends EObjectImpl implements GenAuditRoot {
	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<GenAuditContainer> categories;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<GenAuditRule> rules;

	/**
	 * The cached value of the '{@link #getClientContexts() <em>Client Contexts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<GenAuditContext> clientContexts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenAuditRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenAuditRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorGenerator getEditorGen() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN) return null;
		return (GenEditorGenerator)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenAuditContainer> getCategories() {
		if (categories == null) {
			categories = new EObjectContainmentWithInverseEList<GenAuditContainer>(GenAuditContainer.class, this, GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES, GMFGenPackage.GEN_AUDIT_CONTAINER__ROOT);
		}
		return categories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenAuditRule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentWithInverseEList<GenAuditRule>(GenAuditRule.class, this, GMFGenPackage.GEN_AUDIT_ROOT__RULES, GMFGenPackage.GEN_AUDIT_RULE__ROOT);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenAuditContext> getClientContexts() {
		if (clientContexts == null) {
			clientContexts = new EObjectContainmentWithInverseEList<GenAuditContext>(GenAuditContext.class, this, GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS, GMFGenPackage.GEN_AUDIT_CONTEXT__ROOT);
		}
		return clientContexts;
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
			case GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCategories()).basicAdd(otherEnd, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRules()).basicAdd(otherEnd, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getClientContexts()).basicAdd(otherEnd, msgs);
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
			case GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN:
				return eBasicSetContainer(null, GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				return ((InternalEList<?>)getCategories()).basicRemove(otherEnd, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				return ((InternalEList<?>)getClientContexts()).basicRemove(otherEnd, msgs);
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
			case GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_EDITOR_GENERATOR__AUDITS, GenEditorGenerator.class, msgs);
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
			case GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN:
				return getEditorGen();
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				return getCategories();
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				return getRules();
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				return getClientContexts();
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
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection<? extends GenAuditContainer>)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends GenAuditRule>)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				getClientContexts().clear();
				getClientContexts().addAll((Collection<? extends GenAuditContext>)newValue);
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
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				getCategories().clear();
				return;
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				getRules().clear();
				return;
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				getClientContexts().clear();
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
			case GMFGenPackage.GEN_AUDIT_ROOT__EDITOR_GEN:
				return getEditorGen() != null;
			case GMFGenPackage.GEN_AUDIT_ROOT__CATEGORIES:
				return categories != null && !categories.isEmpty();
			case GMFGenPackage.GEN_AUDIT_ROOT__RULES:
				return rules != null && !rules.isEmpty();
			case GMFGenPackage.GEN_AUDIT_ROOT__CLIENT_CONTEXTS:
				return clientContexts != null && !clientContexts.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public List<GenPackage> getTargetedModelPackages() {
		ArrayList<GenPackage> targetPackages = new ArrayList<GenPackage>();
		for (GenAuditRule r : getRules()) {
			if (r.getTarget() != null && r.getTarget().getTargetClass() != null) {
				targetPackages.add(r.getTarget().getTargetClass().getGenPackage());
			}
		}
		return targetPackages;
	}
} //GenAuditRootImpl
