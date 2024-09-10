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
import org.eclipse.core.runtime.Status;

/**
 * The status for the papyrus nattable status to add some severity (paste configuration severities).
 */
public class PapyrusNattableStatus extends Status implements IPapyrusNattableStatus {

	/**
	 * The severity.
	 */
	private int severity;

	/**
	 * Creates a new status object. The created status has no children.
	 *
	 * @param severity
	 *            the severity; one of <code>OK</code>, <code>ERROR</code>,
	 *            <code>INFO</code>, <code>WARNING</code>, or <code>CANCEL</code>
	 * @param pluginId
	 *            the unique identifier of the relevant plug-in
	 * @param code
	 *            the plug-in-specific status code, or <code>OK</code>
	 * @param message
	 *            a human-readable message, localized to the
	 *            current locale
	 * @param exception
	 *            a low-level exception, or <code>null</code> if not
	 *            applicable
	 */
	public PapyrusNattableStatus(int severity, String pluginId, int code, String message, Throwable exception) {
		super(severity, pluginId, code, message, exception);
	}

	/**
	 * Simplified constructor of a new status object; assumes that code is <code>OK</code>.
	 * The created status has no children.
	 *
	 * @param severity
	 *            the severity; one of <code>OK</code>, <code>ERROR</code>,
	 *            <code>INFO</code>, <code>WARNING</code>, or <code>CANCEL</code>
	 * @param pluginId
	 *            the unique identifier of the relevant plug-in
	 * @param message
	 *            a human-readable message, localized to the
	 *            current locale
	 * @param exception
	 *            a low-level exception, or <code>null</code> if not
	 *            applicable
	 */
	public PapyrusNattableStatus(int severity, String pluginId, String message, Throwable exception) {
		super(severity, pluginId, message, exception);
	}

	/**
	 * Simplified constructor of a new status object; assumes that code is <code>OK</code> and
	 * exception is <code>null</code>. The created status has no children.
	 *
	 * @param severity
	 *            the severity; one of <code>OK</code>, <code>ERROR</code>,
	 *            <code>INFO</code>, <code>WARNING</code>, or <code>CANCEL</code>
	 * @param pluginId
	 *            the unique identifier of the relevant plug-in
	 * @param message
	 *            a human-readable message, localized to the
	 *            current locale
	 */
	public PapyrusNattableStatus(int severity, String pluginId, String message) {
		super(severity, pluginId, message);
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Status#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Status "); //$NON-NLS-1$
		if (severity == OK) {
			buf.append("OK"); //$NON-NLS-1$
		} else if (severity == ERROR) {
			buf.append("ERROR"); //$NON-NLS-1$
		} else if (severity == WARNING) {
			buf.append("WARNING"); //$NON-NLS-1$
		} else if (severity == INFO) {
			buf.append("INFO"); //$NON-NLS-1$
		} else if (severity == CANCEL) {
			buf.append("CANCEL"); //$NON-NLS-1$
		} else if (severity == PASTE_CONFIGURATiON_INFO) {
			buf.append("PASTE_CONFIGURATiON_INFO"); //$NON-NLS-1$
		} else if (severity == PASTE_CONFIGURATiON_WARNING) {
			buf.append("PASTE_CONFIGURATiON_WARNING"); //$NON-NLS-1$
		} else if (severity == PASTE_CONFIGURATiON_ERROR) {
			buf.append("PASTE_CONFIGURATiON_ERROR"); //$NON-NLS-1$
		} else {
			buf.append("severity="); //$NON-NLS-1$
			buf.append(severity);
		}
		buf.append(": "); //$NON-NLS-1$
		buf.append(getPlugin());
		buf.append(" code="); //$NON-NLS-1$
		buf.append(getCode());
		buf.append(' ');
		buf.append(getMessage());
		buf.append(' ');
		buf.append(getException());
		return buf.toString();
	}
}
