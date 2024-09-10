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

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.mouse.action.CellActionMouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.painter.ActionImagePainter;
import org.eclipse.papyrus.infra.nattable.utils.ActionUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract class used to register single Click Action, and Cell Painter on cells
 *
 * @since 6.7
 *
 */
public abstract class AbstractSingleClickActionCellEditorConfiguration implements ICellAxisConfiguration {

	/**
	 * the name of the action (string id used for the axis)
	 */
	private final String actionName;

	/**
	 * the action image to draw in the cell
	 */
	private final Image image;

	/**
	 *
	 * Constructor.
	 *
	 * @param actionName
	 *            the name of the action
	 * @param image
	 *            the image for the action
	 */
	public AbstractSingleClickActionCellEditorConfiguration(final String actionName, final Image image) {
		this.actionName = actionName;
		this.image = image;
	}

	/**
	 *
	 * @return
	 *         the name of the action
	 */
	public String getActionName() {
		return this.actionName;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 *            the table
	 * @param axisElement
	 *            the axis for which we are looking for a cell configuration
	 * @return
	 */
	@Override
	public boolean handles(final Table table, final Object axisElement) {
		if (ActionUtils.isAction(axisElement)) {
			return ActionUtils.getActionId((IAxis) axisElement).endsWith(this.actionName);
		}
		return false;
	}

	/**
	 *
	 * @return
	 *         the event matcher to use for the action
	 */
	protected MouseEventMatcher getMouseEventMatcher() {
		return new CellActionMouseEventMatcher(SWT.NONE, GridRegion.BODY.toString(), MouseEventMatcher.LEFT_BUTTON, this.actionName);
	}

	/**
	 *
	 * @return
	 *         the mouse action
	 */
	protected abstract IMouseAction getIMouseAction();

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param axis
	 * @param configLabel
	 */
	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new ActionImagePainter(this.image, this.actionName), DisplayMode.NORMAL, configLabel);
		final INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final NatTable natTable = manager.getAdapter(NatTable.class);
		final UiBindingRegistry uiBindingRegistry = natTable.getUiBindingRegistry();
		uiBindingRegistry.registerSingleClickBinding(getMouseEventMatcher(), getIMouseAction());
	}
}
