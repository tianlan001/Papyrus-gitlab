/*****************************************************************************
 * Copyright (c) 2009, 2014 CEA LIST and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 448305
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.part;

import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;

public class UMLDiagramActionBarContributor extends DiagramActionBarContributor {

	@Override
	protected Class getEditorClass() {
		return UmlGmfDiagramEditor.class;
	}

	@Override
	protected String getEditorId() {
		return "org.eclipse.papyrus.UMLDiagramEditorID";
	}

	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		IMenuManager menuManager = bars.getMenuManager();

		IContributionItem undoAction = bars.getMenuManager().findMenuUsingPath("undoGroup"); //$NON-NLS-1$
		if (undoAction != null) {
			menuManager.remove(undoAction);
		}
		// print preview
		IMenuManager fileMenu = bars.getMenuManager().findMenuUsingPath(IWorkbenchActionConstants.M_FILE);
		if (null != fileMenu) {
			fileMenu.remove("pageSetupAction"); //$NON-NLS-1$
		}
	}
}
