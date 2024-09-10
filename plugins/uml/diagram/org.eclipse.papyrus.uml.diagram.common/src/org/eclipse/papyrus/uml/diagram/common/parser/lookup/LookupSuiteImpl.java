/*
 * Copyright (c) 2006 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation
 */

package org.eclipse.papyrus.uml.diagram.common.parser.lookup;

import java.util.HashMap;

public class LookupSuiteImpl implements LookupSuite {

	private final HashMap<Class, Lookup> myLookups = new HashMap<Class, Lookup>();

	private LookupResolver myLookupResolver = LookupResolver.NULL;

	// @unused
	public <T> void addLookup(Class<T> clazz, Lookup<T> lookup) {
		if (myLookups.containsKey(clazz)) {
			throw new IllegalArgumentException("I already have lookup for :" + clazz);
		}
		myLookups.put(clazz, lookup);
	}

	@Override
	public <T> Lookup<T> getLookup(Class<T> clazz) {
		Lookup<T> result = myLookups.get(clazz);
		return result == null ? NULL_LOOKUP : result;
	}

	// @unused
	public void setLookupResolver(LookupResolver lookupResolver) {
		myLookupResolver = lookupResolver;
		if (myLookupResolver == null) {
			myLookupResolver = LookupResolver.NULL;
		}
	}

	// @unused
	public LookupResolver getLookupResolver() {
		return myLookupResolver;
	}

}
