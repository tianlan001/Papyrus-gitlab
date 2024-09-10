/**
 * Copyright (c) 2012 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.toolsmiths.model.customizationplugin.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CustomizationPluginFactoryImpl extends EFactoryImpl implements CustomizationPluginFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CustomizationPluginFactory init() {
		try {
			CustomizationPluginFactory theCustomizationPluginFactory = (CustomizationPluginFactory)EPackage.Registry.INSTANCE.getEFactory(CustomizationPluginPackage.eNS_URI);
			if (theCustomizationPluginFactory != null) {
				return theCustomizationPluginFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CustomizationPluginFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomizationPluginFactoryImpl() {
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
			case CustomizationPluginPackage.CUSTOMIZATION_CONFIGURATION: return createCustomizationConfiguration();
			case CustomizationPluginPackage.PROPERTY_VIEW: return createPropertyView();
			case CustomizationPluginPackage.UI_CUSTOM: return createUICustom();
			case CustomizationPluginPackage.MODEL_TEMPLATE: return createModelTemplate();
			case CustomizationPluginPackage.PALETTE: return createPalette();
			case CustomizationPluginPackage.PROFILE: return createProfile();
			case CustomizationPluginPackage.UML_MODEL: return createUMLModel();
			case CustomizationPluginPackage.CONSTRAINTS_ENVIRONMENT: return createConstraintsEnvironment();
			case CustomizationPluginPackage.PROPERTY_VIEW_ENVIRONMENT: return createPropertyViewEnvironment();
			case CustomizationPluginPackage.TABLE_CONFIGURATION: return createTableConfiguration();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomizationConfiguration createCustomizationConfiguration() {
		CustomizationConfigurationImpl customizationConfiguration = new CustomizationConfigurationImpl();
		return customizationConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyView createPropertyView() {
		PropertyViewImpl propertyView = new PropertyViewImpl();
		return propertyView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UICustom createUICustom() {
		UICustomImpl uiCustom = new UICustomImpl();
		return uiCustom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelTemplate createModelTemplate() {
		ModelTemplateImpl modelTemplate = new ModelTemplateImpl();
		return modelTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Palette createPalette() {
		PaletteImpl palette = new PaletteImpl();
		return palette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Profile createProfile() {
		ProfileImpl profile = new ProfileImpl();
		return profile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLModel createUMLModel() {
		UMLModelImpl umlModel = new UMLModelImpl();
		return umlModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintsEnvironment createConstraintsEnvironment() {
		ConstraintsEnvironmentImpl constraintsEnvironment = new ConstraintsEnvironmentImpl();
		return constraintsEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyViewEnvironment createPropertyViewEnvironment() {
		PropertyViewEnvironmentImpl propertyViewEnvironment = new PropertyViewEnvironmentImpl();
		return propertyViewEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TableConfiguration createTableConfiguration() {
		TableConfigurationImpl tableConfiguration = new TableConfigurationImpl();
		return tableConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomizationPluginPackage getCustomizationPluginPackage() {
		return (CustomizationPluginPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CustomizationPluginPackage getPackage() {
		return CustomizationPluginPackage.eINSTANCE;
	}

} //CustomizationPluginFactoryImpl
