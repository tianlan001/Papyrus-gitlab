/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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

package org.eclipse.papyrus.uml.profile.drafter.commands;


/**
 * ValueProxy can be used to share a value among sub-commands. 
 * While command are created, ValueProxy cand be shared among the command.
 * At execution time, a first command can set the value. Then, subsequent commands can use the value.
 * 
 * @author cedric dumoulin
 *
 */
public class ValueProxy<T> {

	/**
	 * The value to share.
	 */
	protected T value;

	
	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	
	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}
}
