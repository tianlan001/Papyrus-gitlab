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

package org.eclipse.papyrus.emf.ui.validators;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.emf.ui.messages.Messages;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Abstract selection validator
 */
public abstract class AbstractSelectionStatusValidator implements ISelectionStatusValidator {

	/**
	 * the name of the plugin (EMF Edit plugin doesn't provide ID)
	 */
	private static final String PLUGIN_NAME = "org.eclipse.papyrus.emf.ui"; //$NON-NLS-1$

	/**
	 * No Selection message
	 */
	protected static final String NO_SELECTION = Messages.AbstractSelectionStatusValidator_NoSelection;

	/**
	 *
	 * @param errorMessage
	 *            the error message, can be <code>null</code> of empty
	 * @return
	 *         an {@link IStatus#ERROR} if the message is defined or an {@link IStatus#OK} if the message is <code>null</code> of emepty
	 */
	protected final IStatus buildIStatus(final String errorMessage) {
		return new Status(errorMessage.isEmpty() ? IStatus.OK : IStatus.ERROR, PLUGIN_NAME, errorMessage);
	}
}
