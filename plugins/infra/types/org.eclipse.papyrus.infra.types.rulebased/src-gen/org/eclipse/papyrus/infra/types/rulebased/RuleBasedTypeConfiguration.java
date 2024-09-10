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
package org.eclipse.papyrus.infra.types.rulebased;

import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration#getRuleConfiguration <em>Rule Configuration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getRuleBasedTypeConfiguration()
 * @model
 * @generated
 */
public interface RuleBasedTypeConfiguration extends SpecializationTypeConfiguration {
	/**
	 * Returns the value of the '<em><b>Rule Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Configuration</em>' containment reference.
	 * @see #setRuleConfiguration(RuleConfiguration)
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getRuleBasedTypeConfiguration_RuleConfiguration()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RuleConfiguration getRuleConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration#getRuleConfiguration <em>Rule Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Configuration</em>' containment reference.
	 * @see #getRuleConfiguration()
	 * @generated
	 */
	void setRuleConfiguration(RuleConfiguration value);

} // RuleBasedTypeConfiguration
