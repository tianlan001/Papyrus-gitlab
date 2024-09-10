/**
 * Copyright (c) 2013 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.viewpoints.style;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus View Style</b></em>'.
 * @deprecated Use {@link org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle} instead
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.viewpoints.style.StylePackage#getPapyrusViewStyle()
 * @model
 * @generated
 */
public interface PapyrusViewStyle extends Style {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(EObject)
	 * @see org.eclipse.papyrus.infra.viewpoints.style.StylePackage#getPapyrusViewStyle_Owner()
	 * @model required="true"
	 * @generated
	 */
	EObject getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(EObject value);

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' reference.
	 * @see #setConfiguration(PapyrusView)
	 * @see org.eclipse.papyrus.infra.viewpoints.style.StylePackage#getPapyrusViewStyle_Configuration()
	 * @model
	 * @generated
	 */
	PapyrusView getConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle#getConfiguration <em>Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(PapyrusView value);

} // PapyrusViewStyle
