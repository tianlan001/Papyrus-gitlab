/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.celleditor.action;

import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.papyrus.infra.nattable.mouse.action.MoveDownAxisElementCellMouseAction;
import org.eclipse.swt.graphics.Image;

/**
 *
 * Action to move down the axis element (change index position in the owner list)
 *
 * @since 6.7
 */
public class MoveDownAxisElementCellEditorAxisConfiguration extends AbstractSingleClickActionCellEditorConfiguration {

	/**
	 * the id string of the column
	 */
	private static final String ACTION_NAME = "moveDown"; //$NON-NLS-1$

	/**
	 * the image for move down action
	 */
	private static final Image IMAGE = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrus.infra.nattable.Activator.PLUGIN_ID, "icons/moveDown.png"); //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public MoveDownAxisElementCellEditorAxisConfiguration() {
		super(ACTION_NAME, IMAGE);
	}

	/**
	 * Constructor.
	 *
	 * @param actionName
	 */
	protected MoveDownAxisElementCellEditorAxisConfiguration(final String actionName) {
		super(actionName, IMAGE);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return "org.eclipse.papyrus.infra.nattable.celleditor.action.MoveDownAxisElementCellEditorAxisConfiguration"; //$NON-NLS-1$
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "Single Click action on cell to move down an axis element in its owner list"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractSingleClickActionCellEditorConfiguration#getIMouseAction()
	 *
	 * @return
	 */
	@Override
	protected IMouseAction getIMouseAction() {
		return new MoveDownAxisElementCellMouseAction();
	}
}
