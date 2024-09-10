/**
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 571560
 */
package org.eclipse.papyrus.infra.types.rulebased;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Not Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration#getComposedRule <em>Composed Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getNotRuleConfiguration()
 * @model
 * @generated
 */
public interface NotRuleConfiguration extends RuleConfiguration {
	/**
	 * Returns the value of the '<em><b>Composed Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composed Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composed Rule</em>' containment reference.
	 * @see #setComposedRule(RuleConfiguration)
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getNotRuleConfiguration_ComposedRule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RuleConfiguration getComposedRule();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration#getComposedRule <em>Composed Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composed Rule</em>' containment reference.
	 * @see #getComposedRule()
	 * @generated
	 */
	void setComposedRule(RuleConfiguration value);

} // NotRuleConfiguration
