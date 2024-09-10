/**
 * Copyright (c) 2013 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ICell Editor Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Common interface for all cell editor configuration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration#getCellEditorId <em>Cell Editor Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getICellEditorConfiguration()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ICellEditorConfiguration extends StyledElement {
	/**
	 * Returns the value of the '<em><b>Cell Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The id of the celleditor.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cell Editor Id</em>' attribute.
	 * @see #setCellEditorId(String)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getICellEditorConfiguration_CellEditorId()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getCellEditorId();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration#getCellEditorId <em>Cell Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cell Editor Id</em>' attribute.
	 * @see #getCellEditorId()
	 * @generated
	 */
	void setCellEditorId(String value);

} // ICellEditorConfiguration
