/**
 * Copyright (c) 2013 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.impl.NattablePackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.NattableaxisPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.impl.NattableaxisconfigurationPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.impl.NattableaxisproviderPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.NattablecellPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.impl.NattablecellPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.NattableconfigurationPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.impl.NattableconfigurationPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.NattablelabelproviderPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.NattableproblemPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.impl.NattableproblemPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl.NattablestylePackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.NattabletesterPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.impl.NattabletesterPackageImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IdWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperPackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NattablewrapperPackageImpl extends EPackageImpl implements NattablewrapperPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idWrapperEClass = null;

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
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NattablewrapperPackageImpl() {
		super(eNS_URI, NattablewrapperFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NattablewrapperPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NattablewrapperPackage init() {
		if (isInited) return (NattablewrapperPackage)EPackage.Registry.INSTANCE.getEPackage(NattablewrapperPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredNattablewrapperPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		NattablewrapperPackageImpl theNattablewrapperPackage = registeredNattablewrapperPackage instanceof NattablewrapperPackageImpl ? (NattablewrapperPackageImpl)registeredNattablewrapperPackage : new NattablewrapperPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		ExpressionsPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablePackage.eNS_URI);
		NattablePackageImpl theNattablePackage = (NattablePackageImpl)(registeredPackage instanceof NattablePackageImpl ? registeredPackage : NattablePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableconfigurationPackage.eNS_URI);
		NattableconfigurationPackageImpl theNattableconfigurationPackage = (NattableconfigurationPackageImpl)(registeredPackage instanceof NattableconfigurationPackageImpl ? registeredPackage : NattableconfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableaxisproviderPackage.eNS_URI);
		NattableaxisproviderPackageImpl theNattableaxisproviderPackage = (NattableaxisproviderPackageImpl)(registeredPackage instanceof NattableaxisproviderPackageImpl ? registeredPackage : NattableaxisproviderPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablelabelproviderPackage.eNS_URI);
		NattablelabelproviderPackageImpl theNattablelabelproviderPackage = (NattablelabelproviderPackageImpl)(registeredPackage instanceof NattablelabelproviderPackageImpl ? registeredPackage : NattablelabelproviderPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableaxisconfigurationPackage.eNS_URI);
		NattableaxisconfigurationPackageImpl theNattableaxisconfigurationPackage = (NattableaxisconfigurationPackageImpl)(registeredPackage instanceof NattableaxisconfigurationPackageImpl ? registeredPackage : NattableaxisconfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattabletesterPackage.eNS_URI);
		NattabletesterPackageImpl theNattabletesterPackage = (NattabletesterPackageImpl)(registeredPackage instanceof NattabletesterPackageImpl ? registeredPackage : NattabletesterPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableaxisPackage.eNS_URI);
		NattableaxisPackageImpl theNattableaxisPackage = (NattableaxisPackageImpl)(registeredPackage instanceof NattableaxisPackageImpl ? registeredPackage : NattableaxisPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablecellPackage.eNS_URI);
		NattablecellPackageImpl theNattablecellPackage = (NattablecellPackageImpl)(registeredPackage instanceof NattablecellPackageImpl ? registeredPackage : NattablecellPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableproblemPackage.eNS_URI);
		NattableproblemPackageImpl theNattableproblemPackage = (NattableproblemPackageImpl)(registeredPackage instanceof NattableproblemPackageImpl ? registeredPackage : NattableproblemPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablestylePackage.eNS_URI);
		NattablestylePackageImpl theNattablestylePackage = (NattablestylePackageImpl)(registeredPackage instanceof NattablestylePackageImpl ? registeredPackage : NattablestylePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablecelleditorPackage.eNS_URI);
		NattablecelleditorPackageImpl theNattablecelleditorPackage = (NattablecelleditorPackageImpl)(registeredPackage instanceof NattablecelleditorPackageImpl ? registeredPackage : NattablecelleditorPackage.eINSTANCE);

		// Create package meta-data objects
		theNattablewrapperPackage.createPackageContents();
		theNattablePackage.createPackageContents();
		theNattableconfigurationPackage.createPackageContents();
		theNattableaxisproviderPackage.createPackageContents();
		theNattablelabelproviderPackage.createPackageContents();
		theNattableaxisconfigurationPackage.createPackageContents();
		theNattabletesterPackage.createPackageContents();
		theNattableaxisPackage.createPackageContents();
		theNattablecellPackage.createPackageContents();
		theNattableproblemPackage.createPackageContents();
		theNattablestylePackage.createPackageContents();
		theNattablecelleditorPackage.createPackageContents();

		// Initialize created meta-data
		theNattablewrapperPackage.initializePackageContents();
		theNattablePackage.initializePackageContents();
		theNattableconfigurationPackage.initializePackageContents();
		theNattableaxisproviderPackage.initializePackageContents();
		theNattablelabelproviderPackage.initializePackageContents();
		theNattableaxisconfigurationPackage.initializePackageContents();
		theNattabletesterPackage.initializePackageContents();
		theNattableaxisPackage.initializePackageContents();
		theNattablecellPackage.initializePackageContents();
		theNattableproblemPackage.initializePackageContents();
		theNattablestylePackage.initializePackageContents();
		theNattablecelleditorPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNattablewrapperPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NattablewrapperPackage.eNS_URI, theNattablewrapperPackage);
		return theNattablewrapperPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIWrapper() {
		return iWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getIWrapper__GetElement() {
		return iWrapperEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectWrapper() {
		return eObjectWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectWrapper_Element() {
		return (EReference)eObjectWrapperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdWrapper() {
		return idWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdWrapper_Element() {
		return (EAttribute)idWrapperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NattablewrapperFactory getNattablewrapperFactory() {
		return (NattablewrapperFactory)getEFactoryInstance();
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
		iWrapperEClass = createEClass(IWRAPPER);
		createEOperation(iWrapperEClass, IWRAPPER___GET_ELEMENT);

		eObjectWrapperEClass = createEClass(EOBJECT_WRAPPER);
		createEReference(eObjectWrapperEClass, EOBJECT_WRAPPER__ELEMENT);

		idWrapperEClass = createEClass(ID_WRAPPER);
		createEAttribute(idWrapperEClass, ID_WRAPPER__ELEMENT);
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
		NattablestylePackage theNattablestylePackage = (NattablestylePackage)EPackage.Registry.INSTANCE.getEPackage(NattablestylePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iWrapperEClass.getESuperTypes().add(theNattablestylePackage.getStyledElement());
		eObjectWrapperEClass.getESuperTypes().add(this.getIWrapper());
		idWrapperEClass.getESuperTypes().add(this.getIWrapper());

		// Initialize classes, features, and operations; add parameters
		initEClass(iWrapperEClass, IWrapper.class, "IWrapper", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEOperation(getIWrapper__GetElement(), ecorePackage.getEJavaObject(), "getElement", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(eObjectWrapperEClass, EObjectWrapper.class, "EObjectWrapper", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEObjectWrapper_Element(), ecorePackage.getEObject(), null, "element", null, 1, 1, EObjectWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(idWrapperEClass, IdWrapper.class, "IdWrapper", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIdWrapper_Element(), ecorePackage.getEString(), "element", null, 1, 1, IdWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
	}

} //NattablewrapperPackageImpl
