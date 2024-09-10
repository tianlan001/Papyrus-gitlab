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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2.problem;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS_MULTIPLE_CHOICE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.valueToSet;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.constraints.util.ConstraintsUtil;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace.ComposedSourceTraceHelper;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace.NameKind;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace.SourceTraceHelper;

import com.google.common.base.Strings;

/**
 * Custom validation rules for <em>Constraints</em> on views and other objects in <em>Properties Context</em> models.
 */
public class ConstraintsCustomValidator extends CustomModelChecker.SwitchValidator {

	private final SourceTraceHelper traceHelper = new ComposedSourceTraceHelper();

	public ConstraintsCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(ConstraintDescriptor constraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (traceHelper.isInstanceOfConstraint(constraint)) {
			EObject sourceClass = traceHelper.resolveInstanceOfConstraintClass(constraint);
			if (sourceClass == null || sourceClass.eIsProxy()) {
				// Can we find what the class name should have been?
				EObject container = constraint.eContainer();
				EObject containerSource = traceHelper.getSourceElement(container);
				Collection<? extends EObject> constraintSourceClasses = traceHelper.getValidConstraintSourceClasses(constraint, containerSource);

				String actualClassName = traceHelper.getClassName(constraint);
				String expectedClassName = constraintSourceClasses.stream().map(c -> traceHelper.getName(c, NameKind.CONSTRAINT))
						.distinct().collect(Collectors.joining(",")); //$NON-NLS-1$
				ValueProperty constraintProperty = Strings.isNullOrEmpty(expectedClassName) ? null : traceHelper.getClassNameProperty(constraint);

				if (constraintProperty != null) {
					// If there are multiple choices, then the fixes will not be applicable to multiple markers,
					// so use a distinct problem ID for that case
					int problemID = constraintSourceClasses.size() > 1 ? UNRESOLVED_CONSTRAINT_CLASS_MULTIPLE_CHOICE : UNRESOLVED_CONSTRAINT_CLASS;
					ConstraintType type = ((SimpleConstraint) constraint).getConstraintType();
					String message = format(Messages.ConstraintsCustomValidator_0, context, ConstraintsUtil.getLabel(constraint), actualClassName,
							ConstraintsUtil.getLabel(constraintProperty), ConstraintsUtil.getLabel(type));
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, constraintProperty, ConstraintsPackage.Literals.VALUE_PROPERTY__VALUE,
							message, problem(problemID), valueToSet(expectedClassName, EcorePackage.Literals.ESTRING)));
				} else {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, constraint,
							format(Messages.ConstraintsCustomValidator_1, context, constraint, actualClassName)));
				}
			}
		}
	}

}
