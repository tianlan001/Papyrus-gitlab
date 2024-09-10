/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.custom;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineRefreshJob;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineTreeState;

/**
 * A custom OutlineJob allowing to avoid NPE in some strange cases
 */
public class PapyrusOutlineRefreshJob extends OutlineRefreshJob {

	/**
	 * @see org.eclipse.xtext.ui.editor.outline.impl.OutlineRefreshJob#internalRefreshOutlineModel(org.eclipse.xtext.ui.editor.outline.impl.OutlineTreeState, org.eclipse.xtext.ui.editor.outline.impl.OutlineTreeState,
	 *      org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider)
	 *
	 * @param formerState
	 * @param newState
	 * @param treeProvider
	 * @return
	 */
	@Override
	protected IOutlineNode internalRefreshOutlineModel(OutlineTreeState formerState, OutlineTreeState newState, IOutlineTreeProvider treeProvider) {
		try {
			return super.internalRefreshOutlineModel(formerState, newState, treeProvider);
		} catch (NullPointerException e) {
			// silent exception
		}
		return null;
	}
}
