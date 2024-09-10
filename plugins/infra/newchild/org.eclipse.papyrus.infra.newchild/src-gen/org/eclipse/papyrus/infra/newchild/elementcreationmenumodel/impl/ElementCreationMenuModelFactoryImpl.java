/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *  
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 * 	Christian W. Damus - bug 572712
 * 
 */
package org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementCreationMenuModelFactoryImpl extends EFactoryImpl implements ElementCreationMenuModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementCreationMenuModelFactory init() {
		try {
			ElementCreationMenuModelFactory theElementCreationMenuModelFactory = (ElementCreationMenuModelFactory)EPackage.Registry.INSTANCE.getEFactory(ElementCreationMenuModelPackage.eNS_URI);
			if (theElementCreationMenuModelFactory != null) {
				return theElementCreationMenuModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ElementCreationMenuModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementCreationMenuModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ElementCreationMenuModelPackage.FOLDER: return createFolder();
			case ElementCreationMenuModelPackage.CREATION_MENU: return createCreationMenu();
			case ElementCreationMenuModelPackage.CREATE_RELATIONSHIP_MENU: return createCreateRelationshipMenu();
			case ElementCreationMenuModelPackage.SEPARATOR: return createSeparator();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Folder createFolder() {
		FolderImpl folder = new FolderImpl();
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreationMenu createCreationMenu() {
		CreationMenuImpl creationMenu = new CreationMenuImpl();
		return creationMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CreateRelationshipMenu createCreateRelationshipMenu() {
		CreateRelationshipMenuImpl createRelationshipMenu = new CreateRelationshipMenuImpl();
		return createRelationshipMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Separator createSeparator() {
		SeparatorImpl separator = new SeparatorImpl();
		return separator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementCreationMenuModelPackage getElementCreationMenuModelPackage() {
		return (ElementCreationMenuModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ElementCreationMenuModelPackage getPackage() {
		return ElementCreationMenuModelPackage.eINSTANCE;
	}

} //ElementCreationMenuModelFactoryImpl
