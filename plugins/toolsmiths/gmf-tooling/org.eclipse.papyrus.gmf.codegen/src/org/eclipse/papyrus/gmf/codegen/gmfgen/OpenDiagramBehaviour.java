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
package org.eclipse.papyrus.gmf.codegen.gmfgen;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Open Diagram Behaviour</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getEditPolicyClassName <em>Edit Policy Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getDiagramKind <em>Diagram Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getEditorID <em>Editor ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#isOpenAsEclipseEditor <em>Open As Eclipse Editor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOpenDiagramBehaviour()
 * @model
 * @generated
 */
public interface OpenDiagramBehaviour extends Behaviour {

	/**
	 * Returns the value of the '<em><b>Edit Policy Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Policy Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Policy Class Name</em>' attribute.
	 * @see #setEditPolicyClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOpenDiagramBehaviour_EditPolicyClassName()
	 * @model required="true"
	 * @generated
	 */
	String getEditPolicyClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getEditPolicyClassName <em>Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Policy Class Name</em>' attribute.
	 * @see #getEditPolicyClassName()
	 * @generated
	 */
	void setEditPolicyClassName(String value);

	/**
	 * Returns the value of the '<em><b>Diagram Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Kind</em>' attribute.
	 * @see #setDiagramKind(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOpenDiagramBehaviour_DiagramKind()
	 * @model
	 * @generated
	 */
	String getDiagramKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getDiagramKind <em>Diagram Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Kind</em>' attribute.
	 * @see #getDiagramKind()
	 * @generated
	 */
	void setDiagramKind(String value);

	/**
	 * Returns the value of the '<em><b>Editor ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor ID</em>' attribute.
	 * @see #setEditorID(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOpenDiagramBehaviour_EditorID()
	 * @model
	 * @generated
	 */
	String getEditorID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#getEditorID <em>Editor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor ID</em>' attribute.
	 * @see #getEditorID()
	 * @generated
	 */
	void setEditorID(String value);

	/**
	 * Returns the value of the '<em><b>Open As Eclipse Editor</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open As Eclipse Editor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open As Eclipse Editor</em>' attribute.
	 * @see #setOpenAsEclipseEditor(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOpenDiagramBehaviour_OpenAsEclipseEditor()
	 * @model default="true"
	 * @generated
	 */
	boolean isOpenAsEclipseEditor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour#isOpenAsEclipseEditor <em>Open As Eclipse Editor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Open As Eclipse Editor</em>' attribute.
	 * @see #isOpenAsEclipseEditor()
	 * @generated
	 */
	void setOpenAsEclipseEditor(boolean value);

} // OpenDiagramBehaviour
