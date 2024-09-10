/**
 * Copyright (c) 2016, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 568782
 */
 
package org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.emf.types.ui.advices.values.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuntimeValuesAdviceFactoryImpl extends EFactoryImpl implements RuntimeValuesAdviceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RuntimeValuesAdviceFactory init() {
		try {
			RuntimeValuesAdviceFactory theRuntimeValuesAdviceFactory = (RuntimeValuesAdviceFactory)EPackage.Registry.INSTANCE.getEFactory(RuntimeValuesAdvicePackage.eNS_URI);
			if (theRuntimeValuesAdviceFactory != null) {
				return theRuntimeValuesAdviceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RuntimeValuesAdviceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeValuesAdviceFactoryImpl() {
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
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION: return createRuntimeValuesAdviceConfiguration();
			case RuntimeValuesAdvicePackage.VIEW_TO_DISPLAY: return createViewToDisplay();
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
	public RuntimeValuesAdviceConfiguration createRuntimeValuesAdviceConfiguration() {
		RuntimeValuesAdviceConfigurationImpl runtimeValuesAdviceConfiguration = new RuntimeValuesAdviceConfigurationImpl();
		return runtimeValuesAdviceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ViewToDisplay createViewToDisplay() {
		ViewToDisplayImpl viewToDisplay = new ViewToDisplayImpl();
		return viewToDisplay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuntimeValuesAdvicePackage getRuntimeValuesAdvicePackage() {
		return (RuntimeValuesAdvicePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RuntimeValuesAdvicePackage getPackage() {
		return RuntimeValuesAdvicePackage.eINSTANCE;
	}

} //RuntimeValuesAdviceFactoryImpl
