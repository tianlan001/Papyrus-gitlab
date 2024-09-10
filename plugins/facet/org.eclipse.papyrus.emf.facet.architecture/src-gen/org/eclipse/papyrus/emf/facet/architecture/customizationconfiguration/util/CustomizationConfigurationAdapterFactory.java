/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.*;

import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage
 * @generated
 */
public class CustomizationConfigurationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static CustomizationConfigurationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CustomizationConfigurationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CustomizationConfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CustomizationConfigurationSwitch<Adapter> modelSwitch = new CustomizationConfigurationSwitch<Adapter>() {
		@Override
		public Adapter caseEMFFacetTreeViewerConfiguration(EMFFacetTreeViewerConfiguration object) {
			return createEMFFacetTreeViewerConfigurationAdapter();
		}

		@Override
		public Adapter caseCustomizationReference(CustomizationReference object) {
			return createCustomizationReferenceAdapter();
		}

		@Override
		public Adapter caseIApplicationRule(IApplicationRule object) {
			return createIApplicationRuleAdapter();
		}

		@Override
		public Adapter caseAbsoluteOrder(AbsoluteOrder object) {
			return createAbsoluteOrderAdapter();
		}

		@Override
		public Adapter caseRelativeOrder(RelativeOrder object) {
			return createRelativeOrderAdapter();
		}

		@Override
		public Adapter caseRedefinition(Redefinition object) {
			return createRedefinitionAdapter();
		}

		@Override
		public Adapter caseTreeViewerConfiguration(TreeViewerConfiguration object) {
			return createTreeViewerConfigurationAdapter();
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
	 *
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration <em>EMF Facet Tree Viewer Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration
	 * @generated
	 */
	public Adapter createEMFFacetTreeViewerConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference <em>Customization Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference
	 * @generated
	 */
	public Adapter createCustomizationReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule <em>IApplication Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule
	 * @generated
	 */
	public Adapter createIApplicationRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder <em>Absolute Order</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder
	 * @generated
	 */
	public Adapter createAbsoluteOrderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder <em>Relative Order</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder
	 * @generated
	 */
	public Adapter createRelativeOrderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition <em>Redefinition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition
	 * @generated
	 */
	public Adapter createRedefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration <em>Tree Viewer Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration
	 * @generated
	 */
	public Adapter createTreeViewerConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // CustomizationConfigurationAdapterFactory
