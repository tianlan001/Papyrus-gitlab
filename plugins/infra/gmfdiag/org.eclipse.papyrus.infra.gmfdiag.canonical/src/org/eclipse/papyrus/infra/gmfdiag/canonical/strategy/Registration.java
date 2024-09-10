/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.internal.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

import com.google.common.base.Strings;

/**
 * Abstract implementation of an optionally filtered lazy-loading extension-point registration with priority ordering.
 * Extensions compare in priority order (least priority is last).
 */
abstract class Registration<T, R extends Registration<? extends T, R>> implements Comparable<R> {

	private static final String ENABLEMENT = "enablement"; //$NON-NLS-1$

	private static final String CLASS = "class"; //$NON-NLS-1$

	private static final String PRIORITY = "priority"; //$NON-NLS-1$

	private final IConfigurationElement config;

	private final Class<? extends T> extensionType;

	private final int priority;

	private final Expression filterExpression;

	private T extension;

	private IStatus extensionFailure;

	protected Registration(IConfigurationElement config, Class<? extends T> extensionType) throws CoreException {
		super();

		this.config = config;
		this.extensionType = extensionType;
		this.filterExpression = parseExpression(config, ENABLEMENT);
		this.priority = parseInt(config, PRIORITY);
	}

	/**
	 * Obtains the extension's priority relative to others that match the same filter criteria (if any).
	 */
	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(R o) {
		return this.getPriority() - o.getPriority();
	}

	public boolean isApplicableTo(EditPart editPart) {
		boolean result = !hasFilterExpression(); // In case there's no filter

		if (!result) {
			try {
				IEvaluationContext context = new EvaluationContext(null, editPart);
				View view = NotationHelper.findView(editPart);
				if (view != null) {
					EObject element = view.getElement();
					if (element != null) {
						context.addVariable("editPart", editPart); //$NON-NLS-1$
						context.addVariable("view", view); //$NON-NLS-1$
						context.addVariable("element", element); //$NON-NLS-1$
						result = EvaluationResult.TRUE.equals(filterExpression.evaluate(context));
					}
				}
			} catch (CoreException e) {
				Activator.getDefault().getLog().log(e.getStatus());
			}
		}

		return result;
	}

	protected boolean hasFilterExpression() {
		return filterExpression != null;
	}

	protected final IConfigurationElement getConfigurationElement() {
		return config;
	}

	/**
	 * Obtains the extension instance, creating it if necessary.
	 * 
	 * @return the extension instance
	 */
	public T getExtension() {
		T result = extension;

		if ((result == null) && (extensionFailure == null)) {
			synchronized (this) {
				try {
					Object instance = config.createExecutableExtension(CLASS);
					if (!extensionType.isInstance(instance)) {
						throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Extension is not an instance of " + extensionType.getClass().getName())); //$NON-NLS-1$
					}
					if (extension == null) {
						extension = extensionType.cast(instance);
					}
				} catch (CoreException e) {
					extensionFailure = e.getStatus();
					Activator.log.error("Failed to instantiate extension.", e); //$NON-NLS-1$
				}

				result = extension;
			}
		}

		return result;
	}

	static int parseInt(IConfigurationElement config, String attribute) {
		int result = 0;

		String attrValue = config.getAttribute(attribute);
		if (!Strings.isNullOrEmpty(attrValue)) {
			try {
				result = Integer.parseInt(attrValue);
			} catch (Exception e) {
				Activator.log.warn(String.format("Not an integer value in '%s' attribute of '%s' element from plug-in '%s': %s", //$NON-NLS-1$
						attribute, config.getName(), config.getContributor().getName(), attrValue));
			}
		}

		return result;
	}

	static Expression parseExpression(IConfigurationElement config, String elementName) throws CoreException {
		Expression result = null;

		IConfigurationElement[] enablement = config.getChildren(elementName);
		if (enablement.length > 0) {
			result = ExpressionConverter.getDefault().perform(enablement[0]);
		}

		return result;
	}
}
