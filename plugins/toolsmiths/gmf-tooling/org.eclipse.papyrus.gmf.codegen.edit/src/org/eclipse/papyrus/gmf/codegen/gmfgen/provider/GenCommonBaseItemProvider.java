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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;

/**
 * This is the item provider adapter for a {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GenCommonBaseItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenCommonBaseItemProvider(AdapterFactory adapterFactory) {
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

			addDiagramRunTimeClassPropertyDescriptor(object);
			addVisualIDPropertyDescriptor(object);
			addEditPartClassNamePropertyDescriptor(object);
			addItemSemanticEditPolicyClassNamePropertyDescriptor(object);
			addNotationViewFactoryClassNamePropertyDescriptor(object);
			addStylesPropertyDescriptor(object);
			addSansDomainPropertyDescriptor(object);
			addSuperEditPartPropertyDescriptor(object);
			addVisualIDOverridePropertyDescriptor(object);
			addUsingDeleteServicePropertyDescriptor(object);
			addUsingReorientServicePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Diagram Run Time Class feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDiagramRunTimeClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_diagramRunTimeClass_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_diagramRunTimeClass_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_DiagramRunTimeClass(),
				 true,
				 false,
				 false,
				 null,
				 getString("_UI_DiagramElementPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Visual ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVisualIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_visualID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_visualID_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_VisualID(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_DiagramElementPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Edit Part Class Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditPartClassNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_editPartClassName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_editPartClassName_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_EditPartClassName(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ClassNamesPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Item Semantic Edit Policy Class Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addItemSemanticEditPolicyClassNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_itemSemanticEditPolicyClassName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_itemSemanticEditPolicyClassName_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_ItemSemanticEditPolicyClassName(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ClassNamesPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Notation View Factory Class Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNotationViewFactoryClassNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_notationViewFactoryClassName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_notationViewFactoryClassName_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_NotationViewFactoryClassName(),
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ClassNamesPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Styles feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStylesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_styles_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_styles_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_Styles(),
				 true,
				 false,
				 true,
				 null,
				 getString("_UI_DiagramElementPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Sans Domain feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSansDomainPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_sansDomain_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_sansDomain_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_SansDomain(),
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 getString("_UI_DiagramElementPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Super Edit Part feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSuperEditPartPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_superEditPart_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_superEditPart_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_SuperEditPart(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Visual ID Override feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVisualIDOverridePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_visualIDOverride_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_visualIDOverride_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_VisualIDOverride(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Using Delete Service feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUsingDeleteServicePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_usingDeleteService_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_usingDeleteService_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_UsingDeleteService(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Using Reorient Service feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUsingReorientServicePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_GenCommonBase_usingReorientService_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_GenCommonBase_usingReorientService_feature", "_UI_GenCommonBase_type"),
				 GMFGenPackage.eINSTANCE.getGenCommonBase_UsingReorientService(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(GMFGenPackage.eINSTANCE.getGenCommonBase_ElementType());
			childrenFeatures.add(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap());
			childrenFeatures.add(GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour());
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
		String label = ((GenCommonBase)object).getEditPartClassName();
		return label == null || label.length() == 0 ?
			getString("_UI_GenCommonBase_type") :
			getString("_UI_GenCommonBase_type") + " " + label;
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

		switch (notification.getFeatureID(GenCommonBase.class)) {
			case GMFGenPackage.GEN_COMMON_BASE__VISUAL_ID:
			case GMFGenPackage.GEN_COMMON_BASE__EDIT_PART_CLASS_NAME:
			case GMFGenPackage.GEN_COMMON_BASE__ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME:
			case GMFGenPackage.GEN_COMMON_BASE__NOTATION_VIEW_FACTORY_CLASS_NAME:
			case GMFGenPackage.GEN_COMMON_BASE__SANS_DOMAIN:
			case GMFGenPackage.GEN_COMMON_BASE__SUPER_EDIT_PART:
			case GMFGenPackage.GEN_COMMON_BASE__VISUAL_ID_OVERRIDE:
			case GMFGenPackage.GEN_COMMON_BASE__USING_DELETE_SERVICE:
			case GMFGenPackage.GEN_COMMON_BASE__USING_REORIENT_SERVICE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case GMFGenPackage.GEN_COMMON_BASE__ELEMENT_TYPE:
			case GMFGenPackage.GEN_COMMON_BASE__VIEWMAP:
			case GMFGenPackage.GEN_COMMON_BASE__BEHAVIOUR:
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
				(GMFGenPackage.eINSTANCE.getGenCommonBase_ElementType(),
				 GMFGenFactory.eINSTANCE.createMetamodelType()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_ElementType(),
				 GMFGenFactory.eINSTANCE.createSpecializationType()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_ElementType(),
				 GMFGenFactory.eINSTANCE.createNotationType()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap(),
				 GMFGenFactory.eINSTANCE.createFigureViewmap()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap(),
				 GMFGenFactory.eINSTANCE.createSnippetViewmap()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap(),
				 GMFGenFactory.eINSTANCE.createInnerClassViewmap()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap(),
				 GMFGenFactory.eINSTANCE.createParentAssignedViewmap()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Viewmap(),
				 GMFGenFactory.eINSTANCE.createModeledViewmap()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour(),
				 GMFGenFactory.eINSTANCE.createCustomBehaviour()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour(),
				 GMFGenFactory.eINSTANCE.createSharedBehaviour()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour(),
				 GMFGenFactory.eINSTANCE.createOpenDiagramBehaviour()));

		newChildDescriptors.add
			(createChildParameter
				(GMFGenPackage.eINSTANCE.getGenCommonBase_Behaviour(),
				 GMFGenFactory.eINSTANCE.createGenVisualEffect()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ((IChildCreationExtender)adapterFactory).getResourceLocator();
	}

}
