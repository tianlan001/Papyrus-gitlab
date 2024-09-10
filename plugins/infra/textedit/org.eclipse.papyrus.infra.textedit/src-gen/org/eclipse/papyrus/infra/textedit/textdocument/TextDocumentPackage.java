/**
 * Copyright (c) 2021 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.textedit.textdocument;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * <!-- begin-model-doc -->
 * This package contains the elements used to represent an instance of a TextDocument
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentFactory
 * @model kind="package"
 * @generated
 */
public interface TextDocumentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "textdocument"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/textedit/textdocument"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "textdocument"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TextDocumentPackage eINSTANCE = org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl <em>Text Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentPackageImpl#getTextDocument()
	 * @generated
	 */
	int TEXT_DOCUMENT = 0;

	/**
	 * The feature id for the '<em><b>Semantic Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT__SEMANTIC_CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>Graphical Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT__GRAPHICAL_CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT__NAME = 2;

	/**
	 * The feature id for the '<em><b>Kind Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT__KIND_ID = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Text Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Text Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument <em>Text Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Text Document</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument
	 * @generated
	 */
	EClass getTextDocument();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getSemanticContext <em>Semantic Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Semantic Context</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getSemanticContext()
	 * @see #getTextDocument()
	 * @generated
	 */
	EReference getTextDocument_SemanticContext();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getGraphicalContext <em>Graphical Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Graphical Context</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getGraphicalContext()
	 * @see #getTextDocument()
	 * @generated
	 */
	EReference getTextDocument_GraphicalContext();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getName()
	 * @see #getTextDocument()
	 * @generated
	 */
	EAttribute getTextDocument_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getKindId <em>Kind Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Kind Id</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getKindId()
	 * @see #getTextDocument()
	 * @generated
	 */
	EAttribute getTextDocument_KindId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getType()
	 * @see #getTextDocument()
	 * @generated
	 */
	EAttribute getTextDocument_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TextDocumentFactory getTextDocumentFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl <em>Text Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl
		 * @see org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentPackageImpl#getTextDocument()
		 * @generated
		 */
		EClass TEXT_DOCUMENT = eINSTANCE.getTextDocument();

		/**
		 * The meta object literal for the '<em><b>Semantic Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TEXT_DOCUMENT__SEMANTIC_CONTEXT = eINSTANCE.getTextDocument_SemanticContext();

		/**
		 * The meta object literal for the '<em><b>Graphical Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TEXT_DOCUMENT__GRAPHICAL_CONTEXT = eINSTANCE.getTextDocument_GraphicalContext();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEXT_DOCUMENT__NAME = eINSTANCE.getTextDocument_Name();

		/**
		 * The meta object literal for the '<em><b>Kind Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEXT_DOCUMENT__KIND_ID = eINSTANCE.getTextDocument_KindId();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEXT_DOCUMENT__TYPE = eINSTANCE.getTextDocument_Type();

	}

} // TextDocumentPackage
