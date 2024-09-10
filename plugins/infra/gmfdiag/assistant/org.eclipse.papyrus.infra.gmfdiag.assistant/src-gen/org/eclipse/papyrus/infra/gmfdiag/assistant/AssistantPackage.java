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
package org.eclipse.papyrus.infra.gmfdiag.assistant;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
 * @model kind="package"
 * @generated
 */
public interface AssistantPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "assistant"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2014/diagram/assistant"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "assistant"; //$NON-NLS-1$

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eCONTENT_TYPE = "org.eclipse.papyrus.infra.gmfdiag.assistants"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	AssistantPackage eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistedElementTypeFilterImpl <em>Assisted Element Type Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistedElementTypeFilterImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getAssistedElementTypeFilter()
	 * @generated
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER__PROVIDER = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Assisted Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The operation id for the '<em>Get Provider</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER___GET_PROVIDER = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Assisted Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTED_ELEMENT_TYPE_FILTER_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl <em>Modeling Assistant Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getModelingAssistantProvider()
	 * @generated
	 */
	int MODELING_ASSISTANT_PROVIDER = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl <em>Assistant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getAssistant()
	 * @generated
	 */
	int ASSISTANT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl <em>Connection Assistant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getConnectionAssistant()
	 * @generated
	 */
	int CONNECTION_ASSISTANT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl <em>Popup Assistant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getPopupAssistant()
	 * @generated
	 */
	int POPUP_ASSISTANT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl <em>Element Type Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getElementTypeFilter()
	 * @generated
	 */
	int ELEMENT_TYPE_FILTER = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider <em>IProvider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIProvider()
	 * @generated
	 */
	int IPROVIDER = 6;

	/**
	 * The feature id for the '<em><b>Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER__LISTENER = 0;

	/**
	 * The number of structural features of the '<em>IProvider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Provides</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER___PROVIDES__IOPERATION = 0;

	/**
	 * The operation id for the '<em>Add Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = 1;

	/**
	 * The operation id for the '<em>Remove Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = 2;

	/**
	 * The number of operations of the '<em>IProvider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IPROVIDER_OPERATION_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider <em>IModeling Assistant Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIModelingAssistantProvider()
	 * @generated
	 */
	int IMODELING_ASSISTANT_PROVIDER = 5;

	/**
	 * The feature id for the '<em><b>Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER__LISTENER = IPROVIDER__LISTENER;

	/**
	 * The number of structural features of the '<em>IModeling Assistant Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT = IPROVIDER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Provides</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___PROVIDES__IOPERATION = IPROVIDER___PROVIDES__IOPERATION;

	/**
	 * The operation id for the '<em>Add Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = IPROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Remove Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = IPROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Get Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE = IPROVIDER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Rel Types On Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE = IPROVIDER_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Rel Types On Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE = IPROVIDER_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Rel Types On Source And Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE = IPROVIDER_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE = IPROVIDER_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE = IPROVIDER_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Types For Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = IPROVIDER_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Types For Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = IPROVIDER_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Select Existing Element For Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = IPROVIDER_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Select Existing Element For Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = IPROVIDER_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Types For Popup Bar</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE = IPROVIDER_OPERATION_COUNT + 10;

	/**
	 * The number of operations of the '<em>IModeling Assistant Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT = IPROVIDER_OPERATION_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__LISTENER = IMODELING_ASSISTANT_PROVIDER__LISTENER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__NAME = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assistant</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__ASSISTANT = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__OWNED_FILTER = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Popup Assistant</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Connection Assistant</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Client Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Client Context ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Excluded Element Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Excluded Element Type ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Relationship Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Relationship Type ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Modeling Assistant Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER_FEATURE_COUNT = IMODELING_ASSISTANT_PROVIDER_FEATURE_COUNT + 13;

	/**
	 * The operation id for the '<em>Provides</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___PROVIDES__IOPERATION = IMODELING_ASSISTANT_PROVIDER___PROVIDES__IOPERATION;

	/**
	 * The operation id for the '<em>Add Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = IMODELING_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Remove Provider Change Listener</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = IMODELING_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Get Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Source And Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Types For Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Get Types For Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Select Existing Element For Source</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Select Existing Element For Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Get Types For Popup Bar</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE = IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Element Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPES = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Client Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_CLIENT_CONTEXT = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Excluded Element Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_EXCLUDED_ELEMENT_TYPES = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Relationship Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___GET_RELATIONSHIP_TYPES = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Relationship Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Modeling Assistant Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MODELING_ASSISTANT_PROVIDER_OPERATION_COUNT = IMODELING_ASSISTANT_PROVIDER_OPERATION_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT__ELEMENT_TYPE_ID = 0;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT__ELEMENT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT__PROVIDER = 2;

	/**
	 * The number of structural features of the '<em>Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT___GET_ELEMENT_TYPE = 0;

	/**
	 * The number of operations of the '<em>Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_OPERATION_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__ELEMENT_TYPE_ID = ASSISTANT__ELEMENT_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__ELEMENT_TYPE = ASSISTANT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__PROVIDER = ASSISTANT__PROVIDER;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__FILTER = ASSISTANT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__OWNED_FILTER = ASSISTANT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owning Provider</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT__OWNING_PROVIDER = ASSISTANT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Popup Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT_FEATURE_COUNT = ASSISTANT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT___GET_ELEMENT_TYPE = ASSISTANT___GET_ELEMENT_TYPE;

	/**
	 * The number of operations of the '<em>Popup Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int POPUP_ASSISTANT_OPERATION_COUNT = ASSISTANT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__ELEMENT_TYPE_ID = ASSISTANT__ELEMENT_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__ELEMENT_TYPE = ASSISTANT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__PROVIDER = ASSISTANT__PROVIDER;

	/**
	 * The feature id for the '<em><b>Source Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__SOURCE_FILTER = ASSISTANT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Source Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER = ASSISTANT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__TARGET_FILTER = ASSISTANT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owned Target Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__OWNED_TARGET_FILTER = ASSISTANT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Owning Provider</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT__OWNING_PROVIDER = ASSISTANT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Connection Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT_FEATURE_COUNT = ASSISTANT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT___GET_ELEMENT_TYPE = ASSISTANT___GET_ELEMENT_TYPE;

	/**
	 * The number of operations of the '<em>Connection Assistant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ASSISTANT_OPERATION_COUNT = ASSISTANT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__ELEMENT_TYPE = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__PROVIDER = FiltersPackage.FILTER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER___GET_ELEMENT_TYPE = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Provider</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER___GET_PROVIDER = FiltersPackage.FILTER_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '<em>Element Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getElementType()
	 * @generated
	 */
	int ELEMENT_TYPE = 8;

	/**
	 * The meta object id for the '<em>Client Context</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.IClientContext
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getClientContext()
	 * @generated
	 */
	int CLIENT_CONTEXT = 9;

	/**
	 * The meta object id for the '<em>IOperation</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IOperation
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIOperation()
	 * @generated
	 */
	int IOPERATION = 11;

	/**
	 * The meta object id for the '<em>IProvider Change Listener</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIProviderChangeListener()
	 * @generated
	 */
	int IPROVIDER_CHANGE_LISTENER = 10;

	/**
	 * The meta object id for the '<em>IAdaptable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.core.runtime.IAdaptable
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIAdaptable()
	 * @generated
	 */
	int IADAPTABLE = 12;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter <em>Assisted Element Type Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Assisted Element Type Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter
	 * @generated
	 */
	EClass getAssistedElementTypeFilter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider()
	 * @see #getAssistedElementTypeFilter()
	 * @generated
	 */
	EReference getAssistedElementTypeFilter_Provider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider() <em>Get Provider</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Provider</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider()
	 * @generated
	 */
	EOperation getAssistedElementTypeFilter__GetProvider();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider <em>Modeling Assistant Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Modeling Assistant Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider
	 * @generated
	 */
	EClass getModelingAssistantProvider();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getName()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getAssistants <em>Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getAssistants()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EReference getModelingAssistantProvider_Assistant();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getOwnedFilters <em>Owned Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Owned Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getOwnedFilters()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EReference getModelingAssistantProvider_OwnedFilter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getPopupAssistants <em>Popup Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Popup Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getPopupAssistants()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EReference getModelingAssistantProvider_PopupAssistant();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getConnectionAssistants <em>Connection Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Connection Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getConnectionAssistants()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EReference getModelingAssistantProvider_ConnectionAssistant();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ElementType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypeIDs <em>Element Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Element Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypeIDs()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ElementTypeID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext <em>Client Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Client Context</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ClientContext();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContextID <em>Client Context ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Client Context ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContextID()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ClientContextID();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes <em>Excluded Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Excluded Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ExcludedElementType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypeIDs <em>Excluded Element Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Excluded Element Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypeIDs()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_ExcludedElementTypeID();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes <em>Relationship Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Relationship Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_RelationshipType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypeIDs <em>Relationship Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Relationship Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypeIDs()
	 * @see #getModelingAssistantProvider()
	 * @generated
	 */
	EAttribute getModelingAssistantProvider_RelationshipTypeID();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes() <em>Get Element Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Element Types</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementTypes()
	 * @generated
	 */
	EOperation getModelingAssistantProvider__GetElementTypes();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext() <em>Get Client Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Client Context</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getClientContext()
	 * @generated
	 */
	EOperation getModelingAssistantProvider__GetClientContext();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementType(java.lang.String) <em>Get Element Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Element Type</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getElementType(java.lang.String)
	 * @generated
	 */
	EOperation getModelingAssistantProvider__GetElementType__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes() <em>Get Excluded Element Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Excluded Element Types</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getExcludedElementTypes()
	 * @generated
	 */
	EOperation getModelingAssistantProvider__GetExcludedElementTypes();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes() <em>Get Relationship Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Relationship Types</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#getRelationshipTypes()
	 * @generated
	 */
	EOperation getModelingAssistantProvider__GetRelationshipTypes();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#isRelationshipType(org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Is Relationship Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Is Relationship Type</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider#isRelationshipType(org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getModelingAssistantProvider__IsRelationshipType__IElementType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant <em>Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant
	 * @generated
	 */
	EClass getAssistant();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementTypeID <em>Element Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementTypeID()
	 * @see #getAssistant()
	 * @generated
	 */
	EAttribute getAssistant_ElementTypeID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType()
	 * @see #getAssistant()
	 * @generated
	 */
	EAttribute getAssistant_ElementType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getProvider()
	 * @see #getAssistant()
	 * @generated
	 */
	EReference getAssistant_Provider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType() <em>Get Element Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Element Type</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType()
	 * @generated
	 */
	EOperation getAssistant__GetElementType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant <em>Connection Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Connection Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant
	 * @generated
	 */
	EClass getConnectionAssistant();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider <em>Owning Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Owning Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwningProvider()
	 * @see #getConnectionAssistant()
	 * @generated
	 */
	EReference getConnectionAssistant_OwningProvider();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getSourceFilter <em>Source Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Source Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getSourceFilter()
	 * @see #getConnectionAssistant()
	 * @generated
	 */
	EReference getConnectionAssistant_SourceFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedSourceFilter <em>Owned Source Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Owned Source Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedSourceFilter()
	 * @see #getConnectionAssistant()
	 * @generated
	 */
	EReference getConnectionAssistant_OwnedSourceFilter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getTargetFilter <em>Target Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Target Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getTargetFilter()
	 * @see #getConnectionAssistant()
	 * @generated
	 */
	EReference getConnectionAssistant_TargetFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedTargetFilter <em>Owned Target Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Owned Target Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant#getOwnedTargetFilter()
	 * @see #getConnectionAssistant()
	 * @generated
	 */
	EReference getConnectionAssistant_OwnedTargetFilter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant <em>Popup Assistant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Popup Assistant</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant
	 * @generated
	 */
	EClass getPopupAssistant();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getFilter()
	 * @see #getPopupAssistant()
	 * @generated
	 */
	EReference getPopupAssistant_Filter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwnedFilter <em>Owned Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Owned Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwnedFilter()
	 * @see #getPopupAssistant()
	 * @generated
	 */
	EReference getPopupAssistant_OwnedFilter();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwningProvider <em>Owning Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Owning Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant#getOwningProvider()
	 * @see #getPopupAssistant()
	 * @generated
	 */
	EReference getPopupAssistant_OwningProvider();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter <em>Element Type Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Element Type Filter</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter
	 * @generated
	 */
	EClass getElementTypeFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementTypeID <em>Element Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementTypeID()
	 * @see #getElementTypeFilter()
	 * @generated
	 */
	EAttribute getElementTypeFilter_ElementTypeID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType()
	 * @see #getElementTypeFilter()
	 * @generated
	 */
	EAttribute getElementTypeFilter_ElementType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider()
	 * @see #getElementTypeFilter()
	 * @generated
	 */
	EReference getElementTypeFilter_Provider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType() <em>Get Element Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Element Type</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType()
	 * @generated
	 */
	EOperation getElementTypeFilter__GetElementType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider() <em>Get Provider</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Provider</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider()
	 * @generated
	 */
	EOperation getElementTypeFilter__GetProvider();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gmf.runtime.common.core.service.IProvider <em>IProvider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>IProvider</em>'.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider
	 * @model instanceClass="org.eclipse.gmf.runtime.common.core.service.IProvider"
	 * @generated
	 */
	EClass getIProvider();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#getListeners <em>Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Listener</em>'.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#getListeners()
	 * @see #getIProvider()
	 * @generated
	 */
	EAttribute getIProvider_Listener();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation) <em>Provides</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Provides</em>' operation.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 * @generated
	 */
	EOperation getIProvider__Provides__IOperation();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Add Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Add Provider Change Listener</em>' operation.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#addProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated
	 */
	EOperation getIProvider__AddProviderChangeListener__IProviderChangeListener();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener) <em>Remove Provider Change Listener</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Remove Provider Change Listener</em>' operation.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#removeProviderChangeListener(org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener)
	 * @generated
	 */
	EOperation getIProvider__RemoveProviderChangeListener__IProviderChangeListener();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider <em>IModeling Assistant Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>IModeling Assistant Provider</em>'.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider
	 * @model instanceClass="org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider" superTypes="org.eclipse.papyrus.infra.gmfdiag.assistant.IProvider"
	 * @generated
	 */
	EClass getIModelingAssistantProvider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable) <em>Get Types</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Types</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypes(java.lang.String, org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetTypes__String_IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Rel Types On Source</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetRelTypesOnSource__IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Rel Types On Target</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetRelTypesOnTarget__IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types On
	 * Source And Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Rel Types On Source And Target</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesOnSourceAndTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Rel Types For SRE On Target</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnTarget(org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetRelTypesForSREOnTarget__IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable) <em>Get Rel Types For SRE On Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Rel Types For SRE On Source</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getRelTypesForSREOnSource(org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetRelTypesForSREOnSource__IAdaptable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For
	 * Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Types For Source</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetTypesForSource__IAdaptable_IElementType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Get Types For
	 * Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Types For Target</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetTypesForTarget__IAdaptable_IElementType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * <em>Select Existing Element For Source</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Select Existing Element For Source</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForSource(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__SelectExistingElementForSource__IAdaptable_IElementType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * <em>Select Existing Element For Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Select Existing Element For Target</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#selectExistingElementForTarget(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__SelectExistingElementForTarget__IAdaptable_IElementType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable) <em>Get Types For Popup Bar</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Types For Popup Bar</em>' operation.
	 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider#getTypesForPopupBar(org.eclipse.core.runtime.IAdaptable)
	 * @generated
	 */
	EOperation getIModelingAssistantProvider__GetTypesForPopupBar__IAdaptable();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.emf.type.core.IElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * From package assistantapi.
	 * </p>
	 * <!-- end-model-doc -->
	 *
	 * @return the meta object for data type '<em>Element Type</em>'.
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
	 * @model instanceClass="org.eclipse.gmf.runtime.emf.type.core.IElementType"
	 * @generated
	 */
	EDataType getElementType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.emf.type.core.IClientContext <em>Client Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Client Context</em>'.
	 * @see org.eclipse.gmf.runtime.emf.type.core.IClientContext
	 * @model instanceClass="org.eclipse.gmf.runtime.emf.type.core.IClientContext"
	 * @generated
	 */
	EDataType getClientContext();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.common.core.service.IOperation <em>IOperation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * From package serviceprovider.
	 * </p>
	 * <!-- end-model-doc -->
	 *
	 * @return the meta object for data type '<em>IOperation</em>'.
	 * @see org.eclipse.gmf.runtime.common.core.service.IOperation
	 * @model instanceClass="org.eclipse.gmf.runtime.common.core.service.IOperation"
	 * @generated
	 */
	EDataType getIOperation();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener <em>IProvider Change Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * From package serviceprovider.
	 * </p>
	 * <!-- end-model-doc -->
	 *
	 * @return the meta object for data type '<em>IProvider Change Listener</em>'.
	 * @see org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener
	 * @model instanceClass="org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener"
	 * @generated
	 */
	EDataType getIProviderChangeListener();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.runtime.IAdaptable <em>IAdaptable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * From package assistantapi.
	 * </p>
	 * <!-- end-model-doc -->
	 *
	 * @return the meta object for data type '<em>IAdaptable</em>'.
	 * @see org.eclipse.core.runtime.IAdaptable
	 * @model instanceClass="org.eclipse.core.runtime.IAdaptable"
	 * @generated
	 */
	EDataType getIAdaptable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AssistantFactory getAssistantFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistedElementTypeFilterImpl <em>Assisted Element Type Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistedElementTypeFilterImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getAssistedElementTypeFilter()
		 * @generated
		 */
		EClass ASSISTED_ELEMENT_TYPE_FILTER = eINSTANCE.getAssistedElementTypeFilter();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ASSISTED_ELEMENT_TYPE_FILTER__PROVIDER = eINSTANCE.getAssistedElementTypeFilter_Provider();

		/**
		 * The meta object literal for the '<em><b>Get Provider</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ASSISTED_ELEMENT_TYPE_FILTER___GET_PROVIDER = eINSTANCE.getAssistedElementTypeFilter__GetProvider();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl <em>Modeling Assistant Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ModelingAssistantProviderImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getModelingAssistantProvider()
		 * @generated
		 */
		EClass MODELING_ASSISTANT_PROVIDER = eINSTANCE.getModelingAssistantProvider();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__NAME = eINSTANCE.getModelingAssistantProvider_Name();

		/**
		 * The meta object literal for the '<em><b>Assistant</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MODELING_ASSISTANT_PROVIDER__ASSISTANT = eINSTANCE.getModelingAssistantProvider_Assistant();

		/**
		 * The meta object literal for the '<em><b>Owned Filter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MODELING_ASSISTANT_PROVIDER__OWNED_FILTER = eINSTANCE.getModelingAssistantProvider_OwnedFilter();

		/**
		 * The meta object literal for the '<em><b>Popup Assistant</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT = eINSTANCE.getModelingAssistantProvider_PopupAssistant();

		/**
		 * The meta object literal for the '<em><b>Connection Assistant</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT = eINSTANCE.getModelingAssistantProvider_ConnectionAssistant();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE = eINSTANCE.getModelingAssistantProvider_ElementType();

		/**
		 * The meta object literal for the '<em><b>Element Type ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID = eINSTANCE.getModelingAssistantProvider_ElementTypeID();

		/**
		 * The meta object literal for the '<em><b>Client Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT = eINSTANCE.getModelingAssistantProvider_ClientContext();

		/**
		 * The meta object literal for the '<em><b>Client Context ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID = eINSTANCE.getModelingAssistantProvider_ClientContextID();

		/**
		 * The meta object literal for the '<em><b>Excluded Element Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE = eINSTANCE.getModelingAssistantProvider_ExcludedElementType();

		/**
		 * The meta object literal for the '<em><b>Excluded Element Type ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID = eINSTANCE.getModelingAssistantProvider_ExcludedElementTypeID();

		/**
		 * The meta object literal for the '<em><b>Relationship Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE = eINSTANCE.getModelingAssistantProvider_RelationshipType();

		/**
		 * The meta object literal for the '<em><b>Relationship Type ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID = eINSTANCE.getModelingAssistantProvider_RelationshipTypeID();

		/**
		 * The meta object literal for the '<em><b>Get Element Types</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPES = eINSTANCE.getModelingAssistantProvider__GetElementTypes();

		/**
		 * The meta object literal for the '<em><b>Get Client Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___GET_CLIENT_CONTEXT = eINSTANCE.getModelingAssistantProvider__GetClientContext();

		/**
		 * The meta object literal for the '<em><b>Get Element Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING = eINSTANCE.getModelingAssistantProvider__GetElementType__String();

		/**
		 * The meta object literal for the '<em><b>Get Excluded Element Types</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___GET_EXCLUDED_ELEMENT_TYPES = eINSTANCE.getModelingAssistantProvider__GetExcludedElementTypes();

		/**
		 * The meta object literal for the '<em><b>Get Relationship Types</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___GET_RELATIONSHIP_TYPES = eINSTANCE.getModelingAssistantProvider__GetRelationshipTypes();

		/**
		 * The meta object literal for the '<em><b>Is Relationship Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation MODELING_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE = eINSTANCE.getModelingAssistantProvider__IsRelationshipType__IElementType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl <em>Assistant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getAssistant()
		 * @generated
		 */
		EClass ASSISTANT = eINSTANCE.getAssistant();

		/**
		 * The meta object literal for the '<em><b>Element Type ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ASSISTANT__ELEMENT_TYPE_ID = eINSTANCE.getAssistant_ElementTypeID();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ASSISTANT__ELEMENT_TYPE = eINSTANCE.getAssistant_ElementType();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ASSISTANT__PROVIDER = eINSTANCE.getAssistant_Provider();

		/**
		 * The meta object literal for the '<em><b>Get Element Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ASSISTANT___GET_ELEMENT_TYPE = eINSTANCE.getAssistant__GetElementType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl <em>Connection Assistant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ConnectionAssistantImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getConnectionAssistant()
		 * @generated
		 */
		EClass CONNECTION_ASSISTANT = eINSTANCE.getConnectionAssistant();

		/**
		 * The meta object literal for the '<em><b>Owning Provider</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_ASSISTANT__OWNING_PROVIDER = eINSTANCE.getConnectionAssistant_OwningProvider();

		/**
		 * The meta object literal for the '<em><b>Source Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_ASSISTANT__SOURCE_FILTER = eINSTANCE.getConnectionAssistant_SourceFilter();

		/**
		 * The meta object literal for the '<em><b>Owned Source Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER = eINSTANCE.getConnectionAssistant_OwnedSourceFilter();

		/**
		 * The meta object literal for the '<em><b>Target Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_ASSISTANT__TARGET_FILTER = eINSTANCE.getConnectionAssistant_TargetFilter();

		/**
		 * The meta object literal for the '<em><b>Owned Target Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONNECTION_ASSISTANT__OWNED_TARGET_FILTER = eINSTANCE.getConnectionAssistant_OwnedTargetFilter();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl <em>Popup Assistant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.PopupAssistantImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getPopupAssistant()
		 * @generated
		 */
		EClass POPUP_ASSISTANT = eINSTANCE.getPopupAssistant();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference POPUP_ASSISTANT__FILTER = eINSTANCE.getPopupAssistant_Filter();

		/**
		 * The meta object literal for the '<em><b>Owned Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference POPUP_ASSISTANT__OWNED_FILTER = eINSTANCE.getPopupAssistant_OwnedFilter();

		/**
		 * The meta object literal for the '<em><b>Owning Provider</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference POPUP_ASSISTANT__OWNING_PROVIDER = eINSTANCE.getPopupAssistant_OwningProvider();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl <em>Element Type Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getElementTypeFilter()
		 * @generated
		 */
		EClass ELEMENT_TYPE_FILTER = eINSTANCE.getElementTypeFilter();

		/**
		 * The meta object literal for the '<em><b>Element Type ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID = eINSTANCE.getElementTypeFilter_ElementTypeID();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_FILTER__ELEMENT_TYPE = eINSTANCE.getElementTypeFilter_ElementType();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ELEMENT_TYPE_FILTER__PROVIDER = eINSTANCE.getElementTypeFilter_Provider();

		/**
		 * The meta object literal for the '<em><b>Get Element Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ELEMENT_TYPE_FILTER___GET_ELEMENT_TYPE = eINSTANCE.getElementTypeFilter__GetElementType();

		/**
		 * The meta object literal for the '<em><b>Get Provider</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ELEMENT_TYPE_FILTER___GET_PROVIDER = eINSTANCE.getElementTypeFilter__GetProvider();

		/**
		 * The meta object literal for the '{@link org.eclipse.gmf.runtime.common.core.service.IProvider <em>IProvider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.common.core.service.IProvider
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIProvider()
		 * @generated
		 */
		EClass IPROVIDER = eINSTANCE.getIProvider();

		/**
		 * The meta object literal for the '<em><b>Listener</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute IPROVIDER__LISTENER = eINSTANCE.getIProvider_Listener();

		/**
		 * The meta object literal for the '<em><b>Provides</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IPROVIDER___PROVIDES__IOPERATION = eINSTANCE.getIProvider__Provides__IOperation();

		/**
		 * The meta object literal for the '<em><b>Add Provider Change Listener</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IPROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = eINSTANCE.getIProvider__AddProviderChangeListener__IProviderChangeListener();

		/**
		 * The meta object literal for the '<em><b>Remove Provider Change Listener</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IPROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = eINSTANCE.getIProvider__RemoveProviderChangeListener__IProviderChangeListener();

		/**
		 * The meta object literal for the '{@link org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider <em>IModeling Assistant Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIModelingAssistantProvider()
		 * @generated
		 */
		EClass IMODELING_ASSISTANT_PROVIDER = eINSTANCE.getIModelingAssistantProvider();

		/**
		 * The meta object literal for the '<em><b>Get Types</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetTypes__String_IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Rel Types On Source</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetRelTypesOnSource__IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Rel Types On Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetRelTypesOnTarget__IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Rel Types On Source And Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetRelTypesOnSourceAndTarget__IAdaptable_IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Rel Types For SRE On Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetRelTypesForSREOnTarget__IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Rel Types For SRE On Source</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetRelTypesForSREOnSource__IAdaptable();

		/**
		 * The meta object literal for the '<em><b>Get Types For Source</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = eINSTANCE.getIModelingAssistantProvider__GetTypesForSource__IAdaptable_IElementType();

		/**
		 * The meta object literal for the '<em><b>Get Types For Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = eINSTANCE.getIModelingAssistantProvider__GetTypesForTarget__IAdaptable_IElementType();

		/**
		 * The meta object literal for the '<em><b>Select Existing Element For Source</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = eINSTANCE.getIModelingAssistantProvider__SelectExistingElementForSource__IAdaptable_IElementType();

		/**
		 * The meta object literal for the '<em><b>Select Existing Element For Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = eINSTANCE.getIModelingAssistantProvider__SelectExistingElementForTarget__IAdaptable_IElementType();

		/**
		 * The meta object literal for the '<em><b>Get Types For Popup Bar</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation IMODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE = eINSTANCE.getIModelingAssistantProvider__GetTypesForPopupBar__IAdaptable();

		/**
		 * The meta object literal for the '<em>Element Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getElementType()
		 * @generated
		 */
		EDataType ELEMENT_TYPE = eINSTANCE.getElementType();

		/**
		 * The meta object literal for the '<em>Client Context</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.emf.type.core.IClientContext
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getClientContext()
		 * @generated
		 */
		EDataType CLIENT_CONTEXT = eINSTANCE.getClientContext();

		/**
		 * The meta object literal for the '<em>IOperation</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.common.core.service.IOperation
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIOperation()
		 * @generated
		 */
		EDataType IOPERATION = eINSTANCE.getIOperation();

		/**
		 * The meta object literal for the '<em>IProvider Change Listener</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIProviderChangeListener()
		 * @generated
		 */
		EDataType IPROVIDER_CHANGE_LISTENER = eINSTANCE.getIProviderChangeListener();

		/**
		 * The meta object literal for the '<em>IAdaptable</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.core.runtime.IAdaptable
		 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantPackageImpl#getIAdaptable()
		 * @generated
		 */
		EDataType IADAPTABLE = eINSTANCE.getIAdaptable();

	}

} // AssistantPackage
