/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.utils;

import java.util.Comparator;

import org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement;



/**
 * Comparator used to sort IBasicExpressionElement
 */
public class ExpressionNameComparator implements Comparator<IBasicExpressionElement> {

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	@Override
	public int compare(final IBasicExpressionElement arg0, final IBasicExpressionElement arg1) {
		final String name0 = null == arg0 ? EMPTY_STRING : null != arg0.getName() ? arg0.getName() : EMPTY_STRING;
		final String name1 = null == arg1 ? EMPTY_STRING : null != arg1.getName() ? arg1.getName() : EMPTY_STRING;
		return name0.compareTo(name1);
	}


}
