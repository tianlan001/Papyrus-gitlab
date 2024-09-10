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
package org.eclipse.papyrus.infra.nattable.sort;

import org.eclipse.nebula.widgets.nattable.sort.ISortModel;

/**
 * Common interface to use for sort model
 *
 * @author Vincent Lorenzo
 *
 */
public interface IPapyrusSortModel extends ISortModel {

	/**
	 * remove axis which have been destroyed from the comparison
	 */
	public void updateSort();

	/**
	 * 
	 * @param isInverted
	 *            <code>true</code> when the table isInverted
	 */
	public void setTableInverted(boolean isInverted);
}
