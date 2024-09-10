/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Modification to match IValidator
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.validator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.widgets.messages.Messages;

/**
 * Validator for the Real
 */
public class RealValidator extends AbstractValidator {


	/**
	 *
	 * @param newValue
	 * @return {@link Status#OK_STATUS} if the newValue is valid and {@link IStatus#ERROR} when newValue is
	 *         invalid
	 */
	@Override
	public IStatus validate(Object newValue) {
		if (newValue instanceof Double) {
			return Status.OK_STATUS;
		}

		if (newValue instanceof String) {
			try {
				Double.parseDouble((String) newValue);
				return Status.OK_STATUS;
			} catch (NumberFormatException ex) {
				return error(Messages.RealInputValidator_NotaRealMessage);
			}
		}

		return error(Messages.RealInputValidator_NotaRealMessage);
	}


}
