/**
 * Copyright (c) 2014 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.advices.values;

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdviceConfiguration#getFeaturesToSet <em>Features To Set</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage#getSetValuesAdviceConfiguration()
 * @model
 * @generated
 */
public interface SetValuesAdviceConfiguration extends AbstractAdviceBindingConfiguration {
	/**
	 * Returns the value of the '<em><b>Features To Set</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.advices.values.FeatureToSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Features To Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features To Set</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage#getSetValuesAdviceConfiguration_FeaturesToSet()
	 * @model containment="true"
	 * @generated
	 */
	EList<FeatureToSet> getFeaturesToSet();

} // SetValuesAdviceConfiguration
