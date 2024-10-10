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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsFactory;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription;
import org.eclipse.sirius.properties.PropertiesFactory;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferenceFactory;
import org.eclipse.sirius.properties.provider.WidgetDescriptionItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypeApplicationDescriptionItemProvider extends WidgetDescriptionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationDescriptionItemProvider(AdapterFactory adapterFactory) {
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

			addExtendsPropertyDescriptor(object);
			addFilterControlsFromExtendedContainerExpressionPropertyDescriptor(object);
			addLabelExpressionPropertyDescriptor(object);
			addHelpExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Extends feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExtendsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AbstractContainerDescription_extends_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_AbstractContainerDescription_extends_feature", "_UI_AbstractContainerDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__EXTENDS,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Filter Controls From Extended Container Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFilterControlsFromExtendedContainerExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AbstractContainerDescription_filterControlsFromExtendedContainerExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_AbstractContainerDescription_filterControlsFromExtendedContainerExpression_feature", "_UI_AbstractContainerDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
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
				getString("_UI_StereotypeApplicationDescription_labelExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_StereotypeApplicationDescription_labelExpression_feature", "_UI_StereotypeApplicationDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				PropertiesAdvancedControlsPackage.Literals.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Help Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHelpExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_StereotypeApplicationDescription_helpExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_StereotypeApplicationDescription_helpExpression_feature", "_UI_StereotypeApplicationDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				PropertiesAdvancedControlsPackage.Literals.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS);
			childrenFeatures.add(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__LAYOUT);
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
	 * This returns StereotypeApplicationDescription.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/StereotypeApplicationDescription")); //$NON-NLS-1$
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
		String label = ((StereotypeApplicationDescription) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_StereotypeApplicationDescription_type") : //$NON-NLS-1$
				getString("_UI_StereotypeApplicationDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(StereotypeApplicationDescription.class)) {
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__EXTENDS:
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__FILTER_CONTROLS_FROM_EXTENDED_CONTAINER_EXPRESSION:
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__CONTROLS:
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION__LAYOUT:
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
				PropertiesFactory.eINSTANCE.createContainerDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createTextDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createButtonDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createLabelDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createCheckboxDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createSelectDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createDynamicMappingForDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createTextAreaDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createRadioDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createListDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createCustomDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesFactory.eINSTANCE.createHyperlinkDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__CONTROLS,
				PropertiesExtWidgetsReferenceFactory.eINSTANCE.createExtReferenceDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__LAYOUT,
				PropertiesFactory.eINSTANCE.createFillLayoutDescription()));

		newChildDescriptors.add(createChildParameter(PropertiesPackage.Literals.ABSTRACT_CONTAINER_DESCRIPTION__LAYOUT,
				PropertiesFactory.eINSTANCE.createGridLayoutDescription()));
	}

}
