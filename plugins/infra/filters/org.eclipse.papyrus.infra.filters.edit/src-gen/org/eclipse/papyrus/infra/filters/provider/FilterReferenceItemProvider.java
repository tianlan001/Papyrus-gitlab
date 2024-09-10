/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.filters.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.FilterReference;
import org.eclipse.papyrus.infra.filters.FiltersFactory;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.uml2.common.edit.command.SubsetSupersetSetCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.filters.FilterReference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FilterReferenceItemProvider
		extends ItemProviderAdapter
		implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FilterReferenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addFilterPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Filter_name_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Filter_name_feature", "_UI_Filter_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				FiltersPackage.Literals.FILTER__NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Filter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addFilterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FilterReference_filter_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_FilterReference_filter_feature", "_UI_FilterReference_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				FiltersPackage.Literals.FILTER_REFERENCE__FILTER,
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
	 *
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns FilterReference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/FilterReference")); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
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
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		StringBuilder result = new StringBuilder();
		FilterReference filterReference = (FilterReference) object;

		result.append(getString("_UI_FilterReference_type")); //$NON-NLS-1$

		String label = filterReference.getName();
		if (label != null && !label.isBlank()) {
			result.append(" "); //$NON-NLS-1$
			result.append(label);
		}

		Filter reference = filterReference.getFilter();
		if (reference != null) {
			String referenceLabel = null;
			IItemLabelProvider provider = (IItemLabelProvider) getRootAdapterFactory().adapt(reference, IItemLabelProvider.class);
			if (provider != null) {
				referenceLabel = provider.getText(reference);
			}
			if (referenceLabel != null && !referenceLabel.isBlank()) {
				result.append(" -> "); //$NON-NLS-1$
				result.append(referenceLabel);
			}
		}

		return result.toString();
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(FilterReference.class)) {
		case FiltersPackage.FILTER_REFERENCE__NAME:
		case FiltersPackage.FILTER_REFERENCE__FILTER:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case FiltersPackage.FILTER_REFERENCE__OWNED_FILTER:
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
	 *
	 * @generated
	 */
	protected void collectNewChildDescriptorsGen(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createFilterReference()));

		newChildDescriptors.add(createChildParameter(FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createCompoundFilter()));

		newChildDescriptors.add(createChildParameter(FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createEquals()));
	}
	
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		collectNewChildDescriptorsGen(newChildDescriptors, object);

		// It seems pointless to create a filter reference owned by a filter reference
		newChildDescriptors.removeIf(descriptor -> descriptor instanceof CommandParameter && ((CommandParameter) descriptor).getValue() instanceof FilterReference);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ((IChildCreationExtender) adapterFactory).getResourceLocator();
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] { FiltersPackage.Literals.FILTER_REFERENCE__FILTER }, null, value);
		}
		if (feature == FiltersPackage.Literals.FILTER_REFERENCE__FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] { FiltersPackage.Literals.FILTER_REFERENCE__OWNED_FILTER }, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
