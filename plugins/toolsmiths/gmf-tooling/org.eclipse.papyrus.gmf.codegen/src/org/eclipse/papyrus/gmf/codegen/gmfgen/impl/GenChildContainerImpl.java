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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Child Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getContainedNodes <em>Contained Nodes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getCanonicalEditPolicyClassName <em>Canonical Edit Policy Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getSpecificDiagramUpdaterClassName <em>Specific Diagram Updater Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getChildNodes <em>Child Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GenChildContainerImpl extends GenCommonBaseImpl implements GenChildContainer {
	/**
	 * The default value of the '{@link #getCanonicalEditPolicyClassName() <em>Canonical Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanonicalEditPolicyClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCanonicalEditPolicyClassName() <em>Canonical Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanonicalEditPolicyClassName()
	 * @generated
	 * @ordered
	 */
	protected String canonicalEditPolicyClassName = CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecificDiagramUpdaterClassName() <em>Specific Diagram Updater Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecificDiagramUpdaterClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecificDiagramUpdaterClassName() <em>Specific Diagram Updater Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecificDiagramUpdaterClassName()
	 * @generated
	 * @ordered
	 */
	protected String specificDiagramUpdaterClassName = SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildNodes() <em>Child Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<GenChildNode> childNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenChildContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenChildContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenNode> getContainedNodes() {
		List<GenChildNode> childNodes = getChildNodes();
		return new EcoreEList.UnmodifiableEList<GenNode>(this, GMFGenPackage.eINSTANCE.getGenContainerBase_ContainedNodes(), childNodes.size(), childNodes.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenChildNode> getChildNodes() {
		if (childNodes == null) {
			childNodes = new EObjectWithInverseResolvingEList.ManyInverse<GenChildNode>(GenChildNode.class, this, GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES, GMFGenPackage.GEN_CHILD_NODE__CONTAINERS);
		}
		return childNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenNode> getAssistantNodes() {
		return getContainedNodes();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean needsCanonicalEditPolicy() {
		// mirror logic previously on getSemanticChildrenList.jetinc - genNodes.size() > 0
		// and refreshSemanticNode.jetinc - genClass2Phantom.size() > 0 && phantomLinks.size() > 0
		if (!isSansDomain() && getDiagram().isSynchronized()) {
			return !getContainedNodes().isEmpty() || (getDiagram().getGenClass2PhantomMap().size() > 0 && getDiagram().getPhantomLinks().size() > 0);
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCanonicalEditPolicyClassNameGen() {
		return canonicalEditPolicyClassName;
	}

	public String getCanonicalEditPolicyClassName() {
		return getValidClassName(getCanonicalEditPolicyClassNameGen(), this, CANONICAL_EDIT_POLICY_SUFFIX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCanonicalEditPolicyClassName(String newCanonicalEditPolicyClassName) {
		String oldCanonicalEditPolicyClassName = canonicalEditPolicyClassName;
		canonicalEditPolicyClassName = newCanonicalEditPolicyClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME, oldCanonicalEditPolicyClassName, canonicalEditPolicyClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSpecificDiagramUpdaterClassName() {
		return specificDiagramUpdaterClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpecificDiagramUpdaterClassName(String newSpecificDiagramUpdaterClassName) {
		String oldSpecificDiagramUpdaterClassName = specificDiagramUpdaterClassName;
		specificDiagramUpdaterClassName = newSpecificDiagramUpdaterClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CHILD_CONTAINER__SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME, oldSpecificDiagramUpdaterClassName, specificDiagramUpdaterClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getCanonicalEditPolicyQualifiedClassName() {
		return getDiagram().getEditPoliciesPackageName() + '.' + getCanonicalEditPolicyClassName();
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildNodes()).basicAdd(otherEnd, msgs);
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return ((InternalEList<?>)getChildNodes()).basicRemove(otherEnd, msgs);
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CONTAINED_NODES:
				return getContainedNodes();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				return getCanonicalEditPolicyClassName();
			case GMFGenPackage.GEN_CHILD_CONTAINER__SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME:
				return getSpecificDiagramUpdaterClassName();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return getChildNodes();
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				setCanonicalEditPolicyClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME:
				setSpecificDiagramUpdaterClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				getChildNodes().clear();
				getChildNodes().addAll((Collection<? extends GenChildNode>)newValue);
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				setCanonicalEditPolicyClassName(CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME:
				setSpecificDiagramUpdaterClassName(SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				getChildNodes().clear();
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
			case GMFGenPackage.GEN_CHILD_CONTAINER__CONTAINED_NODES:
				return !getContainedNodes().isEmpty();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				return CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT == null ? canonicalEditPolicyClassName != null : !CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT.equals(canonicalEditPolicyClassName);
			case GMFGenPackage.GEN_CHILD_CONTAINER__SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME:
				return SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME_EDEFAULT == null ? specificDiagramUpdaterClassName != null : !SPECIFIC_DIAGRAM_UPDATER_CLASS_NAME_EDEFAULT.equals(specificDiagramUpdaterClassName);
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return childNodes != null && !childNodes.isEmpty();
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
		result.append(" (canonicalEditPolicyClassName: ");
		result.append(canonicalEditPolicyClassName);
		result.append(", specificDiagramUpdaterClassName: ");
		result.append(specificDiagramUpdaterClassName);
		result.append(')');
		return result.toString();
	}

} //GenChildContainerImpl
