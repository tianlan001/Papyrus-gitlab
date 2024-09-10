/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;


/**
 * A ContentProvider returning empty collections
 *
 * @author Camille Letavernier
 *
 */
public class EmptyContentProvider extends AbstractStaticContentProvider {

	/**
	 * Singleton instance
	 */
	public static final EmptyContentProvider instance = new EmptyContentProvider();

	private final Object[] value = new Object[0];

	private EmptyContentProvider() {

	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 * 		an empty array
	 */
	@Override
	public Object[] getElements() {
		return value;
	}

}
