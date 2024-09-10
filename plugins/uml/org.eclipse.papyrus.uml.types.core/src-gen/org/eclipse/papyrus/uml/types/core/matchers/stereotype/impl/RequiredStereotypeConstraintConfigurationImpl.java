/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.operations.RequiredStereotypeConstraintConfigurationOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Stereotype Constraint Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RequiredStereotypeConstraintConfigurationImpl extends AdviceConstraintImpl implements RequiredStereotypeConstraintConfiguration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiredStereotypeConstraintConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StereotypeApplicationMatcherPackage.Literals.REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		return RequiredStereotypeConstraintConfigurationOperations.approveRequest(this, request);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case StereotypeApplicationMatcherPackage.REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION___APPROVE_REQUEST__IEDITCOMMANDREQUEST:
				return approveRequest((IEditCommandRequest)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RequiredStereotypeConstraintConfigurationImpl
