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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.configuration;

/**
 * This interface is used to create configuration for Papyrus Table.
 *
 */
public interface IPapyrusNatTableConfiguration {

	/**
	 * 
	 * @return
	 *         the id of the configuration. It must be unique
	 */
	public String getConfigurationId();

	/**
	 * 
	 * @return
	 *         a description of the editor. This description could be used in a dialog allowing to choose a specific configuration
	 */
	public String getConfigurationDescription();
}
