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
package org.eclipse.papyrus.infra.types.rulebased.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Or Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class OrRuleConfigurationImpl extends CompositeRuleConfigurationImpl implements OrRuleConfiguration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrRuleConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuleBasedPackage.Literals.OR_RULE_CONFIGURATION;
	}

} //OrRuleConfigurationImpl
