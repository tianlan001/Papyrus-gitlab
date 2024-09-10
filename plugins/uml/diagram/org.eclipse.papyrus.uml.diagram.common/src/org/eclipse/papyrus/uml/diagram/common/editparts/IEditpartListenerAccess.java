/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import java.util.Iterator;

import org.eclipse.gef.editparts.AbstractEditPart;

/**
 * All edit part that implements this interface make accessible the set of
 * eventListener
 *
 * @see AbstractEditPart
 *
 */
public interface IEditpartListenerAccess {

	/**
	 * Returns an iterator for the specified type of listener
	 *
	 * @param clazz
	 *            the Listener type over which to iterate
	 * @return Iterator
	 */
	public Iterator getEventListenerIterator(Class clazz);
}
