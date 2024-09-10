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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl;

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
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.AxisGroup;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectIdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationTreeItemAxis;
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
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NattableaxisPackageImpl extends EPackageImpl implements NattableaxisPackage {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureIdAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureIdTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eStructuralFeatureAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eOperationAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eStructuralFeatureTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eOperationTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectIdAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectIdTreeItemAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass axisGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationIdAxisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationIdTreeItemAxisEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NattableaxisPackageImpl() {
		super(eNS_URI, NattableaxisFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NattableaxisPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NattableaxisPackage init() {
		if (isInited) return (NattableaxisPackage)EPackage.Registry.INSTANCE.getEPackage(NattableaxisPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredNattableaxisPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		NattableaxisPackageImpl theNattableaxisPackage = registeredNattableaxisPackage instanceof NattableaxisPackageImpl ? (NattableaxisPackageImpl)registeredNattableaxisPackage : new NattableaxisPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablecellPackage.eNS_URI);
		NattablecellPackageImpl theNattablecellPackage = (NattablecellPackageImpl)(registeredPackage instanceof NattablecellPackageImpl ? registeredPackage : NattablecellPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattableproblemPackage.eNS_URI);
		NattableproblemPackageImpl theNattableproblemPackage = (NattableproblemPackageImpl)(registeredPackage instanceof NattableproblemPackageImpl ? registeredPackage : NattableproblemPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablestylePackage.eNS_URI);
		NattablestylePackageImpl theNattablestylePackage = (NattablestylePackageImpl)(registeredPackage instanceof NattablestylePackageImpl ? registeredPackage : NattablestylePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablecelleditorPackage.eNS_URI);
		NattablecelleditorPackageImpl theNattablecelleditorPackage = (NattablecelleditorPackageImpl)(registeredPackage instanceof NattablecelleditorPackageImpl ? registeredPackage : NattablecelleditorPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NattablewrapperPackage.eNS_URI);
		NattablewrapperPackageImpl theNattablewrapperPackage = (NattablewrapperPackageImpl)(registeredPackage instanceof NattablewrapperPackageImpl ? registeredPackage : NattablewrapperPackage.eINSTANCE);

		// Create package meta-data objects
		theNattableaxisPackage.createPackageContents();
		theNattablePackage.createPackageContents();
		theNattableconfigurationPackage.createPackageContents();
		theNattableaxisproviderPackage.createPackageContents();
		theNattablelabelproviderPackage.createPackageContents();
		theNattableaxisconfigurationPackage.createPackageContents();
		theNattabletesterPackage.createPackageContents();
		theNattablecellPackage.createPackageContents();
		theNattableproblemPackage.createPackageContents();
		theNattablestylePackage.createPackageContents();
		theNattablecelleditorPackage.createPackageContents();
		theNattablewrapperPackage.createPackageContents();

		// Initialize created meta-data
		theNattableaxisPackage.initializePackageContents();
		theNattablePackage.initializePackageContents();
		theNattableconfigurationPackage.initializePackageContents();
		theNattableaxisproviderPackage.initializePackageContents();
		theNattablelabelproviderPackage.initializePackageContents();
		theNattableaxisconfigurationPackage.initializePackageContents();
		theNattabletesterPackage.initializePackageContents();
		theNattablecellPackage.initializePackageContents();
		theNattableproblemPackage.initializePackageContents();
		theNattablestylePackage.initializePackageContents();
		theNattablecelleditorPackage.initializePackageContents();
		theNattablewrapperPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNattableaxisPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NattableaxisPackage.eNS_URI, theNattableaxisPackage);
		return theNattableaxisPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIAxis() {
		return iAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIAxis_Manager() {
		return (EReference)iAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIAxis_Alias() {
		return (EAttribute)iAxisEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIAxis__GetElement() {
		return iAxisEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIAxis__GetLocalLabelConfiguration() {
		return iAxisEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getITreeItemAxis() {
		return iTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getITreeItemAxis_Parent() {
		return (EReference)iTreeItemAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getITreeItemAxis_Expanded() {
		return (EAttribute)iTreeItemAxisEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getITreeItemAxis_Children() {
		return (EReference)iTreeItemAxisEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdAxis() {
		return idAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdAxis_Element() {
		return (EAttribute)idAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdTreeItemAxis() {
		return idTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEObjectAxis() {
		return eObjectAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEObjectAxis_Element() {
		return (EReference)eObjectAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEObjectTreeItemAxis() {
		return eObjectTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureAxis() {
		return featureAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureAxis_LocalLabelConfiguration() {
		return (EReference)featureAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationAxis() {
		return operationAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationAxis_LocalLabelConfiguration() {
		return (EReference)operationAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureTreeItemAxis() {
		return featureTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationTreeItemAxis() {
		return operationTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectAxis() {
		return objectAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObjectAxis_LocalLabelConfiguration() {
		return (EReference)objectAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectTreeItemAxis() {
		return objectTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureIdAxis() {
		return featureIdAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureIdTreeItemAxis() {
		return featureIdTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEStructuralFeatureAxis() {
		return eStructuralFeatureAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEStructuralFeatureAxis_Element() {
		return (EReference)eStructuralFeatureAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEOperationAxis() {
		return eOperationAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEOperationAxis_Element() {
		return (EReference)eOperationAxisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEStructuralFeatureTreeItemAxis() {
		return eStructuralFeatureTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEOperationTreeItemAxis() {
		return eOperationTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectIdAxis() {
		return objectIdAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectIdTreeItemAxis() {
		return objectIdTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAxisGroup() {
		return axisGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAxisGroup_GroupedAxis() {
		return (EReference)axisGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationIdAxis() {
		return operationIdAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationIdTreeItemAxis() {
		return operationIdTreeItemAxisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NattableaxisFactory getNattableaxisFactory() {
		return (NattableaxisFactory)getEFactoryInstance();
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
		iAxisEClass = createEClass(IAXIS);
		createEReference(iAxisEClass, IAXIS__MANAGER);
		createEAttribute(iAxisEClass, IAXIS__ALIAS);
		createEOperation(iAxisEClass, IAXIS___GET_ELEMENT);
		createEOperation(iAxisEClass, IAXIS___GET_LOCAL_LABEL_CONFIGURATION);

		iTreeItemAxisEClass = createEClass(ITREE_ITEM_AXIS);
		createEReference(iTreeItemAxisEClass, ITREE_ITEM_AXIS__PARENT);
		createEAttribute(iTreeItemAxisEClass, ITREE_ITEM_AXIS__EXPANDED);
		createEReference(iTreeItemAxisEClass, ITREE_ITEM_AXIS__CHILDREN);

		idAxisEClass = createEClass(ID_AXIS);
		createEAttribute(idAxisEClass, ID_AXIS__ELEMENT);

		idTreeItemAxisEClass = createEClass(ID_TREE_ITEM_AXIS);

		eObjectAxisEClass = createEClass(EOBJECT_AXIS);
		createEReference(eObjectAxisEClass, EOBJECT_AXIS__ELEMENT);

		eObjectTreeItemAxisEClass = createEClass(EOBJECT_TREE_ITEM_AXIS);

		featureAxisEClass = createEClass(FEATURE_AXIS);
		createEReference(featureAxisEClass, FEATURE_AXIS__LOCAL_LABEL_CONFIGURATION);

		operationAxisEClass = createEClass(OPERATION_AXIS);
		createEReference(operationAxisEClass, OPERATION_AXIS__LOCAL_LABEL_CONFIGURATION);

		featureTreeItemAxisEClass = createEClass(FEATURE_TREE_ITEM_AXIS);

		operationTreeItemAxisEClass = createEClass(OPERATION_TREE_ITEM_AXIS);

		objectAxisEClass = createEClass(OBJECT_AXIS);
		createEReference(objectAxisEClass, OBJECT_AXIS__LOCAL_LABEL_CONFIGURATION);

		objectTreeItemAxisEClass = createEClass(OBJECT_TREE_ITEM_AXIS);

		featureIdAxisEClass = createEClass(FEATURE_ID_AXIS);

		featureIdTreeItemAxisEClass = createEClass(FEATURE_ID_TREE_ITEM_AXIS);

		eStructuralFeatureAxisEClass = createEClass(ESTRUCTURAL_FEATURE_AXIS);
		createEReference(eStructuralFeatureAxisEClass, ESTRUCTURAL_FEATURE_AXIS__ELEMENT);

		eOperationAxisEClass = createEClass(EOPERATION_AXIS);
		createEReference(eOperationAxisEClass, EOPERATION_AXIS__ELEMENT);

		eStructuralFeatureTreeItemAxisEClass = createEClass(ESTRUCTURAL_FEATURE_TREE_ITEM_AXIS);

		eOperationTreeItemAxisEClass = createEClass(EOPERATION_TREE_ITEM_AXIS);

		objectIdAxisEClass = createEClass(OBJECT_ID_AXIS);

		objectIdTreeItemAxisEClass = createEClass(OBJECT_ID_TREE_ITEM_AXIS);

		axisGroupEClass = createEClass(AXIS_GROUP);
		createEReference(axisGroupEClass, AXIS_GROUP__GROUPED_AXIS);

		operationIdAxisEClass = createEClass(OPERATION_ID_AXIS);

		operationIdTreeItemAxisEClass = createEClass(OPERATION_ID_TREE_ITEM_AXIS);
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
		NattableaxisconfigurationPackage theNattableaxisconfigurationPackage = (NattableaxisconfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(NattableaxisconfigurationPackage.eNS_URI);
		NattablelabelproviderPackage theNattablelabelproviderPackage = (NattablelabelproviderPackage)EPackage.Registry.INSTANCE.getEPackage(NattablelabelproviderPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iAxisEClass.getESuperTypes().add(theNattablestylePackage.getStyledElement());
		iTreeItemAxisEClass.getESuperTypes().add(this.getIAxis());
		idAxisEClass.getESuperTypes().add(this.getIAxis());
		idTreeItemAxisEClass.getESuperTypes().add(this.getIdAxis());
		idTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		eObjectAxisEClass.getESuperTypes().add(this.getObjectAxis());
		eObjectTreeItemAxisEClass.getESuperTypes().add(this.getEObjectAxis());
		eObjectTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		featureAxisEClass.getESuperTypes().add(this.getIAxis());
		operationAxisEClass.getESuperTypes().add(this.getIAxis());
		featureTreeItemAxisEClass.getESuperTypes().add(this.getFeatureAxis());
		featureTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		operationTreeItemAxisEClass.getESuperTypes().add(this.getOperationAxis());
		operationTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		objectAxisEClass.getESuperTypes().add(this.getIAxis());
		objectTreeItemAxisEClass.getESuperTypes().add(this.getObjectAxis());
		objectTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		featureIdAxisEClass.getESuperTypes().add(this.getIdAxis());
		featureIdAxisEClass.getESuperTypes().add(this.getFeatureAxis());
		featureIdTreeItemAxisEClass.getESuperTypes().add(this.getFeatureIdAxis());
		featureIdTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		eStructuralFeatureAxisEClass.getESuperTypes().add(this.getFeatureAxis());
		eOperationAxisEClass.getESuperTypes().add(this.getOperationAxis());
		eStructuralFeatureTreeItemAxisEClass.getESuperTypes().add(this.getEStructuralFeatureAxis());
		eStructuralFeatureTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		eOperationTreeItemAxisEClass.getESuperTypes().add(this.getEOperationAxis());
		eOperationTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		objectIdAxisEClass.getESuperTypes().add(this.getIdAxis());
		objectIdAxisEClass.getESuperTypes().add(this.getObjectAxis());
		objectIdTreeItemAxisEClass.getESuperTypes().add(this.getObjectIdAxis());
		objectIdTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		axisGroupEClass.getESuperTypes().add(this.getIAxis());
		operationIdAxisEClass.getESuperTypes().add(this.getIdAxis());
		operationIdAxisEClass.getESuperTypes().add(this.getOperationAxis());
		operationIdTreeItemAxisEClass.getESuperTypes().add(this.getITreeItemAxis());
		operationIdTreeItemAxisEClass.getESuperTypes().add(this.getOperationIdAxis());

		// Initialize classes, features, and operations; add parameters
		initEClass(iAxisEClass, IAxis.class, "IAxis", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getIAxis_Manager(), theNattableaxisconfigurationPackage.getAxisManagerRepresentation(), null, "manager", null, 0, 1, IAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getIAxis_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, IAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEOperation(getIAxis__GetElement(), ecorePackage.getEJavaObject(), "getElement", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEOperation(getIAxis__GetLocalLabelConfiguration(), theNattablelabelproviderPackage.getILabelProviderConfiguration(), "getLocalLabelConfiguration", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iTreeItemAxisEClass, ITreeItemAxis.class, "ITreeItemAxis", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getITreeItemAxis_Parent(), this.getITreeItemAxis(), this.getITreeItemAxis_Children(), "parent", null, 0, 1, ITreeItemAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getITreeItemAxis_Expanded(), ecorePackage.getEBoolean(), "expanded", "false", 1, 1, ITreeItemAxis.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getITreeItemAxis_Children(), this.getITreeItemAxis(), this.getITreeItemAxis_Parent(), "children", null, 0, -1, ITreeItemAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(idAxisEClass, IdAxis.class, "IdAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIdAxis_Element(), ecorePackage.getEString(), "element", null, 1, 1, IdAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(idTreeItemAxisEClass, IdTreeItemAxis.class, "IdTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(eObjectAxisEClass, EObjectAxis.class, "EObjectAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEObjectAxis_Element(), ecorePackage.getEObject(), null, "element", null, 1, 1, EObjectAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eObjectTreeItemAxisEClass, EObjectTreeItemAxis.class, "EObjectTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(featureAxisEClass, FeatureAxis.class, "FeatureAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFeatureAxis_LocalLabelConfiguration(), theNattablelabelproviderPackage.getFeatureLabelProviderConfiguration(), null, "localLabelConfiguration", null, 0, 1, FeatureAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(operationAxisEClass, OperationAxis.class, "OperationAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getOperationAxis_LocalLabelConfiguration(), theNattablelabelproviderPackage.getOperationLabelProviderConfiguration(), null, "localLabelConfiguration", null, 0, 1, OperationAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(featureTreeItemAxisEClass, FeatureTreeItemAxis.class, "FeatureTreeItemAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(operationTreeItemAxisEClass, OperationTreeItemAxis.class, "OperationTreeItemAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(objectAxisEClass, ObjectAxis.class, "ObjectAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getObjectAxis_LocalLabelConfiguration(), theNattablelabelproviderPackage.getObjectLabelProviderConfiguration(), null, "localLabelConfiguration", null, 0, 1, ObjectAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(objectTreeItemAxisEClass, ObjectTreeItemAxis.class, "ObjectTreeItemAxis", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(featureIdAxisEClass, FeatureIdAxis.class, "FeatureIdAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(featureIdTreeItemAxisEClass, FeatureIdTreeItemAxis.class, "FeatureIdTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(eStructuralFeatureAxisEClass, EStructuralFeatureAxis.class, "EStructuralFeatureAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEStructuralFeatureAxis_Element(), ecorePackage.getEStructuralFeature(), null, "element", null, 1, 1, EStructuralFeatureAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eOperationAxisEClass, EOperationAxis.class, "EOperationAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEOperationAxis_Element(), ecorePackage.getEOperation(), null, "element", null, 1, 1, EOperationAxis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eStructuralFeatureTreeItemAxisEClass, EStructuralFeatureTreeItemAxis.class, "EStructuralFeatureTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(eOperationTreeItemAxisEClass, EOperationTreeItemAxis.class, "EOperationTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(objectIdAxisEClass, ObjectIdAxis.class, "ObjectIdAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(objectIdTreeItemAxisEClass, ObjectIdTreeItemAxis.class, "ObjectIdTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(axisGroupEClass, AxisGroup.class, "AxisGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAxisGroup_GroupedAxis(), this.getIAxis(), null, "groupedAxis", null, 0, -1, AxisGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(operationIdAxisEClass, OperationIdAxis.class, "OperationIdAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(operationIdTreeItemAxisEClass, OperationIdTreeItemAxis.class, "OperationIdTreeItemAxis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
	}
} // NattableaxisPackageImpl
