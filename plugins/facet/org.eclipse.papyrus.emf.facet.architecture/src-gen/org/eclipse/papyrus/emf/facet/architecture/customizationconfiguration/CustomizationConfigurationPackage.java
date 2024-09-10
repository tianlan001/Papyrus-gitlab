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
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

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
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface CustomizationConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "customizationconfiguration"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/emf/facet/architecture/configuration"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "customizationconfiguration"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	CustomizationConfigurationPackage eINSTANCE = org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl <em>EMF Facet Tree Viewer Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getEMFFacetTreeViewerConfiguration()
	 * @generated
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION__DESCRIPTION = ArchitecturePackage.TREE_VIEWER_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Customization References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES = ArchitecturePackage.TREE_VIEWER_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS = ArchitecturePackage.TREE_VIEWER_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EMF Facet Tree Viewer Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION_FEATURE_COUNT = ArchitecturePackage.TREE_VIEWER_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>EMF Facet Tree Viewer Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_FACET_TREE_VIEWER_CONFIGURATION_OPERATION_COUNT = ArchitecturePackage.TREE_VIEWER_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl <em>Customization Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getCustomizationReference()
	 * @generated
	 */
	int CUSTOMIZATION_REFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOMIZATION_REFERENCE__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Referenced Customization</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION = 1;

	/**
	 * The feature id for the '<em><b>Application Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOMIZATION_REFERENCE__APPLICATION_RULE = 2;

	/**
	 * The number of structural features of the '<em>Customization Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOMIZATION_REFERENCE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Customization Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOMIZATION_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule <em>IApplication Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getIApplicationRule()
	 * @generated
	 */
	int IAPPLICATION_RULE = 2;

	/**
	 * The number of structural features of the '<em>IApplication Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IAPPLICATION_RULE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IApplication Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IAPPLICATION_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.AbsoluteOrderImpl <em>Absolute Order</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.AbsoluteOrderImpl
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getAbsoluteOrder()
	 * @generated
	 */
	int ABSOLUTE_ORDER = 3;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_ORDER__ORDER = IAPPLICATION_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Absolute Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_ORDER_FEATURE_COUNT = IAPPLICATION_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Absolute Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSOLUTE_ORDER_OPERATION_COUNT = IAPPLICATION_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl <em>Relative Order</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getRelativeOrder()
	 * @generated
	 */
	int RELATIVE_ORDER = 4;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIVE_ORDER__LOCATION = IAPPLICATION_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Relative Customization Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE = IAPPLICATION_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Relative Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIVE_ORDER_FEATURE_COUNT = IAPPLICATION_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Relative Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIVE_ORDER_OPERATION_COUNT = IAPPLICATION_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RedefinitionImpl <em>Redefinition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RedefinitionImpl
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getRedefinition()
	 * @generated
	 */
	int REDEFINITION = 5;

	/**
	 * The feature id for the '<em><b>Redefined Customization Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE = IAPPLICATION_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Redefinition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REDEFINITION_FEATURE_COUNT = IAPPLICATION_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Redefinition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REDEFINITION_OPERATION_COUNT = IAPPLICATION_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location <em>Location</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 6;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration <em>EMF Facet Tree Viewer Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>EMF Facet Tree Viewer Configuration</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration
	 * @generated
	 */
	EClass getEMFFacetTreeViewerConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getCustomizationReferences <em>Customization References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Customization References</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getCustomizationReferences()
	 * @see #getEMFFacetTreeViewerConfiguration()
	 * @generated
	 */
	EReference getEMFFacetTreeViewerConfiguration_CustomizationReferences();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getExtends <em>Extends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Extends</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration#getExtends()
	 * @see #getEMFFacetTreeViewerConfiguration()
	 * @generated
	 */
	EReference getEMFFacetTreeViewerConfiguration_Extends();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference <em>Customization Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Customization Reference</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference
	 * @generated
	 */
	EClass getCustomizationReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getDescription()
	 * @see #getCustomizationReference()
	 * @generated
	 */
	EAttribute getCustomizationReference_Description();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getReferencedCustomization <em>Referenced Customization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Referenced Customization</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getReferencedCustomization()
	 * @see #getCustomizationReference()
	 * @generated
	 */
	EReference getCustomizationReference_ReferencedCustomization();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getApplicationRule <em>Application Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Application Rule</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference#getApplicationRule()
	 * @see #getCustomizationReference()
	 * @generated
	 */
	EReference getCustomizationReference_ApplicationRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule <em>IApplication Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>IApplication Rule</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule
	 * @generated
	 */
	EClass getIApplicationRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder <em>Absolute Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Absolute Order</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder
	 * @generated
	 */
	EClass getAbsoluteOrder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder#getOrder()
	 * @see #getAbsoluteOrder()
	 * @generated
	 */
	EAttribute getAbsoluteOrder_Order();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder <em>Relative Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Relative Order</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder
	 * @generated
	 */
	EClass getRelativeOrder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getLocation()
	 * @see #getRelativeOrder()
	 * @generated
	 */
	EAttribute getRelativeOrder_Location();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getRelativeCustomizationReference <em>Relative Customization Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Relative Customization Reference</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder#getRelativeCustomizationReference()
	 * @see #getRelativeOrder()
	 * @generated
	 */
	EReference getRelativeOrder_RelativeCustomizationReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition <em>Redefinition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Redefinition</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition
	 * @generated
	 */
	EClass getRedefinition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition#getRedefinedCustomizationReference <em>Redefined Customization Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Redefined Customization Reference</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition#getRedefinedCustomizationReference()
	 * @see #getRedefinition()
	 * @generated
	 */
	EReference getRedefinition_RedefinedCustomizationReference();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Location</em>'.
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location
	 * @generated
	 */
	EEnum getLocation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CustomizationConfigurationFactory getCustomizationConfigurationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl <em>EMF Facet Tree Viewer Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getEMFFacetTreeViewerConfiguration()
		 * @generated
		 */
		EClass EMF_FACET_TREE_VIEWER_CONFIGURATION = eINSTANCE.getEMFFacetTreeViewerConfiguration();

		/**
		 * The meta object literal for the '<em><b>Customization References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES = eINSTANCE.getEMFFacetTreeViewerConfiguration_CustomizationReferences();

		/**
		 * The meta object literal for the '<em><b>Extends</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS = eINSTANCE.getEMFFacetTreeViewerConfiguration_Extends();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl <em>Customization Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getCustomizationReference()
		 * @generated
		 */
		EClass CUSTOMIZATION_REFERENCE = eINSTANCE.getCustomizationReference();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOMIZATION_REFERENCE__DESCRIPTION = eINSTANCE.getCustomizationReference_Description();

		/**
		 * The meta object literal for the '<em><b>Referenced Customization</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION = eINSTANCE.getCustomizationReference_ReferencedCustomization();

		/**
		 * The meta object literal for the '<em><b>Application Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CUSTOMIZATION_REFERENCE__APPLICATION_RULE = eINSTANCE.getCustomizationReference_ApplicationRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule <em>IApplication Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getIApplicationRule()
		 * @generated
		 */
		EClass IAPPLICATION_RULE = eINSTANCE.getIApplicationRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.AbsoluteOrderImpl <em>Absolute Order</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.AbsoluteOrderImpl
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getAbsoluteOrder()
		 * @generated
		 */
		EClass ABSOLUTE_ORDER = eINSTANCE.getAbsoluteOrder();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ABSOLUTE_ORDER__ORDER = eINSTANCE.getAbsoluteOrder_Order();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl <em>Relative Order</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getRelativeOrder()
		 * @generated
		 */
		EClass RELATIVE_ORDER = eINSTANCE.getRelativeOrder();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RELATIVE_ORDER__LOCATION = eINSTANCE.getRelativeOrder_Location();

		/**
		 * The meta object literal for the '<em><b>Relative Customization Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE = eINSTANCE.getRelativeOrder_RelativeCustomizationReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RedefinitionImpl <em>Redefinition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RedefinitionImpl
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getRedefinition()
		 * @generated
		 */
		EClass REDEFINITION = eINSTANCE.getRedefinition();

		/**
		 * The meta object literal for the '<em><b>Redefined Customization Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE = eINSTANCE.getRedefinition_RedefinedCustomizationReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location <em>Location</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location
		 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationPackageImpl#getLocation()
		 * @generated
		 */
		EEnum LOCATION = eINSTANCE.getLocation();

	}

} // CustomizationConfigurationPackage
