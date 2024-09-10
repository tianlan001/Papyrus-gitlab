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

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

// @unused
public class LookupResolveRequest {

	private final List<IElementType> myElementTypes;

	private final EStructuralFeature myInitFeature;

	private final Object myInitValue;

	// @unused
	public LookupResolveRequest(List<IElementType> elementTypes, EStructuralFeature initFeature, Object initValue) {
		myElementTypes = elementTypes;
		myInitFeature = initFeature;
		myInitValue = initValue;
	}

	public List<IElementType> getElementTypes() {
		return myElementTypes;
	}

	public EStructuralFeature getInitFeature() {
		return myInitFeature;
	}

	public Object getInitValue() {
		return myInitValue;
	}

}
