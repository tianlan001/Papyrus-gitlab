/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.ui.comparators;

import java.util.Comparator;

import org.eclipse.emf.ecore.ENamedElement;

/**
 * Comparator for ENamedElement
 */
public class ENamedElementComparator implements Comparator<ENamedElement> {

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(final ENamedElement o1, final ENamedElement o2) {
		if (o1 != null && o2 != null) {
			final String o1Name = null == o1.getName() ? "" : o1.getName(); //$NON-NLS-1$
			final String o2Name = null == o2.getName() ? "" : o2.getName(); //$NON-NLS-1$
			return o1Name.compareTo(o2Name);
		}
		return 0;
	}

}