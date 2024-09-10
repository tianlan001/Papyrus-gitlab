/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.converter;

import org.eclipse.ui.services.IDisposable;

/**
 * Common interface for string converter
 *
 * @author Vincent Lorenzo
 * @since 1.2
 *
 */
public interface IStringValueConverter extends IDisposable {

	/**
	 *
	 * @param type
	 *            an object representing the type of the in which we want to convert the string
	 * @param valueAsString
	 *            the value represented by a string
	 * @return
	 * 		a {@link ConvertedValueContainer}
	 */
	public ConvertedValueContainer<?> deduceValueFromString(final Object type, final String valueAsString);
}
