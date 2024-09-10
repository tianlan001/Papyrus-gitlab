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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Utility methods to manage status.
 */
public class StatusUtils {

	/**
	 * Returns a localized message describing the given exception. If the given exception does not have a localized message, this returns the string "An error occurred".
	 *
	 * @param exception
	 *            The current exception.
	 * @return The localized message of the exception.
	 */
	public static String getLocalizedMessage(final Throwable exception) {
		String message = exception.getLocalizedMessage();

		if (message != null) {
			return message;
		}

		// Workaround for the fact that CoreException does not implement a getLocalizedMessage() method.
		// Remove this branch when and if CoreException implements getLocalizedMessage()
		if (exception instanceof CoreException) {
			CoreException ce = (CoreException) exception;
			return ce.getStatus().getMessage();
		}

		return "An unexpected exception was thrown."; //$NON-NLS-1$
	}

	/**
	 * This allows to get the cause of an exception.
	 *
	 * @param exceptionThe
	 *            current exception.
	 * @return The cause of an exception.
	 */
	public static Throwable getCause(final Throwable exception) {
		// Figure out which exception should actually be logged -- if the given exception is a wrapper, unwrap it
		Throwable cause = null;
		if (exception != null) {
			if (exception instanceof CoreException) {
				// Workaround: CoreException contains a cause, but does not actually implement getCause().
				// If we get a CoreException, we need to manually unpack the cause. Otherwise, use the general-purpose mechanism. Remove this branch if CoreException ever implements a correct getCause() method.
				final CoreException ce = (CoreException) exception;
				cause = ce.getStatus().getException();
			} else {
				// use reflect instead of a direct call to getCause(), to allow compilation against JCL Foundation (bug 80053)
				try {
					final Method causeMethod = exception.getClass().getMethod("getCause"); //$NON-NLS-1$
					final Object o = causeMethod.invoke(exception);
					if (o instanceof Throwable) {
						cause = (Throwable) o;
					}
				} catch (NoSuchMethodException e) {
					// ignore
				} catch (IllegalArgumentException e) {
					// ignore
				} catch (IllegalAccessException e) {
					// ignore
				} catch (InvocationTargetException e) {
					// ignore
				}
			}

			if (null == cause) {
				cause = exception;
			}
		}

		return cause;
	}

	/**
	 * This method must not be called outside the workbench.
	 * Utility method for creating status.
	 *
	 * @param pluginId
	 *            The plugin identifier.
	 * @param severity
	 *            The severity of the status to create.
	 * @param message
	 *            The message of the status to create.
	 * @param exception
	 *            The exception of the status to create.
	 * @return The created status.
	 */
	public static IStatus newStatus(final String pluginId, final int severity, final String message, final Throwable exception) {

		String statusMessage = message;
		if (message == null || message.trim().length() == 0) {
			if (exception.getMessage() == null) {
				statusMessage = exception.toString();
			} else {
				statusMessage = exception.getMessage();
			}
		}

		return new Status(severity, pluginId, severity, statusMessage, getCause(exception));
	}

}
