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

import org.eclipse.eef.EefPackage;

import org.eclipse.eef.ext.widgets.reference.eefextwidgetsreference.EefExtWidgetsReferencePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFLanguageExpressionDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFProfileApplicationDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsFactory;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EefAdvancedControlsPackageImpl extends EPackageImpl implements EefAdvancedControlsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefExtEditableReferenceDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefContainerBorderDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefLanguageExpressionDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefProfileApplicationDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefStereotypeApplicationDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eefInputContentPapyrusReferenceDescriptionEClass = null;

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
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EefAdvancedControlsPackageImpl() {
		super(eNS_URI, EefAdvancedControlsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EefAdvancedControlsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EefAdvancedControlsPackage init() {
		if (isInited) return (EefAdvancedControlsPackage)EPackage.Registry.INSTANCE.getEPackage(EefAdvancedControlsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEefAdvancedControlsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EefAdvancedControlsPackageImpl theEefAdvancedControlsPackage = registeredEefAdvancedControlsPackage instanceof EefAdvancedControlsPackageImpl ? (EefAdvancedControlsPackageImpl)registeredEefAdvancedControlsPackage : new EefAdvancedControlsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		EefPackage.eINSTANCE.eClass();
		EefExtWidgetsReferencePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEefAdvancedControlsPackage.createPackageContents();

		// Initialize created meta-data
		theEefAdvancedControlsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEefAdvancedControlsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EefAdvancedControlsPackage.eNS_URI, theEefAdvancedControlsPackage);
		return theEefAdvancedControlsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFExtEditableReferenceDescription() {
		return eefExtEditableReferenceDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFExtEditableReferenceDescription_RemoveExpression() {
		return (EAttribute)eefExtEditableReferenceDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFExtEditableReferenceDescription_CreateExpression() {
		return (EAttribute)eefExtEditableReferenceDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFExtEditableReferenceDescription_BrowseExpression() {
		return (EAttribute)eefExtEditableReferenceDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFContainerBorderDescription() {
		return eefContainerBorderDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFContainerBorderDescription_LabelExpression() {
		return (EAttribute)eefContainerBorderDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFLanguageExpressionDescription() {
		return eefLanguageExpressionDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFProfileApplicationDescription() {
		return eefProfileApplicationDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFStereotypeApplicationDescription() {
		return eefStereotypeApplicationDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFStereotypeApplicationDescription_LabelExpression() {
		return (EAttribute)eefStereotypeApplicationDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFStereotypeApplicationDescription_HelpExpression() {
		return (EAttribute)eefStereotypeApplicationDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEEFInputContentPapyrusReferenceDescription() {
		return eefInputContentPapyrusReferenceDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEEFInputContentPapyrusReferenceDescription_InputContentExpression() {
		return (EAttribute)eefInputContentPapyrusReferenceDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EefAdvancedControlsFactory getEefAdvancedControlsFactory() {
		return (EefAdvancedControlsFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		eefExtEditableReferenceDescriptionEClass = createEClass(EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION);
		createEAttribute(eefExtEditableReferenceDescriptionEClass, EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION);
		createEAttribute(eefExtEditableReferenceDescriptionEClass, EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION);
		createEAttribute(eefExtEditableReferenceDescriptionEClass, EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION);

		eefContainerBorderDescriptionEClass = createEClass(EEF_CONTAINER_BORDER_DESCRIPTION);
		createEAttribute(eefContainerBorderDescriptionEClass, EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION);

		eefLanguageExpressionDescriptionEClass = createEClass(EEF_LANGUAGE_EXPRESSION_DESCRIPTION);

		eefProfileApplicationDescriptionEClass = createEClass(EEF_PROFILE_APPLICATION_DESCRIPTION);

		eefStereotypeApplicationDescriptionEClass = createEClass(EEF_STEREOTYPE_APPLICATION_DESCRIPTION);
		createEAttribute(eefStereotypeApplicationDescriptionEClass, EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION);
		createEAttribute(eefStereotypeApplicationDescriptionEClass, EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION);

		eefInputContentPapyrusReferenceDescriptionEClass = createEClass(EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION);
		createEAttribute(eefInputContentPapyrusReferenceDescriptionEClass, EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EefExtWidgetsReferencePackage theEefExtWidgetsReferencePackage = (EefExtWidgetsReferencePackage)EPackage.Registry.INSTANCE.getEPackage(EefExtWidgetsReferencePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		EefPackage theEefPackage = (EefPackage)EPackage.Registry.INSTANCE.getEPackage(EefPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		eefExtEditableReferenceDescriptionEClass.getESuperTypes().add(theEefExtWidgetsReferencePackage.getEEFExtReferenceDescription());
		eefContainerBorderDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFContainerDescription());
		eefContainerBorderDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFControlDescription());
		eefLanguageExpressionDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFWidgetDescription());
		eefProfileApplicationDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFWidgetDescription());
		eefStereotypeApplicationDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFControlDescription());
		eefStereotypeApplicationDescriptionEClass.getESuperTypes().add(theEefPackage.getEEFContainerDescription());
		eefInputContentPapyrusReferenceDescriptionEClass.getESuperTypes().add(this.getEEFExtEditableReferenceDescription());

		// Initialize classes, features, and operations; add parameters
		initEClass(eefExtEditableReferenceDescriptionEClass, EEFExtEditableReferenceDescription.class, "EEFExtEditableReferenceDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEEFExtEditableReferenceDescription_RemoveExpression(), theEcorePackage.getEString(), "removeExpression", null, 0, 1, EEFExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getEEFExtEditableReferenceDescription_CreateExpression(), theEcorePackage.getEString(), "createExpression", null, 0, 1, EEFExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getEEFExtEditableReferenceDescription_BrowseExpression(), theEcorePackage.getEString(), "browseExpression", null, 0, 1, EEFExtEditableReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eefContainerBorderDescriptionEClass, EEFContainerBorderDescription.class, "EEFContainerBorderDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEEFContainerBorderDescription_LabelExpression(), theEcorePackage.getEString(), "labelExpression", null, 0, 1, EEFContainerBorderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eefLanguageExpressionDescriptionEClass, EEFLanguageExpressionDescription.class, "EEFLanguageExpressionDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(eefProfileApplicationDescriptionEClass, EEFProfileApplicationDescription.class, "EEFProfileApplicationDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(eefStereotypeApplicationDescriptionEClass, EEFStereotypeApplicationDescription.class, "EEFStereotypeApplicationDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEEFStereotypeApplicationDescription_LabelExpression(), ecorePackage.getEString(), "labelExpression", null, 0, 1, EEFStereotypeApplicationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getEEFStereotypeApplicationDescription_HelpExpression(), ecorePackage.getEString(), "helpExpression", null, 0, 1, EEFStereotypeApplicationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eefInputContentPapyrusReferenceDescriptionEClass, EEFInputContentPapyrusReferenceDescription.class, "EEFInputContentPapyrusReferenceDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEEFInputContentPapyrusReferenceDescription_InputContentExpression(), theEcorePackage.getEString(), "inputContentExpression", null, 0, 1, EEFInputContentPapyrusReferenceDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //EefAdvancedControlsPackageImpl
