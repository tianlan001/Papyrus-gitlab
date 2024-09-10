/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.utils;


/**
 * Class wishing to observe a {@link IObservableList} can implements this interface and register it
 * to the List.
 *
 * @author cedric dumoulin
 *
 * @param E
 *            Type of the element of the list.
 */
public interface IObservableListListener<E> {


	/**
	 * Method called when an element is added to the list.
	 *
	 * @param element
	 */
	public void elementAdded(E element);

	/**
	 * Method called when an element is removed from the list.
	 *
	 * @param element
	 */
	public void elementRemoved(E element);

}
