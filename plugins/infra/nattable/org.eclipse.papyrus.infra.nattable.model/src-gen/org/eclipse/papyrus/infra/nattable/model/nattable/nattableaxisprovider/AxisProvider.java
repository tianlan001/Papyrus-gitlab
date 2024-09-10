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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Axis Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * AbstractAxisProvider. This one owns IAxis
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AxisProvider#getAxis <em>Axis</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage#getAxisProvider()
 * @model abstract="true"
 * @generated
 */
public interface AxisProvider extends AbstractAxisProvider {

	/**
	 * Returns the value of the '<em><b>Axis</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * IAxis owned by the AxisProvider. The list can be empty in case of synchronized table.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Axis</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage#getAxisProvider_Axis()
	 * @model containment="true"
	 * @generated
	 */
	@Override
	EList<IAxis> getAxis();
} // AxisProvider
