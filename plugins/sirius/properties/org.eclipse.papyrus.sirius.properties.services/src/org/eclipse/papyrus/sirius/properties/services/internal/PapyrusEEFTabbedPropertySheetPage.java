/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.services.internal;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.eef.properties.ui.api.AbstractEEFTabbedPropertySheetPageContributorWrapper;
import org.eclipse.eef.properties.ui.api.EEFTabbedPropertySheetPage;
import org.eclipse.papyrus.infra.core.operation.DelegatingUndoContext;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;

/**
 * This Property sheet page provides the tabbed UI for Papyrus with EEF properties view.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class PapyrusEEFTabbedPropertySheetPage extends EEFTabbedPropertySheetPage {

	/**
	 * The object wrapping the contributor.
	 */
	private AbstractEEFTabbedPropertySheetPageContributorWrapper contributorWrapper;

	/**
	 * Constructor.
	 *
	 * @param contributorWrapper
	 *            the object wrapping the contributor.
	 */
	public PapyrusEEFTabbedPropertySheetPage(AbstractEEFTabbedPropertySheetPageContributorWrapper contributorWrapper) {
		super(contributorWrapper);
		this.contributorWrapper = contributorWrapper;
	}

	/**
	 * Overridden to handle undo/redo actions.
	 * 
	 * @see org.eclipse.eef.properties.ui.api.EEFTabbedPropertySheetPage#setActionBars(org.eclipse.ui.IActionBars)
	 *
	 * @param actionBars
	 *            the action bars for this page
	 */
	@Override
	public void setActionBars(IActionBars actionBars) {
		Object realContributor = contributorWrapper.getRealContributor();
		if (realContributor instanceof IWorkbenchPart) {
			IWorkbenchPart partSite = (IWorkbenchPart) realContributor;
			DelegatingUndoContext undoContext = new DelegatingUndoContext();
			undoContext.setDelegate(org.eclipse.papyrus.infra.tools.util.PlatformHelper.getAdapter(realContributor, IUndoContext.class));
			UndoActionHandler undo = new UndoActionHandler(partSite.getSite(), undoContext);
			RedoActionHandler redo = new RedoActionHandler(partSite.getSite(), undoContext);

			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
		}
	}
}