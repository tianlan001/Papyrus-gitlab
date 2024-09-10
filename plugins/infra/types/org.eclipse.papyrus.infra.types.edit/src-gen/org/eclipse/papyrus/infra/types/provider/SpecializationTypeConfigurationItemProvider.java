/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Ansgar Radermacher - Bug 526162 (use different label for references)
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.types.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.uml2.common.edit.command.SubsetSupersetReplaceCommand;
import org.eclipse.uml2.common.edit.command.SubsetSupersetSetCommand;
import org.eclipse.uml2.common.edit.command.SupersetRemoveCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SpecializationTypeConfigurationItemProvider extends ElementTypeConfigurationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpecializationTypeConfigurationItemProvider(AdapterFactory adapterFactory) {
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

			addSpecializedTypesPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Specialized Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSpecializedTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SpecializationTypeConfiguration_specializedTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SpecializationTypeConfiguration_specializedTypes_feature", "_UI_SpecializationTypeConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__SPECIALIZED_TYPES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection<? extends EStructuralFeature> getChildrenFeaturesGen(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION);
			childrenFeatures.add(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION);
			childrenFeatures.add(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION);
		}
		return childrenFeatures;
	}
	
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			getChildrenFeaturesGen(object);

			// Ensure that owned advice comes after the matcher
			int matcherIndex = childrenFeatures.indexOf(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION);
			int adviceIndex = childrenFeatures.indexOf(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE);
			if (matcherIndex > adviceIndex) {
				childrenFeatures.add(matcherIndex, childrenFeatures.remove(adviceIndex));
			}
		}

		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns SpecializationTypeConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/SpecializationTypeConfiguration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String label = ((SpecializationTypeConfiguration)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_SpecializationTypeConfiguration_type") :
			String.format("%s (%s)", label, getString("_UI_SpecializationTypeConfiguration_type"));
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

		switch (notification.getFeatureID(SpecializationTypeConfiguration.class)) {
			case ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION:
			case ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION:
			case ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
				(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION,
				 ElementTypesConfigurationsFactory.eINSTANCE.createEditHelperAdviceConfiguration()));

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION,
				 ElementTypesConfigurationsFactory.eINSTANCE.createContainerConfiguration()));

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION,
				 ElementTypesConfigurationsFactory.eINSTANCE.createMatcherConfiguration()));
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection) {
		if (feature == ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS) {
			return new SupersetRemoveCommand(domain, owner, feature, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION}, collection);
		}
		return super.createRemoveCommand(domain, owner, feature, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, java.util.Collection)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value, Collection<?> collection) {
		if (feature == ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS) {
			return new SubsetSupersetReplaceCommand(domain, owner, feature, null, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION}, value, collection);
		}
		return super.createReplaceCommand(domain, owner, feature, value, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS}, null, value);
		}
		if (feature == ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS}, null, value);
		}
		if (feature == ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS}, null, value);
		}
		if (feature == ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] {ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION, ElementTypesConfigurationsPackage.Literals.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION}, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
