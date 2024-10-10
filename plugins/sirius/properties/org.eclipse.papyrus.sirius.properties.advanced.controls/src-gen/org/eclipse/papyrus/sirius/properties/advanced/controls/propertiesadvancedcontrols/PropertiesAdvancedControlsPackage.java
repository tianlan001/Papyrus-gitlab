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
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.sirius.properties.PropertiesPackage;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferencePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsFactory
 * @model kind="package"
 * @generated
 */
public interface PropertiesAdvancedControlsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "propertiesadvancedcontrols"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/sirius/properties/advanced/controls"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "properties-advanced-controls"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PropertiesAdvancedControlsPackage eINSTANCE = org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl <em>Ext Editable Reference Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getExtEditableReferenceDescription()
	 * @generated
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__NAME = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__DOCUMENTATION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__HELP_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION;

	/**
	 * The feature id for the '<em><b>On Click Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__ON_CLICK_OPERATION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__ON_CLICK_OPERATION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__STYLE = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__EXTENDS = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION__EXTENDS;

	/**
	 * The feature id for the '<em><b>Remove Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Create Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Browse Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Ext Editable Reference Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT = PropertiesExtWidgetsReferencePackage.EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ContainerBorderDescriptionImpl <em>Container Border Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ContainerBorderDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getContainerBorderDescription()
	 * @generated
	 */
	int CONTAINER_BORDER_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__NAME = PropertiesPackage.CONTAINER_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__LABEL = PropertiesPackage.CONTAINER_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__DOCUMENTATION = PropertiesPackage.CONTAINER_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__CONTROLS = PropertiesPackage.CONTAINER_DESCRIPTION__CONTROLS;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__LAYOUT = PropertiesPackage.CONTAINER_DESCRIPTION__LAYOUT;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__EXTENDS = PropertiesPackage.CONTAINER_DESCRIPTION__EXTENDS;

	/**
	 * The feature id for the '<em><b>Filter Controls From Extended Container Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION = PropertiesPackage.CONTAINER_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION = PropertiesPackage.CONTAINER_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Container Border Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_BORDER_DESCRIPTION_FEATURE_COUNT = PropertiesPackage.CONTAINER_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.LanguageExpressionDescriptionImpl <em>Language Expression Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.LanguageExpressionDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getLanguageExpressionDescription()
	 * @generated
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__NAME = PropertiesPackage.WIDGET_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__LABEL = PropertiesPackage.WIDGET_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__DOCUMENTATION = PropertiesPackage.WIDGET_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__LABEL_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__HELP_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION__IS_ENABLED_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Language Expression Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_EXPRESSION_DESCRIPTION_FEATURE_COUNT = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl <em>Profile Application Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getProfileApplicationDescription()
	 * @generated
	 */
	int PROFILE_APPLICATION_DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__NAME = PropertiesPackage.WIDGET_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__LABEL = PropertiesPackage.WIDGET_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__DOCUMENTATION = PropertiesPackage.WIDGET_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Profile Application Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLICATION_DESCRIPTION_FEATURE_COUNT = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl <em>Stereotype Application Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getStereotypeApplicationDescription()
	 * @generated
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__NAME = PropertiesPackage.WIDGET_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__LABEL = PropertiesPackage.WIDGET_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__DOCUMENTATION = PropertiesPackage.WIDGET_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Filter Controls From Extended Container Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Stereotype Application Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_DESCRIPTION_FEATURE_COUNT = PropertiesPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.InputContentPapyrusReferenceDescriptionImpl <em>Input Content Papyrus Reference Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.InputContentPapyrusReferenceDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getInputContentPapyrusReferenceDescription()
	 * @generated
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__NAME = EXT_EDITABLE_REFERENCE_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__LABEL = EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__DOCUMENTATION = EXT_EDITABLE_REFERENCE_DESCRIPTION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__LABEL_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__HELP_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION;

	/**
	 * The feature id for the '<em><b>On Click Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__ON_CLICK_OPERATION = EXT_EDITABLE_REFERENCE_DESCRIPTION__ON_CLICK_OPERATION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__STYLE = EXT_EDITABLE_REFERENCE_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES = EXT_EDITABLE_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__EXTENDS = EXT_EDITABLE_REFERENCE_DESCRIPTION__EXTENDS;

	/**
	 * The feature id for the '<em><b>Remove Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Create Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Browse Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Input Content Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION = EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Input Content Papyrus Reference Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION_FEATURE_COUNT = EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription <em>Ext Editable Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ext Editable Reference Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription
	 * @generated
	 */
	EClass getExtEditableReferenceDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getRemoveExpression <em>Remove Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remove Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getRemoveExpression()
	 * @see #getExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getExtEditableReferenceDescription_RemoveExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getCreateExpression <em>Create Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Create Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getCreateExpression()
	 * @see #getExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getExtEditableReferenceDescription_CreateExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getBrowseExpression <em>Browse Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Browse Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getBrowseExpression()
	 * @see #getExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getExtEditableReferenceDescription_BrowseExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription <em>Container Border Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container Border Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription
	 * @generated
	 */
	EClass getContainerBorderDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription#getLabelExpression <em>Label Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription#getLabelExpression()
	 * @see #getContainerBorderDescription()
	 * @generated
	 */
	EAttribute getContainerBorderDescription_LabelExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.LanguageExpressionDescription <em>Language Expression Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language Expression Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.LanguageExpressionDescription
	 * @generated
	 */
	EClass getLanguageExpressionDescription();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription <em>Profile Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Profile Application Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription
	 * @generated
	 */
	EClass getProfileApplicationDescription();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription <em>Stereotype Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stereotype Application Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription
	 * @generated
	 */
	EClass getStereotypeApplicationDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getLabelExpression()
	 * @see #getStereotypeApplicationDescription()
	 * @generated
	 */
	EAttribute getStereotypeApplicationDescription_LabelExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getHelpExpression()
	 * @see #getStereotypeApplicationDescription()
	 * @generated
	 */
	EAttribute getStereotypeApplicationDescription_HelpExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription <em>Input Content Papyrus Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Content Papyrus Reference Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription
	 * @generated
	 */
	EClass getInputContentPapyrusReferenceDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription#getInputContentExpression <em>Input Content Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Input Content Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription#getInputContentExpression()
	 * @see #getInputContentPapyrusReferenceDescription()
	 * @generated
	 */
	EAttribute getInputContentPapyrusReferenceDescription_InputContentExpression();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PropertiesAdvancedControlsFactory getPropertiesAdvancedControlsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl <em>Ext Editable Reference Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getExtEditableReferenceDescription()
		 * @generated
		 */
		EClass EXT_EDITABLE_REFERENCE_DESCRIPTION = eINSTANCE.getExtEditableReferenceDescription();

		/**
		 * The meta object literal for the '<em><b>Remove Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = eINSTANCE.getExtEditableReferenceDescription_RemoveExpression();

		/**
		 * The meta object literal for the '<em><b>Create Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = eINSTANCE.getExtEditableReferenceDescription_CreateExpression();

		/**
		 * The meta object literal for the '<em><b>Browse Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = eINSTANCE.getExtEditableReferenceDescription_BrowseExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ContainerBorderDescriptionImpl <em>Container Border Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ContainerBorderDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getContainerBorderDescription()
		 * @generated
		 */
		EClass CONTAINER_BORDER_DESCRIPTION = eINSTANCE.getContainerBorderDescription();

		/**
		 * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getContainerBorderDescription_LabelExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.LanguageExpressionDescriptionImpl <em>Language Expression Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.LanguageExpressionDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getLanguageExpressionDescription()
		 * @generated
		 */
		EClass LANGUAGE_EXPRESSION_DESCRIPTION = eINSTANCE.getLanguageExpressionDescription();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl <em>Profile Application Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getProfileApplicationDescription()
		 * @generated
		 */
		EClass PROFILE_APPLICATION_DESCRIPTION = eINSTANCE.getProfileApplicationDescription();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl <em>Stereotype Application Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.StereotypeApplicationDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getStereotypeApplicationDescription()
		 * @generated
		 */
		EClass STEREOTYPE_APPLICATION_DESCRIPTION = eINSTANCE.getStereotypeApplicationDescription();

		/**
		 * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getStereotypeApplicationDescription_LabelExpression();

		/**
		 * The meta object literal for the '<em><b>Help Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = eINSTANCE.getStereotypeApplicationDescription_HelpExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.InputContentPapyrusReferenceDescriptionImpl <em>Input Content Papyrus Reference Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.InputContentPapyrusReferenceDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsPackageImpl#getInputContentPapyrusReferenceDescription()
		 * @generated
		 */
		EClass INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION = eINSTANCE.getInputContentPapyrusReferenceDescription();

		/**
		 * The meta object literal for the '<em><b>Input Content Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION = eINSTANCE.getInputContentPapyrusReferenceDescription_InputContentExpression();

	}

} //PropertiesAdvancedControlsPackage
