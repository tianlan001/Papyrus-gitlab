/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
package org.eclipse.papyrus.infra.gmfdiag.assistant.util;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;

/**
 * <!-- begin-user-doc -->
 * An adapter that propagates notifications for derived unions.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage
 * @generated
 */
public class AssistantDerivedUnionAdapter extends AdapterImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static AssistantPackage modelPackage;

	/**
	 * Creates an instance of the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AssistantDerivedUnionAdapter() {
		if (modelPackage == null) {
			modelPackage = AssistantPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> with the appropriate model class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if (notifier instanceof EObject) {
			EClass eClass = ((EObject) notifier).eClass();
			if (eClass.eContainer() == modelPackage) {
				notifyChanged(notification, eClass);
			}
		}
	}

	/**
	 * Calls <code>notifyXXXChanged</code> for the corresponding class of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyChanged(Notification notification, EClass eClass) {
		switch (eClass.getClassifierID()) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER:
			notifyModelingAssistantProviderChanged(notification, eClass);
			break;
		case AssistantPackage.POPUP_ASSISTANT:
			notifyPopupAssistantChanged(notification, eClass);
			break;
		case AssistantPackage.CONNECTION_ASSISTANT:
			notifyConnectionAssistantChanged(notification, eClass);
			break;
		}
	}

	/**
	 * Does nothing; clients may override so that it does something.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @param derivedUnion
	 *            the derived union affected by the change.
	 * @generated
	 */
	public void notifyChanged(Notification notification, EClass eClass, EStructuralFeature derivedUnion) {
		// Do nothing.
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyConnectionAssistantChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ConnectionAssistant.class)) {
		case AssistantPackage.CONNECTION_ASSISTANT__OWNING_PROVIDER:
			notifyChanged(notification, eClass, AssistantPackage.Literals.ASSISTANT__PROVIDER);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyModelingAssistantProviderChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ModelingAssistantProvider.class)) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
			notifyChanged(notification, eClass, AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ASSISTANT);
			break;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
			notifyChanged(notification, eClass, AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ASSISTANT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyPopupAssistantChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(PopupAssistant.class)) {
		case AssistantPackage.POPUP_ASSISTANT__OWNING_PROVIDER:
			notifyChanged(notification, eClass, AssistantPackage.Literals.ASSISTANT__PROVIDER);
			break;
		}
	}

} // AssistantDerivedUnionAdapter
