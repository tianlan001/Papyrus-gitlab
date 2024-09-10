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
import org.eclipse.papyrus.infra.nattable.mouse.action.DestroyAxisElementCellMouseAction;
import org.eclipse.swt.graphics.Image;

/**
 * Action to destroy the axis element
 *
 * @since 6.7
 */
public class DestroyAxisElementCellEditorAxisConfiguration extends AbstractSingleClickActionCellEditorConfiguration {

	/**
	 * the id string of the column
	 */
	private static final String ACTION_NAME = "destroyAxisElement"; //$NON-NLS-1$

	/**
	 * the image for delete action
	 */
	private static final Image IMAGE = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrus.infra.nattable.Activator.PLUGIN_ID, "icons/delete_obj.gif"); //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param actionName
	 */
	public DestroyAxisElementCellEditorAxisConfiguration() {
		super(ACTION_NAME, IMAGE);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return "org.eclipse.papyrus.infra.nattable.celleditor.action.DeleteRowElementCellEditorAxisConfiguration"; //$NON-NLS-1$
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "Single Click action on cell to delete an axis"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractSingleClickActionCellEditorConfiguration#getIMouseAction()
	 *
	 * @return
	 */
	@Override
	protected IMouseAction getIMouseAction() {
		return new DestroyAxisElementCellMouseAction();
	}
}
