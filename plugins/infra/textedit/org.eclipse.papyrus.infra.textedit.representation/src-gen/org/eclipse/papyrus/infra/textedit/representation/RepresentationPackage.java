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
package org.eclipse.papyrus.infra.textedit.representation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

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
 * This package contains the elements allowing to integrate a Text Document into the Papyrus ArchitectureFramework
 * <!-- end-model-doc -->
 * 
 * @see org.eclipse.papyrus.infra.textedit.representation.RepresentationFactory
 * @model kind="package"
 * @generated
 */
public interface RepresentationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "representation"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/textedit/representation"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "representation"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	RepresentationPackage eINSTANCE = org.eclipse.papyrus.infra.textedit.representation.impl.RepresentationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.textedit.representation.impl.TextDocumentRepresentationImpl <em>Text Document Representation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.infra.textedit.representation.impl.TextDocumentRepresentationImpl
	 * @see org.eclipse.papyrus.infra.textedit.representation.impl.RepresentationPackageImpl#getTextDocumentRepresentation()
	 * @generated
	 */
	int TEXT_DOCUMENT_REPRESENTATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__DESCRIPTION = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__QUALIFIED_NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ICON;

	/**
	 * The feature id for the '<em><b>Language</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__LANGUAGE = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__LANGUAGE;

	/**
	 * The feature id for the '<em><b>Concerns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__CONCERNS = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__CONCERNS;

	/**
	 * The feature id for the '<em><b>Grayed Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__GRAYED_ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__GRAYED_ICON;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__PARENT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT;

	/**
	 * The feature id for the '<em><b>Model Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__MODEL_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES;

	/**
	 * The feature id for the '<em><b>Owning Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__OWNING_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__IMPLEMENTATION_ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID;

	/**
	 * The feature id for the '<em><b>Creation Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION__CREATION_COMMAND_CLASS = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Document Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION___IS_VALID_CLASS__DIAGNOSTICCHAIN_MAP = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Text Document Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_DOCUMENT_REPRESENTATION_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation <em>Text Document Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Text Document Representation</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation
	 * @generated
	 */
	EClass getTextDocumentRepresentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation#getCreationCommandClass <em>Creation Command Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Creation Command Class</em>'.
	 * @see org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation#getCreationCommandClass()
	 * @see #getTextDocumentRepresentation()
	 * @generated
	 */
	EAttribute getTextDocumentRepresentation_CreationCommandClass();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation#isValidClass(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Is Valid Class</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Is Valid Class</em>' operation.
	 * @see org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation#isValidClass(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTextDocumentRepresentation__IsValidClass__DiagnosticChain_Map();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RepresentationFactory getRepresentationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.textedit.representation.impl.TextDocumentRepresentationImpl <em>Text Document Representation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.infra.textedit.representation.impl.TextDocumentRepresentationImpl
		 * @see org.eclipse.papyrus.infra.textedit.representation.impl.RepresentationPackageImpl#getTextDocumentRepresentation()
		 * @generated
		 */
		EClass TEXT_DOCUMENT_REPRESENTATION = eINSTANCE.getTextDocumentRepresentation();

		/**
		 * The meta object literal for the '<em><b>Creation Command Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEXT_DOCUMENT_REPRESENTATION__CREATION_COMMAND_CLASS = eINSTANCE.getTextDocumentRepresentation_CreationCommandClass();

		/**
		 * The meta object literal for the '<em><b>Is Valid Class</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation TEXT_DOCUMENT_REPRESENTATION___IS_VALID_CLASS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTextDocumentRepresentation__IsValidClass__DiagnosticChain_Map();

	}

} // RepresentationPackage
