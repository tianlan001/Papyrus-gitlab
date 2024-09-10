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

import org.eclipse.emf.ecore.EObject;

/**
 * This interface is used to convert a semantic value into a value displayed in a dialog (and vice versa)
 */
public interface IDisplayConverter {

	/**
	 *
	 * @param canonicalValue
	 *            the semantic value
	 * @param editedObject
	 *            the edited object (can be used as context to be able to calculate the display value)
	 * @return
	 *         the value to use to display in a dialog
	 */
	public Object semanticToDisplayValue(final Object canonicalValue, final EObject editedObject);


	/**
	 *
	 * @param displayValue
	 *            the value displayed in a dialog
	 * @param editedObject
	 *            the edited object (can be used as context to be able to calculate the display value)
	 * @return
	 *         the semantic value
	 */
	public Object displayToSemanticValue(final Object displayValue, final EObject editedObject);
}
