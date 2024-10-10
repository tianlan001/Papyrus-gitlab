/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesAdvancedControlsFactoryImpl extends EFactoryImpl implements PropertiesAdvancedControlsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertiesAdvancedControlsFactory init() {
		try {
			PropertiesAdvancedControlsFactory thePropertiesAdvancedControlsFactory = (PropertiesAdvancedControlsFactory) EPackage.Registry.INSTANCE.getEFactory(PropertiesAdvancedControlsPackage.eNS_URI);
			if (thePropertiesAdvancedControlsFactory != null) {
				return thePropertiesAdvancedControlsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PropertiesAdvancedControlsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesAdvancedControlsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION:
			return createExtEditableReferenceDescription();
		case PropertiesAdvancedControlsPackage.CONTAINER_BORDER_DESCRIPTION:
			return createContainerBorderDescription();
		case PropertiesAdvancedControlsPackage.LANGUAGE_EXPRESSION_DESCRIPTION:
			return createLanguageExpressionDescription();
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION:
			return createProfileApplicationDescription();
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION:
			return createStereotypeApplicationDescription();
		case PropertiesAdvancedControlsPackage.INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION:
			return createInputContentPapyrusReferenceDescription();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtEditableReferenceDescription createExtEditableReferenceDescription() {
		ExtEditableReferenceDescriptionImpl extEditableReferenceDescription = new ExtEditableReferenceDescriptionImpl();
		return extEditableReferenceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContainerBorderDescription createContainerBorderDescription() {
		ContainerBorderDescriptionImpl containerBorderDescription = new ContainerBorderDescriptionImpl();
		return containerBorderDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageExpressionDescription createLanguageExpressionDescription() {
		LanguageExpressionDescriptionImpl languageExpressionDescription = new LanguageExpressionDescriptionImpl();
		return languageExpressionDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProfileApplicationDescription createProfileApplicationDescription() {
		ProfileApplicationDescriptionImpl profileApplicationDescription = new ProfileApplicationDescriptionImpl();
		return profileApplicationDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StereotypeApplicationDescription createStereotypeApplicationDescription() {
		StereotypeApplicationDescriptionImpl stereotypeApplicationDescription = new StereotypeApplicationDescriptionImpl();
		return stereotypeApplicationDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InputContentPapyrusReferenceDescription createInputContentPapyrusReferenceDescription() {
		InputContentPapyrusReferenceDescriptionImpl inputContentPapyrusReferenceDescription = new InputContentPapyrusReferenceDescriptionImpl();
		return inputContentPapyrusReferenceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PropertiesAdvancedControlsPackage getPropertiesAdvancedControlsPackage() {
		return (PropertiesAdvancedControlsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PropertiesAdvancedControlsPackage getPackage() {
		return PropertiesAdvancedControlsPackage.eINSTANCE;
	}

} //PropertiesAdvancedControlsFactoryImpl
