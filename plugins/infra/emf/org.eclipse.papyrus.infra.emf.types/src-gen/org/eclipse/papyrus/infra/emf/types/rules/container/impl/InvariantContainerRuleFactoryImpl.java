/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.emf.types.rules.container.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.emf.types.rules.container.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvariantContainerRuleFactoryImpl extends EFactoryImpl implements InvariantContainerRuleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InvariantContainerRuleFactory init() {
		try {
			InvariantContainerRuleFactory theInvariantContainerRuleFactory = (InvariantContainerRuleFactory)EPackage.Registry.INSTANCE.getEFactory(InvariantContainerRulePackage.eNS_URI);
			if (theInvariantContainerRuleFactory != null) {
				return theInvariantContainerRuleFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InvariantContainerRuleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvariantContainerRuleFactoryImpl() {
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
			case InvariantContainerRulePackage.INVARIANT_CONTAINER_RULE_CONFIGURATION: return createInvariantContainerRuleConfiguration();
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION: return createHierarchyPermission();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InvariantContainerRuleConfiguration createInvariantContainerRuleConfiguration() {
		InvariantContainerRuleConfigurationImpl invariantContainerRuleConfiguration = new InvariantContainerRuleConfigurationImpl();
		return invariantContainerRuleConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HierarchyPermission createHierarchyPermission() {
		HierarchyPermissionImpl hierarchyPermission = new HierarchyPermissionImpl();
		return hierarchyPermission;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InvariantContainerRulePackage getInvariantContainerRulePackage() {
		return (InvariantContainerRulePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InvariantContainerRulePackage getPackage() {
		return InvariantContainerRulePackage.eINSTANCE;
	}

} //InvariantContainerRuleFactoryImpl
