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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.celleditor.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class allows to declare several additional buttons for a cell editor
 *
 * @since 6.6
 */
public class CompositeCellEditorButtonAction implements ICellEditorButtonAction {

	/**
	 * The list of proposed action
	 */
	private List<ICellEditorButtonAction> actions = new ArrayList<>();

	/**
	 *
	 * @param action
	 *            the action to add, can't be <code>null</code>
	 */
	public void addAction(final ICellEditorButtonAction action) {
		Assert.isNotNull(action);
		this.actions.add(action);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#configureAction(org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor, org.eclipse.swt.widgets.Composite, java.lang.Object,
	 *      org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param editor
	 * @param composite
	 * @param originalCanonicalValue
	 * @param cell
	 * @param configRegistry
	 */
	@Override
	public boolean configureAction(IActionCellEditor editor, Composite composite, Object originalCanonicalValue, ILayerCell cell, IConfigRegistry configRegistry) {
		boolean result = false;
		for (ICellEditorButtonAction current : this.actions) {
			result |= current.configureAction(editor, composite, originalCanonicalValue, cell, configRegistry);
		}
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#setImage(org.eclipse.swt.graphics.Image)
	 *
	 * @param image
	 */
	@Override
	public void setImage(Image image) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#setText(java.lang.String)
	 *
	 * @param text
	 */
	@Override
	public void setText(String text) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#setTooltipText(java.lang.String)
	 *
	 * @param tooltipText
	 */
	@Override
	public void setTooltipText(String tooltipText) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		for (ICellEditorButtonAction current : this.actions) {
			if (current.isEnabled()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#createControl(org.eclipse.swt.widgets.Composite, int)
	 *
	 * @param parent
	 * @param style
	 * @return
	 */
	@Override
	public List<Control> createControl(Composite parent, int style) {
		final List<Control> controls = new ArrayList<>();
		for (ICellEditorButtonAction current : this.actions) {
			if (current.isEnabled()) {
				controls.addAll(current.createControl(parent, style));
			}
		}
		return controls;
	}

}
