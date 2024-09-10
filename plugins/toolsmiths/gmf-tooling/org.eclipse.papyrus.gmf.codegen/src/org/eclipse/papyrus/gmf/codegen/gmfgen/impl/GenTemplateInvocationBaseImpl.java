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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTemplateInvocationBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Template Invocation Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenTemplateInvocationBaseImpl#getExtension <em>Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenTemplateInvocationBaseImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenTemplateInvocationBaseImpl#getTemplateFqn <em>Template Fqn</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GenTemplateInvocationBaseImpl extends EObjectImpl implements GenTemplateInvocationBase {
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemplateFqn() <em>Template Fqn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateFqn()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_FQN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplateFqn() <em>Template Fqn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateFqn()
	 * @generated
	 * @ordered
	 */
	protected String templateFqn = TEMPLATE_FQN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenTemplateInvocationBaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenTemplateInvocationBase();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCustomGeneratorExtension getExtension() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION) return null;
		return (GenCustomGeneratorExtension)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTemplateFqn() {
		return templateFqn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTemplateFqn(String newTemplateFqn) {
		String oldTemplateFqn = templateFqn;
		templateFqn = newTemplateFqn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__TEMPLATE_FQN, oldTemplateFqn, templateFqn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION, msgs);
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION:
				return eBasicSetContainer(null, GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION, msgs);
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_CUSTOM_GENERATOR_EXTENSION__INVOCATIONS, GenCustomGeneratorExtension.class, msgs);
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION:
				return getExtension();
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__DESCRIPTION:
				return getDescription();
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__TEMPLATE_FQN:
				return getTemplateFqn();
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__TEMPLATE_FQN:
				setTemplateFqn((String)newValue);
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__TEMPLATE_FQN:
				setTemplateFqn(TEMPLATE_FQN_EDEFAULT);
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
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__EXTENSION:
				return getExtension() != null;
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case GMFGenPackage.GEN_TEMPLATE_INVOCATION_BASE__TEMPLATE_FQN:
				return TEMPLATE_FQN_EDEFAULT == null ? templateFqn != null : !TEMPLATE_FQN_EDEFAULT.equals(templateFqn);
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
		result.append(" (description: ");
		result.append(description);
		result.append(", templateFqn: ");
		result.append(templateFqn);
		result.append(')');
		return result.toString();
	}

} //GenTemplateInvocationBaseImpl
