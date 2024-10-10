/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Ansgar Radermacher (CEA LIST) - Bug 582120
 *****************************************************************************/
package org.eclipse.papyrus.sirius.editor.internal.editor;

import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.Tabbar;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.TabbarToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Papyrus Tabbar. This customization is required to access to the Sirius editor.
 */
@SuppressWarnings("restriction")
public class PapyrusTabbar extends Tabbar {

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param part
	 */
	public PapyrusTabbar(Composite parent, IDiagramWorkbenchPart part) {
		super(parent, part);
	}

	/**
	 * Papyrus variant of Tabbar/Toolbar manager, handling the option that the
	 * part is a multi-diagram editor.
	 * Use static class to avoid "inheritance" from enclosing class
	 */
	static class PapyrusTabbarManager extends TabbarToolBarManager {
	    private IWorkbenchPart wbPart;

		/**
		 * Constructor.
		 *
		 * @param toolBar
		 * @param part
		 */
		public PapyrusTabbarManager(ToolBar toolBar, IWorkbenchPart part) {
			super(toolBar, part);
			this.wbPart = part;
		}

		/**
		 * @see org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.TabbarToolBarManager#getActivePart(org.eclipse.ui.IWorkbenchPage)
		 *
		 * @param activePage
		 * @return
		 */
		
		@Override
		protected IWorkbenchPart getActivePart(IWorkbenchPage activePage) {
			if(wbPart instanceof IMultiDiagramEditor) {
				return ((IMultiDiagramEditor) wbPart).getActiveEditor();
			}
			return wbPart;
		}
		/**
		 * @see org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.TabbarToolBarManager#dispose()
		 *
		 */
		
		@Override
		public void dispose() {
			super.dispose();
			this.wbPart = null;
		}
	};
	
	/**
	 * @see org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.Tabbar#createTabbarToolBarManager(org.eclipse.swt.widgets.ToolBar, org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart)
	 *
	 * @param toolbar
	 * @param part
	 * @return
	 */
	@Override
	protected TabbarToolBarManager createTabbarToolBarManager(ToolBar toolbar, IDiagramWorkbenchPart part) {
		return new PapyrusTabbarManager(toolbar, part);
	}

	/**
	 * @see org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.Tabbar#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 *
	 * @param partSelected
	 * @param selection
	 */

	@Override
	public void selectionChanged(IWorkbenchPart partSelected, ISelection selection) {
		if (partSelected instanceof IMultiDiagramEditor) {
			final IEditorPart part = ((IMultiDiagramEditor) partSelected).getActiveEditor();
			super.selectionChanged(part, selection);
		} else {
			super.selectionChanged(partSelected, selection);
		}

	}
}
