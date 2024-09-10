/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.directedit;

/**
 * Interface used to encapsulate access to a property of the model.
 */
public interface IPropertyAccessor {

	/**
	 * Returns the name of the accessed property.
	 *
	 * @return the name of the accessed property
	 */
	// @unused
	public abstract String getPropertyName();

	/**
	 * Set the new value opf the property.
	 *
	 * @param value
	 *            the new value of the property
	 */
	// @unused
	public abstract void setValue(String value);

	/**
	 * Returns the current value of the accessed property.
	 *
	 * @return the current value of the accessed property
	 */
	public abstract String getValue();
}
