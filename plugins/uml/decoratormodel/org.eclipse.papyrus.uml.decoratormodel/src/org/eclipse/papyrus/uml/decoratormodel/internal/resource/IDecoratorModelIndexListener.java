/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.resource;

/**
 * Protocol for receiving notifications of index change events.
 */
public interface IDecoratorModelIndexListener {
	/**
	 * Notifies me that the index has changed, usually due to changes to workspace resources that are decorator models.
	 * 
	 * @param event
	 *            the change event
	 */
	void indexChanged(DecoratorModelIndexEvent event);
}
