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
 * Default implementation for the interface IPropertyAccessor.
 */
public abstract class PropertyAccessor implements IPropertyAccessor {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.cea.papyrus.core.common.directedit.IPropertyAccessor#getPropertyName
	 * ()
	 */
	/**
	 *
	 *
	 * @return
	 */
	@Override
	public String getPropertyName() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.cea.papyrus.core.common.directedit.IPropertyAccessor#getValue()
	 */
	/**
	 *
	 *
	 * @return
	 */
	@Override
	public abstract String getValue();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.cea.papyrus.core.common.directedit.IPropertyAccessor#setValue(java
	 * .lang.String)
	 */
	/**
	 *
	 *
	 * @param value
	 */
	@Override
	public abstract void setValue(String value);
}
