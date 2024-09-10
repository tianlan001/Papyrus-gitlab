/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.types.rulebased.internal.expressions;

import static java.util.function.Predicate.not;

import java.util.Objects;
import java.util.function.IntPredicate;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * Implementation of properties in XML enablement expressions for rule configurations.
 * Supported properties are:
 *
 * <dl>
 * <dt>container</dt>
 * <dd>the EClass of the rule's container. No expected value indicates that the object is
 * expected not to have a container (i.e., to be a root object)</dd>
 * <dt>operandCount</dt>
 * <dd>the number of operands the rule has if it is a {@link CompositeRuleConfiguration}
 * or a {@link NotRuleConfiguration}, otherwise zero</dd>
 * </dl>
 */
public class RuleConfigurationPropertyTester extends PropertyTester {

	private static final String CONTAINER = "container"; //$NON-NLS-1$
	private static final String OPERAND_COUNT = "operandCount"; //$NON-NLS-1$

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;
		RuleConfiguration rule = asRuleConfiguration(receiver);

		if (rule != null) {
			switch (property) {
			case CONTAINER:
				result = testContainer(rule, asEClass(rule, expectedValue));
				break;
			case OPERAND_COUNT:
				result = testOperandCount(rule, asIntPredicate(expectedValue));
				break;
			}
		}

		return result;
	}

	boolean testContainer(RuleConfiguration rule, EClass expectedContainer) {
		EObject container = rule.eContainer();

		return expectedContainer == null
				? container == null
				: expectedContainer.isInstance(container);
	}

	boolean testOperandCount(RuleConfiguration rule, IntPredicate operandCountPredicate) {
		int operandCount = 0;

		if (rule instanceof NotRuleConfiguration) {
			operandCount = ((NotRuleConfiguration) rule).getComposedRule() != null ? 1 : 0;
		} else if (rule instanceof CompositeRuleConfiguration) {
			operandCount = ((CompositeRuleConfiguration) rule).getComposedRules().size();
		}

		return operandCountPredicate.test(operandCount);
	}

	private RuleConfiguration asRuleConfiguration(Object object) {
		EObject eObject = EMFHelper.getEObject(object);
		return eObject instanceof RuleConfiguration ? (RuleConfiguration) eObject : null;
	}

	private EClass asEClass(EObject context, Object classValue) {
		EClass result = null;

		if (classValue != null) {
			// Let something vague like "*" just denote that there is some container
			result = EcorePackage.Literals.EOBJECT;

			String classSpec = String.valueOf(classValue);
			int fragmentSeparator = classSpec.indexOf('#');
			if (fragmentSeparator >= 0) {
				// It looks like an EClass URI
				URI uri = URI.createURI(classSpec);
				result = getEClass(context, uri);
			} else {
				// Take it as the instance class name of an EClass in the context package
				result = findEClass(context, classSpec);
			}
		}

		return result;
	}

	private IntPredicate asIntPredicate(Object expression) {
		IntPredicate result;

		if (expression == null) {
			// Test is just whether the value is positive
			result = i -> i > 0;
		} else {
			// TODO: For now, we are only supporting literal integers, not e.g. "> 1"
			String predicateSpec = String.valueOf(expression);
			result = Integer.valueOf(predicateSpec)::equals;
		}

		return result;
	}

	private EClass getEClass(EObject context, URI uri) {
		EObject resolved = null;

		ResourceSet rset = EMFHelper.getResourceSet(context);
		if (rset != null) {
			resolved = rset.getEObject(uri, true);
		} else {
			EPackage package_ = EPackage.Registry.INSTANCE.getEPackage(uri.trimFragment().toString());
			Resource resource = package_ != null ? package_.eResource() : null;
			if (resource != null) {
				resolved = resource.getEObject(uri.fragment());
			}
		}

		return (resolved instanceof EClass) ? (EClass) resolved : null;
	}

	private EClass findEClass(EObject context, String instanceClassName) {
		EClass result = getEClass(context.eClass().getEPackage(), instanceClassName);

		if (result == null) {
			result = context.eClass().getEAllSuperTypes().stream()
					.map(EClassifier::getEPackage)
					.filter(not(context.eClass().getEPackage()::equals))
					.distinct()
					.map(package_ -> getEClass(package_, instanceClassName))
					.filter(Objects::nonNull)
					.findFirst().orElse(null);
		}

		return result;
	}

	private EClass getEClass(EPackage package_, String instanceClassName) {
		return package_.getEClassifiers().stream()
				.filter(c -> instanceClassName.equals(c.getInstanceClassName()))
				.filter(EClass.class::isInstance).map(EClass.class::cast)
				.findAny().orElse(null);
	}

}
