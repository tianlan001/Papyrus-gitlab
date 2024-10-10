/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols;

import org.eclipse.eef.EefPackage;

import org.eclipse.eef.ext.widgets.reference.eefextwidgetsreference.EefExtWidgetsReferencePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsFactory
 * @model kind="package"
 * @generated
 */
public interface EefAdvancedControlsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "eefadvancedcontrols"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/sirius/properties/eef/advanced/controls"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eef-advanced-controls"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EefAdvancedControlsPackage eINSTANCE = org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFExtEditableReferenceDescriptionImpl <em>EEF Ext Editable Reference Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFExtEditableReferenceDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFExtEditableReferenceDescription()
	 * @generated
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__IDENTIFIER = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__HELP_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Property Validation Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__PROPERTY_VALIDATION_RULES = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__PROPERTY_VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION;

	/**
	 * The feature id for the '<em><b>On Click Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__ON_CLICK_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__ON_CLICK_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__STYLE = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES;

	/**
	 * The feature id for the '<em><b>Remove Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Create Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Browse Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EEF Ext Editable Reference Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT = EefExtWidgetsReferencePackage.EEF_EXT_REFERENCE_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFContainerBorderDescriptionImpl <em>EEF Container Border Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFContainerBorderDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFContainerBorderDescription()
	 * @generated
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION__IDENTIFIER = EefPackage.EEF_CONTAINER_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION__CONTROLS = EefPackage.EEF_CONTAINER_DESCRIPTION__CONTROLS;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION__LAYOUT = EefPackage.EEF_CONTAINER_DESCRIPTION__LAYOUT;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION = EefPackage.EEF_CONTAINER_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EEF Container Border Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_CONTAINER_BORDER_DESCRIPTION_FEATURE_COUNT = EefPackage.EEF_CONTAINER_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFLanguageExpressionDescriptionImpl <em>EEF Language Expression Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFLanguageExpressionDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFLanguageExpressionDescription()
	 * @generated
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION__IDENTIFIER = EefPackage.EEF_WIDGET_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION__LABEL_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION__HELP_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION__IS_ENABLED_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Property Validation Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION__PROPERTY_VALIDATION_RULES = EefPackage.EEF_WIDGET_DESCRIPTION__PROPERTY_VALIDATION_RULES;

	/**
	 * The number of structural features of the '<em>EEF Language Expression Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_LANGUAGE_EXPRESSION_DESCRIPTION_FEATURE_COUNT = EefPackage.EEF_WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFProfileApplicationDescriptionImpl <em>EEF Profile Application Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFProfileApplicationDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFProfileApplicationDescription()
	 * @generated
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION__IDENTIFIER = EefPackage.EEF_WIDGET_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION = EefPackage.EEF_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Property Validation Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION__PROPERTY_VALIDATION_RULES = EefPackage.EEF_WIDGET_DESCRIPTION__PROPERTY_VALIDATION_RULES;

	/**
	 * The number of structural features of the '<em>EEF Profile Application Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_PROFILE_APPLICATION_DESCRIPTION_FEATURE_COUNT = EefPackage.EEF_WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl <em>EEF Stereotype Application Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFStereotypeApplicationDescription()
	 * @generated
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION__IDENTIFIER = EefPackage.EEF_CONTROL_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS = EefPackage.EEF_CONTROL_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT = EefPackage.EEF_CONTROL_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = EefPackage.EEF_CONTROL_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = EefPackage.EEF_CONTROL_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>EEF Stereotype Application Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_STEREOTYPE_APPLICATION_DESCRIPTION_FEATURE_COUNT = EefPackage.EEF_CONTROL_DESCRIPTION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFInputContentPapyrusReferenceDescriptionImpl <em>EEF Input Content Papyrus Reference Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFInputContentPapyrusReferenceDescriptionImpl
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFInputContentPapyrusReferenceDescription()
	 * @generated
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION = 5;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__IDENTIFIER = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__LABEL_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__HELP_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__HELP_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__IS_ENABLED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Property Validation Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__PROPERTY_VALIDATION_RULES = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__PROPERTY_VALIDATION_RULES;

	/**
	 * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_NAME_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REFERENCE_OWNER_EXPRESSION;

	/**
	 * The feature id for the '<em><b>On Click Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__ON_CLICK_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__ON_CLICK_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__STYLE = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CONDITIONAL_STYLES;

	/**
	 * The feature id for the '<em><b>Remove Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Create Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Browse Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Input Content Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EEF Input Content Papyrus Reference Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION_FEATURE_COUNT = EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription <em>EEF Ext Editable Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Ext Editable Reference Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription
	 * @generated
	 */
	EClass getEEFExtEditableReferenceDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getRemoveExpression <em>Remove Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remove Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getRemoveExpression()
	 * @see #getEEFExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getEEFExtEditableReferenceDescription_RemoveExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getCreateExpression <em>Create Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Create Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getCreateExpression()
	 * @see #getEEFExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getEEFExtEditableReferenceDescription_CreateExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getBrowseExpression <em>Browse Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Browse Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription#getBrowseExpression()
	 * @see #getEEFExtEditableReferenceDescription()
	 * @generated
	 */
	EAttribute getEEFExtEditableReferenceDescription_BrowseExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription <em>EEF Container Border Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Container Border Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription
	 * @generated
	 */
	EClass getEEFContainerBorderDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription#getLabelExpression <em>Label Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription#getLabelExpression()
	 * @see #getEEFContainerBorderDescription()
	 * @generated
	 */
	EAttribute getEEFContainerBorderDescription_LabelExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFLanguageExpressionDescription <em>EEF Language Expression Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Language Expression Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFLanguageExpressionDescription
	 * @generated
	 */
	EClass getEEFLanguageExpressionDescription();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFProfileApplicationDescription <em>EEF Profile Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Profile Application Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFProfileApplicationDescription
	 * @generated
	 */
	EClass getEEFProfileApplicationDescription();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription <em>EEF Stereotype Application Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Stereotype Application Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription
	 * @generated
	 */
	EClass getEEFStereotypeApplicationDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getLabelExpression()
	 * @see #getEEFStereotypeApplicationDescription()
	 * @generated
	 */
	EAttribute getEEFStereotypeApplicationDescription_LabelExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getHelpExpression()
	 * @see #getEEFStereotypeApplicationDescription()
	 * @generated
	 */
	EAttribute getEEFStereotypeApplicationDescription_HelpExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription <em>EEF Input Content Papyrus Reference Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EEF Input Content Papyrus Reference Description</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription
	 * @generated
	 */
	EClass getEEFInputContentPapyrusReferenceDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription#getInputContentExpression <em>Input Content Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Input Content Expression</em>'.
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription#getInputContentExpression()
	 * @see #getEEFInputContentPapyrusReferenceDescription()
	 * @generated
	 */
	EAttribute getEEFInputContentPapyrusReferenceDescription_InputContentExpression();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EefAdvancedControlsFactory getEefAdvancedControlsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFExtEditableReferenceDescriptionImpl <em>EEF Ext Editable Reference Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFExtEditableReferenceDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFExtEditableReferenceDescription()
		 * @generated
		 */
		EClass EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION = eINSTANCE.getEEFExtEditableReferenceDescription();

		/**
		 * The meta object literal for the '<em><b>Remove Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION = eINSTANCE.getEEFExtEditableReferenceDescription_RemoveExpression();

		/**
		 * The meta object literal for the '<em><b>Create Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION = eINSTANCE.getEEFExtEditableReferenceDescription_CreateExpression();

		/**
		 * The meta object literal for the '<em><b>Browse Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION = eINSTANCE.getEEFExtEditableReferenceDescription_BrowseExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFContainerBorderDescriptionImpl <em>EEF Container Border Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFContainerBorderDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFContainerBorderDescription()
		 * @generated
		 */
		EClass EEF_CONTAINER_BORDER_DESCRIPTION = eINSTANCE.getEEFContainerBorderDescription();

		/**
		 * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getEEFContainerBorderDescription_LabelExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFLanguageExpressionDescriptionImpl <em>EEF Language Expression Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFLanguageExpressionDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFLanguageExpressionDescription()
		 * @generated
		 */
		EClass EEF_LANGUAGE_EXPRESSION_DESCRIPTION = eINSTANCE.getEEFLanguageExpressionDescription();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFProfileApplicationDescriptionImpl <em>EEF Profile Application Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFProfileApplicationDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFProfileApplicationDescription()
		 * @generated
		 */
		EClass EEF_PROFILE_APPLICATION_DESCRIPTION = eINSTANCE.getEEFProfileApplicationDescription();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl <em>EEF Stereotype Application Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFStereotypeApplicationDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFStereotypeApplicationDescription()
		 * @generated
		 */
		EClass EEF_STEREOTYPE_APPLICATION_DESCRIPTION = eINSTANCE.getEEFStereotypeApplicationDescription();

		/**
		 * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getEEFStereotypeApplicationDescription_LabelExpression();

		/**
		 * The meta object literal for the '<em><b>Help Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION = eINSTANCE.getEEFStereotypeApplicationDescription_HelpExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFInputContentPapyrusReferenceDescriptionImpl <em>EEF Input Content Papyrus Reference Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFInputContentPapyrusReferenceDescriptionImpl
		 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsPackageImpl#getEEFInputContentPapyrusReferenceDescription()
		 * @generated
		 */
		EClass EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION = eINSTANCE.getEEFInputContentPapyrusReferenceDescription();

		/**
		 * The meta object literal for the '<em><b>Input Content Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION = eINSTANCE.getEEFInputContentPapyrusReferenceDescription_InputContentExpression();

	}

} //EefAdvancedControlsPackage
