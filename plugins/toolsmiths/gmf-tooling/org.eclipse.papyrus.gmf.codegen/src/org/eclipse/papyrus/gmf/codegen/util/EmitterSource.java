/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * @author artem
 */
public abstract class EmitterSource<T extends EObject, E> {

	private final Map<URI, SoftReference<E>> myCachedURI2EmitterMap = new HashMap<URI, SoftReference<E>>();

	public E getEmitters(T genModel, boolean isDynamicTemplates) {
		URI resourceURI = genModel.eResource().getURI();
		if (isDynamicTemplates) {
			myCachedURI2EmitterMap.remove(resourceURI);
		}
		E old = myCachedURI2EmitterMap.containsKey(resourceURI) ? myCachedURI2EmitterMap.get(resourceURI).get() : null;
		if (old != null) {
			return old;
		}
		E newEmitters = newEmitters(genModel);
		if (!isDynamicTemplates) {
			myCachedURI2EmitterMap.put(resourceURI, new SoftReference<E>(newEmitters));
		}
		return newEmitters;
	}

	public void dispose() {
		myCachedURI2EmitterMap.clear();
	}

	protected abstract E newEmitters(T genModel);
}
