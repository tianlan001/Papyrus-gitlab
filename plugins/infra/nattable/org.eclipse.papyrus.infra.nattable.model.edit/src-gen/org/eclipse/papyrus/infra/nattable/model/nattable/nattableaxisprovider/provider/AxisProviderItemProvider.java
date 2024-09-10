/**
 * Copyright (c) 2013 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AxisProvider} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AxisProviderItemProvider extends AbstractAxisProviderItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AxisProviderItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS);
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
		String label = ((AxisProvider)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_AxisProvider_type") : //$NON-NLS-1$
			getString("_UI_AxisProvider_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(AxisProvider.class)) {
			case NattableaxisproviderPackage.AXIS_PROVIDER__AXIS:
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
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createIdTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEObjectAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEObjectTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createFeatureIdAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createFeatureIdTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEOperationAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEStructuralFeatureTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createEOperationTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createObjectIdAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createObjectIdTreeItemAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createAxisGroup()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createOperationIdAxis()));

		newChildDescriptors.add
			(createChildParameter
				(NattableaxisproviderPackage.Literals.AXIS_PROVIDER__AXIS,
				 NattableaxisFactory.eINSTANCE.createOperationIdTreeItemAxis()));
	}

}
