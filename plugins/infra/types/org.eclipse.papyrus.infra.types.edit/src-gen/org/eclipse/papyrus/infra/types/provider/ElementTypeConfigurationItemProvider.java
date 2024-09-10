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
 *  Christian W. Damus - bugs 568782, 568853
 */
package org.eclipse.papyrus.infra.types.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentsEList.FeatureIterator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementTypeConfigurationItemProvider extends ConfigurationElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfigurationItemProvider(AdapterFactory adapterFactory) {
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

			addIdentifierPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addHintPropertyDescriptor(object);
			addKindPropertyDescriptor(object);
			addOwnedConfigurationsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Identifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdentifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IdentifiedConfiguration_identifier_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IdentifiedConfiguration_identifier_feature", "_UI_IdentifiedConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.IDENTIFIED_CONFIGURATION__IDENTIFIER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_NamedConfiguration_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_NamedConfiguration_name_feature", "_UI_NamedConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.NAMED_CONFIGURATION__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Hint feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHintPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ElementTypeConfiguration_hint_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ElementTypeConfiguration_hint_feature", "_UI_ElementTypeConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__HINT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Kind feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKindPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ElementTypeConfiguration_kind_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ElementTypeConfiguration_kind_feature", "_UI_ElementTypeConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__KIND,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Owned Configurations feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedConfigurationsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ElementTypeConfiguration_ownedConfigurations_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ElementTypeConfiguration_ownedConfigurations_feature", "_UI_ElementTypeConfiguration_type"),
				 ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS,
				 false,
				 false,
				 false,
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
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY);
			childrenFeatures.add(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.
		EStructuralFeature result = null;

		// Sort the children features to get the most specific subset reference and, if applicable,
		// a single subset, in which to (e.g.) drop a dragged object
		for (EStructuralFeature feature : sortChildrenFeatures(getChildrenFeatures(object))) {
			if (isValidValue(object, child, feature)) {
				result = feature;
				break;
			}
		}

		return result;
	}

	/**
	 * Sort children features by:
	 * <ul>
	 * <li>single features before many features</li>
	 * <li>subset features before their supersets</li>
	 * </ul>
	 * 
	 * @param features
	 * @return
	 */
	protected List<? extends EStructuralFeature> sortChildrenFeatures(Collection<? extends EStructuralFeature> features) {
		List<EStructuralFeature> result = new ArrayList<>(features);

		Collections.sort(result, this::compareFeatures);

		return result;
	}

	protected int compareFeatures(EStructuralFeature a, EStructuralFeature b) {
		if (a.isMany() && !b.isMany()) {
			return +1;
		}
		if (b.isMany() && !a.isMany()) {
			return -1;
		}
		if (isSubset(a, b)) {
			return +1;
		}
		if (isSubset(b, a)) {
			return -1;
		}
		return 0;
	}

	protected boolean isSubset(EStructuralFeature possibleSubset, EStructuralFeature possibleSuperset) {
		EAnnotation subsetAnnotation = possibleSubset.getEAnnotation("subsets"); //$NON-NLS-1$
		return subsetAnnotation != null && subsetAnnotation.getReferences().contains(possibleSuperset);
	}
	
	@Override
	protected boolean isWrappingNeeded(Object object) {
		return true; // the 'ownedConfigurations' has subsets that can repeat the same element
	}
	
	@Override
	protected Object createWrapper(EObject object, EStructuralFeature feature, Object value, int index) {
		Object result = value;

		if (feature instanceof EReference) {
			// Wrapping is needed if the value appears in multiple subsets of the 'ownedConfigurations'
			EStructuralFeature previousSubset = null;

			for (FeatureIterator<EObject> iter = (FeatureIterator<EObject>) object.eCrossReferences().iterator(); iter.hasNext();) {
				EObject next = iter.next();

				if (next == value
						&& next.eContainmentFeature() == ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS
						&& isSubset(iter.feature(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS)) {

					if (previousSubset != null && previousSubset != iter.feature()) {
						result = new DelegatingWrapperItemProvider(value, object, feature, index, adapterFactory) {
							@Override
							public String getText(Object object) {
								return NLS.bind("{0} as {1}", super.getText(object), getFeatureText(feature));
							}
						};
						break;
					}

					previousSubset = iter.feature();
				}
			}
		} else {
			result = super.createWrapper(object, feature, value, index);
		}

		return result;
	}

	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection, int index) {
		if (feature.isDerived() && isSubset(feature, ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS)) {
			// Add to the superset because the subset is derived
			feature = ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS;
		}
		
		return super.createAddCommand(domain, owner, feature, collection, index);
	}
	
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection) {
		Command result;

		if (isSubset(feature, ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS)) {
			// Remove from the containment superset to entirely remove the object
			result = createRemoveCommand(domain, owner, (EStructuralFeature) ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS, collection);
		} else {
			result = super.createRemoveCommand(domain, owner, feature, collection);
		}

		return result;
	}
	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ElementTypeConfiguration)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ElementTypeConfiguration_type") :
			getString("_UI_ElementTypeConfiguration_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChangedGen(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ElementTypeConfiguration.class)) {
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER:
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME:
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT:
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND:
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		notifyChangedGen(notification);

		switch (notification.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			switch (notification.getFeatureID(ElementTypeConfiguration.class)) {
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				// Refresh derived subsets
				if (childrenStoreMap != null) {
					childrenStoreMap.remove(notification.getNotifier());
				}
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
				return;
			}
			break;
		}
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
				(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY,
				 ElementTypesConfigurationsFactory.eINSTANCE.createIconEntry()));

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE,
				 ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration()));

		newChildDescriptors.add
			(createChildParameter
				(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE,
				 ElementTypesConfigurationsFactory.eINSTANCE.createExternallyRegisteredAdvice()));
	}

}
