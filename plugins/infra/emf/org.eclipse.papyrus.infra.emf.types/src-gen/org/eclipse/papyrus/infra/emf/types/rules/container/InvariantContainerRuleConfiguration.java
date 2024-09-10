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
package org.eclipse.papyrus.infra.emf.types.rules.container;

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration#getPermissions <em>Permissions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRulePackage#getInvariantContainerRuleConfiguration()
 * @model
 * @generated
 */
public interface InvariantContainerRuleConfiguration extends RuleConfiguration {
	/**
	 * Returns the value of the '<em><b>Permissions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permissions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Permissions</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRulePackage#getInvariantContainerRuleConfiguration_Permissions()
	 * @model containment="true"
	 * @generated
	 */
	EList<HierarchyPermission> getPermissions();

} // InvariantContainerRuleConfiguration
