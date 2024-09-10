/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Radek Dvorak (Borland) - Bugzilla 165458
 *   Ansgar Radermacher (CEA) - created variant for evaluation with OCL pivot element
 *
 * </copyright>
 */

package org.eclipse.papyrus.uml.service.validation.oclpivot;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.evaluation.AbstractConstraintEvaluator;
import org.eclipse.ocl.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.pivot.uml.internal.validation.UMLOCLEValidator;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This class is based on the AbstractOCLModelConstraint in org.eclipse.emf.validation.ocl. The main difference is that it enforces
 * the validation with the pivot OCL variant, see bug 436296 - [Validation] DSML plugin generation is broken
 *
 * @link org.eclipse.emf.validation.ocl.AbstractOCLModelConstraint
 */
public abstract class AbstractOCLpivotModelConstraint implements IModelConstraint {

	protected static final class OCLAndQueries {
		/**
		 * Constructor that initializes OCL
		 */
		public OCLAndQueries() {
			ocl = OCL.newInstance();
			queries = new HashMap<IModelConstraint, ExpressionInOCL>();
		}

		OCL ocl;
		Map<IModelConstraint, ExpressionInOCL> queries;
	}

	/**
	 * WeakOCLReference maintains the reference to the OCL instances. Inspired by class of same name in UMLOCLEvalidator
	 * (which cannot be reused directly, as it is protected).
	 */
	protected static final class WeakOCLReference extends WeakReference<OCLAndQueries>
	{
		protected WeakOCLReference(OCLAndQueries ocl) {
			super(ocl);
			// create copy, since get() already returns null, when finalizer gets called 
			oclCopy = ocl;
		}

		OCLAndQueries oclCopy;
		
		@Override
		public void finalize() {
			new Thread("OCLandQueries-Finalizer") //$NON-NLS-1$
			{
				@Override
				public void run() {
					if (oclCopy.queries != null) {
						oclCopy.queries.clear();
					}
					oclCopy.ocl.dispose();
				}
			}.start();
		}
	}

	private final IConstraintDescriptor descriptor;

	protected static Map<ResourceSet, WeakOCLReference> oclRefMap = null;

	/**
	 * Initializes me with the <code>descriptor</code> which contains my OCL
	 * body.
	 * 
	 * @param descriptor
	 *            the descriptor, which must contain an OCL expression in its
	 *            body
	 */
	public AbstractOCLpivotModelConstraint(IConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Return the OCL context for the validation, caching the created value in the validation context for re-use by
	 * further validations. The cached reference is weak to ensure that the OCL context is disposed once no longer in use.
	 */
	protected synchronized OCLAndQueries getOCL(EObject element) {
		ResourceSet rs = element.eResource().getResourceSet();
		if (oclRefMap == null) {
			oclRefMap = new HashMap<ResourceSet, WeakOCLReference>();
		}
		WeakOCLReference oclRef = oclRefMap.get(rs);
		if ((oclRef == null) || (oclRef.get() == null)) {
			oclRef = new WeakOCLReference(new OCLAndQueries());
			oclRefMap.put(rs, oclRef);
		}
		return oclRef.get();
	}


	/**
	 * Obtain the cached OCL query/constraint
	 * 
	 * @param target
	 *            a model element (typically stereotype application)
	 * @param ocl
	 *            an OCL instance
	 * @param queries
	 *            map a map between constraint and query. Used to cache queries.
	 * @return the corresponding OCL query
	 */
	public ExpressionInOCL getConstraintCondition(EObject target, OCL ocl, Map<IModelConstraint, ExpressionInOCL> queries) {

		Stereotype umlStereotype = UMLUtil.getStereotype(target);

		if (umlStereotype == null) {
			return null;
		}

		ExpressionInOCL result = queries.get(this);

		if (result == null) {
			// create query, if not existing yet
			try {
				org.eclipse.ocl.pivot.Class context =
						ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Class.class, umlStereotype);

				String expression = getDescriptor().getBody();
				result = ocl.createQuery(context, expression);
			} catch (ParserException parserException) {
				throw new WrappedException(parserException);
			}
			queries.put(this, result);
		}

		return result;
	}

	/**
	 * Implement inherited validation method
	 *
	 * @see org.eclipse.emf.validation.model.IModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 *
	 * @param ctx
	 *            the validation context
	 * @return the result of the query evaluation in form of a status entry
	 */
	public IStatus validate(IValidationContext ctx) {
		EObject target = ctx.getTarget();

		try {
			OCLAndQueries oclAndQueries = getOCL(target);
			ExpressionInOCL query = getConstraintCondition(target, oclAndQueries.ocl, oclAndQueries.queries);
			AbstractConstraintEvaluator<Boolean> constraintEvaluator = new UMLOCLEValidator.ConstraintEvaluatorWithoutDiagnostics(query);
			EvaluationVisitor evaluationVisitor = oclAndQueries.ocl.createEvaluationVisitor(target, query);
			if (constraintEvaluator.evaluate(evaluationVisitor)) {
				return ctx.createSuccessStatus();
			} else {
				// OCL constraints only support the target object as an extraction
				// variable and result locus, as OCL has no way to provide
				// additional extractions. Also, there is no way for the OCL
				// to access the context object
				return ctx.createFailureStatus(target);
			}

		} catch (Exception e) {
			// do not raise an exception, but create a failure status. This is consistent with
			// the behavior of the "in-profile" OCL pivot validation.
			String message = String.format("The '%s' constraint is invalid - %s", getDescriptor().getName(), e.getMessage()); //$NON-NLS-1$
			return new ConstraintStatus(this, target, IStatus.ERROR, -1,
					message, null);
		}
	}

	/**
	 * return the constraint descriptor
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
