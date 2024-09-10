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
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.operations;

import java.util.Collection;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.operations.AdviceConstraintOperations;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest;
import org.eclipse.uml2.uml.Stereotype;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Required Stereotype Constraint Configuration</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredStereotypeConstraintConfigurationOperations extends AdviceConstraintOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RequiredStereotypeConstraintConfigurationOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean approveRequest(RequiredStereotypeConstraintConfiguration requiredStereotypeConstraintConfiguration, IEditCommandRequest request) {
		Stereotype stereotype = (request instanceof UnapplyStereotypeRequest) ? ((UnapplyStereotypeRequest) request).getStereotype() : null;
		if (stereotype == null) {
			// Nothing to approve
			return true;
		}

		Collection<String> requiredStereotypes = getRequiredStereotypes(requiredStereotypeConstraintConfiguration);
		return !requiredStereotypes.contains(stereotype.getQualifiedName());
	}

	private static Collection<String> getRequiredStereotypes(RequiredStereotypeConstraintConfiguration requiredStereotypeConstraintConfiguration) {
		Collection<String> result = List.of();

		SpecializationTypeConfiguration typeConfig = getTypeConfiguration(requiredStereotypeConstraintConfiguration);
		if (typeConfig != null && typeConfig.getMatcherConfiguration() instanceof StereotypeApplicationMatcherConfiguration) {
			// Check the matcher
			StereotypeApplicationMatcherConfiguration matcher = (StereotypeApplicationMatcherConfiguration) typeConfig.getMatcherConfiguration();
			result = matcher.getStereotypesQualifiedNames();
		}

		return result;
	}

	private static SpecializationTypeConfiguration getTypeConfiguration(AdviceConstraint constraint) {
		ConstraintAdviceConfiguration advice = constraint.getAdvice();
		return advice.getTarget() instanceof SpecializationTypeConfiguration ? (SpecializationTypeConfiguration) advice.getTarget() : null;
	}

} // RequiredStereotypeConstraintConfigurationOperations
