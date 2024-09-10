/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.widgets.util;

import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;

/**
 * Interface used to declare a ISNameResolutionHelper
 *
 */
public interface ISetNameResolutionHelper {

	/**
	 * 
	 * @param helper
	 *            a name resolution helper
	 */
	public void setNameResolutionHelper(INameResolutionHelper helper);
}
