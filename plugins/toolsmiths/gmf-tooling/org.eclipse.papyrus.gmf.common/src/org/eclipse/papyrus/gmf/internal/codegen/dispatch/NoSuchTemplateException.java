/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.dispatch;

/**
 * @author artem
 */
public class NoSuchTemplateException extends Exception {

	private static final long serialVersionUID = -1892646974029705702L;

	/**
	 * @param templateName
	 */
	public NoSuchTemplateException(String templateName) {
		super(templateName);
	}

	/**
	 * @param templateName
	 * @param cause
	 */
	public NoSuchTemplateException(String templateName, Throwable cause) {
		super(templateName, cause);
	}

}
