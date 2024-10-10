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
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsFactory;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;

import org.eclipse.sirius.properties.PropertiesPackage;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferenceFactory;

import org.eclipse.sirius.properties.provider.ContainerDescriptionItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ContainerBorderDescriptionItemProvider extends ContainerDescriptionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainerBorderDescriptionItemProvider(AdapterFactory adapterFactory) {
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

			addLabelExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Label Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ContainerBorderDescription_labelExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ContainerBorderDescription_labelExpression_feature", "_UI_ContainerBorderDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				PropertiesAdvancedControlsPackage.Literals.CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns ContainerBorderDescription.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ContainerBorderDescription")); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ContainerBorderDescription) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ContainerBorderDescription_type") : //$NON-NLS-1$
				getString("_UI_ContainerBorderDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ContainerBorderDescription.class)) {
		case PropertiesAdvancedControlsPackage.CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION:
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

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createExtEditableReferenceDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createContainerBorderDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createLanguageExpressionDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createProfileApplicationDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createStereotypeApplicationDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesAdvancedControlsFactory.eINSTANCE.createInputContentPapyrusReferenceDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesExtWidgetsReferenceFactory.eINSTANCE.createExtReferenceDescription()));
	}

}
