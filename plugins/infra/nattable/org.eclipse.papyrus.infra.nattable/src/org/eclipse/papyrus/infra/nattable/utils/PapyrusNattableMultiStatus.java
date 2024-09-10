/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

/**
 * The multi status for the papyrus nattable status to add some severity (paste configuration severities).
 */
public class PapyrusNattableMultiStatus extends MultiStatus implements IPapyrusNattableStatus {

	/**
	 * The severity.
	 */
	private int severity;
	
	/**
	 * Creates and returns a new multi-status object with the given children.
	 *
	 * @param pluginId the unique identifier of the relevant plug-in
	 * @param code the plug-in-specific status code
	 * @param newChildren the list of children status objects
	 * @param message a human-readable message, localized to the
	 *    current locale
	 * @param exception a low-level exception, or <code>null</code> if not
	 *    applicable 
	 */
	public PapyrusNattableMultiStatus(final String pluginId, final int code, final IStatus[] newChildren, final String message, final Throwable exception) {
		super(pluginId, code, newChildren, message, exception);
		int maxSeverity = getSeverity();
		for (int i = 0; i < newChildren.length; i++) {
			Assert.isLegal(newChildren[i] != null);
			int severity = newChildren[i].getSeverity();
			if (severity > maxSeverity)
				maxSeverity = severity;
		}
		setSeverity(maxSeverity);
	}

	/**
	 * Creates and returns a new multi-status object with no children.
	 *
	 * @param pluginId the unique identifier of the relevant plug-in
	 * @param code the plug-in-specific status code
	 * @param message a human-readable message, localized to the
	 *    current locale
	 * @param exception a low-level exception, or <code>null</code> if not
	 *    applicable 
	 */
	public PapyrusNattableMultiStatus(String pluginId, int code, String message, Throwable exception) {
		super(pluginId, code, message, exception);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IStatus#getSeverity()
	 */
	@Override
	public int getSeverity() {
		return severity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IStatus#isOK()
	 */
	@Override
	public boolean isOK() {
		return severity == OK;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IStatus#matches(int)
	 */
	@Override
	public boolean matches(int severityMask) {
		return (severity & severityMask) != 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Status#setSeverity(int)
	 */
	@Override
	protected void setSeverity(int severity) {
		Assert.isLegal(severity == OK || severity == ERROR || severity == WARNING || severity == INFO || severity == CANCEL 
				|| severity == PASTE_CONFIGURATiON_INFO || severity == PASTE_CONFIGURATiON_WARNING || severity == PASTE_CONFIGURATiON_ERROR);
		this.severity = severity;
	}
}
