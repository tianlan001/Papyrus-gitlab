/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.views.documentation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.views.documentation.Activator;
import org.eclipse.papyrus.views.documentation.views.DocumentationView;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler used to show the 'documentation' view.
 */
public class DocumentationViewHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		openViewAndSetSelection(HandlerUtil.getCurrentStructuredSelection(event));
		return null;
	}

	/**
	 * Open the view 'documentation' and set the selection
	 *
	 * @param structuredSelection
	 *            The objects to search.
	 */
	public void openViewAndSetSelection(final IStructuredSelection structuredSelection) {
		DocumentationView documentationView = openDocumentationView();
		documentationView.setSelectedElement(structuredSelection);
	}

	/**
	 * Show the view 'documentation'.
	 */
	public DocumentationView openDocumentationView() {
		try {

			final IWorkbenchPage activePage = EditorHelper.getActiveWindow().getActivePage();
			return (DocumentationView) activePage.showView(DocumentationView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
		} catch (final PartInitException e) {
			Activator.logError(e.getMessage());
			return null;
		}
	}
}
