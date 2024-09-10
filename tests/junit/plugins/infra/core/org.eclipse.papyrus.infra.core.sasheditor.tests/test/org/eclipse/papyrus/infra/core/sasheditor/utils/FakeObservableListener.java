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

import java.util.ArrayList;
import java.util.List;


/**
 * A fake listener for testing purpose.
 * 
 * @author cedric dumoulin
 *
 */
public class FakeObservableListener<E> implements IObservableListListener<E> {

	/**
	 * Trace of event received
	 */
	protected List<E> addEvents = new ArrayList<E>();
	
	/**
	 * Trace of event received
	 */
	protected List<E> removeEvents = new ArrayList<E>();
	
	
	/**
	 * @return the events
	 */
	public List<E> getAddEvents() {
		return addEvents;
	}

	/**
	 * @return the events
	 */
	public E getLastAddEvents() {
		if(addEvents.size()>0) {
		  return addEvents.get(addEvents.size() -1);
		}
		
		return null;
	}

	/**
	 * @return the events
	 */
	public List<E> getRemoveEvents() {
		return removeEvents;
	}

	/**
	 * @return the events
	 */
	public E getLastRemoveEvents() {
		if(removeEvents.size()>0) {
			return removeEvents.get(removeEvents.size() -1);
		}

		return null;
	}

	public void elementAdded(E element) {
		addEvents.add(element);
	}

	public void elementRemoved(E element) {
		removeEvents.add(element);		
	}

}
