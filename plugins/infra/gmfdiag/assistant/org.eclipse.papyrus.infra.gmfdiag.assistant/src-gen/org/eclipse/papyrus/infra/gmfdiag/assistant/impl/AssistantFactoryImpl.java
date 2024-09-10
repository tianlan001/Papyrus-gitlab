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
package org.eclipse.papyrus.infra.gmfdiag.assistant.impl;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;

import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.papyrus.infra.gmfdiag.assistant.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AssistantFactoryImpl extends EFactoryImpl implements AssistantFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static AssistantFactory init() {
		try {
			AssistantFactory theAssistantFactory = (AssistantFactory) EPackage.Registry.INSTANCE.getEFactory(AssistantPackage.eNS_URI);
			if (theAssistantFactory != null) {
				return theAssistantFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AssistantFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AssistantFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case AssistantPackage.ASSISTED_ELEMENT_TYPE_FILTER:
			return createAssistedElementTypeFilter();
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER:
			return createModelingAssistantProvider();
		case AssistantPackage.POPUP_ASSISTANT:
			return createPopupAssistant();
		case AssistantPackage.CONNECTION_ASSISTANT:
			return createConnectionAssistant();
		case AssistantPackage.ELEMENT_TYPE_FILTER:
			return createElementTypeFilter();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case AssistantPackage.ELEMENT_TYPE:
			return createElementTypeFromString(eDataType, initialValue);
		case AssistantPackage.CLIENT_CONTEXT:
			return createClientContextFromString(eDataType, initialValue);
		case AssistantPackage.IPROVIDER_CHANGE_LISTENER:
			return createIProviderChangeListenerFromString(eDataType, initialValue);
		case AssistantPackage.IOPERATION:
			return createIOperationFromString(eDataType, initialValue);
		case AssistantPackage.IADAPTABLE:
			return createIAdaptableFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case AssistantPackage.ELEMENT_TYPE:
			return convertElementTypeToString(eDataType, instanceValue);
		case AssistantPackage.CLIENT_CONTEXT:
			return convertClientContextToString(eDataType, instanceValue);
		case AssistantPackage.IPROVIDER_CHANGE_LISTENER:
			return convertIProviderChangeListenerToString(eDataType, instanceValue);
		case AssistantPackage.IOPERATION:
			return convertIOperationToString(eDataType, instanceValue);
		case AssistantPackage.IADAPTABLE:
			return convertIAdaptableToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AssistedElementTypeFilter createAssistedElementTypeFilter() {
		AssistedElementTypeFilterImpl assistedElementTypeFilter = new AssistedElementTypeFilterImpl();
		return assistedElementTypeFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelingAssistantProvider createModelingAssistantProvider() {
		ModelingAssistantProviderImpl modelingAssistantProvider = new ModelingAssistantProviderImpl();
		return modelingAssistantProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConnectionAssistant createConnectionAssistant() {
		ConnectionAssistantImpl connectionAssistant = new ConnectionAssistantImpl();
		return connectionAssistant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PopupAssistant createPopupAssistant() {
		PopupAssistantImpl popupAssistant = new PopupAssistantImpl();
		return popupAssistant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ElementTypeFilter createElementTypeFilter() {
		ElementTypeFilterImpl elementTypeFilter = new ElementTypeFilterImpl();
		return elementTypeFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IElementType createElementTypeFromString(EDataType eDataType, String initialValue) {
		return (IElementType) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertElementTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IClientContext createClientContextFromString(EDataType eDataType, String initialValue) {
		return (IClientContext) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertClientContextToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IOperation createIOperationFromString(EDataType eDataType, String initialValue) {
		return (IOperation) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertIOperationToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IProviderChangeListener createIProviderChangeListenerFromString(EDataType eDataType, String initialValue) {
		return (IProviderChangeListener) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertIProviderChangeListenerToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IAdaptable createIAdaptableFromString(EDataType eDataType, String initialValue) {
		return (IAdaptable) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertIAdaptableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AssistantPackage getAssistantPackage() {
		return (AssistantPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AssistantPackage getPackage() {
		return AssistantPackage.eINSTANCE;
	}

} // AssistantFactoryImpl
