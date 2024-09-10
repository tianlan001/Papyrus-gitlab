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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.utils.utils;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This class is created to manage the Qualified Names calculation.
 */
public class UMLQualifiedNameUtils {

	/**
	 * Private constructor to avoid instanciation.
	 */
	private UMLQualifiedNameUtils() {
		// Do nothing
	}

	/**
	 * This method comes from {@link UMLUtil#getQualifiedName(NamedElement, String)}.
	 * While this one is protected in UMLUtil, we need to keep it here.
	 * 
	 * @param namedElement
	 *            The named element to manage.
	 * @param separator
	 *            The namespace separator.
	 * @return The qualified name with the correct separator.
	 */
	public static String getQualifiedName(final NamedElement namedElement, final String separator) {
		final String name = namedElement.getName();

		if (UMLUtil.isEmpty(name)) {
			return null;
		}

		final StringBuffer qualifiedName = new StringBuffer(name);

		for (final Namespace namespace : namedElement.allNamespaces()) {
			final String namespaceName = namespace.getName();

			if (UMLUtil.isEmpty(namespaceName)) {
				return null;
			} else {
				qualifiedName.insert(0, separator);
				qualifiedName.insert(0, namespaceName);
			}
		}

		return qualifiedName.toString();
	}

}
