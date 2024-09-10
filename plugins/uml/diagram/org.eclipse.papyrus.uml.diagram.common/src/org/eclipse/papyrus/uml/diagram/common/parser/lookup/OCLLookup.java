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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.uml2.uml.NamedElement;

public class OCLLookup<T extends NamedElement> implements Lookup<T> {

	public static interface Expression {

		public Object evaluate(Object context);
	}

	private Expression mySelector;

	private final ArrayList<IElementType> myResolutionTypes;

	private static final IElementType[] NO_RESOLUTIONS = new IElementType[0];

	// @unused
	public OCLLookup(Expression ocl) {
		this(ocl, NO_RESOLUTIONS);
	}

	public OCLLookup(Expression ocl, IElementType[] resolutions) {
		mySelector = ocl;
		if (resolutions == null) {
			resolutions = NO_RESOLUTIONS;
		}
		myResolutionTypes = new ArrayList<IElementType>(Arrays.asList(resolutions));
	}

	@Override
	public List getResolutionElementTypes() {
		return new ArrayList<IElementType>(myResolutionTypes);
	}

	public List<T> computeScope(EObject context) {
		Object result = mySelector.evaluate(context);
		if (result instanceof Collection) {
			return new LinkedList<T>((Collection<T>) result);
		}
		return Collections.emptyList();
	}

	@Override
	public T lookup(String name, EObject context) {
		List<T> scope = computeScope(context);
		for (T next : scope) {
			if (name.equals(next.getName()) || name.equals(next.getQualifiedName())) {
				return next;
			}
		}
		return null;
	}

}
