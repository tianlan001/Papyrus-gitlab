/*******************************************************************************
 * Copyright (c) 2013-2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Michael Golubev (Montages) - initial API and implementation
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt

import java.util.regex.Pattern

@com.google.inject.Singleton class Common_qvto {

	def oclIsKindOf(Object obj, Class<?> clazz) {
		return obj !== null && clazz.isInstance(obj);
	}

	def String lastSegment(String qualifiedName) {
		lastSegment(qualifiedName, ".")
	}

	def String withoutLastSegment(String qualifiedName) {
		withoutLastSegment(qualifiedName, ".")
	}

	def String lastSegment(String qualifiedName, String separator) {
		qualifiedName.split(Pattern::quote(separator)).lastOrNull;
	}

	def String withoutLastSegment(String qualifiedName, String separator) {
		var parts = qualifiedName.split(Pattern::quote(separator))
		return parts.take(parts.size - 1).join(separator)
	}

	def String substringAfter(String name, String prefix){
		if (!name.startsWith(prefix)){
			throw new IllegalArgumentException("String " + name + " does not startWith: " + prefix)
		}
		return name.substring(prefix.length);
	}

	def ERROR(String msg) {
		throw new IllegalStateException(msg)
	}

	def <T> T notNullOf(T a, T b) {
		if (a !== null) a else b
	}

	def boolean nullOrSpaces(String text) {
		return text === null || text.trim().length == 0
	}

	def <T> boolean notEmpty(Iterable<T> collection) {
		return !collection.empty;
	}

}
