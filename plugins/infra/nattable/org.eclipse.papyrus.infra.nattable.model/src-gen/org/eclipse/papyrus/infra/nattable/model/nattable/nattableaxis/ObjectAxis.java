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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Axis</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract class used to reference object
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectAxis#getLocalLabelConfiguration <em>Local Label Configuration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage#getObjectAxis()
 * @model abstract="true"
 * @generated
 */
public interface ObjectAxis extends IAxis {

	/**
	 * Returns the value of the '<em><b>Local Label Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the local label configuration to use for this IAxis. This configuration
	 * will override the label configuration defined in the AxisManagerConfiguration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Local Label Configuration</em>' containment reference.
	 * @see #setLocalLabelConfiguration(ObjectLabelProviderConfiguration)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage#getObjectAxis_LocalLabelConfiguration()
	 * @model containment="true"
	 * @generated
	 */
	@Override
	ObjectLabelProviderConfiguration getLocalLabelConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectAxis#getLocalLabelConfiguration <em>Local Label Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Label Configuration</em>' containment reference.
	 * @see #getLocalLabelConfiguration()
	 * @generated
	 */
	void setLocalLabelConfiguration(ObjectLabelProviderConfiguration value);
} // ObjectAxis
