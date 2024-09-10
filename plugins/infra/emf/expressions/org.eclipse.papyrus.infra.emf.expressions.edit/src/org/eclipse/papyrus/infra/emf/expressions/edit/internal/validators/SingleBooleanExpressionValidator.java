/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.edit.internal.validators;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.emf.ui.validators.AbstractSelectionStatusValidator;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.edit.internal.messages.Messages;

/**
 * Validator for single selection of BooleanExpression
 */
public final class SingleBooleanExpressionValidator extends AbstractSelectionStatusValidator {

	/**
	 * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
	 *
	 * @param selection
	 * @return
	 */
	@Override
	public IStatus validate(Object[] selection) {
		String errorMessage = ""; //$NON-NLS-1$
		if (selection.length == 0) {
			errorMessage = NO_SELECTION;
		} else {
			final Object firstSelection = selection[0];
			if (false == firstSelection instanceof IBooleanExpression<?> || selection.length != 1) {
				errorMessage = Messages.SingleBooleanExpressionValidator_YouMustSelectOneBooleanExpression;
			}
		}

		return buildIStatus(errorMessage);
	}

}

