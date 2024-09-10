/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.impl;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProvider;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;

import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;

import org.eclipse.uml2.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AssistantPackageImpl extends EPackageImpl implements AssistantPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass assistedElementTypeFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass modelingAssistantProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass assistantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass connectionAssistantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass popupAssistantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass elementTypeFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iModelingAssistantProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType elementTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType clientContextEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType iOperationEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType iProviderChangeListenerEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType iAdaptableEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AssistantPackageImpl() {
		super(eNS_URI, AssistantFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link AssistantPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AssistantPackage init() {
		if (isInited) {
			return (AssistantPackage) EPackage.Registry.INSTANCE.getEPackage(AssistantPackage.eNS_URI);
		}

		// Obtain or create and register package
		Object registeredAssistantPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AssistantPackageImpl theAssistantPackage = registeredAssistantPackage instanceof AssistantPackageImpl ? (AssistantPackageImpl) registeredAssistantPackage : new AssistantPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		FiltersPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAssistantPackage.createPackageContents();

		// Initialize created meta-data
		theAssistantPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAssistantPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AssistantPackage.eNS_URI, theAssistantPackage);
		return theAssistantPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAssistedElementTypeFilter() {
		return assistedElementTypeFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAssistedElementTypeFilter_Provider() {
		return (EReference) assistedElementTypeFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getAssistedElementTypeFilter__GetProvider() {
		return assistedElementTypeFilterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getModelingAssistantProvider() {
		return modelingAssistantProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_Name() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getModelingAssistantProvider_Assistant() {
		return (EReference) modelingAssistantProviderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getModelingAssistantProvider_OwnedFilter() {
		return (EReference) modelingAssistantProviderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getModelingAssistantProvider_PopupAssistant() {
		return (EReference) modelingAssistantProviderEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getModelingAssistantProvider_ConnectionAssistant() {
		return (EReference) modelingAssistantProviderEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ElementType() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ElementTypeID() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ClientContext() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ClientContextID() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ExcludedElementType() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_ExcludedElementTypeID() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_RelationshipType() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getModelingAssistantProvider_RelationshipTypeID() {
		return (EAttribute) modelingAssistantProviderEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__GetElementTypes() {
		return modelingAssistantProviderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__GetClientContext() {
		return modelingAssistantProviderEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__GetElementType__String() {
		return modelingAssistantProviderEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__GetExcludedElementTypes() {
		return modelingAssistantProviderEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__GetRelationshipTypes() {
		return modelingAssistantProviderEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getModelingAssistantProvider__IsRelationshipType__IElementType() {
		return modelingAssistantProviderEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAssistant() {
		return assistantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAssistant_ElementTypeID() {
		return (EAttribute) assistantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAssistant_ElementType() {
		return (EAttribute) assistantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAssistant_Provider() {
		return (EReference) assistantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getAssistant__GetElementType() {
		return assistantEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getConnectionAssistant() {
		return connectionAssistantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionAssistant_OwningProvider() {
		return (EReference) connectionAssistantEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionAssistant_SourceFilter() {
		return (EReference) connectionAssistantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionAssistant_OwnedSourceFilter() {
		return (EReference) connectionAssistantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionAssistant_TargetFilter() {
		return (EReference) connectionAssistantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConnectionAssistant_OwnedTargetFilter() {
		return (EReference) connectionAssistantEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPopupAssistant() {
		return popupAssistantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPopupAssistant_Filter() {
		return (EReference) popupAssistantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPopupAssistant_OwnedFilter() {
		return (EReference) popupAssistantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPopupAssistant_OwningProvider() {
		return (EReference) popupAssistantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getElementTypeFilter() {
		return elementTypeFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getElementTypeFilter_ElementTypeID() {
		return (EAttribute) elementTypeFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getElementTypeFilter_ElementType() {
		return (EAttribute) elementTypeFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getElementTypeFilter_Provider() {
		return (EReference) elementTypeFilterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getElementTypeFilter__GetElementType() {
		return elementTypeFilterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getElementTypeFilter__GetProvider() {
		return elementTypeFilterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIProvider() {
		return iProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getIProvider_Listener() {
		return (EAttribute) iProviderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIProvider__Provides__IOperation() {
		return iProviderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIProvider__AddProviderChangeListener__IProviderChangeListener() {
		return iProviderEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIProvider__RemoveProviderChangeListener__IProviderChangeListener() {
		return iProviderEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIModelingAssistantProvider() {
		return iModelingAssistantProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetTypes__String_IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetRelTypesOnSource__IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetRelTypesOnTarget__IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetRelTypesForSREOnTarget__IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetRelTypesForSREOnSource__IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetTypesForSource__IAdaptable_IElementType() {
		return iModelingAssistantProviderEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetTypesForTarget__IAdaptable_IElementType() {
		return iModelingAssistantProviderEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__SelectExistingElementForSource__IAdaptable_IElementType() {
		return iModelingAssistantProviderEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__SelectExistingElementForTarget__IAdaptable_IElementType() {
		return iModelingAssistantProviderEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getIModelingAssistantProvider__GetTypesForPopupBar__IAdaptable() {
		return iModelingAssistantProviderEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getElementType() {
		return elementTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getClientContext() {
		return clientContextEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getIOperation() {
		return iOperationEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getIProviderChangeListener() {
		return iProviderChangeListenerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getIAdaptable() {
		return iAdaptableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AssistantFactory getAssistantFactory() {
		return (AssistantFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		assistedElementTypeFilterEClass = createEClass(ASSISTED_ELEMENT_TYPE_FILTER);
		createEReference(assistedElementTypeFilterEClass, ASSISTED_ELEMENT_TYPE_FILTER__PROVIDER);
		createEOperation(assistedElementTypeFilterEClass, ASSISTED_ELEMENT_TYPE_FILTER___GET_PROVIDER);

		modelingAssistantProviderEClass = createEClass(MODELING_ASSISTANT_PROVIDER);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__NAME);
		createEReference(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__ASSISTANT);
		createEReference(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__OWNED_FILTER);
		createEReference(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT);
		createEReference(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE);
		createEAttribute(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPES);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___GET_CLIENT_CONTEXT);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___GET_EXCLUDED_ELEMENT_TYPES);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___GET_RELATIONSHIP_TYPES);
		createEOperation(modelingAssistantProviderEClass, MODELING_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE);

		assistantEClass = createEClass(ASSISTANT);
		createEAttribute(assistantEClass, ASSISTANT__ELEMENT_TYPE_ID);
		createEAttribute(assistantEClass, ASSISTANT__ELEMENT_TYPE);
		createEReference(assistantEClass, ASSISTANT__PROVIDER);
		createEOperation(assistantEClass, ASSISTANT___GET_ELEMENT_TYPE);

		popupAssistantEClass = createEClass(POPUP_ASSISTANT);
		createEReference(popupAssistantEClass, POPUP_ASSISTANT__FILTER);
		createEReference(popupAssistantEClass, POPUP_ASSISTANT__OWNED_FILTER);
		createEReference(popupAssistantEClass, POPUP_ASSISTANT__OWNING_PROVIDER);

		connectionAssistantEClass = createEClass(CONNECTION_ASSISTANT);
		createEReference(connectionAssistantEClass, CONNECTION_ASSISTANT__SOURCE_FILTER);
		createEReference(connectionAssistantEClass, CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER);
		createEReference(connectionAssistantEClass, CONNECTION_ASSISTANT__TARGET_FILTER);
		createEReference(connectionAssistantEClass, CONNECTION_ASSISTANT__OWNED_TARGET_FILTER);
		createEReference(connectionAssistantEClass, CONNECTION_ASSISTANT__OWNING_PROVIDER);

		iModelingAssistantProviderEClass = createEClass(IMODELING_ASSISTANT_PROVIDER);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE);
		createEOperation(iModelingAssistantProviderEClass, IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE);

		iProviderEClass = createEClass(IPROVIDER);
		createEAttribute(iProviderEClass, IPROVIDER__LISTENER);
		createEOperation(iProviderEClass, IPROVIDER___PROVIDES__IOPERATION);
		createEOperation(iProviderEClass, IPROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER);
		createEOperation(iProviderEClass, IPROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER);

		elementTypeFilterEClass = createEClass(ELEMENT_TYPE_FILTER);
		createEAttribute(elementTypeFilterEClass, ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID);
		createEAttribute(elementTypeFilterEClass, ELEMENT_TYPE_FILTER__ELEMENT_TYPE);
		createEReference(elementTypeFilterEClass, ELEMENT_TYPE_FILTER__PROVIDER);
		createEOperation(elementTypeFilterEClass, ELEMENT_TYPE_FILTER___GET_ELEMENT_TYPE);
		createEOperation(elementTypeFilterEClass, ELEMENT_TYPE_FILTER___GET_PROVIDER);

		// Create data types
		elementTypeEDataType = createEDataType(ELEMENT_TYPE);
		clientContextEDataType = createEDataType(CLIENT_CONTEXT);
		iProviderChangeListenerEDataType = createEDataType(IPROVIDER_CHANGE_LISTENER);
		iOperationEDataType = createEDataType(IOPERATION);
		iAdaptableEDataType = createEDataType(IADAPTABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		FiltersPackage theFiltersPackage = (FiltersPackage) EPackage.Registry.INSTANCE.getEPackage(FiltersPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		assistedElementTypeFilterEClass.getESuperTypes().add(theFiltersPackage.getFilter());
		modelingAssistantProviderEClass.getESuperTypes().add(this.getIModelingAssistantProvider());
		popupAssistantEClass.getESuperTypes().add(this.getAssistant());
		connectionAssistantEClass.getESuperTypes().add(this.getAssistant());
		iModelingAssistantProviderEClass.getESuperTypes().add(this.getIProvider());
		elementTypeFilterEClass.getESuperTypes().add(theFiltersPackage.getFilter());

		// Initialize classes, features, and operations; add parameters
		initEClass(assistedElementTypeFilterEClass, AssistedElementTypeFilter.class, "AssistedElementTypeFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAssistedElementTypeFilter_Provider(), this.getModelingAssistantProvider(), null, "provider", null, 1, 1, AssistedElementTypeFilter.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		initEOperation(getAssistedElementTypeFilter__GetProvider(), this.getModelingAssistantProvider(), "getProvider", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(modelingAssistantProviderEClass, ModelingAssistantProvider.class, "ModelingAssistantProvider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getModelingAssistantProvider_Name(), theTypesPackage.getString(), "name", null, 0, 1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getModelingAssistantProvider_Assistant(), this.getAssistant(), this.getAssistant_Provider(), "assistant", null, 0, -1, ModelingAssistantProvider.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getModelingAssistantProvider_OwnedFilter(), theFiltersPackage.getFilter(), null, "ownedFilter", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getModelingAssistantProvider_PopupAssistant(), this.getPopupAssistant(), this.getPopupAssistant_OwningProvider(), "popupAssistant", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, //$NON-NLS-1$
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getModelingAssistantProvider_ConnectionAssistant(), this.getConnectionAssistant(), this.getConnectionAssistant_OwningProvider(), "connectionAssistant", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_ElementType(), this.getElementType(), "elementType", null, 0, -1, ModelingAssistantProvider.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getModelingAssistantProvider_ElementTypeID(), theTypesPackage.getString(), "elementTypeID", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_ClientContext(), this.getClientContext(), "clientContext", null, 1, 1, ModelingAssistantProvider.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getModelingAssistantProvider_ClientContextID(), theTypesPackage.getString(), "clientContextID", null, 0, 1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_ExcludedElementType(), this.getElementType(), "excludedElementType", null, 0, -1, ModelingAssistantProvider.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_ExcludedElementTypeID(), theTypesPackage.getString(), "excludedElementTypeID", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_RelationshipType(), this.getElementType(), "relationshipType", null, 0, -1, ModelingAssistantProvider.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEAttribute(getModelingAssistantProvider_RelationshipTypeID(), theTypesPackage.getString(), "relationshipTypeID", null, 0, -1, ModelingAssistantProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);

		initEOperation(getModelingAssistantProvider__GetElementTypes(), this.getElementType(), "getElementTypes", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEOperation(getModelingAssistantProvider__GetClientContext(), this.getClientContext(), "getClientContext", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		EOperation op = initEOperation(getModelingAssistantProvider__GetElementType__String(), this.getElementType(), "getElementType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "id", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEOperation(getModelingAssistantProvider__GetExcludedElementTypes(), this.getElementType(), "getExcludedElementTypes", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEOperation(getModelingAssistantProvider__GetRelationshipTypes(), this.getElementType(), "getRelationshipTypes", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getModelingAssistantProvider__IsRelationshipType__IElementType(), theTypesPackage.getBoolean(), "isRelationshipType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getElementType(), "elementType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(assistantEClass, Assistant.class, "Assistant", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAssistant_ElementTypeID(), theTypesPackage.getString(), "elementTypeID", null, 1, 1, Assistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAssistant_ElementType(), this.getElementType(), "elementType", null, 1, 1, Assistant.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getAssistant_Provider(), this.getModelingAssistantProvider(), this.getModelingAssistantProvider_Assistant(), "provider", null, 1, 1, Assistant.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		initEOperation(getAssistant__GetElementType(), this.getElementType(), "getElementType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(popupAssistantEClass, PopupAssistant.class, "PopupAssistant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPopupAssistant_Filter(), theFiltersPackage.getFilter(), null, "filter", null, 0, 1, PopupAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getPopupAssistant_OwnedFilter(), theFiltersPackage.getFilter(), null, "ownedFilter", null, 0, 1, PopupAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, //$NON-NLS-1$
				!IS_ORDERED);
		initEReference(getPopupAssistant_OwningProvider(), this.getModelingAssistantProvider(), this.getModelingAssistantProvider_PopupAssistant(), "owningProvider", null, 1, 1, PopupAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(connectionAssistantEClass, ConnectionAssistant.class, "ConnectionAssistant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnectionAssistant_SourceFilter(), theFiltersPackage.getFilter(), null, "sourceFilter", null, 0, 1, ConnectionAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionAssistant_OwnedSourceFilter(), theFiltersPackage.getFilter(), null, "ownedSourceFilter", null, 0, 1, ConnectionAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionAssistant_TargetFilter(), theFiltersPackage.getFilter(), null, "targetFilter", null, 0, 1, ConnectionAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				!IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionAssistant_OwnedTargetFilter(), theFiltersPackage.getFilter(), null, "ownedTargetFilter", null, 0, 1, ConnectionAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionAssistant_OwningProvider(), this.getModelingAssistantProvider(), this.getModelingAssistantProvider_ConnectionAssistant(), "owningProvider", null, 1, 1, ConnectionAssistant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(iModelingAssistantProviderEClass, IModelingAssistantProvider.class, "IModelingAssistantProvider", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetTypes__String_IAdaptable(), this.getElementType(), "getTypes", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theTypesPackage.getString(), "hint", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "data", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetRelTypesOnSource__IAdaptable(), this.getElementType(), "getRelTypesOnSource", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetRelTypesOnTarget__IAdaptable(), this.getElementType(), "getRelTypesOnTarget", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable(), this.getElementType(), "getRelTypesOnSourceAndTarget", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetRelTypesForSREOnTarget__IAdaptable(), this.getElementType(), "getRelTypesForSREOnTarget", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetRelTypesForSREOnSource__IAdaptable(), this.getElementType(), "getRelTypesForSREOnSource", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetTypesForSource__IAdaptable_IElementType(), this.getElementType(), "getTypesForSource", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getElementType(), "relationshipType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetTypesForTarget__IAdaptable_IElementType(), this.getElementType(), "getTypesForTarget", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getElementType(), "relationshipType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__SelectExistingElementForSource__IAdaptable_IElementType(), theEcorePackage.getEObject(), "selectExistingElementForSource", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "target", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getElementType(), "relationshipType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__SelectExistingElementForTarget__IAdaptable_IElementType(), theEcorePackage.getEObject(), "selectExistingElementForTarget", 0, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "source", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getElementType(), "relationshipType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIModelingAssistantProvider__GetTypesForPopupBar__IAdaptable(), this.getElementType(), "getTypesForPopupBar", 0, -1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIAdaptable(), "host", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(iProviderEClass, IProvider.class, "IProvider", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIProvider_Listener(), this.getIProviderChangeListener(), "listener", null, 0, -1, IProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIProvider__Provides__IOperation(), theTypesPackage.getBoolean(), "provides", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIOperation(), "operation", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIProvider__AddProviderChangeListener__IProviderChangeListener(), null, "addProviderChangeListener", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIProviderChangeListener(), "listener", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getIProvider__RemoveProviderChangeListener__IProviderChangeListener(), null, "removeProviderChangeListener", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getIProviderChangeListener(), "listener", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEClass(elementTypeFilterEClass, ElementTypeFilter.class, "ElementTypeFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getElementTypeFilter_ElementTypeID(), theTypesPackage.getString(), "elementTypeID", null, 1, 1, ElementTypeFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getElementTypeFilter_ElementType(), this.getElementType(), "elementType", null, 1, 1, ElementTypeFilter.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getElementTypeFilter_Provider(), this.getModelingAssistantProvider(), null, "provider", null, 1, 1, ElementTypeFilter.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, //$NON-NLS-1$
				IS_DERIVED, !IS_ORDERED);

		initEOperation(getElementTypeFilter__GetElementType(), this.getElementType(), "getElementType", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		initEOperation(getElementTypeFilter__GetProvider(), this.getModelingAssistantProvider(), "getProvider", 1, 1, IS_UNIQUE, !IS_ORDERED); //$NON-NLS-1$

		// Initialize data types
		initEDataType(elementTypeEDataType, IElementType.class, "ElementType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(clientContextEDataType, IClientContext.class, "ClientContext", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iProviderChangeListenerEDataType, IProviderChangeListener.class, "IProviderChangeListener", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iOperationEDataType, IOperation.class, "IOperation", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iAdaptableEDataType, IAdaptable.class, "IAdaptable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// union
		createUnionAnnotations();
		// subsets
		createSubsetsAnnotations();
	}

	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets"; //$NON-NLS-1$
		addAnnotation(getModelingAssistantProvider_PopupAssistant(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ModelingAssistantProvider/assistant") //$NON-NLS-1$
				});
		addAnnotation(getModelingAssistantProvider_ConnectionAssistant(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ModelingAssistantProvider/assistant") //$NON-NLS-1$
				});
		addAnnotation(getModelingAssistantProvider_RelationshipType(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ModelingAssistantProvider/elementType") //$NON-NLS-1$
				});
		addAnnotation(getModelingAssistantProvider_RelationshipTypeID(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ModelingAssistantProvider/elementTypeID") //$NON-NLS-1$
				});
		addAnnotation(getPopupAssistant_OwnedFilter(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//PopupAssistant/filter") //$NON-NLS-1$
				});
		addAnnotation(getPopupAssistant_OwningProvider(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Assistant/provider") //$NON-NLS-1$
				});
		addAnnotation(getConnectionAssistant_OwnedSourceFilter(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ConnectionAssistant/sourceFilter") //$NON-NLS-1$
				});
		addAnnotation(getConnectionAssistant_OwnedTargetFilter(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//ConnectionAssistant/targetFilter") //$NON-NLS-1$
				});
		addAnnotation(getConnectionAssistant_OwningProvider(),
				source,
				new String[] {
				},
				new URI[] {
						URI.createURI(eNS_URI).appendFragment("//Assistant/provider") //$NON-NLS-1$
				});
	}

	/**
	 * Initializes the annotations for <b>union</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createUnionAnnotations() {
		String source = "union"; //$NON-NLS-1$
		addAnnotation(getModelingAssistantProvider_Assistant(),
				source,
				new String[] {
				});
		addAnnotation(getAssistant_Provider(),
				source,
				new String[] {
				});
	}

} // AssistantPackageImpl
