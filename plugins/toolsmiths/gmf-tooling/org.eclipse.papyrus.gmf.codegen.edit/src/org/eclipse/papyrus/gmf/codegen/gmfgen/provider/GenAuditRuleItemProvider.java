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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GenAuditRuleItemProvider
	extends GenRuleBaseItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenAuditRuleItemProvider(AdapterFactory adapterFactory) {
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

			addIdPropertyDescriptor(object);
			addRulePropertyDescriptor(object);
			addMessagePropertyDescriptor(object);
			addSeverityPropertyDescriptor(object);
			addUseInLiveModePropertyDescriptor(object);
			addCategoryPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_id_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_Id(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Rule feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRulePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_rule_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_rule_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_Rule(),
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Message feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMessagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_message_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_message_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_Message(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Severity feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSeverityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_severity_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_severity_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_Severity(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Use In Live Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUseInLiveModePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_useInLiveMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_useInLiveMode_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_UseInLiveMode(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Category feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCategoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenAuditRule_category_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenAuditRule_category_feature", "_UI_GenAuditRule_type"),
				 GMFGenPackage.eINSTANCE.getGenAuditRule_Category(),
				 true,
				 false,
				 true,
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
			childrenFeatures.add(GMFGenPackage.eINSTANCE.getGenAuditRule_Target());
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
	 * This returns GenAuditRule.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/GenAuditRule"));
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
		String label = ((GenAuditRule)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_GenAuditRule_type") :
			getString("_UI_GenAuditRule_type") + " " + label;
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

		switch (notification.getFeatureID(GenAuditRule.class)) {
			case GMFGenPackage.GEN_AUDIT_RULE__ID:
			case GMFGenPackage.GEN_AUDIT_RULE__MESSAGE:
			case GMFGenPackage.GEN_AUDIT_RULE__SEVERITY:
			case GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
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
				(GMFGenPackage.eINSTANCE.getGenAuditRule_Target(),
				 GMFGenFactory.eINSTANCE.createGenDomainElementTarget()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenAuditRule_Target(),
				 GMFGenFactory.eINSTANCE.createGenDiagramElementTarget()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenAuditRule_Target(),
				 GMFGenFactory.eINSTANCE.createGenDomainAttributeTarget()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenAuditRule_Target(),
				 GMFGenFactory.eINSTANCE.createGenNotationElementTarget()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenAuditRule_Target(),
				 GMFGenFactory.eINSTANCE.createGenAuditedMetricTarget()));
	}

}
