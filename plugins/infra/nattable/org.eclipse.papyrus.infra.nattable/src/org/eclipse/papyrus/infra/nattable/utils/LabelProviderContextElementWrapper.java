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
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;

/**
 * This object can used as context to find the best label provider and get the text to display.
 * Its allows to have the context of the value to use it in the label provider
 *
 * @author Vincent Lorenzo
 *
 */
public class LabelProviderContextElementWrapper implements ILabelProviderContextElementWrapper {

	/**
	 * the config registry of the table
	 */
	private IConfigRegistry registry;

	/**
	 * the object for which we want the label
	 */
	private Object object;



	/**
	 * *
	 * Constructor.
	 *
	 * @param object
	 *            the cell for which we want the label/icon, ...
	 * @param registry
	 *            the registry used by nattable
	 * 
	 * @deprecated since Eclipse Mars, please use setter method
	 */
	@Deprecated
	public LabelProviderContextElementWrapper(final Object object, final IConfigRegistry registry) {
		this.object = object;
		this.registry = registry;
	}

	/**
	 * 
	 * Constructor.
	 *
	 */
	public LabelProviderContextElementWrapper() {
		// nothing to do
	}

	/**
	 * 
	 * @param configRegistry
	 *            the config registry to use
	 */
	public void setConfigRegistry(IConfigRegistry configRegistry) {
		this.registry = configRegistry;
	}

	/**
	 * 
	 * @param object
	 *            the object for which we want the label/icon,....
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper#getConfigRegistry()
	 *
	 * @return
	 */
	@Override
	public IConfigRegistry getConfigRegistry() {
		return this.registry;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Object getObject() {
		return this.object;
	}

}
