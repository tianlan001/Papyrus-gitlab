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

package org.eclipse.papyrus.infra.nattable.utils;

/**
 * 
 * @author Vincent Lorenzo
 *
 *         This class can be used to store the axis linked to a cell
 *
 * @param <A>
 *            the type of the first axis
 * @param <B>
 *            the type of the second axis
 */
public class CrossAxisWrapper<A, B> {

	/**
	 * the first axis
	 */
	private A firstAxis;

	/**
	 * the second axis
	 */
	private B secondAxis;

	/**
	 * 
	 * Constructor.
	 *
	 * @param firstAxis
	 * @param secondAxis
	 */
	public CrossAxisWrapper(A firstAxis, B secondAxis) {
		this.firstAxis = firstAxis;
		this.secondAxis = secondAxis;
	}

	/**
	 * 
	 * @return
	 *         the row element
	 */
	public A getFirstAxis() {
		return firstAxis;
	}

	/**
	 * 
	 * @return
	 *         the column element
	 */
	public B getSecondAxis() {
		return secondAxis;
	}
}