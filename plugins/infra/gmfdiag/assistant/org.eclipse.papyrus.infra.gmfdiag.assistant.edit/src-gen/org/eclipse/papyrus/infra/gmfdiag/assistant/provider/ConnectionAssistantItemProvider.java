/**
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.gmfdiag.assistant.provider;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.filters.FiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.uml2.common.edit.command.SubsetSupersetSetCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConnectionAssistantItemProvider extends AssistantItemProvider {

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ConnectionAssistantItemProvider(AdapterFactory adapterFactory) {
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

			addSourceFilterPropertyDescriptor(object);
			addTargetFilterPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Source Filter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected void addSourceFilterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ConnectionAssistant_sourceFilter_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ConnectionAssistant_sourceFilter_feature", "_UI_ConnectionAssistant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.CONNECTION_ASSISTANT__SOURCE_FILTER,
				true,
				false,
				true,
				null,
				null,
				null) {
			@Override
			public Collection<?> getChoiceOfValues(Object object) {
				Set<Object> result = new HashSet<>();
				ConnectionAssistant self = (ConnectionAssistant) object;

				if (self.getProvider() != null) {
					result.addAll(self.getProvider().getOwnedFilters());
				}
				result.remove(self.getSourceFilter());

				return result;
			}
		});
	}

	/**
	 * This adds a property descriptor for the Target Filter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected void addTargetFilterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ConnectionAssistant_targetFilter_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ConnectionAssistant_targetFilter_feature", "_UI_ConnectionAssistant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.CONNECTION_ASSISTANT__TARGET_FILTER,
				true,
				false,
				true,
				null,
				null,
				null) {
			@Override
			public Collection<?> getChoiceOfValues(Object object) {
				Set<Object> result = new HashSet<>();
				ConnectionAssistant self = (ConnectionAssistant) object;

				if (self.getProvider() != null) {
					result.addAll(self.getProvider().getOwnedFilters());
				}
				result.remove(self.getTargetFilter());

				return result;
			}
		});
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
			childrenFeatures.add(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER);
			childrenFeatures.add(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER);
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
	 * This returns ConnectionAssistant.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConnectionAssistant")); //$NON-NLS-1$
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
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ConnectionAssistant) object).getElementTypeID();
		return label == null || label.length() == 0 ? getString("_UI_ConnectionAssistant_type") : //$NON-NLS-1$
				getString("_UI_ConnectionAssistant_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ConnectionAssistant.class)) {
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER:
		case AssistantPackage.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER:
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

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER,
				AssistantFactory.eINSTANCE.createAssistedElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER,
				AssistantFactory.eINSTANCE.createElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER,
				FiltersFactory.eINSTANCE.createFilterReference()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER,
				FiltersFactory.eINSTANCE.createCompoundFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER,
				FiltersFactory.eINSTANCE.createEquals()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER,
				AssistantFactory.eINSTANCE.createAssistedElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER,
				AssistantFactory.eINSTANCE.createElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER,
				FiltersFactory.eINSTANCE.createFilterReference()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER,
				FiltersFactory.eINSTANCE.createCompoundFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER,
				FiltersFactory.eINSTANCE.createEquals()));
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

		boolean qualify = childFeature == AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER ||
				childFeature == AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER;

		if (qualify) {
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.CONNECTION_ASSISTANT__SOURCE_FILTER }, null, value);
		}
		if (feature == AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.CONNECTION_ASSISTANT__TARGET_FILTER }, null, value);
		}
		if (feature == AssistantPackage.Literals.CONNECTION_ASSISTANT__SOURCE_FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] { AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_SOURCE_FILTER }, value);
		}
		if (feature == AssistantPackage.Literals.CONNECTION_ASSISTANT__TARGET_FILTER) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] { AssistantPackage.Literals.CONNECTION_ASSISTANT__OWNED_TARGET_FILTER }, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
