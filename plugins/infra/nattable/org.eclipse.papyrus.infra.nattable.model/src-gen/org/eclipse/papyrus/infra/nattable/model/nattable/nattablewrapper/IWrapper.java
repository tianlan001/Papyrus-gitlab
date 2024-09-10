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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IWrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Common interface for all wrapped. The wrapper is used to be able to reference an
 * object which is not necessary an EObject. In this case, it could be referenced by as string.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperPackage#getIWrapper()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IWrapper extends StyledElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This method returns the wrapped element, but without solving it.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	Object getElement();

} // IWrapper
