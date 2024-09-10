/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.ui.converters;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

/**
 * This converter returns the initial input without convert it.
 */
public final class IdentityDisplayConverter implements IDisplayConverter {

	/**
	 * @see IDisplayConverter#semanticToDisplayValue(java.lang.Object, Collection)
	 *
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public Object semanticToDisplayValue(final Object canonicalValue, final EObject editedObject) {
		return canonicalValue;
	}

	/**
	 * @see IDisplayConverter#displayToSemanticValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToSemanticValue(final Object displayValue, final EObject editedObject) {
		return displayValue;
	}

}
