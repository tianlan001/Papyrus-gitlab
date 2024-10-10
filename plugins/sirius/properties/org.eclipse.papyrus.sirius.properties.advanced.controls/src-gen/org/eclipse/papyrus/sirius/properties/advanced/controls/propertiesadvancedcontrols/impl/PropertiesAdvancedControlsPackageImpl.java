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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.LanguageExpressionDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsFactory;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription;

import org.eclipse.sirius.properties.PropertiesPackage;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferencePackage;

import org.eclipse.sirius.viewpoint.ViewpointPackage;

import org.eclipse.sirius.viewpoint.description.DescriptionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesAdvancedControlsPackageImpl extends EPackageImpl implements PropertiesAdvancedControlsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extEditableReferenceDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containerBorderDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageExpressionDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass profileApplicationDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stereotypeApplicationDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputContentPapyrusReferenceDescriptionEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PropertiesAdvancedControlsPackageImpl() {
		super(eNS_URI, PropertiesAdvancedControlsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link PropertiesAdvancedControlsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PropertiesAdvancedControlsPackage init() {
		if (isInited)
			return (PropertiesAdvancedControlsPackage) EPackage.Registry.INSTANCE.getEPackage(PropertiesAdvancedControlsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPropertiesAdvancedControlsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PropertiesAdvancedControlsPackageImpl thePropertiesAdvancedControlsPackage = registeredPropertiesAdvancedControlsPackage instanceof PropertiesAdvancedControlsPackageImpl
				? (PropertiesAdvancedControlsPackageImpl) registeredPropertiesAdvancedControlsPackage
				: new PropertiesAdvancedControlsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		PropertiesPackage.eINSTANCE.eClass();
		PropertiesExtWidgetsReferencePackage.eINSTANCE.eClass();
		ViewpointPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePropertiesAdvancedControlsPackage.createPackageContents();

		// Initialize created meta-data
		thePropertiesAdvancedControlsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePropertiesAdvancedControlsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PropertiesAdvancedControlsPackage.eNS_URI, thePropertiesAdvancedControlsPackage);
		return thePropertiesAdvancedControlsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExtEditableReferenceDescription() {
		return extEditableReferenceDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExtEditableReferenceDescription_RemoveExpression() {
		return (EAttribute) extEditableReferenceDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExtEditableReferenceDescription_CreateExpression() {
		return (EAttribute) extEditableReferenceDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExtEditableReferenceDescription_BrowseExpression() {
		return (EAttribute) extEditableReferenceDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContainerBorderDescription() {
		return containerBorderDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContainerBorderDescription_LabelExpression() {
		return (EAttribute) containerBorderDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLanguageExpressionDescription() {
		return languageExpressionDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProfileApplicationDescription() {
		return profileApplicationDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStereotypeApplicationDescription() {
		return stereotypeApplicationDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypeApplicationDescription_LabelExpression() {
		return (EAttribute) stereotypeApplicationDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypeApplicationDescription_HelpExpression() {
		return (EAttribute) stereotypeApplicationDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInputContentPapyrusReferenceDescription() {
		return inputContentPapyrusReferenceDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInputContentPapyrusReferenceDescription_InputContentExpression() {
		return (EAttribute) inputContentPapyrusReferenceDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PropertiesAdvancedControlsFactory getPropertiesAdvancedControlsFactory() {
		return (PropertiesAdvancedControlsFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		extEditableReferenceDescriptionEClass = createEClass(EXT_EDITABLE_REFERENCE_DESCRIPTION);
		createEAttribute(extEditableReferenceDescriptionEClass, EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION);
		createEAttribute(extEditableReferenceDescriptionEClass, EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION);
		createEAttribute(extEditableReferenceDescriptionEClass, EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION);

		containerBorderDescriptionEClass = createEClass(CONTAINER_BORDER_DESCRIPTION);
		createEAttribute(containerBorderDescriptionEClass, CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION);

		languageExpressionDescriptionEClass = createEClass(LANGUAGE_EXPRESSION_DESCRIPTION);

		profileApplicationDescriptionEClass = createEClass(PROFILE_APPLICATION_DESCRIPTION);

		stereotypeApplicationDescriptionEClass = createEClass(STEREOTYPE_APPLICATION_DESCRIPTION);
		createEAttribute(stereotypeApplicationDescriptionEClass, STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION);
		createEAttribute(stereotypeApplicationDescriptionEClass, STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION);

		inputContentPapyrusReferenceDescriptionEClass = createEClass(INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION);
		createEAttribute(inputContentPapyrusReferenceDescriptionEClass, INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		PropertiesExtWidgetsReferencePackage thePropertiesExtWidgetsReferencePackage = (PropertiesExtWidgetsReferencePackage) EPackage.Registry.INSTANCE.getEPackage(PropertiesExtWidgetsReferencePackage.eNS_URI);
		DescriptionPackage theDescriptionPackage = (DescriptionPackage) EPackage.Registry.INSTANCE.getEPackage(DescriptionPackage.eNS_URI);
		PropertiesPackage thePropertiesPackage = (PropertiesPackage) EPackage.Registry.INSTANCE.getEPackage(PropertiesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		extEditableReferenceDescriptionEClass.getESuperTypes().add(thePropertiesExtWidgetsReferencePackage.getExtReferenceDescription());
		containerBorderDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getContainerDescription());
		languageExpressionDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getWidgetDescription());
		languageExpressionDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getAbstractWidgetDescription());
		profileApplicationDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getWidgetDescription());
		profileApplicationDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getAbstractWidgetDescription());
		stereotypeApplicationDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getWidgetDescription());
		stereotypeApplicationDescriptionEClass.getESuperTypes().add(thePropertiesPackage.getContainerDescription());
		inputContentPapyrusReferenceDescriptionEClass.getESuperTypes().add(this.getExtEditableReferenceDescription());

		// Initialize classes, features, and operations; add parameters
		initEClass(extEditableReferenceDescriptionEClass, ExtEditableReferenceDescription.class, "ExtEditableReferenceDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getExtEditableReferenceDescription_RemoveExpression(), theDescriptionPackage.getInterpretedExpression(), "removeExpression", null, 0, 1, ExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtEditableReferenceDescription_CreateExpression(), theDescriptionPackage.getInterpretedExpression(), "createExpression", null, 0, 1, ExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtEditableReferenceDescription_BrowseExpression(), theDescriptionPackage.getInterpretedExpression(), "browseExpression", null, 0, 1, ExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containerBorderDescriptionEClass, ContainerBorderDescription.class, "ContainerBorderDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getContainerBorderDescription_LabelExpression(), theDescriptionPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, ContainerBorderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(languageExpressionDescriptionEClass, LanguageExpressionDescription.class, "LanguageExpressionDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(profileApplicationDescriptionEClass, ProfileApplicationDescription.class, "ProfileApplicationDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stereotypeApplicationDescriptionEClass, StereotypeApplicationDescription.class, "StereotypeApplicationDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getStereotypeApplicationDescription_LabelExpression(), theDescriptionPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, StereotypeApplicationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypeApplicationDescription_HelpExpression(), theDescriptionPackage.getInterpretedExpression(), "helpExpression", null, 0, 1, StereotypeApplicationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inputContentPapyrusReferenceDescriptionEClass, InputContentPapyrusReferenceDescription.class, "InputContentPapyrusReferenceDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getInputContentPapyrusReferenceDescription_InputContentExpression(), theDescriptionPackage.getInterpretedExpression(), "inputContentExpression", null, 0, 1, InputContentPapyrusReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PropertiesAdvancedControlsPackageImpl
