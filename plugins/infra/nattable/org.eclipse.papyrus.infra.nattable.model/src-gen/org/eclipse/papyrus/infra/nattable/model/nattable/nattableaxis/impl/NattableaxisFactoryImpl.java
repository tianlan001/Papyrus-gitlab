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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NattableaxisFactoryImpl extends EFactoryImpl implements NattableaxisFactory {

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NattableaxisFactory init() {
		try {
			NattableaxisFactory theNattableaxisFactory = (NattableaxisFactory)EPackage.Registry.INSTANCE.getEFactory(NattableaxisPackage.eNS_URI);
			if (theNattableaxisFactory != null) {
				return theNattableaxisFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NattableaxisFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NattableaxisFactoryImpl() {
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
			case NattableaxisPackage.ID_TREE_ITEM_AXIS: return createIdTreeItemAxis();
			case NattableaxisPackage.EOBJECT_AXIS: return createEObjectAxis();
			case NattableaxisPackage.EOBJECT_TREE_ITEM_AXIS: return createEObjectTreeItemAxis();
			case NattableaxisPackage.FEATURE_ID_AXIS: return createFeatureIdAxis();
			case NattableaxisPackage.FEATURE_ID_TREE_ITEM_AXIS: return createFeatureIdTreeItemAxis();
			case NattableaxisPackage.ESTRUCTURAL_FEATURE_AXIS: return createEStructuralFeatureAxis();
			case NattableaxisPackage.EOPERATION_AXIS: return createEOperationAxis();
			case NattableaxisPackage.ESTRUCTURAL_FEATURE_TREE_ITEM_AXIS: return createEStructuralFeatureTreeItemAxis();
			case NattableaxisPackage.EOPERATION_TREE_ITEM_AXIS: return createEOperationTreeItemAxis();
			case NattableaxisPackage.OBJECT_ID_AXIS: return createObjectIdAxis();
			case NattableaxisPackage.OBJECT_ID_TREE_ITEM_AXIS: return createObjectIdTreeItemAxis();
			case NattableaxisPackage.AXIS_GROUP: return createAxisGroup();
			case NattableaxisPackage.OPERATION_ID_AXIS: return createOperationIdAxis();
			case NattableaxisPackage.OPERATION_ID_TREE_ITEM_AXIS: return createOperationIdTreeItemAxis();
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
	public IdTreeItemAxis createIdTreeItemAxis() {
		IdTreeItemAxisImpl idTreeItemAxis = new IdTreeItemAxisImpl();
		return idTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObjectAxis createEObjectAxis() {
		EObjectAxisImpl eObjectAxis = new EObjectAxisImpl();
		return eObjectAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObjectTreeItemAxis createEObjectTreeItemAxis() {
		EObjectTreeItemAxisImpl eObjectTreeItemAxis = new EObjectTreeItemAxisImpl();
		return eObjectTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureIdAxis createFeatureIdAxis() {
		FeatureIdAxisImpl featureIdAxis = new FeatureIdAxisImpl();
		return featureIdAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureIdTreeItemAxis createFeatureIdTreeItemAxis() {
		FeatureIdTreeItemAxisImpl featureIdTreeItemAxis = new FeatureIdTreeItemAxisImpl();
		return featureIdTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EStructuralFeatureAxis createEStructuralFeatureAxis() {
		EStructuralFeatureAxisImpl eStructuralFeatureAxis = new EStructuralFeatureAxisImpl();
		return eStructuralFeatureAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperationAxis createEOperationAxis() {
		EOperationAxisImpl eOperationAxis = new EOperationAxisImpl();
		return eOperationAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EStructuralFeatureTreeItemAxis createEStructuralFeatureTreeItemAxis() {
		EStructuralFeatureTreeItemAxisImpl eStructuralFeatureTreeItemAxis = new EStructuralFeatureTreeItemAxisImpl();
		return eStructuralFeatureTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperationTreeItemAxis createEOperationTreeItemAxis() {
		EOperationTreeItemAxisImpl eOperationTreeItemAxis = new EOperationTreeItemAxisImpl();
		return eOperationTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectIdAxis createObjectIdAxis() {
		ObjectIdAxisImpl objectIdAxis = new ObjectIdAxisImpl();
		return objectIdAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectIdTreeItemAxis createObjectIdTreeItemAxis() {
		ObjectIdTreeItemAxisImpl objectIdTreeItemAxis = new ObjectIdTreeItemAxisImpl();
		return objectIdTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AxisGroup createAxisGroup() {
		AxisGroupImpl axisGroup = new AxisGroupImpl();
		return axisGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationIdAxis createOperationIdAxis() {
		OperationIdAxisImpl operationIdAxis = new OperationIdAxisImpl();
		return operationIdAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationIdTreeItemAxis createOperationIdTreeItemAxis() {
		OperationIdTreeItemAxisImpl operationIdTreeItemAxis = new OperationIdTreeItemAxisImpl();
		return operationIdTreeItemAxis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NattableaxisPackage getNattableaxisPackage() {
		return (NattableaxisPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NattableaxisPackage getPackage() {
		return NattableaxisPackage.eINSTANCE;
	}
} // NattableaxisFactoryImpl
