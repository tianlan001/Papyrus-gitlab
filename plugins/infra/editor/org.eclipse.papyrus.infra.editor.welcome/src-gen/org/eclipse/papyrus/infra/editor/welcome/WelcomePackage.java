/**
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *
 */
package org.eclipse.papyrus.infra.editor.welcome;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeFactory
 * @model kind="package"
 * @generated
 */
public interface WelcomePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "welcome"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2015/editor/welcome"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "welcome"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	WelcomePackage eINSTANCE = org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeImpl <em>Welcome</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeImpl
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcome()
	 * @generated
	 */
	int WELCOME = 0;

	/**
	 * The feature id for the '<em><b>Welcome Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME__WELCOME_PAGE = 0;

	/**
	 * The number of structural features of the '<em>Welcome</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Welcome</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcomePage()
	 * @generated
	 */
	int WELCOME_PAGE = 1;

	/**
	 * The feature id for the '<em><b>Section</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE__SECTION = 0;

	/**
	 * The feature id for the '<em><b>Visible Section</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE__VISIBLE_SECTION = 1;

	/**
	 * The feature id for the '<em><b>Sash Column</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE__SASH_COLUMN = 2;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Visible Sections</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE___GET_VISIBLE_SECTIONS = 0;

	/**
	 * The operation id for the '<em>Get Section</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE___GET_SECTION__STRING = 1;

	/**
	 * The operation id for the '<em>Get Sash Column</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE___GET_SASH_COLUMN__INT = 2;

	/**
	 * The operation id for the '<em>Get Sash Row</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE___GET_SASH_ROW__INT_INT = 3;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_PAGE_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl <em>Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcomeSection()
	 * @generated
	 */
	int WELCOME_SECTION = 2;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION__IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION__HIDDEN = 1;

	/**
	 * The feature id for the '<em><b>Page</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION__PAGE = 2;

	/**
	 * The number of structural features of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Is Identified By</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION___IS_IDENTIFIED_BY__STRING = 0;

	/**
	 * The number of operations of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WELCOME_SECTION_OPERATION_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl <em>Sash Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getSashColumn()
	 * @generated
	 */
	int SASH_COLUMN = 3;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN__X = 0;

	/**
	 * The feature id for the '<em><b>Sash Row</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN__SASH_ROW = 1;

	/**
	 * The feature id for the '<em><b>Page</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN__PAGE = 2;

	/**
	 * The number of structural features of the '<em>Sash Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Sash Row</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN___GET_SASH_ROW__INT = 0;

	/**
	 * The number of operations of the '<em>Sash Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_COLUMN_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl <em>Sash Row</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl
	 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getSashRow()
	 * @generated
	 */
	int SASH_ROW = 4;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_ROW__Y = 0;

	/**
	 * The feature id for the '<em><b>Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_ROW__PAGE = 1;

	/**
	 * The feature id for the '<em><b>Column</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_ROW__COLUMN = 2;

	/**
	 * The number of structural features of the '<em>Sash Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_ROW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Sash Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SASH_ROW_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.editor.welcome.Welcome <em>Welcome</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Welcome</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.Welcome
	 * @generated
	 */
	EClass getWelcome();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.editor.welcome.Welcome#getWelcomePage <em>Welcome Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Welcome Page</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.Welcome#getWelcomePage()
	 * @see #getWelcome()
	 * @generated
	 */
	EReference getWelcome_WelcomePage();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Page</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage
	 * @generated
	 */
	EClass getWelcomePage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSections <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Section</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSections()
	 * @see #getWelcomePage()
	 * @generated
	 */
	EReference getWelcomePage_Section();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections <em>Visible Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Visible Section</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections()
	 * @see #getWelcomePage()
	 * @generated
	 */
	EReference getWelcomePage_VisibleSection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumns <em>Sash Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Sash Column</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumns()
	 * @see #getWelcomePage()
	 * @generated
	 */
	EReference getWelcomePage_SashColumn();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections() <em>Get Visible Sections</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Visible Sections</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections()
	 * @generated
	 */
	EOperation getWelcomePage__GetVisibleSections();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String) <em>Get Section</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Section</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String)
	 * @generated
	 */
	EOperation getWelcomePage__GetSection__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int) <em>Get Sash Column</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Sash Column</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int)
	 * @generated
	 */
	EOperation getWelcomePage__GetSashColumn__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int) <em>Get Sash Row</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Sash Row</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int)
	 * @generated
	 */
	EOperation getWelcomePage__GetSashRow__int_int();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Section</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeSection
	 * @generated
	 */
	EClass getWelcomeSection();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#getIdentifiers <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Identifier</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#getIdentifiers()
	 * @see #getWelcomeSection()
	 * @generated
	 */
	EAttribute getWelcomeSection_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#isHidden <em>Hidden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Hidden</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#isHidden()
	 * @see #getWelcomeSection()
	 * @generated
	 */
	EAttribute getWelcomeSection_Hidden();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Page</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#getPage()
	 * @see #getWelcomeSection()
	 * @generated
	 */
	EReference getWelcomeSection_Page();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#isIdentifiedBy(java.lang.String) <em>Is Identified By</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Is Identified By</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#isIdentifiedBy(java.lang.String)
	 * @generated
	 */
	EOperation getWelcomeSection__IsIdentifiedBy__String();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn <em>Sash Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Sash Column</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn
	 * @generated
	 */
	EClass getSashColumn();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX()
	 * @see #getSashColumn()
	 * @generated
	 */
	EAttribute getSashColumn_X();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRows <em>Sash Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Sash Row</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRows()
	 * @see #getSashColumn()
	 * @generated
	 */
	EReference getSashColumn_SashRow();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Page</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getPage()
	 * @see #getSashColumn()
	 * @generated
	 */
	EReference getSashColumn_Page();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int) <em>Get Sash Row</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Sash Row</em>' operation.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int)
	 * @generated
	 */
	EOperation getSashColumn__GetSashRow__int();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow <em>Sash Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Sash Row</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashRow
	 * @generated
	 */
	EClass getSashRow();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashRow#getY()
	 * @see #getSashRow()
	 * @generated
	 */
	EAttribute getSashRow_Y();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Page</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashRow#getPage()
	 * @see #getSashRow()
	 * @generated
	 */
	EReference getSashRow_Page();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Column</em>'.
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn()
	 * @see #getSashRow()
	 * @generated
	 */
	EReference getSashRow_Column();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WelcomeFactory getWelcomeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeImpl <em>Welcome</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeImpl
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcome()
		 * @generated
		 */
		EClass WELCOME = eINSTANCE.getWelcome();
		/**
		 * The meta object literal for the '<em><b>Welcome Page</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference WELCOME__WELCOME_PAGE = eINSTANCE.getWelcome_WelcomePage();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePageImpl
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcomePage()
		 * @generated
		 */
		EClass WELCOME_PAGE = eINSTANCE.getWelcomePage();
		/**
		 * The meta object literal for the '<em><b>Section</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference WELCOME_PAGE__SECTION = eINSTANCE.getWelcomePage_Section();
		/**
		 * The meta object literal for the '<em><b>Visible Section</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference WELCOME_PAGE__VISIBLE_SECTION = eINSTANCE.getWelcomePage_VisibleSection();
		/**
		 * The meta object literal for the '<em><b>Sash Column</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference WELCOME_PAGE__SASH_COLUMN = eINSTANCE.getWelcomePage_SashColumn();
		/**
		 * The meta object literal for the '<em><b>Get Visible Sections</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation WELCOME_PAGE___GET_VISIBLE_SECTIONS = eINSTANCE.getWelcomePage__GetVisibleSections();
		/**
		 * The meta object literal for the '<em><b>Get Section</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation WELCOME_PAGE___GET_SECTION__STRING = eINSTANCE.getWelcomePage__GetSection__String();
		/**
		 * The meta object literal for the '<em><b>Get Sash Column</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation WELCOME_PAGE___GET_SASH_COLUMN__INT = eINSTANCE.getWelcomePage__GetSashColumn__int();
		/**
		 * The meta object literal for the '<em><b>Get Sash Row</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation WELCOME_PAGE___GET_SASH_ROW__INT_INT = eINSTANCE.getWelcomePage__GetSashRow__int_int();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl <em>Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getWelcomeSection()
		 * @generated
		 */
		EClass WELCOME_SECTION = eINSTANCE.getWelcomeSection();
		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute WELCOME_SECTION__IDENTIFIER = eINSTANCE.getWelcomeSection_Identifier();
		/**
		 * The meta object literal for the '<em><b>Hidden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute WELCOME_SECTION__HIDDEN = eINSTANCE.getWelcomeSection_Hidden();
		/**
		 * The meta object literal for the '<em><b>Page</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference WELCOME_SECTION__PAGE = eINSTANCE.getWelcomeSection_Page();
		/**
		 * The meta object literal for the '<em><b>Is Identified By</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation WELCOME_SECTION___IS_IDENTIFIED_BY__STRING = eINSTANCE.getWelcomeSection__IsIdentifiedBy__String();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl <em>Sash Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getSashColumn()
		 * @generated
		 */
		EClass SASH_COLUMN = eINSTANCE.getSashColumn();
		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SASH_COLUMN__X = eINSTANCE.getSashColumn_X();
		/**
		 * The meta object literal for the '<em><b>Sash Row</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SASH_COLUMN__SASH_ROW = eINSTANCE.getSashColumn_SashRow();
		/**
		 * The meta object literal for the '<em><b>Page</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SASH_COLUMN__PAGE = eINSTANCE.getSashColumn_Page();
		/**
		 * The meta object literal for the '<em><b>Get Sash Row</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation SASH_COLUMN___GET_SASH_ROW__INT = eINSTANCE.getSashColumn__GetSashRow__int();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl <em>Sash Row</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl
		 * @see org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomePackageImpl#getSashRow()
		 * @generated
		 */
		EClass SASH_ROW = eINSTANCE.getSashRow();
		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SASH_ROW__Y = eINSTANCE.getSashRow_Y();
		/**
		 * The meta object literal for the '<em><b>Page</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SASH_ROW__PAGE = eINSTANCE.getSashRow_Page();
		/**
		 * The meta object literal for the '<em><b>Column</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SASH_ROW__COLUMN = eINSTANCE.getSashRow_Column();

	}

} // WelcomePackage
