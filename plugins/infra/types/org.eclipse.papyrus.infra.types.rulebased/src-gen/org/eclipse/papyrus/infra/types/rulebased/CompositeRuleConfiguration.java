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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration#getComposedRules <em>Composed Rules</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getCompositeRuleConfiguration()
 * @model abstract="true"
 * @generated
 */
public interface CompositeRuleConfiguration extends RuleConfiguration {
	/**
	 * Returns the value of the '<em><b>Composed Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composed Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composed Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#getCompositeRuleConfiguration_ComposedRules()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<RuleConfiguration> getComposedRules();

} // CompositeRuleConfiguration
