/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net -  Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.viewers.ILabelProvider;

/**
 * IDependableLabelProvider interface used to create dependency from a label provider to a {@link ILabelProvider}.
 * 
 * @since 3.0
 */
public interface IDependableLabelProvider {

	/**
	 * @return the {@link ILabelProvider} to depend of.
	 * @since 3.0
	 */
	public ILabelProvider getLabelProvider();

	/**
	 * Set the provider to depend.
	 * 
	 * @param provider
	 *            The {@link ILabelProvider} to depend of.
	 * @since 3.0
	 */
	public void setLabelProvider(ILabelProvider provider);

}
