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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.Reference;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.emf.types.constraints.Reference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ReferenceItemProvider extends ReferencePermissionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ReferenceItemProvider(AdapterFactory adapterFactory) {
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

			addReferencePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Reference feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected void addReferencePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Reference_reference_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Reference_reference_feature", "_UI_Reference_type"),
				ConstraintAdvicePackage.Literals.REFERENCE__REFERENCE,
				true,
				false,
				true,
				null,
				null,
				null) {
			@Override
			public Collection<?> getChoiceOfValues(Object object) {
				Collection<?> result = super.getChoiceOfValues(object);

				// Not the features in 'duplicates' or other annotations in a UML model
				result.removeIf(feature -> feature instanceof EStructuralFeature && ((EStructuralFeature) feature).eContainmentFeature() == EcorePackage.Literals.EANNOTATION__CONTENTS);
				return result;
			}

			@Override
			public IItemLabelProvider getLabelProvider(Object object) {
				return new IItemLabelProvider() {

					@Override
					public String getText(Object value) {
						if (value instanceof EStructuralFeature) {
							EStructuralFeature feature = (EStructuralFeature) value;
							EClass eClass = feature.getEContainingClass();
							// eClass is null if the 'feature' is an unresolved proxy
							EPackage ePackage = eClass == null ? null : eClass.getEPackage();

							if (eClass == null || ePackage == null) {
								return String.format("%s [%s]", feature.getName(), EcoreUtil.getURI(feature));
							}

							EClassifier type = feature.getEType();
							String typeName = type == null ? null : type.getName();
							if (typeName == null || typeName.isBlank()) {
								typeName = "void"; //$NON-NLS-1$
							}
							return String.format("%s : %s - %s::%s [%s]", feature.getName(), typeName, ePackage.getName(), eClass.getName(), ePackage.getNsURI()); //$NON-NLS-1$
						}
						return itemDelegator.getText(value);
					}

					@Override
					public Object getImage(Object value) {
						return itemDelegator.getImage(value);
					}
				};
			}
		});
	}

	/**
	 * This returns Reference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Reference"));
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
		Reference reference = (Reference) object;
		String refName = reference.getReference() == null ? null : reference.getReference().getName();

		if (refName == null) {
			return getString("_UI_Reference_type") + " " + getPermissionModifier(reference);
		}
		return getString("_UI_Reference_type") + " '" + refName + "' " + getPermissionModifier(reference);
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

		switch (notification.getFeatureID(Reference.class)) {
		case ConstraintAdvicePackage.REFERENCE__REFERENCE:
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
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
