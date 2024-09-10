/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
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
 *   Benoit Maggi (CEA LIST) - Bug 518317 Autocompletion for type of properties
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.util;

import java.util.List;
import java.util.function.Predicate;

/**
 * Common interface to use to find elements from a given string
 *
 */
public interface INameResolutionHelper {

	/**
	 * 
	 * @param aString
	 *            a string
	 * @return
	 * 		all elements whose the name starts with this string, or all found element if the string is <code>null</code> or empty
	 */
	public List<?> getMatchingElements(final String aString);

	
	/**
	 * Use a {@link java.util.function.Predicate} to get matching elements.
	 * 
	 * @param predicate to match the elements 
	 * @return all elements whose match the predicate
	 */
	public default List<?> getMatchingElements(final Predicate<?> predicate){
		return getElementsByName(""); //$NON-NLS-1$ TODO: remove default when bumping plugin to 4.0.0
	}
	
	/**
	 * 
	 * @param aString
	 *            a string
	 * @return
	 * 		all elements which have the wanted string as (qualified) name
	 */
	public List<?> getElementsByName(final String aString);


	/**
	 * Get the shortest qualified name.
	 * 
	 * @param element
	 *            The element to get the shortest qualified name.
	 * @return
	 * 		the shortest qualified to use for the element
	 * @deprecated since 2.0
	 */
	@Deprecated
	public List<String> getShortestQualifiedNames(final Object element);

	/**
	 * Get the shortest qualified name.
	 * 
	 * @param element
	 *            The element to get the shortest qualified name.
	 * @param manageDuplicate
	 *            Boolean to determinate if the duplicated shortest qualified names must be remove from the returned list.
	 * @return the shortest qualified to use for the element
	 * 
	 * @since 2.0
	 */
	public List<String> getShortestQualifiedNames(final Object element, final boolean manageDuplicate);

	
	
}
