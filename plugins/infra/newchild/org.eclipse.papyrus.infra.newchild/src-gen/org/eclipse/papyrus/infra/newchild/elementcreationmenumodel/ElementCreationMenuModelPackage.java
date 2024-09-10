/**
 * Copyright (c) 2017 CEA LIST.
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
 * 
 */
package org.eclipse.papyrus.infra.newchild.elementcreationmenumodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelFactory
 * @model kind="package"
 * @generated
 */
public interface ElementCreationMenuModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "elementcreationmenumodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/newchild/elementcreationmenumodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ElementCreationMenuModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementCreationMenuModelPackage eINSTANCE = org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.MenuImpl <em>Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.MenuImpl
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getMenu()
	 * @generated
	 */
	int MENU = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__ICON = 1;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__VISIBLE = 2;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__FILTER = 3;

	/**
	 * The number of structural features of the '<em>Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.FolderImpl
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__LABEL = MENU__LABEL;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ICON = MENU__ICON;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__VISIBLE = MENU__VISIBLE;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__FILTER = MENU__FILTER;

	/**
	 * The feature id for the '<em><b>Menu</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__MENU = MENU_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = MENU_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_OPERATION_COUNT = MENU_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl <em>Creation Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getCreationMenu()
	 * @generated
	 */
	int CREATION_MENU = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__LABEL = MENU__LABEL;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__ICON = MENU__ICON;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__VISIBLE = MENU__VISIBLE;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__FILTER = MENU__FILTER;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__ELEMENT_TYPE = MENU_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__ROLE = MENU_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Display All Roles</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU__DISPLAY_ALL_ROLES = MENU_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Creation Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU_FEATURE_COUNT = MENU_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Creation Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_MENU_OPERATION_COUNT = MENU_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreateRelationshipMenuImpl <em>Create Relationship Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreateRelationshipMenuImpl
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getCreateRelationshipMenu()
	 * @generated
	 */
	int CREATE_RELATIONSHIP_MENU = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__LABEL = CREATION_MENU__LABEL;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__ICON = CREATION_MENU__ICON;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__VISIBLE = CREATION_MENU__VISIBLE;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__FILTER = CREATION_MENU__FILTER;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__ELEMENT_TYPE = CREATION_MENU__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__ROLE = CREATION_MENU__ROLE;

	/**
	 * The feature id for the '<em><b>Display All Roles</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU__DISPLAY_ALL_ROLES = CREATION_MENU__DISPLAY_ALL_ROLES;

	/**
	 * The number of structural features of the '<em>Create Relationship Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU_FEATURE_COUNT = CREATION_MENU_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Create Relationship Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_RELATIONSHIP_MENU_OPERATION_COUNT = CREATION_MENU_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.SeparatorImpl <em>Separator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.SeparatorImpl
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getSeparator()
	 * @generated
	 */
	int SEPARATOR = 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR__LABEL = MENU__LABEL;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR__ICON = MENU__ICON;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR__VISIBLE = MENU__VISIBLE;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR__FILTER = MENU__FILTER;

	/**
	 * The number of structural features of the '<em>Separator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR_FEATURE_COUNT = MENU_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Separator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEPARATOR_OPERATION_COUNT = MENU_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder#getMenu <em>Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Menu</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder#getMenu()
	 * @see #getFolder()
	 * @generated
	 */
	EReference getFolder_Menu();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu <em>Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Menu</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu
	 * @generated
	 */
	EClass getMenu();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getLabel()
	 * @see #getMenu()
	 * @generated
	 */
	EAttribute getMenu_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getIcon()
	 * @see #getMenu()
	 * @generated
	 */
	EAttribute getMenu_Icon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#isVisible <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#isVisible()
	 * @see #getMenu()
	 * @generated
	 */
	EAttribute getMenu_Visible();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu#getFilter()
	 * @see #getMenu()
	 * @generated
	 */
	EReference getMenu_Filter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu <em>Creation Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Creation Menu</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu
	 * @generated
	 */
	EClass getCreationMenu();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#getElementType()
	 * @see #getCreationMenu()
	 * @generated
	 */
	EReference getCreationMenu_ElementType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#getRole()
	 * @see #getCreationMenu()
	 * @generated
	 */
	EAttribute getCreationMenu_Role();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#isDisplayAllRoles <em>Display All Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display All Roles</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu#isDisplayAllRoles()
	 * @see #getCreationMenu()
	 * @generated
	 */
	EAttribute getCreationMenu_DisplayAllRoles();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreateRelationshipMenu <em>Create Relationship Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create Relationship Menu</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreateRelationshipMenu
	 * @generated
	 */
	EClass getCreateRelationshipMenu();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Separator <em>Separator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Separator</em>'.
	 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Separator
	 * @generated
	 */
	EClass getSeparator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ElementCreationMenuModelFactory getElementCreationMenuModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.FolderImpl
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '<em><b>Menu</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOLDER__MENU = eINSTANCE.getFolder_Menu();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.MenuImpl <em>Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.MenuImpl
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getMenu()
		 * @generated
		 */
		EClass MENU = eINSTANCE.getMenu();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU__LABEL = eINSTANCE.getMenu_Label();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU__ICON = eINSTANCE.getMenu_Icon();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU__VISIBLE = eINSTANCE.getMenu_Visible();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MENU__FILTER = eINSTANCE.getMenu_Filter();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl <em>Creation Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getCreationMenu()
		 * @generated
		 */
		EClass CREATION_MENU = eINSTANCE.getCreationMenu();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATION_MENU__ELEMENT_TYPE = eINSTANCE.getCreationMenu_ElementType();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATION_MENU__ROLE = eINSTANCE.getCreationMenu_Role();

		/**
		 * The meta object literal for the '<em><b>Display All Roles</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATION_MENU__DISPLAY_ALL_ROLES = eINSTANCE.getCreationMenu_DisplayAllRoles();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreateRelationshipMenuImpl <em>Create Relationship Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreateRelationshipMenuImpl
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getCreateRelationshipMenu()
		 * @generated
		 */
		EClass CREATE_RELATIONSHIP_MENU = eINSTANCE.getCreateRelationshipMenu();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.SeparatorImpl <em>Separator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.SeparatorImpl
		 * @see org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.ElementCreationMenuModelPackageImpl#getSeparator()
		 * @generated
		 */
		EClass SEPARATOR = eINSTANCE.getSeparator();

	}

} //ElementCreationMenuModelPackage
