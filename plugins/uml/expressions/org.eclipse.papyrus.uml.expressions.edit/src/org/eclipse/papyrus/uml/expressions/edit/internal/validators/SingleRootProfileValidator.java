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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.validators;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.emf.ui.validators.AbstractSelectionStatusValidator;
import org.eclipse.papyrus.uml.expressions.edit.internal.messages.Messages;
import org.eclipse.uml2.uml.Profile;

/**
 * Validator for Single Selection of property attribute
 */
public class SingleRootProfileValidator extends AbstractSelectionStatusValidator {


	/**
	 * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
	 *
	 * @param selection
	 * @return
	 */
	@Override
	public IStatus validate(Object[] selection) {
		String errorMessage = ""; //$NON-NLS-1$
		if (selection.length <= 0) {
			errorMessage = NO_SELECTION;
		} else {
			final Object firstSelection = selection[0];
			final String mes = Messages.SingleRootProfileValidator_SelectARootProfileWithAURI;
			if (selection.length != 1 || false == firstSelection instanceof Profile) {
				errorMessage = mes;
			}
			if (selection.length == 1 && firstSelection instanceof Profile) {
				final Profile p = (Profile) firstSelection;
				if (p.eContainer() != null || p.getURI() == null || p.getURI().isEmpty()) {
					errorMessage = mes;
				}
			}
		}
		return buildIStatus(errorMessage);
	}

}
