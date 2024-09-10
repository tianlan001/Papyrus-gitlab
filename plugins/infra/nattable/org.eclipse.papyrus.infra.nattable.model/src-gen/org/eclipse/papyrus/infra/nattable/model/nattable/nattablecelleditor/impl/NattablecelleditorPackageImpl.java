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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;

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

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;

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

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperPackage;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NattablecelleditorPackageImpl extends EPackageImpl implements NattablecelleditorPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iCellEditorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericRelationshipMatrixCellEditorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iMatrixCellEditorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum matrixRelationShipDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum matrixRelationShipOwnerStrategyEEnum = null;

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
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NattablecelleditorPackageImpl() {
		super(eNS_URI, NattablecelleditorFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NattablecelleditorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NattablecelleditorPackage init() {
		if (isInited) return (NattablecelleditorPackage)EPackage.Registry.INSTANCE.getEPackage(NattablecelleditorPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredNattablecelleditorPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		NattablecelleditorPackageImpl theNattablecelleditorPackage = registeredNattablecelleditorPackage instanceof NattablecelleditorPackageImpl ? (NattablecelleditorPackageImpl)registeredNattablecelleditorPackage : new NattablecelleditorPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablewrapperPackage.eNS_URI);
		NattablewrapperPackageImpl theNattablewrapperPackage = (NattablewrapperPackageImpl)(registeredPackage instanceof NattablewrapperPackageImpl ? registeredPackage : NattablewrapperPackage.eINSTANCE);

		// Create package meta-data objects
		theNattablecelleditorPackage.createPackageContents();
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
		theNattablewrapperPackage.createPackageContents();

		// Initialize created meta-data
		theNattablecelleditorPackage.initializePackageContents();
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
		theNattablewrapperPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNattablecelleditorPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NattablecelleditorPackage.eNS_URI, theNattablecelleditorPackage);
		return theNattablecelleditorPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getICellEditorConfiguration() {
		return iCellEditorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getICellEditorConfiguration_CellEditorId() {
		return (EAttribute)iCellEditorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericRelationshipMatrixCellEditorConfiguration() {
		return genericRelationshipMatrixCellEditorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericRelationshipMatrixCellEditorConfiguration_Direction() {
		return (EAttribute)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter() {
		return (EReference)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericRelationshipMatrixCellEditorConfiguration_EditedElement() {
		return (EReference)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy() {
		return (EAttribute)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner() {
		return (EReference)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerFeature() {
		return (EReference)genericRelationshipMatrixCellEditorConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIMatrixCellEditorConfiguration() {
		return iMatrixCellEditorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMatrixRelationShipDirection() {
		return matrixRelationShipDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMatrixRelationShipOwnerStrategy() {
		return matrixRelationShipOwnerStrategyEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NattablecelleditorFactory getNattablecelleditorFactory() {
		return (NattablecelleditorFactory)getEFactoryInstance();
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
		iCellEditorConfigurationEClass = createEClass(ICELL_EDITOR_CONFIGURATION);
		createEAttribute(iCellEditorConfigurationEClass, ICELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID);

		genericRelationshipMatrixCellEditorConfigurationEClass = createEClass(GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION);
		createEAttribute(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION);
		createEReference(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER);
		createEReference(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT);
		createEAttribute(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY);
		createEReference(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER);
		createEReference(genericRelationshipMatrixCellEditorConfigurationEClass, GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE);

		iMatrixCellEditorConfigurationEClass = createEClass(IMATRIX_CELL_EDITOR_CONFIGURATION);

		// Create enums
		matrixRelationShipDirectionEEnum = createEEnum(MATRIX_RELATION_SHIP_DIRECTION);
		matrixRelationShipOwnerStrategyEEnum = createEEnum(MATRIX_RELATION_SHIP_OWNER_STRATEGY);
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
		BooleanExpressionsPackage theBooleanExpressionsPackage = (BooleanExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(BooleanExpressionsPackage.eNS_URI);
		ElementTypesConfigurationsPackage theElementTypesConfigurationsPackage = (ElementTypesConfigurationsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementTypesConfigurationsPackage.eNS_URI);
		NattablewrapperPackage theNattablewrapperPackage = (NattablewrapperPackage)EPackage.Registry.INSTANCE.getEPackage(NattablewrapperPackage.eNS_URI);
		NattableaxisPackage theNattableaxisPackage = (NattableaxisPackage)EPackage.Registry.INSTANCE.getEPackage(NattableaxisPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iCellEditorConfigurationEClass.getESuperTypes().add(theNattablestylePackage.getStyledElement());
		genericRelationshipMatrixCellEditorConfigurationEClass.getESuperTypes().add(this.getIMatrixCellEditorConfiguration());
		iMatrixCellEditorConfigurationEClass.getESuperTypes().add(this.getICellEditorConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(iCellEditorConfigurationEClass, ICellEditorConfiguration.class, "ICellEditorConfiguration", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getICellEditorConfiguration_CellEditorId(), ecorePackage.getEString(), "cellEditorId", null, 1, 1, ICellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(genericRelationshipMatrixCellEditorConfigurationEClass, GenericRelationshipMatrixCellEditorConfiguration.class, "GenericRelationshipMatrixCellEditorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGenericRelationshipMatrixCellEditorConfiguration_Direction(), this.getMatrixRelationShipDirection(), "direction", "FROM_ROW_TO_COLUMN", 0, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter(), theBooleanExpressionsPackage.getIBooleanEObjectExpression(), null, "cellContentsFilter", null, 0, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGenericRelationshipMatrixCellEditorConfiguration_EditedElement(), theElementTypesConfigurationsPackage.getElementTypeConfiguration(), null, "editedElement", null, 0, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy(), this.getMatrixRelationShipOwnerStrategy(), "relationshipOwnerStrategy", "DEFAULT", 1, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner(), theNattablewrapperPackage.getIWrapper(), null, "relationshipOwner", null, 0, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerFeature(), theNattableaxisPackage.getIAxis(), null, "relationshipOwnerFeature", null, 0, 1, GenericRelationshipMatrixCellEditorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(iMatrixCellEditorConfigurationEClass, IMatrixCellEditorConfiguration.class, "IMatrixCellEditorConfiguration", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(matrixRelationShipDirectionEEnum, MatrixRelationShipDirection.class, "MatrixRelationShipDirection"); //$NON-NLS-1$
		addEEnumLiteral(matrixRelationShipDirectionEEnum, MatrixRelationShipDirection.NONE);
		addEEnumLiteral(matrixRelationShipDirectionEEnum, MatrixRelationShipDirection.FROM_ROW_TO_COLUMN);
		addEEnumLiteral(matrixRelationShipDirectionEEnum, MatrixRelationShipDirection.FROM_COLUMN_TO_ROW);

		initEEnum(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.class, "MatrixRelationShipOwnerStrategy"); //$NON-NLS-1$
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.DEFAULT);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.TABLE_CONTEXT);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.ROW_OWNER);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.ROW_AS_OWNER);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.COLUMN_OWNER);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.COLUMN_AS_OWNER);
		addEEnumLiteral(matrixRelationShipOwnerStrategyEEnum, MatrixRelationShipOwnerStrategy.OTHER);
	}

} //NattablecelleditorPackageImpl
