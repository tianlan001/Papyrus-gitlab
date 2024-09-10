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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element represents an instance of a TextDocument. A such element can be open in the Papyrus SashEditor.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getSemanticContext <em>Semantic Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getGraphicalContext <em>Graphical Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getKindId <em>Kind Id</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument()
 * @model
 * @generated
 */
public interface TextDocument extends EObject {
	/**
	 * Returns the value of the '<em><b>Semantic Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Reference the edited element of the user model.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Semantic Context</em>' reference.
	 * @see #setSemanticContext(EObject)
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument_SemanticContext()
	 * @model required="true"
	 * @generated
	 */
	EObject getSemanticContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getSemanticContext <em>Semantic Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Semantic Context</em>' reference.
	 * @see #getSemanticContext()
	 * @generated
	 */
	void setSemanticContext(EObject value);

	/**
	 * Returns the value of the '<em><b>Graphical Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Reference the element under which the TextDocument will be displayed in a TreeViewer (for example).
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Graphical Context</em>' reference.
	 * @see #setGraphicalContext(EObject)
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument_GraphicalContext()
	 * @model
	 * @generated
	 */
	EObject getGraphicalContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getGraphicalContext <em>Graphical Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Graphical Context</em>' reference.
	 * @see #getGraphicalContext()
	 * @generated
	 */
	void setGraphicalContext(EObject value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name to display in the UI for the editor.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Kind Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field will allow a better integration with the Papyrus Architecture Framework.
	 * This value must be equals to the value PapyrusRepresentationKind#implementationID
	 * This field will be used to find the ViewPrototype of an instance of TextDocument.
	 * This field is useless for usage outside of Papyrus, but we set its multiplicity to [1] to avoid possible mistake in Papyrus usages.
	 * This value can be changed by an architecture context switch.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Kind Id</em>' attribute.
	 * @see #setKindId(String)
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument_KindId()
	 * @model required="true"
	 * @generated
	 */
	String getKindId();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getKindId <em>Kind Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Kind Id</em>' attribute.
	 * @see #getKindId()
	 * @generated
	 */
	void setKindId(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type of the editor. This value must not be changed.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage#getTextDocument_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.textedit.textdocument.TextDocument#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // TextDocument
