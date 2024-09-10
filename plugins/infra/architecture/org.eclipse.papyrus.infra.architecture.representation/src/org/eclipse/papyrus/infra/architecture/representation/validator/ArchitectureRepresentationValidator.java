/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.architecture.representation.validator;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.validation.AbstractEObjectDependencyValidator;
import org.eclipse.papyrus.infra.architecture.representation.Activator;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;

/**
 * Validator for {@link RepresentationPackage}
 */
public class ArchitectureRepresentationValidator extends AbstractEObjectDependencyValidator {

	/**
	 * instance of this validator
	 */
	public static final ArchitectureRepresentationValidator eINSTANCE = new ArchitectureRepresentationValidator();

	/**
	 *
	 * Constructor.
	 *
	 */
	private ArchitectureRepresentationValidator() {
		// to prevent instantiation
	}

	/**
	 * @see org.eclipse.emf.ecore.util.EObjectValidator#getEPackage()
	 *
	 * @return
	 */
	@Override
	protected EPackage getEPackage() {
		return RepresentationPackage.eINSTANCE;
	}

	/**
	 * @see org.eclipse.emf.ecore.util.EObjectValidator#validate(int, java.lang.Object, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param classifierID
	 * @param object
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	protected boolean validate(int classifierID, Object object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
		case RepresentationPackage.MODEL_RULE:
			return validateModelRule((ModelRule) object, diagnostics, context);
		case RepresentationPackage.OWNING_RULE:
			return validateOwningRule((OwningRule) object, diagnostics, context);
		default:
			return super.validate(classifierID, object, diagnostics, context);
		}
	}

	/**
	 *
	 * @param modelRule
	 * @param diagnostics
	 * @param context
	 * @return
	 *         <code>true</code> if the {@link ModelRule} is valid
	 */
	private boolean validateModelRule(final ModelRule modelRule, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return validateRuleElement(modelRule, RepresentationPackage.eINSTANCE.getModelRule_Element(), diagnostics, context)
				&& validateRuleStereotype(modelRule, RepresentationPackage.eINSTANCE.getModelRule_Stereotypes(), diagnostics, context);
	}

	/**
	 *
	 * @param owningRule
	 * @param diagnostics
	 * @param context
	 * @return
	 *         <code>true</code> if the {@link OwningRule} if valid
	 */
	private boolean validateOwningRule(final OwningRule owningRule, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return validateRuleElement(owningRule, RepresentationPackage.eINSTANCE.getOwningRule_Element(), diagnostics, context)
				&& validateRuleStereotype(owningRule, RepresentationPackage.eINSTANCE.getOwningRule_Stereotypes(), diagnostics, context);
	}

	/**
	 *
	 * @param ruleTovalidate
	 *            the rule to validate
	 * @param rule_elementReference
	 *            the element feature of the rule
	 * @param diagnostics
	 * @param context
	 * @return
	 *         <code>true</code> if the element feature of the rule is valid
	 */
	private boolean validateRuleElement(final EObject ruleTovalidate, final EReference rule_elementReference, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		EClass referencedEClass = (EClass) ruleTovalidate.eGet(rule_elementReference);
		if (referencedEClass != null) {
			final Resource resource = ruleTovalidate.eResource();
			final String bundleName = RESOURCE_URI_HELPER.getBundleNameFromResource(resource);
			final String dependencyName = RESOURCE_URI_HELPER.getBundleNameFromResource(referencedEClass.eResource());

			boolean isOK = PROJECT_DEPENDENCY_HELPER.hasDependency(bundleName, dependencyName);
			if (!isOK) {
				final Diagnostic newDiag = createMissingDependenciesDiagnostic(ruleTovalidate, rule_elementReference, Collections.singletonList(dependencyName));
				diagnostics.add(newDiag);
			}
			return isOK;
		} else {
			Activator.log.warn(NLS.bind("The object {0} is not supported by the validation for {1} metamodel", ruleTovalidate, RepresentationPackage.eINSTANCE.getName()));
			return true;
		}
	}

	/**
	 *
	 * @param ruleTovalidate
	 *            the rule to validate
	 * @param rule_elementReference
	 *            the element feature of the rule
	 * @param diagnostics
	 * @param context
	 * @return
	 *         <code>true</code> if the element feature of the rule is valid
	 */
	private boolean validateRuleStereotype(final EObject ruleTovalidate, final EReference rule_stereotypesReference, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		// TODO
		return true;
	}

}
