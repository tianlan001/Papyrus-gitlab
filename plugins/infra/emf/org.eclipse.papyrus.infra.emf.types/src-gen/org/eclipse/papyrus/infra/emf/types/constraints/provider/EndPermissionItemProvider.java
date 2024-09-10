/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.constraints.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceFactory;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.EndKind;
import org.eclipse.papyrus.infra.emf.types.constraints.EndPermission;
import org.eclipse.papyrus.infra.filters.FiltersFactory;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.papyrus.infra.filters.provider.FilteredElementItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class EndPermissionItemProvider extends FilteredElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EndPermissionItemProvider(AdapterFactory adapterFactory) {
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

			addEndKindPropertyDescriptor(object);
			addPermittedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the End Kind feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addEndKindPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_EndPermission_endKind_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_EndPermission_endKind_feature", "_UI_EndPermission_type"),
				ConstraintAdvicePackage.Literals.END_PERMISSION__END_KIND,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Permitted feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addPermittedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_EndPermission_permitted_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_EndPermission_permitted_feature", "_UI_EndPermission_type"),
				ConstraintAdvicePackage.Literals.END_PERMISSION__PERMITTED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER);
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
	 * This returns EndPermission.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EndPermission"));
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
		EndPermission endPermission = (EndPermission) object;
		EndKind endKind = endPermission.getEndKind();
		String typeString;

		if (endKind == null) {
			typeString = getString("_UI_EndPermission_type");
		} else {
			typeString = getString("_UI_EndPermission_type_" + endKind.name().toLowerCase());
		}

		return typeString + " " + getPermissionModifier(endPermission);
	}

	protected String getPermissionModifier(EndPermission endPermission) {
		return getString(endPermission.isPermitted() ? "_UI_ReferencePermission_granted" : "_UI_ReferencePermission_denied");
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

		switch (notification.getFeatureID(EndPermission.class)) {
		case ConstraintAdvicePackage.END_PERMISSION__END_KIND:
		case ConstraintAdvicePackage.END_PERMISSION__PERMITTED:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
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
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(FiltersPackage.Literals.FILTERED_ELEMENT__FILTER,
				ConstraintAdviceFactory.eINSTANCE.createElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER,
				ConstraintAdviceFactory.eINSTANCE.createElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER,
				FiltersFactory.eINSTANCE.createCompoundFilter()));

		newChildDescriptors.add(createChildParameter(ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER,
				FiltersFactory.eINSTANCE.createEquals()));

		newChildDescriptors.add(createChildParameter(ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER,
				FiltersFactory.eINSTANCE.createFilterReference()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == FiltersPackage.Literals.FILTERED_ELEMENT__FILTER ||
				childFeature == ConstraintAdvicePackage.Literals.END_PERMISSION__END_FILTER;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	@Override
	protected boolean isWrappingNeeded(Object object) {
		return true; // EndPermission owns filters in two different roles
	}

	@Override
	protected Object createWrapper(EObject object, EStructuralFeature feature, Object value, int index) {
		Object result = value;
		EReference reference = feature instanceof EReference ? (EReference) feature : null;

		if (reference != null && reference.isContainment() && FiltersPackage.Literals.FILTER.isSuperTypeOf(reference.getEReferenceType())) {
			// Wrapping is needed for contained filters
			String pattern = getString(String.format("_UI_EndPermission_%s_role", feature.getName()));

			result = new DelegatingWrapperItemProvider(value, object, feature, index, adapterFactory) {
				@Override
				public String getText(Object object) {
					return NLS.bind(pattern, super.getText(object));
				}
			};
		} else {
			result = super.createWrapper(object, feature, value, index);
		}

		return result;
	}

}
