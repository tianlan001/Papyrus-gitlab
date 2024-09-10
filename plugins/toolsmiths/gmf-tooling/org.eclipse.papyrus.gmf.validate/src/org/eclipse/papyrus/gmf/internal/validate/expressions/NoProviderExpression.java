/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate.expressions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.gmf.internal.validate.GMFValidationPlugin;
import org.eclipse.papyrus.gmf.internal.validate.Messages;
import org.eclipse.papyrus.gmf.internal.validate.StatusCodes;

public class NoProviderExpression extends AbstractExpression {
	private String lang;
	
	public NoProviderExpression(final String language, String body, EClassifier context) {
		super(body, context, null);
		this.lang = language;
		String message = NLS.bind(Messages.noExpressionProviderAvailable, language);
		IStatus status = new Status(IStatus.ERROR, GMFValidationPlugin.getPluginId(),
				StatusCodes.EXPRESSION_PROVIDER_NOT_AVAILABLE, message, null);			
		setStatus(status);
	}

	public boolean isAssignableToElement(ETypedElement typedElement) {
		return false;
	}

	public boolean isAssignableTo(EClassifier ecoreType) {
		return false;
	}
	
	public String getLanguage() {
		return lang;
	}

	protected Object doEvaluate(Object context) {
		return null;
	}

	protected Object doEvaluate(Object context, IEvaluationEnvironment extEnvironment) {
		return null;
	}
}