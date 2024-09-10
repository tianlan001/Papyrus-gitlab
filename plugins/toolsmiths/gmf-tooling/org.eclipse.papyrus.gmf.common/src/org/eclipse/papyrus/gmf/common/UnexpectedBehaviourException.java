/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: 
 *     Artem Tikhomirov (Borland) - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.common;

/**
 * Checked flavor of {@link java.lang.IllegalStateException}.
 * @author artem
 */
public class UnexpectedBehaviourException extends Exception {
	private static final long serialVersionUID = 3762812684185579574L;

	public UnexpectedBehaviourException(String message) {
		super(message);
	}

	public UnexpectedBehaviourException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnexpectedBehaviourException(Throwable cause) {
		super(cause);
	}
}
