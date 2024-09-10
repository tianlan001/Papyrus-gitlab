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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.blackboxes.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.uml2.uml.NamedElement;

/**
 * 
 * This class provides useful method for UML
 *
 */
public class UMLHelper {

	/**
	 * 
	 * @param listToSort
	 *            the list to sort
	 * @return
	 * 		the sorted list
	 */
	@Operation(kind = Kind.HELPER)
	public List<NamedElement> sortNamedElement(final List<NamedElement> listToSort) {
		final List<NamedElement> sortedList = new ArrayList<NamedElement>(listToSort);
		Collections.sort(sortedList, new NamedElementComparator());
		return sortedList;
	}

	/**
	 * 
	 * NamedElement comparator
	 *
	 */
	private class NamedElementComparator implements Comparator<NamedElement> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 *
		 * @param o1
		 * @param o2
		 * @return
		 */
		@Override
		public int compare(NamedElement o1, NamedElement o2) {
			String name1 = o1.getName();
			if (null == name1) {
				name1 = ""; //$NON-NLS-1$
			}
			String name2 = o2.getName();
			if (null == name2) {
				name2 = ""; //$NON-NLS-1$
			}
			return name1.compareToIgnoreCase(name2);
		}
	}

}
