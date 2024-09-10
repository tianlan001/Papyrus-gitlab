/**
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 568853, 570542
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;

import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.ConfigurationElement;
import org.eclipse.papyrus.infra.types.IdentifiedConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage
 * @generated
 */
public class StereotypeApplicationMatcherAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static StereotypeApplicationMatcherPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationMatcherAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = StereotypeApplicationMatcherPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StereotypeApplicationMatcherSwitch<Adapter> modelSwitch =
		new StereotypeApplicationMatcherSwitch<Adapter>() {
			@Override
			public Adapter caseStereotypeApplicationMatcherConfiguration(StereotypeApplicationMatcherConfiguration object) {
				return createStereotypeApplicationMatcherConfigurationAdapter();
			}
			@Override
			public Adapter caseStereotypeMatcherAdviceConfiguration(StereotypeMatcherAdviceConfiguration object) {
				return createStereotypeMatcherAdviceConfigurationAdapter();
			}
			@Override
			public Adapter caseRequiredStereotypeConstraintConfiguration(RequiredStereotypeConstraintConfiguration object) {
				return createRequiredStereotypeConstraintConfigurationAdapter();
			}
			@Override
			public Adapter caseConfigurationElement(ConfigurationElement object) {
				return createConfigurationElementAdapter();
			}
			@Override
			public Adapter caseAbstractMatcherConfiguration(AbstractMatcherConfiguration object) {
				return createAbstractMatcherConfigurationAdapter();
			}
			@Override
			public Adapter caseAdviceConfiguration(AdviceConfiguration object) {
				return createAdviceConfigurationAdapter();
			}
			@Override
			public Adapter caseIdentifiedConfiguration(IdentifiedConfiguration object) {
				return createIdentifiedConfigurationAdapter();
			}
			@Override
			public Adapter caseAbstractAdviceBindingConfiguration(AbstractAdviceBindingConfiguration object) {
				return createAbstractAdviceBindingConfigurationAdapter();
			}
			@Override
			public Adapter caseAdviceConstraint(AdviceConstraint object) {
				return createAdviceConstraintAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration
	 * @generated
	 */
	public Adapter createStereotypeApplicationMatcherConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration <em>Stereotype Matcher Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration
	 * @generated
	 */
	public Adapter createStereotypeMatcherAdviceConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration <em>Required Stereotype Constraint Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration
	 * @generated
	 */
	public Adapter createRequiredStereotypeConstraintConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.types.ConfigurationElement <em>Configuration Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.types.ConfigurationElement
	 * @generated
	 */
	public Adapter createConfigurationElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration <em>Abstract Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration
	 * @generated
	 */
	public Adapter createAbstractMatcherConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.types.AdviceConfiguration <em>Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.types.AdviceConfiguration
	 * @generated
	 */
	public Adapter createAdviceConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.types.IdentifiedConfiguration <em>Identified Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.types.IdentifiedConfiguration
	 * @generated
	 */
	public Adapter createIdentifiedConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration <em>Abstract Advice Binding Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration
	 * @generated
	 */
	public Adapter createAbstractAdviceBindingConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint <em>Advice Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint
	 * @generated
	 */
	public Adapter createAdviceConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //StereotypeApplicationMatcherAdapterFactory
