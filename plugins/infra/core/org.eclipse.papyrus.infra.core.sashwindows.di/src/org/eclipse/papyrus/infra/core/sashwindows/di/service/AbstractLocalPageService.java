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
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sashwindows.di.service;


/**
 * Basic implementation of the local page service which contains the root element that contains pages.
 */
public abstract class AbstractLocalPageService implements ILocalPageService {

	/** The root element. */
	protected Object rootElement = null;

	/**
	 * Constructor.
	 *
	 */
	public AbstractLocalPageService(Object rootElement) {
		this.rootElement = rootElement;
	}



}
