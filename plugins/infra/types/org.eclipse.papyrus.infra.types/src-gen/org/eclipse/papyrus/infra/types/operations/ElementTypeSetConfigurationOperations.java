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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Element Type Set Configuration</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings() <em>Get All Advice Bindings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementTypeSetConfigurationOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementTypeSetConfigurationOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * adviceBindingsConfigurations->union(elementTypeConfigurations.ownedAdvice)->asSet()
	 * @param elementTypeSetConfiguration The receiving '<em><b>Element Type Set Configuration</b></em>' model object.
	 * <!-- end-model-doc -->
	 * @generated not
	 */
	public static EList<AbstractAdviceBindingConfiguration> getAllAdviceBindings(ElementTypeSetConfiguration elementTypeSetConfiguration) {
		List<AbstractAdviceBindingConfiguration> result = new ArrayList<>(elementTypeSetConfiguration.getAdviceBindingsConfigurations());

		elementTypeSetConfiguration.getElementTypeConfigurations().forEach(type -> result.addAll(type.getOwnedAdvice()));

		// We know that the results are unique because they were collected from containment references only
		return new EcoreEList.UnmodifiableEList<>((InternalEObject) elementTypeSetConfiguration,
				ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION__ALL_ADVICE_BINDINGS,
				result.size(), result.toArray());
	}

} // ElementTypeSetConfigurationOperations
