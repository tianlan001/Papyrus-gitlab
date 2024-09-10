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
package org.eclipse.papyrus.infra.core.architecture.provider;


import static java.util.function.Predicate.not;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitectureContextItemProvider extends ADElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureContextItemProvider(AdapterFactory adapterFactory) {
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

			addDefaultViewpointsPropertyDescriptor(object);
			addElementTypesPropertyDescriptor(object);
			addExtensionPrefixPropertyDescriptor(object);
			addCreationCommandClassPropertyDescriptor(object);
			addConversionCommandClassPropertyDescriptor(object);
			addGeneralContextPropertyDescriptor(object);
			addExtendedContextsPropertyDescriptor(object);
			addExtensionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Default Viewpoints feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultViewpointsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_defaultViewpoints_feature"), //$NON-NLS-1$
				 getString("_UI_ArchitectureContext_defaultViewpoints_description"), //$NON-NLS-1$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS,
				 true,
				 false,
				 true,
				 null,
				 getString("_UI_ContextPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Element Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addElementTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_elementTypes_feature"), //$NON-NLS-1$
				 getString("_UI_ArchitectureContext_elementTypes_description"), //$NON-NLS-1$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__ELEMENT_TYPES,
				 true,
				 false,
				 true,
				 null,
				 getString("_UI_ContextPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Extension Prefix feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExtensionPrefixPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_extensionPrefix_feature"), //$NON-NLS-1$
				 getString("_UI_ArchitectureContext_extensionPrefix_description"), //$NON-NLS-1$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ContextPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Creation Command Class feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCreationCommandClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_creationCommandClass_feature"), //$NON-NLS-1$
				 getString("_UI_ArchitectureContext_creationCommandClass_description"), //$NON-NLS-1$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ContextPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Conversion Command Class feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addConversionCommandClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_conversionCommandClass_feature"), //$NON-NLS-1$
				 getString("_UI_ArchitectureContext_conversionCommandClass_description"), //$NON-NLS-1$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ContextPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the General Context feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addGeneralContextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_generalContext_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ArchitectureContext_generalContext_feature", "_UI_ArchitectureContext_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT,
				 true,
				 false,
				 true,
				 null,
				 getString("_UI_DependenciesPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Extended Contexts feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExtendedContextsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_extendedContexts_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ArchitectureContext_extendedContexts_feature", "_UI_ArchitectureContext_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS,
				 true,
				 false,
				 true,
				 null,
				 getString("_UI_DependenciesPropertyCategory"), //$NON-NLS-1$
				 null));
	}

	/**
	 * This adds a property descriptor for the Extension feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExtensionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ArchitectureContext_extension_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ArchitectureContext_extension_feature", "_UI_ArchitectureContext_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENSION,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 getString("_UI_DependenciesPropertyCategory"), //$NON-NLS-1$
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
			childrenFeatures.add(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__VIEWPOINTS);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ArchitectureContext)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ArchitectureContext_type") : //$NON-NLS-1$
			getString("_UI_ArchitectureContext_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ArchitectureContext.class)) {
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
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
				(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__VIEWPOINTS,
				 ArchitectureFactory.eINSTANCE.createArchitectureViewpoint()));
	}

	@Override
	protected ItemPropertyDescriptor createItemPropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator, String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine, boolean sortChoices,
			Object staticImage, String category, String[] filterFlags, Object propertyEditorFactory) {

		if (feature == ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS) {
			return new ItemPropertyDescriptor(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags, propertyEditorFactory) {

				@Override
				public Collection<?> getChoiceOfValues(Object object) {
					Collection<?> result = super.getChoiceOfValues(object);

					if (result != null && object instanceof ArchitectureContext) {
						// A context can only extend a context that can receive all of the features that it contributes, which
						// means an instance of the same class or a subclass. And, of course, cannot extend itself
						Predicate<Object> condition = ((ArchitectureContext) object).eClass()::isInstance;
						result.removeIf(not(condition).or(object::equals));
					}

					return result;
				}

			};
		} else if (feature == ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT) {
			return new ItemPropertyDescriptor(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags, propertyEditorFactory) {

				@Override
				public Collection<?> getChoiceOfValues(Object object) {
					Collection<?> result = super.getChoiceOfValues(object);

					if (result != null && object instanceof ArchitectureContext) {
						// A context can only specialize a context from which it can inherit all of its features, which
						// means an instance of the same class or a superclass. And, of course, cannot inherit itself
						Predicate<Object> condition = o -> o instanceof EObject && ((EObject) o).eClass().isInstance(object);
						result.removeIf(not(condition).or(object::equals));
					}

					return result;
				}

			};
		} else {
			return super.createItemPropertyDescriptor(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags, propertyEditorFactory);
		}
	}
	
}
