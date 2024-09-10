/******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common;

import java.util.Collection;

/**
 * Base names dispenser implementation.
 * 
 * @author dstadnik
 */
public abstract class AbstractNamesDispenser implements NamesDispenser {

	protected abstract Collection<String> getNamesPool();

	protected static String composeName(String prefixPart, String semanticPart, String suffixPart) {
		String s = suffixPart == null ? semanticPart : semanticPart + suffixPart;
		return prefixPart == null ? s : prefixPart + s;
	}

	public boolean contains(String name) {
		return getNamesPool().contains(name);
	}

	public final String get(String semanticPart, String suffixPart) {
		return get(null, semanticPart, suffixPart);
	}

	public final String get(String semanticPart) {
		return get(null, semanticPart, null);
	}

	public boolean add(String name) {
		return getNamesPool().add(name);
	}

	public void clear() {
		getNamesPool().clear();
	}
}
