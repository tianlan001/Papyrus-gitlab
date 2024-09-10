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

import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Audit Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getEditorGen <em>Editor Gen</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getClientContexts <em>Client Contexts</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditRoot()
 * @model
 * @generated
 */
public interface GenAuditRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Editor Gen</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getAudits <em>Audits</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Gen</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Gen</em>' container reference.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditRoot_EditorGen()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getAudits
	 * @model opposite="audits" resolveProxies="false" required="true" transient="false" changeable="false"
	 * @generated
	 */
	GenEditorGenerator getEditorGen();

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Categories</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditRoot_Categories()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer#getRoot
	 * @model opposite="root" containment="true"
	 * @generated
	 */
	EList<GenAuditContainer> getCategories();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditRoot_Rules()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule#getRoot
	 * @model opposite="root" containment="true"
	 * @generated
	 */
	EList<GenAuditRule> getRules();

	/**
	 * Returns the value of the '<em><b>Client Contexts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Contexts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Client Contexts</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditRoot_ClientContexts()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext#getRoot
	 * @model opposite="root" containment="true"
	 * @generated
	 */
	EList<GenAuditContext> getClientContexts();

	List<GenPackage> getTargetedModelPackages();
} // GenAuditRoot
