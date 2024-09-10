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
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IMaster Axis Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Interface used to distinghuish master axis provider from slave axis provider. 
 * The master are able to disconnect the slave.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider#isDisconnectSlave <em>Disconnect Slave</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider#getSources <em>Sources</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage#getIMasterAxisProvider()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IMasterAxisProvider extends AxisProvider {

	/**
	 * Returns the value of the '<em><b>Disconnect Slave</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * if true, the slave won't be updated when the elements owned by the master will be changed.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Disconnect Slave</em>' attribute.
	 * @see #setDisconnectSlave(boolean)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage#getIMasterAxisProvider_DisconnectSlave()
	 * @model
	 * @generated
	 */
	boolean isDisconnectSlave();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider#isDisconnectSlave <em>Disconnect Slave</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disconnect Slave</em>' attribute.
	 * @see #isDisconnectSlave()
	 * @generated
	 */
	void setDisconnectSlave(boolean value);

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field is used by Matrix Table. it replaces the context of the table as provider for the axis.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage#getIMasterAxisProvider_Sources()
	 * @model containment="true"
	 * @generated
	 */
	EList<IWrapper> getSources();
} // IMasterAxisProvider
