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

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * IDependableContentProvider interface used to create dependency from a content provider to a {@link ITreeContentProvider}.
 * 
 * @since 3.0
 */
public interface IDependableContentProvider {

	/**
	 * @return the {@link ITreeContentProvider} to depend of.
	 * @since 3.0
	 */
	public ITreeContentProvider getContentProvider();

	/**
	 * Set the provider to depend.
	 * 
	 * @param provider
	 *            The {@link ITreeContentProvider} to depend of.
	 * @since 3.0
	 */
	public void setContentProvider(final ITreeContentProvider provider);

}
