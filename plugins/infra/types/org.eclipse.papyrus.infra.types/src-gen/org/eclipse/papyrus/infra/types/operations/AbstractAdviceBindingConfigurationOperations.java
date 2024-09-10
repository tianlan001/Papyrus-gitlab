/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types.operations;

import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Abstract Advice Binding Configuration</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet() <em>Get Element Type Set</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbstractAdviceBindingConfigurationOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractAdviceBindingConfigurationOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * if owningSet.oclIsUndefined() then
	 *   owningTarget.owningSet
	 * else
	 *   owningSet
	 * endif
	 * @param abstractAdviceBindingConfiguration The receiving '<em><b>Abstract Advice Binding Configuration</b></em>' model object.
	 * <!-- end-model-doc -->
	 * @generated not
	 */
	public static ElementTypeSetConfiguration getElementTypeSet(AbstractAdviceBindingConfiguration abstractAdviceBindingConfiguration) {
		if (abstractAdviceBindingConfiguration.getOwningSet() != null) {
			return abstractAdviceBindingConfiguration.getOwningSet();
		}
		if (abstractAdviceBindingConfiguration.getOwningTarget() != null) {
			return abstractAdviceBindingConfiguration.getOwningTarget().getOwningSet();
		}
		return null;
	}

} // AbstractAdviceBindingConfigurationOperations
