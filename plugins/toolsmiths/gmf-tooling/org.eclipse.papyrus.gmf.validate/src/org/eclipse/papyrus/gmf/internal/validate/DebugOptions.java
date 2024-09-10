/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;


public class DebugOptions {
	/**
	 * This class should not be instantiated because it has only static
	 * features.
	 */
	private DebugOptions() {
		super();
	}

	public static final String DEBUG = GMFValidationPlugin.getPluginId() + "/debug"; //$NON-NLS-1$

	public static final String META_DEFINITIONS = DEBUG + "/meta/definitions"; //$NON-NLS-1$
	public static final String CONSTRAINTS = DEBUG + "/constraints"; //$NON-NLS-1$

	
	public static final String EXCEPTIONS_CATCHING = DEBUG + "/exceptions/catching"; //$NON-NLS-1$
	public static final String EXCEPTIONS_THROWING = DEBUG + "/exceptions/throwing"; //$NON-NLS-1$

	public static final String METHODS_ENTERING = DEBUG + "/methods/entering"; //$NON-NLS-1$
	public static final String METHODS_EXITING = DEBUG + "/methods/exiting"; //$NON-NLS-1$
	
	public static final String CACHE = DEBUG + "/cache"; //$NON-NLS-1$
	
}
