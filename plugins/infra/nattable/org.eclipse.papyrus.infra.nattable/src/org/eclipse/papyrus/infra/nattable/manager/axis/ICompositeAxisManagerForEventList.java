/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.axis;

import java.util.List;

/**
 * @author Vincent Lorenzo
 *
 */
public interface ICompositeAxisManagerForEventList extends ICompositeAxisManager, IAxisManagerForEventList {

	/**
	 *
	 * @param subAxisManager
	 *            the managed axis manager
	 */
	public void setSubAxisManagers(final List<IAxisManagerForEventList> subAxisManager);

}
