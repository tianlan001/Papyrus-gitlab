/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.*;

import org.eclipse.sirius.properties.AbstractContainerDescription;
import org.eclipse.sirius.properties.AbstractControlDescription;
import org.eclipse.sirius.properties.AbstractWidgetDescription;
import org.eclipse.sirius.properties.ContainerDescription;
import org.eclipse.sirius.properties.ControlDescription;
import org.eclipse.sirius.properties.WidgetDescription;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.AbstractExtReferenceDescription;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.ExtReferenceDescription;

import org.eclipse.sirius.viewpoint.description.DocumentedElement;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage
 * @generated
 */
public class PropertiesAdvancedControlsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PropertiesAdvancedControlsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesAdvancedControlsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PropertiesAdvancedControlsPackage.eINSTANCE;
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
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertiesAdvancedControlsSwitch<Adapter> modelSwitch = new PropertiesAdvancedControlsSwitch<Adapter>() {
		@Override
		public Adapter caseExtEditableReferenceDescription(ExtEditableReferenceDescription object) {
			return createExtEditableReferenceDescriptionAdapter();
		}

		@Override
		public Adapter caseContainerBorderDescription(ContainerBorderDescription object) {
			return createContainerBorderDescriptionAdapter();
		}

		@Override
		public Adapter caseLanguageExpressionDescription(LanguageExpressionDescription object) {
			return createLanguageExpressionDescriptionAdapter();
		}

		@Override
		public Adapter caseProfileApplicationDescription(ProfileApplicationDescription object) {
			return createProfileApplicationDescriptionAdapter();
		}

		@Override
		public Adapter caseStereotypeApplicationDescription(StereotypeApplicationDescription object) {
			return createStereotypeApplicationDescriptionAdapter();
		}

		@Override
		public Adapter caseInputContentPapyrusReferenceDescription(InputContentPapyrusReferenceDescription object) {
			return createInputContentPapyrusReferenceDescriptionAdapter();
		}

		@Override
		public Adapter caseIdentifiedElement(IdentifiedElement object) {
			return createIdentifiedElementAdapter();
		}

		@Override
		public Adapter caseDocumentedElement(DocumentedElement object) {
			return createDocumentedElementAdapter();
		}

		@Override
		public Adapter caseAbstractControlDescription(AbstractControlDescription object) {
			return createAbstractControlDescriptionAdapter();
		}

		@Override
		public Adapter caseAbstractWidgetDescription(AbstractWidgetDescription object) {
			return createAbstractWidgetDescriptionAdapter();
		}

		@Override
		public Adapter caseAbstractExtReferenceDescription(AbstractExtReferenceDescription object) {
			return createAbstractExtReferenceDescriptionAdapter();
		}

		@Override
		public Adapter caseControlDescription(ControlDescription object) {
			return createControlDescriptionAdapter();
		}

		@Override
		public Adapter caseWidgetDescription(WidgetDescription object) {
			return createWidgetDescriptionAdapter();
		}

		@Override
		public Adapter caseExtReferenceDescription(ExtReferenceDescription object) {
			return createExtReferenceDescriptionAdapter();
		}

		@Override
		public Adapter caseAbstractContainerDescription(AbstractContainerDescription object) {
			return createAbstractContainerDescriptionAdapter();
		}

		@Override
		public Adapter caseContainerDescription(ContainerDescription object) {
			return createContainerDescriptionAdapter();
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
		return modelSwitch.doSwitch((EObject) target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription <em>Ext Editable Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription
	 * @generated
	 */
	public Adapter createExtEditableReferenceDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription <em>Container Border Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription
	 * @generated
	 */
	public Adapter createContainerBorderDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.LanguageExpressionDescription <em>Language Expression Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.LanguageExpressionDescription
	 * @generated
	 */
	public Adapter createLanguageExpressionDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription <em>Profile Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription
	 * @generated
	 */
	public Adapter createProfileApplicationDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription <em>Stereotype Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription
	 * @generated
	 */
	public Adapter createStereotypeApplicationDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription <em>Input Content Papyrus Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription
	 * @generated
	 */
	public Adapter createInputContentPapyrusReferenceDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.viewpoint.description.IdentifiedElement <em>Identified Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.viewpoint.description.IdentifiedElement
	 * @generated
	 */
	public Adapter createIdentifiedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.viewpoint.description.DocumentedElement <em>Documented Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.viewpoint.description.DocumentedElement
	 * @generated
	 */
	public Adapter createDocumentedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.AbstractControlDescription <em>Abstract Control Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.AbstractControlDescription
	 * @generated
	 */
	public Adapter createAbstractControlDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.AbstractWidgetDescription <em>Abstract Widget Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.AbstractWidgetDescription
	 * @generated
	 */
	public Adapter createAbstractWidgetDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.AbstractExtReferenceDescription <em>Abstract Ext Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.AbstractExtReferenceDescription
	 * @generated
	 */
	public Adapter createAbstractExtReferenceDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.ControlDescription <em>Control Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.ControlDescription
	 * @generated
	 */
	public Adapter createControlDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.WidgetDescription <em>Widget Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.WidgetDescription
	 * @generated
	 */
	public Adapter createWidgetDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.ExtReferenceDescription <em>Ext Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.ExtReferenceDescription
	 * @generated
	 */
	public Adapter createExtReferenceDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.AbstractContainerDescription <em>Abstract Container Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.AbstractContainerDescription
	 * @generated
	 */
	public Adapter createAbstractContainerDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.properties.ContainerDescription <em>Container Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.properties.ContainerDescription
	 * @generated
	 */
	public Adapter createContainerDescriptionAdapter() {
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

} //PropertiesAdvancedControlsAdapterFactory
