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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Tab Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeTabFilter#getTypes <em>Types</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeTabFilter#getGeneratedTypes <em>Generated Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeTabFilter()
 * @model
 * @generated
 */
public interface TypeTabFilter extends GenPropertyTabFilter {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Fully-qualified class names for selection to match
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Types</em>' attribute list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeTabFilter_Types()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getTypes();

	/**
	 * Returns the value of the '<em><b>Generated Types</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GeneratedType}.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GeneratedType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Types</em>' attribute list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GeneratedType
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeTabFilter_GeneratedTypes()
	 * @model
	 * @generated
	 */
	EList<GeneratedType> getGeneratedTypes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<String> getAllTypes();

} // TypeTabFilter
