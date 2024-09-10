/**
* Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *
 */
package org.eclipse.papyrus.infra.architecture.representation.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationFactory;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.provider.RepresentationKindItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusRepresentationKindItemProvider extends RepresentationKindItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public PapyrusRepresentationKindItemProvider(AdapterFactory adapterFactory) {
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

			addParentPropertyDescriptor(object);
			addImplementationIDPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Parent feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addParentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_PapyrusRepresentationKind_parent_feature"), //$NON-NLS-1$
				getString("_UI_PapyrusRepresentationKind_parent_description"), //$NON-NLS-1$
				RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__PARENT,
				true,
				false,
				true,
				null,
				getString("_UI_RepresentationKindPropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This adds a property descriptor for the Implementation ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addImplementationIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_PapyrusRepresentationKind_implementationID_feature"), //$NON-NLS-1$
				getString("_UI_PapyrusRepresentationKind_implementationID_description"), //$NON-NLS-1$
				RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				getString("_UI_RepresentationKindPropertyCategory"), //$NON-NLS-1$
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
			childrenFeatures.add(RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES);
			childrenFeatures.add(RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((PapyrusRepresentationKind) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_PapyrusRepresentationKind_type") : //$NON-NLS-1$
				getString("_UI_PapyrusRepresentationKind_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected static String getTextFor(PapyrusRepresentationKind view) {
		ArchitectureDescriptionLanguage lang = (ArchitectureDescriptionLanguage) view.eContainer();

		String langName;
		if (lang == null) {
			// The eContainer will be null if the view is an unresolved proxy
			if (view.eIsProxy()) {
				return "<unresolved view> " + EcoreUtil.getURI(view);
			}
			langName = null;
		} else {
			langName = lang.getName();
		}

		String viewName = view.getName();
		String viewID = view.getImplementationID();

		if (viewName == null) {
			viewName = "<unnamed view>";
		}


		String format = langName != null
				? (viewID != null ? "%1$s :: %2$s [%3$s]" : "%1$s :: %2$s")
				: (viewID != null ? "%2$s [%3$s]" : "%2$s");
		return String.format(format, langName, viewName, viewID);
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

		switch (notification.getFeatureID(PapyrusRepresentationKind.class)) {
		case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
		case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
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

		newChildDescriptors.add(createChildParameter(RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES,
				RepresentationFactory.eINSTANCE.createModelRule()));

		newChildDescriptors.add(createChildParameter(RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES,
				RepresentationFactory.eINSTANCE.createOwningRule()));
	}

}
