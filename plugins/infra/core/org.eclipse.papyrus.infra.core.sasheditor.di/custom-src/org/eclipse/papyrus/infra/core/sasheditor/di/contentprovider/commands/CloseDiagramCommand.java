/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;

/**
 * A command to be used with the Eclipse Commands Framework.
 * This command allows to close the currently openened diagram.
 *
 * @author cedric dumoulin
 *
 */
public class CloseDiagramCommand extends AbstractHandler {

	/**
	 * Check if the Command is enabled.
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(IPageUtils.canClose(new PageContext(evaluationContext).currentPage));
	}

	/**
	 * Execute the command. This method is called when the action is triggered.
	 *
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PageContext context = new PageContext(event);

		if (context.isValid() && IPageUtils.canClose(context.currentPage)) {
			context.pageManager.closePage(context.currentPageIdentifier);
		}

		return null;
	}

}
