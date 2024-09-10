/******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.util.List;


public interface IExtensionTemplatesProvider {
	
	/**
	 * Get Classes for Custom Templates from Custom Generator Extension.
	 * 
	 */
	public List<Class<?>> getCustomTemplateClasses();
	
	/**
	 * Get Classes for Custom Templates from /aspect folder.
	 * 
	 */
	public List<Class<?>> getDynamicTemplateClasses();
	
	/**
	 * Get Class which _class override.
	 * 
	 * @return List<URL>
	 */
	public Class<?> getSuperClassForDynamic(Class<?> _class);
	
	/**
	 * Unload bundle from runtime. Necessarily to call this function
	 * 
	 */
	public void dispose();
}