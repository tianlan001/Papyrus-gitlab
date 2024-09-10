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
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTemplateInvocationBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Custom Generator Extension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#getOclType <em>Ocl Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#getGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#isFromCustomBridge <em>From Custom Bridge</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#getRootInput <em>Root Input</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomGeneratorExtensionImpl#getInvocations <em>Invocations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenCustomGeneratorExtensionImpl extends EObjectImpl implements GenCustomGeneratorExtension {
	/**
	 * The default value of the '{@link #getOclType() <em>Ocl Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclType()
	 * @generated
	 * @ordered
	 */
	protected static final String OCL_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOclType() <em>Ocl Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclType()
	 * @generated
	 * @ordered
	 */
	protected String oclType = OCL_TYPE_EDEFAULT;

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
	 * The default value of the '{@link #isFromCustomBridge() <em>From Custom Bridge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFromCustomBridge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FROM_CUSTOM_BRIDGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFromCustomBridge() <em>From Custom Bridge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFromCustomBridge()
	 * @generated
	 * @ordered
	 */
	protected boolean fromCustomBridge = FROM_CUSTOM_BRIDGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRootInput() <em>Root Input</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootInput()
	 * @generated
	 * @ordered
	 */
	protected EObject rootInput;

	/**
	 * The cached value of the '{@link #getInvocations() <em>Invocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocations()
	 * @generated
	 * @ordered
	 */
	protected EList<GenTemplateInvocationBase> invocations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenCustomGeneratorExtensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenCustomGeneratorExtension();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOclType() {
		return oclType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOclType(String newOclType) {
		String oldOclType = oclType;
		oclType = newOclType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__OCL_TYPE, oldOclType, oclType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorGenerator getGenerator() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR) return null;
		return (GenEditorGenerator)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFromCustomBridge() {
		return fromCustomBridge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFromCustomBridge(boolean newFromCustomBridge) {
		boolean oldFromCustomBridge = fromCustomBridge;
		fromCustomBridge = newFromCustomBridge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__FROM_CUSTOM_BRIDGE, oldFromCustomBridge, fromCustomBridge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getRootInput() {
		if (rootInput != null && rootInput.eIsProxy()) {
			InternalEObject oldRootInput = (InternalEObject)rootInput;
			rootInput = eResolveProxy(oldRootInput);
			if (rootInput != oldRootInput) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT, oldRootInput, rootInput));
			}
		}
		return rootInput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetRootInput() {
		return rootInput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRootInput(EObject newRootInput) {
		EObject oldRootInput = rootInput;
		rootInput = newRootInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT, oldRootInput, rootInput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenTemplateInvocationBase> getInvocations() {
		if (invocations == null) {
			invocations = new EObjectContainmentWithInverseEList<GenTemplateInvocationBase>(GenTemplateInvocationBase.class, this, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS, GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION);
		}
		return invocations;
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR, msgs);
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInvocations()).basicAdd(otherEnd, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR:
				return eBasicSetContainer(null, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR, msgs);
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				return ((InternalEList<?>)getInvocations()).basicRemove(otherEnd, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_EDITOR_GENERATOR__EXTENSIONS, GenEditorGenerator.class, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__OCL_TYPE:
				return getOclType();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR:
				return getGenerator();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__NAME:
				return getName();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__FROM_CUSTOM_BRIDGE:
				return isFromCustomBridge();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT:
				if (resolve) return getRootInput();
				return basicGetRootInput();
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				return getInvocations();
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__OCL_TYPE:
				setOclType((String)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__NAME:
				setName((String)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__FROM_CUSTOM_BRIDGE:
				setFromCustomBridge((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT:
				setRootInput((EObject)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				getInvocations().clear();
				getInvocations().addAll((Collection<? extends GenTemplateInvocationBase>)newValue);
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__OCL_TYPE:
				setOclType(OCL_TYPE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__FROM_CUSTOM_BRIDGE:
				setFromCustomBridge(FROM_CUSTOM_BRIDGE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT:
				setRootInput((EObject)null);
				return;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				getInvocations().clear();
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
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__OCL_TYPE:
				return OCL_TYPE_EDEFAULT == null ? oclType != null : !OCL_TYPE_EDEFAULT.equals(oclType);
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__GENERATOR:
				return getGenerator() != null;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__FROM_CUSTOM_BRIDGE:
				return fromCustomBridge != FROM_CUSTOM_BRIDGE_EDEFAULT;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__ROOT_INPUT:
				return rootInput != null;
			case GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS:
				return invocations != null && !invocations.isEmpty();
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
		result.append(" (oclType: ");
		result.append(oclType);
		result.append(", name: ");
		result.append(name);
		result.append(", fromCustomBridge: ");
		result.append(fromCustomBridge);
		result.append(')');
		return result.toString();
	}

} //GenCustomGeneratorExtensionImpl
