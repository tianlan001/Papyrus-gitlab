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
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
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
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.papyrus.infra.filters.FiltersFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.uml2.common.edit.command.SubsetAddCommand;
import org.eclipse.uml2.common.edit.command.SubsetSupersetReplaceCommand;
import org.eclipse.uml2.common.edit.command.SubsetSupersetSetCommand;
import org.eclipse.uml2.common.edit.command.SupersetRemoveCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ModelingAssistantProviderItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ModelingAssistantProviderItemProvider(AdapterFactory adapterFactory) {
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
			addAssistantPropertyDescriptor(object);
			addElementTypePropertyDescriptor(object);
			addElementTypeIDPropertyDescriptor(object);
			addClientContextPropertyDescriptor(object);
			addClientContextIDPropertyDescriptor(object);
			addExcludedElementTypePropertyDescriptor(object);
			addExcludedElementTypeIDPropertyDescriptor(object);
			addRelationshipTypePropertyDescriptor(object);
			addRelationshipTypeIDPropertyDescriptor(object);
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
				getString("_UI_ModelingAssistantProvider_name_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_name_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Assistant feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addAssistantPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_assistant_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_assistant_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ASSISTANT,
				false,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Element Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addElementTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_elementType_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_elementType_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE,
				false,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				new String[] {
						"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				}));
	}

	/**
	 * This adds a property descriptor for the Element Type ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addElementTypeIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_elementTypeID_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_elementTypeID_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Client Context feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected void addClientContextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_clientContext_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_clientContext_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT,
				true,
				false,
				true,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				new String[] { "org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				}) {

			@Override
			public Collection<?> getChoiceOfValues(Object object) {
				return ClientContextManager.getInstance().getClientContexts();
			}

			@Override
			public void setPropertyValue(Object object, Object value) {
				final EStructuralFeature feature = AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID;
				EObject modelingAssistantProvider = (EObject) object;
				IClientContext context = (IClientContext) value;

				String newID = (context == null) ? null : context.getId();
				String currentID = (String) modelingAssistantProvider.eGet(feature);
				if (!Objects.equals(newID, currentID)) {
					EditingDomain editingDomain = getEditingDomain(modelingAssistantProvider);
					if (editingDomain == null) {
						modelingAssistantProvider.eSet(feature, newID);
					} else {
						editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, object, feature, newID));
					}
				}
			}
		});
	}

	/**
	 * This adds a property descriptor for the Client Context ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addClientContextIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_clientContextID_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_clientContextID_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Excluded Element Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addExcludedElementTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_excludedElementType_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_excludedElementType_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE,
				false,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				new String[] {
						"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				}));
	}

	/**
	 * This adds a property descriptor for the Excluded Element Type ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addExcludedElementTypeIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_excludedElementTypeID_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_excludedElementTypeID_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Relationship Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRelationshipTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_relationshipType_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_relationshipType_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE,
				false,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				new String[] {
						"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				}));
	}

	/**
	 * This adds a property descriptor for the Relationship Type ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addRelationshipTypeIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ModelingAssistantProvider_relationshipTypeID_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ModelingAssistantProvider_relationshipTypeID_feature", "_UI_ModelingAssistantProvider_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID,
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
	 *
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER);
			childrenFeatures.add(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT);
			childrenFeatures.add(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT);
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
	 * This returns ModelingAssistantProvider.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ModelingAssistantProvider")); //$NON-NLS-1$
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
		String label = ((ModelingAssistantProvider) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ModelingAssistantProvider_type") : //$NON-NLS-1$
				getString("_UI_ModelingAssistantProvider_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ModelingAssistantProvider.class)) {
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ASSISTANT:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT:
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT:
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

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER,
				AssistantFactory.eINSTANCE.createAssistedElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER,
				AssistantFactory.eINSTANCE.createElementTypeFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createFilterReference()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createCompoundFilter()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER,
				FiltersFactory.eINSTANCE.createEquals()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT,
				AssistantFactory.eINSTANCE.createPopupAssistant()));

		newChildDescriptors.add(createChildParameter(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT,
				AssistantFactory.eINSTANCE.createConnectionAssistant()));
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
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection, int index) {
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID) {
			return new SubsetAddCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID }, collection, index);
		}
		return super.createAddCommand(domain, owner, feature, collection, index);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection) {
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID) {
			return new SupersetRemoveCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID }, collection);
		}
		return super.createRemoveCommand(domain, owner, feature, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, java.util.Collection)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value, Collection<?> collection) {
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID) {
			return new SubsetSupersetReplaceCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID }, null, value, collection);
		}
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID) {
			return new SubsetSupersetReplaceCommand(domain, owner, feature, null, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID }, value, collection);
		}
		return super.createReplaceCommand(domain, owner, feature, value, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 *      <!-- begin-user-doc -->
	 *      <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID) {
			return new SubsetSupersetSetCommand(domain, owner, feature, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID }, null, value);
		}
		if (feature == AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID) {
			return new SubsetSupersetSetCommand(domain, owner, feature, null, new EStructuralFeature[] { AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID }, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}
}
