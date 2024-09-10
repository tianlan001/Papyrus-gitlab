/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading;


/**
 * The Interface IStrategyChooser that provides the current loading strategy to the proxy manager.
 * Proxy manager load the resource if needed, according to the current strategy
 */
public interface IStrategyChooser {

	/**
	 * Gets the current strategy ID.
	 * Proxy manager load the resource if needed, according to the current strategy
	 *
	 * @return the current strategy id
	 */
	int getCurrentStrategy();

	/**
	 * Suggests a new strategy to take effect in preference over the current.
	 * The strategy-chooser may deny the suggestion, in which case the current
	 * strategy remains effective.
	 * 
	 * @param strategy
	 *            the new strategy to engage
	 * 
	 * @return whether the suggestion to set the new {@code strategy} was accepted and implemented
	 * 
	 * @since 2.0
	 */
	default boolean setStrategy(int strategy) {
		return strategy == getCurrentStrategy();
	}
}
