/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sashwindows.di.service;


/**
 * Service for PageManager to determine local page.
 * 
 * @author Gabriel Pascual
 *
 */
public interface ILocalPageService {


	/**
	 * Checks if is local page.
	 *
	 * @param content
	 *            the content
	 * @return true, if is local page
	 */
	boolean isLocalPage(Object content);

}
