/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.editor.welcome.internal.Activator;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Abstract handler for the Welcome Page menu commands.
 */
public abstract class AbstractWelcomePageHandler extends AbstractHandler {

	public AbstractWelcomePageHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (editor instanceof IMultiDiagramEditor) {
			IMultiDiagramEditor multiEditor = (IMultiDiagramEditor) editor;
			try {
				IWelcomePageService welcomeService = multiEditor.getServicesRegistry().getService(IWelcomePageService.class);
				doExecute(multiEditor, welcomeService);
			} catch (ServiceException e) {
				throw new ExecutionException("Could not obtain the welcome-page service.", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	protected abstract void doExecute(IMultiDiagramEditor editor, IWelcomePageService welcomeService);

	@Override
	public void setEnabled(Object evaluationContext) {
		boolean enable = false;

		Object editor = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME);

		if (editor instanceof IMultiDiagramEditor) {
			IMultiDiagramEditor multiEditor = (IMultiDiagramEditor) editor;
			ISashWindowsContainer sashContainer = multiEditor.getAdapter(ISashWindowsContainer.class);
			if ((sashContainer != null) && !sashContainer.isDisposed()) {
				IPage activePage = sashContainer.getActiveSashWindowsPage();

				try {
					IWelcomePageService welcomeService = multiEditor.getServicesRegistry().getService(IWelcomePageService.class);
					enable = isEnabled(multiEditor, activePage, welcomeService);
				} catch (ServiceException e) {
					// Not enabled if there's no Welcome Service
					Activator.log.error("Could not obtain the welcome-page service.", e); //$NON-NLS-1$
				}
			}
		}

		setBaseEnabled(enable);
	}

	protected boolean isEnabled(IMultiDiagramEditor editor, IPage activePage, IWelcomePageService welcomeService) {
		return (activePage != null) && (IPageUtils.getRawModel(activePage) == welcomeService.getWelcome());
	}
}
