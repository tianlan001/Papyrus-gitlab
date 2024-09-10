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

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IComponentPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageVisitor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A convenient aggregation of objects needed for processing page management commands
 * in the context of the Papyrus multi-diagram editor.
 */
class PageContext {
	final IEditorPart editor;
	final IPageManager pageManager;
	final ISashWindowsContainer sashContainer;
	final Object currentPageIdentifier;
	final IPage currentPage;

	PageContext(ExecutionEvent event) {
		this(HandlerUtil.getActiveEditor(event));
	}

	PageContext(Object evaluationContext) {
		this((IEditorPart) HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME));
	}

	PageContext(IEditorPart activeEditor) {
		super();

		editor = activeEditor;
		sashContainer = (activeEditor == null) ? null : activeEditor.getAdapter(ISashWindowsContainer.class);

		if ((sashContainer == null) || sashContainer.isDisposed()) {
			pageManager = null;
			currentPage = null;
			currentPageIdentifier = null;
		} else {
			pageManager = activeEditor.getAdapter(IPageManager.class);
			currentPage = sashContainer.getActiveSashWindowsPage();
			currentPageIdentifier = (currentPage == null) ? null : IPageUtils.getRawModel(currentPage);
		}
	}

	boolean isValid() {
		return (pageManager != null) && (currentPageIdentifier != null) && (sashContainer != null);
	}

	int getOpenPageCount() {
		return getOpenPages().size();
	}

	Collection<IPage> getOpenPages() {
		Collection<IPage> result;

		if (!isValid()) {
			result = Collections.emptyList();
		} else {
			result = new java.util.ArrayList<>();
			sashContainer.visit(new IPageVisitor() {

				@Override
				public void accept(IEditorPage page) {
					result.add(page);
				}

				@Override
				public void accept(IComponentPage page) {
					result.add(page);
				}
			});
		}

		return result;
	}
}
