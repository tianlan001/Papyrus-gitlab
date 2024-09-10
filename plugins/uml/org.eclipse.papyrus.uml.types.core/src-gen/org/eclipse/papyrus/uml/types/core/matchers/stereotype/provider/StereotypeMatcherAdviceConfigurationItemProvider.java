/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.papyrus.infra.types.provider.AbstractAdviceBindingConfigurationItemProvider;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherFactory;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;

import org.eclipse.uml2.common.edit.command.SubsetSupersetSetCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypeMatcherAdviceConfigurationItemProvider extends AbstractAdviceBindingConfigurationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeMatcherAdviceConfigurationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addMatchedTypePropertyDescriptor(object);
			addStereotypesQualifiedNamesPropertyDescriptor(object);
			addProfileUriPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Matched Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMatchedTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractMatcherConfiguration_matchedType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractMatcherConfiguration_matchedType_feature", "_UI_AbstractMatcherConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Stereotypes Qualified Names feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStereotypesQualifiedNamesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StereotypeApplicationMatcherConfiguration_stereotypesQualifiedNames_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_StereotypeApplicationMatcherConfiguration_stereotypesQualifiedNames_feature", "_UI_StereotypeApplicationMatcherConfiguration_type"),
				 StereotypeApplicationMatcherPackage.Literals.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Profile Uri feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addProfileUriPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StereotypeApplicationMatcherConfiguration_profileUri_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_StereotypeApplicationMatcherConfiguration_profileUri_feature", "_UI_StereotypeApplicationMatcherConfiguration_type"),
				 StereotypeApplicationMatcherPackage.Literals.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns StereotypeMatcherAdviceConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/StereotypeMatcherAdviceConfiguration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((StereotypeMatcherAdviceConfiguration)object).getDescription();
		return label == null || label.length() == 0 ?
			getString("_UI_StereotypeMatcherAdviceConfiguration_type") :
			getString("_UI_StereotypeMatcherAdviceConfiguration_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(StereotypeMatcherAdviceConfiguration.class)) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION,
				 StereotypeApplicationMatcherFactory.eINSTANCE.createStereotypeApplicationMatcherConfiguration()));

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION,
				 StereotypeApplicationMatcherFactory.eINSTANCE.createStereotypeMatcherAdviceConfiguration()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return StereotypeApplicationMatcherConfigurationEditPlugin.INSTANCE;
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == ElementTypesConfigurationsPackage.Literals.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.CONFIGURATION_ELEMENT__OWNING_TYPE}, null, value);
		}
		if (feature == ElementTypesConfigurationsPackage.Literals.CONFIGURATION_ELEMENT__OWNING_TYPE) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE}, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
