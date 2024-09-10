/**
 */
package org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.papyrus.infra.types.provider.AbstractAdviceBindingConfigurationItemProvider;

import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypePropertyReferenceEdgeAdviceConfigurationItemProvider extends AbstractAdviceBindingConfigurationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypePropertyReferenceEdgeAdviceConfigurationItemProvider(AdapterFactory adapterFactory) {
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

			addFeatureToSetPropertyDescriptor(object);
			addStereotypeQualifiedNamePropertyDescriptor(object);
			addEdgeLabelPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Feature To Set feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFeatureToSetPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_featureToSet_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_featureToSet_feature", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_type"),
				 StereotypePropertyReferenceEdgeAdvicePackage.Literals.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Stereotype Qualified Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStereotypeQualifiedNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_stereotypeQualifiedName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_stereotypeQualifiedName_feature", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_type"),
				 StereotypePropertyReferenceEdgeAdvicePackage.Literals.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Edge Label feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEdgeLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_edgeLabel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_edgeLabel_feature", "_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_type"),
				 StereotypePropertyReferenceEdgeAdvicePackage.Literals.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns StereotypePropertyReferenceEdgeAdviceConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/StereotypePropertyReferenceEdgeAdviceConfiguration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((StereotypePropertyReferenceEdgeAdviceConfiguration)object).getStereotypeQualifiedName();
		return label == null || label.length() == 0 ?
			getString("_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_type") :
			getString("_UI_StereotypePropertyReferenceEdgeAdviceConfiguration_type") + " " + label;
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

		switch (notification.getFeatureID(StereotypePropertyReferenceEdgeAdviceConfiguration.class)) {
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET:
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
			case StereotypePropertyReferenceEdgeAdvicePackage.STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL:
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
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return StereotypePropertyReferenceEdgeAdviceEditPlugin.INSTANCE;
	}

}
