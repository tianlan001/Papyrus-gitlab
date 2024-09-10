/*****************************************************************************
 * Copyright (c) 2020 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
  *****************************************************************************/

package org.eclipse.papyrus.example.uml.nattable.empty.line.managers.axis;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.example.uml.nattable.empty.line.cell.editor.CreateClassFromHeaderCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.uml.nattable.manager.axis.EmptyLineUMLElementTreeAxisManagerForEventList;


/**
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 */
public class CustomEmptyLineUMLElementTreeAxisManagerForEventList extends EmptyLineUMLElementTreeAxisManagerForEventList {


	/**
	 *
	 * Constructor.
	 *
	 */
	public CustomEmptyLineUMLElementTreeAxisManagerForEventList() {
		setCreateEmptyRow(true);
	}


	/**
	 * the action used to create element from single left click on row header
	 */
	private static final ICellAxisConfiguration conf = new CreateClassFromHeaderCellEditorConfiguration();


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#managedHideShowCategoriesForDepth(java.util.List, java.util.List)
	 *
	 * @param depthToHide
	 * @param depthToShow
	 */
	@Override
	public void managedHideShowCategoriesForDepth(List<Integer> depthToHide, List<Integer> depthToShow) {
		super.managedHideShowCategoriesForDepth(depthToHide, depthToShow);
		// trick to be able to register action on row header on single left click
		// warning, it requires to hide categories in the table, if not, this method is not called
		conf.configureCellEditor(((NatTable) getTableManager().getAdapter(NatTable.class)).getConfigRegistry(), null, ""); //$NON-NLS-1$
	}
}
