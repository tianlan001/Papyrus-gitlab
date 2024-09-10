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
 *  Christian W. Damus - bug 568782
 */
package org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.uml.types.core.advices.applystereotype.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ApplyStereotypeAdviceFactoryImpl extends EFactoryImpl implements ApplyStereotypeAdviceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ApplyStereotypeAdviceFactory init() {
		try {
			ApplyStereotypeAdviceFactory theApplyStereotypeAdviceFactory = (ApplyStereotypeAdviceFactory)EPackage.Registry.INSTANCE.getEFactory(ApplyStereotypeAdvicePackage.eNS_URI);
			if (theApplyStereotypeAdviceFactory != null) {
				return theApplyStereotypeAdviceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ApplyStereotypeAdviceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplyStereotypeAdviceFactoryImpl() {
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
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION: return createApplyStereotypeAdviceConfiguration();
			case ApplyStereotypeAdvicePackage.STEREOTYPE_TO_APPLY: return createStereotypeToApply();
			case ApplyStereotypeAdvicePackage.FEATURE_TO_SET: return createFeatureToSet();
			case ApplyStereotypeAdvicePackage.LIST_VALUE: return createListValue();
			case ApplyStereotypeAdvicePackage.CONSTANT_VALUE: return createConstantValue();
			case ApplyStereotypeAdvicePackage.QUERY_EXECUTION_VALUE: return createQueryExecutionValue();
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
	public ApplyStereotypeAdviceConfiguration createApplyStereotypeAdviceConfiguration() {
		ApplyStereotypeAdviceConfigurationImpl applyStereotypeAdviceConfiguration = new ApplyStereotypeAdviceConfigurationImpl();
		return applyStereotypeAdviceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StereotypeToApply createStereotypeToApply() {
		StereotypeToApplyImpl stereotypeToApply = new StereotypeToApplyImpl();
		return stereotypeToApply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureToSet createFeatureToSet() {
		FeatureToSetImpl featureToSet = new FeatureToSetImpl();
		return featureToSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ListValue createListValue() {
		ListValueImpl listValue = new ListValueImpl();
		return listValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConstantValue createConstantValue() {
		ConstantValueImpl constantValue = new ConstantValueImpl();
		return constantValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QueryExecutionValue createQueryExecutionValue() {
		QueryExecutionValueImpl queryExecutionValue = new QueryExecutionValueImpl();
		return queryExecutionValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ApplyStereotypeAdvicePackage getApplyStereotypeAdvicePackage() {
		return (ApplyStereotypeAdvicePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ApplyStereotypeAdvicePackage getPackage() {
		return ApplyStereotypeAdvicePackage.eINSTANCE;
	}

} //ApplyStereotypeAdviceFactoryImpl
