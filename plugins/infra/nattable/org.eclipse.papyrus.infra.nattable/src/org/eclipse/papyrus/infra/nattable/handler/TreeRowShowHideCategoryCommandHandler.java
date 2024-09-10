/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 *
 * @deprecated since Papyrus 4.8, use TreeRowHideAllCategoriesHandler
 *
 *             this class is not used
 *             this class will be removed in Papyrus 5.0 (see bug Bug 562870)
 *
 */
@Deprecated
public class TreeRowShowHideCategoryCommandHandler extends AbstractTreeRowHideShowCategoryHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param arg0
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {


		// TODO not yet used
		return null;
	}

}
