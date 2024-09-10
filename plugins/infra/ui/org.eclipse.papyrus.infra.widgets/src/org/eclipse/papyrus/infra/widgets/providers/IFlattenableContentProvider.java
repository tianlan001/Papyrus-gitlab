/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

/**
 *
 * Interfaces for a content provider which can be flat or not
 *
 */
public interface IFlattenableContentProvider {

	/**
	 *
	 * @param isFlat
	 *            <code>true</code> if we display the possible value as a flat view
	 */
	public void setFlat(boolean isFlat);

	/**
	 * @since 3.0
	 * @return <code>true</code> if we display the possible value as a flat view
	 */
	public boolean isFlat();
}
