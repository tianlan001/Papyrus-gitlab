/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureLabelModelFacetItemProvider
	extends LabelModelFacetItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureLabelModelFacetItemProvider(AdapterFactory adapterFactory) {
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

			addMetaFeaturesPropertyDescriptor(object);
			addEditableMetaFeaturesPropertyDescriptor(object);
			addViewPatternPropertyDescriptor(object);
			addEditorPatternPropertyDescriptor(object);
			addEditPatternPropertyDescriptor(object);
			addViewMethodPropertyDescriptor(object);
			addEditMethodPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Meta Features feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMetaFeaturesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_metaFeatures_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FeatureLabelModelFacet_metaFeatures_feature", "_UI_FeatureLabelModelFacet_type"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_MetaFeatures(),
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Editable Meta Features feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditableMetaFeaturesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_editableMetaFeatures_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FeatureLabelModelFacet_editableMetaFeatures_feature", "_UI_FeatureLabelModelFacet_type"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_EditableMetaFeatures(),
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the View Pattern feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViewPatternPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_viewPattern_feature"),
				 getString("_UI_FeatureLabelModelFacet_viewPattern_description"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_ViewPattern(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Editor Pattern feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditorPatternPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_editorPattern_feature"),
				 getString("_UI_FeatureLabelModelFacet_editorPattern_description"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_EditorPattern(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the View Method feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViewMethodPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_viewMethod_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FeatureLabelModelFacet_viewMethod_feature", "_UI_FeatureLabelModelFacet_type"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_ViewMethod(),
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Edit Pattern feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditPatternPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_editPattern_feature"),
				 getString("_UI_FeatureLabelModelFacet_editPattern_description"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_EditPattern(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Edit Method feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditMethodPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_FeatureLabelModelFacet_editMethod_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_FeatureLabelModelFacet_editMethod_feature", "_UI_FeatureLabelModelFacet_type"),
				 GMFGenPackage.eINSTANCE.getFeatureLabelModelFacet_EditMethod(),
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns FeatureLabelModelFacet.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/FeatureLabelModelFacet"));
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
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		StringBuffer sb = new StringBuffer();
		for (GenFeature feature : ((FeatureLabelModelFacet) object).getMetaFeatures()) {
			if (sb.length() > 0) {
				sb.append(", "); //$NON-NLS-1$
			}
			sb.append(feature.getName());
		}
		return getString("_UI_FeatureLabelModelFacet_type") + " " + sb.toString();
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

		switch (notification.getFeatureID(FeatureLabelModelFacet.class)) {
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET__VIEW_PATTERN:
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET__EDITOR_PATTERN:
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET__EDIT_PATTERN:
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET__VIEW_METHOD:
			case GMFGenPackage.FEATURE_LABEL_MODEL_FACET__EDIT_METHOD:
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

}
