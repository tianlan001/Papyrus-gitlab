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
import java.util.HashSet;
import java.util.Set;

/**
 * Names dispenser that generates unique names by appending
 * ascending numbers to the semantic part.
 * 
 * @author dstadnik
 */
public class IncrementalNamesDispenser extends AbstractNamesDispenser {

	private Set<String> namesPool = new HashSet<String>();

	private int initialIndex;

	public IncrementalNamesDispenser() {
		initialIndex = 2;
	}

	public IncrementalNamesDispenser(int initialIndex) {
		this.initialIndex = initialIndex;
	}

	protected final Collection<String> getNamesPool() {
		return namesPool;
	}

	public String get(String prefixPart, String semanticPart, String suffixPart) {
		String name = composeName(prefixPart, semanticPart, suffixPart);
		if (!contains(name)) {
			add(name);
			return name;
		}
		for (int i = initialIndex; i <= Integer.MAX_VALUE; i++) {
			name = composeName(prefixPart, semanticPart + i, suffixPart);
			if (!contains(name)) {
				add(name);
				return name;
			}
		}
		throw new IllegalStateException();
	}
}
