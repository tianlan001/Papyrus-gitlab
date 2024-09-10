/******************************************************************************
 * Copyright (c) 2008, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.gmf.internal.validate.expressions.EnvironmentProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.ExpressionProviderRegistry;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpression;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpressionProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IParseEnvironment;
import org.eclipse.papyrus.gmf.internal.validate.expressions.NoProviderExpression;
import org.eclipse.papyrus.gmf.validate.ValidationOptions;

/**
 * @author artem
 */
class ExpressionCache {
	private final HashMap<EClass, List<ConstraintAdapter>> myClass2Constraints;

	public ExpressionCache() {
		myClass2Constraints = new HashMap<EClass, List<ConstraintAdapter>>();
	}

	public Validator get(EClass context, DiagnosticChain diag, ExternModelImport modelImports) {
		LinkedList<ConstraintAdapter> result = new LinkedList<ConstraintAdapter>();
		List<ConstraintAdapter> l = myClass2Constraints.get(context);
		if (l == null) {
			l = extract(context, diag, modelImports);
			myClass2Constraints.put(context, l);
		}
		result.addAll(l);
		for (EClass s : context.getEAllSuperTypes()) {
			l = myClass2Constraints.get(s);
			if (l == null) {
				l = extract(s, diag, modelImports);
				myClass2Constraints.put(s, l);
			}
			result.addAll(l);
		}
		if (result.isEmpty()) {
			return null;
		}
		return new Validator(result);
	}

	public static Validator get(EAnnotation ann, DiagnosticChain diag, ExternModelImport modelImports) {
		if (!Annotations.CONSTRAINTS_URI.equals(ann.getSource())) {
			return null;
		}
		EModelElement e = ann.getEModelElement();
		EClass contextClass = e instanceof EClass ? (EClass) e : (e instanceof EStructuralFeature ? ((EStructuralFeature) e).getEContainingClass(): null) ;
		if (contextClass != null) {
			List<ConstraintAdapter> r = extract(Collections.singletonList(ann), contextClass, diag, modelImports);
			if (r.isEmpty()) {
				return null;
			}
			return new Validator(r);
		} else {
			diag.add(new BasicDiagnostic(Diagnostic.WARNING, AbstractValidator.DIAGNOSTIC_SOURCE, StatusCodes.INVALID_CONSTRAINT_CONTEXT, MessageFormat.format(
					Messages.validation_ConstraintInInvalidContext, new Object[] { LabelProvider.INSTANCE.getObjectLabel(e) }), new Object[] { ann }));
		}
		return null;
	}

	private static List<ConstraintAdapter> extract(EClass eClass, DiagnosticChain diag, ExternModelImport modelImports) {
		LinkedList<EAnnotation> constraintAnnotations = new LinkedList<EAnnotation>();
		for (EAnnotation a : eClass.getEAnnotations()) {
			if (Annotations.CONSTRAINTS_URI.equals(a.getSource())) {
				constraintAnnotations.add(a);
			}
		}
		for (EOperation nextOperation : eClass.getEOperations()) {
			for (EAnnotation a : nextOperation.getEAnnotations()) {
				if (Annotations.CONSTRAINTS_URI.equals(a.getSource())) {
					constraintAnnotations.add(a);
				}
			}
		}
		for (EStructuralFeature nextFeature : eClass.getEStructuralFeatures()) {
			for (EAnnotation a : nextFeature.getEAnnotations()) {
				if (Annotations.CONSTRAINTS_URI.equals(a.getSource())) {
					constraintAnnotations.add(a);
				}
			}
		}
		return extract(constraintAnnotations, eClass, diag, modelImports);
	}

	private static List<ConstraintAdapter> extract(List<EAnnotation> constraintAnnotations, EClass eClass, DiagnosticChain diag, ExternModelImport modelImports) {
		if (constraintAnnotations.isEmpty()) {
			return Collections.<ConstraintAdapter>emptyList();
		}
		LinkedList<ConstraintAdapter> result = new LinkedList<ConstraintAdapter>();
		// actually, next code may result in few Constraints per same constrain definition (EAnnotation)
		// with few languages/expressions defined. Perhaps, java classes should rather mimic
		// definition structure (one constraint, few expressions) then create constraint-per-expression?
		for (EAnnotation ann : constraintAnnotations) {
			final int severity = getDiagnosticSeverity(ann, diag);
			final String description = getDescriptionDetail(ann);
			for (Map.Entry<String, String> nextDetail : ann.getDetails()) {
				String key = String.valueOf(nextDetail.getKey());
				if (ExpressionProviderRegistry.getInstance().getLanguages().contains(key)) {
					String body = readBodyDetail(nextDetail, diag);
					if (body != null) {
						IModelExpression expression = getExpression(key, body, eClass, modelImports);
						ConstraintAdapter constraint = new ConstraintAdapter(expression, severity, description);
						result.add(constraint);
					}
				}
			}
		}
		return result;
	}

	private static IModelExpression getExpression(String language, String body, EClassifier context, ExternModelImport modelImports) {
		IModelExpressionProvider provider = ExpressionProviderRegistry.getInstance().getProvider(language);
		if (provider == null) {
			return new NoProviderExpression(language, body, context);
		}
		
		IParseEnvironment env = null;
		if(modelImports != null && modelImports.getPackageRegistry() != null) {
			env = EnvironmentProvider.createParseEnv();
			env.setImportRegistry(modelImports.getPackageRegistry());
		}

		return provider.createExpression(body, context, env);
	}

	private static int getDiagnosticSeverity(EAnnotation constraintAnnotation, DiagnosticChain diagnostics) {
		int severity = Diagnostic.ERROR; // default and also fall-back value
		Object val = constraintAnnotation.getDetails().get(Annotations.SEVERITY);
		String strVal = (val instanceof String) ? ((String) val).trim() : null;
		if (Annotations.SEVERITY_INFO.equals(strVal)) {
			severity = Diagnostic.INFO;
		} else if (Annotations.SEVERITY_WARN.equals(strVal)) {
			severity = Diagnostic.WARNING;
		} else if (Annotations.SEVERITY_ERROR.equals(strVal)) {
			severity = Diagnostic.ERROR;
		} else if (strVal != null) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, AbstractValidator.DIAGNOSTIC_SOURCE, StatusCodes.INVALID_CONSTRAINT_SEVERITY, NLS.bind(Messages.invalidConstraintSeverity, new Object[] { strVal,
					Annotations.SEVERITY_ERROR, Annotations.SEVERITY_WARN, Annotations.SEVERITY_INFO }), new Object[] { val }));
		}
		return severity;
	}

	private static String getDescriptionDetail(EAnnotation annotation) {
		Object val = annotation.getDetails().get(Annotations.DESCRIPTION);
		return val != null ? String.valueOf(val) : null;
	}

	private static String readBodyDetail(Map.Entry<String, String> bodyEntry, DiagnosticChain diagnostics) {
		String body = bodyEntry.getValue();
		if (body != null && body.trim().length() > 0) {
			return body;
		}
		diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, AbstractValidator.DIAGNOSTIC_SOURCE, StatusCodes.EMPTY_CONSTRAINT_BODY, Messages.validation_EmptyExpressionBody, new Object[] { bodyEntry }));
		return null;
	}

	static class Validator {
		private final List<ConstraintAdapter> myConstraints;

		public Validator(List<ConstraintAdapter> constraints) {
			assert constraints != null;
			myConstraints = constraints;
		}

		public boolean validate(EObject modelElement, DiagnosticChain diagnostics, ValidationOptions opts) {
			boolean isValid = true;
			for(ConstraintAdapter x : myConstraints) {
				isValid &= handleConstraintDefinition(x, diagnostics);
				isValid &= handleConstrainedElement(x, modelElement, diagnostics, opts);
			}
			
			return isValid;
		}

		public boolean checkConstraints(DiagnosticChain diagnostics) {
			boolean isValid = true;
			for (ConstraintAdapter constraint : myConstraints) {
				isValid &= handleConstraintDefinition(constraint, diagnostics);
			}
			return isValid;
		}
		
		private boolean handleConstraintDefinition(ConstraintAdapter constraintProxy, DiagnosticChain diagnostics) {
			IStatus constraintStatus = constraintProxy.getStatus();

			if (Trace.shouldTrace(DebugOptions.META_DEFINITIONS)) {
				String msgPtn = "[metamodel-constraint] context={0} body={1}"; //$NON-NLS-1$
				Trace.trace(MessageFormat.format(msgPtn, new Object[] { LabelProvider.INSTANCE.getObjectLabel(constraintProxy.getContext()), constraintProxy.getBody() }));
			}

			if (!constraintStatus.isOK()) {
				String message = MessageFormat.format(Messages.invalidExpressionBody, new Object[] { constraintProxy.getBody(), constraintStatus.getMessage() });
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, AbstractValidator.DIAGNOSTIC_SOURCE, constraintStatus.getCode(), message, new Object[] { constraintProxy.getContext() }));
				return false;
			}
			return true;
		}

		private static boolean handleConstrainedElement(ConstraintAdapter constraint, EObject constrainedElement, DiagnosticChain diagnostics, ValidationOptions opts) {
			if (!constraint.isSatisfied(constrainedElement)) {
				String message = null;
				if (constraint.getDescription() == null) {
					message = MessageFormat.format(Messages.validation_ConstraintViolation, new Object[] { constraint.getBody(), LabelProvider.INSTANCE.getObjectLabel(constrainedElement) });
				} else {
					// TODO - user constraint ID as a key, support localication for messages
					message = constraint.getDescription();
				}
				diagnostics.add(new BasicDiagnostic(constraint.getSeverity(), AbstractValidator.DIAGNOSTIC_SOURCE, StatusCodes.CONSTRAINT_VIOLATION, message, new Object[] { constrainedElement }));
				return false;
			} else {
				if (opts.isReportSuccess()) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.OK, AbstractValidator.DIAGNOSTIC_SOURCE, StatusCodes.CONSTRAINT_SATISFIED, MessageFormat.format(Messages.validation_ConstraintSatisfied, new Object[] {
							constraint.getBody(), LabelProvider.INSTANCE.getObjectLabel(constrainedElement) }), new Object[] { constrainedElement }));
				}
			}
	
			return true;
		}
	}

}
