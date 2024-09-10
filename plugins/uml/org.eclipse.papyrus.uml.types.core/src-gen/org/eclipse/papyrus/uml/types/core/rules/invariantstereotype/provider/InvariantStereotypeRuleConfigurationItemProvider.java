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
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.types.rulebased.provider.RuleConfigurationItemProvider;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InvariantStereotypeRuleConfigurationItemProvider extends RuleConfigurationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvariantStereotypeRuleConfigurationItemProvider(AdapterFactory adapterFactory) {
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

			addStereotypeQualifiedNamePropertyDescriptor(object);
			addRequiredProfilePropertyDescriptor(object);
			addStrictPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Stereotype Qualified Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStereotypeQualifiedNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_InvariantStereotypeRuleConfiguration_stereotypeQualifiedName_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_InvariantStereotypeRuleConfiguration_stereotypeQualifiedName_feature", "_UI_InvariantStereotypeRuleConfiguration_type"),
				InvariantStereotypeRuleConfigurationPackage.Literals.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Required Profile feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRequiredProfilePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_InvariantStereotypeRuleConfiguration_requiredProfile_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_InvariantStereotypeRuleConfiguration_requiredProfile_feature", "_UI_InvariantStereotypeRuleConfiguration_type"),
				InvariantStereotypeRuleConfigurationPackage.Literals.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Strict feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStrictPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_InvariantStereotypeRuleConfiguration_strict_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_InvariantStereotypeRuleConfiguration_strict_feature", "_UI_InvariantStereotypeRuleConfiguration_type"),
				InvariantStereotypeRuleConfigurationPackage.Literals.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns InvariantStereotypeRuleConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/InvariantStereotypeRuleConfiguration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((InvariantStereotypeRuleConfiguration) object).getStereotypeQualifiedName();
		return label == null || label.length() == 0 ? getString("_UI_InvariantStereotypeRuleConfiguration_type") : getString("_UI_InvariantStereotypeRuleConfiguration_type") + " " + label;
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

		switch (notification.getFeatureID(InvariantStereotypeRuleConfiguration.class)) {
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE:
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT:
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
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return InvariantStereotypeRuleConfigurationEditPlugin.INSTANCE;
	}

}
