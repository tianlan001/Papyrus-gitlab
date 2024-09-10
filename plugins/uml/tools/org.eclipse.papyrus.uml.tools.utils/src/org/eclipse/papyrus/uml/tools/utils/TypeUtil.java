/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Yann TANGUY (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Iterator;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Type;

/**
 * Utility class for <code>org.eclipse.uml2.uml.Type</code><BR>
 */
public class TypeUtil {

	public static final String UNDEFINED_TYPE_NAME = "<Undefined>";

	/**
	 * Get a string that displays the name of the type, and then its namespace.
	 * <p>
	 * For example: "String - UMLPrimitiveType"
	 *
	 * @return a string that displays information about the type
	 */
	public static String getInfoString(Type type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UMLLabelInternationalization.getInstance().getLabel(type));
		buffer.append(" - ");
		String tmp = "";
		Iterator<Namespace> it = type.allNamespaces().iterator();
		while (it.hasNext()) {
			Namespace namespace = it.next();
			if (it.hasNext()) {
				tmp = NamedElement.SEPARATOR + UMLLabelInternationalization.getInstance().getLabel(namespace) + tmp;
			} else {
				tmp = UMLLabelInternationalization.getInstance().getLabel(namespace) + tmp;
			}
		}
		buffer.append(tmp);
		return buffer.toString();
	}

	/**
	 *
	 *
	 * @param type
	 *            to check
	 *
	 * @return true if type is metaclass, else false
	 */
	@Deprecated
	// use {@link Class#isMetaclass()} Check if a type is a metaclass.
	public static boolean isMetaclass(Type type) {
		if (type instanceof Class) {
			return ((Class) type).isMetaclass();
		}
		return false;
	}
}
