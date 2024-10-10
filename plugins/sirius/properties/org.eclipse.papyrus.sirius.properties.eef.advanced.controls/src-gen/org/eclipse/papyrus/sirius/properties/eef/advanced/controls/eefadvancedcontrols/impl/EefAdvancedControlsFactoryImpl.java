/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EefAdvancedControlsFactoryImpl extends EFactoryImpl implements EefAdvancedControlsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EefAdvancedControlsFactory init() {
		try {
			EefAdvancedControlsFactory theEefAdvancedControlsFactory = (EefAdvancedControlsFactory)EPackage.Registry.INSTANCE.getEFactory(EefAdvancedControlsPackage.eNS_URI);
			if (theEefAdvancedControlsFactory != null) {
				return theEefAdvancedControlsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EefAdvancedControlsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EefAdvancedControlsFactoryImpl() {
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
			case EefAdvancedControlsPackage.EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION: return createEEFExtEditableReferenceDescription();
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION: return createEEFContainerBorderDescription();
			case EefAdvancedControlsPackage.EEF_LANGUAGE_EXPRESSION_DESCRIPTION: return createEEFLanguageExpressionDescription();
			case EefAdvancedControlsPackage.EEF_PROFILE_APPLICATION_DESCRIPTION: return createEEFProfileApplicationDescription();
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION: return createEEFStereotypeApplicationDescription();
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION: return createEEFInputContentPapyrusReferenceDescription();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFExtEditableReferenceDescription createEEFExtEditableReferenceDescription() {
		EEFExtEditableReferenceDescriptionImpl eefExtEditableReferenceDescription = new EEFExtEditableReferenceDescriptionImpl();
		return eefExtEditableReferenceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFContainerBorderDescription createEEFContainerBorderDescription() {
		EEFContainerBorderDescriptionImpl eefContainerBorderDescription = new EEFContainerBorderDescriptionImpl();
		return eefContainerBorderDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFLanguageExpressionDescription createEEFLanguageExpressionDescription() {
		EEFLanguageExpressionDescriptionImpl eefLanguageExpressionDescription = new EEFLanguageExpressionDescriptionImpl();
		return eefLanguageExpressionDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFProfileApplicationDescription createEEFProfileApplicationDescription() {
		EEFProfileApplicationDescriptionImpl eefProfileApplicationDescription = new EEFProfileApplicationDescriptionImpl();
		return eefProfileApplicationDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFStereotypeApplicationDescription createEEFStereotypeApplicationDescription() {
		EEFStereotypeApplicationDescriptionImpl eefStereotypeApplicationDescription = new EEFStereotypeApplicationDescriptionImpl();
		return eefStereotypeApplicationDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEFInputContentPapyrusReferenceDescription createEEFInputContentPapyrusReferenceDescription() {
		EEFInputContentPapyrusReferenceDescriptionImpl eefInputContentPapyrusReferenceDescription = new EEFInputContentPapyrusReferenceDescriptionImpl();
		return eefInputContentPapyrusReferenceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EefAdvancedControlsPackage getEefAdvancedControlsPackage() {
		return (EefAdvancedControlsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EefAdvancedControlsPackage getPackage() {
		return EefAdvancedControlsPackage.eINSTANCE;
	}

} //EefAdvancedControlsFactoryImpl
